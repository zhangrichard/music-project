import java.util.ArrayList;
import java.util.Hashtable;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.lang.*;


public class SongWriter{
    private Hashtable<Integer,String> pitchToNote;
    
    // The constructor of this class
    public SongWriter(){
        this.initPitchToNoteDictionary();
    }
    
    // This initialises the pitchToNote dictionary,
    // which will be used by you to convert pitch numbers
    // to note letters
    public void initPitchToNoteDictionary(){
        pitchToNote  = new Hashtable<Integer, String>();
        pitchToNote.put(60, "C");
        pitchToNote.put(61, "C#");
        pitchToNote.put(62, "D");
        pitchToNote.put(63, "D#");
        pitchToNote.put(64, "E");
        pitchToNote.put(65, "F");
        pitchToNote.put(66, "F#");
        pitchToNote.put(67, "G");
        pitchToNote.put(68, "G#");
        pitchToNote.put(69, "A");
        pitchToNote.put(70, "A#");
        pitchToNote.put(71, "B");
    }

    // This method converts a single MidiNote to its notestring representation
    public String noteToString(MidiNote note){
        String result = "";
        // TODO: Q4.a.
        String duration = "";
        if(note.getDuration() == 1){}
        else duration = ""+note.getDuration();
        
        int pitch = note.getPitch();
        boolean isSilent = note.isSilent();
        int octave = note.getOctave();
        
        System.out.println(octave);
        String pitchString = pitchToNote.get(pitch-octave*12);
        
        if (isSilent) result = duration+"p";
        else result = duration + pitchString;
        System.out.println(result);
        return result;
    }

    // This method converts a MidiTrack to its notestring representation.
    // You should use the noteToString method here
    public  String trackToString(MidiTrack track){
        ArrayList<MidiNote> notes = track.getNotes();
        String result = "";
        int previous_octave = 0;
        MidiNote current_note;
        // TODO: Q4.b.
        for (MidiNote midiNote : notes) {
        	//special case for p
        	if(midiNote.isSilent() == true){
        		int duration = midiNote.getDuration();
        		result +=duration+"p";
        		continue;
        	}
			int currentOctave = midiNote.getOctave();
        	int changeOfOctave = previous_octave-currentOctave;
        	//handle octave
        	
        	if (changeOfOctave>0) {
				while(changeOfOctave>0)
				{
					result +=">";
					changeOfOctave--;
				}
			}
        	
        	else if (changeOfOctave<0){
        		while(changeOfOctave<0)
        		{
        			result +="<";
        			changeOfOctave++;
        		}
        	}
        	
        	String note = noteToString(midiNote);
        	result += note;
        	previous_octave = currentOctave;
		}
        /* 
        * A hint for octaves: if the octave of the previous MidiNote was -1
        * and the octave of the current MidiNote is +3, we will have 
        * to append ">>>>" to the result string.
        */

        return result;
    }
    public void writeToFile( Song s1 , String file_path) throws IOException{
    	BufferedWriter bw = new BufferedWriter(new FileWriter(file_path));
    	String myName = s1.getName();
    	int myBeatsPerMinute = s1.getBPM();
    	String mySoundBank = s1.getSoundbank();
    	bw.write("name = "+myName+"\n");
    	bw.write("bpm = "+ myBeatsPerMinute + "\n");
    	bw.write("soundbank = "+mySoundBank + "\n");
    	System.out.println("write inside");
    	for (MidiTrack mt : s1.getTracks()) {
			bw.write("instrument = "+ mt.getInstrumentId() + "\n");
			bw.write("track = " + trackToString(mt)+"\n");
		}
    	bw.flush();
    	bw.close();
    }
    // TODO Q4.c.
    // Implement the void writeToFile( Song s1 , String file_path) method
    // This method writes the properties of the Song s1 object
    // and writes them into a file in the location specified by 
    // file_path. This file should have the same format as the sample
    // files in the 'data/' folder.

    public static void main( String[] args){
        // TODO: Q4.d.
    	System.out.print("asdf\nasdf");
        // Create a Song object
    	String song_file_path = "./bin/data/07.txt";
    	String song_write_path = "./bin/data/test07.txt";
        // Load text file using the given song_filename, remember to 
        // catch the appropriate Exceptions, print meaningful messages!
        // e.g. if the file was not found, print "The file FILENAME_HERE was not found"

        // call the revert method of the song object.
        
        // Create a SongWriter object here, and call its writeToFile( Song s, String file_location) method.
    	Song s = new Song();
    	try {
			s.loadFromFile(song_file_path);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("cannot open file");
		}
    	s.revert();
    	SongWriter sw = new SongWriter();
    	try {
			sw.writeToFile(s, song_write_path);
			System.out.println("write");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("cannot write file");
		}
    }
}