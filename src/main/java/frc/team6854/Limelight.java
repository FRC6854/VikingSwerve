package frc.team6854;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class Limelight {

    private static Limelight instance = null;
    private NetworkTable limelight = NetworkTableInstance.getDefault().getTable("limelight");
  
    public static enum LightMode {
      DEFAULT,
      OFF,
      BLINK,
      ON
    }

    /**
     * Whether the limelight has any valid targets (0 or 1)
     * @return Returns true if vision target is found
     */
    public boolean validTargets(){
      double value = limelight.getEntry("tv").getDouble(0);
  
      if(value >= 1){
        return true;
      }
  
      return false;
    }
  
    /**
     * Horizontal Offset From Crosshair To Target 
     * (LL1: -27 degrees to 27 degrees | LL2: -29.8 to 29.8 degrees)
     * @return x value of target relative to the crosshair
     */
    public double targetX() {
      return limelight.getEntry("tx").getDouble(0);
    }
  
    /**
     * Vertical Offset From Crosshair To Target 
     * (LL1: -20.5 degrees to 20.5 degrees | LL2: -24.85 to 24.85 degrees)
     * @return y value of target relative to the crosshair
     */
    public double targetY() {
      return limelight.getEntry("ty").getDouble(0);
    }
  
    /**
     * Target Area (0% of image to 100% of image)
     * @return target area in percentage
     */
    public double targetA() {
      return limelight.getEntry("ty").getDouble(0);
    }
  
    /**
     * True active pipeline index of the camera (0 .. 9)
     * @return active pipeline currently used by the Limelight
     */
    public double getPipeline() {
      return limelight.getEntry("getpipe").getDouble(0);
    }

    /**
     * Sets the camMode to the selected value passed to the method (0 or 1)
     * @param value the value to set the camMode to
     */
    public void setDriverMode(boolean value) {
      if (value == true) {
        limelight.getEntry("camMode").setDouble(1);
      }
      else { 
        limelight.getEntry("camMode").setDouble(0);
      }
    }
  
    /**
     * Get the current camMode
     * @return 0 means normal and 1 means driver mode
     */
    public double driverMode() {
      return limelight.getEntry("camMode").getDouble(0);
    }

    /**
     * Sets limelight’s LED state
     * @param mode set the mode to either DEFAULT, OFF, BLINK, or ON
     */
    public void setLEDMode(LightMode mode) {
      switch(mode) {
        case DEFAULT:
          limelight.getEntry("ledMode").setNumber(0);
          break;
        case OFF:
          limelight.getEntry("ledMode").setNumber(1);
          break;
        case BLINK:
          limelight.getEntry("ledMode").setNumber(2);
          break;
        case ON:
          limelight.getEntry("ledMode").setNumber(3);
          break;
      }
    }
  
    /**
     * Set the vision pipeline
     */
    public void setPipeline(int pipelineID) {
      limelight.getEntry("pipeline").setNumber(pipelineID);
    }
  
    /**
     * Uses current targetY to calculate the distance to the target
     * @return the distance to the target in inches (estimation)
     */
    public double getDistanceFromTarget() {
      //double vertFOV = 49.7;
      //return 120/(Math.tan(Math.toRadians(vertFOV))*targetY())*4;
  
      double tapeHeight = 3; //inches
      double limelightHeight = 2; //inches
      return (tapeHeight-limelightHeight)/Math.tan(Math.toRadians(targetY()));
      // we know angle and opposite, so we can use tan to find the adjacent.
    }
  
    /**
     * Uses the FOV and the current targetX to calculate the X angle to the target
     * @return
     */
    public double getHorzAngle() {
      // isn't the "X angle to the target" just targetX()?
      double horzFOV = 59.6;
      return Math.atan(Math.tan(Math.toRadians(horzFOV))*targetX()/160);
    }

    public static Limelight getInstance () {
      if (instance == null)
        instance = new Limelight();
      return instance;
    }
  }