package org.jetbrains.kotlin.backend.jvm.codegen;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.JvmLoweredDeclarationOrigin;
import org.jetbrains.kotlin.backend.jvm.JvmSymbols;
import org.jetbrains.kotlin.backend.jvm.ir.IrJvmFlexibleTypeKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrTypeUtilsKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.backend.jvm.mapping.IrTypeMapper;
import org.jetbrains.kotlin.backend.jvm.mapping.IrTypeMappingKt;
import org.jetbrains.kotlin.backend.jvm.mapping.MethodSignatureMapper;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.TypeAnnotationCollector;
import org.jetbrains.kotlin.codegen.TypePathInfo;
import org.jetbrains.kotlin.config.JVMConfigurationKeys;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.annotations.KotlinRetention;
import org.jetbrains.kotlin.descriptors.annotations.KotlinTarget;
import org.jetbrains.kotlin.ir.declarations.IrAnnotationContainer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrEnumEntry;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.declarations.IrTypeParametersContainer;
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrClassReference;
import org.jetbrains.kotlin.ir.expressions.IrConst;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrDeclarationReference;
import org.jetbrains.kotlin.ir.expressions.IrErrorExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrGetEnumValue;
import org.jetbrains.kotlin.ir.expressions.IrVararg;
import org.jetbrains.kotlin.ir.expressions.IrVarargElement;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrEnumEntrySymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.TypeSystemCommonBackendContext;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.org.objectweb.asm.AnnotationVisitor;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.TypePath;
import org.jetbrains.org.objectweb.asm.TypeReference;

