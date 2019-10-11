package chart

import attack.{FifthAttacker, FirstAttacker, ForthAttacker, SecondAttack, SlideAttacker, ThirdAttacker}
import vegas.DSL.Vegas
import vegas.spec.Spec.MarkEnums.Bar
import vegas.spec.Spec.TypeEnums.{Nominal, Quantitative}

object DrawCharts {


  def main(args: Array[String]): Unit = {
    //todo:  rename attackers in the graph

    val plot = Vegas("Attacker's Chart").
      withData(
        Seq(
          Map("attacker" -> "1st attacker", "probability" -> new FirstAttacker()
            .getAttackProbability(a => a < 18)),
          Map("attacker" -> "2nd attacker", "probability" -> new SecondAttack()
            .getAttackProbability(a => a < 18)),
          Map("attacker" -> "3rd attacker", "probability" -> new ThirdAttacker()
            .getAttackProbability(a => a < 18)),
          Map("attacker" -> "4th attacker", "probability" -> new ForthAttacker()
            .getAttackProbability(a => a < 18)),
          Map("attacker" -> "5th attacker", "probability" -> new FifthAttacker().getAttackProbability(a => a < 18)),
          Map("attacker" -> "Andrzej attacker", "probability" -> new SlideAttacker().getAttackProbability(a => a < 18))
        )
      ).
      encodeX("attacker", Nominal).
      encodeY("probability", Quantitative).
      mark(Bar)

    plot.show
  }
}


