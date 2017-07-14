package gov.samhsa.c2s.staffuiapi.service.dto;

public enum JwtTokenKey {
    JTI("jti"),
    SUB("sub"),
    SCOPE("scope"),
    CLIENT_ID("client_id"),
    GRANT_TYPE("grant_type"),
    USER_ID("user_id"),
    ORIGIN("origin"),
    USER_NAME("user_name"),
    EMAIL("email"),
    AUTH_TIME("auth_time"),
    EXP("exp");

    private final String name;

    JwtTokenKey(String s) {
        name = s;
    }

    public String toString() {
        return this.name;
    }
}
