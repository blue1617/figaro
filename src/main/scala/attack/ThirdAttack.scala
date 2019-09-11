package attack

import attack.AverageProgram.{Age, AverageAge, Name}
import com.cra.figaro.algorithm.sampling.Importance
import com.cra.figaro.language.{Constant, Element, Select}
import com.cra.figaro.library.collection.{FixedSizeArray, FixedSizeArrayElement}

/**
  * Created by apreda on 27.02.2019.
  */
object ThirdAttack {

  def getAttackElement(): Element[Age] = {
    val priorThirdAttackerArray: FixedSizeArray[(Name, Age)] = new FixedSizeArray[(Name, Age)](2, i =>
      generateThirdAttacker())
    val priorThirdAttacker: FixedSizeArrayElement[(Name, Age)] = new FixedSizeArrayElement(Constant
    (priorThirdAttackerArray))

    // This is what we know about average age before any observation
    val average_age: Element[AverageAge] = AverageProgram.alpha_p(priorThirdAttacker)
    // The attacker knows that Alice should be in the list
    val seenAlice: Element[Boolean] = AverageProgram.isNameInArrayElement(priorThirdAttacker, "Alice")
    seenAlice.observe(true)

    average_age.addConstraint(a => AverageProgram.averageAgeConstraint(a == 16 || a == 17))

    val ageOfAliceElement: Element[Age] = AverageProgram.retrieveAge(priorThirdAttacker, "Alice")
    ageOfAliceElement
  }

  def runAttack(): Double = {
    //third attacker
    val priorThirdAttackerArray: FixedSizeArray[(Name, Age)] = new FixedSizeArray[(Name, Age)](2, i =>
      generateThirdAttacker())
    val priorThirdAttacker: FixedSizeArrayElement[(Name, Age)] = new FixedSizeArrayElement(Constant
    (priorThirdAttackerArray))

    // This is what we know about average age before any observation
    val average_age: Element[AverageAge] = AverageProgram.alpha_p(priorThirdAttacker)
    // The attacker knows that Alice should be in the list
    val seenAlice: Element[Boolean] = AverageProgram.isNameInArrayElement(priorThirdAttacker, "Alice")
    seenAlice.observe(true)

    average_age.addConstraint(a => AverageProgram.averageAgeConstraint(a == 16 || a == 17))

    val ageOfAliceElement: Element[Age] = AverageProgram.retrieveAge(priorThirdAttacker, "Alice")

    val attack2: Double = Importance.probability(ageOfAliceElement, (a: Double) => a < 18)
    attack2
  }

  def generateThirdAttacker(): Element[(Name, Age)] = {
    val element: Element[(Name, Age)] = Select(0.5 -> ("Alice", 17.toDouble),
      0.5 -> ("John", 16.toDouble))
    element
  }
}
