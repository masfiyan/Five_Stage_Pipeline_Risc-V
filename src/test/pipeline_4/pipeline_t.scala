package pipeline_4
import chisel3._
import chisel3 . util._
import org.scalatest._
import chiseltest._
import chisel3.experimental.BundleLiterals._
import chiseltest.experimental.TestOptionBuilder._
import chiseltest.internal.VerilatorBackendAnnotation


class pipeline_4t extends FreeSpec with ChiselScalatestTester {

  "pipeline hazard detection unit" in {
    test(new pipeline4()){ dut =>
        // dut.clock.setTimeout(0)
        dut.clock.step(999)
        // // val length = 200
        // var count = 0
        // while(count < 1500){
        // dut.clock.step(1)
        // count = count +1
        // } 
  
        }
    }
}