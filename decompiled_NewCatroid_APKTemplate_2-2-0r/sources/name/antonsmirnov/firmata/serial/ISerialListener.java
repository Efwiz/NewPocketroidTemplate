package name.antonsmirnov.firmata.serial;

/* JADX INFO: loaded from: classes12.dex */
public interface ISerialListener<ConcreteSerialImpl> {
    void onDataReceived(ConcreteSerialImpl concreteserialimpl);

    void onException(Throwable th);
}
