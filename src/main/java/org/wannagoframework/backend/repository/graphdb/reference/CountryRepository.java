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

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.wannagoframework.backend.domain.graphdb.reference.Country;
import org.wannagoframework.backend.domain.graphdb.reference.IntermediateRegion;
import org.wannagoframework.backend.domain.graphdb.reference.Region;
import org.wannagoframework.backend.domain.graphdb.reference.SubRegion;

/**
 * @author WannaGo Dev1.
 * @version 1.0
 * @since 2019-03-16
 */
public interface CountryRepository extends Neo4jRepository<Country, Long> {
  @Query(value = "CALL db.index.fulltext.queryNodes('Country-Trl', {name}) YIELD node RETURN node",
      countQuery = "CALL db.index.fulltext.queryNodes('Country-Trl', {name}) YIELD node RETURN count(node)")
  Page<Country> findByNameLike(String name, Pageable pageable);

  @Query(value = "CALL db.index.fulltext.queryNodes('Country-Trl', {name}) YIELD node RETURN count(node)")
  long countByNameLike(String name);

  @Query(value =
      "CALL db.index.fulltext.queryNodes('Country-Trl', {name}) YIELD node "
          + " MATCH (m:Country)"
          + " WHERE m.iso2  =~ {iso2} OR m.iso3  =~ {iso3} OR id(node) = id(m) RETURN DISTINCT(m)",
      countQuery = "CALL db.index.fulltext.queryNodes('Country-Trl', {name}) YIELD node "
          + " MATCH (m:Country)"
          + " WHERE m.iso2  =~ {iso2} OR m.iso3  =~ {iso3} OR id(node) = id(m) RETURN count(DISTINCT(m))")
  Page<Country> findByNameLikeOrIso2LikeOrIso3Like(String name, String iso2, String iso3, Pageable pageable);

  @Query("CALL db.index.fulltext.queryNodes('Country-Trl', {name}) YIELD node "
      + " MATCH (m:Country)"
      + " WHERE m.iso2  =~ {iso2} OR m.iso3  =~ {iso3} OR id(node) = id(m) RETURN count(DISTINCT(m))")
  long countByNameLikeOrIso2LikeOrIso3Like(String name, String iso2, String iso3);

  @Query(value = "MATCH (m:Country) "
      + " OPTIONAL MATCH (m:Country)-[rr:HAS_REGION]-(r:Region)"
      + " OPTIONAL MATCH (m:Country)-[rs:HAS_SUB_REGION]-(s:SubRegion)"
      + " OPTIONAL MATCH (m:Country)-[ri:HAS_INTERMEDIATE_REGION]-(i:IntermediateRegion)"
      + " RETURN m, rr, r, rs, s, ri, i",
      countQuery = "MATCH (m:Country) RETURN count(m)")
  Page<Country> findAll(Pageable pageable);

  @Query("MATCH (m:Country) RETURN count(m)")
  long count();

  Optional<Country> getByIso2(String iso2);

  Optional<Country> getByIso3(String iso3);

  List<Country> findByRegion(Region region);

  List<Country> findBySubRegion(SubRegion subRegion);

  List<Country> findByIntermediateRegion(IntermediateRegion intermediateRegion);

  @Query("MATCH (m:Country) WHERE id(m) = {id} RETURN m")
  Country getById(Long id);

}
