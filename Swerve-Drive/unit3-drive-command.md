# Unit 3: Field-Oriented Drive Command

**Objective:** Implement `driveFieldOriented(Supplier<ChassisSpeeds> velocity)` in
`SwerveSubsystem` so the robot can actually move.

**Estimated time:** 10 minutes

## What You'll Do

Open `src/main/java/frc/robot/subsystems/swervedrive/SwerveSubsystem.java` and replace the
`driveFieldOriented` method body.

## Instructions

1. The method must return a `Command` that, once per scheduler run, calls
   `swerveDrive.driveFieldOriented(velocity.get())`.
2. Use `this.run(...)` (inherited from `SubsystemBase`), not `Commands.run(...)` — the command
   must declare `SwerveSubsystem` as a requirement so the scheduler cancels it correctly.

See the **ChassisSpeeds Drive** section of the
[YAGSL Swerve Tutorial](../../docs/programming/yagsl_swerve_tutorial.md) for the exact pattern
(look for the `driveFieldOriented (Command)` example).

## Why This Matters

- **Field-oriented drive** means "forward" on the joystick always drives toward the same edge of
  the field, no matter which way the robot is currently facing — this is what makes swerve feel
  intuitive to drive.
- **`Supplier<ChassisSpeeds>`** lets the caller (in Unit 4, a `SwerveInputStream`) decide *how*
  the velocity is computed — from joysticks, from autonomous, from vision — while this method only
  needs to know how to apply it.

## Verification

```bash
./gradlew test --tests "frc.robot.Unit3DriveCommandTest"
```

This checks the method's signature and return type via reflection.

## Next Step

Proceed to **Unit 4: RobotContainer Wiring** to connect this command to a joystick.
