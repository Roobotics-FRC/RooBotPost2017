package org.usfirst.frc.team4373.robot.input.hid;

import edu.wpi.first.wpilibj.Joystick;
import org.usfirst.frc.team4373.robot.input.filter.DoubleTypeFilter;

/**
 * This class extends the WPILib Joystick class
 * to add deadzone and filter functionality.
 * New features:
 * =============
 * 1) Generify the class based on filter type, expressed as an upper bound GenericFilter
 * 2) Rewrite as Singleton
 * 3) Remove custom filter capabilities
 *
 * @author (Henry Pitcairn)
 * @author Rui-Jie Fang
 */
public class RooJoystick<F extends DoubleTypeFilter> extends Joystick {
    private static final double DEADZONE = 0.09;
    private F filter = null;

    public RooJoystick(int port, F filter) {
        super(port);
        this.filter = filter;
    }

    private double filter(double val) {
        // We don't know the return type because of type erasure...
        return applyDeadzone(this.filter.applyFilter(val));
    }

    private double applyDeadzone(double input) {
        return Math.abs(input) <= DEADZONE ? 0 : input;
    }

    private double rooGetX() {
        return this.filter(this.getX());
    }

    private double rooGetY() {
        return this.filter(this.getY());
    }

    private double rooGetZ() {
        return this.filter(this.getZ());
    }

    private double rooGetTwist() {
        return this.filter(this.getTwist());
    }

    private double rooGetThrottle() {
        return this.filter(this.getThrottle());
    }

    @Override
    public double getAxis(Joystick.AxisType axis) {
        return this.getAxis(axis.value);
    }


    /**
     * Returns the filtered value of a joystick access.
     *
     * @param axis the axis to read from
     * @return the filtered value of the axis
     */
    public double getAxis(int axis) {
        switch (axis) {
            case 0:
                return this.rooGetX();
            case 1:
                return this.rooGetY();
            case 2:
                return this.rooGetZ();
            case 3:
                return this.rooGetTwist();
            case 4:
                return this.rooGetThrottle();
            default:
                return 0.0;
        }
    }

    /**
     * Gets the angle the joystick is facing relative to neutral.
     *
     * @return the joystick angle
     */
    public double getAngle() {
        double x = this.getAxis(AxisType.kX);
        double y = this.getAxis(AxisType.kY);
        return Math.atan(y / x);
    }
}
