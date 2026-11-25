package org.jetbrains.kotlin.ir.util;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.IrLock;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.symbols.IrBindableSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: SymbolTable.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000F\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a@\u0010\u0000\u001a\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u0002H\u00040\u0001\"\b\b\u0000\u0010\u0003*\u00020\u0005\"\u0012\b\u0001\u0010\u0004*\f\u0012\u0002\b\u0003\u0012\u0004\u0012\u0002H\u00030\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002\u001a<\u0010\t\u001a\u0002H\n\"\u0004\b\u0000\u0010\n*\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0017\u0010\u000e\u001a\u0013\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u0002H\n0\u000f¢\u0006\u0002\b\u0010H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\u0011\u001a<\u0010\u0012\u001a\u0002H\n\"\u0004\b\u0000\u0010\n*\u00020\u00132\u0006\u0010\f\u001a\u00020\r2\u0017\u0010\u000e\u001a\u0013\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u0002H\n0\u000f¢\u0006\u0002\b\u0010H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\u0014\u001a<\u0010\u0012\u001a\u0002H\n\"\u0004\b\u0000\u0010\n*\u00020\u00132\u0006\u0010\f\u001a\u00020\u00152\u0017\u0010\u000e\u001a\u0013\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u0002H\n0\u000f¢\u0006\u0002\b\u0010H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\u0016\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u0017"}, d2 = {"IdSignatureSymbolTableSlice", "Lorg/jetbrains/kotlin/ir/util/SymbolTableSlice$Flat;", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "SymbolOwner", NativeSymbol.CLASS_NAME, "Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;", "Lorg/jetbrains/kotlin/ir/symbols/IrBindableSymbol;", "lock", "Lorg/jetbrains/kotlin/ir/IrLock;", "withReferenceScope", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/ir/util/ReferenceSymbolTable;", "owner", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "block", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "(Lorg/jetbrains/kotlin/ir/util/ReferenceSymbolTable;Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "withScope", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "(Lorg/jetbrains/kotlin/ir/util/SymbolTable;Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "(Lorg/jetbrains/kotlin/ir/util/SymbolTable;Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "ir.tree"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class SymbolTableKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final <SymbolOwner extends IrSymbolOwner, Symbol extends IrBindableSymbol<?, SymbolOwner>> SymbolTableSlice.Flat<IdSignature, SymbolOwner, Symbol> IdSignatureSymbolTableSlice(IrLock irLock) {
        return new SymbolTableSlice.Flat<>(irLock, new Function1<Symbol, Boolean>() { // from class: org.jetbrains.kotlin.ir.util.SymbolTableKt.IdSignatureSymbolTableSlice.1
            /* JADX WARN: Incorrect types in method signature: (TSymbol;)Ljava/lang/Boolean; */
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(IrBindableSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(it.getSignature() != null);
            }
        });
    }

    public static final <T> T withScope(SymbolTable symbolTable, IrSymbol owner, Function1<? super SymbolTable, ? extends T> block) {
        Intrinsics.checkNotNullParameter(symbolTable, "<this>");
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(block, "block");
        symbolTable.enterScope(owner);
        T tInvoke = block.invoke(symbolTable);
        symbolTable.leaveScope(owner);
        return tInvoke;
    }

    public static final <T> T withScope(SymbolTable symbolTable, IrDeclaration owner, Function1<? super SymbolTable, ? extends T> block) {
        Intrinsics.checkNotNullParameter(symbolTable, "<this>");
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(block, "block");
        symbolTable.enterScope(owner);
        T tInvoke = block.invoke(symbolTable);
        symbolTable.leaveScope(owner);
        return tInvoke;
    }

    public static final <T> T withReferenceScope(ReferenceSymbolTable referenceSymbolTable, IrDeclaration owner, Function1<? super ReferenceSymbolTable, ? extends T> block) {
        Intrinsics.checkNotNullParameter(referenceSymbolTable, "<this>");
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(block, "block");
        referenceSymbolTable.enterScope(owner);
        T tInvoke = block.invoke(referenceSymbolTable);
        referenceSymbolTable.leaveScope(owner);
        return tInvoke;
    }
}
