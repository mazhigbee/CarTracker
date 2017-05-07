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

public class DriveEnded extends AppCompatActivity {

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

                startActivity(new Intent(DriveEnded.this,StartDrive.class));
            }
        });
    }
/*
 * parses and compute all the information form the prev drive
 * displaying it the user in textviews
 *
 */
    private void setGUIFromValues(Drive drive,TextView startLoc,TextView endLoc,TextView driveDist,TextView timeDrive){

        timeDrive.setText(Drive.driveTimeAsString(drive.totalTime));
        startLoc.setText(Drive.startAddAsString(this,lastDrive.startLat,lastDrive.startLong));
        endLoc.setText(Drive.endAddAsString(this,lastDrive.finLat,lastDrive.finLong));

        driveDist.setText(Drive.distAsString(drive.startLat,drive.startLong,drive.finLat,drive.finLong));

        Drive.updateCarMilage(drive,drive.startLat,drive.startLong,drive.finLat,drive.finLong);

    }


}


