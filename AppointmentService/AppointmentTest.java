import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.Date;

public class AppointmentTest {

    private Date futureDateMillis(long millisInFuture) {
        return new Date(System.currentTimeMillis() + millisInFuture);
    }

    private String s(int len) {
        return "c".repeat(Math.max(0, len));
    }

    @Test
    void testValidAppointmentCreation() {
        Date future = futureDateMillis(60_000);
        Appointment apt = new Appointment("123", future, "Doctor visit");

        assertEquals("123", apt.getAppointmentId());
        assertEquals("Doctor visit", apt.getDescription());
        assertNotNull(apt.getAppointmentDate());
    }

    @Test
    void idExactlyTenCharactersShouldWork() {
        Date future = futureDateMillis(60_000);
        Appointment apt = new Appointment("1234567890", future, "Valid description");
        assertEquals("1234567890", apt.getAppointmentId());
    }

    @Test
    void descriptionExactly50CharactersShouldWork() {
        Date future = futureDateMillis(60_000);
        Appointment apt = new Appointment("1", future, s(50));
        assertEquals(s(50), apt.getDescription());
    }

    @Test
    void nullAppointmentIdShouldThrow() {
        Date future = futureDateMillis(60_000);
        assertThrows(NullPointerException.class, () -> new Appointment(null, future, "Desc"));
    }

    @Test
    void appointmentIdTooLongShouldThrow() {
        Date future = futureDateMillis(60_000);
        assertThrows(Appointment.ValidationException.class, () ->
                new Appointment("12345678901", future, "Desc"));
    }

    @Test
    void nullDateShouldThrow() {
        assertThrows(NullPointerException.class, () ->
                new Appointment("1", null, "Desc"));
    }

    @Test
    void pastDateShouldThrow() {
        Date past = new Date(System.currentTimeMillis() - 60_000);
        assertThrows(Appointment.ValidationException.class, () ->
                new Appointment("1", past, "Desc"));
    }

    @Test
    void nullDescriptionShouldThrow() {
        Date future = futureDateMillis(60_000);
        assertThrows(NullPointerException.class, () ->
                new Appointment("1", future, null));
    }

    @Test
    void descriptionTooLongShouldThrow() {
        Date future = futureDateMillis(60_000);
        assertThrows(Appointment.ValidationException.class, () ->
                new Appointment("1", future, s(51)));
    }

    @Test
    void getAppointmentDateReturnsDefensiveCopy() {
        Date future = futureDateMillis(60_000);
        Appointment apt = new Appointment("1", future, "Desc");

        Date d1 = apt.getAppointmentDate();
        d1.setTime(d1.getTime() + 999_999);

        // original should not change
        assertNotEquals(d1.getTime(), apt.getAppointmentDate().getTime());
    }
}
