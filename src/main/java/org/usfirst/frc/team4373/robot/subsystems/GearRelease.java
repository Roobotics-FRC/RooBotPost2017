package org.usfirst.frc.team4373.robot.subsystems;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team4373.robot.RobotMap;
import org.usfirst.frc.team4373.robot.commands.teleop.GearReleaseCommand;

/**
 * A programmatic representation of physical gear release components.
 * @author aaplmath
 */
public class GearRelease extends Subsystem {

    private double INTAKE_POWER = 1;
    private CANTalon motor;
    private DoubleSolenoid solenoid1;
    private DoubleSolenoid solenoid2;
    private Compressor compressor;

    private static GearRelease gearRelease = null;

    public static GearRelease getGearRelease() {
        gearRelease = gearRelease == null ? new GearRelease() : gearRelease;
        return gearRelease;
    }

    private GearRelease() {
        super("GearRelease");
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

    public void activate() {
        setBoth(DoubleSolenoid.Value.kForward);
    }

    public void deactivate() {
        setBoth(DoubleSolenoid.Value.kReverse);
    }

    public void setNeutral() {
        setBoth(DoubleSolenoid.Value.kOff);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new GearReleaseCommand());
    }
}
