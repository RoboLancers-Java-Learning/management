# New Lesson: Swerve-Drive-Simulation

## Context

`Swerve-Drive` (5 units) teaches students to implement YAGSL swerve code, but stops once the
code compiles and passes reflection tests — it never has students actually run the robot. Unit
5's "Next Step" gestures at simulation (`WPILib: Simulate Robot Code`) but gives no instructions
for viewing the Sim GUI inside a headless devcontainer, and there's no way to drive the robot at
all without a physical Xbox controller plugged into the container.

This new lesson, **Swerve-Drive-Simulation**, closes that gap: students start from a *completed*
swerve implementation (the Swerve-Drive solution) and (1) learn to launch and observe the
simulator through the devcontainer's noVNC desktop, then (2) write a second input-command binding
that lets them drive the simulated robot from the keyboard, since a Codespace/devcontainer has no
real joystick. It follows the same repo-per-lesson pattern as `Basic-DriveTrain`, `GitHub-Basics`,
and `Swerve-Drive`: a top-level directory here in `management_2`, registered as a new choice in
`.github/workflows/setup-lesson.yml`, deployed to a fresh student repo by that workflow.

## New directory: `Swerve-Drive-Simulation/`

Scaffolding copied verbatim from `Swerve-Drive/` (same WPILib project, same vendordeps — YAGSL,
Phoenix6, REVLib, ReduxLib, ThriftyLib, WPILibNewCommands): `.githooks/pre-push`,
`.vscode/{launch.json,settings.json}`, `.wpilib/wpilib_preferences.json`,
`gradle/wrapper/*`, `gradlew`, `gradlew.bat`, `build.gradle`, `settings.gradle`, `.gitignore`,
`WPILib-License.md`, `vendordeps/*`. No `grading/` directory is needed — like `Basic-DriveTrain`,
both units grade via `./gradlew test`, not shell scripts.

### Starting source (`src/main/...`)

Unlike `Swerve-Drive`, this lesson's starting code is the **completed** solution, since the
point is to run it, not re-derive it:

- `src/main/deploy/swerve/**` — same JSON files, filled in per `Swerve-Drive`'s Unit 1 spec sheet.
- `src/main/java/frc/robot/subsystems/swervedrive/SwerveSubsystem.java` — all five methods from
  Swerve-Drive Units 2/3/5 implemented (field + constructor, `driveFieldOriented`, odometry/gyro
  helpers).
- `src/main/java/frc/robot/RobotContainer.java` — the Unit 4 Xbox wiring
  (`driveAngularVelocity` + `drivebase.driveFieldOriented(driveAngularVelocity)`) already in
  place, **plus** a new `TODO Unit 2` block (see below) for the keyboard binding this lesson
  adds.
- `Constants.java` — add `OperatorConstants.SIM_KEYBOARD_PORT = 1` (Xbox stays on port 0).

### Unit 1 — `unit1-run-simulation.md`: Launch and Observe the Simulator

No code changes. Objective: get comfortable running sim inside the devcontainer before wiring
any input.

- Run **WPILib: Simulate Robot Code** from the VSCode command palette.
- Open the Sim GUI via noVNC at `http://localhost:6080` (password `vscode`) — the shared
  devcontainer already forwards ports 6080/5901, this unit just documents using them.
- In the Sim GUI, find the `SwerveDrive`/`swerve` NetworkTables entries (published because
  `SwerveDriveTelemetry.verbosity` was set to `HIGH` in Unit 2 of Swerve-Drive) and confirm they
  exist, showing each module at zero velocity with no input source yet.
- **Verification:** a HAL simulation smoke test (`Unit1SimulationTest.java`, mirroring the
  existing HAL-sim tests in `Unit2SwerveSubsystemFieldsTest.java`) that boots `Robot` via
  `TimedRobot`'s test harness in simulation mode and asserts it doesn't throw — confirms the
  student's environment/project actually runs headless, which CI can check even though the GUI
  observation itself is manual.

### Unit 2 — `unit2-keyboard-drive.md`: Keyboard Drive Command

The real coding exercise, in `RobotContainer.java`, following the same
declare-field/build-input-stream/wire-default-command shape as Swerve-Drive Unit 4:

```java
private final CommandJoystick keyboardStick = new CommandJoystick(SIM_KEYBOARD_PORT);

SwerveInputStream driveKeyboard = SwerveInputStream.of(drivebase.getSwerveDrive(),
        () -> keyboardStick.getY() * -1,
        () -> keyboardStick.getX() * -1)
    .withControllerRotationAxis(() -> keyboardStick.getZ() * -1)
    .deadband(DEADBAND)
    .scaleTranslation(0.8)
    .allianceRelativeControl(true);
```

In `configureBindings()`, branch the default command on `RobotBase.isSimulation()` so the real
robot still uses the Xbox controller and only sim uses the keyboard stick:

```java
if (RobotBase.isSimulation()) {
  drivebase.setDefaultCommand(drivebase.driveFieldOriented(driveKeyboard));
} else {
  drivebase.setDefaultCommand(drivebase.driveFieldOriented(driveAngularVelocity));
}
```

Instructions also cover mapping "Keyboard 0" to system joystick slot 1 in the Sim GUI's
Joysticks tab (matching `SIM_KEYBOARD_PORT`), since without that assignment `keyboardStick` reads
all zeros.

- **Verification:** `Unit2KeyboardDriveTest.java`, reflection-based like
  `Unit4RobotContainerTest.java` — checks `RobotContainer` declares `keyboardStick`
  (`CommandJoystick`) and `driveKeyboard` (`SwerveInputStream`) fields.
- **Capstone (manual):** drive around the Sim GUI's Field2d display with the mapped keys, confirm
  field-oriented behavior and that modules point the expected direction — this replaces the
  vague pointer currently at the bottom of Swerve-Drive Unit 5.

## Update to `Swerve-Drive/unit5-odometry-gyro.md`

Replace the current "Next Step" section (which vaguely says to simulate and check module
directions with no devcontainer GUI instructions) with a pointer to the new lesson:

> That's the whole lesson! Proceed to **Swerve-Drive-Simulation** to run this code in the
> devcontainer's simulator and drive it with the keyboard before it ever goes on a real robot.

## `.github/workflows/setup-lesson.yml` changes

- Add `"Swerve-Drive-Simulation"` to the `lesson_name` input's `options` list.
- Add a `setup-swerve-drive-simulation` job, copied from the existing `setup-swerve-drive` job
  (same steps: shared devcontainer, git hooks, CI workflow, WPILib project files), pointed at the
  new directory and with the grading-copy step removed (no `grading/` dir in this lesson).

## Verification

- `cd Swerve-Drive-Simulation && ./gradlew test` — both `Unit1SimulationTest` and
  `Unit2KeyboardDriveTest` pass against the starting (pre-TODO) code for Unit 1, and against a
  filled-in `keyboardStick`/`driveKeyboard` for Unit 2.
- `./gradlew spotlessCheck` and `./gradlew build` clean, matching the CI workflow other lessons
  run.
- Manually launch `WPILib: Simulate Robot Code` in the devcontainer, confirm the Sim GUI reachable
  at `localhost:6080`, map Keyboard 0 to slot 1, and drive the robot around to sanity-check the
  field-oriented behavior end-to-end.
