package org.jetbrains.kotlin.ir.backend.js.utils;

import kotlin.Metadata;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.load.java.JvmAbi;

/* JADX INFO: compiled from: IrNamer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0017\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0005H&J\u0010\u0010\n\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\fH&J\u0010\u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u000fH&J\u0010\u0010\u0010\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u000fH&J\u0010\u0010\u0011\u001a\u00020\b2\u0006\u0010\u0012\u001a\u00020\u0013H&J\u0010\u0010\u0014\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\u0016H&J\u0010\u0010\u0017\u001a\u00020\b2\u0006\u0010\u0018\u001a\u00020\u0019H&J\u0010\u0010\u001a\u001a\u00020\b2\u0006\u0010\u0012\u001a\u00020\u0013H&J\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\t\u001a\u00020\u0005H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u001dÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/utils/IrNamer;", "", "getAssociatedObjectKey", "", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Ljava/lang/Integer;", "getNameForClass", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "klass", "getNameForConstructor", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "getNameForField", "field", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "getNameForMemberField", "getNameForMemberFunction", "function", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "getNameForProperty", "property", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "getNameForStaticDeclaration", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationWithName;", "getNameForStaticFunction", "getRefForExternalClass", "Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface IrNamer {
    Integer getAssociatedObjectKey(IrClass irClass);

    JsName getNameForClass(IrClass klass);

    JsName getNameForConstructor(IrConstructor constructor);

    JsName getNameForField(IrField field);

    JsName getNameForMemberField(IrField field);

    JsName getNameForMemberFunction(IrSimpleFunction function);

    JsName getNameForProperty(IrProperty property);

    JsName getNameForStaticDeclaration(IrDeclarationWithName declaration);

    JsName getNameForStaticFunction(IrSimpleFunction function);

    JsNameRef getRefForExternalClass(IrClass klass);
}
