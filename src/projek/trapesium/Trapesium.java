package projek.trapesium;

// Trapesium mewarisi BangunGeometri (Inheritance)
public class Trapesium extends BangunGeometri {
    public double alasAtas;
    public double alasBawah;
    public double tinggi;

    public Trapesium(String nama, double alasAtas, double alasBawah, double tinggi) {
        super(nama); // Mengirim nama ke abstract class induk
        this.alasAtas = alasAtas;
        this.alasBawah = alasBawah;
        this.tinggi = tinggi;
    }

    // Melakukan Overriding terhadap method abstract milik induk
    @Override
    public void hitung() {
        double selisih = (alasBawah - alasAtas) / 2.0;
        double miring = Math.sqrt((selisih * selisih) + (tinggi * tinggi));
        
        this.luas = 0.5 * (alasAtas + alasBawah) * tinggi;
        this.keliling = alasAtas + alasBawah + (2 * miring);
        this.volume = 0; 
    }
}