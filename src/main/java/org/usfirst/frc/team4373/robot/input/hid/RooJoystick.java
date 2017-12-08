package org.usfirst.frc.team4373.robot.input.hid;

import edu.wpi.first.wpilibj.Joystick;
import org.usfirst.frc.team4373.robot.input.filter.IFilter;

/**
 * This class extends the WPILib Joystick class
 * to add deadzone and filter functionality.
 * @author (Henry Pitcairn)
 */
public class RooJoystick extends Joystick {
    private static final double DEADZONE = 0.09;
    private IFilter filter = null;

    public RooJoystick(int port, IFilter filter) {
        super(port);
        this.filter = filter;
    }

    private double filter(double val, IFilter filter) {
        Object ret;
        if (filter == null) {
            if (this.filter == null) {
                ret = val;
            } else {
                ret = this.filter.applyFilter(val);
            }
        } else {
            ret = filter.applyFilter(val);
        }
        return applyDeadzone((Double) ret);
    }

    private double applyDeadzone(double input) {
        return Math.abs(input) <= DEADZONE ? 0 : input;
    }

    private double rooGetX(IFilter filter) {
        return this.filter(this.getX(), filter);
    }

    private double rooGetY(IFilter filter) {
        return this.filter(this.getY(), filter);
    }

    private double rooGetZ(IFilter filter) {
        return this.filter(this.getZ(), filter);
    }

    private double rooGetTwist(IFilter filter) {
        return this.filter(this.getTwist(), filter);
    }

    private double rooGetThrottle(IFilter filter) {
        return this.filter(this.getThrottle(), filter);
    }

    @Override
    public double getAxis(Joystick.AxisType axis) {
        return this.getAxis(axis.value);
    }

    public double getAxis(Joystick.AxisType axis, IFilter... filter) {
        return this.filter(this.getAxis(axis.value), filter.length > 0 ? filter[0] : null);
    }

    public double getAxis(int axis, IFilter... filter) {
        return this.filter(this.getAxis(axis), filter.length > 0 ? filter[0] : null);
    }

    /**
     * Returns the filtered value of a joystick access.
     * @param axis the axis to read from
     * @return the filtered value of the axis
     */
    public double getAxis(int axis) {
        switch (axis) {
            case 0:
                return this.rooGetX(null);
            case 1:
                return this.rooGetY(null);
            case 2:
                return this.rooGetZ(null);
            case 3:
                return this.rooGetTwist(null);
            case 4:
                return this.rooGetThrottle(null);
            default:
                return 0.0;
        }
    }

    /**
     * Gets the angle the joystick is facing relative to neutral.
     * @return the joystick angle
     */
    public double getAngle() {
        double x = this.getAxis(AxisType.kX);
        double y = this.getAxis(AxisType.kY);
        return Math.atan(y / x);
    }
}
