package controllers;

import com.jfoenix.controls.JFXButton;

import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.util.Duration;

public class LogoutController extends UserController{
	boolean logoutConfirm;
	public void handleConfirmation(ActionEvent event){
        JFXButton btn=(JFXButton)event.getSource();
        TranslateTransition t1=new TranslateTransition(Duration.millis(270),btn);
        t1.setToY(-17d);
        PauseTransition p1=new PauseTransition(Duration.millis(40));
        TranslateTransition t2=new TranslateTransition(Duration.millis(270),btn);
        t2.setToY(0d);

        SequentialTransition d=new SequentialTransition(btn, t1,p1,t2);
        d.play();
        d.setOnFinished(event1 -> {
            if(btn.getId().equals("btnYes")){
            	logoutConfirm = true;
                CloseStage(event);
            }else if(btn.getId().equals("btnNo")){
            	logoutConfirm = false;
                CloseStage(event);
            }
        });
    }
}
