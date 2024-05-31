/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import org.w3c.dom.ls.LSOutput;

import java.util.*;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteVerticesGraph implements P1.graph.Graph<String> {

    private final List<Vertex> vertices = new ArrayList<>();

    // Abstraction function:
    //   AF(vertices) = 图中所有的vertices[i] | 0<= i <= vertices.sizes()
    // Representation invariant:
    //   no repeated vertices in this graph
    // Safety from rep exposure:
    //   put vertices private
    //   due to vertices is mutable, we should use defensive copies

    // TODO constructor

    public ConcreteVerticesGraph() {
    }

    // TODO checkRep
    public void checkRep() {
        Iterator<Vertex> iterator = vertices.iterator();
        List<Vertex> checkVertices = new ArrayList<>();
        int checkrepeat = 0;
        while (iterator.hasNext()) {
            Vertex vertex = iterator.next();
            if(!checkVertices.contains(vertex)) {
                checkVertices.add(vertex);
            }
            for (int i = 0; i < checkVertices.size(); i++) {
                Vertex check = checkVertices.get(i);
                for (int j = i + 1; j < checkVertices.size(); j++) {
                    if(check.equals(checkVertices.get(j))) {
                        checkrepeat = 1;
                    }
                }
            }
        }
        assert checkrepeat == 0;

    }

    List<Vertex> verticesToAddAll = new ArrayList<>();

    @Override
    public boolean add(String label) {
        // 检查是否已存在相同标签的顶点
        int index = 0;
        for (Vertex vertex : vertices) {
            if (vertex.getLabel().equals(label)) {
                index = 1;
                return false; // 标签已存在，返回 false
            }
        }

        // 如果标签不存在，则添加新的顶点
        if(index == 0){
            Vertex vertexToAdd = new Vertex(label);
            vertices.add(vertexToAdd);
        }
        checkRep();
        return true;
    }


    public void transform(List<Vertex> verticesToAddAll) {
        this.verticesToAddAll = verticesToAddAll;
        vertices.addAll(verticesToAddAll);
        checkRep();
    }


    @Override
    public int set(String source, String target, int weight) {
        Integer previousValue = 0;
        if (weight < 0) {
            return -1;
        } else if (weight > 0) {
            Iterator<Vertex> iterator = vertices.iterator();
            List<Vertex> addVertices = new ArrayList<>();
            List<Vertex> changeVertices = new ArrayList<>();
            List<Vertex> addsourceVertices = new ArrayList<>();
            List<Vertex> addtargetVertices = new ArrayList<>();
            int num = 0;//计数器
            while (iterator.hasNext()) {
                Vertex v = iterator.next();
                if (v.getLabel().equals(source)) {
                    Vertex sourceVertex = v;
                    if (sourceVertex.getAlltargets().containsKey(target)) {
                        previousValue = sourceVertex.getAlltargets().get(target);
                        v.removetargets(target);
                        v.addtarget(target, weight);
                        while(iterator.hasNext()){
                            Vertex v2 = iterator.next();
                            if(v2.getLabel().equals(source)){
                                v2.addsource(source, weight);
                            }
                        }
                        addVertices.add(v);
                        addsourceVertices.add(v);
                        return previousValue;
                    } else if (!sourceVertex.getAlltargets().containsKey(target)) {
                        Vertex targetVertex = new Vertex(target);
                        Vertex sourceVertex1 = new Vertex(source);
                        v.addtarget(target, weight);
                        addVertices.add(targetVertex);
                        addtargetVertices.add(v);
                        for (int i = 0; i < vertices.size(); i++) {
                            if(vertices.get(i).getLabel().equals(target)) {
                                vertices.get(i).addsource(source, weight);
                            }
                        }
                    }
                }
                else if (!(v.getAllsources().containsKey(source))) {
                    Vertex sourceVertex = new Vertex(source);
                    if(num == 0)
                    {
                        addVertices.add(sourceVertex);
                        num ++;
                    }
                    for(Vertex v2 :vertices) {
                        if(v2.getLabel().equals(source)){
                            addVertices.remove(sourceVertex);
                        }
                    }
                }

            }
            vertices.addAll(addVertices);
            return previousValue;
        } else if (weight == 0) {
            Iterator<Vertex> iterator = vertices.iterator();
            while (iterator.hasNext()) {
                Vertex v = iterator.next();
                if (v.getLabel().equals(source)) {
                    iterator.remove();
                }
                if (v.getLabel().equals(target)) {
                    iterator.remove();
                }
                return weight;
            }
        }
        checkRep();
        return 0;
    }

    @Override
    public boolean remove(String vertex) {
        for (Vertex vertex1 : vertices) {
            if (vertex1.getLabel().equals(vertex)) {
                vertices.remove(vertex1);
                return true;
            }
        }
        checkRep();
        return false;
    }

    @Override
    public Set<String> vertices() {
        List<String> vertices_copies = new ArrayList<>();
        for (Vertex vertex : vertices) {
            vertices_copies.add(vertex.getLabel());
        }
        Set<String> set = new HashSet<>(vertices_copies);
        checkRep();
        return set;
    }

    @Override
    public Map<String, Integer> sources(String target) {
        for (Vertex vertex : vertices) {
            if (vertex.getLabel().equals(target)) {
                return vertex.getAllsources();
            }
        }
        checkRep();
        return null;
    }

    @Override
    public Map<String, Integer> targets(String source) {
        for (Vertex vertex : vertices) {
            if (vertex.getLabel().equals(source)) {
                return vertex.getAlltargets();
            }
        }
        checkRep();
        return null;
    }

    // TODO toString()
    @Override
    public String toString() {
        Set<String> V = new TreeSet<>();
        Set<String> E = new TreeSet<>();
        for(Vertex vertex : vertices){
            V.add(vertex.getLabel().toString());
        }
        for(Vertex vertex : vertices) {
            if(!vertex.getAlltargets().isEmpty()){
                E.add(vertex.toString());
            }
        }
        checkRep();
        return "(" + V + "," + E + ")";
    }
}

