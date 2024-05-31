/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.test.graph;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import P1.graph.Graph;
import org.junit.Test;

/**
 * Tests for instance methods of Graph.
 *
 * <p>PS2 instructions: you MUST NOT add constructors, fields, or non-@Test
 * methods to this class, or change the spec of {@link #emptyInstance()}.
 * Your tests MUST only obtain Graph instances by calling emptyInstance().
 * Your tests MUST NOT refer to specific concrete implementations.
 *
 */

/**
*测试Graph的实例方法。
 *
 * <p>PS2说明:你不能添加构造函数、字段或non-@Test
 * 方法，或者更改{@link #emptyInstance()}的规范。
 *你的测试必须只通过调用emptyInstance()获得图形实例。
 *你的测试不能涉及特定的具体实现。
*/
public abstract class GraphInstanceTest {
    
    // Testing strategy
    //   TODO
    
    /**
     * Overridden by implementation-specific test classes.
     * 
     * @return a new empty graph of the particular implementation being tested
     */

    /**
     *被特定于实现的测试类覆盖。
     *
     @返回 正在测试的特定实现的新空图
    */
    public abstract Graph<String> emptyInstance();
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testInitialVerticesEmpty() {
        // TODO you may use, change, or remove this test
        assertEquals("expected new graph to have no vertices",
                Collections.emptySet(), emptyInstance().vertices());
    }
    
    // TODO other tests for instance methods of Graph
    /**
     * Constructs a graph with 5 vertices named A,B,C,D,E and no edge
     */
    @Test
    public void testAdd() {
        Graph<String> graph = emptyInstance();//正常测试graph即可
        assertEquals(true, graph.add("A"));
        assertEquals(true, graph.add("B"));
        assertEquals(true, graph.add("C"));
        assertEquals(true, graph.add("D"));
        assertEquals(true, graph.add("E"));
        assertEquals(false,graph.add("A"));
        assertEquals("([A, B, C, D, E],[])", graph.toString());
    }
    /**
     * Constructs a graph with 5 vertices named A,B,C,D,E and no edge
     */
    @Test
    public void testset() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        graph.add("C");
        graph.add("D");
        assertEquals(0, graph.set("A", "B", 1));
        assertEquals(0, graph.set("B", "C", 1));
        assertEquals(0, graph.set("C", "D", 1));
        assertEquals(0, graph.set("D", "A", 1));
        assertEquals(0, graph.set("G", "A", 5));
        assertEquals(1, graph.set("A", "B", 6));
        assertEquals(6, graph.set("A", "B", 2));
        assertEquals(false,graph.add("G"));
    }

    /**
     * Constructs a graph with 4 vertices named A, B, C, D, and remove vertices A
     * B and non-added vertices G
     */
    @Test
    public void testRemove() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        graph.add("C");
        graph.add("D");
        assertEquals(true, graph.remove("A"));
        assertEquals(true, graph.remove("B"));
        assertEquals(false, graph.remove("G"));
    }

    /**
     * test the graph including the added vertices
     */
    @Test
    public void testVertices() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        graph.add("C");
        graph.add("D");
        assertEquals("[A, B, C, D]", graph.vertices().toString());
    }



    Map<String, Integer> expected = new HashMap<>();//用于存储图
    public Map<String, Integer> testexpected(String a, Integer b) {
         expected.put(a, b);
         return expected;
    }

    /**
     * test strategy that will or not return truly
     */
    @Test
    public void testSources(){
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        graph.add("C");
        graph.add("D");
        graph.add("E");
        graph.add("F");
        graph.set("A", "B", 10);
        graph.set("C", "B", 5);
        graph.set("D", "B", 7);
        graph.set("E", "A", 9);
        graph.set("F", "C", 4);
        graph.set("A", "C",15);

        testexpected("E",9);
        assertEquals(expected,  graph.sources("A"));
        expected.clear();
        testexpected("F", 4);
        testexpected("A", 15);
        assertEquals(expected,  graph.sources("C"));
        expected.clear();
    }
    /**
     * test strategy that will or not return truly
     */
    @Test
    public void testTargets(){
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        graph.add("C");
        graph.add("D");
        graph.add("E");
        graph.add("F");
        graph.set("A", "B", 10);
        graph.set("A", "C", 15);
        graph.set("C", "B", 5);
        graph.set("C", "F", 11);
        graph.set("D", "B", 7);
        graph.set("E", "A", 9);
        graph.set("F", "C", 4);

        testexpected("B", 10);
        testexpected("C", 15);
        assertEquals(expected, graph.targets("A"));
        expected.clear();
        testexpected("B", 7);
        assertEquals(expected,  graph.targets("D"));
        expected.clear();
        testexpected("B",5);
        testexpected("F", 11);
        assertEquals(expected,  graph.targets("C"));
        expected.clear();
    }


    
}
