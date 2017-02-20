package org.usfirst.frc.team4373.robot;

/**
 * RobotMap holds various constants.
 * @author Henry Pitcairn
 */
public class RobotMap {
    // Joystick axes and buttons
    public static final int JOYSTICK_HORIZONTAL_AXIS = 0;
    public static final int JOYSTICK_FORWARD_AXIS = 1;
    public static final int JOYSTICK_TWIST_AXIS = 2;
    public static final int GEAR_INTAKE_UP_BUTTON = 9;

    // Sensor ports
    public static final int JOYSTICK_PORT = 0;
    public static final int GYRO_CHANNEL = 0;

    // Motor ports
    public static final int LEFT_DRIVE_MOTOR_1 = 4;
    public static final int LEFT_DRIVE_MOTOR_2 = 3;
    public static final int RIGHT_DRIVE_MOTOR_1 = 8;
    public static final int RIGHT_DRIVE_MOTOR_2 = 5;
    public static final int MIDDLE_DRIVE_MOTOR_1 = 6;
    public static final int MIDDLE_DRIVE_MOTOR_2 = 7;
    public static final int INTAKE_PORT = 1;

    // Pneumatics
    public static final int PCM_PORT = 15;
    public static final int FORWARD_SOLENOID_PORT = 1;
    public static final int BACKWARD_SOLENOID_PORT = 0;
}
