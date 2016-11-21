package com.example.ashraf.receiver.Login.GoogleSigninFragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ashraf.receiver.Main.MainActivity;
import com.example.ashraf.receiver.Model.GoogleAccount;
import com.example.ashraf.receiver.R;
import com.example.ashraf.receiver.SharedPreference.MySharedPreference;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class GoobleSigninFragment extends Fragment implements
        View.OnClickListener {

    static Bundle bundle;

    public static void set_bundle(GoogleAccount googleAccount) {
        bundle = new Bundle();
        bundle.putSerializable("googleAccount", googleAccount);
    }

    public static Bundle getBundle() {
        return bundle;
    }

    Button continueButton, backButton;
    TextView usernameTextview;
    CircleImageView userCircleImageview;
    GoogleAccount googleAccount;
    View rootview;
    GoogleSigninFragmentPresenter googleSigninFragmentPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_gooble_signin, container, false);
        googleSigninFragmentPresenter = new GoogleSigninFragmentPresenterImpl(this);
        googleSigninFragmentPresenter.initView();
        return rootview;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.continueButton:
                googleSigninFragmentPresenter.continueAction();
                break;
            case R.id.backButton:
                googleSigninFragmentPresenter.backAction();
                break;

        }
    }
}
