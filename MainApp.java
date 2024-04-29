// Copyright Eric Chauvin 2020 - 2024.


// This is licensed under the GNU General
// Public License (GPL).  It is the
// same license that Linux has.
// https://www.gnu.org/licenses/gpl-3.0.html




// Language Project.

// "\\ALang\\URLFiles\\"


// Each document has a histogram of words.
// Word counts.
// The list of words and their counts, their
// values, is a vector.  How close is that
// vector to other document vectors?  Are
// the documents very similar?



// SSLSocket
// https://github.com/openjdk-mirror/jdk7u-jdk/blob/master/src/share/classes/javax/net/ssl/SSLSocket.java

// OpenJdk
// https://github.com/openjdk-mirror/


import javax.swing.SwingUtilities;

// For Serializable:
import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;


class MainApp implements Runnable, Serializable
  {
  public static final String versionDate =
                              "4/29/2024";

  public static final long serialVersionUID = 1;

  private MainWindow mainWin;
  // public ConfigureFile mainConfigFile;
  private String[] argsArray;
  private StrA programDirectory;



  public static void main( String[] args )
    {
    MainApp mApp = new MainApp( args );
    SwingUtilities.invokeLater( mApp );
    }


  // For Serializable:
  private void writeObject(
                   ObjectOutputStream stream )
                           throws IOException
    {
    stream.defaultWriteObject();
    }

  private void readObject(
                     ObjectInputStream stream )
                     throws IOException,
                     ClassNotFoundException
    {
    stream.defaultReadObject();
    }



  @Override
  public void run()
    {
    setupProgram();
    }



  private MainApp()
    {
    }


  public MainApp( String[] args )
    {
    argsArray = args;
    }



  private void setupProgram()
    {
    // checkSingleInstance()

     // All programs need to have a batch file give
    // it the program directory so they're not stuck
    // in that directory.
    programDirectory = new StrA(
                           "\\EricMain\\Language" );

    int length = argsArray.length;
    if( length > 0 )
      programDirectory = new StrA( argsArray[0] );

/*
    String mainConfigFileName = programDirectory +
                                      "MainConfigure.txt";

    mainConfigFile = new ConfigureFile( this,
                                mainConfigFileName );
    */

    mainWin = new MainWindow( this, "Language" );
    mainWin.initialize();

    /*
    showStatus( " " );
    showStatus( "argsArray length: " + length );
    for( int count = 0; count < length; count++ )
      showStatus( argsArray[count] );
    */

    // showStatus( " " );
    }



  public void showStatusAsync( String toShow )
    {
    if( mainWin == null )
      return;

    mainWin.showStatusAsync( toShow );
    }



  public void clearStatus()
    {
    if( mainWin == null )
      return;

    mainWin.clearStatus();
    }




  }
