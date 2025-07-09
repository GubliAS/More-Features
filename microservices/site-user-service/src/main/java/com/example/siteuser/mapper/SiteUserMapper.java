package com.example.siteuser.mapper;

import com.example.commonentities.SiteUser;
import com.example.commonentities.SiteUserDTO;
import com.example.commonentities.Role;
import com.example.commonentities.RoleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SiteUserMapper {
    SiteUserMapper INSTANCE = Mappers.getMapper(SiteUserMapper.class);

    SiteUserDTO toDto(SiteUser user);
    SiteUser toEntity(SiteUserDTO userDto);

    RoleDTO toDto(Role role);
    Role toEntity(RoleDTO roleDto);
} 