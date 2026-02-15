import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ContactServiceTest {

    @Test
    void addContactShouldStoreContact() {
        ContactService service = new ContactService();
        Contact c = new Contact("1", "John", "Lennon", "1234567890", "Address");

        service.addContact(c);

        assertNotNull(service.getContact("1"));
        assertEquals("John", service.getContact("1").getFirstName());
    }

    @Test
    void addNullContactShouldThrow() {
        ContactService service = new ContactService();
        assertThrows(NullPointerException.class, () -> service.addContact(null));
    }

    @Test
    void addDuplicateIdShouldThrowCustomException() {
        ContactService service = new ContactService();
        service.addContact(new Contact("1", "John", "Lennon", "1234567890", "Address"));

        assertThrows(ContactService.DuplicateIdException.class, () ->
                service.addContact(new Contact("1", "Paul", "McCartney", "0987654321", "Address 2")));
    }

    @Test
    void deleteExistingContactShouldRemoveIt() {
        ContactService service = new ContactService();
        service.addContact(new Contact("1", "John", "Lennon", "1234567890", "Address"));

        service.deleteContact("1");

        assertNull(service.getContact("1"));
    }

    @Test
    void deleteNonExistingContactShouldThrowCustomNotFound() {
        ContactService service = new ContactService();
        assertThrows(ContactService.NotFoundException.class, () -> service.deleteContact("999"));
    }

    @Test
    void updateShouldModifyAllowedFields() {
        ContactService service = new ContactService();
        service.addContact(new Contact("1", "John", "Lennon", "1234567890", "Address"));

        service.updateContact("1", "Paul", "McCartney", "0987654321", "New Address");

        Contact updated = service.getContact("1");
        assertEquals("Paul", updated.getFirstName());
        assertEquals("McCartney", updated.getLastName());
        assertEquals("0987654321", updated.getPhone());
        assertEquals("New Address", updated.getAddress());
    }

    @Test
    void updateNonExistingContactShouldThrowNotFound() {
        ContactService service = new ContactService();
        assertThrows(ContactService.NotFoundException.class, () ->
                service.updateContact("999", "Paul", "McCartney", "0987654321", "New Address"));
    }

    @Test
    void updateWithInvalidPhoneShouldThrowValidation() {
        ContactService service = new ContactService();
        service.addContact(new Contact("1", "John", "Lennon", "1234567890", "Address"));

        assertThrows(Contact.ValidationException.class, () ->
                service.updateContact("1", "Paul", "McCartney", "BADPHONE", "New Address"));
    }
}
