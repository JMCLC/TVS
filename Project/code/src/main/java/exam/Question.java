package exam;

import java.util.List;

import exam.exception.InvalidOperationException;

public class Question {

    private final String body;
    private final List<String> choices;
    private final int correctChoice;
    private List<String> topics;
    private int weight;

    public Question(String body, List<String> choices, int correctChoice, List<String> topics, int weight) throws InvalidOperationException 
    {

        if (body != null && choices.size() >= 2 
                        && choices.size() <= 8  
                        && correctChoice >= 0 
                        && correctChoice < choices.size()
                        && weight > 0 && weight <= 15
                        && topics != null && topics.size() > 0 && topics.size() <= 5
                        && topics.stream().allMatch(t -> t.length() >= 6)
            ) 
        {
            this.body = body;
            this.choices = choices;
            this.correctChoice = correctChoice;
            this.topics = topics;
            this.weight = weight;
        } else {
            throw new InvalidOperationException("Invalid question");
        }
    
    }
    // Removes a topic
    public void remove(String topic) throws InvalidOperationException {
        if (topic == null || !topics.contains(topic)) {
            throw new InvalidOperationException("Invalid topic");
        }
        topics.remove(topic);
    }
    // Adds a new topic
    public void add(String topic) throws InvalidOperationException {
        if (topic == null || topic.length() < 6 ) {
            throw new InvalidOperationException("Invalid topic");
        }
        if (topics.size() == 5) {
            throw new InvalidOperationException("Too many topics");
        }
        topics.add(topic);
    }
    // Returns all topics of this question
    public List<String> getTopics() {
        return topics;
    }
    // Computes the grade considering the weight of this question and the selected choice
    public float grade(int selectedChoice) {
        if (selectedChoice == correctChoice) {
            return (float) (weight * 0.20);
        } else {
            return -1/choices.size() - 1;
        }
    }
    // Changes the weight of this question
    public void setWeight(int weight) throws InvalidOperationException {
        if (weight <= 0 || weight > 15) {
            throw new InvalidOperationException("Invalid weight");
        }
        this.weight = weight;
    }
    // Returns the weight of this question
    public int getWeight() {
        return weight;
    }
    // Returns the choices of this question
    public List<String> getChoices() {
        return choices;
    }
    }
    