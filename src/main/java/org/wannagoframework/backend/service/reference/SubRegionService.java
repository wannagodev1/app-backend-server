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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.wannagoframework.backend.domain.graphdb.reference.SubRegion;
import org.wannagoframework.baseserver.service.CrudGraphdbService;

/**
 * @author WannaGo Dev1.
 * @version 1.0
 * @since 2019-03-27
 */
public interface SubRegionService extends CrudGraphdbService<SubRegion> {

  List<SubRegion> findAll();

  Page<SubRegion> findAnyMatching(String filter, String iso3Language, Pageable pageable);

  long countAnyMatching(String filter, String iso3Language);

  SubRegion getById(Long id);
}
