package com.example.planerunner;

import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Random;

public class GameViewManager {
    private static final String BACKGROUND_IMAGE = "cloud1.png";
    private static final String CLOUD1_IMAGE = "cloud1.png";
    private static final String CLOUD2_IMAGE = "cloud2.png";
    private static final String STAR_IMAGE = "star_gold.png";
    private static final int STAR_RADIUS = 15;
    private static final int SHIP_RADIUS = 50;
    private static final int METEOR_RADIUS = 60;
    private static final int GAME_WIDTH = 600;
    private static final int GAME_HEIGHT = 800;
    private AnchorPane gamePane;
    private Scene gameScene;
    private Stage gameStage;
    private Stage menuStage;
    private SmallInfoLabel pointsLabel;
    private ImageView ship;
    private ImageView[] brownMeteors;
    private ImageView[] greyMeteors;
    private ImageView star;
    private ImageView[] playerLives;
    private ImageView[] background;
    private final Random randomPositionGenerator;
    private boolean isLeftKeyPressed;
    private boolean isRightKeyPressed;
    private int angle;
    private int playerLivesInt;
    private int points;
    private AnimationTimer gameTimer;

    public GameViewManager(){
        initializeStage();
        createKeyListeners();
        randomPositionGenerator = new Random();
    }

    public void createNewGame(Stage menuStage, Ship chosenShip){
        this.menuStage = menuStage;
        this.menuStage.hide();
        createBackground();
        createShip(chosenShip);
        createGameElements();
        createGameLoop();
        gameStage.show();
    }

    private void initializeStage(){
        gamePane = new AnchorPane();
        gameScene = new Scene(gamePane, GAME_WIDTH, GAME_HEIGHT);
        gameStage = new Stage();
        gameStage.setScene(gameScene);
    }

