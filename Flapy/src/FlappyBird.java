//Flappy Bird es una recreación del popular juego para dispositivos móviles creado por Dong Nguyen.
import acm.graphics.*;
import acm.program.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class FlappyBird extends GraphicsProgram {

	final static int SCREEN_WIDTH = 288, SCREEN_HEIGHT = 512, GROUND_LEVEL = 400, PIPE_WIDTH = 52, BIRD_X_START = 68;

	Bird bird;
	FileHandler highScoreFile;

	static int currentMode = 0; // 0 = preparacion, 1 = Volando, 2 = Callendo, 3 = Game Over
	static int score = 0;
	
	@Override public void init() {
		
		setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
                // carga sus imagenes y sus localizaciones
		Data.init();
		initializeDigits(true);
                // inicia las acciones del pajaro
		bird = new Bird(FlappyBird.BIRD_X_START, 240);
		//pepara la hja del Score
		highScoreFile = new FileHandler();
		
		resetPipes();
                //añade las primeras imagenes de la pantalla de inicio
		add(Data.background);
		for(int i = 0; i < 4; i++){ add(Data.pipeTop[i]); add(Data.pipeBottom[i]); }
		add(Data.ground);
		add(Data.getReady);
		add(Data.instructions);
		add(Data.birdUp);
                //Inicializa las imágenes para los dígitos totales acumulados para que no sea nulo (lo coloca fuera de la vista)
		for(int i = 0; i < 10; i ++){
			Data.scoreDigits[i] = new GImage(Data.bigNums[0].getImage());
			Data.scoreDigits[i].setLocation(-100, 0);			
			add(Data.scoreDigits[i]);
		}

		drawScore();
		// permite el uso del teclado y el mause
		addMouseListeners();
		addKeyListeners();

	}
	
	@Override public void run(){

		int groundOffset = 0;

		while(true){
			
					
			if(FlappyBird.currentMode == 1 || FlappyBird.currentMode == 2){
				
				bird.fly();
				// checa si toco el suelo 
				if(bird.getY() + bird.hoverCounter > FlappyBird.GROUND_LEVEL - Data.birdFlat.getHeight()){
                                        String soundName1 = "src/Music/falling.wav"; 
                                        AudioInputStream audioInputStream1 = null;
                                    try {
                                        audioInputStream1 = AudioSystem.getAudioInputStream(new File(soundName1).getAbsoluteFile());
                                    } catch (UnsupportedAudioFileException ex) {
                                        Logger.getLogger(FlappyBird.class.getName()).log(Level.SEVERE, null, ex);
                                    } catch (IOException ex) {
                                        Logger.getLogger(FlappyBird.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                        Clip clip = null;
                                    try {
                                        clip = AudioSystem.getClip();
                                    } catch (LineUnavailableException ex) {
                                        Logger.getLogger(FlappyBird.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    try {
                                        clip.open(audioInputStream1);
                                    } catch (LineUnavailableException ex) {
                                        Logger.getLogger(FlappyBird.class.getName()).log(Level.SEVERE, null, ex);
                                    } catch (IOException ex) {
                                        Logger.getLogger(FlappyBird.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                        clip.start();
					bird.downwardSpeed = 0;
					endRound();
				}
			}
			// estado de jugando
			if(FlappyBird.currentMode == 1){
						
                            try {
                                movePipes();
                            } catch (UnsupportedAudioFileException ex) {
                                Logger.getLogger(FlappyBird.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                Logger.getLogger(FlappyBird.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (LineUnavailableException ex) {
                                Logger.getLogger(FlappyBird.class.getName()).log(Level.SEVERE, null, ex);
                            }
                                //si le pego a una tuberia 
				if (bird.pipeCollision()){
					String soundName1 = "src/Music/falling.wav"; 
                                        AudioInputStream audioInputStream1 = null;
                                    try {
                                        audioInputStream1 = AudioSystem.getAudioInputStream(new File(soundName1).getAbsoluteFile());
                                    } catch (UnsupportedAudioFileException ex) {
                                        Logger.getLogger(FlappyBird.class.getName()).log(Level.SEVERE, null, ex);
                                    } catch (IOException ex) {
                                        Logger.getLogger(FlappyBird.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                        Clip clip = null;
                                    try {
                                        clip = AudioSystem.getClip();
                                    } catch (LineUnavailableException ex) {
                                        Logger.getLogger(FlappyBird.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    try {
                                        clip.open(audioInputStream1);
                                    } catch (LineUnavailableException ex) {
                                        Logger.getLogger(FlappyBird.class.getName()).log(Level.SEVERE, null, ex);
                                    } catch (IOException ex) {
                                        Logger.getLogger(FlappyBird.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                        clip.start();
					bird.downwardSpeed = 0;
					endRound();
					bird.downwardSpeed = Math.min(0, bird.downwardSpeed);
					currentMode = 2;
				}
				
			}

			if(FlappyBird.currentMode < 2){
						
				Data.ground.setLocation(-groundOffset, FlappyBird.GROUND_LEVEL);
				groundOffset = (groundOffset + 4) % 24;

			}
					
			if(FlappyBird.currentMode < 3)
				bird.draw(this);
			//controla la velocidad del juego 
			pause(40);
			
		}
	}
        //Si el usuario hace clic con el mouse o teclado, verifica si se presionó el botón de reproducción y, si no, invoca respondToUserInput ()
	@Override public void keyPressed(KeyEvent key){

		char character = key.getKeyChar();

		if(character == ' ' || character == 'w')
			try {
                            respondToUserInput();
                } catch (UnsupportedAudioFileException ex) {
                    Logger.getLogger(FlappyBird.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(FlappyBird.class.getName()).log(Level.SEVERE, null, ex);
                } catch (LineUnavailableException ex) {
                    Logger.getLogger(FlappyBird.class.getName()).log(Level.SEVERE, null, ex);
                }

	}
	
	@Override public void mousePressed(MouseEvent mouse) {
		
		if(FlappyBird.currentMode == 3){
			if(mouse.getX() > 89 && mouse.getX() < 191 && mouse.getY() > 333 && mouse.getY() < 389)
				replayGame();
				return;
		}

            try {
                respondToUserInput();
            } catch (UnsupportedAudioFileException ex) {
                Logger.getLogger(FlappyBird.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FlappyBird.class.getName()).log(Level.SEVERE, null, ex);
            } catch (LineUnavailableException ex) {
                Logger.getLogger(FlappyBird.class.getName()).log(Level.SEVERE, null, ex);
            }
		
	}
        //Responde a la entrada del usuario invocando flapWing () o cambiando el estado del juego
	public void respondToUserInput() throws UnsupportedAudioFileException, IOException, LineUnavailableException{
            // si el modo esta en preparados 
            if(FlappyBird.currentMode == 0){
			FlappyBird.currentMode = 1;
			remove(Data.getReady);
			remove(Data.instructions);
		}
                //si ya esta volando
		else if(FlappyBird.currentMode == 1){	
			bird.capHeight();
                        String soundName = "src/Music/flap.wav"; 
                        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
                        clip.open(audioInputStream);
                        clip.start();	
		}

	}

        //Mueve los tubos hacia la izquierda, deformando hacia el lado derecho si es necesario
	public void movePipes() throws UnsupportedAudioFileException, IOException, LineUnavailableException{
		
		for(int i = 0; i < 4; i++){
			//movimiento
			Data.pipeBottom[i].move(-4, 0);
			Data.pipeTop[i].move(-4, 0);
                        // anotacion de puntos
			if(Data.pipeBottom[i].getX() == BIRD_X_START + 2){
				score++;
				drawScore();
                                String soundName2 = "src/Music/coinSound.wav"; 
                                AudioInputStream audioInputStream2 = AudioSystem.getAudioInputStream(new File(soundName2).getAbsoluteFile());
                                Clip clip = AudioSystem.getClip();
                                clip.open(audioInputStream2);
                                clip.start();
			}
			//Vuelve a generar la tubería si ya se han deslizado por la pantalla
			if(Data.pipeBottom[i].getX() < -(SCREEN_WIDTH/2) - (PIPE_WIDTH/2)){
				Data.pipeTop[i].setLocation(SCREEN_WIDTH + (SCREEN_WIDTH/2) - (PIPE_WIDTH/2), -118);
				Data.pipeBottom[i].setLocation(SCREEN_WIDTH + (SCREEN_WIDTH/2) - (PIPE_WIDTH/2), (GROUND_LEVEL/2));
				randomizePipes(i);
			}
			
		}
		
        }
        //Restablece los 4 juegos de tuberías a sus ubicaciones iniciales
	public void resetPipes(){
		for(int i = 0; i < 4; i++){
			Data.pipeTop[i].setLocation(SCREEN_WIDTH*2 + i*(SCREEN_WIDTH/2) - (PIPE_WIDTH/2), -118);
			Data.pipeBottom[i].setLocation(SCREEN_WIDTH*2 + i*(SCREEN_WIDTH/2) - (PIPE_WIDTH/2), (GROUND_LEVEL/2));
			randomizePipes(i);
		}
	}
        //Randomiza la apricion de las tuberia 
	public void randomizePipes(int i){
		int randomAltitude = (int) (Math.random() * (GROUND_LEVEL/2) ) - 101;
		Data.pipeTop[i].move(0, randomAltitude - 50);
		Data.pipeBottom[i].move(0, randomAltitude + 50);
	}
        //Muestra los gráficos para el final de una ronda.
	public void endRound(){

		FlappyBird.currentMode = 3;
		
		remove(Data.birdUp);
		remove(Data.birdDown);
		remove(Data.birdFlat);
		add(Data.birdDead);

		add(Data.ground);
		
		add(Data.gameOver);
		add(Data.scoreboard);
		add(Data.replayButton);
                // agrega un amedaña por la cantidad de puntos 
		if(score >= 40) add(Data.platinumMedal);
		else if(score >= 30) add(Data.goldMedal);
		else if(score >= 20) add(Data.silverMedal);
		else if(score >= 10) add(Data.bronzeMedal);
                // Revisa el Score para ver si lo actualiza 
		String strHighScore = highScoreFile.getHighScore();
		int highScore = Integer.parseInt(strHighScore);

		if(highScoreFile.fileHasBeenManipulated()){
			drawBoardScore(1, score );
			Data.new_.setLocation(-100, 0);
		}

		else if (score > highScore){
			highScoreFile.updateHighScore(Integer.toString(score));
			drawBoardScore(1, score );
			Data.new_.setLocation(164, 256);

		}
		else{
			drawBoardScore(1, highScore);
			Data.new_.setLocation(-100, 0);
		}
		
		add(Data.new_);
		drawBoardScore(0, score);

	}
        //Resetea el game
	public void replayGame(){

		remove(Data.replayButton);
		remove(Data.gameOver);
		remove(Data.birdDead);
		
		bird.setY(240);
		bird.downwardSpeed = 0;
		bird.hoverCounter = 0;
		
		FlappyBird.currentMode = 0;

		resetPipes();
		add(Data.getReady);
		add(Data.instructions);	
	
		if(score >= 40) remove(Data.platinumMedal);
		else if(score >= 30) remove(Data.goldMedal);
		else if(score >= 20) remove(Data.silverMedal);
		else if(score >= 10) remove(Data.bronzeMedal);
		remove(Data.scoreboard);
		remove(Data.new_);
		
		score = 0;
		initializeDigits(false);
		
		drawScore();

	}
	//Inicializa las imágenes para los dígitos totales acumulados para que no sean nulos, colocándolos fuera de la vista
	public void initializeDigits(boolean initialCall){
		
		for(int i = 0; i < 20; i ++){
			
			if(!initialCall)
				remove(Data.scoreBoardDigits[i]);
			
			Data.scoreBoardDigits[i] = new GImage(Data.medNums[0].getImage());
			Data.scoreBoardDigits[i].setLocation(-100, 0);			
			add(Data.scoreBoardDigits[i]);
			
		}
		
	}

        //Dibuja un número en el marcador
	protected void drawBoardScore(int mode, int points){
		
		boolean drawing = true;
		int startPoint = 235;
		
		for(int n = 0; n < 10; n++){
			
			remove(Data.scoreBoardDigits[n + mode*10]);
			Data.scoreBoardDigits[n + mode*10] = new GImage(Data.medNums[points % 10].getImage());
			startPoint -= Data.scoreBoardDigits[n + mode*10].getWidth();
			
			if(points == 0)
				drawing = false;
			
			if(drawing || n == 0)
				Data.scoreBoardDigits[n + mode*10].setLocation(startPoint - n, 232 + mode*42);
			else
				Data.scoreBoardDigits[n + mode*10].setLocation(-100, 0);
			
			add(Data.scoreBoardDigits[n + mode*10]);
			
			points /= 10;
		}

	}
        //Dibuja el Actual Score 
	protected void drawScore(){
			
		int tempScore = score, widthScore = -1, digitCounter = 0;
			
		for(int n = 0; n < 10; n++)
			remove(Data.scoreDigits[n]);
			
		do{
			Data.scoreDigits[digitCounter] = new GImage(Data.bigNums[tempScore % 10].getImage());
			widthScore += Data.bigNums[tempScore % 10].getWidth() + 1;
			tempScore /= 10;
			digitCounter++;
		}
		while(tempScore > 0);
			
		int startPoint = (SCREEN_WIDTH/2) - (widthScore/2);
			
		for(int n = 0; n < digitCounter; n++){
			int index = digitCounter - n - 1;
			Data.scoreDigits[index].setLocation(startPoint, 50);
			add(Data.scoreDigits[index]);
			startPoint += Data.scoreDigits[index].getWidth() + 1;
		}

	}

}
