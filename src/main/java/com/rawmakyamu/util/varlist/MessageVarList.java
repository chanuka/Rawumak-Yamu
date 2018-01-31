/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rawmakyamu.util.varlist;

/**
 * @author chanuka
 */
public class MessageVarList {

    //--------------------- common messages...................
    public static final String COMMON_ERROR_PROCESS = "error occurred while processing";
    public static final String COMMON_NOT_EXISTS = "Record does not exists";
//    public static final String COMMON_AVAILABLE = "Record is available";
    public static final String COMMON_AVAILABLE_MERCHANT = "Merchant ID not exists";
    public static final String COMMON_NOT_AVAILABLE_MERCHANT = "Merchant ID already exists";
    public static final String COMMON_AVAILABLE_TERMINAL = "Terminal ID not exists";
    public static final String COMMON_NOT_AVAILABLE_TERMINAL = "Terminal ID already exists";
    public static final String COMMON_NOT_DELETE = "Record cannot be deleted";
    public static final String COMMON_ALREADY_EXISTS = "Record already exists";
    public static final String COMMON_ALREADY_IN_USE = "Record already in use";
    public static final String COMMON_SUCC_INSERT = "created successfully";
    public static final String COMMON_SUCC_UPDATE = "updated successfully";
    public static final String COMMON_SUCC_UPLOAD = "uploaded successfully";
    public static final String COMMON_PENDING_AVAILABLE = "Request already exists for approval";
//    public static final String COMMON_MSG_AUTHORIZATION_PENDING = " : Authorization pending";

    public static final String COMMON_SUCC_REJECT = "rejected successfully";
    public static final String COMMON_SUCC_APPROVED = "approved successfully";
    public static final String COMMON_SUCC_HOLD = "status change to hold successfully";
    public static final String COMMON_SUCC_WIP = "status change to work in progress successfully";
    public static final String COMMON_ERROR_REJECT = "Error occurred while rejecting";
    public static final String COMMON_ERROR_APPROVED = "Error occurred while approving";
    public static final String COMMON_ERROR_PINRESET = "Error occurred while reseting pin";
    public static final String COMMON_ERROR_ATTEMPTRESET = "Error occurred while reseting attempt count";
    public static final String COMMON_ERROR_INSERT_PENDTASK = "Error occurred while inserting dual auth record";

    public static final String COMMON_ERROR_UPDATE = "Error occurred while updating";
    public static final String COMMON_SUCC_DELETE = "deleted successfully";

    public static final String COMMON_SUCC_CONFIRM = "Confirmed successfully";
    public static final String ALREADY_CONFIRM = "Already confirmed";
    public static final String COMMON_ERROR_DELETE = "Error occurred while deleting";
    public static final String COMMON_SUCC_ASSIGN = "assigned successfully";
    public static final String COMMON_SUCC_ACTIVATE = "Activated successfully";
    public static final String COMMON_ERROR_ACTIVATE = "Error occurred while activating";
    public static final String COMMON_WARN_CHANGE_PASSWORD = "Your password will expire ";
    public static final String COMMON_EMPTY_COMMENT = "Comment cannot be empty";

    //--------------------Login---------------//
    public static final String LOGIN_EMPTY_USERNAME = "Username or password cannot be empty";
    public static final String LOGIN_EMPTY_USERNAME_PASSWORD = "Username and password cannot be empty";
    public static final String LOGIN_EMPTY_PASSWORD = "Username or password cannot be empty";
    public static final String LOGIN_INVALID = "Your login attempt was not successful. Please try again.";
//    public static final String LOGIN_INVALID = "Invalid username or password";
    public static final String LOGIN_ERROR_LOAD = "Cannot login. Please contact administrator";
    public static final String LOGIN_ERROR_INVALID = "Invalid username or password";
    public static final String LOGIN_DEACTIVE = "User has been deactivated. Please contact administrator";
    public static final String LOGIN_DEACTIVE_ROLE = "User Role has been deactivated. Please contact administrator";
    public static final String LOGIN_INVALID_ROLE = "Invalid user role. Please contact administrator";
    public static final String PASSWORDRESET_EMPTY_PASSWORD = "Current password cannot be empty";
    public static final String PASSWORDRESET_EMPTY_NEW_PASSWORD = "New password cannot be empty";
    public static final String PASSWORDRESET_EMPTY_COM_PASSWORD = "Retype new password cannot be empty";
    public static final String PASSWORDRESET_MATCH_PASSWORD = "Passwords mismatched.";
    public static final String PASSWORDRESET_INVALID_CURR_PWD = "Current password invalid";

    // --------------------Task Management---------------//
    public static final String TASK_MGT_EMPTY_TASK_CODE = "Task code cannot be empty";
    public static final String TASK_MGT_EMPTY_DESCRIPTION = "Description cannot be empty";
    public static final String TASK_MGT_EMPTY_SORTKEY = "Sort key cannot be empty";
    public static final String TASK_MGT_EMPTY_STATUS = "Status cannot be empty";
    public static final String TASK_MGT_ERROR_SORTKEY_INVALID = "Sort key invalid";
    public static final String TASK_MGT_ERROR_DESC_INVALID = "Description invalid";
    public static final String TASK_MGT_ERROR_TASKCODE_INVALID = "Task code invalid";
    public static final String TASK_MGT_SORTKEY_ALREADY_EXSISTS = "Sort key already exists";

    //--------------------- Password policy management-------------//
    public static final String PASSPOLICY_MINLEN_INVALID = "Minimum length should be equal or greater than ";
//    public static final String PASSPOLICY_MINLEN_INVALID = "Minimum length should be greater than ";
    public static final String PASSPOLICY_MAXLEN_INVALID = "Maximum length should not exceed ";
    public static final String PASSPOLICY_MINLEN_EMPTY = "Minimum length cannot be empty";
    public static final String PASSPOLICY_MAXLEN_EMPTY = "Maximum length cannot be empty";
    public static final String PASSPOLICY_MIN_MAX_LENGHT_DIFF = "Maximum length should be greater than the minimum length";
//    public static final String PASSPOLICY_SPECCHARS_EMPTY = "Entered special characters allowed";
    public static final String PASSPOLICY_SPECCHARS_EMPTY = "Minimum special characters cannot be empty";
    public static final String PASSPOLICY_SPECCHARS_SHOULD_BE_LESS = "Minimum special characters should be less than ";
    public static final String PASSPOLICY_MINSPECCHARS_EMPTY = "Minimum special characters cannot be empty";
    public static final String PASSPOLICY_MINUPPER_EMPTY = "Minimum upper case characters cannot be empty";
    public static final String PASSPOLICY_MINNUM_EMPTY = "Minimum numerical characters cannot be empty";
    public static final String PASSPOLICY_MINLOWER_EMPTY = "Minimum lower case characters cannot be empty";
    public static final String PASSPOLICY_SUCCESS_ADD = "Password policy successfully added";
    public static final String PASSPOLICY_SUCCESS_DELETE = "Password policy successfully deleted";
    public static final String PASSPOLICY_SUCCESS_UPDATE = "Password policy successfully updated";
    public static final String PASSPOLICY_STATUS_EMPTY = "Select status";
    public static final String PASSPOLICY_POLICYID_EMPTY = "Password policy ID cannot be empty";
    public static final String PASSPOLICY_REPEATE_CHARACTERS_EMPTY = "Allowed repeat characters cannot be empty";
    public static final String PASSPOLICY_REPEATE_CHARACTERS_ZERO = "Allowed repeat characters should be greater than 0";
    public static final String PASSPOLICY_INIT_PASSWORD_EXPIRY_STATUS_EMPTY = "Initial password expiry status cannot be empty";
    public static final String PASSPOLICY_PASSWORD_EXPIRY_PERIOD_EMPTY = "Password expiry period cannot be empty";
    public static final String PASSPOLICY_NO_OF_HISTORY_PASSWORD_EMPTY = "No. of history passwords cannot be empty";
    public static final String PASSPOLICY_MIN_PASSWORD_CHANGE_PERIOD_EMPTY = "Password expiry notification period cannot be empty";
    public static final String PASSPOLICY_IDLE_ACCOUNT_EXPIRY_PERIOD_EMPTY = "Idle account expiry period cannot be empty";
    public static final String PASSPOLICY_IDLE_ACCOUNT_EXPIRY_PERIOD_INVALID = "Idle account expiry period should be 10 days or above";
    public static final String PASSPOLICY_PASSWORD_EXPIRY_PERIOD_INVALID = "Password expiry period should be 10 days or above";
    public static final String PASSPOLICY_NO_OF_INVALID_LOGIN_ATTEMPTS_EMPTY = "No. of invalid login attempts cannot be empty";
    public static final String PASSPOLICY_NO_OF_HISTORY_PASSWORD_INVALID = "No. of history passwords should be 1 or above";

