package add;

/**
 * A JApplet
 */

public class Ition extends javax.swing.JApplet implements javax.sound.midi.MetaEventListener
{
  /**
   * Base Functionality
   */
  /**
   * Constructor
   */
  public Ition()
  {
    label_.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 32)); // font of the label
    getContentPane().add(label_); // adding the label to the applet's content pane
  }
  /**
   * label_
   */
  private final javax.swing.JLabel label_ = new javax.swing.JLabel("Ry", javax.swing.SwingConstants.CENTER); // Label for display, centered with default text "Ry"

  /**
   * Timer for updating the labeling
   */
  private final javax.swing.Timer labelleur_ = new javax.swing.Timer(1000, new java.awt.event.ActionListener()
  {
    public void actionPerformed(final java.awt.event.ActionEvent e)
    {
      // On every tick (1 second) >> update
      char c1 = (char)('A' + Utiles.random(26)); // random letter
      // the second character
      char c2 = "AEIOU".indexOf(c1) >= 0 ? "abcdefghijklmn".charAt(Utiles.random(16)) : "cashrd".charAt(Utiles.random(5)); //it could be cooler
      label_.setText("" + c1 + c2); // Set the new text for the label
    }
  });

  /**
   * Initialise
   */
  public void init()
  {
    if(sequencer_==null)
    {
      try
      {
        sequencer_=javax.sound.midi.MidiSystem.getSequencer();
        sequencer_.addMetaEventListener(this);
        sequencer_.open();
      }
      catch(Exception x)
      {
        x.printStackTrace();
        sequencer_=null;
      }
    }
  }
  /**  */
  public void start()
  {
    parle();
    compositeur_.start();
    enJeu_=true;
    labelleur_.start();
  }
  /**  */
  public void stop()
  {
    enJeu_=false;
    tais();
    compositeur_.stop();
    labelleur_.stop();
  }
  /**  */
  public void destroy()
  {
    sequencer_.close();
    sequencer_=null;
  }

  private static javax.sound.midi.Sequencer sequencer_=null;

  private javax.sound.midi.Sequence aJouer_=null;

  private boolean enJeu_=false;


  public void meta(final javax.sound.midi.MetaMessage m)
  {
    if(m.getType()==47 && sequencer_!=null && sequencer_.isOpen()) // 47 >>> is end of track
    {
      sequencer_.stop();
      if(enJeu_)
      {
        parle();
      }
    }
  }
  /**
   *Sequencer
   */
  protected void tais()
  {
    if(sequencer_!=null && sequencer_.isOpen())
    {
      sequencer_.stop();
    }
  }



  protected void parle()
  {
    if(sequencer_!=null && sequencer_.isOpen())
    {
      if(sequencer_.isRunning())
      {
        tais();
      }
      if(aJouer_==null)
      {
        aJouer_=makeSequence();
      }
      try
      {
        sequencer_.setSequence(aJouer_);
        sequencer_.start();
        aJouer_=null;
      }
      catch(Exception x)
      {
        x.printStackTrace();
      }
    }
  }

  /**
   * Composer
   */

  private final javax.swing.Timer compositeur_=new javax.swing.Timer(10000,new java.awt.event.ActionListener()
  {
    public void actionPerformed(final java.awt.event.ActionEvent e)
    {
      if(aJouer_==null && enJeu_)
      {
        aJouer_=makeSequence();
      }
    }
  });

  public final static int DUREE=300;


  private int tonalite_=Utiles.random(12)-6;


  protected javax.sound.midi.Sequence makeSequence()
  {
    javax.sound.midi.Sequence result=null;
    if(Utiles.random(5)==0)
    {
      tonalite_+=Utiles.random(2)==0 ? 5 : 7;
      while(tonalite_>=6)
      {
        tonalite_-=12;
      }
    }
    try
    {
      result=new javax.sound.midi.Sequence(javax.sound.midi.Sequence.PPQ,Utiles.TICS_PAR_TEMPS);
      fillBasse(result.createTrack());
      fillRythme(result.createTrack());
      fillMelo(result);
    }
    catch(Exception x)
    {
      x.printStackTrace();
    }
    return result;
  }
  /**
   * VoixBasse
   */
  protected VoixBasse makeVoixBasse(final int debut, final int can)
  {
    VoixBasse result=null;
    if(bassesUtiles_.isEmpty() || Utiles.random(3)==0)
    {
      result=new VoixBasse(debut,can);
      if(bassesUtiles_.size()<4 || Utiles.random(5)==0)
      {
        while(bassesUtiles_.size()>=4)
        {
          bassesUtiles_.removeElementAt(Utiles.random(bassesUtiles_.size()));
        }
        bassesUtiles_.addElement(result);
      }
    }
    else
    {
      result=(VoixBasse)((VoixBasse)bassesUtiles_.elementAt(Utiles.random(bassesUtiles_.size()))).varie(debut,can);
    }
    return result;
  }
  /**  */
  protected final java.util.Vector bassesUtiles_=new java.util.Vector();
  /**  */
  protected VoixBasse voixBasse_=null;
  /**
   *
   */
  protected void fillBasse(final javax.sound.midi.Track t)
  {
    if(voixBasse_==null)
    {
      voixBasse_=makeVoixBasse(0,0);
    }
    do
    {
      voixBasse_.fillTrack(t,DUREE,tonalite_);
      voixBasse_=makeVoixBasse(voixBasse_.getFin()+(Utiles.random(8)==0 ? Utiles.TICS_PAR_TEMPS*Voix.tireTemps()*Voix.MIN_MESURES : 0),0);
    }
    while(voixBasse_.getFin()<DUREE);
    voixBasse_.fillTrack(t,DUREE,tonalite_);
    voixBasse_.translate(-DUREE);
  }
  /**  */
  protected final java.util.Vector rythmesUtiles_=new java.util.Vector();
  /**  */
  protected final java.util.Vector voixRythme_=new java.util.Vector();
  /**
   *  VoixRythme
   */
  protected VoixRythme makeVoixRythme()
  {
    VoixRythme result=null;
    if(rythmesUtiles_.isEmpty() || Utiles.random(3)==0)
    {
      result=new VoixRythme(Utiles.random(DUREE));
      if(rythmesUtiles_.size()<4 || Utiles.random(5)==0)
      {
        while(rythmesUtiles_.size()>=4)
        {
          rythmesUtiles_.removeElementAt(Utiles.random(rythmesUtiles_.size()));
        }
        rythmesUtiles_.addElement(result);
      }
    }
    else
    {
      result=(VoixRythme)((VoixRythme)rythmesUtiles_.elementAt(Utiles.random(rythmesUtiles_.size()))).varie(Utiles.random(DUREE));
    }
    return result;
  }
  /**
   *
   */
  protected void fillRythme(final javax.sound.midi.Track t)
  {
    int n=Utiles.random(1,NB_VOIX_RYTHME*DUREE*4/Utiles.TICS_PAR_TEMPS/(Voix.MAX_TEMPS+Voix.MIN_TEMPS)/(Voix.MAX_MESURES+Voix.MIN_MESURES));
    while(voixRythme_.size()<n)
    {
      voixRythme_.addElement(makeVoixRythme());
    }
    t.add(VoixMelo.makeProgramChange(9,0,0));
    for(int i=voixRythme_.size()-1;i>=0;--i)
    {
      VoixRythme v=(VoixRythme)voixRythme_.elementAt(i);
      v.fillTrack(t,DUREE,tonalite_);
      if(v.getFin()<DUREE)
      {
        voixRythme_.removeElementAt(i);
      }
      else
      {
        v.translate(-DUREE);
      }
    }
    t.add(VoixMelo.makeProgramChange(9,0,DUREE));
  }
  /**
   *
   */
  public final static int NB_VOIX_RYTHME=4;
  /**  */
  protected final java.util.Vector melosUtiles_=new java.util.Vector();
  /**  */
  protected final java.util.Vector voixMelo_=new java.util.Vector();
  /**
   *
   */
  protected VoixMelo makeVoixMelo(final int can)
  {
    VoixMelo result=null;
    if(melosUtiles_.isEmpty() || Utiles.random(3)==0)
    {
      result=new VoixMelo(Utiles.random(DUREE),can);
      if(melosUtiles_.size()<4 || Utiles.random(5)==0)
      {
        while(melosUtiles_.size()>=4)
        {
          melosUtiles_.removeElementAt(Utiles.random(melosUtiles_.size()));
        }
        melosUtiles_.addElement(result);
      }
    }
    else
    {
      result=((VoixMelo)melosUtiles_.elementAt(Utiles.random(melosUtiles_.size()))).varie(Utiles.random(DUREE),can);
    }
    return result;
  }
  /**
   *
   */
  protected void fillMelo(final javax.sound.midi.Sequence seq)
  {
    int n=Utiles.random(1,NB_VOIX_MELO*DUREE*4/Utiles.TICS_PAR_TEMPS/(Voix.MAX_TEMPS+Voix.MIN_TEMPS)/(Voix.MAX_MESURES+Voix.MIN_MESURES));
    while(voixMelo_.size()<n)
    {
      voixMelo_.addElement(makeVoixMelo(trouveCanalLibre()));
    }
    for(int i=voixMelo_.size()-1;i>=0;--i)
    {
      VoixMelo v=(VoixMelo)voixMelo_.elementAt(i);
      v.fillTrack(seq.createTrack(),DUREE,tonalite_);
      if(v.getFin()<DUREE)
      {
        voixMelo_.removeElementAt(i);
      }
      else
      {
        v.translate(-DUREE);
      }
    }
  }
  /**
   *
   */
  protected int trouveCanalLibre()
  {
    int result=-1;
    for(int i=0;i<16 && result<0;++i)
      if(i!=9 && voixBasse_.can!=i)
      {
        int j=voixMelo_.size()-1;
        while(j>=0 && ((VoixMelo)voixMelo_.elementAt(j)).can!=i)
        {
          --j;
        }
        if(j<0)
        {
          result=i;
        }
      }
    return result;
  }
  /**
   *
   */
  public final static int NB_VOIX_MELO=6;
}