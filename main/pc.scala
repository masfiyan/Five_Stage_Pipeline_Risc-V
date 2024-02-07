package pipeline_4
import chisel3._
import chisel3.util._
class pc extends Module {
    val io = IO(new Bundle {
        val start = Input(UInt(32.W))
        val pc = Output(UInt(32.W))
        val pc4 = Output(UInt(32.W))
        val pc_write = Input(Bool())

        
    })
    val count = RegInit(0.U(32.W))
    count := io.start
    when(io.pc_write === 0.B){
        io.pc4 := count + 4.U
        //io.pc := count
    }
    .otherwise{
        io.pc4 := count
    }
    io.pc := count
}

