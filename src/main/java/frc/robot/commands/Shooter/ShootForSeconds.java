package frc.robot.commands.Shooter;


import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class ShootForSeconds extends CommandBase {
    private final Shooter shooter;
    private final double seconds;
    private boolean finished = false;
    private Timer timer;

    public ShootForSeconds(Shooter subsystem, double seconds) {
        shooter = subsystem;
        this.seconds = seconds;
        timer = new Timer();
        addRequirements(subsystem);
    }

    @Override
    public void initialize() {
        timer.start();
        while (timer.get() <= seconds) {
            shooter.shootHigh();
        }
        // shooter.disable();
        finished = true;
    }
    @Override
    public boolean isFinished() {
        return finished;
    }
    // @Override
    // public void end(boolean interrupted) {
    // //   m_drive.setMaxOutput(1);
    // }
}
