
package org.usfirst.frc.team4373.robot.input.filter;

/**
 * CubeFilter cubes input numbers.
 * @author Henry Pitcairn
 * @author Rui-Jie Fang
 */
public class CubeFilter implements GenericFilter<Double> {

    @Override
    public Double applyFilter(Double input) {
        return input * input * input;
    }
}
