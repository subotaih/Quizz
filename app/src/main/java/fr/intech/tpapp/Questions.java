package fr.intech.tpapp;

import java.util.List;

public class Questions {

    private List<Question> questions;
    private String type;

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
