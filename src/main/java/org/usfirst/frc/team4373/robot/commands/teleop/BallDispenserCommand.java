package org.usfirst.frc.team4373.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4373.robot.OI;
import org.usfirst.frc.team4373.robot.RobotMap;
import org.usfirst.frc.team4373.robot.subsystems.BallDispenser;

public class BallDispenserCommand extends Command {
    private BallDispenser ballDispenser;

    public BallDispenserCommand() {
        super("BallDispenserCommand");
        requires(this.ballDispenser = BallDispenser.getBallDispenser());
    }

    @Override
    protected void initialize() {
        this.ballDispenser.startCompressor();
    }

    @Override
    protected void execute() {
        if (OI.getOI().getOperatorJoystick()
                .getRawButton(RobotMap.OPERATOR_JOYSTICK_DISPENSER_BUTTON)) {
            ballDispenser.dispense();
        } else {
            ballDispenser.retain();
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        this.ballDispenser.stopCompressor();
        this.ballDispenser.neutralize();
    }

    @Override
    protected void interrupted() {
        this.ballDispenser.stopCompressor();
        this.ballDispenser.neutralize();
    }
}
