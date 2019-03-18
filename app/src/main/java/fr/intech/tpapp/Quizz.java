package fr.intech.tpapp;
import android.app.Activity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class Quizz extends Activity {

    protected Questions questionList;
    protected final ObservableIndex index = new ObservableIndex(0);
    protected TextView questionView;
    private TextView PlayerAlternate;

    protected LinearLayout layout;
    protected boolean alternator = false;
    MediaPlayer rightSound;
    protected String responseString;
    protected String gameType;

    protected SQLiteDatabase db;
    private GameState gameState;
    private GameStateDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //Obligatoire pour que l'application fonctionne
        setContentView(R.layout.activity_quizz); //Utilise ce fichier-là, veut dire : prends ce XML et plaque-le dans activity -- R.id.X pour accéder aux identifiants créés

        db = new MyOpenHelper(this).getWritableDatabase();
        dao = new GameStateDAO(db);
        dao.erase();
        dao.insertNew();
        gameState = dao.get();

        rightSound = MediaPlayer.create(this, R.raw.nice);
        Bundle extra = getIntent().getExtras();
        String id = extra.getString("id");
        gameType = extra.getString("gameType");

        PlayerAlternate = findViewById(R.id.alternate);
        PlayerAlternate.setVisibility(View.GONE);


        try {
            ObjectMapper obj = new ObjectMapper();
            obj.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            if(id.equals("0")) run();
            else {
                questionList = obj.readValue(getResources().openRawResource(getResources().getIdentifier(id, "raw", this.getPackageName())), Questions.class);
                displayData(gameType);
            }

        } catch ( IOException e ) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void onResume () {
        super.onResume();
        db = new MyOpenHelper(this).getWritableDatabase();
    }

    protected void onPause () {
        super.onPause();
        db.close();
    }

    private void displayData(final String gameType) {
        Question question;
        if((gameType.equals("2") && !alternator) || gameType.equals("1")) question = questionList.getQuestions().get(gameState.getPlace_p1());
        else question = questionList.getQuestions().get(gameState.getPlace_p2());
        
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
                    if(thisAnswer == rightAnswer) {
                        Toast.makeText(Quizz.this, "Right ! +5pts ", Toast.LENGTH_LONG).show();
                        rightSound.start();
                        if(!alternator) gameState.setScore_p1(gameState.getScore_p1() + 5);
                        else gameState.setScore_p2(gameState.getScore_p2() + 5);
                    } else {
                        Toast.makeText(Quizz.this, "Wrong ! -5pts ", Toast.LENGTH_LONG).show();
                        wrongAnswer();
                        if(!alternator) {
                            if (gameState.getScore_p1() > 5) gameState.setScore_p1(gameState.getScore_p1() - 5);
                            else gameState.setScore_p1(0);
                        } else {
                            if (gameState.getScore_p2() > 5) gameState.setScore_p2(gameState.getScore_p2() - 5);
                            else gameState.setScore_p2(0);
                        }
                    }

                    if(gameType.equals("2") && !alternator)
                    {
                        gameState.setPlace_p1(gameState.getPlace_p1() +1);
                        alternator = !alternator;
                    }
                    else if(gameType.equals("2") && alternator)
                    {
                        gameState.setPlace_p2(gameState.getPlace_p2() +1);
                        alternator = !alternator;
                    }
                    if(gameType.equals("1"))
                    {
                        gameState.setPlace_p1(gameState.getPlace_p1() +1);
                    }
                    layout.removeAllViews();

                    if (gameState.getPlace_p1() >= questionList.getQuestions().size()) {
                        if(gameType.equals("2") && gameState.getPlace_p1() >= questionList.getQuestions().size()) questionView.setText("Félicitations ! Votre score est de " + gameState.getScore_p1() + " pour le joueur 1 et " + gameState.getScore_p2() + " pour le joueur 2");
                        else questionView.setText("Félicitations ! Votre score est de " + gameState.getScore_p1());
                    } else displayData(gameType);

                    dao.save(gameState);
                    if(gameType.equals("2"))  {
                        PlayerAlternate.setVisibility(View.VISIBLE);
                        long duration = 5000; // 5 seconds
                        long tick = 100; // 0.1 seconds;

                        new CountDownTimer(duration, tick) {

                            public void onTick(long millisUntilFinished) {
                                //PlayerAlternate.setAlpha(millisUntilFinished / (float)duration);
                            }

                            public void onFinish() {
                                PlayerAlternate.setVisibility(View.GONE);
                            }
                        }.start();


                    }

                }
            });
            layout.addView(newButton);
            answerCount++;
        }
    }

    private Questions getRandomQuizz() throws IOException {
        ObjectMapper obj = new ObjectMapper();
        obj.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<Question> qList = new ArrayList<>();
        Questions q = new Questions();
        RandomResultList list =  obj.readValue(responseString, RandomResultList.class);
        List<RandomQuestions> t = list.getResults();
        for (RandomQuestions r : t) {
            Question q2 = new Question();
            q2.setQuestion(r.getQuestion());
            String[] answers = new String[r.getIncorrect_answers().length + 1];
            String[] tmp = r.getIncorrect_answers();
            int index = 0;
            for (String s : tmp){
                answers[index] = tmp[index];
                index++;
            }
            answers[r.getIncorrect_answers().length] = r.getCorrect_answer();
            q2.setAnswers(answers);
            q2.setRightAnswer(r.getIncorrect_answers().length);
            qList.add(q2);
        }
        q.setQuestions(qList);
        return q;
    }

    private final OkHttpClient client = new OkHttpClient();

    public void run() throws Exception {
        Request request = new Request.Builder()
                .url("https://opentdb.com/api.php?amount=10")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);

                    responseString = responseBody.string();
                    questionList = getRandomQuizz();
                    runOnUiThread(() -> displayData(gameType));
                }
            }
        });
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
