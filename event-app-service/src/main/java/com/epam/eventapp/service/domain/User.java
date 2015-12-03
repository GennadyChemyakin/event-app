package com.epam.eventapp.service.domain;

/**
 * domain class describes SEC_USER table
 */
public class User {
    private final int id;
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

    private User(UserBuilder builder) {
        this.id = builder.id;
        this.username = builder.username;
        this.password = builder.password;
        this.email = builder.email;
        this.name = builder.name;
        this.surname = builder.surname;
        this.gender = builder.gender;
        this.photo = builder.photo;
        this.country = builder.country;
        this.city = builder.city;
        this.bio = builder.bio;
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

        public UserBuilder(String username, String email){
            this.username = username;
            this.email = email;
        }

        public UserBuilder id(int id){
            this.id = id;
            return this;
        }

        public UserBuilder password(String password){
            this.password = password;
            return this;
        }

        public UserBuilder name(String name){
            this.name = name;
            return this;
        }

        public UserBuilder surname(String surname){
            this.surname = surname;
            return this;
        }

        public UserBuilder gender(String gender){
            this.gender = gender;
            return this;
        }

        public UserBuilder photo(byte[] photo){
            this.photo = photo;
            return this;
        }

        public UserBuilder country(String country){
            this.country = country;
            return this;
        }

        public UserBuilder city(String city){
            this.city = city;
            return this;
        }

        public UserBuilder bio(String bio){
            this.bio = bio;
            return this;
        }

        public User build(){
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

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getGender() {
        return gender;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getBio() {
        return bio;
    }
}
