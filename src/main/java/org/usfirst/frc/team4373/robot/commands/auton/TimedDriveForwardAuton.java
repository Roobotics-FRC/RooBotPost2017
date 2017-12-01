package org.usfirst.frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4373.robot.subsystems.DriveTrain;

public class TimedDriveForwardAuton extends Command {

    private DriveTrain driveTrain;
    private long startTime;
    private double duration;
    private double speed;

    /**
     * Construct a new TimedDriveForwardAuton command.
     * @param duration The amount of time, in seconds, for which the robot should drive.
     * @param speed The speed, between 0 and 1, at which the robot should drive.
     */
    public TimedDriveForwardAuton(double duration, double speed) {
        requires(this.driveTrain = DriveTrain.getDriveTrain());
        this.duration = duration * 1000d;
        if (speed > 1) {
            this.speed = 1;
        } else if (speed < 0) {
            this.speed = 0;
        } else {
            this.speed = speed;
        }
    }

    @Override
    protected void initialize() {
        this.startTime = System.currentTimeMillis();
    }

    @Override
    protected void execute() {
        this.driveTrain.setBoth(this.speed);
    }

    @Override
    protected boolean isFinished() {
        return System.currentTimeMillis() >= startTime + this.duration;
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
