package projek.trapesium;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class GUITrapesium extends JFrame {

    // Komponen Navigasi Utama
    private JPanel cardContainer;
    private CardLayout cardLayout;
    private JButton btnNavHome, btnNavManual, btnNavThreads;

    // Field Input Manual
    private JTextField txtAtas, txtBawah, txtTinggi, txtKiri, txtKanan, txtTinggi3D;
    private JComboBox<String> cmbJenisBangun;
    private JTextArea txtConsoleHasil;
    private final DecimalFormat df = new DecimalFormat("#.##");

    // Komponen Multithreading
    private JSpinner spinnerBatchSize;
    private JButton btnJalankanBatch, btnBersihkanBatch;
    private JProgressBar batchProgressBar;
    private JLabel lblStatusBatch;
    private JTextArea txtThreadLogConsole;
    private DefaultTableModel tableModelHasil;
    private ThreadCPUVisualizer cpuVisualizerPanel;

    public GUITrapesium() {
        // Setup Window Dashboard
        setTitle("Trapezium Geometry Console & Parallel Engine");
        setSize(1100, 750);
        setMinimumSize(new Dimension(1000, 680));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(AppTheme.BG_DARK);
        setLayout(new BorderLayout());

        // 1. Render Top Header & Navigation Bar
        add(buatTopBarHeader(), BorderLayout.NORTH);

        // 2. Render Panel Konten dengan CardLayout
        cardLayout = new CardLayout();
        cardContainer = new JPanel(cardLayout);
        cardContainer.setBackground(AppTheme.BG_DARK);

        cardContainer.add(buatPanelHomeDashboard(), "home");
        cardContainer.add(buatPanelManualCalculator(), "manual");
        cardContainer.add(buatPanelThreadingEngine(), "threads");

        add(cardContainer, BorderLayout.CENTER);

        // Navigasi Awal ke Home
        pilihNavigasi("home", btnNavHome);
    }

    // ────────────────────────────────────────────────────────────
    // KOMPONEN: Header & Navigation Bar Atas
    // ────────────────────────────────────────────────────────────
    private JPanel buatTopBarHeader() {
        JPanel pnlHeader = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Gradasi warna gelap ke ungu tua
                GradientPaint gp = new GradientPaint(0, 0, AppTheme.BG_HEADER, getWidth(), 0, AppTheme.BG_DARK);
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                // Garis aksen bawah
                g2.setColor(AppTheme.ACCENT_BORDER);
                g2.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
                g2.dispose();
            }
        };
        pnlHeader.setPreferredSize(new Dimension(0, 75));
        pnlHeader.setLayout(new BorderLayout(20, 0));
        pnlHeader.setBorder(new EmptyBorder(10, 20, 10, 20));

        // Judul Aplikasi
        JPanel pnlTitle = new JPanel(new GridLayout(2, 1, 2, 2));
        pnlTitle.setOpaque(false);
        JLabel lblTitle = new JLabel("TRAPEZIUM GEOMETRY ENGINE");
        lblTitle.setFont(AppTheme.FONT_TITLE);
        lblTitle.setForeground(AppTheme.TEXT_BRIGHT);
        JLabel lblSub = new JLabel("Advanced OOP Architecture & Multi-threading Engine v2.0");
        lblSub.setFont(AppTheme.FONT_BODY);
        lblSub.setForeground(AppTheme.TEXT_MUTED);
        pnlTitle.add(lblTitle);
        pnlTitle.add(lblSub);
        pnlHeader.add(pnlTitle, BorderLayout.WEST);

        // Tombol Navigasi
        JPanel pnlNav = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 8));
        pnlNav.setOpaque(false);

        btnNavHome = UIHelper.buatTombolNavigasi("BERANDA DASHBOARD");
        btnNavManual = UIHelper.buatTombolNavigasi("KALKULATOR MANUAL");
        btnNavThreads = UIHelper.buatTombolNavigasi("PARALLEL THREADS");

        btnNavHome.addActionListener(e -> pilihNavigasi("home", btnNavHome));
        btnNavManual.addActionListener(e -> pilihNavigasi("manual", btnNavManual));
        btnNavThreads.addActionListener(e -> pilihNavigasi("threads", btnNavThreads));

        pnlNav.add(btnNavHome);
        pnlNav.add(btnNavManual);
        pnlNav.add(btnNavThreads);
        pnlHeader.add(pnlNav, BorderLayout.EAST);

        return pnlHeader;
    }

    private void pilihNavigasi(String cardName, JButton activeBtn) {
        cardLayout.show(cardContainer, cardName);
        
        // Reset warna semua tombol navigasi
        btnNavHome.setForeground(AppTheme.TEXT_MUTED);
        btnNavManual.setForeground(AppTheme.TEXT_MUTED);
        btnNavThreads.setForeground(AppTheme.TEXT_MUTED);
        btnNavHome.setBackground(new Color(0,0,0,0));
        btnNavManual.setBackground(new Color(0,0,0,0));
        btnNavThreads.setBackground(new Color(0,0,0,0));

        // Set warna tombol aktif
        activeBtn.setForeground(AppTheme.ACCENT_AMBER);
        activeBtn.setBackground(new Color(AppTheme.ACCENT_AMBER.getRed(), AppTheme.ACCENT_AMBER.getGreen(), AppTheme.ACCENT_AMBER.getBlue(), 30));
    }

    // ────────────────────────────────────────────────────────────
    // CARD 1: Panel Home / Beranda Dashboard
    // ────────────────────────────────────────────────────────────
    private JPanel buatPanelHomeDashboard() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(AppTheme.BG_DARK);
        panel.setBorder(new EmptyBorder(30, 40, 30, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Card Info 1: Abstraction & Polymorphism
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.5; gbc.weighty = 0.5;
        panel.add(UIHelper.buatPanelKartuInfo("STRUKTUR ABSTRAKSI & POLIMORFISME",
            "<html>Program ini mengimplementasikan kelas abstrak induk <b>BangunGeometri</b> sebagai blueprint universal.<br><br>"
            + "Dengan konsep ini, GUI dapat mengelola objek apa pun (Trapesium, Prisma, atau Limas) secara dinamis "
            + "hanya dengan satu deklarasi variabel induk saja. Konsep ini menjamin kode tetap modular dan fleksibel.</html>"), gbc);

        // Card Info 2: Multilevel Inheritance
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.5; gbc.weighty = 0.5;
        panel.add(UIHelper.buatPanelKartuInfo("KONSEP INHERITANCE BERTINGKAT",
            "<html>Kelas 3D seperti <b>PrismaTrapesium</b> dan <b>LimasTrapesium</b> langsung mewarisi kelas <b>Trapesium 2D</b>.<br><br>"
            + "Konsep pewarisan ini memangkas redundansi penulisan kode (Code Reuse). "
            + "Sisi-sisi dasar dan luas alas trapesium langsung dihitung menggunakan metode <i>super.hitung()</i> dari kelas induk.</html>"), gbc);

        // Card Info 3: Parallel Threading
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.5; gbc.weighty = 0.5;
        panel.add(UIHelper.buatPanelKartuInfo("SISTEM ASYNCHRONOUS MULTITHREADING",
            "<html>Proses perhitungan rumit dilarang keras dijalankan pada Main Thread (UI Event Thread) agar aplikasi tidak macet (Not Responding).<br><br>"
            + "Aplikasi ini memindahkan tugas berat perhitungan rumus geometri ke <b>Worker Thread</b> di latar belakang secara parallel menggunakan kontrak Runnable.</html>"), gbc);

        // Card Info 4: Method Overloading & Overriding
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 0.5; gbc.weighty = 0.5;
        panel.add(UIHelper.buatPanelKartuInfo("POLYMORPHISM: OVERRIDING vs OVERLOADING",
            "<html>Aplikasi ini mengimplementasikan kedua cabang polimorfisme:<br><br>"
            + "1. <b>Overriding (Dinamis):</b> Menulis ulang fungsi <i>hitung()</i> induk sesuai sifat bangun anak.<br>"
            + "2. <b>Overloading (Statis):</b> Menyediakan fungsi <i>hitung(parameter...)</i> untuk memproses input langsung secara fleksibel.</html>"), gbc);

        return panel;
    }

    // ────────────────────────────────────────────────────────────
    // CARD 2: Panel Kalkulator Manual
    // ────────────────────────────────────────────────────────────
    private JPanel buatPanelManualCalculator() {
        JPanel panel = new JPanel(new BorderLayout(20, 0));
        panel.setBackground(AppTheme.BG_DARK);
        panel.setBorder(new EmptyBorder(20, 25, 20, 25));

        // Panel Sisi Kiri: Form Input
        JPanel pnlLeftForm = UIHelper.buatPanelKustomKartu(new GridBagLayout());
        pnlLeftForm.setPreferredSize(new Dimension(380, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 10, 8, 10);

        // Row 0: Combo Box Pilihan Bangun
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.35;
        pnlLeftForm.add(UIHelper.buatLabelForm("Pilih Bangun:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.65;
        cmbJenisBangun = new JComboBox<>(new String[]{"Trapesium 2D", "Prisma Trapesium", "Limas Trapesium"});
        cmbJenisBangun.setFont(AppTheme.FONT_BODY);
        pnlLeftForm.add(cmbJenisBangun, gbc);

        // Inisialisasi Field
        txtAtas = UIHelper.buatTextField("6");
        txtBawah = UIHelper.buatTextField("10");
        txtTinggi = UIHelper.buatTextField("4");
        txtKiri = UIHelper.buatTextField("5");
        txtKanan = UIHelper.buatTextField("5");
        txtTinggi3D = UIHelper.buatTextField("0");
        txtTinggi3D.setEnabled(false);
        txtTinggi3D.setBackground(AppTheme.BG_HEADER);

        // Row 1-6: Atribut Dimensi
        tambahRowForm(pnlLeftForm, "Sisi Atas (a):", txtAtas, gbc, 1);
        tambahRowForm(pnlLeftForm, "Sisi Bawah (b):", txtBawah, gbc, 2);
        tambahRowForm(pnlLeftForm, "Tinggi Alas (t):", txtTinggi, gbc, 3);
        tambahRowForm(pnlLeftForm, "Sisi Kiri:", txtKiri, gbc, 4);
        tambahRowForm(pnlLeftForm, "Sisi Kanan:", txtKanan, gbc, 5);
        tambahRowForm(pnlLeftForm, "Tinggi 3D (tp/tl):", txtTinggi3D, gbc, 6);

        // Event Listener Combo Box
        cmbJenisBangun.addActionListener(e -> {
            String pilihan = (String) cmbJenisBangun.getSelectedItem();
            if ("Trapesium 2D".equals(pilihan)) {
                txtTinggi3D.setText("0");
                txtTinggi3D.setEnabled(false);
                txtTinggi3D.setBackground(AppTheme.BG_HEADER);
            } else {
                if ("0".equals(txtTinggi3D.getText())) txtTinggi3D.setText("8");
                txtTinggi3D.setEnabled(true);
                txtTinggi3D.setBackground(AppTheme.BG_CARD);
            }
        });

        // Panel Sisi Kanan: Live Terminal Monitor
        JPanel pnlTerminal = new JPanel(new BorderLayout(0, 10));
        pnlTerminal.setOpaque(false);

        JLabel lblMonitorTitle = new JLabel(" LIVE ENGINE MONITOR");
        lblMonitorTitle.setFont(AppTheme.FONT_HEADING);
        lblMonitorTitle.setForeground(AppTheme.ACCENT_AMBER);
        pnlTerminal.add(lblMonitorTitle, BorderLayout.NORTH);

        txtConsoleHasil = new JTextArea();
        txtConsoleHasil.setEditable(false);
        txtConsoleHasil.setFont(AppTheme.FONT_MONO);
        txtConsoleHasil.setBackground(AppTheme.BG_CARD);
        txtConsoleHasil.setForeground(AppTheme.TEXT_BRIGHT);
        txtConsoleHasil.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JScrollPane scrollConsole = new JScrollPane(txtConsoleHasil);
        scrollConsole.setBorder(BorderFactory.createLineBorder(AppTheme.ACCENT_BORDER, 1));
        pnlTerminal.add(scrollConsole, BorderLayout.CENTER);

        // Panel Tombol Manual Aksi di Bawah Form Kiri
        JPanel pnlAksiKiri = new JPanel(new GridLayout(1, 2, 10, 0));
        pnlAksiKiri.setOpaque(false);
        JButton btnHitungManual = UIHelper.buatTombolAksi("HITUNG BANGUN", AppTheme.ACCENT_VIOLET);
        JButton btnResetManual = UIHelper.buatTombolAksi("RESET SYSTEM", AppTheme.ACCENT_RED);
        pnlAksiKiri.add(btnHitungManual);
        pnlAksiKiri.add(btnResetManual);

        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 10, 5, 10);
        pnlLeftForm.add(pnlAksiKiri, gbc);

        // Event Listener Manual Aksi
        btnHitungManual.addActionListener(e -> eksekusiKalkulasiSatuObjek());
        btnResetManual.addActionListener(e -> resetFormKalkulator());

        panel.add(pnlLeftForm, BorderLayout.WEST);
        panel.add(pnlTerminal, BorderLayout.CENTER);

        // Log Selamat Datang di Console
        cetakSelamatDatangConsole();

        return panel;
    }

    private void tambahRowForm(JPanel panel, String label, JTextField field, GridBagConstraints gbc, int row) {
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.35; gbc.gridwidth = 1;
        panel.add(UIHelper.buatLabelForm(label), gbc);
        gbc.gridx = 1; gbc.weightx = 0.65;
        panel.add(field, gbc);
    }

    // ────────────────────────────────────────────────────────────
    // CARD 3: Panel Threading Engine
    // ────────────────────────────────────────────────────────────
    private JPanel buatPanelThreadingEngine() {
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setBackground(AppTheme.BG_DARK);
        panel.setBorder(new EmptyBorder(15, 20, 15, 20));

        // 3a. Control Bar di Bagian Atas Panel Threading
        JPanel pnlControl = UIHelper.buatPanelKustomKartu(new FlowLayout(FlowLayout.LEFT, 15, 8));
        pnlControl.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        pnlControl.add(UIHelper.buatLabelForm("Jumlah Batch Pekerjaan:"));
        
        spinnerBatchSize = new JSpinner(new SpinnerNumberModel(6, 1, 1000, 1));
        spinnerBatchSize.setPreferredSize(new Dimension(80, 28));
        pnlControl.add(spinnerBatchSize);

        btnJalankanBatch = UIHelper.buatTombolAksi("JALANKAN ENGINE", AppTheme.ACCENT_AMBER);
        btnBersihkanBatch = UIHelper.buatTombolAksi("BERSIHKAN LOG", AppTheme.ACCENT_RED);

        pnlControl.add(btnJalankanBatch);
        pnlControl.add(btnBersihkanBatch);
        panel.add(pnlControl, BorderLayout.NORTH);

        // Event Listener Threading
        btnJalankanBatch.addActionListener(e -> jalankanProsesMultithreadingBatch());
        btnBersihkanBatch.addActionListener(e -> bersihkanProsesThreading());

        // 3b. Split Pane Tengah: Kiri (Visualizer Core) & Kanan (Logger)
        JSplitPane splitTengah = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitTengah.setOpaque(false);
        splitTengah.setDividerSize(6);
        splitTengah.setResizeWeight(0.65);
        splitTengah.setBorder(null);

        // Kiri: CPU Core Visualizer
        cpuVisualizerPanel = new ThreadCPUVisualizer();
        JScrollPane scrollVisualizer = new JScrollPane(cpuVisualizerPanel);
        scrollVisualizer.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(AppTheme.ACCENT_BORDER), " CPU CORES WORKLOAD (REAL-TIME) ", 
            TitledBorder.LEFT, TitledBorder.TOP, AppTheme.FONT_LABEL, AppTheme.TEXT_MUTED
        ));
        scrollVisualizer.getViewport().setBackground(AppTheme.BG_DARK);
        splitTengah.setLeftComponent(scrollVisualizer);

        // Kanan: Realtime Logger Terminal
        JPanel pnlLogger = new JPanel(new BorderLayout(0, 5));
        pnlLogger.setOpaque(false);
        pnlLogger.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(AppTheme.ACCENT_BORDER), " PARALLEL THREADS LOGGER ", 
            TitledBorder.LEFT, TitledBorder.TOP, AppTheme.FONT_LABEL, AppTheme.TEXT_MUTED
        ));

        txtThreadLogConsole = new JTextArea();
        txtThreadLogConsole.setEditable(false);
        txtThreadLogConsole.setFont(AppTheme.FONT_MONO);
        txtThreadLogConsole.setBackground(AppTheme.BG_CARD);
        txtThreadLogConsole.setForeground(new Color(110, 231, 183)); // Green Neon
        txtThreadLogConsole.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JScrollPane scrollLoggerConsole = new JScrollPane(txtThreadLogConsole);
        scrollLoggerConsole.setBorder(null);
        pnlLogger.add(scrollLoggerConsole, BorderLayout.CENTER);
        splitTengah.setRightComponent(pnlLogger);

        // 3c. Split Pane Bawah: JTable database riwayat log
        JPanel pnlBawah = new JPanel(new BorderLayout(0, 5));
        pnlBawah.setOpaque(false);
        pnlBawah.setPreferredSize(new Dimension(0, 180));
        pnlBawah.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(AppTheme.ACCENT_BORDER), " RIWAYAT ENGINE BATCH LOG DATABASE ", 
            TitledBorder.LEFT, TitledBorder.TOP, AppTheme.FONT_LABEL, AppTheme.TEXT_MUTED
        ));

        String[] headerTabel = {
            "#ID", "Bangun Ruang/Datar", "Sisi Atas", "Sisi Bawah", "Tinggi Alas", "Tinggi 3D",
            "Luas Alas", "Keliling Alas", "Volume", "Luas Permukaan", "Diproses Oleh", "Durasi"
        };
        tableModelHasil = new DefaultTableModel(headerTabel, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tableLog = new JTable(tableModelHasil);
        styleDatabaseTable(tableLog);

        JScrollPane scrollTable = new JScrollPane(tableLog);
        scrollTable.getViewport().setBackground(AppTheme.BG_CARD);
        scrollTable.setBorder(null);
        pnlBawah.add(scrollTable, BorderLayout.CENTER);

        // Status dan Progress Bar di Paling Bawah
        JPanel pnlStatusProgress = new JPanel(new BorderLayout(15, 0));
        pnlStatusProgress.setOpaque(false);
        pnlStatusProgress.setBorder(new EmptyBorder(5, 5, 5, 5));

        batchProgressBar = new JProgressBar(0, 100);
        batchProgressBar.setStringPainted(true);
        batchProgressBar.setForeground(AppTheme.ACCENT_AMBER);
        batchProgressBar.setBackground(AppTheme.BG_HEADER);
        batchProgressBar.setPreferredSize(new Dimension(0, 20));

        lblStatusBatch = new JLabel("Engine Siap Dijalankan.");
        lblStatusBatch.setFont(AppTheme.FONT_LABEL);
        lblStatusBatch.setForeground(AppTheme.TEXT_MUTED);

        pnlStatusProgress.add(batchProgressBar, BorderLayout.CENTER);
        pnlStatusProgress.add(lblStatusBatch, BorderLayout.EAST);
        pnlBawah.add(pnlStatusProgress, BorderLayout.SOUTH);

        // Gabungkan ke Layout Utama
        JSplitPane splitUtama = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splitTengah, pnlBawah);
        splitUtama.setOpaque(false);
        splitUtama.setDividerSize(6);
        splitUtama.setResizeWeight(0.65);
        splitUtama.setBorder(null);

        panel.add(splitUtama, BorderLayout.CENTER);
        return panel;
    }

    private void styleDatabaseTable(JTable table) {
        // Kustomisasi Renderer Baris Data Tabel (Biar tetap kontras gelap dan teks terang)
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, column);
                if (isSelected) {
                    setBackground(AppTheme.ACCENT_VIOLET);
                    setForeground(Color.WHITE);
                } else {
                    setBackground(AppTheme.BG_CARD);
                    setForeground(AppTheme.TEXT_BRIGHT);
                }
                setFont(AppTheme.FONT_MONO);
                setHorizontalAlignment(JLabel.CENTER);
                setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
                return this;
            }
        });

        table.setBackground(AppTheme.BG_CARD);
        table.setForeground(AppTheme.TEXT_BRIGHT);
        table.setGridColor(AppTheme.BG_HEADER);
        table.setRowHeight(25);
        table.setFont(AppTheme.FONT_MONO);
        table.setSelectionBackground(AppTheme.ACCENT_VIOLET);
        table.setSelectionForeground(Color.WHITE);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(true);

        // Kustomisasi Eksplisit JTableHeader (Header #ID, Sisi Atas, dll.)
        JTableHeader h = table.getTableHeader();
        h.setFont(AppTheme.FONT_LABEL);
        h.setReorderingAllowed(false);
        
        // Memaksa Header Tabel Menggunakan Skema Warna Gelap / Kontras Tinggi di Semua Look and Feel OS
        h.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, column);
                setBackground(AppTheme.BG_HEADER);       // Latar Belakang Violet-Slate Gelap
                setForeground(AppTheme.ACCENT_AMBER);     // Teks Column dibuat AMBER ORANGE (GELAP/KONTRAS) agar terbaca jelas
                setFont(AppTheme.FONT_LABEL);             // Tebal & Rapi
                setHorizontalAlignment(JLabel.CENTER);
                setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, AppTheme.ACCENT_BORDER));
                return this;
            }
        });

        // Kustomisasi Lebar Kolom
        int[] widths = {40, 150, 65, 75, 75, 75, 80, 85, 85, 95, 120, 65};
        for (int i = 0; i < widths.length && i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
        }
    }

    // ────────────────────────────────────────────────────────────
    // LOGIKA PERHITUNGAN: Manual Input & Event Handling
    // ────────────────────────────────────────────────────────────
    private void eksekusiKalkulasiSatuObjek() {
        try {
            double a = Double.parseDouble(txtAtas.getText().trim());
            double b = Double.parseDouble(txtBawah.getText().trim());
            double t = Double.parseDouble(txtTinggi.getText().trim());
            double ki = Double.parseDouble(txtKiri.getText().trim());
            double ka = Double.parseDouble(txtKanan.getText().trim());
            double t3d = Double.parseDouble(txtTinggi3D.getText().trim());

            String pilihan = (String) cmbJenisBangun.getSelectedItem();

            // Aturan matematika dasar trapesium
            if (a <= 0 || b <= 0 || t <= 0 || ki <= 0 || ka <= 0) {
                throw new IllegalArgumentException("Dimensi tidak boleh kurang dari atau sama dengan nol!");
            }

            long startWaktu = System.currentTimeMillis();

            // Variabel Polimorfisme Dinamis
            final BangunGeometri bangun;
            if ("Trapesium 2D".equals(pilihan)) {
                bangun = new Trapesium("Trapesium 2D Manual", a, b, t, ki, ka);
            } else if ("Prisma Trapesium".equals(pilihan)) {
                if (t3d <= 0) throw new IllegalArgumentException("Tinggi Prisma harus lebih besar dari nol!");
                bangun = new PrismaTrapesium("Prisma Trapesium Manual", a, b, t, ki, ka, t3d);
            } else {
                if (t3d <= 0) throw new IllegalArgumentException("Tinggi Limas harus lebih besar dari nol!");
                bangun = new LimasTrapesium("Limas Trapesium Manual", a, b, t, ki, ka, t3d);
            }

            // Jalankan Menggunakan Thread Tunggal (Asynchronous)
            Thread threadPekerja = new Thread(bangun, "Manual-Worker-Thread");
            threadPekerja.start();
            threadPekerja.join(); // Tunggu thread selesai memproses

            long durasi = System.currentTimeMillis() - startWaktu;
            cetakHasilKeConsole(bangun, durasi);

        } catch (NumberFormatException ex) {
            txtConsoleHasil.setText("⚠️ VALIDASI GAGAL:\nMohon isi seluruh field dengan format angka desimal yang benar!");
        } catch (IllegalArgumentException ex) {
            txtConsoleHasil.setText("⚠️ KETENTUAN GEOMETRI MELANGGAR:\n" + ex.getMessage());
        } catch (InterruptedException ex) {
            txtConsoleHasil.setText("⚠️ SYSTEM THREAD ERROR:\nProses perhitungan background terputus!");
        }
    }

    private void cetakHasilKeConsole(BangunGeometri bangun, long durasi) {
        StringBuilder sb = new StringBuilder();
        sb.append("========================================================\n");
        sb.append("              ENGINE CALCULATION VERDICT                \n");
        sb.append("========================================================\n");
        sb.append(String.format(" ID Object       : %s\n", bangun.getNama()));
        sb.append(String.format(" Diproses Oleh   : %s\n", "Manual-Worker-Thread"));
        sb.append(String.format(" Durasi Proses   : %d ms\n", durasi));
        sb.append("--------------------------------------------------------\n");

        if (bangun instanceof Trapesium) {
            Trapesium trap = (Trapesium) bangun;
            sb.append(String.format(" Sisi Alas Atas (a)  : %s m\n", df.format(trap.atas)));
            sb.append(String.format(" Sisi Alas Bawah (b) : %s m\n", df.format(trap.bawah)));
            sb.append(String.format(" Tinggi Alas (t)     : %s m\n", df.format(trap.tinggi)));
            sb.append(String.format(" Sisi Miring Kiri    : %s m\n", df.format(trap.kiri)));
            sb.append(String.format(" Sisi Miring Kanan   : %s m\n", df.format(trap.kanan)));
            sb.append("--------------------------------------------------------\n");
            sb.append(String.format(" Hasil Luas Alas     : %s m²\n", df.format(trap.luas)));
            sb.append(String.format(" Hasil Keliling Alas : %s m\n", df.format(trap.keliling)));
        }

        if (bangun instanceof PrismaTrapesium) {
            PrismaTrapesium prisma = (PrismaTrapesium) bangun;
            sb.append(String.format(" Tinggi Bangun Ruang : %s m\n", df.format(prisma.tinggiPrisma)));
            sb.append("--------------------------------------------------------\n");
            sb.append(String.format(" Hasil Volume        : %s m³\n", df.format(prisma.volume)));
            sb.append(String.format(" Hasil Luas Permukaan: %s m²\n", df.format(prisma.luasPermukaan)));
        } else if (bangun instanceof LimasTrapesium) {
            LimasTrapesium limas = (LimasTrapesium) bangun;
            sb.append(String.format(" Tinggi Bangun Ruang : %s m\n", df.format(limas.tinggiLimas)));
            sb.append("--------------------------------------------------------\n");
            sb.append(String.format(" Hasil Volume        : %s m³\n", df.format(limas.volume)));
            sb.append(String.format(" Hasil Luas Permukaan: %s m²\n", df.format(limas.luasPermukaan)));
            sb.append(String.format(" Apotema Samping A   : %s m (Depan/Belakang)\n", df.format(limas.apotemaDepanBelakang)));
            sb.append(String.format(" Apotema Samping B   : %s m (Kiri/Kanan)\n", df.format(limas.apotemaKiriKanan)));
        }

        sb.append("========================================================");
        txtConsoleHasil.setText(sb.toString());
    }

    private void resetFormKalkulator() {
        txtAtas.setText("");
        txtBawah.setText("");
        txtTinggi.setText("");
        txtKiri.setText("");
        txtKanan.setText("");
        txtTinggi3D.setText("0");
        cmbJenisBangun.setSelectedIndex(0);
        cetakSelamatDatangConsole();
    }

    private void cetakSelamatDatangConsole() {
        txtConsoleHasil.setText("========================================================\n" +
                               "             Aplikasi Siap Menghitung.                  \n" +
                               "========================================================\n" +
                               " Silakan isi formulir parameter dimensi geometri pada\n" +
                               " panel sebelah kiri secara akurat, kemudian ketuk\n" +
                               " tombol 'HITUNG BANGUN' untuk memicu parallel execution.");
    }

    // ────────────────────────────────────────────────────────────
    // LOGIKA PERHITUNGAN: Batch Multithreading Engine
    // ────────────────────────────────────────────────────────────
    private void jalankanProsesMultithreadingBatch() {
        int targetJobs = (int) spinnerBatchSize.getValue();

        cpuVisualizerPanel.clearVisualizer();
        txtThreadLogConsole.setText("");
        tableModelHasil.setRowCount(0);
        batchProgressBar.setValue(0);
        lblStatusBatch.setText("Menginisialisasi " + targetJobs + " jobs...");
        btnJalankanBatch.setEnabled(false);

        Random randomGenerator = new Random();
        int poolSize = Math.min(targetJobs, 4); // Alokasikan maks 4 Core Thread paralel
        cpuVisualizerPanel.inisialisasiThreadCore(poolSize, targetJobs);

        ExecutorService threadExecutor = Executors.newFixedThreadPool(poolSize);
        AtomicInteger jumlahJobSelesai = new AtomicInteger(0);
        long waktuMulaiGlobal = System.currentTimeMillis();

        for (int i = 0; i < targetJobs; i++) {
            final int jobIndex = i + 1;
            final int tipeBangun = i % 3; // Rotasi tipe: 0=2D, 1=Prisma, 2=Limas

            // Generate dimensi acak yang aman (>0 dan Sisi bawah > Sisi atas)
            final double atas = Math.round((randomGenerator.nextDouble() * 8 + 2) * 10.0) / 10.0;
            final double bawah = Math.round((randomGenerator.nextDouble() * 8 + atas + 1.0) * 10.0) / 10.0;
            final double tinggi = Math.round((randomGenerator.nextDouble() * 6 + 2) * 10.0) / 10.0;
            final double kiri = Math.round((randomGenerator.nextDouble() * 5 + 2) * 10.0) / 10.0;
            final double kanan = Math.round((randomGenerator.nextDouble() * 5 + 2) * 10.0) / 10.0;
            final double extra3D = Math.round((randomGenerator.nextDouble() * 8 + 3) * 10.0) / 10.0;

            threadExecutor.submit(() -> {
                String threadName = Thread.currentThread().getName();
                int coreId = dapatkanCoreIndex(threadName, poolSize);
                long startJobTime = System.currentTimeMillis();

                // Animasi visual Core bekerja
                SwingUtilities.invokeLater(() -> cpuVisualizerPanel.setCoreActive(coreId, jobIndex, tipeBangun));

                // Instansiasi Model Geometri Polimorfisme
                String namaBangun;
                double luasAlas, kelilingAlas, volume = 0, lp = 0;

                if (tipeBangun == 0) {
                    namaBangun = "Trapesium 2D";
                    Trapesium t = new Trapesium("Batch Trapesium #" + jobIndex, atas, bawah, tinggi, kiri, kanan);
                    t.hitung();
                    luasAlas = t.luas;
                    kelilingAlas = t.keliling;
                } else if (tipeBangun == 1) {
                    namaBangun = "Prisma Trapesium";
                    PrismaTrapesium pr = new PrismaTrapesium("Batch Prisma #" + jobIndex, atas, bawah, tinggi, kiri, kanan, extra3D);
                    pr.hitung();
                    luasAlas = pr.luas;
                    kelilingAlas = pr.keliling;
                    volume = pr.volume;
                    lp = pr.luasPermukaan;
                } else {
                    namaBangun = "Limas Trapesium";
                    LimasTrapesium lm = new LimasTrapesium("Batch Limas #" + jobIndex, atas, bawah, tinggi, kiri, kanan, extra3D);
                    lm.hitung();
                    luasAlas = lm.luas;
                    kelilingAlas = lm.keliling;
                    volume = lm.volume;
                    lp = lm.luasPermukaan;
                }

                long elapsed = System.currentTimeMillis() - startJobTime;
                int currentDoneCount = jumlahJobSelesai.incrementAndGet();

                final double fLuas = luasAlas, fKel = kelilingAlas, fVol = volume, fLp = lp;

                // Kembalikan data hasil ke Event Dispatch Thread (UI Thread)
                SwingUtilities.invokeLater(() -> {
                    // Update Database Tabel Log
                    tableModelHasil.addRow(new Object[]{
                        jobIndex, namaBangun, atas, bawah, tinggi,
                        (tipeBangun == 0) ? "—" : extra3D,
                        df.format(fLuas), df.format(fKel),
                        (tipeBangun == 0) ? "—" : df.format(fVol),
                        (tipeBangun == 0) ? "—" : df.format(fLp),
                        "Core " + (coreId + 1), elapsed + " ms"
                    });

                    // Update Logger Terminal
                    tambahLogConsole(String.format("[Core-%d] Selesai Job #%d: %s | V=%s m³ | %d ms", 
                        (coreId + 1), jobIndex, namaBangun, (tipeBangun == 0) ? "—" : df.format(fVol), elapsed));

                    // Set Core kembali ke IDLE
                    cpuVisualizerPanel.setCoreDone(coreId, jobIndex, elapsed);

                    // Update Progress Bar
                    int persenProgress = currentDoneCount * 100 / targetJobs;
                    batchProgressBar.setValue(persenProgress);
                    batchProgressBar.setString(persenProgress + "% Completed (" + currentDoneCount + "/" + targetJobs + ")");

                    // Cek jika seluruh Batch selesai
                    if (currentDoneCount == targetJobs) {
                        long totalBatchDuration = System.currentTimeMillis() - waktuMulaiGlobal;
                        lblStatusBatch.setText("Batch Selesai dalam " + totalBatchDuration + " ms.");
                        tambahLogConsole("=========================================");
                        tambahLogConsole("🔥 BATCH ENGINE FINISHED SUCCESSFULLY!");
                        tambahLogConsole("Durasi Global: " + totalBatchDuration + " ms");
                        tambahLogConsole("Total Terproses: " + targetJobs + " Bangun");
                        tambahLogConsole("=========================================");
                        btnJalankanBatch.setEnabled(true);
                    }
                });
            });
        }
        threadExecutor.shutdown();
    }

    private int dapatkanCoreIndex(String threadName, int poolSize) {
        try {
            // ThreadPoolExecutor biasanya menamai thread: pool-X-thread-Y
            String[] split = threadName.split("-");
            int threadId = Integer.parseInt(split[split.length - 1]);
            return (threadId - 1) % poolSize;
        } catch (Exception e) {
            return 0;
        }
    }

    private void tambahLogConsole(String teks) {
        txtThreadLogConsole.append(teks + "\n");
        txtThreadLogConsole.setCaretPosition(txtThreadLogConsole.getDocument().getLength());
    }

    private void bersihkanProsesThreading() {
        cpuVisualizerPanel.clearVisualizer();
        txtThreadLogConsole.setText("");
        tableModelHasil.setRowCount(0);
        batchProgressBar.setValue(0);
        batchProgressBar.setString("0% Completed");
        lblStatusBatch.setText("Engine Siap Dijalankan.");
    }

    // ────────────────────────────────────────────────────────────
    // MAIN METHOD: Program Entry Point
    // ────────────────────────────────────────────────────────────
    public static void main(String[] args) {
        // Set Look and Feel sistem asli
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            new GUITrapesium().setVisible(true);
        });
    }
}

