package org.usfirst.frc.team4373.robot.input.hid;

import edu.wpi.first.wpilibj.DriverStation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

public abstract class HIDDevice {
    String fileName;
    String deviceName;
    ArrayList<Action> actionLog;

    protected enum ActorType {
        Axis, Button
    }
    
    protected static class Action {
        public ActorType actorType;
        public int actorID;
        public double actorValue;
        protected Action(ActorType actorType, int actorID, double actorValue) {
            this.actorType = actorType;
            this.actorID = actorID;
            this.actorValue = actorValue;
        }
    }

    private synchronized void logAction(Action action) {
        actionLog.add(action);
    }

    protected HIDDevice(String fileName, String deviceName) {
      this.fileName = fileName;
      this.deviceName = deviceName;
    }

    private synchronized void serializeActions() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
            for (Action action: actionLog) {
                String actionLine = getSerializedActorType(action.actorType) + ":" + action.actorID + ":" + action.actorValue;
                writer.write(actionLine);
            }
            writer.close();
        } catch (Exception e) {
            DriverStation.reportError(e.getMessage(), true);
        }
    }

    private synchronized int getSerializedActorType(ActorType actorType) {
        int value = -1;
        switch (actorType) {
        case Axis:
            value = 0;
            break;
        case Button:
            value = 1;
            break;
        default:
            break;
        }
        return value;
    }

    public void finalize() {
      serializeActions();
    }

    public void onFinish() {
      finalize();
    }

}
