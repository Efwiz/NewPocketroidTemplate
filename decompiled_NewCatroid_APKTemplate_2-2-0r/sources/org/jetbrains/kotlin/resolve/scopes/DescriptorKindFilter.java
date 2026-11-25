package org.jetbrains.kotlin.resolve.scopes;

import androidx.exifinterface.media.ExifInterface;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PackageViewDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX INFO: compiled from: MemberScope.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u0000 \u001e2\u00020\u0001:\u0001\u001eB\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u0003J\u0013\u0010\u0012\u001a\u00020\r2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\u0011\u0010\u0014\u001a\u00020\u00002\u0006\u0010\u0014\u001a\u00020\u0006H\u0086\u0004J\b\u0010\u0015\u001a\u00020\u0003H\u0016J\u000e\u0010\u0016\u001a\u00020\u00002\u0006\u0010\u0013\u001a\u00020\u0000J\u000e\u0010\u0017\u001a\u00020\u00002\u0006\u0010\u0011\u001a\u00020\u0003J\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u00002\u0006\u0010\u0011\u001a\u00020\u0003J\b\u0010\u0019\u001a\u00020\u001aH\u0016J\u000e\u0010\u001b\u001a\u00020\u00002\u0006\u0010\u0011\u001a\u00020\u0003J\u000e\u0010\u001c\u001a\u00020\u00002\u0006\u0010\u0011\u001a\u00020\u0003J\f\u0010\u001d\u001a\u00020\u0003*\u00020\u000fH\u0002R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/DescriptorKindFilter;", "", "kindMask", "", "excludes", "", "Lorg/jetbrains/kotlin/resolve/scopes/DescriptorKindExclude;", "(ILjava/util/List;)V", "getExcludes", "()Ljava/util/List;", "getKindMask", "()I", "accepts", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "acceptsKinds", "kinds", Namer.EQUALS_METHOD_NAME, "other", "exclude", "hashCode", "intersect", "restrictedToKinds", "restrictedToKindsOrNull", "toString", "", "withKinds", "withoutKinds", Namer.METADATA_CLASS_KIND, "Companion", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DescriptorKindFilter {
    public static final DescriptorKindFilter ALL;
    private static final int ALL_KINDS_MASK;
    public static final DescriptorKindFilter CALLABLES;
    private static final int CALLABLES_MASK;
    public static final DescriptorKindFilter CLASSIFIERS;
    private static final int CLASSIFIERS_MASK;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE;
    private static final List<Companion.MaskToName> DEBUG_MASK_BIT_NAMES;
    private static final List<Companion.MaskToName> DEBUG_PREDEFINED_FILTERS_MASK_NAMES;
    public static final DescriptorKindFilter FUNCTIONS;
    private static final int FUNCTIONS_MASK;
    public static final DescriptorKindFilter NON_SINGLETON_CLASSIFIERS;
    private static final int NON_SINGLETON_CLASSIFIERS_MASK;
    public static final DescriptorKindFilter PACKAGES;
    private static final int PACKAGES_MASK;
    public static final DescriptorKindFilter SINGLETON_CLASSIFIERS;
    private static final int SINGLETON_CLASSIFIERS_MASK;
    public static final DescriptorKindFilter TYPE_ALIASES;
    private static final int TYPE_ALIASES_MASK;
    public static final DescriptorKindFilter VALUES;
    private static final int VALUES_MASK;
    public static final DescriptorKindFilter VARIABLES;
    private static final int VARIABLES_MASK;
    private static int nextMaskValue;
    private final List<DescriptorKindExclude> excludes;
    private final int kindMask;

    /* JADX WARN: Multi-variable type inference failed */
    public DescriptorKindFilter(int i, List<? extends DescriptorKindExclude> excludes) {
        Intrinsics.checkNotNullParameter(excludes, "excludes");
        this.excludes = excludes;
        Iterator it = excludes.iterator();
        while (it.hasNext()) {
            i &= ~((DescriptorKindExclude) it.next()).getFullyExcludedDescriptorKinds();
        }
        this.kindMask = i;
    }

    public /* synthetic */ DescriptorKindFilter(int i, List list, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, (i2 & 2) != 0 ? CollectionsKt.emptyList() : list);
    }

    public final List<DescriptorKindExclude> getExcludes() {
        return this.excludes;
    }

    public final int getKindMask() {
        return this.kindMask;
    }

    public final boolean accepts(DeclarationDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if ((this.kindMask & kind(descriptor)) != 0) {
            List<DescriptorKindExclude> list = this.excludes;
            if (!(list instanceof Collection) || !list.isEmpty()) {
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    if (((DescriptorKindExclude) it.next()).excludes(descriptor)) {
                    }
                }
            }
            return true;
        }
        return false;
    }

    public final boolean acceptsKinds(int kinds) {
        return (kinds & this.kindMask) != 0;
    }

    public final DescriptorKindFilter exclude(DescriptorKindExclude exclude) {
        Intrinsics.checkNotNullParameter(exclude, "exclude");
        return new DescriptorKindFilter(this.kindMask, CollectionsKt.plus((Collection) this.excludes, (Iterable) CollectionsKt.listOf(exclude)));
    }

    public final DescriptorKindFilter withoutKinds(int kinds) {
        return new DescriptorKindFilter((~kinds) & this.kindMask, this.excludes);
    }

    public final DescriptorKindFilter withKinds(int kinds) {
        return new DescriptorKindFilter(kinds | this.kindMask, this.excludes);
    }

    public final DescriptorKindFilter restrictedToKinds(int kinds) {
        return new DescriptorKindFilter(kinds & this.kindMask, this.excludes);
    }

    public final DescriptorKindFilter restrictedToKindsOrNull(int kinds) {
        int i = kinds & this.kindMask;
        if (i == 0) {
            return null;
        }
        return new DescriptorKindFilter(i, this.excludes);
    }

    public final DescriptorKindFilter intersect(DescriptorKindFilter other) {
        Intrinsics.checkNotNullParameter(other, "other");
        return new DescriptorKindFilter(this.kindMask & other.kindMask, CollectionsKt.plus((Collection) this.excludes, (Iterable) other.excludes));
    }

    public String toString() {
        Object next;
        Iterator<T> it = DEBUG_PREDEFINED_FILTERS_MASK_NAMES.iterator();
        do {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
        } while (((Companion.MaskToName) next).getMask() != this.kindMask);
        Companion.MaskToName maskToName = (Companion.MaskToName) next;
        String name2 = maskToName != null ? maskToName.getName() : null;
        if (name2 == null) {
            List<Companion.MaskToName> list = DEBUG_MASK_BIT_NAMES;
            ArrayList arrayList = new ArrayList();
            for (Companion.MaskToName maskToName2 : list) {
                String name3 = acceptsKinds(maskToName2.getMask()) ? maskToName2.getName() : null;
                if (name3 != null) {
                    arrayList.add(name3);
                }
            }
            name2 = CollectionsKt.joinToString$default(arrayList, " | ", null, null, 0, null, null, 62, null);
        }
        return "DescriptorKindFilter(" + name2 + ", " + this.excludes + ')';
    }

    private final int kind(DeclarationDescriptor declarationDescriptor) {
        if (declarationDescriptor instanceof ClassDescriptor) {
            return ((ClassDescriptor) declarationDescriptor).getKind().isSingleton() ? SINGLETON_CLASSIFIERS_MASK : NON_SINGLETON_CLASSIFIERS_MASK;
        }
        if (declarationDescriptor instanceof TypeAliasDescriptor) {
            return TYPE_ALIASES_MASK;
        }
        if (declarationDescriptor instanceof ClassifierDescriptor) {
            return NON_SINGLETON_CLASSIFIERS_MASK;
        }
        if (declarationDescriptor instanceof PackageFragmentDescriptor ? true : declarationDescriptor instanceof PackageViewDescriptor) {
            return PACKAGES_MASK;
        }
        if (declarationDescriptor instanceof FunctionDescriptor) {
            return FUNCTIONS_MASK;
        }
        if (declarationDescriptor instanceof VariableDescriptor) {
            return VARIABLES_MASK;
        }
        return 0;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!Intrinsics.areEqual(getClass(), other != null ? other.getClass() : null)) {
            return false;
        }
        Intrinsics.checkNotNull(other, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter");
        DescriptorKindFilter descriptorKindFilter = (DescriptorKindFilter) other;
        return Intrinsics.areEqual(this.excludes, descriptorKindFilter.excludes) && this.kindMask == descriptorKindFilter.kindMask;
    }

    public int hashCode() {
        return (this.excludes.hashCode() * 31) + this.kindMask;
    }

    /* JADX INFO: compiled from: MemberScope.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0019\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001:\u0001.B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010)\u001a\u00020\u0006H\u0002J#\u0010*\u001a\u0010\u0012\f\u0012\n ,*\u0004\u0018\u00010+0+0\u0010\"\n\b\u0000\u0010-\u0018\u0001*\u00020\u0001H\u0082\bR\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0010\u0010\t\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\n\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\bR\u0010\u0010\f\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\r\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\bR\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0014\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\bR\u0010\u0010\u0016\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0017\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\bR\u0010\u0010\u0019\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u001a\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\bR\u0010\u0010\u001c\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u001d\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\bR\u0010\u0010\u001f\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0011\u0010 \u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\bR\u0010\u0010\"\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0011\u0010#\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b$\u0010\bR\u0010\u0010%\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0011\u0010&\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b'\u0010\bR\u000e\u0010(\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006/"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/DescriptorKindFilter$Companion;", "", "()V", "ALL", "Lorg/jetbrains/kotlin/resolve/scopes/DescriptorKindFilter;", "ALL_KINDS_MASK", "", "getALL_KINDS_MASK", "()I", "CALLABLES", "CALLABLES_MASK", "getCALLABLES_MASK", "CLASSIFIERS", "CLASSIFIERS_MASK", "getCLASSIFIERS_MASK", "DEBUG_MASK_BIT_NAMES", "", "Lorg/jetbrains/kotlin/resolve/scopes/DescriptorKindFilter$Companion$MaskToName;", "DEBUG_PREDEFINED_FILTERS_MASK_NAMES", "FUNCTIONS", "FUNCTIONS_MASK", "getFUNCTIONS_MASK", "NON_SINGLETON_CLASSIFIERS", "NON_SINGLETON_CLASSIFIERS_MASK", "getNON_SINGLETON_CLASSIFIERS_MASK", "PACKAGES", "PACKAGES_MASK", "getPACKAGES_MASK", "SINGLETON_CLASSIFIERS", "SINGLETON_CLASSIFIERS_MASK", "getSINGLETON_CLASSIFIERS_MASK", "TYPE_ALIASES", "TYPE_ALIASES_MASK", "getTYPE_ALIASES_MASK", "VALUES", "VALUES_MASK", "getVALUES_MASK", "VARIABLES", "VARIABLES_MASK", "getVARIABLES_MASK", "nextMaskValue", "nextMask", "staticFields", "Ljava/lang/reflect/Field;", JvmProtoBufUtil.PLATFORM_TYPE_ID, ExifInterface.GPS_DIRECTION_TRUE, "MaskToName", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final int nextMask() {
            int i = DescriptorKindFilter.nextMaskValue;
            Companion companion = DescriptorKindFilter.INSTANCE;
            DescriptorKindFilter.nextMaskValue <<= 1;
            return i;
        }

        public final int getNON_SINGLETON_CLASSIFIERS_MASK() {
            return DescriptorKindFilter.NON_SINGLETON_CLASSIFIERS_MASK;
        }

        public final int getSINGLETON_CLASSIFIERS_MASK() {
            return DescriptorKindFilter.SINGLETON_CLASSIFIERS_MASK;
        }

        public final int getTYPE_ALIASES_MASK() {
            return DescriptorKindFilter.TYPE_ALIASES_MASK;
        }

        public final int getPACKAGES_MASK() {
            return DescriptorKindFilter.PACKAGES_MASK;
        }

        public final int getFUNCTIONS_MASK() {
            return DescriptorKindFilter.FUNCTIONS_MASK;
        }

        public final int getVARIABLES_MASK() {
            return DescriptorKindFilter.VARIABLES_MASK;
        }

        public final int getALL_KINDS_MASK() {
            return DescriptorKindFilter.ALL_KINDS_MASK;
        }

        public final int getCLASSIFIERS_MASK() {
            return DescriptorKindFilter.CLASSIFIERS_MASK;
        }

        public final int getVALUES_MASK() {
            return DescriptorKindFilter.VALUES_MASK;
        }

        public final int getCALLABLES_MASK() {
            return DescriptorKindFilter.CALLABLES_MASK;
        }

        /* JADX INFO: compiled from: MemberScope.kt */
        @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/scopes/DescriptorKindFilter$Companion$MaskToName;", "", "mask", "", "name", "", "(ILjava/lang/String;)V", "getMask", "()I", "getName", "()Ljava/lang/String;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
        private static final class MaskToName {
            private final int mask;
            private final String name;

            public MaskToName(int i, String name2) {
                Intrinsics.checkNotNullParameter(name2, "name");
                this.mask = i;
                this.name = name2;
            }

            public final int getMask() {
                return this.mask;
            }

            public final String getName() {
                return this.name;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    static {
        Companion.MaskToName maskToName;
        Companion.MaskToName maskToName2;
        Companion companion = new Companion(null);
        INSTANCE = companion;
        nextMaskValue = 1;
        int iNextMask = companion.nextMask();
        NON_SINGLETON_CLASSIFIERS_MASK = iNextMask;
        int iNextMask2 = companion.nextMask();
        SINGLETON_CLASSIFIERS_MASK = iNextMask2;
        int iNextMask3 = companion.nextMask();
        TYPE_ALIASES_MASK = iNextMask3;
        int iNextMask4 = companion.nextMask();
        PACKAGES_MASK = iNextMask4;
        int iNextMask5 = companion.nextMask();
        FUNCTIONS_MASK = iNextMask5;
        int iNextMask6 = companion.nextMask();
        VARIABLES_MASK = iNextMask6;
        int iNextMask7 = companion.nextMask() - 1;
        ALL_KINDS_MASK = iNextMask7;
        int i = iNextMask | iNextMask2 | iNextMask3;
        CLASSIFIERS_MASK = i;
        int i2 = iNextMask2 | iNextMask5 | iNextMask6;
        VALUES_MASK = i2;
        int i3 = iNextMask5 | iNextMask6;
        CALLABLES_MASK = i3;
        int i4 = 2;
        ALL = new DescriptorKindFilter(iNextMask7, 0 == true ? 1 : 0, i4, 0 == true ? 1 : 0);
        CALLABLES = new DescriptorKindFilter(i3, 0 == true ? 1 : 0, i4, 0 == true ? 1 : 0);
        NON_SINGLETON_CLASSIFIERS = new DescriptorKindFilter(iNextMask, 0 == true ? 1 : 0, i4, 0 == true ? 1 : 0);
        SINGLETON_CLASSIFIERS = new DescriptorKindFilter(iNextMask2, 0 == true ? 1 : 0, i4, 0 == true ? 1 : 0);
        TYPE_ALIASES = new DescriptorKindFilter(iNextMask3, 0 == true ? 1 : 0, i4, 0 == true ? 1 : 0);
        CLASSIFIERS = new DescriptorKindFilter(i, 0 == true ? 1 : 0, i4, 0 == true ? 1 : 0);
        PACKAGES = new DescriptorKindFilter(iNextMask4, 0 == true ? 1 : 0, i4, 0 == true ? 1 : 0);
        FUNCTIONS = new DescriptorKindFilter(iNextMask5, 0 == true ? 1 : 0, i4, 0 == true ? 1 : 0);
        VARIABLES = new DescriptorKindFilter(iNextMask6, 0 == true ? 1 : 0, i4, 0 == true ? 1 : 0);
        VALUES = new DescriptorKindFilter(i2, 0 == true ? 1 : 0, i4, 0 == true ? 1 : 0);
        Field[] fields = DescriptorKindFilter.class.getFields();
        Intrinsics.checkNotNullExpressionValue(fields, "getFields(...)");
        ArrayList<Field> arrayList = new ArrayList();
        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers())) {
                arrayList.add(field);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (Field field2 : arrayList) {
            Object obj = field2.get(null);
            DescriptorKindFilter descriptorKindFilter = obj instanceof DescriptorKindFilter ? (DescriptorKindFilter) obj : null;
            if (descriptorKindFilter != null) {
                int i5 = descriptorKindFilter.kindMask;
                String name2 = field2.getName();
                Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                maskToName2 = new Companion.MaskToName(i5, name2);
            } else {
                maskToName2 = null;
            }
            if (maskToName2 != null) {
                arrayList2.add(maskToName2);
            }
        }
        DEBUG_PREDEFINED_FILTERS_MASK_NAMES = arrayList2;
        Field[] fields2 = DescriptorKindFilter.class.getFields();
        Intrinsics.checkNotNullExpressionValue(fields2, "getFields(...)");
        ArrayList arrayList3 = new ArrayList();
        for (Field field3 : fields2) {
            if (Modifier.isStatic(field3.getModifiers())) {
                arrayList3.add(field3);
            }
        }
        ArrayList<Field> arrayList4 = new ArrayList();
        for (Object obj2 : arrayList3) {
            if (Intrinsics.areEqual(((Field) obj2).getType(), Integer.TYPE)) {
                arrayList4.add(obj2);
            }
        }
        ArrayList arrayList5 = new ArrayList();
        for (Field field4 : arrayList4) {
            Object obj3 = field4.get(null);
            Intrinsics.checkNotNull(obj3, "null cannot be cast to non-null type kotlin.Int");
            int iIntValue = ((Integer) obj3).intValue();
            if (iIntValue == ((-iIntValue) & iIntValue)) {
                String name3 = field4.getName();
                Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
                maskToName = new Companion.MaskToName(iIntValue, name3);
            } else {
                maskToName = null;
            }
            if (maskToName != null) {
                arrayList5.add(maskToName);
            }
        }
        DEBUG_MASK_BIT_NAMES = arrayList5;
    }
}
