package higbee.Final;



import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



/**
 * Created by Mazlin Higbee on 4/9/2017.
 */

public class Car {
    public final static String USER_ID = "visFinal";
    String model;
    long miles;
    String color;
    private static DatabaseReference mDatabase;




    public static ArrayList<Car> carList = new ArrayList<Car>();


    public Car(String model,long miles,String carColor){
        this.color = carColor;
        this.miles = miles;
        this.model = model;
       // carList.add(this);
    }

    /*
     *https://firebase.google.com/docs/database/android/read-and-write
     * push cars to database
     */
    protected static void writeNewCar(String model,String color, int miles){
        //reference the firebase instance
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //key key for user
        String key = mDatabase.child("cars").push().getKey();
        Car car = new Car(model,miles,color);
        //hash data into firebase format
        Map<String, Object> carVals = car.toMap();

        Map<String, Object> childUpdate = new HashMap<>();
        childUpdate.put("/cars/" + "/"+car.model+"/",carVals);
        //childUpdate.put("/user-cars/" + userId +"/" + key, carVals);
        //push to firebase
        Car.carList.add(car);
        mDatabase.updateChildren(childUpdate);




    }
    //hash car object fields
    private Map<String,Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("model", model);
        result.put("color",color);
        result.put("miles",miles);


        return result;
    }
    //http://stackoverflow.com/questions/38965731/how-to-get-all-childs-data-in-firebase-database
    /*
     * read the cars back from firebase
     * iterate through the map and extract the values for each car
     */
    public static void readCar(Map<String, Object> cars){

        carList.clear();
        for(Map.Entry<String,Object> entry : cars.entrySet()){
            Map singleCar = (Map) entry.getValue();
            String tmpModel = (String) singleCar.get("model");
            long tmpMiles = (long) singleCar.get("miles");
            String tmpColor = (String) singleCar.get("color");
            System.out.println("Fetched from firebase: " + tmpModel + " | " + tmpColor + " | " + tmpMiles);
            carList.add(new Car(tmpModel,tmpMiles,tmpColor));

        }

    }

    /*
    *THIS IS THE ONLY UPDATE A CAR EVER RECIEVES IN DATBASE
    * updates the child of the specified car
    * todo BECAREFUL TO NAME CARS SAME THING BECAUSE OF THIS
    * should probably not be this way.....
     */
    public static void updateMiles(Car car){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> carVals = car.toMap();
        Map<String,Object> childUpdate = new HashMap<>();
        childUpdate.put("/cars/"+ "/" + car.model +"/",carVals);
        mDatabase.updateChildren(childUpdate);
}

    public static void removeCar(String car,int index){
        System.out.println("you are removing " + carList.get(index).model);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("cars").child(car).setValue(null);
        carList.remove(index);
    }

}
