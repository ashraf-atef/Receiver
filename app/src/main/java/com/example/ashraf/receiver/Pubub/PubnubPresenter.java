package com.example.ashraf.receiver.Pubub;

import com.example.ashraf.receiver.Model.ChatMessage;
import com.example.ashraf.receiver.Model.PublishedLocation;


/**
 * Created by ashraf on 11/20/2016.
 */

public interface PubnubPresenter {
    void set_Config();
    void subscribe() ;
    void publishLocation(PublishedLocation location) ;
    void publishMessage(ChatMessage chatMessage);
    void setListioner() ;
    void unsubscribe() ;
}
