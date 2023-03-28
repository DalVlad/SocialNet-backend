package com.practice.SocialNetbackend.util;

public class CatalogNotFoundException extends RuntimeException{

    public CatalogNotFoundException(String message) {
        super(message);
    }
}
