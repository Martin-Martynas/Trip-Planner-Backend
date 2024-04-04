package ca.javau9.tripplanner.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Trip {
    @Id
    @GeneratedValue
    private Long id;
    private String destination;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double budget;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @ManyToOne
    private UserEntity userEntity;
    @OneToMany
    private List<ItineraryItem> itineraryItems;

    public Trip() {}

    public Trip(Long id, String destination, LocalDate startDate, LocalDate endDate, Double budget,
                LocalDateTime createdAt, LocalDateTime updatedAt, UserEntity userEntity, List<ItineraryItem> itineraryItem) {
        this.id = id;
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.budget = budget;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userEntity = userEntity;
        this.itineraryItems = itineraryItem;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "id=" + id +
                ", destination='" + destination + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", budget=" + budget +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", userEntity=" + userEntity +
                ", itineraryItem=" + itineraryItems +
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

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public List<ItineraryItem> getItineraryItems() {
        return itineraryItems;
    }

    public void setItineraryItem(List<ItineraryItem> itineraryItems) {
        this.itineraryItems = itineraryItems;
    }


    //</editor-fold>
}
