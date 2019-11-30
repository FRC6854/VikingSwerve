package frc.team6854.controllers;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class VikingSPX {

    private VictorSPX motor;

    /**
     * @param id the CAN ID for the Victor SPX
     * @param inverted is the motor inverted
     */
    public VikingSPX(int id, boolean inverted) {
        motor = new VictorSPX(id);

        motor.configFactoryDefault();

        motor.setInverted(inverted);
    }

    /**
     * @param id the CAN ID for the Victor SPX
     * @param master the master motor controller
     * @param inverted is the motor inverted
     */
    public VikingSPX(int id, VikingSRX master, boolean inverted) {
        motor = new VictorSPX(id);

        motor.configFactoryDefault();

        motor.follow(master.getTalonSRX());
        motor.setInverted(inverted);
    }

    public VictorSPX getVictorSPX() {
        return motor;
    }
}
