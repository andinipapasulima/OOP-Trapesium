package projek.trapesium;

public abstract class BangunGeometri implements Runnable {
    protected String nama;
 
    public BangunGeometri(String nama) {
        this.nama = nama;
    }

    public abstract void hitung();

    @Override
    public void run() {
        hitung();
        System.out.println(nama + " sukses diproses oleh " + Thread.currentThread().getName());
    }
}
