package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.xrp.XRPRangefinder;

public class AutonomousBumpAndMove extends Command {
    private boolean isFinished;
    private STATES currentState;
    private final XRPRangefinder rangefinder;
    private final Drivetrain drivetrain;

    private enum STATES {
        STOP,
        FORWARD,
        BACKUP
    }

    public AutonomousBumpAndMove(Drivetrain drivetrain, XRPRangefinder rangefinder){
        this.rangefinder = rangefinder;
        this.drivetrain = drivetrain;
        this.currentState = STATES.STOP;
        addRequirements(drivetrain);
    }


    @Override
    public void initialize() {
        isFinished = false;
        this.currentState = STATES.FORWARD;
    }


    @Override
    public void execute(){

        double distance = rangefinder.getDistanceInches();
        SmartDashboard.putNumber("Command_rangeInInches", distance);
        SmartDashboard.putString("Command_State", currentState.toString());

        if (distance <= 2.0){
            currentState = STATES.BACKUP;
        } else if (distance >= 15 ) {
            currentState = STATES.FORWARD;
        } else {
            // do nothing
        }


        switch (currentState) {
            case FORWARD:
                drivetrain.arcadeDrive(1.0, 0.0);
                break;

            case BACKUP:
                drivetrain.arcadeDrive(-1.0, 0.25);

                break;

            case STOP:
                break;

            default:
                isFinished = true;
        }
    }


    @Override
    public void end(boolean interrupted){

    }


    @Override
    public boolean isFinished(){
        return isFinished;
    }
}
