package model;

/**
 * Created by Mitakaa on 04-Apr-16.
 */
public class CramCard {

    private long mId;
    private String mQuestion;
    private String mAnswer;

    public CramCard(String question, String answer) {
        this.mQuestion = question;
        this.mAnswer = answer;
    }

    public String getQuestion() {
        return mQuestion;
    }

    public String getAnswer() {
        return mAnswer;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }


}
