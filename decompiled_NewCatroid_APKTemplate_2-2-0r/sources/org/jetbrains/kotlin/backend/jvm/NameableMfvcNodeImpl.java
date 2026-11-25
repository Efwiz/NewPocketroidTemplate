package org.jetbrains.kotlin.backend.jvm;

import com.beust.jcommander.Parameters;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.state.KotlinTypeMapper;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: MfvcNode.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u0000 \u00132\u00020\u0001:\u0001\u0013B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tR\u0011\u0010\n\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\fR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/NameableMfvcNodeImpl;", "", "methodFullNameMode", "Lorg/jetbrains/kotlin/backend/jvm/NameableMfvcNodeImpl$Companion$MethodFullNameMode;", "nameParts", "", "Lorg/jetbrains/kotlin/name/Name;", "unboxMethod", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "(Lorg/jetbrains/kotlin/backend/jvm/NameableMfvcNodeImpl$Companion$MethodFullNameMode;Ljava/util/List;Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;)V", "fullFieldName", "getFullFieldName", "()Lorg/jetbrains/kotlin/name/Name;", "fullMethodName", "getFullMethodName", "getNameParts", "()Ljava/util/List;", "getUnboxMethod", "()Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "Companion", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NameableMfvcNodeImpl {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final Name fullFieldName;
    private final Name fullMethodName;
    private final List<Name> nameParts;
    private final IrSimpleFunction unboxMethod;

    @JvmStatic
    public static final Name makeFullFieldName(List<Name> list) {
        return INSTANCE.makeFullFieldName(list);
    }

    @JvmStatic
    public static final Name makeFullMethodName(Companion.MethodFullNameMode methodFullNameMode, List<Name> list) {
        return INSTANCE.makeFullMethodName(methodFullNameMode, list);
    }

    public NameableMfvcNodeImpl(Companion.MethodFullNameMode methodFullNameMode, List<Name> nameParts, IrSimpleFunction unboxMethod) {
        Intrinsics.checkNotNullParameter(methodFullNameMode, "methodFullNameMode");
        Intrinsics.checkNotNullParameter(nameParts, "nameParts");
        Intrinsics.checkNotNullParameter(unboxMethod, "unboxMethod");
        this.nameParts = nameParts;
        this.unboxMethod = unboxMethod;
        Companion companion = INSTANCE;
        this.fullMethodName = companion.makeFullMethodName(methodFullNameMode, nameParts);
        this.fullFieldName = companion.makeFullFieldName(nameParts);
    }

    public final List<Name> getNameParts() {
        return this.nameParts;
    }

    public final IrSimpleFunction getUnboxMethod() {
        return this.unboxMethod;
    }

    public final Name getFullMethodName() {
        return this.fullMethodName;
    }

    public final Name getFullFieldName() {
        return this.fullFieldName;
    }

    /* JADX INFO: compiled from: MfvcNode.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001:\u0001\nB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006H\u0007J\u001e\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006H\u0007¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/NameableMfvcNodeImpl$Companion;", "", "()V", "makeFullFieldName", "Lorg/jetbrains/kotlin/name/Name;", "nameParts", "", "makeFullMethodName", "methodFullNameMode", "Lorg/jetbrains/kotlin/backend/jvm/NameableMfvcNodeImpl$Companion$MethodFullNameMode;", "MethodFullNameMode", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {

        /* JADX INFO: compiled from: MfvcNode.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/NameableMfvcNodeImpl$Companion$MethodFullNameMode;", "", "(Ljava/lang/String;I)V", "UnboxFunction", "Getter", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public enum MethodFullNameMode {
            UnboxFunction,
            Getter;

            private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

            public static EnumEntries<MethodFullNameMode> getEntries() {
                return $ENTRIES;
            }
        }

        /* JADX INFO: compiled from: MfvcNode.kt */
        @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[MethodFullNameMode.values().length];
                try {
                    iArr[MethodFullNameMode.UnboxFunction.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[MethodFullNameMode.Getter.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        @JvmStatic
        public final Name makeFullMethodName(MethodFullNameMode methodFullNameMode, List<Name> nameParts) {
            List listPlus;
            Intrinsics.checkNotNullParameter(methodFullNameMode, "methodFullNameMode");
            Intrinsics.checkNotNullParameter(nameParts, "nameParts");
            List<Name> list = nameParts;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(((Name) it.next()).asStringStripSpecialMarkers());
            }
            ArrayList arrayList2 = arrayList;
            int i = WhenMappings.$EnumSwitchMapping$0[methodFullNameMode.ordinal()];
            if (i == 1) {
                listPlus = CollectionsKt.plus((Collection) CollectionsKt.listOf(KotlinTypeMapper.UNBOX_JVM_METHOD_NAME), (Iterable) arrayList2);
            } else {
                if (i != 2) {
                    throw new NoWhenBranchMatchedException();
                }
                Object objFirst = CollectionsKt.first((List<? extends Object>) arrayList2);
                Intrinsics.checkNotNullExpressionValue(objFirst, "first(...)");
                listPlus = CollectionsKt.plus((Collection) CollectionsKt.listOf(JvmAbi.getterName((String) objFirst)), (Iterable) arrayList2.subList(1, nameParts.size()));
            }
            return Name.identifier(CollectionsKt.joinToString$default(listPlus, Parameters.DEFAULT_OPTION_PREFIXES, null, null, 0, null, null, 62, null));
        }

        @JvmStatic
        public final Name makeFullFieldName(List<Name> nameParts) {
            Name nameIdentifier;
            String str;
            Intrinsics.checkNotNullParameter(nameParts, "nameParts");
            if (nameParts.isEmpty()) {
                throw new IllegalArgumentException("Name must contain at least one part".toString());
            }
            List<Name> list = nameParts;
            boolean z = false;
            if (!(list instanceof Collection) || !list.isEmpty()) {
                Iterator<T> it = list.iterator();
                while (it.hasNext()) {
                    if (((Name) it.next()).isSpecial()) {
                        z = true;
                        break;
                    }
                }
            }
            String strJoinToString$default = CollectionsKt.joinToString$default(list, Parameters.DEFAULT_OPTION_PREFIXES, null, null, 0, null, new Function1<Name, CharSequence>() { // from class: org.jetbrains.kotlin.backend.jvm.NameableMfvcNodeImpl$Companion$makeFullFieldName$joined$1
                @Override // kotlin.jvm.functions.Function1
                public final CharSequence invoke(Name it2) {
                    Intrinsics.checkNotNullParameter(it2, "it");
                    String strAsStringStripSpecialMarkers = it2.asStringStripSpecialMarkers();
                    Intrinsics.checkNotNullExpressionValue(strAsStringStripSpecialMarkers, "asStringStripSpecialMarkers(...)");
                    return strAsStringStripSpecialMarkers;
                }
            }, 30, null);
            if (z) {
                nameIdentifier = Name.special("<" + strJoinToString$default + '>');
                str = "special(...)";
            } else {
                nameIdentifier = Name.identifier(strJoinToString$default);
                str = "identifier(...)";
            }
            Intrinsics.checkNotNullExpressionValue(nameIdentifier, str);
            return nameIdentifier;
        }
    }
}
