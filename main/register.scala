package pipeline_4
import chisel3._
import chisel3.util._
class register extends Module {
    val io = IO (new Bundle{
        val r1 = Input(UInt(5.W))
        val r2 = Input(UInt(5.W))
        val W_rd = Input(UInt(5.W))
        val M_rd = Input(UInt(5.W))
        val reg_Write = Input(Bool())
        val alu_out = Input(SInt(32.W))
        val write_date = Input(SInt(32.W))
        val rs1 = Output(SInt(32.W))
        val rs2 = Output(SInt(32.W))
        val lw = Input(Bool())
        //val rd = Output(UInt(32.W))
    })
    val regs = RegInit( VecInit (Seq.fill(32)(0.S( 32 . W ) ) ))
    io . rs1 := Mux (( io . r1 . orR ) , regs ( io .r1 ), 0.S )
    io . rs2 := Mux (( io . r2. orR ) , regs ( io . r2 ) , 0.S )
    when ((io.lw === 0.B) && (io.r1 === io.W_rd) && (io.r2 === io.W_rd)){
        io.rs1 := io.alu_out
        io.rs2 := io.alu_out
    }.elsewhen( (io.lw === 0.B) && (io.r1 === io.M_rd) && (io.r2 === io.M_rd)){
        io.rs1 := io.write_date
        io.rs2 := io.write_date
    }.elsewhen ((io.lw === 0.B) && (io.r1 === io.M_rd) && (io.r2 === io.W_rd)){
        io.rs1 := io.write_date
        io.rs2 := io.alu_out
    }.elsewhen((io.lw === 0.B) && (io.r1 === io.W_rd) && (io.r2 === io.M_rd)){
        io.rs1 := io.alu_out
        io.rs2 := io.write_date
    }.elsewhen ((io.lw === 0.B) && io.r2 === io.W_rd && (io.r1 === 0.U)) {
        io.rs1 := Mux (( io . r1 . orR ) , regs ( io .r1 ), 0.S )
        io.rs2 := io.alu_out
    }.elsewhen ((io.lw === 0.B) && io.r1 === io.M_rd && (io.r2 === 0.U)){
        io.rs1 := io.write_date
        io.rs2 := Mux (( io . r2. orR ) , regs ( io . r2 ) , 0.S )
    }.elsewhen ((io.lw === 0.B) && io.r2 === io.M_rd && (io.r1 === 0.U)) {
        io.rs1 := Mux (( io . r1 . orR ) , regs ( io .r1 ), 0.S )
        io.rs2 := io.write_date
    }.elsewhen ((io.lw === 0.B) && io.r1 === io.W_rd && (io.r2 === 0.U)){
        io.rs1 := io.alu_out
        io.rs2 := Mux (( io . r2. orR ) , regs ( io . r2 ) , 0.S )
    }
    // when (io.r1 === io.rd && io.r2 === io.rd) {
    //     io.rs1 := io.write_date
    //     io.rs2 := io.write_date
    // }.elsewhen (io.r2 === io.rd) {
    //     io.rs1 := Mux (( io . r1 . orR ) , regs ( io .r1 ), 0.S )
    //     io.rs2 := io.write_date
    // }.elsewhen (io.r1 === io.rd){
    //     io.rs1 := io.write_date
    //     io.rs2 := Mux (( io . r2. orR ) , regs ( io . r2 ) , 0.S )
    // }
    when ( io.reg_Write & io.M_rd.orR ) {
        regs ( io .M_rd ) := io . write_date
    }
}
