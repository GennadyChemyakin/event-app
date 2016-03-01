package com.epam.eventappweb.model;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import jdk.nashorn.internal.ir.annotations.Immutable;

@Immutable
@JsonDeserialize(builder = UserVO.Builder.class)
public final class UserVO {

    private final String username;
    private final String password;
    private final String email;
    private final String name;
    private final String surname;
    private final String gender;
    private final byte[] photo;
    private final String country;
    private final String city;
    private final String bio;

    private UserVO(Builder builder) {
        this.username = builder.username;
        this.password = builder.password;
        this.email = builder.email;
        this.name = builder.name;
        this.surname = builder.surname;
        this.bio    = builder.bio;
        this.city   = builder.city;
        this.country = builder.country;
        this.gender  = builder.gender;
        this.photo   = builder.photo;
    }

    public static Builder builder(String username, String email){
        return new Builder(username, email);
    }

    public static Builder builder(){
        return new Builder();
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {

        private String username;
        private String email;
        private String password;
        private String name;
        private String surname;
        private String gender;
        private byte[] photo;
        private String country;
        private String city;
        private String bio;

        private Builder() {

        }

        public Builder(String username, String email) {
            this.username = username;
            this.email = email;
        }

        public Builder username(String username){
            this.username = username;
            return this;
        }

        public Builder email(String email){
            this.email = email;
            return this;
        }

        public Builder password(String password){
            this.password = password;
            return this;
        }

        public Builder name(String name){
            this.name = name;
            return this;
        }

        public Builder surname(String surname){
            this.surname = surname;
            return this;
        }

        public Builder bio (String bio){
            this.bio = bio;
            return this;
        }

        public Builder country(String country){
            this.country = country;
            return this;
        }

        public Builder city(String city){
            this.city = city;
            return this;
        }

        public Builder gender(String gender){
            this.gender = gender;
            return this;
        }

        public Builder photo(byte[] photo){
            this.photo = photo;
            return this;
        }


        public UserVO build(){
            return new UserVO(this);
        }

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getGender() { return gender; }

    public byte[] getPhoto() { return photo;  }

    public String getCountry() { return country; }

    public String getCity() { return city; }

    public String getBio() { return bio;  }

    @Override
    public String toString() {
        return "User{" +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", photo='" + (photo == null ? false : photo.length != 0) + '\'' +
                '}';
    }

}
