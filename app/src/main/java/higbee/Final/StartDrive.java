package higbee.Final;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;


public class StartDrive extends AppCompatActivity {
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_drive);
        //reference for firebase
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //add button listeners
        final ImageButton start_button = (ImageButton) findViewById(R.id.startImgButton);
        start_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
               Intent chooseCar = new Intent(StartDrive.this,choose_car.class);
                startActivity(chooseCar);
            }
        });

        final ImageButton past_drives_button = (ImageButton) findViewById(R.id.pastImgBtn);
        past_drives_button.setOnClickListener(new View.OnClickListener(){
           @Override
            public void onClick(View v){

               Intent choosePastDrives = new Intent(StartDrive.this,past_drives.class);
               startActivity(choosePastDrives);
           }
        });
        //event listener for a single value change
        //this calls the functions to start retrieving info from firebase
        //takes a snapshot of data at specified child
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Drive.readDrives((Map<String,Object>)dataSnapshot.child("drives").getValue());
                Car.readCar((Map<String,Object>)dataSnapshot.child("cars").getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }






}
