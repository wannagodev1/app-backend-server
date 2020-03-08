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
import org.springframework.data.neo4j.annotation.Query;
import org.wannagoframework.backend.domain.graphdb.user.BaseUser;
import org.wannagoframework.baseserver.repository.graphdb.BaseRepository;

/**
 * @author WannaGo Dev1.
 * @version 1.0
 * @since 2019-03-06
 */
public interface BaseUserRepository<T extends BaseUser> extends BaseRepository<T> {

  T findByEmailIgnoreCase(String email);

  @Depth(0)
  T getBySecurityUserId(String securityUserId);

  @Query(value = "MATCH (u:BaseUser) WHERE u.email =~ {filter} OR u.firstName =~ {filter} OR u.lastName =~ {filter} RETURN u, c",
      countQuery = "MATCH (u:BaseUser) WHERE u.email =~ {filter} OR u.firstName =~ {filter} OR u.lastName =~ {filter} RETURN count(u)")
  Page<T> findAnyMatching(String filter, Pageable pageable);

  @Query(value = "MATCH (u:BaseUser) WHERE u.email =~ {filter} OR u.firstName =~ {filter} OR u.lastName =~ {filter} RETURN count(u)")
  long countAnyMatching(String filter);
}