    // --------------------User Role Management---------------//
    public static final String USER_ROLE_MGT_EMPTY_USER_ROLE_CODE = "User role code cannot be empty";
    public static final String USER_ROLE_MGT_EMPTY_DESCRIPTION = "Description cannot be empty";
    public static final String USER_ROLE_MGT_EMPTY_USER_ROLE_LEVEL = "User role level cannot be empty";
    public static final String USER_ROLE_MGT_EMPTY_STATUS = "Status cannot be empty";
    public static final String USER_ROLE_MGT_EMPTY_AUTHORIZER = "Authorizer cannot be empty";
    public static final String USER_ROLE_MGT_ERROR_USER_ROLE_LEVEL_INVALID = "User role level invalid";
    public static final String USER_ROLE_MGT_ERROR_DESC_INVALID = "Description invalid";
    public static final String USER_ROLE_MGT_ERROR_USER_ROLE_CODE_INVALID = "User role code invalid";

    // --------------------Section Management---------------//
    public static final String SECTION_CODE_EMPTY = "Section code cannot be empty";
    public static final String SECTION_DESC_EMPTY = "Description cannot be empty";
    public static final String SECTION_SORY_KEY_EMPTY = "Sort key cannot be empty";
    public static final String SECTION_STATUS_EMPTY = "Status should be selected";
    public static final String SECTION_CODE_ALREADY_EXISTS = "Section code already exists";
    public static final String SECTION_SORT_KEY_ALREADY_EXISTS = "Sort key already exists";

    // --------------------Customer Title Management---------------//
    public static final String CUS_TITLE_MGT_EMPTY_TT_CODE = "Customer title code cannot be empty";
    public static final String CUS_TITLE_MGT_EMPTY_DESCRIPTION = "Description cannot be empty";
    public static final String CUS_TITLE_MGT_EMPTY_STATUS = "Status cannot be empty";
    public static final String CUS_TITLE_MGT_ERROR_CUS_CODE_INVALID = "Customer title code invalid";
    public static final String CUS_TITLE_MGT_ERROR_DESC_INVALID = "Description invalid";

    // --------------------Transaction Type Management---------------//
    public static final String TXN_TYPE_MGT_EMPTY_TT_CODE = "Transaction type code cannot be empty";
    public static final String TXN_TYPE_MGT_EMPTY_DESCRIPTION = "Description cannot be empty";
    public static final String TXN_TYPE_MGT_EMPTY_SORTKEY = "Sort key cannot be empty";
    public static final String TXN_TYPE_MGT_EMPTY_STATUS = "Status cannot be empty";
    public static final String TXN_TYPE_MGT_EMPTY_OTPREQSTATUS = "OTP required status cannot be empty";
    public static final String TXN_TYPE_MGT_EMPTY_RISKREQSTATUS = "Risk required status cannot be empty";
    public static final String TXN_TYPE_MGT_EMPTY_MCTTRANXTYPE = "Merchant Transaction Type cannot be empty";
    public static final String TXN_TYPE_MGT_EMPTY_DESCRIPTION_RECIEVE = "Description receiver cannot be empty";
    public static final String TXN_TYPE_MGT_EMPTY_HISTORY_VISIBILITY = "History visibility cannot be empty";
    public static final String TXN_TYPE_MGT_ERROR_SORTKEY_INVALID = "Sort key invalid";
    public static final String TXN_TYPE_MGT_ERROR_DESC_INVALID = "Description invalid";
    public static final String TXN_TYPE_MGT_ERROR_TT_CODE_INVALID = "Transaction type code invalid";
    public static final String TXN_TYPE_MGT_ERROR_SORT_KEY_ALREADY_EXIST = "Sort key already exists";

    // --------------------Merchant Management---------------//
    public static final String GENERATE_SUCC = "generated successfully";

    public static final String MERCHANT_MGT_MERCHANT = "Merchant customer name cannot be empty";
    public static final String MERCHANT_MGT_EMPTY_MCC = "Merchant ID cannot be empty";
    public static final String MERCHANT_MGT_EMPTY_DESCRIPTION = "Description cannot be empty";
    public static final String MERCHANT_MGT_EMPTY_LATITUDE = "Latitude cannot be empty";
    public static final String MERCHANT_MGT_EMPTY_LONGITUDE = "Longitude cannot be empty";
    public static final String MERCHANT_MGT_EMPTY_ADDRESS = "Address cannot be empty";
    public static final String MERCHANT_MGT_INVALID_ADDRESS = "Address is invalid";
    public static final String MERCHANT_MGT_EMPTY_STATUS = "Status cannot be empty";
    public static final String MERCHANT_MGT_EMPTY_COMMISION = "Acquirer commision cannot be empty";
    public static final String MERCHANT_MGT_EMPTY_PROMOTION = "Acquirer promotion cannot be empty";
    public static final String MERCHANT_MGT_EMPTY_LEGAL_NAME = "Legal name cannot be empty";
    public static final String MERCHANT_MGT_EMPTY_DISCOUNT_PRIORITY = "Discount priority cannot be empty";
    public static final String MERCHANT_MGT_EMPTY_TOPUP_ACC = "Top up account cannot be empty";
    public static final String MERCHANT_MGT_EMPTY_POS_ACC = "POS account cannot be empty";
    public static final String MERCHANT_MGT_ERROR_DESC_INVALID = "Description is invalid";
    public static final String MERCHANT_MGT_ERROR_MERCHANT_CODE = "Merchant ID is invalid";
    public static final String MERCHANT_MGT_EMPTY_MERCHANT_CATEGORY_CODE = "Merchant category code cannot be empty";

//    public static final String MERCHANT_MGT_EMPTY_CHI_ACT = "Cash in Credit account type cannot be empty";
    public static final String MERCHANT_MGT_EMPTY_CHI_IEX = "Cash in credit income/expenses cannot be empty";
    public static final String MERCHANT_MGT_EMPTY_CHI_FPE = "Cash in credit flat/percentage cannot be empty";
    public static final String MERCHANT_MGT_EMPTY_CHI_AMT = "Cash in credit amount cannot be empty";
    public static final String MERCHANT_MGT_EMPTY_CHI_AMT_PERCENTAGE = "Cash in credit percentage amount should not exceed 100";
    public static final String MERCHANT_MGT_EMPTY_CHI_AMT_ISVALID = "Cash in credit percentage amount";
    public static final String MERCHANT_MGT_EMPTY_CHI_AMT_ISVALID_FLAT = "Cash in credit flat amount";
    public static final String MERCHANT_MGT_EMPTY_CHI_DEB_IEX = "Cash in debit income/expenses cannot be empty";
    public static final String MERCHANT_MGT_EMPTY_CHI_DEB_FPE = "Cash in debit flat/percentage cannot be empty";
    public static final String MERCHANT_MGT_EMPTY_CHI_DEB_AMT = "Cash in debit amount cannot be empty";
    public static final String MERCHANT_MGT_EMPTY_CHI_DEB_AMT_PERCENTAGE = "Cash in debit percentage amount should not exceed 100";
    public static final String MERCHANT_MGT_EMPTY_CHI_DEB_AMT_ISVALID = "Cash in debit percentage amount";
    public static final String MERCHANT_MGT_EMPTY_CHI_DEB_AMT_ISVALID_FLAT = "Cash in debit flat amount";

//    public static final String MERCHANT_MGT_EMPTY_CHO_ACT = "Cash out Credit account type cannot be empty";
    public static final String MERCHANT_MGT_EMPTY_CHO_IEX = "Cash out credit income/expenses cannot be empty";
    public static final String MERCHANT_MGT_EMPTY_CHO_FPE = "Cash out credit flat/percentage cannot be empty";
    public static final String MERCHANT_MGT_EMPTY_CHO_AMT = "Cash out credit amount cannot be empty";
    public static final String MERCHANT_MGT_EMPTY_CHO_AMT_PERCENTAGE = "Cash out credit percentage amount should not exceed 100";
    public static final String MERCHANT_MGT_EMPTY_CHO_AMT_ISVALID = "Cash out credit percentage amount";
    public static final String MERCHANT_MGT_EMPTY_CHO_AMT_ISVALID_FLAT = "Cash out credit flat amount";
    public static final String MERCHANT_MGT_EMPTY_CHO_DEB_IEX = "Cash out debit income/expenses cannot be empty";
    public static final String MERCHANT_MGT_EMPTY_CHO_DEB_FPE = "Cash out debit flat/percentage cannot be empty";
    public static final String MERCHANT_MGT_EMPTY_CHO_DEB_AMT = "Cash out debit amount cannot be empty";
    public static final String MERCHANT_MGT_EMPTY_CHO_DEB_AMT_PERCENTAGE = "Cash out debit percentage amount should not exceed 100";
    public static final String MERCHANT_MGT_EMPTY_CHO_DEB_AMT_ISVALID = "Cash out debit percentage amount";
    public static final String MERCHANT_MGT_EMPTY_CHO_DEB_AMT_ISVALID_FLAT = "Cash out debit flat amount";

//    public static final String MERCHANT_MGT_EMPTY_NOL_ACT = "Normal Credit account type cannot be empty";
    public static final String MERCHANT_MGT_EMPTY_NOL_IEX = "Normal credit income/expenses cannot be empty";
    public static final String MERCHANT_MGT_EMPTY_NOL_FPE = "Normal credit flat/percentage cannot be empty";
    public static final String MERCHANT_MGT_EMPTY_NOL_AMT = "Normal credit amount cannot be empty";
    public static final String MERCHANT_MGT_EMPTY_NOL_AMT_PERCENTAGE = "Normal credit percentage amount should not exceed 100";
    public static final String MERCHANT_MGT_EMPTY_NOL_AMT_ISVALID = "Normal credit percentage amount";
    public static final String MERCHANT_MGT_EMPTY_NOL_AMT_ISVALID_FLAT = "Normal credit flat amount";
    public static final String MERCHANT_MGT_EMPTY_NOL_DEB_IEX = "Normal debit income/expenses cannot be empty";
    public static final String MERCHANT_MGT_EMPTY_NOL_DEB_FPE = "Normal debit flat/percentage cannot be empty";
    public static final String MERCHANT_MGT_EMPTY_NOL_DEB_AMT = "Normal debit amount cannot be empty";
    public static final String MERCHANT_MGT_EMPTY_NOL_DEB_AMT_PERCENTAGE = "Normal debit percentage amount should not exceed 100";
    public static final String MERCHANT_MGT_EMPTY_NOL_DEB_AMT_ISVALID = "Normal debit percentage amount";
    public static final String MERCHANT_MGT_EMPTY_NOL_DEB_AMT_ISVALID_FLAT = "Normal debit flat amount";

