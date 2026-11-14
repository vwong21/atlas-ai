package com.atlasai.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class OrderId {
    // If company contains multiple order identifiers, can add them here
    private String contractId; // preferred
    private String purchaseOrder;
    private String orderNumber;

    private static boolean hasText(String s) {
        return s != null && !s.isBlank();
    }

    @JsonIgnore
    public boolean isValid() {
        return hasText(contractId) || hasText(purchaseOrder) || hasText(orderNumber);
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(String purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }
}
