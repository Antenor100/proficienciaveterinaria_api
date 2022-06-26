package br.com.devantenor.clinivet.util.enums;

public enum UserType {
    VETERINARIO(0, RoleNames.VETERINARIO),
    ATENDENTE(1, RoleNames.ATENDENTE),
    CLIENTE(2, RoleNames.CLIENTE);

    public class RoleNames {
        public static final String VETERINARIO = "ROLE_VETERINARIO";
        public static final String ATENDENTE = "ROLE_ATENDENTE";
        public static final String CLIENTE = "ROLE_CLIENTE";
    }
    public final int value;
    public final String roleName;

    UserType(int value, String rolename) {
        this.value = value;
        this.roleName = rolename;
    }

    @Override
    public String toString() {
        return "UserType{" +
                "value=" + value +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}

