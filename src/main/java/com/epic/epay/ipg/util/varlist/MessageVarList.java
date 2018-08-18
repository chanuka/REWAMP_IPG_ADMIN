/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.epay.ipg.util.varlist;

/**
 *
 * @author chanuka
 */
public class MessageVarList {

    public static final String COMMON_ERROR_PROCESS = "Error occurred while processing";
    public static final String COMMON_NOT_EXISTS = "Record doesn't exist.";
    public static final String COMMON_ALREADY_EXISTS = "Record already exists.";
    public static final String COMMON_SUCC_INSERT = "created successfully";
    public static final String COMMON_SUCC_UPDATE = "updated successfully";
    public static final String COMMON_ERROR_UPDATE = "Error occurred while updating";
    public static final String COMMON_SUCC_DELETE = "deleted successfully";
    public static final String COMMON_ERROR_DELETE = "Error occurred while deleting";
    public static final String COMMON_SUCC_ASSIGN = "assigned successfully";
    // --------------------Login---------------//
    public static final String LOGIN_EMPTY_USERNAME = "Username or Password cannot be empty";
    public static final String LOGIN_EMPTY_PWD = "Username or Password cannot be empty";
    public static final String LOGIN_INVALID = "Your login attempt was not successful. Please try again.";
    public static final String LOGIN_ERROR_LOAD = "Cannot login. Please contact administrator";
    public static final String LOGIN_DEACTIVE = "User has been deactivated. Please contact administrator";
    public static final String PASSWORDRESET_EMPTY_PASSWORD = "Empty password";
    public static final String PASSWORDRESET_EMPTY_COM_PASSWORD = "Empty confirm password";
    public static final String PASSWORDRESET_MATCH_PASSWORD = "Passwords mismatch";
    public static final String PASSWORDRESET_INVALID_CURR_PWD = "Invalid current password";
    // --------------------Task Management---------------//
    public static final String TASK_MGT_EMPTY_TASK_CODE = "Task code cannot be empty";
    public static final String TASK_MGT_EMPTY_DESCRIPTION = "Description cannot be empty";
    public static final String TASK_MGT_EMPTY_SORTKEY = "Sort key cannot be empty";
    public static final String TASK_MGT_EMPTY_STATUS = "Status cannot be empty";
    public static final String TASK_MGT_ERROR_SORTKEY_INVALID = "Sort key invalid";
    public static final String TASK_MGT_ERROR_DESC_INVALID = "Description invalid";
    public static final String TASK_MGT_ERROR_TASKCODE_INVALID = "Task code invalid";
    public static final String TASK_SORT_KEY_ALREADY_EXISTS = "Sort key already exists";
    public static final String TASK_PAGES_DEPEND = "Cannot delete the task. Task is already assigned to pages";
    //------------------- Section Page Management----------//
    public static final String SECTION_PAGE_MGT_USER_ROLE_EMPTY_ = "User role cannot be empty";
    public static final String SECTION_PAGE_MGT_SECTION_EMPTY = "Section cannot be empty";
    //------------------- Page Task Management----------//
    public static final String PAGETASK_EMPTY_USERROLE = "User role cannot be empty";
    public static final String PAGETASK_EMPTY_SECTION = "Section cannot be empty";
    public static final String PAGETASK_EMPTY_PAGE = "Page cannot be empty";
    public static final String PAGE_SORT_KEY_ALREADY_EXISTS = "Sort key already exists";
    public static final String PAGE_USERROLES_DEPEND = "Cannot delete the page. Page is already assigned to user roles";
    //--------------------System User Management---------------//
    public static final String SYSUSER_MGT_EMPTY_USERNAME = "User name cannot be empty";
    public static final String SYSUSER_MGT_EMPTY_PASSWORD = "Password cannot be empty";
    public static final String SYSUSER_MGT_PASSWORD_MISSMATCH = "Password confirmation mismatched";
    public static final String SYSUSER_MGT_EMPTY_USERROLE = "User role cannot be empty";
    public static final String SYSUSER_MGT_EMPTY_DUAL_USERROLE = "Dual authentication user role cannot be empty";
    public static final String SYSUSER_MGT_EMPTY_STATUS = "Status cannot be empty";
    public static final String SYSUSER_MGT_EMPTY_EMPID = "Employee id cannot be empty";
    public static final String SYSUSER_MGT_EMPTY_EXPIRYDATE = "Expiry date cannot be empty";
    public static final String SYSUSER_MGT_EMPTY_FULLNAME = "Full name cannot be empty";
    public static final String SYSUSER_MGT_EMPTY_ADDRESS1 = "Address Line 1 cannot be empty";
    public static final String SYSUSER_MGT_EMPTY_ADDRESS2 = "Address Line 2 cannot be empty";
    public static final String SYSUSER_MGT_EMPTY_CITY = "City cannot be empty";
    public static final String SYSUSER_MGT_EMPTY_MOBILE = "Mobile number cannot be empty";
    public static final String SYSUSER_MGT_EMPTY_TELNO = "Telephone number cannot be empty";
    public static final String SYSUSER_MGT_EMPTY_FAX = "Fax cannot be empty";
    public static final String SYSUSER_MGT_EMPTY_EMAIL = "Email cannot be empty";
    public static final String SYSUSER_MGT_INVALID_EMAIL = "Invalid email";
    public static final String SYSUSER_PASSWORD_TOO_SHORT = "Password is shorter than the minimum length expected";
    public static final String SYSUSER_PASSWORD_TOO_LARGE = "Password is larger than the maximum length expected";
    public static final String SYSUSER_PASSWORD_LESS_LOWER_CASE_CHARACTERS = "Lower case characters are less than required";
    public static final String SYSUSER_PASSWORD_LESS_UPPER_CASE_CHARACTERS = "Upper case characters are less than required";
    public static final String SYSUSER_PASSWORD_LESS_NUMERICAL_CHARACTERS = "Numerical characters are less than required";
    public static final String SYSUSER_PASSWORD_LESS_SPACIAL_CHARACTERS = "Special characters are less than required";
    public static final String SYSUSER_PASSWORD_MISSMATCH = "Passwords mismatch.";
    public static final String SYSUSER_EMPTY_MERCHANT_NAME = "Merchant name cannot be empty";
    public static final String SYSUSER_INVALID_SYS_ID = "Invalid system user ID";
    //--------------------- Password policy management-------------//
    // password policy management
    public static final String PASSPOLICY_MINLEN_INVALID = "Minimum length should greater than 4";
    public static final String PASSPOLICY_MAXLEN_INVALID = "Maximum length should less than 8";
    public static final String PASSPOLICY_MINLEN_INVALID_DETAIL = "Minimum length should be equal or greater than ";
    public static final String PASSPOLICY_MAXLEN_INVALID_DETAIT = "Maximum length should be exceed ";
    public static final String PASSPOLICY_MINLEN_EMPTY = "Minimum length can not be empty";
    public static final String PASSPOLICY_MAXLEN_EMPTY = "Maximum length can not be empty";
    public static final String PASSPOLICY_MIN_MAX_LENGHT_DIFF = "Maximum length should greater than minimum length";
    public static final String PASSPOLICY_SPECCHARS_EMPTY = "Enter special characters allowed";
    public static final String PASSPOLICY_SPECCHARS_INVALID = "Minimum special characters should be greater than or equal to Allowed special characters";
    public static final String PASSPOLICY_SPECCHARS_INVALID_DETAIL="Minimum special characters should be equal to 0 when Allowed special characters equal to 0";
    public static final String PASSPOLICY_MINSPECCHARS_EMPTY = "Special characters can not be empty";
    public static final String PASSPOLICY_SPECCHARS_SHOULD_BE_LESS = "Minimum special characters should be less than ";
    public static final String PASSPOLICY_MINUPPER_EMPTY = "Uppercase characters can not be empty";
    public static final String PASSPOLICY_MINNUM_EMPTY = "Numerical characters can not be empty";
    public static final String PASSPOLICY_MINLOWER_EMPTY = "Lowercase characters can not be empty";
    public static final String PASSPOLICY_PIN_EMPTY = "No of PIN count can not be empty";
    public static final String PASSPOLICY_SUCCESS_ADD = "Password policy successfully added";
    public static final String PASSPOLICY_SUCCESS_DELETE = "Password policy successfully deleted";
    public static final String PASSPOLICY_SUCCESS_UPDATE = "Password policy successfully updated";
    public static final String PASSPOLICY_STATUS_EMPTY = "Select status";
    public static final String PASSPOLICY_POLICYID_EMPTY = "Password policy ID cannot be empty";
    //--------------------Page Management---------------//
    public static final String EMPTY_PAGE_CODE = "Page code cannot be empty";
    public static final String INVALID_PAGE_CODE = "Page code invalid";
    public static final String EMPTY_PAGE_DESCRIPTION = "Description cannot be empty";
    public static final String INVALID_PAGE_DESCRIPTION = "Description invalid";
    public static final String EMPTY_PAGE_URL = "URL cannot be empty";
    public static final String EMPTY_PAGE_ROOT = "Root cannot be empty";
    public static final String INVALID_PAGE_ROOT = "Page Root invalid";
    public static final String EMPTY_PAGE_SORTKEY = "Sort key cannot be empty";
    public static final String INVALID_SORTKEY = "Sort key invalid";
    public static final String EMPTY_PAGE_STATUS = "Status cannot be empty";
    //--------------------User Role Management---------------//
    public static final String USER_ROLE_MGT_EMPTY_CODE = "User role code cannot be empty";
    public static final String INVALID_USERROLE_CODE = "User role code invalid";
    public static final String USER_ROLE_MGT_EMPTY_DESCRIPTION = "User role description cannot be empty";
    public static final String INVALID_USERROLE_DESCRIPTION = "User role description invalid";
    public static final String USER_ROLE_TYPE_EMPTY = "User role type cannot be empty";
    public static final String EMPTY_USER_ROLE_STATUS = "Status cannot be empty";
    public static final String EMPTY_USER_ROLE_SORTKEY = "Sort key cannot be empty";
    public static final String INVALID_USER_ROLE_SORTKEY = "Sort key invalid";
    public static final String USER_ROLE_SORT_KEY_ALREADY_EXISTS = "Sort key already exists";
    public static final String USERROLE_USERS_DEPEND = "Cannot delete the user role. User role is already assigned to users";
    //--------------------Section Management---------------//
    public static final String SECTION_MGT_EMPTY_SECTION_CODE = "Section code cannot be empty";
    public static final String SECTION_MGT_EMPTY_DESCRIPTION = "Description cannot be empty";
    public static final String SECTION_MGT_EMPTY_SORTKEY = "Sort key cannot be empty";
    public static final String SECTION_MGT_EMPTY_STATUS = "Status cannot be empty";
    public static final String SECTION_MGT_ERROR_SORTKEY_INVALID = "Sort key invalid";
    public static final String SECTION_MGT_ERROR_DESC_INVALID = "Description invalid";
    public static final String SECTION_MGT_ERROR_SECTIONCODE_INVALID = "Section code invalid";
    public static final String USER_ERROR_ASSIGN = "User role cannot be empty";
    public static final String SECTION_SORT_KEY_ALREADY_EXISTS = "Sort key already exists";
    public static final String SECTION_USERROLES_DEPEND = "Cannot delete the section. Section is already assigned to user roles";
    //--------------------BIN Management---------------//
    public static final String BIN_MGT_EMPTY_BIN_CODE = "BIN code cannot be empty";
    public static final String BIN_MGT_EMPTY_DESCRIPTION = "Description cannot be empty";
    public static final String BIN_MGT_EMPTY_ONUS_STATUS = "On Us status cannot be empty";
    public static final String BIN_MGT_EMPTY_CARD_ASSOCIATION = "Card association cannot be empty";
    public static final String BIN_MGT_EMPTY_STATUS = "Status cannot be empty";
    public static final String BIN_MGT_ERROR_BIN_CODE_INVALID = "BIN number invalid";
    public static final String BIN_MGT_ERROR_DESC_INVALID = "Description invalid";
    //--------------------Common Configuration Management---------------//
    public static final String COMMON_CONFIG_EMPTY_SERVER_IP = "Mpi server IP cannot be empty";
    public static final String COMMON_CONFIG_EMPTY_SERVER_PORT = "Mpi server port cannot be empty";
    public static final String COMMON_CONFIG_EMPTY_SWITCH_IP = "Mpi switch IP cannot be empty";
    public static final String COMMON_CONFIG_EMPTY_SWITCH_PORT = "Mpi switch port cannot be empty";
    public static final String COMMON_CONFIG_EMPTY_IPG_ENGINE_URL = "Ipg engine URL cannot be empty";
    public static final String COMMON_CONFIG_EMPTY_MPI_SERVER_URL = "Mpi server URL cannot be empty";
    public static final String COMMON_CONFIG_EMPTY_KEYSTORE_PASS = "Kestore password cannot be empty";
    public static final String COMMON_CONFIG_EMPTY_ACQUIREBIN = "Acquirer BIN cannot be empty";
    public static final String COMMON_CONFIG_EMPTY_kEYSTORE_PATH = "Keystore path cannot be empty";
    public static final String COMMON_CONFIG_EMPTY_CERT_PATH = "Certificate path cannot be empty";
    public static final String COMMON_CONFIG_EMPTY_KEY_PATH = "Key path cannot be empty";
    public static final String COMMON_CONFIG_INVALID_SERVER_IP = "Mpi server IP is invalid";
    public static final String COMMON_CONFIG_INVALID_SERVER_PORT = "Mpi server port is invalid";
    public static final String COMMON_CONFIG_INVLID_SWITCH_IP = "Mpi switch IP is invalid";
    public static final String COMMON_CONFIG_INVLID_SWITCH_PORT = "Mpi switch port is invalid";
    public static final String COMMON_CONFIG_INVLID_IPG_ENGINE_URL = "Ipg engine URL is invalid";
    public static final String COMMON_CONFIG_INVLID_MPI_SERVER_URL = "Mpi server URL is invalid";
    public static final String COMMON_CONFIG_INVLID_MPI_ACQUIREBIN = "Acquirer BIN is invalid";
    public static final String COMMON_CONFIG_INVLID_KEYSTORE_PASS = "Kestore password is invalid";
    public static final String COMMON_CONFIG_INVLID_kEYSTORE_PATH = "Keystore path is invalid";
    public static final String COMMON_CONFIG_INVLID_CERT_PATH = "Certificate path is invalid";
    public static final String COMMON_CONFIG_INVLID_KEY_PATH = "Key path is invalid";
    public static final String COMMON_FILE_PATH_SYNTAX_ERROR = "Syntax error found in a regular-expression pattern";
    public static final String COMMON_FILE_PATH_URL_ERROR = "Error found in IP validation";
    public static final String COMMON_CONFIG_EMPTY_CODE = "Common configuration code cannot be empty";
    public static final String COMMON_CONFIG_EMPTY_DES = "Common configuration description cannot be empty";
    public static final String COMMON_CONFIG_EMPTY_VALUE = "Common configuration value cannot be empty";
    //--------------------Authentication Configuration Management---------------//
    public static final String AUTH_CONFIG_EMPTY_CA = "Card association cannot be empty";
    public static final String AUTH_CONFIG_EMPTY_TXSTATUS = "Transaction status cannot be empty";
    public static final String AUTH_CONFIG_EMPTY_ECI_VALUE = "ECI Value cannot be empty";
    public static final String AUTH_CONFIG_EMPTY_STATUS = "Authentication status cannot be empty";
    //--------------------Merchant Category Management---------------//
    public static final String MERCHANT_CATEGORY_CODE_EMPTY = "Merchant category code cannot be empty";
    public static final String INVALID_MERCHANT_CATEGORY_CODE = "Merchant category code invalid";
    public static final String MERCHANT_CATEGORY_EMPTY_DESCRIPTION = "Merchant category description cannot be empty";
    public static final String INVALID_MERCHANT_CATEGORY_DESCRIPTION = "Merchant category description invalid";
    public static final String EMPTY_MERCHANT_CATEGORY_STATUS = "Merchant category status cannot be empty";
    //IPG MPI Info
    public static final String PRIMARY_URL_EMPTY = "Primary url cannot be empty";
    public static final String SECONDARY_URL_EMPTY = "Secondary url cannot be empty";
    //--------------------Service Charge Management---------------//
    public static final String SERVICE_CHARGE_MGT_EMPTY_SERVICE_CHARGE_TYPE = "Service charge type cannot be empty";
    public static final String SERVICE_CHARGE_MGT_EMPTY_DESCRIPTION = "Description cannot be empty";
    public static final String SERVICE_CHARGE_MGT_EMPTY_SERVICE_CHARGE_RATE = "Service charge rate cannot be empty";
    public static final String SERVICE_CHARGE_MGT_EMPTY_SERVICE_CHARGE_VALUE = "Service charge value cannot be empty";
    public static final String SERVICE_CHARGE_MGT_EMPTY_CURRENCY = "Currency cannot be empty";
    public static final String SERVICE_CHARGE_MGT_ERROR_DESC_INVALID = "Description invalid";
    public static final String SERVICE_CHARGE_MGT_ERROR_VALUE_INVALID = "Value invalid";
    public static final String SERVICE_CHARGE_MGT_ERROR_RATE_INVALID = "Rate invalid";
    //--------------------Country Management---------------//
    public static final String COUNTRY_CODE_EMPTY = "Country code cannot be empty";
    public static final String INVALID_COUNTRY_CODE = "Country code invalid";
    public static final String COUNTRY_DESCRIPTION_EMPTY = "Country description cannot be empty";
    public static final String INVALID_COUNTRY_DESCRIPTION = "Country description invalid";
    public static final String COUNTRY_SORTKEY_EMPTY = "Sort key cannot be empty";
    public static final String INVALID_COUNTRY_SORTKEY = "Sort key invalid";
    public static final String COUNTRY_ISOCODE_EMPTY = "Country ISO code cannot be empty";
    //-------------------- Currency Management---------------//
    public static final String CURRENCY_EMPTY_ISO_CODE = "Currency ISO code cannot be empty";
    public static final String CURRENCY_EMPTY_CODE = "Currency code cannot be empty";
    public static final String CURRENCY_EMPTY_DES = "Currency description cannot be empty";
    public static final String CURRENCY_EMPTY_SORTKEY = "Currency sort key cannot be empty";
    public static final String CURRENCY_INVALID_ISO_CODE = "Currency ISO code is invalid";
    public static final String CURRENCY_INVALID_CODE = "Currency code is invalid";
    public static final String CURRENCY_INVLID_DES = "Currency description is invalid";
    public static final String CURRENCY_INVLID_SORTKEY = "Currency sort key is invalid";
    //--------------------Card Association Management---------------//
    public static final String EMPTY_CARD_ASSOCIATION_CODE = "Card association code cannot be empty";
    public static final String INVALID_CARD_ASSOCIATION_CODE = "Card association code invalid";
    public static final String EMPTY_CARD_ASSOCIATION_DESCRIPTION = "Description cannot be empty";
    public static final String ALREADY_EXISTS_CARD_ASSOCIATION_DESCRIPTION = "Description already exists";
    public static final String INVALID_CARD_ASSOCIATION_DESCRIPTION = "Description invalid";
    public static final String EMPTY_CARD_IMAGE_URL = "Card image URL cannot be empty";
    public static final String EMPTY_VERIFIED_IMAGE_URL = "Verified image URL cannot be empty";
    //--------------------On Us Bin Range Management---------------//
    public static final String ONUSBINRANGE_EMPTY_VALUE_1 = "Value 1 cannot be empty";
    public static final String ONUSBINRANGE_EMPTY_VALUE_2 = "Value 2 cannot be empty";
    public static final String ONUSBINRANGE_EMPTY_REMARKS = "Remarks cannot be empty";
    public static final String ONUSBINRANGE_EMPTY_STATUS = "Status cannot be empty";
    public static final String ONUSBINRANGE_INVALID_VALUE_1 = "Value 1 is invalid";
    public static final String ONUSBINRANGE_INVALID_VALUE_2 = "Value 2 is invalid";
    public static final String ONUSBINRANGE_INVALID_REMARKS = "Remarks is invalid";
    //--------------------Service charge type Management---------------//
    public static final String SERVICE_CHARGE_TYPE_CODE_EMPTY = "Service charge type code cannot be empty";
    public static final String INVALID_SERVICE_CHARGE_TYPE_CODE = "Service charge type code invalid";
    public static final String SERVICE_CHARGE_TYPE_DESCRIPTION_EMPTY = "Service charge type description cannot be empty";
    public static final String INVALID_SERVICE_CHARGE_TYPE_DESCRIPTION = "Service charge type description invalid";
    public static final String SERVICE_CHARGE_TYPE_SORTKEY_EMPTY = "Sort key cannot be empty";
    public static final String INVALID_SERVICE_CHARGE_TYPE_SORTKEY = "Sort key invalid";
    //--------------------Rule Type Management---------------//
    public static final String RULE_TYPE_EMPTY_CODE = "Rule type code cannot be empty";
    public static final String RULE_TYPE_EMPTY_DES = "Description cannot be empty";
    public static final String RULE_TYPE_EMPTY_SORT_KEY = "Sort key cannot be empty";
    public static final String RULE_TYPE_EMPTY_SCOPE = "Rule scope cannot be empty";
    public static final String RULE_TYPE_EMPTY_STATUS = "Status cannot be empty";
    public static final String RULE_TYPE_INVALID_CODE = "Rule type code is invalid";
    public static final String RULE_TYPE_INVALID_DES = "Description is invalid";
    public static final String RULE_TYPE_INVALID_SORT_KEY = "Sort key is invalid";
    //--------------------Rule Scope Management---------------//
    public static final String RULE_SCOPE_MGT_EMPTY_RULE_SCOPE_CODE = "Rule scope code cannot be empty";
    public static final String RULE_SCOPE_MGT_EMPTY_DESCRIPTION = "Description cannot be empty";
    public static final String RULE_SCOPE_MGT_EMPTY_SORT_KEY = "Sort key cannot be empty";
    public static final String RULE_SCOPE_MGT_ERROR_RULE_SCOPE_CODE_INVALID = "Rule scope code invalid";
    public static final String RULE_SCOPE_MGT_ERROR_DESC_INVALID = "Description invalid";
    public static final String RULE_SCOPE_MGT_ERROR_SORT_KEY_INVALID = "Sort key invalid";
     public static final String RULE_SCOPE_MGT_EMPTY_STATUS_KEY = "Status cannot be empty";
    //merchant management
    //------------upload merchant certificate-------------//
    public static final String MERCHANT_SELECT_FILE = "Select a file.";
    public static final String MERCHANT_SELECT_VALID_FILE = "Select valid file.";
    public static final String MERCHANT_SELECT_MERCHANTNAME = "Select merchant name";
    public static final String COMMON_SUCC_UPLOAD = "Uploaded successfully";
    public static final String COMMON_ERROR_UPLOAD = "Error occured while uploading";
    //-------------merchant currency management------------//
    public static final String MERCHANT_EMPTY = "Merchant name cannot be empty";
    public static final String CURRENCY_EMPTY = "Currency cannot be empty";
    public static final String INVALID_REMARK = "Remark invalid";

