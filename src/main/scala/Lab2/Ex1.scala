
import com.cra.figaro.language._
import com.cra.figaro.algorithm.sampling._



object  LAb2 {
	def main(args: Array[String]) {
		val test = Constant("Test")

		val algorithm = Importance(1000, test)
		algorithm.start()
		
		println(algorithm.probability(test, "Test"))
	}

    abstract class Persoana() {

}


class Student(var nume:String, var prenume:String, var an:Integer, var materii: Array[(String,Integer)]) extends Persoana{


}

class Profesor(var nume:String, var prenume:String, var materie: String) extends Persoana{


}
}