    private void createKeyListeners(){
        gameScene.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.LEFT){
                isLeftKeyPressed = true;
            } else if (keyEvent.getCode() == KeyCode.RIGHT) {
                isRightKeyPressed = true;
            }
        });

        gameScene.setOnKeyReleased(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.LEFT){
                isLeftKeyPressed = false;
            } else if (keyEvent.getCode() == KeyCode.RIGHT) {
                isRightKeyPressed = false;
            }
        });
    }

    private void createShip(Ship chosenShip){
        ship = new ImageView(chosenShip.getUrl());
        ship.setLayoutX((float)(GAME_WIDTH/2) - 20);
        ship.setLayoutY(GAME_HEIGHT - 90);
        gamePane.getChildren().add(ship);
    }

    private void createGameElements(){
        playerLivesInt = 2;
        playerLives = new ImageView[3];
        for(int i=0; i<playerLives.length; i++){
            playerLives[i] = new ImageView("life.png");
            playerLives[i].setLayoutX(455 + (i*50));
            playerLives[i].setLayoutY(80);
            gamePane.getChildren().add(playerLives[i]);
        }

        pointsLabel = new SmallInfoLabel("POINTS:00");
        pointsLabel.setLayoutX(460);
        pointsLabel.setLayoutY(20);
        gamePane.getChildren().add(pointsLabel);

        star = new ImageView(STAR_IMAGE);
        setNewElementPosition(star);
        gamePane.getChildren().add(star);

        brownMeteors = new ImageView[4];
        greyMeteors = new ImageView[4];
        for(int i=0; i< brownMeteors.length;i++){
            brownMeteors[i] = new ImageView(CLOUD1_IMAGE);
            setNewElementPosition(brownMeteors[i]);
            gamePane.getChildren().add(brownMeteors[i]);
        }
        for(int i=0; i< greyMeteors.length;i++){
            greyMeteors[i] = new ImageView(CLOUD2_IMAGE);
            setNewElementPosition(greyMeteors[i]);
            gamePane.getChildren().add(greyMeteors[i]);
        }
    }

    private void setNewElementPosition(ImageView image){
        image.setLayoutX(randomPositionGenerator.nextInt(370));
        image.setLayoutY(-(randomPositionGenerator.nextInt(3200)+600));
    }

    private void moveGameElements(){
        star.setLayoutY(star.getLayoutY()+0.3);

        for (ImageView cloud1 : brownMeteors) {
            cloud1.setLayoutY(cloud1.getLayoutY() + 0.4);
        }
        for (ImageView cloud2 : greyMeteors) {
            cloud2.setLayoutY(cloud2.getLayoutY() + 0.2);
        }
    }

    private void checkIfElementIsOffScreenRelocate(){
        if (star.getLayoutY() >1200){
            setNewElementPosition(star);
        }

        for (ImageView brownMeteor : brownMeteors) {
            if (brownMeteor.getLayoutY() > 900) {
                setNewElementPosition(brownMeteor);
            }
        }
        for (ImageView greyMeteor : greyMeteors) {
            if (greyMeteor.getLayoutY() > 900) {
                setNewElementPosition(greyMeteor);
            }
        }
    }

    private void createGameLoop(){
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                //moveBackground();
                //checkIfOffScreen();
                moveGameElements();
                checkIfElementIsOffScreenRelocate();
                checkIfElementCollide();
                moveShip();
            }
        };

        gameTimer.start();
    }

    private void moveShip(){
        if (isLeftKeyPressed && !isRightKeyPressed){
            if (angle > -30){
                angle -= 2;
            }
            ship.setRotate(angle);
            if (ship.getLayoutX() > -20){
                ship.setLayoutX(ship.getLayoutX() -0.5);
            }
        }
        if (isRightKeyPressed && !isLeftKeyPressed){
            if (angle < 30){
                angle += 2;
            }
            ship.setRotate(angle);
            if (ship.getLayoutX() < 522){
                ship.setLayoutX(ship.getLayoutX() +0.5);
            }
        }
        if ((!isLeftKeyPressed && !isRightKeyPressed) || (isLeftKeyPressed && isRightKeyPressed)){
            if (angle < 0){
                angle += 2;
            } else if (angle > 0) {
                angle -= 2;
            }
            ship.setRotate(angle);
        }
    }

    private void createBackground(){
        Background background = new Background(new BackgroundFill(Color.DODGERBLUE, CornerRadii.EMPTY , Insets.EMPTY));
        gamePane.setBackground(background);
    }

    private void checkIfElementCollide(){
        if (SHIP_RADIUS + STAR_RADIUS > calcDistance(ship.getLayoutX() +42, star.getLayoutX() +15,
                ship.getLayoutY() +28, star.getLayoutY()+15)){
            setNewElementPosition(star);
            points++;
            String textToSet = "POINTS: ";
            if (points <10){
                textToSet = textToSet + "0";
            }
            pointsLabel.setText(textToSet +points);
        }

        for (ImageView brownMeteor : brownMeteors) {
            if (METEOR_RADIUS + SHIP_RADIUS > calcDistance(brownMeteor.getLayoutX() + 60, ship.getLayoutX() + 42,
                    brownMeteor.getLayoutY() + 49, ship.getLayoutY() + 28)) {
                removeLife();
                setNewElementPosition(brownMeteor);
            }
        }

        for (ImageView greyMeteor : greyMeteors) {
            if (METEOR_RADIUS + SHIP_RADIUS > calcDistance(greyMeteor.getLayoutX() + 50, ship.getLayoutX() + 42,
                    greyMeteor.getLayoutY() + 42, ship.getLayoutY() + 28)) {
                removeLife();
                setNewElementPosition(greyMeteor);
            }
        }
    }

    private void removeLife(){
        gamePane.getChildren().remove(playerLives[playerLivesInt]);
        playerLivesInt--;
        if (playerLivesInt < 0){
            gameStage.close();
            gameTimer.stop();
            menuStage.show();
        }
    }

    private double calcDistance(double x1, double x2, double y1, double y2){
        return Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));
    }
}
