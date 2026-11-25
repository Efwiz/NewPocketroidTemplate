package name.antonsmirnov.firmata.message.factory.arduino;

import name.antonsmirnov.firmata.message.factory.BoardMessageFactory;

/* JADX INFO: loaded from: classes12.dex */
public class Nano extends BoardMessageFactory {
    public static final int MAX_PIN = 13;

    public Nano() {
        super(0, 13, arrayFromTo(0, 7), new int[]{3, 5, 6, 9, 10, 11});
    }
}
