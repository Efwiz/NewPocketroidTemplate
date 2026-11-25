package org.jetbrains.kotlin.fir;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.fir.FirTargetElement;

/* JADX INFO: compiled from: FirAbstractTarget.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\u0002\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u000f\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u0015\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u000bR\u0018\u0010\u0007\u001a\u00028\u0000X¤\u000e¢\u0006\f\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u0016\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u000e\u001a\u00028\u00008F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\t¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/fir/FirAbstractTarget;", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirTargetElement;", "Lorg/jetbrains/kotlin/fir/FirTarget;", "labelName", "", "(Ljava/lang/String;)V", "_labeledElement", "get_labeledElement", "()Lorg/jetbrains/kotlin/fir/FirTargetElement;", "set_labeledElement", "(Lorg/jetbrains/kotlin/fir/FirTargetElement;)V", "getLabelName", "()Ljava/lang/String;", "labeledElement", "getLabeledElement", "bind", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirAbstractTarget<E extends FirTargetElement> implements FirTarget<E> {
    private final String labelName;

    protected abstract E get_labeledElement();

    protected abstract void set_labeledElement(E e);

    public FirAbstractTarget(String str) {
        this.labelName = str;
    }

    @Override // org.jetbrains.kotlin.fir.FirTarget
    public String getLabelName() {
        return this.labelName;
    }

    @Override // org.jetbrains.kotlin.fir.FirTarget
    public final E getLabeledElement() {
        return (E) get_labeledElement();
    }

    @Override // org.jetbrains.kotlin.fir.FirTarget
    public void bind(E element) {
        Intrinsics.checkNotNullParameter(element, "element");
        set_labeledElement(element);
    }
}
