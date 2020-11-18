package dev.matta.utilities

import dev.matta.enums.PatchLevel
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SemVerTest extends GroovyTestCase {
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testSameVersion() {
        SemVer testLeft
        SemVer testRight
        testLeft = new SemVer("1.1.0");
        testRight = new SemVer("1.1.0");
        assertEquals(testLeft.compare(testRight), 0)
    }

    @Test
    void testPatchCompare() {
        SemVer testLeft
        SemVer testRight
        testLeft = new SemVer(1, 3, 0);
        testRight = new SemVer(1, 3, 1);
        assertTrue(testLeft.compare(testRight) == -1)
        assertTrue(testRight.compare(testLeft) == 1)
    }

    @Test
    void testMinorCompare() {
        SemVer testLeft
        SemVer testRight
        testLeft = new SemVer(1, 3, 0);
        testRight = new SemVer(1, 4, 0);
        assertTrue(testLeft.compare(testRight) == -1)
        assertTrue(testRight.compare(testLeft) == 1)
    }

    @Test
    void testMajorVersionCompare() {
        SemVer testLeft
        SemVer testRight
        testLeft = new SemVer(1, 3, 0);
        testRight = new SemVer(2, 3, 0);
        assertTrue(testLeft.compare(testRight) == -1)
        assertTrue(testRight.compare(testLeft) == 1)
    }

    @Test
    void testBumpVersionPatch() {
        SemVer normalTest = new SemVer("1.1.1");
        SemVer bumpTest = new SemVer("1.1.2");
        SemVer updatedBump = normalTest.bump(PatchLevel.PATCH);
        assertTrue(updatedBump.toString() == bumpTest.toString())
        assertTrue(updatedBump.toString() != normalTest.toString())
        assertTrue(updatedBump.getMajor() == 1)
        assertTrue(updatedBump.getMinor() == 1)
        assertTrue(updatedBump.getPatch() == 2)
    }

    @Test
    void testBumpVersionMinor() {
        SemVer normalTest = new SemVer("1.1.1");
        SemVer bumpTest = new SemVer("1.2.0");
        SemVer updatedBump = normalTest.bump(PatchLevel.MINOR);
        assertTrue(updatedBump.toString() == bumpTest.toString())
        assertTrue(updatedBump.toString() != normalTest.toString())
        assertTrue(updatedBump.getMajor() == 1)
        assertTrue(updatedBump.getMinor() == 2)
        assertTrue(updatedBump.getPatch() == 0)
    }

    @Test
    void testBumpVersionMajor() {
        SemVer normalTest = new SemVer("1.1.1");
        SemVer bumpTest = new SemVer("2.0.0");
        SemVer updatedBump = normalTest.bump(PatchLevel.MAJOR);
        assertTrue(updatedBump.toString() == bumpTest.toString())
        assertTrue(updatedBump.toString() != normalTest.toString())
        assertTrue(updatedBump.getMajor() == 2)
        assertTrue(updatedBump.getMinor() == 0)
        assertTrue(updatedBump.getPatch() == 0)
    }
}
