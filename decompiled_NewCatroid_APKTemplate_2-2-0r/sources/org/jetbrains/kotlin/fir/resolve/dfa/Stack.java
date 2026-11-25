package org.jetbrains.kotlin.fir.resolve.dfa;

import androidx.exifinterface.media.ExifInterface;
import java.util.List;
import kotlin.Metadata;
import org.jetbrains.kotlin.cli.common.arguments.DevModeOverwritingStrategies;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;

/* JADX INFO: compiled from: Stack.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\b&\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u000e\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\tH&J\r\u0010\n\u001a\u00028\u0000H&¢\u0006\u0002\u0010\u000bJ\u0015\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00028\u0000H&¢\u0006\u0002\u0010\u000fJ\b\u0010\u0010\u001a\u00020\rH&J\r\u0010\u0011\u001a\u00028\u0000H&¢\u0006\u0002\u0010\u000bR\u0012\u0010\u0004\u001a\u00020\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/dfa/Stack;", ExifInterface.GPS_DIRECTION_TRUE, "", "()V", "size", "", "getSize", "()I", DevModeOverwritingStrategies.ALL, "", "pop", "()Ljava/lang/Object;", "push", "", "value", ReifiedTypeInliner.pluginIntrinsicsMarkerSignature, "reset", "top", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class Stack<T> {
    public abstract List<T> all();

    public abstract int getSize();

    public abstract T pop();

    public abstract void push(T value);

    public abstract void reset();

    public abstract T top();
}
