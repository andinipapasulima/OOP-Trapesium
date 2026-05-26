package projek.trapesium;

public class PrismaTrapesium extends Trapesium {
    public double tinggiPrisma;

    public PrismaTrapesium(String nama, double alasAtas, double alasBawah, double tinggi, double tinggiPrisma) {
        super(nama, alasAtas, alasBawah, tinggi);
        this.tinggiPrisma = tinggiPrisma;
    }

    @Override
    public void hitung() {
        super.hitung(); // Ambil kalkulasi dasarnya dari Trapesium
        double luasAlas = this.luas;
        double kelilingAlas = this.keliling;
        
        this.volume = luasAlas * tinggiPrisma;
        this.luas = (2 * luasAlas) + (kelilingAlas * tinggiPrisma);
    }
}