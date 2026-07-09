# Unit 2: SwerveSubsystem Field and Constructor

**Objective:** Declare the `SwerveDrive` field in `SwerveSubsystem` and initialize it in the
constructor by parsing the JSON files you filled in during Unit 1.

**Estimated time:** 15 minutes

## What You'll Do

Open `src/main/java/frc/robot/subsystems/swervedrive/SwerveSubsystem.java`.

## Instructions

1. Add the imports listed in the `TODO Unit 2` comment near the top of the file: `SwerveDrive`,
   `SwerveParser`, `SwerveDriveTelemetry`, `TelemetryVerbosity` (all under `swervelib.*` or
   `swervelib.telemetry.*`), and a static import for `frc.robot.Constants.MAX_SPEED`. VSCode's
   Quick Fix (`Ctrl+.`) can add most of these once you reference the types below.

2. Declare a `private final SwerveDrive swerveDrive;` field above the constructor.

3. Replace the constructor body:
   - Set `SwerveDriveTelemetry.verbosity = TelemetryVerbosity.HIGH;` **before** constructing the
     `SwerveDrive` — YAGSL uses this to decide how many NetworkTables entries to create.
   - Build the `SwerveDrive` with `new SwerveParser(directory).createSwerveDrive(MAX_SPEED)`,
     assigning the result to `swerveDrive`.
   - `createSwerveDrive` throws a checked `IOException` (it reads the JSON files from disk), so
     wrap the call in a `try { ... } catch (Exception e) { throw new RuntimeException(e); }`.

4. Add a public getter: `public SwerveDrive getSwerveDrive() { return swerveDrive; }`. You'll need
   this in Unit 4 to build a `SwerveInputStream` in `RobotContainer`.

See the **Creating the SwerveDrive Object** section of the
[YAGSL Swerve Tutorial](../../docs/programming/yagsl_swerve_tutorial.md) for the exact
constructor pattern.

## Why This Matters

- **`SwerveParser`** reads every JSON file in the deploy directory and builds the module,
  kinematics, and IMU objects for you — this is what makes YAGSL's code portable across robots.
- **Telemetry verbosity must be set before construction** because `SwerveDriveTelemetry` is a
  static field YAGSL reads while building each module's telemetry entries.

## Verification

Since `SwerveSubsystem`'s constructor reads real JSON files, this unit is graded by reflection —
it checks that the field and constructor exist with the right types, without actually
instantiating a `SwerveDrive` (that requires the full hardware simulation stack). Run:

```bash
./gradlew test --tests "frc.robot.Unit2SwerveSubsystemFieldsTest"
```

## Next Step

Proceed to **Unit 3: Field-Oriented Drive Command** to make the robot actually drive.
