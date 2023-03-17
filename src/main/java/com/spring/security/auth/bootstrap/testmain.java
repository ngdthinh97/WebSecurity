package com.spring.security.auth.bootstrap;

import com.ibm.icu.text.Transliterator;

public class testmain {

    public static void main(String[] args) {
        String re = transliterate("とうきょう");
        System.out.println(re);
    }

    public static String TRANSLITERATE_ID = "NFD; Any-Latin; NFC";
    public static String NORMALIZE_ID = "NFD; [:Nonspacing Mark:] Remove; NFC";
    /**
     * Returns the transliterated string to convert any charset to latin.
     */
    public static String transliterate(String input) {
        Transliterator transliterator = Transliterator.getInstance( NORMALIZE_ID + TRANSLITERATE_ID );
        String result = transliterator.transliterate(input);
        return result;
    }
}
