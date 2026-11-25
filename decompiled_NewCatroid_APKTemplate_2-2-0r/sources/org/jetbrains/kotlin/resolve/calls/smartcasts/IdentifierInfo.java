package org.jetbrains.kotlin.resolve.calls.smartcasts;

import com.google.android.gms.cast.MediaError;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.lexer.KtToken;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.resolve.DescriptorEquivalenceForOverrides;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;

/* JADX INFO: compiled from: IdentifierInfo.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000e\bf\u0018\u00002\u00020\u0001:\u000b\n\u000b\f\r\u000e\u000f\u0010\u0011\u0012\u0013\u0014R\u0014\u0010\u0002\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0015À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/smartcasts/IdentifierInfo;", "", "canBeBound", "", "getCanBeBound", "()Z", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValue$Kind;", "getKind", "()Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValue$Kind;", MediaError.ERROR_TYPE_ERROR, "EnumEntry", "Expression", "NO", "NULL", "PackageOrClass", "PostfixIdentifierInfo", "Qualified", "Receiver", "SafeCast", "Variable", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface IdentifierInfo {
    default boolean getCanBeBound() {
        return false;
    }

    default DataFlowValue.Kind getKind() {
        return DataFlowValue.Kind.OTHER;
    }

    /* JADX INFO: compiled from: IdentifierInfo.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/smartcasts/IdentifierInfo$NO;", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/IdentifierInfo;", "()V", "toString", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class NO implements IdentifierInfo {
        public static final NO INSTANCE = new NO();

        private NO() {
        }

        public String toString() {
            return "NO_IDENTIFIER_INFO";
        }
    }

    /* JADX INFO: compiled from: IdentifierInfo.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/smartcasts/IdentifierInfo$NULL;", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/IdentifierInfo;", "()V", "toString", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class NULL implements IdentifierInfo {
        public static final NULL INSTANCE = new NULL();

        private NULL() {
        }

        public String toString() {
            return "NULL";
        }
    }

    /* JADX INFO: compiled from: IdentifierInfo.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/smartcasts/IdentifierInfo$ERROR;", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/IdentifierInfo;", "()V", "toString", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ERROR implements IdentifierInfo {
        public static final ERROR INSTANCE = new ERROR();

        private ERROR() {
        }

        public String toString() {
            return MediaError.ERROR_TYPE_ERROR;
        }
    }

    /* JADX INFO: compiled from: IdentifierInfo.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\u0013\u0010\u0013\u001a\u00020\f2\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0096\u0002J\b\u0010\u0016\u001a\u00020\u0017H\u0016J\b\u0010\u0018\u001a\u00020\u0019H\u0016R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/smartcasts/IdentifierInfo$Variable;", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/IdentifierInfo;", "variable", "Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValue$Kind;", "bound", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValue;", "(Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValue$Kind;Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValue;)V", "getBound", "()Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValue;", "canBeBound", "", "getCanBeBound", "()Z", "getKind", "()Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValue$Kind;", "getVariable", "()Lorg/jetbrains/kotlin/descriptors/VariableDescriptor;", Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "", "toString", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Variable implements IdentifierInfo {
        private final DataFlowValue bound;
        private final DataFlowValue.Kind kind;
        private final VariableDescriptor variable;

        public Variable(VariableDescriptor variable, DataFlowValue.Kind kind, DataFlowValue dataFlowValue) {
            Intrinsics.checkNotNullParameter(variable, "variable");
            Intrinsics.checkNotNullParameter(kind, "kind");
            this.variable = variable;
            this.kind = kind;
            this.bound = dataFlowValue;
        }

        public final VariableDescriptor getVariable() {
            return this.variable;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.smartcasts.IdentifierInfo
        public DataFlowValue.Kind getKind() {
            return this.kind;
        }

        public final DataFlowValue getBound() {
            return this.bound;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.smartcasts.IdentifierInfo
        public boolean getCanBeBound() {
            return getKind() == DataFlowValue.Kind.STABLE_VALUE;
        }

        public boolean equals(Object other) {
            return (other instanceof Variable) && DescriptorEquivalenceForOverrides.areCallableDescriptorsEquivalent$default(DescriptorEquivalenceForOverrides.INSTANCE, this.variable, ((Variable) other).variable, true, false, false, KotlinTypeRefiner.Default.INSTANCE, 24, null);
        }

        public int hashCode() {
            return (this.variable.getShortName().hashCode() * 31) + this.variable.getContainingDeclaration().getOriginal().hashCode();
        }

        public String toString() {
            return this.variable.toString();
        }
    }

    /* JADX INFO: compiled from: IdentifierInfo.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\b\u0010\u0013\u001a\u00020\u0014H\u0016R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/smartcasts/IdentifierInfo$Receiver;", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/IdentifierInfo;", "value", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "(Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;)V", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValue$Kind;", "getKind", "()Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValue$Kind;", "getValue", "()Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValue;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class Receiver implements IdentifierInfo {
        private final DataFlowValue.Kind kind;
        private final ReceiverValue value;

        public static /* synthetic */ Receiver copy$default(Receiver receiver, ReceiverValue receiverValue, int i, Object obj) {
            if ((i & 1) != 0) {
                receiverValue = receiver.value;
            }
            return receiver.copy(receiverValue);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final ReceiverValue getValue() {
            return this.value;
        }

        public final Receiver copy(ReceiverValue value) {
            Intrinsics.checkNotNullParameter(value, "value");
            return new Receiver(value);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof Receiver) && Intrinsics.areEqual(this.value, ((Receiver) other).value);
        }

        public int hashCode() {
            return this.value.hashCode();
        }

        public Receiver(ReceiverValue value) {
            Intrinsics.checkNotNullParameter(value, "value");
            this.value = value;
            this.kind = DataFlowValue.Kind.STABLE_VALUE;
        }

        public final ReceiverValue getValue() {
            return this.value;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.smartcasts.IdentifierInfo
        public DataFlowValue.Kind getKind() {
            return this.kind;
        }

        public String toString() {
            return this.value.toString();
        }
    }

    /* JADX INFO: compiled from: IdentifierInfo.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\b\u0010\u0013\u001a\u00020\u0014H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/smartcasts/IdentifierInfo$PackageOrClass;", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/IdentifierInfo;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;)V", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValue$Kind;", "getKind", "()Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValue$Kind;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class PackageOrClass implements IdentifierInfo {
        private final DeclarationDescriptor descriptor;
        private final DataFlowValue.Kind kind;

        public static /* synthetic */ PackageOrClass copy$default(PackageOrClass packageOrClass, DeclarationDescriptor declarationDescriptor, int i, Object obj) {
            if ((i & 1) != 0) {
                declarationDescriptor = packageOrClass.descriptor;
            }
            return packageOrClass.copy(declarationDescriptor);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final DeclarationDescriptor getDescriptor() {
            return this.descriptor;
        }

        public final PackageOrClass copy(DeclarationDescriptor descriptor) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            return new PackageOrClass(descriptor);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof PackageOrClass) && Intrinsics.areEqual(this.descriptor, ((PackageOrClass) other).descriptor);
        }

        public int hashCode() {
            return this.descriptor.hashCode();
        }

        public PackageOrClass(DeclarationDescriptor descriptor) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            this.descriptor = descriptor;
            this.kind = DataFlowValue.Kind.STABLE_VALUE;
        }

        public final DeclarationDescriptor getDescriptor() {
            return this.descriptor;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.smartcasts.IdentifierInfo
        public DataFlowValue.Kind getKind() {
            return this.kind;
        }

        public String toString() {
            return this.descriptor.toString();
        }
    }

    /* JADX INFO: compiled from: IdentifierInfo.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/smartcasts/IdentifierInfo$EnumEntry;", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/IdentifierInfo;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "(Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;)V", "getDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValue$Kind;", "getKind", "()Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValue$Kind;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class EnumEntry implements IdentifierInfo {
        private final ClassDescriptor descriptor;
        private final DataFlowValue.Kind kind;

        public static /* synthetic */ EnumEntry copy$default(EnumEntry enumEntry, ClassDescriptor classDescriptor, int i, Object obj) {
            if ((i & 1) != 0) {
                classDescriptor = enumEntry.descriptor;
            }
            return enumEntry.copy(classDescriptor);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final ClassDescriptor getDescriptor() {
            return this.descriptor;
        }

        public final EnumEntry copy(ClassDescriptor descriptor) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            return new EnumEntry(descriptor);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof EnumEntry) && Intrinsics.areEqual(this.descriptor, ((EnumEntry) other).descriptor);
        }

        public int hashCode() {
            return this.descriptor.hashCode();
        }

        public String toString() {
            return "EnumEntry(descriptor=" + this.descriptor + ')';
        }

        public EnumEntry(ClassDescriptor descriptor) {
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            this.descriptor = descriptor;
            this.kind = DataFlowValue.Kind.STABLE_VALUE;
        }

        public final ClassDescriptor getDescriptor() {
            return this.descriptor;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.smartcasts.IdentifierInfo
        public DataFlowValue.Kind getKind() {
            return this.kind;
        }
    }

    /* JADX INFO: compiled from: IdentifierInfo.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0001\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\u0013\u0010\u0016\u001a\u00020\u00052\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0096\u0002J\b\u0010\u0019\u001a\u00020\u001aH\u0016J\b\u0010\u001b\u001a\u00020\u001cH\u0016R\u0014\u0010\t\u001a\u00020\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000bR\u0011\u0010\u0003\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0011¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/smartcasts/IdentifierInfo$Qualified;", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/IdentifierInfo;", "receiverInfo", "selectorInfo", "safe", "", "receiverType", "Lorg/jetbrains/kotlin/types/KotlinType;", "(Lorg/jetbrains/kotlin/resolve/calls/smartcasts/IdentifierInfo;Lorg/jetbrains/kotlin/resolve/calls/smartcasts/IdentifierInfo;ZLorg/jetbrains/kotlin/types/KotlinType;)V", "canBeBound", "getCanBeBound", "()Z", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValue$Kind;", "getKind", "()Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValue$Kind;", "getReceiverInfo", "()Lorg/jetbrains/kotlin/resolve/calls/smartcasts/IdentifierInfo;", "getReceiverType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "getSafe", "getSelectorInfo", Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "", "toString", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Qualified implements IdentifierInfo {
        private final IdentifierInfo receiverInfo;
        private final KotlinType receiverType;
        private final boolean safe;
        private final IdentifierInfo selectorInfo;

        public Qualified(IdentifierInfo receiverInfo, IdentifierInfo selectorInfo, boolean z, KotlinType kotlinType) {
            Intrinsics.checkNotNullParameter(receiverInfo, "receiverInfo");
            Intrinsics.checkNotNullParameter(selectorInfo, "selectorInfo");
            this.receiverInfo = receiverInfo;
            this.selectorInfo = selectorInfo;
            this.safe = z;
            this.receiverType = kotlinType;
        }

        public final IdentifierInfo getReceiverInfo() {
            return this.receiverInfo;
        }

        public final IdentifierInfo getSelectorInfo() {
            return this.selectorInfo;
        }

        public final boolean getSafe() {
            return this.safe;
        }

        public final KotlinType getReceiverType() {
            return this.receiverType;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.smartcasts.IdentifierInfo
        public DataFlowValue.Kind getKind() {
            return this.receiverInfo.getKind() == DataFlowValue.Kind.STABLE_VALUE ? this.selectorInfo.getKind() : DataFlowValue.Kind.OTHER;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.smartcasts.IdentifierInfo
        public boolean getCanBeBound() {
            return this.receiverInfo.getCanBeBound();
        }

        public boolean equals(Object other) {
            if (other instanceof Qualified) {
                Qualified qualified = (Qualified) other;
                if (Intrinsics.areEqual(this.receiverInfo, qualified.receiverInfo) && Intrinsics.areEqual(this.selectorInfo, qualified.selectorInfo)) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return (this.receiverInfo.hashCode() * 31) + this.selectorInfo.hashCode();
        }

        public String toString() {
            return this.receiverInfo + (this.safe ? "?." : ".") + this.selectorInfo;
        }
    }

    /* JADX INFO: compiled from: IdentifierInfo.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004¢\u0006\u0002\u0010\u0006J\t\u0010\u0014\u001a\u00020\u0001HÆ\u0003J\u000b\u0010\u0015\u001a\u0004\u0018\u00010\u0004HÆ\u0003J\u000b\u0010\u0016\u001a\u0004\u0018\u00010\u0004HÆ\u0003J+\u0010\u0017\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00012\n\b\u0002\u0010\u0003\u001a\u0004\u0018\u00010\u00042\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0004HÆ\u0001J\u0013\u0010\u0018\u001a\u00020\b2\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aHÖ\u0003J\t\u0010\u001b\u001a\u00020\u001cHÖ\u0001J\b\u0010\u001d\u001a\u00020\u001eH\u0016R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0013\u0010\u0003\u001a\u0004\u0018\u00010\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0012¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/smartcasts/IdentifierInfo$SafeCast;", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/IdentifierInfo;", "subjectInfo", "subjectType", "Lorg/jetbrains/kotlin/types/KotlinType;", "targetType", "(Lorg/jetbrains/kotlin/resolve/calls/smartcasts/IdentifierInfo;Lorg/jetbrains/kotlin/types/KotlinType;Lorg/jetbrains/kotlin/types/KotlinType;)V", "canBeBound", "", "getCanBeBound", "()Z", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValue$Kind;", "getKind", "()Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValue$Kind;", "getSubjectInfo", "()Lorg/jetbrains/kotlin/resolve/calls/smartcasts/IdentifierInfo;", "getSubjectType", "()Lorg/jetbrains/kotlin/types/KotlinType;", "getTargetType", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "", "toString", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class SafeCast implements IdentifierInfo {
        private final IdentifierInfo subjectInfo;
        private final KotlinType subjectType;
        private final KotlinType targetType;

        public static /* synthetic */ SafeCast copy$default(SafeCast safeCast, IdentifierInfo identifierInfo, KotlinType kotlinType, KotlinType kotlinType2, int i, Object obj) {
            if ((i & 1) != 0) {
                identifierInfo = safeCast.subjectInfo;
            }
            if ((i & 2) != 0) {
                kotlinType = safeCast.subjectType;
            }
            if ((i & 4) != 0) {
                kotlinType2 = safeCast.targetType;
            }
            return safeCast.copy(identifierInfo, kotlinType, kotlinType2);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final IdentifierInfo getSubjectInfo() {
            return this.subjectInfo;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final KotlinType getSubjectType() {
            return this.subjectType;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final KotlinType getTargetType() {
            return this.targetType;
        }

        public final SafeCast copy(IdentifierInfo subjectInfo, KotlinType subjectType, KotlinType targetType) {
            Intrinsics.checkNotNullParameter(subjectInfo, "subjectInfo");
            return new SafeCast(subjectInfo, subjectType, targetType);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof SafeCast)) {
                return false;
            }
            SafeCast safeCast = (SafeCast) other;
            return Intrinsics.areEqual(this.subjectInfo, safeCast.subjectInfo) && Intrinsics.areEqual(this.subjectType, safeCast.subjectType) && Intrinsics.areEqual(this.targetType, safeCast.targetType);
        }

        public int hashCode() {
            int iHashCode = this.subjectInfo.hashCode() * 31;
            KotlinType kotlinType = this.subjectType;
            int iHashCode2 = (iHashCode + (kotlinType == null ? 0 : kotlinType.hashCode())) * 31;
            KotlinType kotlinType2 = this.targetType;
            return iHashCode2 + (kotlinType2 != null ? kotlinType2.hashCode() : 0);
        }

        public SafeCast(IdentifierInfo subjectInfo, KotlinType kotlinType, KotlinType kotlinType2) {
            Intrinsics.checkNotNullParameter(subjectInfo, "subjectInfo");
            this.subjectInfo = subjectInfo;
            this.subjectType = kotlinType;
            this.targetType = kotlinType2;
        }

        public final IdentifierInfo getSubjectInfo() {
            return this.subjectInfo;
        }

        public final KotlinType getSubjectType() {
            return this.subjectType;
        }

        public final KotlinType getTargetType() {
            return this.targetType;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.smartcasts.IdentifierInfo
        public DataFlowValue.Kind getKind() {
            return DataFlowValue.Kind.OTHER;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.smartcasts.IdentifierInfo
        public boolean getCanBeBound() {
            return this.subjectInfo.getCanBeBound();
        }

        public String toString() {
            StringBuilder sbAppend = new StringBuilder().append(this.subjectInfo).append(" as? ");
            Object obj = this.targetType;
            if (obj == null) {
                obj = "???";
            }
            return sbAppend.append(obj).toString();
        }
    }

    /* JADX INFO: compiled from: IdentifierInfo.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0013\u0010\r\u001a\u00020\u00052\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0096\u0002J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\r\u0010\u0012\u001a\u00070\u0013¢\u0006\u0002\b\u0014H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/smartcasts/IdentifierInfo$Expression;", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/IdentifierInfo;", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "stableComplex", "", "(Lorg/jetbrains/kotlin/psi/KtExpression;Z)V", "getExpression", "()Lorg/jetbrains/kotlin/psi/KtExpression;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValue$Kind;", "getKind", "()Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValue$Kind;", Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "", "toString", "", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/NlsSafe;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Expression implements IdentifierInfo {
        private final KtExpression expression;
        private final DataFlowValue.Kind kind;

        public Expression(KtExpression expression, boolean z) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            this.expression = expression;
            this.kind = z ? DataFlowValue.Kind.STABLE_COMPLEX_EXPRESSION : DataFlowValue.Kind.OTHER;
        }

        public /* synthetic */ Expression(KtExpression ktExpression, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(ktExpression, (i & 2) != 0 ? false : z);
        }

        public final KtExpression getExpression() {
            return this.expression;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.smartcasts.IdentifierInfo
        public DataFlowValue.Kind getKind() {
            return this.kind;
        }

        public boolean equals(Object other) {
            return (other instanceof Expression) && Intrinsics.areEqual(this.expression, ((Expression) other).expression);
        }

        public int hashCode() {
            return this.expression.hashCode();
        }

        public String toString() {
            String text = this.expression.getText();
            return text == null ? "(empty expression)" : text;
        }
    }

    /* JADX INFO: compiled from: IdentifierInfo.kt */
    @Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\t\u0010\u000e\u001a\u00020\u0001HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0004HÆ\u0003J\u001d\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00012\b\b\u0002\u0010\u0003\u001a\u00020\u0004HÆ\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001J\b\u0010\u0017\u001a\u00020\u0018H\u0016R\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/smartcasts/IdentifierInfo$PostfixIdentifierInfo;", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/IdentifierInfo;", "argumentInfo", "op", "Lorg/jetbrains/kotlin/lexer/KtToken;", "(Lorg/jetbrains/kotlin/resolve/calls/smartcasts/IdentifierInfo;Lorg/jetbrains/kotlin/lexer/KtToken;)V", "getArgumentInfo", "()Lorg/jetbrains/kotlin/resolve/calls/smartcasts/IdentifierInfo;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValue$Kind;", "getKind", "()Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValue$Kind;", "getOp", "()Lorg/jetbrains/kotlin/lexer/KtToken;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class PostfixIdentifierInfo implements IdentifierInfo {
        private final IdentifierInfo argumentInfo;
        private final KtToken op;

        public static /* synthetic */ PostfixIdentifierInfo copy$default(PostfixIdentifierInfo postfixIdentifierInfo, IdentifierInfo identifierInfo, KtToken ktToken, int i, Object obj) {
            if ((i & 1) != 0) {
                identifierInfo = postfixIdentifierInfo.argumentInfo;
            }
            if ((i & 2) != 0) {
                ktToken = postfixIdentifierInfo.op;
            }
            return postfixIdentifierInfo.copy(identifierInfo, ktToken);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final IdentifierInfo getArgumentInfo() {
            return this.argumentInfo;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final KtToken getOp() {
            return this.op;
        }

        public final PostfixIdentifierInfo copy(IdentifierInfo argumentInfo, KtToken op) {
            Intrinsics.checkNotNullParameter(argumentInfo, "argumentInfo");
            Intrinsics.checkNotNullParameter(op, "op");
            return new PostfixIdentifierInfo(argumentInfo, op);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof PostfixIdentifierInfo)) {
                return false;
            }
            PostfixIdentifierInfo postfixIdentifierInfo = (PostfixIdentifierInfo) other;
            return Intrinsics.areEqual(this.argumentInfo, postfixIdentifierInfo.argumentInfo) && Intrinsics.areEqual(this.op, postfixIdentifierInfo.op);
        }

        public int hashCode() {
            return (this.argumentInfo.hashCode() * 31) + this.op.hashCode();
        }

        public PostfixIdentifierInfo(IdentifierInfo argumentInfo, KtToken op) {
            Intrinsics.checkNotNullParameter(argumentInfo, "argumentInfo");
            Intrinsics.checkNotNullParameter(op, "op");
            this.argumentInfo = argumentInfo;
            this.op = op;
        }

        public final IdentifierInfo getArgumentInfo() {
            return this.argumentInfo;
        }

        public final KtToken getOp() {
            return this.op;
        }

        @Override // org.jetbrains.kotlin.resolve.calls.smartcasts.IdentifierInfo
        public DataFlowValue.Kind getKind() {
            return this.argumentInfo.getKind();
        }

        public String toString() {
            return new StringBuilder().append(this.argumentInfo).append('(').append(this.op).append(')').toString();
        }
    }
}
