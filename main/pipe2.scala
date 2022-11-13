package pipeline_4
import chisel3._
import chisel3.util._
class pipe2 extends Module{
    val io = IO(new Bundle{
        val in1 = Input(Bool()) // for jal (operand A)
        val in1pc = Input(UInt(32.W)) // pc (jal)
        val in2 = Input(SInt(32.W))
        val in3 = Input(SInt(32.W))
        val in4 = Input(SInt(32.W))
        val in5 = Input(UInt(32.W))
        val in6 = Input(UInt(5.W)) // rd 
        val in7 = Input(UInt(2.W)) 
        val in8 = Input(UInt(3.W))
        val in9 = Input(UInt(2.W)) 
        val out1 = Output(Bool())
        val out1pc = Output(UInt(32.W))
        val out2 = Output(SInt(32.W))
        val out3 = Output(SInt(32.W))
        val out4 = Output(SInt(32.W))
        val out5 = Output(UInt(32.W))
        val out6 = Output(UInt(5.W))
        val out7 = Output(UInt(2.W))
        val out8 = Output(UInt(3.W))
        val out9 = Output(UInt(2.W))
        val out10 = Output(UInt(5.W))
        val out11 = Output(UInt(5.W))
    })
    val reg1 = RegInit(0.U(1.W))
    val reg1pc = RegInit(0.U(32.W))
    val reg2 = RegInit(0.S(32.W))
    val reg3 = RegInit(0.S(32.W))
    val reg4 = RegInit(0.S(32.W))
    val reg5 = RegInit(0.U(32.W))
    val reg6 = RegInit(0.U(5.W))
    val reg7 = RegInit(0.U(2.W))
    val reg8 = RegInit(0.U(3.W))
    val reg9 = RegInit(0.U(2.W))
    val reg10 = RegInit(0.U(5.W))
    val reg11 = RegInit(0.U(5.W))
    
    reg1 := io.in1
    io.out1 := reg1
        
    reg1pc := io.in1pc
    io.out1pc := reg1pc

    reg2 := io.in2
    io.out2 := reg2
        
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

    reg8 := io.in8
    io.out8 := reg8

    reg9 := io.in9
    io.out9 := reg9

    reg10 := io.in5(19,15)
    io.out10 := reg10

    reg11 := io.in5(24,20)
    io.out11 := reg11
}