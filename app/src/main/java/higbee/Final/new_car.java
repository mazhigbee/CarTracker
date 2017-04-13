package higbee.Final;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class new_car extends AppCompatActivity {
    Car tmp;
    EditText ccMilage;
    EditText ccModel;
    EditText ccColor;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_car);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //load object in view
        ccMilage = (EditText)findViewById(R.id.car_milage);
        ccModel = (EditText)findViewById(R.id.car_model);
        ccColor = (EditText)findViewById(R.id.car_color);

        final Button cancel = (Button) findViewById(R.id.btnCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(new_car.this,choose_car.class));
            }
        });

        final Button createCar = (Button) findViewById(R.id.btnCreateCar);
        createCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create new instance of a car
                Car.writeNewCar(Car.USER_ID,ccModel.getText().toString(),ccColor.getText().toString(),Integer.parseInt(ccMilage.getText().toString()));

                startActivity(new Intent(new_car.this,choose_car.class));


            }
        });
    }


}
