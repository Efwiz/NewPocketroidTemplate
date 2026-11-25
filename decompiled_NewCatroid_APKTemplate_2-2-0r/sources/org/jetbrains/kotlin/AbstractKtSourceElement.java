package org.jetbrains.kotlin;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: KtSourceElement.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B\u0007\b\u0004¢\u0006\u0002\u0010\u0002J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\f\u001a\u00020\u0004H\u0016R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\u0006\u0082\u0001\u0002\r\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/AbstractKtSourceElement;", "", "()V", "endOffset", "", "getEndOffset", "()I", "startOffset", "getStartOffset", Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "Lorg/jetbrains/kotlin/KtOffsetsOnlySourceElement;", "Lorg/jetbrains/kotlin/KtSourceElement;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractKtSourceElement {
    public /* synthetic */ AbstractKtSourceElement(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract int getEndOffset();

    public abstract int getStartOffset();

    private AbstractKtSourceElement() {
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AbstractKtSourceElement)) {
            return false;
        }
        AbstractKtSourceElement abstractKtSourceElement = (AbstractKtSourceElement) other;
        return getStartOffset() == abstractKtSourceElement.getStartOffset() && getEndOffset() == abstractKtSourceElement.getEndOffset();
    }

    public int hashCode() {
        return (getStartOffset() * 31) + getEndOffset();
    }
}
