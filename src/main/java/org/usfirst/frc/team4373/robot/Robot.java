package org.usfirst.frc.team4373.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4373.robot.commands.auton.DriveForwardAuton;
import org.usfirst.frc.team4373.robot.commands.auton.TimeBasedAuton;
import org.usfirst.frc.team4373.robot.commands.auton.TimeBasedGearAuton;
import org.usfirst.frc.team4373.robot.commands.auton.TimedDriveForwardAuton;
import org.usfirst.frc.team4373.robot.commands.teleop.TurnToPosition;
import org.usfirst.frc.team4373.robot.input.hid.HIDLogger;
import org.usfirst.frc.team4373.robot.subsystems.BallDispenser;
import org.usfirst.frc.team4373.robot.subsystems.DriveTrain;
import org.usfirst.frc.team4373.robot.subsystems.Shooter;

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
        SmartDashboard.putNumber("2017 Drive Speed", 0.3);
        SmartDashboard.putNumber("2017 Drive Time", 5);
        SmartDashboard.putNumber("2017 Shoot Speed", 0.3);
        SmartDashboard.putNumber("2017 Shoot Delay", 3);
        SmartDashboard.putString("2017 Joystick Profile File Name",
                RobotMap.MOTION_PROFILE_OUTPUT_PATH);

        SmartDashboard.putNumber("Shooter Power", 1);

        SmartDashboard.putBoolean("Toggle TurnToPosition?", false);

        autonChooser = new SendableChooser();
        autonChooser.addDefault("Disabled", "disabled");
        autonChooser.addObject("DriveStraight", "driveStraight");
        autonChooser.addObject("RudimentaryGear", "rudimentaryGear");
        autonChooser.addObject("Drive Straight 5s", "drive5Seconds");
        autonChooser.addObject("2017 Drive and Shoot", "driveShoot");
        autonChooser.addObject("2017 Joystick Profile", "joystickProfile");
        SmartDashboard.putData("Auton Mode Selector", autonChooser);

        // Joystick profiling
        SmartDashboard.putBoolean("Enable Joystick Recording", false);

        OI.getOI().getGyro().calibrate();

        DriveTrain.getDriveTrain();
        Shooter.getShooter();
        BallDispenser.getBallDispenser();
        // Climber.getClimber();
        // GearRelease.getGearRelease();

    }

    @Override
    public void teleopInit() {
        Scheduler.getInstance().removeAll();
        OI.getOI().getGyro().reset();
        super.teleopInit();
    }

    @Override
    public void autonomousInit() {
        Scheduler.getInstance().removeAll();
        if (autonCommand != null) {
            autonCommand.cancel();
        }

        String command = (String) autonChooser.getSelected();

        int autonValueKey = (int) SmartDashboard.getNumber("Auton Time:",
                RobotMap.TIME_BASED_AUTON_DEFAULT_SECONDS);
        double motorValue = SmartDashboard.getNumber("Auton Speed:",
                RobotMap.TIME_BASED_AUTON_MOTOR_VALUE);

        double driveSpeed = SmartDashboard.getNumber("2017 Drive Speed", 0.3);
        double driveTime = SmartDashboard.getNumber("2017 Drive Time", 5);

        switch (command) {
            case "driveStraight":
                autonCommand = TimeBasedAuton.getTimeBasedAuton(autonValueKey, motorValue);
                break;
            case "rudimentaryGear":
                autonCommand = TimeBasedGearAuton.getTimeBasedGearAuton(autonValueKey, motorValue);
                break;
            case "drive5Seconds":
                autonCommand = new DriveForwardAuton();
                break;
            case "driveShoot":
                autonCommand = new TimedDriveForwardAuton(driveSpeed, driveTime);
                break;
            case "joystickProfile":
                autonCommand = null;
                String actionsFileName = SmartDashboard.getString(
                        "2017 Joystick Profile File Name", RobotMap.MOTION_PROFILE_OUTPUT_PATH);
                OI.getOI().playBackHIDCapture(HIDLogger.getHIDLogger()
                        .deserializeActionsFromFile(actionsFileName));
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
        // We need to use a field variable because fetching the SmartDashboard key every time we
        // query HID devices is a waste of time
        if (SmartDashboard.getBoolean("Enable Joystick Recording", false)) {
            HIDLogger.isProfiling = true;
            // Don't waste resources spinning up the HIDLogger when we make our first query
            HIDLogger.getHIDLogger();
        }
    }

    @Override
    public void disabledPeriodic() {
        // If we just profiled, stop profiling and write to disk
        if (HIDLogger.isProfiling) {
            HIDLogger.isProfiling = false;
            HIDLogger.getHIDLogger().writeActionsToFile(RobotMap.MOTION_PROFILE_OUTPUT_PATH);
            SmartDashboard.putBoolean("Enable Joystick Recording", false);
        }
    }

    public String toString() {
        return "Main robot class";
    }
}
