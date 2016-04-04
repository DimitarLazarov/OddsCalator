package model;

/**
 * Created by Mitakaa on 04-Apr-16.
 */
public class CramCard {
    private String question;
    private String answer;

    public CramCard(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}
