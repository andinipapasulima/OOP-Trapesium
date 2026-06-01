package projek.trapesium;

public class PrismaTrapesium extends Trapesium {

    public double tinggiPrisma;

    public PrismaTrapesium(String nama) {
        super(nama);
        this.tinggiPrisma = 0;
    }

    public PrismaTrapesium(String nama, double alasAtas, double alasBawah,
                           double tinggi, double tinggiPrisma) {
        super(nama, alasAtas, alasBawah, tinggi);
        this.tinggiPrisma = tinggiPrisma;
    }

    @Override
    public void hitung() {
        super.hitung();
        double luasAlasTrapesium    = this.luas;
        double kelilingAlasTrapesium = this.keliling;

        this.volume = luasAlasTrapesium * tinggiPrisma;
        this.luas   = (2.0 * luasAlasTrapesium) + (kelilingAlasTrapesium * tinggiPrisma);
    }
}