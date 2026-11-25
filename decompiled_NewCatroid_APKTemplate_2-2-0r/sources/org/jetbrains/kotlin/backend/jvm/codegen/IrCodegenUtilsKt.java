package org.jetbrains.kotlin.backend.jvm.codegen;

import com.badlogic.gdx.graphics.GL20;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.JvmLoweredDeclarationOrigin;
import org.jetbrains.kotlin.backend.jvm.MultifileFacadeFileEntry;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrInlineUtilsKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrTypeUtilsKt;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.backend.jvm.mapping.IrTypeMapper;
import org.jetbrains.kotlin.backend.jvm.mapping.IrTypeMappingKt;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.builtins.jvm.JavaToKotlinClassMap;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.OwnerKind;
import org.jetbrains.kotlin.codegen.SourceInfo;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeParametersUsages;
import org.jetbrains.kotlin.codegen.inline.SourceMapper;
import org.jetbrains.kotlin.codegen.signature.BothSignatureWriter;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.IrFileEntry;
import org.jetbrains.kotlin.ir.declarations.IrAttributeContainer;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithVisibility;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationsKt;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.expressions.IrBody;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrExpressionBody;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrClassifierSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol;
import org.jetbrains.kotlin.ir.types.IrSimpleType;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.types.IrTypeArgument;
import org.jetbrains.kotlin.ir.types.IrTypeProjection;
import org.jetbrains.kotlin.ir.types.IrTypesKt;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.DumpIrTreeKt;
import org.jetbrains.kotlin.ir.util.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.JavaDescriptorVisibilities;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.kotlin.resolve.jvm.jvmSignature.JvmClassSignature;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.tree.FieldInsnNode;

