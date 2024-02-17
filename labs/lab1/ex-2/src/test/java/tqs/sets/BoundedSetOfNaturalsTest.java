/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tqs.sets;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import tqs.sets.BoundedSetOfNaturals;

/**
 * @author ico0
 */
class BoundedSetOfNaturalsTest {
    private BoundedSetOfNaturals setA;
    private BoundedSetOfNaturals setB;
    private BoundedSetOfNaturals setC;
    private BoundedSetOfNaturals setD;


    @BeforeEach
    public void setUp() {
        setA = new BoundedSetOfNaturals(1);
        setB = BoundedSetOfNaturals.fromArray(new int[]{10, 20, 30, 40, 50, 60});
        setC = BoundedSetOfNaturals.fromArray(new int[]{50, 60});
        setD = new BoundedSetOfNaturals(2);
    }

    @AfterEach
    public void tearDown() {
        setA = setB = setC = setD = null;
    }

    @Test
    public void testAddElement() {

        //  Sucessfully add an element to a set
        setA.add(99);
        assertTrue(setA.contains(99), "add: added element not found in set.");
        assertEquals(1, setA.size());

        //  Wrongfully add an element to a full set
        assertThrows(IllegalArgumentException.class, () -> setB.add(11));
        assertFalse(setB.contains(11), "add: added element found in set.");
        assertEquals(6, setB.size(), "add: elements count not as expected.");

        //  Wrongfully add an element to a set that already contains said element
        setD.add(16);
        assertThrows(IllegalArgumentException.class, () -> setD.add(16));
        assertTrue(setD.contains(16), "add: added element already in set.");
        assertEquals(1, setD.size(), "add: elements count not as expected.");

        //  Wrongfully add an element to a set that is not valid
        assertThrows(IllegalArgumentException.class, () -> setD.add(-8));
        assertFalse(setD.contains(-8), "add: added element found in set.");
        assertEquals(1, setD.size(), "add: elements count not as expected.");
    }

    @Test
    public void testAddFromBadArray() {
        int[] elems = new int[]{10, -20, -30};

        // must fail with exception
        assertThrows(IllegalArgumentException.class, () -> setA.add(elems));
    }

    @Test
    public void testEquals() {
        //  Sucessfully compare the same object
        assertTrue(setA.equals(setA), "equals: same object is not detected.");
        
        //  Sucessfully compare to a null object
        assertFalse(setB.equals(null), "equals: null object is not detected.");

        //  Sucessfully compare different types of objects
        Integer x = 16;
        assertFalse(setC.equals(x), "equals: different object types is not detected.");
    }

    @Test
    public void testIntersects() {
        //  Sucessfully intersect two sets and give an output
        assertTrue(setC.intersects(setB), "intersect: unexpected values returned.");
        assertFalse(setB.intersects(setA), "intersect: unexpected values returned.");

        //  Sucessfully intersect the same set
        assertTrue(setA.intersects(setA), "intersect: same object is not detected.");

        //  Sucessfully intersect two sets and give an empty output
        assertFalse(setC.intersects(null), "intersect: null object is not detected.");
    }

    @Test
    public void testHashCode() {
        //  Simple test to verify if an hashcode is valid
        assertTrue(setB.hashCode() >= 0, "hashcode: invalid hashcode.");
    }

/*     @Disabled("TODO revise test logic")
    @Test
    public void testAddElement() {

        setA.add(99);
        assertTrue(setA.contains(99), "add: added element not found in set.");
        assertEquals(1, setA.size());

        setB.add(11);
        assertTrue(setB.contains(11), "add: added element not found in set.");
        assertEquals(7, setB.size(), "add: elements count not as expected.");
    }

    @Disabled("TODO revise to test the construction from invalid arrays")
    @Test
    public void testAddFromBadArray() {
        int[] elems = new int[]{10, -20, -30};

        // must fail with exception
        assertThrows(IllegalArgumentException.class, () -> setA.add(elems));
    } */


}
