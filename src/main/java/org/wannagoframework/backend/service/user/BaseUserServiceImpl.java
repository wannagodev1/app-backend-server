/*
 *
 *  DO YOU WANNA PLAY CONFIDENTIAL
 *   __________________
 *
 *   [2018] - [2019] Do You Wanna Play
 *   All Rights Reserved.
 *
 *   NOTICE:  All information contained herein is, and remains the property of "Do You Wanna Play"
 *   and its suppliers, if any. The intellectual and technical concepts contained herein are
 *   proprietary to "Do You Wanna Play" and its suppliers and may be covered by Morocco. and Foreign
 *   Patents, patents in process, and are protected by trade secret or copyright law.
 *   Dissemination of this information or reproduction of this material is strictly forbidden unless
 *    prior written permission is obtained from "Do You Wanna Play".
 */

package org.wannagoframework.backend.service.user;

import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wannagoframework.backend.client.ResourceService;
import org.wannagoframework.backend.client.SecurityUserService;
import org.wannagoframework.backend.domain.graphdb.user.BaseUser;
import org.wannagoframework.backend.exception.UserFriendlyDataException;
import org.wannagoframework.backend.repository.graphdb.user.BaseUserRepository;
import org.wannagoframework.dto.domain.security.SecurityUser;
import org.wannagoframework.dto.serviceQuery.ServiceResult;
import org.wannagoframework.dto.serviceQuery.generic.DeleteByStrIdQuery;
import org.wannagoframework.dto.serviceQuery.generic.GetByStrIdQuery;
import org.wannagoframework.dto.serviceQuery.generic.SaveQuery;
import org.wannagoframework.dto.utils.AppContextThread;
import org.wannagoframework.dto.utils.StoredFile;

/**
 * @author WannaGo Dev1.
 * @version 1.0
 * @since 2019-03-06
 */
@Service
@Transactional(readOnly = true)
public class BaseUserServiceImpl<T extends BaseUser> implements BaseUserService<T> {

  public static final String MODIFY_LOCKED_USER_NOT_PERMITTED = "User has been locked and cannot be modified or deleted";
  private static final String DELETING_SELF_NOT_PERMITTED = "You cannot delete your own account";

  private final BaseUserRepository<T> userRepository;
  private final ResourceService resourceService;
  private final SecurityUserService securityUserService;

  public BaseUserServiceImpl(BaseUserRepository userRepository,
      ResourceService resourceService,
      SecurityUserService securityUserService) {
    this.userRepository = userRepository;
    this.resourceService = resourceService;
    this.securityUserService = securityUserService;
  }

  @Override
  public T getBySecurityUserId(String securityUserId) {
    T result = userRepository.getBySecurityUserId(securityUserId);
    return result;
  }

  @Override
  public T getById(Long id) {
    return userRepository.findById(id).get();
  }

  @Override
  public BaseUserRepository<T> getRepository() {
    return userRepository;
  }

  public Page<T> find(Pageable pageable) {
    return getRepository().findAll(pageable);
  }

  @Override
  public Page<T> findAnyMatching(String filter, Boolean showInactive, Pageable pageable) {
    if (StringUtils.isNotBlank(filter)) {
      return userRepository
          .findAnyMatching(filter, pageable);
    } else {
      return userRepository.findAll(pageable);
    }
  }

  @Override
  public long countAnyMatching(String filter, Boolean showInactive) {
    if (StringUtils.isNotBlank(filter)) {
      return userRepository
          .countAnyMatching(filter);
    } else {
      return userRepository.count();
    }
  }

  @Override
  @Transactional
  public T save(T entity) {
    throwIfUserLocked(entity);

    Optional<T> _previousValue = Optional.empty();
    if (entity.getId() != null) {
      _previousValue = userRepository.findById(entity.getId());
    }
    T previousValue = _previousValue.orElse(null);

    if (previousValue != null) {
      if (previousValue.getAvatarId() != null && entity.getAvatarId() == null) {
        // Image change, remove old one
        resourceService.delete(new DeleteByStrIdQuery(previousValue.getAvatarId()));
      }
    }

    if (entity.getAvatar() != null && entity.getAvatar().getContent() != null) {
      StoredFile newImage = resourceService.save(new SaveQuery<>(entity.getAvatar())).getData();
      entity.setAvatar(newImage);
      entity.setAvatarId(newImage.getId());
    }

    ServiceResult<SecurityUser> _securityUser = securityUserService
        .getById(new GetByStrIdQuery(entity.getSecurityUserId()));
    if (_securityUser.getIsSuccess()) {
      SecurityUser securityUser = _securityUser.getData();
      boolean hasChanged = false;
      if ((StringUtils.isNotBlank(securityUser.getFirstName()) && !securityUser.getFirstName()
          .equalsIgnoreCase(entity.getFirstName())) ||
          StringUtils.isBlank(securityUser.getFirstName()) && !StringUtils
              .isNotBlank(securityUser.getFirstName())) {
        securityUser.setFirstName(entity.getFirstName());
        hasChanged = true;
      }
      if ((StringUtils.isNotBlank(securityUser.getLastName()) && !securityUser.getLastName()
          .equalsIgnoreCase(entity.getLastName())) ||
          StringUtils.isBlank(securityUser.getLastName()) && !StringUtils
              .isNotBlank(securityUser.getLastName())) {
        securityUser.setLastName(entity.getLastName());
        hasChanged = true;
      }
      if ((StringUtils.isNotBlank(securityUser.getEmail()) && !securityUser.getEmail()
          .equalsIgnoreCase(entity.getEmail())) ||
          StringUtils.isBlank(securityUser.getEmail()) && !StringUtils
              .isNotBlank(securityUser.getEmail())) {
        securityUser.setEmail(entity.getEmail());
        hasChanged = true;
      }
      if ((StringUtils.isNotBlank(securityUser.getNickName()) && !securityUser.getNickName()
          .equalsIgnoreCase(entity.getNickName())) ||
          StringUtils.isBlank(securityUser.getNickName()) && !StringUtils
              .isNotBlank(securityUser.getNickName())) {
        securityUser.setNickName(entity.getNickName());
        hasChanged = true;
      }
      if (hasChanged) {
        securityUserService.save(new SaveQuery<>(securityUser));
      }
    }
    return userRepository.save(entity); // , 0);
  }

  @Override
  @Transactional
  public void delete(T entity) {
    throwIfDeletingSelf(entity);
    throwIfUserLocked(entity);

    if (entity.getAvatar() != null) {
      resourceService.delete(new DeleteByStrIdQuery(entity.getAvatar().getId()));
    }

    getRepository().delete(entity);
  }

  private void throwIfDeletingSelf(T user) {
    if (AppContextThread.getCurrentSecurityUserId().equals(user.getSecurityUserId())) {
      throw new UserFriendlyDataException(DELETING_SELF_NOT_PERMITTED);
    }
  }

  private void throwIfUserLocked(T entity) {
    ServiceResult<SecurityUser> securityUserResult = securityUserService
        .getById(new GetByStrIdQuery(entity.getSecurityUserId()));
    if (securityUserResult.getData() != null && !securityUserResult.getData()
        .isAccountNonLocked()) {
      throw new UserFriendlyDataException(MODIFY_LOCKED_USER_NOT_PERMITTED);
    }
  }
}
