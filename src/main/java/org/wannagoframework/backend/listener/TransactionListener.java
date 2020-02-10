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


package org.wannagoframework.backend.listener;

import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.wannagoframework.commons.utils.HasLogger;

/**
 * @author WannaGo Dev1.
 * @version 1.0
 * @since 2019-05-24
 */
@Component
public class TransactionListener implements HasLogger {

  @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
  public void beforeCommit(ApplicationEvent event) {
    String loggerPrefix = getLoggerPrefix("beforeCommit");
    logger().debug(loggerPrefix + "Source = " + event.getSource());
  }

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void afterCommit(ApplicationEvent event) {
    String loggerPrefix = getLoggerPrefix("afterCommit");
    logger().debug(loggerPrefix + "Source = " + event.getSource());
  }

  @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
  public void afterRollback(ApplicationEvent event) {
    String loggerPrefix = getLoggerPrefix("afterRollback");
    logger().debug(loggerPrefix + "Source = " + event.getSource());
  }
}
