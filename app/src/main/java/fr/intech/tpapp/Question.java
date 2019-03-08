package fr.intech.tpapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Question {
    private String question;
    private String[] answers;
    private int rightAnswer;

    public String[] getAnswers() { return answers; }

    public void setAnswers(String[] answers) { this.answers = answers; }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(int rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public static Question fromJson(JSONObject jsonObject) {
        Question q = new Question();
        // Deserialize json into object fields
        try {
            q.question = jsonObject.getString("question");
            //JSONArray array = jsonObject.getJSONArray("answer");
            JSONArray array = jsonObject.getJSONArray("answers");
            String[] answers = new String[array.length()];
            for(int i= 0; i<array.length(); i++) {
                answers[i]= array.getString(i);
            }
            q.answers = answers;
            q.rightAnswer = jsonObject.getInt("rightAnswer");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return q;
    }
    public static ArrayList<Question> fromJson(JSONArray jsonArray) {
        JSONObject questionJson;
        ArrayList<Question> questions = new ArrayList<Question>(jsonArray.length());
        // Process each result in json array, decode and convert to business object
        for (int i=0; i < jsonArray.length(); i++) {
            try {
                questionJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            Question question = Question.fromJson(questionJson);
            if (question != null) {
                questions.add(question);
            }
        }

        return questions;
    }

}
