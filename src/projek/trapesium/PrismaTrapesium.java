package projek.trapesium;

public class PrismaTrapesium extends Trapesium {

    public double volume;
    public double luasPermukaan;
    public double tinggiPrisma;

    public PrismaTrapesium(String nama) {
        super(nama);
    }

    public PrismaTrapesium(String nama, double alasAtas, double alasBawah,
                           double tinggi, double tinggiPrisma) {
        super(nama, alasAtas, alasBawah, tinggi);
        this.tinggiPrisma = tinggiPrisma;
    }

    @Override
    public void hitung(){
        hitung(this.alasAtas, this.alasBawah, this.tinggi, this.tinggiPrisma);
    }
    
    public void hitung(double aAtas, double aBawah, double tAlas, double tPrisma) {
        super.hitung(aAtas, aBawah, tAlas);
        double luasAlas    = this.luas;
        double kelilingAlas = this.keliling;

        this.volume = luasAlas * tPrisma;
        this.luasPermukaan   = (2.0 * luasAlas) + (kelilingAlas * tPrisma);
    }
}