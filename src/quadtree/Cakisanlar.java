package quadtree;

//Bu sınıf cakısan noktaların x ve y degerlerini  ArrayList yapısında tutabilmek   için olusturulmustur.
public class Cakisanlar {

    /*
    x cakısan noktanın x degeri
    y cakısan noktanın y degeri
    cakisansayisi cakisan noktaların sayisini tutar
     */
    int x, y;
    static int cakisansayisi = 0;

    /**
    Cakısanlar sınıfının yapılandırıcısıdır. Parametre olan gelen x ye degerlerini nesnenin x ve ye degerlerine atar.
    @param x cakısan noktanın x degeri
    @param y cakısan noktanın y degeri
   
     */
    public Cakisanlar(int x, int y) {
        this.x = x;
        this.y = y;

    }

}
