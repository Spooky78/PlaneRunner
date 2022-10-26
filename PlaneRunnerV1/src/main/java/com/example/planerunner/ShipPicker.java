package com.example.planerunner;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ShipPicker extends VBox {
    private final ImageView boxImage;
    private static final String boxNotChosen = "grey_box.png";
    private static final String boxChosen = "checkmark.png";
    private final Ship ship;
    private boolean isBoxChosen;

    public ShipPicker(Ship ship){
        boxImage = new ImageView(boxNotChosen);
        ImageView shipImage = new ImageView(ship.getUrl());
        this.ship = ship;
        isBoxChosen = false;
        this.setAlignment(Pos.CENTER);
        this.setSpacing(10);
        this.getChildren().add(boxImage);
        this.getChildren().add(shipImage);
    }

    public Ship getShip(){
        return ship;
    }

    public void setIsBoxChosen(boolean isBoxChosen){
        this.isBoxChosen = isBoxChosen;
        String imageToSet = this.isBoxChosen ? boxChosen: boxNotChosen;
        boxImage.setImage(new Image(imageToSet));
    }


}
