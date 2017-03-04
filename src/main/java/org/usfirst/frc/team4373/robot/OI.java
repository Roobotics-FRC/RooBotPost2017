package org.usfirst.frc.team4373.robot;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import org.usfirst.frc.team4373.robot.input.filter.PiecewiseFilter2;
import org.usfirst.frc.team4373.robot.input.hid.RooJoystick;

/**
 * OI encapsulates various inputs and outputs.
 * @author Henry Pitcairn
 */
public class OI {
    private static OI oi = null;

    public static OI getOI() {
        oi = oi == null ? new OI() : oi;
        return oi;
    }

    private RooJoystick driveJoystick;
    private RooJoystick operatorJoystick;
    private Gyro gyro;

    private OI() {
        this.driveJoystick = new RooJoystick(RobotMap.DRIVE_JOYSTICK_PORT, new PiecewiseFilter2());
        this.operatorJoystick = new RooJoystick(RobotMap.OPERATOR_JOYSTICK_PORT);
        this.gyro = new AnalogGyro(RobotMap.GYRO_CHANNEL);
    }

    public RooJoystick getDriveJoystick() {
        return this.driveJoystick;
    }

    public RooJoystick getOperatorJoystick() {
        return this.operatorJoystick;
    }

    public Gyro getGyro() {
        return gyro;
    }

    public double getAngleRelative() {
        double angle = getGyro().getAngle();
        return Math.signum(angle) * (Math.abs(angle) % 180);
    }
}