/* JADX INFO: compiled from: AnnotationCodegen.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0094\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010#\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\b&\u0018\u0000 ;2\u00020\u0001:\u0002;<B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J$\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0013\u001a\u00020\u0005H\u0002J\u0018\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J$\u0010\u0018\u001a\u00020\u00152\b\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eJ\"\u0010\u001f\u001a\u00020\u00152\b\u0010 \u001a\u0004\u0018\u00010\u000e2\u0006\u0010!\u001a\u00020\"2\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J\u000e\u0010#\u001a\u00020\u00152\u0006\u0010!\u001a\u00020\"J\"\u0010$\u001a\u00020\u00152\f\u0010%\u001a\b\u0012\u0004\u0012\u00020\u000e0&2\n\u0010'\u001a\u0006\u0012\u0002\b\u00030(H\u0002J\u001e\u0010)\u001a\u00020\u00152\u0006\u0010*\u001a\u00020+2\f\u0010%\u001a\b\u0012\u0004\u0012\u00020\u000e0&H\u0002J\u001f\u0010,\u001a\u00020\u00152\u0006\u0010\u0019\u001a\u00020\u001a2\b\u0010-\u001a\u0004\u0018\u00010\u001eH\u0000¢\u0006\u0002\b.J\u001a\u0010/\u001a\u00020\u000e2\b\u0010'\u001a\u0004\u0018\u0001002\u0006\u00101\u001a\u000202H\u0002J\u0018\u00103\u001a\u00020\u00052\u0006\u00104\u001a\u0002052\u0006\u00106\u001a\u00020+H\u0002J\u0018\u00107\u001a\u00020\u00172\u0006\u00108\u001a\u00020\u000e2\u0006\u00109\u001a\u00020\u0005H&J\"\u0010:\u001a\u00020\u00172\u0006\u00108\u001a\u00020\u000e2\b\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u00109\u001a\u00020\u0005H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006="}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/codegen/AnnotationCodegen;", "", "classCodegen", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ClassCodegen;", "skipNullabilityAnnotations", "", "(Lorg/jetbrains/kotlin/backend/jvm/codegen/ClassCodegen;Z)V", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "methodSignatureMapper", "Lorg/jetbrains/kotlin/backend/jvm/mapping/MethodSignatureMapper;", "typeMapper", "Lorg/jetbrains/kotlin/backend/jvm/mapping/IrTypeMapper;", "genAnnotation", "", "annotation", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "path", "Lorg/jetbrains/org/objectweb/asm/TypePath;", "isTypeAnnotation", "genAnnotationArguments", "", "annotationVisitor", "Lorg/jetbrains/org/objectweb/asm/AnnotationVisitor;", "genAnnotations", "annotated", "Lorg/jetbrains/kotlin/ir/declarations/IrAnnotationContainer;", "returnType", "Lorg/jetbrains/org/objectweb/asm/Type;", "typeForTypeAnnotations", "Lorg/jetbrains/kotlin/ir/types/IrType;", "genCompileTimeValue", "name", "value", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "generateAnnotationDefaultValue", "generateAnnotationIfNotPresent", "annotationDescriptorsAlreadyPresent", "", "annotationClass", "Ljava/lang/Class;", "generateNullabilityAnnotationForCallable", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "generateTypeAnnotations", ModuleXmlParser.TYPE, "generateTypeAnnotations$backend_jvm_codegen", "getAnnotationArgumentJvmName", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "parameterName", "Lorg/jetbrains/kotlin/name/Name;", "isMovedReceiverParameterOfStaticValueClassReplacement", "parameter", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "parent", "visitAnnotation", "descr", "visible", "visitTypeAnnotation", "Companion", "IrTypeAnnotationCollector", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AnnotationCodegen {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final Map<KotlinRetention, RetentionPolicy> annotationRetentionMap = MapsKt.mapOf(TuplesKt.to(KotlinRetention.SOURCE, RetentionPolicy.SOURCE), TuplesKt.to(KotlinRetention.BINARY, RetentionPolicy.CLASS), TuplesKt.to(KotlinRetention.RUNTIME, RetentionPolicy.RUNTIME));
    private static final Set<FqName> internalAnnotations = SetsKt.setOf((Object[]) new FqName[]{JvmSymbols.INSTANCE.getFLEXIBLE_NULLABILITY_ANNOTATION_FQ_NAME(), JvmSymbols.INSTANCE.getFLEXIBLE_MUTABILITY_ANNOTATION_FQ_NAME(), JvmAnnotationNames.ENHANCED_NULLABILITY_ANNOTATION, JvmSymbols.INSTANCE.getRAW_TYPE_ANNOTATION_FQ_NAME()});
    private final ClassCodegen classCodegen;
    private final JvmBackendContext context;
    private final MethodSignatureMapper methodSignatureMapper;
    private final boolean skipNullabilityAnnotations;
    private final IrTypeMapper typeMapper;

    public abstract AnnotationVisitor visitAnnotation(String descr, boolean visible);

    public AnnotationCodegen(ClassCodegen classCodegen, boolean z) {
        Intrinsics.checkNotNullParameter(classCodegen, "classCodegen");
        this.classCodegen = classCodegen;
        this.skipNullabilityAnnotations = z;
        this.context = classCodegen.getContext();
        this.typeMapper = classCodegen.getTypeMapper();
        this.methodSignatureMapper = classCodegen.getMethodSignatureMapper();
    }

    public /* synthetic */ AnnotationCodegen(ClassCodegen classCodegen, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(classCodegen, (i & 2) != 0 ? false : z);
    }

    public final void genAnnotations(IrAnnotationContainer annotated, Type returnType, IrType typeForTypeAnnotations) {
        if (annotated == null) {
            return;
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (IrConstructorCall irConstructorCall : annotated.getAnnotations()) {
            Set<KotlinTarget> setApplicableTargetSet = INSTANCE.applicableTargetSet(irConstructorCall);
            if ((annotated instanceof IrSimpleFunction) && ((IrSimpleFunction) annotated).getOrigin() == IrDeclarationOrigin.LOCAL_FUNCTION_FOR_LAMBDA.INSTANCE && !setApplicableTargetSet.contains(KotlinTarget.FUNCTION) && !setApplicableTargetSet.contains(KotlinTarget.PROPERTY_GETTER) && !setApplicableTargetSet.contains(KotlinTarget.PROPERTY_SETTER)) {
                setApplicableTargetSet.contains(KotlinTarget.EXPRESSION);
            } else if ((annotated instanceof IrClass) && !setApplicableTargetSet.contains(KotlinTarget.CLASS) && !setApplicableTargetSet.contains(KotlinTarget.ANNOTATION_CLASS) && Intrinsics.areEqual(((IrClass) annotated).getVisibility(), DescriptorVisibilities.LOCAL)) {
                setApplicableTargetSet.contains(KotlinTarget.EXPRESSION);
            } else {
                String strGenAnnotation = genAnnotation(irConstructorCall, null, false);
                if (strGenAnnotation != null) {
                    linkedHashSet.add(strGenAnnotation);
                }
            }
        }
        if (!this.skipNullabilityAnnotations && (annotated instanceof IrDeclaration) && returnType != null && !AsmUtil.isPrimitive(returnType)) {
            generateNullabilityAnnotationForCallable((IrDeclaration) annotated, linkedHashSet);
        }
        generateTypeAnnotations$backend_jvm_codegen(annotated, typeForTypeAnnotations);
    }

    public AnnotationVisitor visitTypeAnnotation(String descr, TypePath path, boolean visible) {
        Intrinsics.checkNotNullParameter(descr, "descr");
        throw new RuntimeException("Not implemented");
    }

    private final void generateNullabilityAnnotationForCallable(IrDeclaration declaration, Set<String> annotationDescriptorsAlreadyPresent) {
        IrType type;
        IrProperty owner;
        Companion companion = INSTANCE;
        if (companion.isInvisibleForNullabilityAnalysis(declaration)) {
            return;
        }
        if (declaration instanceof IrValueParameter) {
            IrDeclarationParent parent = declaration.getParent();
            Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrDeclaration");
            IrDeclaration irDeclaration = (IrDeclaration) parent;
            if (companion.isInvisibleForNullabilityAnalysis(irDeclaration) || isMovedReceiverParameterOfStaticValueClassReplacement((IrValueParameter) declaration, irDeclaration)) {
                return;
            }
        }
        if ((declaration instanceof IrSimpleFunction) && ((IrSimpleFunction) declaration).getCorrespondingPropertySymbol() != null && IrUtilsKt.isAnnotationClass(IrUtilsKt.getParentAsClass(declaration))) {
            return;
        }
        if (declaration instanceof IrFunction) {
            type = ((IrFunction) declaration).getReturnType();
        } else if (declaration instanceof IrValueDeclaration) {
            type = ((IrValueDeclaration) declaration).getType();
        } else {
            if (!(declaration instanceof IrField)) {
                return;
            }
            IrField irField = (IrField) declaration;
            IrPropertySymbol correspondingPropertySymbol = irField.getCorrespondingPropertySymbol();
            if (correspondingPropertySymbol != null && (owner = correspondingPropertySymbol.getOwner()) != null && owner.getIsLateinit()) {
                return;
            } else {
                type = irField.getType();
            }
        }
        if (AnnotationCodegenKt.isBareTypeParameterWithNullableUpperBound(type) || IrJvmFlexibleTypeKt.isWithFlexibleNullability(type)) {
            return;
        }
        generateAnnotationIfNotPresent(annotationDescriptorsAlreadyPresent, IrTypeUtilsKt.isNullable(type) ? Nullable.class : NotNull.class);
    }

    private final boolean isMovedReceiverParameterOfStaticValueClassReplacement(IrValueParameter parameter, IrDeclaration parent) {
        return (Intrinsics.areEqual(parent.getOrigin(), JvmLoweredDeclarationOrigin.STATIC_INLINE_CLASS_REPLACEMENT.INSTANCE) || Intrinsics.areEqual(parent.getOrigin(), JvmLoweredDeclarationOrigin.STATIC_MULTI_FIELD_VALUE_CLASS_REPLACEMENT.INSTANCE)) && Intrinsics.areEqual(parameter.getOrigin(), IrDeclarationOrigin.MOVED_DISPATCH_RECEIVER.INSTANCE);
    }

    private final void generateAnnotationIfNotPresent(Set<String> annotationDescriptorsAlreadyPresent, Class<?> annotationClass) {
        String descriptor = Type.getType(annotationClass).getDescriptor();
        if (annotationDescriptorsAlreadyPresent.contains(descriptor)) {
            return;
        }
        Intrinsics.checkNotNull(descriptor);
        visitAnnotation(descriptor, false).visitEnd();
    }

    public final void generateAnnotationDefaultValue(IrExpression value) {
        Intrinsics.checkNotNullParameter(value, "value");
        AnnotationVisitor annotationVisitorVisitAnnotation = visitAnnotation("", false);
        genCompileTimeValue(null, value, annotationVisitorVisitAnnotation);
        annotationVisitorVisitAnnotation.visitEnd();
    }

    private final String genAnnotation(IrConstructorCall annotation, TypePath path, boolean isTypeAnnotation) {
        AnnotationVisitor annotationVisitorVisitTypeAnnotation;
        Companion companion = INSTANCE;
        IrClass annotationClass = companion.getAnnotationClass(annotation);
        RetentionPolicy retentionPolicy = companion.getRetentionPolicy(annotationClass);
        if ((retentionPolicy == RetentionPolicy.SOURCE && !this.context.getState().getClassBuilderMode().generateSourceRetentionAnnotations) || internalAnnotations.contains(IrUtilsKt.getFqNameWhenAvailable(annotationClass)) || JvmIrUtilsKt.isOptionalAnnotationClass(annotationClass)) {
            return null;
        }
        this.classCodegen.addInnerClassInfo(annotationClass);
        String descriptor = IrTypeMapper.mapType$default(this.typeMapper, annotation.getType(), null, null, false, 14, null).getDescriptor();
        if (!isTypeAnnotation) {
            Intrinsics.checkNotNull(descriptor);
            annotationVisitorVisitTypeAnnotation = visitAnnotation(descriptor, retentionPolicy == RetentionPolicy.RUNTIME);
        } else {
            Intrinsics.checkNotNull(descriptor);
            annotationVisitorVisitTypeAnnotation = visitTypeAnnotation(descriptor, path, retentionPolicy == RetentionPolicy.RUNTIME);
        }
        genAnnotationArguments(annotation, annotationVisitorVisitTypeAnnotation);
        annotationVisitorVisitTypeAnnotation.visitEnd();
        return descriptor;
    }

    private final void genAnnotationArguments(IrConstructorCall annotation, AnnotationVisitor annotationVisitor) {
        IrClass annotationClass = INSTANCE.getAnnotationClass(annotation);
        for (IrValueParameter irValueParameter : annotation.getSymbol().getOwner().getValueParameters()) {
            IrExpression valueArgument = annotation.getValueArgument(irValueParameter.getIndex());
            if (valueArgument != null) {
                genCompileTimeValue(getAnnotationArgumentJvmName(annotationClass, irValueParameter.getName()), valueArgument, annotationVisitor);
            } else if (irValueParameter.getDefaultValue() == null && this.context.getState().getClassBuilderMode().generateBodies) {
                throw new IllegalStateException(("No value for annotation parameter " + RenderIrElementKt.render$default(irValueParameter, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:19:0x0049  */
    /* JADX WARN: Code duplicated, block: B:25:0x005c  */
    /* JADX WARN: Code duplicated, block: B:45:0x0061 A[SYNTHETIC] */
    private final String getAnnotationArgumentJvmName(IrClass annotationClass, Name parameterName) {
        IrProperty owner;
        if (annotationClass == null) {
            String strAsString = parameterName.asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            return strAsString;
        }
        Iterator<T> it = annotationClass.getDeclarations().iterator();
        boolean z = false;
        Object obj = null;
        while (true) {
            if (!it.hasNext()) {
                if (!z) {
                    break;
                }
                break;
            }
            Object next = it.next();
            IrDeclaration irDeclaration = (IrDeclaration) next;
            if (irDeclaration instanceof IrSimpleFunction) {
                IrPropertySymbol correspondingPropertySymbol = ((IrSimpleFunction) irDeclaration).getCorrespondingPropertySymbol();
                if (!Intrinsics.areEqual((correspondingPropertySymbol == null || (owner = correspondingPropertySymbol.getOwner()) == null) ? null : owner.getName(), parameterName)) {
                    if (irDeclaration instanceof IrProperty) {
                        continue;
                    }
                }
                if (z) {
                    obj = next;
                    z = true;
                }
            } else if ((irDeclaration instanceof IrProperty) && Intrinsics.areEqual(((IrProperty) irDeclaration).getName(), parameterName)) {
                if (z) {
                    obj = next;
                    z = true;
                }
            }
            obj = null;
            break;
        }
        IrDeclaration irDeclaration2 = (IrDeclaration) obj;
        if (irDeclaration2 == null) {
            String strAsString2 = parameterName.asString();
            Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
            return strAsString2;
        }
        IrSimpleFunction getter = irDeclaration2 instanceof IrSimpleFunction ? (IrSimpleFunction) irDeclaration2 : null;
        if (getter == null && (getter = ((IrProperty) irDeclaration2).getGetter()) == null) {
            throw new IllegalStateException(("No getter for annotation property: " + RenderIrElementKt.render$default(irDeclaration2, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
        }
        return MethodSignatureMapper.mapFunctionName$default(this.methodSignatureMapper, getter, false, 2, null);
    }

    private final void genCompileTimeValue(String name2, IrExpression value, AnnotationVisitor annotationVisitor) {
        IrClass owner;
        IrClass owner2;
        if (value instanceof IrConst) {
            annotationVisitor.visit(name2, ((IrConst) value).getValue());
            return;
        }
        if (value instanceof IrConstructorCall) {
            IrConstructorCall irConstructorCall = (IrConstructorCall) value;
            IrConstructor owner3 = irConstructorCall.getSymbol().getOwner();
            if (IrUtilsKt.isAnnotationClass(IrUtilsKt.getParentAsClass(owner3))) {
                IrType returnType = owner3.getReturnType();
                AnnotationVisitor annotationVisitorVisitAnnotation = annotationVisitor.visitAnnotation(name2, IrTypeMapper.mapType$default(this.typeMapper, returnType, null, null, false, 14, null).getDescriptor());
                IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(returnType);
                if (classOrNull != null && (owner2 = classOrNull.getOwner()) != null) {
                    this.classCodegen.addInnerClassInfo(owner2);
                }
                Intrinsics.checkNotNull(annotationVisitorVisitAnnotation);
                genAnnotationArguments(irConstructorCall, annotationVisitorVisitAnnotation);
                annotationVisitorVisitAnnotation.visitEnd();
                return;
            }
            throw new IllegalStateException(("Not supported as annotation! " + IrUtilsKt.ir2string(value)).toString());
        }
        if (value instanceof IrGetEnumValue) {
            IrEnumEntry owner4 = ((IrGetEnumValue) value).getSymbol().getOwner();
            IrClass parentAsClass = IrUtilsKt.getParentAsClass(owner4);
            this.classCodegen.addInnerClassInfo(parentAsClass);
            annotationVisitor.visitEnum(name2, IrTypeMappingKt.mapClass(this.typeMapper, parentAsClass).getDescriptor(), owner4.getName().asString());
            return;
        }
        if (value instanceof IrVararg) {
            AnnotationVisitor annotationVisitorVisitArray = annotationVisitor.visitArray(name2);
            for (IrVarargElement irVarargElement : ((IrVararg) value).getElements()) {
                Intrinsics.checkNotNull(irVarargElement, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.expressions.IrExpression");
                Intrinsics.checkNotNull(annotationVisitorVisitArray);
                genCompileTimeValue(null, (IrExpression) irVarargElement, annotationVisitorVisitArray);
            }
            annotationVisitorVisitArray.visitEnd();
            return;
        }
        if (value instanceof IrClassReference) {
            IrType classType = ((IrClassReference) value).getClassType();
            IrClassSymbol classOrNull2 = IrTypesKt.getClassOrNull(classType);
            if (classOrNull2 != null && (owner = classOrNull2.getOwner()) != null) {
                this.classCodegen.addInnerClassInfo(owner);
            }
            annotationVisitor.visit(name2, JvmIrTypeUtilsKt.isInlineClassType(classType) ? IrTypeMappingKt.mapClass(this.typeMapper, JvmIrTypeUtilsKt.getErasedUpperBound(classType)) : IrTypeMapper.mapType$default(this.typeMapper, classType, null, null, false, 14, null));
            return;
        }
        if (!(value instanceof IrErrorExpression)) {
            throw new IllegalStateException(("Unsupported compile-time value " + IrUtilsKt.ir2string(value)).toString());
        }
        throw new IllegalStateException(("Don't know how to compile annotation value " + IrUtilsKt.ir2string(value)).toString());
    }

    /* JADX INFO: compiled from: AnnotationCodegen.kt */
    @Metadata(d1 = {"\u0000\u0084\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0092\u0001\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001b2b\u0010\u001d\u001a^\u0012\u0013\u0012\u00110\u001b¢\u0006\f\b\u001f\u0012\b\b \u0012\u0004\b\b(!\u0012\u0015\u0012\u0013\u0018\u00010\"¢\u0006\f\b\u001f\u0012\b\b \u0012\u0004\b\b(#\u0012\u0013\u0012\u00110$¢\u0006\f\b\u001f\u0012\b\b \u0012\u0004\b\b(%\u0012\u0013\u0012\u00110&¢\u0006\f\b\u001f\u0012\b\b \u0012\u0004\b\b('\u0012\u0004\u0012\u00020(0\u001eJ\u0010\u0010)\u001a\u00020\u00062\u0006\u0010*\u001a\u00020\u000eH\u0002J\u0010\u0010+\u001a\u00020&2\u0006\u0010,\u001a\u00020-H\u0002J\u0010\u0010.\u001a\b\u0012\u0004\u0012\u00020/0\b*\u00020\u000fR\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\"\u0010\u0007\u001a\u0010\u0012\f\u0012\n \n*\u0004\u0018\u00010\t0\t0\bX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0015\u0010\r\u001a\u00020\u000e*\u00020\u000f8F¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011¨\u00060"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/codegen/AnnotationCodegen$Companion;", "", "()V", "annotationRetentionMap", "", "Lorg/jetbrains/kotlin/descriptors/annotations/KotlinRetention;", "Ljava/lang/annotation/RetentionPolicy;", "internalAnnotations", "", "Lorg/jetbrains/kotlin/name/FqName;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "getInternalAnnotations$backend_jvm_codegen", "()Ljava/util/Set;", "annotationClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "getAnnotationClass", "(Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;)Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "genAnnotationsOnTypeParametersAndBounds", "", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "typeParameterContainer", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParametersContainer;", "classCodegen", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ClassCodegen;", "referenceType", "", "boundType", "visitor", "Lkotlin/Function4;", "Lkotlin/ParameterName;", "name", "typeRef", "Lorg/jetbrains/org/objectweb/asm/TypePath;", "typePath", "", "descriptor", "", "visible", "Lorg/jetbrains/org/objectweb/asm/AnnotationVisitor;", "getRetentionPolicy", "irClass", "isInvisibleForNullabilityAnalysis", "declaration", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "applicableTargetSet", "Lorg/jetbrains/kotlin/descriptors/annotations/KotlinTarget;", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final void genAnnotationsOnTypeParametersAndBounds(JvmBackendContext context, final IrTypeParametersContainer typeParameterContainer, final ClassCodegen classCodegen, final int referenceType, final int boundType, final Function4<? super Integer, ? super TypePath, ? super String, ? super Boolean, ? extends AnnotationVisitor> visitor) {
            int i;
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(typeParameterContainer, "typeParameterContainer");
            Intrinsics.checkNotNullParameter(classCodegen, "classCodegen");
            Intrinsics.checkNotNullParameter(visitor, "visitor");
            int i2 = 0;
            for (Object obj : typeParameterContainer.getTypeParameters()) {
                int i3 = i2 + 1;
                if (i2 < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                final IrTypeParameter irTypeParameter = (IrTypeParameter) obj;
                final int i4 = i2;
                new AnnotationCodegen(classCodegen) { // from class: org.jetbrains.kotlin.backend.jvm.codegen.AnnotationCodegen$Companion$genAnnotationsOnTypeParametersAndBounds$1$1
                    @Override // org.jetbrains.kotlin.backend.jvm.codegen.AnnotationCodegen
                    public AnnotationVisitor visitAnnotation(String descr, boolean visible) {
                        Intrinsics.checkNotNullParameter(descr, "descr");
                        return visitor.invoke(Integer.valueOf(TypeReference.newTypeParameterReference(referenceType, i4).getValue()), null, descr, Boolean.valueOf(visible));
                    }

                    @Override // org.jetbrains.kotlin.backend.jvm.codegen.AnnotationCodegen
                    public AnnotationVisitor visitTypeAnnotation(String descr, TypePath path, boolean visible) {
                        Intrinsics.checkNotNullParameter(descr, "descr");
                        throw new RuntimeException("Error during generation: type annotation shouldn't be presented on type parameter: " + IrUtilsKt.ir2string(irTypeParameter) + " in " + IrUtilsKt.ir2string(typeParameterContainer));
                    }
                }.genAnnotations(irTypeParameter, null, null);
                if (context.getState().getConfiguration().getBoolean(JVMConfigurationKeys.EMIT_JVM_TYPE_ANNOTATIONS)) {
                    int i5 = 1;
                    for (IrType irType : irTypeParameter.getSuperTypes()) {
                        if (org.jetbrains.kotlin.ir.util.IrTypeUtilsKt.isInterface(irType) || org.jetbrains.kotlin.ir.util.IrTypeUtilsKt.isAnnotation(irType)) {
                            i = i5;
                            i5++;
                        } else {
                            i = 0;
                        }
                        final int i6 = i2;
                        final int i7 = i;
                        new AnnotationCodegen(classCodegen) { // from class: org.jetbrains.kotlin.backend.jvm.codegen.AnnotationCodegen$Companion$genAnnotationsOnTypeParametersAndBounds$1$2$1
                            @Override // org.jetbrains.kotlin.backend.jvm.codegen.AnnotationCodegen
                            public AnnotationVisitor visitAnnotation(String descr, boolean visible) {
                                Intrinsics.checkNotNullParameter(descr, "descr");
                                throw new RuntimeException("Error during generation: only type annotations should be presented on type parameters bounds: " + IrUtilsKt.ir2string(irTypeParameter) + " in " + IrUtilsKt.ir2string(irTypeParameter));
                            }

                            @Override // org.jetbrains.kotlin.backend.jvm.codegen.AnnotationCodegen
                            public AnnotationVisitor visitTypeAnnotation(String descr, TypePath path, boolean visible) {
                                Intrinsics.checkNotNullParameter(descr, "descr");
                                return visitor.invoke(Integer.valueOf(TypeReference.newTypeParameterBoundReference(boundType, i6, i7).getValue()), path, descr, Boolean.valueOf(visible));
                            }
                        }.generateTypeAnnotations$backend_jvm_codegen(typeParameterContainer, irType);
                    }
                }
                i2 = i3;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean isInvisibleForNullabilityAnalysis(IrDeclaration declaration) {
            return declaration.getOrigin().getIsSynthetic() || Intrinsics.areEqual(declaration.getOrigin(), JvmLoweredDeclarationOrigin.INLINE_CLASS_GENERATED_IMPL_METHOD.INSTANCE) || Intrinsics.areEqual(declaration.getOrigin(), JvmLoweredDeclarationOrigin.MULTI_FIELD_VALUE_CLASS_GENERATED_IMPL_METHOD.INSTANCE) || Intrinsics.areEqual(declaration.getOrigin(), IrDeclarationOrigin.GENERATED_SAM_IMPLEMENTATION.INSTANCE);
        }

        public final Set<FqName> getInternalAnnotations$backend_jvm_codegen() {
            return AnnotationCodegen.internalAnnotations;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final RetentionPolicy getRetentionPolicy(IrClass irClass) {
            KotlinRetention annotationRetention = IrUtilsKt.getAnnotationRetention(irClass);
            if (annotationRetention != null) {
                Object obj = AnnotationCodegen.annotationRetentionMap.get(annotationRetention);
                Intrinsics.checkNotNull(obj);
                return (RetentionPolicy) obj;
            }
            IrConstructorCall annotation = IrUtilsKt.getAnnotation(irClass, new FqName(Retention.class.getName()));
            if (annotation != null) {
                IrExpression valueArgument = annotation.getValueArgument(0);
                if (valueArgument instanceof IrDeclarationReference) {
                    IrSymbol symbol = ((IrDeclarationReference) valueArgument).getSymbol();
                    if (symbol instanceof IrEnumEntrySymbol) {
                        IrEnumEntry owner = ((IrEnumEntrySymbol) symbol).getOwner();
                        FqName fqNameWhenAvailable = IrUtilsKt.getFqNameWhenAvailable(IrUtilsKt.getParentAsClass(owner));
                        if (Intrinsics.areEqual(RetentionPolicy.class.getName(), fqNameWhenAvailable != null ? fqNameWhenAvailable.asString() : null)) {
                            String strAsString = owner.getName().asString();
                            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                            return RetentionPolicy.valueOf(strAsString);
                        }
                    }
                }
            }
            return RetentionPolicy.RUNTIME;
        }

        public final Set<KotlinTarget> applicableTargetSet(IrConstructorCall irConstructorCall) {
            Intrinsics.checkNotNullParameter(irConstructorCall, "<this>");
            Set<KotlinTarget> setApplicableTargetSet = AnnotationCodegenKt.applicableTargetSet(getAnnotationClass(irConstructorCall));
            return setApplicableTargetSet == null ? KotlinTarget.INSTANCE.getDEFAULT_TARGET_SET() : setApplicableTargetSet;
        }

        public final IrClass getAnnotationClass(IrConstructorCall irConstructorCall) {
            Intrinsics.checkNotNullParameter(irConstructorCall, "<this>");
            return IrUtilsKt.getParentAsClass(irConstructorCall.getSymbol().getOwner());
        }
    }

    public final void generateTypeAnnotations$backend_jvm_codegen(IrAnnotationContainer annotated, IrType type) {
        Intrinsics.checkNotNullParameter(annotated, "annotated");
        IrDeclaration irDeclaration = annotated instanceof IrDeclaration ? (IrDeclaration) annotated : null;
        if (Intrinsics.areEqual(irDeclaration != null ? irDeclaration.getOrigin() : null, JvmLoweredDeclarationOrigin.SYNTHETIC_ACCESSOR.INSTANCE) || type == null || !this.context.getState().getConfiguration().getBoolean(JVMConfigurationKeys.EMIT_JVM_TYPE_ANNOTATIONS)) {
            return;
        }
        Iterator<T> it = new IrTypeAnnotationCollector(this.classCodegen.getTypeMapper().getTypeSystem()).collectTypeAnnotations(type).iterator();
        while (it.hasNext()) {
            TypePathInfo typePathInfo = (TypePathInfo) it.next();
            Iterator it2 = typePathInfo.getAnnotations().iterator();
            while (it2.hasNext()) {
                genAnnotation((IrConstructorCall) it2.next(), typePathInfo.getPath(), true);
            }
        }
    }

    /* JADX INFO: compiled from: AnnotationCodegen.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0012\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00020\u000b*\u00020\fH\u0016R\u0018\u0010\u0006\u001a\u00020\u0007*\u00020\b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\t¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/codegen/AnnotationCodegen$IrTypeAnnotationCollector;", "Lorg/jetbrains/kotlin/codegen/TypeAnnotationCollector;", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "context", "Lorg/jetbrains/kotlin/types/TypeSystemCommonBackendContext;", "(Lorg/jetbrains/kotlin/types/TypeSystemCommonBackendContext;)V", "isCompiledToJvm8OrHigher", "", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Z", "extractAnnotations", "", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class IrTypeAnnotationCollector extends TypeAnnotationCollector<IrConstructorCall> {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public IrTypeAnnotationCollector(TypeSystemCommonBackendContext context) {
            super(context);
            Intrinsics.checkNotNullParameter(context, "context");
        }

        @Override // org.jetbrains.kotlin.codegen.TypeAnnotationCollector
        public List<IrConstructorCall> extractAnnotations(KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
            if (!(kotlinTypeMarker instanceof IrType)) {
                throw new IllegalArgumentException("Failed requirement.".toString());
            }
            List<IrConstructorCall> annotations = ((IrType) kotlinTypeMarker).getAnnotations();
            ArrayList arrayList = new ArrayList();
            for (Object obj : annotations) {
                IrConstructorCall irConstructorCall = (IrConstructorCall) obj;
                if ((!Intrinsics.areEqual(AnnotationCodegen.INSTANCE.getAnnotationClass(irConstructorCall).getOrigin(), IrDeclarationOrigin.IR_EXTERNAL_DECLARATION_STUB.INSTANCE) && !Intrinsics.areEqual(AnnotationCodegen.INSTANCE.getAnnotationClass(irConstructorCall).getOrigin(), IrDeclarationOrigin.IR_EXTERNAL_JAVA_DECLARATION_STUB.INSTANCE)) || isCompiledToJvm8OrHigher(AnnotationCodegen.INSTANCE.getAnnotationClass(irConstructorCall))) {
                    arrayList.add(obj);
                }
            }
            return arrayList;
        }

        private final boolean isCompiledToJvm8OrHigher(IrClass irClass) {
            return (Intrinsics.areEqual(irClass.getOrigin(), IrDeclarationOrigin.IR_EXTERNAL_DECLARATION_STUB.INSTANCE) || Intrinsics.areEqual(irClass.getOrigin(), IrDeclarationOrigin.IR_EXTERNAL_JAVA_DECLARATION_STUB.INSTANCE)) && isCompiledToJvm8OrHigher(irClass.getSource());
        }
    }
}
