package org.usfirst.frc.team4373.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4373.robot.subsystems.DriveTrain;

/**
 * This is the main robot class.
 */
public class Robot extends IterativeRobot {

    @Override
    public void robotInit() {
        OI.getOI().getGyro().calibrate();
        DriveTrain.getDriveTrain();
    }

    @Override
    public void teleopInit() {
        OI.getOI().getGyro().reset();
        super.teleopInit();
    }

    @Override
    public void autonomousInit() {
        OI.getOI().getGyro().reset();
        super.autonomousInit();
    }

    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public  void teleopPeriodic() {
        Scheduler.getInstance().run();
        SmartDashboard.putNumber("Gyro", OI.getOI().getGyro().getAngle());
    }
    
    public String toString() {
        return "Main robot class";
    }
}
