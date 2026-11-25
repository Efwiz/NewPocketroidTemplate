package org.jetbrains.kotlin.js.sourceMap;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.js.backend.SourceLocationConsumer;
import org.jetbrains.kotlin.js.backend.ast.JsLocation;
import org.jetbrains.kotlin.js.backend.ast.JsLocationWithSource;
import org.jetbrains.kotlin.js.backend.ast.JsNode;
import org.jetbrains.kotlin.psi.KtPureElement;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.utils.addToStdlib.AddToStdlibKt;

/* JADX INFO: compiled from: SourceMapBuilderConsumer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\t¢\u0006\u0002\u0010\u000bJ\u0012\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u000eH\u0002J\b\u0010\u0012\u001a\u00020\u0010H\u0016J\b\u0010\u0013\u001a\u00020\u0010H\u0016J\u0012\u0010\u0014\u001a\u00020\u00102\b\u0010\u0015\u001a\u0004\u0018\u00010\u000eH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\rX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/js/sourceMap/SourceMapBuilderConsumer;", "Lorg/jetbrains/kotlin/js/backend/SourceLocationConsumer;", "sourceBaseDir", "Ljava/io/File;", "mappingConsumer", "Lorg/jetbrains/kotlin/js/sourceMap/SourceMapMappingConsumer;", "pathResolver", "Lorg/jetbrains/kotlin/js/sourceMap/SourceFilePathResolver;", "provideCurrentModuleContent", "", "provideExternalModuleContent", "(Ljava/io/File;Lorg/jetbrains/kotlin/js/sourceMap/SourceMapMappingConsumer;Lorg/jetbrains/kotlin/js/sourceMap/SourceFilePathResolver;ZZ)V", "sourceStack", "", "", "addMapping", "", "sourceInfo", "newLine", "popSourceInfo", "pushSourceInfo", CompilerOptions.INFO, "js.sourcemap"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SourceMapBuilderConsumer implements SourceLocationConsumer {
    private final SourceMapMappingConsumer mappingConsumer;
    private final SourceFilePathResolver pathResolver;
    private final boolean provideCurrentModuleContent;
    private final boolean provideExternalModuleContent;
    private final File sourceBaseDir;
    private final List<Object> sourceStack;

    public SourceMapBuilderConsumer(File sourceBaseDir, SourceMapMappingConsumer mappingConsumer, SourceFilePathResolver pathResolver, boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(sourceBaseDir, "sourceBaseDir");
        Intrinsics.checkNotNullParameter(mappingConsumer, "mappingConsumer");
        Intrinsics.checkNotNullParameter(pathResolver, "pathResolver");
        this.sourceBaseDir = sourceBaseDir;
        this.mappingConsumer = mappingConsumer;
        this.pathResolver = pathResolver;
        this.provideCurrentModuleContent = z;
        this.provideExternalModuleContent = z2;
        this.sourceStack = new ArrayList();
    }

    @Override // org.jetbrains.kotlin.js.backend.SourceLocationConsumer
    public void newLine() {
        this.mappingConsumer.newLine();
    }

    @Override // org.jetbrains.kotlin.js.backend.SourceLocationConsumer
    public void pushSourceInfo(Object info) {
        this.sourceStack.add(info);
        addMapping(info);
    }

    @Override // org.jetbrains.kotlin.js.backend.SourceLocationConsumer
    public void popSourceInfo() {
        AddToStdlibKt.popLast(this.sourceStack);
        addMapping(CollectionsKt.lastOrNull((List) this.sourceStack));
    }

    private final void addMapping(Object sourceInfo) {
        String file;
        if (sourceInfo == null) {
            this.mappingConsumer.addEmptyMapping();
            return;
        }
        if (sourceInfo instanceof PsiElement) {
            if (CallUtilKt.isFakePsiElement((PsiElement) sourceInfo)) {
                return;
            }
            try {
                JsLocation jsLocationExtractLocationFromPsi = PsiUtils.extractLocationFromPsi((PsiElement) sourceInfo, this.pathResolver);
                String file2 = jsLocationExtractLocationFromPsi.getFile();
                int startLine = jsLocationExtractLocationFromPsi.getStartLine();
                int startChar = jsLocationExtractLocationFromPsi.getStartChar();
                final File file3 = new File(((PsiElement) sourceInfo).getContainingFile().getViewProvider().getVirtualFile().getPath());
                final Function0 function0 = this.provideCurrentModuleContent ? new Function0<InputStreamReader>() { // from class: org.jetbrains.kotlin.js.sourceMap.SourceMapBuilderConsumer$addMapping$contentSupplier$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // kotlin.jvm.functions.Function0
                    public final InputStreamReader invoke() {
                        try {
                            return new InputStreamReader(new FileInputStream(file3), StandardCharsets.UTF_8);
                        } catch (IOException unused) {
                            return null;
                        }
                    }
                } : new Function0() { // from class: org.jetbrains.kotlin.js.sourceMap.SourceMapBuilderConsumer$addMapping$contentSupplier$2
                    @Override // kotlin.jvm.functions.Function0
                    public final Void invoke() {
                        return null;
                    }
                };
                this.mappingConsumer.addMapping(file2, null, new Supplier() { // from class: org.jetbrains.kotlin.js.sourceMap.SourceMapBuilderConsumer$$ExternalSyntheticLambda0
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return SourceMapBuilderConsumer.addMapping$lambda$0(function0);
                    }
                }, startLine, startChar, null);
                return;
            } catch (IOException e) {
                throw new RuntimeException("IO error occurred generating source maps", e);
            }
        }
        if (sourceInfo instanceof JsLocationWithSource) {
            final SourceMapBuilderConsumer$addMapping$contentSupplier$3 sourceProvider = this.provideExternalModuleContent ? ((JsLocationWithSource) sourceInfo).getSourceProvider() : new Function0() { // from class: org.jetbrains.kotlin.js.sourceMap.SourceMapBuilderConsumer$addMapping$contentSupplier$3
                @Override // kotlin.jvm.functions.Function0
                public final Void invoke() {
                    return null;
                }
            };
            JsLocationWithSource jsLocationWithSource = (JsLocationWithSource) sourceInfo;
            File file4 = new File(jsLocationWithSource.getFile());
            if (!file4.isAbsolute()) {
                file4 = new File(this.sourceBaseDir, jsLocationWithSource.getFile());
            }
            if (file4.isAbsolute()) {
                try {
                    file = this.pathResolver.getPathRelativeToSourceRoots(file4);
                } catch (IOException unused) {
                    file = jsLocationWithSource.getFile();
                }
            } else {
                file = jsLocationWithSource.getFile();
            }
            this.mappingConsumer.addMapping(file, jsLocationWithSource.getFileIdentity(), new Supplier() { // from class: org.jetbrains.kotlin.js.sourceMap.SourceMapBuilderConsumer$$ExternalSyntheticLambda1
                @Override // java.util.function.Supplier
                public final Object get() {
                    return SourceMapBuilderConsumer.addMapping$lambda$1(sourceProvider);
                }
            }, jsLocationWithSource.getStartLine(), jsLocationWithSource.getStartChar(), jsLocationWithSource.getName());
            return;
        }
        if (sourceInfo instanceof JsNode) {
            return;
        }
        boolean z = sourceInfo instanceof KtPureElement;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Reader addMapping$lambda$0(Function0 tmp0) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return (Reader) tmp0.invoke();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Reader addMapping$lambda$1(Function0 tmp0) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return (Reader) tmp0.invoke();
    }
}
