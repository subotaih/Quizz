package fr.intech.tpapp;

import java.util.List;

public class RandomResultList {
    private List<RandomQuestions> results;
    private String response_code;

    public List<RandomQuestions> getResults() {
        return results;
    }

    public void setResults(List<RandomQuestions> results) {
        this.results = results;
    }

    public String getResponse_code() {
        return response_code;
    }

    public void setResponse_code(String response_code) {
        this.response_code = response_code;
    }
}
