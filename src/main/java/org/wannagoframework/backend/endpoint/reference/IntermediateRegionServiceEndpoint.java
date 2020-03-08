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


package org.wannagoframework.backend.endpoint.reference;

import java.util.List;
import ma.glasnost.orika.MappingContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wannagoframework.backend.domain.graphdb.reference.IntermediateRegion;
import org.wannagoframework.backend.service.reference.IntermediateRegionService;
import org.wannagoframework.commons.endpoint.BaseEndpoint;
import org.wannagoframework.commons.utils.OrikaBeanMapper;
import org.wannagoframework.dto.serviceQuery.BaseRemoteQuery;
import org.wannagoframework.dto.serviceQuery.ServiceResult;
import org.wannagoframework.dto.serviceQuery.generic.DeleteByIdQuery;
import org.wannagoframework.dto.serviceQuery.generic.GetByIdQuery;
import org.wannagoframework.dto.serviceQuery.generic.SaveQuery;
import org.wannagoframework.dto.serviceQuery.reference.intermediateRegion.CountAnyMatchingQuery;
import org.wannagoframework.dto.serviceQuery.reference.intermediateRegion.FindAnyMatchingQuery;

/**
 * @author WannaGo Dev1.
 * @version 1.0
 * @since 2019-06-05
 */
@RestController
@RequestMapping("/intermediateRegionService")
public class IntermediateRegionServiceEndpoint extends BaseEndpoint {

  private final IntermediateRegionService intermediateRegionService;

  public IntermediateRegionServiceEndpoint(IntermediateRegionService intermediateRegionService,
      OrikaBeanMapper mapperFacade) {
    super(mapperFacade);
    this.intermediateRegionService = intermediateRegionService;
  }

  @PostMapping(value = "/findAll")
  public ResponseEntity<ServiceResult> findAll(BaseRemoteQuery query) {
    String loggerPrefix = getLoggerPrefix("findAll");
    try {
      List<IntermediateRegion> result = intermediateRegionService
          .findAll();
      return handleResult(loggerPrefix,
          mapperFacade
              .mapAsList(result, org.wannagoframework.dto.domain.reference.IntermediateRegion.class,
                  getOrikaContext(query)));
    } catch (Throwable t) {
      return handleResult(loggerPrefix, t);
    }
  }

  @PostMapping(value = "/findAnyMatching")
  public ResponseEntity<ServiceResult> findAnyMatching(@RequestBody FindAnyMatchingQuery query) {
    String loggerPrefix = getLoggerPrefix("findAnyMatching");
    try {
      Page<IntermediateRegion> result = intermediateRegionService
          .findAnyMatching(query.getFilter(), query.getIso3Language(),
              mapperFacade.map(query.getPageable(),
                  Pageable.class, getOrikaContext(query)));
      return handleResult(loggerPrefix,
          mapperFacade
              .map(result, org.wannagoframework.dto.utils.Page.class, getOrikaContext(query)));
    } catch (Throwable t) {
      return handleResult(loggerPrefix, t);
    }
  }

  @PostMapping(value = "/countAnyMatching")
  public ResponseEntity<ServiceResult> countAnyMatching(@RequestBody CountAnyMatchingQuery query) {
    String loggerPrefix = getLoggerPrefix("countAnyMatching");
    try {
      return handleResult(loggerPrefix, intermediateRegionService
          .countAnyMatching(query.getFilter(), query.getIso3Language()));
    } catch (Throwable t) {
      return handleResult(loggerPrefix, t);
    }
  }

  @PostMapping(value = "/getById")
  public ResponseEntity<ServiceResult> getById(@RequestBody GetByIdQuery query) {
    String loggerPrefix = getLoggerPrefix("getById");
    try {
      MappingContext context = new MappingContext.Factory().getContext();
      context.setProperty("iso3Language", query.get_iso3Language());

      return handleResult(loggerPrefix, mapperFacade.map(intermediateRegionService
              .load(query.getId()), org.wannagoframework.dto.domain.reference.IntermediateRegion.class,
          getOrikaContext(query)));
    } catch (Throwable t) {
      return handleResult(loggerPrefix, t);
    }
  }

  @PostMapping(value = "/save")
  public ResponseEntity<ServiceResult> save(
      @RequestBody SaveQuery<org.wannagoframework.dto.domain.reference.IntermediateRegion> query) {
    String loggerPrefix = getLoggerPrefix("save");
    try {
      MappingContext context = new MappingContext.Factory().getContext();
      context.setProperty("iso3Language", query.get_iso3Language());

      return handleResult(loggerPrefix, mapperFacade.map(intermediateRegionService
              .save(mapperFacade
                  .map(query.getEntity(), IntermediateRegion.class, getOrikaContext(query))),
          org.wannagoframework.dto.domain.reference.IntermediateRegion.class,
          getOrikaContext(query)));
    } catch (Throwable t) {
      return handleResult(loggerPrefix, t);
    }
  }

  @PostMapping(value = "/delete")
  public ResponseEntity<ServiceResult> delete(@RequestBody DeleteByIdQuery query) {
    String loggerPrefix = getLoggerPrefix("delete");
    try {
      intermediateRegionService
          .delete(query.getId());
      return handleResult(loggerPrefix);
    } catch (Throwable t) {
      return handleResult(loggerPrefix, t);
    }
  }
}