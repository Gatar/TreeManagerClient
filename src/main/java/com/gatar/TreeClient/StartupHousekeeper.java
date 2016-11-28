package com.gatar.TreeClient;

import com.gatar.TreeClient.View.ClientViewImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * Class used for run UI from {@link ClientViewImpl} bean.
 */
@Component
public class StartupHousekeeper implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    ClientViewImpl clientView;

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        clientView.provideUserInterface();
    }
}
