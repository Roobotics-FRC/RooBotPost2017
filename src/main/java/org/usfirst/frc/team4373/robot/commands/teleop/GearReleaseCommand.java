package org.usfirst.frc.team4373.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4373.robot.OI;
import org.usfirst.frc.team4373.robot.RobotMap;
import org.usfirst.frc.team4373.robot.subsystems.GearRelease;

/**
 * A command to control the gear release subsystem.
 * @author aaplmath
 * @author Henry Pitcairn
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
        this.gearRelease.startCompressor();
    }

    @Override
    protected void execute() {
        if (OI.getOI().getOperatorJoystick().getRawButton(RobotMap.OPERATOR_JOYSTICK_GEAR_INTAKE_UP_BUTTON)) {
            gearRelease.activate();
            SmartDashboard.putBoolean("Pistons", true);
        } else {
            SmartDashboard.putBoolean("Pistons", false);
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
        this.gearRelease.stopCompressor();
    }

    @Override
    protected void interrupted() {
        this.gearRelease.setNeutral();
        this.gearRelease.stopCompressor();
    }
}
