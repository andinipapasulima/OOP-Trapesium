package projek.trapesium;

public class LimasTrapesium extends Trapesium implements Geometri3D {

    public double volume;
    public double luasPermukaan;
    public double tinggiLimas;
    public double apotemaDepanBelakang;
    public double apotemaKiriKanan;

    public LimasTrapesium() {
        super("Limas Trapesium");
        this.tinggiLimas = 0;
    }
    
    public LimasTrapesium(String nama){
        super(nama);
    }
    
    public LimasTrapesium(String nama, double atas, double bawah,
                          double tinggi, double kiri, double kanan, double tinggiLimas) {
        super(nama, atas, bawah, tinggi, kiri, kanan);
        if (tinggiLimas <= 0){
            throw new IllegalArgumentException("Tinggi limas harus lebih besar dari 0, nilai saat ini: " + tinggiLimas);
        }
        this.tinggiLimas = tinggiLimas;
    }

    @Override
    public void hitung(){
        super.hitung();
        hitungApotema();
        hitungVolume();
        hitungLuasPermukaan();
    }
    
    public void hitungApotema(){
        double proyeksiDB = (this.tinggi / 2.0);
        double proyeksiKK = (this.bawah / 2.0);
        this.apotemaDepanBelakang = Math.sqrt((proyeksiDB * proyeksiDB) + (tinggiLimas * tinggiLimas));
        this.apotemaKiriKanan = Math.sqrt((proyeksiKK * proyeksiKK) + (tinggiLimas * tinggiLimas));
    }
    
    @Override
    public double hitungVolume(){
        this.volume = (1.0/3.0) * this.luas * this.tinggiLimas;
        return this.volume;
    }
    
    @Override 
    public double hitungLuasPermukaan(){
        double luasSegitigaDepan = 0.5 * this.atas * this.apotemaDepanBelakang;
        double luasSegitigaBelakang = 0.5 * this.bawah * this.apotemaDepanBelakang;
        double luasSegitigaKiri = 0.5 * this.kiri * this.apotemaKiriKanan;
        double luasSegitigaKanan = 0.5 * this.kanan * this.apotemaKiriKanan;
        
        double totalSelimut = luasSegitigaDepan + luasSegitigaBelakang + luasSegitigaKiri + luasSegitigaKanan;
        this.luasPermukaan = this.luas + totalSelimut;
        return this.luasPermukaan;
    }
    
    @Override
    public String toString(){
        return String.format("LimasTrapesium[atas=%.2f, bawah=%.2f, tinggi=%.2f, kiri=%.2f, kanan=%.2f, tinggiLimas=%.2f]",
                atas, bawah, tinggi, kiri, kanan, tinggiLimas);
    }
}