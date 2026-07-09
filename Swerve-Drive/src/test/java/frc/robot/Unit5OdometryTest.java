package frc.robot;

import static org.junit.jupiter.api.Assertions.*;

import edu.wpi.first.hal.HAL;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.Filesystem;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;
import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit 5: Verifies getPose, resetOdometry, zeroGyro, zeroGyroWithAlliance, and getHeading —
 * reflection checks signatures, and HAL simulation tests confirm the stubs'
 * UnsupportedOperationExceptions have been replaced with real implementations.
 *
 * <p>Assumes Units 1-2 are complete, since constructing SwerveSubsystem requires a working
 * deploy/swerve configuration.
 */
class Unit5OdometryTest {

    // ── Reflection tests: method signatures ──────────────────────────────────

    @Test
    @DisplayName("Unit 5: getPose() is public and returns Pose2d")
    void getPoseSignature() throws NoSuchMethodException {
        Method m = SwerveSubsystem.class.getDeclaredMethod("getPose");
        assertTrue(Modifier.isPublic(m.getModifiers()), "getPose must be public");
        assertEquals(Pose2d.class, m.getReturnType(), "getPose must return Pose2d");
    }

    @Test
    @DisplayName("Unit 5: resetOdometry(Pose2d) is public")
    void resetOdometrySignature() throws NoSuchMethodException {
        Method m = SwerveSubsystem.class.getDeclaredMethod("resetOdometry", Pose2d.class);
        assertTrue(Modifier.isPublic(m.getModifiers()), "resetOdometry must be public");
    }

    @Test
    @DisplayName("Unit 5: zeroGyro() is public")
    void zeroGyroSignature() throws NoSuchMethodException {
        Method m = SwerveSubsystem.class.getDeclaredMethod("zeroGyro");
        assertTrue(Modifier.isPublic(m.getModifiers()), "zeroGyro must be public");
    }

    @Test
    @DisplayName("Unit 5: zeroGyroWithAlliance() is public")
    void zeroGyroWithAllianceSignature() throws NoSuchMethodException {
        Method m = SwerveSubsystem.class.getDeclaredMethod("zeroGyroWithAlliance");
        assertTrue(Modifier.isPublic(m.getModifiers()), "zeroGyroWithAlliance must be public");
    }

    @Test
    @DisplayName("Unit 5: getHeading() is public and returns Rotation2d")
    void getHeadingSignature() throws NoSuchMethodException {
        Method m = SwerveSubsystem.class.getDeclaredMethod("getHeading");
        assertTrue(Modifier.isPublic(m.getModifiers()), "getHeading must be public");
        assertEquals(Rotation2d.class, m.getReturnType(), "getHeading must return Rotation2d");
    }

    // ── HAL simulation tests: runtime behavior ───────────────────────────────

    private static SwerveSubsystem subsystem;

    @BeforeAll
    static void initHalAndSubsystem() {
        HAL.initialize(500, 0);
        subsystem = new SwerveSubsystem(new File(Filesystem.getDeployDirectory(), "swerve"));
    }

    @Test
    @DisplayName("Unit 5: getPose() returns a non-null Pose2d")
    void getPoseReturnsNonNull() {
        assertNotNull(subsystem.getPose(), "getPose must return swerveDrive.getPose(), not throw");
    }

    @Test
    @DisplayName("Unit 5: resetOdometry(Pose2d) updates the pose returned by getPose()")
    void resetOdometryUpdatesPose() {
        Pose2d target = new Pose2d(3.0, 1.0, Rotation2d.fromDegrees(90));
        subsystem.resetOdometry(target);
        Pose2d actual = subsystem.getPose();
        assertEquals(target.getX(), actual.getX(), 1e-6, "resetOdometry must update the robot's X position");
        assertEquals(target.getY(), actual.getY(), 1e-6, "resetOdometry must update the robot's Y position");
    }

    @Test
    @DisplayName("Unit 5: zeroGyro() runs without throwing")
    void zeroGyroDoesNotThrow() {
        assertDoesNotThrow(subsystem::zeroGyro, "zeroGyro must call swerveDrive.zeroGyro(), not throw");
    }

    @Test
    @DisplayName("Unit 5: zeroGyroWithAlliance() runs without throwing")
    void zeroGyroWithAllianceDoesNotThrow() {
        assertDoesNotThrow(subsystem::zeroGyroWithAlliance, "zeroGyroWithAlliance must call zeroGyro() (and resetOdometry() on red alliance), not throw");
    }

    @Test
    @DisplayName("Unit 5: getHeading() matches getPose().getRotation()")
    void getHeadingMatchesPoseRotation() {
        Pose2d target = new Pose2d(0, 0, Rotation2d.fromDegrees(45));
        subsystem.resetOdometry(target);
        assertEquals(
                target.getRotation().getDegrees(),
                subsystem.getHeading().getDegrees(),
                1e-6,
                "getHeading must return getPose().getRotation()");
    }
}
