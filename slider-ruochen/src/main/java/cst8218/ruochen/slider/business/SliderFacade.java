/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
/**
 * Web assignment 1 
 * studentNumber:041088466
 * @author ruochenliu 
 */

package cst8218.ruochen.slider.business;
import cst8218.ruochen.slider.entity.Slider;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * Web assignment 1 
 * studentNumber:041088466
 * @author ruochenliu 
 */

@Stateless
public class SliderFacade extends AbstractFacade<Slider> {
 @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;
 
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    /**
     * Default constructor setting the Slider class.
     */
    public SliderFacade() {
        super(Slider.class);
    }

    

}
