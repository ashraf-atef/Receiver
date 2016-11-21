package com.example.ashraf.receiver.Login.SignInUpFragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.ashraf.receiver.Login.GoogleSigninFragment.GoobleSigninFragment;
import com.example.ashraf.receiver.Model.GoogleAccount;
import com.example.ashraf.receiver.R;
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

/**
 * Created by ashraf on 11/21/2016.
 */

public class SignInUpFragmentPresenterImpl implements SignInUpFragmentPresenter {

    GoogleSignInOptions gso;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "SignInActivity";
    SignInUpFragment signInUpFragment;

    public SignInUpFragmentPresenterImpl(SignInUpFragment signInUpFragment) {
        this.signInUpFragment = signInUpFragment;
    }

    @Override
    public void initView() {
        signInUpFragment.signUpWithGoogleButton = (Button) signInUpFragment.rootView.findViewById(R.id.singUpWtithGoogleButton);
        signInUpFragment.signUpWithGoogleButton.setOnClickListener(signInUpFragment);
    }

    @Override
    public void loginWithGoogleAccount() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(signInUpFragment.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(signInUpFragment.getContext())
                .enableAutoManage(signInUpFragment.getActivity() /* FragmentActivity */, signInUpFragment /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
        signInUpFragment.dialog = ProgressDialog.show(signInUpFragment.getContext(), "",
                "Loading. Please wait...", true);
        signIn();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        signInUpFragment.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();

                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }

    @Override
    public void onStop() {
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onDestroy() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.stopAutoManage(signInUpFragment.getActivity());
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(signInUpFragment.getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        GoogleAccount googleAccount = new GoogleAccount();
                        googleAccount.setIdTooken(acct.getIdToken());
                        googleAccount.setDisplayName(acct.getDisplayName());
                        googleAccount.setEmial(acct.getEmail());
                        googleAccount.setPhotoUrl(acct.getPhotoUrl().toString());

                        android.support.v4.app.FragmentManager fm1 = signInUpFragment.getActivity().getSupportFragmentManager();
                        android.support.v4.app.FragmentTransaction ft1 = fm1.beginTransaction();


                        GoobleSigninFragment goobleSigninFragment = new GoobleSigninFragment();
                        goobleSigninFragment.set_bundle(googleAccount);

                        ft1.replace(R.id.frame, goobleSigninFragment).addToBackStack(null).commit();

                        signInUpFragment.dialog.dismiss();
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(signInUpFragment.getContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // ...
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }
}
