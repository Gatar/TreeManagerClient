package com.gatar.TreeClient;

import com.gatar.TreeClient.View.ClientView;
import com.gatar.TreeClient.View.ClientViewImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TreeClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(TreeClientApplication.class, args);

		ClientView clientView = new ClientViewImpl();
		clientView.provideUserInterface();
	}
}
