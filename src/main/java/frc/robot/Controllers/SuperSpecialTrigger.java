// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Controllers;

import static edu.wpi.first.wpilibj.util.ErrorMessages.requireNonNullParam;

import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants.ControllerConstants;
import frc.robot.Controllers.Controls.XboxButton;
import frc.robot.Controllers.Controls.XboxDPAD;
import frc.robot.Controllers.Controls.XboxTrigger;

import java.util.function.BooleanSupplier;

/**
 * This class provides an easy way to link commands to inputs.
 *
 * <p>It is very easy to link a button to a command. For instance, you could link the trigger button
 * of a joystick to a "score" command.
 *
 * <p>It is encouraged that teams write a subclass of Trigger if they want to have something unusual
 * (for instance, if they want to react to the user holding a button while the robot is reading a
 * certain sensor input). For this, they only have to write the {@link SuperSpecialTrigger#get()} method to get
 * the full functionality of the Trigger class.
 *
 * <p>This class is provided by the NewCommands VendorDep
 */
public class SuperSpecialTrigger implements BooleanSupplier {
  private final BooleanSupplier m_isActive;






  private final Controllers m_controller;
  private final int m_port;

  public SuperSpecialTrigger(Controllers controller, int port, BooleanSupplier isActive) {
    m_isActive = isActive;
    m_controller = controller;
    m_port = port;
  }

  public SuperSpecialTrigger add(String name, BooleanSupplier isActive) {
    return new SuperSpecialTrigger(m_controller, m_port, isActive);
  }

  public SuperSpecialTrigger add(String name, XboxButton xboxButton) {
    return add(name, () -> m_controller.get(m_port).getRawButton(Controls.buttonToButtonNumber(xboxButton)));
  }

  public SuperSpecialTrigger add(String name, XboxDPAD xboxDPAD) {
    return add(name, () -> m_controller.get(m_port).getPOV() == Controls.dpadToHatDegrees(xboxDPAD));
  }

  public SuperSpecialTrigger add(String name, XboxTrigger xboxTrigger) {
    return add(name, () -> m_controller.get(m_port).getRawAxis(Controls.triggerToTriggerNumber(xboxTrigger)) > ControllerConstants.kTriggerThreshold);
  }

  public Controllers finish() {
    return m_controller;
  }


















  /**
   * The Methods below have been modified from trigger to fit this version
   */





  /**
   * Composes this trigger with another trigger, returning a new trigger that is active when both
   * triggers are active.
   *
   * @param trigger the trigger to compose with
   * @return the trigger that is active when both triggers are active
   */
  public SuperSpecialTrigger and(SuperSpecialTrigger trigger) {
    return new SuperSpecialTrigger(m_controller, m_port, () -> get() && trigger.get());
  }

  /**
   * Composes this trigger with another trigger, returning a new trigger that is active when either
   * trigger is active.
   *
   * @param trigger the trigger to compose with
   * @return the trigger that is active when either trigger is active
   */
  public SuperSpecialTrigger or(SuperSpecialTrigger trigger) {
    return new SuperSpecialTrigger(m_controller, m_port, () -> get() || trigger.get());
  }

  /**
   * Creates a new trigger that is active when this trigger is inactive, i.e. that acts as the
   * negation of this trigger.
   *
   * @return the negated trigger
   */
  public SuperSpecialTrigger negate() {
    return new SuperSpecialTrigger(m_controller, m_port, () -> !get());
  }

  /**
   * Creates a new debounced trigger from this trigger - it will become active when this trigger has
   * been active for longer than the specified period.
   *
   * @param seconds The debounce period.
   * @return The debounced trigger (rising edges debounced only)
   */
  public SuperSpecialTrigger debounce(double seconds) {
    return debounce(seconds, Debouncer.DebounceType.kRising);
  }

  /**
   * Creates a new debounced trigger from this trigger - it will become active when this trigger has
   * been active for longer than the specified period.
   *
   * @param seconds The debounce period.
   * @param type The debounce type.
   * @return The debounced trigger.
   */
  public SuperSpecialTrigger debounce(double seconds, Debouncer.DebounceType type) {
    return new SuperSpecialTrigger(
      m_controller, m_port,
        new BooleanSupplier() {
          Debouncer m_debouncer = new Debouncer(seconds, type);

          @Override
          public boolean getAsBoolean() {
            return m_debouncer.calculate(get());
          }
        });
  }


































