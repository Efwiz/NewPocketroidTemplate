package name.antonsmirnov.firmata.wrapper;

/* JADX INFO: loaded from: classes12.dex */
public abstract class MessagePropertyManager<ConcretePropertyClass> implements IMessagePropertyManager<ConcretePropertyClass> {
    private String key;

    protected abstract ConcretePropertyClass createProperty();

    public MessagePropertyManager(String str) {
        this.key = str;
    }

    @Override // name.antonsmirnov.firmata.wrapper.IMessagePropertyManager
    public ConcretePropertyClass get(MessageWithProperties messageWithProperties) {
        return (ConcretePropertyClass) messageWithProperties.getProperty(this.key);
    }

    @Override // name.antonsmirnov.firmata.wrapper.IMessagePropertyManager
    public void set(MessageWithProperties messageWithProperties) {
        messageWithProperties.setProperty(this.key, createProperty());
    }
}
