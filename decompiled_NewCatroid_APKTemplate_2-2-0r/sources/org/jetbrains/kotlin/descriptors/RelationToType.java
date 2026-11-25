package org.jetbrains.kotlin.descriptors;

import com.google.android.gms.cast.MediaTrack;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX INFO: compiled from: EffectiveVisibility.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\n\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u0007\u001a\u00020\u0000J\b\u0010\b\u001a\u00020\u0003H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/descriptors/RelationToType;", "", MediaTrack.ROLE_DESCRIPTION, "", "(Ljava/lang/String;ILjava/lang/String;)V", "getDescription", "()Ljava/lang/String;", "containerRelation", "toString", "CONSTRUCTOR", "CONTAINER", "ARGUMENT", "ARGUMENT_CONTAINER", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public enum RelationToType {
    CONSTRUCTOR(""),
    CONTAINER(" containing declaration"),
    ARGUMENT(" argument"),
    ARGUMENT_CONTAINER(" argument containing declaration");

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
    private final String description;

    /* JADX INFO: compiled from: EffectiveVisibility.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[RelationToType.values().length];
            try {
                iArr[RelationToType.CONSTRUCTOR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[RelationToType.CONTAINER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[RelationToType.ARGUMENT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[RelationToType.ARGUMENT_CONTAINER.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static EnumEntries<RelationToType> getEntries() {
        return $ENTRIES;
    }

    RelationToType(String str) {
        this.description = str;
    }

    public final String getDescription() {
        return this.description;
    }

    public final RelationToType containerRelation() {
        int i = WhenMappings.$EnumSwitchMapping$0[ordinal()];
        if (i == 1 || i == 2) {
            return CONTAINER;
        }
        if (i == 3 || i == 4) {
            return ARGUMENT_CONTAINER;
        }
        throw new NoWhenBranchMatchedException();
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.description;
    }
}
