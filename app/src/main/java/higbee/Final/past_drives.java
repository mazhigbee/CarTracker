package higbee.Final;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class past_drives extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_drives);
        ListView drivesList = (ListView) findViewById(R.id.expandableListView);
        //https://developer.android.com/reference/android/widget/ExpandableListView.html

        final Button cancelBtn = (Button) findViewById(R.id.btnCancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(past_drives.this,StartDrive.class));
            }
        });
    }
}
