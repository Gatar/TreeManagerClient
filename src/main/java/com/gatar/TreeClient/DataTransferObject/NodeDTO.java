package com.gatar.TreeClient.DataTransferObject;

import java.util.LinkedList;

/**
 * DTO object of node used for transfer tree from WebAPI and print it on console.
 */
public class NodeDTO implements Comparable<NodeDTO>{

    private Integer nodeId;
    private Integer value;
    private LinkedList<NodeDTO> children = new LinkedList<>();

    public NodeDTO() {
    }

    public NodeDTO(Integer nodeId, Integer value) {
        this.value = value;
        this.nodeId = nodeId;
    }

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public LinkedList<NodeDTO> getChildren() {
        return children;
    }

    public void setChildren(LinkedList<NodeDTO> children) {
        this.children = children;
    }

    @Override
    public int compareTo(NodeDTO o) {
        int result=0;
        if(nodeId.equals(o.nodeId)){
            if(value.equals(o.value)) {
                if (children.size() == o.children.size()) {
                    for (int counter = 0; counter < children.size(); counter++) {
                        result = children.get(counter).compareTo(o.children.get(counter));
                    }
                } else return -1;
            } else return -1;
        } else return -1;
        return result;
    }

    @Override
    public String toString(){
        return "(id:"+nodeId+"|v:"+value+")";
    }

    /**
     * Provide print on console all tree from this node.
     */
    public void print() {
        print("", true);
    }

    private void print(String prefix, boolean isTail) {
        System.out.println(prefix + (isTail ? "└──── " : "├──── ") + toString());
        for (int i = 0; i < children.size() - 1; i++) {
            children.get(i).print(prefix + (isTail ? "    " : "│   "), false);
        }
        if (children.size() > 0) {
            children.get(children.size() - 1)
                    .print(prefix + (isTail ?"    " : "│   "), true);
        }
    }

}
