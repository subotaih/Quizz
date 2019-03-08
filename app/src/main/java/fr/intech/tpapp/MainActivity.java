package fr.intech.tpapp;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity {

    protected ArrayList<Question> questionList;
    protected int index = 0;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState); //Obligatoire pour que l'application fonctionne
            Log.i("MyActivity", "convert!"); //Simple message de log
            setContentView(R.layout.activity_main); //Utilise ce fichier-là, veut dire : prends ce XML et plaque-le dans activity -- R.id.X pour accéder aux identifiants créés
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
            Question question = questionList.get(index);
            String[] answers = question.getAnswers();
            TextView questionView = findViewById(R.id.question);
            questionView.setText(question.getQuestion());

            LinearLayout l = findViewById(R.id.linearLayout);
            int answerCount = 1;
            for (String s:answers) {
                Button newButton= new Button(this);
                newButton.setText(s);
                newButton.setBackgroundColor(0xFF99D6D6);
                final int rightAnswer = question.getRightAnswer();
                final int thisAnswer = answerCount;
                newButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(thisAnswer == rightAnswer ) {
                            Toast.makeText(MainActivity.this, "Right ! +5pts ", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Wrong ! -5pts ", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                l.addView(newButton);
                answerCount++;
            }
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
