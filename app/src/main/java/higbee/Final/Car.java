package higbee.Final;



import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Mazlin Higbee on 4/9/2017.
 */

public class Car {
    public final static String USER_ID = "visFinal";
    String model;
    int miles;
    String color;
    private static DatabaseReference mDatabase;




    static ArrayList<Car> list = new ArrayList<Car>();


    public Car(String model,int miles,String carColor){
        this.color = carColor;
        this.miles = miles;
        this.model = model;
        list.add(this);
    }

//    /*
//    https://firebase.google.com/docs/database/android/read-and-write
//     */
//    protected static void writeNewCar(String userId,String model,String color, int miles){
//        //reference the firebase instance
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//        //key key for user
//        String key = mDatabase.child("cars").push().getKey();
//        Car car = new Car(model,miles,color);
//        //hash data into firebase format
//        Map<String, Object> carVals = car.toMap();
//
//        Map<String, Object> childUpdate = new HashMap<>();
//        childUpdate.put("/cars/" + key,carVals);
//        childUpdate.put("/user-cars/" + userId +"/" + key, carVals);
//        //push to firebase
//        mDatabase.updateChildren(childUpdate);
//
//
//
//
//    }
    //hash object for firebase
    public Map<String,Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("model", model);
        result.put("color",color);
        result.put("miles",miles);


        return result;
    }


}
