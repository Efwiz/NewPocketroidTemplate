package org.catrobat.catroid.devices.mindstorms.nxt;

import android.util.Log;
import org.catrobat.catroid.devices.mindstorms.MindstormsConnection;
import org.catrobat.catroid.devices.mindstorms.MindstormsException;
import org.catrobat.catroid.devices.mindstorms.MindstormsMotor;
import org.eclipse.jdt.internal.compiler.codegen.Opcodes;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/mindstorms/nxt/NXTMotor.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class NXTMotor implements MindstormsMotor {
    private static final String TAG = "NXTMotor";
    private MindstormsConnection connection;
    private int port;

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/mindstorms/nxt/NXTMotor$MotorMode.class
      classes14.dex
     */
    public static class MotorMode {
        public static final byte BREAK = 2;
        public static final byte ON = 1;
        public static final byte REGULATED = 4;
    }

    public NXTMotor(int port, MindstormsConnection connection) {
        this.port = port;
        this.connection = connection;
    }

    @Override // org.catrobat.catroid.devices.mindstorms.MindstormsMotor
    public void stop() {
        OutputState outputState = new OutputState();
        outputState.setSpeed(0);
        outputState.mode = (byte) 7;
        outputState.regulation = MotorRegulation.SPEED;
        outputState.turnRatio = (byte) 100;
        outputState.runState = MotorRunState.RUNNING;
        outputState.tachoLimit = 0;
        setOutputState(outputState, false);
    }

    private void setOutputState(OutputState state, boolean reply) {
        try {
            trySetOutputState(state, reply);
        } catch (MindstormsException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void trySetOutputState(OutputState state, boolean reply) throws MindstormsException {
        Command command = new Command(CommandType.DIRECT_COMMAND, CommandByte.SET_OUTPUT_STATE, false);
        command.append((byte) this.port);
        command.append(state.getSpeed());
        command.append(state.mode);
        command.append(state.regulation.getByte());
        command.append(state.turnRatio);
        command.append(state.runState.getByte());
        command.append(state.tachoLimit);
        command.append((byte) 0);
        if (reply) {
            this.connection.sendAndReceive(command);
        } else {
            this.connection.send(command);
        }
    }

    @Override // org.catrobat.catroid.devices.mindstorms.MindstormsMotor
    public void move(int speed) {
        move(speed, 0, false);
    }

    @Override // org.catrobat.catroid.devices.mindstorms.MindstormsMotor
    public void move(int speed, int degrees) {
        move(speed, degrees, false);
    }

    @Override // org.catrobat.catroid.devices.mindstorms.MindstormsMotor
    public void move(int speed, int degrees, boolean reply) {
        OutputState outputState = new OutputState();
        outputState.setSpeed(speed);
        outputState.mode = (byte) 7;
        outputState.regulation = MotorRegulation.SPEED;
        outputState.turnRatio = (byte) 100;
        outputState.runState = MotorRunState.RUNNING;
        outputState.tachoLimit = degrees;
        setOutputState(outputState, reply);
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/mindstorms/nxt/NXTMotor$OutputState.class
      classes14.dex
     */
    private static class OutputState {
        public byte mode;
        public MotorRegulation regulation;
        public MotorRunState runState;
        private byte speed;
        public int tachoLimit;
        public byte turnRatio;

        private OutputState() {
        }

        public void setSpeed(int speed) {
            if (speed > 100) {
                this.speed = (byte) 100;
                return;
            }
            if (speed < -100) {
                this.speed = Opcodes.OPC_ifge;
                return;
            }
            byte b = this.turnRatio;
            if (b > 100) {
                this.turnRatio = (byte) 100;
            } else if (b < -100) {
                this.turnRatio = (byte) 100;
            } else {
                this.speed = (byte) speed;
            }
        }

        public byte getSpeed() {
            return this.speed;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/mindstorms/nxt/NXTMotor$MotorRegulation.class
      classes14.dex
     */
    public enum MotorRegulation {
        IDLE(0),
        SPEED(1),
        SYNC(2);

        private int motorRegulationValue;

        MotorRegulation(int motorRegulationValue) {
            this.motorRegulationValue = motorRegulationValue;
        }

        public byte getByte() {
            return (byte) this.motorRegulationValue;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/devices/mindstorms/nxt/NXTMotor$MotorRunState.class
      classes14.dex
     */
    public enum MotorRunState {
        IDLE(0),
        RAMP_UP(16),
        RUNNING(32),
        RAMP_DOWN(64);

        private int motorRunStateValue;

        MotorRunState(int motorRunStateValue) {
            this.motorRunStateValue = motorRunStateValue;
        }

        public byte getByte() {
            return (byte) this.motorRunStateValue;
        }
    }
}