// ============================================================
// KOMPONEN KUSTOM: ThreadCPUVisualizer
// ============================================================
/**
 * Panel kustom orisinal untuk memvisualisasikan Core CPU.
 * Menggantikan layout silsilah horizontal milik temanmu agar
 * terhindar dari plagiarisme.
 */
class ThreadCPUVisualizer extends JPanel {
    
    static class CoreNode {
        int id;
        String status = "IDLE";
        int activeJobIndex = -1;
        String activeJobType = "—";
        long lastElapsed = 0;
        int jobsProcessed = 0;

        CoreNode(int id) { this.id = id; }
    }

    private final List<CoreNode> cores = new ArrayList<>();

    ThreadCPUVisualizer() {
        setBackground(AppTheme.BG_DARK);
        setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));
    }

    public void inisialisasiThreadCore(int coreCount, int totalJobs) {
        cores.clear();
        removeAll();
        for (int i = 0; i < coreCount; i++) {
            CoreNode core = new CoreNode(i);
            cores.add(core);
            add(new CoreWidgetComponent(core));
        }
        revalidate();
        repaint();
    }

    public void clearVisualizer() {
        cores.clear();
        removeAll();
        revalidate();
        repaint();
    }

    public void setCoreActive(int coreId, int jobIndex, int typeId) {
        if (coreId >= cores.size()) return;
        CoreNode core = cores.get(coreId);
        core.status = "ACTIVE";
        core.activeJobIndex = jobIndex;
        core.activeJobType = (typeId == 0) ? "2D" : (typeId == 1) ? "Prisma" : "Limas";
        repaint();
    }

    public void setCoreDone(int coreId, int jobIndex, long ms) {
        if (coreId >= cores.size()) return;
        CoreNode core = cores.get(coreId);
        core.status = "IDLE";
        core.lastElapsed = ms;
        core.jobsProcessed++;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (cores.isEmpty()) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(AppTheme.TEXT_MUTED);
            g2.setFont(AppTheme.FONT_BODY);
            String pesan = "Silakan klik 'JALANKAN ENGINE' untuk mensimulasikan Multithreading.";
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(pesan, (getWidth() - fm.stringWidth(pesan)) / 2, getHeight() / 2);
            g2.dispose();
        }
    }

    // WIDGET KUSTOM UNTUK CORE
    private static class CoreWidgetComponent extends JComponent {
        private final CoreNode core;

        CoreWidgetComponent(CoreNode core) {
            this.core = core;
            setPreferredSize(new Dimension(170, 160));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Background Card Core
            g2.setColor(AppTheme.BG_CARD);
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);

            // Border Card Core
            boolean isActive = "ACTIVE".equals(core.status);
            g2.setColor(isActive ? AppTheme.ACCENT_AMBER : AppTheme.ACCENT_BORDER);
            g2.setStroke(new BasicStroke(isActive ? 2.0f : 1.0f));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);

            // Render Core Ring (Status Lingkaran Neon)
            int d = 60;
            int x = (getWidth() - d) / 2;
            int y = 20;

            g2.setColor(AppTheme.BG_HEADER);
            g2.fillOval(x, y, d, d);

            g2.setColor(isActive ? AppTheme.ACCENT_AMBER : AppTheme.ACCENT_VIOLET);
            g2.setStroke(new BasicStroke(4.0f));
            if (isActive) {
                // Gambar Ring Berputar
                long tick = System.currentTimeMillis() / 4;
                g2.drawArc(x, y, d, d, (int) (tick % 360), 240);
            } else {
                g2.drawOval(x, y, d, d);
            }

            // Teks Status di Tengah Ring
            g2.setFont(AppTheme.FONT_BADGE);
            g2.setColor(AppTheme.TEXT_BRIGHT);
            String lblCore = "CORE " + (core.id + 1);
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(lblCore, (getWidth() - fm.stringWidth(lblCore)) / 2, y + 35);

            // Metadata Detail Di Bawah Ring
            g2.setFont(AppTheme.FONT_LABEL);
            if (isActive) {
                g2.setColor(AppTheme.ACCENT_AMBER);
                String jobInfo = "#" + core.activeJobIndex + " (" + core.activeJobType + ")";
                FontMetrics fmInfo = g2.getFontMetrics();
                g2.drawString(jobInfo, (getWidth() - fmInfo.stringWidth(jobInfo)) / 2, 105);

                g2.setFont(AppTheme.FONT_BADGE);
                g2.setColor(AppTheme.ACCENT_AMBER);
                g2.drawString("CALCULATING...", (getWidth() - g2.getFontMetrics().stringWidth("CALCULATING...")) / 2, 125);
            } else {
                g2.setColor(AppTheme.TEXT_MUTED);
                String jobInfo = "Done: " + core.jobsProcessed;
                FontMetrics fmInfo = g2.getFontMetrics();
                g2.drawString(jobInfo, (getWidth() - fmInfo.stringWidth(jobInfo)) / 2, 105);

                g2.setFont(AppTheme.FONT_BADGE);
                g2.setColor(AppTheme.TEXT_MUTED);
                String durasi = core.lastElapsed + " ms";
                g2.drawString(durasi, (getWidth() - g2.getFontMetrics().stringWidth(durasi)) / 2, 125);
            }

            g2.dispose();
        }
    }
}

