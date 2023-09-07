package com.patikadev.Helper;

import java.awt.*;
import  javax.swing.*;

public class Helper {

    public  static  void setLayout(){
        for(UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
            if("Nimbus".equals(info.getName())){
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (Exception e) {
                    System.out.println("Nimbus tema bulunamadı.");
                }
                break;
            }
        }
    }
    public static int screenCenter(String point, Dimension size){
        return switch (point) {
            case "x" -> (Toolkit.getDefaultToolkit().getScreenSize().width - size.width) / 2;
            case "y" -> (Toolkit.getDefaultToolkit().getScreenSize().height - size.height) / 2;
            default -> 0;
        };
    }

    public static boolean isFieldEmpty(JTextField field){
        return field.getText().trim().isEmpty();
    }


    public static void showMsg(String str){
        optionPageTr();
        String msg ;
        String title;
        switch (str) {
            case "fill" -> {
                msg = "Lütfen tüm alanları doldurunuz.";
                title = "Hata!";
            }
            case "success" -> {
                msg = "İşlem başarılı.";
                title = "Başarılı!";
            }
            case "error" -> {
                msg = "İşlem başarısız.";
                title = "Hata!";
            }
            default -> {
                msg = str;
                title = "Bilgilendirme";
            }
        }
        JOptionPane.showMessageDialog(null,msg,title,JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean confirm(String str){
        String msg;
        optionPageTr();
        switch (str){
            case "sure" -> msg = "Bu işlemi gerçekleştirmek istediğinize emin misiniz?";
            default -> msg = str;
        }

        return JOptionPane.showConfirmDialog(null, msg, "Son Kararın mı?", JOptionPane.YES_NO_OPTION) == 0;

    }

    public static void optionPageTr(){
        UIManager.put("OptionPane.okButtonText","Tamam");
        UIManager.put("OptionPane.cancelButtonText","İptal");
        UIManager.put("OptionPane.yesButtonText","Evet");
        UIManager.put("OptionPane.noButtonText","Hayır");
    }


}
