package org.usfirst.frc.team4373.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team4373.robot.RobotMap;
import org.usfirst.frc.team4373.robot.commands.teleop.BallDispenserCommand;

public class BallDispenser extends Subsystem {

    private DoubleSolenoid dispenserSolenoid;
    private Compressor compressor;

    private static BallDispenser ballDispenser;

    public static BallDispenser getBallDispenser() {
        return ballDispenser == null ? ballDispenser = new BallDispenser() : ballDispenser;
    }

    private BallDispenser() {
        this.dispenserSolenoid = new DoubleSolenoid(RobotMap.PCM_PORT,
                RobotMap.FORWARD_SOLENOID_PORT, RobotMap.BACKWARD_SOLENOID_PORT);
        this.compressor = new Compressor(0);
        this.compressor.setClosedLoopControl(true);
    }

    public void dispense() {
        this.dispenserSolenoid.set(DoubleSolenoid.Value.kForward);
    }

    public void retain() {
        this.dispenserSolenoid.set(DoubleSolenoid.Value.kReverse);
    }

    public void neutralize() {
        this.dispenserSolenoid.set(DoubleSolenoid.Value.kOff);
    }

    public void startCompressor() {
        this.compressor.start();
    }

    public void stopCompressor() {
        this.compressor.stop();
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new BallDispenserCommand());
    }
}
