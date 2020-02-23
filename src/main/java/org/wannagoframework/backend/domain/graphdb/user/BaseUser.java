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

package org.wannagoframework.backend.domain.graphdb.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.wannagoframework.backend.domain.graphdb.BaseEntity;
import org.wannagoframework.dto.utils.StoredFile;

/**
 * This class represent a User.
 *
 * A User can be : - System user (internal) - Admin user (to administer the platform) - Player or
 * Fan - Owner of a Place
 *
 * @author Wanna .
 * @version 1.0
 * @since 2019-03-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseUser extends BaseEntity {

  private String securityUserId;

  private String email;

  private String firstName;

  private String lastName;

  private String nickName;

  @org.springframework.data.annotation.Transient
  @org.neo4j.ogm.annotation.Transient
  private StoredFile avatar = null;

  private String avatarId = null;
}
