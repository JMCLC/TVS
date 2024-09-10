package exam;

import java.util.ArrayList;
import java.util.List;

public class ExamModel {
    private ModelState state;
    private List<Group> groups;

    public ExamModel() {
        this.state = ModelState.OPEN;
        this.groups = new ArrayList<>();
    }

    public List<Question> getQuestions() {
        List<Question> questions = new ArrayList<>();
        for (Group group : groups) {
            questions.addAll(group.getQuestions());
        }
        return questions;
    }

    public int addGroup(int maxNumberOfQuestions, String topic) {
        if (state == ModelState.OPEN) {
            Group group = new Group(maxNumberOfQuestions, topic);
            groups.add(group);
            return groups.size();
        }
        return -1;
    }

    public boolean addQuestion(Question question, int groupId) {
        if (state == ModelState.OPEN && groupId >= 1 && groupId <= groups.size()) {
            Group group = groups.get(groupId - 1);
            return group.addQuestion(question);
        }
        return false;
    }

    public void removeQuestion(Question question, int groupId) {
        if (state == ModelState.OPEN && groupId >= 1 && groupId <= groups.size()) {
            Group group = groups.get(groupId - 1);
            group.removeQuestion(question);
        }
    }

    public String validate() {
        if (state == ModelState.CLOSED) {
            return null;
        }
        if (groups.size() < 2 || groups.size() > 8) {
            return "Number of groups is not between 2 and 8";
        }
        int totalWeight = 0;
        int totalQuestions = 0;
        for (Group group : groups) {
            totalWeight += group.getWeight();
            totalQuestions += group.getQuestions().size();
        }
        if (totalWeight != 100) {
            return "Exam weight is not 100%";
        }
        double avgQuestionsPerGroup = (double) totalQuestions / groups.size();
        if (avgQuestionsPerGroup > 4) {
            return "Average number of questions per group is greater than 4";
        }
        state = ModelState.CLOSED;
        return null;
    }
}