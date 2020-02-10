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


package org.wannagoframework.backend.repository.graphdb.reference;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.wannagoframework.backend.domain.graphdb.reference.SubRegion;

/**
 * @author WannaGo Dev1.
 * @version 1.0
 * @since 2019-03-16
 */
public interface SubRegionRepository extends Neo4jRepository<SubRegion, Long> {
  @Query(value =
      "CALL db.INDEX.fulltext.queryNodes('SubRegion-Trl', {name}) YIELD node RETURN node",
      countQuery = "CALL db.INDEX.fulltext.queryNodes('SubRegion-Trl', {name}) YIELD node RETURN count(node)")
  Page<SubRegion> findByName(String name, Pageable pageable);

  @Query("CALL db.INDEX.fulltext.queryNodes('SubRegion-Trl', {name}) YIELD node RETURN count(node)")
  long countByName(String name);

  @Query("MATCH (m:SubRegion),(c:Country) WHERE id(m) = {subRegionId} AND id(c) = {countryId}"
      + " CREATE (m)-[r:HAS_COUNTRIES]->(c) RETURN m")
  SubRegion addCountryToSubRegion( Long subRegionId, Long countryId );
}
