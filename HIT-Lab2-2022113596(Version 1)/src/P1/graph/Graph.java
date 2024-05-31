/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import java.util.Map;
import java.util.Set;

/**
 * A mutable weighted directed graph with labeled vertices.
 * Vertices have distinct labels of an immutable type {@code L} when compared
 * using the {@link Object#equals(Object) equals} method.
 * Edges are directed and have a positive weight of type {@code int}.
 * 
 * <p>PS2 instructions: this is a required ADT interface.
 * You MUST NOT change the specifications or add additional methods.
 * 
 * @param <L> type of vertex labels in this graph, must be immutable
 */

/**
 * *一个带有标记顶点的可变加权有向图。
 * *顶点有一个不可变类型{@code L}的不同标签进行比较
 * *使用{@链接 对象#等于(对象) 等于}方法。
 * *边是定向的，并且具有{@code int}类型的正权重。
 * ＊
 * * <p>PS2指令:这是必需的ADT接口。
 * *您不得更改规格或添加额外的方法。
 * ＊
 * * @param <L>类型的顶点标签在这个图中，必须是不可变的
 */
public interface Graph<L> {
    
    /**
     * Create an empty graph.
     * 
     * @param <L> type of vertex labels in the graph, must be immutable
     * @return a new empty weighted directed graph
     */
    /**
     *创建一个空图形。
     ＊
     * @param <L>类型的顶点标签在图中，必须是不可变的
     * @返回 一个新的空加权有向图
     */
    public static <L> Graph<L> empty() {
        return new ConcreteEdgesGraph<L>();
    }
    
    /**
     * Add a vertex to this graph.
     * 
     * @param vertex label for the new vertex
     * @return true if this graph did not already include a vertex with the
     *         given label; otherwise false (and this graph is not modified)
     */
    /**
     *在图中添加一个顶点。
     ＊
     * 顶点 标签的新顶点
     * @返回 true如果这个图没有包含给定标签的顶点;(就是添加的顶点不是重复的)
     *否则为false(此图未被修改)
     */
    public boolean add(L vertex);
    
    /**
     * Add, change, or remove a weighted directed edge in this graph.
     * If weight is nonzero, add an edge or update the weight of that edge;
     * vertices with the given labels are added to the graph if they do not
     * already exist.
     * If weight is zero, remove the edge if it exists (the graph is not
     * otherwise modified).
     * 
     * @param source label of the source vertex
     * @param target label of the target vertex
     * @param weight nonnegative weight of the edge
     * @return the previous weight of the edge, or zero if there was no such
     *         edge
     */
    /**
     *在此图中添加，更改或删除加权有向边。
     *如果权重非零，则添加一条边或更新该边的权重;
     *带有给定标签的顶点将被添加至图中如果它们不存在的话
     *如果权值为零，则删除存在的边(图不存在除非另有修改)
     ＊
     @param源 顶点的源标签
     @param目标 顶点的目标标签
     @paramweight 边的非负权值
     @返回 前一个边的权重，如果没有这种边，则返回零
     */
    public int set(L source, L target, int weight);
    
    /**
     * Remove a vertex from this graph; any edges to or from the vertex are
     * also removed.
     * 
     * @param vertex label of the vertex to remove
     * @return true if this graph included a vertex with the given label;
     *         otherwise false (and this graph is not modified)
     */
    /**
     *从图中移除一个顶点;任何到该顶点或从该顶点出发的边也被删除
     *@param要移除顶点的顶点标签
     *@返回true如果这个图包含一个给定的标签的顶点;
     *否则为false(此图不修改)
     */
    public boolean remove(L vertex);
    
    /**
     * Get all the vertices in this graph.
     * 
     * @return the set of labels of vertices in this graph
     */
    /**
     *得到这个图中的所有顶点。
     ＊
     * @返回该图中顶点的标签集
     */
    public Set<L> vertices();
    
    /**
     * Get the source vertices with directed edges to a target vertex and the
     * weights of those edges.
     * 
     * @param target a label
     * @return a map where the key set is the set of labels of vertices such
     *         that this graph includes an edge from that vertex to target, and
     *         the value for each key is the (nonzero) weight of the edge from
     *         the key to target
     */

    /**
     *获取有向边的源顶点到目标顶点和
     *这些边的权值。
     ＊
     * @param目标标签
     * @返回一个映射，
     * 其中键集是顶点的标签集，
     * 使得该图包括从该顶点到目标的边，每个键的值是从键到目标的边的(非零)权值
     */
    public Map<L, Integer> sources(L target);
    
    /**
     * Get the target vertices with directed edges from a source vertex and the
     * weights of those edges.
     * 
     * @param source a label
     * @return a map where the key set is the set of labels of vertices such
     *         that this graph includes an edge from source to that vertex, and
     *         the value for each key is the (nonzero) weight of the edge from
     *         source to the key
     */
    /**
     *获取目标顶点与有向边从源顶点和
     *这些边的权值。
     ＊
     * @param源标签
     * @返回一个映射，其中键集是顶点标签的集合，如
     *这个图包含一条从源到顶点的边，并且
     *每个键的值是(非零)从边的权重
     *源到键
     */
    public Map<L, Integer> targets(L source);
    
}
