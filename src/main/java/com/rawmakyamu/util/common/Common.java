/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.util.common;

import com.rawmakyamu.dao.common.CommonDAO;
import com.rawmakyamu.util.mapping.Systemaudit;
import com.rawmakyamu.util.mapping.Task;
import com.rawmakyamu.util.mapping.Systemuser;
import com.rawmakyamu.util.varlist.SessionVarlist;
import com.rawmakyamu.util.varlist.TaskVarList;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author chanuka
 */
public class Common {

    //Make Audittrace
    public static Systemaudit makeAudittrace(HttpServletRequest request, String task, String page, String section, String description, String remarks) throws Exception {

        HttpSession session = request.getSession(false);
        Systemuser sysUser = (Systemuser) session.getAttribute(SessionVarlist.SYSTEMUSER);
        Systemaudit audit = new Systemaudit();
        audit.setUserrolecode(sysUser.getUserrole().getUserrolecode());
        audit.setDescription(description + " by " + sysUser.getUsername());
        audit.setIp(request.getRemoteAddr());

        CommonDAO dao = new CommonDAO();

        audit.setSectioncode(dao.getSectionByRoleAndPage(audit.getUserrolecode(), page));

//        audit.setStatus(CommonVarList.STATUS_ACTIVE);
        audit.setPagecode(page);

        audit.setTaskcode(task);

//        Systemuser us = new Systemuser();
//        us.setUsername(sysUser.getUsername());
//        audit.setRemarks(remarks);
        audit.setLastupdateduser(sysUser.getUsername());
        return audit;

    }

    //Make Audittrace with new value and old value
    public static Systemaudit makeAudittrace(HttpServletRequest request, String task, String page, String section, String description, String remarks, String oldvalue, String newvalue) throws Exception {

        HttpSession session = request.getSession(false);
        Systemuser sysUser = (Systemuser) session.getAttribute(SessionVarlist.SYSTEMUSER);
        Systemaudit audit = new Systemaudit();
        audit.setUserrolecode(sysUser.getUserrole().getUserrolecode());
        audit.setDescription(description + " by " + sysUser.getUsername());
        audit.setIp(request.getRemoteAddr());

        audit.setOldvalue(oldvalue);
        audit.setNewvalue(newvalue);

        CommonDAO dao = new CommonDAO();

        audit.setSectioncode(dao.getSectionByRoleAndPage(audit.getUserrolecode(), page));

//        audit.setStatus(CommonVarList.STATUS_ACTIVE);
        audit.setPagecode(page);

        audit.setTaskcode(task);

//        Systemuser us = new Systemuser();
//        us.setUsername(sysUser.getUsername());
//        audit.setRemarks(/remarks);
        audit.setLastupdateduser(sysUser.getUsername());
        return audit;

    }

    //Make Audittrace
    public static Systemaudit makeAudittrace(HttpServletRequest request, Systemuser user, String task, String page, String section, String description, String remarks) {

        Systemaudit audit = new Systemaudit();
        audit.setUserrolecode(user.getUserrole().getUserrolecode());
        audit.setDescription(description + " by " + user.getUsername());
        audit.setIp(request.getRemoteAddr());

        audit.setSectioncode(section);

//        audit.setStatus(CommonVarList.STATUS_ACTIVE);
        audit.setPagecode(page);

        audit.setTaskcode(task);

//        Systemuser us = new Systemuser();
//        us.setUsername(user.getUsername());
//        audit.setRemarks(remarks);
        audit.setLastupdateduser(user.getUsername());
        return audit;
    }

    public static String makeHash(String text) throws Exception {
        MessageDigest md;
        md = MessageDigest.getInstance("MD5");
        byte[] md5hash = new byte[32];
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        md5hash = md.digest();
        return convertToHex(md5hash);
    }

