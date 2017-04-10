package higbee.Final;

import java.util.ArrayList;

/**
 * Created by Mazlin Higbee on 4/9/2017.
 */

class Car {
    String model;
    int miles;
    String carColor;



    static ArrayList<Car> list = new ArrayList<Car>();


    public Car(String model,int miles,String carColor){
        this.carColor = carColor;
        this.miles = miles;
        this.model = model;
        list.add(this);
    }

    //TODO
    //add method that reads in instances from json


}
