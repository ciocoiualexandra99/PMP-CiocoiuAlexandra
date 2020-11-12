package Lab7

import com.cra.figaro.language._
import com.cra.figaro.algorithm.sampling._
import com.cra.figaro.library.compound._
import com.cra.figaro.library.atomic.continuous
import com.cra.figaro.library.atomic.discrete
import com.cra.figaro.language.Element[Int]
object lab7  {
	def main(args: Array[String]) {
		
//a
    val skill = continuous.Uniform(0.0, 18.0 / 13.0)
    val holes = Array.fill(18)(discrete.Uniform(3, 4, 5))
    val shots = Array.tabulate(18)(index => Select(
      Apply(skill, hole, (s: Double, h: Int) => s / 8.0) -> (holes(index) - 2),
      Apply(skill, (s: Double) => s / 2.0) -> (holes(index) - 1),
      Apply(skill, (s: Double) => s) -> holes(index),
      Apply(skill, (s: Double) => 4.0 / 5.0 * (1 - 13.0 * s / 8.0)) -> (holes(index) + 1),
      Apply(skill, (s: Double) => 1.0 / 5.0 * (1 - 13.0 * s / 8.0)) -> (holes(index) + 2)))
    var score = 0
    shots.foreach(s => score += s)

    //b
   val s = continuous.Uniform(0.3, 8.0/13.0)
    val shotss = Array.tabulate(18)(index => Select(
      Apply(skill, (s: Double) => s / 8.0) -> (holes(index) - 2),
      Apply(skill, (s: Double) => s / 2.0) -> (holes(index) - 1),
      Apply(skill, (s: Double) => s) -> holes(index),
      Apply(skill, (s: Double) => 4.0 / 5.0 * (1 - 13.0 * s / 8.0)) -> (holes(index) + 1),
      Apply(skill, (s: Double) => 1.0 / 5.0 * (1 - 13.0 * s / 8.0)) -> (holes(index) + 2)))
    var scoree = 0
    shotss.foreach(s => scoree += s)

	}
}