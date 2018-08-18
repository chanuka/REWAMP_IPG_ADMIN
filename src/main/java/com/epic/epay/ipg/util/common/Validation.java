/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.util.common;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 *
 * @author chanuka
 */
public class Validation {

    /**
     * check given value is a percentage value
     *
     * @param val
     * @return
     */
    public static boolean isPercentageValue(String val) {
        boolean status = true;
        int numOfDPoints = 2;
        try {
            String valArray[] = val.split("\\.");
            if (valArray.length < 3) {
                if (valArray[0].length() == numOfDPoints && (valArray.length == 2 && valArray[1].length() == numOfDPoints)) {
                    if (isNumeric(valArray[0]) && (valArray.length == 2 && isNumeric(valArray[1]))) {
                        status = true;
                    } else {
                        status = false;
                    }
                } else {
                    status = false;
                }
            } else {
                status = false;
            }
        } catch (Exception e) {
            status = false;
        }
        return status;

    }

    /**
     * check give value is a currency value
     *
     * @param val
     * @return
     */
    public static boolean isCurrencyValue(String val) {
        boolean status = true;
        int numOfDPoints = 2;
        try {
            String valArray[] = val.split("\\.");
            if (valArray.length == 2) {
                if (valArray[1].length() == numOfDPoints) {
                    if (isNumeric(valArray[0]) && isNumeric(valArray[1])) {
                        status = true;
                    } else {
                        status = false;
                    }
                } else {
                    status = false;
                }
            } else {
                status = false;
            }
        } catch (Exception e) {
            status = false;
        }
        return status;

    }

