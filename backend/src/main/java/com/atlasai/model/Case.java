package com.atlasai.model;

import java.util.ArrayList;
import java.util.List;

public class Case {
    public static final String DEST_HQ = "HQ";
    public static final String DEST_CALL_CENTER = "CALL_CENTER";

    private CaseType caseType;
    private String description;
    private List<String> skus = new ArrayList<>();
    private OrderId orderId = new OrderId();
    private CustomerInfo customerInfo = new CustomerInfo();
    private String destination;
    private CaseStatus caseStatus = CaseStatus.COLLECTING;

    public CaseType getCaseType() {
        return caseType;
    }

    public void setCaseType(CaseType caseType) {
        this.caseType = caseType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getSkus() {
        return skus;
    }

    public void setSkus(List<String> skus) {
        this.skus = skus;
    }

    public OrderId getOrderId() {
        return orderId;
    }

    public void setOrderId(OrderId orderId) {
        this.orderId = orderId;
    }

    public CustomerInfo getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(CustomerInfo customerInfo) {
        this.customerInfo = customerInfo;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public CaseStatus getCaseStatus() {
        return caseStatus;
    }

    public void setCaseStatus(CaseStatus caseStatus) {
        this.caseStatus = caseStatus;
    }
}