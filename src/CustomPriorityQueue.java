import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * This class represents a custom priority queue for managing elevator tasks.
 */
public class CustomPriorityQueue {
    private PriorityQueue<ElementQueue> taskQueue; // Priority queue for storing elevator tasks

    /**
     * Constructor to initialize the custom priority queue.
     */
    public CustomPriorityQueue() {
        // Initialize the priority queue with a comparator based on task priority
        this.taskQueue = new PriorityQueue<>(Comparator.comparingInt(ElementQueue::getPriority));
    }

    /**
     * Method to set the queue of the custom priority queue.
     *
     * @param taskQueue The priority queue to set
     */
    public void setQueue(PriorityQueue<ElementQueue> taskQueue) {
        this.taskQueue = taskQueue;
    }

    /**
     * Method to get the underlying priority queue.
     *
     * @return The priority queue
     */
    public PriorityQueue<ElementQueue> getQueue() {
        return taskQueue;
    }

    /**
     * Method to add an element to the priority queue with the specified priority, floor, person ID, and purpose.
     *
     * @param priority The priority of the elevator task
     * @param floor    The floor associated with the elevator task
     * @param idPerson The ID of the person associated with the elevator task
     * @param purpose  The purpose of the elevator task
     */
    public void addElement(int priority, int floor, int idPerson, ElementQueue.Purpose purpose) {
        ElementQueue newElement = new ElementQueue(priority, floor, idPerson, purpose);
        taskQueue.add(newElement);
    }

    /**
     * Method to retrieve and remove the highest-priority element from the queue.
     *
     * @return The highest-priority element
     */
    public ElementQueue poll() {
        return taskQueue.poll();
    }

    /**
     * Method to retrieve, but not remove, the highest-priority element from the queue.
     *
     * @return The highest-priority element
     */
    public ElementQueue peek() {
        return taskQueue.peek();
    }

    /**
     * Method to update the priorities of all elements in the queue by decrementing them by a specified amount.
     *
     * @param decrement The amount by which to decrement the priorities
     */
    public void updatePriorities(int decrement) {
        PriorityQueue<ElementQueue> updatedQueue = new PriorityQueue<>(Comparator.comparingInt(ElementQueue::getPriority));
        while (!taskQueue.isEmpty()) {
            ElementQueue element = taskQueue.poll();
            element.decreasePriority(decrement);
            updatedQueue.add(element);
        }
        taskQueue = updatedQueue;
    }

    /**
     * Method to remove all elements from the queue with a specified floor value.
     *
     * @param floor The floor value to match for removal
     */
    public void removeAllWithValue(int floor) {
        PriorityQueue<ElementQueue> updatedQueue = new PriorityQueue<>(Comparator.comparingInt(ElementQueue::getPriority));
        for (ElementQueue element : taskQueue) {
            if (element.getFloor() != floor) {
                updatedQueue.add(element);
            }
        }
        taskQueue = updatedQueue;
    }

    /**
     * Method to check if the queue is empty.
     *
     * @return True if the queue is empty, otherwise false
     */
    public boolean isEmpty() {
        return taskQueue.isEmpty();
    }

    /**
     * Method to represent the custom priority queue as a string.
     */
    @Override
    public String toString() {
        return taskQueue.toString();
    }
}
