package pipeline_4
import chisel3._
import chisel3.util._
class hazard_det extends Module{
    val io = IO(new Bundle{
        val instruction = Input(UInt(32.W))
        val EX_rd = Input(UInt(5.W))
        val EX_read = Input(UInt(1.W))
        val pc_write = Output(Bool())
        val IF_write = Output(UInt(32.W))
        val ctrl_sel = Output(Bool())
        val fwd = Output(UInt(1.W))
        //val pc = Input(UInt(32.W))
        //val pc_out = Output(UInt(32.W))
    })
    //val a = Reg(UInt(1.W))
    //val b = Reg(UInt(1.W))

    //a := io.fwd
    //b := io.IF_write 
    when ((io.EX_read === 1.U) && (io.instruction(19,15) === io.EX_rd) && (io.instruction(24,20) === io.EX_rd)){
        io.ctrl_sel := 1.B
        io.pc_write := 1.B
        io.IF_write := io.instruction
        io.fwd := 1.U
        //io.pc_out := io.pc - 4.U
    }.elsewhen ((io.EX_read === 1.U) && (io.instruction(19,15) === io.EX_rd)){
         io.ctrl_sel := 1.B
         io.pc_write := 1.B
         io.IF_write := io.instruction
         io.fwd := 1.U
         //io.pc_out := io.pc - 4.U
    }.elsewhen ((io.EX_read === 1.U) && (io.instruction(24,20) === io.EX_rd)){
        io.ctrl_sel := 1.B
        io.pc_write := 1.B
        io.IF_write := io.instruction
        io.fwd := 1.U
        //io.pc_out := io.pc - 4.U
    }
    .otherwise {
        io.ctrl_sel := 0.B
        io.pc_write := 0.B
        io.IF_write := 0.U
        io.fwd := 0.U
        //io.pc_out := io.pc
    }
}