    public static final String MERCHANT_MGT_EMPTY_MID = "Merchant id cannot be empty";
    public static final String MERCHANT_MGT_INVALID_MID = "Merchant id is invalid";
    public static final String MERCHANT_MGT_MERCHANT_NAME = "Merchant name cannot be empty";
    public static final String MERCHANT_MGT_MERCHANT_INVALID_NAME = "Merchant name is invalid";
    public static final String MERCHANT_MGT_EMPTY_LOGO = "Mobile logo cannot be empty";
    public static final String MERCHANT_MGT_EMPTY_LOGO_WEB = "Web logo cannot be empty";
    public static final String MERCHANT_MGT_EMPTY_RISK_PROFILE = "Acquirer risk profile cannot be empty";
    public static final String MERCHANT_MGT_EMPTY_ = "Risk profile cannot be empty";
    public static final String MERCHANT_MGT_EMPTY_MOBILE = "Mobile cannot be empty";
    public static final String MERCHANT_MGT_EMPTY_CURRENCY = "Currency cannot be empty,Please select one of the currency to proceed";
    public static final String MERCHANT_MGT_EMPTY_MERCANT_MCC = "Merchant category code cannot be empty,Please select one of the category code to proceed";
    public static final String MERCHANT_MGT_EMPTY_TRANSACTION = "Transaction cannot be empty,Please select one of the transaction to proceed";

    public static final String MERCHANT_MGT_INVALID_MAX_MERCHANT = "Merchant customer name maximum length should not exceed ";
    public static final String MERCHANT_MGT_INVALID_MAX_MCC = "Merchant ID maximum length should not exceed ";
    public static final String MERCHANT_MGT_MAX_LATITUDE = "Latitude maximum length should not exceed ";
    public static final String MERCHANT_MGT_INVALID_MAX_RISK_PROFILE = "Risk profile maximum length should not exceed ";
    public static final String MERCHANT_MGT_INVALID_MAX_MOBILE = "Mobile maximum length should not exceed ";
    public static final String MERCHANT_MGT_ERROR_DESC_INVALID_MAX = "Description maximum length should not exceed ";
    public static final String MERCHANT_MGT_INVALID_MAX__STATUS = "Status maximum length should not exceed ";
    public static final String MERCHANT_MGT_ADDRESS_MAX = "Address maximum length should not exceed ";
    public static final String MERCHANT_MGT_MAX_LONGITUDE = "Longitude maximum length should not exceed ";
    public static final String MERCHANT_MGT_INVALID_MAX_MERCHANT_NAME = "Merchant name maximum length should not exceed ";
    public static final String MERCHANT_MGT_INVALID_MAX_MID = "Merchant id maximum length should not exceed ";

    public static final String MERCHANT_MGT_LARGE_IMAGE = "Mobile logo is too large. File size should be less than 1MB.";
    public static final String MERCHANT_MGT_LARGE_IMAGE_WEB = "Web logo image is too large. File size should be less than 1MB.";

    public static final String MERCHANT_MGT_LARGE_WIDTH = "Image width should be less than 480 pixels.";
    public static final String MERCHANT_MGT_LARGE_HEIGHT = "Image height should be less than 800 pixels.";

    public static final String MERCHANT_MGT_INVALID_MERCHANT_NAME = "Merchant name does not exists";
    public static final String MERCHANT_MGT_INVALID_RISK_PROFILE = "Acquirer risk profile does not exists";
    public static final String MERCHANT_MGT_INVALID_STATUS = "Status does not exists";
    public static final String MERCHANT_MGT_INVALID_STATUS_EXCEED = "Status does not exists";
    public static final String MERCHANT_MGT_INVALID_MOBILE = "Mobile is invalid";
    public static final String MERCHANT_MGT_INVALID_CONTACT1 = "Contact number 1 is invalid";
    public static final String MERCHANT_MGT_INVALID_CONTACT2 = "Contact number 2 is invalid";
    public static final String MERCHANT_MGT_INVALID_EMAIL1 = "Email 1 is invalid";
    public static final String MERCHANT_MGT_INVALID_EMAIL2 = "Email 2 is invalid";
    public static final String MERCHANT_MGT_EMPTY_ACC_TYPE = "Account type cannot be empty";
    public static final String MERCHANT_MGT_EMPTY_ACC_NUMBER = "Account number cannot be empty";
    public static final String MERCHANT_MGT_EMPTY_PAYMENT_TYPE = "Payment type cannot be empty";
    public static final String MERCHANT_MGT_EMPTY_BANK = "Bank name cannot be empty";
    public static final String MERCHANT_MGT_EMPTY_BRANCH = "Branch name cannot be empty";
    public static final String MERCHANT_MGT_EMPTY_BRANCH_CODE = "Branch code cannot be empty";

    // --------------------Merchant Customer Management---------------//
    public static final String MERCHANT_CUS_MGT_EMPTY_MID = "Merchant customer code cannot be empty";
    public static final String MERCHANT_CUS_MGT_INVALIDMID = "Merchant customer code is invalid";
    public static final String MERCHANT_CUS_MGT_MERCHANT_NAME = "Merchant customer name cannot be empty";
    public static final String MERCHANT_CUS_MGT_MERCHANT_INVALID_NAME = "Merchant customer name is invalid";
    public static final String MERCHANT_CUS_MGT_MERCHANT_STATUS = "Status cannot be empty";
    public static final String MERCHANT_CUS_MGT_INVALID_MAX_MID = "Merchant customer code maximum length should not exceed ";
    public static final String MERCHANT_CUS_MGT_INVALID_MAX_MERCHANT_NAME = "Merchant customer name maximum length should not exceed ";
    public static final String MERCHANT_CUS_MGT_GENERATE_USER_PASS_EMPTY_MERCHANT_NAME = "Merchant customer name maximum length should not exceed ";

// --------------------Page Management---------------//
    public static final String PAGE_MGT_EMPTY_PAGE_CODE = "Page code cannot be empty";
    public static final String PAGE_MGT_EMPTY_DESCRIPTION = "Description cannot be empty";
    public static final String PAGE_MGT_EMPTY_SORTKEY = "Sort key cannot be empty";
    public static final String PAGE_MGT_EMPTY_STATUS = "Status cannot be empty";
    public static final String PAGE_MGT_EMPTY_URL = "URL cannot be empty";
    public static final String PAGE_MGT_ERROR_SORTKEY_INVALID = "Sort key invalid";
    public static final String PAGE_MGT_ERROR_DESC_INVALID = "Description invalid";
    public static final String PAGE_MGT_ERROR_URL_INVALID = "URL invalid";
    public static final String PAGE_MGT_ERROR_PAGE_CODE_INVALID = "Page code invalid";
    public static final String PAGE_MGT_ERROR_SORT_KEY_ALREADY_EXSITS = "Sort key already exists";

