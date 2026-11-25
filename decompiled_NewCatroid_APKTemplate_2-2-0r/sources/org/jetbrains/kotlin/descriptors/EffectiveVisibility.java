package org.jetbrains.kotlin.descriptors;

import com.huawei.hms.framework.network.grs.GrsBaseInfo;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.types.model.TypeCheckerProviderContext;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;

/* JADX INFO: compiled from: EffectiveVisibility.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\f\u0016\u0017\u0018\u0019\u001a\u001b\u001c\u001d\u001e\u001f !B#\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J\u0018\u0010\r\u001a\u00020\u00002\u0006\u0010\u000e\u001a\u00020\u00002\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u000e\u001a\u00020\u00002\u0006\u0010\u000f\u001a\u00020\u0010H&J\b\u0010\u0013\u001a\u00020\u0003H\u0016J\b\u0010\u0014\u001a\u00020\u0015H&R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000b\u0082\u0001\t\"#$%&'()*¨\u0006+"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility;", "", "name", "", "publicApi", "", "privateApi", "(Ljava/lang/String;ZZ)V", "getName", "()Ljava/lang/String;", "getPrivateApi", "()Z", "getPublicApi", "lowerBound", "other", "typeCheckerContextProvider", "Lorg/jetbrains/kotlin/types/model/TypeCheckerProviderContext;", "relation", "Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility$Permissiveness;", "toString", "toVisibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "Internal", "InternalOrPackage", "InternalProtected", "InternalProtectedBound", "Local", "PackagePrivate", "Permissiveness", "PrivateInClass", "PrivateInFile", "Protected", "ProtectedBound", "Public", "Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility$InternalOrPackage;", "Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility$InternalProtected;", "Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility$InternalProtectedBound;", "Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility$Local;", "Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility$PrivateInClass;", "Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility$PrivateInFile;", "Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility$Protected;", "Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility$ProtectedBound;", "Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility$Public;", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class EffectiveVisibility {
    private final String name;
    private final boolean privateApi;
    private final boolean publicApi;

    /* JADX INFO: compiled from: EffectiveVisibility.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility$Permissiveness;", "", "(Ljava/lang/String;I)V", "LESS", "SAME", "MORE", GrsBaseInfo.CountryCodeSource.UNKNOWN, "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum Permissiveness {
        LESS,
        SAME,
        MORE,
        UNKNOWN;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<Permissiveness> getEntries() {
            return $ENTRIES;
        }
    }

    /* JADX INFO: compiled from: EffectiveVisibility.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Permissiveness.values().length];
            try {
                iArr[Permissiveness.SAME.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Permissiveness.LESS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[Permissiveness.MORE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[Permissiveness.UNKNOWN.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public /* synthetic */ EffectiveVisibility(String str, boolean z, boolean z2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, z, z2);
    }

    public abstract Permissiveness relation(EffectiveVisibility other, TypeCheckerProviderContext typeCheckerContextProvider);

    public abstract Visibility toVisibility();

    private EffectiveVisibility(String str, boolean z, boolean z2) {
        this.name = str;
        this.publicApi = z;
        this.privateApi = z2;
    }

    public /* synthetic */ EffectiveVisibility(String str, boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? false : z, (i & 4) != 0 ? false : z2, null);
    }

    public final String getName() {
        return this.name;
    }

    public final boolean getPrivateApi() {
        return this.privateApi;
    }

    public final boolean getPublicApi() {
        return this.publicApi;
    }

    public String toString() {
        return this.name;
    }

    /* JADX INFO: compiled from: EffectiveVisibility.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\b\u0010\b\u001a\u00020\tH\u0016¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility$PrivateInClass;", "Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility;", "()V", "relation", "Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility$Permissiveness;", "other", "typeCheckerContextProvider", "Lorg/jetbrains/kotlin/types/model/TypeCheckerProviderContext;", "toVisibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class PrivateInClass extends EffectiveVisibility {
        public static final PrivateInClass INSTANCE = new PrivateInClass();

        private PrivateInClass() {
            super("private-in-class", false, true, 2, null);
        }

        @Override // org.jetbrains.kotlin.descriptors.EffectiveVisibility
        public Permissiveness relation(EffectiveVisibility other, TypeCheckerProviderContext typeCheckerContextProvider) {
            Intrinsics.checkNotNullParameter(other, "other");
            Intrinsics.checkNotNullParameter(typeCheckerContextProvider, "typeCheckerContextProvider");
            return (Intrinsics.areEqual(this, other) || Intrinsics.areEqual(Local.INSTANCE, other)) ? Permissiveness.SAME : Permissiveness.LESS;
        }

        @Override // org.jetbrains.kotlin.descriptors.EffectiveVisibility
        public Visibility toVisibility() {
            return Visibilities.Private.INSTANCE;
        }
    }

    /* JADX INFO: compiled from: EffectiveVisibility.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\b\u0010\b\u001a\u00020\tH\u0016¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility$Local;", "Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility;", "()V", "relation", "Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility$Permissiveness;", "other", "typeCheckerContextProvider", "Lorg/jetbrains/kotlin/types/model/TypeCheckerProviderContext;", "toVisibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Local extends EffectiveVisibility {
        public static final Local INSTANCE = new Local();

        private Local() {
            super("local", false, false, 6, null);
        }

        @Override // org.jetbrains.kotlin.descriptors.EffectiveVisibility
        public Permissiveness relation(EffectiveVisibility other, TypeCheckerProviderContext typeCheckerContextProvider) {
            Intrinsics.checkNotNullParameter(other, "other");
            Intrinsics.checkNotNullParameter(typeCheckerContextProvider, "typeCheckerContextProvider");
            return (Intrinsics.areEqual(this, other) || Intrinsics.areEqual(PrivateInClass.INSTANCE, other)) ? Permissiveness.SAME : Permissiveness.LESS;
        }

        @Override // org.jetbrains.kotlin.descriptors.EffectiveVisibility
        public Visibility toVisibility() {
            return Visibilities.Local.INSTANCE;
        }
    }

    /* JADX INFO: compiled from: EffectiveVisibility.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\b\u0010\b\u001a\u00020\tH\u0016¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility$PrivateInFile;", "Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility;", "()V", "relation", "Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility$Permissiveness;", "other", "typeCheckerContextProvider", "Lorg/jetbrains/kotlin/types/model/TypeCheckerProviderContext;", "toVisibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class PrivateInFile extends EffectiveVisibility {
        public static final PrivateInFile INSTANCE = new PrivateInFile();

        private PrivateInFile() {
            super("private-in-file", false, true, 2, null);
        }

        @Override // org.jetbrains.kotlin.descriptors.EffectiveVisibility
        public Permissiveness relation(EffectiveVisibility other, TypeCheckerProviderContext typeCheckerContextProvider) {
            Intrinsics.checkNotNullParameter(other, "other");
            Intrinsics.checkNotNullParameter(typeCheckerContextProvider, "typeCheckerContextProvider");
            if (Intrinsics.areEqual(other, this)) {
                return Permissiveness.SAME;
            }
            return Intrinsics.areEqual(other, PrivateInClass.INSTANCE) ? true : Intrinsics.areEqual(other, Local.INSTANCE) ? Permissiveness.MORE : Permissiveness.LESS;
        }

        @Override // org.jetbrains.kotlin.descriptors.EffectiveVisibility
        public Visibility toVisibility() {
            return Visibilities.Private.INSTANCE;
        }
    }

    /* JADX INFO: compiled from: EffectiveVisibility.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\b\u0010\b\u001a\u00020\tH\u0016¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility$Public;", "Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility;", "()V", "relation", "Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility$Permissiveness;", "other", "typeCheckerContextProvider", "Lorg/jetbrains/kotlin/types/model/TypeCheckerProviderContext;", "toVisibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Public extends EffectiveVisibility {
        public static final Public INSTANCE = new Public();

        private Public() {
            super("public", true, false, 4, null);
        }

        @Override // org.jetbrains.kotlin.descriptors.EffectiveVisibility
        public Permissiveness relation(EffectiveVisibility other, TypeCheckerProviderContext typeCheckerContextProvider) {
            Intrinsics.checkNotNullParameter(other, "other");
            Intrinsics.checkNotNullParameter(typeCheckerContextProvider, "typeCheckerContextProvider");
            return Intrinsics.areEqual(this, other) ? Permissiveness.SAME : Permissiveness.MORE;
        }

        @Override // org.jetbrains.kotlin.descriptors.EffectiveVisibility
        public Visibility toVisibility() {
            return Visibilities.Public.INSTANCE;
        }
    }

    /* JADX INFO: compiled from: EffectiveVisibility.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u000f\b\u0004\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility$InternalOrPackage;", "Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility;", "internal", "", "(Z)V", "lowerBound", "other", "typeCheckerContextProvider", "Lorg/jetbrains/kotlin/types/model/TypeCheckerProviderContext;", "relation", "Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility$Permissiveness;", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class InternalOrPackage extends EffectiveVisibility {
        protected InternalOrPackage(boolean z) {
            super(z ? "internal" : "public/*package*/", false, false, 6, null);
        }

        @Override // org.jetbrains.kotlin.descriptors.EffectiveVisibility
        public Permissiveness relation(EffectiveVisibility other, TypeCheckerProviderContext typeCheckerContextProvider) {
            Intrinsics.checkNotNullParameter(other, "other");
            Intrinsics.checkNotNullParameter(typeCheckerContextProvider, "typeCheckerContextProvider");
            if (Intrinsics.areEqual(other, Public.INSTANCE)) {
                return Permissiveness.LESS;
            }
            if (Intrinsics.areEqual(other, PrivateInClass.INSTANCE) ? true : Intrinsics.areEqual(other, PrivateInFile.INSTANCE) ? true : Intrinsics.areEqual(other, Local.INSTANCE) ? true : Intrinsics.areEqual(other, InternalProtectedBound.INSTANCE) ? true : other instanceof InternalProtected) {
                return Permissiveness.MORE;
            }
            if (other instanceof InternalOrPackage) {
                return Permissiveness.SAME;
            }
            if (Intrinsics.areEqual(other, ProtectedBound.INSTANCE) ? true : other instanceof Protected) {
                return Permissiveness.UNKNOWN;
            }
            throw new NoWhenBranchMatchedException();
        }

        @Override // org.jetbrains.kotlin.descriptors.EffectiveVisibility
        public EffectiveVisibility lowerBound(EffectiveVisibility other, TypeCheckerProviderContext typeCheckerContextProvider) {
            Intrinsics.checkNotNullParameter(other, "other");
            Intrinsics.checkNotNullParameter(typeCheckerContextProvider, "typeCheckerContextProvider");
            if (Intrinsics.areEqual(other, Public.INSTANCE)) {
                return this;
            }
            if (Intrinsics.areEqual(other, PrivateInClass.INSTANCE) ? true : Intrinsics.areEqual(other, PrivateInFile.INSTANCE) ? true : Intrinsics.areEqual(other, Local.INSTANCE) ? true : Intrinsics.areEqual(other, InternalProtectedBound.INSTANCE) ? true : other instanceof InternalOrPackage ? true : other instanceof InternalProtected) {
                return other;
            }
            if (other instanceof Protected) {
                return new InternalProtected(((Protected) other).getContainerTypeConstructor());
            }
            if (Intrinsics.areEqual(other, ProtectedBound.INSTANCE)) {
                return InternalProtectedBound.INSTANCE;
            }
            throw new NoWhenBranchMatchedException();
        }
    }

    /* JADX INFO: compiled from: EffectiveVisibility.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility$Internal;", "Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility$InternalOrPackage;", "()V", "toVisibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Internal extends InternalOrPackage {
        public static final Internal INSTANCE = new Internal();

        private Internal() {
            super(true);
        }

        @Override // org.jetbrains.kotlin.descriptors.EffectiveVisibility
        public Visibility toVisibility() {
            return Visibilities.Internal.INSTANCE;
        }
    }

    /* JADX INFO: compiled from: EffectiveVisibility.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility$PackagePrivate;", "Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility$InternalOrPackage;", "()V", "toVisibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class PackagePrivate extends InternalOrPackage {
        public static final PackagePrivate INSTANCE = new PackagePrivate();

        private PackagePrivate() {
            super(false);
        }

        @Override // org.jetbrains.kotlin.descriptors.EffectiveVisibility
        public Visibility toVisibility() {
            return Visibilities.Private.INSTANCE;
        }
    }

    /* JADX INFO: compiled from: EffectiveVisibility.kt */
    @Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\u0013\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0096\u0002J\b\u0010\u000b\u001a\u00020\fH\u0016J\u0018\u0010\r\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0018\u0010\u0010\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u0015H\u0016R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility$Protected;", "Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility;", "containerTypeConstructor", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "(Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;)V", "getContainerTypeConstructor", "()Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "lowerBound", "typeCheckerContextProvider", "Lorg/jetbrains/kotlin/types/model/TypeCheckerProviderContext;", "relation", "Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility$Permissiveness;", "toString", "", "toVisibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Protected extends EffectiveVisibility {
        private final TypeConstructorMarker containerTypeConstructor;

        /* JADX INFO: compiled from: EffectiveVisibility.kt */
        @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[Permissiveness.values().length];
                try {
                    iArr[Permissiveness.SAME.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[Permissiveness.MORE.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[Permissiveness.UNKNOWN.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                try {
                    iArr[Permissiveness.LESS.ordinal()] = 4;
                } catch (NoSuchFieldError unused4) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        public Protected(TypeConstructorMarker typeConstructorMarker) {
            super("protected", true, false, 4, null);
            this.containerTypeConstructor = typeConstructorMarker;
        }

        public final TypeConstructorMarker getContainerTypeConstructor() {
            return this.containerTypeConstructor;
        }

        public boolean equals(Object other) {
            return (other instanceof Protected) && Intrinsics.areEqual(this.containerTypeConstructor, ((Protected) other).containerTypeConstructor);
        }

        public int hashCode() {
            TypeConstructorMarker typeConstructorMarker = this.containerTypeConstructor;
            if (typeConstructorMarker != null) {
                return typeConstructorMarker.hashCode();
            }
            return 0;
        }

        @Override // org.jetbrains.kotlin.descriptors.EffectiveVisibility
        public String toString() {
            StringBuilder sbAppend = new StringBuilder().append(super.toString()).append(" (in ");
            Object objValueOf = this.containerTypeConstructor;
            if (objValueOf == null) {
                objValueOf = Character.valueOf(MangleConstant.Q_MARK);
            }
            return sbAppend.append(objValueOf).append(')').toString();
        }

        @Override // org.jetbrains.kotlin.descriptors.EffectiveVisibility
        public Permissiveness relation(EffectiveVisibility other, TypeCheckerProviderContext typeCheckerContextProvider) {
            Intrinsics.checkNotNullParameter(other, "other");
            Intrinsics.checkNotNullParameter(typeCheckerContextProvider, "typeCheckerContextProvider");
            if (Intrinsics.areEqual(other, Public.INSTANCE)) {
                return Permissiveness.LESS;
            }
            if (Intrinsics.areEqual(other, PrivateInClass.INSTANCE) ? true : Intrinsics.areEqual(other, PrivateInFile.INSTANCE) ? true : Intrinsics.areEqual(other, Local.INSTANCE) ? true : Intrinsics.areEqual(other, ProtectedBound.INSTANCE) ? true : Intrinsics.areEqual(other, InternalProtectedBound.INSTANCE)) {
                return Permissiveness.MORE;
            }
            if (other instanceof Protected) {
                return EffectiveVisibilityKt.containerRelation(this.containerTypeConstructor, ((Protected) other).containerTypeConstructor, typeCheckerContextProvider);
            }
            if (!(other instanceof InternalProtected)) {
                if (other instanceof InternalOrPackage) {
                    return Permissiveness.UNKNOWN;
                }
                throw new NoWhenBranchMatchedException();
            }
            int i = WhenMappings.$EnumSwitchMapping$0[EffectiveVisibilityKt.containerRelation(this.containerTypeConstructor, ((InternalProtected) other).getContainerTypeConstructor(), typeCheckerContextProvider).ordinal()];
            if (i == 1 || i == 2) {
                return Permissiveness.MORE;
            }
            if (i == 3 || i == 4) {
                return Permissiveness.UNKNOWN;
            }
            throw new NoWhenBranchMatchedException();
        }

        @Override // org.jetbrains.kotlin.descriptors.EffectiveVisibility
        public EffectiveVisibility lowerBound(EffectiveVisibility other, TypeCheckerProviderContext typeCheckerContextProvider) {
            Intrinsics.checkNotNullParameter(other, "other");
            Intrinsics.checkNotNullParameter(typeCheckerContextProvider, "typeCheckerContextProvider");
            if (Intrinsics.areEqual(other, Public.INSTANCE)) {
                return this;
            }
            if (Intrinsics.areEqual(other, PrivateInClass.INSTANCE) ? true : Intrinsics.areEqual(other, PrivateInFile.INSTANCE) ? true : Intrinsics.areEqual(other, Local.INSTANCE) ? true : Intrinsics.areEqual(other, ProtectedBound.INSTANCE) ? true : Intrinsics.areEqual(other, InternalProtectedBound.INSTANCE)) {
                return other;
            }
            if (other instanceof Protected) {
                int i = WhenMappings.$EnumSwitchMapping$0[relation(other, typeCheckerContextProvider).ordinal()];
                if (i != 1) {
                    if (i == 2) {
                        return other;
                    }
                    if (i == 3) {
                        return ProtectedBound.INSTANCE;
                    }
                    if (i != 4) {
                        throw new NoWhenBranchMatchedException();
                    }
                }
                return this;
            }
            if (other instanceof InternalProtected) {
                return WhenMappings.$EnumSwitchMapping$0[relation(other, typeCheckerContextProvider).ordinal()] == 2 ? other : InternalProtectedBound.INSTANCE;
            }
            if (other instanceof InternalOrPackage) {
                return new InternalProtected(this.containerTypeConstructor);
            }
            throw new NoWhenBranchMatchedException();
        }

        @Override // org.jetbrains.kotlin.descriptors.EffectiveVisibility
        public Visibility toVisibility() {
            return Visibilities.Protected.INSTANCE;
        }
    }

    /* JADX INFO: compiled from: EffectiveVisibility.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\b\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility$ProtectedBound;", "Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility;", "()V", "lowerBound", "other", "typeCheckerContextProvider", "Lorg/jetbrains/kotlin/types/model/TypeCheckerProviderContext;", "relation", "Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility$Permissiveness;", "toVisibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ProtectedBound extends EffectiveVisibility {
        public static final ProtectedBound INSTANCE = new ProtectedBound();

        private ProtectedBound() {
            super("protected (in different classes)", true, false, 4, null);
        }

        @Override // org.jetbrains.kotlin.descriptors.EffectiveVisibility
        public Permissiveness relation(EffectiveVisibility other, TypeCheckerProviderContext typeCheckerContextProvider) {
            Intrinsics.checkNotNullParameter(other, "other");
            Intrinsics.checkNotNullParameter(typeCheckerContextProvider, "typeCheckerContextProvider");
            if (Intrinsics.areEqual(other, Public.INSTANCE) ? true : other instanceof Protected) {
                return Permissiveness.LESS;
            }
            if (Intrinsics.areEqual(other, PrivateInClass.INSTANCE) ? true : Intrinsics.areEqual(other, PrivateInFile.INSTANCE) ? true : Intrinsics.areEqual(other, Local.INSTANCE) ? true : Intrinsics.areEqual(other, InternalProtectedBound.INSTANCE)) {
                return Permissiveness.MORE;
            }
            if (Intrinsics.areEqual(other, INSTANCE)) {
                return Permissiveness.SAME;
            }
            if (other instanceof InternalOrPackage ? true : other instanceof InternalProtected) {
                return Permissiveness.UNKNOWN;
            }
            throw new NoWhenBranchMatchedException();
        }

        @Override // org.jetbrains.kotlin.descriptors.EffectiveVisibility
        public EffectiveVisibility lowerBound(EffectiveVisibility other, TypeCheckerProviderContext typeCheckerContextProvider) {
            Intrinsics.checkNotNullParameter(other, "other");
            Intrinsics.checkNotNullParameter(typeCheckerContextProvider, "typeCheckerContextProvider");
            if (Intrinsics.areEqual(other, Public.INSTANCE) ? true : other instanceof Protected) {
                return this;
            }
            if (Intrinsics.areEqual(other, PrivateInClass.INSTANCE) ? true : Intrinsics.areEqual(other, PrivateInFile.INSTANCE) ? true : Intrinsics.areEqual(other, Local.INSTANCE) ? true : Intrinsics.areEqual(other, INSTANCE) ? true : Intrinsics.areEqual(other, InternalProtectedBound.INSTANCE)) {
                return other;
            }
            if (other instanceof InternalOrPackage ? true : other instanceof InternalProtected) {
                return InternalProtectedBound.INSTANCE;
            }
            throw new NoWhenBranchMatchedException();
        }

        @Override // org.jetbrains.kotlin.descriptors.EffectiveVisibility
        public Visibility toVisibility() {
            return Visibilities.Protected.INSTANCE;
        }
    }

    /* JADX INFO: compiled from: EffectiveVisibility.kt */
    @Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\u0013\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0096\u0002J\b\u0010\u000b\u001a\u00020\fH\u0016J\u0018\u0010\r\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\u0018\u0010\u0010\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u0015H\u0016R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility$InternalProtected;", "Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility;", "containerTypeConstructor", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "(Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;)V", "getContainerTypeConstructor", "()Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "lowerBound", "typeCheckerContextProvider", "Lorg/jetbrains/kotlin/types/model/TypeCheckerProviderContext;", "relation", "Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility$Permissiveness;", "toString", "", "toVisibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class InternalProtected extends EffectiveVisibility {
        private final TypeConstructorMarker containerTypeConstructor;

        /* JADX INFO: compiled from: EffectiveVisibility.kt */
        @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[Permissiveness.values().length];
                try {
                    iArr[Permissiveness.SAME.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[Permissiveness.LESS.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[Permissiveness.UNKNOWN.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                try {
                    iArr[Permissiveness.MORE.ordinal()] = 4;
                } catch (NoSuchFieldError unused4) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        public final TypeConstructorMarker getContainerTypeConstructor() {
            return this.containerTypeConstructor;
        }

        public InternalProtected(TypeConstructorMarker typeConstructorMarker) {
            super("internal & protected", false, false, 4, null);
            this.containerTypeConstructor = typeConstructorMarker;
        }

        public boolean equals(Object other) {
            return (other instanceof InternalProtected) && Intrinsics.areEqual(this.containerTypeConstructor, ((InternalProtected) other).containerTypeConstructor);
        }

        public int hashCode() {
            TypeConstructorMarker typeConstructorMarker = this.containerTypeConstructor;
            if (typeConstructorMarker != null) {
                return typeConstructorMarker.hashCode();
            }
            return 0;
        }

        @Override // org.jetbrains.kotlin.descriptors.EffectiveVisibility
        public String toString() {
            StringBuilder sbAppend = new StringBuilder().append(super.toString()).append(" (in ");
            Object objValueOf = this.containerTypeConstructor;
            if (objValueOf == null) {
                objValueOf = Character.valueOf(MangleConstant.Q_MARK);
            }
            return sbAppend.append(objValueOf).append(')').toString();
        }

        @Override // org.jetbrains.kotlin.descriptors.EffectiveVisibility
        public Permissiveness relation(EffectiveVisibility other, TypeCheckerProviderContext typeCheckerContextProvider) {
            Intrinsics.checkNotNullParameter(other, "other");
            Intrinsics.checkNotNullParameter(typeCheckerContextProvider, "typeCheckerContextProvider");
            if (Intrinsics.areEqual(other, Public.INSTANCE) ? true : other instanceof InternalOrPackage) {
                return Permissiveness.LESS;
            }
            if (Intrinsics.areEqual(other, PrivateInClass.INSTANCE) ? true : Intrinsics.areEqual(other, PrivateInFile.INSTANCE) ? true : Intrinsics.areEqual(other, Local.INSTANCE) ? true : Intrinsics.areEqual(other, InternalProtectedBound.INSTANCE)) {
                return Permissiveness.MORE;
            }
            if (other instanceof InternalProtected) {
                return EffectiveVisibilityKt.containerRelation(this.containerTypeConstructor, ((InternalProtected) other).containerTypeConstructor, typeCheckerContextProvider);
            }
            if (!(other instanceof Protected)) {
                if (Intrinsics.areEqual(other, ProtectedBound.INSTANCE)) {
                    return Permissiveness.UNKNOWN;
                }
                throw new NoWhenBranchMatchedException();
            }
            int i = WhenMappings.$EnumSwitchMapping$0[EffectiveVisibilityKt.containerRelation(this.containerTypeConstructor, ((Protected) other).getContainerTypeConstructor(), typeCheckerContextProvider).ordinal()];
            if (i == 1 || i == 2) {
                return Permissiveness.LESS;
            }
            if (i == 3 || i == 4) {
                return Permissiveness.UNKNOWN;
            }
            throw new NoWhenBranchMatchedException();
        }

        @Override // org.jetbrains.kotlin.descriptors.EffectiveVisibility
        public EffectiveVisibility lowerBound(EffectiveVisibility other, TypeCheckerProviderContext typeCheckerContextProvider) {
            Intrinsics.checkNotNullParameter(other, "other");
            Intrinsics.checkNotNullParameter(typeCheckerContextProvider, "typeCheckerContextProvider");
            if (Intrinsics.areEqual(other, Public.INSTANCE) ? true : other instanceof InternalOrPackage) {
                return this;
            }
            if (Intrinsics.areEqual(other, PrivateInClass.INSTANCE) ? true : Intrinsics.areEqual(other, PrivateInFile.INSTANCE) ? true : Intrinsics.areEqual(other, Local.INSTANCE) ? true : Intrinsics.areEqual(other, InternalProtectedBound.INSTANCE)) {
                return other;
            }
            if (!(other instanceof Protected ? true : other instanceof InternalProtected)) {
                if (Intrinsics.areEqual(other, ProtectedBound.INSTANCE)) {
                    return InternalProtectedBound.INSTANCE;
                }
                throw new NoWhenBranchMatchedException();
            }
            int i = WhenMappings.$EnumSwitchMapping$0[relation(other, typeCheckerContextProvider).ordinal()];
            if (i == 1 || i == 2) {
                return this;
            }
            if (i == 3) {
                return InternalProtectedBound.INSTANCE;
            }
            if (i == 4) {
                return other;
            }
            throw new NoWhenBranchMatchedException();
        }

        @Override // org.jetbrains.kotlin.descriptors.EffectiveVisibility
        public Visibility toVisibility() {
            return Visibilities.Private.INSTANCE;
        }
    }

    /* JADX INFO: compiled from: EffectiveVisibility.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\b\u0010\b\u001a\u00020\tH\u0016¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility$InternalProtectedBound;", "Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility;", "()V", "relation", "Lorg/jetbrains/kotlin/descriptors/EffectiveVisibility$Permissiveness;", "other", "typeCheckerContextProvider", "Lorg/jetbrains/kotlin/types/model/TypeCheckerProviderContext;", "toVisibility", "Lorg/jetbrains/kotlin/descriptors/Visibility;", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class InternalProtectedBound extends EffectiveVisibility {
        public static final InternalProtectedBound INSTANCE = new InternalProtectedBound();

        private InternalProtectedBound() {
            super("internal & protected (in different classes)", false, false, 6, null);
        }

        @Override // org.jetbrains.kotlin.descriptors.EffectiveVisibility
        public Permissiveness relation(EffectiveVisibility other, TypeCheckerProviderContext typeCheckerContextProvider) {
            Intrinsics.checkNotNullParameter(other, "other");
            Intrinsics.checkNotNullParameter(typeCheckerContextProvider, "typeCheckerContextProvider");
            if (Intrinsics.areEqual(other, Public.INSTANCE) ? true : other instanceof Protected ? true : other instanceof InternalProtected ? true : Intrinsics.areEqual(other, ProtectedBound.INSTANCE) ? true : other instanceof InternalOrPackage) {
                return Permissiveness.LESS;
            }
            if (Intrinsics.areEqual(other, PrivateInClass.INSTANCE) ? true : Intrinsics.areEqual(other, PrivateInFile.INSTANCE) ? true : Intrinsics.areEqual(other, Local.INSTANCE)) {
                return Permissiveness.MORE;
            }
            if (Intrinsics.areEqual(other, INSTANCE)) {
                return Permissiveness.SAME;
            }
            throw new NoWhenBranchMatchedException();
        }

        @Override // org.jetbrains.kotlin.descriptors.EffectiveVisibility
        public Visibility toVisibility() {
            return Visibilities.Private.INSTANCE;
        }
    }

    public EffectiveVisibility lowerBound(EffectiveVisibility other, TypeCheckerProviderContext typeCheckerContextProvider) {
        Intrinsics.checkNotNullParameter(other, "other");
        Intrinsics.checkNotNullParameter(typeCheckerContextProvider, "typeCheckerContextProvider");
        int i = WhenMappings.$EnumSwitchMapping$0[relation(other, typeCheckerContextProvider).ordinal()];
        if (i == 1 || i == 2) {
            return this;
        }
        if (i == 3) {
            return other;
        }
        if (i == 4) {
            return PrivateInClass.INSTANCE;
        }
        throw new NoWhenBranchMatchedException();
    }
}
