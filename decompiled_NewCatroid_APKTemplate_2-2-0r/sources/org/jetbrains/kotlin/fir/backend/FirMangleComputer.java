package org.jetbrains.kotlin.fir.backend;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleMode;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleUtilsKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousObject;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirContextReceiver;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirEnumEntry;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameter;
import org.jetbrains.kotlin.fir.declarations.FirTypeParameterRef;
import org.jetbrains.kotlin.fir.declarations.FirTypeParametersOwner;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.declarations.synthetic.FirSyntheticPropertyAccessor;
import org.jetbrains.kotlin.fir.resolve.LookupTagUtilsKt;
import org.jetbrains.kotlin.fir.resolve.TypeExpansionUtilsKt;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.fir.signaturer.FirManglerUtilKt;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.ConeClassifierLookupTag;
import org.jetbrains.kotlin.fir.symbols.ConeTypeParameterLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeAliasSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.CompilerConeAttributesKt;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeCapturedType;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeDefinitelyNotNullType;
import org.jetbrains.kotlin.fir.types.ConeDynamicType;
import org.jetbrains.kotlin.fir.types.ConeFlexibleType;
import org.jetbrains.kotlin.fir.types.ConeInferenceContext;
import org.jetbrains.kotlin.fir.types.ConeIntersectionType;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.ConeLookupTagBasedType;
import org.jetbrains.kotlin.fir.types.ConeRawType;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.fir.types.ConeTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.FirTypeRef;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.TypeComponentsKt;
import org.jetbrains.kotlin.fir.visitors.FirVisitorVoid;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;

