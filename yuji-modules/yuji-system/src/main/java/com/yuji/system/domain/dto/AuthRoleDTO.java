 package com.yuji.system.domain.dto;

 import lombok.Getter;
 import lombok.Setter;

 import javax.validation.constraints.NotNull;

 @Getter
 @Setter
 public class AuthRoleDTO {

     private Long userId;

     @NotNull
     private Long[] roleIds;
 }

