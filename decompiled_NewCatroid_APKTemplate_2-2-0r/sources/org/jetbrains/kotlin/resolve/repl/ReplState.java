package org.jetbrains.kotlin.resolve.repl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptorWithResolutionScopes;
import org.jetbrains.kotlin.descriptors.ScriptDescriptor;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.resolve.lazy.FileScopeFactory;
import org.jetbrains.kotlin.resolve.lazy.FileScopeProviderKt;
import org.jetbrains.kotlin.resolve.lazy.FileScopes;
import org.jetbrains.kotlin.resolve.lazy.FileScopesCustomizer;
import org.jetbrains.kotlin.resolve.scopes.HierarchicalScope;
import org.jetbrains.kotlin.resolve.scopes.ImportingScope;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.resolve.scopes.utils.ScopeUtilsKt;

/* JADX INFO: compiled from: ReplState.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001\u001cB\u0005¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u000e\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0005J\u0012\u0010\u0012\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0017\u001a\u00020\u0005H\u0002J\u0016\u0010\u0018\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00052\u0006\u0010\u0019\u001a\u00020\u001aJ\u000e\u0010\u001b\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0005R*\u0010\u0003\u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006`\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\b\u001a\u0012\u0012\u0004\u0012\u00020\n0\tj\b\u0012\u0004\u0012\u00020\n`\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\f\u001a\u00020\r8F¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/resolve/repl/ReplState;", "", "()V", "lines", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/psi/KtFile;", "Lorg/jetbrains/kotlin/resolve/repl/ReplState$LineInfo;", "Lkotlin/collections/HashMap;", "successfulLines", "Ljava/util/ArrayList;", "Lorg/jetbrains/kotlin/resolve/repl/ReplState$LineInfo$SuccessfulLine;", "Lkotlin/collections/ArrayList;", "successfulLinesCount", "", "getSuccessfulLinesCount", "()I", "computeFileScopes", "Lorg/jetbrains/kotlin/resolve/lazy/FileScopes;", "lineInfo", "fileScopeFactory", "Lorg/jetbrains/kotlin/resolve/lazy/FileScopeFactory;", "lineFailure", "", "ktFile", "lineSuccess", "scriptDescriptor", "Lorg/jetbrains/kotlin/descriptors/ScriptDescriptor;", "submitLine", "LineInfo", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReplState {
    private final HashMap<KtFile, LineInfo> lines = new HashMap<>();
    private final ArrayList<LineInfo.SuccessfulLine> successfulLines = new ArrayList<>();

    public final int getSuccessfulLinesCount() {
        return this.successfulLines.size();
    }

    public final void submitLine(final KtFile ktFile) {
        Intrinsics.checkNotNullParameter(ktFile, "ktFile");
        this.lines.put(ktFile, new LineInfo.SubmittedLine(ktFile, (LineInfo.SuccessfulLine) CollectionsKt.lastOrNull((List) this.successfulLines)));
        FileScopeProviderKt.setFileScopesCustomizer(ktFile, new FileScopesCustomizer() { // from class: org.jetbrains.kotlin.resolve.repl.ReplState.submitLine.1
            @Override // org.jetbrains.kotlin.resolve.lazy.FileScopesCustomizer
            public FileScopes createFileScopes(FileScopeFactory fileScopeFactory) {
                FileScopes fileScopesComputeFileScopes;
                Intrinsics.checkNotNullParameter(fileScopeFactory, "fileScopeFactory");
                LineInfo lineInfo = ReplState.this.lineInfo(ktFile);
                return (lineInfo == null || (fileScopesComputeFileScopes = ReplState.this.computeFileScopes(lineInfo, fileScopeFactory)) == null) ? FileScopeFactory.createScopesForFile$default(fileScopeFactory, ktFile, null, false, 6, null) : fileScopesComputeFileScopes;
            }
        });
    }

    public final void lineSuccess(KtFile ktFile, ScriptDescriptor scriptDescriptor) {
        Intrinsics.checkNotNullParameter(ktFile, "ktFile");
        Intrinsics.checkNotNullParameter(scriptDescriptor, "scriptDescriptor");
        LineInfo.SuccessfulLine successfulLine = new LineInfo.SuccessfulLine(ktFile, (LineInfo.SuccessfulLine) CollectionsKt.lastOrNull((List) this.successfulLines), scriptDescriptor);
        this.lines.put(ktFile, successfulLine);
        this.successfulLines.add(successfulLine);
    }

    public final void lineFailure(KtFile ktFile) {
        Intrinsics.checkNotNullParameter(ktFile, "ktFile");
        this.lines.put(ktFile, new LineInfo.FailedLine(ktFile, (LineInfo.SuccessfulLine) CollectionsKt.lastOrNull((List) this.successfulLines)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final LineInfo lineInfo(KtFile ktFile) {
        return this.lines.get(ktFile);
    }

    /* JADX INFO: compiled from: ReplState.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b2\u0018\u00002\u00020\u0001:\u0003\u000b\f\rB\u0007\b\u0004¢\u0006\u0002\u0010\u0002R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u0004\u0018\u00010\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n\u0082\u0001\u0003\u000e\u000f\b¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/resolve/repl/ReplState$LineInfo;", "", "()V", "linePsi", "Lorg/jetbrains/kotlin/psi/KtFile;", "getLinePsi", "()Lorg/jetbrains/kotlin/psi/KtFile;", "parentLine", "Lorg/jetbrains/kotlin/resolve/repl/ReplState$LineInfo$SuccessfulLine;", "getParentLine", "()Lorg/jetbrains/kotlin/resolve/repl/ReplState$LineInfo$SuccessfulLine;", "FailedLine", "SubmittedLine", "SuccessfulLine", "Lorg/jetbrains/kotlin/resolve/repl/ReplState$LineInfo$FailedLine;", "Lorg/jetbrains/kotlin/resolve/repl/ReplState$LineInfo$SubmittedLine;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static abstract class LineInfo {
        public /* synthetic */ LineInfo(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public abstract KtFile getLinePsi();

        public abstract SuccessfulLine getParentLine();

        private LineInfo() {
        }

        /* JADX INFO: compiled from: ReplState.kt */
        @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0016\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/repl/ReplState$LineInfo$SubmittedLine;", "Lorg/jetbrains/kotlin/resolve/repl/ReplState$LineInfo;", "linePsi", "Lorg/jetbrains/kotlin/psi/KtFile;", "parentLine", "Lorg/jetbrains/kotlin/resolve/repl/ReplState$LineInfo$SuccessfulLine;", "(Lorg/jetbrains/kotlin/psi/KtFile;Lorg/jetbrains/kotlin/resolve/repl/ReplState$LineInfo$SuccessfulLine;)V", "getLinePsi", "()Lorg/jetbrains/kotlin/psi/KtFile;", "getParentLine", "()Lorg/jetbrains/kotlin/resolve/repl/ReplState$LineInfo$SuccessfulLine;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class SubmittedLine extends LineInfo {
            private final KtFile linePsi;
            private final SuccessfulLine parentLine;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public SubmittedLine(KtFile linePsi, SuccessfulLine successfulLine) {
                super(null);
                Intrinsics.checkNotNullParameter(linePsi, "linePsi");
                this.linePsi = linePsi;
                this.parentLine = successfulLine;
            }

            @Override // org.jetbrains.kotlin.resolve.repl.ReplState.LineInfo
            public KtFile getLinePsi() {
                return this.linePsi;
            }

            @Override // org.jetbrains.kotlin.resolve.repl.ReplState.LineInfo
            public SuccessfulLine getParentLine() {
                return this.parentLine;
            }
        }

        /* JADX INFO: compiled from: ReplState.kt */
        @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0000\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0016\u0010\u0004\u001a\u0004\u0018\u00010\u0000X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/resolve/repl/ReplState$LineInfo$SuccessfulLine;", "Lorg/jetbrains/kotlin/resolve/repl/ReplState$LineInfo;", "linePsi", "Lorg/jetbrains/kotlin/psi/KtFile;", "parentLine", "lineDescriptor", "Lorg/jetbrains/kotlin/descriptors/ScriptDescriptor;", "(Lorg/jetbrains/kotlin/psi/KtFile;Lorg/jetbrains/kotlin/resolve/repl/ReplState$LineInfo$SuccessfulLine;Lorg/jetbrains/kotlin/descriptors/ScriptDescriptor;)V", "getLineDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ScriptDescriptor;", "getLinePsi", "()Lorg/jetbrains/kotlin/psi/KtFile;", "getParentLine", "()Lorg/jetbrains/kotlin/resolve/repl/ReplState$LineInfo$SuccessfulLine;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class SuccessfulLine extends LineInfo {
            private final ScriptDescriptor lineDescriptor;
            private final KtFile linePsi;
            private final SuccessfulLine parentLine;

            @Override // org.jetbrains.kotlin.resolve.repl.ReplState.LineInfo
            public KtFile getLinePsi() {
                return this.linePsi;
            }

            @Override // org.jetbrains.kotlin.resolve.repl.ReplState.LineInfo
            public SuccessfulLine getParentLine() {
                return this.parentLine;
            }

            public final ScriptDescriptor getLineDescriptor() {
                return this.lineDescriptor;
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public SuccessfulLine(KtFile linePsi, SuccessfulLine successfulLine, ScriptDescriptor lineDescriptor) {
                super(null);
                Intrinsics.checkNotNullParameter(linePsi, "linePsi");
                Intrinsics.checkNotNullParameter(lineDescriptor, "lineDescriptor");
                this.linePsi = linePsi;
                this.parentLine = successfulLine;
                this.lineDescriptor = lineDescriptor;
            }
        }

        /* JADX INFO: compiled from: ReplState.kt */
        @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0016\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/repl/ReplState$LineInfo$FailedLine;", "Lorg/jetbrains/kotlin/resolve/repl/ReplState$LineInfo;", "linePsi", "Lorg/jetbrains/kotlin/psi/KtFile;", "parentLine", "Lorg/jetbrains/kotlin/resolve/repl/ReplState$LineInfo$SuccessfulLine;", "(Lorg/jetbrains/kotlin/psi/KtFile;Lorg/jetbrains/kotlin/resolve/repl/ReplState$LineInfo$SuccessfulLine;)V", "getLinePsi", "()Lorg/jetbrains/kotlin/psi/KtFile;", "getParentLine", "()Lorg/jetbrains/kotlin/resolve/repl/ReplState$LineInfo$SuccessfulLine;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class FailedLine extends LineInfo {
            private final KtFile linePsi;
            private final SuccessfulLine parentLine;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public FailedLine(KtFile linePsi, SuccessfulLine successfulLine) {
                super(null);
                Intrinsics.checkNotNullParameter(linePsi, "linePsi");
                this.linePsi = linePsi;
                this.parentLine = successfulLine;
            }

            @Override // org.jetbrains.kotlin.resolve.repl.ReplState.LineInfo
            public KtFile getLinePsi() {
                return this.linePsi;
            }

            @Override // org.jetbrains.kotlin.resolve.repl.ReplState.LineInfo
            public SuccessfulLine getParentLine() {
                return this.parentLine;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final FileScopes computeFileScopes(LineInfo lineInfo, FileScopeFactory fileScopeFactory) {
        LexicalScope scopeForInitializerResolution;
        LineInfo.SuccessfulLine parentLine = lineInfo.getParentLine();
        ScriptDescriptor lineDescriptor = parentLine != null ? parentLine.getLineDescriptor() : null;
        ClassDescriptorWithResolutionScopes classDescriptorWithResolutionScopes = lineDescriptor instanceof ClassDescriptorWithResolutionScopes ? (ClassDescriptorWithResolutionScopes) lineDescriptor : null;
        if (classDescriptorWithResolutionScopes == null || (scopeForInitializerResolution = classDescriptorWithResolutionScopes.getScopeForInitializerResolution()) == null) {
            return null;
        }
        for (HierarchicalScope hierarchicalScope : ScopeUtilsKt.getParentsWithSelf(scopeForInitializerResolution)) {
            if (hierarchicalScope instanceof ImportingScope) {
                FileScopes fileScopesCreateScopesForFile$default = FileScopeFactory.createScopesForFile$default(fileScopeFactory, lineInfo.getLinePsi(), (ImportingScope) hierarchicalScope, false, 4, null);
                return new FileScopes(ScopeUtilsKt.replaceImportingScopes(scopeForInitializerResolution, fileScopesCreateScopesForFile$default.getImportingScope()), fileScopesCreateScopesForFile$default.getImportingScope(), fileScopesCreateScopesForFile$default.getImportForceResolver());
            }
        }
        throw new NoSuchElementException("No element of given type found");
    }
}
