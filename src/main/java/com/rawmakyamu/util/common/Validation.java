/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.util.common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
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
//        try {
        boolean isValid = false;

        for (int i = 0; i < val.length(); i++) {
            char c = val.charAt(i);

            if ((c == '"') || (c == ';') || (c == ',') || (c == '@') || (c == '|') || (c == '*') || (c == '#') || (c == '{') || (c == '}') || (c == '`') || (c == '?')
                    || (c == '!') || (c == '$') || (c == '%') || (c == '^') || (c == '&') || (c == '/') || (c == '>') || (c == '<') || (c == '(') || (c == ')') || (c == '~') || (c == '\'')) {
                return isValid;
            }
        }
        return true;
//        } catch (Exception e) {
//            return true;
//        }
    }

    public static boolean isSpecailCharacterNumber(String val) {
//        try {
        boolean isValid = false;

        for (int i = 0; i < val.length(); i++) {
            char c = val.charAt(i);

            if ((c == '"') || (c == ';') || (c == ',') || (c == '@') || (c == '|') || (c == '*') || (c == '#') || (c == '{') || (c == '}') || (c == '`') || (c == '?')
                    || (c == '!') || (c == '$') || (c == '%') || (c == '^') || (c == '&') || (c == '/') || (c == '>') || (c == '<') || (c == '(') || (c == ')') || (c == '~') || (c == '\'')
                    || (c == '1') || (c == '2') || (c == '3') || (c == '4') || (c == '5') || (c == '6') || (c == '7')
                    || (c == '8') || (c == '9') || (c == '0')) {
                return isValid;
            }
        }
        return true;
//        } catch (Exception e) {
//            return true;
//        }
    }

    public static boolean isAddressbyChar(String val) {
//        try {
        boolean isValid = false;

        for (int i = 0; i < val.length(); i++) {
            char c = val.charAt(i);

            if ((c == ';') || (c == '@')
                    || (c == '|') || (c == '*')
                    || (c == '#') || (c == '{')
                    || (c == '}') || (c == '`')
                    || (c == '?') || (c == '!')
                    || (c == '$') || (c == '%')
                    || (c == '^') || (c == '&')
                    || (c == '>') || (c == '<')
                    || (c == '(') || (c == ')')
                    || (c == '~') || (c == '\'')
                    || (c == ':')
                    || (c == '-') || (c == '_')
                    || (c == '+') || (c == '=')) {
                return isValid;
            }
        }
        return true;
//        } catch (Exception e) {
//            return true;
//        }
    }

    public static boolean isSpecailCharacterWithbracket(String val) {
//        try {
        boolean isValid = false;

        for (int i = 0; i < val.length(); i++) {
            char c = val.charAt(i);

            if ((c == '"') || (c == ';') || (c == ',') || (c == '@') || (c == '|') || (c == '*') || (c == '#') || (c == '{') || (c == '}') || (c == '`') || (c == '?')
                    || (c == '!') || (c == '$') || (c == '%') || (c == '^') || (c == '&') || (c == '/') || (c == '>') || (c == '<') || (c == '~') || (c == '\'')) {
                return isValid;
            }
        }
        return true;
//        } catch (Exception e) {
//            return true;
//        }
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

    public static boolean isAddress(String value) {
        boolean valied = true;
        try {
            if (value.matches("[^a-zA-Z0-9.,'/ ]")) {
                valied = false;
            }
        } catch (Exception e) {
            valied = false;
        }
        return valied;
    }

    public static boolean isLatitudeLongitude(String value) {
        boolean isValid = true;
        try {

////            String regex = "(\\-?\\d+(\\.\\d+)?),\\s*(\\-?\\d+(\\.\\d+)?)$";
//            String regex = "[^0-9.+-]";
//            Pattern pattern = Pattern.compile(regex);
//            Matcher matcher = pattern.matcher(str);
//            if (!matcher.matches()) {
//                isValid = false;
//            }
            isValid = isLL(value);
            if (isValid) {
                if (value.contains(".")) {
                    isValid = false;

                } else {
                    isValid = true;
                }

            } else {
                isValid = true;
            }

        } catch (Exception e) {
            isValid = true;
        }
        return isValid;
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

    private static final String LL_PATTERN = "\\d{0,15}\\.\\d{0,15}";

    public static boolean isLL(String inputString) {
        boolean validFlag = false;
        Pattern p = Pattern.compile(LL_PATTERN);
        Matcher m = p.matcher(inputString.trim());
        if (m.matches()) {
            validFlag = true;
        }
        return validFlag;
    }

    public static boolean isLatitude(String inputString) {
        boolean validFlag = true;
        Pattern p = Pattern.compile("[^0-9.]");
        Matcher m = p.matcher(inputString.trim());
        if (m.matches()) {
            validFlag = false;
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
        if (cardassociation == null || "".equals(cardassociation)) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean isValidStatus(String status) {
        if (status == null || "".equals(status)) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean isvalidIP(String IP) { // throws PatternSyntaxException,Exception

        try {

            String regularExpression = "(([0-9]){1,3}\\.([0-9]){1,3}\\.([0-9]){1,3}\\.([0-9]){1,3}){1}";
            Pattern pat = Pattern.compile(regularExpression);
            Matcher mat = pat.matcher(IP);

            if (mat.matches()) {
                String ipArray[] = IP.split("\\.");
                if (Integer.parseInt(ipArray[0]) < 256 && Integer.parseInt(ipArray[1]) < 256 && Integer.parseInt(ipArray[2]) < 256 && Integer.parseInt(ipArray[3]) < 256) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }

        } catch (PatternSyntaxException e) {
            return false;
        } catch (Exception ex) {
            return false;
        }

    }

    public static boolean isithaveNumber(String code) {

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
        String message = "";

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
            message = message + extensions.get(i) + ",";

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

    public static String isExcel(String filename) {
        String message = "";

        List<String> extensions = new ArrayList<String>();
        extensions.add("xlsx");
        extensions.add("xls");
        extensions.add("xlsm");

        int pos = filename.lastIndexOf('.') + 1;
        String ext = filename.substring(pos);
        String final_ext = ext.toLowerCase();

        for (int i = 0; i < extensions.size(); i++) {
            if (extensions.get(i).equals(final_ext)) {
                return message;

            }
        }
        message = "Please upload file with one of the following extensions:";
        for (int i = 0; i < extensions.size(); i++) {
            message = message + extensions.get(i) + ",";

        }
        return message;
    }

    public static String excelType(String filename) {
        String message = "";

        List<String> extensions = new ArrayList<String>();
        extensions.add("xlsx");
        extensions.add("xls");
        extensions.add("xlsm");

        int pos = filename.lastIndexOf('.') + 1;
        String ext = filename.substring(pos);
        String final_ext = ext.toLowerCase();

        for (int i = 0; i < extensions.size(); i++) {
            if (extensions.get(i).equals(final_ext)) {
                message = extensions.get(i);
                break;

            }
        }
        return message;
    }

    public static Boolean validPhoneno(String mobileNo) {

        boolean valid = false;

        if (mobileNo.length() != 11) {
            valid = false;
        } else if (mobileNo.charAt(0) != '9' || mobileNo.charAt(1) != '4') {
            valid = false;
        } else {
            for (int i = 0; i < mobileNo.length(); i++) {

                char c = mobileNo.charAt(i);

                if (Character.isDigit(c)) {
                    valid = true;
                } else {
                    valid = false;
                    break;
                }
            }
        }
        return valid;
    }

    public static Boolean validLocalPhoneno(String mobileNo) {

        boolean valid = false;

        if (mobileNo.length() != 10) {
            valid = false;
        } else if (mobileNo.charAt(0) != '0' || mobileNo.charAt(1) != '7') {
            valid = false;
        } else {
            for (int i = 0; i < mobileNo.length(); i++) {

                char c = mobileNo.charAt(i);

                if (Character.isDigit(c)) {
                    valid = true;
                } else {
                    valid = false;
                    break;
                }
            }
        }
        return valid;
    }

    public static Boolean validLocalPhonenoAll(String mobileNo) {

        boolean valid = false;

        if (mobileNo.length() != 10) {
            valid = false;
        } else if (mobileNo.charAt(0) != '0') {
            valid = false;
        } else {
            for (int i = 0; i < mobileNo.length(); i++) {

                char c = mobileNo.charAt(i);

                if (Character.isDigit(c)) {
                    valid = true;
                } else {
                    valid = false;
                    break;
                }
            }
        }
        return valid;
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

    public static boolean isValidfilepath(String path, String ostype) throws PatternSyntaxException, Exception {

        try {
            if ("Windows".equalsIgnoreCase(ostype)) {
//            String regularExpression = "([a-zA-Z]:)?(\\\\[^?*\"<>|/]+)+\\\\?";            
//            String regularExpression = "([a-zA-Z]:\\\\){1}([^?*\"<:>|/]*)+[^\\\\?*\"<:>|/]$";

//            String regularExpression = "([a-zA-Z]:){1}(\\\\([^?*\"<:>|/\\\\])+)+";
                String regularExpression = "([a-zA-Z]:\\\\){1}(([^?*\"<:>|/\\\\])+(\\\\[^?*\"<:>|/\\\\])*)*";
                boolean isMatched = Pattern.matches(regularExpression, path);
                return isMatched;

            } else if ("Linux".equalsIgnoreCase(ostype)) {

                String regularExpression = "(/){1}(.*)+[^/]$";
                boolean isMatched = Pattern.matches(regularExpression, path);
                return isMatched;
            } else if ("Solaris".equalsIgnoreCase(ostype)) {

                String regularExpression = "(/){1}(.*)+[^/]$";
                boolean isMatched = Pattern.matches(regularExpression, path);
                return isMatched;
            } else {
                return false;
            }
        } catch (PatternSyntaxException e) {
            throw e;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public static boolean emailMessageValidation(String code) {

        boolean isValid = false;

        final Pattern wordOTP = Pattern.compile("\\<otp\\>");
        final Pattern wordREF = Pattern.compile("\\<ref\\>");
        final Matcher otpMatcher = wordOTP.matcher(code.toLowerCase());
        int otpWordCount = 0;
        while (otpMatcher.find()) {
            otpWordCount++;
        }
        final Matcher refMatcher = wordREF.matcher(code.toLowerCase());
        int refWordCount = 0;
        while (refMatcher.find()) {
            refWordCount++;
        }
        if (otpWordCount == 1 && refWordCount == 1) {
            isValid = true;
        }

        return isValid;
    }

    public static boolean displaynameValidation(String code) {

        boolean isValid = false;

        final Pattern wordOTP = Pattern.compile("\\[CARD\\]");
        final Matcher otpMatcher = wordOTP.matcher(code);
        int otpWordCount = 0;
        while (otpMatcher.find()) {
            otpWordCount++;
        }

        if (otpWordCount == 1) {
            isValid = true;
        }

        return isValid;
    }

    public static boolean smsValidation(String code) {

        boolean isValid = false;

        final Pattern wordOTP = Pattern.compile("\\|otp\\|");
//        final Pattern wordREF = Pattern.compile("\\<ref\\>");
        final Matcher otpMatcher = wordOTP.matcher(code.toLowerCase());
        int otpWordCount = 0;
        while (otpMatcher.find()) {
            otpWordCount++;
        }
//        final Matcher refMatcher = wordREF.matcher(code.toLowerCase());
//        int refWordCount = 0;
//        while (refMatcher.find()) {
//            refWordCount++;
//        }
        if (otpWordCount == 1) {
            isValid = true;
        }

        return isValid;
    }

    public static String isCSV(String filename) {
        String message = "";

        List<String> extensions = new ArrayList<String>();
        extensions.add("csv");
//        extensions.add("doc");
//        extensions.add("xlsm");

        int pos = filename.lastIndexOf('.') + 1;
        String ext = filename.substring(pos);
        String final_ext = ext.toLowerCase();

        for (int i = 0; i < extensions.size(); i++) {
            if (extensions.get(i).equals(final_ext)) {
                return message;

            }
        }
        message = "Please upload file with following extension:";
        for (int i = 0; i < extensions.size(); i++) {
            message = message + extensions.get(i);

        }
        return message;
//        return "";
    }

    public static String isText(String filename) {
        String message = "";

        List<String> extensions = new ArrayList<String>();
        extensions.add("txt");
//        extensions.add("doc");
//        extensions.add("xlsm");

        int pos = filename.lastIndexOf('.') + 1;
        String ext = filename.substring(pos);
        String final_ext = ext.toLowerCase();

        for (int i = 0; i < extensions.size(); i++) {
            if (extensions.get(i).equals(final_ext)) {
                return message;

            }
        }
        message = "Please upload file with one of the following extensions:";
        for (int i = 0; i < extensions.size(); i++) {
            message = message + extensions.get(i);

        }
        return message;
//        return "";
    }

    public static String isImageIB(String filename) {
        String message = "";

        List<String> extensions = new ArrayList<String>();
        extensions.add("jpg");
        extensions.add("png");
        extensions.add("img");
        extensions.add("gif");

        int pos = filename.lastIndexOf('.') + 1;
        String ext = filename.substring(pos);
        String final_ext = ext.toLowerCase();

        for (int i = 0; i < extensions.size(); i++) {
            if (extensions.get(i).equals(final_ext)) {
                return message;

            }
        }
        message = "Please upload image with one of the following extensions:";
        for (int i = 0; i < extensions.size(); i++) {
            message = message + extensions.get(i) + ",";

        }
        return message;
    }

    public static String isImageMOB(String filename) {
        String message = "";

        List<String> extensions = new ArrayList<String>();
        extensions.add("jpg");
        extensions.add("png");
        extensions.add("img");
        extensions.add("gif");

        int pos = filename.lastIndexOf('.') + 1;
        String ext = filename.substring(pos);
        String final_ext = ext.toLowerCase();

        for (int i = 0; i < extensions.size(); i++) {
            if (extensions.get(i).equals(final_ext)) {
                return message;

            }
        }
        message = "Mobile Image : Please upload image with one of the following extensions:";
        for (int i = 0; i < extensions.size(); i++) {
            message = message + extensions.get(i) + ",";

        }
        return message;
    }

    public static String isImageTAB(String filename) {
        String message = "";

        List<String> extensions = new ArrayList<String>();
        extensions.add("jpg");
        extensions.add("png");
        extensions.add("img");
        extensions.add("gif");

        int pos = filename.lastIndexOf('.') + 1;
        String ext = filename.substring(pos);
        String final_ext = ext.toLowerCase();

        for (int i = 0; i < extensions.size(); i++) {
            if (extensions.get(i).equals(final_ext)) {
                return message;

            }
        }
        message = "Tab Image : Please upload image with one of the following extensions:";
        for (int i = 0; i < extensions.size(); i++) {
            message = message + extensions.get(i) + ",";

        }
        return message;
    }
    //

    public static boolean isLongitudeLatitude(String str) {
        boolean isValid = true;
        try {

////            String regex = "(\\-?\\d+(\\.\\d+)?),\\s*(\\-?\\d+(\\.\\d+)?)$";
//            String regex = "[^0-9.+-]";
//            Pattern pattern = Pattern.compile(regex);
//            Matcher matcher = pattern.matcher(str);
//            if (!matcher.matches()) {
//                isValid = false;
//            }
            isValid = isLL(str);
            if (isValid) {
                if (str.contains(".")) {
                    isValid = false;

                } else {
                    isValid = true;
                }

            } else {
                isValid = true;
            }

        } catch (Exception e) {
            isValid = true;
        }
        return isValid;
    }

    public static boolean isUtilityProviderOption_RefPattern(String str) {
        try {
            boolean isValid;
            String regex = "[A-BD-MO-Za-z0-9'\"]";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(str);
            if (!matcher.find()) {
                isValid = true;
//                System.err.println("valid pattern");
            } else {
                isValid = false;
//                System.err.println("invalid pattern");
            }
            if (str.contains("C") || str.contains("N")) {
                isValid = true;
//                 System.err.println("has C or N");
            } else {
//                 System.err.println("does not has C or N");
                isValid = false;
            }
            return isValid;
        } catch (Exception e) {
            return true;
        }
    }

    public static boolean compareSampletoPattern(String sample, String pattern) {
        boolean isValid = false;
        String[] sample_arr = sample.split("");
        String[] pattern_arr = pattern.split("");
        if (sample.length() == pattern.length()) {
            for (int i = 1; i <= sample.length(); i++) {
                System.err.println(sample_arr[i] + " " + pattern_arr[i]);
                if (pattern_arr[i].equals("C")) {
                    isValid = Validation.isString(sample_arr[i]);
                    if (!isValid) {
                        break;
                    }
                } else if (pattern_arr[i].equals("N")) {
                    isValid = Validation.isNumeric(sample_arr[i]);
                    if (!isValid) {
                        break;
                    }
                } else if (pattern_arr[i].equals(sample_arr[i])) {
                    isValid = true;
                } else {
                    isValid = false;
                    break;
                }
            }
        } else {
            isValid = false;
        }
        return isValid;
    }

    public static boolean isValidateNIC(String nic) {
        boolean status = true;
        try {
            if ((nic.length() == 10)) {
                String nicFirst_9_Digit = nic.substring(0, 9);
                String nicLastCharacter = nic.substring(9, 10);

                if (!isNumeric(nicFirst_9_Digit)) {
                    status = false;
                }

                if (!nicLastCharacter.equalsIgnoreCase("v") && !nicLastCharacter.equalsIgnoreCase("x")) {
                    status = false;

                }
            } else if ((nic.length() == 12)) {

                if (!isNumeric(nic)) {
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

    public static boolean isValidateDoBwithNIC(String dob, String nic) {
        boolean status = true;
        try {

            if ((nic.length() == 10)) {
                if (nic.substring(0, 2).equals(dob.substring(2, 4))) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
                    String dateInString = dob.substring(2, 10);
                    String dateInStringJanF = dob.substring(2, 5) + "01-00";    //set to Jan 1st
                    Date date = sdf.parse(dateInString);
                    Date dateJanF = sdf.parse(dateInStringJanF);

                    long diff = date.getTime() - dateJanF.getTime();
                    long noof_day = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

                    long nic_days = Long.parseLong(nic.substring(2, 5));
                    if (nic_days >= 500) {                  //avoid gender
                        nic_days = nic_days - 500;
                    }
                    if (date.getYear() % 4 != 0) {
                        noof_day = noof_day + 1;
                    }
                    status = nic_days == noof_day;          //return true if equal
                    System.err.println("dob :" + noof_day);
                    System.err.println("nic :" + nic_days);
                } else {
                    status = false;
                }
            } else if ((nic.length() == 12)) {
                if (nic.substring(2, 4).equals(dob.substring(2, 4))) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String dateInString = dob.substring(0, 10);
                    String dateInStringJanF = dob.substring(0, 5) + "01-00";
                    Date date = sdf.parse(dateInString);
                    Date dateJanF = sdf.parse(dateInStringJanF);

                    long diff = date.getTime() - dateJanF.getTime();

                    long noof_day = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                    long nic_days = Long.parseLong(nic.substring(4, 7));
                    if (nic_days >= 500) {
                        nic_days = nic_days - 500;
                    }

                    status = nic_days == noof_day;
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

    public static boolean walletIDValidation(String code, String currentSeries) {

        boolean isValid = false;

        final Pattern wordOTP = Pattern.compile("^" + currentSeries + "([0-9]{6})$");
        final Matcher otpMatcher = wordOTP.matcher(code);
        int otpWordCount = 0;
        while (otpMatcher.find()) {
            otpWordCount++;
        }

        if (otpWordCount == 1) {
            isValid = true;
        }

        return isValid;
    }

    public static String isImageMobileLogo(String filename) {
        String message = "";

        List<String> extensions = new ArrayList<String>();
        extensions.add("jpg");
        extensions.add("png");
        extensions.add("img");
        extensions.add("gif");

        int pos = filename.lastIndexOf('.') + 1;
        String ext = filename.substring(pos);
        String final_ext = ext.toLowerCase();

        for (int i = 0; i < extensions.size(); i++) {
            if (extensions.get(i).equals(final_ext)) {
                return message;

            }
        }
        message = "Mobile logo : Please upload image with one of the following extensions:";
        for (int i = 0; i < extensions.size(); i++) {
            message = message + extensions.get(i) + ",";

        }
        return message;
    }

    public static String isImageWebLogo(String filename) {
        String message = "";

        List<String> extensions = new ArrayList<String>();
        extensions.add("jpg");
        extensions.add("png");
        extensions.add("img");
        extensions.add("gif");

        int pos = filename.lastIndexOf('.') + 1;
        String ext = filename.substring(pos);
        String final_ext = ext.toLowerCase();

        for (int i = 0; i < extensions.size(); i++) {
            if (extensions.get(i).equals(final_ext)) {
                return message;

            }
        }
        message = "Web logo : Please upload image with one of the following extensions:";
        for (int i = 0; i < extensions.size(); i++) {
            message = message + extensions.get(i) + ",";

        }
        return message;
    }

    public static boolean isValiedDateString(String str) {
        try {
            boolean isValid = true;
//            String regex = "/^\\d{4}-\\d{2}-\\d{2}$/";
            String regex = "((?:19|20)\\d\\d)-(0?[1-9]|1[012])-([12][0-9]|3[01]|0?[1-9])";
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
}
