package quadtree;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

//Bu sınıf Pencere olustrulması, butonların metin alanlarının eklenmesi, nokta ve sorgu dairelerinin çizilmesi için oluşturulmuştur. 
public class Pencere extends JFrame implements MouseListener {

    /*
    buton1 arama butonudur 
    buton2 random nokta ureten butondur
    buton3 temizle islemini yapan buton
    yaricaptext yaricap degerini almak icin kullanılan metinbölgesi
    koordinatlar cakısan noktaların koordinatlarının yazılacagı metin bolgesidir
    kaydirmacubugu koordinatlar icin olusturulan metin bolgesinin kaydırma cubugu
    daire arama butonuna basıldıgında 1 degerini alır. Kullanıcı noktaya bastıgında bir sorgu dairesi cizecegi anlamına gelir.
    yaricap sorgu dairesinin yarıcapını tutar
    
     */
    
    JButton buton1, buton2, buton3;
    JTextField yaricaptext;
    JTextArea koordinatlar;
    JScrollPane kaydirmacubugu;
    static int yaricap = 0;
    int daire = 0;

    /*
    Pencere sınıfının yapılandırcısıdır. Pencerenin başlığı, boyutu, yeri gibi görsel ayarları düzenler.
     */
    public Pencere() {

        this.setTitle("QUADTREE");//başlığı ayarlar
        this.setLayout(null);//Pencere için bir düzen yöneticisi olmasını sağlar null değeri ile düzen yöneticisi olmadan kullanılır
        this.setSize(900, 650);//boyut ayarlar
        this.setLocation(350, 50);//yerini  ayarlar
        //this.setBackground(Color.CYAN);
        this.getContentPane().setBackground(Color.getHSBColor(218, 112, 214));//arka plan rengini ayarlar.
        addMouseListener(this);
        this.setVisible(true);//görünürlüğü ayarlar
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Pencere kapatıldığında programıda bitirir.

    }

