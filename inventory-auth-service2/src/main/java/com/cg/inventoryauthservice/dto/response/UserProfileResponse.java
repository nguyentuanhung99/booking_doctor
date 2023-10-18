package com.cg.inventoryauthservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfileResponse {

    private String username;

    private String fullName;

    private String avatar;

    private String email;

}
