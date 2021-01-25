package Lab13
import com.cra.figaro.algorithm.sampling._
import com.cra.figaro.algorithm.factored._
import com.cra.figaro.language._
import com.cra.figaro.library.compound._
import com.cra.figaro.algorithm.factored.beliefpropagation._
import com.cra.figaro.algorithm.sampling.Importance
import com.cra.figaro.algorithm.factored.{VariableElimination, MPEVariableElimination}
import com.cra.figaro.algorithm.factored.beliefpropagation.MPEBeliefPropagation
import com.cra.figaro.algorithm.OneTimeMPE


object lab13{

	abstract class State {
		val confident: Element[Boolean]
		def possession: Element[Boolean] = If(confident, Flip(0.7), Flip(0.3))
	}
	class InitialState() extends State {
		val confident = Flip(0.4)
	}
	class NextState(current: State) extends State {
		val confident = If(current.confident, Flip(0.6), Flip(0.3))
	}
	def stateSequence(n: Int): List[State] = {
		if (n == 0) List(new InitialState())
	else {
		val last :: rest = stateSequence(n - 1)
		new NextState(last) :: last :: rest
		}
	}
	
	def run(algorithm: OneTimeMPE, stateSeq : List[State],obsSeq:List[Boolean]) {
 		algorithm.start()
 		for{i<- 1 until 10 } {
 			print(algorithm.mostLikelyValue(stateSeq(i).confident))
 			print(" ")
			print(obsSeq(obsSeq.length - 1 - i))
			print(" ")
 		}
 		println()
		println()
 		algorithm.kill()
 	}



	def main(args: Array[String]){
		val steps = 10
 		val obsSeq = List.fill(steps)(scala.util.Random.nextBoolean())	
		val stateSeq = stateSequence(obsSeq.length)
		for { i <- 0 until obsSeq.length } 
			stateSeq(i).possession.observe(obsSeq(obsSeq.length - 1))
		println("Observations: ")
		print(obsSeq)
		println()
		println()
		println("MPE variable elimination: ")
		run(MPEVariableElimination(),stateSeq,obsSeq)
		println("MPE belief propagation: ")
		run(MPEBeliefPropagation(10),stateSeq,obsSeq)
		println("Simulated annealing:")
		run(MetropolisHastingsAnnealer(100, ProposalScheme.default,Schedule.default(1.0)),stateSeq,obsSeq)
 	}
}