    /*
    Pencereyle ilgili ayarları yapar. Butonları ekler. Butonlar  ve yaricap alan metin bolgesi icin bir actionlistener olusturur.
    Koordinatları göstermek  ve yaricap degerini almak icin gerekli metin bölgelerini ve kaydırma cubugunu olusturur.
     */
    public void pencere_ayarla() {
        koordinatlar = new JTextArea();
        yaricaptext = new JTextField();
        //koordinatların yazılacagı metin bolgesinin yerini ve boyutunu ayarlar.
        koordinatlar.setLocation(600, 50);
        koordinatlar.setSize(200, 400);
        this.add(koordinatlar);
        //Metin Alanı için yazma silme gibi işlemlerin önlenmesini saglar.
        koordinatlar.setEditable(false);
        koordinatlar.setVisible(true);
        //Metin Alanı İçin dikey kaydirmacubugu  ekler
        kaydirmacubugu = new JScrollPane(koordinatlar);
        kaydirmacubugu.setLocation(600, 50);
        kaydirmacubugu.setSize(200, 400);
        //kaydirmacubuguin sadece dikeyde çıkması icindir
        kaydirmacubugu.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(kaydirmacubugu);
        //yaricapın alınacagı metin bolgesinin gorsel duzenlemlerini yapar.
        yaricaptext.setBackground(Color.WHITE);
        yaricaptext.setBounds(385, 550, 50, 20);
        this.add(yaricaptext);
        yaricaptext.setText(Integer.toString(yaricap));

        Graphics g;

        //çizim ekranı için gereken 512*512 lik ekranı çizer
        g = this.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(50, 50, 512, 512);
        g.setColor(Color.BLACK);
        g.drawRect(49, 49, 513, 513);

        Agacdugum kontrol = new Agacdugum(0, 0, Color.BLACK);
        Agacdugum.cakisan = new ArrayList<Cakisanlar>();

        buton1 = new JButton("arama");
        this.add(buton1);
        buton1.setBounds(45, 550, 80, 20);

        buton1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == buton1) {//e aksiyon yakalayacısının degeri buton1 e esitse daire degerini 1 yapar boylece kullanıcı ekrana tıladıgında nokta yerine sorgu dairesi çizilir.
                    daire = 1;
                }
            }
        });

        yaricaptext.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {//e 'nin degeri yaricaptext 'e esitse yarıcap metin alanına girilen degeri alır ve sorgu dairesi için kullanılan yarıcap degiskenine atar.
                if (e.getSource() == yaricaptext) {
                    yaricap = Integer.parseInt(yaricaptext.getText());
                }
            }
        });

        buton2 = new JButton("random");
        this.add(buton2);
        buton2.setBounds(145, 550, 80, 20);

        buton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == buton2) {//e 'nin degeri buton2'ye esitse 4 tane random nokta uretir.
                    int i = 0;
                    while (i != 4) {
                        Random sayiata = new Random();
                        noktaciz(sayiata.nextInt(512) + 50, sayiata.nextInt(512) + 50);
                        i++;
                    }

                }
            }

        });

        buton3 = new JButton("sil");
        this.add(buton3);
        buton3.setBounds(245, 550, 80, 20);

        buton3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == buton3) {//e 'nin degeri buton3'ye esitse kok dugumun degerini null yapar ve çizim alanının ustune yeni bir çizim alanı koyar ve sağ taraftaki metin alanını siler.
                    if(Agacdugum.kok!=null)
                    Agacdugum.kok=Agacdugum.kok.sil(Agacdugum.kok);
                    g.setColor(Color.WHITE);
                    g.fillRect(50, 50, 512, 512);
                    g.setColor(Color.BLACK);
                    g.drawRect(49, 49, 513, 513);
                      koordinatlar.setText("");

                }
            }
        });

    }

    /**
     Kullanıcının bastıgı noktada bir daire cizer.
    @param x basılan noktanın x degeri
    @param y basıldan noktanın y degeri
     */
    public void noktaciz(int x, int y) {
        if ((x >= 50 && x < 562) && (y >= 50 && y < 562)) {//noktanın çizim ekranında olup olmadıgını kontrol eder.
            Graphics g;
            g = this.getGraphics();

            int a = x - (6 / 2);
            int b = y - (6 / 2);

            Agacdugum yeni = new Agacdugum(x, y, Color.magenta);
            yeni.dugumyerlestir(yeni);

            //quadrantlar çiziliyor
            g.setColor(Color.BLACK);
            g.drawLine(x, y, yeni.maxx, y);
            g.drawLine(x, y, yeni.minx, y);
            g.drawLine(x, y, x, yeni.maxy);
            g.drawLine(x, y, x, yeni.miny);
            //nokta cizilir.
            g.setColor(Color.MAGENTA);
            g.fillOval(a, b, 6, 6);

        }

    }

    /**
    Sorgu dairesini cizer ve içinde kalan noktaların x, y değerini ekrana basar.
    @param x soru dairesinin x degeri
    @param y sorgu dairesinin y degeri 
    
     */
    public void aramacembericiz(int x, int y) {
        //sorgu dairesinin çizim ekranında olup olmadıgını kontrol eder.
        if ((x >= 50 && x < 562) && (y >= 50 && y < 562) && !((x + yaricap >= 562 || x - yaricap < 50 || y + yaricap >= 562 || y - yaricap < 50))) {

            Graphics g;
            g = this.getGraphics();
            g.setColor(Color.BLUE);
            //onceki x ve y koordinatları tutan listenin elemanları silinir.
            Agacdugum.cakisan.clear();
            //arama cemberi cizilir.
            int a = x - (yaricap);
            int b = y - (yaricap);
            g.drawOval(a, b, yaricap * 2, yaricap * 2);
            Cakisanlar.cakisansayisi = 0;
            Agacdugum.arama(Agacdugum.kok, x, y, g);

            int i;
            //cakisan noktaların x ve y degerleri sText degiskenine ekleniyor
            String sText = new String("CAKISAN NOKTA SAYISI: " + Cakisanlar.cakisansayisi + "\n\n");

            for (i = 0; i < Agacdugum.cakisan.size(); i++) {
                Cakisanlar temp = (Cakisanlar) Agacdugum.cakisan.get(i);
                sText = sText.concat((i + 1) + " - " + "X: " + Integer.toString(temp.x) + " Y: " + Integer.toString(temp.y) + "\n");
            }
            //koordinatlar için oluşturulan metin alanının içerigi koordinatların eklendiği sText değişkenine eşitleniyor.
            koordinatlar.setText(sText);
        }

    }

    /*
    Fare basıldıgında calısır x ve y degerlerini alıp nokta veya sorgu dairesi cizen fonksiyonları cagırır
     */
    @Override
    public void mouseClicked(MouseEvent e) {

        int x, y;
        x = e.getX();
        y = e.getY();

        if (daire == 1) {
            aramacembericiz(x, y);
            daire = 0;
        } else {
            noktaciz(x, y);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
