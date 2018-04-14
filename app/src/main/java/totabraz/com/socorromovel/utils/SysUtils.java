package totabraz.com.socorromovel.utils;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by totabraz on 13/04/18.
 */

public abstract class SysUtils {
    /**
     *  Firebase constants
     */

    public static final String PUBLIC_ROOT = "public";
    public static final String SEMI_PUBLIC_ROOT = "semi_public";
    public static final String PRIVATE_ROOT = "private";

    public static final String FB_EMERGENCY_NUMBERS = PUBLIC_ROOT + "/emergency_numbers";
    public static final String FB_ANONYMOUS_REPORT = SEMI_PUBLIC_ROOT +"/anonymous_report";
    public static final String FB_REPORTED_PHONES = PUBLIC_ROOT + "/reported_mobile";

    public static final String FB_USERS = PRIVATE_ROOT + "/user";
    /**
     *  Activities KEYS
     */
    public static final String KEY_MSG_SEND_ANONY_REPORT = "key_anonymous";

    public static final String PHONE_ADD = "Celular já cadastrado";
    public static final String PHONE_STOLED = "Celular Roubado";







}