package com.example.model;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.rest.RetrofitClient;
import com.example.rest.model.UserPojo;
import com.example.rest.service.SearchFriendService;
import com.example.rest.service.SigninService;
import com.example.rest.service.SignupService;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

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
        id = -1;
        authen_token = "No Data";
        phone = "No Data";
        mail = "No Data";
        password = "No Data";
        name = "No Data";
        decription = "No Data";
        age = 0;
        gender = "No Data";
        longtitude = -1;
        latitude = -1;
        max_distance = -1;
        min_age = -1;
        max_age = -1;
        is_active = false;
        is_banned = false;
        ban_reason = "No Data";
        exprired_ban = "No Data";
        created_at = "No Data";
        updated_at = "No Data";
    }

    public User(String mail, String password, String name, int age, String gender) {
        this.mail = mail;
        this.password = password;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public User(int id, String mail, String password, String name, int age, String gender) {
        this.mail = mail;
        this.password = password;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.id = id;
    }

    public User(int id, String mail, String password, String name, int age, String gender, String phone, String decription) {
        this.mail = mail;
        this.password = password;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.id = id;
        this.phone = phone;
        this.decription = decription;
    }

    public User(SigninService.User user) {
        this.name = user.getName();
        this.mail = user.getEmail();
        this.age = user.getAge();
        this.id = user.getId();
        this.phone = user.getPhone();
        this.decription = user.getDescription();
        this.gender = user.getGender();
    }

    public User(SearchFriendService.User user) {
        this.name = user.getName();
        this.mail = user.getEmail();
        this.age = user.getAge();
        this.id = user.getId();
        this.phone = user.getPhone();
        this.decription = user.getDescription();
        this.gender = user.getGender();
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
        user.setAuthen_token(sharedPreferences.getString(AUTHEN_TOKEN, ""));
        user.setIs_active(sharedPreferences.getBoolean(IS_ACTIVE, false));
        user.setIs_banned(sharedPreferences.getBoolean(IS_BANNED, false));

        Log.d("token", user.getAuthen_token());
        return user;
    }

    public void storeToLocal(SharedPreferences.Editor editor) {
        editor.putInt(ID, this.id);
        editor.putString(MAIL, this.mail);
        editor.putString(PASSWORD, this.password);
        editor.putString(NAME, this.name);
        editor.putInt(AGE, this.age);
        editor.putString(GENDER, this.gender);
        editor.putString(PHONE, this.phone);
        editor.putString(DESCRIPTION, this.decription);
        editor.putString(AUTHEN_TOKEN, this.authen_token);
        editor.putBoolean(IS_ACTIVE, this.is_active);
        editor.putBoolean(IS_BANNED, this.is_banned);
        editor.apply();
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
        Log.d("password", password);
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

    public Bundle toBundle() {
        Bundle user = new Bundle();
        user.putInt("id", id);
        user.putString("authen_token", authen_token);
        user.putString("phone", phone);
        user.putString("mail", mail);
        user.putString("password", password);
        user.putString("name", name);
        user.putString("decription", decription);
        user.putString("gender", gender);
        user.putInt("age", age);
        return user;
    }

    public static User getUserFromBundle(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        User user = new User(
                bundle.getInt("id"),
                bundle.getString("mail"),
                bundle.getString("password"),
                bundle.getString("name"),
                bundle.getInt("age"),
                bundle.getString("gender"),
                bundle.getString("phone"),
                bundle.getString("decription"));
        user.setAuthen_token(bundle.getString("authen_token"));
        return user;
    }

    /**
     * Description: perform dislike a friend with friendId
     * @param friendId: id of friend is disliked
     */
    public void dislikeFriend(int friendId) {
        Log.d("dislike friend", "id = " + friendId);
        //TODO: call API dislike friend here
    }

    /**
     * Description: perform like a friend with friendId
     * @param friendId: id of friend is liked
     */
    public void likeFriend(int friendId) {
        Log.d("like friend", "id = " + friendId);
        //TODO: call API like friend here
    }

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

    public void setPassword(String password) throws Exception {
        this.password = new String(Hex.encodeHex(DigestUtils.md5(password)));
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

    public String getHeaderAuthenToken() {
        return "Barer " + this.authen_token;
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
