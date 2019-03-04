package attack

import attack.AverageProgram.{Age, AverageAge, Name}
import com.cra.figaro.algorithm.sampling.Importance
import com.cra.figaro.language.{Constant, Element}
import com.cra.figaro.library.atomic.discrete.Uniform
import com.cra.figaro.library.collection.{FixedSizeArray, FixedSizeArrayElement}

/**
  * Created by apreda on 27.02.2019.
  */
object ZeroAttack {

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

  def ageAttack1(priorKnowledge: FixedSizeArrayElement[(Name, Age)]): Element[Age] = {
    for { // Element[A]
      container <- priorKnowledge.element
      idx <- container findIndex {
        _._1 == "Tom"
      } map {
        _ getOrElse -1
      }
      tom <- container get idx
      age = tom map (_._2)
    } yield age getOrElse -1
  }


  def generateZeroAttacker(): Element[(Name, Age)] = {
    for {name <-Constant("Tom")
         a <-Constant(16)} yield (name, a)
  }


  def main(args: Array[String]): Unit = {

    //zero attacker
    val priorZeroAttackerArray: FixedSizeArray[(Name, Age)] = new FixedSizeArray[(Name, Age)](2, i =>
      generateZeroAttacker())
    val priorZeroAttacker: FixedSizeArrayElement[(Name, Age)] = new FixedSizeArrayElement(Constant(priorZeroAttackerArray))

    // This is what we know about average age before any observation
    val average_age: Element[AverageAge] = AverageProgram.alpha_p(priorZeroAttacker)
    // The attacker knows that Tom should be in the list
    val seenTom = priorZeroAttacker exists { case (s, a) => s == "Tom" }
    seenTom.observe(true)

    average_age.addConstraint(a => averageAgeConstraint(a))

    val ageOfTomElement: Element[Age] = ageAttack1(priorZeroAttacker)

    // How sure is the attacker that Tom is underage?
    val attack: Double = Importance.probability(ageOfTomElement, (a: Double) => a == 16)//this prints 1.0
    println("attack 0 probability " + attack)

  }

}