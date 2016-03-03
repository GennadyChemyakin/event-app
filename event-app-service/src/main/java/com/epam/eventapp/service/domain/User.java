package com.epam.eventapp.service.domain;


import java.util.Optional;

/**
 * class describes User domain
 */
public class User {

    private final int id;
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

    private User(UserBuilder builder) {
        this.id = builder.id;
        this.username = builder.username;
        this.password = builder.password;
        this.email = builder.email;
        this.name = Optional.ofNullable(builder.name);
        this.surname = Optional.ofNullable(builder.surname);
        this.gender = Optional.ofNullable(builder.gender);
        this.photo = Optional.ofNullable(builder.photo);
        this.country = Optional.ofNullable(builder.country);
        this.city = Optional.ofNullable(builder.city);
        this.bio = Optional.ofNullable(builder.bio);
    }

    public static UserBuilder builder(String username, String email) {
        return new UserBuilder(username, email);
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public static class UserBuilder {
        private int id;
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

        private UserBuilder(String username, String email) {
            this.username = username;
            this.email = email;
        }

        private UserBuilder() {
        }

        public UserBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder id(int id) {
            this.id = id;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder surname(String surname) {
            this.surname = surname;
            return this;
        }

        public UserBuilder gender(String gender) {
            this.gender = gender;
            return this;
        }

        public UserBuilder photo(byte[] photo) {
            this.photo = photo;
            return this;
        }

        public UserBuilder country(String country) {
            this.country = country;
            return this;
        }

        public UserBuilder city(String city) {
            this.city = city;
            return this;
        }

        public UserBuilder bio(String bio) {
            this.bio = bio;
            return this;
        }

        public User build() {
            return new User(this);
        }

    }

    public int getId() {
        return id;
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
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", gender='" + gender + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", bio='" + bio + '\'' +
                '}';
    }
}
