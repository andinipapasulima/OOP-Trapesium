package projek.trapesium;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class GUITrapesium extends JFrame {
    public JTextField txtAtas, txtBawah, txtTinggi, txtTinggi3D;
    public JComboBox<String> cmbJenis;
    public JTextArea txtHasil;
    public JButton btnHitung, btnReset;

    private final Color COLOR_PRIMARY    = new Color(41, 128, 185);
    private final Color COLOR_WARNING    = new Color(243, 156, 18);
    private final Color COLOR_BG_PANEL   = new Color(245, 247, 250);
    private final Color COLOR_TEXT_DARK  = new Color(44, 62, 80);

    public GUITrapesium() {
        setTitle("Kalkulator Geometri Trapesium Pro");
        setSize(480, 580);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel pnlMain = new JPanel(new BorderLayout(15, 15));
        pnlMain.setBackground(COLOR_BG_PANEL);
        pnlMain.setBorder(new EmptyBorder(15, 15, 15, 15));
        setContentPane(pnlMain);

        JPanel pnlInput = new JPanel(new GridBagLayout());
        pnlInput.setBackground(COLOR_BG_PANEL);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 6, 6, 6);

        Font fontLabel = new Font("Segoe UI", Font.BOLD, 12);
        Font fontField = new Font("Segoe UI", Font.PLAIN, 13);

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.3;
        pnlInput.add(createStyledLabel("Jenis Bangun", fontLabel), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.7;
        cmbJenis = new JComboBox<>(new String[]{"Trapesium 2D", "Prisma Trapesium", "Limas Trapesium"});
        cmbJenis.setFont(fontField);
        cmbJenis.setBackground(Color.WHITE);
        pnlInput.add(cmbJenis, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.3;
        pnlInput.add(createStyledLabel("Alas Atas (a)", fontLabel), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 0.7;
        txtAtas = createStyledTextField("6", fontField);
        pnlInput.add(txtAtas, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.3;
        pnlInput.add(createStyledLabel("Alas Bawah (b)", fontLabel), gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 0.7;
        txtBawah = createStyledTextField("10", fontField);
        pnlInput.add(txtBawah, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.3;
        pnlInput.add(createStyledLabel("Tinggi Alas (t)", fontLabel), gbc);
        gbc.gridx = 1; gbc.gridy = 3; gbc.weightx = 0.7;
        txtTinggi = createStyledTextField("4", fontField);
        pnlInput.add(txtTinggi, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0.3;
        pnlInput.add(createStyledLabel("Tinggi 3D", fontLabel), gbc);
        gbc.gridx = 1; gbc.gridy = 4; gbc.weightx = 0.7;
        txtTinggi3D = createStyledTextField("0", fontField);
        txtTinggi3D.setEnabled(false);
        txtTinggi3D.setBackground(new Color(210, 215, 222));
        pnlInput.add(txtTinggi3D, gbc);

        pnlMain.add(pnlInput, BorderLayout.NORTH);

        txtHasil = new JTextArea();
        txtHasil.setEditable(false);
        txtHasil.setFont(new Font("Consolas", Font.PLAIN, 13));
        txtHasil.setBackground(Color.WHITE);
        txtHasil.setForeground(COLOR_TEXT_DARK);
        txtHasil.setBorder(new CompoundBorder(
            new LineBorder(new Color(218, 223, 230), 1),
            new EmptyBorder(12, 12, 12, 12)
        ));
        txtHasil.setText("=========================================\n" +
                         "   Silakan isi input lalu tekan Hitung   \n" +
                         "=========================================");

        JScrollPane scrollPane = new JScrollPane(txtHasil);
        scrollPane.setBorder(null);
        pnlMain.add(scrollPane, BorderLayout.CENTER);

        JPanel pnlTombol = new JPanel(new GridLayout(1, 2, 15, 0));
        pnlTombol.setBackground(COLOR_BG_PANEL);

        Font fontTombol = new Font("Segoe UI", Font.BOLD, 13);

        btnHitung = new JButton("HITUNG DATA");
        btnHitung.setFont(fontTombol);
        btnHitung.setBackground(COLOR_PRIMARY);
        btnHitung.setForeground(Color.WHITE);
        btnHitung.setFocusPainted(false);
        btnHitung.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnReset = new JButton("RESET SYSTEM");
        btnReset.setFont(fontTombol);
        btnReset.setBackground(COLOR_WARNING);
        btnReset.setForeground(COLOR_TEXT_DARK);
        btnReset.setFocusPainted(false);
        btnReset.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnHitung.setPreferredSize(new Dimension(0, 40));
        btnReset.setPreferredSize(new Dimension(0, 40));

        pnlTombol.add(btnHitung);
        pnlTombol.add(btnReset);
        pnlMain.add(pnlTombol, BorderLayout.SOUTH);

        cmbJenis.addActionListener(e -> {
            String pilihan = (String) cmbJenis.getSelectedItem();
            if (pilihan.equals("Trapesium 2D")) {
                txtTinggi3D.setText("0");
                txtTinggi3D.setEnabled(false);
                txtTinggi3D.setBackground(new Color(210, 215, 222));
            } else {
                if (txtTinggi3D.getText().equals("0")) txtTinggi3D.setText("8");
                txtTinggi3D.setEnabled(true);
                txtTinggi3D.setBackground(Color.WHITE);
            }
        });

        btnHitung.addActionListener(e -> {
            try {
                double aa   = Double.parseDouble(txtAtas.getText());
                double ab   = Double.parseDouble(txtBawah.getText());
                double t    = Double.parseDouble(txtTinggi.getText());
                double t3d = Double.parseDouble(txtTinggi3D.getText());
                String jenis = (String) cmbJenis.getSelectedItem();

                if (aa < 0 || ab < 0 || t < 0 || t3d < 0) {
                    throw new IllegalArgumentException("Dimensi bangunan tidak boleh bernilai negatif (minus)!");
                }
                if (ab <= aa) {
                    throw new IllegalArgumentException("Alas Bawah harus lebih besar dari Alas Atas!");
                }

                // =================================================================
                // SOLUSI 1 & 2: Deklarasi Polimorfisme dan Instansiasi Bangun
                // =================================================================
                BangunGeometri bangun = null;

                if (jenis.equals("Trapesium 2D")) {
                    bangun = new Trapesium("Trapesium 2D", aa, ab, t);
                } else if (jenis.equals("Prisma Trapesium")) {
                    bangun = new PrismaTrapesium("Prisma Trapesium", aa, ab, t, t3d);
                } else if (jenis.equals("Limas Trapesium")) {
                    bangun = new LimasTrapesium("Limas Trapesium", aa, ab, t, t3d);
                }

                // Jalankan perhitungan melalui Multithreading (sesuai arsitektur proyekmu)
                Thread thread = new Thread(bangun);
                thread.start();
                thread.join(); // Menunggu thread selesai menghitung sebelum mencetak hasil
                // =================================================================

                // Bagian mencetak string ke txtHasil
                StringBuilder sb = new StringBuilder();
                sb.append("=========================================\n");
                sb.append("         HASIL PERHITUNGAN GEOMETRI       \n");
                sb.append("=========================================\n");
                sb.append(String.format(" Nama Objek     : %s\n", jenis)); 

                // Deteksi objek secara spesifik menggunakan instanceof
                if (bangun instanceof PrismaTrapesium) {
                    PrismaTrapesium prisma = (PrismaTrapesium) bangun;
                    sb.append(String.format(" ↳ Luas Alas (2D): %.2f\n", prisma.luas)); // Luas Trapesium asli aman
                    sb.append(String.format(" ↳ Keliling Alas : %.2f\n", prisma.keliling));
                    sb.append(String.format(" ↳ Volume Bangun : %.2f\n", prisma.volume));
                    sb.append(String.format(" ↳ Luas Permukaan: %.2f\n", prisma.luasPermukaan)); // Variabel baru khusus 3D
                } 
                else if (bangun instanceof LimasTrapesium) {
                    LimasTrapesium limas = (LimasTrapesium) bangun;
                    sb.append(String.format(" ↳ Luas Alas (2D): %.2f\n", limas.luas)); // Luas Trapesium asli aman
                    sb.append(String.format(" ↳ Keliling Alas : %.2f\n", limas.keliling));
                    sb.append(String.format(" ↳ Volume Bangun : %.2f\n", limas.volume));
                    sb.append(String.format(" ↳ Luas Permukaan: %.2f\n", limas.luasPermukaan)); // Variabel baru khusus 3D
                } 
                else if (bangun instanceof Trapesium) {
                    Trapesium trapesium = (Trapesium) bangun;
                    sb.append(String.format(" ↳ Luas Objek 2D : %.2f\n", trapesium.luas));
                    sb.append(String.format(" ↳ Keliling Objek: %.2f\n", trapesium.keliling));
                }
                sb.append("=========================================");
                txtHasil.setText(sb.toString());

            } catch (NumberFormatException ex) {
                txtHasil.setText("⚠️ ERROR: Mohon masukkan nilai angka yang valid!");
            } catch (IllegalArgumentException ex) {
                txtHasil.setText("⚠️ VALIDASI GAGAL: " + ex.getMessage());
            } catch (InterruptedException ex) {
                txtHasil.setText("⚠️ ERROR: Eksekusi thread terganggu.");
            }
        });

        btnReset.addActionListener(e -> {
            txtAtas.setText("");
            txtBawah.setText("");
            txtTinggi.setText("");
            cmbJenis.setSelectedIndex(0);
            txtTinggi3D.setText("0");
            txtHasil.setText("=========================================\n" +
                             "   Silakan isi input lalu tekan Hitung   \n" +
                             "=========================================");
        });
    }

    private JLabel createStyledLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(COLOR_TEXT_DARK);
        return label;
    }

    private JTextField createStyledTextField(String defaultText, Font font) {
        JTextField textField = new JTextField(defaultText);
        textField.setFont(font);
        textField.setBackground(Color.WHITE);
        textField.setBorder(new CompoundBorder(
            new LineBorder(new Color(200, 205, 215), 1),
            new EmptyBorder(6, 8, 6, 8)
        ));
        return textField;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GUITrapesium().setVisible(true));
    }
}