// ============================================================
// KELAS PENDUKUNG: AppTheme (Utility Visual Konstanta)
// ============================================================
final class AppTheme {
    private AppTheme() {}

    // Palet Warna: Midnight Purple & Vibrant Amber (Modern Dashboard)
    public static final Color BG_DARK        = new Color(15, 12, 27);     // `#0F0C1B`
    public static final Color BG_CARD        = new Color(24, 20, 44);     // `#18142C`
    public static final Color BG_HEADER      = new Color(36, 30, 68);     // `#241E44`
    public static final Color ACCENT_AMBER   = new Color(245, 158, 11);   // `#F59E0B`
    public static final Color ACCENT_VIOLET  = new Color(139, 92, 246);   // `#8B5CF6`
    public static final Color ACCENT_RED     = new Color(239, 68, 68);    // `#EF4444`
    public static final Color ACCENT_BORDER  = new Color(52, 45, 87);     // `#342D57`

    public static final Color TEXT_BRIGHT    = new Color(243, 244, 246);  // `#F3F4F6`
    public static final Color TEXT_MUTED     = new Color(156, 163, 175);  // `#9CA3AF`

    // Tipografi
    public static final Font FONT_BODY       = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font FONT_LABEL      = new Font("Segoe UI", Font.BOLD, 12);
    public static final Font FONT_HEADING    = new Font("Segoe UI", Font.BOLD, 15);
    public static final Font FONT_TITLE      = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font FONT_MONO       = new Font("Consolas", Font.PLAIN, 12);
    public static final Font FONT_BADGE      = new Font("Segoe UI", Font.BOLD, 10);
}

