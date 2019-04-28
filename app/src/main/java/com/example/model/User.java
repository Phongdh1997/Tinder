package com.example.model;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.rest.RetrofitClient;
import com.example.rest.model.UserPojo;
import com.example.rest.service.SigninService;
import com.example.rest.service.SignupService;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class User {

    public static final String NAME = "name";
    public static final String PHONE = "phone";
    public static final String ID = "id";
    public static final String AUTHEN_TOKEN = "authen_token";
    public static final String MAIL = "mail";
    public static final String PASSWORD = "password";
    public static final String DESCRIPTION = "description";
    public static final String AGE = "age";
    public static final String GENDER = "gender";
    public static final String IS_ACTIVE = "is_active";
    public static final String IS_BANNED = "is_banned";
    public static final int INT_NULL = -100;

    private int id;
    private String authen_token;
    private String phone;
    private String mail;
    private String password;
    private String name;
    private String decription;
    private int age;
    private String gender;
    private int longtitude;
    private int latitude;
    private int max_distance;
    private int min_age;
    private int max_age;
    private boolean is_active;
    private boolean is_banned;
    private String ban_reason;
    private String exprired_ban;
    private String created_at;
    private String updated_at;

    // call back
    private OnRegisterCallBack registerCallBack;
    private OnLoginCallBack onLoginCallBack;

    public void setRegisterCallBack(OnRegisterCallBack registerCallBack) {
        this.registerCallBack = registerCallBack;
    }

    public void setOnLoginCallBack(OnLoginCallBack onLoginCallBack) {
        this.onLoginCallBack = onLoginCallBack;
    }

    public User() {

    }

    public User(String mail, String password, String name, int age, String gender) {
        this.mail = mail;
        this.password = password;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public User(SigninService.User user) {
        this.name = user.getName();
        this.mail = user.getEmail();
        this.age = user.getAge();
        this.id = user.getId();
        this.phone = user.getPhone();
        this.decription = user.getDescription();
    }

    public static User getLocalUser(SharedPreferences sharedPreferences) {
        User user = new User();
        int id = sharedPreferences.getInt(ID, INT_NULL);
        if (id == INT_NULL) {
            return null;
        }
        user.setId(id);
        user.setMail(sharedPreferences.getString(MAIL, ""));
        user.setHashedPassword(sharedPreferences.getString(PASSWORD, ""));
        user.setName(sharedPreferences.getString(NAME, ""));
        user.setAge(sharedPreferences.getInt(AGE, 0));
        user.setGender(sharedPreferences.getString(GENDER, ""));
        user.setPhone(sharedPreferences.getString(PHONE, ""));
        user.setDecription(sharedPreferences.getString(DESCRIPTION, ""));
        return user;
    }

    public void storeToLocal(SharedPreferences.Editor editor) {
        editor.putString(MAIL, this.mail);
        editor.putString(PASSWORD, this.password);
        editor.putInt(AGE, this.age);
        editor.putInt(ID, this.id);
        editor.putString(PHONE, this.phone);
        editor.putString(DESCRIPTION, this.decription);
        editor.apply();
        Log.d("save", "saveAthenToken: ");
    }

    public void register() {
        SignupService signupService = RetrofitClient.getSignupService();
        signupService.getNonce().enqueue(new Callback<SignupService.Nonce>() {
            @Override
            public void onResponse(Call<SignupService.Nonce> call, Response<SignupService.Nonce> response) {
                if (response.body() != null) {
                    String nonce = response.body().getNonce();
                    if (nonce != null) {
                        // perform sign up
                        performRegister(nonce);
                        return;
                    }
                }
                if (registerCallBack != null) {
                    registerCallBack.onRegisterFail(OnRegisterCallBack.NONCE_NULL);
                }
            }

            @Override
            public void onFailure(Call<SignupService.Nonce> call, Throwable t) {
                t.printStackTrace();
                if (registerCallBack != null) {
                    registerCallBack.onRegisterFail(OnRegisterCallBack.REQUEST_FAIL);
                }
            }
        });
    }

    private void performRegister(String nonce) {
        UserPojo userPojo = new UserPojo(name, mail, password, gender, age, nonce);
        SignupService signupService = RetrofitClient.getSignupService();
        signupService.register(userPojo).enqueue(new Callback<SignupService.Message>() {
            @Override
            public void onResponse(Call<SignupService.Message> call, Response<SignupService.Message> response) {
                Log.i("responseCode", " " + response.code());
                switch (response.code()) {
                    case OnRegisterCallBack.SUCCESS:
                        if (registerCallBack != null) {
                            registerCallBack.onRegisterSuccess(response.body());
                        }
                        break;
                        default:
                            if (registerCallBack != null) {
                                registerCallBack.onRegisterFail(response.code());
                            }
                }
            }

            @Override
            public void onFailure(Call<SignupService.Message> call, Throwable t) {
                t.printStackTrace();
                if (registerCallBack != null) {
                    registerCallBack.onRegisterFail(OnRegisterCallBack.REQUEST_FAIL);
                }
            }
        });
    }

    public void login() {
        if (this.mail == null || this.password == null) {
            return;
        }
        Log.d("login: ", "email: " + this.mail + "; pass: "+ this.password);
        SigninService signinService = RetrofitClient.getSigninService();
        signinService.login(new SigninService.SignBody(this.mail, this.password))
                .enqueue(new Callback<SigninService.SigninResponse>() {
            @Override
            public void onResponse(Call<SigninService.SigninResponse> call, Response<SigninService.SigninResponse> response) {
                Log.d("Sign In", "response code: " + response.code());
                switch (response.code()) {
                    case OnLoginCallBack.SUCCESS:
                        if (onLoginCallBack != null) {
                            onLoginCallBack.onLoginSuccess(response.body());
                        }
                        break;
                    default:
                        if (onLoginCallBack != null) {
                            onLoginCallBack.onLoginFail(response.code());
                        }
                }
            }

            @Override
            public void onFailure(Call<SigninService.SigninResponse> call, Throwable t) {
                t.printStackTrace();
                if (onLoginCallBack != null) {
                    onLoginCallBack.onLoginFail(OnLoginCallBack.REQUEST_FAIL);
                }
            }
        });
    }

    public static interface OnRegisterCallBack {
        int NONCE_NULL = -1;
        int SUCCESS = 200;
        int EMAIL_INVALID = 400;
        int REGISTED_EMAIL = 409;
        int SERVER_ERROR = 500;
        int REQUEST_FAIL = -100;

        void onRegisterSuccess(SignupService.Message message);

        void onRegisterFail(int error);
    }

    public static interface OnLoginCallBack {
        int SUCCESS = 200;
        int REQUEST_FAIL = -100;

        void onLoginSuccess(SigninService.SigninResponse message);

        void onLoginFail(int error);
    }

    // getter / setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] bytesOfMessage = password.getBytes("UTF-8");
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hashPass = md.digest(bytesOfMessage);
        this.password = new String(hashPass);
    }

    public void setHashedPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAuthen_token() {
        return authen_token;
    }

    public void setAuthen_token(String authen_token) {
        this.authen_token = authen_token;
    }

    public int getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(int longtitude) {
        this.longtitude = longtitude;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public int getMax_distance() {
        return max_distance;
    }

    public void setMax_distance(int max_distance) {
        this.max_distance = max_distance;
    }

    public int getMin_age() {
        return min_age;
    }

    public void setMin_age(int min_age) {
        this.min_age = min_age;
    }

    public int getMax_age() {
        return max_age;
    }

    public void setMax_age(int max_age) {
        this.max_age = max_age;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public boolean isIs_banned() {
        return is_banned;
    }

    public void setIs_banned(boolean is_banned) {
        this.is_banned = is_banned;
    }

    public String getBan_reason() {
        return ban_reason;
    }

    public void setBan_reason(String ban_reason) {
        this.ban_reason = ban_reason;
    }

    public String getExprired_ban() {
        return exprired_ban;
    }

    public void setExprired_ban(String exprired_ban) {
        this.exprired_ban = exprired_ban;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
