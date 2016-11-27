package com.gatar.TreeClient.Controller;

import com.gatar.TreeClient.DataTransferObject.ChangeNodeValueDTO;
import com.gatar.TreeClient.DataTransferObject.MoveBranchDTO;
import com.gatar.TreeClient.DataTransferObject.NodeDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


public class ClientControllerImpl implements ClientController {

    private String serverURL = "http://localhost:8080";
    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public void setServerURL(String newURL) {
        serverURL = newURL;
    }

    @Override
    public NodeDTO getTree() {
        final String uri = serverURL + URL_GET_TREE;
        ResponseEntity<NodeDTO> tree = restTemplate.getForEntity(uri, NodeDTO.class);

        return tree.getBody();
    }

    @Override
    public void switchBranch(MoveBranchDTO coordinates) {
        final String uri = serverURL + URL_SWITCH_BRANCH;

        HttpEntity<MoveBranchDTO> requestSwitch = new HttpEntity<>(coordinates);
        restTemplate.postForEntity(uri,requestSwitch,Void.class);
    }

    @Override
    public void addLeaf(String nodeId) {
        final String uri = serverURL + URL_ADD_LEAF + nodeId ;

        restTemplate.getForEntity(uri,Void.class);
    }

    @Override
    public void removeNodeWithChildren(String nodeId) {
        final String uri = serverURL + URL_REMOVE_NODE_WITH_CHILDREN + nodeId ;

        restTemplate.getForEntity(uri,Void.class);
    }

    @Override
    public void removeNodeWithoutChildren(String nodeId) {
        final String uri = serverURL + URL_REMOVE_NODE_WITHOUT_CHILDREN + nodeId;

        restTemplate.getForEntity(uri,Void.class);
    }

    @Override
    public void changeValue(ChangeNodeValueDTO newValue) {
        final String uri = serverURL + URL_CHANGE_VALUE;

        HttpEntity<ChangeNodeValueDTO> requestSwitch = new HttpEntity<>(newValue);
        restTemplate.postForEntity(uri,requestSwitch,Void.class);
    }

    @Override
    public boolean isServerUriCorrect() {
        final String uri = serverURL;
        try{
            System.out.println("Connecting.....");
             restTemplate.getForEntity(uri, Void.class);
        }catch (Exception e){
            System.out.println("Server connection error: " + e.toString());
            return false;
        }
        System.out.println("Success!\n");
        return true;
    }
}
