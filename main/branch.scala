package pipeline_4
import chisel3._
import chisel3.util._
class branch extends Module {
    val io = IO(new Bundle{
        val s1 = Input(SInt(32.W))
        val s2 = Input(SInt(32.W))
        val func3 = Input(UInt(3.W))
        val branch = Output(Bool()) 
        val ctrl_b = Input(Bool())
    })


    io.branch := 0.U
    when(io.ctrl_b === 1.B) { 
        when ( io.func3 === "b000".U) {
            when ( io.s1 === io.s2) {
                io.branch := 1.B
            }
        }.elsewhen ( io.func3 ===  "b001".U) {
            when (io.s1 =/= io.s2) {
                io.branch := 1.B
            }
        }.elsewhen (io.func3 === "b100".U) {
            when ( io.s1 < io.s2) {
                io.branch := 1.B
            }
        }.elsewhen (io.func3 === "b101".U) {
            when(io.s1 >= io.s2) {
                io.branch := 1.B
            }
        }.elsewhen (io.func3 === "b110".U) {
            when (io.s1.asUInt < io.s2.asUInt) {
                io.branch := 1.B
            }
        }.elsewhen (io.func3 === "b111".U) {
            when (io.s1.asUInt >= io.s2.asUInt) {
                io.branch := 1.B
            }
        }
    }.otherwise{
        io.branch := 0.B
    }   
}
