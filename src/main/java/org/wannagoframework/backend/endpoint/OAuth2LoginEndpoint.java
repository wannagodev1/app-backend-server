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


package org.wannagoframework.backend.endpoint;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author WannaGo Dev1.
 * @version 1.0
 * @since 2019-05-17
 */
@Controller
public class OAuth2LoginEndpoint {

  @GetMapping("/facebookLogin")
  public void facebookLogin(String access_token) {
  }

  @GetMapping("/googleLogin")
  public void googleLogin(String access_token) {
  }

  @GetMapping("/twitterLogin")
  public void twitterLogin(String access_token) {
  }
}
