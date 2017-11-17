package org.usfirst.frc.team4373.robot.subsystems;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team4373.robot.RobotMap;

public class Shooter extends Subsystem {

    private CANTalon shooterTalon;

    private static Shooter shooter = null;

    public static Shooter getShooter() {
        return shooter = shooter == null ? new Shooter() : shooter;
    }

    private Shooter() {
        shooterTalon = new CANTalon(RobotMap.SHOOTER_MOTOR);
    }

    /**
     * Starts the shooter at full power.
     */
    public void start() {
        shooterTalon.set(1);
    }

    /**
     * Stops the shooter.
     */
    public void stop() {
        shooterTalon.set(0);
    }

    @Override
    protected void initDefaultCommand() {

    }
}
