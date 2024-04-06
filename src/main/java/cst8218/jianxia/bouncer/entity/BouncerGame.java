package cst8218.jianxia.bouncer.entity;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.EJB;
import javax.ejb.Startup;


/**
 * BouncerGame is a singleton session bean which is responsible for managing all the logic of the game.
 * 
 * BouncerGame will continuously update the positions of all bouncers and save the changes to the database.
 * The game runs in a separate thread, with the advanceOneFrame method being called at a fixed rate.
 * CHANGE_RATE constant determines how many times advanceOneFrame is called every second.
 * 
 * @author zhangjianxia
 */
@Singleton
@Startup

public class BouncerGame {

    @EJB
    private BouncerFacade bouncerFacade; // Inject BouncerFacade
    
    // Define constants for game behavior
    private static final int CHANGE_RATE = 60; // Number of times advanceOneFrame method is called per second
    
    @PostConstruct
    public void go() {
        new Thread(new Runnable() {
            public void run() {
                // the game runs indefinitely
                while (true) {
                    //update all the bouncers and save changes to the database
                    List<Bouncer> bouncers = bouncerFacade.findAll();
                    for (Bouncer bouncer : bouncers) {
                        bouncer.advanceOneFrame();
                        bouncerFacade.edit(bouncer);
                    }
                    try {
                        // wake up roughly CHANGE_RATE times per second
                        Thread.sleep((long)(1.0/CHANGE_RATE*1000));
                    } catch (InterruptedException exception) {
                        exception.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