    //-------------merchant Terminal management------------//
    public static final String MERCHANT_TERMINAL_DELETE_ERROR = "Terminal cannot be deleted due to unsettled transaction.";
    public static final String EMPTY_MERCHANT_ID = "Merchant ID cannot be empty";
    public static final String EMPTY_CURRENCY = "Currency cannot be empty";
    public static final String EMPTY_TERMINAL_ID = "Terminal ID cannot be empty";
    public static final String EMPTY_STATUS_TER = "Status cannot be empty";
    public static final String INVALID_TERMINAL_ID = "Terminal ID invalid";
    public static final String LENGTH_TERMINAL_ID = "8 characters required for terminal ID";

    //--------------------Rule Management---------------//
    public static final String RULE_EMPTY_RULE_SCOPE_CODE = "Rule scope code cannot be empty";
    public static final String RULE_EMPTY_DES = "Description cannot be empty";
    public static final String RULE_EMPTY_RULE_TYPE = "Rule type cannot be empty";
    public static final String RULE_EMPTY_START_VALUE = "Start value cannot be empty";
    public static final String RULE_EMPTY_END_VALUE = "End value cannot be empty";
    public static final String RULE_EMPTY_PRIORITY = "Priority cannot be empty";
    public static final String RULE_EMPTY_SECURITY_LEVEL = "Security level cannot be empty";
    public static final String RULE_EMPTY_OPERATOR = "operator cannot be empty";
    public static final String RULE_INVALID_PRIORITY = "Priority is invalid";
    public static final String RULE_INVALID_DES = "Description is invalid";
    public static final String RULE_INVALID_START_VALUE = "Start value is invalid";
    public static final String RULE_INVALID_END_VALUE = "End value is invalid";
    public static final String RULE_EMPTY_TRIGGERSEQUENCE = "Trigger sequence cannot be empty";
     public static final String RULE_EMPTY_STATUS = "Status cannot be empty";
    //--------------------Risk Profile Management---------------//
    public static final String RISK_PROFILE_MGT_EMPTY_RISK_PROFILE_CODE = "Risk profile code cannot be empty";
    public static final String RISK_PROFILE_MGT_EMPTY_DESCRIPTION = "Description cannot be empty";
    public static final String RISK_PROFILE_MGT_EMPTY_STATUS = "Status cannot be empty";
    public static final String RISK_PROFILE_MGT_EMPTY_MAX_TXN_LIMIT = "Max single transaction limit cannot be empty";
    public static final String RISK_PROFILE_MGT_EMPTY_MIN_TXN_LIMIT = "Min single transaction limit cannot be empty";
    public static final String RISK_PROFILE_MGT_EMPTY_MAX_TXN_COUNT = "Max transaction count cannot be empty";
    public static final String RISK_PROFILE_MGT_EMPTY_MAX_TXN_AMOUNT = "Max transaction amount cannot be empty";
    public static final String RISK_PROFILE_MGT_EMPTY_MAX_PASS_COUNT = "Max password count cannot be empty";
    public static final String RISK_PROFILE_MGT_ERROR_RISK_PROFILE_CODE_INVALID = "Risk profile code is invalid";
    public static final String RISK_PROFILE_MGT_ERROR_DESC_INVALID = "Description is invalid";
    public static final String RISK_PROFILE_MGT_ERROR_MAX_TXN_LIMIT_INVALID = "Max single transaction limit is invalid";
    public static final String RISK_PROFILE_MGT_ERROR_MIN_TXN_LIMIT_INVALID = "Min single transaction limit is invalid";
    public static final String RISK_PROFILE_MGT_ERROR_MAX_TXN_COUNT_INVALID = "Max transaction count is invalid";
    public static final String RISK_PROFILE_MGT_ERROR_MAX_TXN_AMOUNT_INVALID = "Max total transaction amount is invalid";
    public static final String RULE_SCOPE_MGT_ERROR_MAX_PASS_COUNT_INVALID = "Max password count is invalid";
    public static final String RULE_SCOPE_MGT_ERROR_MIN_GREATER_THAN_MAX = "Min single transaction limit cannot be greater than max single transaction limit";
    //--------------------Merchant Service Charge Management---------------//
    public static final String MERCHANT_SER_CHARGE_EMPTY_NAME = "Merchant name cannot be empty";
    public static final String MERCHANT_SER_CHARGE_EMPTY_SER_CHARGE = "Service charge cannot be empty";
    public static final String MERCHANT_SER_CHARGE_EMPTY_REMARKS = "Remarks cannot be empty";
    public static final String MERCHANT_SER_CHARGE_INVALID_REMARKS = "Remarks is invalid";
    //--------------------Merchant Credential Management---------------//
    public static final String MERCHANT_CREDENTIAL_EMPTY_NAME = "Merchant ID cannot be empty";
    public static final String MERCHANT_CREDENTIAL_EMPTY_CARD_ASSOCIATION = "Card association cannot be empty";
    public static final String MERCHANT_CREDENTIAL_ALREADY_EXISTS_CARD_ASSOCIATION = "Card association code already exists";
    public static final String MERCHANT_CREDENTIAL_EMPTY_USERNAME = "Username cannot be empty";
    public static final String MERCHANT_CREDENTIAL_EMPTY_PASS = "Password cannot be empty";
    public static final String MERCHANT_CREDENTIAL_EMPTY_RE_TYPE_PASS = "Re-Type password cannot be empty";
    public static final String MERCHANT_CREDENTIAL_INVALID_USERNAME = "Username is invalid";
    public static final String MERCHANT_CREDENTIAL_UNMATCH_PASSWORDS = "Passwords mismatch";
    public static final String MERCHANT_CREDENTIAL_USERNAME_EXISTS = "Username already exists";

