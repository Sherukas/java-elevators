import java.util.Random;
import java.util.concurrent.BlockingQueue;

/**
 * This class represents a request generator responsible for generating elevator requests.
 */
public class RequestGenerator implements Runnable {
    // Instance variables
    private final int maxFloors; // Maximum number of floors in the building
    private final BlockingQueue<ElevatorRequest> queue; // Buffer for storing elevator requests
    private final Random random; // Random number generator
    private int requestId; // ID for each request

    /**
     * Constructor to initialize the request generator with maximum floors and request buffer.
     *
     * @param maxFloors Maximum number of floors in the building
     * @param queue     Buffer for storing elevator requests
     */
    public RequestGenerator(int maxFloors, BlockingQueue<ElevatorRequest> queue) {
        this.maxFloors = maxFloors;
        this.queue = queue;
        this.random = new Random();
        this.requestId = 0;
    }

    /**
     * Method to run the request generator thread, continuously generating elevator requests.
     */
    @Override
    public void run() {
        // Loop until the thread is interrupted
        while (!Thread.currentThread().isInterrupted()) {
            try {
                // Generate random start and end floors for the elevator request
                int startFloor = random.nextInt(maxFloors) + 1;
                int endFloor = generateDifferentFloor(startFloor);

                // Create a new elevator request with generated floors and increment the request ID
                ElevatorRequest request = new ElevatorRequest(startFloor, endFloor, requestId++);

                // Print the created request
                //System.out.println("Created request: " + request+"\n");

                // Put the request into the queue for processing
                queue.put(request);

                // Sleep for a random interval before generating the next request
                Thread.sleep(random.nextInt(2200) + 300);
            } catch (InterruptedException e) {
                // Interrupt the current thread if interrupted during sleep
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Method to generate a different floor from the given start floor.
     *
     * @param startFloor The start floor for the elevator request
     * @return A different floor from the start floor
     */
    private int generateDifferentFloor(int startFloor) {
        int endFloor;
        // Loop until a different floor is generated
        do {
            endFloor = random.nextInt(maxFloors) + 1;
        } while (endFloor == startFloor);
        return endFloor;
    }
}
