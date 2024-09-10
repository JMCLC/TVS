package ap;

import java.util.HashSet;
import java.util.Set;

public class PersonSet {
    private Set<Person> personSet;
    private int maxSize;

    public PersonSet(int maxSize) {
        this.maxSize = maxSize;
        this.personSet = new HashSet<>();
    }

    public boolean add(Person p) throws InvalidInvocationException {
        if (personSet.size() == maxSize) {
            throw new InvalidInvocationException("PersonSet is full.");
        }

        if (personSet.contains(p)) {
            return false; // Person already exists in the set
        }
        
        return personSet.add(p);
    }

    public int getSize() {
        return personSet.size();
    }
}

class InvalidInvocationException extends Exception {
    public InvalidInvocationException(String message) {
        super(message);
    }
}