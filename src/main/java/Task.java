import java.time.LocalDateTime;

public class Task {
    private int id;
    private String status;

    private String createTask;
    private String updateTask;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTask() {
        return createTask;
    }

    public void setCreateTask(String createTask) {
        this.createTask = createTask;
    }

    public String getUpdateTask() {
        return updateTask;
    }

    public void setUpdateTask(String updateTask) {
        this.updateTask = updateTask;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", status='" + status + '\'' +
                '}';
    }
}
