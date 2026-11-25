package org.jetbrains.kotlin.ir.backend.js.export;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.serialization.js.ModuleKind;

/* JADX INFO: compiled from: ExportModelToTsDeclarations.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000*\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0015\u0010\b\u001a\u00020\t*\b\u0012\u0004\u0012\u00020\t0\n¢\u0006\u0002\u0010\u000b\u001a \u0010\f\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\t0\n2\u0006\u0010\r\u001a\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u000f\u001a\u001d\u0010\u0010\u001a\u00020\t*\b\u0012\u0004\u0012\u00020\u00110\n2\u0006\u0010\u000e\u001a\u00020\u000f¢\u0006\u0002\u0010\u0012\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0003\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0004\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0005\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0006\u001a\u00020\u0007X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"NonExistent", "", "Nullable", "declare", "declareExported", Constants.OBJECTS, "syntheticObjectNameSeparator", "", "joinTypeScriptFragments", "Lorg/jetbrains/kotlin/ir/backend/js/export/TypeScriptFragment;", "", "(Ljava/util/List;)Ljava/lang/String;", "toTypeScript", "name", "moduleKind", "Lorg/jetbrains/kotlin/serialization/js/ModuleKind;", "toTypeScriptFragment", "Lorg/jetbrains/kotlin/ir/backend/js/export/ExportedDeclaration;", "(Ljava/util/List;Lorg/jetbrains/kotlin/serialization/js/ModuleKind;)Ljava/lang/String;", "backend.js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ExportModelToTsDeclarationsKt {
    public static final String toTypeScriptFragment(List<? extends ExportedDeclaration> list, ModuleKind moduleKind) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(moduleKind, "moduleKind");
        return new ExportModelToTsDeclarations().m7087generateTypeScriptFragment2EUUaHo(moduleKind, list);
    }

    public static final String joinTypeScriptFragments(List<TypeScriptFragment> list) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        return TypeScriptFragment.m7090constructorimpl(CollectionsKt.joinToString$default(list, "\n", null, null, 0, null, new Function1<TypeScriptFragment, CharSequence>() { // from class: org.jetbrains.kotlin.ir.backend.js.export.ExportModelToTsDeclarationsKt.joinTypeScriptFragments.1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ CharSequence invoke(TypeScriptFragment typeScriptFragment) {
                return m7088invokebvgKOQw(typeScriptFragment.m7095unboximpl());
            }

            /* JADX INFO: renamed from: invoke-bvgKOQw, reason: not valid java name */
            public final CharSequence m7088invokebvgKOQw(String it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it;
            }
        }, 30, null));
    }

    public static final String toTypeScript(List<TypeScriptFragment> list, String name2, ModuleKind moduleKind) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(moduleKind, "moduleKind");
        return new ExportModelToTsDeclarations().generateTypeScript(name2, moduleKind, list);
    }
}
