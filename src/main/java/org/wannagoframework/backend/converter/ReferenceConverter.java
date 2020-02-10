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

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.wannagoframework.backend.domain.graphdb.reference.Country;
import org.wannagoframework.backend.domain.graphdb.reference.IntermediateRegion;
import org.wannagoframework.backend.domain.graphdb.reference.Region;
import org.wannagoframework.backend.domain.graphdb.reference.SubRegion;
import org.wannagoframework.commons.utils.OrikaBeanMapper;

/**
 * @author WannaGo Dev1.
 * @version 1.0
 * @since 2019-06-05
 */
@Component
public class ReferenceConverter {

  private final OrikaBeanMapper orikaBeanMapper;

  public ReferenceConverter(OrikaBeanMapper orikaBeanMapper) {
    this.orikaBeanMapper = orikaBeanMapper;
  }

  @Bean
  public void configure() {
    orikaBeanMapper
        .getClassMapBuilder(Country.class, org.wannagoframework.dto.domain.reference.Country.class)
        .byDefault().customize(
        new CustomMapper<Country, org.wannagoframework.dto.domain.reference.Country>() {
          @Override
          public void mapAtoB(Country domain, org.wannagoframework.dto.domain.reference.Country dto,
              MappingContext context) {
            String iso3Language = (String) context.getProperty("iso3Language");
            if (StringUtils.isNotBlank(iso3Language)) {
              dto.setName( domain.getNames().getTranslation( iso3Language ) );
            }
          }
        }).register();
    orikaBeanMapper
        .getClassMapBuilder(org.wannagoframework.dto.domain.reference.Country.class, Country.class)
        .byDefault().exclude("names").customize(
        new CustomMapper<org.wannagoframework.dto.domain.reference.Country, Country>() {
          @Override
          public void mapAtoB( org.wannagoframework.dto.domain.reference.Country dto,Country domain,
              MappingContext context) {
            String iso3Language = (String) context.getProperty("iso3Language");
            if (StringUtils.isNotBlank(iso3Language)) {
              domain.getNames().setTranslation( iso3Language, dto.getName());
            }
          }
        }).register();

    orikaBeanMapper.getClassMapBuilder(IntermediateRegion.class,
        org.wannagoframework.dto.domain.reference.IntermediateRegion.class).byDefault().customize(
        new CustomMapper<IntermediateRegion, org.wannagoframework.dto.domain.reference.IntermediateRegion>() {
          @Override
          public void mapAtoB(IntermediateRegion domain,
              org.wannagoframework.dto.domain.reference.IntermediateRegion dto, MappingContext context) {
            String iso3Language = (String) context.getProperty("iso3Language");
            if (StringUtils.isNotBlank(iso3Language)) {
              dto.setName( domain.getNames().getTranslation( iso3Language ) );
            }
          }
        }).register();
    orikaBeanMapper
        .getClassMapBuilder(org.wannagoframework.dto.domain.reference.IntermediateRegion.class, IntermediateRegion.class)
        .byDefault().exclude("names").customize(
        new CustomMapper<org.wannagoframework.dto.domain.reference.IntermediateRegion, IntermediateRegion>() {
          @Override
          public void mapAtoB( org.wannagoframework.dto.domain.reference.IntermediateRegion dto,IntermediateRegion domain,
              MappingContext context) {
            String iso3Language = (String) context.getProperty("iso3Language");
            if (StringUtils.isNotBlank(iso3Language)) {
              domain.getNames().setTranslation( iso3Language, dto.getName());
            }
          }
        }).register();

     orikaBeanMapper
        .getClassMapBuilder(Region.class, org.wannagoframework.dto.domain.reference.Region.class)
        .byDefault().customize(
        new CustomMapper<Region, org.wannagoframework.dto.domain.reference.Region>() {
          @Override
          public void mapAtoB(Region domain, org.wannagoframework.dto.domain.reference.Region dto,
              MappingContext context) {
            String iso3Language = (String) context.getProperty("iso3Language");
            if (StringUtils.isNotBlank(iso3Language)) {
              dto.setName( domain.getNames().getTranslation( iso3Language ) );
            }
          }
        }).register();
    orikaBeanMapper
        .getClassMapBuilder(org.wannagoframework.dto.domain.reference.Region.class, Region.class)
        .byDefault().exclude("names").customize(
        new CustomMapper<org.wannagoframework.dto.domain.reference.Region, Region>() {
          @Override
          public void mapAtoB( org.wannagoframework.dto.domain.reference.Region dto,Region domain,
              MappingContext context) {
            String iso3Language = (String) context.getProperty("iso3Language");
            if (StringUtils.isNotBlank(iso3Language)) {
              domain.getNames().setTranslation( iso3Language, dto.getName());
            }
          }
        }).register();

    orikaBeanMapper
        .getClassMapBuilder(SubRegion.class, org.wannagoframework.dto.domain.reference.SubRegion.class)
        .byDefault().customize(
        new CustomMapper<SubRegion, org.wannagoframework.dto.domain.reference.SubRegion>() {
          @Override
          public void mapAtoB(SubRegion domain, org.wannagoframework.dto.domain.reference.SubRegion dto,
              MappingContext context) {
            String iso3Language = (String) context.getProperty("iso3Language");
            if (StringUtils.isNotBlank(iso3Language)) {
              dto.setName( domain.getNames().getTranslation( iso3Language ) );
            }
          }
        }).register();
    orikaBeanMapper
        .getClassMapBuilder(org.wannagoframework.dto.domain.reference.SubRegion.class, SubRegion.class)
        .byDefault().exclude("names").customize(
        new CustomMapper<org.wannagoframework.dto.domain.reference.SubRegion, SubRegion>() {
          @Override
          public void mapAtoB( org.wannagoframework.dto.domain.reference.SubRegion dto,SubRegion domain,
              MappingContext context) {
            String iso3Language = (String) context.getProperty("iso3Language");
            if (StringUtils.isNotBlank(iso3Language)) {
              domain.getNames().setTranslation( iso3Language, dto.getName());
            }
          }
        }).register();
  }
}
