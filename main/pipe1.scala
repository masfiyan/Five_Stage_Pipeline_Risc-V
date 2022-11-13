package pipeline_4
import chisel3._
import chisel3.util._
class pipe1 extends Module{
    val io = IO(new Bundle{
        val in1 = Input(UInt(32.W))   //  pc 
        val in2 = Input(UInt(32.W))   // insturction
        val in3 = Input(UInt(32.W))  // pc4
        val out1 = Output(UInt(32.W))
        val out2 = Output(UInt(32.W))
        val out3 = Output(UInt(32.W))
    })
    io.out2 := 0.U
    val a = RegInit(0.U(32.W))
    val b = RegInit(0.U(32.W))
    val c = RegInit(0.U(32.W))
    b := io.in2
    a := io.in1
    c := io.in3 
    io.out1 := a
    io.out2 := b
    io.out3 := c

}