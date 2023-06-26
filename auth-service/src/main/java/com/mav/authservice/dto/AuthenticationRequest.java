package com.mav.authservice.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthenticationRequest {
    @ApiModelProperty(value = "Email of the user", required = true)
    private String email;
    @ApiModelProperty(value = "Password of the user", required = true)
    private String password;
}
