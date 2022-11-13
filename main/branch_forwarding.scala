package pipeline_4
import chisel3._
import chisel3.util._
class branch_forwarding extends Module{
    val io = IO (new Bundle{
        val ctrl_b = Input(Bool())
        val EX_rd = Input(UInt(5.W))
        val Mem_rd = Input(UInt(5.W))
        val WB_rd = Input(UInt(5.W))
        val br1 = Input(UInt(5.W))
        val br2 = Input(UInt(5.W))
        val forward_rs1 = Output(UInt(2.W))
        val forward_rs2 = Output(UInt(2.W))
    })
    // EX = 01
    // Mem = 10
    // wb = 11
    when ((io.ctrl_b === 1.B) && (io.EX_rd === io.br1) && (io.EX_rd === io.br2)) {
        io.forward_rs1 := "b01".U
        io.forward_rs2 := "b01".U
    }.elsewhen ((io.ctrl_b === 1.B) && (io.Mem_rd === io.br1) && (io.Mem_rd === io.br2)) {
        io.forward_rs1 := "b10".U
        io.forward_rs2 := "b10".U
    }.elsewhen ((io.ctrl_b === 1.B) && (io.Mem_rd === io.br1) && (io.EX_rd === io.br2)) {
        io.forward_rs1 := "b10".U
        io.forward_rs2 := "b01".U
    }.elsewhen ((io.ctrl_b === 1.B) && (io.EX_rd === io.br1) && (io.Mem_rd === io.br2)) {
        io.forward_rs1 := "b01".U
        io.forward_rs2 := "b10".U
    }.elsewhen ((io.ctrl_b === 1.B) && (io.Mem_rd === io.br1) && (io.br2 === 0.U)) {
        io.forward_rs1 := "b10".U
        io.forward_rs2 := "b00".U
    }.elsewhen ((io.ctrl_b === 1.B) && (io.br1 === 0.U) && (io.Mem_rd === io.br2)) {
        io.forward_rs1 := "b00".U
        io.forward_rs2 := "b10".U
    }.elsewhen ((io.ctrl_b === 1.B) && (io.EX_rd === io.br1) && (io.br2 === 0.U )) {
        io.forward_rs1 := "b01".U
        io.forward_rs2 := "b00".U
    }.elsewhen ((io.ctrl_b === 1.B) && (io.br1 === 0.U) && (io.EX_rd === io.br2)) {
        io.forward_rs1 := "b00".U
        io.forward_rs2 := "b01".U



    // ------------------- EX vs Mem done ------------------------------------------------



    }.elsewhen ((io.ctrl_b === 1.B) && (io.WB_rd === io.br1) && (io.WB_rd === io.br2)) {
        io.forward_rs1 := "b11".U
        io.forward_rs2 := "b11".U
    }.elsewhen ((io.ctrl_b === 1.B) && (io.WB_rd === io.br1) && (io.EX_rd === io.br2)) {
        io.forward_rs1 := "b11".U
        io.forward_rs2 := "b01".U
    }.elsewhen ((io.ctrl_b === 1.B) && (io.EX_rd === io.br1) && (io.WB_rd === io.br2)) {
        io.forward_rs1 := "b01".U
        io.forward_rs2 := "b11".U
    }.elsewhen ((io.ctrl_b === 1.B) && (io.WB_rd === io.br1) && (io.br2 === 0.U)) {
        io.forward_rs1 := "b11".U
        io.forward_rs2 := "b00".U
    }.elsewhen ((io.ctrl_b === 1.B) && (io.br1 === 0.U) && (io.WB_rd === io.br2)) {
        io.forward_rs1 := "b00".U
        io.forward_rs2 := "b11".U



    // -------------------- WB vs EX done ----------------------------------------------



    }.elsewhen ((io.ctrl_b === 1.B) && (io.WB_rd === io.br1) && (io.Mem_rd === io.br2)) {
        io.forward_rs1 := "b11".U
        io.forward_rs2 := "b10".U
    }.elsewhen ((io.ctrl_b === 1.B) && (io.Mem_rd === io.br1) && (io.WB_rd === io.br2)) {
        io.forward_rs1 := "b10".U
        io.forward_rs2 := "b11".U
    }.otherwise {
        io.forward_rs1 := "b00".U
        io.forward_rs2 := "b00".U
    }


    // ------------------- WB vs Mem done ------------------------------------------------------



}
