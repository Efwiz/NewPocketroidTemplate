package org.jetbrains.kotlin.backend.common.linkage.partial;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationWithName;
import org.jetbrains.kotlin.ir.declarations.IrPackageFragment;
import org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: PartialLinkageUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\bÀ\u0002\u0018\u00002\u00020\u0001:\u0001\u000fB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0014\u0010\u0007\u001a\u0004\u0018\u00010\b*\u00020\t2\u0006\u0010\n\u001a\u00020\u000bJ\n\u0010\f\u001a\u00020\r*\u00020\u000eR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartialLinkageUtils;", "", "()V", "UNKNOWN_NAME", "Lorg/jetbrains/kotlin/name/Name;", "getUNKNOWN_NAME", "()Lorg/jetbrains/kotlin/name/Name;", "guessName", "", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "nameSegmentsToPickUp", "", "isEffectivelyMissingLazyIrDeclaration", "", "Lorg/jetbrains/kotlin/ir/declarations/lazy/IrLazyDeclarationBase;", "DeclarationId", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class PartialLinkageUtils {
    public static final PartialLinkageUtils INSTANCE = new PartialLinkageUtils();
    private static final Name UNKNOWN_NAME;

    private PartialLinkageUtils() {
    }

    static {
        Name nameIdentifier = Name.identifier("<unknown name>");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        UNKNOWN_NAME = nameIdentifier;
    }

    public final Name getUNKNOWN_NAME() {
        return UNKNOWN_NAME;
    }

    public final String guessName(IdSignature idSignature, int i) {
        Intrinsics.checkNotNullParameter(idSignature, "<this>");
        if (idSignature instanceof IdSignature.CommonSignature) {
            if (i == 1) {
                return ((IdSignature.CommonSignature) idSignature).getShortName();
            }
            return CollectionsKt.joinToString$default(CollectionsKt.takeLast(((IdSignature.CommonSignature) idSignature).getNameSegments(), i), ".", null, null, 0, null, null, 62, null);
        }
        if (idSignature instanceof IdSignature.CompositeSignature) {
            return guessName(((IdSignature.CompositeSignature) idSignature).getInner(), i);
        }
        if (idSignature instanceof IdSignature.AccessorSignature) {
            return guessName(((IdSignature.AccessorSignature) idSignature).getAccessorSignature(), i);
        }
        return null;
    }

    /* JADX INFO: compiled from: PartialLinkageUtils.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\b\u0086\b\u0018\u0000 \u00142\u00020\u0001:\u0001\u0014B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003HÆ\u0003J\t\u0010\n\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0010\u0010\f\u001a\u00020\u00002\u0006\u0010\r\u001a\u00020\u0003H\u0002J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\b\u0010\u0013\u001a\u00020\u0003H\u0016R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartialLinkageUtils$DeclarationId;", "", "packageFqName", "", "declarationRelativeFqName", "(Ljava/lang/String;Ljava/lang/String;)V", "getDeclarationRelativeFqName", "()Ljava/lang/String;", "getPackageFqName", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "createNested", "name", Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "Companion", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class DeclarationId {

        /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);
        private final String declarationRelativeFqName;
        private final String packageFqName;

        public static /* synthetic */ DeclarationId copy$default(DeclarationId declarationId, String str, String str2, int i, Object obj) {
            if ((i & 1) != 0) {
                str = declarationId.packageFqName;
            }
            if ((i & 2) != 0) {
                str2 = declarationId.declarationRelativeFqName;
            }
            return declarationId.copy(str, str2);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final String getPackageFqName() {
            return this.packageFqName;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final String getDeclarationRelativeFqName() {
            return this.declarationRelativeFqName;
        }

        public final DeclarationId copy(String packageFqName, String declarationRelativeFqName) {
            Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
            Intrinsics.checkNotNullParameter(declarationRelativeFqName, "declarationRelativeFqName");
            return new DeclarationId(packageFqName, declarationRelativeFqName);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof DeclarationId)) {
                return false;
            }
            DeclarationId declarationId = (DeclarationId) other;
            return Intrinsics.areEqual(this.packageFqName, declarationId.packageFqName) && Intrinsics.areEqual(this.declarationRelativeFqName, declarationId.declarationRelativeFqName);
        }

        public int hashCode() {
            return (this.packageFqName.hashCode() * 31) + this.declarationRelativeFqName.hashCode();
        }

        public DeclarationId(String packageFqName, String declarationRelativeFqName) {
            Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
            Intrinsics.checkNotNullParameter(declarationRelativeFqName, "declarationRelativeFqName");
            this.packageFqName = packageFqName;
            this.declarationRelativeFqName = declarationRelativeFqName;
        }

        public final String getDeclarationRelativeFqName() {
            return this.declarationRelativeFqName;
        }

        public final String getPackageFqName() {
            return this.packageFqName;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final DeclarationId createNested(String name2) {
            String str = this.packageFqName;
            if (this.declarationRelativeFqName.length() > 0) {
                name2 = this.declarationRelativeFqName + '.' + name2;
            }
            return new DeclarationId(str, name2);
        }

        public String toString() {
            return this.packageFqName + '/' + this.declarationRelativeFqName;
        }

        /* JADX INFO: compiled from: PartialLinkageUtils.kt */
        @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\u0004\u0018\u00010\u0004*\u00020\u00058F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartialLinkageUtils$DeclarationId$Companion;", "", "()V", "declarationId", "Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartialLinkageUtils$DeclarationId;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationWithName;", "getDeclarationId", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationWithName;)Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartialLinkageUtils$DeclarationId;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            public final DeclarationId getDeclarationId(IrDeclarationWithName irDeclarationWithName) {
                DeclarationId declarationId;
                Intrinsics.checkNotNullParameter(irDeclarationWithName, "<this>");
                IrDeclarationParent parent = irDeclarationWithName.getParent();
                if (parent instanceof IrPackageFragment) {
                    String strAsString = ((IrPackageFragment) parent).getPackageFqName().asString();
                    Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                    String strAsString2 = irDeclarationWithName.getName().asString();
                    Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
                    return new DeclarationId(strAsString, strAsString2);
                }
                if (!(parent instanceof IrDeclarationWithName) || (declarationId = getDeclarationId((IrDeclarationWithName) parent)) == null) {
                    return null;
                }
                String strAsString3 = irDeclarationWithName.getName().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString3, "asString(...)");
                return declarationId.createNested(strAsString3);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final boolean isEffectivelyMissingLazyIrDeclaration(IrLazyDeclarationBase irLazyDeclarationBase) {
        Intrinsics.checkNotNullParameter(irLazyDeclarationBase, "<this>");
        IrClass parentClassOrNull = irLazyDeclarationBase instanceof IrClass ? (IrClass) irLazyDeclarationBase : null;
        if (parentClassOrNull == null && (parentClassOrNull = IrUtilsKt.getParentClassOrNull(irLazyDeclarationBase)) == null) {
            return false;
        }
        return Intrinsics.areEqual(((IrClass) SequencesKt.last(SequencesKt.generateSequence(parentClassOrNull, new Function1<IrClass, IrClass>() { // from class: org.jetbrains.kotlin.backend.common.linkage.partial.PartialLinkageUtils$isEffectivelyMissingLazyIrDeclaration$outermostClass$1
            @Override // kotlin.jvm.functions.Function1
            public final IrClass invoke(IrClass it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return IrUtilsKt.getParentClassOrNull(it);
            }
        }))).getVisibility(), DescriptorVisibilities.PRIVATE);
    }
}
