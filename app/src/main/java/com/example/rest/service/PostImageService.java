package com.example.rest.service;

import com.example.tinder.authentication.UserAuth;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PostImageService {
    @POST("/upload/upload_image")
    Call<ResponseMessage> upImage(@Body UpImageBody upImageBody);

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

    class UpImageBody {

        public UpImageBody(String image, Integer num) {
            this.image = image;
            this.num = num;
        }

        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("num")
        @Expose
        private Integer num;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public Integer getNum() {
            return num;
        }

        public void setNum(Integer num) {
            this.num = num;
        }
    }
}
