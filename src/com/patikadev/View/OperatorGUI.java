package com.patikadev.View;

import com.patikadev.Helper.*;
import com.patikadev.Model.Operator;
import com.patikadev.Model.Patika;
import com.patikadev.Model.Users;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class OperatorGUI extends JFrame {
    private JPanel wrapper;
    private JTabbedPane tab_operator;
    private JLabel lbl_welcome;
    private JPanel pnl_top;
    private JButton btn_logout;
    private JPanel pnl_users_list;
    private JScrollPane scrl_user_list;
    private JTable tbl_user_list;
    private JPanel pnl_user_form;
    private JTextField fld_user_name;
    private JTextField fld_user_username;
    private JPasswordField fld_user_password;
    private JComboBox cmb_user_type;
    private JButton btn_user_add;
    private JTextField fld_user_id;
    private JButton btn_user_delete;
    private JTextField fld_srch_user_name;
    private JTextField fld_srch_usarname;
    private JComboBox cmb_srch_type;
    private JButton btn_user_srch;
    private JPanel pnl_patika_list;
    private JScrollPane scrl_patika_list;
    private JTable tbl_patika_list;
    private JPanel pnl_patika_add;
    private JTextField fld_patika_name;
    private JButton btn_patika_add;
    private DefaultTableModel mdl_user_list;

    private Object[] row_user_list;

    private  DefaultTableModel mdl_patika_list;
    private Object[] row_patika_list;
    private  JPopupMenu patikaMenu;
    private final Operator operator ;

    public OperatorGUI(Operator operator) {
        this.operator = operator;

        add(wrapper);
        setSize(800, 600);
        int x = Helper.screenCenter("x", getSize());
        int y = Helper.screenCenter("y", getSize());
        setLocation(x, y);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE + " - Operator Panels-");
        setVisible(true);

        lbl_welcome.setText("Welcome, " + operator.getUser_name());

        //ModelUsersList
        mdl_user_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0)
                    return false;
                return super.isCellEditable(row, column);
            }
        };
        Object[] col_user_list = {"ID", "Name", "Username", "Password", "Type"};
        mdl_user_list.setColumnIdentifiers(col_user_list);

        row_user_list = new Object[col_user_list.length];
        refreshUserList();



        tbl_user_list.setModel(mdl_user_list);
        tbl_user_list.getTableHeader().setReorderingAllowed(false);

        tbl_user_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                int selected_row = tbl_user_list.getSelectedRow();
                fld_user_id.setText(tbl_user_list.getValueAt(selected_row, 0).toString());
                fld_user_name.setText(tbl_user_list.getValueAt(selected_row, 1).toString());
                fld_user_username.setText(tbl_user_list.getValueAt(selected_row, 2).toString());
                fld_user_password.setText(tbl_user_list.getValueAt(selected_row, 3).toString());
                cmb_user_type.setSelectedItem(tbl_user_list.getValueAt(selected_row, 4).toString());
            }catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        });

        tbl_user_list.getModel().addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                int user_id = Integer.parseInt(tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 0).toString());
                String user_name = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 1).toString();
                String user_username = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 2).toString();
                String user_password = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 3).toString();
                String users_type = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 4).toString();

                if (Users.update(user_id, user_name, user_username, user_password, users_type)) {
                    Helper.showMsg("success");
                }

                refreshUserList();
            }
        });

        //ModelPatikaList

        patikaMenu = new JPopupMenu();
        JMenuItem deletePatika = new JMenuItem("Delete Patika");
        JMenuItem updatePatika = new JMenuItem("Update Patika");
        patikaMenu.add(deletePatika);
        patikaMenu.add(updatePatika);

        updatePatika.addActionListener(e -> {
            int selected_row = tbl_patika_list.getSelectedRow();
            int selected_id = Integer.parseInt(tbl_patika_list.getValueAt(selected_row, 0).toString());
            String patika_name = tbl_patika_list.getValueAt(selected_row, 1).toString();
            UpdatePatikaGUI upGUI = new UpdatePatikaGUI(Patika.getFetch(selected_id));
            upGUI.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    refreshPatikaList();
                }
            });
        });

        deletePatika.addActionListener((ActionListener) evt -> {
           if (Helper.confirm("sure")) {
               int selected_row = tbl_patika_list.getSelectedRow();
               int selected_id = Integer.parseInt(tbl_patika_list.getValueAt(selected_row, 0).toString());
               if (Patika.delete(selected_id)) {
                   Helper.showMsg("success");
                   refreshPatikaList();
               }else {
                   Helper.showMsg("error");
               }
           }
        });

                mdl_patika_list = new DefaultTableModel();
        Object[] col_patika_list = {"ID", "Patika Name"};
        mdl_patika_list.setColumnIdentifiers(col_patika_list);
        row_patika_list = new Object[col_patika_list.length];
        refreshPatikaList();

        tbl_patika_list.setModel(mdl_patika_list);
        tbl_patika_list.setComponentPopupMenu(patikaMenu);
        tbl_patika_list.getTableHeader().setReorderingAllowed(false);
        tbl_patika_list.getColumnModel().getColumn(0).setMaxWidth(50);

        tbl_patika_list.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                int row = tbl_patika_list.rowAtPoint(evt.getPoint());
                tbl_patika_list.getSelectionModel().setSelectionInterval(row, row);
                if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
                    patikaMenu.show(tbl_patika_list, evt.getX(), evt.getY());
                }
            }
        });

        btn_user_add.addActionListener(e ->  {
            if (Helper.isFieldEmpty(fld_user_name) || Helper.isFieldEmpty(fld_user_username) || Helper.isFieldEmpty(fld_user_password)) {
                Helper.showMsg("fill");
            }else {
                String user_name = fld_user_name.getText();
                String user_username = fld_user_username.getText();
                String user_password = String.valueOf(fld_user_password.getPassword());
                String users_type = String.valueOf(cmb_user_type.getSelectedItem());

                if (Users.add(user_name, user_username, user_password, users_type)) {
                    Helper.showMsg("success");
                    refreshUserList();

                    fld_user_name.setText(null);
                    fld_user_username.setText(null);
                    fld_user_password.setText(null);
                    cmb_user_type.setSelectedIndex(0);

                }
            }
                }
        );
        btn_user_delete.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_user_id)) {
                Helper.showMsg("fill");
            }else {
                if(Helper.confirm("sure")){
                    int user_id = Integer.parseInt(fld_user_id.getText());
                    if (Users.delete(user_id)) {
                        Helper.showMsg("success");
                        refreshUserList();
                        fld_user_id.setText(null);
                        fld_user_name.setText(null);
                        fld_user_username.setText(null);
                        fld_user_password.setText(null);
                        cmb_user_type.setSelectedIndex(0);
                    }else {
                        Helper.showMsg("error");
                    }
                }
            }
        });
        btn_user_srch.addActionListener(e -> {
            String user_name = fld_srch_user_name.getText();
            String user_username = fld_srch_usarname.getText();
            String users_type = String.valueOf(cmb_srch_type.getSelectedItem());
            String query = Users.searchQuery(user_name, user_username, users_type);
            ArrayList<Users> userList = Users.searchUserList(query);
            refreshUserList(userList);
        });


        btn_logout.addActionListener(e -> {
            dispose();
        });
        btn_patika_add.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_patika_name)) {
                Helper.showMsg("fill");
            }else {
                String patika_name = fld_patika_name.getText();
                if (com.patikadev.Model.Patika.add(patika_name)) {
                    Helper.showMsg("success");
                    refreshPatikaList();
                    fld_patika_name.setText(null);
                }else {
                    Helper.showMsg("error");
                }
            }
        });
    }

    private void refreshPatikaList() {
        mdl_patika_list.setRowCount(0);
        for (com.patikadev.Model.Patika obj : com.patikadev.Model.Patika.getList()) {
            Object[] row_patika_list = {obj.getPatika_id(), obj.getPatika_name()};
            mdl_patika_list.addRow(row_patika_list);
        }
    }

    private void refreshUserList() { // refresh the model table
        mdl_user_list.setRowCount(0); // clear the model table
        for(Users obj : Users.getList()){ // add the new data to the model table
            Object[] row_user_list = {obj.getUser_id(), obj.getUser_name(), obj.getUser_username(), obj.getUser_password(), obj.getUsers_type()}; // create a new row object array
            mdl_user_list.addRow(row_user_list); // add the row to the model table
        }
    }
    private void refreshUserList(ArrayList<Users> list) { // refresh the model table
        mdl_user_list.setRowCount(0); // clear the model table
        for(Users obj : list){ // add the new data to the model table
            Object[] row_user_list = {obj.getUser_id(), obj.getUser_name(), obj.getUser_username(), obj.getUser_password(), obj.getUsers_type()}; // create a new row object array
            mdl_user_list.addRow(row_user_list); // add the row to the model table
        }
    }

    public static void main(String[] args) {
        Helper.setLayout();
        Operator op = new Operator();
        op.setUser_id(1);
        op.setUser_name("Furkan");
        op.setUser_username("aresor");
        op.setUser_password("123");
        op.setUsers_type("operator");

        OperatorGUI opGUI = new OperatorGUI(op);
    }
}
