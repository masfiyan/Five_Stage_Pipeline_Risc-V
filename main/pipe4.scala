package pipeline_4
import chisel3._
import chisel3.util._
class pipe4 extends Module{
    val io = IO(new Bundle{
        val in1 = Input(SInt(32.W))
        val in2 = Input(SInt(32.W))
        val in3 = Input(UInt(5.W))
        val in4 = Input(UInt(2.W))
        val out1 = Output(SInt(32.W))
        val out2 = Output(SInt(32.W))
        val out3 = Output(UInt(5.W))
        val out4 = Output(UInt(2.W))
    })
    val reg1 = RegInit(0.S(32.W))
    val reg2 = RegInit(0.S(32.W))
    val reg3 = RegInit(0.U(5.W))
    val reg4 = RegInit(0.U(2.W))
    
    reg1 := io.in1
    io.out1 := reg1
        
    reg2 := io.in2
    io.out2 := reg2
        
    reg3 := io.in3
    io.out3 := reg3

    reg4 := io.in4
    io.out4 := reg4

        
}