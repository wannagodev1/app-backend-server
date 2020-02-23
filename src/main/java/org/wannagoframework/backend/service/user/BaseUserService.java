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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.wannagoframework.backend.domain.graphdb.user.BaseUser;
import org.wannagoframework.backend.service.CrudGraphdbService;

/**
 * @author WannaGo Dev1.
 * @version 1.0
 * @since 2019-03-07
 */
public interface BaseUserService<T extends BaseUser> extends CrudGraphdbService<T> {

  Page<T> findAnyMatching(String filter, Boolean showInactive, Pageable pageable);

  long countAnyMatching(String filter, Boolean showInactive);

  T getBySecurityUserId(String securityUserId);

  T getById(Long id);
}
