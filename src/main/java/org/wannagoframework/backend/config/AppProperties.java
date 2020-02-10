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


package org.wannagoframework.backend.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "app")
public class AppProperties {

  private final RemoteServices remoteServices = new RemoteServices();
  private final Bootstrap bootstrap = new Bootstrap();
  private final Hazelcast hazelcast = new Hazelcast();

  @Data
  public static class RemoteServices {

    private RemoteServer backendServer = new RemoteServer();
    private RemoteServer authorizationServer = new RemoteServer();
    private RemoteServer i18nServer = new RemoteServer();
    private RemoteServer resourceServer = new RemoteServer();
    private RemoteServer auditServer = new RemoteServer();

    @Data
    public static final class RemoteServer {

      private String url;
      private String name;
    }
  }

  @Data
  public static final class Bootstrap {

    private Iso3166 iso3166 = new Iso3166();
    private CategoryList categoryList = new CategoryList();

    @Data
    public static final class Iso3166 {

      private Boolean isEnabled;
      private String file;
    }

    @Data
    public static final class CategoryList {

      private Boolean isEnabled;
      private String file;
    }
  }

  @Data
  public static class Hazelcast {

    private final ManagementCenter managementCenter = new ManagementCenter();
    private int timeToLiveSeconds = 3600;
    private int backupCount = 1;

    @Data
    public static class ManagementCenter {

      private boolean enabled = false;
      private int updateInterval = 3;
      private String url = "";
    }
  }
}
