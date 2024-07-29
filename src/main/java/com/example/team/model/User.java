package com.example.team.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="id")
    private UUID user_id;
    @Column(name = "full_name")
    private String full_name;
    @Column(name = "mob_num")
    private String mob_num;
    @Column(name = "pan_num")
    private String pan_num;
    @Column(name = "manager_id")
    private UUID manager_id;
    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp created_at;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updated_at;
    @Column(name="is_active")
    private boolean is_active = true;

    public User() {
    }

    public User(UUID user_id, String full_name, String mob_num, String pan_num, UUID manager_id, Timestamp created_at, Timestamp updated_at, boolean is_active) {
        this.user_id = user_id;
        this.full_name = full_name;
        this.mob_num = mob_num;
        this.pan_num = pan_num;
        this.manager_id = manager_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.is_active = is_active;
    }

    public UUID getUser_id() {
        return user_id;
    }

    public void setUser_id(UUID user_id) {
        this.user_id = user_id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getMob_num() {
        return mob_num;
    }

    public void setMob_num(String mob_num) {
        this.mob_num = mob_num;
    }

    public String getPan_num() {
        return pan_num;
    }

    public void setPan_num(String pan_num) {
        this.pan_num = pan_num;
    }

    public UUID getManager_id() {
        return manager_id;
    }

    public void setManager_id(UUID manager_id) {
        this.manager_id = manager_id;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }
}
