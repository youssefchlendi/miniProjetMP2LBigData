import org.apache.spark.sql.SparkSession
import com.johnsnowlabs.nlp.base.{DocumentAssembler, Finisher}
import com.johnsnowlabs.nlp.annotator.{LemmatizerModel, SentenceDetector, SentimentDetector, Tokenizer}
import org.apache.spark.ml.Pipeline
import scala.io.Source

object SparkNLPSentimentAnalysis {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("Spark NLP Sentiment Analysis Scala Application")
      .master("local[*]")
      .getOrCreate()

    import spark.implicits._

    // Initialize Document Assembler
    val documentAssembler = new DocumentAssembler()
      .setInputCol("text")
      .setOutputCol("document")

    // Initialize Sentence Detector
    val sentenceDetector = new SentenceDetector()
      .setInputCols(Array("document"))
      .setOutputCol("sentence")


    // Initialize Tokenizer
    val tokenizer = new Tokenizer()
      .setInputCols(Array("sentence"))
      .setOutputCol("token")

    // Load Lemmatization Dictionary
    val lemmaFile = "C:\\Git\\miniProjetMP2LBigData\\lemmatization-en.txt"
    val fileSource = Source.fromFile(lemmaFile)
    val lemmaMap = fileSource.getLines().map(line => {
      val Array(word, lemma) = line.split("\t")
      (word, lemma)
    }).toMap
    fileSource.close()

    // Initialize Lemmatizer
    val lemmatizer = new LemmatizerModel()
      .setInputCols(Array("token"))
      .setOutputCol("lemma")
      .setLemmaDict(lemmaMap)

    // Initialize Sentiment Detector
    val sentimentDetector = new SentimentDetector()
      .setInputCols(Array("lemma", "sentence"))
      .setOutputCol("sentiment_score")
      .setDictionary("C:\\Git\\miniProjetMP2LBigData\\transformed_dict.txt", readAs = "TEXT", delimiter = ",")
    // Initialize Finisher
    val finisher = new Finisher()
      .setInputCols(Array("sentiment_score"))
      .setOutputCols(Array("sentiment"))

    // Define the pipeline
    val pipeline = new Pipeline()
      .setStages(Array(
        documentAssembler,
        sentenceDetector,
        tokenizer,
        lemmatizer,
        sentimentDetector,
        finisher
      ))

    val testDataset = spark.read.option("header", true).csv("C:\\Git\\miniProjetMP2LBigData\\aclimdb_test.csv")

    //spark.createDataFrame([['']]).toDF("text") // empty dataframe
    val empty_df = Seq(("")).toDF("text")
    // %%time equivalent in Scala
    val pipelineModel = pipeline.fit(empty_df)


    val startTime = System.nanoTime()
    val result = pipeline.fit(testDataset).transform(testDataset)
    val endTime = System.nanoTime()
    println("Time taken to train the pipeline: " + (endTime - startTime) / 1000000000 + " seconds")

    // Show the result after Sentiment Detection
    result.show()


    // Example DataFrame to demonstrate pipeline execution
    //val data = Seq(("I recommend others to avoid because it is too expensive")).toDF("text")

    // Debugging Step: Execute pipeline to inspect sentiment scores
    //val debugResult = pipeline.fit(data).transform(data)

    // Debug: Show the result after Sentiment Detection
    //debugResult.select($"sentiment").show(truncate = false)
  }
}
