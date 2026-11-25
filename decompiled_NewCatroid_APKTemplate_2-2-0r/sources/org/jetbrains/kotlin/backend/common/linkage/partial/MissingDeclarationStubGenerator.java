package org.jetbrains.kotlin.backend.common.linkage.partial;

import androidx.exifinterface.media.ExifInterface;
import com.badlogic.gdx.physics.bullet.collision.ContactCallbackEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrExternalPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeAlias;
import org.jetbrains.kotlin.ir.declarations.impl.IrExternalPackageFragmentImpl;
import org.jetbrains.kotlin.ir.linkage.IrProvider;
import org.jetbrains.kotlin.ir.linkage.partial.PartiallyLinkedDeclarationOrigin;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrEnumEntrySymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeAliasSymbol;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedContainerSource;
import org.jetbrains.kotlin.types.error.ErrorUtils;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: MissingDeclarationStubGenerator.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u008c\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0019\u001a\u00020\u001dH\u0002J\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u0019\u001a\u00020 H\u0002J\u0010\u0010!\u001a\u00020\"2\u0006\u0010\u0019\u001a\u00020#H\u0002J\u0010\u0010$\u001a\u00020%2\u0006\u0010\u0019\u001a\u00020&H\u0002J\u0010\u0010'\u001a\u00020(2\u0006\u0010\u0019\u001a\u00020)H\u0002J\u0010\u0010*\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u0007H\u0016J\f\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00120,J\f\u0010-\u001a\u00020.*\u00020\u0007H\u0002J\u001b\u0010/\u001a\u0002H0\"\b\b\u0000\u00100*\u00020\u0012*\u0002H0H\u0002¢\u0006\u0002\u00101R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068F¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\n\u001a\u00020\u000b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\f\u0010\rR\u001e\u0010\u0010\u001a\u0012\u0012\u0004\u0012\u00020\u00120\u0011j\b\u0012\u0004\u0012\u00020\u0012`\u0013X\u0082\u000e¢\u0006\u0002\n\u0000R\u001e\u0010\u0014\u001a\u0012\u0012\u0004\u0012\u00020\u00070\u0015j\b\u0012\u0004\u0012\u00020\u0007`\u0016X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00062"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/partial/MissingDeclarationStubGenerator;", "Lorg/jetbrains/kotlin/ir/linkage/IrProvider;", "builtIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "(Lorg/jetbrains/kotlin/ir/IrBuiltIns;)V", "allStubbedSymbols", "", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "getAllStubbedSymbols", "()Ljava/util/Set;", "commonParent", "Lorg/jetbrains/kotlin/ir/declarations/IrExternalPackageFragment;", "getCommonParent", "()Lorg/jetbrains/kotlin/ir/declarations/IrExternalPackageFragment;", "commonParent$delegate", "Lkotlin/Lazy;", "declarationsToPatch", "Ljava/util/ArrayList;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "Lkotlin/collections/ArrayList;", "stubbedSymbols", "Ljava/util/HashSet;", "Lkotlin/collections/HashSet;", "generateClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "generateConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "generateEnumEntry", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "Lorg/jetbrains/kotlin/ir/symbols/IrEnumEntrySymbol;", "generateProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "generateSimpleFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "generateTypeAlias", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeAlias;", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeAliasSymbol;", "getDeclaration", "grabDeclarationsToPatch", "", "guessName", "Lorg/jetbrains/kotlin/name/Name;", "setCommonParent", ExifInterface.GPS_DIRECTION_TRUE, "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;)Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MissingDeclarationStubGenerator implements IrProvider {
    private final IrBuiltIns builtIns;

    /* JADX INFO: renamed from: commonParent$delegate, reason: from kotlin metadata */
    private final Lazy commonParent;
    private ArrayList<IrDeclaration> declarationsToPatch;
    private final HashSet<IrSymbol> stubbedSymbols;

    public MissingDeclarationStubGenerator(IrBuiltIns builtIns) {
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        this.builtIns = builtIns;
        this.commonParent = LazyKt.lazy(new Function0<IrExternalPackageFragment>() { // from class: org.jetbrains.kotlin.backend.common.linkage.partial.MissingDeclarationStubGenerator$commonParent$2
            @Override // kotlin.jvm.functions.Function0
            public final IrExternalPackageFragment invoke() {
                IrExternalPackageFragmentImpl.Companion companion = IrExternalPackageFragmentImpl.INSTANCE;
                ModuleDescriptor errorModule = ErrorUtils.INSTANCE.getErrorModule();
                FqName ROOT = FqName.ROOT;
                Intrinsics.checkNotNullExpressionValue(ROOT, "ROOT");
                return companion.createEmptyExternalPackageFragment(errorModule, ROOT);
            }
        });
        this.declarationsToPatch = new ArrayList<>();
        this.stubbedSymbols = new HashSet<>();
    }

    private final IrExternalPackageFragment getCommonParent() {
        return (IrExternalPackageFragment) this.commonParent.getValue();
    }

    public final Set<IrSymbol> getAllStubbedSymbols() {
        return this.stubbedSymbols;
    }

    public final Collection<IrDeclaration> grabDeclarationsToPatch() {
        ArrayList<IrDeclaration> arrayList = this.declarationsToPatch;
        this.declarationsToPatch = new ArrayList<>();
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.ir.linkage.IrProvider
    public IrDeclaration getDeclaration(IrSymbol symbol) {
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        if (symbol.isBound()) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        this.stubbedSymbols.add(symbol);
        if (symbol instanceof IrClassSymbol) {
            return generateClass((IrClassSymbol) symbol);
        }
        if (symbol instanceof IrSimpleFunctionSymbol) {
            return generateSimpleFunction((IrSimpleFunctionSymbol) symbol);
        }
        if (symbol instanceof IrConstructorSymbol) {
            return generateConstructor((IrConstructorSymbol) symbol);
        }
        if (symbol instanceof IrPropertySymbol) {
            return generateProperty((IrPropertySymbol) symbol);
        }
        if (symbol instanceof IrEnumEntrySymbol) {
            return generateEnumEntry((IrEnumEntrySymbol) symbol);
        }
        if (symbol instanceof IrTypeAliasSymbol) {
            return generateTypeAlias((IrTypeAliasSymbol) symbol);
        }
        throw new NotImplementedError("Generation of stubs for " + symbol.getClass() + " is not supported yet");
    }

    private final IrClass generateClass(IrClassSymbol symbol) {
        IrFactory irFactory = this.builtIns.getIrFactory();
        PartiallyLinkedDeclarationOrigin partiallyLinkedDeclarationOrigin = PartiallyLinkedDeclarationOrigin.MISSING_DECLARATION;
        Name nameGuessName = guessName(symbol);
        DescriptorVisibility DEFAULT_VISIBILITY = DescriptorVisibilities.DEFAULT_VISIBILITY;
        Intrinsics.checkNotNullExpressionValue(DEFAULT_VISIBILITY, "DEFAULT_VISIBILITY");
        IrClass irClassCreateClass$default = IrFactory.DefaultImpls.createClass$default(irFactory, -1, -1, (IrDeclarationOrigin) partiallyLinkedDeclarationOrigin, nameGuessName, DEFAULT_VISIBILITY, symbol, ClassKind.CLASS, Modality.OPEN, false, false, false, false, false, false, false, (SourceElement) null, 65280, (Object) null);
        setCommonParent(irClassCreateClass$default);
        IrUtilsKt.createImplicitParameterDeclarationWithWrappedDescriptor(irClassCreateClass$default);
        return irClassCreateClass$default;
    }

    private final IrSimpleFunction generateSimpleFunction(IrSimpleFunctionSymbol symbol) {
        IrFactory irFactory = this.builtIns.getIrFactory();
        PartiallyLinkedDeclarationOrigin partiallyLinkedDeclarationOrigin = PartiallyLinkedDeclarationOrigin.MISSING_DECLARATION;
        Name nameGuessName = guessName(symbol);
        DescriptorVisibility DEFAULT_VISIBILITY = DescriptorVisibilities.DEFAULT_VISIBILITY;
        Intrinsics.checkNotNullExpressionValue(DEFAULT_VISIBILITY, "DEFAULT_VISIBILITY");
        return (IrSimpleFunction) setCommonParent(IrFactory.DefaultImpls.createSimpleFunction$default(irFactory, -1, -1, partiallyLinkedDeclarationOrigin, nameGuessName, DEFAULT_VISIBILITY, false, false, this.builtIns.getNothingType(), Modality.FINAL, symbol, false, false, false, false, false, null, false, 98304, null));
    }

    private final IrConstructor generateConstructor(IrConstructorSymbol symbol) {
        IrFactory irFactory = this.builtIns.getIrFactory();
        PartiallyLinkedDeclarationOrigin partiallyLinkedDeclarationOrigin = PartiallyLinkedDeclarationOrigin.MISSING_DECLARATION;
        Name name2 = SpecialNames.INIT;
        DescriptorVisibility DEFAULT_VISIBILITY = DescriptorVisibilities.DEFAULT_VISIBILITY;
        Intrinsics.checkNotNullExpressionValue(DEFAULT_VISIBILITY, "DEFAULT_VISIBILITY");
        return (IrConstructor) setCommonParent(IrFactory.DefaultImpls.createConstructor$default(irFactory, -1, -1, (IrDeclarationOrigin) partiallyLinkedDeclarationOrigin, name2, DEFAULT_VISIBILITY, false, false, this.builtIns.getNothingType(), symbol, false, false, (DeserializedContainerSource) null, 2048, (Object) null));
    }

    private final IrProperty generateProperty(IrPropertySymbol symbol) {
        IrFactory irFactory = this.builtIns.getIrFactory();
        PartiallyLinkedDeclarationOrigin partiallyLinkedDeclarationOrigin = PartiallyLinkedDeclarationOrigin.MISSING_DECLARATION;
        Name nameGuessName = guessName(symbol);
        DescriptorVisibility DEFAULT_VISIBILITY = DescriptorVisibilities.DEFAULT_VISIBILITY;
        Intrinsics.checkNotNullExpressionValue(DEFAULT_VISIBILITY, "DEFAULT_VISIBILITY");
        return (IrProperty) setCommonParent(IrFactory.DefaultImpls.createProperty$default(irFactory, -1, -1, (IrDeclarationOrigin) partiallyLinkedDeclarationOrigin, nameGuessName, DEFAULT_VISIBILITY, Modality.FINAL, symbol, false, false, false, false, false, (DeserializedContainerSource) null, false, false, ContactCallbackEvent.ON_STARTED_FILTERED_MANIFOLD, (Object) null));
    }

    private final IrEnumEntry generateEnumEntry(IrEnumEntrySymbol symbol) {
        return (IrEnumEntry) setCommonParent(this.builtIns.getIrFactory().createEnumEntry(-1, -1, PartiallyLinkedDeclarationOrigin.MISSING_DECLARATION, guessName(symbol), symbol));
    }

    private final IrTypeAlias generateTypeAlias(IrTypeAliasSymbol symbol) {
        IrFactory irFactory = this.builtIns.getIrFactory();
        PartiallyLinkedDeclarationOrigin partiallyLinkedDeclarationOrigin = PartiallyLinkedDeclarationOrigin.MISSING_DECLARATION;
        Name nameGuessName = guessName(symbol);
        DescriptorVisibility DEFAULT_VISIBILITY = DescriptorVisibilities.DEFAULT_VISIBILITY;
        Intrinsics.checkNotNullExpressionValue(DEFAULT_VISIBILITY, "DEFAULT_VISIBILITY");
        return (IrTypeAlias) setCommonParent(irFactory.createTypeAlias(-1, -1, (IrDeclarationOrigin) partiallyLinkedDeclarationOrigin, nameGuessName, DEFAULT_VISIBILITY, symbol, true, this.builtIns.getNothingType()));
    }

    private final <T extends IrDeclaration> T setCommonParent(T t) {
        t.setParent(getCommonParent());
        this.declarationsToPatch.add(t);
        return t;
    }

    private final Name guessName(IrSymbol irSymbol) {
        String strGuessName;
        IdSignature signature = irSymbol.getSignature();
        Name nameGuessByFirstCharacter = (signature == null || (strGuessName = PartialLinkageUtils.INSTANCE.guessName(signature, 1)) == null) ? null : Name.guessByFirstCharacter(strGuessName);
        return nameGuessByFirstCharacter == null ? PartialLinkageUtils.INSTANCE.getUNKNOWN_NAME() : nameGuessByFirstCharacter;
    }
}