    private static String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9)) {
                    buf.append((char) ('0' + halfbyte));
                } else {
                    buf.append((char) ('a' + (halfbyte - 10)));
                }
                halfbyte = data[i] & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    //checks the accees to the method name passed
    public boolean checkMethodAccess(String taskcode, String page, String userRole, HttpServletRequest request) {
        boolean access = false;
        if (taskcode == null) {
            access = false;
        } else if (taskcode.isEmpty()) {
            access = false;
        } else {
            HttpSession session = request.getSession(false);
            HashMap<String, List<Task>> pageMap = (HashMap<String, List<Task>>) session.getAttribute(SessionVarlist.TASKMAP);
            List<Task> taskList = pageMap.get(page);
            if (taskList == null) {
                access = false;
            } else if (taskList.size() < 1) {
                access = false;
            } else {
                for (Task task : taskList) {
                    if (task.getTaskcode().toString().trim().equalsIgnoreCase(taskcode.trim())) {
                        access = true;
                        if (task.getTaskcode().toString().equalsIgnoreCase(TaskVarList.VIEW_TASK)) {

                            try {

                                session.setAttribute(SessionVarlist.CURRENTPAGE, new CommonDAO().getPageDescription(page).getDescription());

                                session.setAttribute(SessionVarlist.CURRENTSECTION, new CommonDAO().getSectionOfPage(page, userRole).getDescription());

                            } catch (Exception ex) {
                                Logger.getLogger(Common.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                        break;
                    }
                }
            }
        }
        return access;
    }

    //returns allowed task list of current user
    public List<Task> getUserTaskListByPage(String page, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        HashMap<String, List<Task>> pageMap = (HashMap<String, List<Task>>) session.getAttribute(SessionVarlist.TASKMAP);
        List<Task> taskList = pageMap.get(page);
        return taskList;
    }

    public static Date formatStringtoDate(String date) {
        Date fdate = null;
        try {
            String pattern = "dd/MM/yyyy";
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            fdate = dateFormat.parse(date);
        } catch (Exception e) {
            System.out.println("Date Conversion Error");
        }
        return fdate;
    }

//    //date format = dd/MM/yyyy hh:mm
//    public static Date convertStringtoDate(String date) throws Exception {
//        Date fdate = null;
//        try {
//            String pattern = "dd/MM/yyyy HH:mm";
//            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
//            fdate = dateFormat.parse(date);
//
//            System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(fdate));
//            fdate=convertStringtoDate2(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(fdate));
//            
//        } catch (Exception e) {
//            throw e;
//        }
//        return fdate;
//    }
    //date format = dd/MM/yyyy hh:mm
    public static Date convertStringtoDate(String date) throws Exception {
        Date fdate = null;
        try {
            String pattern = "dd/MM/yyyy HH:mm";
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            fdate = dateFormat.parse(date);
        } catch (Exception e) {
            throw e;
        }
        return fdate;
    }

    //date format = dd/MM/yyyy hh:mm
    public static Date convertStringtoDate3(String date) throws Exception {
        Date fdate = null;
        try {
            String pattern = "dd-MM-yyyy HH:mm";
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            fdate = dateFormat.parse(date);
        } catch (Exception e) {
            throw e;
        }
        return fdate;
    }

    public static Date convertStringtoDate2(String date) throws Exception {
        Date fdate = null;
        try {
            String pattern = "yyyy-mm-dd HH:mm:ss";
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            fdate = dateFormat.parse(date);
        } catch (Exception e) {
            throw e;
        }
        return fdate;
    }

    public static String convertStringtoStr(String date) throws Exception {
        Date fdate = null;
        String fdat = null;
        try {
            String pattern = "dd/MM/yyyy HH:mm";
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            fdate = dateFormat.parse(date);

            fdat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(fdate);

        } catch (Exception e) {
            throw e;
        }
        return fdat;
    }

    public static String convertStringtoStr2(String date) throws Exception {
        Date fdate = null;
        String fdat = null;
        try {
            String pattern = "dd-MM-yyyy HH:mm";
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            fdate = dateFormat.parse(date);

            fdat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(fdate);

        } catch (Exception e) {
            throw e;
        }
        return fdat;
    }

    public static String convertStringtoStrUserMgt(String date) throws Exception {
        Date fdate = null;
        String fdat = null;
        try {
            String pattern = "yyyy-MM-dd";
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            fdate = dateFormat.parse(date);

            fdat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(fdate);

        } catch (Exception e) {
            throw e;
        }
        return fdat;
    }

    public static Date specialStringtoDate(String date) {
        Date fdate = null;
        try {
            String pattern = "yyyy-MM-dd";
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            fdate = dateFormat.parse(date);
        } catch (Exception e) {
            System.out.println("Date Conversion Error");
        }
        return fdate;
    }

    //format date into dd/MM/yyyy hh:mm 
    public static String getFormattedDate(Date date) {
        String fDate = "";
        String pattern = "dd/MM/yyyy HH:mm";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        fDate = dateFormat.format(date);
        return fDate;
    }

    //format date into dd-MM-yyyy hh:mm 
    public static String getFormattedDate2(Date date) {
        String fDate = "";
        String pattern = "dd-MM-yyyy HH:mm";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        fDate = dateFormat.format(date);
        return fDate;
    }

    public static long formatStringtoLongDate(String date) {
        Date fdate = null;
        long sqldate = 0;
        try {
            String pattern = "dd/MM/yyyy";
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            fdate = dateFormat.parse(date);

            sqldate = fdate.getTime();

        } catch (Exception e) {
            System.out.println("Date Conversion Error");
        }
        return sqldate;
    }

    //for login Action (Logged date and current time)
    public static String getFormattedDateForLogin(Date date) {
        String fDate = "";
        String pattern = "dd MMMM yyyy hh:mm a";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        fDate = dateFormat.format(date);
        return fDate;
    }

    public static String mpiMd5(String value) throws Exception {
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.update(value.getBytes("UTF8"));
        byte s[] = m.digest();
        String result = "";
        for (int i = 0; i < s.length; i++) {
            result += Integer.toHexString((0x000000ff & s[i]) | 0xffffff00).substring(6);
        }
        return result;
    }

    public static String getOS_Type() {

        String osType = "";
        String osName = "";
        osName = System.getProperty("os.name", "").toLowerCase();

        // For WINDOWS
        if (osName.contains("windows")) {
            osType = "WINDOWS";
        } else if (osName.contains("linux")) {
            osType = "LINUX";
        } else if (osName.contains("sunos")) {
            osType = "SUNOS";
        }

        return osType;
    }

    public static ByteArrayOutputStream zipFiles(File[] listFiles) throws Exception {
        byte[] buffer;
        ByteArrayOutputStream outputStream = null;
        ZipOutputStream zipOutputStream = null;
        FileInputStream fileInputStream = null;
        try {
            outputStream = new ByteArrayOutputStream();
            zipOutputStream = new ZipOutputStream(outputStream);
            for (File file : listFiles) {
                buffer = new byte[(int) file.length()];
                fileInputStream = new FileInputStream(file);
                fileInputStream.read(buffer, 0, (int) file.length());
                ZipEntry ze = new ZipEntry(file.getName());

                zipOutputStream.putNextEntry(ze);
                zipOutputStream.write(buffer);
                zipOutputStream.closeEntry();
                fileInputStream.close();
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            if (zipOutputStream != null) {
                zipOutputStream.finish();
                zipOutputStream.close();
            }
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();
            }
        }
        return outputStream;
    }

    public static String replaceEmptyorNullStringToNA(String string) {
        String value = "--";
        if (string != null && !string.trim().isEmpty()) {
            value = string;
        }
        return value;
    }

    public static String replaceEmptyorNullStringToALL(String string) {
        String value = "-ALL-";
        if (string != null && !string.trim().isEmpty()) {
            value = string;
        }
        return value;
    }

    public static String checkEmptyorNullString(String str) {
        if (str == null || str.isEmpty()) {
            str = "--";
        }
        return str;
    }

    public static String checkEmptyorNullString(String feildName, String str) {
        if (str == null || str.isEmpty()) {
            str = "";
        } else {
            str = feildName + " - " + str + ",";
        }
        return str;
    }

    public static String checkEmptyorNullStringReturn(String str) {
        if (str == null || str.isEmpty()) {
            str = "";
        }
        return str;
    }

    public static String toCurrencyFormat(String digits) {

        String currencyF = "";
        digits = digits.replace(",", "");
        String[] part = digits.split("[.]");
        if (0 < part.length && part.length < 3) {
            if (part.length == 1) {
                String reversed = new StringBuilder(digits).reverse().toString();
                char[] stringToCharArray = reversed.toCharArray();
                for (int i = 0; i < reversed.length(); i++) {
                    currencyF = currencyF + stringToCharArray[i];
                    if (i % 3 == 2 && i != 0 && i != reversed.length() - 1) {
                        currencyF = currencyF + ",";
                    }
                }
                currencyF = new StringBuilder(currencyF).reverse().toString();
            } else if (part.length == 2) {
                String reversed = new StringBuilder(part[0]).reverse().toString();
                char[] stringToCharArray = reversed.toCharArray();
                for (int i = 0; i < reversed.length(); i++) {
                    currencyF = currencyF + stringToCharArray[i];
                    if (i % 3 == 2 && i != 0 && i != reversed.length() - 1) {
                        currencyF = currencyF + ",";
                    }
                }
                currencyF = new StringBuilder(currencyF).reverse().toString() + "." + part[1];
            }
        }
        return currencyF;
//        NumberFormat.getNumberInstance(Locale.US).format(Integer.parseInt(testString))
    }

    public static String toCurrencyPattern(String digits) {
        return NumberFormat.getNumberInstance(Locale.US).format(Integer.parseInt(digits));
    }

    public static boolean isMorethan100BigDecimal(String digits) {
        boolean more = true;
        try {
            BigDecimal checkVal = new BigDecimal(digits);
            BigDecimal maxVal = new BigDecimal("100");
            BigDecimal res = maxVal.subtract(checkVal);

            if (res.signum() == -1) {
//                System.err.println("Should be less than 100");
                more = true;
            } else {
//                System.err.println("Bingo");
                more = false;
            }
        } catch (Exception e) {
            more = true;
        }
        return more;
    }

    public static String isvalidPercentage(String percentage) {

        String message = "";
        boolean isvalid = false;
        try {
            BigDecimal per = new BigDecimal(percentage);
            isvalid = true;
        } catch (Exception e) {
            isvalid = false;
            message = " is invalid";
        }
        if (isvalid) {
            try {
                BigDecimal checkVal = new BigDecimal(percentage);
                BigDecimal maxVal = new BigDecimal("100");
                BigDecimal res = maxVal.subtract(checkVal);

                if (res.signum() == -1) {
                    isvalid = false;
                    message = " should not exceed 100";
                } else {
                    isvalid = true;
                }
            } catch (Exception e) {
                isvalid = false;
            }

        }
        if (isvalid) {
            if (percentage.contains(".")) {
                String[] parts = percentage.split("[.]");
                if (parts[1].length() > 2) {
                    isvalid = false;
                    message = " cannot have more than two decimal points";
                } else {
                    isvalid = true;
                }
            }
        }

        if (isvalid) {
            System.err.println("Valid number format");
        } else {
            System.err.println(message);
        }
        return message;
    }

    public static String toCurrencyFormatForSQL(String digits) {

        String currencyF = "";
        digits = digits.replace(",", "");
        String[] part = digits.split("[.]");
        if (0 < part.length && part.length < 3) {
            if (part.length == 2 && (part[1].equals("00") || part[1].equals("0"))) {

                currencyF = part[0];

            } else if (part.length == 2 && part[1].indexOf("0") >= 0) {

                currencyF = part[0] + ".";
                char[] stringToCharArray = part[1].toCharArray();
                for (int i = 0; i < part[1].length(); i++) {

                    if (i == 1 && stringToCharArray[1] == '0') {

                    } else {
                        currencyF = currencyF + stringToCharArray[i];
                    }
                }

            } else {

                currencyF = digits;
            }
        }

        return currencyF;

    }

}
