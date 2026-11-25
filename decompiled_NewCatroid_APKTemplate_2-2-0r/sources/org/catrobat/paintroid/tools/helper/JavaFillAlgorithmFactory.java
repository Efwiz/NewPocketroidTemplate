package org.catrobat.paintroid.tools.helper;

import kotlin.Metadata;

/* JADX INFO: compiled from: JavaFillAlgorithmFactory.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016¨\u0006\u0005"}, d2 = {"Lorg/catrobat/paintroid/tools/helper/JavaFillAlgorithmFactory;", "Lorg/catrobat/paintroid/tools/helper/FillAlgorithmFactory;", "()V", "createFillAlgorithm", "Lorg/catrobat/paintroid/tools/helper/FillAlgorithm;", "Paintroid_signedRelease"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class JavaFillAlgorithmFactory implements FillAlgorithmFactory {
    @Override // org.catrobat.paintroid.tools.helper.FillAlgorithmFactory
    public FillAlgorithm createFillAlgorithm() {
        return new JavaFillAlgorithm();
    }
}
