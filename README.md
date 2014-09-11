SPARK STREAMING WITH CASSANDRA USING TWITTER STREAM
===================================================

- This is an example application that uses the Datastax Cassandra Spark Connector.
- It uses twitter stream and should work with DSE or open source cassandra with the connector set up properly.

Prerequisites
-------------

- DSE 4.5.2 (or Open Source Apache Cassandra 2.0 and Apache Spark 0.9.1) running on localhost
- Simple Build Tool (SBT) installed - http://www.scala-sbt.org

How To Use
----------

- Ensure cassandra-connector-spark is set up correctly. It should be automatically in DSE 4.5.1; in open source cassandra the connector and driver jars must be added to the classpath of spark.

- Create the cassandra table

    CREATE KEYSPACE twitter WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};
     
    CREATE TABLE hashtags_by_hour (
        hour_start timestamp,
        hashtag text,
        count counter,
        PRIMARY KEY(hour_start, hashtag)
    );
    
- Insert your twitter credentials in TwitterCredentials. (If you do not have then create a new application on the twitter dev website)

- Assemble the project

    $ sbt assembly run
    
- Run the ConnectToCassandra main method when prompted. A new stream will be opened and data saved to the table you just created. Ensure data is being saved to cassandra with cqlsh.

- Stop the stream with ctrl-c

- Run the project again, this time selecting the CountHashTags main method. This will count the hashtags in your table