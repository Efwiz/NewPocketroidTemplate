package org.jetbrains.kotlin.ir.backend.jvm.serialization;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.backend.common.serialization.mangle.KotlinExportChecker;
import org.jetbrains.kotlin.backend.common.serialization.mangle.KotlinMangleComputer;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleMode;
import org.jetbrains.kotlin.backend.common.serialization.mangle.descriptor.DescriptorBasedKotlinManglerImpl;
import org.jetbrains.kotlin.backend.common.serialization.mangle.descriptor.DescriptorExportCheckerVisitor;
import org.jetbrains.kotlin.backend.common.serialization.mangle.descriptor.DescriptorMangleComputer;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.idea.MainFunctionDetector;
import org.jetbrains.kotlin.load.java.lazy.descriptors.JavaDescriptorUtilKt;
import org.jetbrains.kotlin.load.java.typeEnhancement.TypeEnhancementUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.checker.SimpleClassicTypeSystemContext;

/* JADX INFO: compiled from: JvmMangler.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0002\u000e\u000fB\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\tH\u0016J\u001e\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\b\u001a\u00020\tH\u0016R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/jvm/serialization/JvmDescriptorMangler;", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/descriptor/DescriptorBasedKotlinManglerImpl;", "mainDetector", "Lorg/jetbrains/kotlin/idea/MainFunctionDetector;", "(Lorg/jetbrains/kotlin/idea/MainFunctionDetector;)V", "getExportChecker", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/KotlinExportChecker;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "compatibleMode", "", "getMangleComputer", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/KotlinMangleComputer;", "mode", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/MangleMode;", "ExportChecker", "JvmDescriptorManglerComputer", "ir.serialization.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmDescriptorMangler extends DescriptorBasedKotlinManglerImpl {
    private final MainFunctionDetector mainDetector;

    /* JADX INFO: compiled from: JvmMangler.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\f\u0010\u0003\u001a\u00020\u0004*\u00020\u0005H\u0016¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/jvm/serialization/JvmDescriptorMangler$ExportChecker;", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/descriptor/DescriptorExportCheckerVisitor;", "()V", "isPlatformSpecificExported", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "ir.serialization.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class ExportChecker extends DescriptorExportCheckerVisitor {
        public static final ExportChecker INSTANCE = new ExportChecker();

        @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.KotlinExportChecker
        public boolean isPlatformSpecificExported(DeclarationDescriptor declarationDescriptor) {
            Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
            return true;
        }

        private ExportChecker() {
        }
    }

    public JvmDescriptorMangler(MainFunctionDetector mainFunctionDetector) {
        this.mainDetector = mainFunctionDetector;
    }

    /* JADX INFO: compiled from: JvmMangler.kt */
    @Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B#\u0012\n\u0010\u0002\u001a\u00060\u0003j\u0002`\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0014J\u0010\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\bH\u0016J\u0010\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\rH\u0002J\u001c\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\n\u0010\u0016\u001a\u00060\u0003j\u0002`\u0004H\u0014J\u0010\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u0011\u001a\u00020\u0018H\u0014J\u000e\u0010\u0019\u001a\u0004\u0018\u00010\u001a*\u00020\rH\u0014J\u000e\u0010\u0019\u001a\u0004\u0018\u00010\u001a*\u00020\u001bH\u0016R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/jvm/serialization/JvmDescriptorMangler$JvmDescriptorManglerComputer;", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/descriptor/DescriptorMangleComputer;", "builder", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "mainDetector", "Lorg/jetbrains/kotlin/idea/MainFunctionDetector;", "mode", "Lorg/jetbrains/kotlin/backend/common/serialization/mangle/MangleMode;", "(Ljava/lang/StringBuilder;Lorg/jetbrains/kotlin/idea/MainFunctionDetector;Lorg/jetbrains/kotlin/backend/common/serialization/mangle/MangleMode;)V", "addReturnTypeSpecialCase", "", "function", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "newMode", "isMainFunction", "descriptor", "mangleTypePlatformSpecific", "", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "tBuilder", "visitModuleDeclaration", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "platformSpecificSuffix", "", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "ir.serialization.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class JvmDescriptorManglerComputer extends DescriptorMangleComputer {
        private final MainFunctionDetector mainDetector;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
        public boolean addReturnTypeSpecialCase(FunctionDescriptor function) {
            Intrinsics.checkNotNullParameter(function, "function");
            return true;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.descriptor.DescriptorMangleComputer
        protected void visitModuleDeclaration(ModuleDescriptor descriptor) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public JvmDescriptorManglerComputer(StringBuilder builder, MainFunctionDetector mainFunctionDetector, MangleMode mode) {
            super(builder, mode);
            Intrinsics.checkNotNullParameter(builder, "builder");
            Intrinsics.checkNotNullParameter(mode, "mode");
            this.mainDetector = mainFunctionDetector;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.descriptor.DescriptorMangleComputer, org.jetbrains.kotlin.backend.common.serialization.mangle.KotlinMangleComputer
        public DescriptorMangleComputer copy(MangleMode newMode) {
            Intrinsics.checkNotNullParameter(newMode, "newMode");
            return new JvmDescriptorManglerComputer(getBuilder(), this.mainDetector, newMode);
        }

        private final boolean isMainFunction(FunctionDescriptor descriptor) {
            MainFunctionDetector mainFunctionDetector = this.mainDetector;
            return mainFunctionDetector != null && MainFunctionDetector.isMain$default(mainFunctionDetector, descriptor, false, false, false, 14, null);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
        public String platformSpecificSuffix(FunctionDescriptor functionDescriptor) {
            Intrinsics.checkNotNullParameter(functionDescriptor, "<this>");
            if (isMainFunction(functionDescriptor)) {
                return functionDescriptor.getSourceElement().getContainingFile().getName_();
            }
            return null;
        }

        @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.descriptor.DescriptorMangleComputer
        public String platformSpecificSuffix(PropertyDescriptor propertyDescriptor) {
            Intrinsics.checkNotNullParameter(propertyDescriptor, "<this>");
            if (JavaDescriptorUtilKt.isJavaField(propertyDescriptor)) {
                return MangleConstant.JAVA_FIELD_SUFFIX;
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.BaseKotlinMangleComputer
        public void mangleTypePlatformSpecific(KotlinType type, StringBuilder tBuilder) {
            Intrinsics.checkNotNullParameter(type, "type");
            Intrinsics.checkNotNullParameter(tBuilder, "tBuilder");
            if (TypeEnhancementUtilsKt.hasEnhancedNullability(SimpleClassicTypeSystemContext.INSTANCE, type)) {
                appendSignature(tBuilder, MangleConstant.ENHANCED_NULLABILITY_MARK);
            }
        }
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.AbstractKotlinMangler
    public KotlinExportChecker<DeclarationDescriptor> getExportChecker(boolean compatibleMode) {
        return ExportChecker.INSTANCE;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.mangle.AbstractKotlinMangler
    public KotlinMangleComputer<DeclarationDescriptor> getMangleComputer(MangleMode mode, boolean compatibleMode) {
        Intrinsics.checkNotNullParameter(mode, "mode");
        return new JvmDescriptorManglerComputer(new StringBuilder(256), this.mainDetector, mode);
    }
}
