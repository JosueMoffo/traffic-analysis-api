package com.example.traffic.traffic_analysis_api.dto;

public class TrafficUpdateDto {

    private long from;
    private long to;
    private double realTimeSpeed;  // en km/h ou m/s
    private double normalSpeed;    // en km/h ou m/s

    public TrafficUpdateDto() {
    }

    public TrafficUpdateDto(long from, long to, double realTimeSpeed, double normalSpeed) {
        this.from = from;
        this.to = to;
        this.realTimeSpeed = realTimeSpeed;
        this.normalSpeed = normalSpeed;
    }

    public long getFrom() {
        return from;
    }

    public void setFrom(long from) {
        this.from = from;
    }

    public long getTo() {
        return to;
    }

    public void setTo(long to) {
        this.to = to;
    }

    public double getRealTimeSpeed() {
        return realTimeSpeed;
    }

    public void setRealTimeSpeed(double realTimeSpeed) {
        this.realTimeSpeed = realTimeSpeed;
    }

    public double getNormalSpeed() {
        return normalSpeed;
    }

    public void setNormalSpeed(double normalSpeed) {
        this.normalSpeed = normalSpeed;
    }
}
