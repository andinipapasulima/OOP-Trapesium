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
        System.out.printf("[%s] => Perhitungan '%s' selesai diproses.%n",
            Thread.currentThread().getName(), nama);
    }
    public String getNama(){
        return nama;
    }
}
