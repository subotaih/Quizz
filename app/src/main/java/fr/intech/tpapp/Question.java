package fr.intech.tpapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Question {
    private String question;
    private String answer1;
    private String answer2;
    private String answer3;
    private int rightAnswer;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
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
            q.answer1 = jsonObject.getString("answer1");
            q.answer2 = jsonObject.getString("answer2");
            q.answer3 = jsonObject.getString("answer3");
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
