package org.jetbrains.kotlin.backend.jvm.mapping;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.JvmLoweredDeclarationOrigin;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.AsmUtil;
import org.jetbrains.kotlin.codegen.JvmCodegenUtil;
import org.jetbrains.kotlin.codegen.signature.JvmSignatureWriter;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapperBase;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrScript;
import org.jetbrains.kotlin.ir.declarations.IrTypeParameter;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrScriptSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.types.IrErrorType;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.IrTypeSystemContext;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeOptions;
import org.jetbrains.kotlin.ir.util.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.ir.util.RenderIrElementKt;
import org.jetbrains.kotlin.load.kotlin.TypeMappingMode;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.resolve.jvm.JvmClassName;
import org.jetbrains.kotlin.types.AbstractTypeMapper;
import org.jetbrains.kotlin.types.TypeMappingContext;
import org.jetbrains.kotlin.types.TypeSystemCommonBackendContextForTypeMapping;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: IrTypeMapper.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000 \u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\b\u0016\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u000e\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012J\u000e\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016J\u0014\u0010\u0017\u001a\u00060\u0018j\u0002`\u00192\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\u0010\u0010\u001a\u001a\u00020\u00142\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\u0010\u0010\u001d\u001a\u00020\u00142\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u0011\u001a\u00020\u0012H\u0002J\u001e\u0010 \u001a\u00020\u001f2\u0006\u0010!\u001a\u00020\u00162\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020$0#H\u0002J\u0010\u0010%\u001a\u00020\u00102\u0006\u0010!\u001a\u00020&H\u0016J0\u0010'\u001a\u00020\u00102\u0006\u0010(\u001a\u00020\u00122\b\b\u0002\u0010)\u001a\u00020*2\n\b\u0002\u0010+\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010,\u001a\u00020\u001fH\u0016J\u0018\u0010-\u001a\u00020\u00102\u0006\u0010(\u001a\u00020.2\u0006\u0010)\u001a\u00020*H\u0016J\u001c\u0010/\u001a\u0002002\f\u00101\u001a\b\u0012\u0004\u0012\u0002020#2\u0006\u0010+\u001a\u00020\u0003J4\u00103\u001a\u0002002\u0006\u0010+\u001a\u00020\u00032\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020$0#2\f\u00104\u001a\b\u0012\u0004\u0012\u0002050#2\u0006\u0010)\u001a\u00020*H\u0002J \u00103\u001a\u0002002\u0006\u0010+\u001a\u00020\u00032\u0006\u0010(\u001a\u0002062\u0006\u0010)\u001a\u00020*H\u0002J.\u00107\u001a\u0002002\f\u00108\u001a\b\u0012\u0004\u0012\u0002060#2\u0006\u0010+\u001a\u00020\u00032\u0006\u0010)\u001a\u00020*2\u0006\u00109\u001a\u00020:H\u0002J$\u0010;\u001a\u000200*\u00020\u00032\u0006\u0010(\u001a\u00020.2\u0006\u0010<\u001a\u00020\u00102\u0006\u0010)\u001a\u00020*H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006="}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/mapping/IrTypeMapper;", "Lorg/jetbrains/kotlin/codegen/state/KotlinTypeMapperBase;", "Lorg/jetbrains/kotlin/types/TypeMappingContext;", "Lorg/jetbrains/kotlin/codegen/signature/JvmSignatureWriter;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "typeContext", "Lorg/jetbrains/kotlin/types/TypeSystemCommonBackendContextForTypeMapping;", "getTypeContext", "()Lorg/jetbrains/kotlin/types/TypeSystemCommonBackendContextForTypeMapping;", "typeSystem", "Lorg/jetbrains/kotlin/ir/types/IrTypeSystemContext;", "getTypeSystem", "()Lorg/jetbrains/kotlin/ir/types/IrTypeSystemContext;", "boxType", "Lorg/jetbrains/org/objectweb/asm/Type;", "irType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "classInternalName", "", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "computeClassInternalName", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "getClassInternalName", "typeConstructor", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "getScriptInternalName", "hasNothingInNonContravariantPosition", "", "isBigArityFunction", "classifier", "arguments", "", "Lorg/jetbrains/kotlin/ir/types/IrTypeArgument;", "mapClass", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "mapType", ModuleXmlParser.TYPE, "mode", "Lorg/jetbrains/kotlin/load/kotlin/TypeMappingMode;", "sw", "materialized", "mapTypeCommon", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "writeFormalTypeParameters", "", "irParameters", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeParameter;", "writeGenericArguments", "parameters", "Lorg/jetbrains/kotlin/ir/symbols/IrTypeParameterSymbol;", "Lorg/jetbrains/kotlin/backend/jvm/mapping/PossiblyInnerIrType;", "writeInnerParts", "innerTypesAsList", "index", "", "writeGenericType", "asmType", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class IrTypeMapper extends KotlinTypeMapperBase implements TypeMappingContext<JvmSignatureWriter> {
    private final JvmBackendContext context;
    private final TypeSystemCommonBackendContextForTypeMapping typeContext;
    private final IrTypeSystemContext typeSystem;

    public IrTypeMapper(JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.typeSystem = context.getTypeSystem();
        this.typeContext = new IrTypeCheckerContextForTypeMapping(getTypeSystem(), context);
    }

    @Override // org.jetbrains.kotlin.codegen.state.KotlinTypeMapperBase
    public IrTypeSystemContext getTypeSystem() {
        return this.typeSystem;
    }

    @Override // org.jetbrains.kotlin.types.TypeMappingContext
    public TypeSystemCommonBackendContextForTypeMapping getTypeContext() {
        return this.typeContext;
    }

    @Override // org.jetbrains.kotlin.codegen.state.KotlinTypeMapperBase
    public Type mapClass(ClassifierDescriptor classifier) {
        Intrinsics.checkNotNullParameter(classifier, "classifier");
        if (classifier instanceof ClassDescriptor) {
            return IrTypeMappingKt.mapClass(this, this.context.referenceClass((ClassDescriptor) classifier).getOwner());
        }
        if (classifier instanceof TypeParameterDescriptor) {
            return mapType$default(this, IrTypesKt.getDefaultType(this.context.referenceTypeParameter$backend_jvm((TypeParameterDescriptor) classifier)), null, null, false, 14, null);
        }
        throw new IllegalStateException(("Unknown descriptor: " + classifier).toString());
    }

    @Override // org.jetbrains.kotlin.codegen.state.KotlinTypeMapperBase
    public Type mapTypeCommon(KotlinTypeMarker type, TypeMappingMode mode) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(mode, "mode");
        return mapType$default(this, (IrType) type, mode, null, false, 12, null);
    }

    private final StringBuilder computeClassInternalName(IrClass irClass) {
        String internalName;
        Type localClassType = this.context.getLocalClassType(irClass);
        if (localClassType != null && (internalName = localClassType.getInternalName()) != null) {
            return new StringBuilder(internalName);
        }
        String identifier = SpecialNames.safeIdentifier(irClass.getName()).getIdentifier();
        Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
        IrDeclarationParent parent = irClass.getParent();
        if (parent instanceof IrPackageFragment) {
            StringBuilder sb = new StringBuilder();
            FqName packageFqName = ((IrPackageFragment) parent).getPackageFqName();
            if (!packageFqName.isRoot()) {
                String strAsString = packageFqName.asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                sb.append(StringsKt.replace$default(strAsString, '.', '/', false, 4, (Object) null)).append("/");
            }
            sb.append(identifier);
            return sb;
        }
        if (parent instanceof IrClass) {
            StringBuilder sbAppend = computeClassInternalName((IrClass) parent).append("$").append(identifier);
            Intrinsics.checkNotNullExpressionValue(sbAppend, "append(...)");
            return sbAppend;
        }
        if (parent instanceof IrFunction) {
            IrFunction irFunction = (IrFunction) parent;
            if (AdditionalIrUtilsKt.isSuspend(irFunction)) {
                IrDeclaration irDeclaration = (IrDeclaration) parent;
                if (Intrinsics.areEqual(IrUtilsKt.getParentAsClass(irDeclaration).getOrigin(), JvmLoweredDeclarationOrigin.DEFAULT_IMPLS.INSTANCE)) {
                    StringBuilder sbAppend2 = computeClassInternalName(IrUtilsKt.getParentAsClass(IrUtilsKt.getParentAsClass(irDeclaration))).append("$").append(irFunction.getName().asString());
                    Intrinsics.checkNotNullExpressionValue(sbAppend2, "append(...)");
                    return sbAppend2;
                }
            }
        }
        throw new IllegalStateException(("Local class should have its name computed in InventNamesForLocalClasses: " + IrUtilsKt.getFqNameWhenAvailable(irClass) + "\nEnsure that any lowering that transforms elements with local class info (classes, function references) invokes `copyAttributes` on the transformed element.").toString());
    }

    public final String classInternalName(IrClass irClass) {
        String internalName;
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        Type localClassType = this.context.getLocalClassType(irClass);
        if (localClassType != null && (internalName = localClassType.getInternalName()) != null) {
            return internalName;
        }
        JvmClassName jvmClassName = this.context.getClassNameOverride().get(irClass);
        if (jvmClassName == null) {
            String strSanitizeNameIfNeeded = JvmCodegenUtil.sanitizeNameIfNeeded(computeClassInternalName(irClass).toString(), this.context.getState().getLanguageVersionSettings());
            Intrinsics.checkNotNullExpressionValue(strSanitizeNameIfNeeded, "sanitizeNameIfNeeded(...)");
            return strSanitizeNameIfNeeded;
        }
        String internalName2 = jvmClassName.getInternalName();
        Intrinsics.checkNotNullExpressionValue(internalName2, "getInternalName(...)");
        return internalName2;
    }

    @Override // org.jetbrains.kotlin.types.TypeMappingContext
    public String getClassInternalName(TypeConstructorMarker typeConstructor) {
        Intrinsics.checkNotNullParameter(typeConstructor, "typeConstructor");
        return classInternalName(((IrClassSymbol) typeConstructor).getOwner());
    }

    @Override // org.jetbrains.kotlin.types.TypeMappingContext
    public String getScriptInternalName(TypeConstructorMarker typeConstructor) {
        Intrinsics.checkNotNullParameter(typeConstructor, "typeConstructor");
        IrScript owner = ((IrScriptSymbol) typeConstructor).getOwner();
        IrClassSymbol targetClass = owner.getTargetClass();
        if (targetClass == null) {
            throw new IllegalStateException(("No target class computed for script: " + RenderIrElementKt.render$default(owner, (DumpIrTreeOptions) null, 1, (Object) null)).toString());
        }
        return classInternalName(targetClass.getOwner());
    }

    public final void writeFormalTypeParameters(List<? extends IrTypeParameter> irParameters, final JvmSignatureWriter sw) {
        Intrinsics.checkNotNullParameter(irParameters, "irParameters");
        Intrinsics.checkNotNullParameter(sw, "sw");
        if (sw.skipGenericSignature()) {
            return;
        }
        KotlinTypeMapper.Companion companion = KotlinTypeMapper.INSTANCE;
        Iterator<? extends IrTypeParameter> it = irParameters.iterator();
        while (it.hasNext()) {
            companion.writeFormalTypeParameter(getTypeSystem(), it.next().getSymbol(), sw, new Function2<KotlinTypeMarker, TypeMappingMode, Type>() { // from class: org.jetbrains.kotlin.backend.jvm.mapping.IrTypeMapper$writeFormalTypeParameters$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Type invoke(KotlinTypeMarker type, TypeMappingMode mode) {
                    Intrinsics.checkNotNullParameter(type, "type");
                    Intrinsics.checkNotNullParameter(mode, "mode");
                    return IrTypeMapper.mapType$default(this.this$0, (IrType) type, mode, sw, false, 8, null);
                }
            });
        }
    }

    public final Type boxType(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "irType");
        IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(irType);
        IrClass owner = classOrNull != null ? classOrNull.getOwner() : null;
        if (owner != null && IrDeclarationsKt.isSingleFieldValueClass(owner)) {
            return IrTypeMappingKt.mapTypeAsDeclaration(this, irType);
        }
        Type typeMapType$default = AbstractTypeMapper.mapType$default(AbstractTypeMapper.INSTANCE, this, irType, null, null, false, 28, null);
        Type typeBoxPrimitiveType = AsmUtil.boxPrimitiveType(typeMapType$default);
        return typeBoxPrimitiveType == null ? typeMapType$default : typeBoxPrimitiveType;
    }

    public static /* synthetic */ Type mapType$default(IrTypeMapper irTypeMapper, IrType irType, TypeMappingMode typeMappingMode, JvmSignatureWriter jvmSignatureWriter, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: mapType");
        }
        if ((i & 2) != 0) {
            typeMappingMode = TypeMappingMode.DEFAULT;
        }
        if ((i & 4) != 0) {
            jvmSignatureWriter = null;
        }
        if ((i & 8) != 0) {
            z = true;
        }
        return irTypeMapper.mapType(irType, typeMappingMode, jvmSignatureWriter, z);
    }

    public Type mapType(IrType type, TypeMappingMode mode, JvmSignatureWriter sw, boolean materialized) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(mode, "mode");
        return AbstractTypeMapper.INSTANCE.mapType(this, type, mode, sw, materialized);
    }

    @Override // org.jetbrains.kotlin.types.TypeMappingContext
    public void writeGenericType(JvmSignatureWriter jvmSignatureWriter, KotlinTypeMarker type, Type asmType, TypeMappingMode mode) {
        Intrinsics.checkNotNullParameter(jvmSignatureWriter, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(asmType, "asmType");
        Intrinsics.checkNotNullParameter(mode, "mode");
        if (type instanceof IrErrorType) {
            jvmSignatureWriter.writeAsmType(asmType);
            return;
        }
        if (type instanceof IrSimpleType) {
            if (!jvmSignatureWriter.skipGenericSignature() && !hasNothingInNonContravariantPosition((IrType) type)) {
                IrSimpleType irSimpleType = (IrSimpleType) type;
                if (!irSimpleType.getArguments().isEmpty() && !JvmIrUtilsKt.isRawType(irSimpleType)) {
                    PossiblyInnerIrType possiblyInnerIrTypeBuildPossiblyInnerType = IrTypeMappingKt.buildPossiblyInnerType(irSimpleType);
                    if (possiblyInnerIrTypeBuildPossiblyInnerType == null) {
                        throw new IllegalStateException("possiblyInnerType with arguments should not be null".toString());
                    }
                    List<PossiblyInnerIrType> listSegments = possiblyInnerIrTypeBuildPossiblyInnerType.segments();
                    Iterator<PossiblyInnerIrType> it = listSegments.iterator();
                    int i = 0;
                    while (true) {
                        if (!it.hasNext()) {
                            i = -1;
                            break;
                        } else if (!it.next().getArguments().isEmpty()) {
                            break;
                        } else {
                            i++;
                        }
                    }
                    if (i < 0 || listSegments.size() == 1) {
                        jvmSignatureWriter.writeClassBegin(asmType);
                        writeGenericArguments(jvmSignatureWriter, possiblyInnerIrTypeBuildPossiblyInnerType, mode);
                    } else {
                        PossiblyInnerIrType possiblyInnerIrType = listSegments.get(i);
                        jvmSignatureWriter.writeOuterClassBegin(asmType, mapType$default(this, IrUtilsKt.getDefaultType(possiblyInnerIrType.getClassifier()), null, null, false, 14, null).getInternalName());
                        writeGenericArguments(jvmSignatureWriter, possiblyInnerIrType, mode);
                        writeInnerParts(listSegments, jvmSignatureWriter, mode, i + 1);
                    }
                    jvmSignatureWriter.writeClassEnd();
                    return;
                }
            }
            jvmSignatureWriter.writeAsmType(asmType);
        }
    }

    private final boolean hasNothingInNonContravariantPosition(IrType irType) {
        return KotlinTypeMapper.INSTANCE.hasNothingInNonContravariantPosition(getTypeSystem(), irType);
    }

    private final void writeInnerParts(List<PossiblyInnerIrType> innerTypesAsList, JvmSignatureWriter sw, TypeMappingMode mode, int index) {
        for (PossiblyInnerIrType possiblyInnerIrType : innerTypesAsList.subList(index, innerTypesAsList.size())) {
            sw.writeInnerClass(IrTypeMappingKt.getJvmShortName(possiblyInnerIrType.getClassifier()));
            writeGenericArguments(sw, possiblyInnerIrType, mode);
        }
    }

    private final void writeGenericArguments(JvmSignatureWriter sw, PossiblyInnerIrType type, TypeMappingMode mode) {
        IrClass classifier = type.getClassifier();
        List<IrTypeParameter> typeParameters = classifier.getTypeParameters();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(typeParameters, 10));
        Iterator<T> it = typeParameters.iterator();
        while (it.hasNext()) {
            arrayList.add(((IrTypeParameter) it.next()).getSymbol());
        }
        ArrayList arrayList2 = arrayList;
        List<IrTypeArgument> arguments = type.getArguments();
        if (isBigArityFunction(classifier, arguments) || IrTypeUtilsKt.isKFunction(classifier.getSymbol()) || IrTypeUtilsKt.isKSuspendFunction(classifier.getSymbol())) {
            writeGenericArguments(sw, CollectionsKt.listOf(CollectionsKt.last((List) arguments)), CollectionsKt.listOf(CollectionsKt.last((List) arrayList2)), mode);
        } else {
            writeGenericArguments(sw, arguments, arrayList2, mode);
        }
    }

    private final boolean isBigArityFunction(IrClass classifier, List<? extends IrTypeArgument> arguments) {
        return arguments.size() > 23 && (IrTypeUtilsKt.isFunction(classifier.getSymbol()) || IrTypeUtilsKt.isSuspendFunction(classifier.getSymbol()));
    }

    private final void writeGenericArguments(JvmSignatureWriter sw, List<? extends IrTypeArgument> arguments, List<? extends IrTypeParameterSymbol> parameters, TypeMappingMode mode) {
        KotlinTypeMapper.INSTANCE.writeGenericArguments(getTypeSystem(), sw, arguments, parameters, mode, new Function3<KotlinTypeMarker, JvmSignatureWriter, TypeMappingMode, Type>() { // from class: org.jetbrains.kotlin.backend.jvm.mapping.IrTypeMapper$writeGenericArguments$1$1
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public final Type invoke(KotlinTypeMarker type, JvmSignatureWriter sw2, TypeMappingMode mode2) {
                Intrinsics.checkNotNullParameter(type, "type");
                Intrinsics.checkNotNullParameter(sw2, "sw");
                Intrinsics.checkNotNullParameter(mode2, "mode");
                return IrTypeMapper.mapType$default(this.this$0, (IrType) type, mode2, sw2, false, 8, null);
            }
        });
    }
}
