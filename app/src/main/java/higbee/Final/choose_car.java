package higbee.Final;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;


import java.util.ArrayList;
//outside resources
//http://stackoverflow.com/questions/9596663/how-to-make-items-clickable-in-list-view

public class choose_car extends AppCompatActivity {

    private ListView lv;
    private ArrayList<String> modelList = new ArrayList<>(); //array to set the view

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_car);



        final Button newCar = (Button) findViewById(R.id.newCar);
        newCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(choose_car.this,new_car.class));

            }
        });

        //list view logic
        lv =(ListView) findViewById(R.id.list_of_drives);
        lv.setClickable(true);


        modelList.clear();
        //populates the array for the list view with the model names of instances of cars
        for(Car index : Car.list){
            System.out.println(index.model);
            modelList.add(index.model);

        }


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,modelList);
        lv.setAdapter(arrayAdapter);
        //http://stackoverflow.com/questions/9596663/how-to-make-items-clickable-in-list-view
        //Help from the above link in setting up clickable listview
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                System.out.println("POS CLICK : " + position);
//                System.out.println(modelList.get(position).toString());
                current_drive.curModel = modelList.get(position).toString();
                current_drive.carIndex = position;
                Intent i = new Intent(choose_car.this,current_drive.class);
                startActivity(i);

            }
        });



        //http://stackoverflow.com/questions/11168603/android-listview-item-selection
        //link to example of making list view selector


    }



}
