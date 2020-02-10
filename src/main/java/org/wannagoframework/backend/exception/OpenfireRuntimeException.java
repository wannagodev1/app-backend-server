package org.wannagoframework.backend.exception;

/**
 * @author WannaGo Dev1.
 * @version 1.0
 * @since 9/7/19
 */
public class OpenfireRuntimeException extends RuntimeException {

  public OpenfireRuntimeException() {
  }

  public OpenfireRuntimeException(String message) {
    super(message);
  }

  public OpenfireRuntimeException(String message, Throwable cause) {
    super(message, cause);
  }

  public OpenfireRuntimeException(Throwable cause) {
    super(cause);
  }

  public OpenfireRuntimeException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
