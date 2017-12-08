package org.usfirst.frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4373.robot.subsystems.BallDispenser;
import org.usfirst.frc.team4373.robot.subsystems.Shooter;

public class TimedShootingAuton extends Command {

    private Shooter shooter;
    private BallDispenser ballDispenser;
    private long lastBallRelease;
    private double speed;
    private double pistonInterval;
    private static final double pistonDelay = 500d;

    /**
     * Constructs a new TimedShootingAuton class.
     * @param speed The speed, from 0 to 1, at which the shooter should shoot.
     * @param pistonInterval The delay between ball releases.
     */
    public TimedShootingAuton(double speed, double pistonInterval) {
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
        this.pistonInterval = pistonInterval * 1000d;
    }

    @Override
    protected void initialize() {
        this.lastBallRelease = System.currentTimeMillis();
        this.ballDispenser.startCompressor();
        this.shooter.setPower(this.speed);
    }

    @Override
    protected void execute() {
        if (System.currentTimeMillis() >= this.lastBallRelease + this.pistonInterval) {
            this.ballDispenser.dispense();
            this.lastBallRelease = System.currentTimeMillis();
        } else if (System.currentTimeMillis() >= this.lastBallRelease + pistonDelay) {
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
