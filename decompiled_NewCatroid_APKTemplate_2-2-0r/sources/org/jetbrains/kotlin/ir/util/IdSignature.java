package org.jetbrains.kotlin.ir.util;

import com.google.android.gms.cast.MediaTrack;
import com.reandroid.arsc.chunk.OverlayablePolicy;
import java.util.List;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.collections.CollectionsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.ir.symbols.IrFileSymbol;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: IdSignature.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\n\u0019\u001a\u001b\u001c\u001d\u001e\u001f !\"B\u0007\b\u0004¢\u0006\u0002\u0010\u0002J\n\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0016J\b\u0010\r\u001a\u00020\u000eH\u0014J\b\u0010\u000f\u001a\u00020\u0004H\u0016J\b\u0010\u0010\u001a\u00020\u0000H&J\b\u0010\u0011\u001a\u00020\u0012H&J\b\u0010\u0013\u001a\u00020\u0014H\u0007J\u0006\u0010\u0015\u001a\u00020\u0014J\b\u0010\u0016\u001a\u00020\u0000H&J\n\u0010\u0017\u001a\u00020\u0004*\u00020\u0018R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0006R\u0012\u0010\b\u001a\u00020\u0004X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\u0006R\u0014\u0010\t\u001a\u00020\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u0006\u0082\u0001\t#\f$%&'()*¨\u0006+"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/IdSignature;", "", "()V", "hasTopLevel", "", "getHasTopLevel", "()Z", "isLocal", "isPubliclyVisible", "visibleCrossFile", "getVisibleCrossFile", "asPublic", "Lorg/jetbrains/kotlin/ir/util/IdSignature$CommonSignature;", OverlayablePolicy.NAME_flags, "", "isPackageSignature", "nearestPublicSig", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "render", "", "toString", "topLevelSignature", "test", "Lorg/jetbrains/kotlin/ir/util/IdSignature$Flags;", "AccessorSignature", "CommonSignature", "CompositeSignature", "FileLocalSignature", "FileSignature", "Flags", "LocalSignature", "LoweredDeclarationSignature", "ScopeLocalDeclaration", "SpecialFakeOverrideSignature", "Lorg/jetbrains/kotlin/ir/util/IdSignature$AccessorSignature;", "Lorg/jetbrains/kotlin/ir/util/IdSignature$CompositeSignature;", "Lorg/jetbrains/kotlin/ir/util/IdSignature$FileLocalSignature;", "Lorg/jetbrains/kotlin/ir/util/IdSignature$FileSignature;", "Lorg/jetbrains/kotlin/ir/util/IdSignature$LocalSignature;", "Lorg/jetbrains/kotlin/ir/util/IdSignature$LoweredDeclarationSignature;", "Lorg/jetbrains/kotlin/ir/util/IdSignature$ScopeLocalDeclaration;", "Lorg/jetbrains/kotlin/ir/util/IdSignature$SpecialFakeOverrideSignature;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IdSignature {
    public /* synthetic */ IdSignature(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    /* JADX INFO: renamed from: asPublic */
    public CommonSignature getAccessorSignature() {
        return null;
    }

    /* JADX INFO: renamed from: flags */
    protected long getMask() {
        return 0L;
    }

    public boolean getVisibleCrossFile() {
        return true;
    }

    public boolean isPackageSignature() {
        return false;
    }

    public abstract boolean isPubliclyVisible();

    public abstract IdSignature nearestPublicSig();

    /* JADX INFO: renamed from: packageFqName */
    public abstract FqName getFqName();

    public abstract IdSignature topLevelSignature();

    private IdSignature() {
    }

    /* JADX INFO: compiled from: IdSignature.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0007\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tJ\u000e\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\u0003R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000f¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/IdSignature$Flags;", "", "recursive", "", "(Ljava/lang/String;IZ)V", "getRecursive", "()Z", "decode", OverlayablePolicy.NAME_flags, "", "encode", "isSet", "IS_EXPECT", "IS_JAVA_FOR_KOTLIN_OVERRIDE_PROPERTY", "IS_NATIVE_INTEROP_LIBRARY", "IS_SYNTHETIC_JAVA_PROPERTY", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum Flags {
        IS_EXPECT(true),
        IS_JAVA_FOR_KOTLIN_OVERRIDE_PROPERTY(false),
        IS_NATIVE_INTEROP_LIBRARY(true),
        IS_SYNTHETIC_JAVA_PROPERTY(false);

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
        private final boolean recursive;

        public static EnumEntries<Flags> getEntries() {
            return $ENTRIES;
        }

        Flags(boolean z) {
            this.recursive = z;
        }

        public final boolean getRecursive() {
            return this.recursive;
        }

        public final long encode(boolean isSet) {
            if (isSet) {
                return 1 << ordinal();
            }
            return 0L;
        }

        public final boolean decode(long flags) {
            return (flags & (1 << ordinal())) != 0;
        }
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Rendering of signatures has been extracted to IdSignatureRenderer.render()", replaceWith = @ReplaceWith(expression = "render()", imports = {"org.jetbrains.kotlin.ir.util.render"}))
    public final /* synthetic */ String render() {
        return IdSignatureRendererKt.render$default(this, null, 1, null);
    }

    public final String toString() {
        return IdSignatureRendererKt.render$default(this, null, 1, null);
    }

    public final boolean test(Flags flags) {
        Intrinsics.checkNotNullParameter(flags, "<this>");
        return flags.decode(getMask());
    }

    public boolean getHasTopLevel() {
        return !isPackageSignature();
    }

    public boolean isLocal() {
        return !isPubliclyVisible();
    }

    /* JADX INFO: compiled from: IdSignature.kt */
    @Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\n\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\n\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B)\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006¢\u0006\u0002\u0010\bB1\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\b\u0010\t\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\nJ\u0010\u0010!\u001a\u00020\u00062\u0006\u0010\"\u001a\u00020\u0006H\u0002J\b\u0010#\u001a\u00020\u0000H\u0016J\u0013\u0010$\u001a\u00020\u00162\b\u0010%\u001a\u0004\u0018\u00010&H\u0096\u0002J\b\u0010'\u001a\u00020\u0006H\u0014J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010(\u001a\u00020\u0016H\u0016J\b\u0010)\u001a\u00020\u0000H\u0016J\b\u0010\u0002\u001a\u00020*H\u0016J\b\u0010+\u001a\u00020\u0001H\u0016R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\t\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u0011\u0010\u000e\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\fR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0015\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\n\n\u0002\u0010\u0014\u001a\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0015\u001a\u00020\u00168VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0017R\u0011\u0010\u0007\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0017\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00030\u001b8F¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\fR\u0011\u0010\u001f\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b \u0010\f¨\u0006,"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/IdSignature$CommonSignature;", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "packageFqName", "", "declarationFqName", "id", "", "mask", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;J)V", MediaTrack.ROLE_DESCRIPTION, "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Long;JLjava/lang/String;)V", "getDeclarationFqName", "()Ljava/lang/String;", "getDescription", "firstNameSegment", "getFirstNameSegment", "hashCode", "", "getId", "()Ljava/lang/Long;", "Ljava/lang/Long;", "isPubliclyVisible", "", "()Z", "getMask", "()J", "nameSegments", "", "getNameSegments", "()Ljava/util/List;", "getPackageFqName", "shortName", "getShortName", "adaptMask", "old", "asPublic", Namer.EQUALS_METHOD_NAME, "other", "", OverlayablePolicy.NAME_flags, "isPackageSignature", "nearestPublicSig", "Lorg/jetbrains/kotlin/name/FqName;", "topLevelSignature", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class CommonSignature extends IdSignature {
        private final String declarationFqName;
        private final String description;
        private final int hashCode;
        private final Long id;
        private final long mask;
        private final String packageFqName;

        @Override // org.jetbrains.kotlin.ir.util.IdSignature
        /* JADX INFO: renamed from: asPublic */
        public CommonSignature getAccessorSignature() {
            return this;
        }

        @Override // org.jetbrains.kotlin.ir.util.IdSignature
        public boolean isPubliclyVisible() {
            return true;
        }

        @Override // org.jetbrains.kotlin.ir.util.IdSignature
        public CommonSignature nearestPublicSig() {
            return this;
        }

        public final String getPackageFqName() {
            return this.packageFqName;
        }

        public final String getDeclarationFqName() {
            return this.declarationFqName;
        }

        public final Long getId() {
            return this.id;
        }

        public final long getMask() {
            return this.mask;
        }

        public final String getDescription() {
            return this.description;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public CommonSignature(String packageFqName, String declarationFqName, Long l, long j, String str) {
            super(null);
            Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
            Intrinsics.checkNotNullParameter(declarationFqName, "declarationFqName");
            this.packageFqName = packageFqName;
            this.declarationFqName = declarationFqName;
            this.id = l;
            this.mask = j;
            this.description = str;
            this.hashCode = (((((packageFqName.hashCode() * 31) + declarationFqName.hashCode()) * 31) + (l != null ? l.hashCode() : 0)) * 31) + Long.hashCode(j);
        }

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        @Deprecated(level = DeprecationLevel.WARNING, message = "When constructing 'CommonSignature', you need to set 'description' to the mangled name from which 'id' was computed, or to null if it's not applicable")
        public CommonSignature(String packageFqName, String declarationFqName, Long l, long j) {
            this(packageFqName, declarationFqName, l, j, null);
            Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
            Intrinsics.checkNotNullParameter(declarationFqName, "declarationFqName");
        }

        @Override // org.jetbrains.kotlin.ir.util.IdSignature
        /* JADX INFO: renamed from: packageFqName */
        public FqName getFqName() {
            return new FqName(this.packageFqName);
        }

        public final String getShortName() {
            return StringsKt.substringAfterLast$default(this.declarationFqName, '.', (String) null, 2, (Object) null);
        }

        public final String getFirstNameSegment() {
            return StringsKt.substringBefore$default(this.declarationFqName, '.', (String) null, 2, (Object) null);
        }

        public final List<String> getNameSegments() {
            return StringsKt.split$default((CharSequence) this.declarationFqName, new char[]{'.'}, false, 0, 6, (Object) null);
        }

        private final long adaptMask(long old) {
            long jOrdinal = 0;
            for (Flags flags : Flags.values()) {
                if (!flags.getRecursive()) {
                    jOrdinal |= (1 << flags.ordinal()) & old;
                }
            }
            return old ^ jOrdinal;
        }

        @Override // org.jetbrains.kotlin.ir.util.IdSignature
        public IdSignature topLevelSignature() {
            if (this.declarationFqName.length() == 0) {
                return this;
            }
            List<String> nameSegments = getNameSegments();
            long jAdaptMask = adaptMask(this.mask);
            return (nameSegments.size() == 1 && this.mask == jAdaptMask) ? this : new CommonSignature(this.packageFqName, (String) CollectionsKt.first((List) nameSegments), null, jAdaptMask, null);
        }

        @Override // org.jetbrains.kotlin.ir.util.IdSignature
        public boolean isPackageSignature() {
            return this.id == null && this.declarationFqName.length() == 0;
        }

        @Override // org.jetbrains.kotlin.ir.util.IdSignature
        /* JADX INFO: renamed from: flags, reason: from getter */
        protected long getMask() {
            return this.mask;
        }

        public boolean equals(Object other) {
            if (other instanceof CommonSignature) {
                CommonSignature commonSignature = (CommonSignature) other;
                if (Intrinsics.areEqual(this.packageFqName, commonSignature.packageFqName) && Intrinsics.areEqual(this.declarationFqName, commonSignature.declarationFqName) && Intrinsics.areEqual(this.id, commonSignature.id) && this.mask == commonSignature.mask) {
                    return true;
                }
            }
            return false;
        }

        /* JADX INFO: renamed from: hashCode, reason: from getter */
        public int getHashCode() {
            return this.hashCode;
        }
    }

    /* JADX INFO: compiled from: IdSignature.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0001¢\u0006\u0002\u0010\u0004J\u0013\u0010\u000e\u001a\u00020\t2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0096\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\u0001H\u0016J\b\u0010\u0014\u001a\u00020\u0015H\u0016J\b\u0010\u0016\u001a\u00020\u0001H\u0016R\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0003\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u0006R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\nR\u0014\u0010\u000b\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\nR\u0014\u0010\f\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\n¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/IdSignature$CompositeSignature;", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "container", "inner", "(Lorg/jetbrains/kotlin/ir/util/IdSignature;Lorg/jetbrains/kotlin/ir/util/IdSignature;)V", "getContainer", "()Lorg/jetbrains/kotlin/ir/util/IdSignature;", "getInner", "isLocal", "", "()Z", "isPubliclyVisible", "visibleCrossFile", "getVisibleCrossFile", Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "", "nearestPublicSig", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "topLevelSignature", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class CompositeSignature extends IdSignature {
        private final IdSignature container;
        private final IdSignature inner;

        @Override // org.jetbrains.kotlin.ir.util.IdSignature
        public boolean isPubliclyVisible() {
            return true;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public CompositeSignature(IdSignature container, IdSignature inner) {
            super(null);
            Intrinsics.checkNotNullParameter(container, "container");
            Intrinsics.checkNotNullParameter(inner, "inner");
            this.container = container;
            this.inner = inner;
        }

        public final IdSignature getContainer() {
            return this.container;
        }

        public final IdSignature getInner() {
            return this.inner;
        }

        @Override // org.jetbrains.kotlin.ir.util.IdSignature
        public boolean isLocal() {
            return this.inner.isLocal();
        }

        @Override // org.jetbrains.kotlin.ir.util.IdSignature
        public boolean getVisibleCrossFile() {
            return this.container.getVisibleCrossFile();
        }

        @Override // org.jetbrains.kotlin.ir.util.IdSignature
        public IdSignature topLevelSignature() {
            IdSignature idSignature = this.container;
            if (idSignature instanceof FileSignature) {
                return new CompositeSignature(idSignature, this.inner.topLevelSignature());
            }
            return idSignature.topLevelSignature();
        }

        @Override // org.jetbrains.kotlin.ir.util.IdSignature
        public IdSignature nearestPublicSig() {
            IdSignature idSignature = this.container;
            if (idSignature instanceof FileSignature) {
                idSignature = this.inner;
            }
            return idSignature.nearestPublicSig();
        }

        @Override // org.jetbrains.kotlin.ir.util.IdSignature
        /* JADX INFO: renamed from: packageFqName */
        public FqName getFqName() {
            IdSignature idSignature = this.container;
            if (idSignature instanceof FileSignature) {
                idSignature = this.inner;
            }
            return idSignature.getFqName();
        }

        public boolean equals(Object other) {
            if (other instanceof CompositeSignature) {
                CompositeSignature compositeSignature = (CompositeSignature) other;
                if (Intrinsics.areEqual(this.container, compositeSignature.container) && Intrinsics.areEqual(this.inner, compositeSignature.inner)) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return (this.container.hashCode() * 31) + this.inner.hashCode();
        }
    }

    /* JADX INFO: compiled from: IdSignature.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\b\u0010\u000f\u001a\u00020\u0004H\u0016J\u0013\u0010\u0010\u001a\u00020\u000b2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0096\u0002J\b\u0010\u0013\u001a\u00020\u0014H\u0014J\b\u0010\b\u001a\u00020\tH\u0016J\b\u0010\u0015\u001a\u00020\u0001H\u0016J\b\u0010\u0016\u001a\u00020\u0017H\u0016J\b\u0010\u0018\u001a\u00020\u0001H\u0016R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/IdSignature$AccessorSignature;", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "propertySignature", "accessorSignature", "Lorg/jetbrains/kotlin/ir/util/IdSignature$CommonSignature;", "(Lorg/jetbrains/kotlin/ir/util/IdSignature;Lorg/jetbrains/kotlin/ir/util/IdSignature$CommonSignature;)V", "getAccessorSignature", "()Lorg/jetbrains/kotlin/ir/util/IdSignature$CommonSignature;", "hashCode", "", "isPubliclyVisible", "", "()Z", "getPropertySignature", "()Lorg/jetbrains/kotlin/ir/util/IdSignature;", "asPublic", Namer.EQUALS_METHOD_NAME, "other", "", OverlayablePolicy.NAME_flags, "", "nearestPublicSig", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "topLevelSignature", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class AccessorSignature extends IdSignature {
        private final CommonSignature accessorSignature;
        private final int hashCode;
        private final IdSignature propertySignature;

        @Override // org.jetbrains.kotlin.ir.util.IdSignature
        public boolean isPubliclyVisible() {
            return true;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AccessorSignature(IdSignature propertySignature, CommonSignature accessorSignature) {
            super(null);
            Intrinsics.checkNotNullParameter(propertySignature, "propertySignature");
            Intrinsics.checkNotNullParameter(accessorSignature, "accessorSignature");
            this.propertySignature = propertySignature;
            this.accessorSignature = accessorSignature;
            this.hashCode = accessorSignature.getHashCode();
        }

        public final CommonSignature getAccessorSignature() {
            return this.accessorSignature;
        }

        public final IdSignature getPropertySignature() {
            return this.propertySignature;
        }

        @Override // org.jetbrains.kotlin.ir.util.IdSignature
        public IdSignature topLevelSignature() {
            return this.propertySignature.topLevelSignature();
        }

        @Override // org.jetbrains.kotlin.ir.util.IdSignature
        public IdSignature nearestPublicSig() {
            return this;
        }

        @Override // org.jetbrains.kotlin.ir.util.IdSignature
        /* JADX INFO: renamed from: packageFqName */
        public FqName getFqName() {
            return this.propertySignature.getFqName();
        }

        @Override // org.jetbrains.kotlin.ir.util.IdSignature
        /* JADX INFO: renamed from: flags */
        protected long getMask() {
            return this.accessorSignature.getMask();
        }

        @Override // org.jetbrains.kotlin.ir.util.IdSignature
        /* JADX INFO: renamed from: asPublic, reason: from getter */
        public CommonSignature getAccessorSignature() {
            return this.accessorSignature;
        }

        public boolean equals(Object other) {
            return other instanceof AccessorSignature ? Intrinsics.areEqual(this.accessorSignature, ((AccessorSignature) other).accessorSignature) : Intrinsics.areEqual(this.accessorSignature, other);
        }

        /* JADX INFO: renamed from: hashCode, reason: from getter */
        public int getHashCode() {
            return this.hashCode;
        }
    }

    /* JADX INFO: compiled from: IdSignature.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u001d\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\u0013\u0010\u0015\u001a\u00020\u000f2\b\u0010\u0016\u001a\u0004\u0018\u00010\u0006H\u0096\u0002J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\b\u0010\u0019\u001a\u00020\u000fH\u0016J\b\u0010\u001a\u001a\u00020\u0001H\u0016J\b\u0010\u001b\u001a\u00020\bH\u0016J\b\u0010\u001c\u001a\u00020\u0001H\u0016R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\u00020\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\u00020\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u0011R\u0014\u0010\u0013\u001a\u00020\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0011¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/IdSignature$FileSignature;", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "fileSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrFileSymbol;", "(Lorg/jetbrains/kotlin/ir/symbols/IrFileSymbol;)V", "id", "", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", PsiTreeChangeEvent.PROP_FILE_NAME, "", "(Ljava/lang/Object;Lorg/jetbrains/kotlin/name/FqName;Ljava/lang/String;)V", "getFileName", "()Ljava/lang/String;", "hasTopLevel", "", "getHasTopLevel", "()Z", "isPubliclyVisible", "visibleCrossFile", "getVisibleCrossFile", Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "isPackageSignature", "nearestPublicSig", "packageFqName", "topLevelSignature", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class FileSignature extends IdSignature {
        private final String fileName;
        private final FqName fqName;
        private final Object id;

        @Override // org.jetbrains.kotlin.ir.util.IdSignature
        public boolean getHasTopLevel() {
            return false;
        }

        @Override // org.jetbrains.kotlin.ir.util.IdSignature
        public boolean getVisibleCrossFile() {
            return false;
        }

        @Override // org.jetbrains.kotlin.ir.util.IdSignature
        public boolean isPackageSignature() {
            return true;
        }

        @Override // org.jetbrains.kotlin.ir.util.IdSignature
        public boolean isPubliclyVisible() {
            return true;
        }

        public final String getFileName() {
            return this.fileName;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public FileSignature(Object id2, FqName fqName, String fileName) {
            super(null);
            Intrinsics.checkNotNullParameter(id2, "id");
            Intrinsics.checkNotNullParameter(fqName, "fqName");
            Intrinsics.checkNotNullParameter(fileName, "fileName");
            this.id = id2;
            this.fqName = fqName;
            this.fileName = fileName;
        }

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        public FileSignature(IrFileSymbol fileSymbol) {
            this(fileSymbol, fileSymbol.getOwner().getPackageFqName(), fileSymbol.getOwner().getFileEntry().getName());
            Intrinsics.checkNotNullParameter(fileSymbol, "fileSymbol");
        }

        public boolean equals(Object other) {
            return (other instanceof FileSignature) && Intrinsics.areEqual(this.id, ((FileSignature) other).id);
        }

        public int hashCode() {
            return this.id.hashCode();
        }

        @Override // org.jetbrains.kotlin.ir.util.IdSignature
        public IdSignature topLevelSignature() {
            return this;
        }

        @Override // org.jetbrains.kotlin.ir.util.IdSignature
        public IdSignature nearestPublicSig() {
            throw new IllegalStateException(("Should not reach here (" + this + ')').toString());
        }

        @Override // org.jetbrains.kotlin.ir.util.IdSignature
        /* JADX INFO: renamed from: packageFqName, reason: from getter */
        public FqName getFqName() {
            return this.fqName;
        }
    }

    /* JADX INFO: compiled from: IdSignature.kt */
    @Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0007J\u0013\u0010\u0012\u001a\u00020\u000e2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0096\u0002J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\u0006\u0010\u0017\u001a\u00020\u0016J\b\u0010\u0018\u001a\u00020\u0001H\u0016J\b\u0010\u0019\u001a\u00020\u001aH\u0016J\b\u0010\u001b\u001a\u00020\u0001H\u0016R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0015\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\n\n\u0002\u0010\f\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\r\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000fR\u0014\u0010\u0010\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\t¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/IdSignature$LocalSignature;", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "localFqn", "", "hashSig", "", MediaTrack.ROLE_DESCRIPTION, "(Ljava/lang/String;Ljava/lang/Long;Ljava/lang/String;)V", "getDescription", "()Ljava/lang/String;", "getHashSig", "()Ljava/lang/Long;", "Ljava/lang/Long;", "isLocal", "", "()Z", "isPubliclyVisible", "getLocalFqn", Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "", "index", "nearestPublicSig", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "topLevelSignature", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class LocalSignature extends IdSignature {
        private final String description;
        private final Long hashSig;
        private final String localFqn;

        public final int index() {
            Long l = this.hashSig;
            if (l != null) {
                return (int) l.longValue();
            }
            throw new IllegalStateException(("Expected index in " + this).toString());
        }

        @Override // org.jetbrains.kotlin.ir.util.IdSignature
        public boolean isLocal() {
            return true;
        }

        @Override // org.jetbrains.kotlin.ir.util.IdSignature
        public boolean isPubliclyVisible() {
            return false;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public LocalSignature(String localFqn, Long l, String str) {
            super(null);
            Intrinsics.checkNotNullParameter(localFqn, "localFqn");
            this.localFqn = localFqn;
            this.hashSig = l;
            this.description = str;
        }

        public final String getDescription() {
            return this.description;
        }

        public final Long getHashSig() {
            return this.hashSig;
        }

        public final String getLocalFqn() {
            return this.localFqn;
        }

        @Override // org.jetbrains.kotlin.ir.util.IdSignature
        public IdSignature topLevelSignature() {
            throw new IllegalStateException(("Illegal access: Local Sig does not have toplevel (" + this).toString());
        }

        @Override // org.jetbrains.kotlin.ir.util.IdSignature
        public IdSignature nearestPublicSig() {
            throw new IllegalStateException(("Illegal access: Local Sig does not have information about its public part (" + this).toString());
        }

        @Override // org.jetbrains.kotlin.ir.util.IdSignature
        /* JADX INFO: renamed from: packageFqName */
        public FqName getFqName() {
            throw new IllegalStateException(("Illegal access: Local signature does not have package (" + this).toString());
        }

        public boolean equals(Object other) {
            if (other instanceof LocalSignature) {
                LocalSignature localSignature = (LocalSignature) other;
                if (Intrinsics.areEqual(this.localFqn, localSignature.localFqn) && Intrinsics.areEqual(this.hashSig, localSignature.hashSig)) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            Long l = this.hashSig;
            return (((int) (l != null ? l.longValue() : 0L)) * 31) + this.localFqn.hashCode();
        }
    }

    /* JADX INFO: compiled from: IdSignature.kt */
    @Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00010\u0004¢\u0006\u0002\u0010\u0005J\n\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0016J\u0013\u0010\u0011\u001a\u00020\t2\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0096\u0002J\b\u0010\u0006\u001a\u00020\u0007H\u0016J\b\u0010\u0014\u001a\u00020\u0001H\u0016J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\b\u0010\u0017\u001a\u00020\u0001H\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00010\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/IdSignature$SpecialFakeOverrideSignature;", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "memberSignature", "overriddenSignatures", "", "(Lorg/jetbrains/kotlin/ir/util/IdSignature;Ljava/util/List;)V", "hashCode", "", "isPubliclyVisible", "", "()Z", "getMemberSignature", "()Lorg/jetbrains/kotlin/ir/util/IdSignature;", "getOverriddenSignatures", "()Ljava/util/List;", "asPublic", "Lorg/jetbrains/kotlin/ir/util/IdSignature$CommonSignature;", Namer.EQUALS_METHOD_NAME, "other", "", "nearestPublicSig", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "topLevelSignature", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SpecialFakeOverrideSignature extends IdSignature {
        private final int hashCode;
        private final IdSignature memberSignature;
        private final List<IdSignature> overriddenSignatures;

        public final IdSignature getMemberSignature() {
            return this.memberSignature;
        }

        public final List<IdSignature> getOverriddenSignatures() {
            return this.overriddenSignatures;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public SpecialFakeOverrideSignature(IdSignature memberSignature, List<? extends IdSignature> overriddenSignatures) {
            super(null);
            Intrinsics.checkNotNullParameter(memberSignature, "memberSignature");
            Intrinsics.checkNotNullParameter(overriddenSignatures, "overriddenSignatures");
            this.memberSignature = memberSignature;
            this.overriddenSignatures = overriddenSignatures;
            this.hashCode = (memberSignature.hashCode() * 31) + overriddenSignatures.hashCode();
        }

        @Override // org.jetbrains.kotlin.ir.util.IdSignature
        public boolean isPubliclyVisible() {
            return this.memberSignature.isPubliclyVisible();
        }

        @Override // org.jetbrains.kotlin.ir.util.IdSignature
        /* JADX INFO: renamed from: asPublic */
        public CommonSignature getAccessorSignature() {
            return this.memberSignature.getAccessorSignature();
        }

        @Override // org.jetbrains.kotlin.ir.util.IdSignature
        public IdSignature topLevelSignature() {
            return this.memberSignature.topLevelSignature();
        }

        @Override // org.jetbrains.kotlin.ir.util.IdSignature
        public IdSignature nearestPublicSig() {
            if (this.memberSignature.isPubliclyVisible()) {
                return this;
            }
            return this.memberSignature.nearestPublicSig();
        }

        @Override // org.jetbrains.kotlin.ir.util.IdSignature
        /* JADX INFO: renamed from: packageFqName */
        public FqName getFqName() {
            return this.memberSignature.getFqName();
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!Intrinsics.areEqual(getClass(), other != null ? other.getClass() : null)) {
                return false;
            }
            Intrinsics.checkNotNull(other, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.util.IdSignature.SpecialFakeOverrideSignature");
            SpecialFakeOverrideSignature specialFakeOverrideSignature = (SpecialFakeOverrideSignature) other;
            return Intrinsics.areEqual(this.memberSignature, specialFakeOverrideSignature.memberSignature) && Intrinsics.areEqual(this.overriddenSignatures, specialFakeOverrideSignature.overriddenSignatures);
        }

        /* JADX INFO: renamed from: hashCode, reason: from getter */
        public int getHashCode() {
            return this.hashCode;
        }
    }

    /* JADX INFO: compiled from: IdSignature.kt */
    @Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007J\u0013\u0010\u0015\u001a\u00020\u00112\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0096\u0002J\b\u0010\f\u001a\u00020\rH\u0016J\b\u0010\u0018\u001a\u00020\u0001H\u0016J\b\u0010\u0019\u001a\u00020\u001aH\u0016J\b\u0010\u001b\u001a\u00020\u0001H\u0016R\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0010\u001a\u00020\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0012R\u0014\u0010\u0013\u001a\u00020\u00118VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0012¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/IdSignature$FileLocalSignature;", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "container", "id", "", MediaTrack.ROLE_DESCRIPTION, "", "(Lorg/jetbrains/kotlin/ir/util/IdSignature;JLjava/lang/String;)V", "getContainer", "()Lorg/jetbrains/kotlin/ir/util/IdSignature;", "getDescription", "()Ljava/lang/String;", "hashCode", "", "getId", "()J", "isPubliclyVisible", "", "()Z", "visibleCrossFile", "getVisibleCrossFile", Namer.EQUALS_METHOD_NAME, "other", "", "nearestPublicSig", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "topLevelSignature", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class FileLocalSignature extends IdSignature {
        private final IdSignature container;
        private final String description;
        private final int hashCode;
        private final long id;

        @Override // org.jetbrains.kotlin.ir.util.IdSignature
        public boolean getVisibleCrossFile() {
            return false;
        }

        @Override // org.jetbrains.kotlin.ir.util.IdSignature
        public boolean isPubliclyVisible() {
            return false;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public FileLocalSignature(IdSignature container, long j, String str) {
            super(null);
            Intrinsics.checkNotNullParameter(container, "container");
            this.container = container;
            this.id = j;
            this.description = str;
            this.hashCode = (container.hashCode() * 31) + Long.hashCode(j);
        }

        public /* synthetic */ FileLocalSignature(IdSignature idSignature, long j, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(idSignature, j, (i & 4) != 0 ? null : str);
        }

        public final IdSignature getContainer() {
            return this.container;
        }

        public final String getDescription() {
            return this.description;
        }

        public final long getId() {
            return this.id;
        }

        @Override // org.jetbrains.kotlin.ir.util.IdSignature
        /* JADX INFO: renamed from: packageFqName */
        public FqName getFqName() {
            return this.container.getFqName();
        }

        @Override // org.jetbrains.kotlin.ir.util.IdSignature
        public IdSignature topLevelSignature() {
            IdSignature idSignature = this.container.topLevelSignature();
            return (idSignature == this.container && (idSignature instanceof CommonSignature) && ((CommonSignature) idSignature).getDeclarationFqName().length() == 0) ? this : idSignature;
        }

        @Override // org.jetbrains.kotlin.ir.util.IdSignature
        public IdSignature nearestPublicSig() {
            return this.container.nearestPublicSig();
        }

        public boolean equals(Object other) {
            if (other instanceof FileLocalSignature) {
                FileLocalSignature fileLocalSignature = (FileLocalSignature) other;
                if (this.id == fileLocalSignature.id && Intrinsics.areEqual(this.container, fileLocalSignature.container)) {
                    return true;
                }
            }
            return false;
        }

        /* JADX INFO: renamed from: hashCode, reason: from getter */
        public int getHashCode() {
            return this.hashCode;
        }
    }

    /* JADX INFO: compiled from: IdSignature.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u0013\u0010\u0012\u001a\u00020\n2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0096\u0002J\b\u0010\u0015\u001a\u00020\u0003H\u0016J\b\u0010\u0016\u001a\u00020\u0001H\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\b\u0010\u0019\u001a\u00020\u0001H\u0016R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\fR\u0014\u0010\u0010\u001a\u00020\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\f¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/IdSignature$ScopeLocalDeclaration;", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "id", "", MediaTrack.ROLE_DESCRIPTION, "", "(ILjava/lang/String;)V", "getDescription", "()Ljava/lang/String;", "hasTopLevel", "", "getHasTopLevel", "()Z", "getId", "()I", "isPubliclyVisible", "visibleCrossFile", "getVisibleCrossFile", Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "nearestPublicSig", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "topLevelSignature", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ScopeLocalDeclaration extends IdSignature {
        private final String description;
        private final int id;

        @Override // org.jetbrains.kotlin.ir.util.IdSignature
        public boolean getHasTopLevel() {
            return false;
        }

        @Override // org.jetbrains.kotlin.ir.util.IdSignature
        public boolean getVisibleCrossFile() {
            return false;
        }

        @Override // org.jetbrains.kotlin.ir.util.IdSignature
        public boolean isPubliclyVisible() {
            return false;
        }

        public ScopeLocalDeclaration(int i, String str) {
            super(null);
            this.id = i;
            this.description = str;
        }

        public /* synthetic */ ScopeLocalDeclaration(int i, String str, int i2, DefaultConstructorMarker defaultConstructorMarker) {
            this(i, (i2 & 2) != 0 ? null : str);
        }

        public final String getDescription() {
            return this.description;
        }

        public final int getId() {
            return this.id;
        }

        @Override // org.jetbrains.kotlin.ir.util.IdSignature
        public IdSignature topLevelSignature() {
            throw new IllegalStateException("Is not supported for Local ID".toString());
        }

        @Override // org.jetbrains.kotlin.ir.util.IdSignature
        public IdSignature nearestPublicSig() {
            throw new IllegalStateException("Is not supported for Local ID".toString());
        }

        @Override // org.jetbrains.kotlin.ir.util.IdSignature
        /* JADX INFO: renamed from: packageFqName */
        public FqName getFqName() {
            throw new IllegalStateException("Is not supported for Local ID".toString());
        }

        public boolean equals(Object other) {
            return (other instanceof ScopeLocalDeclaration) && this.id == ((ScopeLocalDeclaration) other).id;
        }

        public int hashCode() {
            return this.id;
        }
    }

    /* JADX INFO: compiled from: IdSignature.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0002\u0010\u0006J\u0013\u0010\u0014\u001a\u00020\b2\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0096\u0002J\b\u0010\u000b\u001a\u00020\u0004H\u0016J\b\u0010\u0017\u001a\u00020\u0001H\u0016J\b\u0010\u0018\u001a\u00020\u0019H\u0016J\b\u0010\u001a\u001a\u00020\u0001H\u0016R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\rR\u0014\u0010\u0012\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\n¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/ir/util/IdSignature$LoweredDeclarationSignature;", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "original", "stage", "", "index", "(Lorg/jetbrains/kotlin/ir/util/IdSignature;II)V", "hasTopLevel", "", "getHasTopLevel", "()Z", "hashCode", "getIndex", "()I", "isPubliclyVisible", "getOriginal", "()Lorg/jetbrains/kotlin/ir/util/IdSignature;", "getStage", "visibleCrossFile", "getVisibleCrossFile", Namer.EQUALS_METHOD_NAME, "other", "", "nearestPublicSig", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "topLevelSignature", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class LoweredDeclarationSignature extends IdSignature {
        private final int hashCode;
        private final int index;
        private final IdSignature original;
        private final int stage;

        @Override // org.jetbrains.kotlin.ir.util.IdSignature
        public boolean getHasTopLevel() {
            return true;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public LoweredDeclarationSignature(IdSignature original, int i, int i2) {
            super(null);
            Intrinsics.checkNotNullParameter(original, "original");
            this.original = original;
            this.stage = i;
            this.index = i2;
            this.hashCode = (((i2 * 31) + i) * 31) + original.hashCode();
        }

        public final int getIndex() {
            return this.index;
        }

        public final IdSignature getOriginal() {
            return this.original;
        }

        public final int getStage() {
            return this.stage;
        }

        @Override // org.jetbrains.kotlin.ir.util.IdSignature
        public boolean isPubliclyVisible() {
            return this.original.isPubliclyVisible();
        }

        @Override // org.jetbrains.kotlin.ir.util.IdSignature
        public boolean getVisibleCrossFile() {
            return this.original.getVisibleCrossFile();
        }

        @Override // org.jetbrains.kotlin.ir.util.IdSignature
        public IdSignature topLevelSignature() {
            return this;
        }

        @Override // org.jetbrains.kotlin.ir.util.IdSignature
        public IdSignature nearestPublicSig() {
            return this;
        }

        @Override // org.jetbrains.kotlin.ir.util.IdSignature
        /* JADX INFO: renamed from: packageFqName */
        public FqName getFqName() {
            return this.original.getFqName();
        }

        public boolean equals(Object other) {
            if (other instanceof LoweredDeclarationSignature) {
                LoweredDeclarationSignature loweredDeclarationSignature = (LoweredDeclarationSignature) other;
                if (Intrinsics.areEqual(this.original, loweredDeclarationSignature.original) && this.stage == loweredDeclarationSignature.stage && this.index == loweredDeclarationSignature.index) {
                    return true;
                }
            }
            return false;
        }

        /* JADX INFO: renamed from: hashCode, reason: from getter */
        public int getHashCode() {
            return this.hashCode;
        }
    }
}
