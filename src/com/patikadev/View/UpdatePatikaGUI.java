package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Patika;

import javax.swing.*;


public class UpdatePatikaGUI extends JFrame {
    private JPanel wrapper;
    private JTextField fld_patika_name;
    private JButton btn_apatika_update;
    private Patika patika;

    public UpdatePatikaGUI(Patika patika) {
        this.patika = patika;
        add(wrapper);
        setSize(300, 150);
        int x = Helper.screenCenter("x", getSize());
        int y = Helper.screenCenter("y", getSize());
        setLocation(x, y);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        fld_patika_name.setText(patika.getPatika_name());


        btn_apatika_update.addActionListener(e -> {
            String patika_name = fld_patika_name.getText();

            if (Helper.isFieldEmpty(fld_patika_name)) {
                Helper.showMsg("fill");
            } else {
                boolean isUpdated = Patika.update(patika.getPatika_id(),patika_name);
                if (isUpdated) {
                    Helper.showMsg("success");

                }
                dispose();
            }
        });
    }


}
