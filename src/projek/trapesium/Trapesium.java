package projek.trapesium;

public class Trapesium extends BangunGeometri implements Geometri2D{

    public double atas;
    public double bawah;
    public double tinggi;
    public double kiri;
    public double kanan;
    
    public double luas;
    public double keliling;

    public Trapesium(){
        super("Trapesium 2D");
        this.atas = this.bawah = this.tinggi = this.kiri = this.kanan = 0;
    }

    public Trapesium(String nama) {
        super(nama);
    }
    
    public Trapesium(String nama, double atas, double bawah, double tinggi, double kiri, double kanan){
        super(nama);
        validasiDimensi(atas, bawah, tinggi, kiri, kanan);
        this.atas = atas;
        this.bawah = bawah;
        this.tinggi = tinggi;
        this.kiri = kiri;
        this.kanan = kanan;
    }
    
    protected void validasiDimensi (double atas, double bawah, double tinggi, double kiri, double kanan){
        if (atas <= 0) throw new IllegalArgumentException("Sisi atas harus lebih besar dari 0, nilai saat ini: " + atas);
        if (bawah <= 0) throw new IllegalArgumentException ("Sisi bawah harus lebih besar dari 0, nilai saat ini: " + bawah);
        if (tinggi <= 0) throw new IllegalArgumentException("tinggi harus lebih besar dari 0, nilai saat ini: " + tinggi);
        if (kiri <= 0) throw new IllegalArgumentException ("Sisi kiri harus lebih besar dari 0, nilai saat ini: " + kiri);
        if (kanan <= 0) throw new IllegalArgumentException ("Sisi kanan harus lebih besar dari 0, nilai saat ini: " + kanan);
    }
            
    @Override
    
    public void hitung(){
        hitungLuas();
        hitungKeliling();
    }
    
    @Override
    public double hitungLuas(){
        this.luas = 0.5 * (atas + bawah) * tinggi;
        return this.luas;
    }
    
    @Override 
    public double hitungKeliling(){
        this.keliling = atas + bawah + kiri + kanan;
        return this.keliling;
    }
    
    public double hitungLuas (double a, double b, double t){
        return 0.5 * (a + b)*t;
    }
    
    public double hitungKeliling (double a, double b, double ki, double ka){
        return a + b + ki + ka;
    }
    
    @Override
    public String toString(){
        return String.format("Trapesium[atas=%.2f, bawah=%.2f, tinggi=%.2f, kiri=%.2f, kanan=%.2f]", atas, bawah, tinggi, kiri, kanan);
    }
}