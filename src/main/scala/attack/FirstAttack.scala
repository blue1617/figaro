package attack

import attack.AverageTest._
import com.cra.figaro.algorithm.sampling.Importance
import com.cra.figaro.language.{Constant, Element}
import com.cra.figaro.library.atomic.discrete.Uniform
import com.cra.figaro.library.collection.{FixedSizeArrayElement, VariableSizeArray}

/**
  * Created by apreda on 27.02.2019.
  */
class FirstAttack {


  def generateFirstAttacker(i: AverageAge, dict: Seq[String]): Element[(Name, Age)] = {
    for {name <- Uniform(dict: _*)
         a <- Constant(16)} yield (name, a)
  }

  def main(args: Array[String]): Unit = {

    val dict: Seq[String] = List("John", "Jho", "Joe", "Jim", "Bob", "Alice")

    //first attacker
    val priorFirstAttacker: FixedSizeArrayElement[(Name, Age)] =
      VariableSizeArray(numItems = Constant(1), generator = i =>
        generateFirstAttacker(i, dict))


  // This is what we know about average age before any observation
  val average_age: Element[AverageAge] = AverageProgram.alpha_p(priorFirstAttacker)
  // The attacker knows that Alice should be in the list
  val seenAlice = priorFirstAttacker exists { case (s, a) => s == "Alice" }
  seenAlice.observe(true)

    //    average_age.addConstraint(a => averageAgeConstraint(a))

//    val ageOfAlice: Element[Age] = ageAttack1(priorFirstAttacker)

    // How sure is the attacker that Alice is underage?
//    val attack: Double = Importance.probability(ageOfAlice, (a: Double) => a >=
//      42.0)
//    println("attack 1 probability" + attack)
  }
}
