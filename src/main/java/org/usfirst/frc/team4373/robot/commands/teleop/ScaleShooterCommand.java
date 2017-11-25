package org.usfirst.frc.team4373.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4373.robot.OI;
import org.usfirst.frc.team4373.robot.RobotMap;
import org.usfirst.frc.team4373.robot.subsystems.Shooter;

public class ScaleShooterCommand extends Command {

    private Shooter shooter;

    public ScaleShooterCommand() {
        shooter = Shooter.getShooter();
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        this.shooter.setPower(OI.getOI().getDriveJoystick()
                .getRawAxis(RobotMap.DRIVE_JOYSTICK_SHOOTER_AXIS) + 1);
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
