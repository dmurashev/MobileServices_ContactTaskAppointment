import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.Date;

public class AppointmentServiceTest {

    private Date futureDate() {
        return new Date(System.currentTimeMillis() + 60_000);
    }

    @Test
    void testAddAppointment() {
        AppointmentService service = new AppointmentService();
        Appointment apt = new Appointment("1", futureDate(), "Meeting");

        service.addAppointment(apt);

        assertNotNull(service.getAppointment("1"));
    }

    @Test
    void testAddNullAppointment() {
        AppointmentService service = new AppointmentService();
        assertThrows(NullPointerException.class, () -> service.addAppointment(null));
    }

    @Test
    void testDuplicateAppointmentId() {
        AppointmentService service = new AppointmentService();
        service.addAppointment(new Appointment("1", futureDate(), "Meeting"));

        assertThrows(AppointmentService.DuplicateIdException.class, () ->
                service.addAppointment(new Appointment("1", futureDate(), "Another Meeting")));
    }

    @Test
    void testDeleteAppointment() {
        AppointmentService service = new AppointmentService();
        service.addAppointment(new Appointment("1", futureDate(), "Meeting"));

        service.deleteAppointment("1");

        assertNull(service.getAppointment("1"));
    }

    @Test
    void testDeleteNonExistingAppointment() {
        AppointmentService service = new AppointmentService();
        assertThrows(AppointmentService.NotFoundException.class, () -> service.deleteAppointment("999"));
    }
}
