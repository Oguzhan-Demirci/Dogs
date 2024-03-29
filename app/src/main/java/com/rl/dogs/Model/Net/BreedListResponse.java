package com.rl.dogs.Model.Net;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class BreedListResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private Map<String, String[]> message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Map<String, String[]> getMessage() {
        return message;
    }

    public void setMessage(Map<String, String[]> message) {
        this.message = message;
    }
}
