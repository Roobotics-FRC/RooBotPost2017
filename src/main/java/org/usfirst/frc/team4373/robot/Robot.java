package org.usfirst.frc.team4373.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4373.robot.commands.auton.TimeBasedAuton;
import org.usfirst.frc.team4373.robot.commands.auton.TimeBasedGearAuton;
import org.usfirst.frc.team4373.robot.commands.teleop.TurnToPosition;
import org.usfirst.frc.team4373.robot.subsystems.DriveTrain;

/**
 * This is the main robot class.
 */
public class Robot extends IterativeRobot {
    private Command autonCommand = null;
    private SendableChooser autonChooser = null;

    @Override
    public void robotInit() {

        // PID tuning
        SmartDashboard.putNumber("kP", 0.0d);
        SmartDashboard.putNumber("kI", 0.0d);
        SmartDashboard.putNumber("kD", 0.0d);
        SmartDashboard.putBoolean("Reset Gyro?", false);
        SmartDashboard.putNumber("PID Setpoint", 0);
        SmartDashboard.putBoolean("Use Vision?", false);

        SmartDashboard.putNumber("Auton Time:", 4);
        SmartDashboard.putNumber("Auton Speed:", 0.5);

        SmartDashboard.putBoolean("Toggle TurnToPosition?", false);

        autonChooser = new SendableChooser();
        autonChooser.addDefault("Disabled", "disabled");
        autonChooser.addObject("DriveStraight", "driveStraight");
        autonChooser.addObject("RudimentaryGear", "rudimentaryGear");
        SmartDashboard.putData("Auton Mode Selector", autonChooser);
        SmartDashboard.putNumber("Test Number", 42);

        OI.getOI().getGyro().calibrate();

        DriveTrain.getDriveTrain();
        // Climber.getClimber();
        // GearRelease.getGearRelease();

    }

    @Override
    public void teleopInit() {
        OI.getOI().getGyro().reset();
        super.teleopInit();
    }

    @Override
    public void autonomousInit() {
        if (autonCommand != null) {
            autonCommand.cancel();
        }
        String command = (String) autonChooser.getSelected();
        int autonValueKey = (int) SmartDashboard.getNumber("Auton Time:",
                RobotMap.TIME_BASED_AUTON_DEFAULT_SECONDS);
        double motorValue = SmartDashboard.getNumber("Auton Speed:",
                RobotMap.TIME_BASED_AUTON_MOTOR_VALUE);
        switch (command) {
            case "driveStraight":
                autonCommand = TimeBasedAuton.getTimeBasedAuton(autonValueKey, motorValue);
                break;
            case "rudimentaryGear":
                autonCommand = TimeBasedGearAuton.getTimeBasedGearAuton(autonValueKey, motorValue);
                break;
            default:
                autonCommand = null;
        }
        if (autonCommand != null) {
            autonCommand.start();
        }
    }

    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        if (SmartDashboard.getBoolean("Toggle TurnToPosition?", false)) {
            Scheduler.getInstance().add(new TurnToPosition());
            SmartDashboard.putBoolean("Toggle TurnToPosition?", false);
        }
        SmartDashboard.putNumber("Gyro_Value", Math.round(OI.getOI().getAngleRelative()
                * 1000d) / 1000d);
        if (SmartDashboard.getBoolean("Reset Gyro?", false)) {
            OI.getOI().getGyro().reset();
            SmartDashboard.putBoolean("Reset Gyro?", false);
        }
    }

    public String toString() {
        return "Main robot class";
    }
}
