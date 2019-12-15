package frc.robot.subsystems.drivetrain;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

import viking.controllers.SwerveWheelDrive;

public class SwerveWheel extends PIDSubsystem implements SwerveDrivetrainConstants {

    public String name;

    private TalonSRX steerMotor;
    private AnalogInput absEnc;
    private SwerveWheelDrive drive;

    private int countsWhenFrwd;

    public SwerveWheel(SwerveWheelDrive drive, int m_steer, int analogEnc, int zeroOffset, String name) {
        super(name, kP, kI, kD, kF);

        this.name = name;

        this.drive = drive;
       
        countsWhenFrwd = zeroOffset;
        
        steerMotor = new TalonSRX(m_steer);
        absEnc = new AnalogInput(analogEnc);

        // Reset all of the settings on startup
        steerMotor.configFactoryDefault();

        // Set the feedback device for the steering (turning) Talon SRX
        steerMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);

        // Set the current quadrature position relative to the analog position to make sure motor has 0 position on startup
        steerMotor.setSelectedSensorPosition(getAbsAngleDeg() * QUAD_COUNTS_PER_ROT / 180);

        // Set the input range of the PIDF so that it will only accept angles between -180 to 180
        getPIDController().setInputRange(-180, 180);

        // The output range of the motors for Percent Output
        getPIDController().setOutputRange(-1, 1);

        // Set the PIDF so that it will have continuous output (-179 to 179 will not make the wheel do a 360)
        getPIDController().setContinuous(true);

        // Start the PIDF controller
        getPIDController().enable();
    }

    // Get the current angle of the analog encoder
    private int getAbsAngleDeg(){
        return (int)(180 * (absEnc.getValue() - countsWhenFrwd) / 4096);   
    }

    // Get current ticks
    public int getTicks() {
        return steerMotor.getSelectedSensorPosition();
    }

    public void setSpeed(double speed) {
        drive.setSpeed(speed);
    }

    // Convert ticks to angle bound from -180 to 180
    public double ticksToAngle(int ticks) {
        double angleTicks = ticks % QUAD_COUNTS_PER_ROT;
        
        double result = (angleTicks / (QUAD_COUNTS_PER_ROT / 2)) * 180;

        if (result > 180) {
            result -= 360;
        }

        return result;
    }

    // -------------------
    // PID Subsystem Stuff
    // -------------------

    // Set the current setpoint
    @Override
    public void setSetpoint(double setpoint) {
        getPIDController().setSetpoint(setpoint);
    }

    // Get the input device for the PID
    @Override
    protected double returnPIDInput() {
        return ticksToAngle(getTicks());
    }

    // Set the output for the PID
    @Override
    protected void usePIDOutput(double output) {
        steerMotor.set(ControlMode.PercentOutput, output);
    }

    // This is needed, but it shouldn't be used due to the motors being controlled via the SwerveWheelController
    @Override
    protected void initDefaultCommand() {
    }
}
