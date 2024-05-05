package ca.javau9.tripplanner.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
public class ItineraryItem {
    @Id
    @GeneratedValue
    private Long id;
    private LocalDate tripDate;
    private LocalTime activityTime;
    private String activity;
    private String notes;
    private Integer cost;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @ManyToOne
    @JoinColumn(name="trip_id")
    @JsonBackReference
    private Trip trip;

    public ItineraryItem () {}

    public ItineraryItem(Long id, LocalDate tripDate, LocalTime activityTime, String activity, String notes,
                         Integer cost, LocalDateTime createdAt, LocalDateTime updatedAt, Trip trip) {
        this.id = id;
        this.tripDate = tripDate;
        this.activityTime = activityTime;
        this.activity = activity;
        this.notes = notes;
        this.cost = cost;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.trip = trip;
    }

    public ItineraryItem(LocalDate tripDate, LocalTime activityTime, String activity, String notes) {
        this.tripDate = tripDate;
        this.activityTime = activityTime;
        this.activity = activity;
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "ItineraryItem{" +
                "id=" + id +
                ", tripDate=" + tripDate +
                ", activityTime=" + activityTime +
                ", activity='" + activity + '\'' +
                ", notes='" + notes + '\'' +
                ", cost=" + cost +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    //</editor-fold>
}
