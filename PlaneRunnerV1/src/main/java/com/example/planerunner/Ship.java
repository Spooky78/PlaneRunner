package com.example.planerunner;

public enum Ship {
    BLUE("ship_blue.png", "blue_ship_life.png"),
    GREEN("ship_green.png", "green_ship_life.png"),
    ORANGE("ship_orange.png", "orange_ship_life.png"),
    RED("ship_red.png", "red_ship_life.png");

    final String urlShip;
    final String urlLife;
    Ship(String urlShip, String urlLife){
        this.urlShip = urlShip;
        this.urlLife = urlLife;
    }

    public String getUrl(){
        return this.urlShip;
    }

    public String getUrlLife(){return this.urlLife;}
}