    //------------------- System User Management----------//
    public static final String SYSUSER_MGT_EMPTY_USERNAME = "Username cannot be empty";
    public static final String SYSUSER_MGT_EMPTY_PASSWORD = "Password cannot be empty";
    public static final String SYSUSER_MGT_PASSWORD_MISSMATCH = "Confirm password mismatch with the password";
    public static final String SYSUSER_MGT_EMPTY_USERROLE = "User role cannot be empty";
    public static final String SYSUSER_MGT_EMPTY_DUALAUTHUSER = "Dual auth user cannot be empty";
    public static final String SYSUSER_MGT_EMPTY_STATUS = "Status cannot be empty";
    public static final String SYSUSER_MGT_EMPTY_SERVICEID = "Service ID cannot be empty";
    public static final String SYSUSER_MGT_EMPTY_EXPIRYDATE = "Password expiry date cannot be empty";
    public static final String SYSUSER_MGT_EMPTY_DATEOFBIRTH = "Date of birth cannot be empty";
    public static final String SYSUSER_MGT_EMPTY_FULLNAME = "Full name cannot be empty";
    public static final String SYSUSER_MGT_EMPTY_ADDRESS1 = "Address cannot be empty";
    public static final String SYSUSER_MGT_EMPTY_ADDRESS2 = "Address2 cannot be empty";
    public static final String SYSUSER_MGT_EMPTY_CITY = "City cannot be empty";
    public static final String SYSUSER_MGT_EMPTY_COANTACTNO = "Contact number cannot be empty";
    public static final String SYSUSER_MGT_EMPTY_FAX = "Fax cannot be empty";
    public static final String SYSUSER_MGT_EMPTY_EMAIL = "Email cannot be empty";
    public static final String SYSUSER_MGT_EMPTY_NIC = "NIC cannot be empty";
    public static final String SYSUSER_MGT_INVALID_EMAIL = "Invalid email";
    public static final String SYSUSER_MGT_INVALID_NIC = "Invalid NIC";
    public static final String SYSUSER_MGT_INVALID_NIC_DOB = "Date of birth does not match with NIC";
    public static final String SYSUSER_MGT_INVALID_CONTACT_NO = "Invalid contact number ";
    public static final String SYSUSER_PASSWORD_TOO_SHORT = "Password is shorter than the expected minimum length ";
    public static final String SYSUSER_PASSWORD_TOO_LARGE = "Password is longer than the expected maximum length ";
    public static final String SYSUSER_PASSWORD_LESS_LOWER_CASE_CHARACTERS = "Lower case characters are less than required ";
    public static final String SYSUSER_PASSWORD_LESS_UPPER_CASE_CHARACTERS = "Upper case characters are less than required ";
    public static final String SYSUSER_PASSWORD_MORE_CHAR_REPEAT = "Password contains characters repeating more than allowed ";
    public static final String SYSUSER_PASSWORD_LESS_NUMERICAL_CHARACTERS = "Numerical characters are less than required ";
    public static final String SYSUSER_PASSWORD_LESS_SPACIAL_CHARACTERS = "Special characters are less than required ";
    public static final String SYSUSER_PASSWORD_MISSMATCH = "Passwords mismatched ";
    public static final String RESET_PASSWORD_SUCCESS = "Password reset successful ";
    public static final String SYSUSER_DEL_INVALID = " is already Logged-In, cannot be deleted! ";
    public static final String RESET_PASS_NEW_EXIST = "New password already exists in history ";
    public static final String RESET_PASS_CURRENT_EXIST = "Current password already exists in history ";
    public static final String RESET_PASS_SAME_NEW_CURRENT = "New password and current password cannot be the same ";
    public static final String SYSUSER_MGT_INVALID_USER_ROLE = "Task(s) are not assigned to this user role";

    // --------------------User Role Privilege Management---------------//
    public static final String USER_ROLE_PRI_EMPTY_USER_ROLE = "User role cannot be empty";
    public static final String USER_ROLE_PRI_EMPTY_CATAGARY = "Please select one of the categories to proceed";
    public static final String USER_ROLE_PRI_INVALID_CATAGARY = "Categories should be sections or pages or operations";
    public static final String USER_ROLE_PRI_EMPTY_SECTION = "Section cannot be empty";
    public static final String USER_ROLE_PRI_EMPTY_PAGE = "Page cannot be empty";
    public static final String USER_ROLE_PRI_SEC_DEPEND = "Cannot delete the section because pages are already assigned to it";
    public static final String USER_ROLE_PRI_PAGE_DEPEND = "Cannot delete the page because tasks are already assigned to it";
    public static final String USER_ROLE_PRI_PAGE_TYPE_DEPEND = "Cannot assign page(s) under same category";
    public static final String USER_ROLE_PRI_PAGE_DEPEND_PENDINGTASK = "Cannot delete the page because pending tasks are in the queue";

    //----------------------UtilityProvider---------------------------//
    public static final String UTILITY_PRORVIDE_MGT_EMPTY_PROVIDERCODE = "Provider code cannot be empty";
    public static final String UTILITY_PRORVIDE_MGT_ERROR_PROVIDERCODE_EXISTS = "Provider code already exists";
    public static final String UTILITY_PRORVIDE_MGT_EMPTY_DESCRIPTION = "Description cannot be empty";
    public static final String UTILITY_PRORVIDE_MGT_EMPTY_STATUS = "Status cannot be empty";
    public static final String UTILITY_PRORVIDE_MGT_EMPTY_UTILITYTYPE = "Utility type cannot be empty";
    public static final String UTILITY_PRORVIDE_MGT_EMPTY_REFSAMPLE = "Reference sample cannot be empty";
    public static final String UTILITY_PRORVIDE_MGT_EMPTY_REFPATTERN = "Reference pattern cannot be empty";
    public static final String UTILITY_PRORVIDE_MGT_EMPTY_ACCOUNTTYPE = "Account type cannot be empty";
    public static final String UTILITY_PRORVIDE_MGT_EMPTY_COLLECTIONACCOUNT = "Collection account cannot be empty";
    public static final String UTILITY_PRORVIDE_MGT_ERROR_PROVIDERCODE_INVALID = "Provider code invalid";
    public static final String UTILITY_PRORVIDE_MGT_ERROR_DESC_INVALID = "Description invalid";

    //-----------------------------------Wam configuration-----------------------------
    public static final String WAM_CONFIG_EMPTY_ID = "ID cannot be empty";
    public static final String WAM_CONFIG_EMPTY_MAX_POOL = "Max pool key cannot be empty";
    public static final String WAM_CONFIG_EMPTY_MIN_POOL = "Min pool cannot be empty";
    public static final String WAM_CONFIG_INVALID_MAX_MIN_POOL = "Min pool should be less than max pool";
    public static final String WAM_CONFIG_EMPTY_MAX_POOL_QUEUE = "Max pool queue cannot be empty";
    public static final String WAM_CONFIG_EMPTY_SERVICE_PORT = "Service port cannot be empty";
    public static final String WAM_CONFIG_EMPTY_SERVICE_READ_TIMEOUT = "Service read timeout cannot be empty";
    public static final String WAM_CONFIG_EMPTY_LOG_LEVEL = "Log level cannot be empty";
    public static final String WAM_CONFIG_EMPTY_LOG_BACKUP_PATH = "Log backup path cannot be empty";
    public static final String WAM_CONFIG_EMPTY_LOG_BACKUP_STATUS = "Log backup status cannot be empty";
    public static final String WAM_CONFIG_EMPTY_NOOF_LOG_FILE = "No. of log files cannot be empty";

    public static final String WAM_CONFIG_INVALID_ID = "ID is invalid";
    public static final String WAM_CONFIG_INVALID_MAX_POOL = "Max pool is invalid";
    public static final String WAM_CONFIG_INVALID_MIN_POOL = "Min pool is invalid";
    public static final String WAM_CONFIG_INVALID_MAX_POOL_QUEUE = "Max pool queue is invalid";
    public static final String WAM_CONFIG_INVALID_SERVICE_PORT = "Service port is invalid";
    public static final String WAM_CONFIG_INVALID_LOG_FILE = "Log level is invalid";
    public static final String WAM_CONFIG_INVALID_LOG_BACKUP_PATH = "Log backup path is invalid";
    public static final String WAM_CONFIG_SYNTAX_ERROR = "Syntax error found in a regular-expression pattern";
    public static final String WAM_CONFIG_URL_ERROR = "Error found in URL validation";

    //------------------Common File Path Management---------------//
    public static final String COMMON_FILE_PATH_SYNTAX_ERROR = "Syntax error found in a regular-expression pattern";
    public static final String COMMON_FILE_PATH_URL_ERROR = "Error found in URL validation";

    // --------------------Channel Configuation---------------//
    public static final String CHANNEL_CONFIGURATION_EMPTY_ID = "ID cannot be empty";
    public static final String CHANNEL_CONFIGURATION_EMPTY_NAME = "Name cannot be empty";
    public static final String CHANNEL_CONFIGURATION_EMPTY_IP = "IP cannot be empty";
    public static final String CHANNEL_CONFIGURATION_EMPTY_PORT = "Port cannot be empty";
    public static final String CHANNEL_CONFIGURATION_EMPTY_TYPE = "Type cannot be empty";
    public static final String CHANNEL_CONFIGURATION_EMPTY_NII = "NII cannot be empty";
    public static final String CHANNEL_CONFIGURATION_EMPTY_HEADER_SIZE = "Header size cannot be empty";
    public static final String CHANNEL_CONFIGURATION_EMPTY_READ_TIMEOUT = "Real timeout cannot be empty";
    public static final String CHANNEL_CONFIGURATION_EMPTY_CON_TIMEOUT = "Connection timeout cannot be empty";
    public static final String CHANNEL_CONFIGURATION_EMPTY_STATUS = "Status cannot be empty";
    public static final String CHANNEL_CONFIGURATION_INVALID_NAME = "Name is invalid";
    public static final String CHANNEL_CONFIGURATION_INVALID_IP = "Invalid IP address";
    public static final String CHANNEL_CONFIGURATION_INVALID_PORT = "Port is invalid";
    public static final String CHANNEL_CONFIGURATION_INVALID_TYPE = "Type is invalid";
    public static final String CHANNEL_CONFIGURATION_INVALID_NII = "NII is invalid";
    public static final String CHANNEL_CONFIGURATION_INVALID_HEADER_SIZE = "Header size is invalid";
    public static final String CHANNEL_CONFIGURATION_INVALID_READ_TIMEOUT = "Real timeout size is invalid";
    public static final String CHANNEL_CONFIGURATION_INVALID_CON_TIMEOUT = "Con timeout size is invalid";
    public static final String CHANNEL_CONFIGURATION_SYNTAX_ERROR = "Syntax error found in a regular-expression pattern";

