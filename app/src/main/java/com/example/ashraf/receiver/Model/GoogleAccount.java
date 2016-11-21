package com.example.ashraf.receiver.Model;

import java.io.Serializable;

/**
 * Created by ashraf on 11/19/2016.
 */

public class GoogleAccount implements Serializable{
    private String idTooken ;
    private String displayName ;
    private String emial ;
    private String photoUrl ;
    private String familyName ;
    private String givenName ;
    private String serverAuthCode ;
    private String id ;

    public String getIdTooken() {
        return idTooken;
    }

    public void setIdTooken(String idTooken) {
        this.idTooken = idTooken;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayedName) {
        this.displayName = displayedName;
    }

    public String getEmial() {
        return emial;
    }

    public void setEmial(String emial) {
        this.emial = emial;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getServerAuthCode() {
        return serverAuthCode;
    }

    public void setServerAuthCode(String serverAuthCode) {
        this.serverAuthCode = serverAuthCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
