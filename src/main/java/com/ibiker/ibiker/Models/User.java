package com.ibiker.ibiker.Models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {

    @Id
    private ObjectId _id;

    @Indexed(unique = true)
    private String email;

    private String password;
    private Long registerTimeStamp;

    public User(ObjectId id, String email, String password, Long registerTimeStamp) {
        this._id = id;
        this.email = email;
        this.password = password;
        this.registerTimeStamp = registerTimeStamp;
    }

    public User() {
    }

    public ObjectId getId() {
        return _id;
    }

    public void setId(ObjectId id) {
        this._id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getRegisterTimeStamp() {
        return registerTimeStamp;
    }

    public void setRegisterTimeStamp(Long registerTimeStamp) {
        this.registerTimeStamp = registerTimeStamp;
    }
}
