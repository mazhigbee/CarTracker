package higbee.Final;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class drive_ended extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drive_ended);
        Date date = new Date(Drive.times);
        DateFormat formatter = new SimpleDateFormat("H:mm:ss");
        String formattedTime = formatter.format(date);

        TextView timeDrive = (TextView) findViewById(R.id.totalTime);



        timeDrive.setText("Your Drive Took: " + formattedTime);

        final Button done = (Button) findViewById(R.id.doneBtn);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(drive_ended.this,StartDrive.class));
            }
        });
    }
}
