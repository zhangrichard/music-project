import java.util.ArrayList;
import java.util.Hashtable;

public class MidiTrack{
    private Hashtable<Character,Integer> noteToPitch;

    private ArrayList<MidiNote> notes;
    private int instrumentId;
    
    // The constructor for this class
    public MidiTrack(int instrumentId){
        notes = new ArrayList<MidiNote>();
        this.instrumentId = instrumentId;
        this.initPitchDictionary();
    }

    // This initialises the noteToPitch dictionary,
    // which will be used by you to convert note letters
    // to pitch numbers
    public void initPitchDictionary(){
        noteToPitch  = new Hashtable<Character, Integer>();
        noteToPitch.put('C', 60);
        noteToPitch.put('D', 62);
        noteToPitch.put('E', 64);
        noteToPitch.put('F', 65);
        noteToPitch.put('G', 67);
        noteToPitch.put('A', 69);
        noteToPitch.put('B', 71);
    }

    // GETTER METHODS
    public ArrayList<MidiNote> getNotes(){
        return notes;
    }
    
    public int getInstrumentId(){
        return instrumentId;
    }
    
    // This method converts notestrings like
    // <<3E3P2E2GP2EPDP8C<8B>
    // to an ArrayList of MidiNote objects 
    // ( the notes attribute of this class )
    public void loadNoteString(String notestring){
        // convert the letters in the notestring to upper case
        notestring = notestring.toUpperCase();
        int duration = 0;
        int pitch = 0;
        int octave = 0;
        String durationString = "";
        
        
        // TODO: Q2. implement this method
     
        for (int i = 0; i < notestring.length(); i++) {
        	//add element to arraylist
        	
        	char c = notestring.charAt(i);
//        	System.out.println(c);
        	   // Q2.a. Notes
        	if (noteToPitch.containsKey(c)){
        		pitch = octave+noteToPitch.get(c);
            	// add to the arraylist
//     		System.out.println(duration);
            	notes.add(new MidiNote(pitch, duration));
        	}
        	
        	// Q2.b. Pauses
        	if(notestring.charAt(i)=='P') {
        		MidiNote note = new MidiNote(100, duration);
        		//100 is random number
        		note.setSilent(true);
        		notes.add(note);
        		durationString = "";
            	duration=0;
        		continue;
        	}
        	// Q2.c. Durations
        	if (Character.isDigit(c)){
        		// append string
        		durationString += Character.getNumericValue(c);
        		duration = Integer.parseInt(durationString);
        		continue;
        	}
            // Q2.d. Octaves
        	if (c == '>'){
        		octave+=12;
        		
        	}
        	if(c=='<'){
        		octave-=12;
        		
        	}
        	// Q2.e. Flat and sharp notes

        	if(c=='#'){
        		// get the last element in arraylist
        		MidiNote m = notes.get(notes.size()-1);
        		//change pitch here
        		m.setPitch(m.getPitch()+1);
        	}
        	if(c=='!'){
        		// get the last element in arraylist
        		MidiNote m = notes.get(notes.size()-1);
        		//change pitch here
        		m.setPitch(m.getPitch()-1);
        	}
        	//reset the duration back to one
        	durationString = "";
        	duration=0;
        	
			
		}

       
        

        
        // Hint1: use a for loop with conditional statements
        // Hint2: Use the get method of the noteToPitch object (Hashtable class)
    }

    public void revert(){
        ArrayList<MidiNote> reversedTrack = new ArrayList<MidiNote>();     
        for ( int i = notes.size() - 1; i >= 0; i--){
            MidiNote oldNote = notes.get(i);
            // create a newNote
            MidiNote newNote = new MidiNote(oldNote.getPitch(), oldNote.getDuration());
            
            // check if the note was a pause
            if(oldNote.isSilent()){
                newNote.setSilent(true);
            }
             
            // add the note to the new arraylist
            reversedTrack.add(newNote);
        }
        notes = reversedTrack;
    }

    // This will only be called if you try to run this file directly
    // You may use this to test your code.
    public static void main(String[] args){
        String notestring = "<<3E3P2E2GP2EPDP8C<8B>3E3P2E2GP2EPDP8C<8B>";
        String test = "cdefgab";
        String test1 = "d4c8c8c";
        String test2=">>4cppppd<<4c";
        int instrumentID = 2;
        MidiTrack newTrack = new MidiTrack(instrumentID);
        newTrack.loadNoteString(notestring);
        MusicInterpreter mi = new MusicInterpreter();
        mi.setBPM(1200);
        System.out.println(newTrack.getNotes().toString());
        // Build the MidiTrack object
        // Build a MusicInterpreter and set a playing speed
        // Load the track and play it
        mi.loadSingleTrack(newTrack);
        mi.play();
        
        // Close the player so that your program terminates
        mi.close();
    }
}
