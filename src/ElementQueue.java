/**
 * This class represents an element in the elevator task queue.
 */
public class ElementQueue {
    private int priority; // The priority of the elevator task
    private final int floor; // The floor associated with the elevator task
    private final int idPerson; // The ID of the person associated with the elevator task
    private final Purpose purpose; // The purpose of the elevator task

    // Enumeration for the purpose of the elevator task
    public enum Purpose {
        Deliver, // Delivering a person to a floor
        Take     // Taking a person from a floor
    }

    /**
     * Constructor to initialize an element in the elevator task queue.
     *
     * @param priority The priority of the elevator task
     * @param floor    The floor associated with the elevator task
     * @param idPerson The ID of the person associated with the elevator task
     * @param purpose  The purpose of the elevator task
     */
    public ElementQueue(int priority, int floor, int idPerson, Purpose purpose) {
        this.priority = priority;
        this.floor = floor;
        this.idPerson = idPerson;
        this.purpose = purpose;
    }

    // Getter for priority
    public int getPriority() {
        return priority;
    }

    // Getter for floor
    public int getFloor() {
        return floor;
    }

    // Getter for idPerson
    public int getIdPerson() {
        return idPerson;
    }

    // Getter for purpose
    public Purpose getPurpose() {
        return purpose;
    }

    /**
     * Method to decrease the priority of the elevator task by a specified amount.
     *
     * @param amount The amount by which to decrease the priority
     */
    public void decreasePriority(int amount) {
        this.priority -= amount;
    }

    /**
     * Method to represent the element in the elevator task queue as a string.
     */
    @Override
    public String toString() {
        return "(" + priority + ", " + floor + ", " + idPerson + ", " + purpose + ")";
    }
}
