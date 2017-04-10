package higbee.Final;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class StartDrive extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_drive);


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

    }






}
