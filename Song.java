import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.util.ArrayList;

public class Song{
    String myName;
    int myBeatsPerMinute;
    String mySoundbank;
    ArrayList<MidiTrack> myTracks;
    
    // The constructor of this class
    public Song(){
        myTracks = new ArrayList<MidiTrack>();
        myBeatsPerMinute = 200;
        mySoundbank = "";
        myName = "Default_Name";
    }

    // GETTER METHODS

    public String getName(){
       return myName;
    }

    public String getSoundbank(){
       return mySoundbank;
    }
    
    public int getBPM(){
        return myBeatsPerMinute;
    }

    public ArrayList<MidiTrack> getTracks(){
        return myTracks;
    }

    // TODO: Q3.a.
    public void loadFromFile(String file_path) throws IOException{
   
    	    BufferedReader br = new BufferedReader(new FileReader(file_path));
    	    MidiTrack track = new MidiTrack(0);
    	    String line = br.readLine();
    	    
    	    while (line != null){
    	    	String title = line.trim().split("=")[0].trim();
    	    	String value = line.trim().split("=")[1].trim();
    	    	
    	    	switch (title) {
				case "name":
					myName = value;
					break;
				case "bpm":
					myBeatsPerMinute = Integer.parseInt(value);
					break;
				case "soundbank":
					mySoundbank = value;
					break;
				case "instrument":
					// the instrument for the later track
					 track = new MidiTrack( Integer.parseInt(value));
				
					 break;
				case "track" :				
					track.loadNoteString(value);
					myTracks.add(track);
					break;
				default:
					break;
				}
    	    	line = br.readLine();
    	    }
    		br.close();
//    	for (String string : result) {
//			String title = (string.trim());
//		}
//		} catch (Exception e) {
//			throw (new IOException("connot open filesssss"));
//			// TODO: handle exception
//		}
    }
    // Implement void loadFromFile(String file_path) method
    // This method loads the properties and build the tracks of this
    // song object from a file in the location specified by 
    // file

    public void revert(){
        for (int i = 0; i<myTracks.size(); i++){
            myTracks.get(i).revert();
        }
    }
}