id: file://<WORKSPACE>/src/test/scala/gcd/Practice/t1t.scala:[144..151) in Input.VirtualFile("file://<WORKSPACE>/src/test/scala/gcd/Practice/t1t.scala", "package Practice 

import chisel3._
import chisel3.tester._
import org.scalatest.FreeSpec
import chisel3.experimental.BundleLiterals._


class  extends FreeSpec with ChiselScalatestTester {
    "Chisel Tester file " in {
        test(new My_Queue) { a =>
            a.io.in.valid.poke(1.B)
            a.io.in.bits.poke(1.B)
            a.io.in.ready.poke(1.B)
        }
    }
}
")
file://<WORKSPACE>/src/test/scala/gcd/Practice/t1t.scala
file://<WORKSPACE>/src/test/scala/gcd/Practice/t1t.scala:9: error: expected identifier; obtained extends
class  extends FreeSpec with ChiselScalatestTester {
       ^
#### Short summary: 

expected identifier; obtained extends