    //--------------------Merchant Management---------------//
    //------------------Merchant Addon Management----------------------//
    public static final String MERCHANT_ADDON_EMPTY_NAME = "Merchant name cannot be empty";
    public static final String MERCHANT_ADDON_EMPTY_PATH = "Logo path cannot be empty";
    public static final String MERCHANT_ADDON_EMPTY_X_CO = "X-coordinate cannot be empty";
    public static final String MERCHANT_ADDON_EMPTY_Y_CO = "Y-coordinate cannot be empty";
    public static final String MERCHANT_ADDON_EMPTY_DISPLAY_TEXT = "Tag line cannot be empty";
    public static final String MERCHANT_ADDON_EMPTY_THEME_COLOR = "Theme color cannot be empty";
    public static final String MERCHANT_ADDON_EMPTY_FONT_FAMILY = "Font family cannot be empty";
    public static final String MERCHANT_ADDON_EMPTY_FONT_STYLE = "Font style cannot be empty";
    public static final String MERCHANT_ADDON_EMPTY_FONT_SIZE = "Font size cannot be empty";
    public static final String MERCHANT_ADDON_EMPTY_REMARKS = "Remarks cannot be empty";
    public static final String MERCHANT_ADDON_INVALID_X_CO = "X-coordinate is invalid";
    public static final String MERCHANT_ADDON_INVALID_Y_CO = "Y-coordinate is invalid";
    public static final String MERCHANT_ADDON_INVALID_DISPLAY_TEXT = "Display text is invalid";
    public static final String MERCHANT_ADDON_INVALID_REMARKS = "Remarks is invalid";
//    public static final String MERCHANT_ADDON_INVALID_PATH = "Logo path is invalid";

