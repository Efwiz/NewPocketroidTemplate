package name.antonsmirnov.firmata.message.factory.arduino;

import name.antonsmirnov.firmata.message.factory.BoardMessageFactory;

/* JADX INFO: loaded from: classes12.dex */
public class Mega extends BoardMessageFactory {
    public static final int MAX_PIN = 54;

    public Mega() {
        super(0, 54, arrayFromTo(0, 15), union(arrayFromTo(2, 13), arrayFromTo(44, 46)));
    }
}
