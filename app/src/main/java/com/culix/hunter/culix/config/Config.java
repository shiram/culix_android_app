package com.culix.hunter.culix.config;

public class Config  {
    public static int SPLASH_DURATION = 3000;
    public static final String MYPREFERENCES = "MyPrefs";
    public static int RESULT_LOAD = 1;

    //Error Messages
    public static String NO_SERVER_RESPONSE = "Please Check your Internet Connection.";

    //Keys to map values to server
    public static String FIRSTNAME = "firstname";
    public static String LASTNAME = "lastname";
    public static String EMAIL = "email";
    public static String PASSWORD = "password";

    public static String USER_ID = "user_id";
    public static String ITEM_NAME = "item_name";
    public static String ITEM_PRICE = "item_price";
    public static String ITEM_DESC = "item_desc";
    public static String ITEM_CAT = "item_cat";
    public static String BUSINESS_COUNTRY = "country";
    public static String BUSINESS_CITY = "city";
    public static String BUSINESS_ADDRESS = "address";
    public static String LAT = "lat";
    public static String LNG = "lng";
    public static String ITEM_IMAGE_ENCODED = "item_image_en";
    public static String ITEM_IMAGE_FILE = "item_image_file";

    public static String NO_DATA_ERROR = "No Data Found, try again please.";

    //Urls
    public static String url = "http://10.0.2.2/culix/";
    public static String REGISTER = url+"register.php";
    public static String LOGIN = url+"login.php";
    public  static String ADD_PRODUCT = url+"add_product.php";
    public static String GET_PRODUCTS = url+"get_products.php";
    public static String GET_USER_PRODUCTS = url+"get_user_products.php";
    public static String ADD_MARKETER = url+"add_marketer.php";
    public static String REQUEST_RIGHTS = url+"request_rights.php";
    public static String GET_ACCOUNTS = url+"get_accounts.php";

}
