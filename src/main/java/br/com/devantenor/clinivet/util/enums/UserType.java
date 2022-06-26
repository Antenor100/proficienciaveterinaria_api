package br.com.devantenor.clinivet.util.enums;

public enum UserType {
    VETERINARIO(RoleNames.VETERINARIO),
    ATENDENTE(RoleNames.ATENDENTE),
    CLIENTE(RoleNames.CLIENTE);

    public class RoleNames {
        public static final String VETERINARIO = "ROLE_VETERINARIO";
        public static final String ATENDENTE = "ROLE_ATENDENTE";
        public static final String CLIENTE = "ROLE_CLIENTE";
    }

    public final String roleName;

    UserType(String rolename) {
        this.roleName = rolename;
    }

    public String toString() {
        return this.roleName;
    }
}

