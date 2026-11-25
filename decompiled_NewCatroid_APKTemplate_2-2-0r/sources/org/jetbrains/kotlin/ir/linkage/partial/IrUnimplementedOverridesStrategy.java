package org.jetbrains.kotlin.ir.linkage.partial;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;

/* JADX INFO: compiled from: IrUnimplementedOverridesStrategy.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001:\u0002\u000f\u0010J7\u0010\u0002\u001a\u00020\u0003\"\b\b\u0000\u0010\u0004*\u00020\u0005\"\u000e\b\u0001\u0010\u0006*\b\u0012\u0004\u0012\u0002H\u00040\u00072\u0006\u0010\b\u001a\u0002H\u00062\u0006\u0010\t\u001a\u00020\nH&¢\u0006\u0002\u0010\u000bJ7\u0010\f\u001a\u00020\r\"\b\b\u0000\u0010\u0004*\u00020\u0005\"\u000e\b\u0001\u0010\u0006*\b\u0012\u0004\u0012\u0002H\u00040\u00072\u0006\u0010\b\u001a\u0002H\u00062\u0006\u0010\t\u001a\u00020\nH\u0016¢\u0006\u0002\u0010\u000e¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/ir/linkage/partial/IrUnimplementedOverridesStrategy;", "", "computeCustomization", "Lorg/jetbrains/kotlin/ir/linkage/partial/IrUnimplementedOverridesStrategy$Customization;", ExifInterface.LATITUDE_SOUTH, "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/ir/declarations/IrOverridableDeclaration;", "overridableMember", "parent", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "(Lorg/jetbrains/kotlin/ir/declarations/IrOverridableDeclaration;Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Lorg/jetbrains/kotlin/ir/linkage/partial/IrUnimplementedOverridesStrategy$Customization;", "postProcessGeneratedFakeOverride", "", "(Lorg/jetbrains/kotlin/ir/declarations/IrOverridableDeclaration;Lorg/jetbrains/kotlin/ir/declarations/IrClass;)V", "Customization", "ProcessAsFakeOverrides", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface IrUnimplementedOverridesStrategy {

    /* JADX INFO: compiled from: IrUnimplementedOverridesStrategy.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
        public static <S extends IrSymbol, T extends IrOverridableDeclaration<S>> void postProcessGeneratedFakeOverride(IrUnimplementedOverridesStrategy irUnimplementedOverridesStrategy, T overridableMember, IrClass parent) {
            Intrinsics.checkNotNullParameter(overridableMember, "overridableMember");
            Intrinsics.checkNotNullParameter(parent, "parent");
        }
    }

    <S extends IrSymbol, T extends IrOverridableDeclaration<S>> Customization computeCustomization(T overridableMember, IrClass parent);

    <S extends IrSymbol, T extends IrOverridableDeclaration<S>> void postProcessGeneratedFakeOverride(T overridableMember, IrClass parent);

    /* JADX INFO: compiled from: IrUnimplementedOverridesStrategy.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 \u000b2\u00020\u0001:\u0001\u000bB\u0019\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/ir/linkage/partial/IrUnimplementedOverridesStrategy$Customization;", "", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "modality", "Lorg/jetbrains/kotlin/descriptors/Modality;", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;Lorg/jetbrains/kotlin/descriptors/Modality;)V", "getModality", "()Lorg/jetbrains/kotlin/descriptors/Modality;", "getOrigin", "()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "Companion", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Customization {

        /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);
        private static final Customization NO = new Customization(null, null);
        private final Modality modality;
        private final IrDeclarationOrigin origin;

        /* JADX INFO: compiled from: IrUnimplementedOverridesStrategy.kt */
        @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/ir/linkage/partial/IrUnimplementedOverridesStrategy$Customization$Companion;", "", "()V", "NO", "Lorg/jetbrains/kotlin/ir/linkage/partial/IrUnimplementedOverridesStrategy$Customization;", "getNO", "()Lorg/jetbrains/kotlin/ir/linkage/partial/IrUnimplementedOverridesStrategy$Customization;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            public final Customization getNO() {
                return Customization.NO;
            }
        }

        public Customization(IrDeclarationOrigin irDeclarationOrigin, Modality modality) {
            this.origin = irDeclarationOrigin;
            this.modality = modality;
        }

        public final Modality getModality() {
            return this.modality;
        }

        public final IrDeclarationOrigin getOrigin() {
            return this.origin;
        }
    }

    /* JADX INFO: compiled from: IrUnimplementedOverridesStrategy.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J7\u0010\u0003\u001a\u00020\u0004\"\b\b\u0000\u0010\u0005*\u00020\u0006\"\u000e\b\u0001\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00050\b2\u0006\u0010\t\u001a\u0002H\u00072\u0006\u0010\n\u001a\u00020\u000bH\u0016¢\u0006\u0002\u0010\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/ir/linkage/partial/IrUnimplementedOverridesStrategy$ProcessAsFakeOverrides;", "Lorg/jetbrains/kotlin/ir/linkage/partial/IrUnimplementedOverridesStrategy;", "()V", "computeCustomization", "Lorg/jetbrains/kotlin/ir/linkage/partial/IrUnimplementedOverridesStrategy$Customization;", ExifInterface.LATITUDE_SOUTH, "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/ir/declarations/IrOverridableDeclaration;", "overridableMember", "parent", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "(Lorg/jetbrains/kotlin/ir/declarations/IrOverridableDeclaration;Lorg/jetbrains/kotlin/ir/declarations/IrClass;)Lorg/jetbrains/kotlin/ir/linkage/partial/IrUnimplementedOverridesStrategy$Customization;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ProcessAsFakeOverrides implements IrUnimplementedOverridesStrategy {
        public static final ProcessAsFakeOverrides INSTANCE = new ProcessAsFakeOverrides();

        private ProcessAsFakeOverrides() {
        }

        @Override // org.jetbrains.kotlin.ir.linkage.partial.IrUnimplementedOverridesStrategy
        public <S extends IrSymbol, T extends IrOverridableDeclaration<S>> void postProcessGeneratedFakeOverride(T t, IrClass irClass) {
            DefaultImpls.postProcessGeneratedFakeOverride(this, t, irClass);
        }

        @Override // org.jetbrains.kotlin.ir.linkage.partial.IrUnimplementedOverridesStrategy
        public <S extends IrSymbol, T extends IrOverridableDeclaration<S>> Customization computeCustomization(T overridableMember, IrClass parent) {
            Intrinsics.checkNotNullParameter(overridableMember, "overridableMember");
            Intrinsics.checkNotNullParameter(parent, "parent");
            return Customization.INSTANCE.getNO();
        }
    }
}
