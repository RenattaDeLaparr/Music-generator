package add;

/**
 * voice
 */

abstract public class Voix
{
  /**
   * Base Functionality
   */
  /**
   * Constructor
   */
  public Voix(final int start, final int time, final int measures, final int program, final int channel)
  {
    this.start = start; // Start time
    this.time = time; // Time (duration)
    end = start + measures * time * Utiles.TICS_PAR_TEMPS; // Calculate the end time
    this.program = program; // MIDI program (instrument)
    this.channel = channel; // MIDI channel
  }
  /** MIDI program (instrument) */
  public final int program;
  /** MIDI channel */
  public final int channel;

  /**
   * Duration
   */
  /**
   * The start time
   */
  private int start = 0;
  /**
   * Gets the start time
   */
  public int getStart()
  {
    return start;
  }
  /**
   * The end time
   */
  private int end = 0;
  /**
   * Gets the end time
   */
  public int getEnd()
  {
    return end;
  }
  /**
   * Translates start and end times by the given amount
   */
  public void translate(final int dt)
  {
    start += dt; // Shift the start time
    end += dt;   // Shift the end time
  }

  /**
   * Play
   */
  /**
   * Fills the given Track up to the given maximum date, transposing to the given key
   */
  abstract public void fillTrack(final javax.sound.midi.Track t, final int maxDate, final int key);

  /**
   * Rhythm
   */
  /** Time (duration) */
  public final int time;
  /**
   * Minimum time per measure
   */
  public final static int MIN_TIME = 4;
  /**
   * Maximum time per measure
   */
  public final static int MAX_TIME = 4;
  /**
   * Draws a random number of times
   */
  public static int drawTime()
  {
    return Utiles.random(MIN_TIME, MAX_TIME); // Return a random time between MIN_TIME and MAX_TIME
  }
  /**
   * Minimum number of measures
   */
  public final static int MIN_MEASURES = 4;
  /**
   * Maximum number of measures
   */
  public final static int MAX_MEASURES = 20;
  /**
   * Draws a random number of measures
   */
  public static int drawMeasures()
  {
    return Utiles.random(MIN_MEASURES, MAX_MEASURES); // Return a random number of measures between MIN_MEASURES and MAX_MEASURES
  }
}
