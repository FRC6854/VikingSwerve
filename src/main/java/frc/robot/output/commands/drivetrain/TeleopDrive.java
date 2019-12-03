package frc.robot.output.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Robot;
import frc.robot.subsystems.drivetrain.SwerveWheelController;

public class TeleopDrive extends Command {

    private SwerveWheelController swerve = null;

    private boolean currentFOD = false;

    public TeleopDrive(){
        swerve = SwerveWheelController.getInstance();

        requires(swerve);
    }

    @Override
    protected void initialize(){
        currentFOD = swerve.getFOD();

        swerve.resetGyro();
    }

    @Override
    protected void execute() {
        if (Robot.driver.getDriverAButtonPressed()) {
            swerve.resetGyro();
        }

        if (Robot.driver.getDriverBButtonPressed()) {
            currentFOD = !currentFOD;
            swerve.setFOD(currentFOD);
        }

        swerve.drive(Robot.driver.getDriverLeftStickX(), Robot.driver.getDriverLeftStickY(), Robot.driver.getDriverRightStickX(), swerve.gyroAngle());
    }

    @Override
    protected boolean isFinished(){
        return false;
    }
    
    @Override
    protected void end(){
    }

    @Override
    protected void interrupted(){
        end();
    }
}
