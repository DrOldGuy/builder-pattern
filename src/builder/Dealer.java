/**
 * 
 */
package builder;

/**
 * @author rob.hewitt
 *
 */
public class Dealer {

  /**
   * @param args
   */
  public static void main(String[] args) {
    /* @formatter:off */
    Car basic = Car.builder()
        .colorType(ColorType.FLASHY_RED)
        .build();
    /* @formatter:on */

    System.out.println("Basic=" + basic);
    
    /* @formatter:on */
    Car jeep = Car.builder()
        .carType(CarType.SUV)
        .colorType(ColorType.FLASHY_RED)
        .doorType(DoorType.FOUR_DOOR)
        .driveType(DriveType.FOUR_WHEEL_DRIVE)
        .topType(TopType.SOFTTOP)
        .build();
    /* @formatter:off */
    
    System.out.println("Jeep=" + jeep);
    
    try {
      Car.builder().build();
    }
    catch(BuildException e) {
      System.out.println(e.getMessage());
    }
  }

}
