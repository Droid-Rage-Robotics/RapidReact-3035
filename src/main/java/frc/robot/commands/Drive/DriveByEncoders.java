// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Drive;

import frc.robot.subsystems.Drive;
// import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class DriveByEncoders extends SequentialCommandGroup {

  /**
   * Creates a new DriveDistance.
   *
   * @param seconds The number of seconds the robot will drive
   * @param speed The speed at which the robot will drive
   * @param drive The drive subsystem on which this command will run
   */
  public DriveByEncoders(double leftInches, double rightInches, Drive drive) {
    addCommands(
      new InstantCommand(() -> drive.encoderDrive(leftInches, rightInches))
    );
    addRequirements(drive);
  }

  public DriveByEncoders(
    double leftInches, double rightInches, 
    double leftSpeed, double rightSpeed, 
    Drive drive) {
    addCommands(
      new InstantCommand(() -> drive.encoderDrive(leftInches, rightInches, leftSpeed, rightSpeed))
    );
    addRequirements(drive);
  }
  // public DriveByTime(double seconds, Drive drive) {
  //   DriveByTime(seconds, drive);
  // }
}
