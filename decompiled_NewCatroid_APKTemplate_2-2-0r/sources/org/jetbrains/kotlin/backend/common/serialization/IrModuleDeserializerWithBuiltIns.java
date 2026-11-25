package org.jetbrains.kotlin.backend.common.serialization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.CharsKt;
import net.bytebuddy.implementation.MethodDelegation;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.backend.common.serialization.encodings.BinarySymbolData;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.library.IrLibrary;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IrModuleDeserializer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u009a\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0001¢\u0006\u0002\u0010\u0005J\u0010\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\bH\u0016J\u0010\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020\u001cH\u0002J\u0011\u0010&\u001a\u00020'2\u0006\u0010\"\u001a\u00020\bH\u0096\u0002J\u0010\u0010(\u001a\u00020!2\u0006\u0010)\u001a\u00020\tH\u0016J\b\u0010*\u001a\u00020!H\u0016J\u0010\u0010+\u001a\u00020,2\u0006\u0010\"\u001a\u00020\bH\u0016J\u000e\u0010-\u001a\b\u0012\u0004\u0012\u00020.0\u0013H\u0016J\b\u0010/\u001a\u00020!H\u0016J\b\u00100\u001a\u00020!H\u0016J\u0018\u00101\u001a\u0002022\u0006\u00103\u001a\u0002042\u0006\u00105\u001a\u00020\bH\u0016J\u0018\u00106\u001a\u0002072\u0006\u00103\u001a\u0002042\u0006\u00105\u001a\u00020\bH\u0016J\u0018\u00108\u001a\u00020\t2\u0006\u0010\"\u001a\u00020\b2\u0006\u00109\u001a\u00020:H\u0002J\u0010\u0010;\u001a\u00020<2\u0006\u0010=\u001a\u00020\u001cH\u0016J\u001a\u0010>\u001a\u0004\u0018\u00010\t2\u0006\u0010\"\u001a\u00020\b2\u0006\u00109\u001a\u00020:H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0006\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\b\u0012\u0004\u0012\u00020\t0\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\u00138VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0016\u001a\u00020\u00178VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R \u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\u001d0\u001b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001f¨\u0006?"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/IrModuleDeserializerWithBuiltIns;", "Lorg/jetbrains/kotlin/backend/common/serialization/IrModuleDeserializer;", "builtIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "(Lorg/jetbrains/kotlin/ir/IrBuiltIns;Lorg/jetbrains/kotlin/backend/common/serialization/IrModuleDeserializer;)V", "irBuiltInsMap", "", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/backend/common/serialization/IrModuleDeserializerKind;", "getKind", "()Lorg/jetbrains/kotlin/backend/common/serialization/IrModuleDeserializerKind;", "klib", "Lorg/jetbrains/kotlin/library/IrLibrary;", "getKlib", "()Lorg/jetbrains/kotlin/library/IrLibrary;", "moduleDependencies", "", "getModuleDependencies", "()Ljava/util/Collection;", "moduleFragment", "Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "getModuleFragment", "()Lorg/jetbrains/kotlin/ir/declarations/IrModuleFragment;", "strategyResolver", "Lkotlin/Function1;", "", "Lorg/jetbrains/kotlin/backend/common/serialization/DeserializationStrategy;", "getStrategyResolver", "()Lkotlin/jvm/functions/Function1;", "addModuleReachableTopLevel", "", "idSig", "computeFunctionClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "className", "contains", "", "declareIrSymbol", NativeSymbol.TYPE_NAME, "deserializeReachableDeclarations", "deserializedSymbolNotFound", "", "fileDeserializers", "Lorg/jetbrains/kotlin/backend/common/serialization/IrFileDeserializer;", ConfigConstants.CONFIG_INIT_SECTION, "postProcess", "referencePropertyByLocalSignature", "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "file", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "idSignature", "referenceSimpleFunctionByLocalSignature", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "resolveFunctionalInterface", "symbolKind", "Lorg/jetbrains/kotlin/backend/common/serialization/encodings/BinarySymbolData$SymbolKind;", "signatureDeserializerForFile", "Lorg/jetbrains/kotlin/backend/common/serialization/IdSignatureDeserializer;", PsiTreeChangeEvent.PROP_FILE_NAME, "tryDeserializeIrSymbol", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrModuleDeserializerWithBuiltIns extends IrModuleDeserializer {
    private final IrBuiltIns builtIns;
    private final IrModuleDeserializer delegate;
    private final Map<IdSignature, IrSymbol> irBuiltInsMap;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IrModuleDeserializerWithBuiltIns(IrBuiltIns builtIns, IrModuleDeserializer delegate) {
        super(delegate.getModuleDescriptor(), delegate.getLibraryAbiVersion());
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        Intrinsics.checkNotNullParameter(delegate, "delegate");
        this.builtIns = builtIns;
        this.delegate = delegate;
        List<IrDeclaration> knownBuiltins = DeclarationTableKt.getKnownBuiltins(builtIns);
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(knownBuiltins, 10)), 16));
        for (IrDeclaration irDeclaration : knownBuiltins) {
            Intrinsics.checkNotNull(irDeclaration, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrSymbolOwner");
            IrSymbol symbol = irDeclaration.getSymbol();
            Pair pair = TuplesKt.to(symbol.getSignature(), symbol);
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        this.irBuiltInsMap = linkedHashMap;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
    public boolean contains(IdSignature idSig) {
        Intrinsics.checkNotNullParameter(idSig, "idSig");
        IdSignature idSignature = idSig.topLevelSignature();
        return this.irBuiltInsMap.containsKey(idSignature) || FunctionalInterfacesUtilsKt.checkIsFunctionInterface(idSignature) || this.delegate.contains(idSig);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
    public IrSimpleFunctionSymbol referenceSimpleFunctionByLocalSignature(IrFile file, IdSignature idSignature) {
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(idSignature, "idSignature");
        return this.delegate.referenceSimpleFunctionByLocalSignature(file, idSignature);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
    public IrPropertySymbol referencePropertyByLocalSignature(IrFile file, IdSignature idSignature) {
        Intrinsics.checkNotNullParameter(file, "file");
        Intrinsics.checkNotNullParameter(idSignature, "idSignature");
        return this.delegate.referencePropertyByLocalSignature(file, idSignature);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
    public void deserializeReachableDeclarations() {
        this.delegate.deserializeReachableDeclarations();
    }

    private final IrClass computeFunctionClass(String className) {
        int i = 0;
        boolean z = className.charAt(0) == 'K';
        boolean z2 = (z ? className.charAt(1) : className.charAt(0)) == 'S';
        String str = className;
        int length = str.length();
        while (true) {
            if (i >= length) {
                i = -1;
                break;
            }
            if (Character.isDigit(str.charAt(i))) {
                break;
            }
            i++;
        }
        String strSubstring = className.substring(i);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        int i2 = Integer.parseInt(strSubstring, CharsKt.checkRadix(10));
        IrBuiltIns irBuiltIns = this.builtIns;
        if (z && z2) {
            return irBuiltIns.kSuspendFunctionN(i2);
        }
        if (z) {
            return irBuiltIns.kFunctionN(i2);
        }
        if (z2) {
            return irBuiltIns.suspendFunctionN(i2);
        }
        return irBuiltIns.functionN(i2);
    }

    private final IrSymbol resolveFunctionalInterface(IdSignature idSig, BinarySymbolData.SymbolKind symbolKind) {
        if (symbolKind == BinarySymbolData.SymbolKind.TYPE_PARAMETER_SYMBOL) {
            Intrinsics.checkNotNull(idSig, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.util.IdSignature.CompositeSignature");
            IdSignature.CompositeSignature compositeSignature = (IdSignature.CompositeSignature) idSig;
            IrSymbol irSymbolResolveFunctionalInterface = resolveFunctionalInterface(compositeSignature.getContainer(), BinarySymbolData.SymbolKind.CLASS_SYMBOL);
            Intrinsics.checkNotNull(irSymbolResolveFunctionalInterface, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrClassSymbol");
            IdSignature inner = compositeSignature.getInner();
            Intrinsics.checkNotNull(inner, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.util.IdSignature.LocalSignature");
            return ((IrClassSymbol) irSymbolResolveFunctionalInterface).getOwner().getTypeParameters().get(((IdSignature.LocalSignature) inner).index()).getSymbol();
        }
        IdSignature.CommonSignature accessorSignature = idSig.getAccessorSignature();
        if (accessorSignature == null) {
            throw new IllegalStateException((idSig + " has to be public").toString());
        }
        List<String> nameSegments = accessorSignature.getNameSegments();
        String str = (String) CollectionsKt.firstOrNull((List) nameSegments);
        if (str == null) {
            throw new IllegalStateException(("Expected class name for " + idSig).toString());
        }
        IrClass irClassComputeFunctionClass = computeFunctionClass(str);
        int size = nameSegments.size();
        if (size == 1) {
            IrClassSymbol symbol = irClassComputeFunctionClass.getSymbol();
            BinarySymbolData.SymbolKind symbolKind2 = BinarySymbolData.SymbolKind.CLASS_SYMBOL;
            return symbol;
        }
        boolean z = false;
        Object obj = null;
        if (size == 2) {
            String str2 = nameSegments.get(1);
            for (Object obj2 : irClassComputeFunctionClass.getDeclarations()) {
                IrDeclaration irDeclaration = (IrDeclaration) obj2;
                if ((irDeclaration instanceof IrDeclarationWithName) && Intrinsics.areEqual(((IrDeclarationWithName) irDeclaration).getName().asString(), str2)) {
                    if (z) {
                        throw new IllegalArgumentException("Collection contains more than one matching element.");
                    }
                    obj = obj2;
                    z = true;
                }
            }
            if (z) {
                IrDeclaration irDeclaration2 = (IrDeclaration) obj;
                Intrinsics.checkNotNull(irDeclaration2, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrSymbolOwner");
                return irDeclaration2.getSymbol();
            }
            throw new NoSuchElementException("Collection contains no element matching the predicate.");
        }
        if (size == 3) {
            boolean z2 = idSig instanceof IdSignature.AccessorSignature;
            BinarySymbolData.SymbolKind symbolKind3 = BinarySymbolData.SymbolKind.FUNCTION_SYMBOL;
            String str3 = nameSegments.get(1);
            String str4 = nameSegments.get(2);
            List<IrDeclaration> declarations = irClassComputeFunctionClass.getDeclarations();
            ArrayList arrayList = new ArrayList();
            for (Object obj3 : declarations) {
                if (obj3 instanceof IrProperty) {
                    arrayList.add(obj3);
                }
            }
            for (Object obj4 : arrayList) {
                if (Intrinsics.areEqual(((IrProperty) obj4).getName().asString(), str3)) {
                    if (z) {
                        throw new IllegalArgumentException("Collection contains more than one matching element.");
                    }
                    z = true;
                    obj = obj4;
                }
            }
            if (z) {
                IrProperty irProperty = (IrProperty) obj;
                IrSimpleFunction getter = irProperty.getGetter();
                if (getter != null && Intrinsics.areEqual(getter.getName().asString(), str4)) {
                    return getter.getSymbol();
                }
                IrSimpleFunction setter = irProperty.getSetter();
                if (setter == null || !Intrinsics.areEqual(setter.getName().asString(), str4)) {
                    throw new IllegalStateException(("No accessor found for signature " + idSig).toString());
                }
                return setter.getSymbol();
            }
            throw new NoSuchElementException("Collection contains no element matching the predicate.");
        }
        throw new IllegalStateException(("No member found for signature " + idSig).toString());
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
    /* JADX INFO: renamed from: tryDeserializeIrSymbol */
    public IrSymbol mo6122tryDeserializeIrSymbol(IdSignature idSig, BinarySymbolData.SymbolKind symbolKind) {
        Intrinsics.checkNotNullParameter(idSig, "idSig");
        Intrinsics.checkNotNullParameter(symbolKind, "symbolKind");
        IrSymbol irSymbol = this.irBuiltInsMap.get(idSig);
        if (irSymbol != null) {
            return irSymbol;
        }
        return FunctionalInterfacesUtilsKt.checkIsFunctionInterface(idSig.topLevelSignature()) ? resolveFunctionalInterface(idSig, symbolKind) : this.delegate.mo6122tryDeserializeIrSymbol(idSig, symbolKind);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
    public Void deserializedSymbolNotFound(IdSignature idSig) {
        Intrinsics.checkNotNullParameter(idSig, "idSig");
        this.delegate.deserializedSymbolNotFound(idSig);
        throw null;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
    public void declareIrSymbol(IrSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        IdSignature signature = symbol.getSignature();
        if (signature != null && FunctionalInterfacesUtilsKt.checkIsFunctionInterface(signature)) {
            resolveFunctionalInterface(signature, IrModuleDeserializerKt.kind(symbol));
        } else {
            this.delegate.declareIrSymbol(symbol);
        }
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
    public void init() {
        this.delegate.init(this);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
    public IrLibrary getKlib() {
        return this.delegate.getKlib();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
    public Function1<String, DeserializationStrategy> getStrategyResolver() {
        return this.delegate.getStrategyResolver();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
    public void addModuleReachableTopLevel(IdSignature idSig) {
        Intrinsics.checkNotNullParameter(idSig, "idSig");
        this.delegate.addModuleReachableTopLevel(idSig);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
    public IrModuleFragment getModuleFragment() {
        return this.delegate.getModuleFragment();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
    public Collection<IrModuleDeserializer> getModuleDependencies() {
        return this.delegate.getModuleDependencies();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
    public IrModuleDeserializerKind getKind() {
        return this.delegate.getKind();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
    public Collection<IrFileDeserializer> fileDeserializers() {
        return this.delegate.fileDeserializers();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
    public void postProcess() {
        this.delegate.postProcess();
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrModuleDeserializer
    public IdSignatureDeserializer signatureDeserializerForFile(String fileName) {
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        return this.delegate.signatureDeserializerForFile(fileName);
    }
}
