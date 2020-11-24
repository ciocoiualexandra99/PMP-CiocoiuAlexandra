package Partial

import com.cra.figaro.language._
import com.cra.figaro.algorithm.sampling._

object Partial {
	def main(args: Array[String]) {
		//variabila setAlarm pt cazul in care nu uita sa seteze alarma-90%,nu uita
		val setAlarm=Flip(0.9)
        //variabila wake-upLate, daca setalarm e adevarat te trezesti ,dar poti adormi inapoi
        val Wake-upLate = CPD(setAlarm,
 false -> Flip(0.01),
 true -> Flip(0.9))

 val LateBus=Flip(0.2)

val Work=CPD(Wake-upLate , LateBus,
 (false, false) -> Flip(0.9),
 (false, true) -> Flip(0.2),
 (true, false) -> Flip(0.3),
 (true, true) -> Flip(0.1))	

 	Work.observe(true)
 val alg = VariableElimination(Wake-upLate, LateBus)
 alg.start()
 println("a.Probabilitatea de a ajunge la timp la serviciu " + alg.probability(Wake-upLate,true))
println("b.Probabilitatea de a-ti fi setat alarma:" + alg.probability(setAlarm,true))
Work.unobserve



	}
}