# Unit 4: RobotContainer Wiring

**Objective:** Wire a `SwerveInputStream` to the driver's joystick and bind it as
`SwerveSubsystem`'s default command.

**Estimated time:** 15 minutes

## What You'll Do

Open `src/main/java/frc/robot/RobotContainer.java`.

## Instructions

1. Add the static import noted in the `TODO Unit 4` comment: `import swervelib.SwerveInputStream;`

2. Declare the `driveAngularVelocity` field exactly as described in the TODO comment:
   ```java
   SwerveInputStream driveAngularVelocity = SwerveInputStream.of(drivebase.getSwerveDrive(),
           () -> driverXbox.getLeftY() * -1,
           () -> driverXbox.getLeftX() * -1)
       .withControllerRotationAxis(driverXbox::getRightX)
       .deadband(DEADBAND)
       .scaleTranslation(0.8)
       .allianceRelativeControl(true);
   ```

3. In `configureBindings()`, set the drivebase's default command:
   ```java
   drivebase.setDefaultCommand(drivebase.driveFieldOriented(driveAngularVelocity));
   ```

See the **Joystick Integration** section of the
[YAGSL Swerve Tutorial](../../docs/programming/yagsl_swerve_tutorial.md) for why the Y/X axes
are negated and what each `SwerveInputStream` builder method does.

## Why This Matters

- **`SwerveInputStream`** chains joystick reads, deadband filtering, scaling, and
  alliance-relative control into a single reusable `Supplier<ChassisSpeeds>` — exactly what
  `driveFieldOriented` (Unit 3) expects.
- **Axes are negated** because standard joysticks report negative Y when pushed forward; without
  the `-1`, pushing forward would drive the robot backward.
- **`allianceRelativeControl(true)`** flips "forward" for the red alliance, so "push stick away
  from you" always drives toward the opposing alliance's side of the field for both alliances.

## Verification

```bash
./gradlew test --tests "frc.robot.Unit4RobotContainerTest"
```

This checks (via reflection) that `RobotContainer` declares `drivebase` (a `SwerveSubsystem`) and
`driveAngularVelocity` (a `SwerveInputStream`).

## Next Step

Proceed to **Unit 5: Odometry and Gyro** to track the robot's position on the field.