/**
 * TODO specification
 * Mutable.
 * This class is internal to the rep of ConcreteVerticesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
    class Vertex {

        // TODO fields
        private final String label;//该顶点的名称
        private final Map<String, Integer> allsources = new HashMap<>();//该顶点的源点和边权值的集合
        private final Map<String, Integer> alltargets = new HashMap<>();//从该点导出边的集合


        // Abstraction function:
        //   AF(label) = 点的名字
        //   AF(allsources) = 指向该顶点的所有顶点和之间的权值
        //   AF(alltargets) = 该顶点导出边的所有顶点的集合和所导出边的权值
        // Representation invariant:
        //   Every edge's weight should be nonnegative
        // Safety from rep exposure:
        //   put label, allsources and alltargets into private

        // TODO constructor

        public Vertex(String label) {
            this.label = label;
        }

        // TODO checkRep
        private void checkRep() {
            for(Integer value : allsources.values()) {
                assert value > 0;
            }
            for(Integer value : alltargets.values()) {
                assert value > 0;
            }
        }
        // TODO methods

        public String getLabel() {
            return this.label;
        }
        public Map<String, Integer> getAllsources() {
            return this.allsources;
        }
        public Map<String, Integer> getAlltargets() {
            return this.alltargets;
        }
        public void addsource(String addvertex, int weight) {
            if(allsources.isEmpty())
            {
                allsources.put(addvertex, weight);
            }
//            for(Map.Entry<String, Integer> entry : allsources.entrySet()) {
//                String key = entry.getKey();
//                if(!(key.contains(addvertex)) && weight > 0) {
//                    allsources.put(addvertex, weight);
//                } else if((entry.getKey().contains(addvertex))){
//                    allsources.keySet().remove(addvertex);
//                    allsources.put(addvertex, weight);
//                } else if((entry.getValue().equals(0))){
//                    allsources.remove(entry.getKey());
//                }
//            }
            Set<Map.Entry<String, Integer>> entrySet = allsources.entrySet();
            for (int i = 0; i < entrySet.size(); i++) {
                Map.Entry<String, Integer> entry = entrySet.toArray(new Map.Entry[0])[i];
                String key = entry.getKey();
                if (!(key.contains(addvertex)) && weight > 0) {
                    allsources.put(addvertex, weight);
                } else if (key.contains(addvertex)) {
                    entrySet.remove(entry);
                    allsources.put(addvertex, weight);
                } else if (entry.getValue().equals(0)) {
                    entrySet.remove(entry);
                }
            }
            checkRep();
        }
        public void addtarget(String addvertex, int weight) {
            if(alltargets.isEmpty())
            {
                alltargets.put(addvertex, weight);
            }
            for(Map.Entry<String, Integer> entry : alltargets.entrySet()) {
                String key = entry.getKey();
                if(!(key.contains(addvertex)) && weight > 0) {
                    alltargets.put(addvertex, weight);
                } else if((entry.getKey().contains(addvertex))){
                    alltargets.keySet().remove(addvertex);
                    alltargets.put(addvertex, weight);
                } else if((entry.getValue().equals(0))){
                    alltargets.remove(entry.getKey());
                }
            }
            checkRep();
        }
        public void removesource(String delvertax) {
            for(Map.Entry<String, Integer> entry : allsources.entrySet()) {
                String key = entry.getKey();
                if(key.contains(delvertax)) {
                    allsources.remove(delvertax);
                }
            }
            checkRep();
        }
        public void removetargets(String delvertax) {
            for(Map.Entry<String, Integer> entry : alltargets.entrySet()) {
                String key = entry.getKey();
                if(key.contains(delvertax)) {
                    alltargets.remove(delvertax);
                }
            }
            checkRep();
        }

        // TODO toString()
//        @Override
//        public String toString() {
//            Iterator<Vertex>iterator = vertices.iterator();
//            Set<String> lables = new HashSet<>();
//            List<Integer> weights = new ArrayList<>();
//            if(iterator.hasNext()) {
//                Vertex vertex = iterator.next();
//                lables.add(vertex.getLabel());
//            }
//            return String.format("(%s,%s)",lables.toString(),weights.toString());
//        }
    }