package attack

import attack.AverageProgram.{Age, AverageAge, Name}
import com.cra.figaro.algorithm.sampling.Importance
import com.cra.figaro.language.{Constant, Element}
import com.cra.figaro.library.atomic.continuous.Normal
import com.cra.figaro.library.atomic.discrete.{Binomial, Uniform}
import com.cra.figaro.library.collection.{FixedSizeArrayElement, VariableSizeArray}

/**
  * Created by apreda on 28.02.2019.
  */
object SlideAttack {


  def averageAgeConstraint(a: AverageAge): Double = {
    if ((a >= 20.25) && (a < 23.00)) 1000.0 else 0.0
  }

  def slideAttack1(priorKnowledge: FixedSizeArrayElement[(Name, Age)]): Element[Age] = {
    for { // Element[A]
      container <- priorKnowledge.element
      idx <- container findIndex {
        _._1 == "Alice"
      } map {
        _ getOrElse -1
      }
      alice <- container get idx
      a = alice map (_._2)
    } yield a getOrElse -1
  }


  def generateSlideAttacker(i: AverageAge, dict: Seq[String]): Element[(Name, Age)] = {
    for {name <- Uniform(dict: _*)
         a <- Constant(16)} yield (name, a)
  }

  def main(args: Array[String]): Unit = {
    val dict: Seq[Name] = List("John", "Tom", "Joe", "Bob", "Alice")
    val prior: FixedSizeArrayElement[(Name, Age)] = VariableSizeArray(
      numItems = Binomial(300, 0.3) map {
        _ + 1
      },
      generator = i => for {n <- Uniform(dict: _*)
                            a <- Normal(42.0, 20.0)} yield (n, a)
    )
    // This is what we know about average age before any observation
    val average_age: Element[AverageAge] = AverageProgram.alpha_p(prior)

    // The attacker knows that Alice should be in the list
    val seenAlice: Element[Boolean] = prior exists { case (s, a) => s == "Alice" }
    seenAlice.observe(true)
    // The attacker has seen that the average age was a bit above 20,
    // but does not know precisely
    average_age.addConstraint(a => averageAgeConstraint(a))

    val ageOfAlice: Element[Age] = slideAttack1(prior)
    // How sure is the attacker that Alice is underage?
    val attack: Double = Importance.probability(ageOfAlice, (a: Double) => a >= 42.0)

    println("slide attack probability " + attack)//this prints out NaN

  }
}
