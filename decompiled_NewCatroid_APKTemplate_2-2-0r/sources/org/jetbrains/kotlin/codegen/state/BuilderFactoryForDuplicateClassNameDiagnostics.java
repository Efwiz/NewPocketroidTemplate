package org.jetbrains.kotlin.codegen.state;

import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.ClassBuilderFactory;
import org.jetbrains.kotlin.codegen.ClassNameCollectionClassBuilderFactory;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.renderer.DescriptorRenderer;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOrigin;

/* JADX INFO: compiled from: BuilderFactoryForDuplicateClassNameDiagnostics.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0018\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\nH\u0014J)\u0010\u000f\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\t2\u0012\u0010\u0010\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0\u0011\"\u00020\nH\u0002¢\u0006\u0002\u0010\u0012R\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/codegen/state/BuilderFactoryForDuplicateClassNameDiagnostics;", "Lorg/jetbrains/kotlin/codegen/ClassNameCollectionClassBuilderFactory;", "builderFactory", "Lorg/jetbrains/kotlin/codegen/ClassBuilderFactory;", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", "(Lorg/jetbrains/kotlin/codegen/ClassBuilderFactory;Lorg/jetbrains/kotlin/codegen/state/GenerationState;)V", "className", "Ljava/util/concurrent/ConcurrentHashMap;", "", "Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOrigin;", "handleClashingNames", "", "internalName", "origin", "reportError", "another", "", "(Ljava/lang/String;[Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOrigin;)V", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BuilderFactoryForDuplicateClassNameDiagnostics extends ClassNameCollectionClassBuilderFactory {
    private final ConcurrentHashMap<String, JvmDeclarationOrigin> className;
    private final GenerationState state;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BuilderFactoryForDuplicateClassNameDiagnostics(ClassBuilderFactory builderFactory, GenerationState state) {
        super(builderFactory);
        Intrinsics.checkNotNullParameter(builderFactory, "builderFactory");
        Intrinsics.checkNotNullParameter(state, "state");
        this.state = state;
        this.className = new ConcurrentHashMap<>();
    }

    @Override // org.jetbrains.kotlin.codegen.ClassNameCollectionClassBuilderFactory
    protected void handleClashingNames(String internalName, JvmDeclarationOrigin origin) {
        Intrinsics.checkNotNullParameter(internalName, "internalName");
        Intrinsics.checkNotNullParameter(origin, "origin");
        ConcurrentHashMap<String, JvmDeclarationOrigin> concurrentHashMap = this.className;
        JvmDeclarationOrigin jvmDeclarationOrigin = concurrentHashMap.get(internalName);
        if (jvmDeclarationOrigin == null) {
            JvmDeclarationOrigin jvmDeclarationOriginPutIfAbsent = concurrentHashMap.putIfAbsent(internalName, origin);
            jvmDeclarationOrigin = jvmDeclarationOriginPutIfAbsent == null ? origin : jvmDeclarationOriginPutIfAbsent;
        }
        JvmDeclarationOrigin jvmDeclarationOrigin2 = jvmDeclarationOrigin;
        if (Intrinsics.areEqual(origin.getOriginalSourceElement(), jvmDeclarationOrigin2.getOriginalSourceElement())) {
            return;
        }
        Intrinsics.checkNotNull(jvmDeclarationOrigin2);
        reportError(internalName, origin, jvmDeclarationOrigin2);
    }

    private final void reportError(String internalName, JvmDeclarationOrigin... another) {
        ArrayList arrayList = new ArrayList();
        for (JvmDeclarationOrigin jvmDeclarationOrigin : another) {
            DeclarationDescriptor descriptor = jvmDeclarationOrigin.getDescriptor();
            if (descriptor != null) {
                arrayList.add(descriptor);
            }
        }
        String strJoinToString$default = CollectionsKt.joinToString$default(arrayList, null, null, null, 0, null, new Function1<DeclarationDescriptor, CharSequence>() { // from class: org.jetbrains.kotlin.codegen.state.BuilderFactoryForDuplicateClassNameDiagnostics$reportError$duplicateClasses$2
            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(DeclarationDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return DescriptorRenderer.ONLY_NAMES_WITH_SHORT_TYPES.render(it);
            }
        }, 31, null);
        for (JvmDeclarationOrigin jvmDeclarationOrigin2 : another) {
            this.state.getReportDuplicateClassNameError().invoke(jvmDeclarationOrigin2, internalName, strJoinToString$default);
        }
    }
}
