package org.jetbrains.kotlin.psi2ir.generators;

import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.ir.expressions.IrDelegatingConstructorCall;
import org.jetbrains.kotlin.ir.symbols.IrScriptSymbol;
import org.jetbrains.kotlin.ir.util.StubGeneratorExtensions;
import org.jetbrains.kotlin.psi.KtPureClassOrObject;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: GeneratorExtensions.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001:\u0001&B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0011\u001a\u0004\u0018\u00010\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\"\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0013\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\u0012\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\u0006\u0010\u0013\u001a\u00020\u0019H\u0016J\u0010\u0010\u001e\u001a\n\u0012\u0004\u0012\u00020 \u0018\u00010\u001fH\u0016J\u0010\u0010!\u001a\u00020\u00142\u0006\u0010\"\u001a\u00020\u0014H\u0016J \u0010#\u001a\u0012\u0012\u0004\u0012\u00020%\u0012\u0006\u0012\u0004\u0018\u00010%\u0018\u00010$2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\u0006R\u0014\u0010\t\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u0006R\u0014\u0010\u000b\u001a\u00020\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0006¨\u0006'"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorExtensions;", "Lorg/jetbrains/kotlin/ir/util/StubGeneratorExtensions;", "()V", "debugInfoOnlyOnVariablesInDestructuringDeclarations", "", "getDebugInfoOnlyOnVariablesInDestructuringDeclarations", "()Z", "lowerScriptToClass", "getLowerScriptToClass", "parametersAreAssignable", "getParametersAreAssignable", "samConversion", "Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorExtensions$SamConversion;", "getSamConversion", "()Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorExtensions$SamConversion;", "shouldPreventDeprecatedIntegerValueTypeLiteralConversion", "getShouldPreventDeprecatedIntegerValueTypeLiteralConversion", "computeFieldVisibility", "Lorg/jetbrains/kotlin/descriptors/DescriptorVisibility;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "createCustomSuperConstructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrDelegatingConstructorCall;", "ktPureClassOrObject", "Lorg/jetbrains/kotlin/psi/KtPureClassOrObject;", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "context", "Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorContext;", "getParentClassStaticScope", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "getPreviousScripts", "", "Lorg/jetbrains/kotlin/ir/symbols/IrScriptSymbol;", "remapDebuggerFieldPropertyDescriptor", "propertyDescriptor", "unwrapSyntheticJavaProperty", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "SamConversion", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class GeneratorExtensions extends StubGeneratorExtensions {
    public DescriptorVisibility computeFieldVisibility(PropertyDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return null;
    }

    public IrDelegatingConstructorCall createCustomSuperConstructorCall(KtPureClassOrObject ktPureClassOrObject, ClassDescriptor descriptor, GeneratorContext context) {
        Intrinsics.checkNotNullParameter(ktPureClassOrObject, "ktPureClassOrObject");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        return null;
    }

    public boolean getDebugInfoOnlyOnVariablesInDestructuringDeclarations() {
        return false;
    }

    public boolean getLowerScriptToClass() {
        return true;
    }

    public boolean getParametersAreAssignable() {
        return false;
    }

    public MemberScope getParentClassStaticScope(ClassDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return null;
    }

    public List<IrScriptSymbol> getPreviousScripts() {
        return null;
    }

    public boolean getShouldPreventDeprecatedIntegerValueTypeLiteralConversion() {
        return false;
    }

    public PropertyDescriptor remapDebuggerFieldPropertyDescriptor(PropertyDescriptor propertyDescriptor) {
        Intrinsics.checkNotNullParameter(propertyDescriptor, "propertyDescriptor");
        return propertyDescriptor;
    }

    public Pair<FunctionDescriptor, FunctionDescriptor> unwrapSyntheticJavaProperty(PropertyDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return null;
    }

    public SamConversion getSamConversion() {
        return SamConversion.INSTANCE;
    }

    /* JADX INFO: compiled from: GeneratorExtensions.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u0000 \b2\u00020\u0001:\u0001\bB\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\u0010\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0016¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorExtensions$SamConversion;", "", "()V", "isCarefulApproximationOfContravariantProjection", "", "isPlatformSamType", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/KotlinType;", "Instance", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static class SamConversion {

        /* JADX INFO: renamed from: Instance, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);

        public boolean isCarefulApproximationOfContravariantProjection() {
            return false;
        }

        public boolean isPlatformSamType(KotlinType type) {
            Intrinsics.checkNotNullParameter(type, "type");
            return false;
        }

        /* JADX INFO: renamed from: org.jetbrains.kotlin.psi2ir.generators.GeneratorExtensions$SamConversion$Instance, reason: from kotlin metadata */
        /* JADX INFO: compiled from: GeneratorExtensions.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorExtensions$SamConversion$Instance;", "Lorg/jetbrains/kotlin/psi2ir/generators/GeneratorExtensions$SamConversion;", "()V", "ir.psi2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Companion extends SamConversion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }
    }
}
