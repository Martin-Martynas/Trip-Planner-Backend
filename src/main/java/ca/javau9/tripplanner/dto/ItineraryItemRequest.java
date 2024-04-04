package ca.javau9.tripplanner.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class ItineraryItemRequest {
    private LocalDate tripDate;
    private LocalTime activityTime;
    private String activity;
    private String notes;

    public ItineraryItemRequest() {}

    public ItineraryItemRequest(LocalDate tripDate, LocalTime activityTime, String activity, String notes) {
        this.tripDate = tripDate;
        this.activityTime = activityTime;
        this.activity = activity;
        this.notes = notes;
    }

    //<editor-fold desc="getters and setters">
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
    //</editor-fold>
}
