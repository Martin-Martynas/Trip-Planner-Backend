package ca.javau9.tripplanner.models;

import java.time.LocalDate;
import java.time.LocalTime;

public class ItineraryItemDto {
    private Long id;
    private LocalDate tripDate;
    private LocalTime activityTime;
    private String activity;
    private Integer cost;
    private String notes;

    public ItineraryItemDto() {}

    public ItineraryItemDto(Long id, LocalDate tripDate, LocalTime activityTime, String activity, Integer cost,
                            String notes) {
        this.id = id;
        this.tripDate = tripDate;
        this.activityTime = activityTime;
        this.activity = activity;
        this.cost = cost;
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "ItineraryItemDto{" +
                "id=" + id +
                ", tripDate=" + tripDate +
                ", activityTime=" + activityTime +
                ", activity='" + activity + '\'' +
                ", cost=" + cost +
                ", notes='" + notes + '\'' +
                '}';
    }

    //<editor-fold desc="getters and setters">
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getTripDate() {
        return tripDate;
    }

    public void setTripDate(LocalDate tripDate) {
        this.tripDate = tripDate;
    }

    public LocalTime getActivityTime() {
        return activityTime;
    }

    public void setActivityTime(LocalTime activityTime) {
        this.activityTime = activityTime;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    //</editor-fold>
}
