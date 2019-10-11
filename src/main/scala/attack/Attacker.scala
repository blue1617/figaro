package attack

import attack.AverageProgram.{Age, AverageAge, Name}
import com.cra.figaro.algorithm.sampling.Importance
import com.cra.figaro.language.{Constant, Element}
import com.cra.figaro.library.collection.{FixedSizeArray, FixedSizeArrayElement}

abstract class Attacker {

  val names: Seq[Name]
  val ages: Seq[Age]
  val populationSize: Int
  val averageConstraint: Double => Double

  def getPrior: FixedSizeArrayElement[(Name, Age)] = {
    val priorArray: FixedSizeArray[(Name, Age)] = new FixedSizeArray[(Name, Age)](populationSize, i =>
      generateAttacker(i))
    val prior: FixedSizeArrayElement[(Name, Age)] = new FixedSizeArrayElement(Constant(priorArray))
    prior
  }



  //todo:
  def getAttackElement: Element[Age] = {
    val prior: FixedSizeArrayElement[(Name, Age)] = getPrior
    val average_age: Element[AverageAge] = AverageProgram.alpha_p(prior)// This is what we know about average age before any observation
    // The attacker knows that Alice should be in the list
    val seenAlice: Element[Boolean] = AverageProgram.isNameInArrayElement(prior, "Alice")
    seenAlice.observe(true)

    //todo: attackers two and three had a==16 || a==17
    average_age.addConstraint(averageConstraint)

    val ageOfAliceElement: Element[Age] = AverageProgram.retrieveAge(prior, "Alice")
    ageOfAliceElement
    //    val attack1Belief: Double = BeliefPropagation.probability(ageOfAliceElement, (a: Double) => a == 15)
    //    assert(attack1Belief < 0.5)//we have a uniform distribution on age ranging from 15 to 50
    //    println("attack1Belief " + attack1Belief)
  }


  def getAttackProbability(predicate: Double => Boolean): Double = {
    val ageOfAliceElement: Element[Age] = getAttackElement
    val attack2: Double = Importance.probability(ageOfAliceElement, predicate)
    attack2
  }

  def generateAttacker(i: Int): Element[(Name, Age)]

}
