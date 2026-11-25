package org.jetbrains.kotlin.fir.resolve.transformers;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArrayDeque;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.KtSourceElement;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirLookupTrackerComponent;
import org.jetbrains.kotlin.fir.FirLookupTrackerComponentKt;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousObject;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.diagnostics.DiagnosticKind;
import org.jetbrains.kotlin.fir.extensions.FirExtensionServiceKt;
import org.jetbrains.kotlin.fir.extensions.FirSupertypeGenerationExtension;
import org.jetbrains.kotlin.fir.extensions.FirSupertypeGenerationExtensionKt;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.ScopeSession;
import org.jetbrains.kotlin.fir.resolve.dfa.cfg.ControlFlowGraphBuilderKt;
import org.jetbrains.kotlin.fir.resolve.diagnostics.ConeTypeParameterSupertype;
import org.jetbrains.kotlin.fir.resolve.providers.FirProviderKt;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.LocalClassesNavigationInfo;
import org.jetbrains.kotlin.fir.scopes.FirScope;
import org.jetbrains.kotlin.fir.scopes.ImportingScopesKt;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeAliasSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeParameterType;
import org.jetbrains.kotlin.fir.types.ConeTypeProjection;
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirUserTypeRef;
import org.jetbrains.kotlin.fir.types.TypeUtilsKt;
import org.jetbrains.kotlin.fir.types.builder.FirErrorTypeRefBuilder;
import org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.kotlinx.collections.immutable.ExtensionsKt;
import org.jetbrains.kotlin.kotlinx.collections.immutable.PersistentList;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.types.model.TypeArgumentMarker;
import org.jetbrains.kotlin.util.PrivateForInline;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirSupertypesResolution.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000Ò\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0016\u0018\u00002\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0001:\u0001dBW\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0010\b\u0002\u0010\n\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000b\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0010\u0012\u000e\b\u0002\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012¢\u0006\u0002\u0010\u0014J4\u0010#\u001a\u00020\u00022\u0006\u0010$\u001a\u00020%2\f\u0010&\u001a\b\u0012\u0004\u0012\u00020(0'2\f\u0010)\u001a\b\u0012\u0004\u0012\u00020+0*2\u0006\u0010,\u001a\u00020+H\u0002J&\u0010-\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u0010.\u001a\u00020\u00172\u0006\u0010/\u001a\u0002002\u0006\u00101\u001a\u000200H\u0002J\u001a\u00102\u001a\u0004\u0018\u00010%2\u0006\u00103\u001a\u00020\u00052\u0006\u00104\u001a\u000205H\u0002J\u0016\u00106\u001a\u0004\u0018\u00010\u00102\n\u00107\u001a\u0006\u0012\u0002\b\u000308H\u0002J\u001a\u00109\u001a\f\u0012\u0004\u0012\u00020\f0\u000bj\u0002`:2\u0006\u0010;\u001a\u00020\u0010H\u0002J\u001a\u0010<\u001a\f\u0012\u0004\u0012\u00020\f0\u000bj\u0002`:2\u0006\u0010$\u001a\u00020\u0017H\u0002J\"\u0010=\u001a\f\u0012\u0004\u0012\u00020\f0\u000bj\u0002`:2\u0006\u0010$\u001a\u00020\u00172\u0006\u00101\u001a\u000200H\u0002J\u001e\u0010>\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u0010?\u001a\u00020%2\u0006\u00101\u001a\u000200H\u0002J.\u0010@\u001a\u00020\u00022\u0006\u0010?\u001a\u00020%2\f\u0010&\u001a\b\u0012\u0004\u0012\u00020A0\u00122\u000e\b\u0002\u0010B\u001a\b\u0012\u0004\u0012\u00020%0CH\u0002J\u0010\u0010D\u001a\u00020\u00022\u0006\u0010.\u001a\u00020\u0017H\u0014J<\u0010E\u001a\b\u0012\u0004\u0012\u00020(0\u00122\u0006\u0010?\u001a\u00020%2$\u0010F\u001a \u0012\n\u0012\b\u0012\u0004\u0012\u00020+0*\u0012\u0004\u0012\u00020+\u0012\n\u0012\b\u0012\u0004\u0012\u00020(0\u00120GH\u0002J\"\u0010E\u001a\b\u0012\u0004\u0012\u00020(0\u00122\u0006\u0010?\u001a\u00020%2\f\u0010&\u001a\b\u0012\u0004\u0012\u00020A0\u0012J$\u0010H\u001a\b\u0012\u0004\u0012\u00020(0\u00122\u0006\u0010I\u001a\u00020J2\u0006\u0010K\u001a\u00020A2\u0006\u0010L\u001a\u000200J\u001a\u0010M\u001a\u00020\u00022\u0006\u0010N\u001a\u00020O2\b\u0010P\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010Q\u001a\u00020\u00022\u0006\u0010R\u001a\u00020\u00132\b\u0010P\u001a\u0004\u0018\u00010\u0003H\u0002J\u001a\u0010S\u001a\u00020\u00022\u0006\u0010T\u001a\u00020U2\b\u0010P\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010V\u001a\u00020\u00022\u0006\u0010;\u001a\u00020\u00102\b\u0010P\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010W\u001a\u00020\u00022\u0006\u0010X\u001a\u00020Y2\b\u0010P\u001a\u0004\u0018\u00010\u0003H\u0016J\u001a\u0010Z\u001a\u00020\u00022\u0006\u0010I\u001a\u00020J2\b\u0010P\u001a\u0004\u0018\u00010\u0003H\u0016J(\u0010[\u001a\u00020\u0002\"\u0004\b\u0000\u0010\\2\u0006\u0010]\u001a\u00020\u00172\f\u0010^\u001a\b\u0012\u0004\u0012\u0002H\\0_H\u0086\bø\u0001\u0000J-\u0010`\u001a\u0002Ha\"\u0004\b\u0000\u0010a2\u0006\u0010;\u001a\u00020\u00102\f\u0010b\u001a\b\u0012\u0004\u0012\u0002Ha0_H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010cR\"\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u00168\u0006X\u0087\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001bR\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\n\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\u0012X\u0082\u0004¢\u0006\u0002\n\u0000R&\u0010\u000f\u001a\u0004\u0018\u00010\u00108\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b\u001e\u0010\u0019\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006e"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/FirSupertypeResolverVisitor;", "Lorg/jetbrains/kotlin/fir/visitors/FirDefaultVisitor;", "", "", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "supertypeComputationSession", "Lorg/jetbrains/kotlin/fir/resolve/transformers/SupertypeComputationSession;", "scopeSession", "Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;", "scopeForLocalClass", "Lorg/jetbrains/kotlin/kotlinx/collections/immutable/PersistentList;", "Lorg/jetbrains/kotlin/fir/scopes/FirScope;", "localClassesNavigationInfo", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/LocalClassesNavigationInfo;", "useSiteFile", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "containingDeclarations", "", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/resolve/transformers/SupertypeComputationSession;Lorg/jetbrains/kotlin/fir/resolve/ScopeSession;Lkotlinx/collections/immutable/PersistentList;Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/LocalClassesNavigationInfo;Lorg/jetbrains/kotlin/fir/declarations/FirFile;Ljava/util/List;)V", "classDeclarationsStack", "Lkotlin/collections/ArrayDeque;", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "getClassDeclarationsStack$annotations", "()V", "getClassDeclarationsStack", "()Lkotlin/collections/ArrayDeque;", "supertypeGenerationExtensions", "Lorg/jetbrains/kotlin/fir/extensions/FirSupertypeGenerationExtension;", "getUseSiteFile$annotations", "getUseSiteFile", "()Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "setUseSiteFile", "(Lorg/jetbrains/kotlin/fir/declarations/FirFile;)V", "addSupertypesFromExtensions", "klass", "Lorg/jetbrains/kotlin/fir/declarations/FirClassLikeDeclaration;", "supertypeRefs", "", "Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", "typeResolveTransformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/ScopeClassDeclaration;", "scopeDeclaration", "calculateScopes", "outerClass", "withCompanionScopes", "", "forStaticNestedClass", "getFirClassifierByFqName", "moduleSession", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "getFirClassifierContainerFileIfAny", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "prepareFileScopes", "Lorg/jetbrains/kotlin/fir/resolve/transformers/ScopePersistentList;", "file", "prepareScopeForCompanion", "prepareScopeForNestedClasses", "prepareScopes", "classLikeDeclaration", "resolveAllSupertypes", "Lorg/jetbrains/kotlin/fir/types/FirTypeRef;", "visited", "", "resolveAllSupertypesForOuterClass", "resolveSpecificClassLikeSupertypes", "resolveSuperTypeRefs", "Lkotlin/Function2;", "resolveTypeAliasSupertype", "typeAlias", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeAlias;", "expandedTypeRef", "resolveRecursively", "visitAnonymousObject", "anonymousObject", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousObject;", "data", "visitDeclarationContent", "declaration", "visitElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/fir/FirElement;", "visitFile", "visitRegularClass", "regularClass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "visitTypeAlias", "withClass", ExifInterface.GPS_DIRECTION_TRUE, "firClass", "body", "Lkotlin/Function0;", "withFile", "R", "block", "(Lorg/jetbrains/kotlin/fir/declarations/FirFile;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "TypeResolveServiceForPlugins", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class FirSupertypeResolverVisitor extends FirDefaultVisitor<Unit, Object> {
    private final ArrayDeque<FirClass> classDeclarationsStack;
    private final LocalClassesNavigationInfo localClassesNavigationInfo;
    private final PersistentList<FirScope> scopeForLocalClass;
    private final ScopeSession scopeSession;
    private final FirSession session;
    private final SupertypeComputationSession supertypeComputationSession;
    private final List<FirSupertypeGenerationExtension> supertypeGenerationExtensions;
    private FirFile useSiteFile;

    @PrivateForInline
    public static /* synthetic */ void getClassDeclarationsStack$annotations() {
    }

    @PrivateForInline
    public static /* synthetic */ void getUseSiteFile$annotations() {
    }

    /* JADX INFO: renamed from: visitElement, reason: collision with other method in class */
    public void m7055visitElement(FirElement element, Object data) {
        Intrinsics.checkNotNullParameter(element, "element");
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Object visitAnonymousObject(FirAnonymousObject firAnonymousObject, Object obj) {
        m7054visitAnonymousObject(firAnonymousObject, obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Object visitElement(FirElement firElement, Object obj) {
        m7055visitElement(firElement, obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Object visitFile(FirFile firFile, Object obj) {
        m7056visitFile(firFile, obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirDefaultVisitor, org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Object visitRegularClass(FirRegularClass firRegularClass, Object obj) {
        m7057visitRegularClass(firRegularClass, obj);
        return Unit.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.fir.visitors.FirVisitor
    public /* bridge */ /* synthetic */ Object visitTypeAlias(FirTypeAlias firTypeAlias, Object obj) {
        m7058visitTypeAlias(firTypeAlias, obj);
        return Unit.INSTANCE;
    }

    public final FirFile getUseSiteFile() {
        return this.useSiteFile;
    }

    public final void setUseSiteFile(FirFile firFile) {
        this.useSiteFile = firFile;
    }

    public /* synthetic */ FirSupertypeResolverVisitor(FirSession firSession, SupertypeComputationSession supertypeComputationSession, ScopeSession scopeSession, PersistentList persistentList, LocalClassesNavigationInfo localClassesNavigationInfo, FirFile firFile, List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(firSession, supertypeComputationSession, scopeSession, (i & 8) != 0 ? null : persistentList, (i & 16) != 0 ? null : localClassesNavigationInfo, (i & 32) != 0 ? null : firFile, (i & 64) != 0 ? CollectionsKt.emptyList() : list);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public FirSupertypeResolverVisitor(FirSession session, SupertypeComputationSession supertypeComputationSession, ScopeSession scopeSession, PersistentList<? extends FirScope> persistentList, LocalClassesNavigationInfo localClassesNavigationInfo, FirFile firFile, List<? extends FirDeclaration> containingDeclarations) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(supertypeComputationSession, "supertypeComputationSession");
        Intrinsics.checkNotNullParameter(scopeSession, "scopeSession");
        Intrinsics.checkNotNullParameter(containingDeclarations, "containingDeclarations");
        this.session = session;
        this.supertypeComputationSession = supertypeComputationSession;
        this.scopeSession = scopeSession;
        this.scopeForLocalClass = persistentList;
        this.localClassesNavigationInfo = localClassesNavigationInfo;
        this.useSiteFile = firFile;
        this.supertypeGenerationExtensions = FirSupertypeGenerationExtensionKt.getSupertypeGenerators(FirExtensionServiceKt.getExtensionService(session));
        this.classDeclarationsStack = new ArrayDeque<>();
        for (FirDeclaration firDeclaration : containingDeclarations) {
            if (firDeclaration instanceof FirClass) {
                this.classDeclarationsStack.add((FirClass) firDeclaration);
            }
        }
    }

    public final ArrayDeque<FirClass> getClassDeclarationsStack() {
        return this.classDeclarationsStack;
    }

    public final <R> R withFile(FirFile file, Function0<? extends R> block) {
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(block, "block");
        FirFile useSiteFile = getUseSiteFile();
        try {
            setUseSiteFile(file);
            return block.invoke();
        } finally {
            InlineMarker.finallyStart(1);
            setUseSiteFile(useSiteFile);
            InlineMarker.finallyEnd(1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final FirFile getFirClassifierContainerFileIfAny(FirClassLikeSymbol<?> symbol) {
        return FirProviderKt.getFirProvider(symbol.getModuleData().getSession()).getFirClassifierContainerFileIfAny(symbol.getClassId());
    }

    private final FirClassLikeDeclaration getFirClassifierByFqName(FirSession moduleSession, ClassId classId) {
        return FirProviderKt.getFirProvider(moduleSession).getFirClassifierByFqName(classId);
    }

    private final PersistentList<FirScope> prepareFileScopes(final FirFile file) {
        return this.supertypeComputationSession.getOrPutFileScope(file, new Function0<PersistentList<? extends FirScope>>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.FirSupertypeResolverVisitor.prepareFileScopes.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final PersistentList<? extends FirScope> invoke() {
                return ExtensionsKt.toPersistentList(CollectionsKt.asReversed(ImportingScopesKt.createImportingScopes$default(file, this.session, this.scopeSession, false, 8, null)));
            }
        });
    }

    private final PersistentList<FirScope> prepareScopeForNestedClasses(final FirClass klass, boolean forStaticNestedClass) {
        if (forStaticNestedClass) {
            return this.supertypeComputationSession.getOrPutScopeForStaticNestedClasses(klass, new Function0<PersistentList<? extends FirScope>>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.FirSupertypeResolverVisitor.prepareScopeForNestedClasses.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final PersistentList<? extends FirScope> invoke() {
                    return FirSupertypeResolverVisitor.this.calculateScopes(klass, true, true);
                }
            });
        }
        return this.supertypeComputationSession.getOrPutScopeForNestedClasses(klass, new Function0<PersistentList<? extends FirScope>>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.FirSupertypeResolverVisitor.prepareScopeForNestedClasses.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final PersistentList<? extends FirScope> invoke() {
                return FirSupertypeResolverVisitor.this.calculateScopes(klass, true, false);
            }
        });
    }

    private final PersistentList<FirScope> prepareScopeForCompanion(final FirClass klass) {
        return this.supertypeComputationSession.getOrPutScopeForCompanion(klass, new Function0<PersistentList<? extends FirScope>>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.FirSupertypeResolverVisitor.prepareScopeForCompanion.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final PersistentList<? extends FirScope> invoke() {
                return FirSupertypeResolverVisitor.this.calculateScopes(klass, false, true);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final PersistentList<FirScope> calculateScopes(FirClass outerClass, boolean withCompanionScopes, boolean forStaticNestedClass) {
        resolveAllSupertypesForOuterClass(outerClass);
        return FirSupertypesResolutionKt.pushAll(prepareScopes(outerClass, forStaticNestedClass), FirSupertypesResolutionKt.createOtherScopesForNestedClassesOrCompanion(outerClass, this.session, this.scopeSession, this.supertypeComputationSession, withCompanionScopes));
    }

    protected void resolveAllSupertypesForOuterClass(FirClass outerClass) {
        Intrinsics.checkNotNullParameter(outerClass, "outerClass");
        resolveAllSupertypes$default(this, outerClass, outerClass.getSuperTypeRefs(), null, 4, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ void resolveAllSupertypes$default(FirSupertypeResolverVisitor firSupertypeResolverVisitor, FirClassLikeDeclaration firClassLikeDeclaration, List list, Set set, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: resolveAllSupertypes");
        }
        if ((i & 4) != 0) {
            set = new LinkedHashSet();
        }
        firSupertypeResolverVisitor.resolveAllSupertypes(firClassLikeDeclaration, list, set);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final void resolveAllSupertypes(FirClassLikeDeclaration classLikeDeclaration, List<? extends FirTypeRef> supertypeRefs, Set<FirClassLikeDeclaration> visited) {
        ConeClassLikeType coneClassLikeType;
        FirClassLikeSymbol<?> symbol;
        FirModuleData moduleData;
        FirSession session;
        FirClassLikeSymbol<?> symbol2;
        FirClassLikeDeclaration firClassLikeDeclaration;
        if (visited.add(classLikeDeclaration)) {
            List<FirResolvedTypeRef> listResolveSpecificClassLikeSupertypes = resolveSpecificClassLikeSupertypes(classLikeDeclaration, supertypeRefs);
            ArrayList<ConeKotlinType> arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listResolveSpecificClassLikeSupertypes, 10));
            Iterator<T> it = listResolveSpecificClassLikeSupertypes.iterator();
            while (it.hasNext()) {
                arrayList.add(FirTypeUtilsKt.getConeType((FirResolvedTypeRef) it.next()));
            }
            for (ConeKotlinType coneKotlinType : arrayList) {
                if ((coneKotlinType instanceof ConeClassLikeType) && (symbol = TypeUtilsKt.toSymbol((coneClassLikeType = (ConeClassLikeType) coneKotlinType), this.session)) != null && (moduleData = symbol.getModuleData()) != null && (session = moduleData.getSession()) != null && (symbol2 = LookupTagUtilsKt.toSymbol(coneClassLikeType.getLookupTag(), session)) != null && (firClassLikeDeclaration = (FirClassLikeDeclaration) symbol2.getFir()) != null) {
                    resolveAllSupertypes(firClassLikeDeclaration, this.supertypeComputationSession.supertypeRefs$resolve(firClassLikeDeclaration), visited);
                }
            }
        }
    }

    private final PersistentList<FirScope> prepareScopes(FirClassLikeDeclaration classLikeDeclaration, boolean forStaticNestedClass) {
        PersistentList<FirScope> persistentListPersistentListOf;
        FirRegularClass firRegularClass;
        ClassId classId = classLikeDeclaration.getSymbol().getClassId();
        FirSession session = classLikeDeclaration.getModuleData().getSession();
        if (classId.isLocal()) {
            LocalClassesNavigationInfo localClassesNavigationInfo = this.localClassesNavigationInfo;
            if (localClassesNavigationInfo == null) {
                return ExtensionsKt.persistentListOf();
            }
            FirClassLikeDeclaration firClassLikeDeclaration = localClassesNavigationInfo.getParentForClass().get(classLikeDeclaration);
            if (firClassLikeDeclaration == null || !(firClassLikeDeclaration instanceof FirClass)) {
                persistentListPersistentListOf = this.scopeForLocalClass;
                if (persistentListPersistentListOf == null) {
                    return ExtensionsKt.persistentListOf();
                }
            } else {
                persistentListPersistentListOf = prepareScopeForNestedClasses((FirClass) firClassLikeDeclaration, forStaticNestedClass);
            }
        } else {
            FirRegularClass firRegularClass2 = classLikeDeclaration instanceof FirRegularClass ? (FirRegularClass) classLikeDeclaration : null;
            boolean z = true;
            if (firRegularClass2 != null && firRegularClass2.getStatus().isCompanion()) {
                ClassId outerClassId = classId.getOuterClassId();
                FirClassLikeDeclaration firClassifierByFqName = outerClassId != null ? getFirClassifierByFqName(session, outerClassId) : null;
                firRegularClass = firClassifierByFqName instanceof FirRegularClass ? (FirRegularClass) firClassifierByFqName : null;
                if (firRegularClass == null) {
                    return ExtensionsKt.persistentListOf();
                }
                persistentListPersistentListOf = prepareScopeForCompanion(firRegularClass);
            } else if (classId.isNestedClass()) {
                ClassId outerClassId2 = classId.getOuterClassId();
                FirClassLikeDeclaration firClassifierByFqName2 = outerClassId2 != null ? getFirClassifierByFqName(session, outerClassId2) : null;
                firRegularClass = firClassifierByFqName2 instanceof FirRegularClass ? (FirRegularClass) firClassifierByFqName2 : null;
                boolean z2 = (classLikeDeclaration.getStatus().isInner() || (classLikeDeclaration instanceof FirTypeAlias)) ? false : true;
                if (firRegularClass == null) {
                    return ExtensionsKt.persistentListOf();
                }
                FirRegularClass firRegularClass3 = firRegularClass;
                if (!z2 && !forStaticNestedClass) {
                    z = false;
                }
                persistentListPersistentListOf = prepareScopeForNestedClasses(firRegularClass3, z);
            } else {
                FirFile firClassifierContainerFileIfAny = getFirClassifierContainerFileIfAny(classLikeDeclaration.getSymbol());
                if (firClassifierContainerFileIfAny == null || (persistentListPersistentListOf = prepareFileScopes(firClassifierContainerFileIfAny)) == null) {
                    persistentListPersistentListOf = ExtensionsKt.persistentListOf();
                }
            }
        }
        return forStaticNestedClass ? persistentListPersistentListOf : FirSupertypesResolutionKt.pushIfNotNull(persistentListPersistentListOf, FirSupertypesResolutionKt.typeParametersScope(classLikeDeclaration));
    }

    private final List<FirResolvedTypeRef> resolveSpecificClassLikeSupertypes(FirClassLikeDeclaration classLikeDeclaration, Function2<? super FirTransformer<? super ScopeClassDeclaration>, ? super ScopeClassDeclaration, ? extends List<? extends FirResolvedTypeRef>> resolveSuperTypeRefs) {
        SupertypeComputationStatus supertypesComputationStatus = this.supertypeComputationSession.getSupertypesComputationStatus(classLikeDeclaration);
        if (supertypesComputationStatus instanceof SupertypeComputationStatus.Computed) {
            return ((SupertypeComputationStatus.Computed) supertypesComputationStatus).getSupertypeRefs();
        }
        if (supertypesComputationStatus instanceof SupertypeComputationStatus.Computing) {
            return CollectionsKt.listOf(FirSupertypesResolutionKt.createErrorTypeRef(classLikeDeclaration, "Loop in supertype definition for " + classLikeDeclaration.getSymbol().getClassId(), classLikeDeclaration instanceof FirTypeAlias ? DiagnosticKind.RecursiveTypealiasExpansion : DiagnosticKind.LoopInSupertype));
        }
        Intrinsics.areEqual(supertypesComputationStatus, SupertypeComputationStatus.NotComputed.INSTANCE);
        this.supertypeComputationSession.startComputingSupertypes(classLikeDeclaration);
        PersistentList<FirScope> persistentListPrepareScopes = prepareScopes(classLikeDeclaration, false);
        FirSpecificTypeResolverTransformer firSpecificTypeResolverTransformer = new FirSpecificTypeResolverTransformer(this.session, false, false, this.supertypeComputationSession.getSupertypesSupplier(), 6, null);
        FirFile firClassifierContainerFileIfAny = ControlFlowGraphBuilderKt.isLocalClassOrAnonymousObject(classLikeDeclaration) ? this.useSiteFile : FirProviderKt.getFirProvider(this.session).getFirClassifierContainerFileIfAny(classLikeDeclaration.getSymbol());
        FirFile firFile = firSpecificTypeResolverTransformer.currentFile;
        firSpecificTypeResolverTransformer.currentFile = firClassifierContainerFileIfAny;
        try {
            List<FirResolvedTypeRef> list = (List) resolveSuperTypeRefs.invoke(firSpecificTypeResolverTransformer, new ScopeClassDeclaration(persistentListPrepareScopes, this.classDeclarationsStack, null, classLikeDeclaration, 4, null));
            firSpecificTypeResolverTransformer.currentFile = firFile;
            this.supertypeComputationSession.storeSupertypes(classLikeDeclaration, list);
            return list;
        } catch (Throwable th) {
            firSpecificTypeResolverTransformer.currentFile = firFile;
            throw th;
        }
    }

    private final void visitDeclarationContent(FirDeclaration declaration, Object data) {
        declaration.acceptChildren(this, data);
    }

    public final <T> void withClass(FirClass firClass, Function0<? extends T> body) {
        Intrinsics.checkNotNullParameter(firClass, "firClass");
        Intrinsics.checkNotNullParameter(body, "body");
        ArrayDeque<FirClass> classDeclarationsStack = getClassDeclarationsStack();
        classDeclarationsStack.addLast(firClass);
        try {
            body.invoke();
        } finally {
            InlineMarker.finallyStart(1);
            classDeclarationsStack.removeLast();
            InlineMarker.finallyEnd(1);
        }
    }

    public final List<FirResolvedTypeRef> resolveSpecificClassLikeSupertypes(final FirClassLikeDeclaration classLikeDeclaration, final List<? extends FirTypeRef> supertypeRefs) {
        Intrinsics.checkNotNullParameter(classLikeDeclaration, "classLikeDeclaration");
        Intrinsics.checkNotNullParameter(supertypeRefs, "supertypeRefs");
        return resolveSpecificClassLikeSupertypes(classLikeDeclaration, new Function2<FirTransformer<? super ScopeClassDeclaration>, ScopeClassDeclaration, List<? extends FirResolvedTypeRef>>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.FirSupertypeResolverVisitor.resolveSpecificClassLikeSupertypes.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final List<FirResolvedTypeRef> invoke(FirTransformer<? super ScopeClassDeclaration> transformer, ScopeClassDeclaration scopeDeclaration) {
                FirLookupTrackerComponent lookupTracker;
                Intrinsics.checkNotNullParameter(transformer, "transformer");
                Intrinsics.checkNotNullParameter(scopeDeclaration, "scopeDeclaration");
                if (!ControlFlowGraphBuilderKt.isLocalClassOrAnonymousObject(classLikeDeclaration) && (lookupTracker = FirLookupTrackerComponentKt.getLookupTracker(this.session)) != null) {
                    FirSupertypeResolverVisitor firSupertypeResolverVisitor = this;
                    FirClassLikeDeclaration firClassLikeDeclaration = classLikeDeclaration;
                    List<FirTypeRef> list = supertypeRefs;
                    FirFile firClassifierContainerFileIfAny = firSupertypeResolverVisitor.getFirClassifierContainerFileIfAny(firClassLikeDeclaration.getSymbol());
                    KtSourceElement source = firClassifierContainerFileIfAny != null ? firClassifierContainerFileIfAny.getSource() : null;
                    for (FirTypeRef firTypeRef : list) {
                        Iterable<FirScope> scopes = scopeDeclaration.getScopes();
                        ArrayList arrayList = new ArrayList();
                        Iterator<FirScope> it = scopes.iterator();
                        while (it.hasNext()) {
                            CollectionsKt.addAll(arrayList, it.next().getScopeOwnerLookupNames());
                        }
                        FirLookupTrackerComponentKt.recordTypeLookup(lookupTracker, firTypeRef, arrayList, source);
                    }
                }
                List<FirTypeRef> list2 = supertypeRefs;
                ArrayList arrayList2 = new ArrayList();
                Iterator<T> it2 = list2.iterator();
                while (it2.hasNext()) {
                    FirElement firElementCreateErrorTypeRef = (FirTypeRef) ((FirTypeRef) it2.next()).transform(transformer, scopeDeclaration);
                    boolean z = firElementCreateErrorTypeRef instanceof FirResolvedTypeRef;
                    FirResolvedTypeRef firResolvedTypeRef = z ? (FirResolvedTypeRef) firElementCreateErrorTypeRef : null;
                    ConeKotlinType type = firResolvedTypeRef != null ? firResolvedTypeRef.getType() : null;
                    if (!(type instanceof ConeTypeParameterType)) {
                        type = null;
                    }
                    ConeTypeParameterType coneTypeParameterType = (ConeTypeParameterType) type;
                    if (coneTypeParameterType != null) {
                        FirErrorTypeRefBuilder firErrorTypeRefBuilder = new FirErrorTypeRefBuilder();
                        firErrorTypeRefBuilder.setSource(firElementCreateErrorTypeRef.getSource());
                        firErrorTypeRefBuilder.setDiagnostic(new ConeTypeParameterSupertype(coneTypeParameterType.getLookupTag().getTypeParameterSymbol()));
                        firElementCreateErrorTypeRef = firErrorTypeRefBuilder.mo6809build();
                    } else if (!z) {
                        FirElement firElement = firElementCreateErrorTypeRef;
                        firElementCreateErrorTypeRef = FirSupertypesResolutionKt.createErrorTypeRef(firElement, "Unresolved super-type: " + UtilsKt.render(firElement), DiagnosticKind.UnresolvedSupertype);
                    }
                    arrayList2.add((FirResolvedTypeRef) firElementCreateErrorTypeRef);
                }
                ArrayList arrayList3 = arrayList2;
                this.addSupertypesFromExtensions(classLikeDeclaration, arrayList3, transformer, scopeDeclaration);
                return arrayList3;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void addSupertypesFromExtensions(FirClassLikeDeclaration klass, List<FirResolvedTypeRef> supertypeRefs, FirTransformer<? super ScopeClassDeclaration> typeResolveTransformer, ScopeClassDeclaration scopeDeclaration) {
        if (this.supertypeGenerationExtensions.isEmpty()) {
            return;
        }
        FirSupertypeGenerationExtension.TypeResolveServiceContainer typeResolveServiceContainer = new FirSupertypeGenerationExtension.TypeResolveServiceContainer(new TypeResolveServiceForPlugins(typeResolveTransformer, scopeDeclaration));
        for (FirSupertypeGenerationExtension firSupertypeGenerationExtension : this.supertypeGenerationExtensions) {
            if (firSupertypeGenerationExtension.needTransformSupertypes(klass)) {
                CollectionsKt.addAll(supertypeRefs, firSupertypeGenerationExtension.computeAdditionalSupertypes(typeResolveServiceContainer, klass, supertypeRefs));
            }
        }
    }

    /* JADX INFO: compiled from: FirSupertypesResolution.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u001b\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0002\u0010\u0006J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u0011\u0010\u0005\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/FirSupertypeResolverVisitor$TypeResolveServiceForPlugins;", "Lorg/jetbrains/kotlin/fir/extensions/FirSupertypeGenerationExtension$TypeResolveService;", "typeResolveTransformer", "Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/ScopeClassDeclaration;", "scopeDeclaration", "(Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;Lorg/jetbrains/kotlin/fir/resolve/transformers/ScopeClassDeclaration;)V", "getScopeDeclaration", "()Lorg/jetbrains/kotlin/fir/resolve/transformers/ScopeClassDeclaration;", "getTypeResolveTransformer", "()Lorg/jetbrains/kotlin/fir/visitors/FirTransformer;", "resolveUserType", "Lorg/jetbrains/kotlin/fir/types/FirResolvedTypeRef;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/fir/types/FirUserTypeRef;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class TypeResolveServiceForPlugins extends FirSupertypeGenerationExtension.TypeResolveService {
        private final ScopeClassDeclaration scopeDeclaration;
        private final FirTransformer<ScopeClassDeclaration> typeResolveTransformer;

        public final FirTransformer<ScopeClassDeclaration> getTypeResolveTransformer() {
            return this.typeResolveTransformer;
        }

        public final ScopeClassDeclaration getScopeDeclaration() {
            return this.scopeDeclaration;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public TypeResolveServiceForPlugins(FirTransformer<? super ScopeClassDeclaration> typeResolveTransformer, ScopeClassDeclaration scopeDeclaration) {
            Intrinsics.checkNotNullParameter(typeResolveTransformer, "typeResolveTransformer");
            Intrinsics.checkNotNullParameter(scopeDeclaration, "scopeDeclaration");
            this.typeResolveTransformer = typeResolveTransformer;
            this.scopeDeclaration = scopeDeclaration;
        }

        @Override // org.jetbrains.kotlin.fir.extensions.FirSupertypeGenerationExtension.TypeResolveService
        public FirResolvedTypeRef resolveUserType(FirUserTypeRef type) {
            Intrinsics.checkNotNullParameter(type, "type");
            return (FirResolvedTypeRef) type.transform(this.typeResolveTransformer, this.scopeDeclaration);
        }
    }

    /* JADX INFO: renamed from: visitTypeAlias, reason: collision with other method in class */
    public void m7058visitTypeAlias(FirTypeAlias typeAlias, Object data) {
        Intrinsics.checkNotNullParameter(typeAlias, "typeAlias");
        resolveTypeAliasSupertype(typeAlias, typeAlias.getExpandedTypeRef(), true);
    }

    public final List<FirResolvedTypeRef> resolveTypeAliasSupertype(final FirTypeAlias typeAlias, final FirTypeRef expandedTypeRef, final boolean resolveRecursively) {
        Intrinsics.checkNotNullParameter(typeAlias, "typeAlias");
        Intrinsics.checkNotNullParameter(expandedTypeRef, "expandedTypeRef");
        if (expandedTypeRef instanceof FirResolvedTypeRef) {
            return CollectionsKt.listOf(expandedTypeRef);
        }
        return resolveSpecificClassLikeSupertypes(typeAlias, new Function2<FirTransformer<? super ScopeClassDeclaration>, ScopeClassDeclaration, List<? extends FirResolvedTypeRef>>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.FirSupertypeResolverVisitor.resolveTypeAliasSupertype.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final List<FirResolvedTypeRef> invoke(FirTransformer<? super ScopeClassDeclaration> transformer, ScopeClassDeclaration scope) {
                Intrinsics.checkNotNullParameter(transformer, "transformer");
                Intrinsics.checkNotNullParameter(scope, "scope");
                FirTypeRef firTypeRefTransformTypeRef = transformer.transformTypeRef(expandedTypeRef, scope);
                FirResolvedTypeRef firResolvedTypeRef = firTypeRefTransformTypeRef instanceof FirResolvedTypeRef ? (FirResolvedTypeRef) firTypeRefTransformTypeRef : null;
                if (firResolvedTypeRef == null) {
                    return CollectionsKt.listOf(FirSupertypesResolutionKt.createErrorTypeRef(expandedTypeRef, "Unresolved expanded typeRef for " + typeAlias.getSymbol().getClassId(), DiagnosticKind.UnresolvedExpandedType));
                }
                if (resolveRecursively) {
                    invoke$visitNestedTypeAliases(this, firResolvedTypeRef.getType());
                }
                return CollectionsKt.listOf(firResolvedTypeRef);
            }

            /* JADX WARN: Multi-variable type inference failed */
            private static final void invoke$visitNestedTypeAliases(FirSupertypeResolverVisitor firSupertypeResolverVisitor, TypeArgumentMarker typeArgumentMarker) {
                if (typeArgumentMarker instanceof ConeClassLikeType) {
                    ConeClassLikeType coneClassLikeType = (ConeClassLikeType) typeArgumentMarker;
                    FirClassLikeSymbol<?> symbol = LookupTagUtilsKt.toSymbol(coneClassLikeType.getLookupTag(), firSupertypeResolverVisitor.session);
                    if (symbol instanceof FirTypeAliasSymbol) {
                        firSupertypeResolverVisitor.m7058visitTypeAlias((FirTypeAlias) ((FirTypeAliasSymbol) symbol).getFir(), (Object) null);
                        return;
                    }
                    if (symbol != null) {
                        for (ConeTypeProjection coneTypeProjection : coneClassLikeType.getTypeArguments()) {
                            invoke$visitNestedTypeAliases(firSupertypeResolverVisitor, coneTypeProjection);
                        }
                    }
                }
            }
        });
    }

    /* JADX INFO: renamed from: visitRegularClass, reason: collision with other method in class */
    public void m7057visitRegularClass(FirRegularClass regularClass, Object data) {
        Intrinsics.checkNotNullParameter(regularClass, "regularClass");
        ArrayDeque<FirClass> classDeclarationsStack = getClassDeclarationsStack();
        classDeclarationsStack.addLast(regularClass);
        try {
            resolveSpecificClassLikeSupertypes(regularClass, regularClass.getSuperTypeRefs());
            visitDeclarationContent(regularClass, null);
            Unit unit = Unit.INSTANCE;
        } finally {
            classDeclarationsStack.removeLast();
        }
    }

    /* JADX INFO: renamed from: visitAnonymousObject, reason: collision with other method in class */
    public void m7054visitAnonymousObject(FirAnonymousObject anonymousObject, Object data) {
        Intrinsics.checkNotNullParameter(anonymousObject, "anonymousObject");
        ArrayDeque<FirClass> classDeclarationsStack = getClassDeclarationsStack();
        classDeclarationsStack.addLast(anonymousObject);
        try {
            resolveSpecificClassLikeSupertypes(anonymousObject, anonymousObject.getSuperTypeRefs());
            visitDeclarationContent(anonymousObject, null);
            Unit unit = Unit.INSTANCE;
        } finally {
            classDeclarationsStack.removeLast();
        }
    }

    /* JADX INFO: renamed from: visitFile, reason: collision with other method in class */
    public void m7056visitFile(FirFile file, Object data) {
        Intrinsics.checkNotNullParameter(file, "file");
        FirFile useSiteFile = getUseSiteFile();
        try {
            setUseSiteFile(file);
            visitDeclarationContent(file, null);
            Unit unit = Unit.INSTANCE;
        } finally {
            setUseSiteFile(useSiteFile);
        }
    }
}
