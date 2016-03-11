package com.epam.eventappweb.model;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import jdk.nashorn.internal.ir.annotations.Immutable;

import java.util.Optional;

@Immutable
@JsonDeserialize(builder = UserVO.Builder.class)
public final class UserVO {

    private final String username;
    private final String password;
    private final String email;
    private final Optional<String> name;
    private final Optional<String> surname;
    private final Optional<String> gender;
    private final Optional<byte[]> photo;
    private final Optional<String> country;
    private final Optional<String> city;
    private final Optional<String> bio;

    private UserVO(Builder builder) {
        this.username = builder.username;
        this.password = builder.password;
        this.email = builder.email;
        this.name = Optional.ofNullable(builder.name);
        this.surname = Optional.ofNullable(builder.surname);
        this.bio = Optional.ofNullable(builder.bio);
        this.city = Optional.ofNullable(builder.city);
        this.country = Optional.ofNullable(builder.country);
        this.gender = Optional.ofNullable(builder.gender);
        this.photo = Optional.ofNullable(builder.photo);
    }

    public static Builder builder(String username, String email) {
        return new Builder(username, email);
    }

    public static Builder builder() {
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

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder surname(String surname) {
            this.surname = surname;
            return this;
        }

        public Builder bio(String bio) {
            this.bio = bio;
            return this;
        }

        public Builder country(String country) {
            this.country = country;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder gender(String gender) {
            this.gender = gender;
            return this;
        }

        public Builder photo(byte[] photo) {
            this.photo = photo;
            return this;
        }


        public UserVO build() {
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

    public Optional<String> getName() {
        return name;
    }

    public Optional<String> getSurname() {
        return surname;
    }

    public Optional<String> getGender() {
        return gender;
    }

    public Optional<byte[]> getPhoto() {
        return photo;
    }

    public Optional<String> getCountry() {
        return country;
    }

    public Optional<String> getCity() {
        return city;
    }

    public Optional<String> getBio() {
        return bio;
    }

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
                ", photo='" + (photo.isPresent() ? photo.get().length != 0 : false) + '\'' +
                '}';
    }

}
