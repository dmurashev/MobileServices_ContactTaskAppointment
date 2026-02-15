import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    private String s(int len) {
        return "b".repeat(Math.max(0, len));
    }

    @Test
    void createValidTaskShouldSucceed() {
        Task task = new Task("123", "Write Code", "Implement the task service logic");

        assertEquals("123", task.getTaskId());
        assertEquals("Write Code", task.getName());
        assertEquals("Implement the task service logic", task.getDescription());
    }

    // ---- Boundary ----

    @Test
    void taskIdExactly10CharactersShouldWork() {
        Task task = new Task("1234567890", "Name", "Description");
        assertEquals("1234567890", task.getTaskId());
    }

    @Test
    void nameExactly20CharactersShouldWork() {
        Task task = new Task("1", s(20), "Description");
        assertEquals(s(20), task.getName());
    }

    @Test
    void descriptionExactly50CharactersShouldWork() {
        Task task = new Task("1", "Name", s(50));
        assertEquals(s(50), task.getDescription());
    }

    // ---- Negative ----

    @Test
    void nullTaskIdShouldThrow() {
        assertThrows(NullPointerException.class, () -> new Task(null, "Name", "Description"));
    }

    @Test
    void blankNameShouldThrow() {
        assertThrows(Task.ValidationException.class, () -> new Task("1", "   ", "Description"));
    }

    @Test
    void nameTooLongShouldThrow() {
        assertThrows(Task.ValidationException.class, () -> new Task("1", s(21), "Description"));
    }

    @Test
    void descriptionTooLongShouldThrow() {
        assertThrows(Task.ValidationException.class, () -> new Task("1", "Name", s(51)));
    }

    @Test
    void updatesShouldValidate() {
        Task task = new Task("1", "Name", "Description");
        task.setName("New Name");
        task.setDescription("New Description");

        assertEquals("New Name", task.getName());
        assertEquals("New Description", task.getDescription());
    }

    @Test
    void updateDescriptionInvalidShouldThrow() {
        Task task = new Task("1", "Name", "Description");
        assertThrows(Task.ValidationException.class, () -> task.setDescription(s(51)));
    }
}
