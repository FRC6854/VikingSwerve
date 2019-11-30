package frc.team6854;

import edu.wpi.first.wpilibj.XboxController;

public class Controller {
    
    private XboxController controller;

    public Controller(int port) {
        controller = new XboxController(port);
    }

    public double getDriverLeftStickY() {
        return controller.getRawAxis(1) * -1;
      }
    
      public double getDriverLeftStickX() {
        return controller.getRawAxis(0);
      }
    
      public double getDriverRightStickX() {
        return controller.getRawAxis(4);
      }
    
      public double getDriverRightStickY() {
        return controller.getRawAxis(5);
      }
    
      public double getDriverLTrigger() {
        return controller.getRawAxis(2);
      }
    
      public double getDriverRTrigger() {
        return controller.getRawAxis(3);
      }
    
      public boolean getDriverLBumperPressed(){
        return controller.getRawButtonPressed(5);
      }
    
      public boolean getDriverLBumper(){
        return controller.getRawButton(5);
      }
    
      public boolean getDriverRBumperPressed(){
        return controller.getRawButtonPressed(6);
      }
    
      public boolean getDriverRBumper(){
        return controller.getRawButton(6);
      }
    
      public boolean getDriverAButtonPressed() {
        return controller.getAButtonPressed();
      }
    
      public boolean getDriverAButton() {
        return controller.getAButton();
      }
    
      public boolean getDriverBButtonPressed() {
        return controller.getBButtonPressed();
      }
    
      public boolean getDriverBButton() {
        return controller.getBButton();
      }
    
      public boolean getDriverXButtonPressed() {
        return controller.getXButtonPressed();
      }
    
      public boolean getDriverYButtonPressed() {
        return controller.getYButtonPressed();
      }
     
      public boolean getDriverStartButtonPressed() {
        return controller.getStartButtonPressed();
      }
    
      public boolean getDriverStartButton(){
        return controller.getStartButton();
      }
}
