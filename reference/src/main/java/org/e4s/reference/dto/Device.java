package org.e4s.reference.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import java.util.UUID;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "device")
public class Device {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "device_key", updatable = false, nullable = false)
    private UUID deviceKey;


    @Column(unique = true, nullable = false, length = 30)
    private String name;

    @Column(name = "type", nullable = false, length = 30)
    private String type;


    @Column(name = "created_on")
    @CreationTimestamp
    private Timestamp createdOn;


    @Column(name = "description", length = 1000)
    private String description;


    public Device() {
    }

    public Device(String name, String type, String description) {
        this.name = name;
        this.type = type;
        this.description = description;
    }

    public UUID getDeviceKey() {
        return deviceKey;
    }

    public void setDeviceKey(UUID deviceKey) {
        this.deviceKey = deviceKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Device{" +
            "deviceKey=" + deviceKey +
            ", name='" + name + '\'' +
            ", type='" + type + '\'' +
            ", createdOn=" + createdOn +
            ", description='" + description + '\'' +
            '}';
    }
}
