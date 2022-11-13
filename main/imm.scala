package pipeline_4
import chisel3._
import chisel3.util._
class immb extends Bundle {
    val instr = Input ( UInt (32. W ) )
    val itype = Output ( SInt (32. W ) )
    val stype = Output ( SInt (32. W ) )
    val sbtype = Output ( SInt (32. W ) )
    val utype = Output ( SInt (32. W ) )
    val ujtype = Output ( SInt (32. W ) )
    val pc_in = Input(UInt(32.W))
}
object immo{
    val i = 19.U(7.W)
    val s = 35.U(7.W)
    val sb = 99.U(7.W)
    val u = 55.U(7.W)
    val uj = 111.U(7.W)
    val ei = "hffffffff".U(32.W)
    val e0 = 0.U(32.W)
}
class imm extends Module {
    val io = IO ( new immb)
    io.itype := Mux(io.instr(6,0) === immo.i || io.instr(6,0) === "b0000011".U || io.instr(6,0) === "b1100111".U,Cat(Fill(20,io.instr(31)), io.instr(31,20) ) , 0.U).asSInt
    io.stype := Mux(io.instr(6,0) === immo.s , Cat(Fill(20,io.instr(31)) , io.instr(31,25) , io.instr(11,7) ) , 0.U ).asSInt
    io.sbtype := Mux(io.instr(6,0) === immo.sb , Cat(Fill(20,io.instr(31)) , io.instr(31) , io.instr(7) , io.instr(30,25) , io.instr(11,8) , "b0".U ) + (io.pc_in) , 0.U).asSInt
    io.utype := Mux(io.instr(6,0) === immo.u , Cat( io.instr(31,12) , Fill(12,io.instr(31)) ) , 0.U ).asSInt
    io.ujtype := Mux(io.instr(6,0) === immo.uj , Cat(Fill(12,io.instr(31)) , io.instr(31) , io.instr(19,12) , io.instr(20) , io.instr(30,21) , "b0".U) + (io.pc_in) , 0.U).asSInt
    

}