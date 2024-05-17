import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToDoTree2 extends JFrame {
    private JTree tree;
    private DefaultTreeModel treeModel;
    private DefaultMutableTreeNode root;
    private DefaultMutableTreeNode highPriorityNode;
    private DefaultMutableTreeNode mediumPriorityNode;
    private DefaultMutableTreeNode lowPriorityNode;
    private JTextField taskField;
    private JComboBox<String> priorityComboBox;

    public ToDoTree2() {
        setTitle("Carl To-Do UI : Tree Edition");
        setSize(600, 400);
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

        taskField = new JTextField(25);
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


        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Task:"));
        inputPanel.add(taskField);
        inputPanel.add(new JLabel("Priority:"));
        inputPanel.add(priorityComboBox);
        inputPanel.add(addButton);

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

    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                new ToDoTree2().setVisible(true);
            }
        });
    }
}
