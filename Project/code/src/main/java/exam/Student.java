package exam;

import java.util.HashMap;

public class Student {
    private HashMap<ExamModel, Exam> exams;

    public Student() {
        exams = new HashMap<>();
    }

    public void setExam(ExamModel model, Exam exam) {
        exams.put(model, exam);
    }

    public Exam getExam(ExamModel model) {
        return exams.get(model);
    }
}
