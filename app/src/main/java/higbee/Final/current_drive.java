package higbee.Final;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class current_drive extends AppCompatActivity {
    protected static String curModel;
    protected static int carIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_drive);

        final Button btnDoneDriving = (Button) findViewById(R.id.endDrive);
        btnDoneDriving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(current_drive.this,drive_ended.class));
            }
        });
    }
}
