// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;
import java.io.File;
import static frc.robot.Constants.OperatorConstants.*;

// TODO Unit 4: Add a static import for SwerveInputStream (import swervelib.SwerveInputStream;)

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final SwerveSubsystem drivebase = new SwerveSubsystem(
      new File(Filesystem.getDeployDirectory(), "swerve"));

  // The driver's controller
  private final CommandXboxController driverXbox = new CommandXboxController(
      DRIVER_CONTROLLER_PORT);

  // TODO Unit 4: Declare a SwerveInputStream field named driveAngularVelocity, built with
  //   SwerveInputStream.of(drivebase.getSwerveDrive(), () -> driverXbox.getLeftY() * -1, () -> driverXbox.getLeftX() * -1)
  //       .withControllerRotationAxis(driverXbox::getRightX)
  //       .deadband(DEADBAND)
  //       .scaleTranslation(0.8)
  //       .allianceRelativeControl(true);
  // Reference: See unit4-robotcontainer-wiring.md and the "Joystick Integration" section of
  // docs/programming/yagsl_swerve_tutorial.md.

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be
   * created via the {@link Trigger#Trigger(java.util.function.BooleanSupplier)}
   * constructor with an arbitrary predicate, or via the named factories in
   * {@link edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses
   * for {@link CommandXboxController Xbox}/
   * {@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller PS4}
   * controllers or
   * {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // TODO Unit 4: Set drivebase's default command to drivebase.driveFieldOriented(driveAngularVelocity)
    //              Reference: See unit4-robotcontainer-wiring.md
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // Return null for now (no autonomous routine)
    return null;
  }
}
