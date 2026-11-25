package name.antonsmirnov.firmata.wrapper;

import java.util.Date;
import java.util.GregorianCalendar;

/* JADX INFO: loaded from: classes12.dex */
public class DateMessagePropertyManager extends MessagePropertyManager<Date> {
    private static final String KEY = "date";

    public DateMessagePropertyManager() {
        super("date");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // name.antonsmirnov.firmata.wrapper.MessagePropertyManager
    public Date createProperty() {
        return GregorianCalendar.getInstance().getTime();
    }
}
