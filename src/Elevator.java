/**
 * This class represents an elevator in the building.
 */
public class Elevator {
    private int elevatorNumber; // The elevator number
    private int currentFloor;   // The current floor of the elevator
    private MotionState motionState; // The direction of motion of the elevator

    // The task queue for the elevator
    private CustomPriorityQueue taskQueue;

    // Reference to the elevator controller
    final private ElevatorController elevatorManager;

    // Enumeration for the direction of elevator motion
    public enum MotionState {
        UP,
        DOWN,
        IDLE
    }

    /**
     * Default constructor for creating an elevator with default parameters.
     *
     * @param elevatorNumber  The number assigned to the elevator
     * @param elevatorManager The controller managing the elevator
     */
    public Elevator(int elevatorNumber, ElevatorController elevatorManager) {
        this(elevatorNumber, 1, MotionState.IDLE, elevatorManager);
    }

    /**
     * Main constructor for creating an elevator with specified parameters.
     *
     * @param elevatorNumber  The number assigned to the elevator
     * @param currentFloor    The current floor of the elevator
     * @param motionState     The current motion state of the elevator
     * @param elevatorManager The controller managing the elevator
     */
    public Elevator(int elevatorNumber, int currentFloor, MotionState motionState, ElevatorController elevatorManager) {
        this.elevatorNumber = elevatorNumber;
        this.currentFloor = currentFloor;
        this.motionState = motionState;
        this.taskQueue = new CustomPriorityQueue();
        this.elevatorManager = elevatorManager;
    }

    // Getter for elevatorManager
    public ElevatorController getElevatorManager() {
        return elevatorManager;
    }

    // Getter for elevatorNumber
    public int getElevatorNumber() {
        return elevatorNumber;
    }

    // Getter for TaskQueue
    public CustomPriorityQueue getTaskQueue() {
        return taskQueue;
    }

    // Setter for elevatorNumber
    public void setElevatorNumber(int elevatorNumber) {
        this.elevatorNumber = elevatorNumber;
    }

    // Getter for currentFloor
    public int getCurrentFloor() {
        return currentFloor;
    }

    // Getter for motionState
    public MotionState getMotionState() {
        return motionState;
    }

    // Setter for motionState
    public void setMotionState(MotionState motionState) {
        this.motionState = motionState;
    }

    // Setter for taskQueue
    public void setTaskQueue(CustomPriorityQueue taskQueue) {
        this.taskQueue = taskQueue;
    }

    /**
     * Method to add a request to the elevator's task queue.
     *
     * @param floor    The floor to which the request is made
     * @param idPerson The ID of the person making the request
     * @param purpose  The purpose of the request (deliver or take)
     */
    public void addRequest(int floor, int idPerson, ElementQueue.Purpose purpose) {
        int p1 = purpose.ordinal() + Math.abs(currentFloor - floor);
        taskQueue.addElement(p1, floor, idPerson, purpose);
    }

    /**
     * Method to update the motion state of the elevator based on the task queue.
     */
    private void updateMotionState() {
        if (taskQueue.isEmpty()) {
            motionState = MotionState.IDLE;
        } else {
            ElementQueue nextRequest = taskQueue.peek();
            boolean found = false;
            for (ElementQueue request : taskQueue.getQueue()) {
                if (request.getFloor() == currentFloor) {
                    found = true;
                    break;
                }
            }
            if (found) {
                taskQueue.removeAllWithValue(currentFloor);
                elevatorManager.call(elevatorNumber, currentFloor);
                motionState = MotionState.IDLE;
            } else if (nextRequest.getFloor() > currentFloor) {
                motionState = MotionState.UP;
            } else if (nextRequest.getFloor() < currentFloor) {
                motionState = MotionState.DOWN;
            } else {
                motionState = MotionState.IDLE;
            }
        }
    }

    /**
     * Method to execute a step in the elevator's operation.
     */
    public void step() {
        if (taskQueue.isEmpty()) {
            updateMotionState();
            return;
        }
        taskQueue.updatePriorities(1);
        ElementQueue currentRequest = taskQueue.peek();
        if (currentFloor < currentRequest.getFloor()) {
            ++currentFloor;
        } else if (currentFloor > currentRequest.getFloor()) {
            --currentFloor;
        }
        updateMotionState();
    }

    /**
     * Method to represent the elevator's information as a string.
     */
    @Override
    public String toString() {
        return "Elevator{" +
                "elevatorNumber=" + elevatorNumber +
                ", currentFloor=" + currentFloor +
                ", motionState='" + motionState + '\'' +
                ", taskQueue=" + taskQueue +
                '}';
    }
}
