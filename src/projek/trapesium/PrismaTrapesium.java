package projek.trapesium;

public class PrismaTrapesium extends Trapesium implements Geometri3D{

    public double volume;
    public double luasPermukaan;
    public double tinggiPrisma;

    public PrismaTrapesium() {
        super("Prisma Trapesium");
        this.tinggiPrisma = 0;
    }
    
    public PrismaTrapesium(String nama){
        super(nama);
    }

    public PrismaTrapesium(String nama, double atas, double bawah,
                           double tinggi,double kiri, double kanan, double tinggiPrisma) {
        super(nama, atas, bawah, tinggi, kiri, kanan);
        if (tinggiPrisma <= 0){
            throw new IllegalArgumentException("Tinggi prisma harus lebih besar dari 0, nilai saat ini: " + tinggiPrisma);
        }
        this.tinggiPrisma = tinggiPrisma;
    }

    @Override
    public void hitung(){
        super.hitung();
        hitungVolume();
        hitungLuasPermukaan();
    }
    
    @Override
    public double hitungVolume(){
        this.volume = this.luas * this.tinggiPrisma;
        return this.volume;
    }
    
    @Override
    public double hitungLuasPermukaan(){
        this.luasPermukaan = (2.0 * this.luas) + (this.keliling * this.tinggiPrisma);
        return this.luasPermukaan;
    }
    
    @Override
    public String toString(){
        return String.format("PrismaTrapesium[atas=%.2f, bawah=%.2f, tinggi=%.2f, kiri=%.2f, kanan=%.2f, tinggiPrisma=%.2f]",
                atas, bawah, tinggi, kiri, kanan, tinggiPrisma);
    }
}