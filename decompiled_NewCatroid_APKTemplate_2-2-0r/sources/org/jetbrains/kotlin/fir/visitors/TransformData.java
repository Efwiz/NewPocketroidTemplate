package org.jetbrains.kotlin.fir.visitors;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;

/* JADX INFO: compiled from: FirTransformerUtil.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00012\u00020\u0002:\u0002\u0004\u0005B\u0007\b\u0004¢\u0006\u0002\u0010\u0003\u0082\u0001\u0002\u0006\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/fir/visitors/TransformData;", "D", "", "()V", "Data", "Nothing", "Lorg/jetbrains/kotlin/fir/visitors/TransformData$Data;", "Lorg/jetbrains/kotlin/fir/visitors/TransformData$Nothing;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class TransformData<D> {
    public /* synthetic */ TransformData(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    /* JADX INFO: compiled from: FirTransformerUtil.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000*\u0004\b\u0001\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00028\u0001¢\u0006\u0002\u0010\u0004R\u0013\u0010\u0003\u001a\u00028\u0001¢\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\u0005\u0010\u0006¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/fir/visitors/TransformData$Data;", "D", "Lorg/jetbrains/kotlin/fir/visitors/TransformData;", "value", ReifiedTypeInliner.pluginIntrinsicsMarkerSignature, "getValue", "()Ljava/lang/Object;", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Data<D> extends TransformData<D> {
        private final D value;

        public Data(D d) {
            super(null);
            this.value = d;
        }

        public final D getValue() {
            return this.value;
        }
    }

    private TransformData() {
    }

    /* JADX INFO: compiled from: FirTransformerUtil.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0001\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003¨\u0006\u0004"}, d2 = {"Lorg/jetbrains/kotlin/fir/visitors/TransformData$Nothing;", "Lorg/jetbrains/kotlin/fir/visitors/TransformData;", "", "()V", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Nothing extends TransformData {
        public static final Nothing INSTANCE = new Nothing();

        private Nothing() {
            super(null);
        }
    }
}
