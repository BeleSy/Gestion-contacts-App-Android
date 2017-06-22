package com.example.exemple_sqlite_3;

/**
 * Created by 201696392 on 2017-03-31.
 */
public class Config {
    //Address of our scripts of the CRUD
    public static final String URL_ADD="http://127.0.0.1/addContact.php";
    public static final String URL_GET_ALL = "http://127.0.0.1/getAllContacts.php";
    public static final String URL_GET_CONTACT = "http://127.0.0.1/getContacts.php?id=";
    public static final String URL_UPDATE_CONTACT = "http://127.0.0.1/updateContact.php";
    public static final String URL_DELETE_CONTACT = "http://127.0.0.1/deleteContact.php?id=";

    //Keys that will be used to send the request to php scripts
    public static final String KEY_EMP_ID = "_id";
    public static final String KEY_EMP_NAME = "firstName";
    public static final String KEY_EMP_DESG = "lastName";
    public static final String KEY_EMP_SAL = "phone";

    //JSON Tags
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_ID = "_id";
    public static final String TAG_NAME = "firstName";
    public static final String TAG_DESG = "lastName";
    public static final String TAG_SAL = "phone";

    //employee id to pass with intent
    public static final String CONTACT_ID = "contact_id";
}
