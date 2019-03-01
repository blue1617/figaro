package attack

import com.cra.figaro.algorithm.sampling.Importance
import com.cra.figaro.language._
import com.cra.figaro.library.atomic.continuous.Normal
import com.cra.figaro.library.atomic.discrete.{Binomial, Uniform}
import com.cra.figaro.library.collection.{FixedSizeArrayElement, VariableSizeArray}

object SecondAttack {



/**   alpha ( John, 17 :: Tom,15)
    returns 16
    an attacker that knows the input list.
    a1 = Constant ( List((John,17),(Tom,15) )  )
    alpha_p (a1)
    returns Constant (16)*/

  //scala test if I call on this on line 99 is 0, under 42 should 1 should
  // include model, inference, constant attacker, age of Alice, everything
  // that is in main , add more composition as tests

  //a third attacker model


  def main(args: Array[String]): Unit = {



  }



  /*

    a2 = Select (  List((John,17),(Tom,15) -> 0.5; List((John,27),(Tom,25 ) -> 0.5 ) )
    alpa_p (a2)
    alpha_p (a2).observe (16)
    a2.probability ( l => l.size == 2)
    a2.probability (l => l.exists ( x => x == (John,15))
  */
}


