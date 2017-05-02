package higbee.Final;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        //firebase reference
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //load object in view
        ccMilage = (EditText)findViewById(R.id.car_milage);
        ccModel = (EditText)findViewById(R.id.car_model);
        ccColor = (EditText)findViewById(R.id.car_color);
        //buttons listeners
        final ImageButton cancel = (ImageButton) findViewById(R.id.btnCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(new_car.this,choose_car.class));
            }
        });

        final ImageButton createCar = (ImageButton) findViewById(R.id.btnCreate);
        createCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create new instance of a car
                String model = null;
                String color = null;
                int miles = -1;
                boolean errors = false;

                try{
                    model = ccModel.getText().toString();
                    color = ccColor.getText().toString();
                    miles = Integer.parseInt(ccMilage.getText().toString());

                }catch (NumberFormatException e){
                    errors = true;
                    Toast.makeText(getApplicationContext(),"Please enter valid info",Toast.LENGTH_SHORT).show();
                }
                //Comment here maz todo
                if(errors == false && color != null && model != null && miles != -1){
                    Car.writeNewCar(model,color,miles);
                    startActivity(new Intent(new_car.this,choose_car.class));
                }




                //prompts the database to call an event listener on cars if they have been updated
                //New one is added in this class anyway

                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Car.readCar((Map<String,Object>)dataSnapshot.child("cars").getValue());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });




            }
        });
    }


}
