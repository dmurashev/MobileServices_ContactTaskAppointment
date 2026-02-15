import java.util.Objects;

/**
 * Task model that stores task data and enforces validation rules.
 *
 * Requirements:
 * - taskId: required, unique (enforced by TaskService), not null, max 10 chars, NOT updatable
 * - name: required, not null, max 20 chars
 * - description: required, not null, max 50 chars
 */
public class Task {

    public static final int MAX_ID_LENGTH = 10;
    public static final int MAX_NAME_LENGTH = 20;
    public static final int MAX_DESCRIPTION_LENGTH = 50;

    private final String taskId; // not updatable
    private String name;
    private String description;

    public Task(String taskId, String name, String description) {
        this.taskId = validateId(taskId);
        this.name = validateName(name);
        this.description = validateDescription(description);
    }

    public String getTaskId() { return taskId; }
    public String getName() { return name; }
    public String getDescription() { return description; }

    public void setName(String name) { this.name = validateName(name); }
    public void setDescription(String description) { this.description = validateDescription(description); }

    private static String validateId(String id) {
        String value = requireNonBlank(id, "taskId");
        if (value.length() > MAX_ID_LENGTH) {
            throw new ValidationException("taskId must be <= " + MAX_ID_LENGTH + " characters");
        }
        return value;
    }

    private static String validateName(String name) {
        String value = requireNonBlank(name, "name");
        if (value.length() > MAX_NAME_LENGTH) {
            throw new ValidationException("name must be <= " + MAX_NAME_LENGTH + " characters");
        }
        return value;
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
