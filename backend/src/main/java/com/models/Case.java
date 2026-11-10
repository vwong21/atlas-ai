package com.models;

import java.util.List;

public class Case {
    private String caseType;
    private String description;
    private List<Integer> skus;
    private OrderId orderId;
    private CustomerInfo customerInfo;

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Integer> getSkus() {
        return skus;
    }

    public void setSkus(List<Integer> skus) {
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

    public static class OrderId {
        // If company contains multiple order identifiers, can add them here
        private Integer orderNumber;
        private Integer transactionId;
        private Integer purchaseOrder;

        public boolean isValid() {
            return orderNumber != null || transactionId != null || purchaseOrder != null;
        }

        public Integer getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(Integer orderNumber) {
            this.orderNumber = orderNumber;
        }

        public Integer getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(Integer transactionId) {
            this.transactionId = transactionId;
        }

        public Integer getPurchaseOrder() {
            return purchaseOrder;
        }

        public void setPurchaseOrder(Integer purchaseOrder) {
            this.purchaseOrder = purchaseOrder;
        }
    }

    public static class CustomerInfo {
        private String firstName;
        private String lastName;
        private Contact contact;

        public boolean isValid() {
            return contact != null && contact.isValid();
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public Contact getContact() {
            return contact;
        }

        public void setContact(Contact contact) {
            this.contact = contact;
        }

        public static class Contact {
            private String phoneNumber;
            private String email;

            public boolean isValid() {
                return phoneNumber != null || email != null;
            }

            public String getPhoneNumber() {
                return phoneNumber;
            }

            public void setPhoneNumber(String phoneNumber) {
                this.phoneNumber = phoneNumber;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }
        }
    }
}
