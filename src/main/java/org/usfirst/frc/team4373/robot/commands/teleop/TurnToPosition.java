package org.usfirst.frc.team4373.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4373.robot.OI;
import org.usfirst.frc.team4373.robot.subsystems.DriveTrain;

public class TurnToPosition extends PIDCommand {
    private static double kP = 0.0d;
    private static double kI = 0.0d;
    private static double kD = 0.0d;
    private DriveTrain driveTrain;
    private double pidOutput;

    public static TurnToPosition turnToPosition = null;

    public static TurnToPosition getTurnToPosition() {
        return turnToPosition == null ? turnToPosition = new TurnToPosition() : turnToPosition;
    }

    private TurnToPosition() {
        super("TurnToPosition", kP, kI, kD);
        requires(DriveTrain.getDriveTrain());
        driveTrain = DriveTrain.getDriveTrain();
        setInterruptible(true);
    }

    @Override
    protected double returnPIDInput() {
        return OI.getOI().getAngleAbsolute();
    }

    @Override
    protected void usePIDOutput(double output) {
        if (Math.abs(this.pidOutput - this.getSetpoint()) < 0.25) {
            this.pidOutput = 0;
        }
        this.pidOutput = output;
        SmartDashboard.putNumber("PID Output", this.pidOutput);
    }

    @Override
    protected void initialize() {
        this.setSetpoint(0);
        this.setInputRange(-180, 180);
        this.getPIDController().setOutputRange(-1, 1);
    }

    @Override
    protected void execute() {
        kP = SmartDashboard.getNumber("kP", 0.0d);
        kI = SmartDashboard.getNumber("kI", 0.0d);
        kD = SmartDashboard.getNumber("kD", 0.0d);
        this.getPIDController().setPID(kP, kI, kD);
        SmartDashboard.putNumber("Gyro value", OI.getOI().getAngleAbsolute());
        this.setSetpoint(SmartDashboard.getNumber("Angle setpoint", 0));
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        this.getPIDController().reset();
        OI.getOI().getGyro().reset();
        this.driveTrain.setBoth(0);
    }

    @Override
    protected void interrupted() {
        this.getPIDController().reset();
        this.driveTrain.setBoth(0);
    }
}
