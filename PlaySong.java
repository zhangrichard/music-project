import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class PlaySong{
    public static void main( String[] args){
        MusicInterpreter myMusicPlayer = new MusicInterpreter();
        // uncomment this line to print the available instruments
        //System.out.println(myMusicPlayer.availableInstruments());

        // TODO: Q3. b

        // Create a Song object
        String song_file_path = "./bin/data/03.txt";
        
        Song mySong = new Song();
        try {
			mySong.loadFromFile(song_file_path);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("cannot not open the file");
		}
        MusicInterpreter mi = new MusicInterpreter();
        mi.loadSong(mySong);
        mi.play();
////        
        mi.close();
        
        // load text file using the given song_filename, 
        // remember to catch the appropriate Exceptions

        // Play it
    }
}