package org.jetbrains.kotlin.ir.backend.js.lower;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.NotImplementedError;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KFunction;
import org.jetbrains.kotlin.backend.common.BodyLoweringPass;
import org.jetbrains.kotlin.backend.common.CompilationExceptionKt;
import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext;
import org.jetbrains.kotlin.backend.common.ir.IrUtilsKt;
import org.jetbrains.kotlin.backend.common.ir.Symbols;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext;
import org.jetbrains.kotlin.ir.backend.js.JsStatementOrigins;
import org.jetbrains.kotlin.ir.backend.js.ReflectionSymbols;
import org.jetbrains.kotlin.ir.backend.js.ir.JsIrBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrClassReference;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetClass;
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.types.IrDynamicType;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.IrTypePredicatesKt;
import org.jetbrains.kotlin.ir.types.IrTypeProjection;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoidKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.Variance;

/* JADX INFO: compiled from: ClassReferenceLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0096\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J)\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&2\u0012\u0010'\u001a\n\u0012\u0006\b\u0001\u0012\u00020$0(\"\u00020$H\u0002¢\u0006\u0002\u0010)J\u0010\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020\nH\u0002J\u001a\u0010-\u001a\u00020+2\b\b\u0002\u0010.\u001a\u00020\n2\u0006\u0010/\u001a\u00020\nH\u0002J \u00100\u001a\u00020$2\u0006\u0010.\u001a\u00020\n2\u0006\u0010/\u001a\u00020\n2\u0006\u00101\u001a\u00020$H\u0002J\b\u00102\u001a\u00020$H\u0002J\u001e\u00103\u001a\u00020$2\u0006\u00104\u001a\u0002052\f\u00106\u001a\b\u0012\u0004\u0012\u00020807H\u0002J\u001e\u00109\u001a\u00020$2\u0006\u0010,\u001a\u00020\n2\f\u00106\u001a\b\u0012\u0004\u0012\u00020807H\u0002J\u001e\u0010:\u001a\u00020$2\u0006\u0010;\u001a\u0002082\f\u00106\u001a\b\u0012\u0004\u0012\u00020807H\u0002J\u001e\u0010<\u001a\u00020$2\u0006\u0010=\u001a\u00020>2\f\u00106\u001a\b\u0012\u0004\u0012\u00020807H\u0002J\u001e\u0010?\u001a\u00020$2\u0006\u0010,\u001a\u00020@2\f\u00106\u001a\b\u0012\u0004\u0012\u00020807H\u0002J\u001a\u0010A\u001a\u0004\u0018\u00010+2\u0006\u0010.\u001a\u00020\n2\u0006\u0010/\u001a\u00020\nH\u0002J\u001a\u0010B\u001a\u0004\u0018\u00010+2\u0006\u0010.\u001a\u00020\n2\u0006\u0010/\u001a\u00020\nH\u0002J\u0018\u0010C\u001a\u00020+2\u0006\u0010D\u001a\u00020\f2\u0006\u0010.\u001a\u00020\nH\u0002J\u0018\u0010E\u001a\u00020F2\u0006\u0010G\u001a\u00020H2\u0006\u0010I\u001a\u00020JH\u0016J\u0010\u0010K\u001a\u00020\f2\u0006\u0010%\u001a\u00020LH\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R3\u0010\u0007\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\t\u0012\u0004\u0012\u00020\f0\b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\r\u0010\u000eR3\u0010\u0011\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\t\u0012\u0004\u0012\u00020\f0\b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\u0010\u001a\u0004\b\u0012\u0010\u000eR\u001b\u0010\u0014\u001a\u00020\f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0017\u0010\u0010\u001a\u0004\b\u0015\u0010\u0016R&\u0010\u0018\u001a\r\u0012\t\u0012\u00070\u001a¢\u0006\u0002\b\u001b0\u00198BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001e\u0010\u0010\u001a\u0004\b\u001c\u0010\u001dR\u0014\u0010\u001f\u001a\u00020 8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b!\u0010\"¨\u0006M"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/ClassReferenceLowering;", "Lorg/jetbrains/kotlin/backend/common/BodyLoweringPass;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "finalPrimitiveClasses", "", "Lkotlin/reflect/KFunction1;", "Lorg/jetbrains/kotlin/ir/types/IrType;", "", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "getFinalPrimitiveClasses", "()Ljava/util/Map;", "finalPrimitiveClasses$delegate", "Lkotlin/Lazy;", "openPrimitiveClasses", "getOpenPrimitiveClasses", "openPrimitiveClasses$delegate", "primitiveClassFunctionClass", "getPrimitiveClassFunctionClass", "()Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "primitiveClassFunctionClass$delegate", "primitiveClassProperties", "", "Lorg/jetbrains/kotlin/ir/declarations/IrProperty;", "Lkotlin/internal/NoInfer;", "getPrimitiveClassProperties", "()Ljava/util/List;", "primitiveClassProperties$delegate", "reflectionSymbols", "Lorg/jetbrains/kotlin/ir/backend/js/ReflectionSymbols;", "getReflectionSymbols", "()Lorg/jetbrains/kotlin/ir/backend/js/ReflectionSymbols;", "buildCall", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "name", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "args", "", "(Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;[Lorg/jetbrains/kotlin/ir/expressions/IrExpression;)Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "callGetClassByType", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", ModuleXmlParser.TYPE, "callGetKClass", "returnType", "typeArgument", "callGetKClassFromExpression", "argument", "createDynamicType", "createKClassifier", "classifier", "Lorg/jetbrains/kotlin/ir/symbols/IrClassifierSymbol;", "visitedTypeParams", "", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", Namer.CREATE_KTYPE, Namer.CREATE_KTYPE_PARAMETER, "typeParameter", "createKTypeProjection", "tp", "Lorg/jetbrains/kotlin/ir/types/IrTypeArgument;", "createSimpleKType", "Lorg/jetbrains/kotlin/ir/types/IrSimpleType;", "getFinalPrimitiveKClass", "getOpenPrimitiveKClass", "getPrimitiveClass", "target", "lower", "", "irBody", "Lorg/jetbrains/kotlin/ir/expressions/IrBody;", "container", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "primitiveClassProperty", "", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ClassReferenceLowering implements BodyLoweringPass {
    private final JsCommonBackendContext context;

    /* JADX INFO: renamed from: finalPrimitiveClasses$delegate, reason: from kotlin metadata */
    private final Lazy finalPrimitiveClasses;

    /* JADX INFO: renamed from: openPrimitiveClasses$delegate, reason: from kotlin metadata */
    private final Lazy openPrimitiveClasses;

    /* JADX INFO: renamed from: primitiveClassFunctionClass$delegate, reason: from kotlin metadata */
    private final Lazy primitiveClassFunctionClass;

    /* JADX INFO: renamed from: primitiveClassProperties$delegate, reason: from kotlin metadata */
    private final Lazy primitiveClassProperties;

    /* JADX INFO: compiled from: ClassReferenceLowering.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Variance.values().length];
            try {
                iArr[Variance.INVARIANT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Variance.IN_VARIANCE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[Variance.OUT_VARIANCE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public ClassReferenceLowering(JsCommonBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.primitiveClassProperties = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<List<? extends IrProperty>>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.ClassReferenceLowering$primitiveClassProperties$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends IrProperty> invoke() {
                List<IrDeclaration> declarations = this.this$0.getReflectionSymbols().getPrimitiveClassesObject().getOwner().getDeclarations();
                ArrayList arrayList = new ArrayList();
                for (Object obj : declarations) {
                    if (obj instanceof IrProperty) {
                        arrayList.add(obj);
                    }
                }
                return arrayList;
            }
        });
        this.primitiveClassFunctionClass = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<IrSimpleFunction>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.ClassReferenceLowering$primitiveClassFunctionClass$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final IrSimpleFunction invoke() {
                for (Object obj : this.this$0.getReflectionSymbols().getPrimitiveClassesObject().getOwner().getDeclarations()) {
                    if ((obj instanceof IrSimpleFunction) && Intrinsics.areEqual(((IrSimpleFunction) obj).getName(), Name.identifier("functionClass"))) {
                        Intrinsics.checkNotNull(obj);
                        return (IrSimpleFunction) obj;
                    }
                }
                obj = null;
                Intrinsics.checkNotNull(obj);
                return (IrSimpleFunction) obj;
            }
        });
        this.finalPrimitiveClasses = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<Map<KFunction<? extends Boolean>, ? extends IrSimpleFunction>>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.ClassReferenceLowering$finalPrimitiveClasses$2
            {
                super(0);
            }

            /* JADX INFO: renamed from: org.jetbrains.kotlin.ir.backend.js.lower.ClassReferenceLowering$finalPrimitiveClasses$2$1, reason: invalid class name */
            /* JADX INFO: compiled from: ClassReferenceLowering.kt */
            @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
            /* synthetic */ class AnonymousClass1 extends FunctionReference implements Function1<IrType, Boolean> {
                public static final AnonymousClass1 INSTANCE = new AnonymousClass1();

                AnonymousClass1() {
                    super(1);
                }

                @Override // kotlin.jvm.internal.CallableReference
                public final String getSignature() {
                    return "isBoolean(Lorg/jetbrains/kotlin/ir/types/IrType;)Z";
                }

                @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
                public final String getName() {
                    return "isBoolean";
                }

                @Override // kotlin.jvm.internal.CallableReference
                public final KDeclarationContainer getOwner() {
                    return Reflection.getOrCreateKotlinPackage(IrTypePredicatesKt.class, "backend.js");
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(IrType p0) {
                    Intrinsics.checkNotNullParameter(p0, "p0");
                    return Boolean.valueOf(IrTypePredicatesKt.isBoolean(p0));
                }
            }

            /* JADX INFO: renamed from: org.jetbrains.kotlin.ir.backend.js.lower.ClassReferenceLowering$finalPrimitiveClasses$2$2, reason: invalid class name */
            /* JADX INFO: compiled from: ClassReferenceLowering.kt */
            @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
            /* synthetic */ class AnonymousClass2 extends FunctionReference implements Function1<IrType, Boolean> {
                public static final AnonymousClass2 INSTANCE = new AnonymousClass2();

                AnonymousClass2() {
                    super(1);
                }

                @Override // kotlin.jvm.internal.CallableReference
                public final String getSignature() {
                    return "isByte(Lorg/jetbrains/kotlin/ir/types/IrType;)Z";
                }

                @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
                public final String getName() {
                    return "isByte";
                }

                @Override // kotlin.jvm.internal.CallableReference
                public final KDeclarationContainer getOwner() {
                    return Reflection.getOrCreateKotlinPackage(IrTypePredicatesKt.class, "backend.js");
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(IrType p0) {
                    Intrinsics.checkNotNullParameter(p0, "p0");
                    return Boolean.valueOf(IrTypePredicatesKt.isByte(p0));
                }
            }

            @Override // kotlin.jvm.functions.Function0
            public final Map<KFunction<? extends Boolean>, ? extends IrSimpleFunction> invoke() {
                Map mapMapOf = MapsKt.mapOf(TuplesKt.to(AnonymousClass1.INSTANCE, "booleanClass"), TuplesKt.to(AnonymousClass2.INSTANCE, "byteClass"), TuplesKt.to(AnonymousClass3.INSTANCE, "shortClass"), TuplesKt.to(AnonymousClass4.INSTANCE, "intClass"), TuplesKt.to(AnonymousClass5.INSTANCE, "floatClass"), TuplesKt.to(AnonymousClass6.INSTANCE, "doubleClass"), TuplesKt.to(AnonymousClass7.INSTANCE, "arrayClass"), TuplesKt.to(AnonymousClass8.INSTANCE, "stringClass"), TuplesKt.to(AnonymousClass9.INSTANCE, "booleanArrayClass"), TuplesKt.to(AnonymousClass10.INSTANCE, "charArrayClass"), TuplesKt.to(AnonymousClass11.INSTANCE, "byteArrayClass"), TuplesKt.to(AnonymousClass12.INSTANCE, "shortArrayClass"), TuplesKt.to(AnonymousClass13.INSTANCE, "intArrayClass"), TuplesKt.to(AnonymousClass14.INSTANCE, "longArrayClass"), TuplesKt.to(AnonymousClass15.INSTANCE, "floatArrayClass"), TuplesKt.to(AnonymousClass16.INSTANCE, "doubleArrayClass"));
                ClassReferenceLowering classReferenceLowering = this.this$0;
                LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt.mapCapacity(mapMapOf.size()));
                for (Map.Entry entry : mapMapOf.entrySet()) {
                    linkedHashMap.put(entry.getKey(), classReferenceLowering.primitiveClassProperty((String) entry.getValue()));
                }
                return linkedHashMap;
            }

            /* JADX INFO: renamed from: org.jetbrains.kotlin.ir.backend.js.lower.ClassReferenceLowering$finalPrimitiveClasses$2$3, reason: invalid class name */
            /* JADX INFO: compiled from: ClassReferenceLowering.kt */
            @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
            /* synthetic */ class AnonymousClass3 extends FunctionReference implements Function1<IrType, Boolean> {
                public static final AnonymousClass3 INSTANCE = new AnonymousClass3();

                AnonymousClass3() {
                    super(1);
                }

                @Override // kotlin.jvm.internal.CallableReference
                public final String getSignature() {
                    return "isShort(Lorg/jetbrains/kotlin/ir/types/IrType;)Z";
                }

                @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
                public final String getName() {
                    return "isShort";
                }

                @Override // kotlin.jvm.internal.CallableReference
                public final KDeclarationContainer getOwner() {
                    return Reflection.getOrCreateKotlinPackage(IrTypePredicatesKt.class, "backend.js");
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(IrType p0) {
                    Intrinsics.checkNotNullParameter(p0, "p0");
                    return Boolean.valueOf(IrTypePredicatesKt.isShort(p0));
                }
            }

            /* JADX INFO: renamed from: org.jetbrains.kotlin.ir.backend.js.lower.ClassReferenceLowering$finalPrimitiveClasses$2$4, reason: invalid class name */
            /* JADX INFO: compiled from: ClassReferenceLowering.kt */
            @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
            /* synthetic */ class AnonymousClass4 extends FunctionReference implements Function1<IrType, Boolean> {
                public static final AnonymousClass4 INSTANCE = new AnonymousClass4();

                AnonymousClass4() {
                    super(1);
                }

                @Override // kotlin.jvm.internal.CallableReference
                public final String getSignature() {
                    return "isInt(Lorg/jetbrains/kotlin/ir/types/IrType;)Z";
                }

                @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
                public final String getName() {
                    return "isInt";
                }

                @Override // kotlin.jvm.internal.CallableReference
                public final KDeclarationContainer getOwner() {
                    return Reflection.getOrCreateKotlinPackage(IrTypePredicatesKt.class, "backend.js");
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(IrType p0) {
                    Intrinsics.checkNotNullParameter(p0, "p0");
                    return Boolean.valueOf(IrTypePredicatesKt.isInt(p0));
                }
            }

            /* JADX INFO: renamed from: org.jetbrains.kotlin.ir.backend.js.lower.ClassReferenceLowering$finalPrimitiveClasses$2$5, reason: invalid class name */
            /* JADX INFO: compiled from: ClassReferenceLowering.kt */
            @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
            /* synthetic */ class AnonymousClass5 extends FunctionReference implements Function1<IrType, Boolean> {
                public static final AnonymousClass5 INSTANCE = new AnonymousClass5();

                AnonymousClass5() {
                    super(1);
                }

                @Override // kotlin.jvm.internal.CallableReference
                public final String getSignature() {
                    return "isFloat(Lorg/jetbrains/kotlin/ir/types/IrType;)Z";
                }

                @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
                public final String getName() {
                    return "isFloat";
                }

                @Override // kotlin.jvm.internal.CallableReference
                public final KDeclarationContainer getOwner() {
                    return Reflection.getOrCreateKotlinPackage(IrTypePredicatesKt.class, "backend.js");
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(IrType p0) {
                    Intrinsics.checkNotNullParameter(p0, "p0");
                    return Boolean.valueOf(IrTypePredicatesKt.isFloat(p0));
                }
            }

            /* JADX INFO: renamed from: org.jetbrains.kotlin.ir.backend.js.lower.ClassReferenceLowering$finalPrimitiveClasses$2$6, reason: invalid class name */
            /* JADX INFO: compiled from: ClassReferenceLowering.kt */
            @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
            /* synthetic */ class AnonymousClass6 extends FunctionReference implements Function1<IrType, Boolean> {
                public static final AnonymousClass6 INSTANCE = new AnonymousClass6();

                AnonymousClass6() {
                    super(1);
                }

                @Override // kotlin.jvm.internal.CallableReference
                public final String getSignature() {
                    return "isDouble(Lorg/jetbrains/kotlin/ir/types/IrType;)Z";
                }

                @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
                public final String getName() {
                    return "isDouble";
                }

                @Override // kotlin.jvm.internal.CallableReference
                public final KDeclarationContainer getOwner() {
                    return Reflection.getOrCreateKotlinPackage(IrTypePredicatesKt.class, "backend.js");
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(IrType p0) {
                    Intrinsics.checkNotNullParameter(p0, "p0");
                    return Boolean.valueOf(IrTypePredicatesKt.isDouble(p0));
                }
            }

            /* JADX INFO: renamed from: org.jetbrains.kotlin.ir.backend.js.lower.ClassReferenceLowering$finalPrimitiveClasses$2$7, reason: invalid class name */
            /* JADX INFO: compiled from: ClassReferenceLowering.kt */
            @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
            /* synthetic */ class AnonymousClass7 extends FunctionReference implements Function1<IrType, Boolean> {
                public static final AnonymousClass7 INSTANCE = new AnonymousClass7();

                AnonymousClass7() {
                    super(1);
                }

                @Override // kotlin.jvm.internal.CallableReference
                public final String getSignature() {
                    return "isArray(Lorg/jetbrains/kotlin/ir/types/IrType;)Z";
                }

                @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
                public final String getName() {
                    return "isArray";
                }

                @Override // kotlin.jvm.internal.CallableReference
                public final KDeclarationContainer getOwner() {
                    return Reflection.getOrCreateKotlinPackage(IrTypePredicatesKt.class, "backend.js");
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(IrType p0) {
                    Intrinsics.checkNotNullParameter(p0, "p0");
                    return Boolean.valueOf(IrTypePredicatesKt.isArray(p0));
                }
            }

            /* JADX INFO: renamed from: org.jetbrains.kotlin.ir.backend.js.lower.ClassReferenceLowering$finalPrimitiveClasses$2$8, reason: invalid class name */
            /* JADX INFO: compiled from: ClassReferenceLowering.kt */
            @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
            /* synthetic */ class AnonymousClass8 extends FunctionReference implements Function1<IrType, Boolean> {
                public static final AnonymousClass8 INSTANCE = new AnonymousClass8();

                AnonymousClass8() {
                    super(1);
                }

                @Override // kotlin.jvm.internal.CallableReference
                public final String getSignature() {
                    return "isString(Lorg/jetbrains/kotlin/ir/types/IrType;)Z";
                }

                @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
                public final String getName() {
                    return "isString";
                }

                @Override // kotlin.jvm.internal.CallableReference
                public final KDeclarationContainer getOwner() {
                    return Reflection.getOrCreateKotlinPackage(IrTypePredicatesKt.class, "backend.js");
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(IrType p0) {
                    Intrinsics.checkNotNullParameter(p0, "p0");
                    return Boolean.valueOf(IrTypePredicatesKt.isString(p0));
                }
            }

            /* JADX INFO: renamed from: org.jetbrains.kotlin.ir.backend.js.lower.ClassReferenceLowering$finalPrimitiveClasses$2$9, reason: invalid class name */
            /* JADX INFO: compiled from: ClassReferenceLowering.kt */
            @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
            /* synthetic */ class AnonymousClass9 extends FunctionReference implements Function1<IrType, Boolean> {
                public static final AnonymousClass9 INSTANCE = new AnonymousClass9();

                AnonymousClass9() {
                    super(1);
                }

                @Override // kotlin.jvm.internal.CallableReference
                public final String getSignature() {
                    return "isBooleanArray(Lorg/jetbrains/kotlin/ir/types/IrType;)Z";
                }

                @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
                public final String getName() {
                    return "isBooleanArray";
                }

                @Override // kotlin.jvm.internal.CallableReference
                public final KDeclarationContainer getOwner() {
                    return Reflection.getOrCreateKotlinPackage(IrTypePredicatesKt.class, "backend.js");
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(IrType p0) {
                    Intrinsics.checkNotNullParameter(p0, "p0");
                    return Boolean.valueOf(IrTypePredicatesKt.isBooleanArray(p0));
                }
            }

            /* JADX INFO: renamed from: org.jetbrains.kotlin.ir.backend.js.lower.ClassReferenceLowering$finalPrimitiveClasses$2$10, reason: invalid class name */
            /* JADX INFO: compiled from: ClassReferenceLowering.kt */
            @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
            /* synthetic */ class AnonymousClass10 extends FunctionReference implements Function1<IrType, Boolean> {
                public static final AnonymousClass10 INSTANCE = new AnonymousClass10();

                AnonymousClass10() {
                    super(1);
                }

                @Override // kotlin.jvm.internal.CallableReference
                public final String getSignature() {
                    return "isCharArray(Lorg/jetbrains/kotlin/ir/types/IrType;)Z";
                }

                @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
                public final String getName() {
                    return "isCharArray";
                }

                @Override // kotlin.jvm.internal.CallableReference
                public final KDeclarationContainer getOwner() {
                    return Reflection.getOrCreateKotlinPackage(IrTypePredicatesKt.class, "backend.js");
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(IrType p0) {
                    Intrinsics.checkNotNullParameter(p0, "p0");
                    return Boolean.valueOf(IrTypePredicatesKt.isCharArray(p0));
                }
            }

            /* JADX INFO: renamed from: org.jetbrains.kotlin.ir.backend.js.lower.ClassReferenceLowering$finalPrimitiveClasses$2$11, reason: invalid class name */
            /* JADX INFO: compiled from: ClassReferenceLowering.kt */
            @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
            /* synthetic */ class AnonymousClass11 extends FunctionReference implements Function1<IrType, Boolean> {
                public static final AnonymousClass11 INSTANCE = new AnonymousClass11();

                AnonymousClass11() {
                    super(1);
                }

                @Override // kotlin.jvm.internal.CallableReference
                public final String getSignature() {
                    return "isByteArray(Lorg/jetbrains/kotlin/ir/types/IrType;)Z";
                }

                @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
                public final String getName() {
                    return "isByteArray";
                }

                @Override // kotlin.jvm.internal.CallableReference
                public final KDeclarationContainer getOwner() {
                    return Reflection.getOrCreateKotlinPackage(IrTypePredicatesKt.class, "backend.js");
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(IrType p0) {
                    Intrinsics.checkNotNullParameter(p0, "p0");
                    return Boolean.valueOf(IrTypePredicatesKt.isByteArray(p0));
                }
            }

            /* JADX INFO: renamed from: org.jetbrains.kotlin.ir.backend.js.lower.ClassReferenceLowering$finalPrimitiveClasses$2$12, reason: invalid class name */
            /* JADX INFO: compiled from: ClassReferenceLowering.kt */
            @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
            /* synthetic */ class AnonymousClass12 extends FunctionReference implements Function1<IrType, Boolean> {
                public static final AnonymousClass12 INSTANCE = new AnonymousClass12();

                AnonymousClass12() {
                    super(1);
                }

                @Override // kotlin.jvm.internal.CallableReference
                public final String getSignature() {
                    return "isShortArray(Lorg/jetbrains/kotlin/ir/types/IrType;)Z";
                }

                @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
                public final String getName() {
                    return "isShortArray";
                }

                @Override // kotlin.jvm.internal.CallableReference
                public final KDeclarationContainer getOwner() {
                    return Reflection.getOrCreateKotlinPackage(IrTypePredicatesKt.class, "backend.js");
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(IrType p0) {
                    Intrinsics.checkNotNullParameter(p0, "p0");
                    return Boolean.valueOf(IrTypePredicatesKt.isShortArray(p0));
                }
            }

            /* JADX INFO: renamed from: org.jetbrains.kotlin.ir.backend.js.lower.ClassReferenceLowering$finalPrimitiveClasses$2$13, reason: invalid class name */
            /* JADX INFO: compiled from: ClassReferenceLowering.kt */
            @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
            /* synthetic */ class AnonymousClass13 extends FunctionReference implements Function1<IrType, Boolean> {
                public static final AnonymousClass13 INSTANCE = new AnonymousClass13();

                AnonymousClass13() {
                    super(1);
                }

                @Override // kotlin.jvm.internal.CallableReference
                public final String getSignature() {
                    return "isIntArray(Lorg/jetbrains/kotlin/ir/types/IrType;)Z";
                }

                @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
                public final String getName() {
                    return "isIntArray";
                }

                @Override // kotlin.jvm.internal.CallableReference
                public final KDeclarationContainer getOwner() {
                    return Reflection.getOrCreateKotlinPackage(IrTypePredicatesKt.class, "backend.js");
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(IrType p0) {
                    Intrinsics.checkNotNullParameter(p0, "p0");
                    return Boolean.valueOf(IrTypePredicatesKt.isIntArray(p0));
                }
            }

            /* JADX INFO: renamed from: org.jetbrains.kotlin.ir.backend.js.lower.ClassReferenceLowering$finalPrimitiveClasses$2$14, reason: invalid class name */
            /* JADX INFO: compiled from: ClassReferenceLowering.kt */
            @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
            /* synthetic */ class AnonymousClass14 extends FunctionReference implements Function1<IrType, Boolean> {
                public static final AnonymousClass14 INSTANCE = new AnonymousClass14();

                AnonymousClass14() {
                    super(1);
                }

                @Override // kotlin.jvm.internal.CallableReference
                public final String getSignature() {
                    return "isLongArray(Lorg/jetbrains/kotlin/ir/types/IrType;)Z";
                }

                @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
                public final String getName() {
                    return "isLongArray";
                }

                @Override // kotlin.jvm.internal.CallableReference
                public final KDeclarationContainer getOwner() {
                    return Reflection.getOrCreateKotlinPackage(IrTypePredicatesKt.class, "backend.js");
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(IrType p0) {
                    Intrinsics.checkNotNullParameter(p0, "p0");
                    return Boolean.valueOf(IrTypePredicatesKt.isLongArray(p0));
                }
            }

            /* JADX INFO: renamed from: org.jetbrains.kotlin.ir.backend.js.lower.ClassReferenceLowering$finalPrimitiveClasses$2$15, reason: invalid class name */
            /* JADX INFO: compiled from: ClassReferenceLowering.kt */
            @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
            /* synthetic */ class AnonymousClass15 extends FunctionReference implements Function1<IrType, Boolean> {
                public static final AnonymousClass15 INSTANCE = new AnonymousClass15();

                AnonymousClass15() {
                    super(1);
                }

                @Override // kotlin.jvm.internal.CallableReference
                public final String getSignature() {
                    return "isFloatArray(Lorg/jetbrains/kotlin/ir/types/IrType;)Z";
                }

                @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
                public final String getName() {
                    return "isFloatArray";
                }

                @Override // kotlin.jvm.internal.CallableReference
                public final KDeclarationContainer getOwner() {
                    return Reflection.getOrCreateKotlinPackage(IrTypePredicatesKt.class, "backend.js");
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(IrType p0) {
                    Intrinsics.checkNotNullParameter(p0, "p0");
                    return Boolean.valueOf(IrTypePredicatesKt.isFloatArray(p0));
                }
            }

            /* JADX INFO: renamed from: org.jetbrains.kotlin.ir.backend.js.lower.ClassReferenceLowering$finalPrimitiveClasses$2$16, reason: invalid class name */
            /* JADX INFO: compiled from: ClassReferenceLowering.kt */
            @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
            /* synthetic */ class AnonymousClass16 extends FunctionReference implements Function1<IrType, Boolean> {
                public static final AnonymousClass16 INSTANCE = new AnonymousClass16();

                AnonymousClass16() {
                    super(1);
                }

                @Override // kotlin.jvm.internal.CallableReference
                public final String getSignature() {
                    return "isDoubleArray(Lorg/jetbrains/kotlin/ir/types/IrType;)Z";
                }

                @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
                public final String getName() {
                    return "isDoubleArray";
                }

                @Override // kotlin.jvm.internal.CallableReference
                public final KDeclarationContainer getOwner() {
                    return Reflection.getOrCreateKotlinPackage(IrTypePredicatesKt.class, "backend.js");
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(IrType p0) {
                    Intrinsics.checkNotNullParameter(p0, "p0");
                    return Boolean.valueOf(IrTypePredicatesKt.isDoubleArray(p0));
                }
            }
        });
        this.openPrimitiveClasses = LazyKt.lazy(LazyThreadSafetyMode.NONE, (Function0) new Function0<Map<KFunction<? extends Boolean>, ? extends IrSimpleFunction>>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.ClassReferenceLowering$openPrimitiveClasses$2
            {
                super(0);
            }

            /* JADX INFO: renamed from: org.jetbrains.kotlin.ir.backend.js.lower.ClassReferenceLowering$openPrimitiveClasses$2$1, reason: invalid class name */
            /* JADX INFO: compiled from: ClassReferenceLowering.kt */
            @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
            /* synthetic */ class AnonymousClass1 extends FunctionReference implements Function1<IrType, Boolean> {
                public static final AnonymousClass1 INSTANCE = new AnonymousClass1();

                AnonymousClass1() {
                    super(1);
                }

                @Override // kotlin.jvm.internal.CallableReference
                public final String getSignature() {
                    return "isAny(Lorg/jetbrains/kotlin/ir/types/IrType;)Z";
                }

                @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
                public final String getName() {
                    return "isAny";
                }

                @Override // kotlin.jvm.internal.CallableReference
                public final KDeclarationContainer getOwner() {
                    return Reflection.getOrCreateKotlinPackage(IrTypePredicatesKt.class, "backend.js");
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(IrType p0) {
                    Intrinsics.checkNotNullParameter(p0, "p0");
                    return Boolean.valueOf(IrTypePredicatesKt.isAny(p0));
                }
            }

            /* JADX INFO: renamed from: org.jetbrains.kotlin.ir.backend.js.lower.ClassReferenceLowering$openPrimitiveClasses$2$2, reason: invalid class name */
            /* JADX INFO: compiled from: ClassReferenceLowering.kt */
            @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
            /* synthetic */ class AnonymousClass2 extends FunctionReference implements Function1<IrType, Boolean> {
                public static final AnonymousClass2 INSTANCE = new AnonymousClass2();

                AnonymousClass2() {
                    super(1);
                }

                @Override // kotlin.jvm.internal.CallableReference
                public final String getSignature() {
                    return "isNumber(Lorg/jetbrains/kotlin/ir/types/IrType;)Z";
                }

                @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
                public final String getName() {
                    return Namer.IS_NUMBER;
                }

                @Override // kotlin.jvm.internal.CallableReference
                public final KDeclarationContainer getOwner() {
                    return Reflection.getOrCreateKotlinPackage(IrTypePredicatesKt.class, "backend.js");
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(IrType p0) {
                    Intrinsics.checkNotNullParameter(p0, "p0");
                    return Boolean.valueOf(IrTypePredicatesKt.isNumber(p0));
                }
            }

            @Override // kotlin.jvm.functions.Function0
            public final Map<KFunction<? extends Boolean>, ? extends IrSimpleFunction> invoke() {
                Map mapMapOf = MapsKt.mapOf(TuplesKt.to(AnonymousClass1.INSTANCE, "anyClass"), TuplesKt.to(AnonymousClass2.INSTANCE, "numberClass"), TuplesKt.to(AnonymousClass3.INSTANCE, "throwableClass"), TuplesKt.to(AnonymousClass4.INSTANCE, "nothingClass"));
                ClassReferenceLowering classReferenceLowering = this.this$0;
                LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt.mapCapacity(mapMapOf.size()));
                for (Map.Entry entry : mapMapOf.entrySet()) {
                    linkedHashMap.put(entry.getKey(), classReferenceLowering.primitiveClassProperty((String) entry.getValue()));
                }
                return linkedHashMap;
            }

            /* JADX INFO: renamed from: org.jetbrains.kotlin.ir.backend.js.lower.ClassReferenceLowering$openPrimitiveClasses$2$3, reason: invalid class name */
            /* JADX INFO: compiled from: ClassReferenceLowering.kt */
            @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
            /* synthetic */ class AnonymousClass3 extends FunctionReference implements Function1<IrType, Boolean> {
                public static final AnonymousClass3 INSTANCE = new AnonymousClass3();

                AnonymousClass3() {
                    super(1);
                }

                @Override // kotlin.jvm.internal.CallableReference
                public final String getSignature() {
                    return "isThrowable(Lorg/jetbrains/kotlin/ir/types/IrType;)Z";
                }

                @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
                public final String getName() {
                    return "isThrowable";
                }

                @Override // kotlin.jvm.internal.CallableReference
                public final KDeclarationContainer getOwner() {
                    return Reflection.getOrCreateKotlinPackage(IrTypeUtilsKt.class, "backend.js");
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(IrType p0) {
                    Intrinsics.checkNotNullParameter(p0, "p0");
                    return Boolean.valueOf(IrTypeUtilsKt.isThrowable(p0));
                }
            }

            /* JADX INFO: renamed from: org.jetbrains.kotlin.ir.backend.js.lower.ClassReferenceLowering$openPrimitiveClasses$2$4, reason: invalid class name */
            /* JADX INFO: compiled from: ClassReferenceLowering.kt */
            @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
            /* synthetic */ class AnonymousClass4 extends FunctionReference implements Function1<IrType, Boolean> {
                public static final AnonymousClass4 INSTANCE = new AnonymousClass4();

                AnonymousClass4() {
                    super(1);
                }

                @Override // kotlin.jvm.internal.CallableReference
                public final String getSignature() {
                    return "isNothing(Lorg/jetbrains/kotlin/ir/types/IrType;)Z";
                }

                @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
                public final String getName() {
                    return "isNothing";
                }

                @Override // kotlin.jvm.internal.CallableReference
                public final KDeclarationContainer getOwner() {
                    return Reflection.getOrCreateKotlinPackage(IrTypePredicatesKt.class, "backend.js");
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(IrType p0) {
                    Intrinsics.checkNotNullParameter(p0, "p0");
                    return Boolean.valueOf(IrTypePredicatesKt.isNothing(p0));
                }
            }
        });
    }

    public final JsCommonBackendContext getContext() {
        return this.context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final ReflectionSymbols getReflectionSymbols() {
        return this.context.getReflectionSymbols();
    }

    private final List<IrProperty> getPrimitiveClassProperties() {
        return (List) this.primitiveClassProperties.getValue();
    }

    private final IrSimpleFunction getPrimitiveClassFunctionClass() {
        return (IrSimpleFunction) this.primitiveClassFunctionClass.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrSimpleFunction primitiveClassProperty(String name2) {
        IrSimpleFunction getter;
        Iterator<T> it = getPrimitiveClassProperties().iterator();
        Object obj = null;
        boolean z = false;
        Object obj2 = null;
        boolean z2 = false;
        while (true) {
            if (!it.hasNext()) {
                if (!z2) {
                    break;
                }
                break;
            }
            Object next = it.next();
            if (Intrinsics.areEqual(((IrProperty) next).getName(), Name.identifier(name2))) {
                if (!z2) {
                    obj2 = next;
                    z2 = true;
                }
            }
            obj2 = null;
            break;
        }
        IrProperty irProperty = (IrProperty) obj2;
        if (irProperty != null && (getter = irProperty.getGetter()) != null) {
            return getter;
        }
        List<IrDeclaration> declarations = getReflectionSymbols().getPrimitiveClassesObject().getOwner().getDeclarations();
        ArrayList arrayList = new ArrayList();
        for (Object obj3 : declarations) {
            if (obj3 instanceof IrSimpleFunction) {
                arrayList.add(obj3);
            }
        }
        for (Object obj4 : arrayList) {
            if (Intrinsics.areEqual(((IrSimpleFunction) obj4).getName(), Name.special("<get-" + name2 + '>'))) {
                if (z) {
                    throw new IllegalArgumentException("Collection contains more than one matching element.");
                }
                obj = obj4;
                z = true;
            }
        }
        if (!z) {
            throw new NoSuchElementException("Collection contains no element matching the predicate.");
        }
        return (IrSimpleFunction) obj;
    }

    private final Map<KFunction<Boolean>, IrSimpleFunction> getFinalPrimitiveClasses() {
        return (Map) this.finalPrimitiveClasses.getValue();
    }

    private final Map<KFunction<Boolean>, IrSimpleFunction> getOpenPrimitiveClasses() {
        return (Map) this.openPrimitiveClasses.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrExpression callGetKClassFromExpression(IrType returnType, IrType typeArgument, IrExpression argument) {
        IrCall finalPrimitiveKClass = getFinalPrimitiveKClass(returnType, typeArgument);
        if (finalPrimitiveKClass != null) {
            return JsIrBuilder.INSTANCE.buildBlock(returnType, CollectionsKt.listOf((Object[]) new IrExpression[]{argument, finalPrimitiveKClass}));
        }
        IrCall irCallBuildCall$default = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, getReflectionSymbols().getGetKClassFromExpression(), returnType, CollectionsKt.listOf(typeArgument), null, null, 24, null);
        irCallBuildCall$default.putValueArgument(0, argument);
        return irCallBuildCall$default;
    }

    private final IrCall getPrimitiveClass(IrSimpleFunction target, IrType returnType) {
        IrCall irCallBuildCall$default = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, target.getSymbol(), returnType, null, null, null, 28, null);
        irCallBuildCall$default.setDispatchReceiver(JsIrBuilder.INSTANCE.buildGetObjectValue(IrTypesKt.getDefaultType(getReflectionSymbols().getPrimitiveClassesObject()), getReflectionSymbols().getPrimitiveClassesObject()));
        return irCallBuildCall$default;
    }

    private final IrCall getFinalPrimitiveKClass(IrType returnType, IrType typeArgument) {
        for (Map.Entry<KFunction<Boolean>, IrSimpleFunction> entry : getFinalPrimitiveClasses().entrySet()) {
            KFunction<Boolean> key = entry.getKey();
            IrSimpleFunction value = entry.getValue();
            if (((Boolean) ((Function1) key).invoke(typeArgument)).booleanValue()) {
                return getPrimitiveClass(value, returnType);
            }
        }
        return null;
    }

    private final IrCall getOpenPrimitiveKClass(IrType returnType, IrType typeArgument) {
        for (Map.Entry<KFunction<Boolean>, IrSimpleFunction> entry : getOpenPrimitiveClasses().entrySet()) {
            KFunction<Boolean> key = entry.getKey();
            IrSimpleFunction value = entry.getValue();
            if (((Boolean) ((Function1) key).invoke(typeArgument)).booleanValue()) {
                return getPrimitiveClass(value, returnType);
            }
        }
        if (!IrTypeUtilsKt.isFunction(typeArgument)) {
            return null;
        }
        IrClass irClass = IrTypesKt.getClass(typeArgument);
        Intrinsics.checkNotNull(irClass);
        int size = irClass.getTypeParameters().size() - 1;
        IrCall primitiveClass = getPrimitiveClass(getPrimitiveClassFunctionClass(), returnType);
        primitiveClass.putValueArgument(0, JsIrBuilder.INSTANCE.buildInt(this.context.getIrBuiltIns().getIntType(), size));
        return primitiveClass;
    }

    static /* synthetic */ IrCall callGetKClass$default(ClassReferenceLowering classReferenceLowering, IrType irType, IrType irType2, int i, Object obj) {
        if ((i & 1) != 0) {
            irType = classReferenceLowering.getReflectionSymbols().getGetKClass().getOwner().getReturnType();
        }
        return classReferenceLowering.callGetKClass(irType, irType2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrCall callGetKClass(IrType returnType, IrType typeArgument) {
        IrCall finalPrimitiveKClass = getFinalPrimitiveKClass(returnType, typeArgument);
        if (finalPrimitiveKClass == null) {
            finalPrimitiveKClass = getOpenPrimitiveKClass(returnType, typeArgument);
        }
        if (finalPrimitiveKClass != null) {
            return finalPrimitiveKClass;
        }
        IrCall irCallBuildCall$default = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, getReflectionSymbols().getGetKClass(), returnType, CollectionsKt.listOf(typeArgument), null, null, 24, null);
        irCallBuildCall$default.putValueArgument(0, callGetClassByType(typeArgument));
        return irCallBuildCall$default;
    }

    private final IrCall callGetClassByType(IrType type) {
        return JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, getReflectionSymbols().getGetClassData(), null, CollectionsKt.listOf(type), JsStatementOrigins.CLASS_REFERENCE.INSTANCE, null, 18, null);
    }

    private final IrExpression buildCall(IrSimpleFunctionSymbol name2, IrExpression... args) {
        IrCall irCallBuildCall$default = JsIrBuilder.buildCall$default(JsIrBuilder.INSTANCE, name2, null, null, null, null, 30, null);
        int length = args.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            irCallBuildCall$default.putValueArgument(i2, args[i]);
            i++;
            i2++;
        }
        return irCallBuildCall$default;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final IrExpression createKType(IrType type, Set<IrTypeParameter> visitedTypeParams) {
        if (type instanceof IrSimpleType) {
            return createSimpleKType((IrSimpleType) type, visitedTypeParams);
        }
        if (type instanceof IrDynamicType) {
            return createDynamicType();
        }
        CompilationExceptionKt.compilationException("Unexpected type", type);
        throw null;
    }

    private final IrExpression createDynamicType() {
        IrSimpleFunctionSymbol createDynamicKType = getReflectionSymbols().getCreateDynamicKType();
        Intrinsics.checkNotNull(createDynamicKType);
        return buildCall(createDynamicKType, new IrExpression[0]);
    }

    private final IrExpression createSimpleKType(IrSimpleType type, Set<IrTypeParameter> visitedTypeParams) {
        IrExpression irExpressionCreateKClassifier = createKClassifier(type.getClassifier(), visitedTypeParams);
        JsCommonBackendContext jsCommonBackendContext = this.context;
        JsCommonBackendContext jsCommonBackendContext2 = jsCommonBackendContext;
        IrType defaultType = IrTypesKt.getDefaultType(jsCommonBackendContext.getReflectionSymbols().getKTypeClass());
        List<IrTypeArgument> arguments = type.getArguments();
        ArrayList arrayList = new ArrayList(arguments.size());
        Iterator<T> it = arguments.iterator();
        while (it.hasNext()) {
            arrayList.add(createKTypeProjection((IrTypeArgument) it.next(), visitedTypeParams));
        }
        IrExpression irExpressionCreateArrayOfExpression = IrUtilsKt.createArrayOfExpression(jsCommonBackendContext2, -1, -1, defaultType, org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList));
        IrConstImpl<Boolean> irConstImplBuildBoolean = JsIrBuilder.INSTANCE.buildBoolean(this.context.getIrBuiltIns().getBooleanType(), IrTypePredicatesKt.isMarkedNullable(type));
        IrSimpleFunctionSymbol createKType = getReflectionSymbols().getCreateKType();
        Intrinsics.checkNotNull(createKType);
        return buildCall(createKType, irExpressionCreateKClassifier, irExpressionCreateArrayOfExpression, irConstImplBuildBoolean);
    }

    private final IrExpression createKTypeProjection(IrTypeArgument tp, Set<IrTypeParameter> visitedTypeParams) {
        IrSimpleFunctionSymbol createInvariantKTypeProjection;
        if (!(tp instanceof IrTypeProjection)) {
            IrSimpleFunctionSymbol getStarKTypeProjection = getReflectionSymbols().getGetStarKTypeProjection();
            Intrinsics.checkNotNull(getStarKTypeProjection);
            return buildCall(getStarKTypeProjection, new IrExpression[0]);
        }
        IrTypeProjection irTypeProjection = (IrTypeProjection) tp;
        int i = WhenMappings.$EnumSwitchMapping$0[irTypeProjection.getVariance().ordinal()];
        if (i == 1) {
            createInvariantKTypeProjection = getReflectionSymbols().getCreateInvariantKTypeProjection();
            Intrinsics.checkNotNull(createInvariantKTypeProjection);
        } else if (i == 2) {
            createInvariantKTypeProjection = getReflectionSymbols().getCreateContravariantKTypeProjection();
            Intrinsics.checkNotNull(createInvariantKTypeProjection);
        } else {
            if (i != 3) {
                throw new NoWhenBranchMatchedException();
            }
            createInvariantKTypeProjection = getReflectionSymbols().getCreateCovariantKTypeProjection();
            Intrinsics.checkNotNull(createInvariantKTypeProjection);
        }
        return buildCall(createInvariantKTypeProjection, createKType(irTypeProjection.getType(), visitedTypeParams));
    }

    private final IrExpression createKClassifier(IrClassifierSymbol classifier, Set<IrTypeParameter> visitedTypeParams) {
        return classifier instanceof IrTypeParameterSymbol ? createKTypeParameter(((IrTypeParameterSymbol) classifier).getOwner(), visitedTypeParams) : callGetKClass$default(this, null, IrTypesKt.getDefaultType(classifier), 1, null);
    }

    private final IrExpression createKTypeParameter(IrTypeParameter typeParameter, Set<IrTypeParameter> visitedTypeParams) {
        IrConstImpl<String> irConstImplBuildString;
        if (visitedTypeParams.contains(typeParameter)) {
            throw new NotImplementedError("An operation is not implemented: Non-reified type parameters with recursive bounds are not supported yet");
        }
        visitedTypeParams.add(typeParameter);
        JsIrBuilder jsIrBuilder = JsIrBuilder.INSTANCE;
        IrType stringType = this.context.getIrBuiltIns().getStringType();
        String strAsString = typeParameter.getName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        IrConstImpl<String> irConstImplBuildString2 = jsIrBuilder.buildString(stringType, strAsString);
        JsCommonBackendContext jsCommonBackendContext = this.context;
        JsCommonBackendContext jsCommonBackendContext2 = jsCommonBackendContext;
        IrType defaultType = IrTypesKt.getDefaultType(jsCommonBackendContext.getReflectionSymbols().getKTypeClass());
        List<IrType> superTypes = typeParameter.getSuperTypes();
        ArrayList arrayList = new ArrayList(superTypes.size());
        Iterator<T> it = superTypes.iterator();
        while (it.hasNext()) {
            arrayList.add(createKType((IrType) it.next(), visitedTypeParams));
        }
        IrExpression irExpressionCreateArrayOfExpression = IrUtilsKt.createArrayOfExpression(jsCommonBackendContext2, -1, -1, defaultType, org.jetbrains.kotlin.utils.CollectionsKt.compactIfPossible(arrayList));
        int i = WhenMappings.$EnumSwitchMapping$0[typeParameter.getVariance().ordinal()];
        if (i == 1) {
            irConstImplBuildString = JsIrBuilder.INSTANCE.buildString(this.context.getIrBuiltIns().getStringType(), "invariant");
        } else if (i == 2) {
            irConstImplBuildString = JsIrBuilder.INSTANCE.buildString(this.context.getIrBuiltIns().getStringType(), "in");
        } else {
            if (i != 3) {
                throw new NoWhenBranchMatchedException();
            }
            irConstImplBuildString = JsIrBuilder.INSTANCE.buildString(this.context.getIrBuiltIns().getStringType(), "out");
        }
        IrSimpleFunctionSymbol createKTypeParameter = getReflectionSymbols().getCreateKTypeParameter();
        Intrinsics.checkNotNull(createKTypeParameter);
        IrExpression irExpressionBuildCall = buildCall(createKTypeParameter, irConstImplBuildString2, irExpressionCreateArrayOfExpression, irConstImplBuildString);
        visitedTypeParams.remove(typeParameter);
        return irExpressionBuildCall;
    }

    @Override // org.jetbrains.kotlin.backend.common.BodyLoweringPass
    public void lower(IrBody irBody, IrDeclaration container) {
        Intrinsics.checkNotNullParameter(irBody, "irBody");
        Intrinsics.checkNotNullParameter(container, "container");
        IrElementTransformerVoidKt.transformChildrenVoid(irBody, new IrElementTransformerVoidWithContext() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.ClassReferenceLowering.lower.1
            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitGetClass(IrGetClass expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                return ClassReferenceLowering.this.callGetKClassFromExpression(expression.getType(), expression.getArgument().getType(), expression.getArgument().transform((IrElementTransformer<? super Object>) this, (Object) null));
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrCall visitClassReference(IrClassReference expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                return ClassReferenceLowering.this.callGetKClass(expression.getType(), IrTypesKt.makeNotNull(expression.getClassType()));
            }

            @Override // org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
            public IrExpression visitCall(IrCall expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                if (Symbols.INSTANCE.isTypeOfIntrinsic(expression.getSymbol())) {
                    ClassReferenceLowering classReferenceLowering = ClassReferenceLowering.this;
                    IrType typeArgument = expression.getTypeArgument(0);
                    Intrinsics.checkNotNull(typeArgument);
                    return classReferenceLowering.createKType(typeArgument, new HashSet());
                }
                return super.visitCall(expression);
            }
        });
    }
}
