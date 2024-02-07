package pipeline_4
import chisel3._
import chisel3.util._
class pipe3 extends Module{
    val io = IO(new Bundle{
        
        val in3 = Input(SInt(32.W))
        val in4 = Input(SInt(32.W))
        val in5 = Input(UInt(5.W))
        val in6 = Input(UInt(2.W))
        val in7 = Input(UInt(3.W))
        val out3 = Output(SInt(32.W))
        val out4 = Output(SInt(32.W))
        val out5 = Output(UInt(5.W))
        val out6 = Output(UInt(2.W))
        val out7 = Output(UInt(3.W))

    })
    val reg3 = RegInit(0.S(32.W))
    val reg4 = RegInit(0.S(32.W))
    val reg5 = RegInit(0.U(5.W))
    val reg6 = RegInit(0.U(2.W))
    val reg7 = RegInit(0.U(3.W))
        
    reg3 := io.in3
    io.out3 := reg3

    reg4 := io.in4
    io.out4 := reg4
        
    reg5 := io.in5
    io.out5 := reg5

    reg6 := io.in6
    io.out6 := reg6

    reg7 := io.in7
    io.out7 := reg7
        
}