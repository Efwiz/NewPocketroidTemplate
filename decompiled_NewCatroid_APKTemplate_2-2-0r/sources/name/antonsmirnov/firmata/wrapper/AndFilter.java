package name.antonsmirnov.firmata.wrapper;

/* JADX INFO: loaded from: classes12.dex */
public class AndFilter implements IMessageFilter {
    private IMessageFilter[] filters;

    public AndFilter(IMessageFilter... iMessageFilterArr) {
        this.filters = iMessageFilterArr;
    }

    @Override // name.antonsmirnov.firmata.wrapper.IMessageFilter
    public boolean isAllowed(MessageWithProperties messageWithProperties) {
        for (IMessageFilter iMessageFilter : this.filters) {
            if (!iMessageFilter.isAllowed(messageWithProperties)) {
                return false;
            }
        }
        return true;
    }
}
