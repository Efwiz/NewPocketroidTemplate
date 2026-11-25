package name.antonsmirnov.firmata.wrapper;

/* JADX INFO: loaded from: classes12.dex */
public interface IMessagePropertyManager<ConcretePropertyClass> {
    ConcretePropertyClass get(MessageWithProperties messageWithProperties);

    void set(MessageWithProperties messageWithProperties);
}
