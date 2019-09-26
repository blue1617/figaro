package attack

import attack.AverageProgram.{Age, AverageAge, Name}
import com.cra.figaro.algorithm.sampling.Importance
import com.cra.figaro.language.{Constant, Element}
import com.cra.figaro.library.atomic.discrete.Uniform
import com.cra.figaro.library.collection.{FixedSizeArray, FixedSizeArrayElement}

/**
  * Created by apreda on 27.02.2019.
  */
object SecondAttack {

  def getAttackElement: Element[Age] = {
    val dict: Seq[Name] = List("John", "Alice")
    val ages: Seq[Age] = List(17, 16)
    val priorSecondAttackerArray: FixedSizeArray[(Name, Age)] = new FixedSizeArray[(Name, Age)](2, i =>
      generateSecondAttacker(dict, ages))
    val priorSecondAttacker: FixedSizeArrayElement[(Name, Age)] = new FixedSizeArrayElement(Constant(priorSecondAttackerArray))

    // This is what we know about average age before any observation
    val average_age: Element[AverageAge] = AverageProgram.alpha_p(priorSecondAttacker)
    // The attacker knows that Alice isin the list
    val seenAlice: Element[Boolean] = AverageProgram.isNameInArrayElement(priorSecondAttacker, "Alice")
    seenAlice.observe(true)

    average_age.addConstraint(a => AverageProgram.averageAgeConstraint(a == 16 || a == 17))

    val ageOfAliceElement: Element[Age] = AverageProgram.retrieveAge(priorSecondAttacker, "Alice")
    ageOfAliceElement
  }

  def runAttack(): Double = {
    val ageOfAliceElement: Element[Age] = getAttackElement
    val attack2: Double = Importance.probability(ageOfAliceElement, (a: Double) => a < 18)
    attack2
  }

  def generateSecondAttacker(dict: Seq[String], ages: Seq[Age]): Element[(Name, Age)] = {
    for {name <- Uniform(dict: _*)
         a <- Uniform(ages: _*)} yield (name, a)
  }
}
