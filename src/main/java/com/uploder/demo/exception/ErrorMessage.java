package com.uploder.demo.exception;

public interface ErrorMessage {

    String AUTHENTICATION_USER_NOT_VALID = "These credentials do not match our records !";

    String USER_ALREADY_EXISTS = "User already exist ";

    String REGISTER_PROCESS_NOT_VALID = "There is an account with that email address";

    String CHANGE_PASSWORD_ERROR = "The old password you have entered is incorrect";

    String USER_NOT_FOUND_BY_EMAIL = "Couldn't find your email !";
}
