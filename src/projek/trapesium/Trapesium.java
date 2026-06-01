package projek.trapesium;

public class Trapesium extends BangunGeometri {

    public double alasAtas;
    public double alasBawah;
    public double tinggi;

    public Trapesium(String nama) {
        super(nama);
        this.alasAtas  = 0;
        this.alasBawah = 0;
        this.tinggi    = 0;
    }

    public Trapesium(String nama, double alasAtas, double alasBawah, double tinggi) {
        super(nama);
        this.alasAtas  = alasAtas;
        this.alasBawah = alasBawah;
        this.tinggi    = tinggi;
    }

    @Override
    public void hitung() {
        double selisihAlas = Math.abs(alasBawah - alasAtas) / 2.0;
        double miring      = Math.sqrt((selisihAlas * selisihAlas) + (tinggi * tinggi));

        this.luas     = 0.5 * (alasAtas + alasBawah) * tinggi;
        this.keliling = alasAtas + alasBawah + (2.0 * miring);
        this.volume   = 0.0;
    }
}