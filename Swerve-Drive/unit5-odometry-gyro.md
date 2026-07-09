# Unit 5: Odometry and Gyro

**Objective:** Implement `SwerveSubsystem`'s odometry and gyro helper methods:
`getPose`, `resetOdometry`, `zeroGyro`, `zeroGyroWithAlliance`, and `getHeading`.

**Estimated time:** 15 minutes

## What You'll Do

Open `src/main/java/frc/robot/subsystems/swervedrive/SwerveSubsystem.java` and replace the five
method bodies at the bottom of the file. Each one is a thin wrapper around the equivalent
`SwerveDrive` method:

| Method | Implementation |
|---|---|
| `getPose()` | `return swerveDrive.getPose();` |
| `resetOdometry(Pose2d initialHolonomicPose)` | `swerveDrive.resetOdometry(initialHolonomicPose);` |
| `zeroGyro()` | `swerveDrive.zeroGyro();` |
| `getHeading()` | `return getPose().getRotation();` |
| `zeroGyroWithAlliance()` | See below |

`zeroGyroWithAlliance()` needs a little more logic: if the robot is on the red alliance, zeroing
the gyro alone isn't enough — the robot's *starting* pose also needs to face 180° from blue's,
so call `zeroGyro()` and then `resetOdometry(...)` with the current translation and a 180°
rotation. If not on red alliance, just call `zeroGyro()`.

See the **Odometry and Pose Reset** section of the
[YAGSL Swerve Tutorial](../../docs/programming/yagsl_swerve_tutorial.md) for the full
`zeroGyroWithAlliance` example, including how to safely read the current alliance from
`DriverStation`.

## Why This Matters

- **Odometry** is how the robot tracks its field position between vision updates — it combines
  wheel encoder deltas with gyro heading.
- **`zeroGyroWithAlliance`** exists because both alliances start facing each other across the
  field — "zero" needs to mean something different depending which alliance you're on, or your
  field-oriented drive (Unit 3/4) will be backwards for half your matches.

## Verification

```bash
./gradlew test --tests "frc.robot.Unit5OdometryTest"
```

This checks each method's signature and return type via reflection.

## Next Step

That's the whole lesson! From here, drive the robot in simulation
(`WPILib: Simulate Robot Code` in VSCode) and confirm the modules point the direction you'd
expect before ever putting this code on a real robot. See the **Tuning and Debugging** section of
the [YAGSL Swerve Tutorial](../../docs/programming/yagsl_swerve_tutorial.md) if anything spins
the wrong way.
