import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

public class ToDoTree2 extends JFrame {
    private JTree tree;
    private DefaultTreeModel treeModel;
    private DefaultMutableTreeNode root;
    private DefaultMutableTreeNode highPriorityNode;
    private DefaultMutableTreeNode mediumPriorityNode;
    private DefaultMutableTreeNode lowPriorityNode;
    private JTextField taskField;
    private JComboBox<String> priorityComboBox;
    private JLabel stopwatchLabel;
    private Timer timer;
    private long startTime;

    public ToDoTree2() {
        setTitle("Carl To-Do UI : Tree Edition");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        root = new DefaultMutableTreeNode("Tasks For Today");

        highPriorityNode = new DefaultMutableTreeNode("High Priority");
        mediumPriorityNode = new DefaultMutableTreeNode("Medium Priority");
        lowPriorityNode = new DefaultMutableTreeNode("Low Priority");

        root.add(highPriorityNode);
        root.add(mediumPriorityNode);
        root.add(lowPriorityNode);

        treeModel = new DefaultTreeModel(root);
        tree = new JTree(treeModel);

        taskField = new JTextField(18);
        String[] priorities = {"High Priority", "Medium Priority", "Low Priority"};
        priorityComboBox = new JComboBox<>(priorities);

        JButton addButton = new JButton("Add Task");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String task = taskField.getText();
                String priority = (String) priorityComboBox.getSelectedItem();
                if (!task.isEmpty()) {
                    addTask(task, priority);
                    taskField.setText("");
                }
            }
        });

        JButton markDoneButton = new JButton("Finished");
        markDoneButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                markTaskAsDone();
            }
        });

        JButton removeStrikethroughButton = new JButton("Undo");
        removeStrikethroughButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeStrikethrough();
            }
        });

        stopwatchLabel = new JLabel("You Have Been Locked In For 0 Hours & 0 Minutes & 0 Seconds");
        startTime = System.currentTimeMillis();

        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                long elapsedMillis = System.currentTimeMillis() - startTime;
                String elapsedTime = formatElapsedTime(elapsedMillis);
                stopwatchLabel.setText(elapsedTime);
            }
        });
        timer.start();

        JPanel stopwatchPanel = new JPanel();
        stopwatchPanel.add(stopwatchLabel);

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Task:"));
        inputPanel.add(taskField);
        inputPanel.add(new JLabel("Priority:"));
        inputPanel.add(priorityComboBox);
        inputPanel.add(addButton);
        inputPanel.add(markDoneButton);
        inputPanel.add(removeStrikethroughButton);

        getContentPane().add(stopwatchPanel, BorderLayout.NORTH);
        getContentPane().add(new JScrollPane(tree), BorderLayout.CENTER);
        getContentPane().add(inputPanel, BorderLayout.SOUTH);
    }

    private void addTask(String task, String priority) {
        DefaultMutableTreeNode taskNode = new DefaultMutableTreeNode(task);
        switch (priority) {
            case "High Priority":
                highPriorityNode.add(taskNode);
                break;
            case "Medium Priority":
                mediumPriorityNode.add(taskNode);
                break;
            case "Low Priority":
                lowPriorityNode.add(taskNode);
                break;
        }
        treeModel.reload();
    }

    private void markTaskAsDone() {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        if (selectedNode != null && selectedNode.getParent() != null && selectedNode != root) {
            String task = (String) selectedNode.getUserObject();
            selectedNode.setUserObject("<html><strike>" + task + "</strike></html>");
            treeModel.nodeChanged(selectedNode);
        }
    }

    private void removeStrikethrough() {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        if (selectedNode != null && selectedNode.getParent() != null && selectedNode != root) {
            String task = (String) selectedNode.getUserObject();
            if (task.startsWith("<html><strike>") && task.endsWith("</strike></html>")) {
                task = task.substring(14, task.length() - 15); // Remove the <html><strike> and </strike></html>
                selectedNode.setUserObject(task);
                treeModel.nodeChanged(selectedNode);
            }
        }
    }

    private String formatElapsedTime(long elapsedMillis) {
        long hours = TimeUnit.MILLISECONDS.toHours(elapsedMillis);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(elapsedMillis) % 60;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(elapsedMillis) % 60;
        return String.format("You Have Been Locked In For %d Hours & %d Minutes & %d Seconds", hours, minutes, seconds);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ToDoTree2().setVisible(true);
            }
        });
    }
}
