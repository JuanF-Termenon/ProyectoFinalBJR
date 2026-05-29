package com.st.security;

public class Utils {

    public static String simplificarHash(String password){
        if (password == null) return "";
        StringBuilder sb = new StringBuilder(password);
        return sb.reverse().toString() + "st";
    }
}
