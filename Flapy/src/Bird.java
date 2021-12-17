//Bird: esta clase crea una funcionalidad para que un pájaro vuele y tenga detección de colisiones con tuberías.

import acm.graphics.*;
import acm.program.*;

public class Bird extends FlappyBird{

	GRectangle birdRect;

	protected int downwardSpeed = 0, hoverCounter = 0, x, y;
	private int animationCounter = 0;
	boolean hoverDirectionUp = true;

	public Bird(int startingX, int startingY){
		// Crea un rectángulo delgado e invisible en la parte superior del pájaro mientras vuela para la detección de colisiones

		birdRect = new GRectangle(x, y , (int) Data.birdFlat.getWidth(), (int) Data.birdFlat.getHeight());

		this.x = startingX;
		this.y = startingY;

	}
        
        //Comprueba si hay colisión entre las tuberías y el pájaro 

	public boolean pipeCollision(){

		for (GImage pipeImage : Data.pipeTop)
			if(birdRect.intersects( new GRectangle(pipeImage.getBounds())))
				return true;

		for (GImage pipeImage : Data.pipeBottom)
			if(birdRect.intersects( new GRectangle(pipeImage.getBounds())))
				return true;

		return false;
	}
        //Dibujar pájaro en la pantalla
	public void draw(GraphicsProgram window){
		// Restablece la ubicación de todas las imágenes de aves
		Data.birdDown.setLocation(FlappyBird.BIRD_X_START, getY() + hoverCounter);
		Data.birdFlat.setLocation(FlappyBird.BIRD_X_START, getY() + hoverCounter);
		Data.birdUp.setLocation(FlappyBird.BIRD_X_START, getY() + hoverCounter);
		
		if(FlappyBird.currentMode != 2){
			// Pasa a la siguiente imagen de la animación.

			if(animationCounter % 2 == 0)
				animateBird(animationCounter/2, window);
	
			animationCounter = (animationCounter + 1) % 8;
			
		}
		
	}
        // Crea los movimiento de arriba y abajo 
	public void fly(){

		hoverBird();
                //movimiento del pajaro
		downwardSpeed -= 1*FlappyBird.currentMode;
		this.setY( this.getY() - downwardSpeed );
                // Establecer la nueva ubicación del rectángulo invisible debajo del pájaro, utilizado para la detección de colisiones
		birdRect.setLocation(FlappyBird.BIRD_X_START, getY() + hoverCounter);
		
 	}
        //Se asegura de que el pájaro no se salga de la pantalla.
	public void capHeight(){
		
		if(getY() + hoverCounter > -16)
			downwardSpeed = 10;

	}
        //Animacion del pajaro
	protected void animateBird(int index, GraphicsProgram window){
		
		if(index == 0){
			window.add(Data.birdFlat);
			window.remove(Data.birdUp);
		}
		else if(index == 1){
			window.add(Data.birdDown);
			window.remove(Data.birdFlat);
		}
		else if(index == 2){
			window.add(Data.birdFlat);
			window.remove(Data.birdDown);
		}
		else{
			window.add(Data.birdUp);
			window.remove(Data.birdFlat);
		}
		
	}
        //Hace que el pájaro parezca flotar hacia arriba y hacia abajo.
	protected void hoverBird(){

		if(hoverDirectionUp){
			hoverCounter--;
			if(hoverCounter == -5)
				hoverDirectionUp = false;
		}
		else{
			hoverCounter++;
			if(hoverCounter == 5)
				hoverDirectionUp = true;
		}

	}

	public void setY(int y){ this.y = y; }
	public void setX(int x){ this.x = x; }
	public int getX(){ return this.x; }
	public int getY(){ return this.y; }

}
