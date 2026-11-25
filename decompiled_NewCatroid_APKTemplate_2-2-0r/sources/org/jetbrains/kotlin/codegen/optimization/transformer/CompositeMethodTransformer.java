package org.jetbrains.kotlin.codegen.optimization.transformer;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.org.objectweb.asm.tree.MethodNode;

/* JADX INFO: compiled from: CompositeMethodTransformer.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u0000 \r2\u00020\u0001:\u0001\rB\u001f\b\u0016\u0012\u0016\u0010\u0002\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u0003\"\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010\u0004B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005¢\u0006\u0002\u0010\u0006J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/transformer/CompositeMethodTransformer;", "Lorg/jetbrains/kotlin/codegen/optimization/transformer/MethodTransformer;", "transformers", "", "([Lorg/jetbrains/kotlin/codegen/optimization/transformer/MethodTransformer;)V", "", "(Ljava/util/List;)V", "transform", "", "internalClassName", "", "methodNode", "Lorg/jetbrains/org/objectweb/asm/tree/MethodNode;", "Companion", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public class CompositeMethodTransformer extends MethodTransformer {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final List<MethodTransformer> transformers;

    /* JADX WARN: Multi-variable type inference failed */
    public CompositeMethodTransformer(List<? extends MethodTransformer> transformers) {
        Intrinsics.checkNotNullParameter(transformers, "transformers");
        this.transformers = transformers;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public CompositeMethodTransformer(MethodTransformer... transformers) {
        this((List<? extends MethodTransformer>) ArraysKt.filterNotNull(transformers));
        Intrinsics.checkNotNullParameter(transformers, "transformers");
    }

    @Override // org.jetbrains.kotlin.codegen.optimization.transformer.MethodTransformer
    public void transform(String internalClassName, MethodNode methodNode) {
        Intrinsics.checkNotNullParameter(internalClassName, "internalClassName");
        Intrinsics.checkNotNullParameter(methodNode, "methodNode");
        Iterator<T> it = this.transformers.iterator();
        while (it.hasNext()) {
            ((MethodTransformer) it.next()).transform(internalClassName, methodNode);
        }
    }

    /* JADX INFO: compiled from: CompositeMethodTransformer.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J+\u0010\u0003\u001a\u00020\u00042\u001d\u0010\u0005\u001a\u0019\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0004\u0012\u00020\t0\u0006¢\u0006\u0002\b\nH\u0086\bø\u0001\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/transformer/CompositeMethodTransformer$Companion;", "", "()V", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/codegen/optimization/transformer/CompositeMethodTransformer;", "builder", "Lkotlin/Function1;", "", "Lorg/jetbrains/kotlin/codegen/optimization/transformer/MethodTransformer;", "", "Lkotlin/ExtensionFunctionType;", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final CompositeMethodTransformer build(Function1<? super List<MethodTransformer>, Unit> builder) {
            Intrinsics.checkNotNullParameter(builder, "builder");
            ArrayList arrayList = new ArrayList();
            builder.invoke(arrayList);
            return new CompositeMethodTransformer(arrayList);
        }
    }
}
