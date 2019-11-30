package frc.team6854.controllers;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;

public class SwerveWheelDrive {

    public enum SwerveWheelDriveType {
        TalonSRX,
        Spark,
        VictorSPX
    }

    SwerveWheelDriveType type;

    SpeedController controller;

    public SwerveWheelDrive(SwerveWheelDriveType type, int id, boolean inverted) {
        if (type == SwerveWheelDriveType.TalonSRX) {

            // Create TalonSRX object with the ID from the constructor
            WPI_TalonSRX drive = new WPI_TalonSRX(id);

            drive.configFactoryDefault();

            // Invert the motor depending on the inverted value
            drive.setInverted(inverted);

            // WPI_TalonSRX can be passed into many different WPILib objects like the SpeedController
            controller = drive;

        } else if (type == SwerveWheelDriveType.Spark) {

            // Create Spark object with the ID from the constructor
            Spark drive = new Spark(id);

            // Invert the motor depending on the inverted value
            drive.setInverted(inverted);

            // Spark is a WPILib object so it can be passed into many different WPILib objects like the SpeedController
            controller = drive;

        } else if (type == SwerveWheelDriveType.VictorSPX) {

            // Create VictorSPX object with the ID from the constructor
            WPI_VictorSPX drive = new WPI_VictorSPX(id);

            drive.configFactoryDefault();

            // Invert the motor depending on the inverted value
            drive.setInverted(inverted);
            
            // WPI_VictorSPX can be passed into many different WPILib objects like the SpeedController
            controller = drive;
        }
    }

    public void setSpeed(double speed) {
        controller.set(speed);
    }
}
