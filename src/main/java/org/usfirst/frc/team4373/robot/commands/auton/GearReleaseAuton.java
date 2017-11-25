package org.usfirst.frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4373.robot.subsystems.GearRelease;

public class GearReleaseAuton extends Command {

    private GearRelease gearRelease;
    private long startTime;

    public GearReleaseAuton() {
        requires(GearRelease.getGearRelease());
        this.gearRelease = GearRelease.getGearRelease();
    }

    @Override
    protected void initialize() {
        this.gearRelease.startCompressor();
        this.startTime = System.currentTimeMillis();
    }

    @Override
    protected void execute() {
        this.gearRelease.releaseGear();
        if (System.currentTimeMillis() - 2000 >= startTime) {
            this.gearRelease.retainGear();
        }
        if (System.currentTimeMillis() - 4000 >= startTime) {
            this.gearRelease.releaseGear();
        }
    }

    @Override
    protected boolean isFinished() {
        return System.currentTimeMillis() - 5000 >= startTime;
    }

    @Override
    protected void end() {
        this.gearRelease.setNeutral();
        this.gearRelease.stopCompressor();
    }

    @Override
    protected void interrupted() {
        this.gearRelease.setNeutral();
        this.gearRelease.stopCompressor();
    }
}
