package com.makestar.streamwith.streamwith_api.util;

public class StringUtil {
    public static final String SPACE_REPLACE_CHAR = "+";

    public static String replaceWhiteSpace(String input) {
        return input.replaceAll(" ", SPACE_REPLACE_CHAR);
    }


    public static String convertRegexStr(String input){
        return  input.toLowerCase().replaceAll(" ","")
                .replaceAll("\\(","\\\\\\(").replaceAll("\\)","\\\\\\)");
    }


}
