package org.usfirst.frc.team4373.robot.subsystems;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team4373.robot.RobotMap;

/**
 * Created by jrr6 on 2/16/17.
 */
public class GearIntake extends Subsystem {

    private double INTAKE_POWER = 1;
    private CANTalon motor;
    private DoubleSolenoid solenoid1;
    private DoubleSolenoid solenoid2;
    private Compressor compressor;

    private static GearIntake gearIntake = null;

    public static GearIntake getGearIntake() {
        gearIntake = gearIntake == null ? new GearIntake() : gearIntake;
        return gearIntake;
    }

    private GearIntake() {
        super("GearIntake");
        this.motor = new CANTalon(RobotMap.INTAKE_PORT);
        this.solenoid1 = new DoubleSolenoid(RobotMap.PCM_PORT, 2, 0);
        this.solenoid2 = new DoubleSolenoid(RobotMap.PCM_PORT, 3, 1);
        this.compressor = new Compressor(RobotMap.PCM_PORT);
        this.compressor.setClosedLoopControl(true);
        startCompressor();
    }

    public void startCompressor() {
        this.compressor.start();
    }

    private void setBoth(DoubleSolenoid.Value value) {
        this.solenoid1.set(value);
        this.solenoid2.set(value);
    }

    public void raise() {
        setBoth(DoubleSolenoid.Value.kForward);
    }

    public void lower() {
        setBoth(DoubleSolenoid.Value.kReverse);
    }

    public void stop() {
        setBoth(DoubleSolenoid.Value.kOff);
    }

    @Override
    protected void initDefaultCommand() {
//        setDefaultCommand(new GearIntakeCommand());
    }
}
