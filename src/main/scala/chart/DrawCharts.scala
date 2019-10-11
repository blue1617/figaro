package chart

import attack.{FifthAttack, FirstAttack, ForthAttack, SecondAttack, SlideAttack, ThirdAttack}
import vegas.DSL.Vegas
import vegas.spec.Spec.MarkEnums.Bar
import vegas.spec.Spec.TypeEnums.{Nominal, Quantitative}

object DrawCharts {


  def main(args: Array[String]): Unit = {
    //todo:  rename attackers in the graph

    val plot = Vegas("Attacker's Chart").
      withData(
        Seq(
          Map("attacker" -> "Constant distribution, one element", "probability" -> new FirstAttack()
            .getAttackProbability(a
          => a < 18)),
          Map("attacker" -> "Constant distribution, two elements", "probability" -> new SecondAttack()
            .getAttackProbability(a => a < 18)),
          Map("attacker" -> "Select distribution, one element", "probability" -> new ThirdAttack()
            .getAttackProbability(a =>
            a < 18)),
          Map("attacker" -> "Uniform distribution, two elements", "probability" -> new ForthAttack()
            .getAttackProbability(a
          =>
            a < 18)),
          Map("attacker" -> "Fifth attacker", "probability" -> new FifthAttack().getAttackProbability(a => a < 18)),
          Map("attacker" -> "Slide attacker", "probability" -> new SlideAttack().getAttackProbability(a => a < 18)
          )
        )
      ).
      encodeX("attacker", Nominal).
      encodeY("probability", Quantitative).
      mark(Bar)

    plot.show
  }
}