/* JADX INFO: compiled from: irCodegenUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0084\u0001\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a\u001a\u0010\u0016\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u0018\u001a\u00020\u000e2\u0006\u0010\u0019\u001a\u00020\u001aH\u0000\u001a\u0013\u0010\u001b\u001a\u00020\u00072\b\u0010\u001c\u001a\u0004\u0018\u00010\u000bH\u0082\u0010\u001a\u0012\u0010\u001d\u001a\u00020\u0005*\u00020\u00132\u0006\u0010\u001e\u001a\u00020\u001f\u001a\u001a\u0010 \u001a\u00020\u0005*\u00020!2\u0006\u0010\"\u001a\u00020#2\u0006\u0010\u0018\u001a\u00020$\u001a\u0012\u0010%\u001a\u00020&*\u00020\u001f2\u0006\u0010\u001c\u001a\u00020\u0013\u001a\u0016\u0010'\u001a\u00020\u0005*\u00020(2\n\b\u0002\u0010)\u001a\u0004\u0018\u00010*\u001a\f\u0010+\u001a\u00020\u0005*\u00020\u000bH\u0002\u001a\n\u0010,\u001a\u00020\u0005*\u00020\u0013\u001a\f\u0010-\u001a\u00020\u0005*\u00020\u0013H\u0002\u001a\u0014\u0010.\u001a\u00020\u0007*\u00020\b2\u0006\u0010\u001e\u001a\u00020\u001fH\u0002\u001a\u0014\u0010/\u001a\u00020\u0007*\u00020\u000b2\u0006\u0010\u001e\u001a\u00020\u001fH\u0000\u001a\u0014\u00100\u001a\u00020\u0007*\u00020\b2\u0006\u0010\u001e\u001a\u00020\u001fH\u0000\u001a\f\u00101\u001a\u00020\u0007*\u00020(H\u0002\u001a\u0012\u00102\u001a\u00020\u0005*\u00020!2\u0006\u0010\"\u001a\u00020#\u001a$\u00103\u001a\u000204*\u0002052\u0006\u00106\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020$2\u0006\u00107\u001a\u00020\u0007H\u0000\u001a\u001d\u00108\u001a\u0004\u0018\u00010\u0005*\u00020(2\b\u0010)\u001a\u0004\u0018\u00010*H\u0002¢\u0006\u0002\u00109\"\u001a\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\u0004\u001a\u00020\u0005X\u0082D¢\u0006\u0002\n\u0000\"\u0018\u0010\u0006\u001a\u00020\u0007*\u00020\b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\t\"\u0015\u0010\n\u001a\u00020\u0007*\u00020\u000b8F¢\u0006\u0006\u001a\u0004\b\n\u0010\f\"\u0015\u0010\r\u001a\u00020\u0007*\u00020\u000e8F¢\u0006\u0006\u001a\u0004\b\r\u0010\u000f\"\u0018\u0010\u0010\u001a\u00020\u0007*\u00020\b8@X\u0080\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\t\"\u0015\u0010\u0011\u001a\u00020\u0012*\u00020\u00138F¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015¨\u0006:"}, d2 = {"KOTLIN_MARKER_INTERFACES", "", "Lorg/jetbrains/kotlin/name/FqName;", "", "NO_FLAG_LOCAL", "", "isAccessorForDeprecatedPropertyImplementedByDelegation", "", "Lorg/jetbrains/kotlin/ir/declarations/IrFunction;", "(Lorg/jetbrains/kotlin/ir/declarations/IrFunction;)Z", "isAnnotatedWithDeprecated", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;)Z", "isExtensionFunctionType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "(Lorg/jetbrains/kotlin/ir/types/IrType;)Z", "isStatic", "reifiedTypeParameters", "Lorg/jetbrains/kotlin/codegen/inline/ReifiedTypeParametersUsages;", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getReifiedTypeParameters", "(Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Lorg/jetbrains/kotlin/codegen/inline/ReifiedTypeParametersUsages;", "generateExternalEntriesForEnumTypeIfNeeded", "Lorg/jetbrains/org/objectweb/asm/tree/FieldInsnNode;", ModuleXmlParser.TYPE, "containingCodegen", "Lorg/jetbrains/kotlin/backend/jvm/codegen/ClassCodegen;", "isInlineOrContainedInInline", "declaration", "calculateInnerClassAccessFlags", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "enter", "Lorg/jetbrains/kotlin/backend/jvm/codegen/IrFrameMap;", "irDeclaration", "Lorg/jetbrains/kotlin/ir/declarations/IrSymbolOwner;", "Lorg/jetbrains/org/objectweb/asm/Type;", "getSourceMapper", "Lorg/jetbrains/kotlin/codegen/inline/SourceMapper;", "getVisibilityAccessFlag", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationWithVisibility;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/codegen/OwnerKind;", "getVisibilityAccessFlagForAnonymous", "getVisibilityAccessFlagForClass", "innerAccessFlagsForModalityAndKind", "isAccessorForDeprecatedJvmStaticProperty", "isDeprecatedCallable", "isDeprecatedFunction", "isInlineOnlyPrivateInBytecode", "leave", "mapClassSignature", "Lorg/jetbrains/kotlin/resolve/jvm/jvmSignature/JvmClassSignature;", "Lorg/jetbrains/kotlin/backend/jvm/mapping/IrTypeMapper;", "irClass", "generateBodies", "specialCaseVisibility", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationWithVisibility;Lorg/jetbrains/kotlin/codegen/OwnerKind;)Ljava/lang/Integer;", "backend.jvm.codegen"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class IrCodegenUtilsKt {
    private static final Map<FqName, String> KOTLIN_MARKER_INTERFACES;
    private static final int NO_FLAG_LOCAL = 0;

    /* JADX INFO: compiled from: irCodegenUtils.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ClassKind.values().length];
            try {
                iArr[ClassKind.INTERFACE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ClassKind.ENUM_CLASS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ClassKind.ANNOTATION_CLASS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final boolean isStatic(IrFunction irFunction) {
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        return irFunction.getDispatchReceiverParameter() == null && !(irFunction instanceof IrConstructor);
    }

    public static final int enter(IrFrameMap irFrameMap, IrSymbolOwner irDeclaration, Type type) {
        Intrinsics.checkNotNullParameter(irFrameMap, "<this>");
        Intrinsics.checkNotNullParameter(irDeclaration, "irDeclaration");
        Intrinsics.checkNotNullParameter(type, "type");
        return irFrameMap.enter(irDeclaration.getSymbol(), type);
    }

    public static final int leave(IrFrameMap irFrameMap, IrSymbolOwner irDeclaration) {
        Intrinsics.checkNotNullParameter(irFrameMap, "<this>");
        Intrinsics.checkNotNullParameter(irDeclaration, "irDeclaration");
        return irFrameMap.leave(irDeclaration.getSymbol());
    }

    public static final SourceMapper getSourceMapper(JvmBackendContext jvmBackendContext, IrClass declaration) {
        Type typeMapClass;
        String name2;
        Intrinsics.checkNotNullParameter(jvmBackendContext, "<this>");
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        IrFile fileParentBeforeInline = JvmIrInlineUtilsKt.getFileParentBeforeInline(declaration);
        IrAttributeContainer attributeOwnerBeforeInline = JvmIrInlineUtilsKt.getAttributeOwnerBeforeInline(declaration);
        if (attributeOwnerBeforeInline == null || (typeMapClass = jvmBackendContext.getLocalClassType(attributeOwnerBeforeInline)) == null) {
            typeMapClass = IrTypeMappingKt.mapClass(jvmBackendContext.getDefaultTypeMapper(), declaration);
        }
        IrFileEntry fileEntry = fileParentBeforeInline.getFileEntry();
        boolean z = fileEntry instanceof MultifileFacadeFileEntry;
        int endLineNumber = z ? 0 : fileEntry.getSourceRangeInfo(0, fileEntry.getMaxOffset()).getEndLineNumber();
        if (z) {
            IrFile irFile = (IrFile) CollectionsKt.singleOrNull((List) ((MultifileFacadeFileEntry) fileEntry).getPartFiles());
            name2 = irFile != null ? IrDeclarationsKt.getName(irFile) : null;
        } else {
            name2 = IrDeclarationsKt.getName(fileParentBeforeInline);
        }
        String internalName = typeMapClass.getInternalName();
        Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
        return new SourceMapper(new SourceInfo(name2, internalName, endLineNumber + 1));
    }

    public static final boolean isExtensionFunctionType(IrType irType) {
        Intrinsics.checkNotNullParameter(irType, "<this>");
        return IrTypeUtilsKt.isFunctionTypeOrSubtype(irType) && IrUtilsKt.hasAnnotation(irType, StandardNames.FqNames.extensionFunctionType);
    }

    private static final int getVisibilityAccessFlagForAnonymous(IrDeclaration irDeclaration) {
        IrDeclarationParent parent = irDeclaration.getParent();
        return isInlineOrContainedInInline(parent instanceof IrDeclaration ? (IrDeclaration) parent : null) ? 1 : 0;
    }

    /* JADX WARN: Code duplicated, block: B:15:0x0051  */
    /* JADX WARN: Code duplicated, block: B:16:0x0053  */
    public static final int calculateInnerClassAccessFlags(IrClass irClass, JvmBackendContext context) {
        int visibilityAccessFlagForAnonymous;
        int iInnerAccessFlagsForModalityAndKind;
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        List<IrType> superTypes = irClass.getSuperTypes();
        if ((superTypes instanceof Collection) && superTypes.isEmpty()) {
            if (irClass.getVisibility() == DescriptorVisibilities.LOCAL) {
                visibilityAccessFlagForAnonymous = 1;
            } else {
                visibilityAccessFlagForAnonymous = getVisibilityAccessFlag$default(irClass, null, 1, null);
            }
        } else {
            Iterator<T> it = superTypes.iterator();
            while (true) {
                if (it.hasNext()) {
                    if (IrTypesKt.getClassOrNull((IrType) it.next()) == context.getIr().getSymbols().getLambdaClass()) {
                        visibilityAccessFlagForAnonymous = getVisibilityAccessFlagForAnonymous(irClass);
                    }
                } else if (irClass.getVisibility() == DescriptorVisibilities.LOCAL) {
                    visibilityAccessFlagForAnonymous = 1;
                } else {
                    visibilityAccessFlagForAnonymous = getVisibilityAccessFlag$default(irClass, null, 1, null);
                }
            }
        }
        if (irClass.getOrigin().getIsSynthetic()) {
            iInnerAccessFlagsForModalityAndKind = 4096;
        } else {
            iInnerAccessFlagsForModalityAndKind = (irClass.getIsInner() ? 0 : 8) | innerAccessFlagsForModalityAndKind(irClass);
        }
        return iInnerAccessFlagsForModalityAndKind | visibilityAccessFlagForAnonymous;
    }

    private static final int innerAccessFlagsForModalityAndKind(IrClass irClass) {
        int i = WhenMappings.$EnumSwitchMapping$0[irClass.getKind().ordinal()];
        if (i == 1) {
            return 1536;
        }
        if (i == 2) {
            return 16400;
        }
        if (i == 3) {
            return GL20.GL_NEAREST;
        }
        if (irClass.getModality() == Modality.FINAL) {
            return 16;
        }
        return (irClass.getModality() == Modality.ABSTRACT || irClass.getModality() == Modality.SEALED) ? 1024 : 0;
    }

    public static /* synthetic */ int getVisibilityAccessFlag$default(IrDeclarationWithVisibility irDeclarationWithVisibility, OwnerKind ownerKind, int i, Object obj) {
        if ((i & 1) != 0) {
            ownerKind = null;
        }
        return getVisibilityAccessFlag(irDeclarationWithVisibility, ownerKind);
    }

    public static final int getVisibilityAccessFlag(IrDeclarationWithVisibility irDeclarationWithVisibility, OwnerKind ownerKind) {
        Intrinsics.checkNotNullParameter(irDeclarationWithVisibility, "<this>");
        Integer numSpecialCaseVisibility = specialCaseVisibility(irDeclarationWithVisibility, ownerKind);
        if (numSpecialCaseVisibility != null) {
            return numSpecialCaseVisibility.intValue();
        }
        DescriptorVisibility visibility = irDeclarationWithVisibility.getVisibility();
        if (Intrinsics.areEqual(visibility, DescriptorVisibilities.PRIVATE) || Intrinsics.areEqual(visibility, DescriptorVisibilities.PRIVATE_TO_THIS)) {
            return 2;
        }
        if (Intrinsics.areEqual(visibility, DescriptorVisibilities.PROTECTED) || Intrinsics.areEqual(visibility, JavaDescriptorVisibilities.PROTECTED_STATIC_VISIBILITY) || Intrinsics.areEqual(visibility, JavaDescriptorVisibilities.PROTECTED_AND_PACKAGE)) {
            return 4;
        }
        if (Intrinsics.areEqual(visibility, DescriptorVisibilities.PUBLIC) || Intrinsics.areEqual(visibility, DescriptorVisibilities.INTERNAL)) {
            return 1;
        }
        if (Intrinsics.areEqual(visibility, DescriptorVisibilities.LOCAL)) {
            return NO_FLAG_LOCAL;
        }
        if (Intrinsics.areEqual(visibility, JavaDescriptorVisibilities.PACKAGE_VISIBILITY)) {
            return 0;
        }
        throw new IllegalStateException(irDeclarationWithVisibility.getVisibility() + " is not a valid visibility in backend for " + IrUtilsKt.ir2string(irDeclarationWithVisibility));
    }

    private static final Integer specialCaseVisibility(IrDeclarationWithVisibility irDeclarationWithVisibility, OwnerKind ownerKind) {
        IrField irField;
        IrPropertySymbol correspondingPropertySymbol;
        IrProperty owner;
        IrSimpleFunction setter;
        IrProperty owner2;
        IrProperty owner3;
        boolean z = irDeclarationWithVisibility instanceof IrClass;
        if (z && DescriptorVisibilities.isPrivate(irDeclarationWithVisibility.getVisibility()) && ((IrClass) irDeclarationWithVisibility).getIsCompanion() && IrUtilsKt.hasInterfaceParent(irDeclarationWithVisibility)) {
            return 1;
        }
        boolean z2 = irDeclarationWithVisibility instanceof IrConstructor;
        if ((!z2 || !IrDeclarationsKt.isSingleFieldValueClass(IrUtilsKt.getParentAsClass(irDeclarationWithVisibility)) || ownerKind != OwnerKind.IMPLEMENTATION) && !isInlineOnlyPrivateInBytecode(irDeclarationWithVisibility)) {
            if (irDeclarationWithVisibility.getVisibility() == DescriptorVisibilities.LOCAL && (irDeclarationWithVisibility instanceof IrFunction)) {
                return 1;
            }
            if (z && ((IrClass) irDeclarationWithVisibility).getKind() == ClassKind.ENUM_ENTRY) {
                return 0;
            }
            if ((irDeclarationWithVisibility instanceof IrField) && (correspondingPropertySymbol = (irField = (IrField) irDeclarationWithVisibility).getCorrespondingPropertySymbol()) != null && (owner = correspondingPropertySymbol.getOwner()) != null && owner.getIsExternal()) {
                IrPropertySymbol correspondingPropertySymbol2 = irField.getCorrespondingPropertySymbol();
                if (correspondingPropertySymbol2 == null || (owner3 = correspondingPropertySymbol2.getOwner()) == null || (setter = owner3.getGetter()) == null) {
                    IrPropertySymbol correspondingPropertySymbol3 = irField.getCorrespondingPropertySymbol();
                    setter = (correspondingPropertySymbol3 == null || (owner2 = correspondingPropertySymbol3.getOwner()) == null) ? null : owner2.getSetter();
                    if (setter == null) {
                        StringBuilder sb = new StringBuilder("No get/set method in SyntheticJavaPropertyDescriptor: ");
                        IrPropertySymbol correspondingPropertySymbol4 = irField.getCorrespondingPropertySymbol();
                        throw new IllegalStateException(sb.append(IrUtilsKt.ir2string(correspondingPropertySymbol4 != null ? correspondingPropertySymbol4.getOwner() : null)).toString().toString());
                    }
                }
                return Integer.valueOf(getVisibilityAccessFlag$default(setter, null, 1, null));
            }
            if ((irDeclarationWithVisibility instanceof IrSimpleFunction) && irDeclarationWithVisibility.getVisibility() == DescriptorVisibilities.PROTECTED) {
                List listAllOverridden$default = IrUtilsKt.allOverridden$default((IrOverridableDeclaration) irDeclarationWithVisibility, false, 1, null);
                if (!(listAllOverridden$default instanceof Collection) || !listAllOverridden$default.isEmpty()) {
                    Iterator it = listAllOverridden$default.iterator();
                    while (it.hasNext()) {
                        if (JvmIrUtilsKt.isJvmInterface(IrUtilsKt.getParentAsClass((IrSimpleFunction) it.next()))) {
                            return 1;
                        }
                    }
                }
            }
            return (DescriptorVisibilities.isPrivate(irDeclarationWithVisibility.getVisibility()) && z2 && IrUtilsKt.getParentAsClass(irDeclarationWithVisibility).getKind() == ClassKind.ENUM_ENTRY) ? 0 : null;
        }
        return 2;
    }

    private static final boolean isInlineOrContainedInInline(IrDeclaration irDeclaration) {
        while (irDeclaration != null) {
            if ((irDeclaration instanceof IrFunction) && ((IrFunction) irDeclaration).getIsInline()) {
                return true;
            }
            IrDeclarationParent parent = irDeclaration.getParent();
            irDeclaration = parent instanceof IrDeclaration ? (IrDeclaration) parent : null;
        }
        return false;
    }

    private static final boolean isInlineOnlyPrivateInBytecode(IrDeclarationWithVisibility irDeclarationWithVisibility) {
        return (irDeclarationWithVisibility instanceof IrFunction) && (JvmIrInlineUtilsKt.isInlineOnly(irDeclarationWithVisibility) || JvmIrInlineUtilsKt.isPrivateInlineSuspend((IrFunction) irDeclarationWithVisibility));
    }

    static {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (JavaToKotlinClassMap.PlatformMutabilityMapping platformMutabilityMapping : JavaToKotlinClassMap.INSTANCE.getMutabilityMappings()) {
            FqName fqNameAsSingleFqName = platformMutabilityMapping.getKotlinReadOnly().asSingleFqName();
            Intrinsics.checkNotNullExpressionValue(fqNameAsSingleFqName, "asSingleFqName(...)");
            linkedHashMap.put(fqNameAsSingleFqName, "kotlin/jvm/internal/markers/KMappedMarker");
            ClassId kotlinMutable = platformMutabilityMapping.getKotlinMutable();
            FqName fqNameAsSingleFqName2 = kotlinMutable.asSingleFqName();
            Intrinsics.checkNotNullExpressionValue(fqNameAsSingleFqName2, "asSingleFqName(...)");
            StringBuilder sb = new StringBuilder("kotlin/jvm/internal/markers/K");
            String strAsString = kotlinMutable.getRelativeClassName().asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            linkedHashMap.put(fqNameAsSingleFqName2, sb.append(StringsKt.replace$default(StringsKt.replace$default(strAsString, "MutableEntry", "Entry", false, 4, (Object) null), ".", "$", false, 4, (Object) null)).toString());
        }
        KOTLIN_MARKER_INTERFACES = linkedHashMap;
    }

    public static final JvmClassSignature mapClassSignature(IrTypeMapper irTypeMapper, IrClass irClass, Type type, boolean z) {
        Object next;
        Type typeMapSupertype;
        IrClass owner;
        Intrinsics.checkNotNullParameter(irTypeMapper, "<this>");
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        Intrinsics.checkNotNullParameter(type, "type");
        BothSignatureWriter bothSignatureWriter = new BothSignatureWriter(BothSignatureWriter.Mode.CLASS);
        BothSignatureWriter bothSignatureWriter2 = bothSignatureWriter;
        irTypeMapper.writeFormalTypeParameters(irClass.getTypeParameters(), bothSignatureWriter2);
        bothSignatureWriter.writeSuperclass();
        Iterator<T> it = irClass.getSuperTypes().iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            IrClass irClass2 = IrTypesKt.getClass((IrType) next);
            if (irClass2 != null && !JvmIrUtilsKt.isJvmInterface(irClass2)) {
                break;
            }
        }
        IrType irType = (IrType) next;
        if (irType == null) {
            bothSignatureWriter.writeClassBegin(AsmTypes.OBJECT_TYPE);
            bothSignatureWriter.writeClassEnd();
            typeMapSupertype = AsmTypes.OBJECT_TYPE;
        } else {
            typeMapSupertype = IrTypeMappingKt.mapSupertype(irTypeMapper, irType, bothSignatureWriter2);
        }
        bothSignatureWriter.writeSuperclassEnd();
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet();
        if (z) {
            List<IrType> superTypes = irClass.getSuperTypes();
            if (!(superTypes instanceof Collection) || !superTypes.isEmpty()) {
                for (IrType irType2 : superTypes) {
                    if (IrTypeUtilsKt.isSuspendFunction(irType2) || IrTypeUtilsKt.isKSuspendFunction(irType2)) {
                        linkedHashSet.add("kotlin/coroutines/jvm/internal/SuspendFunction");
                        break;
                    }
                }
            }
        }
        LinkedHashSet linkedHashSet2 = new LinkedHashSet();
        for (IrType irType3 : irClass.getSuperTypes()) {
            IrClassSymbol classOrNull = IrTypesKt.getClassOrNull(irType3);
            if (classOrNull != null && (owner = classOrNull.getOwner()) != null && JvmIrUtilsKt.isJvmInterface(owner)) {
                bothSignatureWriter.writeInterface();
                linkedHashSet2.add(IrTypeMappingKt.mapSupertype(irTypeMapper, irType3, bothSignatureWriter2).getInternalName());
                bothSignatureWriter.writeInterfaceEnd();
                Map<FqName, String> map = KOTLIN_MARKER_INTERFACES;
                FqName fqNameWhenAvailable = IrUtilsKt.getFqNameWhenAvailable(owner);
                Intrinsics.checkNotNull(fqNameWhenAvailable);
                org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(linkedHashSet, map.get(fqNameWhenAvailable));
            }
        }
        for (String str : linkedHashSet) {
            bothSignatureWriter.writeInterface();
            bothSignatureWriter.writeAsmType(Type.getObjectType(str));
            bothSignatureWriter.writeInterfaceEnd();
        }
        linkedHashSet2.addAll(linkedHashSet);
        return new JvmClassSignature(type.getInternalName(), typeMapSupertype.getInternalName(), new ArrayList(linkedHashSet2), bothSignatureWriter.makeJavaGenericSignature());
    }

    public static final int getVisibilityAccessFlagForClass(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        if (irClass.getKind() == ClassKind.ENUM_ENTRY) {
            return 0;
        }
        return (irClass.getVisibility() == DescriptorVisibilities.PUBLIC || irClass.getVisibility() == DescriptorVisibilities.PROTECTED || irClass.getVisibility() == DescriptorVisibilities.LOCAL || irClass.getVisibility() == DescriptorVisibilities.INTERNAL) ? 1 : 0;
    }

    public static final boolean isAnnotatedWithDeprecated(IrDeclaration irDeclaration) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        return AdditionalIrUtilsKt.hasAnnotation(irDeclaration.getAnnotations(), StandardNames.FqNames.deprecated);
    }

    public static final boolean isDeprecatedCallable(IrDeclaration irDeclaration, JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(irDeclaration, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        if (!isAnnotatedWithDeprecated(irDeclaration)) {
            List<IrConstructorCall> annotations = irDeclaration.getAnnotations();
            if (!(annotations instanceof Collection) || !annotations.isEmpty()) {
                Iterator<T> it = annotations.iterator();
                while (it.hasNext()) {
                    if (Intrinsics.areEqual(((IrConstructorCall) it.next()).getSymbol(), context.getIr().getSymbols().getJavaLangDeprecatedConstructorWithDeprecatedFlag())) {
                    }
                }
            }
            return false;
        }
        return true;
    }

    public static final boolean isDeprecatedFunction(IrFunction irFunction, JvmBackendContext context) {
        IrPropertySymbol correspondingPropertySymbol;
        IrProperty owner;
        Intrinsics.checkNotNullParameter(irFunction, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        if (Intrinsics.areEqual(irFunction.getOrigin(), JvmLoweredDeclarationOrigin.SYNTHETIC_METHOD_FOR_PROPERTY_OR_TYPEALIAS_ANNOTATIONS.INSTANCE) || isDeprecatedCallable(irFunction, context)) {
            return true;
        }
        IrSimpleFunction irSimpleFunction = irFunction instanceof IrSimpleFunction ? (IrSimpleFunction) irFunction : null;
        return !(irSimpleFunction == null || (correspondingPropertySymbol = irSimpleFunction.getCorrespondingPropertySymbol()) == null || (owner = correspondingPropertySymbol.getOwner()) == null || !isDeprecatedCallable(owner, context)) || isAccessorForDeprecatedPropertyImplementedByDelegation(irFunction) || isAccessorForDeprecatedJvmStaticProperty(irFunction, context);
    }

    private static final boolean isAccessorForDeprecatedPropertyImplementedByDelegation(IrFunction irFunction) {
        IrProperty owner;
        if (Intrinsics.areEqual(irFunction.getOrigin(), IrDeclarationOrigin.DELEGATED_MEMBER.INSTANCE) && (irFunction instanceof IrSimpleFunction)) {
            IrSimpleFunction irSimpleFunction = (IrSimpleFunction) irFunction;
            if (irSimpleFunction.getCorrespondingPropertySymbol() != null) {
                List<IrSimpleFunctionSymbol> overriddenSymbols = irSimpleFunction.getOverriddenSymbols();
                if (!(overriddenSymbols instanceof Collection) || !overriddenSymbols.isEmpty()) {
                    Iterator<T> it = overriddenSymbols.iterator();
                    while (it.hasNext()) {
                        IrPropertySymbol correspondingPropertySymbol = ((IrSimpleFunctionSymbol) it.next()).getOwner().getCorrespondingPropertySymbol();
                        if (correspondingPropertySymbol != null && (owner = correspondingPropertySymbol.getOwner()) != null && isAnnotatedWithDeprecated(owner)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private static final boolean isAccessorForDeprecatedJvmStaticProperty(IrFunction irFunction, JvmBackendContext jvmBackendContext) {
        IrProperty owner;
        if (!Intrinsics.areEqual(irFunction.getOrigin(), JvmLoweredDeclarationOrigin.JVM_STATIC_WRAPPER.INSTANCE)) {
            return false;
        }
        IrBody body = irFunction.getBody();
        IrExpressionBody irExpressionBody = body instanceof IrExpressionBody ? (IrExpressionBody) body : null;
        if (irExpressionBody == null) {
            throw new AssertionError("IrExpressionBody expected for JvmStatic wrapper:\n" + DumpIrTreeKt.dump$default(irFunction, null, 1, null));
        }
        IrExpression expression = irExpressionBody.getExpression();
        IrCall irCall = expression instanceof IrCall ? (IrCall) expression : null;
        if (irCall == null) {
            throw new AssertionError("IrCall expected inside JvmStatic wrapper:\n" + DumpIrTreeKt.dump$default(irFunction, null, 1, null));
        }
        IrPropertySymbol correspondingPropertySymbol = irCall.getSymbol().getOwner().getCorrespondingPropertySymbol();
        if (correspondingPropertySymbol == null || (owner = correspondingPropertySymbol.getOwner()) == null) {
            return false;
        }
        return isDeprecatedCallable(owner, jvmBackendContext);
    }

    public static final ReifiedTypeParametersUsages getReifiedTypeParameters(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "<this>");
        ReifiedTypeParametersUsages reifiedTypeParametersUsages = new ReifiedTypeParametersUsages();
        Iterator<IrType> it = irClass.getSuperTypes().iterator();
        while (it.hasNext()) {
            _get_reifiedTypeParameters_$processTypeParameters(reifiedTypeParametersUsages, it.next());
        }
        return reifiedTypeParametersUsages;
    }

    private static final void _get_reifiedTypeParameters_$processTypeParameters(ReifiedTypeParametersUsages reifiedTypeParametersUsages, IrType irType) {
        List<IrTypeArgument> listEmptyList;
        IrSimpleType irSimpleType = irType instanceof IrSimpleType ? (IrSimpleType) irType : null;
        if (irSimpleType == null || (listEmptyList = irSimpleType.getArguments()) == null) {
            listEmptyList = CollectionsKt.emptyList();
        }
        for (IrTypeArgument irTypeArgument : listEmptyList) {
            if (irTypeArgument instanceof IrTypeProjection) {
                IrType type = ((IrTypeProjection) irTypeArgument).getType();
                if (JvmIrTypeUtilsKt.isReifiedTypeParameter(type)) {
                    IrClassifierSymbol classifierOrFail = IrTypesKt.getClassifierOrFail(type);
                    Intrinsics.checkNotNull(classifierOrFail, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.symbols.IrTypeParameterSymbol");
                    String strAsString = ((IrTypeParameterSymbol) classifierOrFail).getOwner().getName().asString();
                    Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                    reifiedTypeParametersUsages.addUsedReifiedParameter(strAsString);
                } else {
                    _get_reifiedTypeParameters_$processTypeParameters(reifiedTypeParametersUsages, type);
                }
            }
        }
    }

    public static final FieldInsnNode generateExternalEntriesForEnumTypeIfNeeded(IrType type, ClassCodegen containingCodegen) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(containingCodegen, "containingCodegen");
        IrClass irClass = IrTypesKt.getClass(type);
        if (irClass == null || !JvmIrUtilsKt.isEnumClassWhichRequiresExternalEntries(irClass)) {
            return null;
        }
        IrField enumEntriesIntrinsicMappings = containingCodegen.getContext().getEnumEntriesIntrinsicMappingsCache().getEnumEntriesIntrinsicMappings(containingCodegen.getIrClass(), irClass);
        return new FieldInsnNode(178, IrTypeMappingKt.mapClass(containingCodegen.getTypeMapper(), IrUtilsKt.getParentAsClass(enumEntriesIntrinsicMappings)).getInternalName(), enumEntriesIntrinsicMappings.getName().asString(), AsmTypes.ENUM_ENTRIES.getDescriptor());
    }
}