    //--------------------Merchant Management---------------//
    public static final String MERCHANT_ID_EMPTY = "Merchant ID cannot be empty";
    public static final String MERCHANT_ID_INVALID = "Merchant ID invalid";
    public static final String MERCHANT_MGT_EMPTY_MERCHANT_CUSTOMER_CODE = "Merchant customer ID cannot be empty";
    public static final String INVALID_MERCHANT_ID = "Merchant ID invalid";
    public static final String MERCHANT_NAME_EMPTY = "Merchant name cannot be empty";
    public static final String INVALID_MERCHANT_NAME = "Merchant name invalid";
    public static final String MERCHANT_ADDRESS1_EMPTY = "Merchant address 1 cannot be empty";
    public static final String DRS_URL_EMPTY = "Dynamic return success URL cannot be empty";
    public static final String DRE_URL_EMPTY = "Dynamic return error URL cannot be empty";
    public static final String CITY_EMPTY = "City cannot be empty";
    public static final String COUNTRY_EMPTY = "Country cannot be empty";
    public static final String INVALID_CITY = "City invalid";
    public static final String DATE_OF_REGISTRY_EMPTY = "Date of Registry cannot be empty";
    public static final String DATE_OF_EXPIRY_EMPTY = "Date of Expiry cannot be empty";
    public static final String STATUS_EMPTY = "Status cannot be empty";
    public static final String CONTACT_PERSON_EMPTY = "Contact person cannot be empty";
    public static final String SECURITY_MECHANISM_EMPTY = "Security mechanism cannot be empty";
    public static final String ERROR_ADDRESS1_INVALID = "Address1 invalid";
    public static final String ERROR_ADDRESS2_INVALID = "Address2 invalid";
    public static final String ERROR_CITY_INVALID = "City invalid";
    public static final String ERROR_STATE_CODE_INVALID = "State code invalid";
    public static final String ERROR_POSTAL_CODE_INVALID = "Postal code invalid";
    public static final String ERROR_PROVINCE_INVALID = "Province invalid";
    public static final String ERROR_MOBILE_INVALID = "Mobile invalid";
    public static final String ERROR_TEL_NO_INVALID = "Telephone no. invalid";
    public static final String ERROR_FAX_INVALID = "Fax invalid";
    public static final String ERROR_EMAIL_EMPTY = "Email can not be empty";
    public static final String ERROR_EMAIL_INVALID = "Email invalid";
    public static final String ERROR_PRIMARY_URL_INVALID = "Primary URL invalid";
    public static final String ERROR_SECONDARY_URL_INVALID = "Secondary URL invalid";
    public static final String ERROR_SUCCESS_URL_INVALID = "Dynamic success URL invalid";
    public static final String ERROR_ERROR_URL_INVALID = "Dynamic error URL invalid";
    public static final String ERROR_CONTACT_PERSON_INVALID = "Contact person invalid";
    public static final String ERROR_REMARKS_INVALID = "Remarks invalid";
    public static final String MERCH_INVALID_DEL = "Record cannot be deleted";
    public static final String MERCH_MGT_EMPTY_AUTH_REQUIRED_STATUS = "Authentication required status cannot be empty";
    public static final String RISKPROFILE_EMPTY = "Risk profile cannot be empty";
    
