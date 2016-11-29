package com.gatar.TreeClient.Controller;

import com.gatar.TreeClient.Domain.ChangeNodeValueDTO;
import com.gatar.TreeClient.Domain.MoveBranchDTO;
import com.gatar.TreeClient.Domain.NodeDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.HttpClientErrorException;

/**
 * Needs working Tree Manager WebAPI on http://localhost:8080
 * For change URL use in setUp ClientController.setServerULR method.
 */
public class ClientControllerIntegrationTest {

    final ClientController clientController = new ClientControllerImpl();

    //Create Nodes
    final NodeDTO rootDTO  = new NodeDTO(1,1);
    final NodeDTO node2DTO = new NodeDTO(2,3);
    final NodeDTO node3DTO = new NodeDTO(3,4);
    final NodeDTO node4DTO = new NodeDTO(4,4);
    final NodeDTO node5DTO = new NodeDTO(5,-5);
    final NodeDTO node6DTO = new NodeDTO(6,0);
    final NodeDTO node7DTO = new NodeDTO(7,0);
    final NodeDTO node8DTO = new NodeDTO(8,-15);
    final NodeDTO node9DTO = new NodeDTO(9,4);
    final NodeDTO node10DTO = new NodeDTO(10,2);
    final NodeDTO node11DTO = new NodeDTO(11,2);
    final NodeDTO node12DTO = new NodeDTO(12,-11);



    @Before
    public void setUp() throws Exception {
        clientController.setWebAPIDefaultTree();
        //Left
        rootDTO.getChildren().add(node2DTO);
        rootDTO.getChildren().add(node3DTO);
        node2DTO.getChildren().add(node4DTO);
        node2DTO.getChildren().add(node8DTO);
        node2DTO.getChildren().add(node9DTO);
        node8DTO.getChildren().add(node12DTO);
        //Right
        node3DTO.getChildren().add(node5DTO);
        node5DTO.getChildren().add(node6DTO);
        node6DTO.getChildren().add(node7DTO);
        node6DTO.getChildren().add(node10DTO);
        node10DTO.getChildren().add(node11DTO);
    }

    @Test
    public void setServerURL() throws Exception {

        //Try set incorrect server URL
        clientController.setServerURL("wrong url");
        Assert.assertFalse(clientController.isServerUriCorrect());

        //Try set incorrect server URL
        clientController.setServerURL("http://blablablablablkka.com");
        Assert.assertFalse(clientController.isServerUriCorrect());

        //Set correct server URL
        clientController.setServerURL("http://localhost:8080");
        Assert.assertTrue(clientController.isServerUriCorrect());
    }

    @Test(expected = HttpClientErrorException.class)
    public void addLeaf() throws Exception {

        //Add leaf to existing node
        clientController.addLeaf("11");
        NodeDTO node13DTO = new NodeDTO(13,4);
        node11DTO.getChildren().add(node13DTO);
        Assert.assertEquals(0,rootDTO.compareTo(clientController.getTree()));

        //Add leaf to not existing node
        clientController.addLeaf("125");
    }

    @Test(expected = HttpClientErrorException.class)
    public void changeValue() throws Exception {

        ChangeNodeValueDTO changeNodeValueDTO = new ChangeNodeValueDTO();

        //Change value of existing node
        changeNodeValueDTO.setNewValue(10);
        changeNodeValueDTO.setNodeId(10);
        clientController.changeValue(changeNodeValueDTO);
        node10DTO.setValue(10);
        node11DTO.setValue(10);
        Assert.assertEquals(0,rootDTO.compareTo(clientController.getTree()));

        //Change value of not existing node
        changeNodeValueDTO.setNodeId(15);
        clientController.changeValue(changeNodeValueDTO);
    }

    @Test(expected = HttpClientErrorException.class)
    public void switchBranch() throws Exception {

        MoveBranchDTO moveBranchDTO = new MoveBranchDTO();

        //Move existing branch to existing node
        moveBranchDTO.setNodeId(10);
        moveBranchDTO.setTargetNodeId(12);
        clientController.switchBranch(moveBranchDTO);
        node6DTO.getChildren().remove(node10DTO);
        node12DTO.getChildren().add(node10DTO);
        node11DTO.setValue(-20);
        Assert.assertEquals(0,rootDTO.compareTo(clientController.getTree()));

        //Move branch with one non existing node
        moveBranchDTO.setNodeId(66);
        clientController.switchBranch(moveBranchDTO);
    }

    @Test(expected = HttpClientErrorException.class)
    public void removeNodeWithoutChildren() throws Exception {

        //Remove existing, non-leaf node
        clientController.removeNodeWithoutChildren("6");
        node5DTO.getChildren().clear();
        node5DTO.getChildren().add(node7DTO);
        node5DTO.getChildren().add(node10DTO);
        Assert.assertEquals(0,rootDTO.compareTo(clientController.getTree()));


        //Remove non existing node
        clientController.removeNodeWithoutChildren("19");
    }

    @Test(expected = HttpClientErrorException.class)
    public void removeNodeWithChildren() throws Exception {

        //Remove existing, non-leaf node
        clientController.removeNodeWithChildren("5");
        node3DTO.getChildren().clear();
        node3DTO.setValue(1);
        Assert.assertEquals(0,rootDTO.compareTo(clientController.getTree()));

        //Remove non existing node
        clientController.removeNodeWithChildren("19485");
    }

    @Test
    public void getTree() throws Exception {

        //Compare tree from WebAPI with the same tree
        Assert.assertEquals(0,rootDTO.compareTo(clientController.getTree()));
    }

    @Test
    public void checkH2Database() throws Exception{

        //Save database, change it, load from database and check is it the same with previous
        clientController.removeNodeWithChildren("11");
        node10DTO.getChildren().clear();
        node10DTO.setValue(0);
        clientController.saveTreeInInternalDatabase();
        clientController.removeNodeWithoutChildren("2");

        //WebAPI tree was modified and test tree not, should return -1
        Assert.assertEquals(-1,rootDTO.compareTo(clientController.getTree()));

        //Load database before remove node 2, should be equal
        clientController.loadTreeFromInternalDatabase();
        Assert.assertEquals(0,rootDTO.compareTo(clientController.getTree()));

    }



}