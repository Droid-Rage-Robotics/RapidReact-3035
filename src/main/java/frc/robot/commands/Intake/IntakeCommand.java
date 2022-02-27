package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IndexerSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakeCommand extends CommandBase{

    private IntakeSubsystem intake; 
    private IntakeType intakeType;

    
    public IntakeCommand(IntakeSubsystem intake, IntakeType intakeType){
        this.intake = intake;
        this.intakeType = intakeType;
   
    }

    @Override 

    public void initialize (){
        switch(intakeType){
            case OUTTAKE:
            intake.outtakeBalls(); 
            //Indexer.setIndexState(IndexerSubsystem.IndexState.EXTAKING); 
            break;
            
            case INTAKE:
            intake.intakeBalls(); 
            //Index.setIndexState(IndexerSubsystem.IndexState.INTAKING);
            break;
        }
    }

    public enum IntakeType{
        OUTTAKE,
        INTAKE
    }

}