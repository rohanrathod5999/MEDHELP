package com.example.medhelp.Model;

import android.widget.ImageView;
import android.widget.TextView;

public class offer_fragment_model {
   private int cupoonImage;
   private String cupponTextHeader;
   private String cupponDescription;
   private String cupponNumber;

    public offer_fragment_model(int cupoonImage, String cupponTextHeader, String cupponDescription, String cupponNumber) {
        this.cupoonImage = cupoonImage;
        this.cupponTextHeader = cupponTextHeader;
        this.cupponDescription = cupponDescription;
        this.cupponNumber = cupponNumber;
    }

    public int getCupoonImage() {
        return cupoonImage;
    }

    public void setCupoonImage(int cupoonImage) {
        this.cupoonImage = cupoonImage;
    }

    public String getCupponTextHeader() {
        return cupponTextHeader;
    }

    public void setCupponTextHeader(String cupponTextHeader) {
        this.cupponTextHeader = cupponTextHeader;
    }

    public String getCupponDescription() {
        return cupponDescription;
    }

    public void setCupponDescription(String cupponDescription) {
        this.cupponDescription = cupponDescription;
    }

    public String getCupponNumber() {
        return cupponNumber;
    }

    public void setCupponNumber(String cupponNumber) {
        this.cupponNumber = cupponNumber;
    }
}
