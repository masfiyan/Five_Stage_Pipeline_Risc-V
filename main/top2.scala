package pipeline_4
import chisel3._
import chisel3.util._
class pipeline4 extends Module {
    val io = IO(new Bundle{
    })
    val pipe1_m = Module(new pipe1)
    dontTouch(pipe1_m.io)
    val pipe2_m = Module(new pipe2)
    dontTouch(pipe2_m.io)
    val pipe3_m = Module(new pipe3)
    dontTouch(pipe3_m.io)
    val pipe4_m = Module(new pipe4)
    dontTouch(pipe4_m.io) 
    val program_counter = Module(new pc)
    dontTouch(program_counter.io)
    val control_m = Module(new control)
    dontTouch(control_m.io)
    val alu_control_m =Module(new alu_control)
    dontTouch(alu_control_m.io)
    val data_mem_m = Module(new data_mem)
    dontTouch(data_mem_m.io)
    val inst_mem_m = Module(new inst_mem("/home/masfiyan/Desktop/inst.hex"))
    dontTouch(inst_mem_m.io)
    val register_m = Module(new register)
    dontTouch(register_m.io)
    val alu_m = Module(new alu)
    dontTouch(alu_m.io)
    val forwarding_unit_m = Module(new forwarding_unit)
    dontTouch(forwarding_unit_m.io)
    val imm_m = Module(new imm)
    dontTouch(imm_m.io)
    val hazard2 = Module(new hazard_det)
    dontTouch(hazard2.io)
    val branch_forwarding_m = Module(new branch_forwarding)
    dontTouch(branch_forwarding_m.io)
    val branch_m = Module(new branch)
    dontTouch(branch_m.io)


    program_counter.io.start := (program_counter.io.pc4)
    program_counter.io.pc_write := hazard2.io.pc_write




    pipe1_m.io.in1 := program_counter.io.pc
    pipe1_m.io.in2 := Mux(control_m.io.jal , 0.U , inst_mem_m.io.inst)
    pipe1_m.io.in3 := program_counter.io.pc4



    hazard2.io.instruction := pipe1_m.io.out2
    hazard2.io.EX_rd := pipe2_m.io.out6
    hazard2.io.EX_read := pipe2_m.io.out8(1)
    //hazard2.io.pc := pipe1_m.io.out1




    pipe1_m.io.in2 := MuxCase (inst_mem_m.io.inst,Array(
        (hazard2.io.fwd === 1.U) -> hazard2.io.IF_write,
        (branch_m.io.branch === 1.B) -> 0.U
    ))//Mux( hazard2.io.fwd === 1.U, hazard2.io.IF_write , inst_mem_m.io.inst)
    //program_counter.io.start := program_counter.io.pc4







    register_m.io.r1 := pipe1_m.io.out2(19,15)
    register_m.io.r2 := pipe1_m.io.out2(24,20)
    register_m.io.alu_out := pipe3_m.io.out3
    register_m.io.W_rd := pipe3_m.io.out5
    register_m.io.lw := hazard2.io.ctrl_sel




    val branch_reg = RegInit(0.U(1.W))
    branch_reg := control_m.io.M(0) 
    branch_forwarding_m.io.ctrl_b := control_m.io.M(0) //branch_reg
    branch_forwarding_m.io.EX_rd := pipe2_m.io.out6
    branch_forwarding_m.io.Mem_rd := pipe3_m.io.out5
    branch_forwarding_m.io.WB_rd := pipe4_m.io.out3
    branch_forwarding_m.io.br1 := pipe1_m.io.out2(19,15)
    branch_forwarding_m.io.br2 := pipe1_m.io.out2(24,20)






    imm_m.io.instr := pipe1_m.io.out2
    imm_m.io.pc_in := program_counter.io.pc



    control_m.io.inst_op := pipe1_m.io.out2(6,0)
    control_m.io.zero := hazard2.io.ctrl_sel


    val imm_generator = MuxLookup (  (control_m.io.extend_sel), 0.U , Array (
    (0. U ) -> (imm_m.io.itype),
    (1. U ) -> (imm_m.io.stype),
    (2. U ) -> (imm_m.io.utype),
    (3. U ) -> 0.S))



    val Next_sel_mux = MuxCase ( 0.S,Array(
        ((control_m.io.Next_pc) === 0. U) -> (program_counter.io.pc4).asSInt,
        (((control_m.io.Next_pc) === 1. U) && (branch_m.io.branch === 0.B) ) -> (program_counter.io.pc4).asSInt,
        (((control_m.io.Next_pc) === 1. U)  && (branch_m.io.branch === 1.B)) -> (imm_m.io.sbtype - 4.S) ,
        ((control_m.io.Next_pc) === 2.U ) -> (imm_m.io.ujtype -4.S)

    ))


    program_counter.io.start := Next_sel_mux.asUInt
    inst_mem_m.io.addr := program_counter.io.pc






