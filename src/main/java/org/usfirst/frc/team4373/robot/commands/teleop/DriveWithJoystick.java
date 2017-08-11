package org.usfirst.frc.team4373.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4373.robot.OI;
import org.usfirst.frc.team4373.robot.RobotMap;
import org.usfirst.frc.team4373.robot.input.hid.RooJoystick;
import org.usfirst.frc.team4373.robot.subsystems.DriveTrain;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This command handles operator control of the driveWithJoystick train subsystem.
 * It sets outputs based on joystick axes.
 * @author Henry Pitcairn
 * @author aaplmath
 */
public class DriveWithJoystick extends Command {

    public enum Direction {
        FORWARD, BACKWARD, RIGHT, LEFT
    }

    private Direction forwardDirection;

    private DriveTrain driveTrain;
    private RooJoystick joystick;

    private static DriveWithJoystick driveWithJoystick = null;

    /**
     * Gets the singleton instance of DriveWithJoystick.
     */
    public static DriveWithJoystick getDriveWithJoystick() {
        if (driveWithJoystick == null) {
            driveWithJoystick = new DriveWithJoystick();
        }

        return driveWithJoystick;
    }

    private DriveWithJoystick() {
        super("DriveWithJoystick");
        requires(DriveTrain.getDriveTrain());
        driveTrain = DriveTrain.getDriveTrain();
        joystick = OI.getOI().getDriveJoystick();
        forwardDirection = Direction.FORWARD;
        setInterruptible(true);
    }

    @Override
    protected void execute() {
        SmartDashboard.putNumber("Gyro", Math.round(OI.getOI().getGyro().getAngle() * 100d) / 100d);
        if (OI.getOI().getDriveJoystick().getRawButton(1)) {
            switch (OI.getOI().getDriveJoystick().getPOV()) {
                case 0:
                    forwardDirection = Direction.FORWARD;
                    break;
                case 90:
                    forwardDirection = Direction.RIGHT;
                    break;
                case 180:
                    forwardDirection = Direction.BACKWARD;
                    break;
                case 270:
                    forwardDirection = Direction.LEFT;
                    break;
                default:
                    break;
            }
        }

        // Turn more slowly
        double twistAxis = this.joystick.getAxis(RobotMap.DRIVE_JOYSTICK_TWIST_AXIS) / 2;
        double horizontalAxis = this.joystick.getAxis(RobotMap.DRIVE_JOYSTICK_HORIZONTAL_AXIS);
        double forwardAxis = -this.joystick.getAxis(RobotMap.DRIVE_JOYSTICK_FORWARD_AXIS);

        double temp = forwardAxis;
        switch (forwardDirection) {
            case BACKWARD:
                forwardAxis = -forwardAxis;
                horizontalAxis = -horizontalAxis;
                break;
            case LEFT:
                forwardAxis = horizontalAxis;
                horizontalAxis = -temp;
                break;
            case RIGHT:
                forwardAxis = -horizontalAxis;
                horizontalAxis = temp;
                break;
            default:
                break;
        }

        if (twistAxis == 0 && forwardAxis != 0) { // Just forward
            double right = forwardAxis;
            double left = forwardAxis;
            driveTrain.setLeft(left);
            driveTrain.setRight(right);
        } else if (twistAxis != 0 && forwardAxis != 0) { // Twist and forward
            // OI.getOI().getGyro().reset();
            double right = forwardAxis;
            double left = forwardAxis;
            if (twistAxis > 0) {
                right -= twistAxis;
            } else if (twistAxis < 0) {
                left -= Math.abs(twistAxis);
            }
            driveTrain.setRight(right);
            driveTrain.setLeft(left);
        } else if (twistAxis != 0 && forwardAxis == 0) { // Just twist
            // OI.getOI().getGyro().reset();
            driveTrain.setRight(-twistAxis);
            driveTrain.setLeft(twistAxis);
        } else {
            driveTrain.setBoth(0);
        }
        driveTrain.setMiddle(horizontalAxis);
    }

    @Override
    protected void initialize() {
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        this.driveTrain.setBoth(0);
    }

    @Override
    protected void interrupted() {
        this.driveTrain.setBoth(0);
    }
}