  /**
   * The Methods below are unmodified from trigger
   */

  /**
   * Returns whether or not the trigger is active.
   *
   * <p>This method will be called repeatedly a command is linked to the Trigger.
   *
   * <p>Functionally identical to {@link SuperSpecialTrigger#getAsBoolean()}.
   *
   * @return whether or not the trigger condition is active.
   */
  public boolean get() {
    return this.getAsBoolean();
  }

  /**
   * Returns whether or not the trigger is active.
   *
   * <p>This method will be called repeatedly a command is linked to the Trigger.
   *
   * <p>Functionally identical to {@link SuperSpecialTrigger#get()}.
   *
   * @return whether or not the trigger condition is active.
   */
  @Override
  public boolean getAsBoolean() {
    return m_isActive.getAsBoolean();
  }

  /**
   * Starts the given command whenever the trigger just becomes active.
   *
   * @param command the command to start
   * @param interruptible whether the command is interruptible
   * @return this trigger, so calls can be chained
   */
  public SuperSpecialTrigger whenActive(final Command command, boolean interruptible) {
    requireNonNullParam(command, "command", "whenActive");

    CommandScheduler.getInstance()
        .addButton(
            new Runnable() {
              private boolean m_pressedLast = get();

              @Override
              public void run() {
                boolean pressed = get();

                if (!m_pressedLast && pressed) {
                  command.schedule(interruptible);
                }

                m_pressedLast = pressed;
              }
            });

    return this;
  }

  /**
   * Starts the given command whenever the trigger just becomes active. The command is set to be
   * interruptible.
   *
   * @param command the command to start
   * @return this trigger, so calls can be chained
   */
  public SuperSpecialTrigger whenActive(final Command command) {
    return whenActive(command, true);
  }

  /**
   * Runs the given runnable whenever the trigger just becomes active.
   *
   * @param toRun the runnable to run
   * @param requirements the required subsystems
   * @return this trigger, so calls can be chained
   */
  public SuperSpecialTrigger whenActive(final Runnable toRun, Subsystem... requirements) {
    return whenActive(new InstantCommand(toRun, requirements));
  }

  /**
   * Constantly starts the given command while the button is held.
   *
   * <p>{@link Command#schedule(boolean)} will be called repeatedly while the trigger is active, and
   * will be canceled when the trigger becomes inactive.
   *
   * @param command the command to start
   * @param interruptible whether the command is interruptible
   * @return this trigger, so calls can be chained
   */
  public SuperSpecialTrigger whileActiveContinuous(final Command command, boolean interruptible) {
    requireNonNullParam(command, "command", "whileActiveContinuous");

    CommandScheduler.getInstance().
        addButton(
            new Runnable() {
              private boolean m_pressedLast = get();

              @Override
              public void run() {
                boolean pressed = get();

                if (pressed) {
                  command.schedule(interruptible);
                } else if (m_pressedLast) {
                  command.cancel();
                }

                m_pressedLast = pressed;
              }
            });
    return this;
  }

  /**
   * Constantly starts the given command while the button is held.
   *
   * <p>{@link Command#schedule(boolean)} will be called repeatedly while the trigger is active, and
   * will be canceled when the trigger becomes inactive. The command is set to be interruptible.
   *
   * @param command the command to start
   * @return this trigger, so calls can be chained
   */
  public SuperSpecialTrigger whileActiveContinuous(final Command command) {
    return whileActiveContinuous(command, true);
  }

  /**
   * Constantly runs the given runnable while the button is held.
   *
   * @param toRun the runnable to run
   * @param requirements the required subsystems
   * @return this trigger, so calls can be chained
   */
  public SuperSpecialTrigger whileActiveContinuous(final Runnable toRun, Subsystem... requirements) {
    return whileActiveContinuous(new InstantCommand(toRun, requirements));
  }