    //------------------------listner configuration------------------------
    public static final String LISTNER_CONFIGUATION_EMPTY_BACKLOG_SIZE = "Backlog size cannot be empty";
    public static final String LISTNER_CONFIGUATION_INVALID_BACKLOG_SIZE = "Backlog size is invalid";
    public static final String LISTNER_CONFIGUATION_EMPTY_PREFIX = "Prefix cannot be empty";
    public static final String LISTNER_CONFIGUATION_INVALID_PREFIX = "Prefix is invalid";
    public static final String LISTNER_CONFIGUATION_EMPTY_NAME = "Name cannot be empty";
    public static final String LISTNER_CONFIGUATION_INVALID_NAME = "Name is invalid";
    public static final String LISTNER_CONFIGUATION_EMPTY_PORT = "Port cannot be empty";
    public static final String LISTNER_CONFIGUATION_INVALID_PORT = "Port is invalid";
    public static final String LISTNER_CONFIGUATION_EMPTY_READ_TIMEOUT = "Read timeout cannot be empty";
    public static final String LISTNER_CONFIGUATION_INVALID_READ_TIMEOUT = "Read timeout is invalid";
    public static final String LISTNER_CONFIGUATION_EMPTY_UID = "UID cannot be empty";
    public static final String LISTNER_CONFIGUATION_INVALID_UID = "UID is invalid";
    public static final String LISTNER_CONFIGUATION_EMPTY_TYPE = "Type cannot be empty";
    public static final String LISTNER_CONFIGUATION_EMPTY_HEADER_SIZE = "Header size cannot be empty";
    public static final String LISTNER_CONFIGUATION_EMPTY_STATUS = "Status cannot be empty";

    //-------------------- service configuation --------------------------------
    public static final String SERVICE_CONFIGUATION_EMPTY_ID = "ID cannot be empty";
    public static final String SERVICE_CONFIGUATION_EMPTY_NAME = "Name cannot be empty";
    public static final String SERVICE_CONFIGUATION_EMPTY_URL = "URL cannot be empty";
    public static final String SERVICE_CONFIGUATION_EMPTY_TIMEOUT = "Timeout cannot be empty";
    public static final String SERVICE_CONFIGUATION_EMPTY_REQ_METHOD = "Request method cannot be empty";
    public static final String SERVICE_CONFIGUATION_EMPTY_REQ_PRO_KEY = "Request property key cannot be empty";
    public static final String SERVICE_CONFIGUATION_EMPTY_REQ_PRO_VALUE = "Request property value cannot be empty";
    public static final String SERVICE_CONFIGUATION_EMPTY_STATUS = "Status cannot be empty";
    public static final String SERVICE_CONFIGUATION_INVALID_URL = "URL is invalid";
    public static final String SERVICE_CONFIGUATION_SYNTAX_ERROR = "Syntax error found in a regular-expression pattern";

    public static final String FILE_UPLOAD_SUCCESS = "File has been successfully uploaded";
    public static final String FILE_UPLOAD_ERROR = "An error occurred while uploading the file";
    public static final String FILE_UPLOAD_EMPTY_ONLY_PDF = "No file has been uploaded / uploaded file type is not a PDF";

    //-------------------- atm location --------------------------------
    public static final String ATM_LOCATIONS_EMPTY_ATM_CODE = "Location code cannot be empty";
    public static final String ATM_LOCATIONS_INVALID_ATM_CODE_SPECIAL = "Location code is invalid";
    public static final String ATM_LOCATIONS_INVALID_ATM_CODE = "Location code maximum length should not exceed ";
    public static final String ATM_LOCATIONS_EMPTY_DESCRIPTION = "Location name cannot be empty";
    public static final String ATM_LOCATIONS_INVALID_DESCRIPTION_SPECIAL = "Location name is invalid";
    public static final String ATM_LOCATIONS_INVALID_DESCRIPTION = "Location name maximum length should not exceed ";
    public static final String ATM_LOCATIONS_EMPTY_ADDRESS = "Address cannot be empty";
    public static final String ATM_LOCATIONS_INVALID_ADDRESS_SCPECIAL = "Address is invalid";
    public static final String ATM_LOCATIONS_INVALID_ADDRESS = "Address maximum length should not exceed ";
    public static final String ATM_LOCATIONS_EMPTY_ATM_LONGITUDE = "Longitude cannot be empty";
    public static final String ATM_LOCATIONS_INVALID_MAX_ATM_LONGITUDE = "Longitude maximum length should not exceed ";
    public static final String ATM_LOCATIONS_EMPTY_ATM_STATUS = "Status cannot be empty";
    public static final String ATM_LOCATIONS_EMPTY_ATM_STATUS_INVALID = "Status maximum length should not exceed";
    public static final String ATM_LOCATIONS_EMPTY_ATM_LATITUDE = "Latitude cannot be empty";
    public static final String ATM_LOCATIONS_INVALID_MAX_ATM_LATITUDE = "Latitude maximum length should not exceed ";
    public static final String ATM_LOCATIONS_INVALID_ATM_LATITUDE = "Latitude is invalid";
    public static final String ATM_LOCATIONS_INVALID_ATM_LONGITUDE = "Longitude is invalid";
    public static final String ATM_LOCATIONS_INVALID_ATM_NAME = "Name is invalid";
    public static final String ATM_LOCATIONS_INVALID_ADDRESS_NAME = "Address is invalid";
    public static final String ATM_LOCATIONS_INVALID_TYPE_ATM = "ATM type does not exists";
    public static final String ATM_LOCATIONS_INVALID_TYPE_CDM = "CDM type does not exists";
    public static final String ATM_LOCATIONS_INVALID_TYPE_BRANCH = "Branch type does not exists";
    public static final String ATM_LOCATIONS_INVALID_TYPE_AGENT = "Agent type does not exists";
    public static final String ATM_LOCATIONS_INVALID_TYPE_SHOP = "Shop type does not exists";
    public static final String ATM_LOCATIONS_EMPTY_ATM_TYPE = "At least one or more of the \"ATM/CDM/Branch/Agent/Shop\" should be selected";
    public static final String ATM_LOCATIONS_EMPTY_ATM_TYPE_CSV = "At least one or more of the \"ATM/CDM/Branch/Agent/Shop\" should be \"YES\" ";

    //-------------------- Card Product --------------------------------
    public static final String CARD_PRODUCT_EMPTY_PRODUCT_CODE = "Product code cannot be empty";
    public static final String CARD_PRODUCT_EMPTY_DESCRIPTION = "Description cannot be empty";
    public static final String CARD_PRODUCT_EMPTY_CARD_TYPE = "Card type cannot be empty";
    public static final String CARD_PRODUCT_EMPTY_CARD_BRAND = "Card brand cannot be empty";
    public static final String CARD_PRODUCT_EMPTY_DISPLAY_NAME = "Display name cannot be empty";
    public static final String CARD_PRODUCT_EMPTY_INVALIDDIS_NAME = "Display name should contains [CARD]";
    public static final String CARD_PRODUCT_EMPTY_IMAGE = "Image cannot be empty";

    public static final String CARD_PRODUCT_LARGE_IMAGE = "Image is too large. File size should be less than 650kb.";
    public static final String CARD_PRODUCT_LARGE_WIDTH = "Image width should be less than 450 pixels.";
    public static final String CARD_PRODUCT_SMALL_WIDTH = "Image width should be more than 445 pixels.";
    public static final String CARD_PRODUCT_LARGE_HEIGHT = "Image height should be less than 290 pixels.";
    public static final String CARD_PRODUCT_SMALL_HEIGHT = "Image height should be more than 280 pixels.";

    // --------------------Promotion Management---------------//
    public static final String PROMOTION_ID_EMPTY = "Campaign ID cannot be empty";
    public static final String PROMOTION_DESC_EMPTY = "Description cannot be empty";
    public static final String PROMOTION_TITLE_EMPTY = "Title cannot be empty";
    public static final String PROMOTION_STATUS_EMPTY = "Status should be selected";
    public static final String PROMOTION_TAB_IMAGE_EMPTY = "Tab image cannot be empty";
    public static final String PROMOTION_MOBILE_IMAGE_EMPTY = "Mobile image cannot be empty";
    public static final String PROMOTION_START_DATE_EMPTY = "From date cannot be empty";
    public static final String PROMOTION_EXP_DATE_EMPTY = "To date cannot be empty";
    public static final String PROMOTION_SORT_ORDER_EMPTY = "Sort order should be selected";
    public static final String PROMOTION_USER_LEVEL_EMPTY = "User level should be selected";

