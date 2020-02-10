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


package org.wannagoframework.backend.service.reference;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wannagoframework.backend.domain.graphdb.reference.IntermediateRegion;
import org.wannagoframework.backend.repository.graphdb.reference.IntermediateRegionRepository;
import org.wannagoframework.commons.utils.HasLogger;

/**
 * @author WannaGo Dev1.
 * @version 1.0
 * @since 2019-03-27
 */
@Transactional(readOnly = true, transactionManager = "graphdbTransactionManager")
@Service
public class IntermediateRegionServiceImpl implements IntermediateRegionService, HasLogger {

  private final IntermediateRegionRepository intermediateRegionRepository;

  public IntermediateRegionServiceImpl(IntermediateRegionRepository intermediateRegionRepository) {
    this.intermediateRegionRepository = intermediateRegionRepository;
  }

  @Override
  public List<IntermediateRegion> findAll() {
    return StreamSupport.stream(intermediateRegionRepository.findAll().spliterator(), false)
        .collect(Collectors.toList());
  }

  @Override
  public Page<IntermediateRegion> findAnyMatching(String filter, String iso3Language,
      Pageable pageable) {
    if (StringUtils.isNotBlank(filter)) {
      return intermediateRegionRepository.findByName( filter, pageable );
    } else {
      return intermediateRegionRepository.findAll(pageable);
    }
  }

  @Override
  public long countAnyMatching(String filter, String iso3Language) {
    if (StringUtils.isNotBlank(filter)) {
      return intermediateRegionRepository.countByName(filter);
    } else {
      return intermediateRegionRepository.count();
    }
  }

  @Override
  public IntermediateRegion getById(Long id) {
    return intermediateRegionRepository.findById(id).get();
  }

  @Override
  public Neo4jRepository<IntermediateRegion, Long> getRepository() {
    return intermediateRegionRepository;
  }
}