    branch_m.io.s1 := MuxCase ( 0 .S , Array (
    (branch_forwarding_m.io.forward_rs1 === 0.U) -> register_m.io.rs1 ,
    (branch_forwarding_m.io.forward_rs1 === 1.U) -> alu_m.io.out,
    (branch_forwarding_m.io.forward_rs1 === 2.U) -> pipe3_m.io.out3,
    (branch_forwarding_m.io.forward_rs1 === 3.U) ->  Mux( pipe4_m.io.out4(1) , pipe4_m.io.out1 , pipe4_m.io.out2) ,
    ))
    branch_m.io.s2 := MuxCase ( 0 .S , Array (
    (branch_forwarding_m.io.forward_rs2 === 0.U) -> register_m.io.rs2 ,
    (branch_forwarding_m.io.forward_rs2 === 1.U) -> alu_m.io.out,
    (branch_forwarding_m.io.forward_rs2 === 2.U) -> pipe3_m.io.out3,
    (branch_forwarding_m.io.forward_rs2 === 3.U) ->  Mux( pipe4_m.io.out4(1) , pipe4_m.io.out1 , pipe4_m.io.out2) ,
    ))
    branch_m.io.ctrl_b := control_m.io.M(0) 
    branch_m.io.func3 := Mux(pipe1_m.io.out2(6,0) === "b1100011".U , pipe1_m.io.out2(14,12) , 0.U)





    pipe2_m.io.in1 := control_m.io.operand_A
    pipe2_m.io.in1pc := pipe1_m.io.out1
    pipe2_m.io.in2 := register_m.io.rs1
    pipe2_m.io.in3 := register_m.io.rs2
    pipe2_m.io.in4 := imm_generator
    pipe2_m.io.in5 := pipe1_m.io.out2
    pipe2_m.io.in6 := pipe1_m.io.out2(11,7)
    pipe2_m.io.in7 := control_m.io.WB//Mux(hazard2.io.ctrl_sel,0.U,control_m.io.WB)
    pipe2_m.io.in8 := control_m.io.M//Mux(hazard2.io.ctrl_sel,0.U,control_m.io.M)
    pipe2_m.io.in9 := control_m.io.EX//Mux(hazard2.io.ctrl_sel,0.U,control_m.io.EX)




    alu_control_m.io.in := pipe2_m.io.out5
    alu_control_m.io.aluop := pipe2_m.io.out9(0)  


    forwarding_unit_m.io.rs1 := pipe2_m.io.out10
    forwarding_unit_m.io.rs2 := pipe2_m.io.out11
    forwarding_unit_m.io.Mem_rd := pipe3_m.io.out5 
    forwarding_unit_m.io.Mem_wb := pipe3_m.io.out6(0)
    forwarding_unit_m.io.Wb_rd := pipe4_m.io.out3
    forwarding_unit_m.io.Wb_wb := pipe4_m.io.out4
    



    val select1 = MuxCase ( 0 .S , Array (
    (forwarding_unit_m.io.forward_a === 0.U) -> pipe2_m.io.out2 ,
    (forwarding_unit_m.io.forward_a === 1.U) -> register_m.io.write_date,
    (forwarding_unit_m.io.forward_a === 2.U) -> pipe3_m.io.out3 ,
    ))
    val uj = MuxCase(0.S , Array( 
        (pipe2_m.io.out1 === 1.B) -> (pipe2_m.io.out1pc).asSInt ,
        (pipe2_m.io.out1 === 0.B) -> select1))

    val select2 = MuxCase ( 0 .S , Array (
    (forwarding_unit_m.io.forward_b === 0.U) -> pipe2_m.io.out3.asSInt ,
    (forwarding_unit_m.io.forward_b === 1.U) -> register_m.io.write_date,
    (forwarding_unit_m.io.forward_b === 2.U) -> pipe3_m.io.out3,
    ))
    val rs2_or_imm = MuxCase (0.S , Array (
    (pipe2_m.io.out9(1) === ("b0".U)) -> (select2),
    (pipe2_m.io.out9(1) === ("b1".U)) -> (pipe2_m.io.out4),
    ))



    alu_m.io.s1 :=  uj
    alu_m.io.s2 := rs2_or_imm
    alu_m.io.opcode := alu_control_m.io.contol



    pipe3_m.io.in3 := alu_m.io.out
    pipe3_m.io.in4 := select2
    pipe3_m.io.in5 := pipe2_m.io.out6
    pipe3_m.io.in6 := pipe2_m.io.out7
    pipe3_m.io.in7 := pipe2_m.io.out8 



    val delay_on_controlPins = RegInit(0.U(3.W))
    delay_on_controlPins := pipe3_m.io.out7



    data_mem_m.io.A := (pipe3_m.io.out3).asUInt
    data_mem_m.io.D := pipe3_m.io.out4
    data_mem_m.io.mem_Write := pipe3_m.io.out7(2)
    data_mem_m.io.mem_Read := pipe3_m.io.out7(1)  




    pipe4_m.io.in1 := data_mem_m.io.Dout //connect out of data mem
    pipe4_m.io.in2 := pipe3_m.io.out3    //connect alu out
    pipe4_m.io.in3 := pipe3_m.io.out5    // connect rd
    pipe4_m.io.in4 := pipe3_m.io.out6    // control wb




    register_m.io.reg_Write := pipe4_m.io.out4(0) === 1.U

    when (pipe4_m.io.out4(1)) {
        register_m.io.write_date := pipe4_m.io.out1
    } otherwise {
        register_m.io.write_date := pipe4_m.io.out2
    }
    register_m.io.M_rd := pipe4_m.io.out3
}

