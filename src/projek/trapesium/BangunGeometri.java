package projek.trapesium;

// Menambahkan pilar Abstract Class demi nilai akademik yang sempurna
public abstract class BangunGeometri implements Runnable {
    protected String nama;
    public double luas;
    public double keliling;
    public double volume;

    public BangunGeometri(String nama) {
        this.nama = nama;
    }

    // Method abstract yang wajib diturunkan dan diisi oleh anak-anaknya
    public abstract void hitung();

    // Implementasi Runnable dari interface bawaan Java (Multithreading)
    @Override
    public void run() {
        hitung();
        System.out.println(nama + " sukses diproses oleh " + Thread.currentThread().getName());
    }
}
