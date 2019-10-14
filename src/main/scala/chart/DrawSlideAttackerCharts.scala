package chart

import attack._
import vegas.DSL.Vegas
import vegas.spec.Spec.MarkEnums.Bar
import vegas.spec.Spec.TypeEnums.{Nominal, Quantitative}

/**
  * This plots variations of the slide attackers/ Andrzej's attacker by changing the standard deviation on the OX AND on
  * OY HAVE THE
  * // PROBABILITY of the attack
  */
object DrawSlideAttackerCharts {


  val attackerQuery: Double => Boolean = a => a < 18

  def main(args: Array[String]): Unit = {

    val attackerList: IndexedSeq[SlideAttacker] = for (i <- 1 to 10) yield new SlideAttacker(i)

    val plot = Vegas("Attacker's Chart").
      withData(
        Seq(
          Map("attacker" -> "standard deviation 1", "probability" -> attackerList(0).getAttackProbability(attackerQuery)),
          Map("attacker" -> "standard deviation 2", "probability" -> attackerList(1).getAttackProbability(attackerQuery)),
          Map("attacker" -> "standard deviation 3", "probability" -> attackerList(2).getAttackProbability(attackerQuery)),
          Map("attacker" -> "standard deviation 4", "probability" -> attackerList(3).getAttackProbability(attackerQuery)),
          Map("attacker" -> "standard deviation 5", "probability" -> attackerList(4).getAttackProbability(attackerQuery)),
          Map("attacker" -> "standard deviation 5", "probability" -> attackerList(5).getAttackProbability(attackerQuery)),
          Map("attacker" -> "standard deviation 6", "probability" -> attackerList(6).getAttackProbability(attackerQuery)),
          Map("attacker" -> "standard deviation 7", "probability" -> attackerList(7).getAttackProbability(attackerQuery)),
          Map("attacker" -> "standard deviation 8", "probability" -> attackerList(8).getAttackProbability(attackerQuery)),
          Map("attacker" -> "standard deviation 9", "probability" -> attackerList(9).getAttackProbability(attackerQuery))
        )
      ).
      encodeX("attacker", Nominal).
      encodeY("probability", Quantitative).
      mark(Bar)

    plot.show
  }
}


