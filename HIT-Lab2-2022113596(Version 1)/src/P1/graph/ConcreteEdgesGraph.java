/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import java.util.*;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */

/**
 *图形的实现。
 ＊
 * <p>PS2说明:您必须使用提供的rep。
 */
public class ConcreteEdgesGraph implements P1.graph.Graph<String> {
    
    private final Set<String> vertices = new HashSet<>();//用于存放点
    private final List<Edge> edges = new ArrayList<>();//用于存放边

    // Abstraction function:
    //   AF(G) = vertices中点组成的集合V和edges中边组成的集合E构成的图(V,E).
    // Representation invariant:
    //   vertices' all weight of edges should be nonnegative,
    //   and all vertices appear in edges should be in this set
    // Safety from rep exposure:
    //   Set vertices and edges into private
    //   because vertices and edges are mutable, return them with defensive copies
    
    // TODO constructor

    public ConcreteEdgesGraph() {
    }

    // TODO checkRep
//    RI(G) = G中所有边的边权均大于0，且每条边的点都应该出现在点集中；
//    AF(G) = vertices中点组成的集合V和edges中边组成的集合E构成的图(V,E).
    private void checkRep() {
        for (Edge edge : edges) {
            int weight = edge.getWeight();
            assert weight > 0;
            String source = edge.getSource();
            assert vertices.contains(source);
            String target = edge.getTarget();
            assert vertices.contains(target);
        }

    }

    @Override public boolean add(String vertex) {
        if(vertices.contains(vertex)) {
            return false;
        } else {
            vertices.add(vertex);
            return true;
        }
    }
    
    @Override public int set(String source, String target, int weight) {
        List<Edge> addEdges = new ArrayList<>();
        Iterator<Edge> iterator = edges.iterator();
        int previousValue = 0;
        while(iterator.hasNext()) {
            Edge e = iterator.next();
            if(e.getSource().equals(source) && e.getTarget().equals(target)) {
                previousValue = e.getWeight();
                iterator.remove();//找到后将边删去
                //由于限定Edge是Immutable的，在修改边权时我们需要先将其删除再加回去
                if(weight > 0) {
                    addEdges.add(new Edge(source, target, weight));
                }
            }
        }
        edges.addAll(addEdges);
//新的边
        if(weight != 0) {
            if(!vertices.contains(source)) vertices.add(source);
            if(!vertices.contains(target)) vertices.add(target);
            edges.add(new Edge(source, target, weight));
        }
        return previousValue;
    }
    
    @Override public boolean remove(String vertex) {
        Iterator<Edge> iterator = edges.iterator();
        Iterator<String> v_iterator = vertices.iterator();
        while(iterator.hasNext()) {
            Edge e = iterator.next();
            if(e.getSource().equals(vertex) && e.getTarget().equals(vertex)) {
                iterator.remove();//找到后将边删去
            }
        }
        while(v_iterator.hasNext()) {
            String v = v_iterator.next();
            if(v.equals(vertex)) {
                v_iterator.remove();
                return true;
            }
        }
        return false;
    }
    
    @Override public Set<String> vertices() {
        return new HashSet<>(vertices);//进行防御式拷贝
    }
    
    @Override public Map<String, Integer> sources(String target) {
        Map<String, Integer> map = new HashMap<>();
        for(Edge e: edges) {
            if(e.getTarget().equals(target)) {
                map.put(e.getSource(), e.getWeight());
            }
        }
        return map;
    }
    
    @Override public Map<String, Integer> targets(String source) {
        Map<String, Integer> map = new HashMap<>();
        for(Edge e: edges) {
            if(e.getSource().equals(source)) {
                map.put(e.getTarget(), e.getWeight());
            }
        }
        return map;
    }
    
    // TODO toString()
    public String toString() {
        return String.format("(%s,%s)",vertices.toString(), edges.toString());
        //如果后面不调用toString()的话,传回的分别为Set<String>、List<String>类型
        // 与format的形式不匹配

    }

}

/**
 * TODO specification
 * Immutable.
 * This class is internal to the rep of ConcreteEdgesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */

/**
 * TODO规范
 *不可变的。
 这个类是ConcreteEdgesGraph的内部代表。
 ＊
 * <p>PS2指令:该类的规范和实现取决于你
 */
class Edge {
    // TODO fields
    private final String source, target;
    private final int weight;



    // Abstraction function:
    //   AF(source) = the source of edge
    //   AF(target) = the target of edge
    //   AF(weight) = the weight of edge
    // Representation invariant:
    //   weight >= 0 , source and target is not null
    // Safety from rep exposure:
    //   set source, target and weight to private

    // TODO constructor
    public Edge(String source, String target, int weight) {
        this.weight = weight;
        this.source = source;
        this.target = target;
        checkRep();
    }

    // TODO checkRep
    private void checkRep() {
        assert this.weight > 0;
    }
    // TODO methods
    public String getSource() {
        return this.source;
    }
    public String getTarget() {
        return this.target;
    }
    public int getWeight() {
        return this.weight;
    }
    // TODO toString()
    @Override
    public String toString() {
        return String.format("(%s,%s,%d)",source.toString(), target.toString(), weight);
    }
    
}
