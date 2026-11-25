package org.jetbrains.kotlin.backend.jvm.codegen;

import com.badlogic.gdx.graphics.GL20;
import com.reandroid.arsc.chunk.OverlayablePolicy;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.ir.JvmIrUtilsKt;
import org.jetbrains.kotlin.codegen.DescriptorAsmUtil;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.util.AdditionalIrUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.name.JvmNames;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: ClassCodegen.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000@\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u001c\u0010\r\u001a\u00020\u0001*\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0002\u001a\u0014\u0010\u0003\u001a\u00020\u0001*\u00020\u00132\u0006\u0010\u0011\u001a\u00020\u0012H\u0002\u001a\u0014\u0010\u0014\u001a\u00020\u0001*\u00020\u00132\u0006\u0010\u0011\u001a\u00020\u0012H\u0002\u001a\u0014\u0010\u0015\u001a\u00020\n*\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u0012H\u0002\"\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00028BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00058BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0006\"\u0018\u0010\u0000\u001a\u00020\u0001*\u00020\u00078BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0003\u0010\b\"\u0018\u0010\t\u001a\u00020\n*\u00020\u000b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\f¨\u0006\u0016"}, d2 = {OverlayablePolicy.NAME_flags, "", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "getFlags", "(Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;)I", "Lorg/jetbrains/kotlin/descriptors/Modality;", "(Lorg/jetbrains/kotlin/descriptors/Modality;)I", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;)I", "isAnonymousClass", "", "Lorg/jetbrains/org/objectweb/asm/Type;", "(Lorg/jetbrains/org/objectweb/asm/Type;)Z", "computeFieldFlags", "Lorg/jetbrains/kotlin/ir/declarations/IrField;", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "getSynthAccessFlag", "isPrivateCompanionFieldInInterface", "backend.jvm.codegen"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ClassCodegenKt {

    /* JADX INFO: compiled from: ClassCodegen.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Modality.values().length];
            try {
                iArr[Modality.ABSTRACT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Modality.SEALED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[Modality.FINAL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[Modality.OPEN.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int getFlags(IrClass irClass, LanguageVersionSettings languageVersionSettings) {
        int flags;
        int synthAccessFlag = getSynthAccessFlag(irClass, languageVersionSettings) | getFlags(irClass.getOrigin()) | IrCodegenUtilsKt.getVisibilityAccessFlagForClass(irClass) | (IrCodegenUtilsKt.isAnnotatedWithDeprecated(irClass) ? 131072 : 0);
        if (IrUtilsKt.isAnnotationClass(irClass)) {
            flags = GL20.GL_NEAREST;
        } else if (IrUtilsKt.isInterface(irClass)) {
            flags = 1536;
        } else if (IrUtilsKt.isEnumClass(irClass)) {
            flags = getFlags(irClass.getModality()) | 16416;
        } else {
            flags = IrUtilsKt.hasAnnotation(irClass, JvmNames.JVM_RECORD_ANNOTATION_FQ_NAME) ? getFlags(irClass.getModality()) | 65568 : getFlags(irClass.getModality()) | 32;
        }
        return flags | synthAccessFlag;
    }

    private static final int getSynthAccessFlag(IrClass irClass, LanguageVersionSettings languageVersionSettings) {
        if (IrUtilsKt.hasAnnotation(irClass, JvmNames.JVM_SYNTHETIC_ANNOTATION_FQ_NAME)) {
            return 4096;
        }
        return (Intrinsics.areEqual(irClass.getOrigin(), IrDeclarationOrigin.GENERATED_SAM_IMPLEMENTATION.INSTANCE) && languageVersionSettings.supportsFeature(LanguageFeature.SamWrapperClassesAreSynthetic)) ? 4096 : 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int computeFieldFlags(IrField irField, JvmBackendContext jvmBackendContext, LanguageVersionSettings languageVersionSettings) {
        IrPropertySymbol correspondingPropertySymbol;
        IrProperty owner;
        IrField irField2 = irField;
        return ((IrCodegenUtilsKt.isDeprecatedCallable(irField, jvmBackendContext) || !((correspondingPropertySymbol = irField.getCorrespondingPropertySymbol()) == null || (owner = correspondingPropertySymbol.getOwner()) == null || !IrCodegenUtilsKt.isDeprecatedCallable(owner, jvmBackendContext))) ? 131072 : 0) | getFlags(irField.getOrigin()) | getFlags(irField.getVisibility()) | (irField.getIsFinal() ? 16 : 0) | (irField.getIsStatic() ? 8 : 0) | (IrUtilsKt.hasAnnotation(irField2, JvmNames.VOLATILE_ANNOTATION_FQ_NAME) ? 64 : 0) | (IrUtilsKt.hasAnnotation(irField2, JvmNames.TRANSIENT_ANNOTATION_FQ_NAME) ? 128 : 0) | ((IrUtilsKt.hasAnnotation(irField2, JvmNames.JVM_SYNTHETIC_ANNOTATION_FQ_NAME) || isPrivateCompanionFieldInInterface(irField, languageVersionSettings)) ? 4096 : 0);
    }

    private static final boolean isPrivateCompanionFieldInInterface(IrField irField, LanguageVersionSettings languageVersionSettings) {
        if (Intrinsics.areEqual(irField.getOrigin(), IrDeclarationOrigin.FIELD_FOR_OBJECT_INSTANCE.INSTANCE) && languageVersionSettings.supportsFeature(LanguageFeature.ProperVisibilityForCompanionObjectInstanceField)) {
            IrField irField2 = irField;
            if (JvmIrUtilsKt.isJvmInterface(IrUtilsKt.getParentAsClass(irField2))) {
                IrClass irClassCompanionObject = AdditionalIrUtilsKt.companionObject(IrUtilsKt.getParentAsClass(irField2));
                Intrinsics.checkNotNull(irClassCompanionObject);
                if (DescriptorVisibilities.isPrivate(irClassCompanionObject.getVisibility())) {
                    return true;
                }
            }
        }
        return false;
    }

    private static final int getFlags(IrDeclarationOrigin irDeclarationOrigin) {
        return (irDeclarationOrigin.getIsSynthetic() ? 4096 : 0) | (Intrinsics.areEqual(irDeclarationOrigin, IrDeclarationOrigin.FIELD_FOR_ENUM_ENTRY.INSTANCE) ? 16384 : 0);
    }

    private static final int getFlags(Modality modality) {
        int i = WhenMappings.$EnumSwitchMapping$0[modality.ordinal()];
        if (i == 1 || i == 2) {
            return 1024;
        }
        if (i == 3) {
            return 16;
        }
        if (i == 4) {
            return 0;
        }
        throw new AssertionError("Unsupported modality " + modality);
    }

    private static final int getFlags(DescriptorVisibility descriptorVisibility) {
        Integer visibilityAccessFlag = DescriptorAsmUtil.getVisibilityAccessFlag(descriptorVisibility);
        if (visibilityAccessFlag != null) {
            return visibilityAccessFlag.intValue();
        }
        throw new AssertionError("Unsupported visibility " + descriptorVisibility);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isAnonymousClass(Type type) {
        String internalName = type.getInternalName();
        Intrinsics.checkNotNullExpressionValue(internalName, "getInternalName(...)");
        return StringsKt.toIntOrNull(StringsKt.substringAfterLast(internalName, "$", "")) != null;
    }
}
