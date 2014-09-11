/**
 * Created by Will on 02/09/2014.
 */

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.SparkContext._
import com.datastax.spark.connector._

object CountHashTags{

  def main (args: Array[String]) {

    val conf = new SparkConf(true).set("spark.cassandra.connection.host", "localhost")
      .setJars(Array("target/scala-2.10/spark-streaming-cassandra-demo-assembly-1.0.jar"))
      .set("spark.cleaner.ttl", "3600")
      .setAppName("SparkTwitterApp")

    val sc = new SparkContext("spark://127.0.0.1:7077", "Cassandra Connector Test", conf)

    val tweets = sc.cassandraTable("twitter", "hashtags_by_hour")

    println(Console.CYAN + tweets.count() + " hashtags recorded in table" + Console.BLACK)

  }
}