package chart

import attack.{FifthAttacker, FirstAttacker, ForthAttacker, SecondAttack, SlideAttacker, ThirdAttacker}
import vegas.DSL.Vegas
import vegas.spec.Spec.MarkEnums.Bar
import vegas.spec.Spec.TypeEnums.{Nominal, Quantitative}

object DrawAttackersChart {


  val attackerQuery: Double => Boolean = a => a < 18

  def main(args: Array[String]): Unit = {


    val plot = Vegas("Attacker's Chart").
      withData(
        Seq(
          Map("attacker" -> "1st attacker", "probability" -> new FirstAttacker()
            .getAttackProbability(attackerQuery)),
          Map("attacker" -> "2nd attacker", "probability" -> new SecondAttack()
            .getAttackProbability(attackerQuery)),
          Map("attacker" -> "3rd attacker", "probability" -> new ThirdAttacker()
            .getAttackProbability(attackerQuery)),
          Map("attacker" -> "4th attacker", "probability" -> new ForthAttacker()
            .getAttackProbability(attackerQuery)),
          Map("attacker" -> "5th attacker", "probability" -> new FifthAttacker().getAttackProbability(attackerQuery)),
          Map("attacker" -> "Andrzej attacker", "probability" -> new SlideAttacker(2)
            .getAttackProbability(attackerQuery))
        )
      ).
      encodeX("attacker", Nominal).
      encodeY("probability", Quantitative).
      mark(Bar)

    plot.show
  }
}


