import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;

/**
 * Controller class for managing elevators and processing elevator requests.
 */
public class ElevatorController {
    // List of elevators in the system
    List<Elevator> liftList;
    // Buffer to hold incoming elevator requests
    BlockingQueue<ElevatorRequest> requestBuffer;
    // Queues for holding requests specific to each floor
    Queue<ElevatorRequest>[] floorBuffers;
    // Total number of floors in the building
    int totalFloors;

    /**
     * Constructor to initialize the ElevatorController with the specified number of elevators and floors.
     *
     * @param totalLifts    The total number of elevators
     * @param totalFloors   The total number of floors
     * @param requestBuffer The shared buffer for elevator requests
     */
    @SuppressWarnings("unchecked")
    public ElevatorController(int totalLifts, int totalFloors, BlockingQueue<ElevatorRequest> requestBuffer) {
        liftList = new ArrayList<>();
        for (int i = 0; i < totalLifts; i++) {
            liftList.add(new Elevator(i, this));
        }
        this.requestBuffer = requestBuffer;
        floorBuffers = new Queue[totalFloors];
        for (int i = 0; i < totalFloors; i++) {
            floorBuffers[i] = new LinkedList<>();
        }
        this.totalFloors = totalFloors;
    }

    /**
     * Main loop to process incoming elevator requests and update elevator states.
     * This method runs continuously to simulate the operation of the elevator system.
     *
     * @throws InterruptedException If the thread running this method is interrupted
     */
    public void processRequests() throws InterruptedException {
        while (true) {
            // Process requests from the buffer
            if (!requestBuffer.isEmpty()) {
                for (int i = 0; i < requestBuffer.size(); ++i) {
                    ElevatorRequest request = requestBuffer.take();
                    floorBuffers[request.getStartFloor() - 1].add(request);
                    Elevator needElevator = selectOptimalElevator(request);
                    if (needElevator != null) {
                        needElevator.addRequest(request.getStartFloor(), request.getPassengerId(), ElementQueue.Purpose.Take);
                    }
                }
            }

            // Update each elevator's state
            for (Elevator elevator : liftList) {
                elevator.step();
            }
            // Display the current state of System
            printSystemState1();
            System.out.println();

            try {
                // Simulate the system working for a short period
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.err.println("Elevator operation interrupted");
                break;
            }
        }
    }

    /**
     * The method of telling the elevator to the controller that it has stopped on a certain floor.
     *
     * @param elevatorNumber The number of the elevator to be called
     * @param floor          The floor number where the elevator is called
     */
    public void call(int elevatorNumber, int floor) {
        Elevator elevator = liftList.get(elevatorNumber);
        Queue<ElevatorRequest> floorQueue = floorBuffers[floor - 1];
        if (!floorQueue.isEmpty()) {
            for (ElevatorRequest req : floorQueue) {
                elevator.addRequest(req.getTargetFloor(), req.getPassengerId(), ElementQueue.Purpose.Deliver);
            }
            floorQueue.clear();
        }
    }

    /**
     * Method to select the optimal elevator to handle a given request.
     *
     * @param request The elevator request
     * @return The optimal elevator to handle the request
     */
    private Elevator selectOptimalElevator(ElevatorRequest request) {
        Elevator optimalElevator = null;
        int bestScore = Integer.MAX_VALUE;
        for (Elevator elevator : liftList) {
            int score = computeScore(elevator, request);
            if (score < bestScore) {
                bestScore = score;
                optimalElevator = elevator;
            }
        }
        return optimalElevator;
    }

    /**
     * Method to compute a score for an elevator handling a given request.
     *
     * @param lift    The elevator being scored
     * @param request The elevator request
     * @return The score indicating the suitability of the elevator for the request
     */
    private int computeScore(Elevator lift, ElevatorRequest request) {
        int distance = Math.abs(lift.getCurrentFloor() - request.getStartFloor());
        int score = distance + lift.getTaskQueue().getQueue().size();
        if (lift.getTaskQueue().getQueue().size() >= 3) {
            score += lift.getTaskQueue().getQueue().size();
        }
        if (!lift.getTaskQueue().isEmpty()) {
            score += (lift.getTaskQueue().peek().getFloor() != request.getStartFloor()) ? 2 : 1;
        }
        if ((lift.getMotionState() == Elevator.MotionState.UP && request.getStartFloor() < lift.getCurrentFloor()) || (lift.getMotionState() == Elevator.MotionState.DOWN && request.getStartFloor() > lift.getCurrentFloor())) {
            return score + this.totalFloors / 3;
        }
        return score;
    }

