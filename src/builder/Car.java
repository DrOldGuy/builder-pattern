/**
 * 
 */
package builder;

import java.util.Objects;

/**
 * This demonstrates the builder design pattern using a fluent API. Defaults are in the Builder class. Everything is
 * defaulted except the color. In this simple example, all type choices are controlled by enums - it doesn't have to be
 * that way. Use it like this:
 * <pre>
 * Car car = Car.builder()
 *   .carType(CarType.SEDAN)
 *   .colorType(ColorType.MIDNIGHT_BLACK)
 *   .build();
 * </pre>
 * All values are defaulted except color.
 * 
 * @author dr.rob.hewitt
 *
 */
public class Car {
  private CarType carType;
  private DoorType doorType;
  private TopType topType;
  private DriveType driveType;
  private ColorType colorType;

  private Car(CarType carType, DoorType doorType, TopType topType, DriveType driveType, ColorType colorType) {
    this.carType = carType;
    this.doorType = doorType;
    this.topType = topType;
    this.driveType = driveType;
    this.colorType = colorType;

    if(Objects.isNull(colorType)) {
      throw new BuildException("You must select a color!");
    }
  }

  public CarType getCarType() {
    return carType;
  }

  public DoorType getDoorType() {
    return doorType;
  }

  public TopType getTopType() {
    return topType;
  }

  public DriveType getDriveType() {
    return driveType;
  }

  public ColorType getColorType() {
    return colorType;
  }

  public static Builder builder() {
    return new Builder();
  }

  @Override
  public String toString() {
    /* @formatter:off */
    return "Car: ["
        + "car type=" + carType
        + ", door type=" + doorType
        + ", top type=" + topType
        + ", drive type=" + driveType
        + ", color=" + colorType;
    /* @formatter:on */
  }

  public static class Builder {
    private CarType carType = CarType.COUPE;
    private DoorType doorType = DoorType.TWO_DOOR;
    private TopType topType = TopType.HARDTOP;
    private DriveType driveType = DriveType.TWO_WHEEL_DRIVE;
    private ColorType colorType;

    public Builder carType(CarType carType) {
      this.carType = carType;
      return this;
    }

    public Builder doorType(DoorType doorType) {
      this.doorType = doorType;
      return this;
    }

    public Builder topType(TopType topType) {
      this.topType = topType;
      return this;
    }

    public Builder driveType(DriveType driveType) {
      this.driveType = driveType;
      return this;
    }

    public Builder colorType(ColorType colorType) {
      this.colorType = colorType;
      return this;
    }

    public Car build() {
      return new Car(carType, doorType, topType, driveType, colorType);
    }
  }
}
