package org.jetbrains.kotlin.ir.backend.js.transformers.irToJs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.js.backend.ast.JsCompositeBlock;
import org.jetbrains.kotlin.utils.CollectionsKt;

/* JADX INFO: compiled from: JsClassGenerator.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\nR\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/transformers/irToJs/JsIrClassModel;", "", "klass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)V", "getKlass", "()Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "postDeclarationBlock", "Lorg/jetbrains/kotlin/js/backend/ast/JsCompositeBlock;", "getPostDeclarationBlock", "()Lorg/jetbrains/kotlin/js/backend/ast/JsCompositeBlock;", "preDeclarationBlock", "getPreDeclarationBlock", "superClasses", "", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "getSuperClasses", "()Ljava/util/List;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsIrClassModel {
    private final IrClass klass;
    private final JsCompositeBlock postDeclarationBlock;
    private final JsCompositeBlock preDeclarationBlock;
    private final List<IrClassSymbol> superClasses;

    public JsIrClassModel(IrClass klass) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        this.klass = klass;
        List<IrType> superTypes = klass.getSuperTypes();
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = superTypes.iterator();
        while (it.hasNext()) {
            IrClassifierSymbol classifierOrNull = IrTypesKt.getClassifierOrNull((IrType) it.next());
            Intrinsics.checkNotNull(classifierOrNull, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrClassSymbol");
            IrClassSymbol irClassSymbol = (IrClassSymbol) classifierOrNull;
            irClassSymbol = JsClassGeneratorKt.existsInRuntime(irClassSymbol) ? irClassSymbol : null;
            if (irClassSymbol != null) {
                arrayList.add(irClassSymbol);
            }
        }
        this.superClasses = CollectionsKt.compactIfPossible(arrayList);
        this.preDeclarationBlock = new JsCompositeBlock();
        this.postDeclarationBlock = new JsCompositeBlock();
    }

    public final IrClass getKlass() {
        return this.klass;
    }

    public final List<IrClassSymbol> getSuperClasses() {
        return this.superClasses;
    }

    public final JsCompositeBlock getPreDeclarationBlock() {
        return this.preDeclarationBlock;
    }

    public final JsCompositeBlock getPostDeclarationBlock() {
        return this.postDeclarationBlock;
    }
}
