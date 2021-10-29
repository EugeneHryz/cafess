package com.eugene.cafe.model.dto;

import com.eugene.cafe.entity.User;
import com.eugene.cafe.entity.UserRole;
import com.eugene.cafe.entity.UserStatus;

public class UserDto {

    private int id;
    private String name;
    private String surname;
    private UserRole role;
    private UserStatus status;
    private String email;
    private double balance;
    private String profileImagePath;

    public UserDto(User user) {

        this.id = user.getId();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.role = user.getRole();
        this.status = user.getStatus();
        this.email = user.getEmail();
        this.balance = user.getBalance();
        this.profileImagePath = user.getProfileImagePath();
    }

    public int getId() {
        return id;
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

    public double getBalance() {
        return balance;
    }

    public String getProfileImagePath() {
        return profileImagePath;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setProfileImagePath(String profileImagePath) {
        this.profileImagePath = profileImagePath;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("UserDto{id: ")
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
                .append(balance)
                .append(", profileImagePath: ")
                .append(profileImagePath)
                .append("}");

        return builder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof UserDto userDto) {
            return id == userDto.id && name.equals(userDto.name) && surname.equals(userDto.surname)
                    && role == userDto.role && status == userDto.status && email.equals(userDto.email)
                    && balance == userDto.balance && profileImagePath.equals(userDto.profileImagePath);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hashCode = Integer.hashCode(id);
        hashCode = hashCode * 31 + name.hashCode();
        hashCode = hashCode * 31 + surname.hashCode();
        hashCode = hashCode * 31 + role.hashCode();
        hashCode = hashCode * 31 + status.hashCode();
        hashCode = hashCode * 31 + email.hashCode();
        hashCode = hashCode * 31 + Double.hashCode(balance);
        hashCode = hashCode * 31 + profileImagePath.hashCode();

        return hashCode;
    }
}
