package frc.robot;

import static org.junit.jupiter.api.Assertions.*;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;
import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.function.Supplier;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit 3: Verifies the driveFieldOriented(Supplier&lt;ChassisSpeeds&gt;) command factory —
 * reflection checks its signature, and a HAL simulation test confirms the stub's
 * UnsupportedOperationException has been replaced with a real implementation.
 *
 * <p>Assumes Units 1-2 are complete, since constructing SwerveSubsystem requires a working
 * deploy/swerve configuration.
 */
class Unit3DriveCommandTest {

    // ── Reflection test: method signature ────────────────────────────────────

    @Test
    @DisplayName("Unit 3: driveFieldOriented(Supplier<ChassisSpeeds>) is declared public and returns Command")
    void driveFieldOrientedMethodSignature() throws NoSuchMethodException {
        Method m = SwerveSubsystem.class.getDeclaredMethod("driveFieldOriented", Supplier.class);
        assertTrue(Modifier.isPublic(m.getModifiers()), "driveFieldOriented must be public");
        assertEquals(Command.class, m.getReturnType(), "driveFieldOriented must return Command");
    }

    // ── HAL simulation test: runtime behavior ────────────────────────────────

    private static SwerveSubsystem subsystem;

    @BeforeAll
    static void initHalAndSubsystem() {
        HAL.initialize(500, 0);
        subsystem = new SwerveSubsystem(new File(Filesystem.getDeployDirectory(), "swerve"));
    }

    @Test
    @DisplayName("Unit 3: driveFieldOriented returns a non-null Command that requires SwerveSubsystem")
    void driveFieldOrientedReturnsCommand() {
        Supplier<ChassisSpeeds> zero = () -> new ChassisSpeeds(0, 0, 0);
        Command cmd = subsystem.driveFieldOriented(zero);
        assertNotNull(cmd, "driveFieldOriented must return a non-null Command — check that the stub's throw was replaced");
        assertTrue(
                cmd.getRequirements().contains(subsystem),
                "The Command returned by driveFieldOriented must require SwerveSubsystem — use 'this.run(...)' not 'Commands.run(...)'");
    }
}
