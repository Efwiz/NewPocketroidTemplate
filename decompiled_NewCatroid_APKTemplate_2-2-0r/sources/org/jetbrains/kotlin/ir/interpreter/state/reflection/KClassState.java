package org.jetbrains.kotlin.ir.interpreter.state.reflection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KCallable;
import kotlin.reflect.KFunction;
import kotlin.reflect.KType;
import kotlin.reflect.KTypeParameter;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.expressions.IrClassReference;
import org.jetbrains.kotlin.ir.interpreter.CallInterceptor;
import org.jetbrains.kotlin.ir.interpreter.UtilsKt;
import org.jetbrains.kotlin.ir.interpreter.proxy.reflection.KFunctionProxy;
import org.jetbrains.kotlin.ir.interpreter.proxy.reflection.KMutableProperty1Proxy;
import org.jetbrains.kotlin.ir.interpreter.proxy.reflection.KMutableProperty2Proxy;
import org.jetbrains.kotlin.ir.interpreter.proxy.reflection.KProperty1Proxy;
import org.jetbrains.kotlin.ir.interpreter.proxy.reflection.KProperty2Proxy;
import org.jetbrains.kotlin.ir.interpreter.proxy.reflection.KTypeParameterProxy;
import org.jetbrains.kotlin.ir.interpreter.proxy.reflection.KTypeProxy;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: KClassState.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J\u0013\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0096\u0002J\u0018\u0010\u0019\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\t2\u0006\u0010\u001a\u001a\u00020\u001bJ\u0018\u0010\u001c\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\f0\t2\u0006\u0010\u001a\u001a\u00020\u001bJ\u0014\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\u001a\u001a\u00020\u001bJ\u0014\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00110\u000e2\u0006\u0010\u001a\u001a\u00020\u001bJ\b\u0010\u001f\u001a\u00020 H\u0016J\b\u0010!\u001a\u00020\"H\u0016R\u001a\u0010\b\u001a\u000e\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u000e\u0012\b\u0012\u0006\u0012\u0002\b\u00030\f\u0018\u00010\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\r\u001a\n\u0012\u0004\u0012\u00020\u000f\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0016\u0010\u0010\u001a\n\u0012\u0004\u0012\u00020\u0011\u0018\u00010\u000eX\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0006\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0013¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/ir/interpreter/state/reflection/KClassState;", "Lorg/jetbrains/kotlin/ir/interpreter/state/reflection/ReflectionState;", "classReference", "Lorg/jetbrains/kotlin/ir/expressions/IrClassReference;", "(Lorg/jetbrains/kotlin/ir/expressions/IrClassReference;)V", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "irClass", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;Lorg/jetbrains/kotlin/ir/declarations/IrClass;)V", "_constructors", "", "Lkotlin/reflect/KFunction;", "_members", "Lkotlin/reflect/KCallable;", "_supertypes", "", "Lkotlin/reflect/KType;", "_typeParameters", "Lkotlin/reflect/KTypeParameter;", "getClassReference", "()Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getIrClass", Namer.EQUALS_METHOD_NAME, "", "other", "", "getConstructors", "callInterceptor", "Lorg/jetbrains/kotlin/ir/interpreter/CallInterceptor;", "getMembers", "getSupertypes", "getTypeParameters", "hashCode", "", "toString", "", "ir.interpreter"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class KClassState extends ReflectionState {
    private Collection<? extends KFunction<?>> _constructors;
    private Collection<? extends KCallable<?>> _members;
    private List<? extends KType> _supertypes;
    private List<? extends KTypeParameter> _typeParameters;
    private final IrClass classReference;
    private final IrClass irClass;

    public KClassState(IrClass classReference, IrClass irClass) {
        Intrinsics.checkNotNullParameter(classReference, "classReference");
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        this.classReference = classReference;
        this.irClass = irClass;
    }

    public final IrClass getClassReference() {
        return this.classReference;
    }

    @Override // org.jetbrains.kotlin.ir.interpreter.state.State
    public IrClass getIrClass() {
        return this.irClass;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public KClassState(IrClassReference classReference) {
        Intrinsics.checkNotNullParameter(classReference, "classReference");
        IrSymbolOwner owner = classReference.getSymbol().getOwner();
        Intrinsics.checkNotNull(owner, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrClass");
        IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(classReference.getType());
        Intrinsics.checkNotNull(classOrNull);
        this((IrClass) owner, classOrNull.getOwner());
    }

    public final Collection<KCallable<?>> getMembers(CallInterceptor callInterceptor) {
        KCallable kFunctionProxy;
        Intrinsics.checkNotNullParameter(callInterceptor, "callInterceptor");
        Collection collection = this._members;
        if (collection != null) {
            Intrinsics.checkNotNull(collection);
            return collection;
        }
        List<IrDeclaration> declarations = this.classReference.getDeclarations();
        ArrayList arrayList = new ArrayList();
        for (Object obj : declarations) {
            IrDeclaration irDeclaration = (IrDeclaration) obj;
            if (!(irDeclaration instanceof IrClass) && !(irDeclaration instanceof IrConstructor)) {
                arrayList.add(obj);
            }
        }
        ArrayList<IrDeclaration> arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        for (IrDeclaration irDeclaration2 : arrayList2) {
            if (irDeclaration2 instanceof IrProperty) {
                IrProperty irProperty = (IrProperty) irDeclaration2;
                IrSimpleFunction getter = irProperty.getGetter();
                boolean z = (getter != null ? getter.getExtensionReceiverParameter() : null) != null;
                if (!z && !irProperty.getIsVar()) {
                    kFunctionProxy = new KProperty1Proxy(new KPropertyState(irProperty, callInterceptor.getIrBuiltIns().getKPropertyClass(false, 1).getOwner(), null, 4, null), callInterceptor);
                } else if (!z && irProperty.getIsVar()) {
                    kFunctionProxy = new KMutableProperty1Proxy(new KPropertyState(irProperty, callInterceptor.getIrBuiltIns().getKPropertyClass(true, 1).getOwner(), null, 4, null), callInterceptor);
                } else if (z && !irProperty.getIsVar()) {
                    kFunctionProxy = new KProperty2Proxy(new KPropertyState(irProperty, callInterceptor.getIrBuiltIns().getKPropertyClass(false, 2).getOwner(), null, 4, null), callInterceptor);
                } else if (!z && irProperty.getIsVar()) {
                    kFunctionProxy = new KMutableProperty2Proxy(new KPropertyState(irProperty, callInterceptor.getIrBuiltIns().getKPropertyClass(true, 2).getOwner(), null, 4, null), callInterceptor);
                } else {
                    throw new NotImplementedError(null, 1, null);
                }
            } else if (irDeclaration2 instanceof IrFunction) {
                IrFunction irFunction = (IrFunction) irDeclaration2;
                kFunctionProxy = new KFunctionProxy(new KFunctionState(irFunction, callInterceptor.getIrBuiltIns().kFunctionN(irFunction.getValueParameters().size()), callInterceptor.getEnvironment(), null, 8, null), callInterceptor);
            } else {
                throw new NotImplementedError(null, 1, null);
            }
            arrayList3.add(kFunctionProxy);
        }
        ArrayList arrayList4 = arrayList3;
        this._members = arrayList4;
        Intrinsics.checkNotNull(arrayList4);
        return arrayList4;
    }

    public final Collection<KFunction<?>> getConstructors(CallInterceptor callInterceptor) {
        Intrinsics.checkNotNullParameter(callInterceptor, "callInterceptor");
        Collection collection = this._constructors;
        if (collection != null) {
            Intrinsics.checkNotNull(collection);
            return collection;
        }
        List<IrDeclaration> declarations = this.classReference.getDeclarations();
        ArrayList arrayList = new ArrayList();
        for (Object obj : declarations) {
            if (obj instanceof IrConstructor) {
                arrayList.add(obj);
            }
        }
        ArrayList<IrConstructor> arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        for (IrConstructor irConstructor : arrayList2) {
            arrayList3.add(new KFunctionProxy(new KFunctionState(irConstructor, callInterceptor.getIrBuiltIns().kFunctionN(irConstructor.getValueParameters().size()), callInterceptor.getEnvironment(), null, 8, null), callInterceptor));
        }
        ArrayList arrayList4 = arrayList3;
        this._constructors = arrayList4;
        Intrinsics.checkNotNull(arrayList4);
        return arrayList4;
    }

    public final List<KTypeParameter> getTypeParameters(CallInterceptor callInterceptor) {
        Intrinsics.checkNotNullParameter(callInterceptor, "callInterceptor");
        List list = this._typeParameters;
        if (list != null) {
            Intrinsics.checkNotNull(list);
            return list;
        }
        IrClass owner = callInterceptor.getEnvironment().getKTypeParameterClass$ir_interpreter().getOwner();
        List<IrTypeParameter> typeParameters = this.classReference.getTypeParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(typeParameters, 10));
        Iterator<T> it = typeParameters.iterator();
        while (it.hasNext()) {
            arrayList.add(new KTypeParameterProxy(new KTypeParameterState((IrTypeParameter) it.next(), owner), callInterceptor));
        }
        ArrayList arrayList2 = arrayList;
        this._typeParameters = arrayList2;
        Intrinsics.checkNotNull(arrayList2);
        return arrayList2;
    }

    public final List<KType> getSupertypes(CallInterceptor callInterceptor) {
        Intrinsics.checkNotNullParameter(callInterceptor, "callInterceptor");
        List list = this._supertypes;
        if (list != null) {
            Intrinsics.checkNotNull(list);
            return list;
        }
        IrClass owner = callInterceptor.getEnvironment().getKTypeClass$ir_interpreter().getOwner();
        List<IrType> superTypes = this.classReference.getSuperTypes();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(superTypes, 10));
        Iterator<T> it = superTypes.iterator();
        while (it.hasNext()) {
            arrayList.add((IrType) it.next());
        }
        Set set = CollectionsKt.toSet(CollectionsKt.plus((Collection<? extends IrType>) arrayList, callInterceptor.getIrBuiltIns().getAnyType()));
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(set, 10));
        Iterator it2 = set.iterator();
        while (it2.hasNext()) {
            arrayList2.add(new KTypeProxy(new KTypeState((IrType) it2.next(), owner), callInterceptor));
        }
        ArrayList arrayList3 = arrayList2;
        this._supertypes = arrayList3;
        Intrinsics.checkNotNull(arrayList3);
        return arrayList3;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!Intrinsics.areEqual(getClass(), other != null ? other.getClass() : null)) {
            return false;
        }
        Intrinsics.checkNotNull(other, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.interpreter.state.reflection.KClassState");
        return Intrinsics.areEqual(this.classReference, ((KClassState) other).classReference);
    }

    public int hashCode() {
        return this.classReference.hashCode();
    }

    public String toString() {
        return ExternalAnnotationProvider.CLASS_PREFIX + UtilsKt.internalName(this.classReference);
    }
}
