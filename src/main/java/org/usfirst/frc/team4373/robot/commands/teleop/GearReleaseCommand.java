package org.usfirst.frc.team4373.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4373.robot.OI;
import org.usfirst.frc.team4373.robot.RobotMap;
import org.usfirst.frc.team4373.robot.subsystems.GearRelease;

/**
 * A command to control the gear release subsystem.
 * @author aaplmath
 */
public class GearReleaseCommand extends Command {
    private GearRelease gearRelease;

    public GearReleaseCommand() {
        super();
        requires(gearRelease = GearRelease.getGearRelease());
    }

    @Override
    protected void initialize() {
        this.gearRelease.activate();
    }

    @Override
    protected void execute() {
        if (OI.getOI().getDriveJoystick().getRawButton(RobotMap.GEAR_INTAKE_UP_BUTTON)) {
            gearRelease.activate();
        } else {
            gearRelease.deactivate();
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        this.gearRelease.setNeutral();
    }

    @Override
    protected void interrupted() {
        this.gearRelease.setNeutral();
    }
}