    // --------------------Customer Search---------------//
    public static final String CUS_SEARCH_SUCC_PINCHANGE = "PIN changed successfully";
    public static final String CUS_SEARCH_ERROR_PINCHANGE = "Error occurred while changing pin";
    public static final String CUS_SEARCH_EMPTY_WALLETID = "Empty Wallet ID";
    public static final String CUS_SEARCH_SUCC_UPDATE_FATCA = "FATCA status changed successfully";

    // --------------------Wallet Management---------------//
    public static final String WALLET_MGT_CUS_SUCC_UPDATE = "Customer status changed successfully";
    public static final String WALLET_MGT_DIV_SUCC_UPDATE = "Device status changed successfully";
    public static final String WALLET_MGT_DIV_PIN_SUCC_RESET = "device PIN reset successfully";
    public static final String WALLET_MGT_DIV_ATTEMPTS_SUCC_RESET = "Device attempt reset successfully";
    public static final String WALLET_MGT_STATUS_SELECT = "status should be selected.";
    public static final String WALLET_MGT_WALLETID_EMPTY = "Wallet ID cannot be empty";
    public static final String WALLET_MGT_PHONENO_EMPTY = "Phone no cannot be empty";
    public static final String WALLET_MGT_STATUS_EMPTY = "Status cannot be empty";
    public static final String WALLET_VIP_STATUS_EMPTY = "status cannot be empty";
    public static final String WALLET_MGT_INVALID_PHONE_NO = "Phone No. does not exist";
    public static final String WALLET_MGT_INVALID_WALLET_ID = "Wallet ID does not exist";
    public static final String WALLET_MGT_INVALID_CONTACT_NO = "Mobile is invalid";
    public static final String WALLET_MGT_SUCC_ACTIVATE_VIP = "VIP activated successfully";
    public static final String WALLET_MGT_SUCC_DEACTIVATE_VIP = "VIP deactivated successfully";
    public static final String WALLET_MGT_ERROR_ACTIVATE_VIP = "Error occurred while activating VIP";
    public static final String WALLET_MGT_ERROR_DEACTIVATE_VIP = "Error occurred while deactivating VIP";
    public static final String WALLET_MGT_EMPTY_WALLETID = "Empty Wallet ID";
    public static final String WALLET_MGT_INVALID_WALLET_ID_CURRENT_SERIES = "Wallet ID should start ";
    public static final String WALLET_MGT_INVALID_LENGTH_WALLET_ID = "Wallet ID length should be 10";
    public static final String WALLET_RISK_PROFILE_EMPTY = "risk profile cannot be empty";
    public static final String WALLET_MGT_CUS_SUCC_UPDATE_RISK_PROFILE = "Customer risk profile changed successfully";
    public static final String WALLET_COMMENT_EMPTY = "Comment cannot be empty";
    public static final String WALLET_CUSTOMER_COMMENT_EMPTY = "Comment cannot be empty";
    public static final String WALLET_KYC_STATUS_EMPTY = "KYC status cannot be empty";
    public static final String WALLET_KYC_EFTC_CLAUSE_EMPTY = "Please acknowledge the EFTC declaration to proceed";
    public static final String WALLET_MGT_SUCC_RESET_ATTEMPT_COUNT = "Attempt count reset successfully";
    public static final String WALLET_MGT_EMPTY_IDENTITYCOPY_IMG = "Customer cannot approve without identity copy image";
    public static final String WALLET_MGT_EMPTY_IDENTITYCOPY_BACK_IMG = "Customer cannot approve without identity copy back image";
    public static final String WALLET_MGT_EMPTY_SELFIE_IMG = "Customer cannot approve without the user image";
    public static final String WALLET_MGT_EMPTY_DAILY_LIMIT = "Daily limit cannot be empty";
    public static final String WALLET_MGT_EMPTY_DAILY_LIMIT_TEMP = "Temporary daily limit cannot be empty";
    public static final String WALLET_MGT_EMPTY_TXN_MAX_LIMIT = "Transaction max limit cannot be empty";
    public static final String WALLET_MGT_EMPTY_TXN_MAX_LIMIT_TEMP = "Temporary transaction max limit cannot be empty";
    public static final String WALLET_MGT_EMPTY_TOPUP_LIMIT = "Top up limit cannot be empty";
    public static final String WALLET_MGT_EMPTY_WITDRAWAL_LIMIT = "Wthdrawal limit cannot be empty";
    public static final String WALLET_MGT_EMPTY_TOKEN_LIMIT = "Token limit cannot be empty";
    public static final String WALLET_MGT_SUCC_UPDATE_LIMIT = "Limits updated successfully";
    public static final String WALLET_MGT_EMPTY_TRANSACTION_TYPE = "Transaction type cannot be empty";
    public static final String WALLET_MGT_EMPTY_TXN_LIMIT = "Transaction limit cannot be empty";
    public static final String WALLET_MGT_EMPTY_TXN_LIMIT_FROMDATE = "Transaction limit \"From Date\" cannot be empty";
    public static final String WALLET_MGT_EMPTY_TXN_LIMIT_TODATE = "Transaction limit \"To Date\" cannot be empty";
    public static final String WALLET_MGT_TXN_LIMITS_EMPTY = "Please assign wallet management transactions";
    public static final String WALLET_MGT_INVALID_TXN_LIMIT_VS_MAXLIMIT = "Transaction limit should be less than transaction max limit";
    public static final String WALLET_MGT_INVALID_TXN_LIMIT_VS_GLOBALLIMIT = "Transaction limit should be less than global transaction limit";
    public static final String WALLET_MGT_INVALID_TXN_LIMIT_VS_TEMP_TXNMAX_LIMIT = "Transaction limit should be less than temporary transaction max limit ";
    public static final String WALLET_MGT_INVALID_TXN_LIMIT_VS_TEMP_DAILY_LIMIT = "Transaction limit should be less than temporary daily limit ";
    public static final String WALLET_MGT_INVALID_TXN_LIMIT_VS_TEMP_TXNMAX_LIMIT_LC = "transaction limit should be less than temporary transaction max limit ";
    public static final String WALLET_MGT_INVALID_TXN_LIMIT_VS_TEMP_DAILY_LIMIT_LC = "transaction limit should be less than temporary daily limit ";
    public static final String WALLET_MGT_INVALID_TXN_MAX_LIMIT_PERMANENT = "Permanent transaction max limit should be less than permanent daily limit ";
    public static final String WALLET_MGT_INVALID_TXN_MAX_LIMIT_TEMP = "Temporary transaction max limit should be less than temporary daily limit";
    public static final String WALLET_MGT_INVALID_TXN_LIMIT_LOWERCASE = "transaction limit should be less than transaction max limit";
    public static final String WALLET_MGT_EMPTY_DAILY_LIMIT_FROMDATE = "Temporary daily limit \"From Date\" cannot be empty";
    public static final String WALLET_MGT_EMPTY_DAILY_LIMIT_TODATE = "Temporary daily limit \"To Date\" cannot be empty";
    public static final String WALLET_MGT_EMPTY_TXN_MAX_LIMIT_FROMDATE = "Temporary transaction max limit \"From Date\" cannot be empty";
    public static final String WALLET_MGT_EMPTY_TXN_MAX_LIMIT_TODATE = "Temporary transaction max limit \"To Date\" cannot be empty";
    public static final String WALLET_MGT_INVALID_DAILY_LIMIT_DATERANGE = "Temporary daily limit date range invalid";
    public static final String WALLET_MGT_INVALID_TXN_MAX_LIMIT_DATERANGE = "Temporary transaction max limit date range invalid";
    public static final String WALLET_MGT_INVALID_TRANSACTION_DATERANGE = "Transaction date range invalid";
    public static final String WALLET_MGT_INVALID_TXN_MAX_LIMIT = "Transaction max limit should be less than daily limit";
    public static final String WALLET_MGT_INVALID_DAILY_LIMIT_VALUE = "Daily limit invalid";
    public static final String WALLET_MGT_INVALID_TXN_LIMIT_VALUE = "Transaction limit invalid";
    public static final String WALLET_MGT_INVALID_TXN_MAX_LIMIT_VALUE = "Transaction max limit invalid";
    public static final String WALLET_MGT_INVALID_DAILY_LIMIT_VALUE_AND_TXN_MAX_LIMIT_VALUE = "Daily limit and transaction max limit invalid";
    public static final String WALLET_MGT_EMPTY_LIMIT_TYPE_CSV = "Limit type cannot be empty";
    public static final String WALLET_MGT_EMPTY_LIMIT_CSV = "Limit cannot be empty";
    public static final String WALLET_MGT_EMPTY_FROMDATE_CSV = "From date cannot be empty";
    public static final String WALLET_MGT_EMPTY_TODATE_CSV = "To date cannot be empty";
    public static final String WALLET_MGT_INVALID_DAILY_LIMIT_TEMP_CSV = "Temporary daily limit should be greater than temporary transaction max limit";
    public static final String WALLET_MGT_INVALID_TRANSACTION_TYPECODE = "Transaction type code invalid";
    public static final String WALLET_MGT_INVALID_FROMDATE_PATTERN = "From Date should be the patten YYYY-MM-DD";
    public static final String WALLET_MGT_INVALID_TODATE_PATTERN = "To Date should be the patten YYYY-MM-DD";
    public static final String WALLET_MGT_TRANSACTION_TYPE_EXISTS = "Transaction type already exists";

