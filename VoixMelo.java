package add;

/**
 * A Bass Voice
 */

public class VoixBasse extends VoixMelo
{
  /**
   * Base Functionality
   */
  /**
   * Constructor
   */
  public VoixBasse(final int start, final int time, final int measures, final int program, final int channel)
  {
    super(start, time, measures, program, channel);
  }
  /**
   * Constructor
   * Randomizes the rest
   */
  public VoixBasse(final int start, final int channel)
  {
    this(start, Voix.pickTime(), Voix.pickMeasures(), VoixBasse.pickBassPrg(), channel);
  }
  /**
   * Picks a random Bass Program
   */
  public static int pickBassPrg()
  {
    return PRGS_BASSE[Utiles.random(PRGS_BASSE.length)];
  }
  /**
   * Bass programs
   */
  public final static int[] PRGS_BASSE = new int[]
          {
                  32, 33, 34, 35, 36, 37, 38, 39, 43, 58
          };

  /**
   * Compose
   */
  /**
   * Fills the pitch array<br>
   * Called by the constructor
   */
  protected void pickH()
  {
    for(int i = 0; i < h.length; ++i)
    {
      h[i] = -1; // Resetting all pitches
    }
    for(int i = 0; i < temps; ++i)
    {
      // Assign a random pitch based on predefined rules
      h[i * Utiles.TICS_PAR_TEMPS] = Utiles.random(2) == 0 ? 36 : Utiles.random(2) == 0 ? 31 : 43;
    }
    // Make the last pitch the same as the first for continuity
    h[temps * Utiles.TICS_PAR_TEMPS] = h[0];
    // Random variations on the pitch, based on conditions
    for(int i = 1; i <= temps; ++i)
    {
      if(Utiles.random(temps) == 0) // Random chance to change the pitch
      {
        if(h[i * Utiles.TICS_PAR_TEMPS] == 36) // If the pitch is 36
        {
          h[i * Utiles.TICS_PAR_TEMPS - (Utiles.random(3) == 0 ? Utiles.TICS_PAR_TEMPS / 2 : 1)] = Utiles.random(2) == 0 ? 35 : 38;
        }
        else // For other pitches
        {
          h[i * Utiles.TICS_PAR_TEMPS - (Utiles.random(3) == 0 ? Utiles.TICS_PAR_TEMPS / 2 : 1)] = Utiles.random(2) == 0 ? h[i * Utiles.TICS_PAR_TEMPS] - 2 : h[i * Utiles.TICS_PAR_TEMPS] + 1;
        }
      }
    }
    repeatH();
  }
  /**
   * Fills the volume array<br>
   * Called by the constructor
   */
  protected void pickVol()
  {
    for(int i = 0; i < vol.length; ++i)
    {
      vol[i] = -1; // Resetting all volume values
    }
  }
  /**
   * Fills the panning array<br>
   * Called by the constructor
   */
  protected void pickPan()
  {
    for(int i = 0; i < pan.length; ++i)
    {
      pan[i] = -1; // Resetting all panning values
    }
  }
  /**
   * Produces a varied VoixMelo from <b>this</b>, with given start and channel
   */
  public VoixMelo vary(final int start, final int channel)
  {
    VoixBasse result = new VoixBasse(start, temps, h.length / temps / Utiles.TICS_PAR_TEMPS, VoixBasse.pickBassPrg(), channel);
    // Copying the pitch array from this instance to the new one
    for(int i = 0; i < h.length; ++i)
    {
      result.h[i] = h[i];
    }
    return result;
  }
}
