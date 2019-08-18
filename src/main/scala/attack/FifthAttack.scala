package attack

import attack.AverageProgram.{Age, Name}
import com.cra.figaro.language.Element
import com.cra.figaro.library.atomic.discrete.Uniform

/**
  * Created by apreda on 13.08.2019.
  */
object FifthAttack {


  def generateFifthAttacker(dictName: Seq[Name], dictAge:Seq[Age]): Element[(Name, Age)] = {
    for {name <- Uniform(dictName: _*)
         a <- Uniform(dictAge: _*)} yield (name, a)
  }
}
