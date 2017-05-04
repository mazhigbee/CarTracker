package higbee.Final;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
    //this is the constructor for drives that have already happened and are being read back from fire base.
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
    //creates the in nessacary to push to firebase
    protected static void writeNewDrive(Drive drive){
        //reference the firebase instance
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

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
/*
*this function maps all the params of a drive and is called form writeNewDrive
 */
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


//    public Map<String,Object> sortMap(Map<String,Object> drives){
//        Map<String,Object> tmp1 = new HashMap<String, Object>();
//
//
//        for(Map.Entry<String,Object> entry : drives.entrySet()){
//
//        }
//
//    }
    /*
    *read the dive info from firebase back into the application
    * takes a data snap shot of the child "drives" and sorts through each
    * extracting the nessasary info to create drive object
    * todo figure out nested car object
     */
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

            double realDistTrav  = tmpDistTrav;
            //add the new drive object to drive log for use
            drivesLog.add(new Drive(tmpStartLat,tmpStartLong,tmpFinLat,tmpFinLong,tmpStartTime,tmpEndTime,tmpTotalTime,realDistTrav));

        }


    }



    /*
    *returns distance between 2 points as string for gui
     */
    public static String distAsString(double startLat,double startLong,double finLat,double finLong){

        Location start = new Location("");
        start.setLatitude(startLat);
        start.setLongitude(startLong);

        Location end = new Location("");
        end.setLatitude(finLat);
        end.setLongitude(finLong);

        float distanceTravled = start.distanceTo(end);//distance is in meters...



        double   tmpDistance = distanceTravled * 0.000621371192;//convert to miles

        DecimalFormat df = new DecimalFormat("#.##");//trim float
        String driveDist = ("Miles Travled: " + String.valueOf(df.format(tmpDistance)));

        return driveDist;
    }


    /*
    *returns a string of your start locaiton from geocoder
     */
    public static String startAddAsString(Context context,double startLat,double startLong){
        Geocoder mGeocoder = new Geocoder(context, Locale.getDefault());
        String startLocation = "Proper Data Not Found!";
        try {
            List<Address> geoCodeList = mGeocoder.getFromLocation(startLat,startLong,2);
            if(geoCodeList.size() != 0){
                startLocation = "Drive started at: " + geoCodeList.get(0).getAddressLine(0) ;   //+ geoCodeList.get(0).getAddressLine(1)


            } else {

                Toast.makeText(context,"Geo lookup failed",Toast.LENGTH_SHORT);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context,"Unable to fetch start location for lookup",Toast.LENGTH_SHORT);
        }
        return startLocation;
    }

      /*
      * returns string of end location from geocoder
       */
    public static String endAddAsString(Context context,double finLat,double finLong){
        Geocoder mGeocoder = new Geocoder(context,Locale.getDefault());
        //end location town and city
        String endLocation = "Proper end location not found";
        try {
            List<Address> geoCodeList = mGeocoder.getFromLocation(finLat,finLong,2);
            if(geoCodeList.size() != 0){
                endLocation = "Ended at: " + geoCodeList.get(0).getAddressLine(0);   //+ geoCodeList.get(0).getAddressLine(1)

            } else {

                Toast.makeText(context,"Geo lookup failed",Toast.LENGTH_SHORT);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context,"Unable to fetch start location for lookup",Toast.LENGTH_SHORT);
        }
        return endLocation;
    }
    /*
     * converts drive time from millis to a string
     */
    public static String driveTimeAsString(long totalTime){
        int hours = (int) ((totalTime / (1000*60*60))  );
        int mins = (int) ((totalTime / (1000*60)) );
        String driveTime = ("Your Drive Took " + Integer.toString(hours) + " Hours and " + Integer.toString(mins) + " Minutes");
        return driveTime;

    }
//todo implement this maybe?
//    public static String estimatedDriveTime(){
//
//    }


}
