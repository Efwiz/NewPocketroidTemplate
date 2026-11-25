package org.jetbrains.kotlin.codegen.state;

import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.JvmDeclarationOriginKind;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.MemberKind;
import org.jetbrains.kotlin.resolve.jvm.diagnostics.RawSignature;

/* JADX INFO: compiled from: BuilderFactoryForDuplicateSignatureDiagnostics.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\"\u0016\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0003\"\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0007"}, d2 = {"EXTERNAL_SOURCES_KINDS", "", "Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOriginKind;", "[Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/JvmDeclarationOriginKind;", "PREDEFINED_SIGNATURES", "", "Lorg/jetbrains/kotlin/resolve/jvm/diagnostics/RawSignature;", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class BuilderFactoryForDuplicateSignatureDiagnosticsKt {
    private static final JvmDeclarationOriginKind[] EXTERNAL_SOURCES_KINDS = {JvmDeclarationOriginKind.CLASS_MEMBER_DELEGATION_TO_DEFAULT_IMPL, JvmDeclarationOriginKind.DEFAULT_IMPL_DELEGATION_TO_SUPERINTERFACE_DEFAULT_IMPL, JvmDeclarationOriginKind.DELEGATION, JvmDeclarationOriginKind.BRIDGE};
    private static final List<RawSignature> PREDEFINED_SIGNATURES;

    static {
        List<String> listListOf = CollectionsKt.listOf((Object[]) new String[]{"getClass()Ljava/lang/Class;", "notify()V", "notifyAll()V", "wait()V", "wait(J)V", "wait(JI)V"});
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listListOf, 10));
        for (String str : listListOf) {
            String strSubstringBefore$default = StringsKt.substringBefore$default(str, '(', (String) null, 2, (Object) null);
            String strSubstring = str.substring(StringsKt.indexOf$default((CharSequence) str, '(', 0, false, 6, (Object) null));
            Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
            arrayList.add(new RawSignature(strSubstringBefore$default, strSubstring, MemberKind.METHOD));
        }
        PREDEFINED_SIGNATURES = arrayList;
    }
}
