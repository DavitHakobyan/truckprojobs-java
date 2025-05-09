package com.smartit.truckprojobs.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class CustomClaimsRequest implements Serializable {
    private String email;
    private String displayName;
    private String role;
    private String photoURL;
}