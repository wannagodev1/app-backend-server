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

import java.util.HashMap;
import java.util.Map;
import lombok.Data;

/**
 * Base class for all translations
 *
 * @author WannaGo Dev1.
 * @version 1.0
 * @since 2019-03-12
 */
@Data
public class EntityTranslations {
  private Map<String, EntityTranslation> translations;

  public EntityTranslations() {
    this.translations = new HashMap<>();
  }

  public EntityTranslations(Map<String, EntityTranslation> translations) {
    this.translations = translations;
  }

  public String getTranslation( String iso3Language ) {
    if ( translations.containsKey( iso3Language))
      return translations.get(iso3Language).getValue();
    else return getDefaultTranslation();
  }
  public String getDefaultTranslation() {
    for (EntityTranslation entityTranslation : translations.values()) {
      if (entityTranslation.getIsDefault())
        return entityTranslation.getValue();
    }

    if ( translations.size() > 0 )
      return translations.values().iterator().next().getValue();
    else
      return null;
  }

  public void setTranslation( String iso3Language, String value, Boolean isDefault ) {
    if ( translations.containsKey( iso3Language ))
      translations.get( iso3Language ).setValue( value );
    else {
      EntityTranslation entityTranslation = new EntityTranslation();
      entityTranslation.setIso3Language( iso3Language );
      entityTranslation.setIsDefault(isDefault);
      entityTranslation.setValue( value );
      entityTranslation.setIsTranslated(true);

      translations.put( iso3Language, entityTranslation );
    }
  }

  public void setTranslation( String iso3Language, String value ) {
    setTranslation( iso3Language, value,translations.size() == 0 );
  }

  public void clearTranslations() {
    translations = new HashMap<>();
  }

  public void addTranslation( EntityTranslation entityTranslation ) {
    translations.put( entityTranslation.getIso3Language(), entityTranslation );
  }
}
