package exam;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private int maxNumberOfQuestions;
    private String topic;
    private List<Question> questions;

    public Group(int maxNumberOfQuestions, String topic) {
        this.maxNumberOfQuestions = maxNumberOfQuestions;
        this.topic = topic;
        this.questions = new ArrayList<>();
    }

    public boolean addQuestion(Question question) {
        if (questions.size() < maxNumberOfQuestions && question.getTopics().contains(topic)) {
            questions.add(question);
            return true;
        }
        return false;
    }

    public void removeQuestion(Question question) {
        questions.remove(question);
    }

    public int getWeight() {
        int weight = 0;
        for (Question question : questions) {
            weight += question.getWeight();
        }
        return weight;
    }

    public List<Question> getQuestions() {
        return questions;
    }
}