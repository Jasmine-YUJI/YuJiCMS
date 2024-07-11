 package com.yuji.system.domain.dto;

 import lombok.Getter;
 import lombok.Setter;

 import javax.validation.constraints.NotNull;

 @Getter
 @Setter
 public class RoleMenuDTO {

     private Long roleId;

     @NotNull
     private Long[] menuIds;
 }

