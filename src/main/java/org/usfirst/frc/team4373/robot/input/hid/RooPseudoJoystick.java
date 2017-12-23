package org.usfirst.frc.team4373.robot.input.hid;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A fake joystick for playing back joystick captures.
 *
 * @author aaplmath
 */
public class RooPseudoJoystick implements RooHIDDevice {

    private HashMap<Integer, ArrayDeque<Double>> axes;
    private HashMap<Integer, ArrayDeque<Boolean>> buttons;
    private ArrayDeque<Integer> pov;

    /**
     * Initializes a pseudo-joystick for HIDLogger "macro" playback.
     * Note that construction is computationally intensive in order to minimize strain
     * during execution.
     * @param actions the array of HIDLogger actions to play back.
     */
    public RooPseudoJoystick(ArrayList<HIDLogger.Action> actions) {
        for (HIDLogger.Action action: actions) {
            switch (action.actorType) {
                case Axis:
                    ArrayDeque<Double> axis = axes.get(action.actorID);
                    if (axis == null) {
                        axis = new ArrayDeque<>();
                        axis.add(action.actorValue);
                        axes.put(action.actorID, axis);
                    } else {
                        axis.add(action.actorValue);
                    }
                    break;
                case Button:
                    ArrayDeque<Boolean> button = buttons.get(action.actorID);
                    // Coerce 1 -> true and 0 -> false
                    boolean value = action.actorValue == 1;
                    if (button == null) {
                        button = new ArrayDeque<>();
                        button.add(value);
                        buttons.put(action.actorID, button);
                    } else {
                        button.add(value);
                    }
                    break;
                case POV:
                    pov.add(action.actorID);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public double getAxis(int axis) {
        return axes.get(axis).removeLast();
    }

    @Override
    public int getPOV() {
        return pov.removeLast();
    }

    @Override
    public boolean getRawButton(int button) {
        return buttons.get(button).removeLast();
    }
}
