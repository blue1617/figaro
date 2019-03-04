package attack

import attack.AverageProgram.{Age, AverageAge, Name}
import attack.SecondAttack.{ageAttack1, averageAgeConstraint, generateFirstAttacker}
import com.cra.figaro.algorithm.sampling.Importance
import com.cra.figaro.language._
import com.cra.figaro.library.atomic.continuous.Normal
import com.cra.figaro.library.atomic.discrete.{Binomial, Uniform}
import com.cra.figaro.library.collection.{FixedSizeArray, FixedSizeArrayElement, VariableSizeArray}

object FirstAttack {


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


  def generateFirstAttacker(dict: Seq[String], ages: Seq[Age]): Element[(Name, Age)] = {
    for {name <- Uniform(dict: _*)
         a <- Uniform(ages: _*)} yield (name, a)
  }

  //scala test if I call on this on line 99 is 0, under 42 should 1 should
  // include model, inference, constant attacker, age of Alice, everything
  // that is in main , add more composition as tests

  //a third attacker model


  def main(args: Array[String]): Unit = {
    val dict: Seq[Name] = List("Tom")
    val ages: Seq[Age] = List(16)
    //first attacker
    val priorFirstAttackerArray: FixedSizeArray[(Name, Age)] = new FixedSizeArray[(Name, Age)](2, i =>
      generateFirstAttacker(dict, ages))
    val priorFirstAttacker: FixedSizeArrayElement[(Name, Age)] = new FixedSizeArrayElement(Constant(priorFirstAttackerArray))

    // This is what we know about average age before any observation
    val average_age: Element[AverageAge] = AverageProgram.alpha_p(priorFirstAttacker)
    // The attacker knows that Tom should be in the list
    val seenTom = priorFirstAttacker exists { case (s, a) => s == "Tom" }
    seenTom.observe(true)

    average_age.addConstraint(a => averageAgeConstraint(a))

    val ageOfTomElement: Element[Age] = ageAttack1(priorFirstAttacker)

    // How sure is the attacker that Tom is underage?
    val attack: Double = Importance.probability(ageOfTomElement, (a: Double) => a == 16)//this prints 1.0
    println("attack 1 probability " + attack)


  }
}


