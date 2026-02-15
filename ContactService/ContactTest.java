import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ContactTest {

    private String s(int len) {
        return "a".repeat(Math.max(0, len));
    }

    @Test
    void createValidContactShouldSucceed() {
        Contact c = new Contact("123", "John", "Lennon", "1234567890", "1 W 72nd St, New York");

        assertEquals("123", c.getContactId());
        assertEquals("John", c.getFirstName());
        assertEquals("Lennon", c.getLastName());
        assertEquals("1234567890", c.getPhone());
        assertEquals("1 W 72nd St, New York", c.getAddress());
    }

    // ---- Boundary tests ----

    @Test
    void idExactly10CharactersShouldWork() {
        Contact c = new Contact("1234567890", "John", "Lennon", "1234567890", "Address");
        assertEquals("1234567890", c.getContactId());
    }

    @Test
    void firstNameExactly10CharactersShouldWork() {
        Contact c = new Contact("1", s(10), "Lennon", "1234567890", "Address");
        assertEquals(s(10), c.getFirstName());
    }

    @Test
    void lastNameExactly10CharactersShouldWork() {
        Contact c = new Contact("1", "John", s(10), "1234567890", "Address");
        assertEquals(s(10), c.getLastName());
    }

    @Test
    void addressExactly30CharactersShouldWork() {
        Contact c = new Contact("1", "John", "Lennon", "1234567890", s(30));
        assertEquals(s(30), c.getAddress());
    }

    // ---- Negative validation tests ----

    @Test
    void nullContactIdShouldThrow() {
        assertThrows(NullPointerException.class, () ->
                new Contact(null, "John", "Lennon", "1234567890", "Address"));
    }

    @Test
    void blankContactIdShouldThrow() {
        assertThrows(Contact.ValidationException.class, () ->
                new Contact("   ", "John", "Lennon", "1234567890", "Address"));
    }

    @Test
    void idTooLongShouldThrow() {
        assertThrows(Contact.ValidationException.class, () ->
                new Contact("12345678901", "John", "Lennon", "1234567890", "Address"));
    }

    @Test
    void nullFirstNameShouldThrow() {
        assertThrows(NullPointerException.class, () ->
                new Contact("1", null, "Lennon", "1234567890", "Address"));
    }

    @Test
    void firstNameTooLongShouldThrow() {
        assertThrows(Contact.ValidationException.class, () ->
                new Contact("1", s(11), "Lennon", "1234567890", "Address"));
    }

    @Test
    void phoneNot10DigitsShouldThrow() {
        assertThrows(Contact.ValidationException.class, () ->
                new Contact("1", "John", "Lennon", "123456789", "Address"));
    }

    @Test
    void phoneWithLettersShouldThrow() {
        assertThrows(Contact.ValidationException.class, () ->
                new Contact("1", "John", "Lennon", "12345abcde", "Address"));
    }

    @Test
    void addressTooLongShouldThrow() {
        assertThrows(Contact.ValidationException.class, () ->
                new Contact("1", "John", "Lennon", "1234567890", s(31)));
    }

    // ---- Update tests ----

    @Test
    void updatesShouldValidate() {
        Contact c = new Contact("1", "John", "Lennon", "1234567890", "Address");

        c.setFirstName("Paul");
        c.setLastName("McCartney");
        c.setPhone("0987654321");
        c.setAddress("Somewhere 123");

        assertEquals("Paul", c.getFirstName());
        assertEquals("McCartney", c.getLastName());
        assertEquals("0987654321", c.getPhone());
        assertEquals("Somewhere 123", c.getAddress());
    }

    @Test
    void updatePhoneInvalidShouldThrow() {
        Contact c = new Contact("1", "John", "Lennon", "1234567890", "Address");
        assertThrows(Contact.ValidationException.class, () -> c.setPhone("111"));
    }
}
