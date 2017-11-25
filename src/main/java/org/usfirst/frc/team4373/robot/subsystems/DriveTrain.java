package org.usfirst.frc.team4373.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.CanTalonJNI;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team4373.robot.RobotMap;
import org.usfirst.frc.team4373.robot.commands.teleop.DriveWithJoystick;

/**
 * Programmatic representation of physical drive train components.
 *
 * @author aaplmath
 * @author Henry Pitcairn
 */
public class DriveTrain extends Subsystem {
    private static DriveTrain driveTrain = null;
    private CANTalon left1;
    private CANTalon left2;
    private CANTalon right1;
    private CANTalon right2;
    private CANTalon middle1;
    private CANTalon middle2;
    private CANTalon leftEncoderTalon;
    private CANTalon rightEncoderTalon;
    private CANTalon middleEncoderTalon;
    private boolean hasLeftEncoder;
    private boolean hasRightEncoder;
    private boolean hasMiddleEncoder;

    /**
     * Initializes motors on respective ports, sets break and reverse modes, and sets followers.
     */
    private DriveTrain() {
        super("DriveTrain");
        hasLeftEncoder = false;
        hasRightEncoder = false;
        hasMiddleEncoder = false;
        this.leftEncoderTalon = null;
        this.rightEncoderTalon = null;
        this.middleEncoderTalon = null;
        this.left1 = new CANTalon(RobotMap.LEFT_DRIVE_MOTOR_1);
        this.left2 = new CANTalon(RobotMap.LEFT_DRIVE_MOTOR_2);
        this.right1 = new CANTalon(RobotMap.RIGHT_DRIVE_MOTOR_1);
        this.right2 = new CANTalon(RobotMap.RIGHT_DRIVE_MOTOR_2);
        this.middle1 = new CANTalon(RobotMap.MIDDLE_DRIVE_MOTOR_1);
        this.middle2 = new CANTalon(RobotMap.MIDDLE_DRIVE_MOTOR_2);

        if (talonHasEncoder(left1)) {
            this.leftEncoderTalon = left1;
            this.hasLeftEncoder = true;
            talonSetEncoder(left1);
        } else if (talonHasEncoder(left2)) {
            this.leftEncoderTalon = left2;
            this.hasLeftEncoder = true;
            talonSetEncoder(left2);
        }
        if (talonHasEncoder(right1)) {
            this.rightEncoderTalon = right1;
            this.hasRightEncoder = true;
            talonSetEncoder(right1);
        } else if (talonHasEncoder(right2)) {
            this.rightEncoderTalon = right2;
            this.hasRightEncoder = true;
            talonSetEncoder(right2);
        }
        if (talonHasEncoder(middle1)) {
            this.middleEncoderTalon = middle1;
            this.hasMiddleEncoder = true;
            talonSetEncoder(middle1);
        } else if (talonHasEncoder(middle2)) {
            this.middleEncoderTalon = middle2;
            this.hasMiddleEncoder = true;
            talonSetEncoder(middle2);
        }

        this.right1.enableBrakeMode(true);
        this.right2.enableBrakeMode(true);
        this.left1.enableBrakeMode(true);
        this.left2.enableBrakeMode(true);
        this.middle1.enableBrakeMode(true);
        this.middle2.enableBrakeMode(true);

        this.right2.changeControlMode(CANTalon.TalonControlMode.Follower);
        this.right2.set(RobotMap.RIGHT_DRIVE_MOTOR_1);
        this.left2.changeControlMode(CANTalon.TalonControlMode.Follower);
        this.left2.set(RobotMap.LEFT_DRIVE_MOTOR_1);
        this.middle2.changeControlMode(CANTalon.TalonControlMode.Follower);
        this.middle2.set(RobotMap.MIDDLE_DRIVE_MOTOR_1);

        System.out.println();
    }

    /**
     * Has encoder?.
     */
    private static boolean talonHasEncoder(CANTalon talon) {
        return talon.isSensorPresent(CANTalon.FeedbackDevice.CtreMagEncoder_Absolute)
                == CANTalon.FeedbackDeviceStatus.FeedbackStatusPresent;
    }

