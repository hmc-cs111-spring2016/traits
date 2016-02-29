package logging

/******************************************************************************
 * Thin interface
 ******************************************************************************/
trait Logger {  
  def log(message: String): Unit   // abstract method
  
  def infoTag    = "[info]"
  def warningTag = "[warning]"
  def errorTag   = "[error]"

  // these methods are implemented in terms of the abstract method
  def info(message: String) = log(s"$infoTag $message")
  def warning(message: String) = log(s"$warningTag $message")
  def error(message: String) = log(s"$errorTag $message")
}

/******************************************************************************
 * Concrete classes
 ******************************************************************************/
// concrete implementation: do nothg
class NoOpLogger extends Logger {
  override def log(message: String) = {}
}

// concrete implementation: to console
class ConsoleLogger extends Logger {
  override def log(message: String) {
    println(message)
  }
}

// concrete implementation: to Twitter
class TwitterLogger extends Logger {
  import twitter4j._

  val twitter = new TwitterFactory().getInstance()

  override def log(message: String) = {
    twitter.updateStatus(message)
  }
  
  override def infoTag = ""
  override def warningTag = "Heads up: "
  override def errorTag = "Sorry, "

}

// concrete implementation: Mac notifications
class NotificationLogger extends Logger {
  lazy val runtime = Runtime.getRuntime
  val title = ""
  
  def command(message: String) = 
    s"""display notification "${message}" with title "${title}""""
  
  def execute(appleScript: String) = {
    val code = Array("osascript", "-e", appleScript)
    runtime.exec(code)
  }
  
  override def log(message: String) = execute(command(message))
}

/******************************************************************************
 * Mixins
 ******************************************************************************/
trait Debugging extends Logger {
  def debugTag = "[debug]"
  def debug(message: String) = 
    log(s"debugTag $message")
}

/******************************************************************************
 * Extending existing traits
 ******************************************************************************/
trait ColoredConsoleLogger extends ConsoleLogger {
  override def infoTag = Console.GREEN + super.infoTag + Console.RESET
  override def warningTag = Console.YELLOW + super.warningTag + Console.RESET
  override def errorTag = Console.RED + super.errorTag + Console.RESET
}

trait SuppressInfo extends Logger {
  override def info(message: String) = {}
}

trait SuppressWarnings extends Logger {
  override def warning(message: String) = {}
}

trait SuppressErrors extends Logger {
  override def error(message: String) = {}
}

/******************************************************************************
 * Stacked traits
 ******************************************************************************/

trait Timestamping extends Logger {
  def timestamp = new java.util.Date()

  abstract override def log(message: String) = 
    super.log(s"[$timestamp] $message")
}

trait Lowercasing extends Logger {
  abstract override def log(message: String) = 
    super.log(message.toLowerCase())
}
