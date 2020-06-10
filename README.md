# Builder Design Pattern with Fluent Interface

The Builder Design Pattern allows you to create complex objects from a series of method calls that set attributes on the
built object. It avoids having a myriad of constructors that accept different attributes while still allowing for 
default values. For example:

```
public class Car {
  private CarType carType = CarType.COUPE;
  private DoorType doorType = DoorType.TWO_DOOR;
  private TopType topType = TopType.HARDTOP;
  private DriveType driveType = DriveType.TWO_WHEEL_DRIVE;
  private ColorType colorType;
  
  public Car(ColorType color) { ... }
  public Car(ColorType color, DoorType doors { ... }
  public Car(ColorType color, DoorType doors, TopType top) { ... }
}
```

This type of coding leads to constructor sprawl, which even with only five attributes can lead to a whole bunch of 
constructors (like 1 + 4! = 25 constructors). 

## Traditional builder pattern

A better way is to use the Builder Design Pattern. A typical explanation is to have a Builder interface that defines the 
operations, a concrete builder that implements the interface and produces a result, and a director that specifies the 
build order. So, you might have:

```
interface CarProcess {
  void buildChassis(CarType carType);
  void addDoors(DoorType doorType);
  ...
}

class Car {
  public setChassis(Chassis chassis) {}
  public addDoors(DoorType doorType) {}
}

class CarBuilder implements CarProcess {
  private Car car = new Car();

  public void buildChassis(CarType carType) { 
    Chassis chassis = carType == CarType.COUPE ? new CoupeChassis() ...
    car.setChassis();
  }
  
  public void addDoors(DoorType doorType) {
    Doors doors = doorType == FOUR_DOOR ? new FourDoors() : new TwoDoors();
    car.addDoors(doorType);
  }
  
  public Car getResult() { return car; } 
}

class JeepDirector { // Controls the build
  private CarBuilder builder;
  
  public Director(CarBuilder builder) {
    this.builder = builder;
  }
  
  public void buildCar() {
    builder.buildChassis(CarType.SUV);
    builder.addDoors(DoorType.FOUR_DOOR);
  }
}

// A client can then request a car
public class Showroom {
  public static void main(String args) {
    CarBuilder builder = new CarBuilder();
    Director director = new JeepDirector(builder);
    
    director.buildCar();
    Car myJeep = builder.getResult();
  }
}
```

What are we building? The Builder Design Pattern can be used to create complex objects built from a series of operations
that must be performed in a certain order. A car is a perfect example because you can't put the roof on if there is no
chassis, etc.

The Builder Design Pattern can also be used to construct data objects. In this case the object stores data and the data 
can be put in with no thought as to sequential ordering. In this case a director isn't really needed.

Also, if the object is just storing data, there isn't need of an interface, as an interface that contains only getters
or setters is silly.

## Simplify with fluent interface

The traditional builder pattern can be simplified by using a [fluent interface](https://martinfowler.com/bliki/FluentInterface.html). With a fluent
interface, you don't need the interface and the director is optional. The builder returns itself so that methods can be
chained together. If build ordering is important you can often achieve that in the builder without a director. By 
keeping the builder within the built object class in a static class, each type of built object has a separate builder.

```
class Car {
  private CarType carType;
  private DoorType doorType;
  
  private Car() {}
  private void setChassis(CarType carType) {}
  private void addDoors(DoorType doorType) {}
  
  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private CarType carType = CarType.COUPE;
    private DoorType doorType = DoorType.TWO_DOOR;
    
    public Builder carType(CarType carType) {
      this.carType = carType;
      return this;
    }
    
    public Builder doorType(DoorType doors) {
      this.doorType = doors;
    }
    
    // Builds in the correct order.
    public Car build() {
      Car car = new Car();
      car.setChassis(createChassisOfType(carType));
      car.addDoors(createDoorsOfType(doorType));
    }
  }
}

class Showroom {
  public static void main(String args) {
    // I want a Jeep, I can add parts in any order. The builder will sort it out.
    Car jeep = Car.builder()
        .doorType(DoorType.FOUR_DOOR)
        .carType(CarType.SUV)
        .build();
        
    // I don't care what kind of car - just use defaults
    Car car = Car.builder().build();
  }
}
```

As you can see, the fluent interface is much easier to read and use. If you want templates with different default 
settings, you can create different "directors" like this:

```
class Jeep {
  public static Car build() {
    return Car.builder()
        .doorType(DoorType.FOUR_DOOR)
        .carType(CarType.SUV)
        .build();
  }
}

class Showroom {
  public static void main(String args) {
    // I want a Jeep.
    Car jeep = Jeep.build();
  }
}
```

See the fuller example in builder.Dealer and builder.Car.