package com.example.retooneplaces;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialog;


public class bottomSheetPlace extends BottomSheetDialog {

    private TextView bottomPlace;
    private TextView bottomAddress;
    private RatingBar bottomRatingBar;
    private ImageView bottomImage;
    private Button bottomButton;

    public bottomSheetPlace(@NonNull Context context, int theme) {
        super(context, theme);
    }

    public void initialize(View v){
        bottomPlace = v.findViewById(R.id.bottomPlace);
        bottomAddress = v.findViewById(R.id.bottomAddress);
        bottomRatingBar = v.findViewById(R.id.bottomRatingBar);
        bottomImage = v.findViewById(R.id.bottomImage);
        bottomButton = v.findViewById(R.id.bottomButton);
    }

    public TextView getBottomPlace() {
        return bottomPlace;
    }

    public void setBottomPlace(TextView bottomPlace) {
        this.bottomPlace = bottomPlace;
    }

    public TextView getBottomAddress() {
        return bottomAddress;
    }

    public void setBottomAddress(TextView bottomAddress) {
        this.bottomAddress = bottomAddress;
    }

    public RatingBar getBottomRatingBar() {
        return bottomRatingBar;
    }

    public void setBottomRatingBar(RatingBar bottomRatingBar) {
        this.bottomRatingBar = bottomRatingBar;
    }

    public ImageView getBottomImage() {
        return bottomImage;
    }

    public void setBottomImage(ImageView bottomImage) {
        this.bottomImage = bottomImage;
    }

    public Button getBottomButton() {
        return bottomButton;
    }

    public void setBottomButton(Button bottomButton) {
        this.bottomButton = bottomButton;
    }


}
