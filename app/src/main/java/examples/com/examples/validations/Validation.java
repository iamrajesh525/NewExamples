package examples.com.examples.validations;

/**
 * Created by Mizpah_DEV on 24-Oct-16.
 */


import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import java.util.regex.Pattern;

public class Validation {

    // Regular Expression
    // you can change the expression based on your need
    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    //private static final String PHONE_REGEX = "\\d{3}-\\d{7}";
    private static final String PHONE_REGEX =  "[789]{1}[0-9]{9}";

    private static final String NAME_REGEX = "[a-zA-Z, $str]{6,30}";
    //private static final String NAME_REGEX = "[a-zA-Z0-9, $str]{6,30}";
    private static final String PASSWORD_REGEX = "[a-zA-Z0-9]{6,12}";
    // Error Messages
    private static final String REQUIRED_MSG = "required";
    private static final String EMAIL_MSG = "invalid email";
    private static final String PHONE_MSG = "###-#######";
    public final static String TOAST_PASSWORD="Must contain min 6, max 30 characters.";
    // call this method when you need to check email validation
    public static boolean isEmailAddress(EditText editText, TextInputLayout textInputLayout, boolean required) {
        return isValid(editText,textInputLayout, EMAIL_REGEX, EMAIL_MSG, required);
    }

    // call this method when you need to check phone number validation
    public static boolean isPhoneNumber(EditText editText, TextInputLayout textInputLayout, boolean required) {
        String text = editText.getText().toString().trim();
        textInputLayout. setErrorEnabled(true);
        textInputLayout.setError(null);
        // pattern doesn't match so returning false
        if (required && !Pattern.matches(PHONE_REGEX, text))
        {
            textInputLayout.setError("Enter Valid Number");
            return false;
        };
        textInputLayout. setErrorEnabled(false);
        return true;
    }


    // return true if the input field is valid, based on the parameter passed
    public static boolean isValid(EditText editText, TextInputLayout textInputLayout, String regex, String errMsg, boolean required) {

        String text = editText.getText().toString().trim();
        // clearing the error, if it was previously set by some other values
        textInputLayout. setErrorEnabled(true);
        textInputLayout.setError(null);

        // text required and editText is blank, so return false
        if ( required && !hasText(editText,textInputLayout,true) ){
            return false;
        }
        // pattern doesn't match so returning false
        if (required && !Pattern.matches(regex, text))
        {
            textInputLayout.setError(errMsg);
            return false;
        };
        textInputLayout. setErrorEnabled(false);
        return true;
    }
public static boolean isNameValid(EditText editText, TextInputLayout textInputLayout, Boolean required)
{

    String text = editText.getText().toString().trim();
    textInputLayout. setErrorEnabled(true);
    textInputLayout.setError(null);
    if ( required && !hasText(editText,textInputLayout,true) ){
        return false;
    }
    if( required && !Pattern.matches(NAME_REGEX,text)){
        textInputLayout.setError("Must conatin a-z or A-Z and min 6, max 30 characters.");
        return false;
    }
    textInputLayout. setErrorEnabled(false);
    return true;
}
    public static boolean isPasswordValid(EditText editText, TextInputLayout textInputLayout, Boolean required)
    {

        String text = editText.getText().toString().trim();
        textInputLayout. setErrorEnabled(true);
         textInputLayout.setError(null);
        if ( required && !hasText(editText,textInputLayout,true) ){
            return false;
        }
        if( required && !Pattern.matches(PASSWORD_REGEX,text)){
            textInputLayout.setError(TOAST_PASSWORD);
            return false;
        }
        textInputLayout. setErrorEnabled(false);
        return true;
    }
    public static boolean isPasswordsMatch(EditText editText, EditText confirm_editText, TextInputLayout textInputLayout, Boolean required)
    {

        String pass = editText.getText().toString().trim();
        String c_pass = confirm_editText.getText().toString().trim();
        textInputLayout. setErrorEnabled(true);
        textInputLayout.setError(null);
        if ( required && !hasText(editText,textInputLayout,true) ){
            return false;
        }
        if( required && (!pass.equals(c_pass))){
            textInputLayout.setError("Passwords does not match.");
            return false;
        }
        textInputLayout. setErrorEnabled(false);
        return true;
    }

    // check the input field has any text or not
    // return true if it contains text otherwise false
    public static boolean hasText(EditText editText, TextInputLayout textInputLayout, Boolean required) {

        String text = editText.getText().toString().trim();
        textInputLayout. setErrorEnabled(true);
        textInputLayout.setError(null);

        // length 0 means there is no text
        if (required && text.length() == 0) {
            textInputLayout.setError(REQUIRED_MSG);
            return false;
        }
        textInputLayout.setErrorEnabled(false);
        return true;
    }




    /*
    * How to use
    *
    *  et_email = (EditText) findViewById(R.id.et_email);
        et_email.addTextChangedListener(new TextWatcher() {
            // after every change has been made to this editText, we would like to check validity
            public void afterTextChanged(Editable s) {
                Validation.isEmailAddress(et_email, input_layout_email, true);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });




          private boolean checkValidation() {
        boolean ret = true;

        if (!Validation.isNameValid(et_name, input_layout_fullname, true)) {
            ret = false;
        }
        if (!Validation.hasText(et_description, input_layout_description, true)) {
            ret = false;
        }
        if (!Validation.hasText(et_courses, input_layout_courses, true)) {
            ret = false;
        }
        if (!Validation.isEmailAddress(et_email, input_layout_email, true)) {
            ret = false;
        }
        if (!Validation.isPhoneNumber(et_mobilenumber, input_layout_mobilenumber, true)) {
            ret = false;
        }
        if (!Validation.hasText(et_landline, input_layout_landline, true)) {
            ret = false;
        }

        return ret;
    }
    * */

}