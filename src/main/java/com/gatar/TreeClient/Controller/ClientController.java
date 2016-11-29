package com.gatar.TreeClient.Controller;

import com.gatar.TreeClient.Domain.ChangeNodeValueDTO;
import com.gatar.TreeClient.Domain.MoveBranchDTO;
import com.gatar.TreeClient.Domain.NodeDTO;

/**
 * Required methods used for connecting with TreeManager WebAPI.
 */
public interface ClientController {

    //URL for WebAPI Tree Manager
    String URL_GET_TREE = "/treemanager/getTree";
    String URL_SWITCH_BRANCH = "/treemanager/switchBranch";
    String URL_ADD_LEAF = "/treemanager/addLeaf/";
    String URL_REMOVE_NODE_WITH_CHILDREN = "/treemanager/removeWithChildren/";
    String URL_REMOVE_NODE_WITHOUT_CHILDREN = "/treemanager/removeWithoutChildren/";
    String URL_CHANGE_VALUE = "/treemanager/changeValue";
    String URL_SET_DEFAULT_TREE = "/treemanager/prepareForIntegrationTest";
    String URL_SAVE_TO_DATABASE = "/treemanager/saveToInternalDatabase";
    String URL_LOAD_FROM_DATABASE = "/treemanager/loadFromInternalDatabase";

    /**
     * Get all tree structure as JSON value.
     * @return NodeDTO containing tree root with references
     */
    NodeDTO getTree();

    /**
     *  Move branch to other place.
     * @param coordinates {@link MoveBranchDTO} object containing id of moved and target node
     */
    void switchBranch(MoveBranchDTO coordinates);

    /**
     * Add new leaf to selected node.
     * @param nodeId new parent node id
     */
    void addLeaf(String nodeId);

    /**
     * Delete selected node with all its children.
     * @param nodeId node for delete id
     */
    void removeNodeWithChildren(String nodeId);

    /**
     * Delete only selected node, their children are set as child of parent.
     * @param nodeId node for delete id
     */
    void removeNodeWithoutChildren(String nodeId);

    /**
     * Change value of node by its id.
     * @param newValue {@link ChangeNodeValueDTO} object containing id and new value for node
     */
    void changeValue(ChangeNodeValueDTO newValue);

    /**
     * Set user path to server, instead of default value.
     * @param newURL path to server
     */
    void setServerURL(String newURL);

    /**
     * Send get request to server address for check connection.
     * @return true - url correct, response HttpStatus.OK, false - url incorrect, response other than OK status
     */
    boolean isServerUriCorrect();

    /**
     * Set in TreeManager default tree, used for tests.
     */
    void setWebAPIDefaultTree();

    /**
     * Save tree structure into internal H2 database of WebAPI
     */
    void saveTreeInInternalDatabase();

    /**
     * Load to in-memory tree, structure saved in internal H2 database of WebAPI
     */
    void loadTreeFromInternalDatabase();
}
