package frc.robot;

import static org.junit.jupiter.api.Assertions.*;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.Filesystem;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import swervelib.SwerveDrive;

/**
 * Unit 2: Verifies that SwerveSubsystem declares the swerveDrive field, a getSwerveDrive()
 * getter, and that the constructor actually builds a SwerveDrive from the Unit 1 JSON files.
 *
 * <p>Reflection tests check field/method declarations without needing hardware. HAL simulation
 * tests instantiate the subsystem in WPILib's desktop simulation mode, which also depends on the
 * deploy/swerve JSON files from Unit 1 being filled in correctly.
 */
class Unit2SwerveSubsystemFieldsTest {

    // ── Reflection tests: field and method declarations ──────────────────────

    @Test
    @DisplayName("Unit 2: swerveDrive is declared as private final SwerveDrive")
    void swerveDriveField() throws NoSuchFieldException {
        Field f = SwerveSubsystem.class.getDeclaredField("swerveDrive");
        assertEquals(SwerveDrive.class, f.getType(), "swerveDrive must be of type SwerveDrive");
        assertTrue(Modifier.isPrivate(f.getModifiers()), "swerveDrive must be private");
        assertTrue(Modifier.isFinal(f.getModifiers()), "swerveDrive must be final");
    }

    @Test
    @DisplayName("Unit 2: SwerveSubsystem has exactly 1 SwerveDrive field")
    void exactlyOneSwerveDriveField() {
        Field[] fields = SwerveSubsystem.class.getDeclaredFields();
        long count = Arrays.stream(fields).filter(f -> f.getType().equals(SwerveDrive.class)).count();
        assertEquals(1, count, "SwerveSubsystem must have exactly 1 SwerveDrive field named 'swerveDrive'");
    }

    @Test
    @DisplayName("Unit 2: getSwerveDrive() is declared public and returns SwerveDrive")
    void getSwerveDriveMethodSignature() throws NoSuchMethodException {
        Method m = SwerveSubsystem.class.getDeclaredMethod("getSwerveDrive");
        assertTrue(Modifier.isPublic(m.getModifiers()), "getSwerveDrive must be public");
        assertEquals(SwerveDrive.class, m.getReturnType(), "getSwerveDrive must return SwerveDrive");
    }

    // ── HAL simulation tests: constructor behavior ───────────────────────────
    //
    // The subsystem is created once per test class (static @BeforeAll), not once per @Test, since
    // each module registers several simulated CAN devices (drive motor, angle motor, encoder) and
    // repeated construction can exhaust the HAL's simulated CAN device table.

    private static SwerveSubsystem subsystem;
    private static Exception constructionError;

    @BeforeAll
    static void initHalAndSubsystem() {
        assertTrue(HAL.initialize(500, 0), "WPILib HAL initialization failed — check that JNI native libs are on java.library.path");
        try {
            subsystem = new SwerveSubsystem(new File(Filesystem.getDeployDirectory(), "swerve"));
        } catch (Exception e) {
            constructionError = e;
        }
    }

    @Test
    @DisplayName("Unit 2: SwerveSubsystem constructor runs without throwing")
    void constructorDoesNotThrow() {
        assertNull(
                constructionError,
                "SwerveSubsystem constructor threw — check the constructor body, and that Unit 1's"
                        + " deploy/swerve JSON files are filled in and valid. Cause: " + constructionError);
    }

    @Test
    @DisplayName("Unit 2: getSwerveDrive() returns a non-null SwerveDrive after construction")
    void getSwerveDriveReturnsNonNull() {
        assertNotNull(subsystem, "SwerveSubsystem failed to construct — see the constructorDoesNotThrow test");
        assertNotNull(subsystem.getSwerveDrive(), "getSwerveDrive() must return the swerveDrive field initialized in the constructor");
    }
}
