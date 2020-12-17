import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Form {

    private JFrame frame;
    private JTextField txt_name;
    private JTextField txt_size;
    private JTextField txt_mem;
    private JPanel panel;
    private int width = 300;
    private int heigth = 100;
    Work work = new Work();
    private JTree tree;
    private List<Directory> list_dir = new ArrayList<>();
    private List<String> names = new ArrayList<>();
    private String nameSelected = "";
    private int guiVar = work.getHard_drive().length;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Form window = new Form();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public Form() {

        initialize();

    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

        frame = new JFrame();
        frame.setBounds(100, 100, 507, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("C:", true);
        Directory dir = new Directory("C:");
        list_dir.add(dir);
        DefaultTreeModel treeModel = new DefaultTreeModel(top, true);
        txt_mem = new JTextField();

        tree = new JTree(treeModel);
        tree.setBounds(280, 14, 201, 193);
        frame.getContentPane().add(tree);

        JButton btn_crt_fl = new JButton("Create file");
        btn_crt_fl.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                panel.repaint();
                if ((txt_name.getText() != "") && (txt_size.getText() != "")) {
                    try {
                        boolean flag = false;
                        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree
                                .getLastSelectedPathComponent();

                        for (Directory d : list_dir) {
                            if (d.getName().equals("d" + selectedNode.toString())) {
                                for (File f : d.list_f) {
                                    if (f.getName().equals(txt_name.getText())) {
                                        flag = true;
                                    }
                                }
                            }
                        }
                        if (!flag) {
                            try {
                                File file = null;
                                File file_prev=file;
                                file = new File(txt_name.getText(), Integer.parseInt(txt_size.getText()),file_prev);
                                if (!work.loadFile(file)) {
                                    JOptionPane.showMessageDialog(frame, "Не хватает места");
                                } else {
                                    DefaultMutableTreeNode fl = new DefaultMutableTreeNode(file, false);
                                    //DefaultMutableTreeNode fl = new DefaultMutableTreeNode(txt_name.getText(), false);
                                    for (Directory d : list_dir) {
                                        if (d.getName().equals("d" + selectedNode.toString())) {
                                            d.list_f.add(file);
                                            names.add(file.getName());
                                        }
                                    }
                                    selectedNode.add(fl);
                                    tree.updateUI();
                                    txt_mem.setText(work.toString());
                                }
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(frame, "Введите число в поле \"размер\"");
                            }
                        } else {
                            JOptionPane.showMessageDialog(frame, "Такой файл уже существует");
                        }
                    } catch (NullPointerException ex) {
                        JOptionPane.showMessageDialog(frame, "Нужно выбрать директорию");
                    } catch (IllegalStateException ex) {
                        JOptionPane.showMessageDialog(frame, "Это не папка");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Введите корректные данные");
                }
            }
        });
        btn_crt_fl.setBounds(10, 11, 116, 23);
        frame.getContentPane().add(btn_crt_fl);

        JButton btn_del_file = new JButton("Delete file");
        btn_del_file.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panel.repaint();
                try {
                    DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                    work.freeFile((File)selectedNode.getUserObject());
                    DefaultTreeModel treeModel = (DefaultTreeModel) tree.getModel();
                    for (Directory d : list_dir) {
                        if (d.getName().equals("d" + selectedNode.getParent().toString())) {
                            for (int i = 0; i < d.list_f.size(); i++) {
                                // for (File f : d.list_f) {
                                if (d.list_f.get(i).getName().equals(selectedNode.toString())) {
                                    work.freeFile((File)selectedNode.getUserObject());
                                    names.remove(selectedNode.toString());
                                    treeModel.removeNodeFromParent(selectedNode);
                                    d.list_f.remove(i);
                                }
                            }
                        }
                    }
                    txt_mem.setText(work.toString());
                    tree.setModel(treeModel);
                    tree.updateUI();
                } catch (NullPointerException ex) {
                    JOptionPane.showMessageDialog(frame, "Выберите файл");
                }
            }
        });
        btn_del_file.setBounds(10, 45, 116, 23);
        frame.getContentPane().add(btn_del_file);

        JButton btn_crt_dir = new JButton("Create directory");
        btn_crt_dir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panel.repaint();
                if (txt_name.getText() != "") {
                    try {
                        boolean flag = false;
                        int i = 0;
                        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree
                                .getLastSelectedPathComponent();
                        for (i = 0; i < list_dir.size(); i++) {
                            if (list_dir.get(i).getName().equals("d" + selectedNode.toString())) {
                                for (Directory dr : list_dir.get(i).list_d) {
                                    if (dr.getName().equals("d" + txt_name.getText())) {
                                        flag = true;
                                    }
                                }
                                if (flag)
                                    break;
                            }
                        }
                        i--;
                        if (!flag) {
                            Directory directory = new Directory(txt_name.getText());
                            DefaultMutableTreeNode dr = new DefaultMutableTreeNode(txt_name.getText(), true);
                            if (!work.load(directory)) {
                                JOptionPane.showMessageDialog(frame, "Не хватает места");
                            } else {
                                treeModel.insertNodeInto(dr, selectedNode, selectedNode.getChildCount());
                                list_dir.add(directory);
                                names.add(directory.getName());
                                list_dir.get(i).list_d.add(directory);
                                // work.load(directory);
                                tree.updateUI();
                                txt_mem.setText(work.toString());
                            }
                        } else {
                            JOptionPane.showMessageDialog(frame, "Такая папка уже существует");
                        }
                    } catch (NullPointerException ex) {
                        JOptionPane.showMessageDialog(frame, "Нужно выбрать директорию");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Введите корректные данные");
                }
            }
        });
        btn_crt_dir.setBounds(136, 11, 134, 23);
        frame.getContentPane().add(btn_crt_dir);

        JButton btn_del_dir = new JButton("Delete directory");
        btn_del_dir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panel.repaint();
                try {
                    DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                    if ("dC:".equals("d" + selectedNode.toString())) {
                        JOptionPane.showMessageDialog(frame, "Не пытайтесь удалить корневой каталог");
                    }
                    for (int i = 0; i < list_dir.size(); i++) {
                        if (list_dir.get(i).getName().equals("d" + selectedNode.toString())) {
                            work.freeDir(list_dir.get(i));
                            list_dir.remove(list_dir.get(i));
                            names.remove(selectedNode.toString());
                        }
                    }
                    DefaultTreeModel treeModel = (DefaultTreeModel) tree.getModel();
                    selectedNode.removeAllChildren();
                    selectedNode.removeFromParent();

                    treeModel.reload(selectedNode);
                    txt_mem.setText(work.toString());
                    tree.setModel(treeModel);
                } catch (NullPointerException ex) {
                    JOptionPane.showMessageDialog(frame, "Нужно выбрать директорию");
                }
            }
        });

        btn_del_dir.setBounds(136, 45, 134, 23);
        frame.getContentPane().add(btn_del_dir);

        tree.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                        tree.getLastSelectedPathComponent();

                /* if nothing is selected */
                if (node == null) return;

                /* retrieve the node that was selected */
                Object nodeInfo = node.getUserObject();
                nameSelected = nodeInfo.toString();
                panel.repaint();


                /* React to the node selection. */

            }
        });

        txt_name = new JTextField();
        txt_name.setBounds(136, 79, 86, 20);
        frame.getContentPane().add(txt_name);
        txt_name.setColumns(10);

        txt_size = new JTextField();
        txt_size.setBounds(136, 110, 86, 20);
        frame.getContentPane().add(txt_size);
        txt_size.setColumns(10);

        JLabel lblNameFiledir = new JLabel("Name file/dir");
        lblNameFiledir.setBounds(48, 82, 79, 14);
        frame.getContentPane().add(lblNameFiledir);

        JLabel lblNewLabel = new JLabel("Size");
        lblNewLabel.setBounds(48, 113, 46, 14);
        frame.getContentPane().add(lblNewLabel);

        txt_mem = new JTextField();
        txt_mem.setBounds(10, 230, 471, 20);
        frame.getContentPane().add(txt_mem);
        txt_mem.setColumns(10);

        panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                Draw(g);
            }
        };
        panel.setBackground(Color.WHITE);
        panel.setBounds(10, 270, 471, 150);
        frame.getContentPane().add(panel);
    }
    public void Draw(Graphics g) {
        int k = 0;
        if (!names.contains(nameSelected)&&!nameSelected.equals("")) {
            g.setColor(Color.BLUE);
        } else if (names.contains(nameSelected)) {
            g.setColor(Color.RED);
        } else if (!names.contains(nameSelected)) {
            g.setColor(Color.BLACK);
        }
        for (int i = 0; i < work.getHard_drive().length; i++) {
            if(work.getHard_drive()[i]==0){
                g.setColor(Color.BLUE);
                g.fillRect(k, 0, width / guiVar, (heigth - 2));
            } else {
                g.setColor(Color.RED);
                g.fillRect(k, 0, width / guiVar, (heigth - 2));
            };
            k = k + width / guiVar;

        }
        g.setColor(Color.BLACK);
        for (int i = 0; i < width; i += width / guiVar) {
            g.drawLine(i, 0, i, heigth);
        }
        for (int i = 0; i < heigth; i += (heigth - 2)) {
            g.drawLine(0, i, width, i);
        }
    }
}