package pipeline_4
import chisel3._
import chisel3.util._
class control extends Module {
    val io = IO(new Bundle{
        val inst_op = Input(UInt(7.W))
        val M = Output(UInt(3.W)) // 0 bit branch or 1 bit mem read or 2 bit mem write
        val EX = Output(UInt(2.W)) // 0 bit aluop or 1 bit operand B
        val operand_A = Output(Bool())
        val extend_sel = Output(UInt(2.W))
        val Next_pc = Output(UInt(2.W))
        val WB = Output(UInt(2.W))//0 bit reg write or 1 bit mem to reg
        val al = Output(Bool())
        val zero = Input(Bool())
        val jal = Output(Bool())
    })
    io.M := 0.U
    io.operand_A := 0.U
    io.extend_sel := 0.U
    io.Next_pc := 0.U
    io.EX := 0.U
    io.WB := 0.U
    io.al := 0.B
    io.jal := 0.B
    //val a = RegInit(0.U(2.W))\
    when(io.zero === 0.U){
        when (io.inst_op === "b0110011".U) { // R-type
            val no = (io.inst_op ==="b0110011".U) & (~(io.inst_op ==="b0110011".U))
            val yes = (io.inst_op ==="b0110011".U) & (io.inst_op ==="b0110011".U)
            io.M := 0.U
            io.EX := 1.U
            //a := io.EX
            io.WB := 1.U
            io.operand_A := 0.B
            //io.extend_sel := no 
        }.elsewhen (io.inst_op === "b0010011".U) { // I-type
            val no = (io.inst_op === "b0010011".U) & (~(io.inst_op === "b0010011".U))
            val yes = (io.inst_op === "b0010011".U) & (io.inst_op === "b0010011".U)
            io.M := 0.U
            io.EX := 3.U
            //a := io.EX
            io.WB := 1.U
            io.operand_A := 0.B
            io.extend_sel := 0.U
        }.elsewhen (io.inst_op === "b0100011".U) { //S-type
            val no = (io.inst_op === "b0100011".U) & (~(io.inst_op === "b0100011".U))
            val yes = (io.inst_op === "b0100011".U) & (io.inst_op === "b0100011".U)
            io.M := 4.U
            io.WB := 0.U
            io.EX := 3.U
            //io.mem_to_reg := no
            io.operand_A := 0.B
            io.extend_sel := 1.U
        }.elsewhen (io.inst_op === "b0000011".U) { //load type (i-type)
            val no = (io.inst_op === "b0000011".U) & (~(io.inst_op === "b0000011".U))
            val yes = (io.inst_op === "b0000011".U) & (io.inst_op === "b0000011".U)
            io.M := 2.U
            io.WB := 3.U
            io.EX := 3.U
            io.operand_A := 0.B
            io.extend_sel := 0.U
        }.elsewhen (io.inst_op === "b1100011".U){//SB type 
            val no = (io.inst_op === "b1100011".U) & (~(io.inst_op === "b1100011".U))
            val yes = (io.inst_op === "b1100011".U) & (io.inst_op === "b1100011".U)
            io.M := 1.U
            io.WB := 0.U
            io.EX := 1.U
            io.operand_A := 0.B
            //io.extend_sel := "b00".U
            io.Next_pc := 1.U
            io.al := 1.B
        }.elsewhen (io.inst_op=== "b1101111".U) {//UJ-type
            val no = (io.inst_op === "b1101111".U) & (~(io.inst_op === "b1101111".U))
            val yes = (io.inst_op === "b1101111".U) & (io.inst_op === "b1101111".U)
            io.M := 0.U
            io.WB := 1.U
            io.EX := 1.U
            io.operand_A := 1.B
            io.Next_pc := 2.U
            io.jal := 1.B
        }.elsewhen (io.inst_op === "b1100111".U){//jalr - type
            val no = (io.inst_op === "b1100111".U) & (~(io.inst_op === "b1100111".U))
            val yes = (io.inst_op === "b1100111".U) & (io.inst_op === "b1100111".U)
            io.M := 0.U
            io.EX := 3.U
            io.WB := 1.U
            io.operand_A := 0.B
            io.extend_sel := "b00".U
            io.Next_pc := 3.U
        }.elsewhen (io.inst_op === "b0110111".U) {//u type
            val no = (io.inst_op === "b0110111".U) & (~(io.inst_op === "b0110111".U))
            val yes = (io.inst_op === "b0110111".U) & (io.inst_op === "b0110111".U)
            io.M := 0.U
            io.EX := 3.U
            io.WB := 1.U
            io.operand_A := 0.B
            io.extend_sel := 2.U
            //io.Next_pc := "b11".U
        }.otherwise {
            io.M := 0.U
            io.operand_A := 0.U
            io.extend_sel := 0.U
            io.Next_pc := 0.U
            io.EX := 0.U
            io.WB := 0.U
            io.al := 0.B
        }
    }.otherwise{
        io.M := 0.U
        io.operand_A := 0.U
        io.extend_sel := 0.U
        io.Next_pc := 0.U
        io.EX := 0.U
        io.WB := 0.U
        io.al := 0.B
    }
}
