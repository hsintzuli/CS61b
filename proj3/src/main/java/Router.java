import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * This class provides a shortestPath method for finding routes between two points
 * on the map. Start by using Dijkstra's, and if your code isn't fast enough for your
 * satisfaction (or the autograder), upgrade your implementation by switching it to A*.
 * Your code will probably not be fast enough to pass the autograder unless you use A*.
 * The difference between A* and Dijkstra's is only a couple of lines of code, and boils
 * down to the priority you use to order your vertices.
 */
public class Router {

    /**
     * Return a List of longs representing the shortest path from the node
     * closest to a start location and the node closest to the destination
     * location.
     * @param g The graph to use.
     * @param stlon The longitude of the start location.
     * @param stlat The latitude of the start location.
     * @param destlon The longitude of the destination location.
     * @param destlat The latitude of the destination location.
     * @return A list of node id's in the order visited on the shortest path.
     */
    public static List<Long> shortestPath(GraphDB g, double stlon, double stlat,
                                          double destlon, double destlat) {
        PriorityQueue<Vertices> fringe = new PriorityQueue<>(new Vertices_Comparator());
        Map<Long, GraphDB.Node> nodes = g.getNodes();
        List<Long> bestPath = new LinkedList<>();
        Stack<Long> tempPath = new Stack<>();

        HashMap<Long, Long> edgeTo = new HashMap<>();
        HashMap<Long, Double> distTo = new HashMap<>();

        GraphDB.Node startNode = nodes.get(g.closest(stlon, stlat));
        GraphDB.Node endNode = nodes.get(g.closest(destlon, destlat));

        fringe.add(new Vertices(startNode.id, 0));
        edgeTo.put(startNode.id, startNode.id);
        distTo.put(startNode.id, 0.0);

        while (!fringe.isEmpty()) {
            long v = fringe.poll().nodeID;
            if (v == endNode.id) {
                break;
            }

            for (long n : g.adjacent(v)) {
                double dist = distTo.get(v) + g.distance(v, n);
                if (distTo.containsKey(n) && distTo.get(n) < dist) {
                    continue;
                }
                edgeTo.put(n, v);
                distTo.put(n, dist);
                double f = dist + g.distance(endNode.id, n);
                fringe.add(new Vertices(n, f));
            }
        }
        long thisNode = endNode.id;
        while (thisNode != startNode.id) {
            tempPath.push(thisNode);
            thisNode = edgeTo.get(thisNode);
        }
        tempPath.push(thisNode);
        while (!tempPath.isEmpty()) {
            bestPath.add(tempPath.pop());
        }

        return bestPath;
    }

    static class Vertices {
        long nodeID;
        double priority;

        Vertices (long id, double pri) {
            nodeID = id;
            priority = pri;
        }
    }

