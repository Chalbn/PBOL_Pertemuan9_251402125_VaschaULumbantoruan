package org.example;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class GaleriHewan extends JFrame {
    public GaleriHewan() {
        setTitle("Galeri Hewan");
        setSize(750, 480);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                konfirmasiKeluar();
            }
        });

        buatMenuBar();
        buatPanelHewan();
        buatTombolBawah();
    }

    private void buatMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("File");
        JMenuItem itemSapaSemua = new JMenuItem("Sapa Semua Hewan");
        itemSapaSemua.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        itemSapaSemua.addActionListener(e -> JOptionPane.showMessageDialog(this, "Semua hewan senang disapa olehmu!", "Message", JOptionPane.INFORMATION_MESSAGE));

        JMenuItem itemKeluar = new JMenuItem("Keluar");
        itemKeluar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
        itemKeluar.addActionListener(e -> konfirmasiKeluar());

        menuFile.add(itemSapaSemua);
        menuFile.addSeparator();
        menuFile.add(itemKeluar);

        JMenu menuBantuan = new JMenu("Bantuan");
        JMenuItem itemCara = new JMenuItem("Cara Interaksi");
        itemCara.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
        String petunjuk = "• Arahkan kursor (hover) ke gambar hewan untuk efek sorotan.\n"
                + "• Klik gambar hewan untuk menyapa masing-masing hewan.\n"
                + "• Klik tombol 'Sapa Mereka!' di bagian bawah untuk menyapa semuanya.";
        itemCara.addActionListener(e -> JOptionPane.showMessageDialog(this, petunjuk, "Petunjuk", JOptionPane.INFORMATION_MESSAGE));
        menuBantuan.add(itemCara);

        menuBar.add(menuFile);
        menuBar.add(menuBantuan);
        setJMenuBar(menuBar);
    }

    private void buatPanelHewan() {
        JPanel panelTengah = new JPanel(new GridLayout(1, 3, 15, 15));
        panelTengah.setBackground(Color.WHITE);
        panelTengah.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Hewan[] daftarHewan = {
                new Hewan("Anjing", "dog.jpg", "Guk Guk", "Guk! Aku anjing yang ramah, senang ketemu kamu!"),
                new Hewan("Kucing", "cat.jpg", "Meow Meow", "Meow~ Aku kucing manis, jangan lupa elus aku ya!"),
                new Hewan("Hamster", "hamster.jpg", "Ciittt Cittt", "Ciittt! Aku hamster mungil yang suka makan kuaci!")
        };

        for (Hewan h : daftarHewan) {
            panelTengah.add(buatKartu(h));
        }
        add(panelTengah, BorderLayout.CENTER);
    }

    private JPanel buatKartu(Hewan h) {
        JPanel kartu = new JPanel(new BorderLayout(0, 10));
        kartu.setBackground(Color.WHITE);
        JLabel lblGambar = new JLabel();
        lblGambar.setHorizontalAlignment(SwingConstants.CENTER);
        lblGambar.setVerticalAlignment(SwingConstants.CENTER);
        lblGambar.setPreferredSize(new Dimension(190, 190));
        lblGambar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblGambar.setToolTipText("Klik untuk menyapa " + h.nama);

        Border borderNormal = BorderFactory.createEmptyBorder(2, 2, 2, 2);
        Border borderHover = BorderFactory.createLineBorder(Color.BLUE, 2);
        lblGambar.setBorder(borderNormal);

        File file = new File(h.fileGambar);
        if (file.exists()) {
            ImageIcon ikon = new ImageIcon(h.fileGambar);
            Image imgResize = ikon.getImage().getScaledInstance(190, 190, Image.SCALE_SMOOTH);
            lblGambar.setIcon(new ImageIcon(imgResize));
        } else {
            lblGambar.setText("[" + h.fileGambar + " tidak ditemukan]");
            lblGambar.setOpaque(true);
            lblGambar.setBackground(Color.LIGHT_GRAY);
        }

        JLabel lblSuara = new JLabel(h.suara, SwingConstants.CENTER);
        lblSuara.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblSuara.setForeground(Color.BLACK);

        JPopupMenu popup = new JPopupMenu();
        JMenuItem itemSapa = new JMenuItem("Sapa " + h.nama);
        itemSapa.addActionListener(e -> JOptionPane.showMessageDialog(this, h.nama + " berkata: \"" + h.sapaan + "\"", "Sapa " + h.nama, JOptionPane.INFORMATION_MESSAGE));
        popup.add(itemSapa);
        lblGambar.setComponentPopupMenu(popup);

        lblGambar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                lblGambar.setBorder(borderHover);
                lblSuara.setForeground(Color.BLUE);
                lblSuara.setText(h.suara + "!");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                lblGambar.setBorder(borderNormal);
                lblSuara.setForeground(Color.BLACK);
                lblSuara.setText(h.suara);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    JOptionPane.showMessageDialog(GaleriHewan.this, h.nama + " berkata: \"" + h.sapaan + "\"", "Sapa " + h.nama, JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        kartu.add(lblGambar, BorderLayout.CENTER);
        kartu.add(lblSuara, BorderLayout.SOUTH);
        return kartu;
    }

    private void buatTombolBawah() {
        JButton btnSapa = new JButton("Sapa Mereka!");
        btnSapa.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnSapa.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        String pesan = "Halo teman-teman kecil!\nAnjing: Guk Guk!\nKucing: Meow Meow!\nHamster: Ciittt Cittt!";
        btnSapa.addActionListener(e -> JOptionPane.showMessageDialog(this, pesan, "Sapaan Terkirim", JOptionPane.INFORMATION_MESSAGE));
        add(btnSapa, BorderLayout.SOUTH);
    }

    private void konfirmasiKeluar() {
        int pilih = JOptionPane.showConfirmDialog(this, "Kamu yakin gamau sapa mereka :(", "Konfirmasi Keluar", JOptionPane.YES_NO_OPTION);
        if (pilih == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}