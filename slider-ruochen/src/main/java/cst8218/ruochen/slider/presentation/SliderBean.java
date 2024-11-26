/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * Web assignment 1 
 * studentNumber:041088466
 * @author ruochenliu 
 */
// File: cst8218.alice.slider.presentation.SliderBean.java
package cst8218.ruochen.slider.presentation;

import cst8218.ruochen.slider.business.SliderFacade;
import cst8218.ruochen.slider.entity.Slider;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * JSF Backing Bean for managing Sliders.
 */
@Named("sliderBean")
@SessionScoped
public class SliderBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private SliderFacade sliderFacade;

    private Slider slider = new Slider();
    private List<Slider> sliders;

    /**
     * Initializes the list of Sliders upon bean creation.
     */
    @PostConstruct
    public void init() {
        sliders = sliderFacade.findAll();
    }

    // Getters and Setters

    public Slider getSlider() {
        return slider;
    }

    public void setSlider(Slider slider) {
        this.slider = slider;
    }

    public List<Slider> getSliders() {
        return sliders;
    }

    /**
     * Creates a new Slider and redirects to the Slider list page.
     * @return navigation outcome
     */
    public String createSlider() {
        sliderFacade.create(slider);
        sliders = sliderFacade.findAll(); // Refresh the list
        slider = new Slider(); // Reset the slider for the next creation
        return "sliderLists?faces-redirect=true";
    }

    /**
     * Prepares the Slider for editing and redirects to the edit page.
     * @param slider the Slider to edit
     * @return navigation outcome
     */
    public String editSlider(Slider slider) {
        this.slider = slider;
        return "sliderEdit?faces-redirect=true";
    }

    /**
     * Updates the Slider and redirects to the Slider list page.
     * @return navigation outcome
     */
    public String updateSlider() {
        sliderFacade.edit(slider);
        sliders = sliderFacade.findAll(); // Refresh the list
        return "sliderLists?faces-redirect=true";
    }

    /**
     * Prepares the Slider for deletion and redirects to the delete confirmation page.
     * @param slider the Slider to delete
     * @return navigation outcome
     */
    public String deleteSlider(Slider slider) {
        this.slider = slider;
        return "sliderDelete?faces-redirect=true";
    }

    /**
     * Confirms deletion of the Slider and redirects to the Slider list page.
     * @return navigation outcome
     */
    public String confirmDelete() {
        sliderFacade.remove(slider);
        sliders = sliderFacade.findAll(); // Refresh the list
        return "sliderLists?faces-redirect=true";
    }
}

