package org.usfirst.frc.team4373.robot.subsystems;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team4373.robot.RobotMap;
import org.usfirst.frc.team4373.robot.commands.teleop.ScaleShooterCommand;
import org.usfirst.frc.team4373.robot.commands.teleop.ToggleShooterCommand;

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
     * Sets the shooter's power to the amount given, between 0 and 1.
     * @param power The amount, between 0 and 1, to give to the motor.
     */
    public void setPower(double power) {
        if (power < 0) {
            power = -0;
        } else if (power > 1) {
            power = -1;
        }
        shooterTalon.set(-power);
    }

    /**
     * Stops the shooter.
     */
    public void stop() {
        setPower(0);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new ToggleShooterCommand());
    }
}
