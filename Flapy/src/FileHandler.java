//FileHandling: esta es una clase que crea una capa de seguridad que evita que el usuario cambie manualmente
//su puntuación más alta sin que el programa se dé cuenta. Esto se hace almacenando el hash de la puntuación más alta.
//en un archivo separado llamado hash.txt. Cada vez que el programa quiere actualizar la puntuación alta, verifica
//que el hash almacenado es equivalente al hash de puntuación más alta actual.
import java.io.*;
import java.util.*;
import java.security.*;

public class FileHandler{

    final static String hashAlgorithm = "MD5", encoding = "UTF-8";
    static int filesCreated = 0;
    // highScoreFile -> realiza un seguimiento de la puntuación más alta
    String highScoreFileString = "Record";
    // hashFile -> almacena el hash para la puntuación más alta
    String hashFileString = "hash";
    
    // crea un txt al cual amacenar el score
    public FileHandler() {

        highScoreFileString += Integer.toString(filesCreated) + ".txt";
        hashFileString += Integer.toString(filesCreated) + ".txt" ;

        File highScoreFile = new File(highScoreFileString);
        File hashFile = new File(hashFileString);

        if (!highScoreFile.exists())
        	writeToFile(highScoreFileString, "0");

        if (!hashFile.exists())
        	writeToFile(hashFileString, getHash(getFirstLine(highScoreFileString)));

        filesCreated++;
    }
    // Actualice la puntuación más alta dada una nueva puntuación y asegúrese de que el usuario no haya intentado cambiar la puntuación manualmente
    public void updateHighScore(String newScore){

        if (fileHasBeenManipulated())
            writeToFile(highScoreFileString, "0");
        else
            writeToFile(highScoreFileString, newScore); 

        updateHash();

    }
    // obtiene el maximo score por el momento 
    public String getHighScore(){

        return getFirstLine(highScoreFileString);

    }
    // checa si intenta cambiar el score
    public boolean fileHasBeenManipulated(){

        String highScoreText = getFirstLine(highScoreFileString),
        		highScoreTextHash = getHash(highScoreText),
        		md5FileText = getFirstLine(hashFileString);
        
        boolean hasBeenManipulated = !highScoreTextHash.equals(md5FileText);

        if (hasBeenManipulated) {
        	writeToFile(highScoreFileString, "0");
        	updateHash();
        }
        
        return hasBeenManipulated;
        
    }
    // escribe en un archivo
    protected void writeToFile(String fileName, String str){

        try {
        	
            PrintWriter writer = new PrintWriter(fileName, encoding);
            writer.println(str);
            writer.close();

        } 
        catch (FileNotFoundException e){} 
        catch (UnsupportedEncodingException e){}

    }
    // actualisa el Score
    protected void updateHash() {

        String line =  getFirstLine(highScoreFileString);
        String byteHash = getHash(line);

        writeToFile(hashFileString, byteHash);

    }
    // Genera el byte hash asociado con una cadena
    protected String getHash(String strLine){

        String md5byteHash = "";

        try{
            byte[] bytesOfMessage = strLine.getBytes(encoding);
            MessageDigest md = MessageDigest.getInstance(hashAlgorithm);
            byte[] thedigest = md.digest(bytesOfMessage);

            for (byte b : thedigest){ md5byteHash += Byte.toString(b); }
        }
        catch (UnsupportedEncodingException e){}
        catch(NoSuchAlgorithmException e){}

        return md5byteHash;
    }
    // obtener la primera línea en el archivo
    protected String getFirstLine(String fileName){

        String line = null;

        try{
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            line = reader.readLine();
            reader.close();
        }
        catch (IOException e){}
        
        if(line == null)
        	line = "0";
        
        return line;
    }

}
