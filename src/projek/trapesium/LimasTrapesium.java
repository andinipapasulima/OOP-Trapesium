package projek.trapesium;

public class LimasTrapesium extends Trapesium {

    public double tinggiLimas;

    public LimasTrapesium(String nama) {
        super(nama);
        this.tinggiLimas = 0;
    }

    public LimasTrapesium(String nama, double alasAtas, double alasBawah,
                          double tinggi, double tinggiLimas) {
        super(nama, alasAtas, alasBawah, tinggi);
        this.tinggiLimas = tinggiLimas;
    }

    @Override
    public void hitung() {
        super.hitung();
        double luasAlasTrapesium  = this.luas;
        double setengahTinggiAlas = tinggi / 2.0;
        double apothem            = Math.sqrt((setengahTinggiAlas * setengahTinggiAlas)
                                            + (tinggiLimas * tinggiLimas));
        double luasSelimutSegitiga = 0.5 * alasBawah * apothem;
        double totalLuasSelimut   = 4.0 * luasSelimutSegitiga;

        this.volume = (1.0 / 3.0) * luasAlasTrapesium * tinggiLimas;
        this.luas   = luasAlasTrapesium + totalLuasSelimut;
    }
}