package fr.intech.tpapp;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class MainActivity extends Activity {

    protected ArrayList<Question> questionList;
    protected final ObservableIndex index = new ObservableIndex(0);
    protected int score = 0;
    protected TextView questionView;
    protected LinearLayout layout;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState); //Obligatoire pour que l'application fonctionne
            setContentView(R.layout.activity_main); //Utilise ce fichier-là, veut dire : prends ce XML et plaque-le dans activity -- R.id.X pour accéder aux identifiants créés

            try {
                JSONObject jsonObj = new JSONObject(loadJSONFromAsset(this));
                JSONArray data = jsonObj.getJSONArray("questions");
                questionList = Question.fromJson(data);


            } catch (JSONException e) {
                e.printStackTrace();
            }
            createQuestionView ();
        }

    private void createQuestionView() {

        Question question = questionList.get(index.getValue());
        String[] answers = question.getAnswers();
        questionView = findViewById(R.id.question);
        questionView.setText(question.getQuestion());

        layout = findViewById(R.id.linearLayout);
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
                        score += 5;
                    } else {
                        Toast.makeText(MainActivity.this, "Wrong ! -5pts ", Toast.LENGTH_LONG).show();
                        if(score > 5) score -= 5;
                        else score = 0;
                    }
                    index.setValue(index.getValue() +1);
                    layout.removeAllViews();
                    if (index.getValue() >= questionList.size()) {
                        questionView.setText("Félicitaiton ! Votre score est de " + score);
                    }
                    else createQuestionView();
                }
            });
            layout.addView(newButton);
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
