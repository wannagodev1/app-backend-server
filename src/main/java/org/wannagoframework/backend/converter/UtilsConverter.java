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


package org.wannagoframework.backend.converter;

import com.google.maps.model.LatLng;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.wannagoframework.backend.domain.graphdb.BaseEntityWithComments;
import org.wannagoframework.backend.domain.graphdb.utils.MapBounds;
import org.wannagoframework.commons.utils.OrikaBeanMapper;
import org.wannagoframework.dto.domain.BaseEntity;
import org.wannagoframework.dto.domain.EntityTranslation;

/**
 * @author WannaGo Dev1.
 * @version 1.0
 * @since 2019-06-05
 */
@Component
public class UtilsConverter {

  private final OrikaBeanMapper orikaBeanMapper;

  public UtilsConverter(OrikaBeanMapper orikaBeanMapper) {
    this.orikaBeanMapper = orikaBeanMapper;
  }

  @Bean
  public void utilsConverters() {
    orikaBeanMapper.addMapper(LatLng.class, org.wannagoframework.dto.utils.LatLng.class);
    orikaBeanMapper.addMapper(org.wannagoframework.dto.utils.LatLng.class, LatLng.class);

    orikaBeanMapper.addMapper(MapBounds.class, org.wannagoframework.dto.utils.MapBounds.class);
    orikaBeanMapper.addMapper(org.wannagoframework.dto.utils.MapBounds.class, MapBounds.class);

    orikaBeanMapper.addMapper(BaseEntityWithComments.class,
        org.wannagoframework.dto.domain.BaseEntityWithComments.class);
    orikaBeanMapper.addMapper(org.wannagoframework.dto.domain.BaseEntityWithComments.class,
        BaseEntityWithComments.class);

    orikaBeanMapper
        .addMapper(EntityTranslation.class, EntityTranslation.class);
    orikaBeanMapper
        .addMapper(EntityTranslation.class, EntityTranslation.class);

    orikaBeanMapper
        .addMapper(org.wannagoframework.backend.domain.graphdb.BaseEntity.class, BaseEntity.class);
    orikaBeanMapper
        .addMapper(org.wannagoframework.backend.domain.nosqldb.BaseEntity.class, BaseEntity.class);
    orikaBeanMapper
        .addMapper(org.wannagoframework.backend.domain.relationaldb.BaseEntity.class, BaseEntity.class);

    orikaBeanMapper
        .getClassMapBuilder(BaseEntity.class, org.wannagoframework.backend.domain.graphdb.BaseEntity.class)
        .byDefault().customize(
        new CustomMapper<BaseEntity, org.wannagoframework.backend.domain.graphdb.BaseEntity>() {
          @Override
          public void mapAtoB(BaseEntity a, org.wannagoframework.backend.domain.graphdb.BaseEntity b,
              MappingContext context) {
            if (a.getIsNew()) {
              b.setId(null);
            }
          }
        }).register();

    orikaBeanMapper
        .getClassMapBuilder(BaseEntity.class, org.wannagoframework.backend.domain.nosqldb.BaseEntity.class)
        .byDefault().customize(
        new CustomMapper<BaseEntity, org.wannagoframework.backend.domain.graphdb.BaseEntity>() {
          @Override
          public void mapAtoB(BaseEntity a, org.wannagoframework.backend.domain.graphdb.BaseEntity b,
              MappingContext context) {
            if (a.getIsNew()) {
              b.setId(null);
            }
          }
        }).register();

    orikaBeanMapper
        .getClassMapBuilder(BaseEntity.class,
            org.wannagoframework.backend.domain.relationaldb.BaseEntity.class)
        .byDefault().customize(
        new CustomMapper<BaseEntity, org.wannagoframework.backend.domain.graphdb.BaseEntity>() {
          @Override
          public void mapAtoB(BaseEntity a, org.wannagoframework.backend.domain.graphdb.BaseEntity b,
              MappingContext context) {
            if (a.getIsNew()) {
              b.setId(null);
            }
          }
        }).register();
  }
}