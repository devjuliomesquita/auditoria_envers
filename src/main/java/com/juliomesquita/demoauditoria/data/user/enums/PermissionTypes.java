package com.juliomesquita.demoauditoria.data.user.enums;

import java.util.HashSet;
import java.util.Set;

public enum PermissionTypes {
    USER_READ("Usuário Leitura"),
    USER_WRITE("Usuário Escrita"),
    USER_DELETE("Usuário Deletar");

    private final String description;

    PermissionTypes(String description) {
        this.description = description;
    }

    public static Set<PermissionTypes> getPermissions(final String name) {
        Set<PermissionTypes> permissionList = new HashSet<>();
        for (PermissionTypes key : values()) {
            String nameToLowerCase = key.name().toLowerCase();
            if (nameToLowerCase.contains(name)) {
                permissionList.add(key);
            }
        }
        return permissionList;
    }
}
