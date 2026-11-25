package org.jetbrains.kotlin.resolve;

import kotlin.Metadata;
import org.jetbrains.kotlin.org.jline.reader.LineReader;

/* JADX INFO: compiled from: CleanableBindingContext.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0004À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/CleanableBindingContext;", "Lorg/jetbrains/kotlin/resolve/BindingContext;", LineReader.CLEAR, "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface CleanableBindingContext extends BindingContext {
    void clear();
}
