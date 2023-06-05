package com.garagemanagement.baseproject.common.utils;

import java.util.UUID;

public class GenerateUUID {
    public static String generateRandomUUID(){
        UUID uuid = UUID.randomUUID();

        return uuid.toString();
    }
}