  /**
   * Starts the given command when the trigger initially becomes active, and ends it when it becomes
   * inactive, but does not re-start it in-between.
   *
   * @param command the command to start
   * @param interruptible whether the command is interruptible
   * @return this trigger, so calls can be chained
   */
  public SuperSpecialTrigger whileActiveOnce(final Command command, boolean interruptible) {
    requireNonNullParam(command, "command", "whileActiveOnce");

    CommandScheduler.getInstance()
        .addButton(
            new Runnable() {
              private boolean m_pressedLast = get();

              @Override
              public void run() {
                boolean pressed = get();

                if (!m_pressedLast && pressed) {
                  command.schedule(interruptible);
                } else if (m_pressedLast && !pressed) {
                  command.cancel();
                }

                m_pressedLast = pressed;
              }
            });
    return this;
  }

  /**
   * Starts the given command when the trigger initially becomes active, and ends it when it becomes
   * inactive, but does not re-start it in-between. The command is set to be interruptible.
   *
   * @param command the command to start
   * @return this trigger, so calls can be chained
   */
  public SuperSpecialTrigger whileActiveOnce(final Command command) {
    return whileActiveOnce(command, true);
  }

  /**
   * Starts the command when the trigger becomes inactive.
   *
   * @param command the command to start
   * @param interruptible whether the command is interruptible
   * @return this trigger, so calls can be chained
   */
  public SuperSpecialTrigger whenInactive(final Command command, boolean interruptible) {
    requireNonNullParam(command, "command", "whenInactive");

    CommandScheduler.getInstance()
        .addButton(
            new Runnable() {
              private boolean m_pressedLast = get();

              @Override
              public void run() {
                boolean pressed = get();

                if (m_pressedLast && !pressed) {
                  command.schedule(interruptible);
                }

                m_pressedLast = pressed;
              }
            });
    return this;
  }

  /**
   * Starts the command when the trigger becomes inactive. The command is set to be interruptible.
   *
   * @param command the command to start
   * @return this trigger, so calls can be chained
   */
  public SuperSpecialTrigger whenInactive(final Command command) {
    return whenInactive(command, true);
  }

  /**
   * Runs the given runnable when the trigger becomes inactive.
   *
   * @param toRun the runnable to run
   * @param requirements the required subsystems
   * @return this trigger, so calls can be chained
   */
  public SuperSpecialTrigger whenInactive(final Runnable toRun, Subsystem... requirements) {
    return whenInactive(new InstantCommand(toRun, requirements));
  }

  /**
   * Toggles a command when the trigger becomes active.
   *
   * @param command the command to toggle
   * @param interruptible whether the command is interruptible
   * @return this trigger, so calls can be chained
   */
  public SuperSpecialTrigger toggleWhenActive(final Command command, boolean interruptible) {
    requireNonNullParam(command, "command", "toggleWhenActive");

    CommandScheduler.getInstance()
        .addButton(
            new Runnable() {
              private boolean m_pressedLast = get();

              @Override
              public void run() {
                boolean pressed = get();

                if (!m_pressedLast && pressed) {
                  if (command.isScheduled()) {
                    command.cancel();
                  } else {
                    command.schedule(interruptible);
                  }
                }

                m_pressedLast = pressed;
              }
            });
    return this;
  }

  /**
   * Toggles a command when the trigger becomes active. The command is set to be interruptible.
   *
   * @param command the command to toggle
   * @return this trigger, so calls can be chained
   */
  public SuperSpecialTrigger toggleWhenActive(final Command command) {
    return toggleWhenActive(command, true);
  }

  /**
   * Cancels a command when the trigger becomes active.
   *
   * @param command the command to cancel
   * @return this trigger, so calls can be chained
   */
  public SuperSpecialTrigger cancelWhenActive(final Command command) {
    requireNonNullParam(command, "command", "cancelWhenActive");

    CommandScheduler.getInstance()
        .addButton(
            new Runnable() {
              private boolean m_pressedLast = get();

              @Override
              public void run() {
                boolean pressed = get();

                if (!m_pressedLast && pressed) {
                  command.cancel();
                }

                m_pressedLast = pressed;
              }
            });
    return this;
  }

  
}
