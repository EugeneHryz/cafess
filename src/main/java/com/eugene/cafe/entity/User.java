package com.eugene.cafe.entity;

import java.io.InputStream;

public class User extends AbstractEntity {

    private String name;

    private String surname;

    private UserRole role;

    private UserStatus status;

    private String email;

    private String hashedPassword;

    private double balance;

    private String profileImagePath;

    public User(String name, String surname, UserRole role, UserStatus status, String email, String hashedPassword,
                double balance, String profileImagePath) {

        this.name = name;
        this.surname = surname;
        this.role = role;
        this.status = status;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.balance = balance;
        this.profileImagePath = profileImagePath;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public UserRole getRole() {
        return role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public String getEmail() {
        return email;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public double getBalance() {
        return balance;
    }

    public String getProfileImage() {
        return profileImagePath;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setProfileImage(String profileImagePath) {
        this.profileImagePath = profileImagePath;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("id: ")
                .append(getId())
                .append(", name: ")
                .append(name)
                .append(", surname: ")
                .append(surname)
                .append(", role: ")
                .append(role.toString())
                .append(", status: ")
                .append(status.toString())
                .append(", email: ")
                .append(email)
                .append(", balance: ")
                .append(balance);
        return builder.toString();
    }

    // todo: implement hashCode(), equals()

    public static class Builder {

        private int id;

        private String name;

        private String surname;

        private UserRole role;

        private UserStatus status;

        private String email;

        private String hashedPassword;

        private double balance;

        private String profileImagePath;

        public Builder() {
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setSurname(String surname) {
            this.surname = surname;
            return this;
        }

        public Builder setRole(UserRole role) {
            this.role = role;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setHashedPassword(String hashedPassword) {
            this.hashedPassword = hashedPassword;
            return this;
        }

        public Builder setStatus(UserStatus status) {
            this.status = status;
            return this;
        }

        public Builder setBalance(double balance) {
            this.balance = balance;
            return this;
        }

        public Builder setProfileImage(String profileImagePath) {
            this.profileImagePath = profileImagePath;
            return this;
        }

        public User buildUser() {
            User user = new User(name, surname, role, status, email, hashedPassword, balance, profileImagePath);
            user.setId(id);
            return user;
        }
    }
}
