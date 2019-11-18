package com.rl.dogs.Model.Net;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class BreedImageResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private List<String> message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }
}
