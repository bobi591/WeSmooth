/* WeSmooth! 2024 */
package com.wesmooth.service.sdk.services.users.register.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegistrationRequest {
  private String username;
  private String password;
  private String email;
  private String name;
}
