package org.billthefarmer.mididriver;

/* JADX INFO: loaded from: classes13.dex */
public class MidiDriver {
    private OnMidiStartListener listener;

    public interface OnMidiStartListener {
        void onMidiStart();
    }

    private native boolean init();

    private native boolean shutdown();

    public native int[] config();

    public native boolean setVolume(int i);

    public native boolean write(byte[] bArr);

    public void start() {
        OnMidiStartListener onMidiStartListener;
        if (init() && (onMidiStartListener = this.listener) != null) {
            onMidiStartListener.onMidiStart();
        }
    }

    public void queueEvent(byte[] bArr) {
        write(bArr);
    }

    public void stop() {
        shutdown();
    }

    public void setOnMidiStartListener(OnMidiStartListener onMidiStartListener) {
        this.listener = onMidiStartListener;
    }

    static {
        System.loadLibrary("midi");
    }
}
