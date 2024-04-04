package ca.javau9.tripplanner.dto;

import java.time.LocalDate;

public class TripRequest {
    private String destination;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double budget;

    public TripRequest() {}

    public TripRequest(String destination, LocalDate startDate, LocalDate endDate, Double budget) {
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.budget = budget;
    }

    //<editor-fold desc="getters and setters">
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
    //</editor-fold>
}
