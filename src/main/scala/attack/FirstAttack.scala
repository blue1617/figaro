package attack

import attack.AverageProgram.{Age, AverageAge, Name}
import com.cra.figaro.algorithm.sampling.Importance
import com.cra.figaro.language._
import com.cra.figaro.library.atomic.discrete.Uniform
import com.cra.figaro.library.collection.{FixedSizeArray, FixedSizeArrayElement}

object FirstAttack {

  def getAttackElement: Element[Age] = {
    val names: Seq[Name] = List("Alice")
    val ages: Seq[Age] = List(16)
    val priorFirstAttackerArray: FixedSizeArray[(Name, Age)] = new FixedSizeArray[(Name, Age)](2, i =>
      generateFirstAttacker(names, ages))
    val priorFirstAttacker: FixedSizeArrayElement[(Name, Age)] = new FixedSizeArrayElement(Constant(priorFirstAttackerArray))

    // This is what we know about average age before any observation
    val average_age: Element[AverageAge] = AverageProgram.alpha_p(priorFirstAttacker)
    // The attacker knows that Alice should be in the list
    val seenAlice: Element[Boolean] = AverageProgram.isNameInArrayElement(priorFirstAttacker, "Alice")
    seenAlice.observe(true)

    average_age.addConstraint(a => AverageProgram.averageAgeConstraint(a == 16))

    val ageOfAliceElement: Element[Age] = AverageProgram.retrieveAge(priorFirstAttacker, "Alice")//todo:plot a sample
    // of this element; ask Figaro for a sample?
    //todo: an element before and after the observation (prior and posterior) visualize it as a line;e I can use it
    // to explain how the probability distribution captures knowledge
    ageOfAliceElement
  }
  def runAttack(): Double = {
    // How sure is the attacker that "Alice" is underage?
    val ageOfAliceElement: Element[Age] = getAttackElement
    val attack: Double = Importance.probability(ageOfAliceElement, (a: Double) => a == 16) //this prints 1.0
    attack
  }

  def generateFirstAttacker(dict: Seq[String], ages: Seq[Age]): Element[(Name, Age)] = {
    for {name <- Uniform(dict: _*)
         a <- Uniform(ages: _*)} yield (name, a)
  }
}


