package org.jetbrains.kotlin.ir.backend.js.ic;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithVisibility;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.ir.util.IrFakeOverrideUtilsKt;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: IdSignatureSource.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\u001aJ\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00020\u00042\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0000ø\u0001\u0000¢\u0006\u0004\b\f\u0010\r\u001a(\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u000f0\u00062\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u000f0\u0006H\u0002\u001a\u0012\u0010\u0011\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0012\u001a\u00020\u000fH\u0000\u0082\u0002\u0007\n\u0005\b¡\u001e0\u0001¨\u0006\u0013"}, d2 = {"addParentSignatures", "", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "signatures", "", "idSignatureToFile", "", "Lorg/jetbrains/kotlin/ir/backend/js/ic/IdSignatureSource;", "importerLibFile", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinLibraryFile;", "importerSrcFile", "Lorg/jetbrains/kotlin/ir/backend/js/ic/KotlinSourceFile;", "addParentSignatures-ouLM99E", "(Ljava/util/Collection;Ljava/util/Map;Ljava/lang/String;Ljava/lang/String;)Ljava/util/Set;", "collectImplementedSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "deserializedSymbols", "resolveFakeOverrideFunction", NativeSymbol.TYPE_NAME, "backend.js"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IdSignatureSourceKt {
    /* JADX INFO: renamed from: addParentSignatures-ouLM99E, reason: not valid java name */
    public static final Set<IdSignature> m7134addParentSignaturesouLM99E(Collection<? extends IdSignature> signatures, Map<IdSignature, IdSignatureSource> idSignatureToFile, String importerLibFile, String importerSrcFile) {
        Intrinsics.checkNotNullParameter(signatures, "signatures");
        Intrinsics.checkNotNullParameter(idSignatureToFile, "idSignatureToFile");
        Intrinsics.checkNotNullParameter(importerLibFile, "importerLibFile");
        Intrinsics.checkNotNullParameter(importerSrcFile, "importerSrcFile");
        HashSet hashSet = new HashSet(signatures.size());
        Iterator<T> it = signatures.iterator();
        while (it.hasNext()) {
            addParentSignatures_ouLM99E$addAllParents(idSignatureToFile, importerLibFile, importerSrcFile, hashSet, (IdSignature) it.next());
        }
        return hashSet;
    }

    private static final void addParentSignatures_ouLM99E$addAllParents(Map<IdSignature, IdSignatureSource> map, String str, String str2, HashSet<IdSignature> hashSet, IdSignature idSignature) {
        IdSignature signature;
        IdSignatureSource idSignatureSource = map.get(idSignature);
        if (idSignatureSource == null) {
            return;
        }
        if (!(KotlinLibraryFile.m7153equalsimpl0(idSignatureSource.getLib(), str) && KotlinSourceFile.m7167equalsimpl0(idSignatureSource.m7133getSrczDqpWdY(), str2)) && hashSet.add(idSignature)) {
            IrSymbolOwner owner = idSignatureSource.getSymbol().getOwner();
            IrDeclaration irDeclaration = owner instanceof IrDeclaration ? (IrDeclaration) owner : null;
            if (irDeclaration != null) {
                IrDeclarationParent parent = irDeclaration.getParent();
                IrSymbolOwner irSymbolOwner = parent instanceof IrSymbolOwner ? (IrSymbolOwner) parent : null;
                if (irSymbolOwner == null || (signature = irSymbolOwner.getSymbol().getSignature()) == null) {
                    return;
                }
                addParentSignatures_ouLM99E$addAllParents(map, str, str2, hashSet, signature);
            }
        }
    }

    public static final IdSignature resolveFakeOverrideFunction(IrSymbol symbol) {
        IrSimpleFunction irSimpleFunction;
        IrSimpleFunctionSymbol symbol2;
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        IrSymbolOwner owner = symbol.getOwner();
        IrSimpleFunction irSimpleFunction2 = owner instanceof IrSimpleFunction ? (IrSimpleFunction) owner : null;
        if (irSimpleFunction2 == null || !irSimpleFunction2.getIsFakeOverride() || (irSimpleFunction = (IrSimpleFunction) IrFakeOverrideUtilsKt.resolveFakeOverride$default(irSimpleFunction2, false, null, 3, null)) == null || (symbol2 = irSimpleFunction.getSymbol()) == null) {
            return null;
        }
        return symbol2.getSignature();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Map<IdSignature, IrSymbol> collectImplementedSymbol(Map<IdSignature, ? extends IrSymbol> map) {
        HashMap map2 = new HashMap(map.size());
        for (Map.Entry<IdSignature, ? extends IrSymbol> entry : map.entrySet()) {
            IdSignature key = entry.getKey();
            IrSymbol value = entry.getValue();
            map2.put(key, value);
            IrSymbolOwner owner = value.getOwner();
            IrClass irClass = owner instanceof IrClass ? (IrClass) owner : null;
            if (irClass != null) {
                collectImplementedSymbol$lambda$7$addNestedDeclarations(map, map2, irClass);
            }
        }
        return map2;
    }

    private static final <T extends IrDeclarationWithVisibility & IrSymbolOwner> boolean collectImplementedSymbol$lambda$7$addSymbol(Map<IdSignature, ? extends IrSymbol> map, HashMap<IdSignature, IrSymbol> map2, T t) {
        IdSignature signature;
        DescriptorVisibility visibility = t.getVisibility();
        return (Intrinsics.areEqual(visibility, DescriptorVisibilities.LOCAL) || Intrinsics.areEqual(visibility, DescriptorVisibilities.PRIVATE) || Intrinsics.areEqual(visibility, DescriptorVisibilities.PRIVATE_TO_THIS) || (signature = t.getSymbol().getSignature()) == null || map.containsKey(signature) || map2.put(signature, t.getSymbol()) != null) ? false : true;
    }

    private static final void collectImplementedSymbol$lambda$7$addNestedDeclarations(Map<IdSignature, ? extends IrSymbol> map, HashMap<IdSignature, IrSymbol> map2, IrClass irClass) {
        for (IrDeclaration irDeclaration : irClass.getDeclarations()) {
            if (irDeclaration instanceof IrSimpleFunction) {
                collectImplementedSymbol$lambda$7$addSymbol(map, map2, (IrDeclarationWithVisibility) irDeclaration);
            } else if (irDeclaration instanceof IrProperty) {
                IrProperty irProperty = (IrProperty) irDeclaration;
                IrSimpleFunction getter = irProperty.getGetter();
                if (getter != null) {
                    collectImplementedSymbol$lambda$7$addSymbol(map, map2, getter);
                }
                IrSimpleFunction setter = irProperty.getSetter();
                if (setter != null) {
                    collectImplementedSymbol$lambda$7$addSymbol(map, map2, setter);
                }
            } else if ((irDeclaration instanceof IrClass) && collectImplementedSymbol$lambda$7$addSymbol(map, map2, (IrDeclarationWithVisibility) irDeclaration)) {
                collectImplementedSymbol$lambda$7$addNestedDeclarations(map, map2, (IrClass) irDeclaration);
            }
        }
    }
}
