import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * In-memory service for managing Task objects by taskId.
 *
 * Requirements:
 * - Add tasks with unique ID
 * - Delete tasks by taskId
 * - Update (name, description) by taskId
 */
public class TaskService {

    private final Map<String, Task> tasks = new HashMap<>();

    public void addTask(Task task) {
        Objects.requireNonNull(task, "task must not be null");

        String id = task.getTaskId();
        if (tasks.containsKey(id)) {
            throw new DuplicateIdException("Task ID already exists: " + id);
        }
        tasks.put(id, task);
    }

    public void deleteTask(String taskId) {
        String id = requireId(taskId);
        if (!tasks.containsKey(id)) {
            throw new NotFoundException("Task not found: " + id);
        }
        tasks.remove(id);
    }

    public void updateTask(String taskId, String name, String description) {
        Task t = getTaskOrThrow(taskId);
        t.setName(name);
        t.setDescription(description);
    }

    public Task getTask(String taskId) {
        String id = requireId(taskId);
        return tasks.get(id);
    }

    public Map<String, Task> getAllTasksView() {
        return Collections.unmodifiableMap(tasks);
    }

    private Task getTaskOrThrow(String taskId) {
        String id = requireId(taskId);
        Task t = tasks.get(id);
        if (t == null) {
            throw new NotFoundException("Task not found: " + id);
        }
        return t;
    }

    private static String requireId(String id) {
        Objects.requireNonNull(id, "taskId must not be null");
        String trimmed = id.trim();
        if (trimmed.isEmpty()) {
            throw new Task.ValidationException("taskId must not be blank");
        }
        if (trimmed.length() > Task.MAX_ID_LENGTH) {
            throw new Task.ValidationException("taskId must be <= " + Task.MAX_ID_LENGTH + " characters");
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
