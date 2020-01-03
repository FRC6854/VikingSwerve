package frc.robot.output.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.drivetrain.SwerveWheelController;

public class TeleopDrive extends CommandBase {

    private SwerveWheelController swerve = null;

    private boolean currentFOD = false;

    public TeleopDrive(){
        swerve = SwerveWheelController.getInstance();

        addRequirements(swerve);
    }

    @Override
    public void initialize(){
        currentFOD = swerve.getFOD();

        swerve.resetGyro();
    }

    @Override
    public void execute() {
        if (Robot.driver.getDriverAButtonPressed()) {
            swerve.resetGyro();
        }

        if (Robot.driver.getDriverBButtonPressed()) {
            currentFOD = !currentFOD;
            swerve.setFOD(currentFOD);
        }

        swerve.drive(Robot.driver.getDriverLeftStickX(), Robot.driver.getDriverLeftStickY(), Robot.driver.getDriverRightStickX(), swerve.gyroAngle());
    }
}
