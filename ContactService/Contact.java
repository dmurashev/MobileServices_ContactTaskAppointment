import java.util.Objects;

/**
 * Contact model that stores contact data and enforces validation rules.
 *
 * Requirements:
 * - contactId: required, unique (enforced by ContactService), not null, max 10 chars, NOT updatable
 * - firstName: required, not null, max 10 chars
 * - lastName: required, not null, max 10 chars
 * - phone: required, not null, exactly 10 digits
 * - address: required, not null, max 30 chars
 */
public class Contact {

    public static final int MAX_ID_LENGTH = 10;
    public static final int MAX_FIRST_NAME_LENGTH = 10;
    public static final int MAX_LAST_NAME_LENGTH = 10;
    public static final int PHONE_LENGTH = 10;
    public static final int MAX_ADDRESS_LENGTH = 30;

    private final String contactId; // not updatable
    private String firstName;
    private String lastName;
    private String phone;
    private String address;

    public Contact(String contactId, String firstName, String lastName, String phone, String address) {
        this.contactId = validateId(contactId);
        this.firstName = validateFirstName(firstName);
        this.lastName = validateLastName(lastName);
        this.phone = validatePhone(phone);
        this.address = validateAddress(address);
    }

    public String getContactId() { return contactId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }

    /** Updatable fields per requirements */
    public void setFirstName(String firstName) { this.firstName = validateFirstName(firstName); }
    public void setLastName(String lastName) { this.lastName = validateLastName(lastName); }
    public void setPhone(String phone) { this.phone = validatePhone(phone); }
    public void setAddress(String address) { this.address = validateAddress(address); }

    // -------- Validation --------

    private static String validateId(String id) {
        String value = requireNonBlank(id, "contactId");
        if (value.length() > MAX_ID_LENGTH) {
            throw new ValidationException("contactId must be <= " + MAX_ID_LENGTH + " characters");
        }
        return value;
    }

    private static String validateFirstName(String firstName) {
        String value = requireNonBlank(firstName, "firstName");
        if (value.length() > MAX_FIRST_NAME_LENGTH) {
            throw new ValidationException("firstName must be <= " + MAX_FIRST_NAME_LENGTH + " characters");
        }
        return value;
    }

    private static String validateLastName(String lastName) {
        String value = requireNonBlank(lastName, "lastName");
        if (value.length() > MAX_LAST_NAME_LENGTH) {
            throw new ValidationException("lastName must be <= " + MAX_LAST_NAME_LENGTH + " characters");
        }
        return value;
    }

    private static String validatePhone(String phone) {
        String value = requireNonBlank(phone, "phone");
        if (value.length() != PHONE_LENGTH) {
            throw new ValidationException("phone must be exactly " + PHONE_LENGTH + " digits");
        }
        for (int i = 0; i < value.length(); i++) {
            if (!Character.isDigit(value.charAt(i))) {
                throw new ValidationException("phone must contain digits only");
            }
        }
        return value;
    }

    private static String validateAddress(String address) {
        String value = requireNonBlank(address, "address");
        if (value.length() > MAX_ADDRESS_LENGTH) {
            throw new ValidationException("address must be <= " + MAX_ADDRESS_LENGTH + " characters");
        }
        return value;
    }

    private static String requireNonBlank(String s, String fieldName) {
        Objects.requireNonNull(s, fieldName + " must not be null");
        String trimmed = s.trim();
        if (trimmed.isEmpty()) {
            throw new ValidationException(fieldName + " must not be blank");
        }
        return trimmed;
    }

    /** Custom runtime exception for domain validation issues. */
    public static class ValidationException extends RuntimeException {
        public ValidationException(String message) { super(message); }
    }
}
