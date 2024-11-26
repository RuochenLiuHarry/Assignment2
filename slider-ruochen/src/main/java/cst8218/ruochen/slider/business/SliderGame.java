/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
// File: cst8218.alice.slider.business.SliderGame.java
package cst8218.ruochen.slider.business;

import cst8218.ruochen.slider.entity.Slider;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.EJB;
import java.util.List;

/**
 * Singleton session bean responsible for progressing the game simulation by updating Sliders.
 */
@Singleton
@Startup
public class SliderGame {

    @EJB
    private SliderFacade sliderFacade;

    /**
     * Initializes the game loop upon bean creation.
     */
    @PostConstruct
    public void go() {
        new Thread(new Runnable() {
            public void run() {
                // The game runs indefinitely
                while (true) {
                    // Retrieve all Sliders from the database
                    List<Slider> sliders = sliderFacade.findAll();

                    // Update each Slider's state
                    for (Slider slider : sliders) {
                        slider.timeStep();
                        sliderFacade.edit(slider);
                    }

                    // Sleep for the duration based on CHANGE_RATE
                    try {
                        // Calculate sleep time in milliseconds
                        Thread.sleep((long) (1000.0 / Slider.CHANGE_RATE));
                    } catch (InterruptedException exception) {
                        exception.printStackTrace();
                    }
                }
            }
        }).start();
    }
}

