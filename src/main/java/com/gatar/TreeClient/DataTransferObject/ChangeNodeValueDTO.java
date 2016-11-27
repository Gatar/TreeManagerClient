package com.gatar.TreeClient.DataTransferObject;

/**
 * DataTransferObject object used for change node value basing on its id number.
 */
public class ChangeNodeValueDTO {

    public ChangeNodeValueDTO() {
    }

    private Integer nodeId;

    private Integer newValue;

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public Integer getNewValue() {
        return newValue;
    }

    public void setNewValue(Integer newValue) {
        this.newValue = newValue;
    }
}
