package org.jetbrains.kotlin.fir.declarations.builder;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.List;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceFile;
import org.jetbrains.kotlin.KtSourceFileLinesMapping;
import org.jetbrains.kotlin.fir.FirFileAnnotationsContainer;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.FirPackageDirective;
import org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder;
import org.jetbrains.kotlin.fir.builder.FirBuilderDsl;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationAttributes;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirImport;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.impl.FirFileImpl;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.symbols.impl.FirFileSymbol;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirFileBuilder.kt */
/* JADX INFO: loaded from: classes4.dex */
@FirBuilderDsl
@Metadata(d1 = {"\u0000\u0086\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010Q\u001a\u00020RH\u0016R\"\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0016X\u0097\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0006\u0010\u0002\u001a\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u0017\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\bR\u0017\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\bR\u001a\u0010\u001b\u001a\u00020\u001cX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u001a\u0010!\u001a\u00020\"X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&R\u001a\u0010'\u001a\u00020(X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b)\u0010*\"\u0004\b+\u0010,R\u001a\u0010-\u001a\u00020.X\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\b/\u00100\"\u0004\b1\u00102R\u001a\u00103\u001a\u000204X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b5\u00106\"\u0004\b7\u00108R\u001c\u00109\u001a\u0004\u0018\u00010:X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b;\u0010<\"\u0004\b=\u0010>R\u001c\u0010?\u001a\u0004\u0018\u00010@X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bA\u0010B\"\u0004\bC\u0010DR\u001c\u0010E\u001a\u0004\u0018\u00010FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bG\u0010H\"\u0004\bI\u0010JR\u001a\u0010K\u001a\u00020LX\u0086.¢\u0006\u000e\n\u0000\u001a\u0004\bM\u0010N\"\u0004\bO\u0010P¨\u0006S"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/builder/FirFileBuilder;", "Lorg/jetbrains/kotlin/fir/builder/FirAnnotationContainerBuilder;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations$annotations", "getAnnotations", "()Ljava/util/List;", "annotationsContainer", "Lorg/jetbrains/kotlin/fir/FirFileAnnotationsContainer;", "getAnnotationsContainer", "()Lorg/jetbrains/kotlin/fir/FirFileAnnotationsContainer;", "setAnnotationsContainer", "(Lorg/jetbrains/kotlin/fir/FirFileAnnotationsContainer;)V", "attributes", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "getAttributes", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "setAttributes", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;)V", "declarations", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "getDeclarations", "imports", "Lorg/jetbrains/kotlin/fir/declarations/FirImport;", "getImports", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "getModuleData", "()Lorg/jetbrains/kotlin/fir/FirModuleData;", "setModuleData", "(Lorg/jetbrains/kotlin/fir/FirModuleData;)V", "name", "", "getName", "()Ljava/lang/String;", "setName", "(Ljava/lang/String;)V", "origin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "getOrigin", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "setOrigin", "(Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;)V", "packageDirective", "Lorg/jetbrains/kotlin/fir/FirPackageDirective;", "getPackageDirective", "()Lorg/jetbrains/kotlin/fir/FirPackageDirective;", "setPackageDirective", "(Lorg/jetbrains/kotlin/fir/FirPackageDirective;)V", "resolvePhase", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "getResolvePhase", "()Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "setResolvePhase", "(Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;)V", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "setSource", "(Lorg/jetbrains/kotlin/KtSourceElement;)V", "sourceFile", "Lorg/jetbrains/kotlin/KtSourceFile;", "getSourceFile", "()Lorg/jetbrains/kotlin/KtSourceFile;", "setSourceFile", "(Lorg/jetbrains/kotlin/KtSourceFile;)V", "sourceFileLinesMapping", "Lorg/jetbrains/kotlin/KtSourceFileLinesMapping;", "getSourceFileLinesMapping", "()Lorg/jetbrains/kotlin/KtSourceFileLinesMapping;", "setSourceFileLinesMapping", "(Lorg/jetbrains/kotlin/KtSourceFileLinesMapping;)V", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFileSymbol;", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirFileSymbol;", "setSymbol", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirFileSymbol;)V", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirFileBuilder implements FirAnnotationContainerBuilder {
    private FirFileAnnotationsContainer annotationsContainer;
    public FirModuleData moduleData;
    public String name;
    public FirDeclarationOrigin origin;
    public FirPackageDirective packageDirective;
    private KtSourceElement source;
    private KtSourceFile sourceFile;
    private KtSourceFileLinesMapping sourceFileLinesMapping;
    public FirFileSymbol symbol;
    private FirResolvePhase resolvePhase = FirResolvePhase.RAW_FIR;
    private FirDeclarationAttributes attributes = new FirDeclarationAttributes();
    private final List<FirImport> imports = new ArrayList();
    private final List<FirDeclaration> declarations = new ArrayList();
    private final List<FirAnnotation> annotations = new ArrayList();

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Modification of 'annotations' has no impact for FirFileBuilder")
    public static /* synthetic */ void getAnnotations$annotations() {
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public KtSourceElement getSource() {
        return this.source;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public void setSource(KtSourceElement ktSourceElement) {
        this.source = ktSourceElement;
    }

    public final FirResolvePhase getResolvePhase() {
        return this.resolvePhase;
    }

    public final void setResolvePhase(FirResolvePhase firResolvePhase) {
        Intrinsics.checkNotNullParameter(firResolvePhase, "<set-?>");
        this.resolvePhase = firResolvePhase;
    }

    public final FirModuleData getModuleData() {
        FirModuleData firModuleData = this.moduleData;
        if (firModuleData != null) {
            return firModuleData;
        }
        Intrinsics.throwUninitializedPropertyAccessException("moduleData");
        return null;
    }

    public final void setModuleData(FirModuleData firModuleData) {
        Intrinsics.checkNotNullParameter(firModuleData, "<set-?>");
        this.moduleData = firModuleData;
    }

    public final FirDeclarationOrigin getOrigin() {
        FirDeclarationOrigin firDeclarationOrigin = this.origin;
        if (firDeclarationOrigin != null) {
            return firDeclarationOrigin;
        }
        Intrinsics.throwUninitializedPropertyAccessException("origin");
        return null;
    }

    public final void setOrigin(FirDeclarationOrigin firDeclarationOrigin) {
        Intrinsics.checkNotNullParameter(firDeclarationOrigin, "<set-?>");
        this.origin = firDeclarationOrigin;
    }

    public final FirDeclarationAttributes getAttributes() {
        return this.attributes;
    }

    public final void setAttributes(FirDeclarationAttributes firDeclarationAttributes) {
        Intrinsics.checkNotNullParameter(firDeclarationAttributes, "<set-?>");
        this.attributes = firDeclarationAttributes;
    }

    public final FirFileAnnotationsContainer getAnnotationsContainer() {
        return this.annotationsContainer;
    }

    public final void setAnnotationsContainer(FirFileAnnotationsContainer firFileAnnotationsContainer) {
        this.annotationsContainer = firFileAnnotationsContainer;
    }

    public final FirPackageDirective getPackageDirective() {
        FirPackageDirective firPackageDirective = this.packageDirective;
        if (firPackageDirective != null) {
            return firPackageDirective;
        }
        Intrinsics.throwUninitializedPropertyAccessException("packageDirective");
        return null;
    }

    public final void setPackageDirective(FirPackageDirective firPackageDirective) {
        Intrinsics.checkNotNullParameter(firPackageDirective, "<set-?>");
        this.packageDirective = firPackageDirective;
    }

    public final List<FirImport> getImports() {
        return this.imports;
    }

    public final List<FirDeclaration> getDeclarations() {
        return this.declarations;
    }

    public final String getName() {
        String str = this.name;
        if (str != null) {
            return str;
        }
        Intrinsics.throwUninitializedPropertyAccessException("name");
        return null;
    }

    public final void setName(String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.name = str;
    }

    public final KtSourceFile getSourceFile() {
        return this.sourceFile;
    }

    public final void setSourceFile(KtSourceFile ktSourceFile) {
        this.sourceFile = ktSourceFile;
    }

    public final KtSourceFileLinesMapping getSourceFileLinesMapping() {
        return this.sourceFileLinesMapping;
    }

    public final void setSourceFileLinesMapping(KtSourceFileLinesMapping ktSourceFileLinesMapping) {
        this.sourceFileLinesMapping = ktSourceFileLinesMapping;
    }

    public final FirFileSymbol getSymbol() {
        FirFileSymbol firFileSymbol = this.symbol;
        if (firFileSymbol != null) {
            return firFileSymbol;
        }
        Intrinsics.throwUninitializedPropertyAccessException(NativeSymbol.TYPE_NAME);
        return null;
    }

    public final void setSymbol(FirFileSymbol firFileSymbol) {
        Intrinsics.checkNotNullParameter(firFileSymbol, "<set-?>");
        this.symbol = firFileSymbol;
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public FirFile build() {
        return new FirFileImpl(getSource(), this.resolvePhase, getModuleData(), getOrigin(), this.attributes, this.annotationsContainer, getPackageDirective(), this.imports, this.declarations, getName(), this.sourceFile, this.sourceFileLinesMapping, getSymbol());
    }

    @Override // org.jetbrains.kotlin.fir.builder.FirAnnotationContainerBuilder
    public /* synthetic */ List getAnnotations() {
        return this.annotations;
    }
}
