package com.example.team.model;

import java.util.List;
import java.util.UUID;

public class UpdateUserRequest {
    private List<UUID> user_ids;
    private User update_data;

    public UpdateUserRequest(List<UUID> user_ids, User update_data) {
        this.user_ids = user_ids;
        this.update_data = update_data;
    }

    public List<UUID> getUser_ids() {
        return user_ids;
    }

    public void setUser_ids(List<UUID> user_ids) {
        this.user_ids = user_ids;
    }

    public User getUpdate_data() {
        return update_data;
    }

    public void setUpdate_data(User update_data) {
        this.update_data = update_data;
    }


}
