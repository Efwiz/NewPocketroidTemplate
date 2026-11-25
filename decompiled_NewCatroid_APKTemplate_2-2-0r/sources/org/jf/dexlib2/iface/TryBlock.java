package org.jf.dexlib2.iface;

import java.util.List;
import org.jf.dexlib2.iface.ExceptionHandler;

/* JADX INFO: loaded from: classes6.dex */
public interface TryBlock<EH extends ExceptionHandler> {
    boolean equals(Object obj);

    int getCodeUnitCount();

    List<? extends EH> getExceptionHandlers();

    int getStartCodeAddress();
}
