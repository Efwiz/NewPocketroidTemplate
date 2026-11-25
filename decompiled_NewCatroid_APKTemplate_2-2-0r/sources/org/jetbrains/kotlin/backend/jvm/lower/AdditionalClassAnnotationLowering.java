package org.jetbrains.kotlin.backend.jvm.lower;

import java.lang.annotation.ElementType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.ClassLoweringPass;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.JvmSymbols;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.descriptors.annotations.KotlinRetention;
import org.jetbrains.kotlin.descriptors.annotations.KotlinTarget;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetEnumValue;
import org.jetbrains.kotlin.ir.expressions.IrVararg;
import org.jetbrains.kotlin.ir.expressions.IrVarargElement;
import org.jetbrains.kotlin.ir.expressions.impl.IrClassReferenceImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstructorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrGetEnumValueImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrVarargImpl;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: AdditionalClassAnnotationLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002J\u0010\u0010\u000e\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002J\u0010\u0010\u000f\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002J\u0010\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002J\u0018\u0010\u0011\u001a\n\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u00122\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u0010\u0010\u0016\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J\u0012\u0010\u0017\u001a\u0004\u0018\u00010\u00182\u0006\u0010\u0019\u001a\u00020\u0013H\u0002J\u0014\u0010\u001a\u001a\n\u0012\u0004\u0012\u00020\u0013\u0018\u00010\u0012*\u00020\rH\u0002J\u0016\u0010\u001b\u001a\u0004\u0018\u00010\u001c*\u00020\u00152\u0006\u0010\u001d\u001a\u00020\u001eH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0007\u001a\u00060\bR\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/AdditionalClassAnnotationLowering;", "Lorg/jetbrains/kotlin/backend/common/ClassLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "noNewJavaAnnotationTargets", "", "symbols", "Lorg/jetbrains/kotlin/backend/jvm/JvmSymbols$JavaAnnotations;", "Lorg/jetbrains/kotlin/backend/jvm/JvmSymbols;", "generateDocumentedAnnotation", "", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "generateRepeatableAnnotation", "generateRetentionAnnotation", "generateTargetAnnotation", "loadAnnotationTargets", "", "Lorg/jetbrains/kotlin/descriptors/annotations/KotlinTarget;", "targetEntry", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "lower", "mapTarget", "Lorg/jetbrains/kotlin/ir/declarations/IrEnumEntry;", "target", "applicableTargetSet", "getValueArgument", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "name", "Lorg/jetbrains/kotlin/name/Name;", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class AdditionalClassAnnotationLowering implements ClassLoweringPass {
    private final JvmBackendContext context;
    private final boolean noNewJavaAnnotationTargets;
    private final JvmSymbols.JavaAnnotations symbols;

    /* JADX INFO: compiled from: AdditionalClassAnnotationLowering.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[KotlinTarget.values().length];
            try {
                iArr[KotlinTarget.TYPE_PARAMETER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[KotlinTarget.TYPE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public AdditionalClassAnnotationLowering(JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.symbols = context.getIr().getSymbols().getJavaAnnotations();
        this.noNewJavaAnnotationTargets = context.getConfig().getNoNewJavaAnnotationTargets() || !context.getIsCompilingAgainstJdk8OrLater();
    }

    @Override // org.jetbrains.kotlin.backend.common.ClassLoweringPass
    public void lower(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        if (IrUtilsKt.isAnnotationClass(irClass)) {
            generateDocumentedAnnotation(irClass);
            generateRetentionAnnotation(irClass);
            generateTargetAnnotation(irClass);
            generateRepeatableAnnotation(irClass);
        }
    }

    private final void generateDocumentedAnnotation(IrClass irClass) {
        IrClass irClass2 = irClass;
        if (IrUtilsKt.hasAnnotation(irClass2, StandardNames.FqNames.mustBeDocumented)) {
            FqName DOCUMENTED_ANNOTATION = JvmAnnotationNames.DOCUMENTED_ANNOTATION;
            Intrinsics.checkNotNullExpressionValue(DOCUMENTED_ANNOTATION, "DOCUMENTED_ANNOTATION");
            if (IrUtilsKt.hasAnnotation(irClass2, DOCUMENTED_ANNOTATION)) {
                return;
            }
            irClass.setAnnotations(CollectionsKt.plus((Collection<? extends IrConstructorCallImpl>) irClass.getAnnotations(), IrConstructorCallImpl.Companion.fromSymbolOwner$default(IrConstructorCallImpl.INSTANCE, -1, -1, this.symbols.getDocumentedConstructor().getReturnType(), this.symbols.getDocumentedConstructor().getSymbol(), 0, null, 32, null)));
        }
    }

    private final void generateRetentionAnnotation(IrClass irClass) {
        IrEnumEntry rpRuntime;
        FqName RETENTION_ANNOTATION = JvmAnnotationNames.RETENTION_ANNOTATION;
        Intrinsics.checkNotNullExpressionValue(RETENTION_ANNOTATION, "RETENTION_ANNOTATION");
        if (IrUtilsKt.hasAnnotation(irClass, RETENTION_ANNOTATION)) {
            return;
        }
        KotlinRetention annotationRetention = IrUtilsKt.getAnnotationRetention(irClass);
        if (annotationRetention == null || (rpRuntime = this.symbols.getAnnotationRetentionMap().get(annotationRetention)) == null) {
            rpRuntime = this.symbols.getRpRuntime();
        }
        List<IrConstructorCall> annotations = irClass.getAnnotations();
        IrConstructorCallImpl irConstructorCallImplFromSymbolOwner$default = IrConstructorCallImpl.Companion.fromSymbolOwner$default(IrConstructorCallImpl.INSTANCE, -1, -1, this.symbols.getRetentionConstructor().getReturnType(), this.symbols.getRetentionConstructor().getSymbol(), 0, null, 32, null);
        irConstructorCallImplFromSymbolOwner$default.putValueArgument(0, new IrGetEnumValueImpl(-1, -1, IrUtilsKt.getDefaultType(this.symbols.getRetentionPolicyEnum()), rpRuntime.getSymbol()));
        Unit unit = Unit.INSTANCE;
        irClass.setAnnotations(CollectionsKt.plus((Collection<? extends IrConstructorCallImpl>) annotations, irConstructorCallImplFromSymbolOwner$default));
    }

    private final void generateTargetAnnotation(IrClass irClass) {
        Set<KotlinTarget> setApplicableTargetSet;
        FqName TARGET_ANNOTATION = JvmAnnotationNames.TARGET_ANNOTATION;
        Intrinsics.checkNotNullExpressionValue(TARGET_ANNOTATION, "TARGET_ANNOTATION");
        if (IrUtilsKt.hasAnnotation(irClass, TARGET_ANNOTATION) || (setApplicableTargetSet = applicableTargetSet(irClass)) == null) {
            return;
        }
        HashSet hashSet = new HashSet();
        Iterator<T> it = setApplicableTargetSet.iterator();
        while (it.hasNext()) {
            IrEnumEntry irEnumEntryMapTarget = mapTarget((KotlinTarget) it.next());
            if (irEnumEntryMapTarget != null) {
                hashSet.add(irEnumEntryMapTarget);
            }
        }
        List listSortedWith = CollectionsKt.sortedWith(hashSet, new Comparator() { // from class: org.jetbrains.kotlin.backend.jvm.lower.AdditionalClassAnnotationLowering$generateTargetAnnotation$$inlined$sortedBy$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                String strAsString = ((IrEnumEntry) t).getSymbol().getOwner().getName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                ElementType elementTypeValueOf = ElementType.valueOf(strAsString);
                String strAsString2 = ((IrEnumEntry) t2).getSymbol().getOwner().getName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
                return ComparisonsKt.compareValues(elementTypeValueOf, ElementType.valueOf(strAsString2));
            }
        });
        IrVarargImpl irVarargImpl = new IrVarargImpl(-1, -1, IrTypesKt.typeWith(this.context.getIrBuiltIns().getArrayClass(), IrUtilsKt.getDefaultType(this.symbols.getElementTypeEnum())), IrUtilsKt.getDefaultType(this.symbols.getElementTypeEnum()));
        Iterator it2 = listSortedWith.iterator();
        while (it2.hasNext()) {
            irVarargImpl.getElements().add(new IrGetEnumValueImpl(-1, -1, IrUtilsKt.getDefaultType(this.symbols.getElementTypeEnum()), ((IrEnumEntry) it2.next()).getSymbol()));
        }
        List<IrConstructorCall> annotations = irClass.getAnnotations();
        IrConstructorCallImpl irConstructorCallImplFromSymbolOwner$default = IrConstructorCallImpl.Companion.fromSymbolOwner$default(IrConstructorCallImpl.INSTANCE, -1, -1, this.symbols.getTargetConstructor().getReturnType(), this.symbols.getTargetConstructor().getSymbol(), 0, null, 32, null);
        irConstructorCallImplFromSymbolOwner$default.putValueArgument(0, irVarargImpl);
        Unit unit = Unit.INSTANCE;
        irClass.setAnnotations(CollectionsKt.plus((Collection<? extends IrConstructorCallImpl>) annotations, irConstructorCallImplFromSymbolOwner$default));
    }

    private final IrEnumEntry mapTarget(KotlinTarget target) {
        IrEnumEntry typeParameterTarget;
        int i = WhenMappings.$EnumSwitchMapping$0[target.ordinal()];
        if (i == 1) {
            typeParameterTarget = this.symbols.getTypeParameterTarget();
            if (this.noNewJavaAnnotationTargets) {
                return null;
            }
        } else if (i == 2) {
            typeParameterTarget = this.symbols.getTypeUseTarget();
            if (this.noNewJavaAnnotationTargets) {
                return null;
            }
        } else {
            return this.symbols.getJvmTargetMap().get(target);
        }
        return typeParameterTarget;
    }

    private final void generateRepeatableAnnotation(IrClass irClass) {
        IrClass irClass2 = irClass;
        if (IrUtilsKt.hasAnnotation(irClass2, StandardNames.FqNames.repeatable)) {
            FqName REPEATABLE_ANNOTATION = JvmAnnotationNames.REPEATABLE_ANNOTATION;
            Intrinsics.checkNotNullExpressionValue(REPEATABLE_ANNOTATION, "REPEATABLE_ANNOTATION");
            if (IrUtilsKt.hasAnnotation(irClass2, REPEATABLE_ANNOTATION)) {
                return;
            }
            Iterator<T> it = irClass.getDeclarations().iterator();
            boolean z = false;
            Object obj = null;
            while (true) {
                if (!it.hasNext()) {
                    if (!z) {
                        break;
                    } else {
                        break;
                    }
                }
                Object next = it.next();
                IrDeclaration irDeclaration = (IrDeclaration) next;
                if ((irDeclaration instanceof IrClass) && Intrinsics.areEqual(((IrClass) irDeclaration).getName().asString(), JvmAbi.REPEATABLE_ANNOTATION_CONTAINER_NAME)) {
                    if (!z) {
                        obj = next;
                        z = true;
                    }
                }
                obj = null;
                break;
            }
            IrClass irClass3 = (IrClass) obj;
            if (irClass3 == null) {
                throw new IllegalStateException(("Repeatable annotation class should have a container generated: " + RenderIrElementKt.render$default(irClass, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
            }
            IrClassReferenceImpl irClassReferenceImpl = new IrClassReferenceImpl(-1, -1, IrTypesKt.typeWith(this.context.getIrBuiltIns().getKClassClass(), IrUtilsKt.getDefaultType(irClass3)), irClass3.getSymbol(), IrUtilsKt.getDefaultType(irClass3));
            List<IrConstructorCall> annotations = irClass.getAnnotations();
            IrConstructorCallImpl irConstructorCallImplFromSymbolOwner$default = IrConstructorCallImpl.Companion.fromSymbolOwner$default(IrConstructorCallImpl.INSTANCE, -1, -1, this.symbols.getRepeatableConstructor().getReturnType(), this.symbols.getRepeatableConstructor().getSymbol(), 0, null, 32, null);
            irConstructorCallImplFromSymbolOwner$default.putValueArgument(0, irClassReferenceImpl);
            Unit unit = Unit.INSTANCE;
            irClass.setAnnotations(CollectionsKt.plus((Collection<? extends IrConstructorCallImpl>) annotations, irConstructorCallImplFromSymbolOwner$default));
        }
    }

    private final IrExpression getValueArgument(IrConstructorCall irConstructorCall, Name name2) {
        Object next;
        Iterator<T> it = irConstructorCall.getSymbol().getOwner().getValueParameters().iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (!Intrinsics.areEqual(((IrValueParameter) next).getName(), name2));
        IrValueParameter irValueParameter = (IrValueParameter) next;
        if (irValueParameter != null) {
            return irConstructorCall.getValueArgument(irValueParameter.getIndex());
        }
        return null;
    }

    private final Set<KotlinTarget> applicableTargetSet(IrClass irClass) {
        IrConstructorCall annotation = IrUtilsKt.getAnnotation(irClass, StandardNames.FqNames.target);
        if (annotation == null) {
            return null;
        }
        return loadAnnotationTargets(annotation);
    }

    private final Set<KotlinTarget> loadAnnotationTargets(IrConstructorCall targetEntry) {
        Name nameIdentifier = Name.identifier("allowedTargets");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        IrExpression valueArgument = getValueArgument(targetEntry, nameIdentifier);
        IrVararg irVararg = valueArgument instanceof IrVararg ? (IrVararg) valueArgument : null;
        if (irVararg == null) {
            return null;
        }
        List<IrVarargElement> elements = irVararg.getElements();
        ArrayList<IrGetEnumValue> arrayList = new ArrayList();
        for (Object obj : elements) {
            if (obj instanceof IrGetEnumValue) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (IrGetEnumValue irGetEnumValue : arrayList) {
            KotlinTarget.Companion companion = KotlinTarget.INSTANCE;
            String strAsString = irGetEnumValue.getSymbol().getOwner().getName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            KotlinTarget kotlinTargetValueOrNull = companion.valueOrNull(strAsString);
            if (kotlinTargetValueOrNull != null) {
                arrayList2.add(kotlinTargetValueOrNull);
            }
        }
        return CollectionsKt.toSet(arrayList2);
    }
}
