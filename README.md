# 📐 Trapezium Geometry Console & Parallel Engine

Aplikasi desktop Java (Swing) untuk menghitung geometri bangun berbasis trapesium — **Trapesium 2D**, **Prisma Trapesium**, dan **Limas Trapesium** — sambil mendemonstrasikan konsep **OOP** (abstraksi, inheritance, interface, polymorphism) dan **multithreading** lewat visualizer beban CPU/thread saat perhitungan diproses secara paralel.

## ✨ Fitur

- **Kalkulator geometri** untuk 3 bangun:
  - **Trapesium** (2D) — luas & keliling.
  - **Prisma Trapesium** (3D) — volume & luas permukaan.
  - **Limas Trapesium** (3D) — volume & luas permukaan (dengan perhitungan apotema).
- **Validasi input** — setiap dimensi (sisi atas/bawah/tinggi/kiri/kanan, tinggi prisma/limas) divalidasi harus lebih besar dari 0, dengan pesan error yang jelas.
- **Log hasil perhitungan** dalam bentuk tabel (`JTable`) yang bisa terus bertambah selama sesi berjalan.
- **Pemrosesan paralel** — setiap perhitungan dijalankan di `Thread` terpisah (`BangunGeometri implements Runnable`), dilengkapi **Thread CPU Visualizer** kustom yang menampilkan aktivitas core/thread secara visual saat perhitungan diproses.
- **UI custom dark theme** — komponen Swing (field, tombol, border) digambar ulang manual (custom `paintComponent`, `RoundedBorder`) untuk tampilan modern bertema gelap dengan aksen amber & violet.

## 🧱 Struktur OOP

```
                 Runnable
                     △
                     │
              BangunGeometri (abstract)
                     △
                     │
     Geometri2D ◁────Trapesium
     (interface)         △
                          │
             ┌────────────┴────────────┐
             │                         │
   PrismaTrapesium              LimasTrapesium
   implements Geometri3D        implements Geometri3D
```

- `Geometri2D` — interface: `hitungLuas()`, `hitungKeliling()`.
- `Geometri3D` — interface: `hitungVolume()`, `hitungLuasPermukaan()`.
- `BangunGeometri` — abstract class, implements `Runnable`, menyimpan nama bangun dan menjalankan `hitung()` di thread-nya sendiri.
- `Trapesium` — implementasi dasar 2D, sekaligus superclass untuk kedua bangun 3D.
- `PrismaTrapesium` & `LimasTrapesium` — extends `Trapesium`, menambahkan perhitungan volume & luas permukaan versi 3D masing-masing.

## 🛠️ Tech Stack

| Komponen | Detail |
|---|---|
| Bahasa | Java 22 |
| GUI | Java Swing (custom-styled, tanpa library eksternal) |
| Concurrency | `java.util.concurrent`, `Thread` manual per perhitungan |
| Build | Ant (project NetBeans standar) |
| IDE | Apache NetBeans |

## 📋 Prasyarat

- JDK 22 (atau kompatibel) terpasang.
- Apache NetBeans (opsional, memudahkan run/build lewat GUI) — atau Ant + JDK saja lewat command line.

## 🚀 Cara Menjalankan

### Lewat NetBeans
1. Clone repo ini:
   ```bash
   git clone https://github.com/andinipapasulima/OOP-Trapesium.git
   ```
2. Buka folder project di **Apache NetBeans** (`File → Open Project`).
3. Klik **Run Project** (▶) — atau tekan `F6`.

### Lewat command line (Ant)
```bash
git clone https://github.com/andinipapasulima/OOP-Trapesium.git
cd OOP-Trapesium
ant run
```

Main class: `projek.trapesium.GUITrapesium`

## 📁 Struktur File

```
OOP-Trapesium/
├── build.xml                      → build script Ant
├── manifest.mf                    → manifest untuk JAR
├── nbproject/                     → konfigurasi project NetBeans
└── src/projek/trapesium/
    ├── Geometri2D.java            → interface bangun 2D
    ├── Geometri3D.java            → interface bangun 3D
    ├── BangunGeometri.java        → abstract class dasar (Runnable)
    ├── Trapesium.java             → bangun trapesium 2D
    ├── PrismaTrapesium.java       → bangun prisma trapesium (3D)
    ├── LimasTrapesium.java        → bangun limas trapesium (3D)
    └── GUITrapesium.java          → tampilan Swing + logic aplikasi utama
```

## 👤 Author

Dibuat oleh [Andini](https://github.com/andinipapasulima).
