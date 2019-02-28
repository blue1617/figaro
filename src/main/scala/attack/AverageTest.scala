package attack

import com.cra.figaro.algorithm.sampling.Importance
import com.cra.figaro.language._
import com.cra.figaro.library.atomic.continuous.Normal
import com.cra.figaro.library.atomic.discrete.{Binomial, Uniform}
import com.cra.figaro.library.collection.{FixedSizeArrayElement, VariableSizeArray}

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
    if (a == 16)
      1000.0
    else
      0.0
  }


  def ageAttack1(prior: FixedSizeArrayElement[(Name, Age)]):
  Element[Option[Age]]
  = {

     for { // Element[A]
      container <- prior.element
      idx <- container findIndex {
        _._1 == "Alice"
      } map {
        _ getOrElse -1
      }
      alice <- container get idx
      aliceAgeI = alice map (_._2)
    } yield aliceAgeI
  }


  //scala test if I call on this on line 99 is 0, under 42 should 1 should
  // include model, inference, constant attacker, age of Alice, everythign
  // that is in main , add more composition as tests

  //a third attacker model


  def main(args: Array[String]): Unit = {


    val dict: Seq[String] = List("John", "Jho", "Joe", "Jim", "Bob", "Alice")



//    average_age.addConstraint(a => averageAgeConstraint(a))

//    val ageOfAlice: Element[Option[Age]] = ageAttack1(priorFirstAttacker)

    print("aaaa")
    // How sure is the attacker that Alice is underage?
//    val attack: Double = Importance.probability(ageOfAlice, (a: Double) => a >=
//      42.0)
//    println("attack 1 probability" + attack)
  }



  /*
    alpha ( John, 17 :: Tom,15)
    returns 16
    an attacker that knows the input list.
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


