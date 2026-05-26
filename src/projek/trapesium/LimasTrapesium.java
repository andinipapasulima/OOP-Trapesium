package projek.trapesium;

public class LimasTrapesium extends Trapesium {
    public double tinggiLimas;

    public LimasTrapesium(String nama, double alasAtas, double alasBawah, double tinggi, double tinggiLimas) {
        super(nama, alasAtas, alasBawah, tinggi);
        this.tinggiLimas = tinggiLimas;
    }

    @Override
    public void hitung() {
        super.hitung(); 
        double luasAlas = this.luas;
        double apothem = Math.sqrt((tinggi * tinggi) + (tinggiLimas * tinggiLimas)); 
        
        this.volume = (1.0 / 3.0) * luasAlas * tinggiLimas;
        this.luas = luasAlas + (this.keliling * apothem * 0.5); 
    }
}