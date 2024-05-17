/**
 * This class represents a request made by a passenger to use an elevator.
 */
public class ElevatorRequest {
    // Instance variables
    private int startFloor; // Floor from which the request is made
    private int targetFloor; // Destination floor
    private int passengerId; // Passenger ID

    /**
     * Constructor to initialize an elevator request with start floor, target floor, and passenger ID.
     *
     * @param startFloor  The floor from which the request is made
     * @param targetFloor The destination floor for the request
     * @param passengerId The ID of the passenger making the request
     */
    public ElevatorRequest(int startFloor, int targetFloor, int passengerId) {
        this.startFloor = startFloor;
        this.targetFloor = targetFloor;
        this.passengerId = passengerId;
    }

    // Getter and setter methods for instance variables...

    /**
     * Method to get the start floor of the elevator request.
     *
     * @return The start floor of the request
     */
    public int getStartFloor() {
        return startFloor;
    }

    /**
     * Method to set the start floor of the elevator request.
     *
     * @param startFloor The start floor to set
     */
    public void setStartFloor(int startFloor) {
        this.startFloor = startFloor;
    }

    /**
     * Method to get the target floor of the elevator request.
     *
     * @return The target floor of the request
     */
    public int getTargetFloor() {
        return targetFloor;
    }

    /**
     * Method to set the target floor of the elevator request.
     *
     * @param targetFloor The target floor to set
     */
    public void setTargetFloor(int targetFloor) {
        this.targetFloor = targetFloor;
    }

    /**
     * Method to get the passenger ID associated with the elevator request.
     *
     * @return The passenger ID of the request
     */
    public int getPassengerId() {
        return passengerId;
    }

    /**
     * Method to set the passenger ID associated with the elevator request.
     *
     * @param passengerId The passenger ID to set
     */
    public void setPassengerId(int passengerId) {
        this.passengerId = passengerId;
    }

    /**
     * Overridden toString method to represent the elevator request as a string.
     *
     * @return A string representation of the elevator request
     */
    @Override
    public String toString() {
        return "Request{" +
                "startFloor=" + startFloor +
                ", targetFloor=" + targetFloor +
                ", passengerId=" + passengerId +
                '}';
    }
}