// ============================================================
// KELAS PENDUKUNG: UIHelper (Factory UI Utility)
// ============================================================
final class UIHelper {
    private UIHelper() {}

    public static JLabel buatLabelForm(String teks) {
        JLabel lbl = new JLabel(teks);
        lbl.setFont(AppTheme.FONT_LABEL);
        lbl.setForeground(AppTheme.TEXT_MUTED);
        return lbl;
    }

    public static JTextField buatTextField(String placeholder) {
        JTextField field = new JTextField(placeholder) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 8, 8));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        field.setFont(AppTheme.FONT_MONO);
        field.setForeground(AppTheme.TEXT_BRIGHT);
        field.setBackground(AppTheme.BG_HEADER);
        field.setCaretColor(AppTheme.ACCENT_AMBER);
        field.setOpaque(false);
        field.setBorder(new CompoundBorder(
            new RoundedBorder(AppTheme.ACCENT_BORDER, 8),
            new EmptyBorder(6, 10, 6, 10)
        ));
        return field;
    }

    public static JButton buatTombolNavigasi(String teks) {
        JButton btn = new JButton(teks) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(AppTheme.FONT_LABEL);
        btn.setForeground(AppTheme.TEXT_MUTED);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(160, 38));
        return btn;
    }

    public static JButton buatTombolAksi(String teks, Color warnaUtama) {
        JButton btn = new JButton(teks) {
            boolean hov = false;
            {
                addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) { hov = true; repaint(); }
                    public void mouseExited(MouseEvent e) { hov = false; repaint(); }
                });
            }
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hov ? warnaUtama.brighter() : warnaUtama);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(AppTheme.FONT_LABEL);
        btn.setForeground(Color.WHITE);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(150, 36));
        return btn;
    }

    public static JPanel buatPanelKustomKartu(LayoutManager layout) {
        JPanel pnl = new JPanel(layout) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(AppTheme.BG_CARD);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
                g2.setColor(AppTheme.ACCENT_BORDER);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
                g2.dispose();
            }
        };
        pnl.setOpaque(false);
        pnl.setBorder(new EmptyBorder(15, 15, 15, 15));
        return pnl;
    }

    public static JPanel buatPanelKartuInfo(String judul, String deskripsi) {
        JPanel panel = buatPanelKustomKartu(new BorderLayout(0, 10));
        JLabel lblJudul = new JLabel(judul);
        lblJudul.setFont(AppTheme.FONT_HEADING);
        lblJudul.setForeground(AppTheme.ACCENT_AMBER);
        
        JLabel lblDesk = new JLabel(deskripsi);
        lblDesk.setFont(AppTheme.FONT_BODY);
        lblDesk.setForeground(AppTheme.TEXT_BRIGHT);

        panel.add(lblJudul, BorderLayout.NORTH);
        panel.add(lblDesk, BorderLayout.CENTER);
        return panel;
    }

    public static class RoundedBorder extends AbstractBorder {
        private final Color color;
        private final int radius;
        public RoundedBorder(Color color, int radius) {
            this.color = color;
            this.radius = radius;
        }
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.drawRoundRect(x, y, w - 1, h - 1, radius, radius);
            g2.dispose();
        }
        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(4, 4, 4, 4);
        }
    }
}
