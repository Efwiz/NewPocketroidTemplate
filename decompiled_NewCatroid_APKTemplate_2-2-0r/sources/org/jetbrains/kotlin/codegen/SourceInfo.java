package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.backend.common.CodegenUtil;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtNamedFunction;

/* JADX INFO: compiled from: SourceInfo.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0086\b\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016B\u001f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u000b\u0010\r\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0006HÆ\u0003J)\u0010\u0010\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0006HÆ\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0006HÖ\u0001J\t\u0010\u0015\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000b¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/codegen/SourceInfo;", "", "sourceFileName", "", "pathOrCleanFQN", "linesInFile", "", "(Ljava/lang/String;Ljava/lang/String;I)V", "getLinesInFile", "()I", "getPathOrCleanFQN", "()Ljava/lang/String;", "getSourceFileName", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "toString", "Companion", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class SourceInfo {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final int linesInFile;
    private final String pathOrCleanFQN;
    private final String sourceFileName;

    public static /* synthetic */ SourceInfo copy$default(SourceInfo sourceInfo, String str, String str2, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = sourceInfo.sourceFileName;
        }
        if ((i2 & 2) != 0) {
            str2 = sourceInfo.pathOrCleanFQN;
        }
        if ((i2 & 4) != 0) {
            i = sourceInfo.linesInFile;
        }
        return sourceInfo.copy(str, str2, i);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getSourceFileName() {
        return this.sourceFileName;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getPathOrCleanFQN() {
        return this.pathOrCleanFQN;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final int getLinesInFile() {
        return this.linesInFile;
    }

    public final SourceInfo copy(String sourceFileName, String pathOrCleanFQN, int linesInFile) {
        Intrinsics.checkNotNullParameter(pathOrCleanFQN, "pathOrCleanFQN");
        return new SourceInfo(sourceFileName, pathOrCleanFQN, linesInFile);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SourceInfo)) {
            return false;
        }
        SourceInfo sourceInfo = (SourceInfo) other;
        return Intrinsics.areEqual(this.sourceFileName, sourceInfo.sourceFileName) && Intrinsics.areEqual(this.pathOrCleanFQN, sourceInfo.pathOrCleanFQN) && this.linesInFile == sourceInfo.linesInFile;
    }

    public int hashCode() {
        String str = this.sourceFileName;
        return ((((str == null ? 0 : str.hashCode()) * 31) + this.pathOrCleanFQN.hashCode()) * 31) + Integer.hashCode(this.linesInFile);
    }

    public String toString() {
        return "SourceInfo(sourceFileName=" + this.sourceFileName + ", pathOrCleanFQN=" + this.pathOrCleanFQN + ", linesInFile=" + this.linesInFile + ')';
    }

    public SourceInfo(String str, String pathOrCleanFQN, int i) {
        Intrinsics.checkNotNullParameter(pathOrCleanFQN, "pathOrCleanFQN");
        this.sourceFileName = str;
        this.pathOrCleanFQN = pathOrCleanFQN;
        this.linesInFile = i;
    }

    public final String getSourceFileName() {
        return this.sourceFileName;
    }

    public final String getPathOrCleanFQN() {
        return this.pathOrCleanFQN;
    }

    public final int getLinesInFile() {
        return this.linesInFile;
    }

    /* JADX INFO: compiled from: SourceInfo.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/codegen/SourceInfo$Companion;", "", "()V", "createFromPsi", "Lorg/jetbrains/kotlin/codegen/SourceInfo;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/psi/KtElement;", "internalClassName", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final SourceInfo createFromPsi(KtElement element, String internalClassName) {
            Intrinsics.checkNotNullParameter(internalClassName, "internalClassName");
            Intrinsics.checkNotNull(element);
            PsiFile containingFile = element.getContainingFile();
            Intrinsics.checkNotNullExpressionValue(containingFile, "getContainingFile(...)");
            Integer lineNumberForElement = CodegenUtil.getLineNumberForElement(containingFile, true);
            if (lineNumberForElement == null) {
                throw new IllegalStateException(("Couldn't extract line count in " + element.getContainingFile()).toString());
            }
            int iIntValue = lineNumberForElement.intValue();
            if ((element instanceof KtFile) || ((element instanceof KtNamedFunction) && (((KtNamedFunction) element).getMyParent() instanceof KtFile))) {
                internalClassName = StringsKt.substringBefore$default(internalClassName, '$', (String) null, 2, (Object) null);
            }
            String name2 = element.getContainingKtFile().getMyName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            return new SourceInfo(name2, internalClassName, iIntValue);
        }
    }
}