    private static class Vertices_Comparator implements Comparator<Vertices> {
        @Override
        public int compare(Vertices a, Vertices b) {
            double value = a.priority - b.priority;
            if (value > 0) {
                return 1;
            } else if (value < 0) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    /**
     * Create the list of directions corresponding to a route on the graph.
     * @param g The graph to use.
     * @param route The route to translate into directions. Each element
     *              corresponds to a node from the graph in the route.
     * @return A list of NavigatiionDirection objects corresponding to the input
     * route.
     */
    public static List<NavigationDirection> routeDirections(GraphDB g, List<Long> route) {
        List<NavigationDirection> directions = new LinkedList<>();
        Stack<String> routeStack = new Stack<>();
        Map<Long, GraphDB.Node> nodes = g.getNodes();

        NavigationDirection direction = new NavigationDirection();
        Double degree = 0.0;
        Double newDegree;
        Double dist = 0.0;

        for (int i = 0; i < route.size() - 1; i++) {
            String thisWay = "unknown road";
            GraphDB.Node prevNode = nodes.get(route.get(i));
            GraphDB.Node lastNode = nodes.get(route.get(i + 1));
            Set<String> way = new HashSet<>();
            way.addAll(prevNode.ways);
            way.retainAll(lastNode.ways);
            newDegree = g.bearing(prevNode.id, lastNode.id);
            Iterator<String> iter = way.iterator();
            if (iter.hasNext()) {
                thisWay = iter.next();
                if (thisWay == null) {
                    thisWay =  "unknown road";
                }
            }

            Double d = g.distance(prevNode.id, lastNode.id);


            if (routeStack.isEmpty()){
                routeStack.push(thisWay);
                direction.way = thisWay;
                directions.add(direction);
                direction.direction = NavigationDirection.START;
            } else if (!thisWay.equals(routeStack.peek())) {
                direction.distance = dist;

                direction = new NavigationDirection();
                dist = 0.0;
                direction.way = thisWay;

                if (degree < 15 && degree > -15) {
                    direction.direction = 1;
                } else if (degree < 30 && degree > -30) {
                    if (degree > 0) {
                        direction.direction = 3;
                    } else {
                        direction.direction = 2;
                    }
                } else if (degree < 100 && degree > -100) {
                    if (degree > 0) {
                        direction.direction = 4;
                    } else {
                        direction.direction = 5;
                    }
                } else {
                    if (degree > 0) {
                        direction.direction = 7;
                    } else {
                        direction.direction = 6;
                    }
                }
                directions.add(direction);
                routeStack.push(thisWay);
            }
            degree = newDegree;
            dist += d;
        }
        direction.distance = dist;
        return directions;
    }

    /**
     * Class to represent a navigation direction, which consists of 3 attributes:
     * a direction to go, a way, and the distance to travel for.
     */
    public static class NavigationDirection {

        /** Integer constants representing directions. */
        public static final int START = 0;
        public static final int STRAIGHT = 1;
        public static final int SLIGHT_LEFT = 2;
        public static final int SLIGHT_RIGHT = 3;
        public static final int RIGHT = 4;
        public static final int LEFT = 5;
        public static final int SHARP_LEFT = 6;
        public static final int SHARP_RIGHT = 7;

        /** Number of directions supported. */
        public static final int NUM_DIRECTIONS = 8;

        /** A mapping of integer values to directions.*/
        public static final String[] DIRECTIONS = new String[NUM_DIRECTIONS];

        /** Default name for an unknown way. */
        public static final String UNKNOWN_ROAD = "unknown road";
        
        /** Static initializer. */
        static {
            DIRECTIONS[START] = "Start";
            DIRECTIONS[STRAIGHT] = "Go straight";
            DIRECTIONS[SLIGHT_LEFT] = "Slight left";
            DIRECTIONS[SLIGHT_RIGHT] = "Slight right";
            DIRECTIONS[LEFT] = "Turn left";
            DIRECTIONS[RIGHT] = "Turn right";
            DIRECTIONS[SHARP_LEFT] = "Sharp left";
            DIRECTIONS[SHARP_RIGHT] = "Sharp right";
        }

        /** The direction a given NavigationDirection represents.*/
        int direction;
        /** The name of the way I represent. */
        String way;
        /** The distance along this way I represent. */
        double distance;

        /**
         * Create a default, anonymous NavigationDirection.
         */
        public NavigationDirection() {
            this.direction = STRAIGHT;
            this.way = UNKNOWN_ROAD;
            this.distance = 0.0;
        }

        public String toString() {
            return String.format("%s on %s and continue for %.3f miles.",
                    DIRECTIONS[direction], way, distance);
        }

        /**
         * Takes the string representation of a navigation direction and converts it into
         * a Navigation Direction object.
         * @param dirAsString The string representation of the NavigationDirection.
         * @return A NavigationDirection object representing the input string.
         */
        public static NavigationDirection fromString(String dirAsString) {
            String regex = "([a-zA-Z\\s]+) on ([\\w\\s]*) and continue for ([0-9\\.]+) miles\\.";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(dirAsString);
            NavigationDirection nd = new NavigationDirection();
            if (m.matches()) {
                String direction = m.group(1);
                if (direction.equals("Start")) {
                    nd.direction = NavigationDirection.START;
                } else if (direction.equals("Go straight")) {
                    nd.direction = NavigationDirection.STRAIGHT;
                } else if (direction.equals("Slight left")) {
                    nd.direction = NavigationDirection.SLIGHT_LEFT;
                } else if (direction.equals("Slight right")) {
                    nd.direction = NavigationDirection.SLIGHT_RIGHT;
                } else if (direction.equals("Turn right")) {
                    nd.direction = NavigationDirection.RIGHT;
                } else if (direction.equals("Turn left")) {
                    nd.direction = NavigationDirection.LEFT;
                } else if (direction.equals("Sharp left")) {
                    nd.direction = NavigationDirection.SHARP_LEFT;
                } else if (direction.equals("Sharp right")) {
                    nd.direction = NavigationDirection.SHARP_RIGHT;
                } else {
                    return null;
                }

                nd.way = m.group(2);
                try {
                    nd.distance = Double.parseDouble(m.group(3));
                } catch (NumberFormatException e) {
                    return null;
                }
                return nd;
            } else {
                // not a valid nd
                return null;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof NavigationDirection) {
                return direction == ((NavigationDirection) o).direction
                    && way.equals(((NavigationDirection) o).way)
                    && distance == ((NavigationDirection) o).distance;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(direction, way, distance);
        }
    }
}
