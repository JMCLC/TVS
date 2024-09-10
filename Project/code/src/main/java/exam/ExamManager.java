package exam;

import java.util.List;

import exam.exception.InvalidInvocationException;

import java.util.ArrayList;

public class ExamManager {
    private ManagerMode mode;
    private ExamModel model;
    private List<Student> studentsRegistered;
    private List<Student> studentsThatAccessed;
    private List<Student> studentsThatDelivered;
    
    public ExamManager(ExamModel model) {
        this.model = model;
        this.mode = ManagerMode.OPEN;
        this.studentsRegistered = new ArrayList<>();
        this.studentsThatAccessed = new ArrayList<>();
        this.studentsThatDelivered = new ArrayList<>();
    }

    // Returns the mode of this exam manager
    public final ManagerMode getMode() { 
       return mode;     
    }
    
    // Enrolls a student for this exam
    public void enroll(Student t) throws InvalidInvocationException {
        if (mode != ManagerMode.OPEN)
            throw new InvalidInvocationException();
        if (studentsRegistered.contains(t))
            return;
        if (studentsRegistered.size() == 9)
            close();
        studentsRegistered.add(t);
    }
    
    // Cancels start and close operations
    public void cancel() { 
        if (mode == ManagerMode.EVALUATION)
            mode = ManagerMode.OPEN;
        else if (mode == ManagerMode.TERMINATED)
            mode = ManagerMode.EVALUATION;
    }
    
    // Returns an exam for the (enrolled) student
    public Exam getExam(Student student) throws InvalidInvocationException {
        if (mode != ManagerMode.EVALUATION)
            throw new InvalidInvocationException();
        studentsThatAccessed.add(student);
        Exam exam = new Exam(student, model);
        student.setExam(model, exam);
        return exam;
    }

    // Finish current state (
    public void close() { 
        if (mode == ManagerMode.OPEN)
            mode = ManagerMode.EVALUATION;
        else if (mode == ManagerMode.EVALUATION)
            mode = ManagerMode.TERMINATED;
        else if (mode == ManagerMode.TERMINATED)
            mode = ManagerMode.PUBLISHED;
     }

    // Publishes the results
    public void submit(Exam exam) throws InvalidInvocationException {
        if (mode != ManagerMode.EVALUATION)
            throw new InvalidInvocationException();
        Student student = exam.getStudent();
        studentsThatDelivered.add(student);
        student.setExam(model, exam);
     }
    
    // Returns the evaluation of the exam made by the specified student
    public float evaluate(Student student) throws InvalidInvocationException { 
        if (mode != ManagerMode.PUBLISHED)
            throw new InvalidInvocationException();
        if (!studentsThatDelivered.contains(student))
            return -1;
        Exam exam = student.getExam(model);
        if (!exam.finished())
            return -1;
        studentsThatDelivered.add(student);
        return exam.evaluate();
    }
}