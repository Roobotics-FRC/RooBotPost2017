package org.usfirst.frc.team4373.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4373.robot.subsystems.DriveTrain;

/**
 * @author (tesla)
 * Created on 8/11/17
 */
public class VisionPID extends PIDCommand {
    private static double kP = 0.1d;
    private static double kI = 0.0d;
    private static double kD = 0.0d;
    private DriveTrain driveTrain;

    private double pidOutput;

    public static VisionPID visionPID = null;

    public static VisionPID getVisionPID() {
        return visionPID == null ? visionPID = new VisionPID() : visionPID;
    }

    public VisionPID() {
        super("VisionPID", kP, kI, kD);
    }

    @Override
    protected double returnPIDInput() {
        return NetworkTable.getTable("org.usfirst.frc.team4373.vision").getNumber("offset", 0);
    }

    @Override
    protected void usePIDOutput(double output) {
        this.pidOutput = output;
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        SmartDashboard.putNumber("System Time Thing", System.currentTimeMillis());
        this.driveTrain.setLeft(pidOutput);
        this.driveTrain.setRight(-pidOutput);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {

    }

    @Override
    protected void interrupted() {

    }
}
