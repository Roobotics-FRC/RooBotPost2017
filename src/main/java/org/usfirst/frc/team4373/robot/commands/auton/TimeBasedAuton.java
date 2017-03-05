package org.usfirst.frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4373.robot.subsystems.DriveTrain;

/**
 * Drives forward at a specified speed for a specified amount of time.
 * @author Alex Fang
 * @author aaplmath
 */
public class TimeBasedAuton extends Command {


    private final int TO_MILLISECONDS = 1000;
    private DriveTrain driveTrain;
    private int timeSeconds;
    private long desiredDurationMillis;
    private boolean isFinished = false;
    private double motorValue;

    private long timeStart;

    private static TimeBasedAuton timeBasedAuton = null;

    public static TimeBasedAuton getTimeBasedAuton(int time, double motorValue) {
        if (timeBasedAuton == null) {
            timeBasedAuton = new TimeBasedAuton(time, motorValue);
        } else {
            timeBasedAuton = timeBasedAuton;
        }
        return timeBasedAuton;
    }

    private TimeBasedAuton(int time, double motorValue) {
        super();
        requires(driveTrain = DriveTrain.getDriveTrain());
        this.timeSeconds = time;
        this.desiredDurationMillis = this.timeSeconds * this.TO_MILLISECONDS;
        this.motorValue = motorValue;
    }

    @Override
    protected void initialize() {
        timeStart = 0;
    }

    @Override
    protected void execute() {
        if (timeStart == 0) timeStart = System.currentTimeMillis();
        SmartDashboard.putNumber("Time remaining", System.currentTimeMillis() - timeStart);
        if (System.currentTimeMillis() - timeStart <= desiredDurationMillis) {
            driveTrain.setBoth(this.motorValue);
        } else {
            driveTrain.setBoth(0.0d);
            isFinished = true;
        }
    }

    @Override
    protected boolean isFinished() {
        return this.isFinished;
    }

    @Override
    protected void end() {
        timeStart = 0;
        driveTrain.setBoth(0.0d);
    }

    @Override
    protected void interrupted() {
        // shouldn't be
        timeStart = 0;
        driveTrain.setBoth(0d);
    }
}
