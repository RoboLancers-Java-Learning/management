// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

// TODO Unit 1: Add import for TalonFX using VSCode quick-fix (Ctrl+.)
// TODO Unit 2: Add imports for TalonFXConfiguration, NeutralModeValue, and DifferentialDrive
// TODO Unit 2: Add static import for DriveConstants: import static frc.robot.Constants.DriveConstants.*;
// TODO Unit 3: Add imports for Follower, MotorAlignmentValue, and InvertedValue
import edu.wpi.first.wpilibj2.command.Command;
import java.util.function.DoubleSupplier;

public class CANDriveSubsystem extends SubsystemBase {

  // TODO Unit 1: Declare four private final TalonFX fields:
  //   leftLeader, leftFollower, rightLeader, rightFollower

  // TODO Unit 2: Declare a private final DifferentialDrive field named drive (above the constructor)

  /** Creates a new CANDriveSubsystem. */
  public CANDriveSubsystem() {
    // Placeholder — replace this entire constructor body in Units 2 and 3.
    // This throw allows the class to compile while the final fields are unassigned.
    // See unit2-constructor-constants.md and unit3-arcade-drive-factory.md for instructions.
    throw new UnsupportedOperationException("CANDriveSubsystem not yet implemented — complete Units 2 and 3");
  }

  // TODO Unit 3: Replace this stub with your implementation.
  // The method must return a Command that calls arcadeDrive using the two suppliers.
  // Reference: See unit3-arcade-drive-factory.md
  public Command driveArcade(DoubleSupplier speed, DoubleSupplier rotation) {
    throw new UnsupportedOperationException("driveArcade not yet implemented — complete Unit 3");
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run.
  }
}
