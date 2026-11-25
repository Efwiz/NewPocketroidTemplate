package org.jetbrains.kotlin.fir.declarations;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.KtSourceFile;
import org.jetbrains.kotlin.KtSourceFileLinesMapping;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirFileAnnotationsContainer;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.FirPackageDirective;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.fir.symbols.impl.FirFileSymbol;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirVisitor;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirFile.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u008e\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J5\u00105\u001a\u0002H6\"\u0004\b\u0000\u00106\"\u0004\b\u0001\u001072\u0012\u00108\u001a\u000e\u0012\u0004\u0012\u0002H6\u0012\u0004\u0012\u0002H7092\u0006\u0010:\u001a\u0002H7H\u0016¢\u0006\u0002\u0010;J\u0016\u0010<\u001a\u00020=2\f\u0010>\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H&J3\u0010?\u001a\u0002H@\"\b\b\u0000\u0010@*\u00020A\"\u0004\b\u0001\u001072\f\u0010B\u001a\b\u0012\u0004\u0012\u0002H70C2\u0006\u0010:\u001a\u0002H7H\u0016¢\u0006\u0002\u0010DJ)\u0010E\u001a\u00020\u0000\"\u0004\b\u0000\u001072\f\u0010B\u001a\b\u0012\u0004\u0012\u0002H70C2\u0006\u0010:\u001a\u0002H7H&¢\u0006\u0002\u0010FJ)\u0010G\u001a\u00020\u0000\"\u0004\b\u0000\u001072\f\u0010B\u001a\b\u0012\u0004\u0012\u0002H70C2\u0006\u0010:\u001a\u0002H7H&¢\u0006\u0002\u0010FJ)\u0010H\u001a\u00020\u0000\"\u0004\b\u0000\u001072\f\u0010B\u001a\b\u0012\u0004\u0012\u0002H70C2\u0006\u0010:\u001a\u0002H7H&¢\u0006\u0002\u0010FJ)\u0010I\u001a\u00020\u0000\"\u0004\b\u0000\u001072\f\u0010B\u001a\b\u0012\u0004\u0012\u0002H70C2\u0006\u0010:\u001a\u0002H7H&¢\u0006\u0002\u0010FR\u0018\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u0004\u0018\u00010\tX¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0012\u0010\f\u001a\u00020\rX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0018\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00010\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0007R\u0018\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0007R\u0012\u0010\u0015\u001a\u00020\u0016X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0012\u0010\u0019\u001a\u00020\u001aX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001cR\u0012\u0010\u001d\u001a\u00020\u001eX¦\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010 R\u0012\u0010!\u001a\u00020\"X¦\u0004¢\u0006\u0006\u001a\u0004\b#\u0010$R\u0014\u0010%\u001a\u0004\u0018\u00010&X¦\u0004¢\u0006\u0006\u001a\u0004\b'\u0010(R\u0014\u0010)\u001a\u0004\u0018\u00010*X¦\u0004¢\u0006\u0006\u001a\u0004\b+\u0010,R\u0014\u0010-\u001a\u0004\u0018\u00010.X¦\u0004¢\u0006\u0006\u001a\u0004\b/\u00100R\u0012\u00101\u001a\u000202X¦\u0004¢\u0006\u0006\u001a\u0004\b3\u00104¨\u0006J"}, d2 = {"Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "()V", "annotations", "", "Lorg/jetbrains/kotlin/fir/expressions/FirAnnotation;", "getAnnotations", "()Ljava/util/List;", "annotationsContainer", "Lorg/jetbrains/kotlin/fir/FirFileAnnotationsContainer;", "getAnnotationsContainer", "()Lorg/jetbrains/kotlin/fir/FirFileAnnotationsContainer;", "attributes", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "getAttributes", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationAttributes;", "declarations", "getDeclarations", "imports", "Lorg/jetbrains/kotlin/fir/declarations/FirImport;", "getImports", "moduleData", "Lorg/jetbrains/kotlin/fir/FirModuleData;", "getModuleData", "()Lorg/jetbrains/kotlin/fir/FirModuleData;", "name", "", "getName", "()Ljava/lang/String;", "origin", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "getOrigin", "()Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationOrigin;", "packageDirective", "Lorg/jetbrains/kotlin/fir/FirPackageDirective;", "getPackageDirective", "()Lorg/jetbrains/kotlin/fir/FirPackageDirective;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/KtSourceElement;", "getSource", "()Lorg/jetbrains/kotlin/KtSourceElement;", "sourceFile", "Lorg/jetbrains/kotlin/KtSourceFile;", "getSourceFile", "()Lorg/jetbrains/kotlin/KtSourceFile;", "sourceFileLinesMapping", "Lorg/jetbrains/kotlin/KtSourceFileLinesMapping;", "getSourceFileLinesMapping", "()Lorg/jetbrains/kotlin/KtSourceFileLinesMapping;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirFileSymbol;", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirFileSymbol;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;", "data", "(Lorg/jetbrains/kotlin/fir/visitors/FirVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "replaceAnnotations", "", "newAnnotations", "transform", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", "transformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElement;", "transformAnnotations", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "transformAnnotationsContainer", "transformDeclarations", "transformImports", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirFile extends FirDeclaration {
    @Override // org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract List<FirAnnotation> getAnnotations();

    public abstract FirFileAnnotationsContainer getAnnotationsContainer();

    @Override // org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public abstract FirDeclarationAttributes getAttributes();

    public abstract List<FirDeclaration> getDeclarations();

    public abstract List<FirImport> getImports();

    @Override // org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState
    public abstract FirModuleData getModuleData();

    public abstract String getName();

    @Override // org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public abstract FirDeclarationOrigin getOrigin();

    public abstract FirPackageDirective getPackageDirective();

    @Override // org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState, org.jetbrains.kotlin.fir.FirElement
    public abstract KtSourceElement getSource();

    public abstract KtSourceFile getSourceFile();

    public abstract KtSourceFileLinesMapping getSourceFileLinesMapping();

    @Override // org.jetbrains.kotlin.fir.declarations.FirDeclaration
    public abstract FirFileSymbol getSymbol();

    @Override // org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract void replaceAnnotations(List<? extends FirAnnotation> newAnnotations);

    @Override // org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirAnnotationContainer
    public abstract <D> FirFile transformAnnotations(FirTransformer<? super D> transformer, D data);

    public abstract <D> FirFile transformAnnotationsContainer(FirTransformer<? super D> transformer, D data);

    public abstract <D> FirFile transformDeclarations(FirTransformer<? super D> transformer, D data);

    public abstract <D> FirFile transformImports(FirTransformer<? super D> transformer, D data);

    public FirFile() {
        super(null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState, org.jetbrains.kotlin.fir.FirElement
    public <R, D> R accept(FirVisitor<? extends R, ? super D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitFile(this, data);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.declarations.FirDeclaration, org.jetbrains.kotlin.fir.FirElementWithResolveState, org.jetbrains.kotlin.fir.FirElement
    public <E extends FirElement, D> E transform(FirTransformer<? super D> transformer, D data) {
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        FirFile firFileTransformFile = transformer.transformFile(this, data);
        Intrinsics.checkNotNull(firFileTransformFile, "null cannot be cast to non-null type E of org.jetbrains.kotlin.fir.declarations.FirFile.transform");
        return firFileTransformFile;
    }
}
