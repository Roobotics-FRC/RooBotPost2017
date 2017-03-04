package org.usfirst.frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4373.robot.subsystems.DriveTrain;

/**
 * Created by derros on 3/4/17.
 *
 * @author Alex Fang
 */
public class TimeBasedAuton extends Command {


    private final int TO_NANOSECONDS = 1000000000;
    private DriveTrain driveTrain;
    private int timeSeconds;
    private long timeNanoseconds;
    private boolean isFinished = false;

    private long timeStart;

    public TimeBasedAuton(int time) {
        super();
        requires(driveTrain = DriveTrain.getDriveTrain());
        this.timeSeconds = time;
        this.timeNanoseconds = this.timeSeconds * this.TO_NANOSECONDS;
    }

    @Override
    protected void initialize() {
        timeStart = System.nanoTime();
        SmartDashboard.putBoolean("\tOverride Auton Default?", false);
        SmartDashboard.putNumber("\tOverriden Auton Value:", 0);
    }

    @Override
    protected void execute() {
        if (System.nanoTime() - timeStart <= timeNanoseconds) {
            driveTrain.setBoth(0.5d);
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
        driveTrain.setBoth(0d);
    }

    @Override
    protected void interrupted() {
        // shouldn't be
        driveTrain.setBoth(0d);
    }
}
