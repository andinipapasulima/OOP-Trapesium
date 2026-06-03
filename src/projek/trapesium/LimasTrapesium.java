package projek.trapesium;

public class LimasTrapesium extends Trapesium {

    public double volume;
    public double luasPermukaan;
    public double tinggiLimas;

    public LimasTrapesium(String nama) {
        super(nama);
    }

    public LimasTrapesium(String nama, double alasAtas, double alasBawah,
                          double tinggi, double tinggiLimas) {
        super(nama, alasAtas, alasBawah, tinggi);
        this.tinggiLimas = tinggiLimas;
    }

    @Override
    public void hitung(){
        hitung(this.alasAtas, this.alasBawah, this.tinggi, this.tinggiLimas);
    }
    public void hitung(double aAtas, double aBawah, double tAlas, double tLimas) {
        super.hitung(aAtas, aBawah, tAlas); 
        double luasAlas          = this.luas;
        double setengahTinggiAlas = tAlas / 2.0;
        double apothem            = Math.sqrt((setengahTinggiAlas * setengahTinggiAlas)
                                            + (tLimas * tLimas));
        double luasSelimutSegitiga = 0.5 * aBawah * apothem;
        double totalLuasSelimut   = 4.0 * luasSelimutSegitiga;

        this.volume = (1.0 / 3.0) * luasAlas * tLimas;
        this.luasPermukaan   = luasAlas + totalLuasSelimut;
    }
}