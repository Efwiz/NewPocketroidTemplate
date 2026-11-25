package org.jetbrains.kotlin.fir.declarations.impl;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceFile;
import org.jetbrains.kotlin.KtSourceFileLinesMapping;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirFileAnnotationsContainer;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.FirPackageDirective;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationAttributes;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirImport;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirResolveStateKt;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.symbols.impl.FirFileSymbol;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirTransformerUtilKt;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirFileImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0082\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u001d\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0000\u0018\u00002\u00020\u0001B\u0081\u0001\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\b\u0010\f\u001a\u0004\u0018\u00010\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011\u0012\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u0011\u0012\u0006\u0010\u0015\u001a\u00020\u0016\u0012\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018\u0012\b\u0010\u0019\u001a\u0004\u0018\u00010\u001a\u0012\u0006\u0010\u001b\u001a\u00020\u001c¢\u0006\u0002\u0010\u001dJ5\u0010=\u001a\u00020>\"\u0004\b\u0000\u0010?\"\u0004\b\u0001\u0010@2\u0012\u0010A\u001a\u000e\u0012\u0004\u0012\u0002H?\u0012\u0004\u0012\u0002H@0B2\u0006\u0010C\u001a\u0002H@H\u0016¢\u0006\u0002\u0010DJ\u0016\u0010E\u001a\u00020>2\f\u0010F\u001a\b\u0012\u0004\u0012\u00020 0\u001fH\u0016J)\u0010G\u001a\u00020\u0000\"\u0004\b\u0000\u0010@2\f\u0010H\u001a\b\u0012\u0004\u0012\u0002H@0I2\u0006\u0010C\u001a\u0002H@H\u0016¢\u0006\u0002\u0010JJ)\u0010K\u001a\u00020\u0000\"\u0004\b\u0000\u0010@2\f\u0010H\u001a\b\u0012\u0004\u0012\u0002H@0I2\u0006\u0010C\u001a\u0002H@H\u0016¢\u0006\u0002\u0010JJ)\u0010L\u001a\u00020\u0000\"\u0004\b\u0000\u0010@2\f\u0010H\u001a\b\u0012\u0004\u0012\u0002H@0I2\u0006\u0010C\u001a\u0002H@H\u0016¢\u0006\u0002\u0010JJ)\u0010M\u001a\u00020\u0000\"\u0004\b\u0000\u0010@2\f\u0010H\u001a\b\u0012\u0004\u0012\u0002H@0I2\u0006\u0010C\u001a\u0002H@H\u0016¢\u0006\u0002\u0010JJ)\u0010N\u001a\u00020\u0000\"\u0004\b\u0000\u0010@2\f\u0010H\u001a\b\u0012\u0004\u0012\u0002H@0I2\u0006\u0010C\u001a\u0002H@H\u0016¢\u0006\u0002\u0010JR\u001a\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020 0\u001f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\"R\u001c\u0010\f\u001a\u0004\u0018\u00010\rX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&R\u0014\u0010\n\u001a\u00020\u000bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b'\u0010(R\u001a\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u0011X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b)\u0010\"R\u001a\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b*\u0010\"R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b+\u0010,R\u0014\u0010\u0015\u001a\u00020\u0016X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b-\u0010.R\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b/\u00100R\u001a\u0010\u000e\u001a\u00020\u000fX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b1\u00102\"\u0004\b3\u00104R\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b5\u00106R\u0016\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b7\u00108R\u0016\u0010\u0019\u001a\u0004\u0018\u00010\u001aX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b9\u0010:R\u0014\u0010\u001b\u001a\u00020\u001cX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b;\u0010<¨\u0006O"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/impl/FirFileImpl;", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "resolvePhase", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "origin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "attributes", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "annotationsContainer", "Lorg/jetbrains/kotlin/fir/FirFileAnnotationsContainer;", "packageDirective", "Lorg/jetbrains/kotlin/fir/FirPackageDirective;", "imports", "", "Lorg/jetbrains/kotlin/fir/declarations/FirImport;", "declarations", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "name", "", "sourceFile", "Lorg/jetbrains/kotlin/KtSourceFile;", "sourceFileLinesMapping", "Lorg/jetbrains/kotlin/KtSourceFileLinesMapping;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFileSymbol;", "(Lorg/jetbrains/kotlin/KtSourceElement;Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;Lorg/jetbrains/kotlin/fir/FirModuleData;Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;Lorg/jetbrains/kotlin/fir/FirFileAnnotationsContainer;Lorg/jetbrains/kotlin/fir/FirPackageDirective;Ljava/util/List;Ljava/util/List;Ljava/lang/String;Lorg/jetbrains/kotlin/KtSourceFile;Lorg/jetbrains/kotlin/KtSourceFileLinesMapping;Lorg/jetbrains/kotlin/fir/symbols/impl/FirFileSymbol;)V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "getAnnotationsContainer", "()Lorg/jetbrains/kotlin/fir/FirFileAnnotationsContainer;", "setAnnotationsContainer", "(Lorg/jetbrains/kotlin/fir/FirFileAnnotationsContainer;)V", "getAttributes", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "getDeclarations", "getImports", "getModuleData", "()Lorg/jetbrains/kotlin/fir/FirModuleData;", "getName", "()Ljava/lang/String;", "getOrigin", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "getPackageDirective", "()Lorg/jetbrains/kotlin/fir/FirPackageDirective;", "setPackageDirective", "(Lorg/jetbrains/kotlin/fir/FirPackageDirective;)V", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "getSourceFile", "()Lorg/jetbrains/kotlin/KtSourceFile;", "getSourceFileLinesMapping", "()Lorg/jetbrains/kotlin/KtSourceFileLinesMapping;", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirFileSymbol;", "acceptChildren", "", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)V", "replaceAnnotations", "newAnnotations", "transformAnnotations", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/declarations/impl/FirFileImpl;", "transformAnnotationsContainer", "transformChildren", "transformDeclarations", "transformImports", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirFileImpl extends FirFile {
    private FirFileAnnotationsContainer annotationsContainer;
    private final FirDeclarationAttributes attributes;
    private final List<FirDeclaration> declarations;
    private final List<FirImport> imports;
    private final FirModuleData moduleData;
    private final String name;
    private final FirDeclarationOrigin origin;
    private FirPackageDirective packageDirective;
    private final KtSourceElement source;
    private final KtSourceFile sourceFile;
    private final KtSourceFileLinesMapping sourceFileLinesMapping;
    private final FirFileSymbol symbol;

    @Override // org.jetbrains.kotlin.fir.declarations.FirFile, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public void replaceAnnotations(List<? extends FirAnnotation> newAnnotations) {
        Intrinsics.checkNotNullParameter(newAnnotations, "newAnnotations");
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirFile, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public <D> FirFileImpl transformAnnotations(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirAnnotationContainer transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirDeclaration transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirFile, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public /* bridge */ /* synthetic */ FirFile transformAnnotations(FirTransformer firTransformer, Object obj) {
        return transformAnnotations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirFile
    public /* bridge */ /* synthetic */ FirFile transformAnnotationsContainer(FirTransformer firTransformer, Object obj) {
        return transformAnnotationsContainer((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public /* bridge */ /* synthetic */ FirElement transformChildren(FirTransformer firTransformer, Object obj) {
        return transformChildren((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirFile
    public /* bridge */ /* synthetic */ FirFile transformDeclarations(FirTransformer firTransformer, Object obj) {
        return transformDeclarations((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirFile
    public /* bridge */ /* synthetic */ FirFile transformImports(FirTransformer firTransformer, Object obj) {
        return transformImports((FirTransformer<? super Object>) firTransformer, obj);
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirFile, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState, org.jetbrains.kotlin.fir.FirElement
    public KtSourceElement getSource() {
        return this.source;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirFile, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState
    public FirModuleData getModuleData() {
        return this.moduleData;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirFile, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public FirDeclarationOrigin getOrigin() {
        return this.origin;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirFile, org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public FirDeclarationAttributes getAttributes() {
        return this.attributes;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirFile
    public FirFileAnnotationsContainer getAnnotationsContainer() {
        return this.annotationsContainer;
    }

    public void setAnnotationsContainer(FirFileAnnotationsContainer firFileAnnotationsContainer) {
        this.annotationsContainer = firFileAnnotationsContainer;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirFile
    public FirPackageDirective getPackageDirective() {
        return this.packageDirective;
    }

    public void setPackageDirective(FirPackageDirective firPackageDirective) {
        Intrinsics.checkNotNullParameter(firPackageDirective, "<set-?>");
        this.packageDirective = firPackageDirective;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirFile
    public List<FirImport> getImports() {
        return this.imports;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirFile
    public List<FirDeclaration> getDeclarations() {
        return this.declarations;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirFile
    public String getName() {
        return this.name;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirFile
    public KtSourceFile getSourceFile() {
        return this.sourceFile;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirFile
    public KtSourceFileLinesMapping getSourceFileLinesMapping() {
        return this.sourceFileLinesMapping;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public FirFileSymbol getSymbol() {
        return this.symbol;
    }

    public FirFileImpl(KtSourceElement ktSourceElement, FirResolvePhase resolvePhase, FirModuleData moduleData, FirDeclarationOrigin origin, FirDeclarationAttributes attributes, FirFileAnnotationsContainer firFileAnnotationsContainer, FirPackageDirective packageDirective, List<FirImport> imports, List<FirDeclaration> declarations, String name2, KtSourceFile ktSourceFile, KtSourceFileLinesMapping ktSourceFileLinesMapping, FirFileSymbol symbol) {
        Intrinsics.checkNotNullParameter(resolvePhase, "resolvePhase");
        Intrinsics.checkNotNullParameter(moduleData, "moduleData");
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        Intrinsics.checkNotNullParameter(packageDirective, "packageDirective");
        Intrinsics.checkNotNullParameter(imports, "imports");
        Intrinsics.checkNotNullParameter(declarations, "declarations");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        this.source = ktSourceElement;
        this.moduleData = moduleData;
        this.origin = origin;
        this.attributes = attributes;
        this.annotationsContainer = firFileAnnotationsContainer;
        this.packageDirective = packageDirective;
        this.imports = imports;
        this.declarations = declarations;
        this.name = name2;
        this.sourceFile = ktSourceFile;
        this.sourceFileLinesMapping = ktSourceFileLinesMapping;
        this.symbol = symbol;
        getSymbol().bind(this);
        setResolveState(FirResolveStateKt.asResolveState(resolvePhase));
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirFile, org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public List<FirAnnotation> getAnnotations() {
        List<FirAnnotation> annotations;
        FirFileAnnotationsContainer annotationsContainer = getAnnotationsContainer();
        return (annotationsContainer == null || (annotations = annotationsContainer.getAnnotations()) == null) ? CollectionsKt.emptyList() : annotations;
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <R, D> void acceptChildren(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        FirFileAnnotationsContainer annotationsContainer = getAnnotationsContainer();
        if (annotationsContainer != null) {
            annotationsContainer.accept(visitor, data);
        }
        getPackageDirective().accept(visitor, data);
        Iterator<T> it = getImports().iterator();
        while (it.hasNext()) {
            ((FirImport) it.next()).accept(visitor, data);
        }
        Iterator<T> it2 = getDeclarations().iterator();
        while (it2.hasNext()) {
            ((FirDeclaration) it2.next()).accept(visitor, data);
        }
    }

    @Override // org.jetbrains.kotlin.fir.FirPureAbstractElement, org.jetbrains.kotlin.fir.FirElement
    public <D> FirFileImpl transformChildren(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        transformAnnotationsContainer((FirTransformer) transformer, (Object) data);
        setPackageDirective((FirPackageDirective) getPackageDirective().transform(transformer, data));
        transformImports((FirTransformer) transformer, (Object) data);
        transformDeclarations((FirTransformer) transformer, (Object) data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirFile
    public <D> FirFileImpl transformAnnotationsContainer(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirFileAnnotationsContainer annotationsContainer = getAnnotationsContainer();
        setAnnotationsContainer(annotationsContainer != null ? (FirFileAnnotationsContainer) annotationsContainer.transform(transformer, data) : null);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirFile
    public <D> FirFileImpl transformImports(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTransformerUtilKt.transformInplace(getImports(), transformer, data);
        return this;
    }

    @Override // org.jetbrains.kotlin.fir.declarations.FirFile
    public <D> FirFileImpl transformDeclarations(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirTransformerUtilKt.transformInplace(getDeclarations(), transformer, data);
        return this;
    }
}
