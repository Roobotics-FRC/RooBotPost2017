package org.usfirst.frc.team4373.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4373.robot.OI;
import org.usfirst.frc.team4373.robot.RobotMap;
import org.usfirst.frc.team4373.robot.subsystems.GearIntake;

/**
 * A command to control the gear intake subsystem.
 * @author aaplmath
 */
public class GearIntakeCommand extends Command {
    private GearIntake gearIntake;

    public GearIntakeCommand() {
        super();
        gearIntake = GearIntake.getGearIntake();
    }

    @Override
    protected void initialize() {
        gearIntake.startCompressor();
    }

    @Override
    protected void execute() {
        if (OI.getOI().getDriveJoystick().getRawButton(RobotMap.GEAR_INTAKE_UP_BUTTON)) {
            gearIntake.raise();
        } else {
            gearIntake.lower();
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        this.gearIntake.stop();
    }

    @Override
    protected void interrupted() {
        this.gearIntake.stop();
    }
}
