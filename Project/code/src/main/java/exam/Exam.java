package exam;

import java.util.HashMap;

public class Exam {
    private Student student;
    private ExamModel model;
    private HashMap<Question, Integer> answers;

    public Exam(Student student, ExamModel model) {
        this.student = student;
        this.model = model;
        this.answers = new HashMap<>();
    }

    public Student getStudent() {
        return student;
    }

    public void selectChoice(Question question, int choice) {
        answers.put(question, choice);
    }

    public boolean finished() {
        return model.getQuestions().size() != answers.size();
    }

    public float evaluate() {
        float grade = 0;
        for (Question question : answers.keySet()) {
            grade += question.grade(answers.get(question));
        }
        return grade;
    }
}
