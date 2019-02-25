package hello


import com.cra.figaro.language._
import com.cra.figaro.library.atomic.continuous.Normal
import com.cra.figaro.library.atomic.discrete.{Binomial, Uniform}
import com.cra.figaro.library.collection.{FixedSizeArrayElement, VariableSizeArray}

object AverageTest {

  type Name = String
  type Age = Double
  type AverageAge = Double


  def main(args: Array[String]): Unit = {
    val dict: Seq[String] = List("John", "Jho", "Joe", "Jim", "Bob", "Alice")

    def generate(i: AverageAge): Element[(Name, Age)] = {
      for {name <- Uniform(dict: _*)
           a <- Normal(42.0, 20.0)} yield (name, a)
    }

    def generateFirstAttacker(i: AverageAge): Element[(Name, Age)] = {
      for {name <- Uniform(dict: _*)
           a <- Constant(16)} yield (name, a)
    }
    val prior: FixedSizeArrayElement[(Name, Age)] = VariableSizeArray(
      numItems = Binomial(300, 0.3) map {
        _ + 1
      },
      generator = i =>
        generate(i)

    )


    //first attacker
    val priorFirstAttacker : FixedSizeArrayElement[(Name, Age)] =
      VariableSizeArray(numItems=Constant(1),generator = i =>
        generateFirstAttacker(i))

    println("average_age first attacker " + alpha_p(priorFirstAttacker).value)
    // This is what we know about average age before any observation
    val average_age: Element[AverageAge] = alpha_p(prior)
    println("average_age= " + average_age.value)
  }

  def alpha_p(records: FixedSizeArrayElement[(Name, Age)]):
  Element[Age] = {
    records
      .map { case (n, a) => (a, 1) }
      .reduce { case (x, y) => (x._1 + y._1, x._2 + y._2) }
      .map { case (sum, count) => sum / count }
  }

  /*
    alpha ( John, 17 :: Tom,15)
    returns 16
    an attacker that knows the linput list.
    a1 = Constant ( List((John,17),(Tom,15) )  )
    alpha_p (a1)
    returns Constant (16)

    a2 = Select (  List((John,17),(Tom,15) -> 0.5; List((John,27),(Tom,25 ) -> 0.5 ) )
    alpa_p (a2)
    alpha_p (a2).observe (16)
    a2.probabiility ( l => l.size == 2)
    a2.probability (l => l.exists ( x => x == (John,15))
  */
}


