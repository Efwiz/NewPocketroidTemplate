package org.jetbrains.kotlin.ir.backend.js.utils;

import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrScript;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.js.backend.ast.JsName;
import org.jetbrains.kotlin.js.backend.ast.JsNameRef;
import org.jetbrains.kotlin.load.java.JvmAbi;

/* JADX INFO: compiled from: IrNamer.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0017\u0010\b\u001a\u0004\u0018\u00010\u00062\u0006\u0010\t\u001a\u00020\u0005H\u0016¢\u0006\u0002\u0010\nJ\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0005H\u0016J\u0010\u0010\u000e\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0010\u0010\u0011\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u0010\u0010\u0014\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\u0013H&J\u0010\u0010\u0015\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020\u0017H&J\u0010\u0010\u0018\u001a\u00020\f2\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J\u0010\u0010\u001b\u001a\u00020\f2\u0006\u0010\u001c\u001a\u00020\u001dH&J\u0010\u0010\u001e\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\u0010\u0010\u001f\u001a\u00020 2\u0006\u0010\r\u001a\u00020\u0005H\u0016J\u0016\u0010!\u001a\u00020\f*\u00020\"2\b\b\u0002\u0010#\u001a\u00020$H\u0004R*\u0010\u0003\u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006`\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/utils/IrNamerBase;", "Lorg/jetbrains/kotlin/ir/backend/js/utils/IrNamer;", "()V", "associatedObjectKeyMap", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "", "Lkotlin/collections/HashMap;", "getAssociatedObjectKey", "irClass", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Ljava/lang/Integer;", "getNameForClass", "Lorg/jetbrains/kotlin/js/backend/ast/JsName;", "klass", "getNameForConstructor", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "getNameForField", "field", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "getNameForMemberField", "getNameForMemberFunction", "function", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "getNameForProperty", "property", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "getNameForStaticDeclaration", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationWithName;", "getNameForStaticFunction", "getRefForExternalClass", "Lorg/jetbrains/kotlin/js/backend/ast/JsNameRef;", "toJsName", "", "temporary", "", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrNamerBase implements IrNamer {
    private final HashMap<IrClass, Integer> associatedObjectKeyMap = new HashMap<>();

    @Override // org.jetbrains.kotlin.ir.backend.js.utils.IrNamer
    public abstract JsName getNameForMemberField(IrField field);

    @Override // org.jetbrains.kotlin.ir.backend.js.utils.IrNamer
    public abstract JsName getNameForMemberFunction(IrSimpleFunction function);

    @Override // org.jetbrains.kotlin.ir.backend.js.utils.IrNamer
    public abstract JsName getNameForStaticDeclaration(IrDeclarationWithName declaration);

    public static /* synthetic */ JsName toJsName$default(IrNamerBase irNamerBase, String str, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: toJsName");
        }
        if ((i & 1) != 0) {
            z = true;
        }
        return irNamerBase.toJsName(str, z);
    }

    protected final JsName toJsName(String str, boolean z) {
        Intrinsics.checkNotNullParameter(str, "<this>");
        return new JsName(str, z);
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.utils.IrNamer
    public JsName getNameForStaticFunction(IrSimpleFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        return getNameForStaticDeclaration(function);
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.utils.IrNamer
    public JsName getNameForField(IrField field) {
        Intrinsics.checkNotNullParameter(field, "field");
        if (field.getIsStatic() || (field.getParent() instanceof IrScript)) {
            return getNameForStaticDeclaration(field);
        }
        return getNameForMemberField(field);
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.utils.IrNamer
    public JsName getNameForConstructor(IrConstructor constructor) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        return getNameForStaticDeclaration(IrUtilsKt.getParentAsClass(constructor));
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.utils.IrNamer
    public JsName getNameForClass(IrClass klass) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        return getNameForStaticDeclaration(klass);
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.utils.IrNamer
    public JsNameRef getRefForExternalClass(IrClass klass) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        IrDeclarationParent parent = klass.getParent();
        if (klass.getIsCompanion()) {
            Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
            return getRefForExternalClass((IrClass) parent);
        }
        IrClass irClass = klass;
        String identifier = AnnotationUtilsKt.getJsNameOrKotlinName(irClass).getIdentifier();
        Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
        if (parent instanceof IrClass) {
            return new JsNameRef(identifier, getRefForExternalClass((IrClass) parent));
        }
        if (parent instanceof IrPackageFragment) {
            JsNameRef jsNameRefMakeRef = getNameForStaticDeclaration(irClass).makeRef();
            Intrinsics.checkNotNull(jsNameRefMakeRef);
            return jsNameRefMakeRef;
        }
        throw new IllegalStateException(("Unsupported external class parent " + parent).toString());
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.utils.IrNamer
    public JsName getNameForProperty(IrProperty property) {
        Intrinsics.checkNotNullParameter(property, "property");
        if (property.getParent() instanceof IrClass) {
            String strAsString = AnnotationUtilsKt.getJsNameOrKotlinName(property).asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            return toJsName$default(this, strAsString, false, 1, null);
        }
        return getNameForStaticDeclaration(property);
    }

    @Override // org.jetbrains.kotlin.ir.backend.js.utils.IrNamer
    public Integer getAssociatedObjectKey(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        if (!AnnotationUtilsKt.isAssociatedObjectAnnotatedAnnotation(irClass)) {
            return null;
        }
        HashMap<IrClass, Integer> map = this.associatedObjectKeyMap;
        Integer numValueOf = map.get(irClass);
        if (numValueOf == null) {
            numValueOf = Integer.valueOf(this.associatedObjectKeyMap.size());
            map.put(irClass, numValueOf);
        }
        return numValueOf;
    }
}
