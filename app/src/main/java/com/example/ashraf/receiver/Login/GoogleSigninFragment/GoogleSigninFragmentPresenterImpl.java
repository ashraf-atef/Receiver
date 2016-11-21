package com.example.ashraf.receiver.Login.GoogleSigninFragment;

import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;

import com.example.ashraf.receiver.Main.MainActivity;
import com.example.ashraf.receiver.Model.GoogleAccount;
import com.example.ashraf.receiver.R;
import com.example.ashraf.receiver.SharedPreference.MySharedPreference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ashraf on 11/21/2016.
 */

public class GoogleSigninFragmentPresenterImpl implements GoogleSigninFragmentPresenter {

    GoobleSigninFragment goobleSigninFragment;

    public GoogleSigninFragmentPresenterImpl(GoobleSigninFragment goobleSigninFragment) {
        this.goobleSigninFragment = goobleSigninFragment;
    }

    @Override
    public void initView() {
        goobleSigninFragment.continueButton = (Button) goobleSigninFragment.rootview.findViewById(R.id.continueButton);
        goobleSigninFragment.backButton = (Button) goobleSigninFragment.rootview.findViewById(R.id.backButton);
        goobleSigninFragment.usernameTextview = (TextView) goobleSigninFragment.rootview.findViewById(R.id.usernameTextview);
        goobleSigninFragment.userCircleImageview = (CircleImageView) goobleSigninFragment.rootview.findViewById(R.id.userPhotoCircleImageView);
        goobleSigninFragment.continueButton.setOnClickListener(goobleSigninFragment);
        goobleSigninFragment.backButton.setOnClickListener(goobleSigninFragment);

        goobleSigninFragment.googleAccount = (GoogleAccount) goobleSigninFragment.getBundle().get("googleAccount");
        if (goobleSigninFragment.googleAccount.getPhotoUrl() != null)
            Picasso.with(goobleSigninFragment.getContext()).load(goobleSigninFragment.googleAccount.getPhotoUrl().toString()).placeholder(R.drawable.user).into(goobleSigninFragment.userCircleImageview);
        goobleSigninFragment.usernameTextview.setText(goobleSigninFragment.googleAccount.getDisplayName());
    }


    @Override

    public void continueAction() {
        MySharedPreference.saveGoogleAccount(goobleSigninFragment.googleAccount, goobleSigninFragment.getContext());
        MainActivity.set_intent(goobleSigninFragment.googleAccount, goobleSigninFragment.getContext());
        goobleSigninFragment.getActivity().startActivity
                (MainActivity.getmyIntent().setFlags((Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)));
    }

    @Override
    public void backAction() {
        android.support.v4.app.FragmentManager fm1 = goobleSigninFragment.getActivity().getSupportFragmentManager();
        fm1.popBackStackImmediate();
    }
}
