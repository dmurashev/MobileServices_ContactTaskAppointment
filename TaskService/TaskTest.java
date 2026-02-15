import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    private String s(int len) {
        return "b".repeat(Math.max(0, len));
    }

    @Test
    void testCreateValidTaskShouldSucceed() {
        Task task = new Task("123", "Write Code", "Implement the task service logic");

        assertEquals("123", task.getTaskId());
        assertEquals("Write Code", task.getName());
        assertEquals("Implement the task service logic", task.getDescription());
    }

    // ---- Boundary ----

    @Test
    void testTaskIdExactly10CharactersShouldWork() {
        Task task = new Task("1234567890", "Name", "Description");
        assertEquals("1234567890", task.getTaskId());
    }

    @Test
    void testNameExactly20CharactersShouldWork() {
        Task task = new Task("1", s(20), "Description");
        assertEquals(s(20), task.getName());
    }

    @Test
    void testDescriptionExactly50CharactersShouldWork() {
        Task task = new Task("1", "Name", s(50));
        assertEquals(s(50), task.getDescription());
    }

    // ---- Negative ----

    @Test
    void testNullTaskIdShouldThrow() {
        assertThrows(NullPointerException.class, () -> new Task(null, "Name", "Description"));
    }

    @Test
    void testBlankNameShouldThrow() {
        assertThrows(Task.ValidationException.class, () -> new Task("1", "   ", "Description"));
    }

    @Test
    void testNameTooLongShouldThrow() {
        assertThrows(Task.ValidationException.class, () -> new Task("1", s(21), "Description"));
    }

    @Test
    void testDescriptionTooLongShouldThrow() {
        assertThrows(Task.ValidationException.class, () -> new Task("1", "Name", s(51)));
    }

    @Test
    void testUpdatesShouldValidate() {
        Task task = new Task("1", "Name", "Description");
        task.setName("New Name");
        task.setDescription("New Description");

        assertEquals("New Name", task.getName());
        assertEquals("New Description", task.getDescription());
    }

    @Test
    void testUpdateDescriptionInvalidShouldThrow() {
        Task task = new Task("1", "Name", "Description");
        assertThrows(Task.ValidationException.class, () -> task.setDescription(s(51)));
    }
}
