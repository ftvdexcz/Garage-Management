package com.garagemanagement.userservice.common.constant;

public enum UserRole {
    ADMIN(1),
    SUPPORT(2),
    CUSTOMER(3);

    private int role;

    UserRole(int role) {
        this.role = role;
    }

    public int getRole() {
        return role;
    }

    public static UserRole fromRole(int role) {
        for (UserRole r : UserRole.values()) {
            if (r.getRole() == role) {
                return r;
            }
        }
        throw new IllegalArgumentException("Invalid role: " + role);
    }
}

//UserRole userRole = UserRole.ADMIN;
//System.out.println(userRole.getValue());  // In ra: 1
//
//    UserRole convertedRole = UserRole.fromValue(2);
//    System.out.println(convertedRole);  // In ra: GUEST