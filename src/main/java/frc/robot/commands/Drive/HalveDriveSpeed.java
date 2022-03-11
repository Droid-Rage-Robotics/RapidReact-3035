// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Drive;

import frc.robot.subsystems.Drive;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class HalveDriveSpeed extends CommandBase {
  private final Drive m_drive;

  public HalveDriveSpeed(Drive drive) {
    m_drive = drive;
  }

  @Override
  public void initialize() {
    m_drive.setMaxOutput(0.5);
  }

  @Override
  public void end(boolean interrupted) {
    m_drive.setMaxOutput(1);
  }
}
