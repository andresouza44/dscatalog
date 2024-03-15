package com.andresouza.dscatalog.dto;

import com.andresouza.dscatalog.entities.Role;

public class RoleDto {
    private Long id;
    private String authority;

    private RoleDto(){
    }

    public RoleDto(Long id, String authority) {
        this.id = id;
        this.authority = authority;
    }

    public RoleDto(Role entity){
        id = entity.getId();
        authority = entity.getAuthority();

    }

    public Long getId() {
        return id;
    }

    public String getAuthority() {
        return authority;
    }
}
