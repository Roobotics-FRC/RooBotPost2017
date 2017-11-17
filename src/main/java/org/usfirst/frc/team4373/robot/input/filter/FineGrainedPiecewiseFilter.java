package org.usfirst.frc.team4373.robot.input.filter;

/**
 * Piecewise linear function for more granular joystick control.
 *
 * @author (tesla)
 * @author Rui-Jie Fang
 */
public class FineGrainedPiecewiseFilter extends DoubleTypeFilter {

    @Override
    public Double applyFilter(Double input) {
        return (Math.abs(input) <= 0.8) ? 0.5 * input : ((3 * input) - (Math.signum(input) * 2));
    }
}
