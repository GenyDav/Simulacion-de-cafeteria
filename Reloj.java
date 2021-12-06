/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cafeteria;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author Geny
 */
public final class Reloj {
    private double tmpIntervalo;
    private BufferedImage sprt1,sprt2,sprt3;
    private BufferedImage sprtActual;
    private int ladoPendulo;
    private final int IZQ = 1;
    private final int DER = 2;
    private final int x,y;    // coordenadas del centro de las manecillas
    private int horaSistema;
    private int xMin,yMin,xSeg,ySeg;
    private int min,seg;
    private final int radioMin, radioSeg;
    
    public Reloj(){
        sprt1 = Imagen.cargaImagen("cafeteria/sprites/reloj_1.png");
        sprt2 = Imagen.cargaImagen("cafeteria/sprites/reloj_2.png");
        sprt3 = Imagen.cargaImagen("cafeteria/sprites/reloj_3.png");
        sprtActual = sprt1;
        tmpIntervalo = 0;
        ladoPendulo = 1;
        horaSistema = obtenerHoraSistema();
        min = horaSistema;
        //System.out.println(min);
        radioMin = 8;
        radioSeg = 12;
        seg = 0;
        x = 316;
        y = 64;
        xMin = (int)(x+radioMin*Math.sin(this.min*30*Math.PI/180));
        yMin = (int)(y-radioMin*Math.cos(this.min*30*Math.PI/180));
        xSeg = 311;
        ySeg = 43;
    }
    
    public void pintarReloj(Graphics g){
        g.drawImage(sprtActual,Math.round(305),Math.round(93),null);
        //System.out.println(spSalida);
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(2));
        g.setColor(Color.BLACK);
        //g.fillRect(309,62, 5, 5);
        g.drawLine(x,y,xSeg,ySeg);
        g2.setStroke(new BasicStroke(3));
        //g.fillRect(309,62, 5, 5);
        g.drawLine(x,y,xMin,yMin);
    }
    
    public void moverManecillas(int min, int seg){
        this.min = horaSistema + min;
        this.seg = seg;
        xSeg = (int)(x+radioSeg*Math.sin(this.seg*6*Math.PI/180));
        ySeg = (int)(y-radioSeg*Math.cos(this.seg*6*Math.PI/180));
        xMin = (int)(x+radioMin*Math.sin(this.min*30*Math.PI/180));
        yMin = (int)(y-radioMin*Math.cos(this.min*30*Math.PI/180));
    }
    
    public void moverPendulo(double dt){
        tmpIntervalo += dt;
        //System.out.println(tmpIntervalo);
        if(tmpIntervalo>=0.450&&tmpIntervalo<=0.550){
            sprtActual = sprt2;
        }else if(tmpIntervalo>=1){
            //System.out.println("seg");
            tmpIntervalo = 0;
            //seg++;
            if(ladoPendulo==DER){
                sprtActual = sprt3;
                ladoPendulo = IZQ;
                //System.out.println("der");
            }
            else if(ladoPendulo==IZQ){
                ladoPendulo = DER;
                sprtActual = sprt1;
                //System.out.println("izq");
            }
        }     
    }
    
    public int obtenerHoraSistema(){
        return new GregorianCalendar().get(Calendar.HOUR);
    }
}
