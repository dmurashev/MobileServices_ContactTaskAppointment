import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * In-memory service for managing Appointment objects by appointmentId.
 *
 * Requirements:
 * - Add appointments with a unique appointmentId
 * - Delete appointments by appointmentId
 */
public class AppointmentService {

    private final Map<String, Appointment> appointments = new HashMap<>();

    public void addAppointment(Appointment appointment) {
        Objects.requireNonNull(appointment, "appointment must not be null");

        String id = appointment.getAppointmentId();
        if (appointments.containsKey(id)) {
            throw new DuplicateIdException("Appointment ID already exists: " + id);
        }
        appointments.put(id, appointment);
    }

    public void deleteAppointment(String appointmentId) {
        String id = requireId(appointmentId);
        if (!appointments.containsKey(id)) {
            throw new NotFoundException("Appointment not found: " + id);
        }
        appointments.remove(id);
    }

    public Appointment getAppointment(String appointmentId) {
        String id = requireId(appointmentId);
        return appointments.get(id);
    }

    public Map<String, Appointment> getAllAppointmentsView() {
        return Collections.unmodifiableMap(appointments);
    }

    private static String requireId(String id) {
        Objects.requireNonNull(id, "appointmentId must not be null");
        String trimmed = id.trim();
        if (trimmed.isEmpty()) {
            throw new Appointment.ValidationException("appointmentId must not be blank");
        }
        if (trimmed.length() > Appointment.MAX_ID_LENGTH) {
            throw new Appointment.ValidationException("appointmentId must be <= " + Appointment.MAX_ID_LENGTH + " characters");
        }
        return trimmed;
    }

    public static class DuplicateIdException extends RuntimeException {
        public DuplicateIdException(String message) { super(message); }
    }

    public static class NotFoundException extends RuntimeException {
        public NotFoundException(String message) { super(message); }
    }
}
