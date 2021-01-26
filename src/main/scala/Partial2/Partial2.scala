package Partial2

import com.cra.figaro.language._
import com.cra.figaro.algorithm.sampling._

object Ex1 {

abstract class State {
val Ploios: Element[Boolean]
val Start : Int
val Insorit: Int
val Innorat :Int

def vreme: Element[Boolean] =
If()
}
class InitialState()
      extends State {
    val Start=
  }

	def main(args: Array[String]) {
		val test = Constant("Test")

		val algorithm = Importance(1000, test)
		algorithm.start()
		
		println(algorithm.probability(test, "Test"))
	}
}