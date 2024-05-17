import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This class initializes and starts the elevator simulation system.
 */
public class Main {
    public static void main(String[] args) {
        // Total number of floors and elevators in the building
        int totalFloors = 12;
        int totalElevators = 2;

        // Creating a buffer for elevator requests
        BlockingQueue<ElevatorRequest> requestBuffer = new LinkedBlockingQueue<>();

        // Initializing the elevator controller with the specified number of elevators, floors, and request buffer
        ElevatorController elevatorManager = new ElevatorController(totalElevators, totalFloors, requestBuffer);

        // Initializing the elevator system with the elevator controller
        ElevatorSystem elevatorSystem = new ElevatorSystem(elevatorManager);

        // Initializing the request generator with the total number of floors and request buffer
        RequestGenerator requestGenerator = new RequestGenerator(totalFloors, requestBuffer);

        // Creating threads for elevator system and request generator
        Thread elevatorSystemThread = new Thread(elevatorSystem);
        Thread requestGenThread = new Thread(requestGenerator);

        // Starting the threads
        elevatorSystemThread.start();
        requestGenThread.start();

        try {
            // Running the simulation
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            System.err.println("Error in simulating system operation");
        }

        // Interrupting the request generator thread
        requestGenThread.interrupt();
        try {
            requestGenThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Interrupting the elevator system thread
        elevatorSystemThread.interrupt();
        try {
            // Waiting for the elevator system thread to finish
            elevatorSystemThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
