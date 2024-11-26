/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cst8218.ruochen.slider.rest;
import cst8218.ruochen.slider.business.SliderFacade;
import cst8218.ruochen.slider.entity.Slider;
import jakarta.ejb.EJB;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

/**
 * Web assignment 1 
 * studentNumber:041088466
 * @author ruochenliu 
 */
/**
 * RESTful API resource for managing Sliders.
 */

@Path("/sliders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SliderFacadeREST {

    @EJB
    private SliderFacade sliderFacade;

    /**
     * Retrieves all Sliders.
     * @return Response containing list of Sliders
     */
    @GET
    public Response getAllSliders() {
        List<Slider> sliders = sliderFacade.findAll();
        return Response.ok(sliders).build();
    }

    /**
     * Retrieves the count of Sliders.
     * @return Response containing the count of Sliders
     */
    @GET
    @Path("/count")
    public Response getSliderCount() {
        long count = (long) sliderFacade.findAll().size();
        return Response.ok(count).build();
    }

    /**
     * Retrieves a Slider by ID.
     * @param id the ID of the Slider
     * @return Response containing the Slider
     */
    @GET
    @Path("/{id}")
    public Response getSliderById(@PathParam("id") Long id) {
        Slider slider = sliderFacade.find(id);
        if (slider == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(slider).build();
    }

    /**
     * Creates a new Slider or updates an existing one based on the presence of ID.
     * @param slider the Slider to create or update
     * @param uriInfo URI context
     * @return Response indicating the result of the operation
     */
    @POST
    public Response createOrUpdateSlider(Slider slider, @Context UriInfo uriInfo) {
        if (slider.getId() == null) {
            // Create new Slider
            sliderFacade.create(slider);
            URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(slider.getId())).build();
            return Response.created(uri).entity(slider).build();
        } else {
            Slider existingSlider = sliderFacade.find(slider.getId());
            if (existingSlider != null) {
                // Update existing Slider
                updateSliderProperties(existingSlider, slider);
                sliderFacade.edit(existingSlider);
                return Response.ok(existingSlider).build();
            } else {
                // ID not found, bad request
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("Slider with ID " + slider.getId() + " does not exist.")
                               .build();
            }
        }
    }

    /**
     * Updates a specific Slider by ID.
     * @param id the ID of the Slider to update
     * @param slider the Slider data to update
     * @return Response indicating the result of the operation
     */
    @POST
    @Path("/{id}")
    public Response updateSliderById(@PathParam("id") Long id, Slider slider) {
        if (slider.getId() != null && !slider.getId().equals(id)) {
            // ID in body does not match ID in URL
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("ID in the request body does not match ID in the URL.")
                           .build();
        }

        Slider existingSlider = sliderFacade.find(id);
        if (existingSlider == null) {
            // ID not found
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("Slider with ID " + id + " does not exist.")
                           .build();
        }

        // Update existing Slider with non-null properties from the new Slider
        updateSliderProperties(existingSlider, slider);
        sliderFacade.edit(existingSlider);

        return Response.ok(existingSlider).build();
    }

    /**
     * Completely replaces a specific Slider by ID.
     * @param id the ID of the Slider to replace
     * @param slider the new Slider data
     * @return Response indicating the result of the operation
     */
    @PUT
    @Path("/{id}")
    public Response replaceSliderById(@PathParam("id") Long id, Slider slider) {
        if (slider.getId() != null && !slider.getId().equals(id)) {
            // ID in body does not match ID in URL
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("ID in the request body does not match ID in the URL.")
                           .build();
        }

        Slider existingSlider = sliderFacade.find(id);
        if (existingSlider == null) {
            // ID not found
            return Response.status(Response.Status.BAD_REQUEST)
                           .entity("Slider with ID " + id + " does not exist.")
                           .build();
        }

        // Replace existing Slider with new Slider
    
        sliderFacade.edit(slider);

        return Response.noContent().build();
    }

    /**
     * Disallows PUT on the root resource.
     * @param slider the Slider data
     * @return Response indicating the method is not allowed
     */
    @PUT
    public Response putOnRootNotAllowed(Slider slider) {
        return Response.status(Response.Status.METHOD_NOT_ALLOWED)
                       .entity("PUT on the root resource is not allowed.")
                       .build();
    }

    /**
     * Deletes a Slider by ID.
     * @param id the ID of the Slider to delete
     * @return Response indicating the result of the operation
     */
    @DELETE
    @Path("/{id}")
    public Response deleteSlider(@PathParam("id") Long id) {
        Slider existingSlider = sliderFacade.find(id);
        if (existingSlider == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        sliderFacade.remove(existingSlider);
        return Response.noContent().build();
    }

    /**
     * Helper method to update existing Slider properties with new non-null values.
     * @param existingSlider the existing Slider entity
     * @param newSlider the new Slider data
     */
    private void updateSliderProperties(Slider existingSlider, Slider newSlider) {
        if (newSlider.getSize() != null) {
            existingSlider.setSize(newSlider.getSize());
        }
        if (newSlider.getX() != null) {
            existingSlider.setX(newSlider.getX());
        }
        if (newSlider.getY() != null) {
            existingSlider.setY(newSlider.getY());
        }
        if (newSlider.getCurrentTravel() != null) {
            existingSlider.setCurrentTravel(newSlider.getCurrentTravel());
        }
        if (newSlider.getMaxTravel() != null) {
            existingSlider.setMaxTravel(newSlider.getMaxTravel());
        }
        if (newSlider.getMvtDirection() != null) {
            existingSlider.setMvtDirection(newSlider.getMvtDirection());
        }
        if (newSlider.getDirChangeCount() != null) {
            existingSlider.setDirChangeCount(newSlider.getDirChangeCount());
        }
    }
}