package org.grechman.contacts_storage.utils;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class ValidateRegex {

    public static boolean isRegexValid(String regexString){
        try {
            Pattern.compile(regexString);
            return true;
        }catch (PatternSyntaxException ex){
            return false;
        }
    }
}
