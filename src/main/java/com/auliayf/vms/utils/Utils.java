/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.auliayf.vms.utils;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author uoy
 */
public class Utils {

    private static final String BASE_URL = "http://localhost/vms/intranet/";

    public static String baseUrl(String url) {
        return BASE_URL + "api.php" + url;
    }

    public static String baseUrl2(String url) {
        return BASE_URL + url;
    }

    public static void info(JFrame form, String title, String content) {
        JOptionPane.showMessageDialog(form, content, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void warning(JFrame form, String title, String content) {
        JOptionPane.showMessageDialog(form, content, title, JOptionPane.WARNING_MESSAGE);
    }

    public static void error(JFrame form, String title, String content) {
        JOptionPane.showMessageDialog(form, content, title, JOptionPane.ERROR_MESSAGE);
    }

    public static void connectionError(JFrame form) {
        JOptionPane.showMessageDialog(form, "Unable to connect to the server", "Connection Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void centerForm(JFrame form) {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        form.setLocation(dim.width / 2 - form.getWidth() / 2, dim.height / 2 - form.getHeight() / 2);
    }

    public static String getCurrentDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("E, dd MMMM yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String formatDate(String datetime) {
        DateFormat origFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date origDate = origFormat.parse(datetime);
            DateFormat destFormat = new SimpleDateFormat("dd/MM/yyyy");
            return destFormat.format(origDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String formatTime(String datetime) {
        DateFormat origFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date origDate = origFormat.parse(datetime);
            DateFormat destFormat = new SimpleDateFormat("HH:mm");
            return destFormat.format(origDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }
}
