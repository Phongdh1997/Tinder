package com.example.rest.service;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface PostImageService {
    @Multipart
    @POST("upload/upload_image")
    Call<ResponseMessage> upImage(@Header("Authorization") String authorization, @Part MultipartBody.Part file , @Part("num") RequestBody num);

    class ResponseMessage {
        @SerializedName("message")
        @Expose
        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

//    class UpImageBody {
//
//        public UpImageBody(MultipartBody.Part image, Integer num) {
//            this.image = image;
//            this.num = num;
//        }
//
//        @SerializedName("image")
//        @Expose
//        private MultipartBody.Part image;
//        @SerializedName("num")
//        @Expose
//        private Integer num;
//
//        public MultipartBody.Part getImage() {
//            return image;
//        }
//
//        public void setImage(MultipartBody.Part image) {
//            this.image = image;
//        }
//
//        public Integer getNum() {
//            return num;
//        }
//
//        public void setNum(Integer num) {
//            this.num = num;
//        }
//    }
}
