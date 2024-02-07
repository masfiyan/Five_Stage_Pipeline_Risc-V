package pipeline_4
import chisel3._
import chisel3.util._
class forwarding_unit extends Module{
    val io = IO(new Bundle{
        //val op = Input(UInt(7.W))
        val rs1 = Input(UInt(5.W))
        val rs2 = Input(UInt(5.W))
        val Mem_rd = Input(UInt(5.W))
        val Mem_wb = Input(UInt(1.W))
        val Wb_rd = Input(UInt(5.W))
        val Wb_wb = Input(UInt(1.W))
        val forward_a = Output(UInt(2.W))
        val forward_b = Output(UInt(2.W))
    
    })
    when ((io.Mem_wb === 1.U) && (io.Wb_wb === 1.U) && (io.Mem_rd =/= 0.U) && (io.Wb_rd =/= 0.U) && (io.Wb_rd === io.rs1) && (io.Mem_rd === io.rs2)) {
        io.forward_a := "b01".U
        io.forward_b := "b10".U
    }.elsewhen ( io.Mem_wb === 1.U && (io.Wb_wb === 1.U) && (io.Mem_rd =/= 0.U) && (io.Wb_rd =/= 0.U) && (io.Mem_rd === io.rs1) && (io.Wb_rd === io.rs2)) {
        io.forward_a := "b10".U
        io.forward_b := "b01".U
    // }.elsewhen ( io.Mem_wb === 1.U && (io.Mem_rd =/= 0.U) && ((io.rs2 === 0.U) || (io.rs2 =/= 0.U)) && (io.rs1 === io.Mem_rd)){
    //     io.forward_a := "b10".U
    //     io.forward_b := 0.U
    }.elsewhen (io.Mem_wb === 1.U && (io.Mem_rd =/= 0.U) && (io.rs1 === 0.U) && (io.rs2 === io.Mem_rd)){
        io.forward_b := "b10".U
        io.forward_a :=  0.U
    }.elsewhen ( io.Wb_wb === 1.U && (io.Wb_rd =/= 0.U) && (io.rs2 === 0.U) && (io.rs1 === io.Wb_rd)){
        io.forward_a := "b01".U
        io.forward_b := 0.U
    }.elsewhen ( io.Wb_wb === 1.U && (io.Wb_rd =/= 0.U) && (io.rs1 === 0.U ) && (io.rs2 === io.Wb_rd)){
        io.forward_b := "b01".U
        io.forward_a := 0.U
    }.elsewhen ( io.Wb_wb === 1.U && (io.Wb_rd =/= 0.U) && (io.rs1 === io.Wb_rd) && (io.rs2 === io.Wb_rd)){
        io.forward_b := "b01".U
        io.forward_a := "b01".U
    
    }.elsewhen ( io.Mem_wb === 1.U && (io.Mem_rd =/= 0.U) && (io.rs1 === io.Mem_rd) && (io.rs2 === io.Mem_rd)){
        io.forward_b := "b10".U
        io.forward_a := "b10".U
    }.elsewhen ((io.Mem_wb === 1.U) && (io.Wb_wb === 0.U) && (io.rs1 === io.Mem_rd) ){
        io.forward_a := "b10".U
        io.forward_b := "b00".U
    
    // }.elsewhen ( io.Wb_wb === 1.U && (io.Wb_rd =/= 0.U) && (io.rs2 === io.Mem_rd) && ((io.rs1 === 0.U) || (io.rs1 =/= 0.U))){
    //     io.forward_b := "b00".U
    //     io.forward_a := "b10".U
    
    // }.elsewhen (io.Mem_wb === 1.U && (io.Mem_rd === io.rs1)) {// addi 
    //     io.forward_a := "b10".U
    //     io.forward_b := 0.U
    // }.elsewhen ( io.Wb_wb === 1.U && (io.Wb_rd === io.rs1)) {// addi 
    //     io.forward_a := "b01".U
    //     io.forward_b := 0.U
    }.otherwise {
        io.forward_a := "b00".U
        io.forward_b := 0.U
    }
    // when ((io.Mem_wb === 1.U) && (io.Mem_rd =/= 0.U) && (io.rs1 === io.Mem_rd) && (io.rs2 === io.Mem_rd)) {
    //     io.forward_a := "b10".U
    //     io.forward_b := "b10".U
    // }.elsewhen ((io.Mem_wb === 1.U) && (io.Mem_rd =/= 0.U) && (io.rs1 === io.Mem_rd)) {
    //     io.forward_a := "b10".U
    //     io.forward_b := "b00".U
    // }.elsewhen ((io.Mem_wb === 1.U) && (io.Mem_rd =/= 0.U ) && (io.rs2 === io.Mem_rd)) {
    //     io.forward_a := "b00".U
    //     io.forward_b := "b10".U
    // }.elsewhen ((io.Wb_wb === 1.U) && (io.Wb_rd =/= 0.U) && (io.rs1 === io.Wb_rd) && (io.rs2 === io.Wb_rd)){
    //     io.forward_a := "b01".U
    //     io.forward_b := "b01".U
    // }.elsewhen ((io.Wb_wb === 1.U) && (io.Wb_rd =/= 0.U) && (io.rs1 === io.Wb_rd)){
    //     io.forward_a := "b01".U
    //     io.forward_b := "b00".U
    // }.elsewhen ((io.Wb_wb === 1.U) && (io.Wb_rd =/= 0.U) && (io.rs2 === io.Wb_rd)){
    //     io.forward_a := "b00".U
    //     io.forward_b := "b01".U
    // }
    // val a = Mux(io.Mem_wb & (io.Mem_rd =/= 0.U) , 
    //             Mux(io.Mem_rd === io.rs1 , 2.U , 0.U) , 
    //         Mux(io.Wb_wb & (io.Wb_rd =/= 0.U) ,
    //             Mux(io.Wb_rd === io.rs1 ,1.U ,0.U) , 
    //         0.U))
    // io.forward_a := a
    // when (io.Mem_wb === 1.U && (io.Mem_rd =/= 0.U) ){
    //     when(io.Mem_rd === io.rs1){
    //         io.forward_a := 2.U
    //     }
    // } elsewhen (io.Wb_wb === 1.B && io.Wb_rd =/= 0.U ){
    //     when (io.Wb_rd === io.rs1){
    //         io.forward_a := 1.U
    //     }
    // }
    // io . forward_a := MuxCase ( 0 .U , Array (
    // ( io.Mem_wb === 1.B && io.Mem_rd =/= 0.U && io.Mem_rd === io.rs1) -> 2.U ,
    // ( io.Wb_wb === 1.B  &&  io.Wb_rd =/= 0.U && io.Wb_rd === io.rs1 ) -> 1.U,
    // ))
    // io . forward_b := MuxCase ( 0 .U , Array (
    // ( io.Mem_wb === 1.B && io.Mem_rd =/= 0.U && io.Mem_rd === io.rs2) -> 2.U ,
    // ( io.Wb_wb === 1.B  &&  io.Wb_rd =/= 0.U && io.Wb_rd === io.rs2 ) -> 1.U,
    // ))
    // when((io.Wb_wb === 1.U) && (io.Wb_rd =/= 0.U)  && (io.Wb_rd === io.rs1) && (io.Wb_rd === io.rs2)) {
    //     io.forward_a := "b01".U
    //     io.forward_b := "b01".U
    // }.elsewhen((io.Mem_wb === 1.U) && (io.Mem_rd =/= 0.U)  && (io.Mem_rd === io.rs1) && (io.Mem_rd === io.rs2)) {
    //     io.forward_a := "b10".U
    //     io.forward_b := "b00".U
    // }.elsewhen((io.Mem_wb === 1.U) && (io.Mem_rd =/= 0.U) && (io.Mem_rd === io.rs2)) {
    //     io.forward_b := "b10".U
    //     io.forward_a := "b01".U
    // }.elsewhen((io.Mem_wb === 1.U) && (io.Mem_rd =/= 0.U) && (io.Mem_rd === io.rs1)) {
    //     io.forward_a := "b10".U
    //     io.forward_b := "b00".U
    // }.elsewhen((io.Wb_wb === 1.U) && (io.Wb_rd =/= 0.U) && (io.Wb_rd === io.rs2)) {
    //     io.forward_b := "b01".U
    // }.elsewhen((io.Wb_wb === 1.U) && (io.Wb_rd =/= 0.U) && (io.Wb_rd === io.rs1)) {
    //     io.forward_a := "b10".U
    //     io.forward_b := "b00".U
    // }
    // }

}

    
    //val b = 
    // when (io.Mem_wb && (io.Mem_rd =/= 0.U)) {
    //     when(io.Mem_rd === io.rs1){
    //         io.forward_a := 2.U
    //     } 
    //     when (io.Mem_rd === io.rs2){
    //         io.forward_b := 2.U
    //     }
    // } elsewhen (io.Wb_wb ) {
        
    //     when (io.Wb_rd != 0.U) {
    //         when(io.Wb_rd === io.rs2){
    //             io.forward_a := 1.U
    //         } 
    //         when (io.Wb_rd === io.rs2){
    //             io.forward_b := 1.U
    //         }
    //     }
    // } otherwise {
    //     io.forward_a := 0.U
    //     io.forward_b := 0.U
    // }
