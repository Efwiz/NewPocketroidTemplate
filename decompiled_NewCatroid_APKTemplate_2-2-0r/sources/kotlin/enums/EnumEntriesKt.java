package kotlin.enums;

import androidx.exifinterface.media.ExifInterface;
import com.reandroid.arsc.chunk.TypeBlock;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/kotlin-stdlib-1.8.20.jar:kotlin/enums/EnumEntriesKt.class
  classes11.dex
 */
/* JADX INFO: compiled from: EnumEntries.kt */
/* JADX INFO: loaded from: classes11.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0010\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0002\b\u0003\u001a!\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0010\b\u0000\u0010\u0002\u0018\u0001*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u0087\b\u001a2\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0001\"\u000e\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u00032\u0012\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00040\u00070\u0006H\u0001\u001a1\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0001\"\u000e\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u00032\f\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\u00040\u0007H\u0001¢\u0006\u0002\u0010\t¨\u0006\n"}, d2 = {"enumEntries", "Lkotlin/enums/EnumEntries;", ExifInterface.GPS_DIRECTION_TRUE, "", ExifInterface.LONGITUDE_EAST, "entriesProvider", "Lkotlin/Function0;", "", TypeBlock.NAME_entries, "([Ljava/lang/Enum;)Lkotlin/enums/EnumEntries;", PathUtil.KOTLIN_JAVA_STDLIB_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class EnumEntriesKt {

    /* JADX INFO: Add missing generic type declarations: [E] */
    /* JADX INFO: renamed from: kotlin.enums.EnumEntriesKt$enumEntries$1, reason: invalid class name */
    /* JADX INFO: compiled from: EnumEntries.kt */
    /* JADX INFO: loaded from: NewCatroid_APKTemplate_2-2-0r.apk:assets/kotlin-stdlib-1.8.20.jar:kotlin/enums/EnumEntriesKt$enumEntries$1.class */
    @Metadata(mv = {1, 8, 0}, k = 3, xi = 48, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u0010\n\u0002\b\u0002\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\n¢\u0006\u0004\b\u0004\u0010\u0005"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", ExifInterface.LONGITUDE_EAST, "", "invoke", "()[Ljava/lang/Enum;"})
    static final class AnonymousClass1<E> extends Lambda implements Function0<E[]> {

        /* JADX INFO: Incorrect field signature: [TE; */
        final /* synthetic */ Enum[] $entries;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Incorrect types in method signature: ([TE;)V */
        AnonymousClass1(Enum[] $entries) {
            super(0);
            this.$entries = $entries;
        }

        /* JADX WARN: Incorrect return type in method signature: ()[TE; */
        @Override // kotlin.jvm.functions.Function0
        @NotNull
        public final Enum[] invoke() {
            return this.$entries;
        }
    }

    public static final /* synthetic */ <T extends Enum<T>> EnumEntries<T> enumEntries() {
        Intrinsics.reifiedOperationMarker(5, ExifInterface.GPS_DIRECTION_TRUE);
        return enumEntries(new Enum[0]);
    }

    public static final <E extends Enum<E>> EnumEntries<E> enumEntries(Function0<E[]> entriesProvider) {
        Intrinsics.checkNotNullParameter(entriesProvider, "entriesProvider");
        return new EnumEntriesList(entriesProvider.invoke());
    }

    public static final <E extends Enum<E>> EnumEntries<E> enumEntries(E[] entries) {
        Intrinsics.checkNotNullParameter(entries, "entries");
        return new EnumEntriesList(entries);
    }
}
