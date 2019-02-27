package hello


import com.cra.figaro.algorithm.decision.index.Index
import com.cra.figaro.algorithm.sampling.Importance
import com.cra.figaro.language._
import com.cra.figaro.library.atomic.continuous.Normal
import com.cra.figaro.library.atomic.discrete.{Binomial, Uniform}
import com.cra.figaro.library.collection.{Container, FixedSizeArrayElement, VariableSizeArray}

object AverageTest {

  type Name = String
  type Age = Double
  type AverageAge = Double

  /**
    * // The attacker has seen that the average age was a bit above 20,
    * // but does not know precisely. Rather than specifying hard evidence that
    * // average_age is a certain value, 22, for example, one can specify soft
    * // evidence that
    * // average_age is most likely to be between 20.25 and 23. The age range
    * // of 20.25 to 23 is 1000 times more likely than any other age.
    * //Constraint can be seen as either a soft evidence, or a way of providing
    * // additional connections between elements in a model. A constraint is
    * // added when one
    * // has
    * // some evidence
    * // about an
    * // element, but is not quite sure about it.The probability of the average
    * a ge to be in the given range is 1000 more likely than outside it?
    *
    * @param a average age
    * @return the average age with the intented constraint
    */
  def averageAgeConstraint(a: AverageAge): Double = {
    if ((a >= 20.25) && (a < 23.00))
      1000.0
    else
      0.0
  }


  def ageAttack1(prior: FixedSizeArrayElement[(Name, Age)]): Element[Age] = {

    //    val container: Element[Container[Int, (Name, Age)]] = prior.element

//     for { // Element[A]
//      container <- prior.element
//      idx <- container findIndex {
//        _._1 == "Alice"
//      } map {
//        _ getOrElse -1
//      }
//      alice <- container get idx
//      aliceAgeI = alice map (_._2)
//    } yield aliceAgeI getOrElse -1
  Constant(20)
  }


  def generate(i: AverageAge, dict: Seq[String]): Element[(Name, Age)] = {
    for {name <- Uniform(dict: _*)
         a <- Normal(42.0, 20.0)} yield (name, a)
  }

  def generateFirstAttacker(i: AverageAge, dict: Seq[String]): Element[(Name, Age)] = {
    for {name <- Uniform(dict: _*)
         a <- Constant(16)} yield (name, a)
  }


  def main(args: Array[String]): Unit = {
    val dict: Seq[String] = List("John", "Jho", "Joe", "Jim", "Bob", "Alice")


    val prior: FixedSizeArrayElement[(Name, Age)] = VariableSizeArray(
      numItems = Binomial(300, 0.3) map {
        _ + 1
      },
      generator = i =>
        generate(i, dict)
    )

    //first attacker
    val priorFirstAttacker: FixedSizeArrayElement[(Name, Age)] =
      VariableSizeArray(numItems = Constant(1), generator = i =>
        generateFirstAttacker(i, dict))

    // This is what we know about average age before any observation
    val average_age: Element[AverageAge] = alpha_p(priorFirstAttacker)
    // The attacker knows that Alice should be in the list
    val seenAlice = priorFirstAttacker exists { case (s, a) => s == "Alice" }
    seenAlice.observe(true)

    average_age.addConstraint(a => averageAgeConstraint(a))

    val ageOfAlice: Element[Age] = ageAttack1(prior)

    // How sure is the attacker that Alice is underage?
    val attack: Double = Importance.probability(ageOfAlice, (a: Double) => a >=
      42.0)
    println("attack 1 probability" + attack)
  }

  def alpha_p(records: FixedSizeArrayElement[(Name, Age)]):
  Element[Age] = {
    records
      .map { case (n, a) => (a, 1) }
      .reduce { case (x, y) => (x._1 + y._1, x._2 + y._2) }
      .map { case (sum, count) => sum / count }
  }

  /*
    alpha ( John, 17 :: Tom,15)
    returns 16
    an attacker that knows the linput list.
    a1 = Constant ( List((John,17),(Tom,15) )  )
    alpha_p (a1)
    returns Constant (16)

    a2 = Select (  List((John,17),(Tom,15) -> 0.5; List((John,27),(Tom,25 ) -> 0.5 ) )
    alpa_p (a2)
    alpha_p (a2).observe (16)
    a2.probability ( l => l.size == 2)
    a2.probability (l => l.exists ( x => x == (John,15))
  */
}


