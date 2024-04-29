// Copyright Eric Chauvin 2020 - 2024.



// This is licensed under the GNU General
// Public License (GPL).  It is the
// same license that Linux has.
// https://www.gnu.org/licenses/gpl-3.0.html


import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


// For Serializable:
import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;


public class WebSites implements ActionListener,
                                 Serializable
  {
  // It needs to have a version UID since it's
  // serializable.
  public static final long serialVersionUID = 1;

  private MainApp mApp;
  private Timer getURLTimer;
  private FifoStrA urlFifo;
  private URLFileDictionary urlDictionary;



  private WebSites()
    {
    }


  public WebSites( MainApp appToUse )
    {
    mApp = appToUse;
    StrA fileName = new StrA(
                      "\\ALang\\UrlDictionary.txt" );

    urlDictionary = new URLFileDictionary( mApp,
                                            fileName );
    urlDictionary.readFromFile();
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



  public void timerStart()
    {
    urlFifo = new FifoStrA( mApp, 1024 * 16 );

    addURLsToFifo();
    setupTimer();
    }



  public void analyze()
    {
    AnalyzeNewLinks newLinks = new AnalyzeNewLinks(
                                mApp, urlDictionary );
    Thread aThread = new Thread( newLinks );
    aThread.start();
    }


  public void analyzeSpanish()
    {
    AnalyzeSpanish spanish = new AnalyzeSpanish(
                             mApp, urlDictionary );
    Thread aThread = new Thread( spanish );
    aThread.start();
    }


  public void cancel()
    {
    if( getURLTimer != null )
      {
      getURLTimer.stop();
      getURLTimer = null;
      }

    urlDictionary.saveToFile();
    }



  private void setupTimer()
    {
    int delay = 1000 * 2;
    getURLTimer = new Timer( delay, this );
    getURLTimer.start();
    mApp.showStatusAsync( "Timer started." );
    }



  public void actionPerformed( ActionEvent event )
    {
    try
    {
    // String paramS = event.paramString();
    String command = event.getActionCommand();
    if( command == null )
      {
      // mApp.showStatusAsync( "ActionEvent command is null." );
      doTimerEvent();
      return;
      }

    // showStatus( "ActionEvent Command is: " + command );
    }
    catch( Exception e )
      {
      mApp.showStatusAsync( "Exception in ActionPerformed()." );
      mApp.showStatusAsync( e.getMessage() );
      }
    }



  private void doTimerEvent()
    {
    StrA urlToGet = urlFifo.getValue();
    if( urlToGet == null )
      {
      mApp.showStatusAsync( "\n\nNothing in Fifo." );
      getURLTimer.stop();
      urlDictionary.saveToFile();
      return;
      }

    // mApp.showStatusAsync( "\nurlToGet is:\n" + urlToGet );
    URLFile uFile = urlDictionary.getValue( urlToGet );
    if( uFile == null )
      uFile = new URLFile( mApp, urlToGet );

    uFile.setAnchorsPulledFalse();
    urlDictionary.setValue( urlToGet, uFile );

    String fileName = uFile.getFileName().toString();
    fileName = "\\ALang\\URLFiles\\" + fileName;
    // mApp.showStatusAsync( "File name: " + fileName );
    String urlS = urlToGet.toString();
    URLClient urlClient = new URLClient( mApp,
                                 fileName,
                                 urlS );

    Thread urlThread = new Thread( urlClient );
    urlThread.start();
    }



  public void showCharacters()
    {
    // 126 is the tilde character.
    // 127 is delete.
    // 161 is upside down exclamation.
    // 169 is copyright.
    // 174 is rights symbol.
    // 209 is capital N like el niNa.
    // 232 through 235 is e.

    // C1 Controls and Latin-1 Supplement (0080 00FF)
    // Latin Extended-A (0100 017F)
    // Latin Extended-B (0180 024F)


    mApp.showStatusAsync( "\n\n" );
    // for( int count = 0x100; count <= 0x17F; count++ )
    for( int count = 161; count <= 255; count++ )
      {
      // Integer.toHexString(n).toUpperCase()

      char testC = (char)count;
      mApp.showStatusAsync( "" + count + ") " + testC );
      }

    mApp.showStatusAsync( "\n\n" );
    }



  public void addURLsToFifo()
    {
    // Not this: "https://news.google.com/" ));

    // urlFifo.setValue( new StrA(
     //             "https://finance.yahoo.com/news/" ));

    // urlFifo.setValue( new StrA(
    //                 "https://www.ft.com/" ));

    // urlFifo.setValue( new StrA(
    //           "https://news.bitcoin.com/" ));

    // urlFifo.setValue( new StrA(
    //            "https://www.foxnews.com/" ));

    // urlFifo.setValue( new StrA(
    //         "https://www.msnbc.com/" ));


    urlFifo.setValue( new StrA(
                  "https://www.foxbusiness.com/" ));


  // Add it to isGoodFullFile() too.
  // _And_ URLParse.hasValidDomain()

    urlFifo.setValue( new StrA(
     "https://www.sciencenews.org/" ));

    // urlFifo.setValue( new StrA(
     // "https://www.dcourier.com/" ));

    // urlFifo.setValue( new StrA(
      // "https://www.paysonroundup.com/" ));

    // urlFifo.setValue( new StrA(
      // "https://www.paysonroundup.com/news/" ));

    // urlFifo.setValue( new StrA(
       //           "https://www.azcentral.com/" ));

    // urlFifo.setValue( new StrA(
    //              "https://llvm.org/" ));

    // urlFifo.setValue( new StrA(
       // "https://www.radiationnetwork.com/" ));

    // urlFifo.setValue( new StrA(
       // "https://www.mineralab.com/" ));

    // urlFifo.setValue( new StrA(
      // "https://www.mineralab.net/" ));

    // White Mountain Independent news.
    // urlFifo.setValue( new StrA(
       // "https://www.wmicentral.com/" ));

    urlFifo.setValue( new StrA(
      "https://krebsonsecurity.com/" ));

    urlFifo.setValue( new StrA(
      "https://blog.cryptographyengineering.com/" ));

    urlFifo.setValue( new StrA(
       "https://blogs.imf.org/" ));

    urlFifo.setValue( new StrA(
  "https://libertystreeteconomics.newyorkfed.org/" ));

    urlFifo.setValue( new StrA(
      "https://www.iacr.org/news/" ));

    urlFifo.setValue( new StrA(
      "https://www.schneier.com/" ));

  urlFifo.setValue( new StrA(
    "https://www.openssl.org/blog/"
                               ));

  urlFifo.setValue( new StrA(
    "https://www.openssl.org/news/vulnerabilities.html"
                               ));

  // urlFifo.setValue( new StrA(
   //   "https://www.usgs.gov/" ));


  // urlFifo.setValue( new StrA(
    // "https://www.usgs.gov/products/software"
     //                          ));


  // urlFifo.setValue( new StrA(
    // "https://www.usgs.gov/news"
    //                           ));




  // urlFifo.setValue( new StrA(
    // "https://www.themountainmail.com/"
    //                           ));

  // urlFifo.setValue( new StrA(
    // "https://www.chaffeecountytimes.com/special_editions/mountain_guide/"
    //                           ));

  urlFifo.setValue( new StrA(
    "https://www.chaffeecountytimes.com/"
                               ));

  // urlFifo.setValue( new StrA(
    // "https://www.theflume.com/"
     //                          ));

  urlFifo.setValue( new StrA(
  "https://coloradomtn.edu/campuses/leadville/"
                               ));

  urlFifo.setValue( new StrA(
 "https://coloradomtn.edu/campuses/leadville/programs/"
                               ));

  urlFifo.setValue( new StrA(
  "https://coloradomtn.edu/campuses/leadville/classes/"
                               ));

  urlFifo.setValue( new StrA(
  "https://coloradomtn.edu/campuses/leadville/faculty-staff/"
                               ));

  urlFifo.setValue( new StrA(
 "https://coloradomtn.edu/campuses/leadville/faculty-staff/page/2/"
                               ));


  urlFifo.setValue( new StrA(
 "https://coloradomtn.edu/campuses/leadville/faculty-staff/page/3/"
                               ));

  // Four bytes problem?
  // urlFifo.setValue( new StrA(
 // "https://coloradomtn.edu/"
  //                             ));

  urlFifo.setValue( new StrA(
 "https://coloradomtn.edu/cmc-news/"
                               ));

//////////
// FRED:

  urlFifo.setValue( new StrA(
  "https://news.research.stlouisfed.org/category/fred-announcements/"
                               ));

  urlFifo.setValue( new StrA(
   "https://fredblog.stlouisfed.org/"
                               ));

  urlFifo.setValue( new StrA(
  "https://fred.stlouisfed.org/"
                               ));

  urlFifo.setValue( new StrA(
  "https://research.stlouisfed.org/publications/"
                               ));

  urlFifo.setValue( new StrA(
  "https://research.stlouisfed.org/publications/research-news/"
                               ));

  urlFifo.setValue( new StrA(
  "https://research.stlouisfed.org/wp/"
                               ));

  urlFifo.setValue( new StrA(
  "https://research.stlouisfed.org/publications/cryptocurrencies-and-fintech/"
                               ));

  urlFifo.setValue( new StrA(
  "https://research.stlouisfed.org/publications/page1-econ/"
                               ));


  // urlFifo.setValue( new StrA(
  // "https://www.politico.com/"
  //                             ));

  urlFifo.setValue( new StrA(
  "https://www.artificialintelligence-news.com/"
                               ));


  urlFifo.setValue( new StrA(
    "https://www.durangoherald.com/categories/local-news/"
                               ));

  // urlFifo.setValue( new StrA(
     // "https://www.durangoherald.com/" ));


  // http 403 forbidden.
  // What's the deal with this?
  //   urlFifo.setValue( new StrA(
  //  "https://www.economist.com/"
  //                             ));


    urlFifo.setValue( new StrA(
      "https://www.leadvilleherald.com/" ));

    // urlFifo.setValue( new StrA(
      // "https://www.sltrib.com/" ));

    // urlFifo.setValue( new StrA(
      // "https://www.moabtimes.com/" ));

    // urlFifo.setValue( new StrA(
     //             "https://noticiasya.com/el-paso/" ));

    // urlFifo.setValue( new StrA(
      //         "https://diario.mx/seccion/El_Paso/" ));

    // urlFifo.setValue( new StrA(
      //            "https://www.la-prensa.com.mx/" ));

    // urlFifo.setValue( new StrA(
       //           "https://www.milenio.com/" ));

    // addEmptyFilesToFifo();
    }



  private void addEmptyFilesToFifo()
    {
    mApp.showStatusAsync( "Adding empty files to Fifo." );
    StrA fileS = urlDictionary.makeKeysValuesStrA();

    StrArray linesArray = fileS.splitChar( '\n' );
    final int last = linesArray.length();
    int howMany = 0;
    for( int count = 0; count < last; count++ )
      {
      StrA line = linesArray.getStrAt( count );
      URLFile uFile = new URLFile( mApp );
      uFile.setFromStrA( line );
      StrA fileName = uFile.getFileName();

      // mApp.showStatusAsync( "" + line );
      StrA filePath = new StrA( "\\ALang\\URLFiles\\" );
      filePath = filePath.concat( fileName );
      // mApp.showStatusAsync( "filePath: " + filePath );

      if( !FileUtility.exists( filePath ))
        {
        StrA urlToGet = uFile.getUrl();
        if( !isGoodFullFile( urlToGet ))
          continue;

        howMany++;
        // 3 seconds times 100 = 300 seconds. 5 Minutes.
        if( howMany > 10000 )
          break;

        mApp.showStatusAsync( "\nAdding to Fifo: (" +
                                   howMany + ") " +
                                   urlToGet );

        urlFifo.setValue( urlToGet );
        }
      }
    }



  private boolean isGoodFullFile( StrA in )
    {
    if( in.containsStrA( new StrA(
                 ".msnbc.com/" )))

    if( in.containsStrA( new StrA(
                      "wmicentral.com" )))
      return true;

    if( in.containsStrA( new StrA(
                      "radiationnetwork.com" )))
      return true;

    if( in.containsStrA( new StrA( ".foxnews.com" )))
      return false;

    if( in.containsStrA( new StrA( ".foxbusiness.com" )))
      return false;

    if( !URLParse.isSpanish( in ))
      return false;

    if( in.containsStrA( new StrA( "noticiasya.com/" )))
      return false;

    return true;
    }




  }
