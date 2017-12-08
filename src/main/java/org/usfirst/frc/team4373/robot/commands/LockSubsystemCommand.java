package org.usfirst.frc.team4373.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Created by derros on 12/8/17.
 */
public class LockSubsystemCommand extends Command {

    private Subsystem lockedSubsystem;

    public LockSubsystemCommand(Subsystem subsystem) {
        this.lockedSubsystem = subsystem;
    }

    @Override
    protected void initialize() {
        requires(this.lockedSubsystem);
        setInterruptible(true);
    }

    @Override
    protected void execute() {
        // do nothing
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {
        // do nothing
    }

    @Override
    protected void interrupted() {
        // do nothing
    }
}
