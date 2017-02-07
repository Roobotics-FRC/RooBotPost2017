package org.usfirst.frc.team4373.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.PIDCommand;
import org.usfirst.frc.team4373.robot.OI;
import org.usfirst.frc.team4373.robot.RobotMap;
import org.usfirst.frc.team4373.robot.input.hid.RooJoystick;
import org.usfirst.frc.team4373.robot.subsystems.DriveTrain;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This command handles operator control of the drive train subsystem.
 * It sets outputs based on joystick axes.
 * @author Henry Pitcairn
 */
public class DriveWithJoystick extends PIDCommand {
    private static double kP = 0.01;
    private static double kI = 0.000;
    private static double kD = 0.01;

    private DriveTrain driveTrain;
    private RooJoystick joystick;
    private double pidOutput;
    // Whether robot is cooling down from a turn and should not immediately readjust
    private final AtomicBoolean cooldown;
    private long cooldownEndTime;

    /**
     * Constructor for DriveWithJoystick.
     */
    public DriveWithJoystick() {
        super("DriveWithJoystick", kP, kI, kD);
        requires(DriveTrain.getDriveTrain());
        driveTrain = DriveTrain.getDriveTrain();
        joystick = OI.getOI().getDriveJoystick();
        cooldown = new AtomicBoolean(false);
    }

    @Override
    protected void execute() {
        if (System.currentTimeMillis() >= cooldownEndTime) {
            cooldown.set(false);
        } else {
            OI.getOI().getGyro().reset();
        }
        // Turn more slowly
        double twistAxis = this.joystick.getAxis(RobotMap.JOYSTICK_TWIST_AXIS) / 2;
        double horizontalAxis = this.joystick.getAxis(RobotMap.JOYSTICK_HORIZONTAL_AXIS);
        double forwardAxis = -this.joystick.getAxis(RobotMap.JOYSTICK_FORWARD_AXIS);
        if (twistAxis == 0 && forwardAxis != 0) { // Just forward
            double right = forwardAxis;
            double left = forwardAxis;
            if (pidOutput > 0) {
                right -= pidOutput;
            } else {
                left -= Math.abs(pidOutput);
            }
            driveTrain.setLeft(left);
            driveTrain.setRight(right);
        } else if (twistAxis != 0 && forwardAxis != 0) { // Twist and forward
            OI.getOI().getGyro().reset();
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
            OI.getOI().getGyro().reset();
            driveTrain.setRight(-twistAxis);
            driveTrain.setLeft(twistAxis);
            cooldown.set(true);
            cooldownEndTime = System.currentTimeMillis() + 500;

        } else { // Hold straight
            driveTrain.setRight(-pidOutput);
            driveTrain.setLeft(pidOutput);
        }
        driveTrain.setMiddle(horizontalAxis);
    }

    @Override
    protected void initialize() {
        this.setSetpoint(0);
        this.setInputRange(-180, 180);
        cooldown.set(false);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        this.driveTrain.setBoth(0);
        cooldown.set(false);
    }

    @Override
    protected void interrupted() {
        this.driveTrain.setBoth(0);
        cooldown.set(false);
    }

    @Override
    protected double returnPIDInput() {
        return OI.getOI().getAngleRelative();
    }

    @Override
    protected void usePIDOutput(double pidOutput) {
        if (cooldown.get()) {
            this.pidOutput = 0;
        } else {
            this.pidOutput = pidOutput;
        }
    }
}
