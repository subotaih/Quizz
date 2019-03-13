package fr.intech.tpapp;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;


public class Quizz extends Activity {

    protected Questions questionList;
    protected final ObservableIndex index = new ObservableIndex(0);
    protected int score = 0;
    protected TextView questionView;
    protected LinearLayout layout;

    MediaPlayer rightSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //Obligatoire pour que l'application fonctionne
        setContentView(R.layout.activity_quizz); //Utilise ce fichier-là, veut dire : prends ce XML et plaque-le dans activity -- R.id.X pour accéder aux identifiants créés

        SQLiteDatabase db = new MyOpenHelper(this).getWritableDatabase();

        rightSound = MediaPlayer.create(this, R.raw.nice);
        Bundle extra = getIntent().getExtras();
        String id = extra.getString("id");


        try {
            ObjectMapper obj = new ObjectMapper();
            obj.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            questionList = obj.readValue(getResources().openRawResource(getResources().getIdentifier(id, "raw", this.getPackageName())), Questions.class);

        } catch ( IOException e) {
            e.printStackTrace();
        }
        createQuestionView ();
    }

    private void createQuestionView() {
        Question question = questionList.getQuestions().get(index.getValue());
        String[] answers = question.getAnswers();
        questionView = findViewById(R.id.question);
        questionView.setText(question.getQuestion());

        layout = findViewById(R.id.linearLayout);
        int answerCount = 1;
        for (String s:answers) {
            final Button newButton= new Button(this);
            newButton.setText(s);
            newButton.setBackgroundColor(0xFF99D6D6);
            final int rightAnswer = question.getRightAnswer();
            final int thisAnswer = answerCount;
            newButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if(thisAnswer == rightAnswer ) {
                        Toast.makeText(Quizz.this, "Right ! +5pts ", Toast.LENGTH_LONG).show();
                        rightSound.start();
                        score += 5;
                    } else {
                        Toast.makeText(Quizz.this, "Wrong ! -5pts ", Toast.LENGTH_LONG).show();
                        wrongAnswer();
                        if(score > 5) score -= 5;
                        else score = 0;
                    }
                    index.setValue(index.getValue() +1);
                    layout.removeAllViews();
                    if (index.getValue() >= questionList.getQuestions().size()) {
                        questionView.setText("Félicitations ! Votre score est de " + score);
                    }
                    else createQuestionView();
                }
            });
            layout.addView(newButton);
            answerCount++;
        }
    }

    // Vibrate for 150 milliseconds
    private void wrongAnswer() {
        if (Build.VERSION.SDK_INT >= 26) {
            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(500, 1));
        } else {
            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(500);
        }
    }
}