/* JADX INFO: compiled from: FirMangleComputer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\b\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\t\b\u0016\u0018\u00002,\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0001:\u00017B\u0019\u0012\n\u0010\t\u001a\u00060\nj\u0002`\u000b\u0012\u0006\u0010\f\u001a\u00020\r¢\u0006\u0002\u0010\u000eJ\u0010\u0010\u0011\u001a\u00020\u00002\u0006\u0010\u0012\u001a\u00020\rH\u0016J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0006H\u0014J\u0016\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00030\u00172\u0006\u0010\u0018\u001a\u00020\u0007H\u0014J\u0010\u0010\u0019\u001a\u00020\u00062\u0006\u0010\u001a\u001a\u00020\u0004H\u0014J\u0012\u0010\u001b\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0018\u001a\u00020\u0007H\u0014J\u0018\u0010\u001c\u001a\u00020\u00142\u0006\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u001d\u001a\u00020\u0006H\u0014J\u0010\u0010\u001e\u001a\u00020\u00032\u0006\u0010\u0018\u001a\u00020\u0007H\u0014J\u0010\u0010\u001f\u001a\u00020 2\u0006\u0010\u001a\u001a\u00020\u0004H\u0014J$\u0010!\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040#0\"2\u0006\u0010\u0018\u001a\u00020\u00072\u0006\u0010\u001d\u001a\u00020\u0002H\u0014J\u0010\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020\bH\u0014J\u0010\u0010'\u001a\u00020\u00032\u0006\u0010(\u001a\u00020\u0005H\u0014J\u0016\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00050\u00172\u0006\u0010\u0018\u001a\u00020\u0007H\u0014J\u0010\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020\u0003H\u0014J\u0010\u0010-\u001a\u00020+2\u0006\u0010(\u001a\u00020\u0005H\u0014J$\u0010.\u001a\u00020/2\n\u00100\u001a\u00060\nj\u0002`\u000b2\u0006\u0010,\u001a\u00020\u00032\u0006\u00101\u001a\u00020\bH\u0014J\u0010\u00102\u001a\u00020 2\u0006\u00103\u001a\u00020\u0002H\u0014J\u000e\u00104\u001a\u0004\u0018\u00010\u0006*\u00020\u0002H\u0014J\f\u00105\u001a\u00020/*\u00020\u0002H\u0014J\f\u00106\u001a\u00020/*\u00020\u0002H\u0014R\u0012\u0010\u000f\u001a\u00060\u0010R\u00020\u0000X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00068"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/FirMangleComputer;", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/BaseKotlinMangleComputer;", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "Lorg/jetbrains/kotlin/fir/symbols/ConeTypeParameterLookupTag;", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "Lorg/jetbrains/kotlin/fir/declarations/FirMemberDeclaration;", "Lorg/jetbrains/kotlin/fir/declarations/FirFunction;", "Lorg/jetbrains/kotlin/fir/FirSession;", "builder", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "mode", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/MangleMode;", "(Ljava/lang/StringBuilder;Lorg/jetbrains/kotlin/backend/common/serialization/mangle/MangleMode;)V", "visitor", "Lorg/jetbrains/kotlin/fir/backend/FirMangleComputer$Visitor;", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "newMode", "getContainerIndex", "", "parent", "getContextReceiverTypes", "", "function", "getEffectiveParent", "typeParameter", "getExtensionReceiverParameterType", "getIndexOfTypeParameter", "container", "getReturnType", "getTypeParameterName", "", "getTypeParametersWithIndices", "", "Lkotlin/collections/IndexedValue;", "getTypeSystemContext", "Lorg/jetbrains/kotlin/fir/types/ConeInferenceContext;", "session", "getValueParameterType", "valueParameter", "getValueParameters", "isUnit", "", ModuleXmlParser.TYPE, "isVararg", "mangleType", "", "tBuilder", "declarationSiteSession", "renderDeclaration", "declaration", "asTypeParameterContainer", "visit", "visitParent", "Visitor", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class FirMangleComputer extends BaseKotlinMangleComputer<FirDeclaration, ConeKotlinType, ConeTypeParameterLookupTag, FirValueParameter, FirMemberDeclaration, FirFunction, FirSession> {
    private final Visitor visitor;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirMangleComputer(StringBuilder builder, MangleMode mode) {
        super(builder, mode, false, 4, null);
        Intrinsics.checkNotNullParameter(builder, "builder");
        Intrinsics.checkNotNullParameter(mode, "mode");
        this.visitor = new Visitor();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.KotlinMangleComputer
    public FirMangleComputer copy(MangleMode newMode) {
        Intrinsics.checkNotNullParameter(newMode, "newMode");
        return new FirMangleComputer(getBuilder(), newMode);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
    public ConeInferenceContext getTypeSystemContext(final FirSession session) {
        Intrinsics.checkNotNullParameter(session, "session");
        return new ConeInferenceContext() { // from class: org.jetbrains.kotlin.fir.backend.FirMangleComputer.getTypeSystemContext.1
            @Override // org.jetbrains.kotlin.fir.types.ConeTypeContext
            /* JADX INFO: renamed from: getSession, reason: from getter */
            public FirSession get$session() {
                return session;
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
    public void visitParent(FirDeclaration firDeclaration) {
        Pair pair;
        FirClassLikeDeclaration firClassLikeDeclaration;
        Intrinsics.checkNotNullParameter(firDeclaration, "<this>");
        if (firDeclaration instanceof FirCallableDeclaration) {
            FirCallableDeclaration firCallableDeclaration = (FirCallableDeclaration) firDeclaration;
            FqName packageName = firCallableDeclaration.getSymbol().getCallableId().getPackageName();
            ConeClassLikeLookupTag coneClassLikeLookupTagContainingClassLookupTag = ClassMembersKt.containingClassLookupTag(firCallableDeclaration);
            pair = TuplesKt.to(packageName, coneClassLikeLookupTagContainingClassLookupTag != null ? coneClassLikeLookupTagContainingClassLookupTag.getClassId() : null);
        } else {
            if (!(firDeclaration instanceof FirClassLikeDeclaration)) {
                return;
            }
            ClassId classId = ((FirClassLikeDeclaration) firDeclaration).getSymbol().getClassId();
            pair = TuplesKt.to(classId.getPackageFqName(), classId.getOuterClassId());
        }
        FqName fqName = (FqName) pair.component1();
        ClassId classId2 = (ClassId) pair.component2();
        if (classId2 != null && !classId2.isLocal()) {
            FirClassLikeSymbol<?> classLikeSymbolByClassId = FirSymbolProviderKt.getSymbolProvider(firDeclaration.getModuleData().getSession()).getClassLikeSymbolByClassId(classId2);
            if (classLikeSymbolByClassId == null || (firClassLikeDeclaration = (FirClassLikeDeclaration) classLikeSymbolByClassId.getFir()) == null) {
                throw new IllegalStateException(("Attempt to find parent (" + classId2 + ") for probably-local declaration!").toString());
            }
            if ((firClassLikeDeclaration instanceof FirRegularClass) || (firClassLikeDeclaration instanceof FirTypeAlias)) {
                visit((FirDeclaration) firClassLikeDeclaration);
                return;
            }
            throw new IllegalStateException(("Strange class-like declaration: " + UtilsKt.render(firClassLikeDeclaration)).toString());
        }
        if (classId2 != null || fqName.isRoot()) {
            return;
        }
        StringBuilder builder = getBuilder();
        String strAsString = fqName.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        appendName(builder, strAsString);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
    public void visit(FirDeclaration firDeclaration) {
        Intrinsics.checkNotNullParameter(firDeclaration, "<this>");
        firDeclaration.accept(this.visitor, null);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
    public FirMemberDeclaration asTypeParameterContainer(FirDeclaration firDeclaration) {
        Intrinsics.checkNotNullParameter(firDeclaration, "<this>");
        if (firDeclaration instanceof FirMemberDeclaration) {
            return (FirMemberDeclaration) firDeclaration;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
    public List<ConeKotlinType> getContextReceiverTypes(FirFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        List<FirContextReceiver> contextReceivers = function instanceof FirPropertyAccessor ? ((FirProperty) ((FirPropertyAccessor) function).getPropertySymbol().getFir()).getContextReceivers() : function.getContextReceivers();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(contextReceivers, 10));
        Iterator<T> it = contextReceivers.iterator();
        while (it.hasNext()) {
            arrayList.add(FirTypeUtilsKt.getConeType(((FirContextReceiver) it.next()).getTypeRef()));
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
    public ConeKotlinType getExtensionReceiverParameterType(FirFunction function) {
        FirPropertySymbol propertySymbol;
        FirProperty firProperty;
        FirReceiverParameter receiverParameter;
        FirTypeRef typeRef;
        FirTypeRef typeRef2;
        ConeKotlinType coneType;
        Intrinsics.checkNotNullParameter(function, "function");
        FirReceiverParameter receiverParameter2 = function.getReceiverParameter();
        if (receiverParameter2 != null && (typeRef2 = receiverParameter2.getTypeRef()) != null && (coneType = FirTypeUtilsKt.getConeType(typeRef2)) != null) {
            return coneType;
        }
        FirPropertyAccessor firPropertyAccessor = function instanceof FirPropertyAccessor ? (FirPropertyAccessor) function : null;
        if (firPropertyAccessor == null || (propertySymbol = firPropertyAccessor.getPropertySymbol()) == null || (firProperty = (FirProperty) propertySymbol.getFir()) == null || (receiverParameter = firProperty.getReceiverParameter()) == null || (typeRef = receiverParameter.getTypeRef()) == null) {
            return null;
        }
        return FirTypeUtilsKt.getConeType(typeRef);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
    public List<FirValueParameter> getValueParameters(FirFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        return function.getValueParameters();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
    public ConeKotlinType getReturnType(FirFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        return FirTypeUtilsKt.getConeType(function.getReturnTypeRef());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
    public Iterable<IndexedValue<ConeTypeParameterLookupTag>> getTypeParametersWithIndices(FirFunction function, FirDeclaration container) {
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(container, "container");
        FirTypeParametersOwner firTypeParametersOwner = container instanceof FirTypeParametersOwner ? (FirTypeParametersOwner) container : null;
        List<FirTypeParameter> typeParameters = firTypeParametersOwner != null ? firTypeParametersOwner.getTypeParameters() : null;
        if (typeParameters == null) {
            typeParameters = CollectionsKt.emptyList();
        }
        List<FirTypeParameter> list = typeParameters;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((FirTypeParameter) it.next()).getSymbol().getLookupTag());
        }
        return CollectionsKt.withIndex(arrayList);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
    public boolean isUnit(ConeKotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return ConeBuiltinTypeUtilsKt.isUnit(type);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
    public FirMemberDeclaration getEffectiveParent(ConeTypeParameterLookupTag typeParameter) {
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        FirDeclaration fir = typeParameter.getSymbol().getContainingDeclarationSymbol().getFir();
        Intrinsics.checkNotNull(fir, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.declarations.FirMemberDeclaration");
        return (FirMemberDeclaration) fir;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
    public int getContainerIndex(FirMemberDeclaration parent) {
        FirCallableSymbol<? extends FirCallableDeclaration> symbol;
        Intrinsics.checkNotNullParameter(parent, "parent");
        CallableId callableId = null;
        FirCallableDeclaration firCallableDeclaration = parent instanceof FirCallableDeclaration ? (FirCallableDeclaration) parent : null;
        if (firCallableDeclaration != null && (symbol = firCallableDeclaration.getSymbol()) != null) {
            callableId = symbol.getCallableId();
        }
        int i = 0;
        for (FirMemberDeclaration firMemberDeclaration : getTypeParameterContainers()) {
            if (Intrinsics.areEqual(firMemberDeclaration, parent)) {
                return i;
            }
            if ((firMemberDeclaration instanceof FirCallableDeclaration) && Intrinsics.areEqual(((FirCallableDeclaration) firMemberDeclaration).getSymbol().getCallableId(), callableId)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
    public String renderDeclaration(FirDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        return UtilsKt.render(declaration);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
    public String getTypeParameterName(ConeTypeParameterLookupTag typeParameter) {
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        String strAsString = typeParameter.getName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        return strAsString;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
    public boolean isVararg(FirValueParameter valueParameter) {
        Intrinsics.checkNotNullParameter(valueParameter, "valueParameter");
        return valueParameter.getIsVararg();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
    public ConeKotlinType getValueParameterType(FirValueParameter valueParameter) {
        Intrinsics.checkNotNullParameter(valueParameter, "valueParameter");
        return FirTypeUtilsKt.getConeType(valueParameter.getReturnTypeRef());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
    public int getIndexOfTypeParameter(ConeTypeParameterLookupTag typeParameter, FirMemberDeclaration container) {
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        Intrinsics.checkNotNullParameter(container, "container");
        return container.getTypeParameters().indexOf(typeParameter.getSymbol().getFir());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
    public void mangleType(StringBuilder tBuilder, ConeKotlinType type, FirSession declarationSiteSession) {
        ConeClassLikeType coneClassLikeTypeReplaceArguments;
        Intrinsics.checkNotNullParameter(tBuilder, "tBuilder");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(declarationSiteSession, "declarationSiteSession");
        if (type instanceof ConeLookupTagBasedType) {
            ConeLookupTagBasedType coneLookupTagBasedType = (ConeLookupTagBasedType) type;
            FirClassifierSymbol<?> symbol = LookupTagUtilsKt.toSymbol(coneLookupTagBasedType.getLookupTag(), declarationSiteSession);
            if (symbol instanceof FirTypeAliasSymbol) {
                mangleType(tBuilder, (ConeKotlinType) TypeExpansionUtilsKt.fullyExpandedType((ConeSimpleKotlinType) type, declarationSiteSession), declarationSiteSession);
                return;
            }
            if (symbol instanceof FirClassSymbol) {
                copy(MangleMode.FQNAME).visit(symbol.getFir());
            } else if (symbol instanceof FirTypeParameterSymbol) {
                mangleTypeParameterReference(tBuilder, ((FirTypeParameterSymbol) symbol).getLookupTag());
            } else if (symbol == null) {
                ConeClassifierLookupTag lookupTag = coneLookupTagBasedType.getLookupTag();
                ConeClassLikeLookupTag coneClassLikeLookupTag = lookupTag instanceof ConeClassLikeLookupTag ? (ConeClassLikeLookupTag) lookupTag : null;
                if (coneClassLikeLookupTag != null) {
                    tBuilder.append(coneClassLikeLookupTag.getClassId().asFqNameString());
                }
            }
            mangleTypeArguments(tBuilder, type, declarationSiteSession);
            if (ConeTypeUtilsKt.isMarkedNullable(type)) {
                appendSignature(tBuilder, MangleConstant.Q_MARK);
            }
            if (CompilerConeAttributesKt.getHasEnhancedNullability(type)) {
                appendSignature(tBuilder, MangleConstant.ENHANCED_NULLABILITY_MARK);
                return;
            }
            return;
        }
        if (type instanceof ConeRawType) {
            mangleType(tBuilder, (ConeKotlinType) ((ConeRawType) type).getLowerBound(), declarationSiteSession);
            return;
        }
        if (type instanceof ConeDynamicType) {
            appendSignature(tBuilder, MangleConstant.DYNAMIC_MARK);
            return;
        }
        if (type instanceof ConeFlexibleType) {
            ConeInferenceContext typeContext = TypeComponentsKt.getTypeContext(declarationSiteSession);
            ConeFlexibleType coneFlexibleType = (ConeFlexibleType) type;
            ConeSimpleKotlinType upperBound = coneFlexibleType.getUpperBound();
            if (upperBound instanceof ConeClassLikeType) {
                ConeSimpleKotlinType lowerBound = coneFlexibleType.getLowerBound();
                ConeClassLikeType coneClassLikeType = lowerBound instanceof ConeClassLikeType ? (ConeClassLikeType) lowerBound : null;
                if (coneClassLikeType == null) {
                    throw new IllegalStateException(("Expecting class-like type, got " + coneFlexibleType.getLowerBound()).toString());
                }
                if (Intrinsics.areEqual(coneClassLikeType.getLookupTag(), ((ConeClassLikeType) upperBound).getLookupTag())) {
                    coneClassLikeTypeReplaceArguments = typeContext.replaceArguments(coneClassLikeType, typeContext.getArguments(upperBound));
                } else {
                    coneClassLikeTypeReplaceArguments = coneClassLikeType;
                }
                KotlinTypeMarker kotlinTypeMarker = coneClassLikeTypeReplaceArguments;
                KotlinTypeMarker kotlinTypeMarkerMakeNullable = ConeTypeUtilsKt.isNullable(upperBound) ? typeContext.makeNullable(kotlinTypeMarker) : typeContext.makeDefinitelyNotNullOrNotNull(kotlinTypeMarker);
                Intrinsics.checkNotNull(kotlinTypeMarkerMakeNullable, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.types.ConeKotlinType");
                mangleType(tBuilder, (ConeKotlinType) kotlinTypeMarkerMakeNullable, declarationSiteSession);
                return;
            }
            mangleType(tBuilder, (ConeKotlinType) upperBound, declarationSiteSession);
            return;
        }
        if (type instanceof ConeDefinitelyNotNullType) {
            mangleType(tBuilder, (ConeKotlinType) ((ConeDefinitelyNotNullType) type).getOriginal(), declarationSiteSession);
            return;
        }
        if (type instanceof ConeCapturedType) {
            ConeCapturedType coneCapturedType = (ConeCapturedType) type;
            ConeKotlinType lowerType = coneCapturedType.getLowerType();
            if (lowerType == null) {
                List<ConeKotlinType> supertypes = coneCapturedType.getConstructor().getSupertypes();
                Intrinsics.checkNotNull(supertypes);
                lowerType = (ConeKotlinType) CollectionsKt.first((List) supertypes);
            }
            mangleType(tBuilder, lowerType, declarationSiteSession);
            return;
        }
        if (type instanceof ConeIntersectionType) {
            mangleType(tBuilder, (ConeKotlinType) CollectionsKt.first(((ConeIntersectionType) type).getIntersectedTypes()), declarationSiteSession);
            return;
        }
        throw new IllegalStateException(("Unexpected type " + type).toString());
    }

    /* JADX INFO: compiled from: FirMangleComputer.kt */
    @Metadata(d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0094\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0016J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J\u0010\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0010\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u0010\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\u0010\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J\u0010\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\u0010\u0010\u001d\u001a\u00020\u00042\u0006\u0010\u001e\u001a\u00020\u001fH\u0016J\u0010\u0010 \u001a\u00020\u00042\u0006\u0010!\u001a\u00020\"H\u0016J\u0010\u0010#\u001a\u00020\u00042\u0006\u0010$\u001a\u00020%H\u0016¨\u0006&"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/FirMangleComputer$Visitor;", "Lorg/jetbrains/kotlin/fir/visitors/FirVisitorVoid;", "(Lorg/jetbrains/kotlin/fir/backend/FirMangleComputer;)V", "visitAnonymousObject", "", "anonymousObject", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousObject;", "visitConstructor", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", "visitElement", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/fir/FirElement;", "visitEnumEntry", "enumEntry", "Lorg/jetbrains/kotlin/fir/declarations/FirEnumEntry;", "visitField", "field", "Lorg/jetbrains/kotlin/fir/declarations/FirField;", "visitProperty", "property", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "visitPropertyAccessor", "propertyAccessor", "Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "visitRegularClass", "regularClass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "visitSimpleFunction", "simpleFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "visitTypeAlias", "typeAlias", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeAlias;", "visitVariable", "variable", "Lorg/jetbrains/kotlin/fir/declarations/FirVariable;", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    protected class Visitor extends FirVisitorVoid {
        public Visitor() {
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        /* JADX INFO: renamed from: visitElement, reason: merged with bridge method [inline-methods] */
        public Void mo6787visitElement(FirElement element) {
            Intrinsics.checkNotNullParameter(element, "element");
            throw new IllegalStateException(("unexpected element " + UtilsKt.render(element)).toString());
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitRegularClass(FirRegularClass regularClass) {
            Intrinsics.checkNotNullParameter(regularClass, "regularClass");
            FirMangleComputer.this.getTypeParameterContainers().add(regularClass);
            FirMangleComputer firMangleComputer = FirMangleComputer.this;
            FirRegularClass firRegularClass = regularClass;
            String strAsString = regularClass.getName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            firMangleComputer.mangleSimpleDeclaration(firRegularClass, strAsString);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitAnonymousObject(FirAnonymousObject anonymousObject) {
            Intrinsics.checkNotNullParameter(anonymousObject, "anonymousObject");
            FirMangleComputer.this.mangleSimpleDeclaration(anonymousObject, SpecialNames.ANONYMOUS_STRING);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitVariable(final FirVariable variable) {
            FirTypeRef typeRef;
            Intrinsics.checkNotNullParameter(variable, "variable");
            FirMangleComputer.this.getTypeParameterContainers().add(variable);
            FirMangleComputer.this.visitParent((FirDeclaration) variable);
            if (variable.getStatus().isStatic()) {
                FirMangleComputer firMangleComputer = FirMangleComputer.this;
                firMangleComputer.appendSignature(firMangleComputer.getBuilder(), MangleConstant.STATIC_MEMBER_MARK);
            }
            FirReceiverParameter receiverParameter = variable.getReceiverParameter();
            if (receiverParameter != null && (typeRef = receiverParameter.getTypeRef()) != null) {
                FirMangleComputer firMangleComputer2 = FirMangleComputer.this;
                firMangleComputer2.appendSignature(firMangleComputer2.getBuilder(), '@');
                firMangleComputer2.mangleType(firMangleComputer2.getBuilder(), FirTypeUtilsKt.getConeType(typeRef), variable.getModuleData().getSession());
            }
            List list = CollectionsKt.toList(CollectionsKt.withIndex(variable.getTypeParameters()));
            StringBuilder builder = FirMangleComputer.this.getBuilder();
            MangleConstant mangleConstant = MangleConstant.TYPE_PARAMETERS;
            final FirMangleComputer firMangleComputer3 = FirMangleComputer.this;
            MangleUtilsKt.collectForMangler(list, builder, mangleConstant, new Function2<StringBuilder, IndexedValue<? extends FirTypeParameterRef>, Unit>() { // from class: org.jetbrains.kotlin.fir.backend.FirMangleComputer$Visitor$visitVariable$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Unit invoke(StringBuilder sb, IndexedValue<? extends FirTypeParameterRef> indexedValue) {
                    invoke2(sb, indexedValue);
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(StringBuilder collectForMangler, IndexedValue<? extends FirTypeParameterRef> indexedValue) {
                    Intrinsics.checkNotNullParameter(collectForMangler, "$this$collectForMangler");
                    Intrinsics.checkNotNullParameter(indexedValue, "<name for destructuring parameter 0>");
                    firMangleComputer3.mangleTypeParameter(collectForMangler, indexedValue.component2().getSymbol().getLookupTag(), indexedValue.getIndex(), variable.getModuleData().getSession());
                }
            });
            FirMangleComputer.this.getBuilder().append(variable.getName().asString());
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitProperty(FirProperty property) {
            Intrinsics.checkNotNullParameter(property, "property");
            visitVariable(property);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitField(FirField field) {
            Intrinsics.checkNotNullParameter(field, "field");
            FirMangleComputer firMangleComputer = FirMangleComputer.this;
            FirField firField = field;
            String strAsString = field.getName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            firMangleComputer.mangleSimpleDeclaration(firField, strAsString);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitEnumEntry(FirEnumEntry enumEntry) {
            Intrinsics.checkNotNullParameter(enumEntry, "enumEntry");
            FirMangleComputer firMangleComputer = FirMangleComputer.this;
            FirEnumEntry firEnumEntry = enumEntry;
            String strAsString = enumEntry.getName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            firMangleComputer.mangleSimpleDeclaration(firEnumEntry, strAsString);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitTypeAlias(FirTypeAlias typeAlias) {
            Intrinsics.checkNotNullParameter(typeAlias, "typeAlias");
            FirMangleComputer firMangleComputer = FirMangleComputer.this;
            FirTypeAlias firTypeAlias = typeAlias;
            String strAsString = typeAlias.getName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            firMangleComputer.mangleSimpleDeclaration(firTypeAlias, strAsString);
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitSimpleFunction(FirSimpleFunction simpleFunction) {
            Intrinsics.checkNotNullParameter(simpleFunction, "simpleFunction");
            FirMangleComputer.this.mangleFunction(simpleFunction, simpleFunction.getName(), false, simpleFunction.getStatus().isStatic(), simpleFunction, simpleFunction.getModuleData().getSession());
        }

        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitConstructor(FirConstructor constructor) {
            Intrinsics.checkNotNullParameter(constructor, "constructor");
            FirMangleComputer.this.mangleFunction(constructor, SpecialNames.INIT, true, false, constructor, constructor.getModuleData().getSession());
        }

        /* JADX WARN: Type inference failed for: r5v0, types: [org.jetbrains.kotlin.fir.declarations.FirDeclaration] */
        @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
        public void visitPropertyAccessor(FirPropertyAccessor propertyAccessor) {
            Intrinsics.checkNotNullParameter(propertyAccessor, "propertyAccessor");
            if (!(propertyAccessor instanceof FirSyntheticPropertyAccessor)) {
                FirMangleComputer.this.mangleFunction(propertyAccessor, FirManglerUtilKt.getIrName(propertyAccessor), false, propertyAccessor.getStatus().isStatic(), propertyAccessor.getPropertySymbol().getFir(), propertyAccessor.getModuleData().getSession());
            } else {
                visitSimpleFunction(((FirSyntheticPropertyAccessor) propertyAccessor).getDelegate());
            }
        }
    }
}
