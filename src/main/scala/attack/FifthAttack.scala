package attack

import attack.AverageProgram.{Age, AverageAge, Name}
import com.cra.figaro.algorithm.sampling.Importance
import com.cra.figaro.language.{Constant, Element}
import com.cra.figaro.library.atomic.discrete.Uniform
import com.cra.figaro.library.collection.{FixedSizeArray, FixedSizeArrayElement}
import vegas.DSL.Vegas
import vegas.spec.Spec.MarkEnums.Line
import vegas.spec.Spec.TypeEnums.{Nominal, Quantitative}

/**
  * Created by apreda on 13.08.2019.
  */
object FifthAttack {


  def generateFifthAttacker(dictName: Seq[Name], dictAge:Seq[Age]): Element[(Name, Age)] = {
    for {name <- Uniform(dictName: _*)
         a <- Uniform(dictAge: _*)} yield (name, a)
  }

  def getAttackElement(): Element[Age] = {
    val dictNames: Seq[Name] = List("John", "Alice")
    val dictAges: Seq[Age] = List.tabulate(36)(_ + 15)
    val priorFixedSizeArray: FixedSizeArray[(Name, Age)] = new FixedSizeArray[(Name, Age)](2, i =>
      generateFifthAttacker(dictNames, dictAges))//
    val prior: FixedSizeArrayElement[(Name, Age)] = new FixedSizeArrayElement(Constant
    (priorFixedSizeArray))

    val ageOfAliceElement: Element[Age] = AverageProgram.retrieveAge(prior, "Alice")
    val priorValue: List[Age] = Importance.sampleJointPosterior(ageOfAliceElement).map { case h :: _ => h; case _ =>
      ??? }
      .map { case n: Double => n; case _ => ??? }
      .take(1)
      .toList
    println("prior sample " + priorValue )

    // This is what we know about aver+age age before any observation
    val average_age: Element[AverageAge] = AverageProgram.alpha_p(prior)
    // The attacker knows that Tom should be in the list
    val seenAlice: Element[Boolean] = AverageProgram.isNameInArrayElement(prior, "Alice")
    seenAlice.observe(true)

    average_age.addConstraint(a => AverageProgram.averageAgeConstraint(a >= 19))
    val posteriorValue: List [Age] =  Importance.sampleJointPosterior(ageOfAliceElement).map { case h :: _ => h; case _ =>
      ??? }
      .map { case n: Double => n; case _ => ??? }
      .take(1)
      .toList
    println("posterior sample after adding condition and observing evidence" + posteriorValue)
    val plot = Vegas("Attacker's Chart").
      withData(
        Seq(
          Map("sample value" -> "Prior value", "age" -> priorValue(0)),
          Map("sample value" -> "Posterior value", "age" -> posteriorValue(0)
          )
          //todo: add a graph with attackers that
          //todo: use a different universe for each attacker, call a constructor and use the universe for each
          // element creation
        )
      ).
      encodeX("sample value", Nominal).
      encodeY("age", Quantitative).
      mark(Line)

    plot.show//todo: the window disappears, probably because this is written in a test
    ageOfAliceElement

    //    val attack1Belief: Double = BeliefPropagation.probability(ageOfAliceElement, (a: Double) => a == 15)
    //    assert(attack1Belief < 0.5)//we have a uniform distribution on age ranging from 15 to 50
    //    println("attack1Belief " + attack1Belief)
  }

  def runAttack(): Double = {
    val ageOfAliceElement: Element[Age] = getAttackElement()
    val attack2: Double = Importance.probability(ageOfAliceElement, (a: Double) => a < 18) //this prints 0.06813750641354209
    attack2
  }
}
