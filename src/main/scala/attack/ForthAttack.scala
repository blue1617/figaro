package attack

import attack.AverageProgram.{Age, AverageAge, Name}
import com.cra.figaro.algorithm.sampling.Importance
import com.cra.figaro.language.{Constant, Element}
import com.cra.figaro.library.atomic.discrete.Uniform
import com.cra.figaro.library.collection.{FixedSizeArray, FixedSizeArrayElement}

/**
  * Created by apreda on 06.03.2019.
  */
object ForthAttack {

  def getAttackElement(): Element[Age] = {
    val dict: Seq[Name] = List("John", "Alice")
    //forth attacker
    val priorForthAttackerArray: FixedSizeArray[(Name, Age)] = new FixedSizeArray[(Name, Age)](2, i =>
      generateForthAttacker(dict))
    val priorThirdAttacker: FixedSizeArrayElement[(Name, Age)] = new FixedSizeArrayElement(Constant
    (priorForthAttackerArray))

    // This is what we know about average age before any observation
    val average_age: Element[AverageAge] = AverageProgram.alpha_p(priorThirdAttacker)
    // The attacker knows that Tom should be in the list
    val seenAlice: Element[Boolean] = AverageProgram.isNameInArrayElement(priorThirdAttacker, "Alice")
    seenAlice.observe(true)

    average_age.addConstraint(a => AverageProgram.averageAgeConstraint(a >= 15 && a <= 18))

    val ageOfAliceElement: Element[Age] = AverageProgram.retrieveAge(priorThirdAttacker, "Alice")
    ageOfAliceElement
  }

  def runAttack(): Double = {

    val ageOfAliceElement: Element[Age] = getAttackElement()

    val attack2: Double = Importance.probability(ageOfAliceElement, (a: Double) => a < 18)
    attack2
  }

  def generateForthAttacker(dict: Seq[Name]): Element[(Name, Age)] = {
    for {name <- Uniform(dict: _*)
         a <- Uniform(15, 50)} yield (name, a) //todo: try Other built-in continuous atomic classes include Normal, Exponent-
    //ial, Gamma, Beta , and Dirichlet
  }
}
