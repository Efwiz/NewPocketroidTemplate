package org.jetbrains.kotlin.ir.declarations;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.Named;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ScriptDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptorWithAccessors;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: MetadataSource.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0006\u000b\f\r\u000e\u000f\u0010B\u0007\b\u0004¢\u0006\u0002\u0010\u0002R\u0016\u0010\u0003\u001a\u0004\u0018\u00010\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0016\u0010\u0007\u001a\u0004\u0018\u00010\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n\u0082\u0001\u0006\u0011\u0012\u0013\u0014\u0015\u0016¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/DescriptorMetadataSource;", "Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;", "()V", "descriptor", "Lorg/jetbrains/kotlin/descriptors/Named;", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/Named;", "name", "Lorg/jetbrains/kotlin/name/Name;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "Class", "File", "Function", "LocalDelegatedProperty", "Property", "Script", "Lorg/jetbrains/kotlin/ir/declarations/DescriptorMetadataSource$Class;", "Lorg/jetbrains/kotlin/ir/declarations/DescriptorMetadataSource$File;", "Lorg/jetbrains/kotlin/ir/declarations/DescriptorMetadataSource$Function;", "Lorg/jetbrains/kotlin/ir/declarations/DescriptorMetadataSource$LocalDelegatedProperty;", "Lorg/jetbrains/kotlin/ir/declarations/DescriptorMetadataSource$Property;", "Lorg/jetbrains/kotlin/ir/declarations/DescriptorMetadataSource$Script;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class DescriptorMetadataSource implements MetadataSource {
    public /* synthetic */ DescriptorMetadataSource(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public Named getDescriptor() {
        return null;
    }

    private DescriptorMetadataSource() {
    }

    @Override // org.jetbrains.kotlin.ir.declarations.MetadataSource
    public Name getName() {
        Named descriptor = getDescriptor();
        if (descriptor != null) {
            return descriptor.getShortName();
        }
        return null;
    }

    /* JADX INFO: compiled from: MetadataSource.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0012\n\u0002\b\u0005\u0018\u00002\u00020\u00012\u00020\u0002B\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\u0002\u0010\u0006R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/DescriptorMetadataSource$File;", "Lorg/jetbrains/kotlin/ir/declarations/DescriptorMetadataSource;", "Lorg/jetbrains/kotlin/ir/declarations/MetadataSource$File;", "descriptors", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "(Ljava/util/List;)V", "getDescriptors", "()Ljava/util/List;", "serializedIr", "", "getSerializedIr", "()[B", "setSerializedIr", "([B)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class File extends DescriptorMetadataSource implements MetadataSource.File {
        private final List<DeclarationDescriptor> descriptors;
        private byte[] serializedIr;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public File(List<? extends DeclarationDescriptor> descriptors) {
            super(null);
            Intrinsics.checkNotNullParameter(descriptors, "descriptors");
            this.descriptors = descriptors;
        }

        public final List<DeclarationDescriptor> getDescriptors() {
            return this.descriptors;
        }

        @Override // org.jetbrains.kotlin.ir.declarations.MetadataSource.File
        public byte[] getSerializedIr() {
            return this.serializedIr;
        }

        @Override // org.jetbrains.kotlin.ir.declarations.MetadataSource.File
        public void setSerializedIr(byte[] bArr) {
            this.serializedIr = bArr;
        }
    }

    /* JADX INFO: compiled from: MetadataSource.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0012\n\u0002\b\u0005\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001c\u0010\b\u001a\u0004\u0018\u00010\tX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/DescriptorMetadataSource$Class;", "Lorg/jetbrains/kotlin/ir/declarations/DescriptorMetadataSource;", "Lorg/jetbrains/kotlin/ir/declarations/MetadataSource$Class;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;)V", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "serializedIr", "", "getSerializedIr", "()[B", "setSerializedIr", "([B)V", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Class extends DescriptorMetadataSource implements MetadataSource.Class {
        private final ClassDescriptor descriptor;
        private byte[] serializedIr;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Class(ClassDescriptor descriptor) {
            super(null);
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            this.descriptor = descriptor;
        }

        @Override // org.jetbrains.kotlin.ir.declarations.DescriptorMetadataSource
        public ClassDescriptor getDescriptor() {
            return this.descriptor;
        }

        @Override // org.jetbrains.kotlin.ir.declarations.MetadataSource.Class
        public byte[] getSerializedIr() {
            return this.serializedIr;
        }

        @Override // org.jetbrains.kotlin.ir.declarations.MetadataSource.Class
        public void setSerializedIr(byte[] bArr) {
            this.serializedIr = bArr;
        }
    }

    /* JADX INFO: compiled from: MetadataSource.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/DescriptorMetadataSource$Script;", "Lorg/jetbrains/kotlin/ir/declarations/DescriptorMetadataSource;", "Lorg/jetbrains/kotlin/ir/declarations/MetadataSource$Script;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ScriptDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/ScriptDescriptor;)V", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ScriptDescriptor;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Script extends DescriptorMetadataSource implements MetadataSource.Script {
        private final ScriptDescriptor descriptor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Script(ScriptDescriptor descriptor) {
            super(null);
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            this.descriptor = descriptor;
        }

        @Override // org.jetbrains.kotlin.ir.declarations.DescriptorMetadataSource
        public ScriptDescriptor getDescriptor() {
            return this.descriptor;
        }
    }

    /* JADX INFO: compiled from: MetadataSource.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/DescriptorMetadataSource$Function;", "Lorg/jetbrains/kotlin/ir/declarations/DescriptorMetadataSource;", "Lorg/jetbrains/kotlin/ir/declarations/MetadataSource$Function;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;)V", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Function extends DescriptorMetadataSource implements MetadataSource.Function {
        private final FunctionDescriptor descriptor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Function(FunctionDescriptor descriptor) {
            super(null);
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            this.descriptor = descriptor;
        }

        @Override // org.jetbrains.kotlin.ir.declarations.DescriptorMetadataSource
        public FunctionDescriptor getDescriptor() {
            return this.descriptor;
        }
    }

    /* JADX INFO: compiled from: MetadataSource.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/DescriptorMetadataSource$Property;", "Lorg/jetbrains/kotlin/ir/declarations/DescriptorMetadataSource;", "Lorg/jetbrains/kotlin/ir/declarations/MetadataSource$Property;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;)V", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "isConst", "", "()Z", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Property extends DescriptorMetadataSource implements MetadataSource.Property {
        private final PropertyDescriptor descriptor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Property(PropertyDescriptor descriptor) {
            super(null);
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            this.descriptor = descriptor;
        }

        @Override // org.jetbrains.kotlin.ir.declarations.DescriptorMetadataSource
        public PropertyDescriptor getDescriptor() {
            return this.descriptor;
        }

        @Override // org.jetbrains.kotlin.ir.declarations.MetadataSource.Property
        public boolean isConst() {
            return getDescriptor().isConst();
        }
    }

    /* JADX INFO: compiled from: MetadataSource.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/DescriptorMetadataSource$LocalDelegatedProperty;", "Lorg/jetbrains/kotlin/ir/declarations/DescriptorMetadataSource;", "Lorg/jetbrains/kotlin/ir/declarations/MetadataSource$Property;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptorWithAccessors;", "(Lorg/jetbrains/kotlin/descriptors/VariableDescriptorWithAccessors;)V", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/VariableDescriptorWithAccessors;", "isConst", "", "()Z", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class LocalDelegatedProperty extends DescriptorMetadataSource implements MetadataSource.Property {
        private final VariableDescriptorWithAccessors descriptor;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public LocalDelegatedProperty(VariableDescriptorWithAccessors descriptor) {
            super(null);
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            this.descriptor = descriptor;
        }

        @Override // org.jetbrains.kotlin.ir.declarations.DescriptorMetadataSource
        public VariableDescriptorWithAccessors getDescriptor() {
            return this.descriptor;
        }

        @Override // org.jetbrains.kotlin.ir.declarations.MetadataSource.Property
        public boolean isConst() {
            return getDescriptor().isConst();
        }
    }
}