    //-------------------- Terminal ORI --------------------------------
    public static final String TERMINAL_ORI_EMPTY_ID = "Terminal ID cannot be empty";
    public static final String TERMINAL_ORI_ID_LENGTH = "Terminal ID length should be ";
    public static final String TERMINAL_ORI_EMPTY_TERMINAL_NAME = "Terminal name cannot be empty";
    public static final String TERMINAL_ORI_EMPTY_SERIALNO = "Serial number cannot be empty";
    public static final String TERMINAL_ORI_EMPTY_MANUFACTURER = "Manufacturer cannot be empty";
    public static final String TERMINAL_ORI_EMPTY_MODEL = "Model cannot be empty";
    public static final String TERMINAL_ORI_EMPTY_TERMINAL_STATUS = "Terminal status cannot be empty";
    public static final String TERMINAL_ORI_EMPTY_STATUS = "Status cannot be empty";
    public static final String TERMINAL_ORI_ACT_DEACT_TO_INITIAL = "Status cannot update to initial";
    public static final String TERMINAL_ORI_INITIAL_OTHER = "Initial status cannot update";

    public static final String TERMINAL_ORI_EMPTY_MOBILE = "Mobile cannot be empty";
    public static final String TERMINAL_ORI_INVALID_MOBILE = "Mobile is invalid";
    public static final String TERMINAL_ORI_EMPTY_TERMINAL_CATE = "Terminal category cannot be empty";
    public static final String TERMINAL_ORI_EMPTY_MID = "Merchant ID cannot be empty";
    public static final String TERMINAL_ORI_EMPTY_CURRENCY = "Currency cannot be empty";
    public static final String TERMINAL_ORI_EMPTY_DATE_INSTALLED = "Date installed cannot be empty";

    public static final String TERMINAL_ORI_EMPTY_MCC = "Merchant category cannot be empty";
    public static final String TERMINAL_ORI_EMPTY_RISKPROFILE = "Acquirer risk Profile cannot be empty";
    public static final String TERMINAL_ORI_INVALID_ASSIGN = "Terminal ORI must add before assign transactions";
    public static final String TERMINAL_ORI_EMPTY_AMEX_STATUS = "Amex status cannot be empty";

    // --------------------Acquirer Risk Management---------------//
    public static final String ACQUIRER_MGT_EMPTY_PROFILE_CODE = "Risk profile code cannot be empty";
    public static final String ACQUIRER_MGT_EMPTY_PROFILE_TYPE = "Risk profile type cannot be empty";
    public static final String ACQUIRER_MGT_EMPTY_DESCRIPTION = "Description cannot be empty";
    public static final String ACQUIRER_MGT_EMPTY_STATUS = "Status cannot be empty";
    public static final String ACQUIRER_MGT_EMPTY_DEFAULT_STATUS = "Default status cannot be empty";
    public static final String ACQUIRER_RISK_PROFILE_TXN_TYPE = "Transaction type cannot be empty";
    public static final String ACQUIRER_RISK_PROFILE_TXN_DAILY_COUNT = "Daily count cannot be empty";
    public static final String ACQUIRER_RISK_PROFILE_TXN_DAILY_AMOUNT = "Daily amount cannot be empty";
    public static final String ACQUIRER_RISK_PROFILE_TXN_DAILY_AMOUNT_INVALID = "Daily amount is invalid";
    public static final String ACQUIRER_RISK_PROFILE_TXN_MAX_AMOUNT = "Max amount cannot be empty";
    public static final String ACQUIRER_RISK_PROFILE_TXN_MAX_AMOUNT_INVALID = "Max amount is invalid";
    public static final String ACQUIRER_RISK_PROFILE_TXN_MIN_AMOUNT = "Min amount cannot be empty";
    public static final String ACQUIRER_RISK_PROFILE_TXN_MIN_AMOUNT_INVALID = "Min amount is invalid";
    public static final String ACQUIRER_MGT_ERROR_DESC_INVALID = "Description invalid";
    public static final String ACQUIRER_MGT_ERROR_PROFILE_CODE_INVALID = "Risk profile code invalid";
    public static final String ACQUIRER_TXN_RISK_PROFILE_TXN_EMPTY = "Please assign acquirer risk profile transactions";
    public static final String ACQUIRER_RISK_PROFILE_INVALID_MIN_MAX_TRANSACTION_AMOUNT = "Min amount cannot be greater than max amount";
    public static final String ACQUIRER_RISK_PROFILE_DEFAULT_ATLEAST_STATUS_YES = "At least one record should have default status \"yes\"";
    public static final String ACQUIRER_RISK_PROFILE_DEFAULT_STATUS_YES_AND_STATUS_DEACTIVE = "Inactive risk profile cannot be added with the dafault status \"yes\"";

    // --------------------Utility Provider---------------//
    public static final String UTILITY_PROVIDER_MGT_IS_MANDATORY_REF = "Mandatory fields cannot be empty";
    public static final String UTILITY_PROVIDER_MGT_EMPTY_REF_MAX_NUMBER = "Maximum Reference Number Length cannot be empty";
    public static final String UTILITY_PROVIDER_MGT_EMPTY_REF_LABEL = "Reference Number Label cannot be empty";
    public static final String UTILITY_PROVIDER_MGT_EMPTY_REF_PATTERN = "Reference pattern cannot be empty";
    public static final String UTILITY_PROVIDER_MGT_EMPTY_REF_SAMPLE = "Reference sample cannot be empty";
    public static final String UTILITY_PROVIDER_MGT_LENGTH_MISMATCH = "Reference sample lenght and reference pattern length does not match";
    public static final String UTILITY_PROVIDER_MGT_SAMPLE_LENGTH_MISMATCH = "Maximum reference number length and reference sample lenght does not match";

    public static final String UTILITY_PROVIDER_MGT_EMPTY_PROVIDER_CODE = "Provider code cannot be empty";
    public static final String UTILITY_PROVIDER_MGT_EMPTY_DESCRIPTION = "Description cannot be empty";
    public static final String UTILITY_PROVIDER_MGT_EMPTY_STATUS = "Status should be selected";
    public static final String UTILITY_PROVIDER_MGT_EMPTY_PROVIDER_TYPE = "Utility Provider type should be selected";
    public static final String UTILITY_PROVIDER_MGT_EMPTY_COLLECTION_ACCOUNT = "Collection account cannot be empty";
    public static final String UTILITY_PROVIDER_MGT_EMPTY_LOGO = "Logo cannot be empty";
    public static final String UTILITY_PROVIDER_MGT_EMPTY_TID = "TID cannot be empty";
    public static final String UTILITY_PROVIDER_MGT_EMPTY_MID = "MID cannot be empty";
    public static final String UTILITY_PROVIDER_MGT_EMPTY_AMEX_STATUS = "Amex status should be selected";
    public static final String UTILITY_PROVIDER_MGT_TID_LENGTH = "TID length should be ";

    public static final String UTILITY_PROVIDER_MGT_INVALID_REF_PATTERN = "Reference pattern invalid";
    public static final String UTILITY_PROVIDER_MGT_LENGTH_MISMATCH_MAX_REF = "Maximum reference number length and Reference pattern length mismatch";
    public static final String UTILITY_PROVIDER_MGT_LENGTH_MISMATCH_REF_SAM = "Reference pattern length and Reference sample length mismatch";
    public static final String UTILITY_PROVIDER_MGT_NOT_MATCH_REF_PATTERN = "Reference sample does not match with the reference pattern";

    public static final String UTILITY_PROVIDER_MGT_LARGE_IMAGE = "Image is too large. File size should be less than 650kb.";
    public static final String UTILITY_PROVIDER_MGT_LARGE_WIDTH = "Image width should be less than 480 pixels.";
    public static final String UTILITY_PROVIDER_MGT_SMALL_WIDTH = "Image width should be more than 480 pixels.";
    public static final String UTILITY_PROVIDER_MGT_LARGE_HEIGHT = "Image height should be less than 800 pixels.";
    public static final String UTILITY_PROVIDER_MGT_SMALL_HEIGHT = "Image height should be more than 800 pixels.";

