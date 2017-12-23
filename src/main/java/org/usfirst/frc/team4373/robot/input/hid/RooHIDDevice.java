package org.usfirst.frc.team4373.robot.input.hid;

public interface RooHIDDevice {
    double getAxis(int axis);

    int getPOV();

    boolean getRawButton(int button);
}
