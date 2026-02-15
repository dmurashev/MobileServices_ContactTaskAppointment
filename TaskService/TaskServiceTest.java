import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TaskServiceTest {

    @Test
    void addTaskShouldStoreTask() {
        TaskService service = new TaskService();
        Task task = new Task("1", "Task1", "Description1");

        service.addTask(task);

        assertNotNull(service.getTask("1"));
    }

    @Test
    void addNullTaskShouldThrow() {
        TaskService service = new TaskService();
        assertThrows(NullPointerException.class, () -> service.addTask(null));
    }

    @Test
    void addDuplicateIdShouldThrowCustomException() {
        TaskService service = new TaskService();
        service.addTask(new Task("1", "Task1", "Description1"));

        assertThrows(TaskService.DuplicateIdException.class, () ->
                service.addTask(new Task("1", "Task2", "Description2")));
    }

    @Test
    void deleteTaskShouldRemoveTask() {
        TaskService service = new TaskService();
        service.addTask(new Task("1", "Task1", "Description1"));

        service.deleteTask("1");

        assertNull(service.getTask("1"));
    }

    @Test
    void deleteNonExistingTaskShouldThrowNotFound() {
        TaskService service = new TaskService();
        assertThrows(TaskService.NotFoundException.class, () -> service.deleteTask("999"));
    }

    @Test
    void updateTaskShouldModifyFields() {
        TaskService service = new TaskService();
        service.addTask(new Task("1", "Task1", "Description1"));

        service.updateTask("1", "Updated", "Updated Desc");

        assertEquals("Updated", service.getTask("1").getName());
        assertEquals("Updated Desc", service.getTask("1").getDescription());
    }

    @Test
    void updateNonExistingTaskShouldThrowNotFound() {
        TaskService service = new TaskService();
        assertThrows(TaskService.NotFoundException.class, () ->
                service.updateTask("404", "Updated", "Updated Desc"));
    }

    @Test
    void updateWithInvalidNameShouldThrowValidation() {
        TaskService service = new TaskService();
        service.addTask(new Task("1", "Task1", "Description1"));

        assertThrows(Task.ValidationException.class, () ->
                service.updateTask("1", " ".repeat(5), "Updated Desc"));
    }
}
