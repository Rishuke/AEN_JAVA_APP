package com.wicookin.connecttomysql;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "invoices")
public class InvoicesEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column(columnDefinition = "DECIMAL(10,2)")
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private MembersEntity member_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public MembersEntity getMember_id() {
        return member_id;
    }

    public void setMember_id(MembersEntity member_id) {
        this.member_id = member_id;
    }
}
