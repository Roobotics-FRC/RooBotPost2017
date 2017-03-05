package org.usfirst.frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4373.robot.subsystems.DriveTrain;
import org.usfirst.frc.team4373.robot.subsystems.GearRelease;

/**
 * Autonomously deposits a gear using hard-coded time values
 * @author Henry Pitcairn
 * @author aaplmath
 */
public class TimeBasedGearAuton extends Command {
    private final int TO_MILLISECONDS = 1000;
    private DriveTrain driveTrain;
    private GearRelease gearRelease;
    private int timeSeconds;
    private long moveForwardDuration, releaseDuration, moveBackwardDuration;
    private boolean isFinished = false;
    private double motorValue;
    private long timeStart;
    private State state;

    enum State {
        WAITING,
        MOVING_TOWARD_PEG,
        RELEASING,
        MOVING_AWAY_FROM_PEG
    }

    private static TimeBasedGearAuton timeBasedGearAuton = null;
    
    public static TimeBasedGearAuton getTimeBasedGearAuton(int time, double motorValue) {
        timeBasedGearAuton = timeBasedGearAuton == null ? new TimeBasedGearAuton(time, motorValue) : timeBasedGearAuton;
        return timeBasedGearAuton;
    }
    
    public TimeBasedGearAuton(int time, double motorValue) {
        super();
        requires(driveTrain = DriveTrain.getDriveTrain());
        requires(gearRelease = GearRelease.getGearRelease());
        this.timeSeconds = time;
        this.moveForwardDuration = this.timeSeconds * this.TO_MILLISECONDS;
        this.releaseDuration = 2000;
        this.moveBackwardDuration = 1000;
        this.motorValue = motorValue;
        this.state = State.WAITING;
    }

    @Override
    protected void initialize() {
        timeStart = 0;
        state = State.WAITING;
    }

    @Override
    protected void execute() {
        SmartDashboard.putNumber("Time remaining", System.currentTimeMillis() - timeStart);
        switch(state) {
            case WAITING:
                state = State.MOVING_TOWARD_PEG;
                break;
            case MOVING_TOWARD_PEG:
                if (timeStart == 0) timeStart = System.currentTimeMillis();
                if (System.currentTimeMillis() - timeStart <= moveForwardDuration) {
                    driveTrain.setBoth(-this.motorValue);
                } else {
                    driveTrain.setBoth(0.0d);
                    timeStart = System.currentTimeMillis();
                    state = State.RELEASING;
                }
                break;
            case RELEASING:
                if (System.currentTimeMillis() - timeStart <= releaseDuration) {
                    gearRelease.activate();
                } else {
                    timeStart = System.currentTimeMillis();
                    state = State.MOVING_AWAY_FROM_PEG;
                }
                break;
            case MOVING_AWAY_FROM_PEG:
                if (System.currentTimeMillis() - timeStart <= moveBackwardDuration) {
                    driveTrain.setBoth(0.25);
                } else {
                    driveTrain.setBoth(0.0d);
                    gearRelease.deactivate();
                    timeStart = 0;
                    state = State.WAITING;
                    isFinished = true;  
                }
                break;
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
        state = State.WAITING;
    }

    @Override
    protected void interrupted() {
        // shouldn't be
        timeStart = 0;
        driveTrain.setBoth(0d);
        state = State.WAITING;
    }
}
