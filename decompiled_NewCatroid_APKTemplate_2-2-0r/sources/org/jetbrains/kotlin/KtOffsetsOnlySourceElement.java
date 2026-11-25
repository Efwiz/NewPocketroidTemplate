package org.jetbrains.kotlin;

import kotlin.Metadata;

/* JADX INFO: compiled from: KtSourceElement.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005R\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/KtOffsetsOnlySourceElement;", "Lorg/jetbrains/kotlin/AbstractKtSourceElement;", "startOffset", "", "endOffset", "(II)V", "getEndOffset", "()I", "getStartOffset", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KtOffsetsOnlySourceElement extends AbstractKtSourceElement {
    private final int endOffset;
    private final int startOffset;

    @Override // org.jetbrains.kotlin.AbstractKtSourceElement
    public int getStartOffset() {
        return this.startOffset;
    }

    @Override // org.jetbrains.kotlin.AbstractKtSourceElement
    public int getEndOffset() {
        return this.endOffset;
    }

    public KtOffsetsOnlySourceElement(int i, int i2) {
        super(null);
        this.startOffset = i;
        this.endOffset = i2;
    }
}
