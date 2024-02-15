package pramos.lab1;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class Stack<T> {
    
    private int size;
    private LinkedList<T> list;

    public Stack() {
        this.list = new LinkedList<T>();
    }

    public T pop() throws NoSuchElementException {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Stack is Empty!");
        }
        return this.list.removeFirst();
    }

    public int size() {
        return list.size();
    }

    public T peek() throws NoSuchElementException {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Stack is Empty!");
        }
        return this.list.getFirst();
    }

    public void push(T val) {
        list.addFirst(val);
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }


}
