package org.usfirst.frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4373.robot.subsystems.DriveTrain;

/**
 * Created by derros on 3/4/17.
 *
 * @author Alex Fang
 */
public class TimeBasedAuton extends Command {


    private final int TO_SECONDS = 1000000000;
    private DriveTrain driveTrain;
    private int timeSeconds;
    private long timeNanoseconds;
    private boolean isFinished = false;

    public TimeBasedAuton(int time) {
        super();
        requires(driveTrain = DriveTrain.getDriveTrain());
        this.timeSeconds = time;
        this.timeNanoseconds = this.timeSeconds * this.TO_SECONDS;
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        long timeStart = System.nanoTime();
        long timeStop = 0;
        do {
            driveTrain.setBoth(0.5);
            timeStop = System.nanoTime();
        } while ((timeStop - timeStart) <= timeNanoseconds);
        isFinished = true;
    }

    @Override
    protected boolean isFinished() {
        return this.isFinished;
    }

    @Override
    protected void end() {

    }

    @Override
    protected void interrupted() {
        // shouldn't be
    }
}
