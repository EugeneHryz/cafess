package com.eugene.cafe.entity;

import java.io.InputStream;

public class Client extends Entity {

    private String name;

    private String surname;

    private ClientRole role;

    private ClientStatus status;

    private double balance;

    private InputStream profileImage;

    public Client(String name, String surname, ClientRole role, ClientStatus status,
                  double balance, InputStream profileImage) {

        this.name = name;
        this.surname = surname;
        this.role = role;
        this.status = status;
        this.balance = balance;
        this.profileImage = profileImage;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public ClientRole getRole() {
        return role;
    }

    public ClientStatus getStatus() {
        return status;
    }

    public double getBalance() {
        return balance;
    }

    public InputStream getProfileImage() {
        return profileImage;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setRole(ClientRole role) {
        this.role = role;
    }

    public void setStatus(ClientStatus status) {
        this.status = status;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setProfileImage(InputStream profileImage) {
        this.profileImage = profileImage;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("id: ")
                .append(id)
                .append(", name: ")
                .append(name)
                .append(", surname: ")
                .append(surname)
                .append(", client role: ")
                .append(role.toString())
                .append(", balance: ")
                .append(balance);
        return builder.toString();
    }

    // todo: implement hashCode(), ...

    public static class Builder {

        private long id;

        private String name;

        private String surname;

        private ClientRole role;

        private ClientStatus status;

        private double balance;

        private InputStream profileImage;

        public Builder() {
        }

        public Builder setId(long id) {
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

        public Builder setRole(ClientRole role) {
            this.role = role;
            return this;
        }

        public Builder setStatus(ClientStatus status) {
            this.status = status;
            return this;
        }

        public Builder setBalance(double balance) {
            this.balance = balance;
            return this;
        }

        public Builder setProfileImage(InputStream profileImage) {
            this.profileImage = profileImage;
            return this;
        }

        public Client build() {
            Client client = new Client(name, surname, role, status, balance, profileImage);
            client.setId(id);
            return client;
        }
    }
}