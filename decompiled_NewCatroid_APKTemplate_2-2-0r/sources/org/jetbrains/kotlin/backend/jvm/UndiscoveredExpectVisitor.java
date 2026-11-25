package org.jetbrains.kotlin.backend.jvm;

import androidx.exifinterface.media.ExifInterface;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.Implementation;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.ir.util.DescriptorSymbolTableExtension;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtConstructor;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtPrimaryConstructor;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtPropertyAccessor;
import org.jetbrains.kotlin.psi.KtSecondaryConstructor;
import org.jetbrains.kotlin.psi.KtTreeVisitorVoid;
import org.jetbrains.kotlin.psi.KtTypeAlias;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.multiplatform.ExpectedActualResolverKt;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;

/* JADX INFO: compiled from: UndiscoveredExpectUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J&\u0010\u0007\u001a\u00020\b\"\u000e\b\u0000\u0010\t*\b\u0012\u0004\u0012\u0002H\t0\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\t0\nH\u0002J\u0010\u0010\f\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0010\u0010\u0012\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\u0013H\u0016J\u0010\u0010\u0014\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\u0010\u0010\u0017\u001a\u00020\b2\u0006\u0010\u0018\u001a\u00020\u0019H\u0016J\u0010\u0010\u001a\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\u001bH\u0016J\u0010\u0010\u001c\u001a\u00020\b2\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J>\u0010\u001f\u001a\u0004\u0018\u0001H \"\b\b\u0000\u0010!*\u00020\"\"\n\b\u0001\u0010 \u0018\u0001*\u00020#*\u0002H!2\u0012\u0010$\u001a\u000e\u0012\u0004\u0012\u0002H!\u0012\u0004\u0012\u0002H 0%H\u0082\b¢\u0006\u0002\u0010&JD\u0010'\u001a\u0004\u0018\u0001H(\"\n\b\u0000\u0010(\u0018\u0001*\u00020#\"\b\b\u0001\u0010!*\u00020\"\"\u0004\b\u0002\u0010 *\u0002H!2\u0012\u0010$\u001a\u000e\u0012\u0004\u0012\u0002H!\u0012\u0004\u0012\u0002H 0%H\u0082\b¢\u0006\u0002\u0010&R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006)"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/UndiscoveredExpectVisitor;", "Lorg/jetbrains/kotlin/psi/KtTreeVisitorVoid;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "(Lorg/jetbrains/kotlin/ir/util/SymbolTable;Lorg/jetbrains/kotlin/resolve/BindingContext;)V", "referenceConstructor", "", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/psi/KtConstructor;", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "visitClassOrObject", "classOrObject", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "visitNamedFunction", "function", "Lorg/jetbrains/kotlin/psi/KtNamedFunction;", "visitPrimaryConstructor", "Lorg/jetbrains/kotlin/psi/KtPrimaryConstructor;", "visitProperty", "property", "Lorg/jetbrains/kotlin/psi/KtProperty;", "visitPropertyAccessor", Implementation.Context.Default.ACCESSOR_METHOD_SUFFIX, "Lorg/jetbrains/kotlin/psi/KtPropertyAccessor;", "visitSecondaryConstructor", "Lorg/jetbrains/kotlin/psi/KtSecondaryConstructor;", "visitTypeAlias", "typeAlias", "Lorg/jetbrains/kotlin/psi/KtTypeAlias;", "findExpectForActual", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "K", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "Lorg/jetbrains/kotlin/descriptors/MemberDescriptor;", "bindingContextKey", "Lorg/jetbrains/kotlin/util/slicedMap/ReadOnlySlice;", "(Lcom/intellij/psi/PsiElement;Lorg/jetbrains/kotlin/util/slicedMap/ReadOnlySlice;)Lorg/jetbrains/kotlin/descriptors/MemberDescriptor;", "findExpectForActualOfType", "D", "backend.jvm.entrypoint"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class UndiscoveredExpectVisitor extends KtTreeVisitorVoid {
    private final BindingContext bindingContext;
    private final SymbolTable symbolTable;

    public UndiscoveredExpectVisitor(SymbolTable symbolTable, BindingContext bindingContext) {
        Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        this.symbolTable = symbolTable;
        this.bindingContext = bindingContext;
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
    public void visitClassOrObject(KtClassOrObject classOrObject) {
        Intrinsics.checkNotNullParameter(classOrObject, "classOrObject");
        super.visitClassOrObject(classOrObject);
        DescriptorSymbolTableExtension descriptorExtension = this.symbolTable.getDescriptorExtension();
        WritableSlice<PsiElement, ClassDescriptor> CLASS = BindingContext.CLASS;
        Intrinsics.checkNotNullExpressionValue(CLASS, "CLASS");
        Object obj = this.bindingContext.get(CLASS, classOrObject);
        ClassDescriptor classDescriptor = null;
        if (!(obj instanceof ClassDescriptor)) {
            obj = null;
        }
        ClassDescriptor classDescriptor2 = (ClassDescriptor) obj;
        if (classDescriptor2 != null) {
            if (!classDescriptor2.isActual()) {
                classDescriptor2 = null;
            }
            if (classDescriptor2 != null) {
                Object objSingleOrNull = CollectionsKt.singleOrNull((List<? extends Object>) ExpectedActualResolverKt.findCompatibleExpectsForActual$default(classDescriptor2, null, 1, null));
                classDescriptor = (ClassDescriptor) (objSingleOrNull instanceof ClassDescriptor ? objSingleOrNull : null);
            }
        }
        ClassDescriptor classDescriptor3 = (ClassDescriptor) classDescriptor;
        if (classDescriptor3 == null) {
            return;
        }
        descriptorExtension.referenceClass(classDescriptor3);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
    public void visitTypeAlias(KtTypeAlias typeAlias) {
        Intrinsics.checkNotNullParameter(typeAlias, "typeAlias");
        super.visitTypeAlias(typeAlias);
        DescriptorSymbolTableExtension descriptorExtension = this.symbolTable.getDescriptorExtension();
        WritableSlice<PsiElement, TypeAliasDescriptor> TYPE_ALIAS = BindingContext.TYPE_ALIAS;
        Intrinsics.checkNotNullExpressionValue(TYPE_ALIAS, "TYPE_ALIAS");
        Object obj = this.bindingContext.get(TYPE_ALIAS, typeAlias);
        TypeAliasDescriptor typeAliasDescriptor = null;
        if (!(obj instanceof TypeAliasDescriptor)) {
            obj = null;
        }
        TypeAliasDescriptor typeAliasDescriptor2 = (TypeAliasDescriptor) obj;
        if (typeAliasDescriptor2 != null) {
            if (!typeAliasDescriptor2.isActual()) {
                typeAliasDescriptor2 = null;
            }
            if (typeAliasDescriptor2 != null) {
                Object objSingleOrNull = CollectionsKt.singleOrNull((List<? extends Object>) ExpectedActualResolverKt.findCompatibleExpectsForActual$default(typeAliasDescriptor2, null, 1, null));
                typeAliasDescriptor = (TypeAliasDescriptor) (objSingleOrNull instanceof TypeAliasDescriptor ? objSingleOrNull : null);
            }
        }
        TypeAliasDescriptor typeAliasDescriptor3 = (TypeAliasDescriptor) typeAliasDescriptor;
        if (typeAliasDescriptor3 == null) {
            return;
        }
        descriptorExtension.referenceTypeAlias(typeAliasDescriptor3);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
    public void visitPrimaryConstructor(KtPrimaryConstructor constructor) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        super.visitPrimaryConstructor(constructor);
        referenceConstructor(constructor);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
    public void visitSecondaryConstructor(KtSecondaryConstructor constructor) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        super.visitSecondaryConstructor(constructor);
        referenceConstructor(constructor);
    }

    private final <T extends KtConstructor<T>> void referenceConstructor(KtConstructor<T> constructor) {
        SymbolTable symbolTable = this.symbolTable;
        WritableSlice<PsiElement, ConstructorDescriptor> CONSTRUCTOR = BindingContext.CONSTRUCTOR;
        Intrinsics.checkNotNullExpressionValue(CONSTRUCTOR, "CONSTRUCTOR");
        Object obj = this.bindingContext.get(CONSTRUCTOR, constructor);
        ConstructorDescriptor constructorDescriptor = null;
        if (!(obj instanceof ConstructorDescriptor)) {
            obj = null;
        }
        ConstructorDescriptor constructorDescriptor2 = (ConstructorDescriptor) obj;
        if (constructorDescriptor2 != null) {
            if (!constructorDescriptor2.isActual()) {
                constructorDescriptor2 = null;
            }
            if (constructorDescriptor2 != null) {
                Object objSingleOrNull = CollectionsKt.singleOrNull((List<? extends Object>) ExpectedActualResolverKt.findCompatibleExpectsForActual$default(constructorDescriptor2, null, 1, null));
                constructorDescriptor = (ConstructorDescriptor) (objSingleOrNull instanceof ConstructorDescriptor ? objSingleOrNull : null);
            }
        }
        ConstructorDescriptor constructorDescriptor3 = (ConstructorDescriptor) constructorDescriptor;
        if (constructorDescriptor3 == null) {
            return;
        }
        IrUtilsKt.referenceFunction(symbolTable, constructorDescriptor3);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
    public void visitNamedFunction(KtNamedFunction function) {
        Intrinsics.checkNotNullParameter(function, "function");
        super.visitNamedFunction(function);
        SymbolTable symbolTable = this.symbolTable;
        WritableSlice<PsiElement, SimpleFunctionDescriptor> FUNCTION = BindingContext.FUNCTION;
        Intrinsics.checkNotNullExpressionValue(FUNCTION, "FUNCTION");
        Object obj = this.bindingContext.get(FUNCTION, function);
        SimpleFunctionDescriptor simpleFunctionDescriptor = null;
        if (!(obj instanceof SimpleFunctionDescriptor)) {
            obj = null;
        }
        SimpleFunctionDescriptor simpleFunctionDescriptor2 = (SimpleFunctionDescriptor) obj;
        if (simpleFunctionDescriptor2 != null) {
            if (!simpleFunctionDescriptor2.isActual()) {
                simpleFunctionDescriptor2 = null;
            }
            if (simpleFunctionDescriptor2 != null) {
                Object objSingleOrNull = CollectionsKt.singleOrNull((List<? extends Object>) ExpectedActualResolverKt.findCompatibleExpectsForActual$default(simpleFunctionDescriptor2, null, 1, null));
                simpleFunctionDescriptor = (SimpleFunctionDescriptor) (objSingleOrNull instanceof SimpleFunctionDescriptor ? objSingleOrNull : null);
            }
        }
        SimpleFunctionDescriptor simpleFunctionDescriptor3 = (SimpleFunctionDescriptor) simpleFunctionDescriptor;
        if (simpleFunctionDescriptor3 == null) {
            return;
        }
        IrUtilsKt.referenceFunction(symbolTable, simpleFunctionDescriptor3);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
    public void visitProperty(KtProperty property) {
        Intrinsics.checkNotNullParameter(property, "property");
        super.visitProperty(property);
        DescriptorSymbolTableExtension descriptorExtension = this.symbolTable.getDescriptorExtension();
        WritableSlice<PsiElement, VariableDescriptor> VARIABLE = BindingContext.VARIABLE;
        Intrinsics.checkNotNullExpressionValue(VARIABLE, "VARIABLE");
        Object obj = this.bindingContext.get(VARIABLE, property);
        PropertyDescriptor propertyDescriptor = null;
        if (!(obj instanceof PropertyDescriptor)) {
            obj = null;
        }
        PropertyDescriptor propertyDescriptor2 = (PropertyDescriptor) obj;
        if (propertyDescriptor2 != null) {
            if (!propertyDescriptor2.isActual()) {
                propertyDescriptor2 = null;
            }
            if (propertyDescriptor2 != null) {
                Object objSingleOrNull = CollectionsKt.singleOrNull((List<? extends Object>) ExpectedActualResolverKt.findCompatibleExpectsForActual$default(propertyDescriptor2, null, 1, null));
                propertyDescriptor = (PropertyDescriptor) (objSingleOrNull instanceof PropertyDescriptor ? objSingleOrNull : null);
            }
        }
        PropertyDescriptor propertyDescriptor3 = (PropertyDescriptor) propertyDescriptor;
        if (propertyDescriptor3 == null) {
            return;
        }
        descriptorExtension.referenceProperty(propertyDescriptor3);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
    public void visitPropertyAccessor(KtPropertyAccessor accessor) {
        Intrinsics.checkNotNullParameter(accessor, "accessor");
        super.visitPropertyAccessor(accessor);
        SymbolTable symbolTable = this.symbolTable;
        WritableSlice<KtPropertyAccessor, PropertyAccessorDescriptor> PROPERTY_ACCESSOR = BindingContext.PROPERTY_ACCESSOR;
        Intrinsics.checkNotNullExpressionValue(PROPERTY_ACCESSOR, "PROPERTY_ACCESSOR");
        Object obj = this.bindingContext.get(PROPERTY_ACCESSOR, accessor);
        PropertyAccessorDescriptor propertyAccessorDescriptor = null;
        if (!(obj instanceof PropertyAccessorDescriptor)) {
            obj = null;
        }
        PropertyAccessorDescriptor propertyAccessorDescriptor2 = (PropertyAccessorDescriptor) obj;
        if (propertyAccessorDescriptor2 != null) {
            if (!propertyAccessorDescriptor2.isActual()) {
                propertyAccessorDescriptor2 = null;
            }
            if (propertyAccessorDescriptor2 != null) {
                Object objSingleOrNull = CollectionsKt.singleOrNull((List<? extends Object>) ExpectedActualResolverKt.findCompatibleExpectsForActual$default(propertyAccessorDescriptor2, null, 1, null));
                propertyAccessorDescriptor = (PropertyAccessorDescriptor) (objSingleOrNull instanceof PropertyAccessorDescriptor ? objSingleOrNull : null);
            }
        }
        PropertyAccessorDescriptor propertyAccessorDescriptor3 = (PropertyAccessorDescriptor) propertyAccessorDescriptor;
        if (propertyAccessorDescriptor3 == null) {
            return;
        }
        IrUtilsKt.referenceFunction(symbolTable, propertyAccessorDescriptor3);
    }
}
