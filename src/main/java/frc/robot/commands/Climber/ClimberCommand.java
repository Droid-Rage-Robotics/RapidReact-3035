package frc.robot.commands.Climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimberSubsystem;

public class ClimberCommand extends CommandBase {
    public ClimberSubsystem climber;
    public ClimberMotionType ClimberMotionType;

    public ClimberCommand(ClimberSubsystem climber, ClimberMotionType climberMotionType){
        this.climber = climber;
        this.ClimberMotionType = climberMotionType;
    }

    @Override
    public void execute(){
        switch(ClimberMotionType){
            case EXTEND:
            climber.setClimberDown();
            case RETRACT:
            climber.setClimberDown();
        }
    }

    @Override
    public void end(boolean interrupted){
        climber.setPower(0);
        System.out.println("climber End:" + climber.getPosition());
    }

    public enum ClimberMotionType{
        EXTEND,
        RETRACT,
        LOCK
    }
}