    /**
     * Method to print the current state of the elevator system.
     * Displays information about each elevator including its number, current floor, direction, and requests.
     * Also prints the current state of each floor's request buffer, showing the origin and destination floors of pending requests.
     * It also visualizes the movement of elevators and waiting people in the terminal
     */
    public void printSystemState() {
        // Print elevator information
        System.out.println("Elevator Information:\n");
        for (Elevator elevator : liftList) {
            System.out.println("Elevator " + elevator.getElevatorNumber() + ":");
            System.out.println("  Current Floor: " + elevator.getCurrentFloor());
            System.out.println("  Direction: " + elevator.getMotionState());
            System.out.println("  Requests:");
            for (ElementQueue request : elevator.getTaskQueue().getQueue()) {
                System.out.println("    Floor: " + request.getFloor() + ", Passenger ID: " + request.getIdPerson() + ", Purpose: " + request.getPurpose());
            }
            System.out.println();
        }

        // Print floor buffer information
        System.out.println("Information about people waiting on the floors:");
        for (int i = totalFloors; i >= 1; i--) {
            System.out.println("Floor " + i + " Requests:");
            Queue<ElevatorRequest> queue = floorBuffers[i - 1];
            for (ElevatorRequest request : queue) {
                System.out.println("  From Floor: " + request.getStartFloor() + ", To Floor: " + request.getTargetFloor() + ", Passenger ID: " + request.getPassengerId());
            }
        }
        System.out.println("\n\n");
    }

    public void printSystemState1() {
        // Print elevator information
        System.out.println("Elevator Information:");
        for (Elevator elevator : liftList) {
            System.out.println("Elevator " + elevator.getElevatorNumber() + ":");
            System.out.println("  Current Floor: " + elevator.getCurrentFloor());
            System.out.println("  Direction: " + elevator.getMotionState());
            System.out.println("  Requests:");
            for (ElementQueue request : elevator.getTaskQueue().getQueue()) {
                System.out.println("    Floor: " + request.getFloor() + ", Passenger ID: " + request.getIdPerson() + ", Purpose: " + request.getPurpose());
            }
            System.out.println();
        }

        // Print floor buffer information
        System.out.println("Information about people waiting on the floors:");
        for (int i = totalFloors; i >= 1; i--) {
            System.out.println("Floor " + i + " Requests:");
            Queue<ElevatorRequest> queue = floorBuffers[i - 1];
            for (ElevatorRequest request : queue) {
                System.out.println("  From Floor: " + request.getStartFloor() + ", To Floor: " + request.getTargetFloor() + ", Passenger ID: " + request.getPassengerId());
            }
        }

        // Visualize elevators and floor requests
        System.out.println("\nElevator Visualization:");
        System.out.print("Floors        Elevators    People_on_the_floors");
        for (int i = 0; i < liftList.size(); ++i)
            System.out.printf("    id_of_people_in_%d_elevator", i);
        System.out.println();
        for (int i = totalFloors; i >= 1; i--) {
            System.out.printf("Floor %2d:     ", i);
            for (Elevator elevator : liftList) {
                if (elevator.getCurrentFloor() == i) {
                    System.out.printf("%-2s", "□");
                } else {
                    System.out.printf("%-2s", "| ");
                }
            }
            System.out.print("         -");
            Queue<ElevatorRequest> queue = floorBuffers[i - 1];

            StringBuilder peopleOnFloor = new StringBuilder();
            for (ElevatorRequest request : queue) {
                peopleOnFloor.append("P");
            }
            System.out.printf("%-23s", peopleOnFloor);
            if (i == totalFloors) {
                for (Elevator elevator : liftList) {
                    StringBuilder peopleInElevator = new StringBuilder();
                    for (ElementQueue request : elevator.getTaskQueue().getQueue()) {
                        if (request.getPurpose() == ElementQueue.Purpose.Deliver) {
                            peopleInElevator.append(request.getIdPerson()).append(" ");
                        }
                    }
                    System.out.printf("%-30s", peopleInElevator);
                }
            }

            System.out.println();
        }
        System.out.println("\n\n\n\n\n\n");
    }
}
