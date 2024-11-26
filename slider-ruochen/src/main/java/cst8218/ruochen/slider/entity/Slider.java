/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cst8218.ruochen.slider.entity;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.io.Serializable;
/**
 * Web assignment 1 
 * studentNumber:041088466
 * @author ruochenliu 
 */
/**
 * Entity class representing a Slider in the Slider Game.
 */
@Entity
@Table(name = "SLIDERS")
public class Slider implements Serializable {

    private static final long serialVersionUID = 1L;

    // Constants defining game behavior and limits
    public static final int INITIAL_SIZE = 10;
    public static final int TRAVEL_SPEED = 5;
    public static final int MAX_DIR_CHANGES = 5;
    public static final int DECREASE_RATE = 1;
    public static final int X_LIMIT = 800;
    public static final int Y_LIMIT = 600;
    public static final int SIZE_LIMIT = 100;
    public static final int MAX_TRAVEL_LIMIT = 300;
    public static final int CHANGE_RATE = 60;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

  // size must be at least 1 and at most SIZE_LIMIT. It cannot be null in the database.
    @Min(1) // Minimum value is 1
    @Max(SIZE_LIMIT) // Maximum value is SIZE_LIMIT
    @Column(nullable = false) // Column cannot be null in the database
    private Integer size;

    // x must be at least 0 and at most X_LIMIT. It cannot be null in the database.
    @Min(0) // Minimum value is 0
    @Max(X_LIMIT) // Maximum value is X_LIMIT
    @Column(nullable = false) // Column cannot be null in the database
    private Integer x;

    // y must be at least 0 and at most Y_LIMIT. It cannot be null in the database.
    @Min(0) // Minimum value is 0
    @Max(Y_LIMIT) // Maximum value is Y_LIMIT
    @Column(nullable = false) // Column cannot be null in the database
    private Integer y;

    // currentTravel cannot be null in the database.
    @Column(nullable = false) // Column cannot be null in the database
    private Integer currentTravel;

    // maxTravel must be at least 1 and at most MAX_TRAVEL_LIMIT. It cannot be null in the database.
    @Min(1) // Minimum value is 1
    @Max(MAX_TRAVEL_LIMIT) // Maximum value is MAX_TRAVEL_LIMIT
    @Column(nullable = false) // Column cannot be null in the database
    private Integer maxTravel;

    // mvtDirection cannot be null in the database.
    @Column(nullable = false) // Column cannot be null in the database
    private Integer mvtDirection;

    // dirChangeCount cannot be null in the database.
    @Column(nullable = false) // Column cannot be null in the database
    private Integer dirChangeCount;


    /**
     * Default constructor initializing default values.
     */
    public Slider() {
        this.size = INITIAL_SIZE;
        this.x = 0;
        this.y = 0;
        this.currentTravel = INITIAL_SIZE;
        this.maxTravel = INITIAL_SIZE;
        this.mvtDirection = 1; // Start moving to the right
        this.dirChangeCount = 0;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    // No setter for id to prevent changes

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getCurrentTravel() {
        return currentTravel;
    }

    public void setCurrentTravel(Integer currentTravel) {
        this.currentTravel = currentTravel;
    }

    public Integer getMaxTravel() {
        return maxTravel;
    }

    public void setMaxTravel(Integer maxTravel) {
        this.maxTravel = maxTravel;
    }

    public Integer getMvtDirection() {
        return mvtDirection;
    }

    public void setMvtDirection(Integer mvtDirection) {
        this.mvtDirection = mvtDirection;
    }

    public Integer getDirChangeCount() {
        return dirChangeCount;
    }

    public void setDirChangeCount(Integer dirChangeCount) {
        this.dirChangeCount = dirChangeCount;
    }

    /**
     * Updates the properties to simulate the passing of one unit of time.
     */
    public void timeStep() {
        if (maxTravel > 0) {
            // Update currentTravel based on movement direction and speed
            currentTravel += mvtDirection * TRAVEL_SPEED;

            // Check if currentTravel has reached or exceeded maxTravel
            if (Math.abs(currentTravel) >= maxTravel) {
                // Reverse the movement direction
                mvtDirection = -mvtDirection;

                // Increment the direction change count
                dirChangeCount++;

                // If direction changes exceed the maximum allowed, decrease maxTravel
                if (dirChangeCount >= MAX_DIR_CHANGES) {
                    maxTravel -= DECREASE_RATE;
                    dirChangeCount = 0;

                    // Ensure maxTravel doesn't go below zero
                    if (maxTravel < 0) {
                        maxTravel = 0;
                    }
                }
            }
        }
    }

    /**
     * Updates the existing Slider with non-null values from another Slider.
     * @param newSlider the Slider containing new values
     */
    public void updates(Slider newSlider) {
        if (newSlider.getSize() != null) {
            this.size = newSlider.getSize();
        }
        if (newSlider.getX() != null) {
            this.x = newSlider.getX();
        }
        if (newSlider.getY() != null) {
            this.y = newSlider.getY();
        }
        if (newSlider.getCurrentTravel() != null) {
            this.currentTravel = newSlider.getCurrentTravel();
        }
        if (newSlider.getMaxTravel() != null) {
            this.maxTravel = newSlider.getMaxTravel();
        }
        if (newSlider.getMvtDirection() != null) {
            this.mvtDirection = newSlider.getMvtDirection();
        }
        if (newSlider.getDirChangeCount() != null) {
            this.dirChangeCount = newSlider.getDirChangeCount();
        }
    }
}