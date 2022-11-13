// package pipeline_FU
// import chisel3._
// import chisel3.util._
// class branch_control extends Module {
//     val io = IO(new Bundle{
//         val in = Input(UInt(32.W))
//         val aluop = Input(Bool())
//         val control = Output(UInt(32.W))
//     })
//     io.control := 0.U
//     when (io.aluop === 1.B) {
//         when (io.in(6,0) === "b1100011".U) {//SB type
//             when ( io.in(14,12) === "b000".U) {//sb (beq)
//                 io.control := 10.U 
//             }.elsewhen (io.in(14,12) === "b001".U) {//sb (bne)
//                 io.control := 11.U
//             }.elsewhen (io.in(14,12) === "b100".U) {//sb (blt)
//                 io.control := 12.U
//             }.elsewhen (io.in(14,12) === "b101".U) {//sb (bge)
//                 io.control := 13.U
//             }.elsewhen (io.in(14,12) === "b110".U) {//sb (bltu)
//                 io.control := 14.U
//             }.elsewhen (io.in(14,12) === "b111".U) {//sb (bgeu)
//                 io.control := 15.U
//             }
//         }
//     }
// }