import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class Case {
    // Allowed classifier labels, checked when the label comes back from
    // MlServiceClient
    public static final String CANCELLATION = "cancellation";
    public static final String RETURN_EXCHANGE = "return_exchange";
    public static final String SERVICE_REQUEST = "service_request";
    public static final String APPLIANCE_CONDITION = "appliance_condition";

    public static final String DEST_HQ = "HQ";
    public static final String DEST_CALL_CENTER = "CALL_CENTER";

    private String caseType;
    private String description;
    private List<String> skus = new ArrayList<>();
    private OrderId orderId = new OrderId();
    private CustomerInfo customerInfo = new CustomerInfo();
    private String destination;

    private static boolean hasText(String s) {
        return s != null && !s.isBlank();
    }

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

    public static class OrderId {
        // If company contains multiple order identifiers, can add them here
        private String contractId; // preferred
        private String purchaseOrder;
        private String orderNumber;

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

    public static class CustomerInfo {
        private String firstName;
        private String lastName;
        private Contact contact = new Contact();

        @JsonIgnore
        public boolean isValid() {
            return hasText(firstName) && hasText(lastName) && contact != null && contact.isValid();
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
            private String phoneNumber; // optional
            private String email; // required

            @JsonIgnore
            public boolean isValid() {
                return hasText(email);
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