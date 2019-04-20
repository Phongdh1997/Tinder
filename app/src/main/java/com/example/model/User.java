package com.example.model;

public class User {
    private int id;
    private String phone;
    private String mail;
    private String password;
    private String name;
    private String decription;
    private String age;
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

    public User(String mail, String password, String name, String age, String gender) {
        this.mail = mail;
        this.password = password;
        this.name = name;
        this.age = age;
        this.gender = gender;
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

    public void setPassword(String password) {
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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
