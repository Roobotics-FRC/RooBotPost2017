package org.usfirst.frc.team4373.robot.input.filter;

/**
 * ThirdFilter simply divides input by 3.
 * @author Henry Pitcairn
 * @author Rui-Jie Fang
 */
public class ThirdFilter implements GenericFilter<Double> {

    @Override
    public Double applyFilter(Double input) {
        return input / 3;
    }
}
