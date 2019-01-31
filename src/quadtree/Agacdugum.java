package quadtree;

import java.awt.*;
import java.util.ArrayList;

//Bu sınıf dörtlü agac yapısında ekleme ve arama işlemlerini yapmak için oluşturulmuştur.
public class Agacdugum {

    /*
    x nokta x
    y nokta y
    renkkod nokta renkkod
    maxx nokta maksimum x
    maxy nokta maksimum y
    minx nokta minimum x
    miny nokta minimum y
    kok dortlu agacın  kök  dugumu
    child1 dugumun birinci cocugu
    child2 dugumun ikinci cocugu
    child3 dugumun üçüncü cocugu
    child4 dugumun dördüncü cocugu
    cakisan sorgu icine düsen noktaların x,y degerlerini tutan liste
     */
    int x, y, minx, miny, maxx, maxy;
    Color renkkod;
    static Agacdugum kok = null;
    Agacdugum child1,child2, child3, child4;
    static ArrayList cakisan;

    /*
    Agacdugum sınıfının yapılandırıcısıdır. Noktanın x, y, renkkodu degerlerini atar. Cocuklarının ilk degerini null yapar.
    @param x nokta x
    @param y nokta y
    @param renkkod nokta renkkod
     */
    public Agacdugum(int x, int y, Color renkkod) {

        this.x = x;
        this.y = y;
        this.renkkod = renkkod;
        child1 = child2 = child3 = child4 = null;

    }

    /**
    Basılan nokta ilk noktaysa kok dugume ekler  degilse yerbul fonksiyonunu cagirir.
    @param x kokdugum 
     */
    public void dugumyerlestir(Agacdugum x) {

        //kökün maksimum ve minimum degerleri pencere boyutunda ayarlanır.
        if (kok == null) {
            x.maxx = 561;
            x.maxy = 561;
            x.minx = 50;
            x.miny = 50;
            kok = x;
        } else {
            yerbul(kok, x);
            
        }

    }

    /**
    Basılan noktanın hangi dugumun kacıncı cocugu oldugunu bularak ekleme islemi yapar
     @param dugum o anda cocukları kontrol edilen dugum
     @param yerlestirilcek basılan noktanın x ve y 'si alınarak olusturulan eklenecek dugum 
     @return Agacdugum
     */
    public Agacdugum yerbul(Agacdugum dugum, Agacdugum yerlestirilcek) {
      
        //dugumun cocugunun degeri null ise veya aynı x,y degerine sahip bir nokta varsa yeni dugumu geri döndürür. 
        if (dugum == null || (yerlestirilcek.x == dugum.x && yerlestirilcek.y == dugum.y)) {
            return yerlestirilcek;
        }
        //yeni eklenecek dugumun x ve y degerinin kacıncı cocuga ait dikdörtgende oldugunu kontrol eder. Ona göre maksimum ve minimu degerleri ayarlar.
        if (yerlestirilcek.x <= dugum.x && yerlestirilcek.y <= dugum.y) {
            yerlestirilcek.maxx = dugum.x;
            yerlestirilcek.maxy = dugum.y;
            yerlestirilcek.minx = dugum.minx;
            yerlestirilcek.miny = dugum.miny;

            dugum.child1 = yerbul(dugum.child1, yerlestirilcek);//dönen değeri atama işlemi yapılır
        } else if (yerlestirilcek.x <= dugum.x && yerlestirilcek.y > dugum.y) {
            yerlestirilcek.maxx = dugum.x;
            yerlestirilcek.maxy = dugum.maxy;
            yerlestirilcek.minx = dugum.minx;
            yerlestirilcek.miny = dugum.y;

            dugum.child3 = yerbul(dugum.child3, yerlestirilcek);
        } else if (yerlestirilcek.x > dugum.x && yerlestirilcek.y <= dugum.y) {
            yerlestirilcek.maxx = dugum.maxx;
            yerlestirilcek.maxy = dugum.y;
            yerlestirilcek.minx = dugum.x;
            yerlestirilcek.miny = dugum.miny;

            dugum.child2 = yerbul(dugum.child2, yerlestirilcek);
        } else if (yerlestirilcek.x > dugum.x && yerlestirilcek.y > dugum.y) {
            yerlestirilcek.maxx = dugum.maxx;
            yerlestirilcek.maxy = dugum.maxy;
            yerlestirilcek.minx = dugum.x;
            yerlestirilcek.miny = dugum.y;
            dugum.child4 = yerbul(dugum.child4, yerlestirilcek);
        }

        return dugum;

    }

