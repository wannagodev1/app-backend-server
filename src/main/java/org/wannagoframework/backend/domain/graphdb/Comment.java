/*
 * This file is part of the WannaGo distribution (https://github.com/wannago).
 * Copyright (c) [2019] - [2020].
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */


package org.wannagoframework.backend.domain.graphdb;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.wannagoframework.dto.utils.StoredFile;

/**
 * A comment can be attached to anything
 *
 * @author WannaGo Dev1.
 * @version 1.0
 * @since 2019-03-07
 */
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"relatedEntity", "parent"})
@NodeEntity
public class Comment extends BaseEntity {

  /**
   * Text of the Comment
   */
  private String content;

  /**
   * List of attachments attached to the comment
   */
  @org.springframework.data.annotation.Transient
  @org.neo4j.ogm.annotation.Transient
  private List<StoredFile> attachments = null;

  private List<String> attachmentIds = new ArrayList<>();

  /**
   * In case of a reply, this is the parent comment
   */
  @Relationship("HAS_PARENT")
  private Comment parent;

  @Relationship("HAS_RELATED_ENTITY")
  private BaseEntity relatedEntity;
}
