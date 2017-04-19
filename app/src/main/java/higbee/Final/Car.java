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
        //carList.add(this);
    }

//    /*
//    https://firebase.google.com/docs/database/android/read-and-write
//     */
    protected static void writeNewCar(String userId,String model,String color, int miles){
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
    //hash object for firebase
    private Map<String,Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("model", model);
        result.put("color",color);
        result.put("miles",miles);


        return result;
    }
    //http://stackoverflow.com/questions/38965731/how-to-get-all-childs-data-in-firebase-database
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
public static void updateMiles(Car car){
    mDatabase = FirebaseDatabase.getInstance().getReference();
   Map<String, Object> carVals = car.toMap();
    Map<String,Object> childUpdate = new HashMap<>();
    childUpdate.put("/cars/"+ "/" + car.model +"/",carVals);

    mDatabase.updateChildren(childUpdate);
}

}
