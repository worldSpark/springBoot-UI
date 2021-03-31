package com.fc.model.common;

import lombok.Data;
import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

import java.time.Instant;

/**
 * 用于influxdb的使用
 */
@Data
@Measurement(name = "vehicle")
public class VehicleHistory {

    @Column(name = "time")
    private Instant time;

    private Long times;

    @Column(name = "altitude")
    private Double altitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "comDeviceId")
    private String comDeviceId;

    @Column(name = "orientation")
    private Integer orientation;

    @Column(name = "speed")
    private Double speed;

    private Integer online = 0;

    @Override
    public String toString() {
        return "Sensor [time=" + time + ", altitude=" + altitude + ", longitude=" + longitude + ", latitude="
                + latitude + ", comDeviceId=" + comDeviceId + ", orientation=" + orientation + ", _unit=" + speed + "]";
    }

    @Override
    public int hashCode() {
        String result = comDeviceId;
        return result.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        VehicleHistory u = (VehicleHistory) obj;
        return this.getComDeviceId().equals(u.getComDeviceId());
    }

}
