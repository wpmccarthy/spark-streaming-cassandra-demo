/**
 * Created by Will McCarthy on 02/09/2014.
 */
object TwitterCredentials{
  val consumerKey = "INSERTYOURKEYSHEREINSERTYOURKEYSHERE"
  val consumerSecret = "INSERTYOURKEYSHEREINSERTYOURKEYSHERE"
  val accessToken = "INSERTYOURKEYSHERE-INSERTYOURKEYSHEREINSERTYOURKEYSHERE"
  val accessTokenSecret = "INSERTYOURKEYSHEREINSERTYOURKEYSHERE"

  def setCredentials(): Unit =
  {
    // Set the system properties so that Twitter4j library used by twitter stream
    // can use them to generat OAuth credentials
    System.setProperty("twitter4j.oauth.consumerKey", consumerKey)
    System.setProperty("twitter4j.oauth.consumerSecret", consumerSecret)
    System.setProperty("twitter4j.oauth.accessToken", accessToken)
    System.setProperty("twitter4j.oauth.accessTokenSecret", accessTokenSecret)
  }
}