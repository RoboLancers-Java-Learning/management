# Unit 1: Configure the Swerve Drive JSON Files

**Objective:** Fill in the YAGSL JSON configuration files in `src/main/deploy/swerve/` so they
match the spec sheet below for a fictional practice robot, "RoboLancers Practice Swerve Bot."

**Estimated time:** 20 minutes

## What You'll Do

YAGSL reads your robot's hardware layout from JSON files instead of hardcoded Java constants.
Every file in `src/main/deploy/swerve/` currently has placeholder `0` values — replace each one
using the spec sheet below. Do not change anything else in these files (motor types, `inverted`
flags, and the PIDF/controller tuning files are already correct).

## Spec Sheet: RoboLancers Practice Swerve Bot

**IMU** — `swervedrive.json`

| Property | Value |
|---|---|
| Type | `pigeon2` |
| CAN ID | `13` |

**Physical properties** — `modules/physicalproperties.json` (`conversionFactors`)

| Property | Value |
|---|---|
| Wheel diameter (inches) | `4` |
| Drive gear ratio | `6.75` |
| Angle gear ratio | `12.8` |

**Modules** — `modules/frontleft.json`, `frontright.json`, `backleft.json`, `backright.json`.
`location.front`/`location.left` are inches from the robot's center; positive front is toward
the front bumper, positive left is toward the left side.

| Module | Drive CAN ID | Angle CAN ID | CANcoder CAN ID | Encoder Offset (deg) | front | left |
|---|---|---|---|---|---|---|
| frontleft | 1 | 2 | 9 | -63.4 | 12 | 12 |
| frontright | 3 | 4 | 10 | 12.3 | 12 | -12 |
| backleft | 5 | 6 | 11 | 171.9 | -12 | 12 |
| backright | 7 | 8 | 12 | -100.2 | -12 | -12 |

## Instructions

1. Open `src/main/deploy/swerve/swervedrive.json` and set `imu.id` to `13`.
2. Open `src/main/deploy/swerve/modules/physicalproperties.json` and set
   `conversionFactors.drive.diameter`, `conversionFactors.drive.gearRatio`, and
   `conversionFactors.angle.gearRatio` from the physical properties table above.
3. For each of the four module files, set `drive.id`, `angle.id`, `encoder.id`,
   `absoluteEncoderOffset`, `location.front`, and `location.left` from the modules table above.

> **Note:** JSON doesn't care whether you write `4` or `4.0` — both parse to the same number.
> Leave `inverted`, `canbus`, `type`, `currentLimit`, `rampRate`, `wheelGripCoefficientOfFriction`,
> and `optimalVoltage` untouched.

See the **Configuration Steps** section of the
[YAGSL Swerve Tutorial](../../docs/programming/yagsl_swerve_tutorial.md) for what each of these
JSON files means and how YAGSL uses them.

## Why This Matters

- **JSON over hardcoded constants** — the same Java code works on every robot; only the JSON
  changes. This is the core idea behind YAGSL (and most swerve libraries).
- **CAN IDs must be unique** — no two devices on the same CAN bus can share an ID.
- **Absolute encoder offsets** — correct for how each CANcoder happens to be mounted on its
  module, so "zero" always means the wheel is pointing straight forward on the robot.

## Verification

Run the Unit 1 grading check from the project root:

```bash
bash grading/check_swerve_config.sh
```

Every line should print `PASS`. If something prints `FAIL`, it tells you which file, which field,
and what value it expected.

## Next Step

Proceed to **Unit 2: SwerveSubsystem Field and Constructor** to parse these JSON files into a
working `SwerveDrive` object.
