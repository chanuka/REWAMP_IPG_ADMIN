/**
 * 
 */
package com.epic.epay.ipg.util.common;

import java.security.MessageDigest;

/**
 *  @created :Oct 25, 2013, 4:07:21 PM
 *  @author  :thushanth
 */
public class IPGMd5 {
	
    public static String ipgMd5(String value) throws Exception {
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.update(value.getBytes("UTF8"));
        byte s[] = m.digest();
        String result = "";
        for (int i = 0; i < s.length; i++) {
            result += Integer.toHexString((0x000000ff & s[i]) | 0xffffff00).substring(6);
        }
        return result;
    }

}