    //-------------------- Acquirer Promotion Management --------------------------------
    public static final String ACQUIRER_PROMOTION_MGT_EMPTY_CODE = "Acquirer promotion code cannot be empty";
    public static final String ACQUIRER_PROMOTION_MGT_EMPTY_DESCRIPTION = "Description cannot be empty";
    public static final String ACQUIRER_PROMOTION_MGT_EMPTY_STATUS = "Status should be selected";
    public static final String ACQUIRER_PROMOTION_MGT_EMPTY_TRANSACTION_TYPE = "Transaction type should be selected";
    public static final String ACQUIRER_PROMOTION_MGT_EMPTY_BANK_AMOUNT_TYPE = "Bank amount type should be selected";
    public static final String ACQUIRER_PROMOTION_MGT_EMPTY_BANK_AMOUNT = "Bank amount cannot be empty";
    public static final String ACQUIRER_PROMOTION_MGT_INVALID_BANK_AMOUNT_PER_FLAT = "Bank amount";
    public static final String ACQUIRER_PROMOTION_MGT_EMPTY_BANK_AMOUNT_ISVALID = "Bank amount";
    public static final String ACQUIRER_PROMOTION_MGT_INVALID_BANK_AMOUNT = "Bank amount is invalid";
    public static final String ACQUIRER_PROMOTION_MGT_EMPTY_MERCHANT_AMOUNT_TYPE = "Merchant amount type should be selected";
    public static final String ACQUIRER_PROMOTION_MGT_EMPTY_MERCHANT_AMOUNT = "Merchant amount cannot be empty";
    public static final String ACQUIRER_PROMOTION_MGT_EMPTY_MERCHANT_AMOUNT_PER_FLAT = "Merchant amount";
    public static final String ACQUIRER_PROMOTION_MGT_EMPTY_MERCHANT_AMOUNT_ISVALID = "Merchant amount";
    public static final String ACQUIRER_PROMOTION_MGT_INVALID_MERCHANT_AMOUNT = "Merchant amount is invalid";
    public static final String ACQUIRER_PROMOTION_TXN_EMPTY = "Please assign acquirer promotion transactions";
    public static final String ACQUIRER_PROMOTION_TRANSACTION_TYPE_EXISTS = "Transaction type already exists";

    public static final String MERCHANT_USER_PASS_RESET_SUCC = "password reset successful";

    //------------------------- ib mob user param ---------------------------------------------
    public static final String IB_MOB_USER_PARAM_EMPTY_CODE = "Parameter code cannot be empty";
    public static final String IB_MOB_USER_PARAM_EMPTY_DESCRIPTION = "Description cannot be empty";
    public static final String IB_MOB_USER_PARAM_EMPTY_VALUE = "Value cannot be empty";

    public static final String IB_MOB_USER_PARAM_INVALID_CODE = "Parameter code is invalid";
    public static final String IB_MOB_USER_PARAM_INVALID_DESCRIPTION = "Description is invalid";
    public static final String IB_MOB_USER_PARAM_INVALID_VALUE = "Value is invalid";

    //------------------------- Bank Managemnt ---------------------------------------------
    public static final String BANK_MGT_EMPTY_CODE = "Bank code cannot be empty";
    public static final String BANK_MGT_EMPTY_DESCRIPTION = "Description cannot be empty";
    public static final String BANK_MGT_EMPTY_STATUS = "Status should be selected";
    public static final String BANK_MGT_EMPTY_LOGO = "Logo cannot be empty";

    public static final String BANK_MGT_LARGE_IMAGE = "Image is too large. File size should be less than 650kb.";
    public static final String BANK_MGT_LARGE_WIDTH = "Image width should be less than 480 pixels.";
    public static final String BANK_MGT_SMALL_WIDTH = "Image width should be more than 480 pixels.";
    public static final String BANK_MGT_LARGE_HEIGHT = "Image height should be less than 800 pixels.";
    public static final String BANK_MGT_SMALL_HEIGHT = "Image height should be more than 800 pixels.";

    // --------------------City Management---------------//
    public static final String CITY_EMPTY_CITY_CODE = "City code cannot be empty";
    public static final String CITY_EMPTY_DESCRIPTION = "Description cannot be empty";
    public static final String CITY_EMPTY_STATUS = "Status cannot be empty";

    // --------------------Term and Conditions---------------//
    public static final String TERMS_EMPTY_TERM_TYPE = "Terms & conditions type cannot be empty";
    public static final String TERMS_EMPTY_DESCRIPTION = "Description cannot be empty";
    public static final String TERMS_EMPTY_STATUS = "Status cannot be empty";
    public static final String TERMS_EMPTY_VERSION = "Version number cannot be empty";
    public static final String TERMS_VERSION_EXISTS = "Version number already exists";

    //-------------------------  Global Limits ---------------------------------------------
    public static final String GLOBAL_LIMITS_EMPTY_CUSTOMER_CATEGORY = "Customer category cannot be empty";
    public static final String GLOBAL_LIMITS_EMPTY_USER_LEVEL = "User level cannot be empty";
    public static final String GLOBAL_LIMITS_EMPTY_DAILY_LIMIT = "Daily limit cannot be empty";
    public static final String GLOBAL_LIMITS_EMPTY_TXN_LIMIT = "Transaction limit cannot be empty";
    public static final String GLOBAL_LIMITS_EMPTY_TOPUP_LIMIT = "POS top up limit cannot be empty";
    public static final String GLOBAL_LIMITS_EMPTY_WITDRAWAL_LIMIT = "POS withdrawal limit cannot be empty";
    public static final String GLOBAL_LIMITS_EMPTY_TOKEN_LIMIT = "Token limit cannot be empty";
    public static final String GLOBAL_LIMITS_EMPTY_ATM_WITDRAWAL_LIMIT = "ATM withdrawal limit cannot be empty";

    public static final String CUSTOMER_AML_COMMENT_EMPTY = "Comment cannot be empty";
    public static final String CUSTOMER_AML_STATUS_EMPTY = "Status cannot be empty";
    public static final String CUSTOMER_AML_SEARCH_EMPTY_WALLETID = "Empty Wallet ID";

    public static final String GLOBAL_LIMITS_INVALID_TXN_LIMIT = "Transaction limit should be less than daily limit";
    public static final String GLOBAL_LIMITS_INVALID_TOPUP_LIMIT = "POS top up limit should be less than transaction limit";
    public static final String GLOBAL_LIMITS_INVALID_WITHDRAWAL_LIMIT = "POS withdrawal limit should be less than transaction limit";
    public static final String GLOBAL_LIMITS_INVALID_TOKEN_LIMIT = "Token limit should be less than transaction limit";

    //------------------------- Mobile Message Managemnt ---------------------------------------------
    public static final String MOB_MSG_MGT_EMPTY_PARAMETER_CODE = "Parameter Code cannot be empty";
    public static final String MOB_MSG_MGT_EMPTY_DESCRIPTION = "Description cannot be empty";
    public static final String MOB_MSG_MGT_EMPTY_VALUE = "Value cannot be empty";

    //-------------------------Frimi rates Managemnt ---------------------------------------------
    public static final String FRIMI_RATES_EMPTY_RATE = "Rate cannot be empty";
    public static final String FRIMI_RATES_EMPTY_DESCRIPTION = "Description cannot be empty";
    public static final String FRIMI_RATES_EMPTY_SORT_ORDER = "Sort order cannot be empty";
    public static final String FRIMI_RATES_EMPTY_STATUS = "Status should be selected";

    //-------------------------customer push notification ---------------------------------------------
    public static final String CUS_PUSH_NOTIFICATION_WALLETID = "Emply wallet ID";
    public static final String CUS_PUSH_NOTIFICATION_MESSAGE = "Empty message";
    public static final String CUS_PUSH_NOTIFICATION_WALLETID_LENGTH = "Incorrect wallet ID";
    public static final String CUS_PUSH_NOTIFICATION_WALLETID_NUMERIC = "Incorrect wallet ID";

    // --------------------Block notification---------------//
    public static final String BLOCK_NOTIFICATION_EMPTY_WALLET_ID = "Wallet ID cannot be empty";
    public static final String BLOCK_NOTIFICATION_EMPTY_STATUS = "Status cannot be empty";
    public static final String BLOCK_NOTIFICATION_EMPTY_FROM_DATE = "From date cannot be empty";
    public static final String BLOCK_NOTIFICATION_EMPTY_TO_DATE = "To date cannot be empty";
    public static final String BLOCK_NOTIFICATION_INVALID_WALLET_ID = "Invalid wallet ID";

    // --------------------Pending Action---------------//
    public static final String PENDING_ACTION_APPROVED_SUCCESS = "Requested operation approved successfully";
    public static final String PENDING_ACTION_APPROVED_ERROR = "Requested operation approval fail";
    public static final String PENDING_ACTION_REJECTED_SUCCESS = "Requested operation rejected successfully";
    public static final String PENDING_ACTION_USERROLE_NOT_EXISTS = "Assigned user role has been removed";
    public static final String PENDING_ACTION_INVALID_USER_ROLE = "Task(s) are unassigned for this user role";

    //-------------------- How to page --------------------------------
    public static final String HOW_TO_EMPTY_CODE = "Code cannot be empty";
    public static final String HOW_TO_EMPTY_LEVELCODE = "Customer level cannot be empty";
    public static final String HOW_TO_EMPTY_STATUS = "Status cannot be empty";
    public static final String HOW_TO_EMPTY_HEADING = "Heading cannot be empty";
    public static final String HOW_TO_INVALID_LENGTH_HEADING = "Length of the heading should not exceed 128 characters";
    public static final String HOW_TO_EMPTY_URL = "URL cannot be empty";
    public static final String HOW_TO_INVALID_LENGTH_URL = "Length of the URL should not exceed 1000 characters";

}