    //------------------- Merchant Mgt Assign Currency -------------------------------------
    public static final String MERCHANT_AC_EMPTY_MERCHNAT_ID = "Merchant ID cannot be empty";
    public static final String MERCHANT_AC_EMPTY_CURRENCY = "Currency cannot be empty";
    //------------------- Merchant Mgt Rule and service charge -------------------------------------
    public static final String MERCHANT_RS_EMPTY_MERCHNAT_ID = "Merchant ID cannot be empty";
    public static final String MERCHANT_RS_EMPTY_SERVICE = "Service cannot be empty";

    //--------------------Head Merchant Management---------------//
    public static final String HM_MGT_EMPTY_MERCHANT_CUSTOMER_CODE = "Merchant customer ID cannot be empty";
    public static final String HM_MGT_EMPTY_MERCHANT_ID = "Merchant ID cannot be empty";
    public static final String HM_MGT_EMPTY_MERCHANT_ID_EXISTS = "Merchant ID already exists";

    public static final String HM_MGT_EMPTY_MERCHANT_NAME = "Merchant name cannot be empty";
    public static final String HM_MGT_EMPTY_MERCHANT_CUSTOMER_NAME = "Merchant customer name cannot be empty";
    ;
    public static final String HM_MGT_EMPTY_ADDRESS1 = "Address1 cannot be empty";
    public static final String HM_MGT_EMPTY_CITY = "City cannot be empty";
    public static final String HM_MGT_EMPTY_COUNTRY = "Country cannot be empty";
    public static final String HM_MGT_EMPTY_MOBILE = "Mobile cannot be empty";
    public static final String HM_MGT_EMPTY_TEL_NO = "Telephone no. cannot be empty";
    public static final String HM_MGT_EMPTY_FAX = "Fax cannot be empty";
    public static final String HM_MGT_EMPTY_EMAIL = "Email cannot be empty";
    public static final String HM_MGT_EMPTY_PRIMARY_URL = "Primary URL cannot be empty";
    public static final String HM_MGT_EMPTY_SUCCESS_URL = "Dynamic success url cannot be empty";
    public static final String HM_MGT_EMPTY_ERROR_URL = "Dynamic error URL cannot be empty";
    public static final String HM_MGT_EMPTY_DATE_OF_REGISTRY = "Date of registry cannot be empty";
    public static final String HM_MGT_EMPTY_DATE_OF_EXPIARY = "Date of expiary cannot be empty";
    public static final String HM_MGT_EMPTY_STATUS = "Status cannot be empty";
    public static final String HM_MGT_EMPTY_CONTACT_PERSON = "Contact person cannot be empty";
    public static final String HM_MGT_EMPTY_SECURITY_MECHANISM = "Security mechanism cannot be empty";
    public static final String HM_MGT_EMPTY_RISK_PROFILE = "Risk profile cannot be empty";
    public static final String HM_MGT_EMPTY_AUTH_REQUIRED_STATUS = "Authentication required status cannot be empty";
    public static final String HM_MGT_EMPTY_TXN_INIT_STATUS = "Transaction initiated status cannot be empty";
    public static final String HM_MGT_ERROR_MERCHANT_CUSTOMER_CODE_INVALID = "Merchant customer ID invalid";
    public static final String HM_MGT_ERROR_MERCHAND_ID_INVALID = "Merchant ID invalid";
    public static final String HM_MGT_ERROR_ADDRESS1_INVALID = "Address1 invalid";
    public static final String HM_MGT_ERROR_ADDRESS2_INVALID = "Address2 invalid";
    public static final String HM_MGT_ERROR_CITY_INVALID = "City invalid";
    public static final String HM_MGT_ERROR_STATE_CODE_INVALID = "State code invalid";
    public static final String HM_MGT_ERROR_POSTAL_CODE_INVALID = "Postal code invalid";
    public static final String HM_MGT_ERROR_PROVINCE_INVALID = "Province invalid";
    public static final String HM_MGT_ERROR_MOBILE_INVALID = "Mobile invalid";
    public static final String HM_MGT_ERROR_TEL_NO_INVALID = "Telephone no. invalid";
    public static final String HM_MGT_ERROR_FAX_INVALID = "Fax invalid";
    public static final String HM_MGT_ERROR_EMAIL_INVALID = "Email invalid";
    public static final String HM_MGT_ERROR_PRIMARY_URL_INVALID = "Primary URL invalid";
    public static final String HM_MGT_ERROR_SECONDARY_URL_INVALID = "Secondary URL invalid";
    public static final String HM_MGT_ERROR_SUCCESS_URL_INVALID = "Dynamic success URL invalid";
    public static final String HM_MGT_ERROR_ERROR_URL_INVALID = "Dynamic error URL invalid";
    public static final String HM_MGT_ERROR_CONTACT_PERSON_INVALID = "Contact person invalid";
    public static final String HM_MGT_ERROR_REMARKS_INVALID = "Remarks invalid";
    public static final String HM_MGT_MERCHANT_ID_INVALID = "Merchant ID should be 15 characters long";

