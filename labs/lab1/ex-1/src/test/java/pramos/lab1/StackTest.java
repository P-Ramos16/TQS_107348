package pramos.lab1;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;


/**
 * Unit test for simple StackTest.
 */
public class StackTest 
{
    
    Stack<Integer> stack;

    @BeforeEach
    public void setup() {
        stack = new Stack<Integer>();
    }

    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue( true );
    }
    
    @DisplayName("Test isEmpty()")
    @Test
    public void testIsEmpty() {
        assertTrue(stack.isEmpty());
    }

    @DisplayName("Test size()")
    @Test
    public void testStartSize() {
        assertTrue(stack.size() == 0);
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertTrue(stack.size() == 3);
        assertTrue(!stack.isEmpty());

    }

    @DisplayName("Test push()")
    @Test
    public void testPush() {
        stack.push(16);
        stack.push(32);
        stack.push(64);
        assertTrue(stack.pop() == 64);
    }

    @DisplayName("Test size()")
    @Test
    public void testSize() {
        stack.push(8);
        assertTrue(stack.size() == 1);
    }

    @DisplayName("Test peek()")
    @Test
    public void testPeek() {
        stack.push(16);
        Integer size = stack.size();
        assertTrue(stack.peek() == 16);
        assertTrue(stack.size() == size);
    }

    @DisplayName("Test pop()")
    @Test
    public void testPop() {
        for (int i = 0; i < 10; i += 1) {
            stack.push(2 * i);
        }
        
        assertTrue(stack.size() == 10);

        Integer size = stack.size();
        for (int i = 0; i < 9; i += 1) {
            stack.pop();
        }

        assertTrue(stack.pop() == 0);
        assertTrue(stack.size() == 0);
    }


    @DisplayName("Test Peek Exception")
    @Test
    public void testPeekException() {
        try {
            stack.peek();
        }
        catch(NoSuchElementException e) {
            assertTrue(true);
            return;
        }
        catch(Exception e) {
            assertTrue(false);
            return;
        }

        assertTrue(false);
        
    }

    @DisplayName("Test Stack Simple")
    @Test
    public void testStackSimple() {
        assertTrue(stack.size() == 0);

        stack.push(16);
        assertTrue(stack.size() == 1);

        Integer val = stack.pop();
        assertTrue(val == 16);
        assertTrue(stack.size() == 0);
    }

    @DisplayName("Test Stack Multiple")
    @Test
    public void testStackMultiple() {
        stack.push(2);
        stack.push(0);
        stack.push(10);
        stack.push(16);

        Integer val = stack.pop();
        assertTrue(val == 16);
        assertTrue(stack.size() == 3);

        val = stack.peek();
        assertTrue(val == 10);
        assertTrue(stack.size() == 3);

        stack.pop();
        stack.pop();
        val = stack.peek();
        assertTrue(val == 2);

        stack.pop();
        assertTrue(stack.isEmpty());
    }
    
}
