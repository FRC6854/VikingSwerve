package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.drivetrain.SwerveWheelController;
import viking.Controller;

public class Robot extends TimedRobot {

	public static Controller driver = new Controller(0);

	private static CommandScheduler scheduler = CommandScheduler.getInstance();

	@Override
	public void robotInit() {
		System.out.println("--------------");
		System.out.println("Robot Init");
		System.out.println("--------------");

		SwerveWheelController.getInstance();
	}

	@Override
	public void robotPeriodic() {}

	@Override
	public void disabledInit() {
		System.out.println("--------------");
		System.out.println("Disabled");
		System.out.println("--------------");
	}

	@Override
	public void autonomousInit() {
		System.out.println("--------------");
		System.out.println("Autonomous");
		System.out.println("--------------");
	}

	@Override
	public void teleopInit() {
		System.out.println("--------------");
		System.out.println("Teleop");
		System.out.println("--------------");
	}

	@Override
	public void autonomousPeriodic() {
		scheduler.run();
	}

	@Override
	public void teleopPeriodic() {
		scheduler.run();
	}
}
