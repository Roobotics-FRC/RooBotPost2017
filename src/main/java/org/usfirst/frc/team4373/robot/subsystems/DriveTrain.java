package org.usfirst.frc.team4373.robot.subsystems;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team4373.robot.RobotMap;
import org.usfirst.frc.team4373.robot.commands.teleop.DriveWithJoystick;
import org.usfirst.frc.team4373.robot.commands.teleop.DriveWithJoystick.Direction;

/**
 * Programmatic representation of physical drive train components.
 * @author aaplmath
 * @author Henry Pitcairn
 */
public class DriveTrain extends Subsystem {
    private CANTalon left1;
    private CANTalon left2;
    private CANTalon right1;
    private CANTalon right2;
    private CANTalon middle1;
    private CANTalon middle2;

    private static DriveTrain driveTrain = null;

    public static DriveTrain getDriveTrain() {
        driveTrain = driveTrain == null ? new DriveTrain() : driveTrain;
        return driveTrain;
    }

    /**
     * Initializes motors on respective ports, sets break and reverse modes, and sets followers.
    */
    private DriveTrain() {
        super("DriveTrain");
        this.left1 = new CANTalon(RobotMap.LEFT_DRIVE_MOTOR_1);
        this.left2 = new CANTalon(RobotMap.LEFT_DRIVE_MOTOR_2);
        this.right1 = new CANTalon(RobotMap.RIGHT_DRIVE_MOTOR_1);
        this.right2 = new CANTalon(RobotMap.RIGHT_DRIVE_MOTOR_2);
        this.middle1 = new CANTalon(RobotMap.MIDDLE_DRIVE_MOTOR_1);
        this.middle2 = new CANTalon(RobotMap.MIDDLE_DRIVE_MOTOR_2);

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
    }

    /**
     * Sets power to the left motors.
     * @param power The power to allocate to the left motors from -1 to 1.
     */
    public void setLeft(double power) {
        this.left1.set(power);
    }

    /**
     * Sets power to the right motors.
     * Note that the right motors are facing backwards, so power is negated.
     * @param power The power to allocate to the right motors from -1 to 1.
     */
    public void setRight(double power) {
        this.right1.set(-power);
    }

    /**
     * Sets power to the middle motors.
     * @param power The power to allocate to the middle motor from -1 (left) to 1 (right).
     */
    public void setMiddle(double power) {
        this.middle1.set(power);
    }

    /**
     * "Bumps" the robot in the target direction.
     * @param direction The direction in which to bump the robot.
     */
    public void bumpToDirection(Direction direction) {
        CANTalon.TalonControlMode origControlMode = this.right1.getControlMode();
        this.right1.changeControlMode(CANTalon.TalonControlMode.Position);
        this.left1.changeControlMode(CANTalon.TalonControlMode.Position);
        this.middle1.changeControlMode(CANTalon.TalonControlMode.Position);
        switch (direction) {
            case FORWARD:
                this.right1.set(0.5d);
                this.left1.set(0.5d);
                break;
            case BACKWARD:
                this.right1.set(-0.5d);
                this.left1.set(-0.5d);
                break;
            case RIGHT:
                this.middle1.set(0.5d);
                break;
            case LEFT:
                this.middle1.set(-0.5d);
                break;
            default:
                break;
        }
        this.right1.changeControlMode(origControlMode);
        this.left1.changeControlMode(origControlMode);
        this.middle1.changeControlMode(origControlMode);
    }

    /**
     * Sets power to both motors simultaneously.
     * @param power The power to allocate to both motors from -1 to 1.
     */
    public void setBoth(double power) {
        setLeft(power);
        setRight(power);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(DriveWithJoystick.getDriveWithJoystick());
    }

}
