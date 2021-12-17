package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class TransferDTO {

    private int userTo;
    private int userFrom;
    private BigDecimal amount;
    private boolean approved = true;

    public TransferDTO() {
    }
    public int getUserTo() {
        return userTo;
    }

    public void setUserTo(int userTo) {
        this.userTo = userTo;
    }

    public int getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(int userFrom) {
        this.userFrom = userFrom;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
