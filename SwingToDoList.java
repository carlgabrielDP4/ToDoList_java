import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

class Task {
    private String description;
    private boolean completed;

    public Task(String description) {
        this.description = description;
        this.completed = false;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}

public class SwingToDoList extends JFrame {
    private ArrayList<Task> tasks;
    private DefaultListModel<String> model;
    private JList<String> taskList;
    private JTextField taskField;

    public SwingToDoList() {
        tasks = new ArrayList<>();
        model = new DefaultListModel<>();
        taskList = new JList<>(model);
        taskField = new JTextField(20);

        JButton addButton = new JButton("Add Task");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String description = taskField.getText().trim();
                if (!description.isEmpty()) {
                    tasks.add(new Task(description));
                    updateTaskList();
                    taskField.setText("");
                }
            }
        });

        JButton completeButton = new JButton("Mark as Completed");
        completeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = taskList.getSelectedIndex();
                if (selectedIndex != -1) {
                    Task selectedTask = tasks.get(selectedIndex);
                    selectedTask.setCompleted(true);
                    updateTaskList();
                }
            }
        });

        JButton removeButton = new JButton("Remove Task");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = taskList.getSelectedIndex();
                if (selectedIndex != -1) {
                    tasks.remove(selectedIndex);
                    updateTaskList();
                }
            }
        });

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Task Description:"));
        inputPanel.add(taskField);
        inputPanel.add(addButton);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(completeButton);
        buttonPanel.add(removeButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(taskList), BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setTitle("Swing To-Do List");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void updateTaskList() {
        model.clear();
        for (Task task : tasks) {
            String description = task.getDescription();
            if (task.isCompleted()) {
                description = "<html><s>" + description + "</s></html>";
            }
            model.addElement(description);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SwingToDoList();
            }
        });
    }
}
