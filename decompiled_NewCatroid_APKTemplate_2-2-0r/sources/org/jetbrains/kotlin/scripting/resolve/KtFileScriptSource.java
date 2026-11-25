package org.jetbrains.kotlin.scripting.resolve;

import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: refineCompilationConfiguration.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0096\u0002J\b\u0010\u0014\u001a\u00020\u0015H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0016\u0010\t\u001a\u0004\u0018\u00010\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u001b\u0010\f\u001a\u00020\u00058VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\r\u0010\u000b¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/scripting/resolve/KtFileScriptSource;", "Lorg/jetbrains/kotlin/scripting/resolve/VirtualFileScriptSource;", "ktFile", "Lorg/jetbrains/kotlin/psi/KtFile;", "preloadedText", "", "(Lorg/jetbrains/kotlin/psi/KtFile;Ljava/lang/String;)V", "getKtFile", "()Lorg/jetbrains/kotlin/psi/KtFile;", "name", "getName", "()Ljava/lang/String;", "text", "getText", "text$delegate", "Lkotlin/Lazy;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public class KtFileScriptSource extends VirtualFileScriptSource {
    private final KtFile ktFile;

    /* JADX INFO: renamed from: text$delegate, reason: from kotlin metadata */
    private final Lazy text;

    public /* synthetic */ KtFileScriptSource(KtFile ktFile, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(ktFile, (i & 2) != 0 ? null : str);
    }

    public final KtFile getKtFile() {
        return this.ktFile;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public KtFileScriptSource(KtFile ktFile, final String str) {
        Intrinsics.checkNotNullParameter(ktFile, "ktFile");
        VirtualFile virtualFile = ktFile.getVirtualFile();
        virtualFile = virtualFile == null ? ktFile.getOriginalFile().getVirtualFile() : virtualFile;
        if (virtualFile == null) {
            virtualFile = ktFile.getViewProvider().getVirtualFile();
            Intrinsics.checkNotNullExpressionValue(virtualFile, "getVirtualFile(...)");
        }
        super(virtualFile, str);
        this.ktFile = ktFile;
        this.text = LazyKt.lazy(new Function0<String>() { // from class: org.jetbrains.kotlin.scripting.resolve.KtFileScriptSource$text$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final String invoke() {
                String str2 = str;
                return str2 == null ? this.getKtFile().getText() : str2;
            }
        });
    }

    @Override // org.jetbrains.kotlin.scripting.resolve.VirtualFileScriptSource, kotlin.script.experimental.api.SourceCode
    public String getText() {
        Object value = this.text.getValue();
        Intrinsics.checkNotNullExpressionValue(value, "getValue(...)");
        return (String) value;
    }

    @Override // org.jetbrains.kotlin.scripting.resolve.VirtualFileScriptSource, kotlin.script.experimental.api.SourceCode
    public String getName() {
        return this.ktFile.getName();
    }

    @Override // org.jetbrains.kotlin.scripting.resolve.VirtualFileScriptSource
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        KtFileScriptSource ktFileScriptSource = other instanceof KtFileScriptSource ? (KtFileScriptSource) other : null;
        return ktFileScriptSource != null && Intrinsics.areEqual(this.ktFile, ktFileScriptSource.ktFile);
    }

    @Override // org.jetbrains.kotlin.scripting.resolve.VirtualFileScriptSource
    public int hashCode() {
        return this.ktFile.hashCode();
    }
}
