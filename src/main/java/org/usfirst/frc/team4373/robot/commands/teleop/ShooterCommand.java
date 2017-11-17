package org.usfirst.frc.team4373.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4373.robot.OI;
import org.usfirst.frc.team4373.robot.RobotMap;
import org.usfirst.frc.team4373.robot.subsystems.Shooter;

public class ShooterCommand extends Command {

    private Shooter shooter;

    public ShooterCommand() {
        shooter = Shooter.getShooter();
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        if (OI.getOI().getOperatorJoystick().getRawButton(
                RobotMap.OPERATOR_JOYSTICK_SHOOTER_BUTTON)) {
            this.shooter.start();
        } else {
            this.shooter.stop();
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        this.shooter.stop();
    }

    @Override
    protected void interrupted() {
        this.shooter.stop();
    }
}
