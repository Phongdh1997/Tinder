package com.example.rest.service;

import com.example.rest.model.UserPojo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SigninService {

    class SigninResponse {

    }

    class SignBody {
        public SignBody() {
        }

        public SignBody(String email, String password) {
            this.email = email;
            this.password = password;
        }

        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("password")
        @Expose
        private String password;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    @POST("/api/login")
    Call<SigninResponse> login(@Body SignBody body);

}
