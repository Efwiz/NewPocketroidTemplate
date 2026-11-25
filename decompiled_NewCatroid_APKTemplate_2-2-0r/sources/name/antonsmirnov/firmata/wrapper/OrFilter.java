package name.antonsmirnov.firmata.wrapper;

/* JADX INFO: loaded from: classes12.dex */
public class OrFilter implements IMessageFilter {
    private IMessageFilter[] filters;

    public OrFilter(IMessageFilter... iMessageFilterArr) {
        this.filters = iMessageFilterArr;
    }

    @Override // name.antonsmirnov.firmata.wrapper.IMessageFilter
    public boolean isAllowed(MessageWithProperties messageWithProperties) {
        for (IMessageFilter iMessageFilter : this.filters) {
            if (iMessageFilter.isAllowed(messageWithProperties)) {
                return true;
            }
        }
        return false;
    }
}
