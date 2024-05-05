package ca.javau9.tripplanner.models;

import java.time.LocalDate;
import java.util.List;

public class TripDto {
    private Long id;
    private String destination;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double budget;
    private String createdBy;
    private List<ItineraryItemDto> itineraryItemDtos;

    public TripDto(){}

    public TripDto(Long id, String destination, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public TripDto(Long id, String destination, LocalDate startDate, LocalDate endDate, Double budget) {
        this.id = id;
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.budget = budget;
    }

    public TripDto(Long id, String destination, LocalDate startDate, LocalDate endDate, Double budget,
                   String createdBy) {
        this.id = id;
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.budget = budget;
        this.createdBy = createdBy;
    }

    public TripDto(Long id, String destination, LocalDate startDate, LocalDate endDate, Double budget,
                   String createdBy, List<ItineraryItemDto> itineraryItemDtos) {
        this.id = id;
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.budget = budget;
        this.createdBy = createdBy;
        this.itineraryItemDtos = itineraryItemDtos;
    }

    @Override
    public String toString() {
        return "TripDto{" +
                "id=" + id +
                ", destination='" + destination + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", budget=" + budget +
                ", createdBy='" + createdBy + '\'' +
                ", itineraryItemDtos=" + itineraryItemDtos +
                '}';
    }

    //<editor-fold desc="getters and setters">
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public List<ItineraryItemDto> getItineraryItemDtos() {
        return itineraryItemDtos;
    }

    public void setItineraryItemDtos(List<ItineraryItemDto> itineraryItemDtos) {
        this.itineraryItemDtos = itineraryItemDtos;
    }

    //</editor-fold>



}
