package org.eclipse.jgit.logging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

/* JADX INFO: loaded from: classes13.dex */
public class PerformanceLogContext {
    private static final PerformanceLogContext INSTANCE = new PerformanceLogContext();
    private static final ThreadLocal<List<PerformanceLogRecord>> eventRecords = ThreadLocal.withInitial(new Supplier() { // from class: org.eclipse.jgit.logging.PerformanceLogContext$$ExternalSyntheticLambda0
        @Override // java.util.function.Supplier
        public final Object get() {
            return PerformanceLogContext.$r8$lambda$aw4WkQINtNlXlsGxYEbzatsgkDc();
        }
    });

    public static /* synthetic */ ArrayList $r8$lambda$aw4WkQINtNlXlsGxYEbzatsgkDc() {
        return new ArrayList();
    }

    private PerformanceLogContext() {
    }

    public static PerformanceLogContext getInstance() {
        return INSTANCE;
    }

    public List<PerformanceLogRecord> getEventRecords() {
        return Collections.unmodifiableList(eventRecords.get());
    }

    public void addEvent(PerformanceLogRecord performanceLogRecord) {
        eventRecords.get().add(performanceLogRecord);
    }

    public void cleanEvents() {
        eventRecords.remove();
    }
}
