package com.boveybrawlers.TreeRepository.utils;

public class Str {

    /**
     * Fix string spaces to align text in Minecraft chat
     *
     * @param s     String to be resized
     * @param size  Size to align
     * @return      New aligned String
     */
    public static String fixedFontSize(String s, int size) {
        String fixedString = s;

        char[] shortChars = { ' ', ',', '.', 'i', 'I', 'l', '[', ']' };

        for(int i = 0; i < s.length(); i++) {
            for(char c : shortChars) {
                if(s.charAt(i) == c) {
                    fixedString += " ";
                }
            }
        }

        int lackSpaces = size - s.length();
        lackSpaces = lackSpaces * 2;

        for(int i = 0; i < lackSpaces; i++) {
            fixedString += " ";
        }

        return fixedString;
    }

    public static String generateSpacing(int length) {
        String spacing = "";
        for(int i = 0; i < length; i++){
            spacing += " ";
        }

        return spacing;
    }

}