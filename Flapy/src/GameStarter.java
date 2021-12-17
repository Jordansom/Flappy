//GameStarter: inicia la aplicación Flappy Bird colocando el Applet en un JFrame.
import java.awt.*;
import javax.swing.*;

public class GameStarter{

	public static void main(String[] args) {
		
		FlappyBird flappyBirdApplet = new FlappyBird();
		JFrame appletFrame = new JFrame();
                // Debe llamar al método init () para Applet
		flappyBirdApplet.init();
                // Centro del marco a la mitad de la pantalla al inicio
		Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
		int screenStartX = screenDimension.width/2 - appletFrame.getSize().width/2;
		int screenStartY = screenDimension.height/2 - appletFrame.getSize().height/2;
		appletFrame.setLocation(screenStartX - flappyBirdApplet.SCREEN_WIDTH/2, screenStartY - flappyBirdApplet.SCREEN_WIDTH);
                
                // Establecer los valores predeterminados del programa
		appletFrame.setResizable(false);
		appletFrame.setSize(flappyBirdApplet.SCREEN_WIDTH, flappyBirdApplet.SCREEN_HEIGHT);
	    appletFrame.setVisible(true); 
		appletFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		// Agregue el applet al marco y comience
	    appletFrame.add(flappyBirdApplet);
	   	flappyBirdApplet.start();
	   	// Pone titulo 
	   	appletFrame.setTitle("Flappy Bird - Proyecto Final");

	}
}
