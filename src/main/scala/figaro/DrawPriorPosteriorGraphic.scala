package figaro

import attack.AverageProgram.{Age, AverageAge, Name}
import attack.{Attacker, AverageProgram, SlideAttacker}
import com.cra.figaro.algorithm.sampling.Importance
import com.cra.figaro.language.Element
import com.cra.figaro.library.collection.FixedSizeArrayElement
import vegas.DSL.Vegas
import vegas.spec.Spec.AggregateOpEnums
import vegas.spec.Spec.MarkEnums.Line
import vegas.spec.Spec.TypeEnums.{Nominal, Quantitative}

object DrawPriorPosteriorGraphic {

  /*
  an element before and after the observation (prior and posterior) visualize it as a line;e I can use it
    // to explain how the probability distribution captures knowledge
     This classs drwas a chart with the values for the prior and posterior attacks" and set width and
    // height? they need to be double values
  */

  def plotAttack(): Unit = {
    val attacker: Attacker = new SlideAttacker(2)
    val ageOfAliceElement: Element[Age] = attacker.getAttackElement
    println("Importance.sampleJointPosterior(ageOfAliceElement) " + Importance.sampleJointPosterior(ageOfAliceElement))
    val priorValue: Age = FigaroCommon.getSampleValue(ageOfAliceElement).head
    println("prior sample " + priorValue)

    // This is what we know about average age before any observation
    val prior: FixedSizeArrayElement[(Name, Age)] = attacker.getPrior
    val average_age: Element[AverageAge] = AverageProgram.alpha_p(prior)
    // The attacker knows that Tom should be in the list
    val seenAlice: Element[Boolean] = AverageProgram.isNameInArrayElement(prior, "Alice")
    seenAlice.observe(true)

    average_age.addConstraint(a => if (a >= 16 && a < 23.00) 1.0 else 0.001)
    val posteriorValue: List[Age] = FigaroCommon.getSampleValue(ageOfAliceElement)
    println("posterior sample after adding condition and observing evidence" + posteriorValue)
    val plot = Vegas("Attacker's Chart").
      withData(
        Seq(
          Map("sample value" -> "Prior value", "age" -> priorValue),
          Map("sample value" -> "Posterior value", "age" -> posteriorValue.head
          )
        )
      ).
      encodeSize("sample value", Quantitative, aggregate = AggregateOpEnums.Sum).
      encodeX("sample value", Nominal).
      encodeY("age", Quantitative).
      mark(Line)
    plot.show
  }

  def main(args: Array[String]): Unit = {
    plotAttack()
  }
}
