package org.jetbrains.kotlin.fir.resolve.transformers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.Visibilities;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousObject;
import org.jetbrains.kotlin.fir.declarations.FirBackingField;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirEnumEntry;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirResolvedDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirValueClassRepresentationKt;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.ValueClassesUtilsKt;
import org.jetbrains.kotlin.fir.declarations.utils.DeclarationAttributesKt;
import org.jetbrains.kotlin.fir.expressions.FirBlock;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.scopes.FirScope;
import org.jetbrains.kotlin.fir.symbols.FirLazyDeclarationResolverKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirAnonymousObjectSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeAliasSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.fir.utils.exceptions.FirExceptionUtilsKt;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirTransformerUtilKt;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.util.PrivateForInline;
import org.jetbrains.kotlin.utils.exceptions.ExceptionAttachmentBuilder;
import org.jetbrains.kotlin.utils.exceptions.KotlinIllegalArgumentExceptionWithAttachments;

/* JADX INFO: compiled from: FirStatusResolveTransformer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000è\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001B=\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0014\u0010\t\u001a\u0010\u0012\u0004\u0012\u00020\u000b\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\n\u0012\b\u0010\f\u001a\u0004\u0018\u00010\r¢\u0006\u0002\u0010\u000eJ\u0010\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020*H\u0002J\u0010\u0010+\u001a\u00020(2\u0006\u0010,\u001a\u00020-H\u0002J\u000e\u0010.\u001a\u00020(2\u0006\u0010,\u001a\u00020\u0011J\u0010\u0010/\u001a\u00020\u001b2\u0006\u0010,\u001a\u00020-H\u0014J\"\u00100\u001a\u0002012\u0006\u00102\u001a\u00020\u00112\f\u00103\u001a\b\u0012\u0004\u0012\u00020104H\u0086\bø\u0001\u0000J\u001a\u00105\u001a\u0002062\u0006\u00107\u001a\u0002082\b\u00109\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u0010:\u001a\u0002062\u0006\u0010;\u001a\u00020<2\b\u00109\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u0010=\u001a\u0002062\u0006\u00102\u001a\u00020\u00112\b\u00109\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u0010>\u001a\u0002062\u0006\u0010?\u001a\u00020\u00112\b\u00109\u001a\u0004\u0018\u00010\u0002H&J\u000e\u0010@\u001a\u00020(2\u0006\u0010?\u001a\u00020\u0011J\u001a\u0010A\u001a\u0002062\u0006\u0010B\u001a\u00020C2\b\u00109\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u0010D\u001a\u0002012\u0006\u0010E\u001a\u0002012\b\u00109\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u0010F\u001a\u0002012\u0006\u0010E\u001a\u0002012\b\u00109\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u0010G\u001a\u00020H2\u0006\u0010I\u001a\u00020H2\b\u00109\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u0010J\u001a\u0002062\u0006\u0010K\u001a\u00020L2\b\u00109\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u0010M\u001a\u0002062\u0006\u0010N\u001a\u00020O2\b\u00109\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u0010P\u001a\u00020Q2\u0006\u0010R\u001a\u00020Q2\b\u00109\u001a\u0004\u0018\u00010\u0002H\u0016J\u001c\u0010S\u001a\u00020(2\u0006\u0010T\u001a\u00020U2\f\u0010V\u001a\b\u0012\u0004\u0012\u00020U0WJ\u001a\u0010S\u001a\u0002062\u0006\u0010T\u001a\u00020U2\b\u00109\u001a\u0004\u0018\u00010\u0002H\u0016J(\u0010X\u001a\u00020(2\u0006\u0010Y\u001a\u00020Z2\u0006\u0010[\u001a\u00020U2\u000e\b\u0002\u0010\\\u001a\b\u0012\u0004\u0012\u00020\u00020WH\u0002J\u001a\u0010X\u001a\u0002062\u0006\u0010Y\u001a\u00020Z2\b\u00109\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u0010]\u001a\u0002062\u0006\u0010,\u001a\u00020-2\b\u00109\u001a\u0004\u0018\u00010\u0002H\u0016J(\u0010^\u001a\u00020(2\u0006\u0010_\u001a\u00020`2\f\u0010a\u001a\b\u0012\u0004\u0012\u00020`0W2\n\b\u0002\u00109\u001a\u0004\u0018\u00010\u0002J\u001a\u0010^\u001a\u0002062\u0006\u0010_\u001a\u00020`2\b\u00109\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u0010b\u001a\u0002062\u0006\u0010c\u001a\u00020d2\b\u00109\u001a\u0004\u0018\u00010\u0002H\u0016J\u001a\u0010e\u001a\u00020f2\u0006\u0010g\u001a\u00020h2\b\u00109\u001a\u0004\u0018\u00010\u0002H\u0016J\u000e\u0010i\u001a\u00020(2\u0006\u0010?\u001a\u00020\u0011J\u001a\u0010j\u001a\u0002062\u0006\u0010k\u001a\u00020l2\b\u00109\u001a\u0004\u0018\u00010\u0002H\u0016J\f\u0010m\u001a\u00020\u001b*\u000201H$J\f\u0010n\u001a\u00020\u001b*\u000201H$R\"\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u00108\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015R\u0019\u0010\u0016\u001a\u0004\u0018\u00010\u00118F¢\u0006\f\u0012\u0004\b\u0017\u0010\u0013\u001a\u0004\b\u0018\u0010\u0019R\u001c\u0010\t\u001a\u0010\u0012\u0004\u0012\u00020\u000b\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001a\u001a\u00020\u001b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u001cR\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0011\u0010#\u001a\u00020$¢\u0006\b\n\u0000\u001a\u0004\b%\u0010&\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006o"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/AbstractFirStatusResolveTransformer;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/FirAbstractTreeTransformer;", "Lorg/jetbrains/kotlin/fir/declarations/FirResolvedDeclarationStatus;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "statusComputationSession", "Lorg/jetbrains/kotlin/fir/resolve/transformers/StatusComputationSession;", "designationMapForLocalClasses", "", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "scopeForLocalClass", "Lorg/jetbrains/kotlin/fir/scopes/FirScope;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;Lorg/jetbrains/kotlin/fir/resolve/transformers/StatusComputationSession;Ljava/util/Map;Lorg/jetbrains/kotlin/fir/scopes/FirScope;)V", "classes", "", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "getClasses$annotations", "()V", "getClasses", "()Ljava/util/List;", "containingClass", "getContainingClass$annotations", "getContainingClass", "()Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "isTransformerForLocalDeclarations", "", "()Z", "getScopeSession", "()Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "getSession", "()Lorg/jetbrains/kotlin/fir/FirSession;", "getStatusComputationSession", "()Lorg/jetbrains/kotlin/fir/resolve/transformers/StatusComputationSession;", "statusResolver", "Lorg/jetbrains/kotlin/fir/resolve/transformers/FirStatusResolver;", "getStatusResolver", "()Lorg/jetbrains/kotlin/fir/resolve/transformers/FirStatusResolver;", "forceResolveStatusOfCorrespondingClass", "", "typeRef", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "forceResolveStatusesOfClass", "regularClass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "forceResolveStatusesOfSupertypes", "resolveClassForSuperType", "storeClass", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "klass", "computeResult", "Lkotlin/Function0;", "transformAnonymousObject", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "anonymousObject", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousObject;", "data", "transformBlock", "block", "Lorg/jetbrains/kotlin/fir/expressions/FirBlock;", "transformClass", "transformClassContent", "firClass", "transformClassStatus", "transformConstructor", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", "transformDeclaration", "declaration", "transformDeclarationContent", "transformDeclarationStatus", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "declarationStatus", "transformEnumEntry", "enumEntry", "Lorg/jetbrains/kotlin/fir/declarations/FirEnumEntry;", "transformField", "field", "Lorg/jetbrains/kotlin/fir/declarations/FirField;", "transformFile", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "file", "transformProperty", "property", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "overriddenProperties", "", "transformPropertyAccessor", "propertyAccessor", "Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "containingProperty", "overriddenStatuses", "transformRegularClass", "transformSimpleFunction", "simpleFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "overriddenFunctions", "transformTypeAlias", "typeAlias", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeAlias;", "transformTypeParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameterRef;", "typeParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeParameter;", "transformValueClassRepresentation", "transformValueParameter", "valueParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "needResolveMembers", "needResolveNestedClassifiers", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractFirStatusResolveTransformer extends FirAbstractTreeTransformer<FirResolvedDeclarationStatus> {
    private final List<FirClass> classes;
    private final Map<FirClassLikeDeclaration, FirClassLikeDeclaration> designationMapForLocalClasses;
    private final FirScope scopeForLocalClass;
    private final ScopeSession scopeSession;
    private final FirSession session;
    private final StatusComputationSession statusComputationSession;
    private final FirStatusResolver statusResolver;

    @PrivateForInline
    public static /* synthetic */ void getClasses$annotations() {
    }

    public static /* synthetic */ void getContainingClass$annotations() {
    }

    protected abstract boolean needResolveMembers(FirDeclaration firDeclaration);

    protected abstract boolean needResolveNestedClassifiers(FirDeclaration firDeclaration);

    public abstract FirStatement transformClassContent(FirClass firClass, FirResolvedDeclarationStatus data);

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.FirAbstractPhaseTransformer
    public final FirSession getSession() {
        return this.session;
    }

    public final ScopeSession getScopeSession() {
        return this.scopeSession;
    }

    public final StatusComputationSession getStatusComputationSession() {
        return this.statusComputationSession;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public AbstractFirStatusResolveTransformer(FirSession session, ScopeSession scopeSession, StatusComputationSession statusComputationSession, Map<FirClassLikeDeclaration, ? extends FirClassLikeDeclaration> designationMapForLocalClasses, FirScope firScope) {
        super(FirResolvePhase.STATUS);
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        Intrinsics.checkNotNullParameter(statusComputationSession, "statusComputationSession");
        Intrinsics.checkNotNullParameter(designationMapForLocalClasses, "designationMapForLocalClasses");
        this.session = session;
        this.scopeSession = scopeSession;
        this.statusComputationSession = statusComputationSession;
        this.designationMapForLocalClasses = designationMapForLocalClasses;
        this.scopeForLocalClass = firScope;
        this.classes = new ArrayList();
        this.statusResolver = new FirStatusResolver(session, scopeSession);
    }

    private final boolean isTransformerForLocalDeclarations() {
        return this.scopeForLocalClass != null;
    }

    public final List<FirClass> getClasses() {
        return this.classes;
    }

    public final FirStatusResolver getStatusResolver() {
        return this.statusResolver;
    }

    public final FirClass getContainingClass() {
        return (FirClass) CollectionsKt.lastOrNull((List) this.classes);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.FirAbstractPhaseTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirFile transformFile(FirFile file, FirResolvedDeclarationStatus data) {
        Intrinsics.checkNotNullParameter(file, "file");
        try {
            transformDeclarationContent(file, data);
            return file;
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(file.getModuleData().getSession()).handleExceptionOnFileAnalysis(file, th);
            throw null;
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirDeclarationStatus transformDeclarationStatus(FirDeclarationStatus declarationStatus, FirResolvedDeclarationStatus data) {
        Intrinsics.checkNotNullParameter(declarationStatus, "declarationStatus");
        return data != null ? data : declarationStatus;
    }

    public final FirDeclaration storeClass(FirClass klass, Function0<? extends FirDeclaration> computeResult) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(computeResult, "computeResult");
        getClasses().add(klass);
        FirDeclaration firDeclarationInvoke = computeResult.invoke();
        getClasses().remove(CollectionsKt.getLastIndex(getClasses()));
        return firDeclarationInvoke;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirDeclaration transformDeclaration(FirDeclaration declaration, FirResolvedDeclarationStatus data) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        FirSession firSession = this.session;
        try {
            if (declaration instanceof FirCallableDeclaration) {
                if (!(declaration instanceof FirFunction)) {
                    return declaration;
                }
                Iterator<FirValueParameter> it = ((FirFunction) declaration).getValueParameters().iterator();
                while (it.hasNext()) {
                    transformValueParameter(it.next(), data);
                }
                return declaration;
            }
            return (FirDeclaration) transformElement(declaration, data);
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(firSession).handleExceptionOnElementAnalysis(declaration, th);
            throw null;
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformTypeAlias(FirTypeAlias typeAlias, FirResolvedDeclarationStatus data) {
        Intrinsics.checkNotNullParameter(typeAlias, "typeAlias");
        FirSession firSession = this.session;
        try {
            Iterator<T> it = typeAlias.getTypeParameters().iterator();
            while (it.hasNext()) {
                transformDeclaration((FirDeclaration) it.next(), data);
            }
            typeAlias.transformStatus((FirTransformer<? super FirResolvedDeclarationStatus>) this, this.statusResolver.resolveStatus(typeAlias, getContainingClass(), false));
            FirDeclaration firDeclarationTransformDeclaration = transformDeclaration((FirDeclaration) typeAlias, data);
            Intrinsics.checkNotNull(firDeclarationTransformDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirTypeAlias");
            return (FirTypeAlias) firDeclarationTransformDeclaration;
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(firSession).handleExceptionOnElementAnalysis(typeAlias, th);
            throw null;
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformRegularClass(FirRegularClass regularClass, FirResolvedDeclarationStatus data) {
        Intrinsics.checkNotNullParameter(regularClass, "regularClass");
        FirSession firSession = this.session;
        try {
            return transformClassContent(regularClass, data);
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(firSession).handleExceptionOnElementAnalysis(regularClass, th);
            throw null;
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformAnonymousObject(FirAnonymousObject anonymousObject, FirResolvedDeclarationStatus data) {
        Intrinsics.checkNotNullParameter(anonymousObject, "anonymousObject");
        FirSession firSession = this.session;
        try {
            return transformClassContent(anonymousObject, data);
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(firSession).handleExceptionOnElementAnalysis(anonymousObject, th);
            throw null;
        }
    }

    public FirDeclaration transformDeclarationContent(FirDeclaration declaration, FirResolvedDeclarationStatus data) {
        List<FirDeclaration> declarations;
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (declaration instanceof FirRegularClass) {
            declarations = ((FirRegularClass) declaration).getDeclarations();
        } else if (declaration instanceof FirAnonymousObject) {
            declarations = ((FirAnonymousObject) declaration).getDeclarations();
        } else {
            if (!(declaration instanceof FirFile)) {
                KotlinIllegalArgumentExceptionWithAttachments kotlinIllegalArgumentExceptionWithAttachments = new KotlinIllegalArgumentExceptionWithAttachments("Unsupported declaration: " + declaration.getClass(), null);
                ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
                FirExceptionUtilsKt.withFirEntry(exceptionAttachmentBuilder, "declaration", declaration);
                kotlinIllegalArgumentExceptionWithAttachments.withAttachment("info.txt", exceptionAttachmentBuilder.buildString());
                throw kotlinIllegalArgumentExceptionWithAttachments;
            }
            declarations = ((FirFile) declaration).getDeclarations();
        }
        if (needResolveMembers(declaration)) {
            for (FirDeclaration firDeclaration : declarations) {
                if (!(firDeclaration instanceof FirClassLikeDeclaration)) {
                    FirTransformerUtilKt.transformSingle(firDeclaration, this, data);
                }
            }
        }
        if (needResolveNestedClassifiers(declaration)) {
            for (FirDeclaration firDeclaration2 : declarations) {
                if (firDeclaration2 instanceof FirClassLikeDeclaration) {
                    FirTransformerUtilKt.transformSingle(firDeclaration2, this, data);
                }
            }
        }
        return declaration;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformClass(FirClass klass, FirResolvedDeclarationStatus data) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        FirSession firSession = this.session;
        try {
            getClasses().add(klass);
            Iterator<T> it = klass.getTypeParameters().iterator();
            while (it.hasNext()) {
                FirTransformerUtilKt.transformSingle((FirTypeParameterRef) it.next(), this, data);
            }
            FirAnnotationContainer firAnnotationContainerTransformDeclarationContent = transformDeclarationContent(klass, data);
            getClasses().remove(CollectionsKt.getLastIndex(getClasses()));
            Intrinsics.checkNotNull(firAnnotationContainerTransformDeclarationContent, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirStatement");
            return (FirStatement) firAnnotationContainerTransformDeclarationContent;
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(firSession).handleExceptionOnElementAnalysis(klass, th);
            throw null;
        }
    }

    public final void transformValueClassRepresentation(FirClass firClass) {
        Intrinsics.checkNotNullParameter(firClass, "firClass");
        if ((firClass instanceof FirRegularClass) && firClass.getStatus().isInline()) {
            FirRegularClass firRegularClass = (FirRegularClass) firClass;
            FirValueClassRepresentationKt.setValueClassRepresentation(firRegularClass, ValueClassesUtilsKt.computeValueClassRepresentation(firRegularClass, this.session));
        }
    }

    public final void transformClassStatus(FirClass firClass) {
        Intrinsics.checkNotNullParameter(firClass, "firClass");
        firClass.transformStatus((FirTransformer<? super FirResolvedDeclarationStatus>) this, this.statusResolver.resolveStatus(firClass, getContainingClass(), false));
    }

    public final void forceResolveStatusesOfSupertypes(FirClass regularClass) {
        Intrinsics.checkNotNullParameter(regularClass, "regularClass");
        Iterator<FirTypeRef> it = regularClass.getSuperTypeRefs().iterator();
        while (it.hasNext()) {
            forceResolveStatusOfCorrespondingClass(it.next());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void forceResolveStatusOfCorrespondingClass(FirTypeRef typeRef) {
        FirClassifierSymbol<?> symbol = TypeUtilsKt.toSymbol(FirTypeUtilsKt.getConeType(typeRef), this.session);
        if (isTransformerForLocalDeclarations()) {
            if (symbol instanceof FirClassSymbol) {
                FirLazyDeclarationResolverKt.lazyResolveToPhaseWithCallableMembers((FirClassSymbol<?>) symbol, FirResolvePhase.STATUS);
            } else if (symbol != null) {
                FirLazyDeclarationResolverKt.lazyResolveToPhase(symbol, FirResolvePhase.STATUS);
            }
        } else if (symbol != null) {
            FirLazyDeclarationResolverKt.lazyResolveToPhase(symbol, FirResolvePhase.STATUS.getPrevious());
        }
        if (symbol instanceof FirRegularClassSymbol) {
            forceResolveStatusesOfClass((FirRegularClass) ((FirRegularClassSymbol) symbol).getFir());
        } else if (symbol instanceof FirTypeAliasSymbol) {
            forceResolveStatusOfCorrespondingClass(((FirTypeAlias) ((FirTypeAliasSymbol) symbol).getFir()).getExpandedTypeRef());
        } else {
            if (symbol instanceof FirTypeParameterSymbol) {
                return;
            }
            boolean z = symbol instanceof FirAnonymousObjectSymbol;
        }
    }

    private final void forceResolveStatusesOfClass(FirRegularClass regularClass) {
        if ((regularClass.getOrigin() instanceof FirDeclarationOrigin.Java) || Intrinsics.areEqual(regularClass.getOrigin(), FirDeclarationOrigin.Precompiled.INSTANCE)) {
            FirRegularClass firRegularClass = regularClass;
            if (this.statusComputationSession.get(firRegularClass).getRequiresComputation()) {
                this.statusComputationSession.startComputing(firRegularClass);
                forceResolveStatusesOfSupertypes(firRegularClass);
                this.statusComputationSession.endComputing(firRegularClass);
                return;
            }
            return;
        }
        if (Intrinsics.areEqual(regularClass.getOrigin(), FirDeclarationOrigin.Source.INSTANCE)) {
            FirRegularClass firRegularClass2 = regularClass;
            if (this.statusComputationSession.get(firRegularClass2).getRequiresComputation() && resolveClassForSuperType(regularClass)) {
                this.statusComputationSession.endComputing(firRegularClass2);
            }
        }
    }

    protected boolean resolveClassForSuperType(FirRegularClass regularClass) {
        Intrinsics.checkNotNullParameter(regularClass, "regularClass");
        DesignationState designationStateCreate = DesignationState.INSTANCE.create(regularClass.getSymbol(), this.designationMapForLocalClasses, false);
        if (designationStateCreate == null) {
            return false;
        }
        FirTransformerUtilKt.transformSingle(designationStateCreate.getFirstDeclaration(), new FirDesignatedStatusResolveTransformer(this.session, this.scopeSession, designationStateCreate, this.statusComputationSession, this.designationMapForLocalClasses, this.scopeForLocalClass), null);
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ void transformPropertyAccessor$default(AbstractFirStatusResolveTransformer abstractFirStatusResolveTransformer, FirPropertyAccessor firPropertyAccessor, FirProperty firProperty, List list, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: transformPropertyAccessor");
        }
        if ((i & 4) != 0) {
            list = CollectionsKt.emptyList();
        }
        abstractFirStatusResolveTransformer.transformPropertyAccessor(firPropertyAccessor, firProperty, list);
    }

    private final void transformPropertyAccessor(FirPropertyAccessor propertyAccessor, FirProperty containingProperty, List<? extends FirResolvedDeclarationStatus> overriddenStatuses) {
        FirSession firSession = this.session;
        try {
            propertyAccessor.transformStatus((FirTransformer<? super FirResolvedDeclarationStatus>) this, this.statusResolver.resolveStatus(propertyAccessor, getContainingClass(), containingProperty, false, overriddenStatuses));
            propertyAccessor.transformValueParameters((FirTransformer<? super Object>) this, (Object) null);
            Unit unit = Unit.INSTANCE;
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(firSession).handleExceptionOnElementAnalysis(propertyAccessor, th);
            throw null;
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformConstructor(FirConstructor constructor, FirResolvedDeclarationStatus data) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        FirSession firSession = this.session;
        try {
            constructor.transformStatus((FirTransformer<? super FirResolvedDeclarationStatus>) this, this.statusResolver.resolveStatus(constructor, getContainingClass(), false));
            FirAnnotationContainer firAnnotationContainerTransformDeclaration = transformDeclaration((FirDeclaration) constructor, data);
            Intrinsics.checkNotNull(firAnnotationContainerTransformDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirStatement");
            return (FirStatement) firAnnotationContainerTransformDeclaration;
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(firSession).handleExceptionOnElementAnalysis(constructor, th);
            throw null;
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformSimpleFunction(FirSimpleFunction simpleFunction, FirResolvedDeclarationStatus data) {
        Intrinsics.checkNotNullParameter(simpleFunction, "simpleFunction");
        FirSession firSession = this.session;
        try {
            transformSimpleFunction(simpleFunction, this.statusResolver.getOverriddenFunctions(simpleFunction, getContainingClass()), data);
            return simpleFunction;
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(firSession).handleExceptionOnElementAnalysis(simpleFunction, th);
            throw null;
        }
    }

    public static /* synthetic */ void transformSimpleFunction$default(AbstractFirStatusResolveTransformer abstractFirStatusResolveTransformer, FirSimpleFunction firSimpleFunction, List list, FirResolvedDeclarationStatus firResolvedDeclarationStatus, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: transformSimpleFunction");
        }
        if ((i & 4) != 0) {
            firResolvedDeclarationStatus = null;
        }
        abstractFirStatusResolveTransformer.transformSimpleFunction(firSimpleFunction, list, firResolvedDeclarationStatus);
    }

    public final void transformSimpleFunction(FirSimpleFunction simpleFunction, List<? extends FirSimpleFunction> overriddenFunctions, FirResolvedDeclarationStatus data) {
        Intrinsics.checkNotNullParameter(simpleFunction, "simpleFunction");
        Intrinsics.checkNotNullParameter(overriddenFunctions, "overriddenFunctions");
        FirStatusResolver firStatusResolver = this.statusResolver;
        FirClass containingClass = getContainingClass();
        List<? extends FirSimpleFunction> list = overriddenFunctions;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            FirDeclarationStatus status = ((FirSimpleFunction) it.next()).getStatus();
            Intrinsics.checkNotNull(status, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirResolvedDeclarationStatus");
            arrayList.add((FirResolvedDeclarationStatus) status);
        }
        simpleFunction.transformStatus((FirTransformer<? super FirResolvedDeclarationStatus>) this, firStatusResolver.resolveStatus(simpleFunction, containingClass, false, (List<? extends FirResolvedDeclarationStatus>) arrayList));
        FirAnnotationContainer firAnnotationContainerTransformDeclaration = transformDeclaration((FirDeclaration) simpleFunction, data);
        Intrinsics.checkNotNull(firAnnotationContainerTransformDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirStatement");
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformProperty(FirProperty property, FirResolvedDeclarationStatus data) {
        Intrinsics.checkNotNullParameter(property, "property");
        FirSession firSession = this.session;
        try {
            transformProperty(property, (List<? extends FirProperty>) this.statusResolver.getOverriddenProperties(property, getContainingClass()));
            return property;
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(firSession).handleExceptionOnElementAnalysis(property, th);
            throw null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void transformProperty(FirProperty property, List<? extends FirProperty> overriddenProperties) {
        FirResolvedDeclarationStatus firResolvedDeclarationStatus;
        Intrinsics.checkNotNullParameter(property, "property");
        Intrinsics.checkNotNullParameter(overriddenProperties, "overriddenProperties");
        List<? extends FirProperty> list = overriddenProperties;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            FirDeclarationStatus status = ((FirProperty) it.next()).getStatus();
            Intrinsics.checkNotNull(status, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirResolvedDeclarationStatus");
            arrayList.add((FirResolvedDeclarationStatus) status);
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList();
        Iterator<T> it2 = list.iterator();
        while (it2.hasNext()) {
            FirPropertyAccessor setter = ((FirProperty) it2.next()).getSetter();
            if (setter == null) {
                firResolvedDeclarationStatus = null;
            } else {
                FirDeclarationStatus status2 = setter.getStatus();
                Intrinsics.checkNotNull(status2, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirResolvedDeclarationStatus");
                firResolvedDeclarationStatus = (FirResolvedDeclarationStatus) status2;
            }
            if (firResolvedDeclarationStatus != null) {
                arrayList3.add(firResolvedDeclarationStatus);
            }
        }
        ArrayList arrayList4 = arrayList3;
        AbstractFirStatusResolveTransformer abstractFirStatusResolveTransformer = this;
        property.transformStatus((FirTransformer<? super FirResolvedDeclarationStatus>) abstractFirStatusResolveTransformer, this.statusResolver.resolveStatus(property, getContainingClass(), false, (List<? extends FirResolvedDeclarationStatus>) arrayList2));
        FirPropertyAccessor getter = property.getGetter();
        if (getter != null) {
            transformPropertyAccessor$default(this, getter, property, null, 4, null);
        }
        FirPropertyAccessor setter2 = property.getSetter();
        if (setter2 != null) {
            transformPropertyAccessor(setter2, property, arrayList4);
        }
        FirBackingField backingField = property.getBackingField();
        if (backingField != null) {
            backingField.transformStatus((FirTransformer<? super FirResolvedDeclarationStatus>) abstractFirStatusResolveTransformer, this.statusResolver.resolveStatus((FirDeclaration) backingField, getContainingClass(), property, false));
        }
        FirNamedFunctionSymbol componentFunctionSymbol = DeclarationAttributesKt.getComponentFunctionSymbol(property);
        if (componentFunctionSymbol == null || !Intrinsics.areEqual(((FirSimpleFunction) componentFunctionSymbol.getFir()).getStatus().getVisibility(), Visibilities.Unknown.INSTANCE)) {
            return;
        }
        FirSimpleFunction firSimpleFunction = (FirSimpleFunction) componentFunctionSymbol.getFir();
        FirDeclarationStatus status3 = ((FirSimpleFunction) componentFunctionSymbol.getFir()).getStatus();
        firSimpleFunction.replaceStatus(UtilsKt.copy(status3, (2097147 & 1) != 0 ? status3.getVisibility() : property.getStatus().getVisibility(), (2097147 & 2) != 0 ? status3.getModality() : null, (2097147 & 4) != 0 ? status3.isExpect() : false, (2097147 & 8) != 0 ? status3.isActual() : false, (2097147 & 16) != 0 ? status3.isOverride() : false, (2097147 & 32) != 0 ? status3.isOperator() : false, (2097147 & 64) != 0 ? status3.isInfix() : false, (2097147 & 128) != 0 ? status3.isInline() : false, (2097147 & 256) != 0 ? status3.isTailRec() : false, (2097147 & 512) != 0 ? status3.isExternal() : false, (2097147 & 1024) != 0 ? status3.isConst() : false, (2097147 & 2048) != 0 ? status3.isLateInit() : false, (2097147 & 4096) != 0 ? status3.isInner() : false, (2097147 & 8192) != 0 ? status3.isCompanion() : false, (2097147 & 16384) != 0 ? status3.isData() : false, (2097147 & 32768) != 0 ? status3.isSuspend() : false, (2097147 & 65536) != 0 ? status3.isStatic() : false, (2097147 & 131072) != 0 ? status3.isFromSealedClass() : false, (2097147 & 262144) != 0 ? status3.isFromEnumClass() : false, (2097147 & 524288) != 0 ? status3.isFun() : false, (2097147 & 1048576) != 0 ? status3.getHasStableParameterNames() : false));
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformField(FirField field, FirResolvedDeclarationStatus data) {
        Intrinsics.checkNotNullParameter(field, "field");
        FirSession firSession = this.session;
        try {
            field.transformStatus((FirTransformer<? super FirResolvedDeclarationStatus>) this, this.statusResolver.resolveStatus(field, getContainingClass(), false));
            FirDeclaration firDeclarationTransformDeclaration = transformDeclaration((FirDeclaration) field, data);
            Intrinsics.checkNotNull(firDeclarationTransformDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirField");
            return (FirField) firDeclarationTransformDeclaration;
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(firSession).handleExceptionOnElementAnalysis(field, th);
            throw null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformPropertyAccessor(FirPropertyAccessor propertyAccessor, FirResolvedDeclarationStatus data) {
        Intrinsics.checkNotNullParameter(propertyAccessor, "propertyAccessor");
        transformProperty((FirProperty) propertyAccessor.getPropertySymbol().getFir(), data);
        return propertyAccessor;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformEnumEntry(FirEnumEntry enumEntry, FirResolvedDeclarationStatus data) {
        Intrinsics.checkNotNullParameter(enumEntry, "enumEntry");
        FirSession firSession = this.session;
        try {
            enumEntry.transformStatus((FirTransformer<? super FirResolvedDeclarationStatus>) this, this.statusResolver.resolveStatus(enumEntry, getContainingClass(), false));
            FirDeclaration firDeclarationTransformDeclaration = transformDeclaration((FirDeclaration) enumEntry, data);
            Intrinsics.checkNotNull(firDeclarationTransformDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirEnumEntry");
            return (FirEnumEntry) firDeclarationTransformDeclaration;
        } catch (Throwable th) {
            UtilsKt.getExceptionHandler(firSession).handleExceptionOnElementAnalysis(enumEntry, th);
            throw null;
        }
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformValueParameter(FirValueParameter valueParameter, FirResolvedDeclarationStatus data) {
        Intrinsics.checkNotNullParameter(valueParameter, "valueParameter");
        FirAnnotationContainer firAnnotationContainerTransformDeclaration = transformDeclaration((FirDeclaration) valueParameter, data);
        Intrinsics.checkNotNull(firAnnotationContainerTransformDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.expressions.FirStatement");
        return (FirStatement) firAnnotationContainerTransformDeclaration;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirTypeParameterRef transformTypeParameter(FirTypeParameter typeParameter, FirResolvedDeclarationStatus data) {
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        FirDeclaration firDeclarationTransformDeclaration = transformDeclaration((FirDeclaration) typeParameter, data);
        Intrinsics.checkNotNull(firDeclarationTransformDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirTypeParameter");
        return (FirTypeParameter) firDeclarationTransformDeclaration;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformBlock(FirBlock block, FirResolvedDeclarationStatus data) {
        Intrinsics.checkNotNullParameter(block, "block");
        return block;
    }
}
