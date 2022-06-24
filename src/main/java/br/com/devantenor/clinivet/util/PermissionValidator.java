package br.com.devantenor.clinivet.util;

import br.com.devantenor.clinivet.util.enums.UserType;

public class PermissionValidator {
    public static boolean canManageUser(UserType userType) {
        if (userType.equals(UserType.ATENDENTE) || userType.equals(UserType.VETERINARIO)) {
            return true;
        }
        return false;
    }

    public static boolean canManageCostumer(UserType userType) {
        if (userType.equals(UserType.ATENDENTE) || userType.equals(UserType.VETERINARIO)) {
            return true;
        }
        return false;
    }

    public static boolean canManageAnimal(UserType userType) {
        if (userType.equals(UserType.VETERINARIO)) {
            return true;
        }
        return false;
    }
}
