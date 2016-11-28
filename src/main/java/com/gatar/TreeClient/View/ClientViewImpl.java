package com.gatar.TreeClient.View;

import com.gatar.TreeClient.Controller.ClientController;
import com.gatar.TreeClient.Domain.ChangeNodeValueDTO;
import com.gatar.TreeClient.Domain.MoveBranchDTO;
import com.gatar.TreeClient.Domain.NodeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Scanner;

/**
 * Class providing UI. Used for input/output data from console and check their correctness.
 */
@Service
public class ClientViewImpl implements ClientView{

    @Autowired
    ClientController clientController;


    @Override
    public void provideUserInterface() {
        clientController.setWebAPIDefaultTree();
        try {
            while (true) {
                drawTree(clientController.getTree());
                showMenu();
                System.out.println("Wybierz opcję: \n");
                switch (readNumberFromKeyboard()) {
                    case 1:
                        proceedAddNewLeaf();
                        break;
                    case 2:
                        proceedChangeNodeValue();
                        break;
                    case 3:
                        proceedMoveBranch();
                        break;
                    case 4:
                        proceedRemoveNodeWithChildren();
                        break;
                    case 5:
                        proceedRemoveNodeWithoutChildren();
                        break;
                    case 6:
                        proceedSetNewServerPath();
                        break;
                    default:
                        proceedShutdown();
                        break;
                }
            }

            //Only predictable error response from TreeManager is NOT_ACCEPTABLE in case of send non-existing
            //node Id. It's handled here, but better would be handle each Controller method itself separately.
        }catch (HttpClientErrorException e){
            if(e.getStatusCode().equals(HttpStatus.NOT_ACCEPTABLE)){
                System.out.println("ERROR: Node with inputed number/numbers not exist! Process canceled.\n");
            } else {
                System.out.println("ERROR: Server response " + e.toString());
            }
            pressEnterToContinue();
            provideUserInterface();
        }
    }

    private void showMenu(){
        String menu = "\n----Tree Client----\n" +
                "1 - Add new leaf\n" +
                "2 - Change node value\n" +
                "3 - Move branch\n" +
                "4 - Remove node with children\n" +
                "5 - Remove node without children\n" +
                "6 - Set new server path\n" +
                "7up - Exit\n" +
                "-------------------\n";
        System.out.println(menu);
    }

    private void proceedAddNewLeaf(){
        System.out.println("Input id of node for create new leaf");
        clientController.addLeaf(readNumberFromKeyboard().toString());
    }

    private void proceedChangeNodeValue(){
        ChangeNodeValueDTO nodeNewValues = new ChangeNodeValueDTO();

        System.out.println("Input id of node for change value:");
        nodeNewValues.setNodeId(readNumberFromKeyboard());

        System.out.println("Input new value:");
        nodeNewValues.setNewValue(readNumberFromKeyboard());

        clientController.changeValue(nodeNewValues);
    }

    private void proceedMoveBranch(){
        MoveBranchDTO coordinates = new MoveBranchDTO();

        System.out.println("Input id of node for move:");
        coordinates.setNodeId(readNumberFromKeyboard());

        System.out.println("Input id of target node:");
        coordinates.setTargetNodeId(readNumberFromKeyboard());

        clientController.switchBranch(coordinates);
    }

    private void proceedRemoveNodeWithChildren(){
        System.out.println("Input id of node for remove:");
        clientController.removeNodeWithChildren(readNumberFromKeyboard().toString());
    }

    private void proceedRemoveNodeWithoutChildren(){
        System.out.println("Input id of node for remove:");
        clientController.removeNodeWithoutChildren(readNumberFromKeyboard().toString());
    }

    private void proceedSetNewServerPath(){
        do{
            System.out.println("Please input new path: (confirm twice Enter)");
            String newServerPath = readNewServerPath();
            clientController.setServerURL(newServerPath);
        }while(!clientController.isServerUriCorrect());
    }

    private void proceedShutdown(){
        System.exit(1);
    }

    /**
     * Read from console new server path as String and check it correctness until valid with regex.
     * @return String with data
     */
    private String readNewServerPath(){
        Scanner sc = new Scanner(System.in);
        String path;
        String correctPathRegex = "^(http:\\/\\/).+[^\\/]";

        do{
            path = sc.nextLine();
            if(!path.matches(correctPathRegex)) System.out.println("Server path must start with 'http://'");
            while(!sc.hasNextLine()) sc.next();
        }while(!path.matches(correctPathRegex));

        return path;
    }

    /**
     * Check is on console valid integer and read it.
     * @return Integer value from console
     */
    private Integer readNumberFromKeyboard(){
        Scanner sc = new Scanner(System.in);
        int number;
            while (!sc.hasNextInt()) {
                System.out.println("That's not a number!");
                sc.next();
            }
            number = sc.nextInt();

        return number;
    }

    private void drawTree(NodeDTO root){
        root.print();
    }
    private void pressEnterToContinue()
    {
        System.out.println("Press enter to continue...");
        try{
            System.in.read();
        }
        catch(Exception e){

        }
    }
}
