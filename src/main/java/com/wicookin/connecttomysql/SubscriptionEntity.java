package com.wicookin.connecttomysql;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "subscriptions")
public class SubscriptionEntity {

    @Column(columnDefinition = "DECIMAL(10,2)")
    private BigDecimal amount;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private MembersEntity member_id;

    @Column(name="date_created")
    private Date date_created;

    @Column(name="start_date")
    private Date start_date;

    @Column(name="end_date")
    private Date end_date;


    @Column(name="status", nullable=false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private InvoicesEntity invoice_id;

    @Column(name="type", nullable=false)
    private String type;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MembersEntity getMember_id() {
        return member_id;
    }

    public void setMember_id(MembersEntity member_id) {
        this.member_id = member_id;
    }

    public Date getDate_created() {
        return date_created;
    }

    public void setDate_created(Date date_created) {
        this.date_created = date_created;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public InvoicesEntity getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(InvoicesEntity invoice_id) {
        this.invoice_id = invoice_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
