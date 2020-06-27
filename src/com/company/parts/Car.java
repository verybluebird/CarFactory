package com.company.parts;

public class Car extends Part{
    private final Accessories accessories;
    private final Body body;
    private final Engine engine;


    public Car(int detailId, Accessories accessories, Body body, Engine engine) {
        super(detailId);
        this.accessories = accessories;
        this.body = body;
        this.engine = engine;
    }
    public Accessories getAccessories(){
        return accessories;
    }
    public Body getBody(){
        return body;
    }
    public Engine getEngine(){
        return engine;
    }
}
