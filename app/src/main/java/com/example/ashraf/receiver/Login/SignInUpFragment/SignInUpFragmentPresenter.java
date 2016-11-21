package com.example.ashraf.receiver.Login.SignInUpFragment;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;

/**
 * Created by ashraf on 11/21/2016.
 */

public interface SignInUpFragmentPresenter {
    void initView ();
    void loginWithGoogleAccount();
    void signIn();
    void onActivityResult(int requestCode, int resultCode, Intent data);
    void onStop();
    void onDestroy();
    void firebaseAuthWithGoogle(final GoogleSignInAccount acct);
    void onConnectionFailed(@NonNull ConnectionResult connectionResult);

}
