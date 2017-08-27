package org.usfirst.frc.team4373.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4373.robot.OI;
import org.usfirst.frc.team4373.robot.subsystems.DriveTrain;

public class TurnToPosition extends PIDCommand {
    private static double kP = 0.1d;
    private static double kI = 0.0d;
    private static double kD = 0.0d;
    private DriveTrain driveTrain;
    private double pidOutput;

    private boolean isFinished = false;

    /**
     * Constructs a TurnToPosition command.
     */
    public TurnToPosition() {
        super("TurnToPosition", kP, kI, kD);
        requires(DriveTrain.getDriveTrain());
        driveTrain = DriveTrain.getDriveTrain();
        setInterruptible(true);
    }

    @Override
    protected double returnPIDInput() {
        return OI.getOI().getAngleRelative();
    }

    @Override
    protected void usePIDOutput(double output) {
        if (Math.abs(OI.getOI().getAngleRelative() - this.getSetpoint()) < 1) {
            // TODO: Allow for oscillation
            // Recommended PID values: P=0.06 P=0.09
            // isFinished = true;
        }
        this.pidOutput = output;
        SmartDashboard.putNumber("PID Output", this.pidOutput);
    }

    @Override
    protected void initialize() {
        // this.setSetpoint(90d);
        this.setInputRange(-180, 180);
        this.getPIDController().setOutputRange(-0.2, 0.2);
    }

    @Override
    protected void execute() {
        if (isFinished) return;
        kP = SmartDashboard.getNumber("kP", 0.0d);
        kI = SmartDashboard.getNumber("kI", 0.0d);
        kD = SmartDashboard.getNumber("kD", 0.0d);
        this.getPIDController().setPID(kP, kI, kD);

        this.driveTrain.setLeft(this.pidOutput);
        this.driveTrain.setRight(-this.pidOutput);

        SmartDashboard.putNumber("Current Setpoint", this.getSetpoint());
        this.setSetpoint(SmartDashboard.getNumber("PID Setpoint", 0));

        if (SmartDashboard.getBoolean("Toggle TurnToPosition?", true)) {
            isFinished = true;
            SmartDashboard.putBoolean("Toggle TurnToPosition?", false);
        }

    }

    @Override
    protected boolean isFinished() {
        return this.isFinished;
    }

    @Override
    protected void end() {
        System.out.println("***TurnToPosition terminated***");
        OI.getOI().getGyro().reset();
        this.getPIDController().reset();
        this.driveTrain.setBoth(0);
    }

    @Override
    protected void interrupted() {
        this.getPIDController().reset();
        this.driveTrain.setBoth(0);
    }
}
