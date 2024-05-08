package ca.javau9.tripplanner.models;

import java.time.LocalDate;
import java.time.LocalTime;

public class ItineraryItemDto {
    private Long id;
    private LocalDate itineraryDate;
    private LocalTime activityTime;
    private String activity;
    private Integer cost;
    private String notes;
    private Long tripId;

    public ItineraryItemDto() {}

    public ItineraryItemDto(Long id, LocalDate itineraryDate, LocalTime activityTime, String activity, Integer cost,
                            String notes, Long tripId) {
        this.id = id;
        this.itineraryDate = itineraryDate;
        this.activityTime = activityTime;
        this.activity = activity;
        this.cost = cost;
        this.notes = notes;
        this.tripId = tripId;
    }

    @Override
    public String toString() {
        return "ItineraryItemDto{" +
                "id=" + id +
                ", itineraryDate=" + itineraryDate +
                ", activityTime=" + activityTime +
                ", activity='" + activity + '\'' +
                ", cost=" + cost +
                ", notes='" + notes + '\'' +
                ", tripId=" + tripId +
                '}';
    }

    //<editor-fold desc="getters and setters">

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getItineraryDate() {
        return itineraryDate;
    }

    public void setItineraryDate(LocalDate itineraryDate) {
        this.itineraryDate = itineraryDate;
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

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    //</editor-fold>
}
