package attack

import attack.AverageProgram.{Age, AverageAge, Name}
import attack.SecondAttack._
import com.cra.figaro.algorithm.sampling.Importance
import com.cra.figaro.language.{Constant, Element}
import com.cra.figaro.library.atomic.discrete.Uniform
import com.cra.figaro.library.collection.{FixedSizeArray, FixedSizeArrayElement, VariableSizeArray}

/**
  * Created by apreda on 27.02.2019.
  */
object FirstAttack {

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

  def ageAttack1(priorKnowledge: FixedSizeArrayElement[(Name, Age)]): Element[Option[Age]] = {
    for { // Element[A]
      container <- priorKnowledge.element
      idx <- container findIndex {
        _._1 == "Tom"
      } map {
        _ getOrElse -1
      }
      alice <- container get idx
      aliceAgeOption = alice map (_._2)
    } yield aliceAgeOption
  }


  def generateFirstAttacker(dict: Seq[String], ages: Seq[Age]): Element[(Name, Age)] = {
    for {name <- Uniform(dict: _*)
         a <- Uniform(ages: _*)} yield (name, a)
  }

  def getAgeOfAliceElement(ageOfAliceOption: Element[Option[Age]]): Element[Age] = {
    ageOfAliceOption.value match {
      case None => Constant(-1)
      case null => Constant(-1)
      case _ => Constant(ageOfAliceOption.value.get)
    }
  }

  def main(args: Array[String]): Unit = {

    val dict: Seq[Name] = List("John", "Tom")
    val ages: Seq[Age] = List(16, 17)
    //first attacker
    val priorFirstAttackerArray: FixedSizeArray[(Name, Age)] = new FixedSizeArray[(Name, Age)](2, i =>
      generateFirstAttacker(dict, ages))
    val priorFirstAttacker: FixedSizeArrayElement[(Name, Age)] = new FixedSizeArrayElement(Constant(priorFirstAttackerArray))

    // This is what we know about average age before any observation
    val average_age: Element[AverageAge] = AverageProgram.alpha_p(priorFirstAttacker)
    // The attacker knows that Alice should be in the list
    val seenAlice = priorFirstAttacker exists { case (s, a) => s == "Alice" }
    seenAlice.observe(true)

    average_age.addConstraint(a => averageAgeConstraint(a))

    val ageOfAliceOption: Element[Option[Age]] = ageAttack1(priorFirstAttacker)
    val ageOfAliceElement:Element[Age] = getAgeOfAliceElement(ageOfAliceOption)

    print("aaaa")
    // How sure is the attacker that Alice is underage?
    val attack: Double = Importance.probability(ageOfAliceElement, (a: Double) => a >= 42.0)
    println("attack 1 probability" + attack)

  }

}
