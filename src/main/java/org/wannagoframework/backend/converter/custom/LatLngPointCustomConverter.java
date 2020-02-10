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


package org.wannagoframework.backend.converter.custom;

import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Component;
import org.wannagoframework.dto.utils.LatLng;

/**
 * @author WannaGo Dev1.
 * @version 1.0
 * @since 2019-06-05
 */
@Component
public class LatLngPointCustomConverter extends
    BidirectionalConverter<LatLng, Point> {

  @Override
  public Point convertTo(LatLng source,
      Type<Point> destinationType, MappingContext context) {
    return new Point(source.getLat(), source.getLng());
  }

  @Override
  public LatLng convertFrom(Point source,
      Type<LatLng> destinationType, MappingContext context) {
    return new LatLng(source.getX(), source.getY());
  }
}
