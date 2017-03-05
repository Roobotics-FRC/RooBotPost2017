package org.usfirst.frc.team4373.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4373.robot.OI;
import org.usfirst.frc.team4373.robot.RobotMap;
import org.usfirst.frc.team4373.robot.input.hid.RooJoystick;
import org.usfirst.frc.team4373.robot.subsystems.Climber;

/**
 * A command to control the climber.
 * @author aaplmath
 */
public class ClimberCommand extends Command {

    private Climber climber;

    /**
     * Initialize the class.
     */
    public ClimberCommand() {
        super("ClimberCommand");
        requires(this.climber = Climber.getClimber());
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        if (OI.getOI().getOperatorJoystick().getRawButton(RobotMap.OPERATOR_JOYSTICK_CLIMBER_BUTTON)) {
            climber.setForward(1d);
        } else {
            climber.stop();
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        climber.stop();
    }

    @Override
    protected void interrupted() {
        climber.stop();
    }
}
