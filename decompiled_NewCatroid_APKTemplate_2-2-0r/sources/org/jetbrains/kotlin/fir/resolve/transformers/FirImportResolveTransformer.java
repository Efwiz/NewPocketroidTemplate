package org.jetbrains.kotlin.fir.resolve.transformers;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirLookupTrackerComponent;
import org.jetbrains.kotlin.fir.FirLookupTrackerComponentKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirImport;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.builder.FirErrorImportBuilder;
import org.jetbrains.kotlin.fir.declarations.builder.FirResolvedImportBuilder;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeImportFromSingleton;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirImportResolveTransformer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0016\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005B\u0017\b\u0004\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ)\u0010\u0013\u001a\u0002H\u0014\"\b\b\u0000\u0010\u0014*\u00020\u00152\u0006\u0010\u0016\u001a\u0002H\u00142\b\u0010\u0017\u001a\u0004\u0018\u00010\u0002H\u0016¢\u0006\u0002\u0010\u0018J\u001a\u0010\u0019\u001a\u00020\n2\u0006\u0010\u001a\u001a\u00020\n2\b\u0010\u0017\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001c2\b\u0010\u0017\u001a\u0004\u0018\u00010\u0002H\u0016J\u0018\u0010\u001e\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020\u00112\u0006\u0010 \u001a\u00020\u001cH\u0002R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u000f\u001a\u00020\u0010*\u00020\u00118TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0012¨\u0006!"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/FirImportResolveTransformer;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/FirAbstractTreeTransformer;", "", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "(Lorg/jetbrains/kotlin/fir/FirSession;)V", TypedValues.CycleType.S_WAVE_PHASE, "Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/declarations/FirResolvePhase;)V", "currentFile", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "symbolProvider", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;", "isAcceptable", "", "Lorg/jetbrains/kotlin/name/FqName;", "(Lorg/jetbrains/kotlin/name/FqName;)Z", "transformElement", ExifInterface.LONGITUDE_EAST, "Lorg/jetbrains/kotlin/fir/FirElement;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "data", "(Lorg/jetbrains/kotlin/fir/FirElement;Ljava/lang/Object;)Lorg/jetbrains/kotlin/fir/FirElement;", "transformFile", "file", "transformImport", "Lorg/jetbrains/kotlin/fir/declarations/FirImport;", PsiKeyword.IMPORT, "transformImportForFqName", "fqName", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class FirImportResolveTransformer extends FirAbstractTreeTransformer<Object> {
    private FirFile currentFile;
    private final FirSession session;
    private final FirSymbolProvider symbolProvider;

    protected boolean isAcceptable(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "<this>");
        return true;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.FirAbstractTreeTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public <E extends FirElement> E transformElement(E element, Object data) {
        Intrinsics.checkNotNullParameter(element, "element");
        return element;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.FirAbstractPhaseTransformer
    public final FirSession getSession() {
        return this.session;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    protected FirImportResolveTransformer(FirSession session, FirResolvePhase phase) {
        super(phase);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(phase, "phase");
        this.session = session;
        this.symbolProvider = FirSymbolProviderKt.getSymbolProvider(session);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public FirImportResolveTransformer(FirSession session) {
        this(session, FirResolvePhase.IMPORTS);
        Intrinsics.checkNotNullParameter(session, "session");
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.FirAbstractPhaseTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirFile transformFile(FirFile file, Object data) {
        Intrinsics.checkNotNullParameter(file, "file");
        checkSessionConsistency(file);
        try {
            FirFile firFile = this.currentFile;
            this.currentFile = file;
            try {
                file.transformChildren(this, null);
                return file;
            } finally {
                this.currentFile = firFile;
            }
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(file.getModuleData().getSession()).handleExceptionOnFileAnalysis(file, th);
            throw null;
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirImport transformImport(FirImport firImport, Object data) {
        FirLookupTrackerComponent lookupTracker;
        Intrinsics.checkNotNullParameter(firImport, "import");
        FqName importedFqName = firImport.getImportedFqName();
        if (importedFqName == null) {
            return firImport;
        }
        if (importedFqName.isRoot()) {
            importedFqName = null;
        }
        if (importedFqName == null || !isAcceptable(importedFqName)) {
            return firImport;
        }
        if (firImport.getIsAllUnder()) {
            return transformImportForFqName(importedFqName, firImport);
        }
        FqName fqNameParent = importedFqName.parent();
        Intrinsics.checkNotNullExpressionValue(fqNameParent, "parent(...)");
        FirFile firFile = this.currentFile;
        if (firFile != null && (lookupTracker = FirLookupTrackerComponentKt.getLookupTracker(this.session)) != null) {
            Name nameShortName = importedFqName.shortName();
            Intrinsics.checkNotNullExpressionValue(nameShortName, "shortName(...)");
            String strAsString = fqNameParent.asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            lookupTracker.recordLookup(nameShortName, strAsString, firImport.getSource(), firFile.getSource());
        }
        return transformImportForFqName(fqNameParent, firImport);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final FirImport transformImportForFqName(FqName fqName, FirImport delegate) {
        ClassKind classKind;
        PackageResolutionResult packageResolutionResultResolveToPackageOrClass = ImportUtilsKt.resolveToPackageOrClass(this.symbolProvider, fqName);
        if (!(packageResolutionResultResolveToPackageOrClass instanceof PackageResolutionResult.Error)) {
            if (packageResolutionResultResolveToPackageOrClass instanceof PackageResolutionResult.PackageOrClass) {
                PackageResolutionResult.PackageOrClass packageOrClass = (PackageResolutionResult.PackageOrClass) packageResolutionResultResolveToPackageOrClass;
                FqName packageFqName = packageOrClass.getPackageFqName();
                FqName relativeClassFqName = packageOrClass.getRelativeClassFqName();
                FirClassLikeSymbol<?> firClassLikeSymbolComponent3 = packageOrClass.component3();
                FirClassLikeDeclaration firClassLikeDeclaration = firClassLikeSymbolComponent3 != null ? (FirClassLikeDeclaration) firClassLikeSymbolComponent3.getFir() : null;
                FirRegularClass firRegularClass = firClassLikeDeclaration instanceof FirRegularClass ? (FirRegularClass) firClassLikeDeclaration : null;
                if (delegate.getIsAllUnder() && firRegularClass != null && (classKind = firRegularClass.getClassKind()) != null && classKind.isSingleton()) {
                    FirErrorImportBuilder firErrorImportBuilder = new FirErrorImportBuilder();
                    firErrorImportBuilder.setDelegate(delegate);
                    firErrorImportBuilder.setDiagnostic(new ConeImportFromSingleton(firRegularClass.getName()));
                    return firErrorImportBuilder.build();
                }
                FirResolvedImportBuilder firResolvedImportBuilder = new FirResolvedImportBuilder();
                firResolvedImportBuilder.setDelegate(delegate);
                firResolvedImportBuilder.setPackageFqName(packageFqName);
                firResolvedImportBuilder.setRelativeParentClassName(relativeClassFqName);
                return firResolvedImportBuilder.build();
            }
            throw new NoWhenBranchMatchedException();
        }
        FirErrorImportBuilder firErrorImportBuilder2 = new FirErrorImportBuilder();
        firErrorImportBuilder2.setDelegate(delegate);
        firErrorImportBuilder2.setDiagnostic(((PackageResolutionResult.Error) packageResolutionResultResolveToPackageOrClass).getDiagnostic());
        return firErrorImportBuilder2.build();
    }
}
