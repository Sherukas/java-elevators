/**
 * This class represents a thread for managing elevators and processing elevator requests.
 */
public class ElevatorSystem implements Runnable {
    // Instance variable
    final private ElevatorController elevatorManager; // Reference to the elevator controller

    /**
     * Constructor to initialize the elevator system with an elevator controller.
     *
     * @param controller The elevator controller responsible for managing elevators and requests
     */
    public ElevatorSystem(ElevatorController controller) {
        this.elevatorManager = controller;
    }

    /**
     * Method to run the elevator system thread, which continuously processes elevator requests.
     */
    @Override
    public void run() {
        try {
            // Process elevator requests using the elevator manager
            elevatorManager.processRequests();
        } catch (InterruptedException e) {
            // Interrupt the current thread if interrupted during processing
            Thread.currentThread().interrupt();
        }
    }
}
