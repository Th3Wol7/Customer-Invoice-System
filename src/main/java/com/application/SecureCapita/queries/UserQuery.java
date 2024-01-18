package com.application.SecureCapita.queries;

public class UserQuery {
    public static final String INSERT_USER_QUERY = "INSERT INTO Users(first_name, last_name, email, password) " +
            "VALUES (:firstName, :lastName, :email, :password)";
    public static final String INSERT_ACCOUNT_VERIFICATION_QUERY = "SELECT COUNT(*) WHERE email = :email";
    public static final String COUNT_USER_EMAIL_QUERY = "INSERT INTO AccountVerifications (user_id, url) VALUES (:userId, :url)";
}
