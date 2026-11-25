package name.antonsmirnov.firmata.wrapper;

/* JADX INFO: loaded from: classes12.dex */
public class DirectionMessageFilter implements IMessageFilter {
    private DirectionMessagePropertyManager propertyManager;

    public DirectionMessageFilter(DirectionMessagePropertyManager directionMessagePropertyManager) {
        this.propertyManager = directionMessagePropertyManager;
    }

    @Override // name.antonsmirnov.firmata.wrapper.IMessageFilter
    public boolean isAllowed(MessageWithProperties messageWithProperties) {
        return this.propertyManager.get(messageWithProperties).equals(Boolean.valueOf(this.propertyManager.isIncoming()));
    }
}
