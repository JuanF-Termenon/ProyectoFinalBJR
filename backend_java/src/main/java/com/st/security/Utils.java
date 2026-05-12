package com.st.security;

public class Utils {

    /**
     * Un "hash" simple para clase: 
     * Solo le da la vuelta al texto y le añade "st" al final.
     */
    public static String simplificarHash(String password) {
        if (password == null) return "";
        
        StringBuilder sb = new StringBuilder(password);
        return sb.reverse().toString() + "st";
    }
}
