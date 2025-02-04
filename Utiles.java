package add;

/**
 * Utilities
 */

public class Utiles
{
  /**
   * Random number between 0 and n
   */
  public static int random(final int n)
  {
    return RANDOM.nextInt(n); // Return a random integer between 0 (inclusive) and n (exclusive)
  }
  /**
   * Random number between min and max
   */
  public static int random(final int min, final int max)
  {
    return min + random(max - min + 1); // Return a random integer between min (inclusive) and max (inclusive)
  }
  /**  */
  public final static java.util.Random RANDOM = new java.util.Random(System.currentTimeMillis()); // Random number generator initialized with the current time

  /**
   * Rhythm
   */
  /**
   * The number of ticks per beat (4) :D
   */
  public final static int TICS_PAR_TEMPS = 4; // Standard number of MIDI ticks per quarter note (common time)
}
