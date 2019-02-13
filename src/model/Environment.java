package model;

import simulator.SimulatorConstants;

import java.util.*;

public class Environment {

    private static final int INITIAL_PLANKTON = 40;
    private static final int INITIAL_SARDINE = 20;
    private static final int INITIAL_SHARK = 3;

    private final Agent[][] world;
    private final List<Agent> agents;
    private final List<Agent> newAgents;

    private int speed = 10;

    public Environment() {
        this.world = new Agent[SimulatorConstants.WORLD_WIDTH][SimulatorConstants.WORLD_HEIGHT];
        this.agents = new ArrayList<>();
        this.newAgents = new ArrayList<>();
        setSpeed(SimulatorConstants.INIT_SIM_SPEED);
        initEnvironment();
    }

    public void initEnvironment() {

        for(int i = 1; i < INITIAL_PLANKTON; i++) {
            addAgent(new Plankton());
            if(INITIAL_SARDINE >= i) {
                addAgent(new Sardine());
            }
            if(INITIAL_SHARK >= i) {
                addAgent(new Shark());
            }
        }
    }

    public Location getFreeLocation(Agent agent) {
        List<Location> locations = getFreeLocations(agent);
        if(!locations.isEmpty()) {
            return locations.get(randomInt(0, locations.size()-1));
        }
        return null;
    }

    public Agent getNeighbour(Agent agent, Class<? extends Agent> classPrey) {
        List<Agent> agents = getNeighbours(agent);
        agents.removeIf(a -> a.getClass() != classPrey);
        if(!agents.isEmpty()) {
            return agents.get(randomInt(0, agents.size()-1));
        }
        return null;
    }

    private List<Location> getFreeLocations(Agent agent) {
        Map<Location, Agent> locationsMap = getNeighbouringLocations(agent);
        List<Location> freeLocations = new ArrayList<>();

        locationsMap.forEach((location, a) -> {
            if(a == null)
                freeLocations.add(location);
        });
        return freeLocations;
    }

    private List<Agent> getNeighbours(Agent agent) {
        Map<Location, Agent> locationsMap = getNeighbouringLocations(agent);
        List<Agent> agents = new ArrayList<>();

        locationsMap.forEach((location, a) -> {
            if(a != null)
                agents.add(a);
        });
        return agents;
    }


    private Map<Location, Agent> getNeighbouringLocations(Agent agent) {
        Map<Location, Agent> locations = new HashMap<>();
        int currentX = agent.getLocation().getX();
        int currentY = agent.getLocation().getY();

        for(int xOffset = -1; xOffset <= 1; xOffset++) {
            for(int yOffset = -1; yOffset <= 1; yOffset++) {
                int x = currentX + xOffset;
                int y = currentY + yOffset;
                if(withinXBounds(x) && withinYBounds(y)) {
                    if(!agent.getLocation().equals(x, y)) {
                        Agent neighbour = world[x][y];
                        if(neighbour != null) {
                            locations.put(neighbour.getLocation(), neighbour);
                        }
                        else {
                            locations.put(new Location(x, y), null);
                        }
                    }
                }
            }
        }
        return locations;
    }

    public boolean withinXBounds(int x) {
        return x >= 0 && x < SimulatorConstants.WORLD_WIDTH;
    }

    public boolean withinYBounds(int y) {
        return y >= 0 && y < SimulatorConstants.WORLD_HEIGHT;
    }

    public static int randomInt(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    public void updateAgentLocation(Agent agent, Location location) {
        if(location != null) {
            int x = agent.getLocation().getX();
            int y = agent.getLocation().getY();
            world[x][y] = null;
            agent.getLocation().update(location);
        } else {
            agent.setLocation(getFreeSpawnLocation());
        }
        world[agent.getLocation().getX()][agent.getLocation().getY()] = agent;
    }

    //NOTE- could get stuck in while loop if there are no free spawn positions - future consideration
    public Location getFreeSpawnLocation() {
        Location location = null;
        while(location == null) {
            int randomX = randomInt(0, SimulatorConstants.WORLD_WIDTH - 1);
            int randomY = randomInt(0, SimulatorConstants.WORLD_HEIGHT - 1);
            Agent agent = world[randomX][randomY];
            if(agent == null) {
                location = new Location(randomX, randomY);
            }
        }
        return location;
    }

    public List<Agent> getAgents() {
        return agents;
    }

    public Agent getAgent(int x, int y) {
        return world[x][y];
    }

    public void addAgent(Agent agent) {
        newAgents.add(agent);
    }

    public void addNewAgents() {
        if(!newAgents.isEmpty()) {
            newAgents.forEach(a -> updateAgentLocation(a, null));
            agents.addAll(newAgents);
            newAgents.clear();
        }
    }

    public void reset() {
        agents.clear();
        newAgents.clear();
        resetWorld();
        initEnvironment();
    }

    private void resetWorld() {
        for(int x = 0; x < SimulatorConstants.WORLD_WIDTH; x++) {
            for(int y = 0; y < SimulatorConstants.WORLD_HEIGHT; y++) {
                world[x][y] = null;
            }
        }
    }

    public void clearDeadAgents() {
        getAgents().removeIf(a -> !a.isAlive());
    }

    public void actAgents() {
        getAgents().forEach(a -> a.act(this));
    }

    public void sleep() {
        try {
            Thread.sleep(1000 / getSpeed());
        } catch (InterruptedException e) {};
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
