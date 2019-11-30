package frc.team6854;

import edu.wpi.first.wpilibj.DriverStation.Alliance;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.DriverStation;

public class OI {
  private static OI instance = null;

  private DriverStation ds;
  private SerialPort arduino;

  private static int lastCommandArduino = 0;
  private boolean connected = false;

  public OI() {
    ds = DriverStation.getInstance();
    
    try {
      // Init the SerialPort on baud 9600
      arduino = new SerialPort(9600, SerialPort.Port.kUSB1);

      // Whenever the readString function recieves a \n it will return
      // less bytes than it requested from the arduino
      //arduino.enableTermination();

      // Set connected to be false
      connected = true;
    }
    catch (Exception e) {
      System.out.print("Failed to connect to Arduino: ");
      System.out.println(e.toString());
    }
  }

  public Alliance getAlliance() {
    return ds.getAlliance();
  }

  public void ledDataSerialPort(int number) {
    // Set a string variable to the number
    String dataWrite = Integer.toString(number);

    if ((lastCommandArduino != number) && (connected == true)) {
      // Set the last command to the command about to be sent
      lastCommandArduino = number;

      // Write the number to the Serial Channel
      arduino.writeString(dataWrite);

      // Since the output buffer is 8 bytes and we usually only print 2 bytes, we must flush the buffer to send the line
      // The limitation of this is that we can only send up to 99,999,999
      arduino.flush();

      // Read the current line of text in the Serial Channel
      arduino.readString();
    }
  }

  public static String getCurrentSystemTimeDate (boolean isFile) {
    DateTimeFormatter formatter;

    if (isFile) {
      formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss");
    }
    else {
      formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm.ss");
    }

    return formatter.format(LocalDateTime.now());
  }

  public static OI getInstance () {
    if (instance == null)
      instance = new OI();
    return instance;
  }
}
