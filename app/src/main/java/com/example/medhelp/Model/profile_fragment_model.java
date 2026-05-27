package com.example.medhelp.Model;

public class profile_fragment_model {
    private int profile_list_image;
    private String profile_list_text;

    public profile_fragment_model(int profile_list_image, String profile_list_text) {
        this.profile_list_image = profile_list_image;
        this.profile_list_text = profile_list_text;
    }

    public int getProfile_list_image() {
        return profile_list_image;
    }

    public void setProfile_list_image(int profile_list_image) {
        this.profile_list_image = profile_list_image;
    }

    public String getProfile_list_text() {
        return profile_list_text;
    }

    public void setProfile_list_text(String profile_list_text) {
        this.profile_list_text = profile_list_text;
    }
}
