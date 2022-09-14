package com.microservice.restservice.auxillary;

public class SecretKeyStorage {
    // В будущем перенести секрет в переменную окружения
    private static final String SECRET = "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4";

    public static String getSecret(){
        return SECRET;
    }
}
