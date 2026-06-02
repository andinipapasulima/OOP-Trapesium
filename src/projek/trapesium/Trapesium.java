package projek.trapesium;

public class Trapesium extends BangunGeometri {

    public double luas;
    public double keliling;
    public double alasAtas;
    public double alasBawah;
    public double tinggi;
    
    public Trapesium(String nama, double alasAtas, double alasBawah, double tinggi) {
        super(nama);
        this.alasAtas  = alasAtas;
        this.alasBawah = alasBawah;
        this.tinggi    = tinggi;
    }

    @Override
    public void hitung() {
         this.luas     = 0.5 * (alasAtas + alasBawah) * tinggi;
        double selisihAlas = Math.abs(alasBawah - alasAtas) / 2.0;
        double sisiMiring      = Math.sqrt((selisihAlas * selisihAlas) + (tinggi * tinggi));
        this.keliling = alasAtas + alasBawah + (2.0 * sisiMiring);
    }
}