package org.jetbrains.kotlin.ir.util;

import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeAlias;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrEnumEntrySymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeAliasSymbol;
import org.jetbrains.kotlin.ir.symbols.impl.IrClassPublicSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrClassSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrConstructorPublicSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrConstructorSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrEnumEntryPublicSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrEnumEntrySymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrPropertyPublicSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrPropertySymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrSimpleFunctionPublicSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrSimpleFunctionSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrTypeAliasPublicSymbolImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrTypeAliasSymbolImpl;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;

/* JADX INFO: compiled from: DeepCopySymbolRemapperPreservingSignatures.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\bH\u0016J\u0010\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\nH\u0016J\u0010\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\fH\u0016J\u0010\u0010\r\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0010H\u0016¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/DeepCopySymbolRemapperPreservingSignatures;", "Lorg/jetbrains/kotlin/ir/util/DeepCopySymbolRemapper;", "()V", "visitClass", "", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "visitConstructor", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "visitEnumEntry", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "visitProperty", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "visitSimpleFunction", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "visitTypeAlias", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeAlias;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class DeepCopySymbolRemapperPreservingSignatures extends DeepCopySymbolRemapper {
    /* JADX WARN: Multi-variable type inference failed */
    public DeepCopySymbolRemapperPreservingSignatures() {
        super(null, 1, 0 == true ? 1 : 0);
    }

    @Override // org.jetbrains.kotlin.ir.util.DeepCopySymbolRemapper, org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    /* JADX INFO: renamed from: visitClass */
    public void mo6112visitClass(IrClass declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        HashMap<IrClassSymbol, IrClassSymbol> classes = getClasses();
        IrSymbol symbol = declaration.getSymbol();
        if (symbol != null) {
            IrClassSymbol irClassSymbol = (IrClassSymbol) symbol;
            IdSignature signature = irClassSymbol.getSignature();
            classes.put(irClassSymbol, signature != null ? new IrClassPublicSymbolImpl(signature, null, 2, null) : new IrClassSymbolImpl(null, 1, null));
            IrVisitorsKt.acceptChildrenVoid(declaration, this);
            return;
        }
        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrClassSymbol");
    }

    @Override // org.jetbrains.kotlin.ir.util.DeepCopySymbolRemapper, org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitConstructor(IrConstructor declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        HashMap<IrConstructorSymbol, IrConstructorSymbol> constructors = getConstructors();
        IrSymbol symbol = declaration.getSymbol();
        if (symbol != null) {
            IrConstructorSymbol irConstructorSymbol = (IrConstructorSymbol) symbol;
            IdSignature signature = irConstructorSymbol.getSignature();
            constructors.put(irConstructorSymbol, signature != null ? new IrConstructorPublicSymbolImpl(signature, null, 2, null) : new IrConstructorSymbolImpl(null, 1, null));
            IrVisitorsKt.acceptChildrenVoid(declaration, this);
            return;
        }
        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol");
    }

    @Override // org.jetbrains.kotlin.ir.util.DeepCopySymbolRemapper, org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitEnumEntry(IrEnumEntry declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        HashMap<IrEnumEntrySymbol, IrEnumEntrySymbol> enumEntries = getEnumEntries();
        IrSymbol symbol = declaration.getSymbol();
        if (symbol != null) {
            IrEnumEntrySymbol irEnumEntrySymbol = (IrEnumEntrySymbol) symbol;
            IdSignature signature = irEnumEntrySymbol.getSignature();
            enumEntries.put(irEnumEntrySymbol, signature != null ? new IrEnumEntryPublicSymbolImpl(signature, null, 2, null) : new IrEnumEntrySymbolImpl(null, 1, null));
            IrVisitorsKt.acceptChildrenVoid(declaration, this);
            return;
        }
        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrEnumEntrySymbol");
    }

    @Override // org.jetbrains.kotlin.ir.util.DeepCopySymbolRemapper, org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitSimpleFunction(IrSimpleFunction declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        HashMap<IrSimpleFunctionSymbol, IrSimpleFunctionSymbol> functions = getFunctions();
        IrSymbol symbol = declaration.getSymbol();
        if (symbol != null) {
            IrSimpleFunctionSymbol irSimpleFunctionSymbol = (IrSimpleFunctionSymbol) symbol;
            IdSignature signature = irSimpleFunctionSymbol.getSignature();
            functions.put(irSimpleFunctionSymbol, signature != null ? new IrSimpleFunctionPublicSymbolImpl(signature, null, 2, null) : new IrSimpleFunctionSymbolImpl(null, 1, null));
            IrVisitorsKt.acceptChildrenVoid(declaration, this);
            return;
        }
        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol");
    }

    @Override // org.jetbrains.kotlin.ir.util.DeepCopySymbolRemapper, org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitProperty(IrProperty declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        HashMap<IrPropertySymbol, IrPropertySymbol> properties = getProperties();
        IrSymbol symbol = declaration.getSymbol();
        if (symbol != null) {
            IrPropertySymbol irPropertySymbol = (IrPropertySymbol) symbol;
            IdSignature signature = irPropertySymbol.getSignature();
            properties.put(irPropertySymbol, signature != null ? new IrPropertyPublicSymbolImpl(signature, null, 2, null) : new IrPropertySymbolImpl(null, 1, null));
            IrVisitorsKt.acceptChildrenVoid(declaration, this);
            return;
        }
        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrPropertySymbol");
    }

    @Override // org.jetbrains.kotlin.ir.util.DeepCopySymbolRemapper, org.jetbrains.kotlin.ir.visitors.IrElementVisitorVoid
    public void visitTypeAlias(IrTypeAlias declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        HashMap<IrTypeAliasSymbol, IrTypeAliasSymbol> typeAliases = getTypeAliases();
        IrSymbol symbol = declaration.getSymbol();
        if (symbol != null) {
            IrTypeAliasSymbol irTypeAliasSymbol = (IrTypeAliasSymbol) symbol;
            IdSignature signature = irTypeAliasSymbol.getSignature();
            typeAliases.put(irTypeAliasSymbol, signature != null ? new IrTypeAliasPublicSymbolImpl(signature, null, 2, null) : new IrTypeAliasSymbolImpl(null, 1, null));
            IrVisitorsKt.acceptChildrenVoid(declaration, this);
            return;
        }
        throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrTypeAliasSymbol");
    }
}
