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

package org.wannagoframework.backend.repository.graphdb.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Depth;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.wannagoframework.backend.domain.graphdb.user.BaseUser;

/**
 * @author WannaGo Dev1.
 * @version 1.0
 * @since 2019-03-06
 */
public interface BaseUserRepository<T extends BaseUser> extends Neo4jRepository<T, Long> {

  T findByEmailIgnoreCase(String email);

  @Depth(0)
  T getBySecurityUserId(String securityUserId);

  Page<T> findByEmailRegexOrFirstNameRegexOrLastNameRegex(String emailRegexp,
      String firstNameRegexp, String lastNameRegexp, Pageable pageable);

  long countByEmailRegexOrFirstNameRegexOrLastNameRegex(String emailRegexp, String firstNameRegexp,
      String lastNameRegexp);
}
