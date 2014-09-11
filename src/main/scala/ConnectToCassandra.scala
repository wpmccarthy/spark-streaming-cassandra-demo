/**
 * Created by Will on 03/09/2014.
 */

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.StreamingContext._
import org.apache.spark.SparkContext._
import org.apache.spark.streaming.twitter._
import com.datastax.spark.connector.streaming._
import com.github.nscala_time.time.Imports._

object ConnectToCassandra {

  def main (args: Array[String]) {

    // Setup Twitter access
    TwitterCredentials.setCredentials()

    val conf = new SparkConf(true).set("spark.cassandra.connection.host", "localhost")
      .setJars(Array("target/scala-2.10/spark-streaming-cassandra-demo-assembly-1.0.jar"))
      .set("spark.cleaner.ttl", "3600")
      .setAppName("SparkTwitterApp")

    val sc = new SparkContext("spark://127.0.0.1:7077", "Cassandra Connector Test", conf)
    val ssc = new StreamingContext(sc, Seconds(5))

    val stream = TwitterUtils.createStream(ssc, None)

    val hashTags = stream.flatMap(status => status.getText.split(" ").filter(_.startsWith("#")))

    val topCounts = hashTags.map((_, 1)).reduceByKeyAndWindow(_ + _, Seconds(5))
      .map { case (topic, count) => (count, topic)}
      .transform(_.sortByKey(false))
      .map { case (count, topic) => (DateTime.now.minute(0).second(0).millis(0), topic, count)}

    topCounts.saveToCassandra("twitter", "hashtags_by_hour", Seq("hour_start", "hashtag", "count"))

    ssc.start()
    ssc.awaitTermination()
  }
}
