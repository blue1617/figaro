package attack

import com.cra.figaro.algorithm.sampling._
import com.cra.figaro.language._
import com.cra.figaro.library.atomic._
import com.cra.figaro.library.collection.{FixedSizeArrayElement, VariableSizeArray}

// Plotting
//import com.quantifind.charts.Highcharts._

// Vegas  // NOTE: Not working JavaFX
// import vegas._
// import vegas.render.WindowRenderer._


//   NOTE: Original alpha
//   def alpha (records: List[(Name,Age)]) :Age =
//     records
//       .map { case (n,a) => (a,1) }
//       .reduce[(Age,Int)]
//         { case (x,y) => (x._1+y._1, x._2 + y._2) }
//       .map { case (sum,count) => (sum / count) }
// }


object AverageAgeRaul {

  // Define alpha
  def alpha (records: Array[(String, Double)]) :Double =
    records
      .map { case (n,a) => (a)}
      .reduce { (x, y) => x + y} / records.length

  def alpha_p(records: FixedSizeArrayElement[(String, Double)]):
  Element[Double] = {
    val averageAge: Element[Double] = records
      .map { case (n, a) => (a, 1) }
      .reduce { case (x, y) => (x._1 + y._1, x._2 + y._2) }
      .map { case (sum, count) => sum / count }
    averageAge
  }


  def prior(size: Element[Int]): FixedSizeArrayElement[(String, Double)] = {

    val dict: Seq[String] = List("John", "Jho", "Joe", "Joo", "Hoj", "Haj",
      "Jah", "Jih", "Jee", "Chi", "Khi", "Kha", "Khe", "Kho", "Khu", "Anz",
      "Inz", "Enz", "Unz", "Ulu", "Ulo", "Ula", "Ule", "Uli", "Olo", "Ole",
      "Olu", "Oli", "Lli", "Lle", "Lla", "Llo", "Llu", "Lly", "Pia", "Pio",
      "Pie", "Pii", "Piu", "Piy", "Per", "Pit", "Pat", "Por", "Par", "Pur",
      "Pir", "Pyr", "Bob")

    // val size: Element[Int] = for { n <- Binomial (N,P) } yield n+1 // Now a parameter (see main())
    // val age: Element[Double] = continuous.Normal(23.2, 10) // For now we use a new distribution per row

    VariableSizeArray(size, i =>
      for {
        n <- if (i==0) Constant ("Alice") else discrete.Uniform (dict: _*)
        a <- if (i==0) continuous.Normal(40.2, 10) else continuous.Normal(23.2, 10) } // What if change to `age`
        yield  (n, a)
      // yield  n -> a //NOTE: Syntactic sugar for the line above
    )
  }

  def main(args: Array[String]) = {
    // Attacker's belief before running the program
    val original = continuous.Normal(40.2, 10) // NOTE: Should be the same as in case i==0 in line 60

    // Uniform distribution for the size of the database, i.e., the
    // attacker considers that the database can be of size from 1 to
    // 100
    val sizeDatabase     = discrete.Uniform((1 to 100):_*) // NOTE: Perhaps should not be a parameter of the model
    val prior            = AverageAgeRaul.prior(sizeDatabase)
    val average          = AverageAgeRaul.alpha_p(prior)

    // Attacker's knowledge after observation
    // Constraint: The averages falling within interval below are 1000x
    // more likely than other averages
    average.setConstraint ((x) => if ((x >= 22.2) && (x < 55.5)) 1.0 else 0.001)
    // average.addCondition((x) => 22 < x && x < 24) // NOTE: If absurd the programs hangs...
    // REASON: Importance ignores

    // The attacker considers that databases of size 3 to 5 are more
    // likely than the rest
    sizeDatabase.setConstraint(x => if (3 <= x && x <= 5) 1.0 else 0.001)

    // Obtain Alice's age distribution
    val aliceRow = prior(0)
    val aliceAge = aliceRow.map( (x) => x._2)

    //Sampling
    val alg = Importance(20000, original, average, aliceAge)

    // Querying
    alg.start()
    // println("Probability of age of Alice given that attacker knows 23.5 < avg < 24.5: " +
    //   alg.probability(aliceAge, (x: Double) => 23.5 < x && x < 24.5))
    // println("Probability of average being 23.5 < avg < 24.5: " +
    //   alg.probability(average, (x: Double) => 23.5 < x && x < 24.5))
    // println("Probability of age of Alice before observations about the average: " +
    //   alg.probability(original, (x: Double) => 23.5 < x && x < 24.5))

    // Comoparing expected values // NOTE: Seems to be a good metric
    val expBefore: Double = alg.expectation(original, (x: Double) => x)
    val expAfter: Double = alg.expectation(aliceAge, (x: Double) => x)
    println("Expectation of Alice's before observation: "+ expBefore + "\n" + "Variance: " + alg.variance(original))
    println("Expectation of Alice's after observation: "+ expAfter + "\n" + "Variance: " + alg.variance(aliceAge))
    println("Difference: "+ math.abs(expBefore - expAfter))



    // Plotting

    // Synthesising X-axis
    val l1 = 0 to 1000
    val l1prime = l1.map { (i) => i / 10.0}
    // Probabilities Y-axis
    val l2 = l1prime.map{ (i) => (i, alg.probability(aliceAge, (x: Double) => i < x && x < i + 0.1))}
    val lA1 = for {item <- l2} yield item._1
    val lA2 = for {item <- l2} yield item._2

    // Probabilities Y-axis
    val l3 = l1prime.map{ (i) => (i, alg.probability(original, (x: Double) => i < x && x < i + 0.1))}
    val lB1 = for {item <- l3} yield item._1
    val lB2 = for {item <- l3} yield item._2

    // Probabilities Y-axis
    val l4 = l1prime.map{ (i) => (i, alg.probability(average, (x: Double) => i < x && x < i + 0.1))}
    val lC1 = for {item <- l4} yield item._1
    val lC2 = for {item <- l4} yield item._2

    // // Creating plot
    // line(lA1, lA2)
    // hold
    // line(lB1, lB2)
    // hold
    // line(lC1, lC2)
    // //Axis naming
    // xAxis("Alice's age")
    // yAxis("Probability")
    // //Legend
    // legend(List("Posterior", "Prior", "Average"))

    // Not working well, the result is greater than 1 and it doesn't
    // vary too much by chaing the observation...
    println("Hellinger distance: " + discreteHellingerDistance(l2.toList,l3.toList))

    // Clean up
    alg.kill()
  }


  // Attempt to implement Hellinger Distance to compare distributions
  // NOTE: Cheating a bit. We are in a continuous domain, and we use
  // the version for discrete distributions
  def discreteHellingerDistance(l1: List[(Double, Double)], l2: List[(Double, Double)]): Double = {
    val x: List[Double] = for {
      i1 <- l1
      i2 <- l2
    } yield math.pow(math.sqrt(i1._2)-math.sqrt(i2._2), 2) // (sqrt(p_i) - sqrt(q_i))^2
    math.sqrt(x.reduce((x,y) => x+y)) * (1 / math.sqrt(2))

  }
}
