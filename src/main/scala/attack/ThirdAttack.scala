package attack

import attack.AverageProgram.{Age, AverageAge, Name}
import com.cra.figaro.algorithm.sampling.Importance
import com.cra.figaro.language.{Constant, Element, Select}
import com.cra.figaro.library.atomic.discrete.Uniform
import com.cra.figaro.library.collection.{FixedSizeArray, FixedSizeArrayElement}

/**
  * Created by apreda on 27.02.2019.
  */
object ThirdAttack {

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
    if (a == 16 || a == 17)
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


  def generateThirdAttacker(): Element[(Name, Age)] = {
    val element: Element[(Name, Age)] = Select(0.5 -> ("Tom", 17.toDouble),
      0.5 -> ("Tom", 16.toDouble))
    element
  }


  def main(args: Array[String]): Unit = {

    //thir attacker
    val priorThirdAttackerArray: FixedSizeArray[(Name, Age)] = new FixedSizeArray[(Name, Age)](2, i =>
      generateThirdAttacker())
    val priorThirdAttacker: FixedSizeArrayElement[(Name, Age)] = new FixedSizeArrayElement(Constant
    (priorThirdAttackerArray))

    // This is what we know about average age before any observation
    val average_age: Element[AverageAge] = AverageProgram.alpha_p(priorThirdAttacker)
    // The attacker knows that Tom should be in the list
    val seenTom = priorThirdAttacker exists { case (s, a) => s == "Tom" }
    seenTom.observe(true)

    average_age.addConstraint(a => averageAgeConstraint(a))

    val ageOfTomElement: Element[Age] = ageAttack1(priorThirdAttacker)

    // How sure is the attacker that Tom is 16?
    val attack1: Double = Importance.probability(ageOfTomElement, (a: Double) => a == 16) //this prints 0.51105305466237
    println("attack 1 probability " + attack1)

    val attack2: Double = Importance.probability(average_age, (a: Double) => a == 16) //this prints 0.49880430450377383

    println("attack 2 probability " + attack2)

  }

}