    /**
     * set encoder.
     */
    private static void talonSetEncoder(CANTalon talon) {
        talon.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Absolute);
    }

    public static DriveTrain getDriveTrain() {
        driveTrain = driveTrain == null ? new DriveTrain() : driveTrain;
        return driveTrain;
    }

    /**
     * Returns the position (number of revolutions away from 0) of the left motors.
     *
     * @return The number of rotations (positive or negative) of the left motors.
     */
    public int getLeftEncoderPosition() {
        return this.hasLeftEncoder
                ? this.leftEncoderTalon.getEncPosition() : 0;
    }

    /**
     * Returns the position (number of revolutions away from 0) of the right motors.
     *
     * @return The number of rotations (positive or negative) of the right motors.
     */
    public int getRightEncoderPosition() {
        return this.hasRightEncoder
                ? this.rightEncoderTalon.getEncPosition() : 0;
    }

    /**
     * Returns the position (number of revolutions away from 0) of the middle motors.
     *
     * @return The number of rotations (positive or negative) of the middle motors.
     */
    public int getMiddleEncoderPosition() {
        return this.hasMiddleEncoder
                ? this.middleEncoderTalon.getEncPosition() : 0;
    }

    /**
     * Gets the number of encoder counts per revolution of the left motors.
     *
     * @return the number of encoder counts per revolution of the left motors.
     */
    public double getLeftEncoderCPR() {
        return this.hasLeftEncoder
                ? this.leftEncoderTalon
                        .getParameter(CanTalonJNI.param_t.eNumberEncoderCPR) : 0;
    }

    /**
     * Gets the number of encoder counts per revolution of the right motors.
     *
     * @return the number of encoder counts per revolution of the right motors.
     */
    public double getRightEncoderCPR() {
        return this.hasRightEncoder
                ? this.rightEncoderTalon
                        .getParameter(CanTalonJNI.param_t.eNumberEncoderCPR) : 0;
    }

    /**
     * Gets the encoder velocity for the right motors.
     * @return encoder velocity
     */
    public double getRightEncoderVelocity() {
        return this.hasRightEncoder
                ? this.rightEncoderTalon
                        .getParameter(CanTalonJNI.param_t.eEncVel) : 0;
    }

    /**
     * Gets the encoder velocity for left motors.
     * @return encoder velocity
     */
    public double getLeftEncoderVelocity() {
        return this.hasLeftEncoder
                ? this.leftEncoderTalon
                        .getParameter(CanTalonJNI.param_t.eEncVel) : 0;
    }

    /**
     * Gets the encoder velocity for middle motors.
     * @return encoder velocity
     */
    public double getMiddleEncoderVelocity() {
        return this.hasMiddleEncoder
                ? this.middleEncoderTalon
                        .getParameter(CanTalonJNI.param_t.eEncVel) : 0;
    }

    /**
     * Gets the number of encoder counts per revolution of the middle motors.
     *
     * @return the number of encoder counts per revolution of the middle motors.
     */
    public double getMiddleEncoderCPR() {
        return this.hasMiddleEncoder
                ? this.middleEncoderTalon
                        .getParameter(CanTalonJNI.param_t.eNumberEncoderCPR) : 0;
    }

    /**
     * Gets the number of times the left wheels have rotated.
     *
     * @return the number of times the left wheels have rotated.
     */
    public double getLeftEncoderTurns() {
        return getLeftEncoderPosition() / getLeftEncoderCPR();
    }

    /**
     * Gets the number of times the right wheels have rotated.
     *
     * @return the number of times the right wheels have rotated.
     */
    public double getRightEncoderTurns() {
        return getRightEncoderPosition() / getRightEncoderCPR();
    }

    /**
     * Gets the number of times the middle wheels have rotated.
     *
     * @return the number of times the middle wheels have rotated.
     */
    public double getMiddleEncoderTurns() {
        return getMiddleEncoderPosition() / getMiddleEncoderCPR();
    }

    /**
     * Prints encoder data to stdout
     */
    public void printEncoderData() {
        //    System.out.println("------------------------");
        //    System.out.println("L Encoder Position:" + this.getLeftEncoderPosition());
        //    System.out.println("R Encoder Position:" + this.getRightEncoderPosition());
        //    System.out.println("L Encoder CPR:" + this.getLeftEncoderCPR());
        //    System.out.println("R Encoder CPR:" + this.getRightEncoderCPR());
        //    System.out.println("L Encoder Velocity:" + this.getLeftEncoderVelocity());
        //    System.out.println("R Enocder Velocity:" + this.getRightEncoderVelocity());
        //    System.out.println("------------------------");
        //    printTalonData();
    }

    /**
     * Prints talon data to stdout
     */
    public void printTalonData() {
        System.out.println("-------------------");
        System.out.println("L Analog Velocity:" + this.left1.getAnalogInVelocity());
        System.out.println("R Analog Velocity:" + this.right1.getAnalogInVelocity());
        System.out.println("L Analog Position:" + this.left1.getAnalogInPosition());
        System.out.println("R Analog Position:" + this.right1.getAnalogInPosition());
    }

    /**
     * Sets power to the left motors.
     *
     * @param power The power to allocate to the left motors from -1 to 1.
     */
    public void setLeft(double power) {
        this.left1.set(power);
    }

    /**
     * Sets power to the right motors.
     * Note that the right motors are facing backwards, so power is negated.
     *
     * @param power The power to allocate to the right motors from -1 to 1.
     */
    public void setRight(double power) {
        this.right1.set(-power);
    }

    /**
     * Sets power to the middle motors.
     *
     * @param power The power to allocate to the middle motor from -1 (left) to 1 (right).
     */
    public void setMiddle(double power) {
        this.middle1.set(power);
    }

    /**
     * Sets power to both motors simultaneously.
     *
     * @param power The power to allocate to both motors from -1 to 1.
     */
    public void setBoth(double power) {
        setLeft(power);
        setRight(power);
    }

    @Override
    protected void initDefaultCommand() {
        // setDefaultCommand(TurnToPosition.getTurnToPosition());
        setDefaultCommand(DriveWithJoystick.getDriveWithJoystick());
    }

}
