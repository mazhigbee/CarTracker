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
    double distanceTrav;

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

    private Drive(Double startLat,Double startLong,Double finLat,Double finLong,long startTime,long endTime, long totalTime,double distanceTrav){
        this.startLat = startLat;
        this.startLong = startLong;
        this.finLat = finLat;
        this.finLong = finLong;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalTime = totalTime;
        this.distanceTrav = distanceTrav;
        this.carUsed = carUsed;
    }




    //    /*
//    https://firebase.google.com/docs/database/android/read-and-write
//     */
    //ONLY CALL THIS ONCE A DRIVE IS COMPLETED WITH THE OBJECT
    protected static void writeNewDrive(Drive drive){
        //reference the firebase instance
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        //key key for user
        String key = mDatabase.child("drives").push().getKey();
//        Drive driveTmp = new Drive((long)drive.startLat,(long)drive.startLong,drive.carUsed,drive.startTime);
        //hash data into firebase format
        Map<String, Object> driveVals = drive.toMap();

        Map<String, Object> childUpdate = new HashMap<>();
        childUpdate.put("/drives/" + "/"+drive.startTime+"/",driveVals);
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
        result.put("distanceTrav", distanceTrav);



        return result;
    }


    public static void readDrives(Map<String,Object> drives){

        drivesLog.clear();

        for(Map.Entry<String, Object> entry : drives.entrySet()){
            Map singleDrive = (Map) entry.getValue();
            double tmpStartLat = (Double) singleDrive.get("startLat");
            double tmpStartLong = (Double) singleDrive.get("startLong");
            double tmpFinLat = (Double) singleDrive.get("finLat");
            double tmpFinLong = (Double) singleDrive.get("finLong");
            long tmpStartTime = (Long) singleDrive.get("startTime");
            long tmpEndTime = (Long) singleDrive.get("endTime");
            long tmpTotalTime = (Long) singleDrive.get("totalTime");
            double tmpDistTrav = (Double) singleDrive.get("distanceTrav");

            //Car tmpCar = (Car) singleDrive.get("carUsed");

            double realDistTrav  = (double)  tmpDistTrav;
            drivesLog.add(new Drive(tmpStartLat,tmpStartLong,tmpFinLat,tmpFinLong,tmpStartTime,tmpEndTime,tmpTotalTime,realDistTrav));


//
        }


    }


}
