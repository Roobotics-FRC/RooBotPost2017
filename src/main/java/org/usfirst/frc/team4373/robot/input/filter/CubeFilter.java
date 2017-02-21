
package org.usfirst.frc.team4373.robot.input.filter;

/**
 * CubeFilter cubes input numbers.
 * @author Henry Pitcairn
 */
public class CubeFilter implements IFilter {
    @Override
    public Object applyFilter(Object input) {
        double magnitude = (Double)input;
        return magnitude * magnitude * magnitude;
    }
}
