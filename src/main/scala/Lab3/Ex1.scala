package Lab3 

import com.cra.figaro.language._
import com.cra.figaro.algorithm.sampling._
import com.cra.figaro.library.compound._
object Ex1 {
	def main(args: Array[String]) {
		
	val tuse=Flip(0.04)
	val febra=Flip(0.06)
    val infectat=Flip(0.03)
	
	val simptome=CPD(tuse,febra,
	(false,false) -> Flip(0.01),
	(false,true) -> Flip(0.05),
	(true,false) -> Flip(0.002),
	(true,true) -> Flip(0.03))

	val covidcheck=CPD(simptome,
	false->Flip(0.03),
	true->Flip(0.04)
	)

	covidcheck.observe(true)
	
	val alg=Importance(100,tuse,febra)
	
	alg.start()
	
	println("probabilitate tuse: " + alg.probability(tuse,true))
	println("probabilitatea febra : " + alg.probability(febra, true))
	println("Probabilitatea ca suferi de covid: " + alg.probability(covidcheck,true))
   
  
	}
}
