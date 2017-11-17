package org.usfirst.frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4373.robot.subsystems.DriveTrain;

public class DriveForwardAuton extends Command {

    private DriveTrain driveTrain;
    private long startTime;

    public DriveForwardAuton() {
        requires(DriveTrain.getDriveTrain());
        this.driveTrain = DriveTrain.getDriveTrain();
    }

    @Override
    protected void initialize() {
        this.startTime = System.currentTimeMillis();
    }

    @Override
    protected void execute() {
        this.driveTrain.setBoth(0.3);
    }

    @Override
    protected boolean isFinished() {
        if (System.currentTimeMillis() - 5000 >= startTime) {
            return true;
        } else {
            return false;
        }
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
