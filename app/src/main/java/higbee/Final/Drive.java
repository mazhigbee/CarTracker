package higbee.Final;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mazlin Higbee on 4/12/2017.
 */

public class Drive {

    //todo delete this \/
    static long times;

    double startLat;
    double startLong;
    double finLat;
    double finLong;
    static double curLat;
    static double curLong;
    long startTime;
    long endTime;
    long totalTime;
    float distanceTrav;

    Car carUsed;

   static ArrayList<Drive> drivesLog = new ArrayList<>();

    //create normal object and when you are done with drive upload to firebase with WRITENEWCAR
    //TODO add this stuff to firebase WHEN DRIVE IS OVER
    public Drive(double startLat,double startLong,Car carUsed,long startTime){
        this.startLat = startLat;
        this.startLong = startLong;
        this.carUsed = carUsed;
        this.startTime = startTime;
        endTime = 0;
        finLat = 0;
        finLong = 0;
        totalTime = 0;
        drivesLog.add(this);
        distanceTrav = 0;


    }




    //    /*
//    https://firebase.google.com/docs/database/android/read-and-write
//     */
    //ONLY CALL THIS ONCE A DRIVE IS COMPLETED WITH THE OBJECT
    protected static void writeNewCar(Drive drive){
        //reference the firebase instance
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        //key key for user
        String key = mDatabase.child("drives").push().getKey();
        Drive driveTmp = new Drive((long)drive.startLat,(long)drive.startLong,drive.carUsed,drive.startTime);
        //hash data into firebase format
        Map<String, Object> driveVals = drive.toMap();

        Map<String, Object> childUpdate = new HashMap<>();
        childUpdate.put("/"+drive.startTime+"/",driveVals);
        //childUpdate.put("/user-cars/" + userId +"/" + key, carVals);
        //push to firebase
        mDatabase.updateChildren(childUpdate);




    }

    private Map<String,Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("startLat",startLat);
        result.put("startLong",startLong);
        result.put("carUsed",carUsed);
        result.put("startTime",startTime);
        result.put("endTime",endTime);
        result.put("finLat",finLat);
        result.put("finLong",finLong);
        result.put("totalTime",totalTime);



        return result;
    }



}
