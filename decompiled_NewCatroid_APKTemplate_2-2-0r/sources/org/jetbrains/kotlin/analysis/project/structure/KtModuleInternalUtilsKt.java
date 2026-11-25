package org.jetbrains.kotlin.analysis.project.structure;

import java.util.List;
import javax.xml.transform.OutputKeys;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.com.intellij.openapi.util.Key;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.psi.UserDataProperty;

/* JADX INFO: compiled from: ktModuleInternalUtils.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\u001a\u0014\u0010\f\u001a\u00020\r*\u00020\u00012\b\b\u0002\u0010\u000e\u001a\u00020\u000f\"9\u0010\u0002\u001a\u0004\u0018\u00010\u0001*\u00020\u00032\b\u0010\u0000\u001a\u0004\u0018\u00010\u00018F@FX\u0087\u008e\u0002¢\u0006\u0018\n\u0004\b\n\u0010\u000b\u0012\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\t¨\u0006\u0010"}, d2 = {"<set-?>", "Lorg/jetbrains/kotlin/analysis/project/structure/KtModule;", "analysisExtensionFileContextModule", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "getAnalysisExtensionFileContextModule$annotations", "(Lcom/intellij/openapi/vfs/VirtualFile;)V", "getAnalysisExtensionFileContextModule", "(Lcom/intellij/openapi/vfs/VirtualFile;)Lorg/jetbrains/kotlin/analysis/project/structure/KtModule;", "setAnalysisExtensionFileContextModule", "(Lcom/intellij/openapi/vfs/VirtualFile;Lorg/jetbrains/kotlin/analysis/project/structure/KtModule;)V", "analysisExtensionFileContextModule$delegate", "Lorg/jetbrains/kotlin/psi/UserDataProperty;", "asDebugString", "", OutputKeys.INDENT, "", "project-structure"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KtModuleInternalUtilsKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinPackage(KtModuleInternalUtilsKt.class, "project-structure"), "analysisExtensionFileContextModule", "getAnalysisExtensionFileContextModule(Lcom/intellij/openapi/vfs/VirtualFile;)Lorg/jetbrains/kotlin/analysis/project/structure/KtModule;"))};
    private static final UserDataProperty analysisExtensionFileContextModule$delegate;

    @KtModuleStructureInternals
    public static /* synthetic */ void getAnalysisExtensionFileContextModule$annotations(VirtualFile virtualFile) {
    }

    static {
        Key keyCreate = Key.create("ANALYSIS_CONTEXT_MODULE");
        Intrinsics.checkNotNullExpressionValue(keyCreate, "create(...)");
        analysisExtensionFileContextModule$delegate = new UserDataProperty(keyCreate);
    }

    public static final KtModule getAnalysisExtensionFileContextModule(VirtualFile virtualFile) {
        Intrinsics.checkNotNullParameter(virtualFile, "<this>");
        return (KtModule) analysisExtensionFileContextModule$delegate.getValue(virtualFile, $$delegatedProperties[0]);
    }

    public static final void setAnalysisExtensionFileContextModule(VirtualFile virtualFile, KtModule ktModule) {
        Intrinsics.checkNotNullParameter(virtualFile, "<this>");
        analysisExtensionFileContextModule$delegate.setValue(virtualFile, $$delegatedProperties[0], ktModule);
    }

    public static final String asDebugString(KtModule ktModule, final int i) {
        Intrinsics.checkNotNullParameter(ktModule, "<this>");
        StringBuilder sb = new StringBuilder();
        StringBuilder sbAppend = sb.append(ktModule.getModuleDescription() + " {");
        Intrinsics.checkNotNullExpressionValue(sbAppend, "append(...)");
        Intrinsics.checkNotNullExpressionValue(sbAppend.append('\n'), "append(...)");
        StringBuilder sbAppend2 = sb.append("contentScope: " + ktModule.getContentScope());
        Intrinsics.checkNotNullExpressionValue(sbAppend2, "append(...)");
        Intrinsics.checkNotNullExpressionValue(sbAppend2.append('\n'), "append(...)");
        StringBuilder sbAppend3 = sb.append("directRegularDependencies:" + System.lineSeparator());
        Intrinsics.checkNotNullExpressionValue(sbAppend3, "append(...)");
        Intrinsics.checkNotNullExpressionValue(sbAppend3.append('\n'), "append(...)");
        List<KtModule> directRegularDependencies = ktModule.getDirectRegularDependencies();
        StringBuilder sb2 = sb;
        String strLineSeparator = System.lineSeparator();
        Intrinsics.checkNotNullExpressionValue(strLineSeparator, "lineSeparator(...)");
        CollectionsKt.joinTo(directRegularDependencies, sb2, (112 & 2) != 0 ? ", " : strLineSeparator, (112 & 4) != 0 ? "" : null, (112 & 8) != 0 ? "" : null, (112 & 16) != 0 ? -1 : 0, (112 & 32) != 0 ? "..." : null, (112 & 64) != 0 ? null : new Function1<KtModule, CharSequence>() { // from class: org.jetbrains.kotlin.analysis.project.structure.KtModuleInternalUtilsKt$asDebugString$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(KtModule dep) {
                Intrinsics.checkNotNullParameter(dep, "dep");
                return KtModuleInternalUtilsKt.asDebugString(dep, i + 1);
            }
        });
        StringBuilder sbAppend4 = sb.append("directDependsOnDependencies:" + System.lineSeparator());
        Intrinsics.checkNotNullExpressionValue(sbAppend4, "append(...)");
        Intrinsics.checkNotNullExpressionValue(sbAppend4.append('\n'), "append(...)");
        List<KtModule> directDependsOnDependencies = ktModule.getDirectDependsOnDependencies();
        String strLineSeparator2 = System.lineSeparator();
        Intrinsics.checkNotNullExpressionValue(strLineSeparator2, "lineSeparator(...)");
        CollectionsKt.joinTo(directDependsOnDependencies, sb2, (112 & 2) != 0 ? ", " : strLineSeparator2, (112 & 4) != 0 ? "" : null, (112 & 8) != 0 ? "" : null, (112 & 16) != 0 ? -1 : 0, (112 & 32) != 0 ? "..." : null, (112 & 64) != 0 ? null : new Function1<KtModule, CharSequence>() { // from class: org.jetbrains.kotlin.analysis.project.structure.KtModuleInternalUtilsKt$asDebugString$1$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final CharSequence invoke(KtModule dep) {
                Intrinsics.checkNotNullParameter(dep, "dep");
                return KtModuleInternalUtilsKt.asDebugString(dep, i + 1);
            }
        });
        sb.append("}");
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return StringsKt.prependIndent(string, StringsKt.repeat("  ", i));
    }

    public static /* synthetic */ String asDebugString$default(KtModule ktModule, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 0;
        }
        return asDebugString(ktModule, i);
    }
}
