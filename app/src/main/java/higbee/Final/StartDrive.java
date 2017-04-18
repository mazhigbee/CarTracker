package higbee.Final;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

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
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //add button listeners
        final Button start_button = (Button) findViewById(R.id.start_drive);
        start_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
               Intent chooseCar = new Intent(StartDrive.this,choose_car.class);
                startActivity(chooseCar);
            }
        });

        final Button past_drives_button = (Button) findViewById(R.id.past_drives);
        past_drives_button.setOnClickListener(new View.OnClickListener(){
           @Override
            public void onClick(View v){

               Intent choosePastDrives = new Intent(StartDrive.this,past_drives.class);
               startActivity(choosePastDrives);
           }
        });
        //TODo
//        implement firebase read!
        //read cars from database
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
