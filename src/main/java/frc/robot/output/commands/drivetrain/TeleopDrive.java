package frc.robot.output.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Robot;
import frc.robot.subsystems.drivetrain.SwerveWheelController;

public class TeleopDrive extends Command {

    private SwerveWheelController swerve = null;

    public TeleopDrive(){
        swerve = SwerveWheelController.getInstance();

        requires(swerve);
    }

    @Override
    protected void initialize(){
        swerve.resetGyro();
    }

    @Override
    protected void execute() {
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
