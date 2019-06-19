package br.com.danubio.apps;

public class App 
{
    public static void main( String[] args )
    {
        
        javax.swing.SwingUtilities.invokeLater(() -> {
            HorseRideView view = new HorseRideView();
        });
    }
}
