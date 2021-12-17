//Datos: carga imágenes de una hoja de sprites y establece su ubicación en la pantalla si corresponde.
import acm.graphics.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public abstract class Data{

		public static BufferedImage fullImage = null;

		public static GImage background, ground,
					     birdFlat, birdUp, birdDown, birdDead,
					     getReady, gameOver, instructions, scoreboard, replayButton,
					     bronzeMedal, silverMedal, goldMedal, platinumMedal, new_;

		public static GImage[] pipeTop = new  GImage[4], pipeBottom = new GImage[4],
				medNums = new  GImage[10], bigNums = new GImage[10],
				scoreDigits = new GImage[10], scoreBoardDigits = new GImage[20];
                // Carga y establece la ubicación de todas las imágenes 
		public static void init(){

			Data.loadImages();
			Data.setLocations();

		}
		// Carga todas las imágenes de la hoja de sprites
		public static void loadImages(){
			
			try 
                        {   // Import sprite-sheet
                            File file = new File("src/Images/Flappy-Graphics.png");
                            fullImage = ImageIO.read(file);	
                        }
			catch (IOException e) {	e.printStackTrace(); }
			// fondo
			Data.background = makeImage(fullImage, 0, 0, 288, 512);
                         //las tuberias 
			for(int i = 0; i < 4; i++){
				Data.pipeTop[i] = makeImage(fullImage, 112, 646, 164, 965);
				Data.pipeBottom[i] = makeImage(fullImage, 168, 646, 220, 965);
			}
                        //la lista de Score
			Data.medNums[0] = makeImage(fullImage, 274, 612, 288, 632);
			Data.medNums[1] = makeImage(fullImage, 278, 954, 288, 974);
			Data.medNums[2] = makeImage(fullImage, 274, 978, 288, 998);
			Data.medNums[3] = makeImage(fullImage, 262, 1002, 276, 1022);
			Data.medNums[4] = makeImage(fullImage, 1004, 0, 1018, 20);
			Data.medNums[5] = makeImage(fullImage, 1004, 24, 1018, 44);
			Data.medNums[6] = makeImage(fullImage, 1010, 52, 1024, 72);
			Data.medNums[7] = makeImage(fullImage, 1010, 84, 1024, 104);
			Data.medNums[8] = makeImage(fullImage, 586, 484, 600, 504);
			Data.medNums[9] = makeImage(fullImage, 622, 412, 636, 432);
                        //los datos de corrida 
			Data.bigNums[0] = makeImage(fullImage, 992, 120, 1016, 156);
			Data.bigNums[1] = makeImage(fullImage, 272, 910, 288, 946);
			Data.bigNums[2] = makeImage(fullImage, 584, 320, 608, 356);
			Data.bigNums[3] = makeImage(fullImage, 612, 320, 636, 356);
			Data.bigNums[4] = makeImage(fullImage, 640, 320, 664, 356);
			Data.bigNums[5] = makeImage(fullImage, 668, 320, 692, 356);
			Data.bigNums[6] = makeImage(fullImage, 584, 368, 608, 404);
			Data.bigNums[7] = makeImage(fullImage, 612, 368, 636, 404);
			Data.bigNums[8] = makeImage(fullImage, 640, 368, 664, 404);
			Data.bigNums[9] = makeImage(fullImage, 668, 368, 692, 404);
                        //la tierra
			Data.ground = makeImage(fullImage, 584, 0, 919, 111);
                        // el pajaro
			Data.birdUp = makeImage(fullImage, 6, 982, 39, 1005);
			Data.birdFlat = makeImage(fullImage, 62, 982, 95, 1005);
			Data.birdDown = makeImage(fullImage, 118, 982, 151, 1005);
			Data.birdDead = makeImage(fullImage, 297, 983, 327, 1020);
                        // los letreros del preparacion y fin del juego
			Data.getReady = makeImage(fullImage, 584, 116, 780, 180);
			Data.instructions = makeImage(fullImage, 584, 175, 700, 283);
			Data.gameOver = makeImage(fullImage, 780, 116, 990, 180);
			Data.replayButton = makeImage(fullImage, 705, 234, 816, 303);
                        // la tabla de Score
			Data.scoreboard = makeImage(fullImage, 0, 515, 237, 643);
			Data.bronzeMedal = makeImage(fullImage, 224, 954, 268, 998);
			Data.silverMedal = makeImage(fullImage, 224, 906, 268, 950);
			Data.goldMedal = makeImage(fullImage, 242, 564, 286, 608);
			Data.platinumMedal = makeImage(fullImage, 242, 516, 286, 560);
			Data.new_ = makeImage(fullImage, 224, 1002, 256, 1016);

		}
		// pone las localizaciones de las imagenes
		private static void setLocations(){
			// la tierra
			Data.ground.setLocation(0, 400);
			//el pajaro
			Data.birdFlat.setLocation(-100, 0);
			Data.birdDown.setLocation(-100, 0);
			Data.birdUp.setLocation(-100, 0);
			Data.birdDead.setLocation(70, 371);
			//los carteles del juego
			Data.getReady.setLocation(45, 130);
			Data.instructions.setLocation(85, 210);
			Data.gameOver.setLocation(40, 130);
			Data.replayButton.setLocation(85, 330);
                        //la tabla de resultados
			Data.scoreboard.setLocation(25, 195);
			Data.bronzeMedal.setLocation(57, 240);
			Data.silverMedal.setLocation(57, 240);
			Data.goldMedal.setLocation(57, 240);
			Data.platinumMedal.setLocation(57, 240);

		}
		//Se usa para ayudar a obtener las subimágenes de la hoja de sprites
		protected static GImage makeImage(BufferedImage i, int xStart, int yStart, int xEnd, int yEnd){
			return new GImage(i.getSubimage(xStart, yStart, xEnd - xStart, yEnd - yStart));
		}

}