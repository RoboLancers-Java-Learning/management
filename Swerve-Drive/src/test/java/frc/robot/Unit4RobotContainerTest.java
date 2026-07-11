package frc.robot;

import static org.junit.jupiter.api.Assertions.*;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import swervelib.SwerveInputStream;

/**
 * Unit 4: Verifies that RobotContainer declares the drivebase subsystem and a SwerveInputStream,
 * and wires up the default command in configureBindings().
 *
 * <p>Assumes Units 1-3 are complete, since constructing RobotContainer constructs SwerveSubsystem.
 */
class Unit4RobotContainerTest {

    // ── Reflection tests: field declarations ─────────────────────────────────

    @Test
    @DisplayName("Unit 4: RobotContainer declares a SwerveSubsystem field named drivebase")
    void drivebaseFieldDeclared() throws NoSuchFieldException {
        Field f = RobotContainer.class.getDeclaredField("drivebase");
        assertEquals(SwerveSubsystem.class, f.getType(), "drivebase must be of type SwerveSubsystem");
        assertTrue(Modifier.isFinal(f.getModifiers()), "drivebase must be final");
    }

    @Test
    @DisplayName("Unit 4: RobotContainer declares a SwerveInputStream field named driveAngularVelocity")
    void driveAngularVelocityFieldDeclared() throws NoSuchFieldException {
        Field f = RobotContainer.class.getDeclaredField("driveAngularVelocity");
        assertEquals(SwerveInputStream.class, f.getType(), "driveAngularVelocity must be of type SwerveInputStream");
    }

    // ── HAL simulation tests: default command ────────────────────────────────

    @BeforeAll
    static void initHal() {
        HAL.initialize(500, 0);
    }

    @Test
    @DisplayName("Unit 4: RobotContainer constructor completes without throwing")
    void robotContainerConstructsWithoutError() {
        assertDoesNotThrow(
                RobotContainer::new,
                "RobotContainer constructor threw — check that drivebase, driveAngularVelocity, and"
                        + " setDefaultCommand() are all wired up in configureBindings()");
    }

    @Test
    @DisplayName("Unit 4: drivebase has a default command set after RobotContainer construction")
    void defaultCommandIsSet() throws Exception {
        RobotContainer container = new RobotContainer();
        Field f = RobotContainer.class.getDeclaredField("drivebase");
        f.setAccessible(true);
        SwerveSubsystem drivebase = (SwerveSubsystem) f.get(container);

        Command defaultCmd = drivebase.getDefaultCommand();
        assertNotNull(
                defaultCmd,
                "drivebase must have a default command — call"
                        + " drivebase.setDefaultCommand(drivebase.driveFieldOriented(driveAngularVelocity)) in configureBindings()");
    }
}
