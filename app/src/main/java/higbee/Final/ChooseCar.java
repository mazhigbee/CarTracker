package higbee.Final;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import static higbee.Final.R.layout.activity_choose_car;
//outside resources
//http://stackoverflow.com/questions/9596663/how-to-make-items-clickable-in-list-view

public class ChooseCar extends AppCompatActivity {

    private ListView lv;
    private ArrayList<String> modelList = new ArrayList<>(); //array to set the view


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_choose_car);

        //checkbox
        final CheckBox removeCars = (CheckBox) findViewById(R.id.removeCar);
        //firebase reference
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        //refresh data because adding new car needs this...
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Car.readCar((Map<String,Object>)dataSnapshot.child("cars").getValue());
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });



        //button lisenters
        final ImageButton newCar = (ImageButton) findViewById(R.id.newCarBtn);
        newCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChooseCar.this,NewCar.class));

            }
        });

        //list view logic
        lv =(ListView) findViewById(R.id.list_of_drives);
        lv.setClickable(true);


        modelList.clear();
        //populates the array for the list view with the model names of instances of cars
        for(Car index : Car.carList){
            System.out.println(index.model);
            modelList.add(index.model);

        }



        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,modelList);
        lv.setAdapter(arrayAdapter);
        //http://stackoverflow.com/questions/9596663/how-to-make-items-clickable-in-list-view
        //http://stackoverflow.com/questions/11168603/android-listview-item-selection
        //link to example of making list view selector
        //Help from the above link in setting up clickable listview
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
//                System.out.println("POS CLICK : " + position);
//                System.out.println(modelList.get(position).toString());
                //models in the Car list should correpond to indexes of list view


                //if you have checked the box to remove cars do so.
                if(removeCars.isChecked()){
                    new AlertDialog.Builder(ChooseCar.this)
                            .setTitle("Removing Cars")
                            .setMessage("Would you like to remove " + modelList.get(position).toString())
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //yes
                                    String tmp = modelList.get(position).toString();
                                    Car.removeCar(tmp,position);

                                    modelList.remove(position);
                                    ((BaseAdapter)lv.getAdapter()).notifyDataSetChanged();
                                    Toast.makeText(getApplicationContext(),"The " + tmp + " has been removed",Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                  //no
                                    //do nothing

                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();



                } else {
                    //else choose car and start drive.
                    CurrentDrive.curModel = modelList.get(position).toString();
                    CurrentDrive.carIndex = position;
                    Intent i = new Intent(ChooseCar.this, CurrentDrive.class);
                    startActivity(i);






                }

            }
        });






    }



}
