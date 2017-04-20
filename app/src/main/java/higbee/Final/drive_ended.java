package higbee.Final;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

public class drive_ended extends AppCompatActivity {

    private String endLocation;
    private String startLocation;
    private float distanceTravled;
    private Drive lastDrive;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drive_ended);

        TextView timeDrive = (TextView) findViewById(R.id.totalTime);
        TextView startLoc = (TextView)  findViewById(R.id.startLoc);
        TextView endLoc = (TextView) findViewById(R.id.endLoc);
        TextView driveDist = (TextView) findViewById(R.id.distTraveled);
        //fetch last instance added to the list of drives
        lastDrive = Drive.drivesLog.get(Drive.drivesLog.size()-1);
        //for debug of that fetch
        System.out.println("You are using the following drive and car" + lastDrive.carUsed.model + "Start location = " + lastDrive.startLat + " | " + lastDrive.startLong);



        setGUIFromValues(lastDrive,startLoc,endLoc,driveDist,timeDrive);





        //button listeners
        final ImageButton done = (ImageButton) findViewById(R.id.btnDone);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Drive.writeNewDrive(lastDrive);

                Car.updateMiles(lastDrive.carUsed);

                startActivity(new Intent(drive_ended.this,StartDrive.class));
            }
        });
    }
/*
 * parses and compute all the information form the prev drive
 * displaying it the user in textviews
 *
 */
    private void setGUIFromValues(Drive drive,TextView startLoc,TextView endLoc,TextView driveDist,TextView timeDrive){
        //new geocoder for town names
        Geocoder mGeocoder = new Geocoder(this, Locale.getDefault());


        timeDrive.setText(Drive.driveTimeAsString(drive.totalTime));
        //drive.totalTime = drive.times;

        //start with start location
        //WHEN TESTING IF YOU USE UNSPECIFIC CORDS THESE STRINGS WILL BE BROAD
        //FETCHING FROM PHONE GPS GIVES PRECISE INFO
        //A ADDRESS IS THE EXPECTED RETURN FROM GEOCODER ADDRESLINE(0)
        try {
            List<Address> geoCodeList = mGeocoder.getFromLocation(lastDrive.startLat,lastDrive.startLong,2);
            if(geoCodeList.size() != 0){
                startLocation = "Drive started at:\n" + geoCodeList.get(0).getAddressLine(0) ;   //+ geoCodeList.get(0).getAddressLine(1)
                startLoc.setText(startLocation);

            } else {
                startLoc.setText("ERROR fetching location");
                Toast.makeText(this,"Geo lookup failed",Toast.LENGTH_SHORT);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this,"Unable to fetch start location for lookup",Toast.LENGTH_SHORT);
        }
        //end address info.
        try {
            List<Address> geoCodeList = mGeocoder.getFromLocation(lastDrive.finLat,lastDrive.finLong,2);
            if(geoCodeList.size() != 0){
                endLocation = "Ended at: \n" + geoCodeList.get(0).getAddressLine(0);   //+ geoCodeList.get(0).getAddressLine(1) for more info
                endLoc.setText(endLocation); //set text
            } else {
                startLoc.setText("ERROR fetching location");
                Toast.makeText(this,"Geo lookup failed",Toast.LENGTH_SHORT);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this,"Unable to fetch start location for lookup",Toast.LENGTH_SHORT);
        }


        //distance between
        Location start = new Location("");
        start.setLatitude(drive.startLat);
        start.setLongitude(drive.startLong);

        Location end = new Location("");
        end.setLatitude(drive.finLat);
        end.setLongitude(drive.finLong);

        distanceTravled = start.distanceTo(end);


        //convert from meters to miles
        double tmpDistance = distanceTravled * 0.000621371192;
        //format the double
        DecimalFormat df = new DecimalFormat("#.##");  //EX: 8.99


        driveDist.setText("Miles Travled:\n" + String.valueOf(df.format(tmpDistance)));
        drive.carUsed.miles +=  tmpDistance;
        drive.distanceTrav = tmpDistance;

    }


}


