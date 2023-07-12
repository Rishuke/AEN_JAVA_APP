package com.wicookin.connecttomysql;

import jakarta.persistence.*;

import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "events")
public class EventsEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name",nullable = false)
    private String name;

    @Column(name="description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private RoomsEntiy room_id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private MembersEntity member_id;

    @Column(name="date")
    private Date date;

    @Column(name="start_time")
    private Time startTime;

    @Column(name="end_time")
    private Time endTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RoomsEntiy getRoom_id() {
        return room_id;
    }

    public void setRoom_id(RoomsEntiy room_id) {
        this.room_id = room_id;
    }

    public MembersEntity getMember_id() {
        return member_id;
    }

    public void setMember_id(MembersEntity member_id) {
        this.member_id = member_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }
}
