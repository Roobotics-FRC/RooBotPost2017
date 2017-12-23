package org.usfirst.frc.team4373.robot.input.hid;

import edu.wpi.first.wpilibj.DriverStation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.stream.Stream;

/**
 * Records events by HID components and saves them for autonomous playback.
 * Essentially a keyboard macro for a robot.
 * @author aaplmath
 * @author Alex Fang
 * @author samasaur
 */
public class HIDLogger {
    public static boolean isProfiling = false;
    private ArrayList<Action> actionLog;

    private static HIDLogger logger;

    /**
     * Fetches the instance of HIDLogger.
     * @return the instance of HIDLogger.
     */
    public static HIDLogger getHIDLogger() {
        if (logger == null) {
            synchronized (HIDLogger.class) {
                if (logger == null) logger = new HIDLogger();
            }
        }
        return logger;
    }

    private HIDLogger() {
        this.actionLog = new ArrayList<>();
    }

    protected enum ActorType {
        Axis(0), Button(1), POV(2);

        protected final int value;

        ActorType(int value) {
            this.value = value;
        }

        private static final HashMap<Integer,ActorType> lookup = new HashMap<>();

        static {
            for (ActorType s: EnumSet.allOf(ActorType.class)) lookup.put(s.value, s);
        }

        public static ActorType get(int value) {
            return lookup.get(value);
        }
    }

    public static class Action {
        ActorType actorType;
        int actorID;
        // Booleans are represented as 0 == false and 1 == true
        double actorValue;

        Action(ActorType actorType, int actorID, double actorValue) {
            this.actorType = actorType;
            this.actorID = actorID;
            this.actorValue = actorValue;
        }
    }

    public synchronized void logAction(Action action) {
        actionLog.add(action);
    }

    /**
     * Reads a joystick profile file into an action array for playback.
     * @param fileName the file from which to load the profile
     * @return the profile, represented as an {@code Action[]}
     */
    public synchronized ArrayList<Action> deserializeActionsFromFile(String fileName) {
        ArrayList<Action> readActions = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(line -> {
                String[] components = line.split(":");
                ActorType type = ActorType.get(Integer.parseInt(components[0]));
                int id = Integer.parseInt(components[1]);
                double value = Double.parseDouble(components[2]);
                readActions.add(new Action(type, id, value));
            });
        } catch (Exception exc) {
            DriverStation.reportError(exc.getMessage(), true);
        }
        return readActions;
    }

    /**
     * Writes a list of the recorded actions to a file. Files should be stored in the resources
     * folder so that they will be deployed by the Gradle script.
     * @param fileName the name of the file to which to write.
     */
    public synchronized void writeActionsToFile(String fileName) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
            for (Action action: actionLog) {
                String actionLine = action.actorType.value + ":" + action.actorID + ":"
                        + action.actorValue + "\n";
                writer.write(actionLine);
            }
            writer.close();
        } catch (Exception exc) {
            DriverStation.reportError(exc.getMessage(), true);
        }
    }

}
