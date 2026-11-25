package org.jetbrains.kotlin.ir.util;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;

/* JADX INFO: compiled from: KotlinMangler.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\f\bf\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0002:\u0002\u0017\u0018J\u0019\u0010\u000b\u001a\u00020\b*\u00028\u00002\u0006\u0010\f\u001a\u00020\rH\u0016¢\u0006\u0002\u0010\u000eJ\u0019\u0010\u000f\u001a\u00020\r*\u00028\u00002\u0006\u0010\f\u001a\u00020\rH&¢\u0006\u0002\u0010\u0010J\u0011\u0010\u0011\u001a\u00020\r*\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0012J\u0019\u0010\u0013\u001a\u00020\u0004*\u00028\u00002\u0006\u0010\f\u001a\u00020\rH&¢\u0006\u0002\u0010\u0014J\u0019\u0010\u0015\u001a\u00020\b*\u00028\u00002\u0006\u0010\f\u001a\u00020\rH\u0016¢\u0006\u0002\u0010\u000eJ\u0019\u0010\u0016\u001a\u00020\u0004*\u00028\u00002\u0006\u0010\f\u001a\u00020\rH&¢\u0006\u0002\u0010\u0014R\u0012\u0010\u0003\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0016\u0010\u0007\u001a\u00020\b*\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/KotlinMangler;", "D", "", "manglerName", "", "getManglerName", "()Ljava/lang/String;", "hashMangle", "", "getHashMangle", "(Ljava/lang/String;)J", "hashedMangle", "compatibleMode", "", "(Ljava/lang/Object;Z)J", "isExported", "(Ljava/lang/Object;Z)Z", "isPlatformSpecificExport", "(Ljava/lang/Object;)Z", "mangleString", "(Ljava/lang/Object;Z)Ljava/lang/String;", "signatureMangle", "signatureString", "DescriptorMangler", "IrMangler", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface KotlinMangler<D> {
    long getHashMangle(String str);

    String getManglerName();

    long hashedMangle(D d, boolean z);

    boolean isExported(D d, boolean z);

    boolean isPlatformSpecificExport(D d);

    String mangleString(D d, boolean z);

    long signatureMangle(D d, boolean z);

    String signatureString(D d, boolean z);

    /* JADX INFO: compiled from: KotlinMangler.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
        public static <D> boolean isPlatformSpecificExport(KotlinMangler<D> kotlinMangler, D receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return false;
        }

        public static <D> long hashedMangle(KotlinMangler<D> kotlinMangler, D receiver, boolean z) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return kotlinMangler.getHashMangle(kotlinMangler.mangleString(receiver, z));
        }

        public static <D> long signatureMangle(KotlinMangler<D> kotlinMangler, D receiver, boolean z) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return kotlinMangler.getHashMangle(kotlinMangler.signatureString(receiver, z));
        }
    }

    /* JADX INFO: compiled from: KotlinMangler.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\u0014\u0010\u0007\u001a\u00020\u0004*\u00020\b2\u0006\u0010\t\u001a\u00020\nH&J\u0014\u0010\u000b\u001a\u00020\u0004*\u00020\f2\u0006\u0010\t\u001a\u00020\nH&R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/KotlinMangler$DescriptorMangler;", "Lorg/jetbrains/kotlin/ir/util/KotlinMangler;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "manglerName", "", "getManglerName", "()Ljava/lang/String;", "mangleEnumEntryString", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "compatibleMode", "", "mangleFieldString", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface DescriptorMangler extends KotlinMangler<DeclarationDescriptor> {
        @Override // 
        String getManglerName();

        String mangleEnumEntryString(ClassDescriptor classDescriptor, boolean z);

        String mangleFieldString(PropertyDescriptor propertyDescriptor, boolean z);

        /* JADX INFO: compiled from: KotlinMangler.kt */
        @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
        public static final class DefaultImpls {
            public static long hashedMangle(DescriptorMangler descriptorMangler, DeclarationDescriptor receiver, boolean z) {
                Intrinsics.checkNotNullParameter(receiver, "$receiver");
                return DefaultImpls.hashedMangle(descriptorMangler, receiver, z);
            }

            public static boolean isPlatformSpecificExport(DescriptorMangler descriptorMangler, DeclarationDescriptor receiver) {
                Intrinsics.checkNotNullParameter(receiver, "$receiver");
                return DefaultImpls.isPlatformSpecificExport(descriptorMangler, receiver);
            }

            public static long signatureMangle(DescriptorMangler descriptorMangler, DeclarationDescriptor receiver, boolean z) {
                Intrinsics.checkNotNullParameter(receiver, "$receiver");
                return DefaultImpls.signatureMangle(descriptorMangler, receiver, z);
            }

            public static String getManglerName(DescriptorMangler descriptorMangler) {
                return "Descriptor";
            }
        }
    }

    /* JADX INFO: compiled from: KotlinMangler.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\bf\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/KotlinMangler$IrMangler;", "Lorg/jetbrains/kotlin/ir/util/KotlinMangler;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "manglerName", "", "getManglerName", "()Ljava/lang/String;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface IrMangler extends KotlinMangler<IrDeclaration> {
        @Override // org.jetbrains.kotlin.ir.util.KotlinMangler, org.jetbrains.kotlin.ir.util.KotlinMangler.DescriptorMangler
        String getManglerName();

        /* JADX INFO: compiled from: KotlinMangler.kt */
        @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
        public static final class DefaultImpls {
            public static long hashedMangle(IrMangler irMangler, IrDeclaration receiver, boolean z) {
                Intrinsics.checkNotNullParameter(receiver, "$receiver");
                return DefaultImpls.hashedMangle(irMangler, receiver, z);
            }

            public static boolean isPlatformSpecificExport(IrMangler irMangler, IrDeclaration receiver) {
                Intrinsics.checkNotNullParameter(receiver, "$receiver");
                return DefaultImpls.isPlatformSpecificExport(irMangler, receiver);
            }

            public static long signatureMangle(IrMangler irMangler, IrDeclaration receiver, boolean z) {
                Intrinsics.checkNotNullParameter(receiver, "$receiver");
                return DefaultImpls.signatureMangle(irMangler, receiver, z);
            }

            public static String getManglerName(IrMangler irMangler) {
                return "Ir";
            }
        }
    }
}
