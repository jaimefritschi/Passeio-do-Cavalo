
package br.com.danubio.apps;

import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class Settings {
    private static final String DEMO_FILE = "demos/";
    private static final String MOVEMENT_IMAGE = "images/movement.png";
    private static final String SELECTED_IMAGE = "images/selected.png";
    private static final String HORSE_IMAGE = "images/horse.png";
    private static final int SIZES = 6;
    
    private List<SettingsObserver> observer;
    
    private static final Settings uniqueSettings = new Settings();
    
    private Image[] horseImage;
    private Image[] movementImage;
    private Image[] selectedImage;
    private int[] amountPixels;
    private int currentIndex;
    
    private Settings()
    {
        amountPixels = new int[SIZES];
        amountPixels[0] = 50;
        amountPixels[1] = 60;
        amountPixels[2] = 70;
        amountPixels[3] = 80;
        amountPixels[4] = 90;
        amountPixels[5] = 100;
        
        movementImage = new Image[SIZES];
        movementImage[SIZES-1] = createImage(MOVEMENT_IMAGE);
        selectedImage = new Image[SIZES];
        selectedImage[SIZES-1] = createImage(SELECTED_IMAGE);
        horseImage = new Image[SIZES];
        horseImage[SIZES-1] = createImage(HORSE_IMAGE);
        
        for(int i=SIZES-2; i >= 0; i--) {
            movementImage[i] = renderImage(movementImage[SIZES-1], amountPixels[i]);
            selectedImage[i] = renderImage(selectedImage[SIZES-1], amountPixels[i]);
            horseImage[i] = renderImage(horseImage[SIZES-1], amountPixels[i]);
        }
        
        this.observer = new ArrayList<SettingsObserver>();
        
        currentIndex = 3;
    }
    
    int getAmountPixels()
    {
        return amountPixels[currentIndex];
    }
    
    Image getHorseImage()
    {
        return horseImage[currentIndex];
    }
    
    Image getMovementImage()
    {
        return movementImage[currentIndex];
    }
    
    Image getSelectedImage()
    {
        return selectedImage[currentIndex];
    }
    
    static Settings getSettings()
    {
        return uniqueSettings;
    }
    
    public void addSettingsObserver(SettingsObserver observer)
    {
        this.observer.add(observer);
    }
    
    public void notifyObserver()
    {
        for(SettingsObserver o : observer)
        {
            o.updateObserver();
        }
    }

    static Image createImage(String path) 
    {
        java.net.URL URL = Settings.class.getClassLoader().getResource(path);
        
        try {
            return ImageIO.read(URL);
        } catch (IOException exception) {
            System.out.println("Não conseguiu carregar a imagem " + path);
            return null;
        }
    }
    
    static Image renderImage(Image img, int size) 
    {
        return img.getScaledInstance(size, size, 100);
    }
}
