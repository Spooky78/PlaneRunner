package com.example.planerunner;

import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;

public class SpaceRunnerButton extends Button {
    private final String FONT_PATH;
    {
        try {
            FONT_PATH = String.valueOf(new File(ClassLoader.getSystemResource("kenvector_future.ttf").toURI()));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    private final URI BUTTON_PRESSED_PATH;
    {
        try {
            BUTTON_PRESSED_PATH = new File(ClassLoader.getSystemResource("yellow_button_pressed.png").toURI()).toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    private final URI BUTTON_FREE_PATH;
    {
        try {
            BUTTON_FREE_PATH = new File(ClassLoader.getSystemResource("yellow_button.png").toURI()).toURI();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    private final String BUTTON_PRESSED_STYLE = "-fx-background-color: transparent; -fx-background-image: url('"+BUTTON_PRESSED_PATH+"');";
    private final String BUTTON_FREE_STYLE = "-fx-background-color: transparent; -fx-background-image: url('"+BUTTON_FREE_PATH+"');";

    public SpaceRunnerButton(String text){
        setText(text);
        setButtonFont();
        setPrefWidth(190);
        setPrefHeight(45);
        setStyle(BUTTON_FREE_STYLE);
        initializeButtonListener();
    }

    private void setButtonFont(){
        try {
            setFont(Font.loadFont(new FileInputStream(FONT_PATH), 23));
        } catch (FileNotFoundException e) {
            setFont(Font.font("Verdana", 23));
        }
    }

    private void setButtonPressedStyle(){
        setStyle(BUTTON_PRESSED_STYLE);
        setPrefHeight(49);
        setLayoutY(getLayoutY() + 4);
    }

    private void setButtonReleasedStyle(){
        setStyle(BUTTON_FREE_STYLE);
        setPrefHeight(45);
        setLayoutY(getLayoutY() - 4);
    }

    private void initializeButtonListener(){

        setOnMousePressed(mouseEvent -> {
            if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                setButtonPressedStyle();
            }
        });

        setOnMouseReleased(mouseEvent -> {
            if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                setButtonReleasedStyle();
            }
        });

        setOnMouseEntered(mouseEvent -> setEffect(new DropShadow()));

        setOnMouseExited(mouseEvent -> setEffect(null));
    }
}
