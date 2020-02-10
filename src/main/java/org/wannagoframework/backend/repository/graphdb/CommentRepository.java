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


package org.wannagoframework.backend.repository.graphdb;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.wannagoframework.backend.domain.graphdb.Comment;

/**
 * @author WannaGo Dev1.
 * @version 1.0
 * @since 2019-07-14
 */
public interface CommentRepository extends Neo4jRepository<Comment, Long> {

  @Query(value =
      "MATCH (m:Comment)-[:HAS_RELATED_ENTITY]->(p) "
          + "WHERE NOT ((m)-[:HAS_PARENT]->()) AND id(p) = {relatedEntityId} RETURN m ORDER BY m.created DESC",
      countQuery = "MATCH (m:Comment)-[:HAS_RELATED_ENTITY]->(p) "
          + " WHERE NOT ((m)-[:HAS_PARENT]->()) AND id(p) = {relatedEntityId} RETURN count(m)")
  Page<Comment> getRootComments(Long relatedEntityId, Pageable pageable);

  @Query(
      "MATCH (m:Comment)-[:HAS_RELATED_ENTITY]->(p) "
          + " WHERE NOT ((m)-[:HAS_PARENT]->()) AND id(p) = {relatedEntityId} RETURN count(m)")
  long countRootComments(Long relatedEntityId);

  @Query(value =
      "MATCH (m:Comment)-[:HAS_PARENT]->(n1:Comment) "
          + "WHERE id(n1) = {parentId} RETURN m ORDER BY m.created DESC",
      countQuery =
          "MATCH (m:Comment)-[:HAS_PARENT]->(n1:Comment) "
              + "WHERE id(n1) = {parentId} RETURN count(DISTINCT m)")
  Page<Comment> getCommentsFilterByParent(Long parentId, Pageable pageable);

  @Query(value =
      "MATCH (m:Comment)-[:HAS_PARENT]->(n1:Comment) "
          + "WHERE id(n1) = {parentId} RETURN count(DISTINCT m)")
  long countCommentsFilterByParent(Long parentId);
}