    /**
     * check give value is a string of special characters
     *
     * @param val
     * @return
     */
    public static boolean is12HourTimeString(String str) {
        try {
            boolean isValid = true;
            String regex = "[0-1][0-2].[0-5][0-9]";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(str);
            if (!matcher.matches()) {
                isValid = false;
            }
            return isValid;
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * check give value is a string of special characters
     *
     * @param val
     * @return
     */
    public static boolean isSpecailString(String val) {
        try {
            boolean isValid = true;
            String regex = "\\W+";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(val);
            if (!matcher.matches()) {
                isValid = false;
            }
            if (isValid) {
                String newStr = "";
                for (int i = 0; i < val.length(); i++) {
                    char c = val.charAt(i);
                    if (newStr.indexOf(c) == -1) {
                        StringBuffer sb = new StringBuffer();
                        sb.append(c);
                        newStr = newStr.concat(sb.toString());
                    } else {
                        isValid = false;
                        break;
                    }
                }
            }
            return isValid;
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * check give value has special characters
     *
     * @param val
     * @return
     */
    public static boolean isSpecailCharacter(String val) {
        try {
            boolean isValid = true;

            for (int i = 0; i < val.length(); i++) {
                char c = val.charAt(i);

                if ((c == '"') || (c == ';') || (c == ',') || (c == '@') || (c == '|') || (c == '*') || (c == '#') || (c == '{') || (c == '}') || (c == '`') || (c == '?')
                        || (c == '!') || (c == '$') || (c == '%') || (c == '^') || (c == '&') || (c == '/') || (c == '>') || (c == '<') || (c == '(') || (c == ')') || (c == '~') || (c == '\'')) {
                    isValid = false;
                }
            }
            return isValid;
        } catch (Exception e) {
            return true;
        }
    }

    public static boolean isValidEmail(String theInputString) {

        boolean isValid = true;

        //Set the email pattern string
        Pattern p = Pattern.compile(".+@.+\\.[a-z]+");

        //Match the given string with the pattern
        Matcher m = p.matcher(theInputString);

        //Check whether match is found
        boolean matchFound = m.matches();

        if (!matchFound) {
            isValid = false;
        }

        return isValid;
    }
    //if correct numerc value found, then return true

    public static boolean isNumeric(String theInputString) {

        boolean isValid = false;

        for (int i = 0; i < theInputString.length(); i++) {
            char c = theInputString.charAt(i);

            if ((c >= '0') && (c <= '9')) {
                isValid = true;
            } else {
                isValid = false;
                break;
            }
        }
        return isValid;
    }

    public static Boolean isNumericDouble(String val) {
        try {
            if (isDecimalNumeric(val)) {
                return true;
            } else if (isNumeric(val)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isString(String value) {
        boolean valied = true;
        try {
            if (!value.matches("[a-zA-Z]*")) {
                valied = false;
            }
        } catch (Exception e) {
            valied = false;
        }
        return valied;
    }

    public static boolean isAlphaNumeric(String value) throws Exception {
        boolean valied = true;

        try {
            if (!value.matches("[a-zA-Z0-9]*")) {
                valied = false;
            }

        } catch (Exception ex) {
            valied = false;
        }

        return valied;
    }
    private static final String DECIMALNUMERIC_PATTERN = "\\d{0,15}\\.\\d{0,2}";

    public static boolean isDecimalNumeric(String inputString) {
        boolean validFlag = false;
        Pattern p = Pattern.compile(DECIMALNUMERIC_PATTERN);
        Matcher m = p.matcher(inputString.trim());
        if (m.matches()) {
            validFlag = true;
        }
        return validFlag;
    }

    public static boolean validate94PhoneNumber(String mobileNo) {
        boolean Valid94number = false;

        if (mobileNo.length() != 10) {
            Valid94number = false;
        } else {
            for (int i = 0; i < mobileNo.length(); i++) {
                if (mobileNo.charAt(0) == '9' && mobileNo.charAt(1) == '4') {
                    if (mobileNo.length() != 11) {
                        Valid94number = false;
                        break;
                    }
                }
                char c = mobileNo.charAt(i);

                if (Character.isDigit(c)) {
                    Valid94number = true;
                } else {
                    Valid94number = false;
                    break;
                }
            }
        }
        return Valid94number;
    }
    private static final String URL_PATTERN = "^((http|https|ftp):\\/\\/).*$";

    public static boolean isValidUrl(String url) {

        boolean flag = false;
        Pattern p = Pattern.compile(URL_PATTERN);
        Matcher m = p.matcher(url.trim());
        if (m.matches()) {
            flag = true;
        }

        return flag;
    }

    public static boolean isValidCardAssociation(String cardassociation) {
        if(cardassociation == null || "".equals(cardassociation)){
           return false; 
        }
        else {
            return true;
        }
    }

    public static boolean isValidStatus(String status) {
         if(status == null || "".equals(status)){
           return false; 
        }
        else {
            return true;
        }
    }
    
    public static boolean isvalidIP(String IP) throws PatternSyntaxException,Exception{
    	
        try{
        	
            String regularExpression = "(([0-9]){1,3}\\.([0-9]){1,3}\\.([0-9]){1,3}\\.([0-9]){1,3}){1}";
            boolean isMatched = Pattern.matches(regularExpression,IP);
            
            if(isMatched){
            String ipArray[] = IP.split("\\.");
            if(Integer.parseInt(ipArray[0])<256 && Integer.parseInt(ipArray[1])<256 && Integer.parseInt(ipArray[2])<256 && Integer.parseInt(ipArray[3])<256){
            return true;           
            }else{
            	return false;
            }
            }else{
            	return false;
            }
            
        }
        catch(PatternSyntaxException e){
            throw e;            
        }catch(Exception ex){
            throw ex;
        }
		
    	
    }
    
    public static boolean isithaveNumber (String code) {

        boolean isValid = false;

        for (int i = 0; i < code.length(); i++) {
            char c = code.charAt(i);

            if ((c >= '0') && (c <= '9')) {
                isValid = true;
                break;
            } else {
                isValid = false;
                
            }
        }
        return isValid;
    }
    
    public static String validateExtension(String filename) {        
    	String message="";
    	
        List<String> extensions = new ArrayList<String>();
        extensions.add("jpg");
        extensions.add("jpeg");
        extensions.add("gif");
        extensions.add("png");
        extensions.add("bmp");
        
        int pos = filename.lastIndexOf('.') + 1;
        String ext = filename.substring(pos);
        String final_ext = ext.toLowerCase();

        for (int i = 0; i < extensions.size(); i++) {
            if (extensions.get(i).equals(final_ext)) {
                return message;              
            
        } 
    }
        message = "You must upload an image file with one of the following extensions:";
        for (int i = 0; i < extensions.size(); i++) {
            message = message + extensions.get(i)+ ",";
            
        }  
        return message;
}
    
    //  ********************  This is a method to validate the decimal numeric Format with any number of scale and precision ********************
    public static boolean isDecimalOrNumeric(String inputString, String integer, String decimal) {
        final String COMMON_PATTERN = "\\d{0," + integer + "}\\.\\d{0," + decimal + "}";
        final String COMMON_PATTERN_1 = "\\d{0," + integer + "}";
        boolean validFlag = false;
        Pattern p = Pattern.compile(COMMON_PATTERN);
        Pattern q = Pattern.compile(COMMON_PATTERN_1);
        Matcher m = p.matcher(inputString.trim());
        Matcher n = q.matcher(inputString.trim());
        if (m.matches() || n.matches()) {
            validFlag = true;
        }
        return validFlag;
    }
    
    //This method check wheather the input string contains any special characters or numeric characters. If there is any  it will return false.
    public static boolean isNonNumericNonSpecialString(String theInputString) {
        boolean isValid = true;

        for (int i = 0; i < theInputString.length(); i++) {
            char c = theInputString.charAt(i);

            if ((c == '\'') || (c == '\\') || (c == '\'') || (c == '!') || (c == ':') || (c == '"') || (c == ';') || (c == ',') || (c == '@') || (c == '|') || (c == '*') || (c == '#') || (c == '{') || (c == '}') || (c == '`') || (c == '?')
                    || (c == '$') || (c == '%') || (c == '^') || (c == '&') || (c == '/') || (c == '>') || (c == '<') || (c == '(') || (c == ')') || (c == '~')) {
                isValid = false;
            } else if ((c >= '0') && (c <= '9')) {
                isValid = false;
            }
        }

        return isValid;
    }
    
    public static boolean checkNIC(String nic) {
        boolean status = true;
        try {
            String nicFirst_9_Digit = nic.substring(0, 9);
            String nicLastCharacter = nic.substring(9, 10);

            if (nic.length() > 10) {
                status = false;
            }
            if (!isNumeric(nicFirst_9_Digit)) {
                status = false;

            }

            if (!nicLastCharacter.equalsIgnoreCase("v") && !nicLastCharacter.equalsIgnoreCase("x")) {
                status = false;

            }
        } catch (Exception e) {
            status = false;
        }
        return status;
    }

    public static boolean isAllowNumericNonSpecialString(String theInputString) {
        boolean isValid = true;

        for (int i = 0; i < theInputString.length(); i++) {
            char c = theInputString.charAt(i);

            if ((c == '\'') || (c == '\\') || (c == '\'') || (c == '!') || (c == ':') || (c == '"') || (c == ';') || (c == ',') || (c == '@') || (c == '|') || (c == '*') || (c == '#') || (c == '{') || (c == '}') || (c == '`') || (c == '?')
                    || (c == '$') || (c == '%') || (c == '^') || (c == '&') || (c == '/') || (c == '>') || (c == '<') || (c == '(') || (c == ')') || (c == '~')) {
                isValid = false;
            }
        }

        return isValid;
    }

}
