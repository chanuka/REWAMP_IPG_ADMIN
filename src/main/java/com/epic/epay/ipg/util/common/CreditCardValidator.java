/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.epic.epay.ipg.util.common;

/**
 *
 * @author upul
 */
import java.util.regex.*;


public class CreditCardValidator  {
    public static final int MASTERCARD = 0, VISA = 1;
    public static final int AMEX = 2, DISCOVER = 3, DINERS = 4;
 

    private static final String[] messages = {
        "Not a valid number for MasterCard.",
        "Not a valid number for Visa.",
        "Not a valid number for American Express.",
       
    };

    public CreditCardValidator()
    {
      
      
    }

    public boolean validationCriteria(String cardNumber,int cardType) {
        String number = cardNumber;

        if (number.equals("")) {
           // setMessage("Field cannnot be blank.");
            return false;
        }

        Matcher m = Pattern.compile("[^\\d\\s.-]").matcher(number);

        if (m.find()) {
           // setMessage("Credit card number can only contain numbers, spaces, \"-\", and \".\"");
            return false;
        }

        int type = cardType;
      //  setMessage(messages[type]);
        Matcher matcher = Pattern.compile("[\\s.-]").matcher(number);
        number = matcher.replaceAll("");
        return validate(number, type);
    }

    // Check that cards start with proper digits for
    // selected card type and are also the right length.

    public  boolean validate(String number, int type) {
        switch(type) {

        case 0:
            if (number.length() != 16 ||
                Integer.parseInt(number.substring(0, 2)) < 51 ||
                Integer.parseInt(number.substring(0, 2)) > 55)
            {
                return false;
            }
            break;

        case 1:
            
            if ((number.length() != 13 && number.length() != 16) ||
                    Integer.parseInt(number.substring(0, 1)) != 4)
            {
              
                return false;
            }
            break;

        case 2:
            if (number.length() != 15 ||
                (Integer.parseInt(number.substring(0, 2)) != 34 &&
                    Integer.parseInt(number.substring(0, 2)) != 37))
            {
                return false;
            }
            break;

        case 3:
            if (number.length() != 16 ||
                Integer.parseInt(number.substring(0, 5)) != 5504)
            {
                return false;
            }
            break;

        case 4:
            if (number.length() != 14 ||
                ((Integer.parseInt(number.substring(0, 2)) != 36 &&
                    Integer.parseInt(number.substring(0, 2)) != 38) &&
                    Integer.parseInt(number.substring(0, 3)) < 300 ||
                        Integer.parseInt(number.substring(0, 3)) > 305))
            {
                return false;
            }
            break;
        }
        return luhnValidate(number);
    }

    // The Luhn algorithm is basically a CRC type
    // system for checking the validity of an entry.
    // All major credit cards use numbers that will
    // pass the Luhn check. Also, all of them are based
    // on MOD 10.

    public boolean luhnValidate(String numberString) {
        char[] charArray = numberString.toCharArray();
        int[] number = new int[charArray.length];
        int total = 0;

        for (int i=0; i < charArray.length; i++) {
            number[i] = Character.getNumericValue(charArray[i]);
        }

        for (int i = number.length-2; i > -1; i-=2) {
            number[i] *= 2;

            if (number[i] > 9)
                number[i] -= 9;
        }

        for (int i=0; i < number.length; i++)
            total += number[i];

            if (total % 10 != 0)
                return false;

        return true;
    }
}
