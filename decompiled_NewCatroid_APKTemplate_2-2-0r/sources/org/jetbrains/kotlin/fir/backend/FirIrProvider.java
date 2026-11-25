package org.jetbrains.kotlin.fir.backend;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializerKt;
import org.jetbrains.kotlin.backend.common.serialization.encodings.BinarySymbolData;
import org.jetbrains.kotlin.fir.backend.generators.FakeOverrideGenerator;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirClassLikeDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirEnumEntry;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolvePhase;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.FirVariable;
import org.jetbrains.kotlin.fir.declarations.utils.FirDeclarationUtilKt;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProviderKt;
import org.jetbrains.kotlin.fir.scopes.FirKotlinScopeProviderKt;
import org.jetbrains.kotlin.fir.scopes.FirScope;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.fir.types.ConeClassLikeType;
import org.jetbrains.kotlin.fir.types.ConeSimpleKotlinType;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrExternalPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeParametersContainer;
import org.jetbrains.kotlin.ir.linkage.IrProvider;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: FirIrProvider.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J'\u0010\u0014\u001a\u0004\u0018\u00010\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00150\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0002¢\u0006\u0002\u0010\u001aJ\u0012\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J\u0012\u0010\u001f\u001a\u0004\u0018\u00010\u001c2\u0006\u0010 \u001a\u00020!H\u0002J\u001a\u0010\"\u001a\u0004\u0018\u00010\u001c2\u0006\u0010 \u001a\u00020#2\u0006\u0010$\u001a\u00020%H\u0002J\u001a\u0010&\u001a\u0004\u0018\u00010\u001c2\u0006\u0010 \u001a\u00020'2\u0006\u0010$\u001a\u00020%H\u0002J\u001a\u0010(\u001a\u0004\u0018\u00010\u001c2\u0006\u0010 \u001a\u00020)2\u0006\u0010$\u001a\u00020%H\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u000f\u001a\u0004\u0018\u00010\u0010*\u00020\u00118BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013¨\u0006*"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/FirIrProvider;", "Lorg/jetbrains/kotlin/ir/linkage/IrProvider;", "fir2IrComponents", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;", "(Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;)V", "classifierStorage", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrClassifierStorage;", "declarationStorage", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage;", "fakeOverrideGenerator", "Lorg/jetbrains/kotlin/fir/backend/generators/FakeOverrideGenerator;", "getFir2IrComponents", "()Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;", "symbolProvider", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;", "staticScopeForCallables", "Lorg/jetbrains/kotlin/fir/scopes/FirScope;", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "getStaticScopeForCallables", "(Lorg/jetbrains/kotlin/fir/declarations/FirClass;)Lorg/jetbrains/kotlin/fir/scopes/FirScope;", "findDeclarationByHash", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "candidates", "", "hash", "", "(Ljava/util/Collection;Ljava/lang/Long;)Lorg/jetbrains/kotlin/fir/declarations/FirDeclaration;", "getDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "getDeclarationForAccessorSignature", "signature", "Lorg/jetbrains/kotlin/ir/util/IdSignature$AccessorSignature;", "getDeclarationForCommonSignature", "Lorg/jetbrains/kotlin/ir/util/IdSignature$CommonSignature;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/backend/common/serialization/encodings/BinarySymbolData$SymbolKind;", "getDeclarationForCompositeSignature", "Lorg/jetbrains/kotlin/ir/util/IdSignature$CompositeSignature;", "getDeclarationForSignature", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirIrProvider implements IrProvider {
    private final Fir2IrClassifierStorage classifierStorage;
    private final Fir2IrDeclarationStorage declarationStorage;
    private final FakeOverrideGenerator fakeOverrideGenerator;
    private final Fir2IrComponents fir2IrComponents;
    private final FirSymbolProvider symbolProvider;

    /* JADX INFO: compiled from: FirIrProvider.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[BinarySymbolData.SymbolKind.values().length];
            try {
                iArr[BinarySymbolData.SymbolKind.CLASS_SYMBOL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[BinarySymbolData.SymbolKind.ENUM_ENTRY_SYMBOL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[BinarySymbolData.SymbolKind.CONSTRUCTOR_SYMBOL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[BinarySymbolData.SymbolKind.FUNCTION_SYMBOL.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[BinarySymbolData.SymbolKind.PROPERTY_SYMBOL.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[BinarySymbolData.SymbolKind.FIELD_SYMBOL.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public FirIrProvider(Fir2IrComponents fir2IrComponents) {
        Intrinsics.checkNotNullParameter(fir2IrComponents, "fir2IrComponents");
        this.fir2IrComponents = fir2IrComponents;
        this.symbolProvider = FirSymbolProviderKt.getSymbolProvider(fir2IrComponents.getSession());
        this.declarationStorage = fir2IrComponents.getDeclarationStorage();
        this.classifierStorage = fir2IrComponents.getClassifierStorage();
        this.fakeOverrideGenerator = new FakeOverrideGenerator(fir2IrComponents, new Fir2IrConversionScope());
    }

    public final Fir2IrComponents getFir2IrComponents() {
        return this.fir2IrComponents;
    }

    @Override // org.jetbrains.kotlin.ir.linkage.IrProvider
    public IrDeclaration getDeclaration(IrSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        IdSignature signature = symbol.getSignature();
        if (signature == null) {
            return null;
        }
        return getDeclarationForSignature(signature, IrModuleDeserializerKt.kind(symbol));
    }

    private final IrDeclaration getDeclarationForSignature(IdSignature signature, BinarySymbolData.SymbolKind kind) {
        if (signature instanceof IdSignature.AccessorSignature) {
            return getDeclarationForAccessorSignature((IdSignature.AccessorSignature) signature);
        }
        if (signature instanceof IdSignature.CompositeSignature) {
            return getDeclarationForCompositeSignature((IdSignature.CompositeSignature) signature, kind);
        }
        if (signature instanceof IdSignature.CommonSignature) {
            return getDeclarationForCommonSignature((IdSignature.CommonSignature) signature, kind);
        }
        throw new IllegalStateException(("Unexpected signature kind: " + signature).toString());
    }

    private final IrDeclaration getDeclarationForAccessorSignature(IdSignature.AccessorSignature signature) {
        Name name2;
        Name name3;
        IrDeclaration declarationForSignature = getDeclarationForSignature(signature.getPropertySignature(), BinarySymbolData.SymbolKind.PROPERTY_SYMBOL);
        IrProperty irProperty = declarationForSignature instanceof IrProperty ? (IrProperty) declarationForSignature : null;
        if (irProperty == null) {
            return null;
        }
        String shortName = signature.getAccessorSignature().getShortName();
        IrSimpleFunction getter = irProperty.getGetter();
        if (Intrinsics.areEqual(shortName, (getter == null || (name3 = getter.getName()) == null) ? null : name3.asString())) {
            return irProperty.getGetter();
        }
        IrSimpleFunction setter = irProperty.getSetter();
        if (Intrinsics.areEqual(shortName, (setter == null || (name2 = setter.getName()) == null) ? null : name2.asString())) {
            return irProperty.getSetter();
        }
        return null;
    }

    private final IrDeclaration getDeclarationForCompositeSignature(IdSignature.CompositeSignature signature, BinarySymbolData.SymbolKind kind) {
        if (kind == BinarySymbolData.SymbolKind.TYPE_PARAMETER_SYMBOL) {
            IrDeclaration declarationForSignature = getDeclarationForSignature(signature.getContainer(), BinarySymbolData.SymbolKind.CLASS_SYMBOL);
            if (declarationForSignature == null && (declarationForSignature = getDeclarationForSignature(signature.getContainer(), BinarySymbolData.SymbolKind.FUNCTION_SYMBOL)) == null) {
                declarationForSignature = getDeclarationForSignature(signature.getContainer(), BinarySymbolData.SymbolKind.PROPERTY_SYMBOL);
            }
            Intrinsics.checkNotNull(declarationForSignature, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrTypeParametersContainer");
            IdSignature inner = signature.getInner();
            Intrinsics.checkNotNull(inner, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.util.IdSignature.LocalSignature");
            return ((IrTypeParametersContainer) declarationForSignature).getTypeParameters().get(((IdSignature.LocalSignature) inner).index());
        }
        return getDeclarationForSignature(signature.nearestPublicSig(), kind);
    }

    /* JADX WARN: Code duplicated, block: B:108:0x0346 A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:109:0x0347  */
    /* JADX WARN: Code duplicated, block: B:111:0x0354  */
    /* JADX WARN: Code duplicated, block: B:113:0x036d  */
    /* JADX WARN: Code duplicated, block: B:114:0x0381  */
    /* JADX WARN: Code duplicated, block: B:115:0x0390  */
    /* JADX WARN: Code duplicated, block: B:116:0x039f  */
    /* JADX WARN: Code duplicated, block: B:117:0x03b6  */
    /* JADX WARN: Code duplicated, block: B:118:0x03cb  */
    /* JADX WARN: Multi-variable type inference failed */
    private final IrDeclaration getDeclarationForCommonSignature(IdSignature.CommonSignature signature, BinarySymbolData.SymbolKind kind) {
        T t;
        List<String> listDropLast;
        final ArrayList arrayListListOf;
        IrClass owner;
        IrExternalPackageFragment owner2;
        final IrClass owner3;
        final ArrayList arrayList;
        T t2;
        FirRegularClass firRegularClass;
        Name name2;
        FirRegularClass firRegularClass2;
        FirDeclaration firDeclarationFindDeclarationByHash;
        FqName fqName = new FqName(signature.getPackageFqName());
        List<String> nameSegments = signature.getNameSegments();
        boolean z = false;
        Name nameIdentifier = Name.identifier(nameSegments.get(0));
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        IrExternalPackageFragment irExternalPackageFragment$default = Fir2IrDeclarationStorage.getIrExternalPackageFragment$default(this.declarationStorage, fqName, null, 2, null);
        if (nameSegments.size() == 1 && kind != BinarySymbolData.SymbolKind.CLASS_SYMBOL) {
            List<FirCallableSymbol<?>> topLevelCallableSymbols = this.symbolProvider.getTopLevelCallableSymbols(fqName, nameIdentifier);
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(topLevelCallableSymbols, 10));
            Iterator<T> it = topLevelCallableSymbols.iterator();
            while (it.hasNext()) {
                arrayList2.add((FirCallableDeclaration) ((FirCallableSymbol) it.next()).getFir());
            }
            arrayListListOf = arrayList2;
            owner2 = irExternalPackageFragment$default;
        } else {
            FirClassLikeSymbol<?> classLikeSymbolByClassId = this.symbolProvider.getClassLikeSymbolByClassId(new ClassId(fqName, nameIdentifier));
            FirClassLikeDeclaration firClassLikeDeclaration = classLikeSymbolByClassId != null ? (FirClassLikeDeclaration) classLikeSymbolByClassId.getFir() : null;
            if (firClassLikeDeclaration instanceof FirRegularClass) {
                firRegularClass2 = (FirRegularClass) firClassLikeDeclaration;
            } else {
                t = 0;
            }
            if (t == 0) {
                t = firRegularClass2;
                return null;
            }
            final Ref.ObjectRef objectRef = new Ref.ObjectRef();
            objectRef.element = t;
            if (kind == BinarySymbolData.SymbolKind.CLASS_SYMBOL) {
                t = firRegularClass2;
                listDropLast = CollectionsKt.drop(nameSegments, 1);
            } else {
                t = firRegularClass2;
                listDropLast = CollectionsKt.dropLast(CollectionsKt.drop(nameSegments, 1), 1);
            }
            FirRegularClass firRegularClass3 = null;
            for (String str : listDropLast) {
                FirRegularClass firRegularClass4 = (FirRegularClass) objectRef.element;
                Iterator<T> it2 = ((FirRegularClass) objectRef.element).getDeclarations().iterator();
                boolean z2 = z;
                Object obj = null;
                while (true) {
                    if (!it2.hasNext()) {
                        if (!z2) {
                            break;
                        }
                        break;
                    }
                    Object next = it2.next();
                    FirDeclaration firDeclaration = (FirDeclaration) next;
                    FirRegularClass firRegularClass5 = firDeclaration instanceof FirRegularClass ? (FirRegularClass) firDeclaration : null;
                    if (Intrinsics.areEqual((firRegularClass5 == null || (name2 = firRegularClass5.getName()) == null) ? null : name2.asString(), str)) {
                        if (!z2) {
                            z2 = true;
                            obj = next;
                        }
                    }
                    obj = null;
                    break;
                }
                if (obj instanceof FirRegularClass) {
                    firRegularClass = (FirRegularClass) obj;
                } else {
                    t2 = 0;
                }
                if (t2 == 0) {
                    t2 = firRegularClass;
                    return null;
                }
                t2 = firRegularClass;
                objectRef.element = t2;
                firRegularClass3 = firRegularClass4;
                z = false;
            }
            final ClassId classId = FirDeclarationUtilKt.getClassId((FirClassLikeDeclaration) objectRef.element);
            final FirTypeScope firTypeScopeUnsubstitutedScope = FirKotlinScopeProviderKt.unsubstitutedScope((FirClass) objectRef.element, this.fir2IrComponents.getSession(), this.fir2IrComponents.getScopeSession(), true, (FirResolvePhase) null);
            switch (WhenMappings.$EnumSwitchMapping$0[kind.ordinal()]) {
                case 1:
                    arrayListListOf = CollectionsKt.listOf(objectRef.element);
                    owner2 = (firRegularClass3 != null && (owner = this.classifierStorage.getIrClassSymbol(firRegularClass3.getSymbol()).getOwner()) != null) ? owner : irExternalPackageFragment$default;
                    break;
                case 2:
                    Name nameGuessByFirstCharacter = Name.guessByFirstCharacter((String) CollectionsKt.last((List) nameSegments));
                    Intrinsics.checkNotNullExpressionValue(nameGuessByFirstCharacter, "guessByFirstCharacter(...)");
                    Iterator<T> it3 = ((FirRegularClass) objectRef.element).getDeclarations().iterator();
                    Object obj2 = null;
                    boolean z3 = false;
                    while (true) {
                        if (it3.hasNext()) {
                            Object next2 = it3.next();
                            FirDeclaration firDeclaration2 = (FirDeclaration) next2;
                            FirEnumEntry firEnumEntry = firDeclaration2 instanceof FirEnumEntry ? (FirEnumEntry) firDeclaration2 : null;
                            if (Intrinsics.areEqual(firEnumEntry != null ? firEnumEntry.getName() : null, nameGuessByFirstCharacter)) {
                                if (!z3) {
                                    obj2 = next2;
                                    z3 = true;
                                }
                            }
                        } else if (!z3) {
                        }
                        obj2 = null;
                    }
                    FirDeclaration firDeclaration3 = (FirDeclaration) obj2;
                    if (firDeclaration3 == null || (arrayListListOf = CollectionsKt.listOf(firDeclaration3)) == null) {
                        return null;
                    }
                    owner2 = this.classifierStorage.getIrClassSymbol(((FirRegularClass) objectRef.element).getSymbol()).getOwner();
                    break;
                case 3:
                    arrayListListOf = new ArrayList();
                    firTypeScopeUnsubstitutedScope.processDeclaredConstructors(new Function1<FirConstructorSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.backend.FirIrProvider.getDeclarationForCommonSignature.5
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(FirConstructorSymbol firConstructorSymbol) {
                            invoke2(firConstructorSymbol);
                            return Unit.INSTANCE;
                        }

                        /* JADX WARN: Multi-variable type inference failed */
                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(FirConstructorSymbol it4) {
                            Intrinsics.checkNotNullParameter(it4, "it");
                            arrayListListOf.add((FirConstructor) it4.getFir());
                        }
                    });
                    owner2 = this.classifierStorage.getIrClassSymbol(((FirRegularClass) objectRef.element).getSymbol()).getOwner();
                    break;
                case 4:
                    owner3 = this.classifierStorage.getIrClassSymbol(((FirRegularClass) objectRef.element).getSymbol()).getOwner();
                    Name nameGuessByFirstCharacter2 = Name.guessByFirstCharacter((String) CollectionsKt.last((List) nameSegments));
                    Intrinsics.checkNotNullExpressionValue(nameGuessByFirstCharacter2, "guessByFirstCharacter(...)");
                    arrayList = new ArrayList();
                    firTypeScopeUnsubstitutedScope.processFunctionsByName(nameGuessByFirstCharacter2, new Function1<FirNamedFunctionSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.backend.FirIrProvider.getDeclarationForCommonSignature.6
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(FirNamedFunctionSymbol firNamedFunctionSymbol) {
                            invoke2(firNamedFunctionSymbol);
                            return Unit.INSTANCE;
                        }

                        /* JADX WARN: Multi-variable type inference failed */
                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(FirNamedFunctionSymbol functionSymbol) {
                            FirSimpleFunction first;
                            ConeClassLikeLookupTag lookupTag;
                            Intrinsics.checkNotNullParameter(functionSymbol, "functionSymbol");
                            ConeSimpleKotlinType dispatchReceiverType = ((FirSimpleFunction) functionSymbol.getFir()).getDispatchReceiverType();
                            ClassId classId2 = null;
                            ConeClassLikeType coneClassLikeType = dispatchReceiverType instanceof ConeClassLikeType ? (ConeClassLikeType) dispatchReceiverType : null;
                            if (coneClassLikeType != null && (lookupTag = coneClassLikeType.getLookupTag()) != null) {
                                classId2 = lookupTag.getClassId();
                            }
                            if (classId2 != null && !Intrinsics.areEqual(classId2, classId)) {
                                Pair<FirSimpleFunction, List<FirNamedFunctionSymbol>> pairCreateFirFunctionFakeOverride$fir2ir = this.fakeOverrideGenerator.createFirFunctionFakeOverride$fir2ir(objectRef.element, (IrClass) owner3, functionSymbol, firTypeScopeUnsubstitutedScope);
                                Intrinsics.checkNotNull(pairCreateFirFunctionFakeOverride$fir2ir);
                                first = pairCreateFirFunctionFakeOverride$fir2ir.getFirst();
                            } else {
                                first = (FirSimpleFunction) functionSymbol.getFir();
                            }
                            arrayList.add(first);
                        }
                    });
                    FirScope staticScopeForCallables = getStaticScopeForCallables((FirClass) objectRef.element);
                    if (staticScopeForCallables != null) {
                        staticScopeForCallables.processFunctionsByName(nameGuessByFirstCharacter2, new Function1<FirNamedFunctionSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.backend.FirIrProvider.getDeclarationForCommonSignature.7
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public /* bridge */ /* synthetic */ Unit invoke(FirNamedFunctionSymbol firNamedFunctionSymbol) {
                                invoke2(firNamedFunctionSymbol);
                                return Unit.INSTANCE;
                            }

                            /* JADX WARN: Multi-variable type inference failed */
                            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2(FirNamedFunctionSymbol functionSymbol) {
                                Intrinsics.checkNotNullParameter(functionSymbol, "functionSymbol");
                                arrayList.add((FirSimpleFunction) functionSymbol.getFir());
                            }
                        });
                    }
                    arrayListListOf = arrayList;
                    firDeclarationFindDeclarationByHash = findDeclarationByHash(arrayListListOf, signature.getId());
                    if (firDeclarationFindDeclarationByHash == null) {
                        return null;
                    }
                    switch (WhenMappings.$EnumSwitchMapping$0[kind.ordinal()]) {
                        case 1:
                            return this.classifierStorage.getIrClassSymbol(((FirRegularClass) firDeclarationFindDeclarationByHash).getSymbol()).getOwner();
                        case 2:
                            Intrinsics.checkNotNull(owner3, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
                            return Fir2IrClassifierStorage.getIrEnumEntry$default(this.classifierStorage, (FirEnumEntry) firDeclarationFindDeclarationByHash, (IrClass) owner3, null, 4, null);
                        case 3:
                            Fir2IrDeclarationStorage fir2IrDeclarationStorage = this.declarationStorage;
                            Intrinsics.checkNotNull(owner3, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
                            return Fir2IrDeclarationStorage.getOrCreateIrConstructor$default(fir2IrDeclarationStorage, (FirConstructor) firDeclarationFindDeclarationByHash, (IrClass) owner3, null, false, 12, null);
                        case 4:
                            return Fir2IrDeclarationStorage.getOrCreateIrFunction$default(this.declarationStorage, (FirSimpleFunction) firDeclarationFindDeclarationByHash, owner3, false, 4, null);
                        case 5:
                            return Fir2IrDeclarationStorage.getOrCreateIrProperty$default(this.declarationStorage, (FirProperty) firDeclarationFindDeclarationByHash, owner3, false, 4, null);
                        case 6:
                            return Fir2IrDeclarationStorage.getIrFieldSymbol$default(this.declarationStorage, ((FirField) firDeclarationFindDeclarationByHash).getSymbol(), null, 2, null).getOwner();
                        default:
                            throw new IllegalStateException(("Don't know how to deal with this symbol kind: " + kind).toString());
                    }
                case 5:
                    owner3 = this.classifierStorage.getIrClassSymbol(((FirRegularClass) objectRef.element).getSymbol()).getOwner();
                    Name nameGuessByFirstCharacter3 = Name.guessByFirstCharacter((String) CollectionsKt.last((List) nameSegments));
                    Intrinsics.checkNotNullExpressionValue(nameGuessByFirstCharacter3, "guessByFirstCharacter(...)");
                    arrayList = new ArrayList();
                    firTypeScopeUnsubstitutedScope.processPropertiesByName(nameGuessByFirstCharacter3, new Function1<FirVariableSymbol<?>, Unit>() { // from class: org.jetbrains.kotlin.fir.backend.FirIrProvider.getDeclarationForCommonSignature.8
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(FirVariableSymbol<?> firVariableSymbol) {
                            invoke2(firVariableSymbol);
                            return Unit.INSTANCE;
                        }

                        /* JADX WARN: Multi-variable type inference failed */
                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(FirVariableSymbol<?> propertySymbol) {
                            FirProperty first;
                            ConeClassLikeLookupTag lookupTag;
                            Intrinsics.checkNotNullParameter(propertySymbol, "propertySymbol");
                            FirPropertySymbol firPropertySymbol = (FirPropertySymbol) propertySymbol;
                            ConeSimpleKotlinType dispatchReceiverType = ((FirProperty) firPropertySymbol.getFir()).getDispatchReceiverType();
                            ClassId classId2 = null;
                            ConeClassLikeType coneClassLikeType = dispatchReceiverType instanceof ConeClassLikeType ? (ConeClassLikeType) dispatchReceiverType : null;
                            if (coneClassLikeType != null && (lookupTag = coneClassLikeType.getLookupTag()) != null) {
                                classId2 = lookupTag.getClassId();
                            }
                            if (classId2 != null && !Intrinsics.areEqual(classId2, classId)) {
                                Pair<FirProperty, List<FirPropertySymbol>> pairCreateFirPropertyFakeOverride$fir2ir = this.fakeOverrideGenerator.createFirPropertyFakeOverride$fir2ir(objectRef.element, (IrClass) owner3, firPropertySymbol, firTypeScopeUnsubstitutedScope);
                                Intrinsics.checkNotNull(pairCreateFirPropertyFakeOverride$fir2ir);
                                first = pairCreateFirPropertyFakeOverride$fir2ir.getFirst();
                            } else {
                                first = (FirProperty) firPropertySymbol.getFir();
                            }
                            arrayList.add(first);
                        }
                    });
                    arrayListListOf = arrayList;
                    firDeclarationFindDeclarationByHash = findDeclarationByHash(arrayListListOf, signature.getId());
                    if (firDeclarationFindDeclarationByHash == null) {
                        return null;
                    }
                    switch (WhenMappings.$EnumSwitchMapping$0[kind.ordinal()]) {
                        case 1:
                            return this.classifierStorage.getIrClassSymbol(((FirRegularClass) firDeclarationFindDeclarationByHash).getSymbol()).getOwner();
                        case 2:
                            Intrinsics.checkNotNull(owner3, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
                            return Fir2IrClassifierStorage.getIrEnumEntry$default(this.classifierStorage, (FirEnumEntry) firDeclarationFindDeclarationByHash, (IrClass) owner3, null, 4, null);
                        case 3:
                            Fir2IrDeclarationStorage fir2IrDeclarationStorage2 = this.declarationStorage;
                            Intrinsics.checkNotNull(owner3, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
                            return Fir2IrDeclarationStorage.getOrCreateIrConstructor$default(fir2IrDeclarationStorage2, (FirConstructor) firDeclarationFindDeclarationByHash, (IrClass) owner3, null, false, 12, null);
                        case 4:
                            return Fir2IrDeclarationStorage.getOrCreateIrFunction$default(this.declarationStorage, (FirSimpleFunction) firDeclarationFindDeclarationByHash, owner3, false, 4, null);
                        case 5:
                            return Fir2IrDeclarationStorage.getOrCreateIrProperty$default(this.declarationStorage, (FirProperty) firDeclarationFindDeclarationByHash, owner3, false, 4, null);
                        case 6:
                            return Fir2IrDeclarationStorage.getIrFieldSymbol$default(this.declarationStorage, ((FirField) firDeclarationFindDeclarationByHash).getSymbol(), null, 2, null).getOwner();
                        default:
                            throw new IllegalStateException(("Don't know how to deal with this symbol kind: " + kind).toString());
                    }
                case 6:
                    owner2 = this.classifierStorage.getIrClassSymbol(((FirRegularClass) objectRef.element).getSymbol()).getOwner();
                    Name nameGuessByFirstCharacter4 = Name.guessByFirstCharacter((String) CollectionsKt.last((List) nameSegments));
                    Intrinsics.checkNotNullExpressionValue(nameGuessByFirstCharacter4, "guessByFirstCharacter(...)");
                    arrayListListOf = new ArrayList();
                    firTypeScopeUnsubstitutedScope.processPropertiesByName(nameGuessByFirstCharacter4, new Function1<FirVariableSymbol<?>, Unit>() { // from class: org.jetbrains.kotlin.fir.backend.FirIrProvider.getDeclarationForCommonSignature.9
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(FirVariableSymbol<?> firVariableSymbol) {
                            invoke2(firVariableSymbol);
                            return Unit.INSTANCE;
                        }

                        /* JADX WARN: Multi-variable type inference failed */
                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(FirVariableSymbol<?> propertySymbol) {
                            Intrinsics.checkNotNullParameter(propertySymbol, "propertySymbol");
                            arrayListListOf.add((FirVariable) propertySymbol.getFir());
                        }
                    });
                    FirScope staticScopeForCallables2 = getStaticScopeForCallables((FirClass) objectRef.element);
                    if (staticScopeForCallables2 != null) {
                        staticScopeForCallables2.processPropertiesByName(nameGuessByFirstCharacter4, new Function1<FirVariableSymbol<?>, Unit>() { // from class: org.jetbrains.kotlin.fir.backend.FirIrProvider.getDeclarationForCommonSignature.10
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public /* bridge */ /* synthetic */ Unit invoke(FirVariableSymbol<?> firVariableSymbol) {
                                invoke2(firVariableSymbol);
                                return Unit.INSTANCE;
                            }

                            /* JADX WARN: Multi-variable type inference failed */
                            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2(FirVariableSymbol<?> propertySymbol) {
                                Intrinsics.checkNotNullParameter(propertySymbol, "propertySymbol");
                                arrayListListOf.add((FirVariable) propertySymbol.getFir());
                            }
                        });
                    }
                    break;
                default:
                    String str2 = (String) CollectionsKt.last((List) nameSegments);
                    List<FirDeclaration> declarations = ((FirRegularClass) objectRef.element).getDeclarations();
                    ArrayList arrayList3 = new ArrayList();
                    for (Object obj3 : declarations) {
                        FirDeclaration firDeclaration4 = (FirDeclaration) obj3;
                        if ((firDeclaration4 instanceof FirCallableDeclaration) && Intrinsics.areEqual(((FirCallableDeclaration) firDeclaration4).getSymbol().getName().asString(), str2)) {
                            arrayList3.add(obj3);
                        }
                    }
                    arrayListListOf = arrayList3;
                    owner2 = this.classifierStorage.getIrClassSymbol(((FirRegularClass) objectRef.element).getSymbol()).getOwner();
                    break;
            }
        }
        owner3 = owner2;
        firDeclarationFindDeclarationByHash = findDeclarationByHash(arrayListListOf, signature.getId());
        if (firDeclarationFindDeclarationByHash == null) {
            return null;
        }
        switch (WhenMappings.$EnumSwitchMapping$0[kind.ordinal()]) {
            case 1:
                return this.classifierStorage.getIrClassSymbol(((FirRegularClass) firDeclarationFindDeclarationByHash).getSymbol()).getOwner();
            case 2:
                Intrinsics.checkNotNull(owner3, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
                return Fir2IrClassifierStorage.getIrEnumEntry$default(this.classifierStorage, (FirEnumEntry) firDeclarationFindDeclarationByHash, (IrClass) owner3, null, 4, null);
            case 3:
                Fir2IrDeclarationStorage fir2IrDeclarationStorage3 = this.declarationStorage;
                Intrinsics.checkNotNull(owner3, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
                return Fir2IrDeclarationStorage.getOrCreateIrConstructor$default(fir2IrDeclarationStorage3, (FirConstructor) firDeclarationFindDeclarationByHash, (IrClass) owner3, null, false, 12, null);
            case 4:
                return Fir2IrDeclarationStorage.getOrCreateIrFunction$default(this.declarationStorage, (FirSimpleFunction) firDeclarationFindDeclarationByHash, owner3, false, 4, null);
            case 5:
                return Fir2IrDeclarationStorage.getOrCreateIrProperty$default(this.declarationStorage, (FirProperty) firDeclarationFindDeclarationByHash, owner3, false, 4, null);
            case 6:
                return Fir2IrDeclarationStorage.getIrFieldSymbol$default(this.declarationStorage, ((FirField) firDeclarationFindDeclarationByHash).getSymbol(), null, 2, null).getOwner();
            default:
                throw new IllegalStateException(("Don't know how to deal with this symbol kind: " + kind).toString());
        }
    }

    private final FirDeclaration findDeclarationByHash(Collection<? extends FirDeclaration> candidates, Long hash) {
        Object next;
        Iterator<T> it = candidates.iterator();
        while (it.hasNext()) {
            next = it.next();
            FirDeclaration firDeclaration = (FirDeclaration) next;
            if (hash == null) {
                if ((firDeclaration instanceof FirClass) || (firDeclaration instanceof FirEnumEntry) || (firDeclaration instanceof FirTypeAlias)) {
                    return (FirDeclaration) next;
                }
            } else if (this.fir2IrComponents.getSignatureComposer().getMangler().signatureMangle(firDeclaration, false) == hash.longValue()) {
                return (FirDeclaration) next;
            }
        }
        next = null;
        return (FirDeclaration) next;
    }

    private final FirScope getStaticScopeForCallables(FirClass firClass) {
        return firClass.getScopeProvider().getStaticMemberScopeForCallables(firClass, this.fir2IrComponents.getSession(), this.fir2IrComponents.getScopeSession());
    }
}