    //-----------------Merchant Certificate Management----------------//        
    public static final String MERCHANT_CER_MGT_ERROR_DOWNLOAD = "Error occurred while downloading merchant certificate";

    //----------------Analyzer Last Transaction------------------------//
    public static final String ANALYZER_LAST_TRANSACTION_ERROR = "Error occurred while calculating.";
    public static final String ANALYZER_LAST_TRANSACTION_ERROR_ILLEGAL = "Analyzer time does not have the timestamp format.";
    public static final String ANALYZER_LAST_TRANSACTION_NULL_ERROR = "Not enough data to process.";

    //----------transaction management--------------//
    // Moto Transaction
    public static final String MOTO_CARD_NUMBER_EMPTY = "Card number cannot be empty";
    public static final String MOTO_CARD_NUMBER_INVALID = "Invalid card number";
    public static final String MOTO_EXPIRY_DATE_EMPTY = "Expiry date cannot be empty";
    public static final String MOTO_EXPIRY_DATE_INVALID = "Invalid expiry date";
    public static final String MOTO_AMOUNT_EMPTY = "Amount cannot be empty";
    public static final String MOTO_AMOUNT_NIVALID = "Invalid amount";
    public static final String MOTO_ORDER_NO_EMPTY = "Order code cannot be empty";
    public static final String MOTO_ORDER_NO_INVALID = "Invalid order code";
    public static final String MOTO_CURRENCY_EMPTY = "Currency cannot be empty";
    public static final String MOTO_CARD_TYPE_EMPTY = "Card type cannot be empty";
    public static final String MOTO_CARD_NAME_EMPTY = "Card holder name cannot be empty";
    public static final String MOTO_CARD_NAME_INVALID = "Invalid card holder name";
    public static final String FAILED_TO_INITIATE_IPG_TRANSACTION = "Failed to initiate IPG transaction.";
    public static final String TXN_FAILED = "Transaction failed.";
    public static final String TXN_SUCCESSFUL = "Transaction successful.";
    public static final String FAILD_SWITCH_REQUEST_CREATE = "Failed to generate switch request.";
    public static final String FAILED_TO_SEND_MESSAGE_TO_TXN_SWITCH = "Failed to send request message to transaction switch.";
    public static final String INVALID_TXN_SWITCH_RESPONSE = "Invalid transaction switch response.";
    public static final String FAILED_TO_RECEIVE_MESSAGE_FROM_TXN_SWITCH = "Failed to receive response message from transaction switch.";

