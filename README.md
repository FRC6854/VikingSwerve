# Viking Swerve

Swerve drive is known as one of the most complicated drivetrains around and Viking Swerve aims to fix that. It has the math and motor driving already setup and it is easy to change the values to match your robot's. To get started just do a `git pull`. Our swerve drive uses the Andy Mark Swerve Modules.

## Whats Included?

This project contains TalonSRX angle motor controllers with Quad Encoders and a mismatch of speed motor controllers (TalonSRX, VictorSPX and Spark). It also uses the [VIKING](https://github.com/FRC6854/VIKING) package which contains many wrappers and example motion profiling code. This also contains Field Oriented Drive.

The Quad Encoders are the encoders on the angle motor going through the [analog breakout board](http://www.ctr-electronics.com/adaptors/talon-srx-analog-breakout-board.html#product_tabs_description_tabbed). We then declare in the code that they are Quad Encoders.

## Getting Started

| Location                               | Name                  | Description                                                                                                                                      |
|----------------------------------------|-----------------------|--------------------------------------------------------------------------------------------------------------------------------------------------|
| /frc/robot/subsystems/drivetrain/      | SwerveWheelController | Controls all of the Swerve Modules and does the calculations for swerve. It also hold the Gyro for FOC.                                          |
| /frc/robot/subsystems/drivetrain/      | SwerveWheel           | This hold a Swerve Module. It sets the angle and also sets the speed using the SwerveWheelDrive.                                                 |
| /frc/robot/subsystems/drivetrain/      | SwerveWheelConstants  | Hold the values needed for setting up swerve in your code base.                                                                                  |
| /frc/team6854/controllers/             | SwerveWheelDrive      | Controls the speed of the Swerve Modules since we didn't have enough of each motor controller so we had to make a class to control different types of motor controllers. |
| /frc/robot/output/commands/drivetrain/ | TeleopDrive           | The default command for swerve. It drives using ROC or FOC but always passes the Gyro angle just incase you need it for something else..         |

## Contributing

As of right now you must be a member of the team to contribute to this repository but that may change later.