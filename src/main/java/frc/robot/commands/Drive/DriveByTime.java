// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Drive;

import frc.robot.subsystems.Drive;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class DriveByTime extends SequentialCommandGroup {

  /**
   * Creates a new DriveDistance.
   *
   * @param seconds The number of seconds the robot will drive
   * @param speed The speed at which the robot will drive
   * @param drive The drive subsystem on which this command will run
   */
  public DriveByTime(double seconds, double speed, Drive drive) {
    addCommands(
      new InstantCommand(() -> drive.arcadeDrive(speed, 0)),
      new WaitCommand(seconds),
      new InstantCommand(() -> drive.arcadeDrive(0, 0))
    );
    addRequirements(drive);
  }
}
