package add;

/**
 * A Drum Voice
 */

public class VoixRythme extends Voix
{
  /**
   * Base Functionality
   */
  /**
   * Constructor
   */
  public VoixRythme(final int start, final int time, final int measures, final int program)
  {
    super(start, time, measures, program, 9); // Channel 9 is typically used for drums in MIDI
    v = new int[measures * time * Utiles.TICS_PAR_TEMPS]; // Initialize the array for drum hits
    pickHits(); // Randomize the hits within the measures
  }
  /**
   * Constructor
   * Randomizes the rest
   */
  public VoixRythme(final int start)
  {
    this(start, Voix.pickTime(), Voix.pickMeasures(), VoixRythme.pickDrumInstrument());
  }
  /**
   * Picks a random drum instrument
   */
  public static int pickDrumInstrument()
  {
    return INSTRUS_RYTHME[Utiles.random(INSTRUS_RYTHME.length)];
  }
  /**
   * The drum instruments
   */
  public final static int[] INSTRUS_RYTHME = new int[]
          {
                  35, 36, 37, 38, 39,
                  40, 41, 42, 43, 44,
                  45, 46, 47, 48, 49,
                  50, 51, 52, 53, 54,
                  55, 56, 57, 58, 59,
                  60, 61, 62, 63, 64, 
                  65, 66
          };

  /**
   * Play
   */
  /**
   * Fills the given Track up to the given maximum date, transposing based on the given key
   */
  public void fillTrack(final javax.sound.midi.Track t, final int datemax, final int tonality)
  {
    int start = getDebut();
    for(int end = Math.min(getFin(), datemax), i = Math.max(0, start); i < end; ++i)
    {
      if(v[i - start] > 0) // If there is a drum hit at this position
      {
        t.add(VoixMelo.makeNoteOn(can, prg, v[i - start], i)); // Add a MIDI note to the track
      }
    }
  }
  /** The array for drum hits */
  protected final int[] v;

  /**
   * Compose
   */
  /**
   * Repeats the drum hits from measure to measure
   */
  protected void repeatHits()
  {
    int measureLength = temps * Utiles.TICS_PAR_TEMPS;
    for(int i = measureLength; i < v.length; ++i)
    {
      v[i] = v[i - measureLength]; // Copy the pattern of the first measure throughout the array
    }
  }
  /**
   * Randomizes the drum hits<br>
   * Called by the constructor
   */
  protected void pickHits()
  {
    int measureLength = temps * Utiles.TICS_PAR_TEMPS;
    v[0] = 100; // First beat is always strong
    for(int remainingHits = Utiles.random(measureLength - 1), i = 1; remainingHits > 0 && i < measureLength; ++i)
    {
      if(Utiles.random(measureLength - i) < remainingHits)
      {
        // Randomize hits based on the position within the measure
        v[i] = i % Utiles.TICS_PAR_TEMPS == 0 ? 80 : i % 2 == 0 ? 60 : 40;
        --remainingHits;
      }
    }
    repeatHits(); // Repeat this pattern for all measures
  }
  /**
   * Produces a varied VoixRythme from <b>this</b>, with given start
   */
  public VoixRythme vary(final int start)
  {
    VoixRythme result = new VoixRythme(start, temps, v.length / temps / Utiles.TICS_PAR_TEMPS, VoixRythme.pickDrumInstrument());
    for(int i = 0; i < v.length; ++i)
    {
      result.v[i] = v[i]; // Copy the hit pattern to the new instance
    }
    return result;
  }
}