    // Transaction Void
    public static final String TXN_VOID_INVALID_TXN_ID = "Invalid transaction ID";
    public static final String TXN_VOID_INVALID_MERCHNT_NAME = "Invalid merchant name";
    public static final String TXN_VOID_INVALID_CARD_HOLDR_NAME = "Invalid card holder name";
    public static final String TXN_VOID_INVALID_CARD_NUMBER = "Invalid card number";
    public static final String COMMON_SUCC_VOID = "Transaction VOID successful.";
    public static final String COMMON_SUCC_REVERSAL = "Transaction reversal successful.";
    public static final String COMMON_FAILED_VOID = "Transaction VOID failed.";
    public static final String COMMON_FAILED_REVERSAL = "Transaction reversal failed.";

    //------------upload merchant certificate-------------//
    public static final String TXN_BATCH_UPLOAD_EMPTY_FILE = "Select a file";
    public static final String TXN_BATCH_UPLOAD_EMPTY_TXN_TYPE = "Empty transaction type";
    public static final String TXN_BATCH_UPLOAD_SUCCESS = "Uploaded successfully";
    public static final String TXN_BATCH_UPLOAD_ERROR = "Error occured while uploading";
    //------------refund txn-------------//
    public static final String AMOUNT_NULL = "Enter amount";
    public static final String AMOUNT_NIVALID = "Invalid amount";
    //------------settlement txn-------------//
    public static final String MERCHANTID_NULL = "Merchant id can not be empty";

