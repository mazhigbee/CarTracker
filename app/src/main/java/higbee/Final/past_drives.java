package higbee.Final;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

//https://developer.android.com/reference/android/widget/ExpandableListView.html


public class past_drives extends AppCompatActivity {
    ExpandableListView drivesList;
    ExpandableListAdapter listAdapter;
    ArrayList<ArrayList> driveComp;
    List<String> listDataHeader;
    HashMap<String,List<String>> listDataChild;
//    private boolean hasBeenLoaded = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_drives);
        drivesList = (ExpandableListView) findViewById(R.id.expandablePastDrives);

        if(listDataChild == null && listDataHeader == null) {
            driveComp = new ArrayList<>();


            setupList();


            listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        }
        drivesList.setAdapter(listAdapter);




        final Button cancelBtn = (Button) findViewById(R.id.btnCancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(past_drives.this,StartDrive.class));
            }
        });
    }

    private void setupList() {
        //TODO
        //parse drive data for each drive
       //int numDrives = Drive.drivesLog.size();
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        int counter = 0;

        for(Drive drive : Drive.drivesLog){
            //format headers
            SimpleDateFormat formatDate = new SimpleDateFormat(" HH:mm MM-dd-yyyy", Locale.getDefault());
            Date tmpDate = new Date(drive.startTime);
            String dateFormatted = formatDate.format(tmpDate);
            listDataHeader.add("Drive at " + dateFormatted);
            System.out.println(dateFormatted + "Date test");
            //add all info from drive to list
            List<String> info = new ArrayList<>();
            info.add(Drive.distAsString(drive.startLat,drive.startLong,drive.finLat,drive.finLong)); //miles between start and finish of drive
            info.add(Drive.startAddAsString(this,drive.startLat,drive.startLong));//start add
            info.add(Drive.endAddAsString(this,drive.finLat,drive.finLong));//end add
            info.add(Drive.driveTimeAsString(drive.totalTime));//drive time
            //TODO add predicted time from google api
            //info.add();//predicted time now
            listDataChild.put(listDataHeader.get(counter),info);
            counter++;



        }
        //hasBeenLoaded = true;

    }
}
