// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.swervedrive;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import java.io.File;
import java.util.function.Supplier;

// TODO Unit 2: Add imports for SwerveDrive, SwerveParser, SwerveDriveTelemetry, and
//              TelemetryVerbosity (all in the swervelib.* packages), plus a static import for
//              frc.robot.Constants.MAX_SPEED.
//              See docs/programming/yagsl_swerve_tutorial.md section 5 (Code Setup and Integration).

public class SwerveSubsystem extends SubsystemBase {

  // TODO Unit 2: Declare a private final SwerveDrive field named swerveDrive.

  /**
   * Creates a new SwerveSubsystem, parsing the YAGSL JSON configuration files found in the given
   * deploy directory (see src/main/deploy/swerve).
   *
   * @param directory Directory of swerve drive config files.
   */
  public SwerveSubsystem(File directory) {
    // TODO Unit 2: Replace this entire constructor body.
    //   1. Set SwerveDriveTelemetry.verbosity before constructing the SwerveDrive (HIGH is fine).
    //   2. Build swerveDrive with: new SwerveParser(directory).createSwerveDrive(MAX_SPEED)
    //      Wrap the call in a try/catch that rethrows as a RuntimeException, since SwerveParser's
    //      createSwerveDrive() throws a checked IOException.
    // Reference: unit2-subsystem-constructor.md and the SwerveSubsystem constructor example in
    // docs/programming/yagsl_swerve_tutorial.md.
    throw new UnsupportedOperationException("SwerveSubsystem not yet implemented — complete Unit 2");
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run.
  }

  // TODO Unit 2: Add a public getter: public SwerveDrive getSwerveDrive() { return swerveDrive; }
  //              RobotContainer needs this in Unit 4 to build its SwerveInputStream.

  /**
   * Drive the robot given a chassis field-oriented velocity supplier. Used as the subsystem's
   * default command, driven by a {@code SwerveInputStream} in RobotContainer.
   *
   * @param velocity Supplier of the field-relative {@link ChassisSpeeds} to drive at.
   * @return a Command that continuously drives the robot using the supplied velocity.
   */
  public Command driveFieldOriented(Supplier<ChassisSpeeds> velocity) {
    // TODO Unit 3: Replace this stub with your implementation.
    // The command must call swerveDrive.driveFieldOriented(velocity.get()) once per scheduler run.
    // Reference: See unit3-drive-command.md
    throw new UnsupportedOperationException("driveFieldOriented not yet implemented — complete Unit 3");
  }

  // TODO Unit 5: Replace the stubs below with implementations.
  // Each is a thin wrapper around the equivalent SwerveDrive method.
  // Reference: unit5-odometry-gyro.md and the "Odometry and Pose Reset" section of
  // docs/programming/yagsl_swerve_tutorial.md.

  public Pose2d getPose() {
    throw new UnsupportedOperationException("getPose not yet implemented — complete Unit 5");
  }

  public void resetOdometry(Pose2d initialHolonomicPose) {
    throw new UnsupportedOperationException("resetOdometry not yet implemented — complete Unit 5");
  }

  public void zeroGyro() {
    throw new UnsupportedOperationException("zeroGyro not yet implemented — complete Unit 5");
  }

  public void zeroGyroWithAlliance() {
    throw new UnsupportedOperationException("zeroGyroWithAlliance not yet implemented — complete Unit 5");
  }

  public Rotation2d getHeading() {
    throw new UnsupportedOperationException("getHeading not yet implemented — complete Unit 5");
  }
}
