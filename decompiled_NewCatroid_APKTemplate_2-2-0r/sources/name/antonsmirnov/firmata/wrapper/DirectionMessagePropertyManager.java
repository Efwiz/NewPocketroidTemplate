package name.antonsmirnov.firmata.wrapper;

/* JADX INFO: loaded from: classes12.dex */
public class DirectionMessagePropertyManager extends MessagePropertyManager<Boolean> {
    private static final String KEY = "isIncoming";
    private boolean isIncoming;

    public boolean isIncoming() {
        return this.isIncoming;
    }

    public DirectionMessagePropertyManager(boolean z) {
        super(KEY);
        this.isIncoming = z;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // name.antonsmirnov.firmata.wrapper.MessagePropertyManager
    public Boolean createProperty() {
        return Boolean.valueOf(this.isIncoming);
    }
}
