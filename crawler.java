// import statements
import java.util.*;
import java.net.*;
import java.io.*;
import java.util.regex.*;

// It's easier and ulimately quicker to iterate through this, 
// trying/compiliing one thing at a time

public class crawler
{
  public static void main (String[] args)  throws MalformedURLException, IOException // you'll add some Exceptions to be thrown
  {
    // Input a URL entered by user
    
    Scanner keyboard = new Scanner(System.in);
    System.out.println("Enter a URL to Scan");
    String urlInString  = keyboard.next();
    
    // Declare and instantiate a URL Object.
    URL urlInput = new URL( urlInString );
    
    // Create an Input stream object using the URL; object defined directly above.
    
    InputStream urlStream = urlInput.openStream();
    
    // Use the input stream object defined above as input to a Scanner object
    
    Scanner urlScan = new Scanner( urlStream );
    
    // Pattern for email IDs.
    
    Pattern emailPattern = Pattern.compile( "[A-Za-z0-9+_.-]+@(\\w+).(\\w+)" ); //regex from https://howtodoinjava.com/regex/java-regex-validate-email-address/
    
    // Pattern to recognize URLs and output them as well.
    
    Pattern urlPattern = Pattern.compile("(http://|https://)(www.)?([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{3}.?([a-z]+)?");//regex from https://stackoverflow.com/questions/24924072/website-url-validation-regex-in-java/37864510
    
    // Instantiate structures to collect emails and urls
    
    // ArrayList was chosen as it non synchronized, so we don't need to worry about order of adding elements, 
    // with random access to its elements, it is also dynamic which overall is better for storing and accessing data
    
    //List emails = new ArrayList(); 
    Set<String> emails = new HashSet<String>();
    List <String> urlsToVisit = new ArrayList<String>();
    List <String> urlsVisited = new ArrayList<String>();
    
    urlsVisited.add(urlInString);//saving the initial URL into the list of visited
    
// save the URLs and emails from the root web-link 
    while ( urlScan.hasNext() )
    {  
      // if there is a match with the pattern 
      String token = urlScan.next();
      Matcher emailMatch = emailPattern.matcher( token );
      Matcher urlMatch = urlPattern.matcher( token );
    
       if ( emailMatch.find() ) 
       {
         if (!emails.contains(emailMatch.group()))
         {
         String patternFound = emailMatch.group();
         emails.add(patternFound);
         }
       }
       
       if (urlMatch.find())
       {
         if (!urlsToVisit.contains(urlMatch.group())&& !urlsVisited.contains(urlMatch.group()))
         {
         String patternFound = urlMatch.group();
         urlsToVisit.add(patternFound);
         }
       }
    }
    
    
    while (!urlsToVisit.isEmpty())
    {
         Scanner keyboard2 = new Scanner(System.in);
        
         System.out.println("\nThe next URL is: '"+urlsToVisit.get(0)+"' .\nType 'Yes' if you want to continue and 'No' if you want to stop.");
         String answer  = keyboard2.next();
         
          URL urlInput2 = new URL( urlsToVisit.get(0) );
          InputStream urlStream2 = urlInput2.openStream();
          Scanner urlScan2 = new Scanner( urlStream2 );
        
         if (answer.equals("Yes")||answer.equals("yes")) //if the user's input is lowercase
         {
         urlsVisited.add(urlsToVisit.get(0));
         urlsToVisit.remove(0);
         while ( urlScan2.hasNext() )
         {
         try
    {  
      // if there is a match with the pattern 
      String token2 = urlScan2.next();
      Matcher emailMatch = emailPattern.matcher( token2 );
      Matcher urlMatch = urlPattern.matcher( token2 );
    
       if ( emailMatch.find() ) 
       {
         if (!emails.contains(emailMatch.group()))
         {
         String patternFound = emailMatch.group();
         emails.add(patternFound);
         }
       }
       
       if (urlMatch.find())
       {
         if (!urlsToVisit.contains(urlMatch.group())&& !urlsVisited.contains(urlMatch.group()))
         {
         String patternFound = urlMatch.group();
         urlsToVisit.add(patternFound);
         }
       }
       
         else
           {
             break;
           }
         }
         }
    catch (Exception IOException)
    {
      System.out.println("Bad URL");
    }
       }
    }
         
System.out.println("URLs visited: "+urlsVisited);
System.out.println("Emails found: "+emails);
System.out.println("URLs not visited: "+urlsToVisit);
    }
}

//http://jcsites.juniata.edu/faculty/kruse/cs240/assignments/PatternLab.htm


//sample output: 
//https://en.wikipedia.org/wiki/Camino_de_Santiago: javax.net.ssl.SSLException: Received fatal alert: protocol_version
