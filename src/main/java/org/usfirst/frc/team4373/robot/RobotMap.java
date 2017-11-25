package org.usfirst.frc.team4373.robot;

/**
 * RobotMap holds various constants.
 * @author Henry Pitcairn
 */
public class RobotMap {
    // Joystick axes and buttons
    public static final int DRIVE_JOYSTICK_HORIZONTAL_AXIS = 0;
    public static final int DRIVE_JOYSTICK_FORWARD_AXIS = 1;
    public static final int DRIVE_JOYSTICK_TWIST_AXIS = 2;
    public static final int DRIVE_JOYSTICK_SHOOTER_AXIS = 3;
    public static final int OPERATOR_JOYSTICK_CLIMBER_BUTTON = 4;
    public static final int OPERATOR_JOYSTICK_GEAR_INTAKE_UP_BUTTON = 3;
    public static final int OPERATOR_JOYSTICK_SHOOTER_BUTTON = 2;
    public static final int OPERATOR_JOYSTICK_DISPENSER_BUTTON = 3;

    // Sensor ports
    public static final int DRIVE_JOYSTICK_PORT = 0;
    public static final int OPERATOR_JOYSTICK_PORT = 1;
    public static final int GYRO_CHANNEL = 0;

    // Motor ports
    public static final int LEFT_DRIVE_MOTOR_1 = 3;
    public static final int LEFT_DRIVE_MOTOR_2 = 5;
    public static final int RIGHT_DRIVE_MOTOR_1 = 4;
    public static final int RIGHT_DRIVE_MOTOR_2 = 8;
    public static final int MIDDLE_DRIVE_MOTOR_1 = 6;
    public static final int MIDDLE_DRIVE_MOTOR_2 = 7;
    public static final int CLIMBER_MOTOR_1 = 9;
    public static final int CLIMBER_MOTOR_2 = 10;
    public static final int SHOOTER_MOTOR = 2;

    // Pneumatics
    public static final int PCM_PORT = 15;
    public static final int FORWARD_SOLENOID_PORT = 1;
    public static final int BACKWARD_SOLENOID_PORT = 0;

    // misc
    public static final int TIME_BASED_AUTON_DEFAULT_SECONDS = 4;
    public static final double TIME_BASED_AUTON_MOTOR_VALUE = 0.5;
}