    // IPG Transaction Batch Upload
    public static final String INVALID_FILE_SIZE = "Cannot upload.Minimum file size should be 10MB";
    public static final String ERROR_FILE_UPLOAD = "Error occured while file uploading";
    public static final String FILE_UPLOAD_SUCCESS = "File successfully uploaded";
    public static final String INVALID_FILE = "Please upload valid file";
    public static final String FILE_ALREADY_UPLOADED = "File already uploaded";
    public static final String FILE_TYPE_EMPTY = "File type cannot be empty";
    public static final String FILE_NAME_EMPTY = "File name cannot be empty";
    public static final String FILE_TYPE_INVALID = "Select valid file.";
    public static final String BATCH_FILE_LENTH_ERROR = "Insufficient data to validate file";
    public static final String LENTH_ERROR = "Insufficient data";
    public static final String BATCH_FILE_LUHNA_VALIDATE_FAILED = "Reject file due to invalid card number";
    public static final String LUHNA_VALIDATE_FAILED = "Invalid card number";
    public static final String BATCH_FILE_CARD_HOLDER_NAME_ERROR = "Reject file due to invalid card holder name";
    public static final String CARD_HOLDER_NAME_ERROR = "Invalid card holder name";
    public static final String BATCH_FILE_NIC_INVALID = "Reject file due to invalid nic";
    public static final String NIC_INVALID = "Invalid nic";
    public static final String BATCH_FILE_EXPIRY_DATE_INVALID = "Reject file due to invalid expiry date";
    public static final String BATCH_EXPIRY_DATE_INVALID = "invalid expiry date";
    public static final String BATCH_FILE_TRANSACTION_AMOUNT_ERROR = "Reject file due to invalid transaction amount";
    public static final String TRANSACTION_AMOUNT_ERROR = "Invalid transaction amount";
    public static final String BATCH_FILE_CURRENCY_CODE_ERROR = "Reject file due to invalid currency code";
    public static final String CURRENCY_CODE_ERROR = "Invalid currency code";
    public static final String BATCH_FILE_MERCHANT_ID_ERROR = "Reject file due to invalid merchant id";
    public static final String MERCHANT_ID_ERROR = "Invalid merchant id";
    public static final String BATCH_FILE_CARD_ASSOCIATION_INVALID = "Reject file due to invalid card association code";
    public static final String CARD_ASSOCIATION_INVALID = "Invalid card association code";
    public static final String BATCH_FILE_EMPTY_LINE_DETECTED = "Reject file due to empty line";
    public static final String EMPTY_LINE_DETECTED = "Empty line detected";
    public static final String BATCH_FILE_TRANSACTION_INIT = "Transaction initiated";
    public static final String BATCH_FILE_NUM_OF_INSTALLMENT_ERROR = "Reject file due to invalid number of installment";
    public static final String NUM_OF_INSTALLMENT_ERROR = "Invalid number of installment";
    public static final String BATCH_FILE_INSTALLMENT_PERIOD_ERROR = "Reject file due to invalid installment period";
    public static final String INSTALLMENT_PERIOD_ERROR = "Invalid installment period";
    public static final String BATCH_FILE_COUNT_VALUE_ERROR = "Reject file due to invalid count value";
    public static final String COUNT_VALUE_ERROR = "Invalid count value";
    public static final String BATCH_FILE_NEXT_TRANSACTION_DATE_ERROR = "Reject file due to invalid transaction date";
    public static final String NEXT_TRANSACTION_DATE_ERROR = "Invalid transaction date";
    public static final String BATCH_FILE_NOT_FOUND_ERROR = "File not found";
    public static final String EXPIRY_DATE_INVALID = "Invalid expiry date";
    public static final String FILE_PATH_EMPTY = "File path not assigned";

    //------------ccba manual transaction ---------------//
    public static final String MANUAL_TXN_PROCESS_SERVER_ERROR = "Server not responding";
    public static final String MANUAL_TXN_SUCCESS = "Transaction successfull";
    public static final String MANUAL_TXN_NOT_SELECTED_ROW = "At least one txn must be selected";

    //-----------------Merchant Settlment File Download----------------//        
    public static final String MERCHANT_SETTLEMT_FILE_ERROR_DOWNLOAD = "Error occurred while downloading settlment file";
    public static final String MERCHANT_SETTLEMT_FILE_NO_TRAN = "No pending transaction to generate file";
    public static final String MERCHANT_SETTLEMT_FILE_DOWNLOAD_SUCCESS = "Settlement file download success";

     // Transaction Failed
    public static final String TXN_FAILED_INVALID_TXN_ID = "Invalid transaction ID";
    public static final String TXN_FAILED_INVALID_MERCHNT_NAME = "Invalid merchant name";
    public static final String TXN_FAILED_INVALID_CARD_HOLDR_NAME = "Invalid card holder name";
    public static final String TXN_FAILED_INVALID_CARD_NUMBER = "Invalid card number";
    
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

}
