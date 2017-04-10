package higbee.Final;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


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

        final Button selectCar = (Button) findViewById(R.id.selectCarBtn);
        selectCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("test");
                Intent i = new Intent(choose_car.this,current_drive.class);
                startActivity(i);
            }
        });



        modelList.clear();
        //populates the array for the list view with the model names of instances of cars
        for(Car index : Car.list){
            System.out.println(index.model);
            modelList.add(index.model);



        }

        lv =(ListView) findViewById(R.id.list_of_drives);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,modelList);
        lv.setAdapter(arrayAdapter);
        //prints the models of cars from list


    }
}
