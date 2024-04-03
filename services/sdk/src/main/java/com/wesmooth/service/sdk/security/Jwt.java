/* WeSmooth! 2024 */
package com.wesmooth.service.sdk.security;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * JWT Data Transfer Object.
 *
 * @author Boris Georgiev
 */
public class Jwt {
  private final Header header;
  private final Payload payload;

  public Header getHeader() {
    return header;
  }

  public Payload getPayload() {
    return payload;
  }

  public Jwt(Header header, Payload payload) {
    this.header = header;
    this.payload = payload;
  }

  @AllArgsConstructor
  @Getter
  public static class Header {
    public Header() {
      alg = "RS256";
      typ = "JWT";
    }

    private final String alg;
    private final String typ;

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof Header header)) return false;
      return Objects.equals(alg, header.alg) && Objects.equals(typ, header.typ);
    }

    @Override
    public int hashCode() {
      return Objects.hash(alg, typ);
    }
  }

  @Getter
  public static class Payload {

    public Payload(long exp, String username, String iss) {
      this.exp = exp;
      this.username = username;
      this.iss = iss;
    }

    public Payload(long exp, String username) {
      this.exp = exp;
      this.username = username;
      this.iss = "WeSmooth!";
    }

    private final long exp;
    private final String username;

    private final String iss;

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof Payload payload)) return false;
      return exp == payload.exp
          && Objects.equals(username, payload.username)
          && Objects.equals(iss, payload.iss);
    }

    @Override
    public int hashCode() {
      return Objects.hash(exp, username, iss);
    }
  }
}
