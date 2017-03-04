package org.usfirst.frc.team4373.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4373.robot.commands.auton.TimeBasedAuton;
import org.usfirst.frc.team4373.robot.subsystems.Climber;
import org.usfirst.frc.team4373.robot.subsystems.DriveTrain;
import org.usfirst.frc.team4373.robot.subsystems.GearRelease;

/**
 * This is the main robot class.
 */
public class Robot extends IterativeRobot {

    private Command autonCommand = null;
    @Override
    public void robotInit() {
        SmartDashboard.putNumber("Overriden Auton Time:", 4);
        SmartDashboard.putNumber("Overriden Auton Speed:", 0.5);
        OI.getOI().getGyro().calibrate();
        DriveTrain.getDriveTrain();
        Climber.getClimber();
        GearRelease.getGearRelease();
    }

    @Override
    public void teleopInit() {
        OI.getOI().getGyro().reset();
        super.teleopInit();
    }

    @Override
    public void autonomousInit() {
        super.autonomousInit();
        OI.getOI().getGyro().reset();
        int autonValueKey = (int) SmartDashboard.getNumber("Overriden Auton Time:",
                RobotMap.TIME_BASED_AUTON_DEFAULT_SECONDS);
        double motorValue = SmartDashboard.getNumber("Overriden Auton Speed:",
                RobotMap.TIME_BASED_AUTON_MOTOR_VALUE);
        autonCommand = new TimeBasedAuton(autonValueKey, motorValue);
        autonCommand.start();
    }

    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        SmartDashboard.putNumber("Gyro", OI.getOI().getGyro().getAngle());
    }

    public String toString() {
        return "Main robot class";
    }
}
