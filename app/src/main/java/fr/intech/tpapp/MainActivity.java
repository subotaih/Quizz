package fr.intech.tpapp;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    protected ArrayList<Question> questionList;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState); //Obligatoire pour que l'application fonctionne
            Log.i("MyActivity", "convert!"); //Simple message de log
            setContentView(R.layout.activity_main); //Utilise ce fichier-là, veut dire : prends ce XML et plaque-le dans activity -- R.id.X pour accéder aux identifiants créés
            Button button = findViewById(R.id.button);
            //button.setText(R.string.app_name); Chaque attribut (text, etc.) a un getter et un setter automatiquement pour changer les valeurs dynamiquement
            //button.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

            JSONObject jsonObj = null;
            JSONArray data = null;
            try {
                jsonObj = new JSONObject(loadJSONFromAsset(this));

                data = jsonObj.getJSONArray("questions");
                questionList = Question.fromJson(data);

            } catch (JSONException e) {
                e.printStackTrace();
            }


            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    
                    EditText userInput = findViewById(R.id.number);
                    double convertedNb = Double.valueOf(userInput.getText().toString()) *1.2;

                    Button button = findViewById(R.id.button);
                    button.setText(String.valueOf(convertedNb));
                    Toast.makeText(MainActivity.this, String.valueOf(convertedNb), Toast.LENGTH_LONG).show();
                }
            });

        }


    public String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("data.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

}
