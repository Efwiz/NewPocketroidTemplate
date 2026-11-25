package org.jetbrains.kotlin.fir.backend;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirFunction;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.expressions.FirReturnExpression;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.IrVariable;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.util.PrivateForInline;

/* JADX INFO: compiled from: Fir2IrConversionScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000¢\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u000e\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001d\u0010(\u001a\u0002H)\"\b\b\u0000\u0010)*\u00020*2\u0006\u0010+\u001a\u0002H)¢\u0006\u0002\u0010,J\b\u0010-\u001a\u0004\u0018\u00010\nJ\b\u0010.\u001a\u0004\u0018\u00010/J\u0006\u00100\u001a\u000201J\u0010\u00102\u001a\u0004\u0018\u0001032\u0006\u00104\u001a\u00020\u0005J3\u00105\u001a\u0002H)\"\u0004\b\u0000\u0010)2\u0006\u00106\u001a\u00020\u000f2\u0006\u00104\u001a\u00020\u00052\f\u00107\u001a\b\u0012\u0004\u0012\u0002H)08H\u0000¢\u0006\u0004\b9\u0010:J\u0010\u0010;\u001a\u0004\u0018\u00010\u000f2\u0006\u0010<\u001a\u00020\u0005J\b\u0010=\u001a\u0004\u0018\u00010\u0005J\u0006\u0010>\u001a\u00020\"J\u0006\u0010?\u001a\u00020\"J\b\u0010@\u001a\u0004\u0018\u00010\u0018J\u0010\u0010A\u001a\u0004\u0018\u00010B2\u0006\u0010C\u001a\u00020\u001dJ\u0006\u0010D\u001a\u00020\u0018J\u0016\u0010E\u001a\u00020\u00142\u0006\u0010F\u001a\u00020G2\u0006\u0010H\u001a\u00020IJ8\u0010J\u001a\u0002HK\"\u0004\b\u0000\u0010K2\u0006\u0010L\u001a\u00020\u00052\u0017\u00107\u001a\u0013\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u0002HK0M¢\u0006\u0002\bNH\u0086\bø\u0001\u0000¢\u0006\u0002\u0010OJ\"\u0010P\u001a\u00020Q2\u0006\u0010R\u001a\u00020\n2\f\u00107\u001a\b\u0012\u0004\u0012\u00020Q08H\u0086\bø\u0001\u0000JB\u0010S\u001a\u0002HK\"\b\b\u0000\u0010)*\u00020\u0014\"\u0004\b\u0001\u0010K2\u0006\u0010T\u001a\u0002H)2\u0017\u00107\u001a\u0013\u0012\u0004\u0012\u0002H)\u0012\u0004\u0012\u0002HK0M¢\u0006\u0002\bNH\u0086\bø\u0001\u0000¢\u0006\u0002\u0010UJB\u0010V\u001a\u0002HK\"\b\b\u0000\u0010)*\u00020\u0018\"\u0004\b\u0001\u0010K2\u0006\u0010@\u001a\u0002H)2\u0017\u00107\u001a\u0013\u0012\u0004\u0012\u0002H)\u0012\u0004\u0012\u0002HK0M¢\u0006\u0002\bNH\u0086\bø\u0001\u0000¢\u0006\u0002\u0010WJD\u0010X\u001a\u0002HK\"\u0004\b\u0000\u0010K2\u0006\u0010C\u001a\u00020\u001d2\n\b\u0002\u0010Y\u001a\u0004\u0018\u00010\u001e2\u0017\u00107\u001a\u0013\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u0002HK0M¢\u0006\u0002\bNH\u0086\bø\u0001\u0000¢\u0006\u0002\u0010ZJ/\u0010[\u001a\u0002H)\"\u0004\b\u0000\u0010)2\b\u0010\\\u001a\u0004\u0018\u00010\"2\f\u00107\u001a\b\u0012\u0004\u0012\u0002H)08H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010]J/\u0010^\u001a\u0002H)\"\u0004\b\u0000\u0010)2\b\u0010\\\u001a\u0004\u0018\u00010\"2\f\u00107\u001a\b\u0012\u0004\u0012\u0002H)08H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010]R\"\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048\u0000X\u0081\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0006\u0010\u0002\u001a\u0004\b\u0007\u0010\bR\"\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u00048\u0000X\u0081\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u000b\u0010\u0002\u001a\u0004\b\f\u0010\bR(\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000f0\u000e8\u0000X\u0081\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0010\u0010\u0002\u001a\u0004\b\u0011\u0010\u0012R\"\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\u00048\u0000X\u0081\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0015\u0010\u0002\u001a\u0004\b\u0016\u0010\bR\"\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\u00048\u0000X\u0081\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u0019\u0010\u0002\u001a\u0004\b\u001a\u0010\bR0\u0010\u001b\u001a\u0016\u0012\u0012\u0012\u0010\u0012\u0004\u0012\u00020\u001d\u0012\u0006\u0012\u0004\u0018\u00010\u001e0\u001c0\u00048\u0000X\u0081\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b\u001f\u0010\u0002\u001a\u0004\b \u0010\bR\"\u0010!\u001a\b\u0012\u0004\u0012\u00020\"0\u00048\u0000X\u0081\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b#\u0010\u0002\u001a\u0004\b$\u0010\bR\"\u0010%\u001a\b\u0012\u0004\u0012\u00020\"0\u00048\u0000X\u0081\u0004¢\u0006\u000e\n\u0000\u0012\u0004\b&\u0010\u0002\u001a\u0004\b'\u0010\b\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006_"}, d2 = {"Lorg/jetbrains/kotlin/fir/backend/Fir2IrConversionScope;", "", "()V", "classStack", "", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getClassStack$annotations", "getClassStack", "()Ljava/util/List;", "containingFirClassStack", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "getContainingFirClassStack$annotations", "getContainingFirClassStack", "currentlyGeneratedDelegatedConstructors", "", "Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;", "getCurrentlyGeneratedDelegatedConstructors$annotations", "getCurrentlyGeneratedDelegatedConstructors", "()Ljava/util/Map;", "functionStack", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "getFunctionStack$annotations", "getFunctionStack", "parentStack", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "getParentStack$annotations", "getParentStack", "propertyStack", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "getPropertyStack$annotations", "getPropertyStack", "safeCallSubjectVariableStack", "Lorg/jetbrains/kotlin/ir/declarations/IrVariable;", "getSafeCallSubjectVariableStack$annotations", "getSafeCallSubjectVariableStack", "whenSubjectVariableStack", "getWhenSubjectVariableStack$annotations", "getWhenSubjectVariableStack", "applyParentFromStackTo", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "declaration", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;)Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "containerFirClass", "containingFileIfAny", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "defaultConversionTypeOrigin", "Lorg/jetbrains/kotlin/fir/backend/ConversionTypeOrigin;", "dispatchReceiverParameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "irClass", "forDelegatingConstructorCall", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "f", "Lkotlin/Function0;", "forDelegatingConstructorCall$fir2ir", "(Lorg/jetbrains/kotlin/ir/declarations/IrConstructor;Lorg/jetbrains/kotlin/ir/declarations/IrClass;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "getConstructorForCurrentlyGeneratedDelegatedConstructor", "itClass", "lastClass", "lastSafeCallSubject", "lastWhenSubject", "parent", "parentAccessorOfPropertyFromStack", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "property", "parentFromStack", "returnTarget", "expression", "Lorg/jetbrains/kotlin/fir/expressions/FirReturnExpression;", "declarationStorage", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrDeclarationStorage;", "withClass", "R", "klass", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "withContainingFirClass", "", "containingFirClass", "withFunction", "function", "(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "withParent", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "withProperty", "firProperty", "(Lorg/jetbrains/kotlin/ir/declarations/IrProperty;Lorg/jetbrains/kotlin/fir/declarations/FirProperty;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "withSafeCallSubject", "subject", "(Lorg/jetbrains/kotlin/ir/declarations/IrVariable;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withWhenSubject", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Fir2IrConversionScope {
    private final List<IrDeclarationParent> parentStack = new ArrayList();
    private final List<FirClass> containingFirClassStack = new ArrayList();
    private final Map<IrClass, IrConstructor> currentlyGeneratedDelegatedConstructors = new LinkedHashMap();
    private final List<IrFunction> functionStack = new ArrayList();
    private final List<Pair<IrProperty, FirProperty>> propertyStack = new ArrayList();
    private final List<IrClass> classStack = new ArrayList();
    private final List<IrVariable> whenSubjectVariableStack = new ArrayList();
    private final List<IrVariable> safeCallSubjectVariableStack = new ArrayList();

    @PrivateForInline
    public static /* synthetic */ void getClassStack$annotations() {
    }

    @PrivateForInline
    public static /* synthetic */ void getContainingFirClassStack$annotations() {
    }

    @PrivateForInline
    public static /* synthetic */ void getCurrentlyGeneratedDelegatedConstructors$annotations() {
    }

    @PrivateForInline
    public static /* synthetic */ void getFunctionStack$annotations() {
    }

    @PrivateForInline
    public static /* synthetic */ void getParentStack$annotations() {
    }

    @PrivateForInline
    public static /* synthetic */ void getPropertyStack$annotations() {
    }

    @PrivateForInline
    public static /* synthetic */ void getSafeCallSubjectVariableStack$annotations() {
    }

    @PrivateForInline
    public static /* synthetic */ void getWhenSubjectVariableStack$annotations() {
    }

    public final List<IrDeclarationParent> getParentStack() {
        return this.parentStack;
    }

    public final List<FirClass> getContainingFirClassStack() {
        return this.containingFirClassStack;
    }

    public final Map<IrClass, IrConstructor> getCurrentlyGeneratedDelegatedConstructors() {
        return this.currentlyGeneratedDelegatedConstructors;
    }

    public final <T extends IrDeclarationParent, R> R withParent(T parent, Function1<? super T, ? extends R> f) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(f, "f");
        getParentStack().add(parent);
        try {
            return f.invoke(parent);
        } finally {
            InlineMarker.finallyStart(1);
            getParentStack().remove(getParentStack().size() - 1);
            InlineMarker.finallyEnd(1);
        }
    }

    public final <T> T forDelegatingConstructorCall$fir2ir(IrConstructor constructor, IrClass irClass, Function0<? extends T> f) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        Intrinsics.checkNotNullParameter(f, "f");
        this.currentlyGeneratedDelegatedConstructors.put(irClass, constructor);
        try {
            return f.invoke();
        } finally {
            this.currentlyGeneratedDelegatedConstructors.remove(irClass);
        }
    }

    public final IrConstructor getConstructorForCurrentlyGeneratedDelegatedConstructor(IrClass itClass) {
        Intrinsics.checkNotNullParameter(itClass, "itClass");
        return this.currentlyGeneratedDelegatedConstructors.get(itClass);
    }

    public final IrFile containingFileIfAny() {
        Object orNull = CollectionsKt.getOrNull(this.parentStack, 0);
        if (orNull instanceof IrFile) {
            return (IrFile) orNull;
        }
        return null;
    }

    public final void withContainingFirClass(FirClass containingFirClass, Function0<Unit> f) {
        Intrinsics.checkNotNullParameter(containingFirClass, "containingFirClass");
        Intrinsics.checkNotNullParameter(f, "f");
        getContainingFirClassStack().add(containingFirClass);
        try {
            f.invoke();
        } finally {
            InlineMarker.finallyStart(1);
            getContainingFirClassStack().remove(getContainingFirClassStack().size() - 1);
            InlineMarker.finallyEnd(1);
        }
    }

    public final IrDeclarationParent parentFromStack() {
        return (IrDeclarationParent) CollectionsKt.last((List) this.parentStack);
    }

    public final IrSimpleFunction parentAccessorOfPropertyFromStack(IrProperty property) {
        Intrinsics.checkNotNullParameter(property, "property");
        for (IrDeclarationParent irDeclarationParent : CollectionsKt.asReversedMutable(this.parentStack)) {
            if (Intrinsics.areEqual(irDeclarationParent, property.getGetter())) {
                return property.getGetter();
            }
            if (Intrinsics.areEqual(irDeclarationParent, property.getSetter())) {
                return property.getSetter();
            }
        }
        return null;
    }

    public final <T extends IrDeclaration> T applyParentFromStackTo(T declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        declaration.setParent((IrDeclarationParent) CollectionsKt.last((List) this.parentStack));
        return declaration;
    }

    public final FirClass containerFirClass() {
        return (FirClass) CollectionsKt.lastOrNull((List) this.containingFirClassStack);
    }

    public final List<IrFunction> getFunctionStack() {
        return this.functionStack;
    }

    public final <T extends IrFunction, R> R withFunction(T function, Function1<? super T, ? extends R> f) {
        Intrinsics.checkNotNullParameter(function, "function");
        Intrinsics.checkNotNullParameter(f, "f");
        getFunctionStack().add(function);
        try {
            return f.invoke(function);
        } finally {
            InlineMarker.finallyStart(1);
            getFunctionStack().remove(getFunctionStack().size() - 1);
            InlineMarker.finallyEnd(1);
        }
    }

    public final List<Pair<IrProperty, FirProperty>> getPropertyStack() {
        return this.propertyStack;
    }

    public static /* synthetic */ Object withProperty$default(Fir2IrConversionScope fir2IrConversionScope, IrProperty property, FirProperty firProperty, Function1 f, int i, Object obj) {
        if ((i & 2) != 0) {
            firProperty = null;
        }
        Intrinsics.checkNotNullParameter(property, "property");
        Intrinsics.checkNotNullParameter(f, "f");
        fir2IrConversionScope.getPropertyStack().add(TuplesKt.to(property, firProperty));
        try {
            return f.invoke(property);
        } finally {
            InlineMarker.finallyStart(1);
            fir2IrConversionScope.getPropertyStack().remove(fir2IrConversionScope.getPropertyStack().size() - 1);
            InlineMarker.finallyEnd(1);
        }
    }

    public final <R> R withProperty(IrProperty property, FirProperty firProperty, Function1<? super IrProperty, ? extends R> f) {
        Intrinsics.checkNotNullParameter(property, "property");
        Intrinsics.checkNotNullParameter(f, "f");
        getPropertyStack().add(TuplesKt.to(property, firProperty));
        try {
            return f.invoke(property);
        } finally {
            InlineMarker.finallyStart(1);
            getPropertyStack().remove(getPropertyStack().size() - 1);
            InlineMarker.finallyEnd(1);
        }
    }

    public final List<IrClass> getClassStack() {
        return this.classStack;
    }

    public final <R> R withClass(IrClass klass, Function1<? super IrClass, ? extends R> f) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(f, "f");
        getClassStack().add(klass);
        try {
            return f.invoke(klass);
        } finally {
            InlineMarker.finallyStart(1);
            getClassStack().remove(getClassStack().size() - 1);
            InlineMarker.finallyEnd(1);
        }
    }

    public final List<IrVariable> getWhenSubjectVariableStack() {
        return this.whenSubjectVariableStack;
    }

    public final List<IrVariable> getSafeCallSubjectVariableStack() {
        return this.safeCallSubjectVariableStack;
    }

    public final <T> T withWhenSubject(IrVariable subject, Function0<? extends T> f) {
        Intrinsics.checkNotNullParameter(f, "f");
        if (subject != null) {
            getWhenSubjectVariableStack().add(subject);
        }
        try {
            return f.invoke();
        } finally {
            InlineMarker.finallyStart(1);
            if (subject != null) {
                getWhenSubjectVariableStack().remove(getWhenSubjectVariableStack().size() - 1);
            }
            InlineMarker.finallyEnd(1);
        }
    }

    public final <T> T withSafeCallSubject(IrVariable subject, Function0<? extends T> f) {
        Intrinsics.checkNotNullParameter(f, "f");
        if (subject != null) {
            getSafeCallSubjectVariableStack().add(subject);
        }
        try {
            return f.invoke();
        } finally {
            InlineMarker.finallyStart(1);
            if (subject != null) {
                getSafeCallSubjectVariableStack().remove(getSafeCallSubjectVariableStack().size() - 1);
            }
            InlineMarker.finallyEnd(1);
        }
    }

    public final IrFunction returnTarget(FirReturnExpression expression, Fir2IrDeclarationStorage declarationStorage) {
        IrSimpleFunction cachedIrFunction;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(declarationStorage, "declarationStorage");
        FirFunction firFunction = (FirFunction) expression.getTarget().getLabeledElement();
        if (firFunction instanceof FirConstructor) {
            cachedIrFunction = Fir2IrDeclarationStorage.getCachedIrConstructor$default(declarationStorage, (FirConstructor) firFunction, null, 2, null);
        } else if (firFunction instanceof FirPropertyAccessor) {
            IrSimpleFunction getter = null;
            for (Pair pair : CollectionsKt.asReversedMutable(this.propertyStack)) {
                IrProperty irProperty = (IrProperty) pair.component1();
                FirProperty firProperty = (FirProperty) pair.component2();
                if ((firProperty != null ? firProperty.getGetter() : null) == firFunction) {
                    getter = irProperty.getGetter();
                } else if ((firProperty != null ? firProperty.getSetter() : null) == firFunction) {
                    getter = irProperty.getSetter();
                }
            }
            cachedIrFunction = getter;
        } else {
            cachedIrFunction = declarationStorage.getCachedIrFunction(firFunction);
        }
        for (IrFunction irFunction : CollectionsKt.asReversedMutable(this.functionStack)) {
            if (Intrinsics.areEqual(irFunction, cachedIrFunction)) {
                return irFunction;
            }
        }
        return (IrFunction) CollectionsKt.last((List) this.functionStack);
    }

    public final IrDeclarationParent parent() {
        return (IrDeclarationParent) CollectionsKt.lastOrNull((List) this.parentStack);
    }

    public final ConversionTypeOrigin defaultConversionTypeOrigin() {
        IrDeclarationParent irDeclarationParentParent = parent();
        IrFunction irFunction = irDeclarationParentParent instanceof IrFunction ? (IrFunction) irDeclarationParentParent : null;
        return (irFunction == null || !AdditionalIrUtilsKt.isSetter(irFunction)) ? ConversionTypeOrigin.DEFAULT : ConversionTypeOrigin.SETTER;
    }

    public final IrValueParameter dispatchReceiverParameter(IrClass irClass) {
        IrValueParameter thisReceiver;
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        for (IrFunction irFunction : CollectionsKt.asReversedMutable(this.functionStack)) {
            if (Intrinsics.areEqual(IrUtilsKt.getParentClassOrNull(irFunction), irClass)) {
                if ((irFunction instanceof IrConstructor) && irClass.getIsInner() && (thisReceiver = irClass.getThisReceiver()) != null) {
                    return thisReceiver;
                }
                IrValueParameter dispatchReceiverParameter = irFunction.getDispatchReceiverParameter();
                if (dispatchReceiverParameter != null) {
                    return dispatchReceiverParameter;
                }
            }
        }
        return irClass.getThisReceiver();
    }

    public final IrClass lastClass() {
        return (IrClass) CollectionsKt.lastOrNull((List) this.classStack);
    }

    public final IrVariable lastWhenSubject() {
        return (IrVariable) CollectionsKt.last((List) this.whenSubjectVariableStack);
    }

    public final IrVariable lastSafeCallSubject() {
        return (IrVariable) CollectionsKt.last((List) this.safeCallSubjectVariableStack);
    }
}
