import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * In-memory service for managing Contact objects by contactId.
 *
 * Requirements:
 * - Add contacts with unique ID
 * - Delete contacts by contactId
 * - Update (firstName, lastName, phone, address) by contactId
 */
public class ContactService {

    private final Map<String, Contact> contacts = new HashMap<>();

    public void addContact(Contact contact) {
        Objects.requireNonNull(contact, "contact must not be null");

        String id = contact.getContactId();
        if (contacts.containsKey(id)) {
            throw new DuplicateIdException("Contact ID already exists: " + id);
        }
        contacts.put(id, contact);
    }

    public void deleteContact(String contactId) {
        String id = requireId(contactId);
        if (!contacts.containsKey(id)) {
            throw new NotFoundException("Contact not found: " + id);
        }
        contacts.remove(id);
    }

    /**
     * Updates allowed fields for the contact with the given ID.
     * All provided values must be non-null and valid.
     */
    public void updateContact(String contactId, String firstName, String lastName, String phone, String address) {
        Contact c = getContactOrThrow(contactId);

        c.setFirstName(firstName);
        c.setLastName(lastName);
        c.setPhone(phone);
        c.setAddress(address);
    }

    /** Convenience method for unit tests / internal use. */
    public Contact getContact(String contactId) {
        String id = requireId(contactId);
        return contacts.get(id);
    }

    /** Read-only view (useful for debugging / testing). */
    public Map<String, Contact> getAllContactsView() {
        return Collections.unmodifiableMap(contacts);
    }

    private Contact getContactOrThrow(String contactId) {
        String id = requireId(contactId);
        Contact c = contacts.get(id);
        if (c == null) {
            throw new NotFoundException("Contact not found: " + id);
        }
        return c;
    }

    private static String requireId(String id) {
        Objects.requireNonNull(id, "contactId must not be null");
        String trimmed = id.trim();
        if (trimmed.isEmpty()) {
            throw new Contact.ValidationException("contactId must not be blank");
        }
        if (trimmed.length() > Contact.MAX_ID_LENGTH) {
            throw new Contact.ValidationException("contactId must be <= " + Contact.MAX_ID_LENGTH + " characters");
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
