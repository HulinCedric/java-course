/*
 * Mulot.java
 * @author lise Created on 19 avril 2007, 18:51
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;

public class Mulot {
    
    static final int BLACK = 0;
    static final int WHITE = 1;
    static final int RED = 2;
    static final int BLUE = 3;
    static final int YELLOW = 4;
    static final int PINK = 5;
    static final int GREEN = 6;
    static final int ORANGE = 7;
    
    static final int WIDTH = 1000;
    static final int HEIGHT = 1000;
    
    /*
     * - position, coordonn�es courantes du mulot. -
     * couleur, couleur de trac� et du mulot. - angle,
     * orientation du mulot selon le sens trigonom�trique. -
     * pose, renseigne sur l'aptitude ou non du mulot �
     * laisser une trace lors de ses d�pTacements. -
     * origine, coordonn�es pr�c�dentes du mulot, tr�s
     * utiles pour tracer les traits...
     */
    int position_x;
    int position_y;
    Color couleur;
    double angle;
    int pose;
    int origine_x;
    int origine_y;
    Polygon p;
    
    Point resize(Point p, Graphics g) {
        // les points du mulot sont donn�s dans un espace
        // th�orique d�limit� par (0,0) et (1000,1000)
        // il faut donc faire un changement de coordonn�es
        // pour s'adapter � la taille de la fen�tre
        Rectangle r = g.getClipBounds();
        Point resu = new Point();
        resu.x = (p.x * r.width) / WIDTH;
        resu.y = (p.y * r.height) / HEIGHT;
        return resu;
    }
    
    Color initCoul(int coul) {
        switch (coul) {
            case BLACK:
                return Color.BLACK;
            case WHITE:
                return Color.WHITE;
            case RED:
                return Color.RED;
            case BLUE:
                return Color.BLUE;
            case YELLOW:
                return Color.YELLOW;
            case PINK:
                return Color.PINK;
            case GREEN:
                return Color.GREEN;
            case ORANGE:
                return Color.ORANGE;
            default:
                return Color.BLACK;
        }
    }
    
    Polygon creePolygoneMulot() {
        int x, y;
        
        Polygon p = new Polygon();
        x = Math.round((float) (position_x - 4 * Math.cos(-angle)));
        y = Math.round((float) (position_y + 4 * Math.sin(-angle)));
        p.addPoint(x, y);
        x = Math.round((float) (position_x + 4 * Math.cos(-angle)));
        y = Math.round((float) (position_y - 4 * Math.sin(-angle)));
        p.addPoint(x, y);
        x = Math.round((float) (position_x + 8 * Math.sin(-angle)));
        y = Math.round((float) (position_y + 8 * Math.cos(-angle)));
        p.addPoint(x, y);
        return p;
    }
    
    public void initMulot(int x, int y, int coul, int angl, Graphics g) {
        // on passe de coordonn�es th�oriques pT aux
        // coordonn�es fen�tre pF
        if ((x == -1) && (y == -1)) {
            // on positionne le mulot au centre de la
            // fen�tre
            Rectangle r = g.getClipBounds();
            position_x = (r.x + r.width) / 2;
            position_y = (r.y + r.height) / 2;
        }
        else {
            Point pT = new Point(x, y);
            Point pF = resize(pT, g);
            position_x = pF.x;
            position_y = pF.y;
        }
        origine_x = position_x;
        origine_y = position_y;
        
        couleur = initCoul(coul);
        angle = angl;
        pose = 0;
        
    }
    
    public Mulot(Graphics g) {
        initMulot(-1, -1, BLACK, 0, g);
        p = creePolygoneMulot();
    }
    
    public Mulot(int x, int y, Graphics g) {
        initMulot(x, y, BLACK, 0, g);
        p = creePolygoneMulot();
    }
    
    public Mulot(int x, int y, int coul, Graphics g) {
        initMulot(x, y, coul, 0, g);
        p = creePolygoneMulot();
    }
    
    public Mulot(int x, int y, int coul, int angl, Graphics g) {
        initMulot(x, y, coul, angl, g);
        tourne(angl, g);
        p = creePolygoneMulot();
    }
    
    public void avance(int n, Graphics g) {
        int xf = 0, yf = 0;
        
        g.setColor(couleur);
        
        // ancienne position
        origine_x = position_x;
        origine_y = position_y;
        
        // trace de la ligne entre origine et position
        int xd = origine_x;
        int yd = origine_y;
        int d = 2;
        for (int i = 1; i <= n; i++) {
            xf = Math.round((float) (xd + d * Math.sin(-angle)));
            yf = Math.round((float) (yd + d * Math.cos(-angle)));
            if (pose == 1) g.drawLine(xd, yd, xf, yf);
            xd = xf;
            yd = yf;
        }
        // nouvelle position
        position_x = xf;
        position_y = yf;
        
        // on recr�e le polygone Mulot � la nouvelle
        // position
        p = creePolygoneMulot();
        // g.drawPolygon(p);
        
    }
    
    public void tourne(int angl, Graphics g) {
        angl = -angl;
        angle = (angle + (double) ((Math.PI * angl) / 180)) % 360;
        p = creePolygoneMulot();
    }
    
    public void baisse(Graphics g) {
        pose = 1;
    }
    
    public void leve(Graphics g) {
        pose = 0;
    }
}
