package hello

import com.cra.figaro.algorithm.factored.VariableElimination
import com.cra.figaro.algorithm.sampling.Importance
import com.cra.figaro.language._
import com.cra.figaro.library.atomic.continuous.Normal
import com.cra.figaro.library.atomic.discrete.{Binomial, Uniform}
import com.cra.figaro.library.collection.{FixedSizeArrayElement, VariableSizeArray}

object HelloWorld {

  type Name = String
  type Age = Integer
  type AverageAge = Integer
  def main(args: Array[String]): Unit = {
    /*
    //    val helloWorldElement = Constant("Hello world!")
    val helloWorldElement = Select(0.8 -> "Hello world", 0.2 -> "Goodbye world")
    //    val sampleHelloWorld = Importance(1000, helloWorldElement)
    val sampleHelloWorld = VariableElimination(helloWorldElement)
    sampleHelloWorld.start()
    println("Probability of Hello world:")
    println(sampleHelloWorld.probability(helloWorldElement, "Hello world!"))
    println("Probability of Goodbye world:")
    println(sampleHelloWorld.probability(helloWorldElement, "Goodbye world!"))
*/

    val helloWorldElement = Select(0.8 -> "Hello world", 0.2 -> "Goodbye world")
    val sampleHelloWorld = VariableElimination(helloWorldElement)
    sampleHelloWorld.start()
    println("Probability of Hello world:")
    println(sampleHelloWorld.probability(helloWorldElement, "Hello world!"))
    println("Probability of Goodbye world:")
    println(sampleHelloWorld.probability(helloWorldElement, "Goodbye world!"))
  }



}


