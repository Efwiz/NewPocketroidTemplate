package com.android.tools.r8.internal;

import com.android.tools.r8.references.Reference;
import com.android.tools.r8.references.TypeReference;
import com.android.tools.r8.retrace.RetraceClassElement;
import com.android.tools.r8.retrace.RetraceTypeElement;
import com.android.tools.r8.retrace.RetraceTypeResult;
import com.android.tools.r8.retrace.RetracedTypeReference;
import com.android.tools.r8.retrace.Retracer;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class KT implements RetraceTypeResult {
    static final /* synthetic */ boolean d = true;
    private final TypeReference a;
    private final List b;
    private final Retracer c;

    private KT(TypeReference typeReference, List list, Retracer retracer) {
        this.a = typeReference;
        this.b = list;
        this.c = retracer;
    }

    static KT a(TypeReference typeReference, Retracer retracer) {
        return new KT(typeReference, b(typeReference, retracer), retracer);
    }

    private static List b(TypeReference typeReference, Retracer retracer) {
        if (typeReference == null) {
            return Collections.emptyList();
        }
        if (typeReference.isPrimitive()) {
            return Collections.singletonList(XT.a(typeReference));
        }
        if (typeReference.isArray()) {
            final int dimensions = typeReference.asArray().getDimensions();
            return com.android.tools.r8.utils.V1.a((Collection) b(typeReference.asArray().getBaseType(), retracer), new Function() { // from class: com.android.tools.r8.internal.KT$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return KT.a(dimensions, (RetracedTypeReference) obj);
                }
            });
        }
        if (d || typeReference.isClass()) {
            return (List) retracer.retraceClass(typeReference.asClass()).stream().map(new Function() { // from class: com.android.tools.r8.internal.KT$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return KT.a((RetraceClassElement) obj);
                }
            }).collect(Collectors.toList());
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.retrace.RetraceResult
    public final void forEach(Consumer consumer) {
        stream().forEach(consumer);
    }

    @Override // com.android.tools.r8.retrace.RetraceResult
    public final boolean isAmbiguous() {
        return this.b.size() > 1;
    }

    @Override // com.android.tools.r8.retrace.RetraceResult
    public final boolean isEmpty() {
        return this.b.size() == 0;
    }

    @Override // com.android.tools.r8.retrace.RetraceResult
    public final Stream stream() {
        return com.android.tools.r8.utils.V1.a((Collection) this.b, new Function() { // from class: com.android.tools.r8.internal.KT$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a((RetracedTypeReference) obj);
            }
        }).stream();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ RetracedTypeReference a(int i, RetracedTypeReference retracedTypeReference) {
        return XT.a(Reference.array(retracedTypeReference.getTypeReference(), i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ RetracedTypeReference a(RetraceClassElement retraceClassElement) {
        return retraceClassElement.getRetracedClass().getRetracedType();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ RetraceTypeElement a(RetracedTypeReference retracedTypeReference) {
        return new JT(this, retracedTypeReference);
    }
}
