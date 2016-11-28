package com.gatar.TreeClient.Domain;

/**
 * Domain object used for get nodes id's to move from one to another.
 */
public class MoveBranchDTO {

    public MoveBranchDTO() {
    }

    private Integer nodeId;

    private Integer targetNodeId;

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public Integer getTargetNodeId() {
        return targetNodeId;
    }

    public void setTargetNodeId(Integer targetNodeId) {
        this.targetNodeId = targetNodeId;
    }
}
