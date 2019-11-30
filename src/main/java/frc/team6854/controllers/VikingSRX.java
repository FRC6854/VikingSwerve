package frc.team6854.controllers;

import com.ctre.phoenix.motion.BufferedTrajectoryPointStream;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class VikingSRX {

    private TalonSRX motor;
    private BufferedTrajectoryPointStream bufferedStream = new BufferedTrajectoryPointStream();
    
    private boolean closedLoop = true;

    private double metersPerRevolution = 0;

    /**
     * @param id the CAN ID for the Talon SRX
     * @param inverted is the motor inverted
     */
    public VikingSRX(int id, boolean inverted) {
        this(id, inverted, false, null, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        closedLoop = false;
    }

    /**
     * @param id the CAN ID for the Talon SRX
     * @param inverted is the motor inverted
     * @param sensorPhase should the encoder be inverted
     * @param device the type of encoder
     */
    public VikingSRX(int id, boolean inverted, boolean sensorPhase, FeedbackDevice device) {
        this(id, inverted, sensorPhase, device, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        closedLoop = false;
    }

    /**
     * @param id the CAN ID for the Talon SRX
     * @param inverted is the motor inverted
     * @param sensorPhase should the encoder be inverted
     * @param device the type of encoder
     * @param kF the F variable of PIDF
     * @param kP the P variable of PIDF
     * @param kI the I variable of PIDF
     * @param kD the D variable of PIDF
     * @param velocity the max velocity for Motion Magic
     * @param acceleration the max acceleration for Motion Magic
     * @param metersPerRevolution the meters traveled per revolution of the motor
     */
    public VikingSRX(int id, boolean inverted, boolean sensorPhase, 
                            FeedbackDevice device, double kF, double kP, double kI, 
                            double kD, double velocity, double acceleration,
                            double metersPerRevolution) {
        closedLoop = true;

        motor = new TalonSRX(id);

        motor.configFactoryDefault();

        // Invert Power
        motor.setInverted(inverted);

        // Invert Encoder
        motor.setSensorPhase(sensorPhase);

        motor.configSelectedFeedbackSensor(device, 0, 0);

        // Set-up PIDF[0]
        motor.selectProfileSlot(0, 0);

        motor.config_kF(0, kF, 0);
        motor.config_kP(0, kP, 0);
        motor.config_kI(0, kI, 0);
        motor.config_kD(0, kD, 0);

        // Motion Magic
        motor.configMotionCruiseVelocity(1250);
        motor.configMotionAcceleration(1500);

        /*  
            --------------
            Motion Profile
            --------------
            In our case we must use the value 25ms for both since in our profile we use a delta time of 50ms
        */
        motor.configMotionProfileTrajectoryPeriod(25);
        motor.changeMotionControlFramePeriod(25);

        // Zero Sensor
        motor.setSelectedSensorPosition(0);
    }

    public void initMotionBuffer(Double[][] profile, int totalCnt) {
        if (closedLoop == true) {
            TrajectoryPoint point = new TrajectoryPoint(); // temp for for loop, since unused params are initialized
                                                       // automatically, you can alloc just one

            /* Insert every point into buffer, no limit on size */
            for (int i = 0; i < totalCnt; ++i) {

                double positionRot = profile[i][0] * (1 / metersPerRevolution);
                double velocityRPM = profile[i][1] * (1 / metersPerRevolution);
                int durationMilliseconds = profile[i][2].intValue();

                /* for each point, fill our structure and pass it to API */
                point.timeDur = durationMilliseconds;
                point.position = positionRot * 4096; // Convert Revolutions to
                                                                // Units
                point.velocity = velocityRPM * 4096 / 600.0; // Convert RPM to
                                                                        // Units/100ms
                point.profileSlotSelect0 = 0; /* which set of gains would you like to use [0,3]? */
                point.profileSlotSelect1 = 0; /* auxiliary PID [0,1], leave zero */
                point.zeroPos = (i == 0); /* set this to true on the first point */
                point.isLastPoint = ((i + 1) == totalCnt); /* set this to true on the last point */
                point.arbFeedFwd = 0; /* you can add a constant offset to add to PID[0] output here */

                bufferedStream.Write(point);
            }
        }
    }

    public void resetMotionProfile() {
        bufferedStream.Clear();
        motor.clearMotionProfileTrajectories();
    }

    public void percentOutput(double value) {
        motor.set(ControlMode.PercentOutput, value);
    }

    public void positionControl(double ticks) {
        if (closedLoop == true) {
            motor.set(ControlMode.Position, ticks);
        }
    }

    public void velocityControl(int velocity) {
        if (closedLoop == true) {
            motor.set(ControlMode.Velocity, velocity);
        }
    }

    public void motionMagic(double ticks) {
        if (closedLoop == true) {
            motor.set(ControlMode.MotionMagic, ticks);
        }
    }

    public void motionProfileStart() {
        if (closedLoop == true ) {
            motor.startMotionProfile(bufferedStream, 10, ControlMode.MotionProfile);
        }
    }

    public void setNeutralMode(NeutralMode mode) {
        motor.setNeutralMode(mode);
    }

    public int getTicks() {
        return motor.getSelectedSensorPosition();
    }

    public int getVelocity() {
        return motor.getSelectedSensorVelocity();
    }

    public ControlMode getControlMode() {
        return motor.getControlMode();
    }

    public boolean isMotionProfileFinished() {
        if (closedLoop == true) {
            return motor.isMotionProfileFinished();
        }

        return false;
    }

    public void zeroSensor() {
        motor.setSelectedSensorPosition(0);
    }

    public TalonSRX getTalonSRX() {
        return motor;
    }
}
