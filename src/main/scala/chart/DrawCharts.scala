package chart

import attack.{FifthAttack, FirstAttack, ForthAttack, SecondAttack, SlideAttack, ThirdAttack}
import vegas.DSL.Vegas
import vegas.spec.Spec.MarkEnums.Bar
import vegas.spec.Spec.TypeEnums.{Nominal, Quantitative}

object DrawCharts {


  def main(args: Array[String]): Unit = {


    val plot = Vegas("Attacker's Chart").
      withData(
        Seq(
          Map("attacker" -> "First attacker", "probability" -> FirstAttack.runAttack()),
          Map("attacker" -> "Second attacker", "probability" -> SecondAttack.runAttack()),
          Map("attacker" -> "Third attacker", "probability" -> ThirdAttack.runAttack()),
          Map("attacker" -> "Forth attacker", "probability" -> ForthAttack.runAttack()),
          Map("attacker" -> "Fifth attacker", "probability" -> FifthAttack.runAttack()),
          Map("attacker" -> "Slide attacker", "probability" -> SlideAttack.runAttack()
          )
          //todo: add a graph with attackers that
          //todo: use a different universe for each attacker, call a constructor and use the universe for each
          // element creation
        )
      ).
      encodeX("attacker", Nominal).
      encodeY("probability", Quantitative).
      mark(Bar)

    plot.show
  }
}


