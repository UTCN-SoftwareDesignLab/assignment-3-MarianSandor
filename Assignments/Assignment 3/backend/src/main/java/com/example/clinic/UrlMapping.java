package com.example.clinic;

public class UrlMapping {
    public static final String API_PATH = "/api";
    public static final String ENTITY = "/{id}";
    public static final String SEARCH = "/search";
    public static final String QUERY = "/{query}";

    public static final String AUTH = API_PATH + "/auth";
    public static final String SIGN_IN = "/sign-in";
    public static final String SIGN_UP = "/sign-up";

    public static final String PATIENTS = API_PATH + "/patients";
    public static final String USERS = API_PATH + "/users";
    public static final String CONSULTATIONS = "/consultations";

    public static final String AVAILABLE = "/available";

    public static final String MESSAGE = "/message";
    public static final String TOPIC = "/topic";
    public static final String ALERTS = "/alerts";
}
