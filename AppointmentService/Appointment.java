import java.util.Date;
import java.util.Objects;

/**
 * Appointment model that stores appointment data and enforces validation rules.
 *
 * Requirements:
 * - appointmentId: required, unique (enforced by AppointmentService), not null, max 10 chars, NOT updatable
 * - appointmentDate: required, not null, cannot be in the past (use before(new Date()))
 * - description: required, not null, max 50 chars
 */
public class Appointment {

    public static final int MAX_ID_LENGTH = 10;
    public static final int MAX_DESCRIPTION_LENGTH = 50;

    private final String appointmentId; // not updatable
    private final Date appointmentDate; // not updatable per assignment (only required field)
    private final String description;   // not updatable per assignment (only required field)

    public Appointment(String appointmentId, Date appointmentDate, String description) {
        this.appointmentId = validateId(appointmentId);
        this.appointmentDate = validateDate(appointmentDate);
        this.description = validateDescription(description);
    }

    public String getAppointmentId() { return appointmentId; }
    public Date getAppointmentDate() { return new Date(appointmentDate.getTime()); } // defensive copy
    public String getDescription() { return description; }

    private static String validateId(String id) {
        String value = requireNonBlank(id, "appointmentId");
        if (value.length() > MAX_ID_LENGTH) {
            throw new ValidationException("appointmentId must be <= " + MAX_ID_LENGTH + " characters");
        }
        return value;
    }

    private static Date validateDate(Date date) {
        Objects.requireNonNull(date, "appointmentDate must not be null");
        Date now = new Date();
        if (date.before(now)) {
            throw new ValidationException("appointmentDate cannot be in the past");
        }
        return new Date(date.getTime()); // defensive copy
    }

    private static String validateDescription(String description) {
        String value = requireNonBlank(description, "description");
        if (value.length() > MAX_DESCRIPTION_LENGTH) {
            throw new ValidationException("description must be <= " + MAX_DESCRIPTION_LENGTH + " characters");
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

    public static class ValidationException extends RuntimeException {
        public ValidationException(String message) { super(message); }
    }
}
