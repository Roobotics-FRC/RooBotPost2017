package org.usfirst.frc.team4373.robot.input.hid;

import edu.wpi.first.wpilibj.DriverStation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
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
    private ConcurrentHashMap<Integer, ArrayDeque<Double>> axes;
    private ConcurrentHashMap<Integer, ArrayDeque<Boolean>> buttons;
    private ArrayDeque<Integer> pov;
    private ArrayDeque<Action> actionLog;

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
        this.actionLog = new ArrayDeque<>();
        this.axes = new ConcurrentHashMap<>();
        this.buttons = new ConcurrentHashMap<>();
        this.pov = new ArrayDeque<>();
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

    // TODO: Switch to proper logging
    public void logAction(ActorType type, int id, double value) {
        switch (type) {
            case Axis:
                ArrayDeque<Double> axis = axes.get(id);
                if (axis == null) {
                    axis = new ArrayDeque<>();
                    axis.add(value);
                    axes.put(id, axis);
                } else {
                    axis.add(value);
                }
                break;
            case Button:
                ArrayDeque<Boolean> button = buttons.get(id);
                // Coerce 1 -> true and 0 -> false
                boolean logVal = value == 1;
                if (button == null) {
                    button = new ArrayDeque<>();
                    button.add(logVal);
                    buttons.put(id, button);
                } else {
                    button.add(logVal);
                }
                break;
            case POV:
                pov.add(id);
                break;
            default:
                break;
        }
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
     * Writes a list of the recorded actions to a file.
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
