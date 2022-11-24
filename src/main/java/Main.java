import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static User user;
    private static final String url = "http://localhost:8080/";
    private static final RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) {
        int value;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Client Render Ferma");
        System.out.println("===================");
        System.out.println("1. Registration user");
        System.out.println("2. Sign in");
        System.out.println("0. Exit");
        System.out.println("===================");
        value = scanner.nextInt();
        System.out.print("Enter name: ");
        String name = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();
        switch (value) {
            case 1 -> registrationUser(name, password);
            case 2 -> singInUser(name, password);
            default -> System.exit(0);
        }
        while (true){
            System.out.println("===================");
            System.out.println("3. New task");
            System.out.println("4. Show my tasks");
            System.out.println("5. Show history tasks");
            System.out.println("0. Exit");
            System.out.println("===================");
            value = scanner.nextInt();
            switch (value) {
                case 3 -> newTask();
                case 4 -> showMyTasks();
                case 5 -> historyTask();
                default -> System.exit(0);
            }
        }
    }

    private static void registrationUser(String name, String password) {
        Map<String, String> jsonToSend = new HashMap<>();
        jsonToSend.put("name", name);
        jsonToSend.put("password", password);
        HttpEntity<Map<String, String>> request = new HttpEntity<>(jsonToSend);
        user = restTemplate.postForObject(url + "user/registration", request, User.class);
    }

    private static void singInUser(String name, String password){
        user = restTemplate.getForObject(url + "user/sign-in?name=" + name + "&password=" + password, User.class);
    }

    private static void newTask(){
        Task task = restTemplate.postForObject(url + "user/" + user.getId() + "/tasks", null, Task.class);
        Thread thread = new Thread(()->{
            try {
                Thread.sleep((long) (60000 + Math.random() * 240000));
                updateTask(task.getId());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();
    }

    private static void showMyTasks(){
        Task[] response = restTemplate.getForObject(url + "user/" + user.getId() + "/tasks", Task[].class);
        if (response != null) {
            for (Task task : response){
                System.out.println(task);
            }
        } else {
            System.out.println("No task");
        }
    }

    private static void updateTask(int id){
        restTemplate.postForObject(url + "task/" + id, null,String.class);
    }

    private static void historyTask(){
        Task[] response = restTemplate.getForObject(url + "user/" + user.getId() + "/tasks", Task[].class);
        if (response != null) {
            for (Task task : response){
                System.out.println("ID - " + task.getId() + " CREATE TASK - " + task.getCreateTask() + " UPDATE TASK - " + task.getUpdateTask());
            }
        } else {
            System.out.println("No task");
        }
    }
}