    /**
    sorgu cemberinin icine dusen noktaları bulur ve çakışanların tutulduğu listeye sıraya uygun olarak ekler
     @param dugum o anda 4 cocuguna sorgu dairesinin degip degmedigi kontrol edilen dugum
     @param a sorgu dairesinin x degeri
     @param b sorgu dairesinin y degeri
     @param noktaların rengini degistirmek icin kullanılacak olan degiskenr
     @return Agacdugum 
     */
    public  static Agacdugum arama(Agacdugum dugum, int a, int b, Graphics g) {

        //cakisiyor1 sorgu dairesi 1. cocukla cakıstıgında 1 degerini alır
        //cakisiyor2 sorgu dairesi 2. cocukla cakıstıgında 1 degerini alır 
        //cakisiyor3 sorgu dairesi 3. cocukla cakıstıgında 1 degerini alır
        //cakisiyor4 sorgu dairesi 4. cocukla cakıstıgında 1 degerini alır
        int cakisiyor1 = 0, cakisiyor2 = 0, cakisiyor3 = 0, cakisiyor4 = 0;

        //var cakısan noktaları tutan listede ekleme islemi yapıldıında 1 degerini alır. Eger degeri 0 ise elemanın en sona eklenecegi anlamına gelir for dongusunden sonra ekleme yapılır
        int var = 0;
        if (dugum == null) {
            return dugum;
        }

        //dugumun x ve y degerinin sorgu dairesinin içinde olup olmama durumu kontrol edilir.
        if (Math.sqrt(Math.pow(dugum.x - a, 2) + Math.pow(dugum.y - b, 2)) <= Pencere.yaricap) {
            Cakisanlar.cakisansayisi++;
            dugum.renkkod = Color.RED;
            //cakısan dugumun rengi degstirilir
            g.setColor(Color.RED);
            g.fillOval(dugum.x - (6 / 2), dugum.y - (6 / 2), 6, 6);

            Cakisanlar ekle = new Cakisanlar(dugum.x, dugum.y);
            Cakisanlar kontrol;
            int i;
            //cakısan listesine noktanın  x ve y degerleri kucukten buyuge kuralı dikkate alınarak eklenir
            if (cakisan.isEmpty()) {
                cakisan.add(0, ekle);

            } else {
                for (i = 0; i < cakisan.size(); i++) {
                    kontrol = (Cakisanlar) cakisan.get(i);
                    if (kontrol.x > ekle.x) {
                        cakisan.add(i, ekle);
                        var = 1;
                        break;
                    }
                    if (kontrol.x == ekle.x && kontrol.y >= ekle.y) {
                        cakisan.add(i, ekle);
                        var = 1;
                        break;
                    }

                }//son elemanda bulundugunda var 0 degerini aldıgı için for dongusu bitinceson elemana ekleme yapılır.
                if (var == 0 && i == cakisan.size()) {
                    cakisan.add(i, ekle);
                }

            }

        }
        //bak o anda bakılan dugumun x ve y degerleri sorgu dairesini kestiginde noktanın sorgu dairesinin icinde olup olmadıgını kontrol eder. Eger icinde degilse sorgu dairesinin 4 cocuguda
        // kesmedigi anlamına gelir ve 1 degerini alır. 
        int bak = 0;
        //bu kontrol blogu bakılan dugumun x ve y sinin sorgu dairesini kesip kesmedigini kontrol eder.
        if ((((dugum.x < a + Pencere.yaricap && dugum.x > a - Pencere.yaricap) && (dugum.y < b + Pencere.yaricap && dugum.y > b - Pencere.yaricap)))) {

            if (Math.sqrt(Math.pow(dugum.x - a, 2) + Math.pow(dugum.y - b, 2)) >= Pencere.yaricap) {
                bak = 1;
            }
            if (bak == 0 || (bak == 1 && (dugum.x > a || dugum.y > b))) {
                cakisiyor1 = 1;
            }
            if (bak == 0 || (bak == 1 && (dugum.x < a || dugum.y > b))) {
                cakisiyor2 = 1;
            }
            if (bak == 0 || (bak == 1 && (dugum.x > a || dugum.y < b))) {
                cakisiyor3 = 1;
            }
            if (bak == 0 || (bak == 1 && (dugum.x < a || dugum.y < b))) {
                cakisiyor4 = 1;
            }
        } else if ((dugum.x < a + Pencere.yaricap && dugum.x > a - Pencere.yaricap)) {//sadece x degeri sorgu dairesini kesiyorsa bu kontrol blogu calısır
            if (b > dugum.y) {
                cakisiyor3 = 1;
                cakisiyor4 = 1;
            } else {
                cakisiyor1 = 1;
                cakisiyor2 = 1;
            }

        } else if (dugum.y < b + Pencere.yaricap && dugum.y > b - Pencere.yaricap) {//sadece y degeri sorgu dairesini kesiyorsa bu kontrol blogu calısır

            if (a > dugum.x) {
                cakisiyor2 = 1;
                cakisiyor4 = 1;
            } else if (a < dugum.x) {
                cakisiyor1 = 1;
                cakisiyor3 = 1;
            }

        } else {//dugumun  x ve y degeride sorgu dairesini kesmiyorsa bu kontrol blogu calısır.

            if (a < dugum.x && b < dugum.y) {
                cakisiyor1 = 1;
            }
            if (a < dugum.x && b > dugum.y) {
                cakisiyor3 = 1;
            }
            if (a > dugum.x && b < dugum.y) {
                cakisiyor2 = 1;
            }
            if (a > dugum.x && b > dugum.y) {
                cakisiyor4 = 1;
            }
        }

        //yukarıdaki kontroller sonucu 1 degerini alan dugumun cocuklarının cocukları kontrol edilmek uzere arama fonksiyonu tekrar cagırılır.
        if (cakisiyor1 == 1) {
            dugum.child1 = arama(dugum.child1, a, b, g);
        }
        if (cakisiyor2 == 1) {
            dugum.child2 = arama(dugum.child2, a, b, g);
        }
        if (cakisiyor3 == 1) {
            dugum.child3 = arama(dugum.child3, a, b, g);
        }
        if (cakisiyor4 == 1) {
            dugum.child4 = arama(dugum.child4, a, b, g);
        }

        return dugum;
    }
    
    /**
     * Ağaçtaki düğümleri tek tek null yapar ve ağacı siler
     * @param bakilan  anda çocuklarına bakılan düğümdür.
     * @return  Agacdugum
     */
    public Agacdugum sil(Agacdugum bakilan)
    {
        
        if(bakilan==null)
           return null;
        
        bakilan.child1=sil(bakilan.child1);
        bakilan.child2=sil(bakilan.child2); 
        bakilan.child3=sil(bakilan.child3);
        bakilan.child4=sil(bakilan.child4);
        
        return null;
                
        
    }
    
    
    

}
