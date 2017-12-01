package org.usfirst.frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4373.robot.subsystems.BallDispenser;
import org.usfirst.frc.team4373.robot.subsystems.Shooter;

public class TimedShootingAuton extends Command {

    private Shooter shooter;
    private BallDispenser ballDispenser;
    private long lastBallRelease;
    private double speed;
    private double pistonDelay;

    public TimedShootingAuton(double speed, double pistonDelay) {
        requires(this.shooter = Shooter.getShooter());
        requires(this.ballDispenser = BallDispenser.getBallDispenser());
        if (speed > 1) {
            this.speed = 1;
        } else if (speed < 0) {
            this.speed = 0;
        } else {
            this.speed = speed;
        }
        this.speed = speed;
        this.pistonDelay = pistonDelay;
    }

    @Override
    protected void initialize() {
        this.lastBallRelease = System.currentTimeMillis();
        this.ballDispenser.startCompressor();
        this.shooter.setPower(this.speed);
    }

    @Override
    protected void execute() {
        if (System.currentTimeMillis() >= this.lastBallRelease + this.pistonDelay) {
            this.ballDispenser.dispense();
            this.lastBallRelease = System.currentTimeMillis();
        } else {
            this.ballDispenser.retain();
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        this.shooter.stop();
        this.ballDispenser.stopCompressor();
        this.ballDispenser.neutralize();
    }

    @Override
    protected void interrupted() {
        this.shooter.stop();
        this.ballDispenser.stopCompressor();
        this.ballDispenser.neutralize();
    }
}
