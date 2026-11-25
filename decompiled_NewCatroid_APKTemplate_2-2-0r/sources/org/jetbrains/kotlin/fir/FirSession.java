package org.jetbrains.kotlin.fir;

import androidx.exifinterface.media.ExifInterface;
import ar.com.hjg.pngj.chunks.PngChunkTextVar;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.util.ConeTypeRegistry;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.util.ArrayMapAccessor;
import org.jetbrains.kotlin.util.ComponentArrayOwner;
import org.jetbrains.kotlin.util.NullableArrayMapAccessor;
import org.jetbrains.kotlin.util.TypeRegistry;

/* JADX INFO: compiled from: FirSession.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u0000 \u001c2\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0001:\u0002\u001c\u001dB\u0019\b\u0007\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0018\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0002H\u0007J \u0010\u0014\u001a\u00020\u00152\u000e\u0010\u0019\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00020\u001a2\u0006\u0010\u0018\u001a\u00020\u0002H\u0007J\b\u0010\u001b\u001a\u00020\u0017H\u0016R\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0013\u0010\u0003\u001a\u0004\u0018\u00010\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR \u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0011X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/fir/FirSession;", "Lorg/jetbrains/kotlin/util/ComponentArrayOwner;", "Lorg/jetbrains/kotlin/fir/FirSessionComponent;", "sessionProvider", "Lorg/jetbrains/kotlin/fir/FirSessionProvider;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/fir/FirSession$Kind;", "(Lorg/jetbrains/kotlin/fir/FirSessionProvider;Lorg/jetbrains/kotlin/fir/FirSession$Kind;)V", "builtinTypes", "Lorg/jetbrains/kotlin/fir/BuiltinTypes;", "getBuiltinTypes", "()Lorg/jetbrains/kotlin/fir/BuiltinTypes;", "getKind", "()Lorg/jetbrains/kotlin/fir/FirSession$Kind;", "getSessionProvider", "()Lorg/jetbrains/kotlin/fir/FirSessionProvider;", "typeRegistry", "Lorg/jetbrains/kotlin/util/TypeRegistry;", "getTypeRegistry", "()Lorg/jetbrains/kotlin/util/TypeRegistry;", "register", "", "keyQualifiedName", "", "value", "tClass", "Lkotlin/reflect/KClass;", "toString", "Companion", Namer.CLASS_KIND_ENUM, Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirSession extends ComponentArrayOwner<FirSessionComponent, FirSessionComponent> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final BuiltinTypes builtinTypes;
    private final Kind kind;
    private final FirSessionProvider sessionProvider;
    private final TypeRegistry<FirSessionComponent, FirSessionComponent> typeRegistry;

    /* JADX INFO: compiled from: FirSession.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/fir/FirSession$Kind;", "", "(Ljava/lang/String;I)V", PngChunkTextVar.KEY_Source, "Library", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum Kind {
        Source,
        Library;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<Kind> getEntries() {
            return $ENTRIES;
        }
    }

    public final FirSessionProvider getSessionProvider() {
        return this.sessionProvider;
    }

    public final Kind getKind() {
        return this.kind;
    }

    /* JADX INFO: compiled from: FirSession.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J'\u0010\u0004\u001a\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H\u00060\u0005\"\n\b\u0000\u0010\u0006\u0018\u0001*\u00020\u0002H\u0086\bJ'\u0010\u0007\u001a\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H\u00060\b\"\n\b\u0000\u0010\u0006\u0018\u0001*\u00020\u0002H\u0086\bJ/\u0010\u0007\u001a\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u0002H\u00060\b\"\n\b\u0000\u0010\u0006\u0018\u0001*\u00020\u00022\u0006\u0010\t\u001a\u00020\nH\u0086\b¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/FirSession$Companion;", "Lorg/jetbrains/kotlin/fir/util/ConeTypeRegistry;", "Lorg/jetbrains/kotlin/fir/FirSessionComponent;", "()V", "nullableSessionComponentAccessor", "Lorg/jetbrains/kotlin/util/NullableArrayMapAccessor;", ExifInterface.GPS_DIRECTION_TRUE, "sessionComponentAccessor", "Lorg/jetbrains/kotlin/util/ArrayMapAccessor;", "id", "", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion extends ConeTypeRegistry<FirSessionComponent, FirSessionComponent> {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final /* synthetic */ <T extends FirSessionComponent> ArrayMapAccessor<FirSessionComponent, FirSessionComponent, T> sessionComponentAccessor() {
            Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
            return TypeRegistry.generateAccessor$default(this, Reflection.getOrCreateKotlinClass(FirSessionComponent.class), (Object) null, 2, (Object) null);
        }

        public final /* synthetic */ <T extends FirSessionComponent> ArrayMapAccessor<FirSessionComponent, FirSessionComponent, T> sessionComponentAccessor(String id2) {
            Intrinsics.checkNotNullParameter(id2, "id");
            return TypeRegistry.generateAccessor$default(this, id2, (Object) null, 2, (Object) null);
        }

        public final /* synthetic */ <T extends FirSessionComponent> NullableArrayMapAccessor<FirSessionComponent, FirSessionComponent, T> nullableSessionComponentAccessor() {
            Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
            return (NullableArrayMapAccessor<FirSessionComponent, FirSessionComponent, T>) generateNullableAccessor(Reflection.getOrCreateKotlinClass(FirSessionComponent.class));
        }
    }

    @PrivateSessionConstructor
    public FirSession(FirSessionProvider firSessionProvider, Kind kind) {
        Intrinsics.checkNotNullParameter(kind, "kind");
        this.sessionProvider = firSessionProvider;
        this.kind = kind;
        this.builtinTypes = new BuiltinTypes();
        this.typeRegistry = INSTANCE;
    }

    public BuiltinTypes getBuiltinTypes() {
        return this.builtinTypes;
    }

    @Override // org.jetbrains.kotlin.util.AbstractArrayMapOwner
    protected final TypeRegistry<FirSessionComponent, FirSessionComponent> getTypeRegistry() {
        return this.typeRegistry;
    }

    @SessionConfiguration
    public final void register(KClass<? extends FirSessionComponent> tClass, FirSessionComponent value) {
        Intrinsics.checkNotNullParameter(tClass, "tClass");
        Intrinsics.checkNotNullParameter(value, "value");
        registerComponent(tClass, value);
    }

    @SessionConfiguration
    public final void register(String keyQualifiedName, FirSessionComponent value) {
        Intrinsics.checkNotNullParameter(keyQualifiedName, "keyQualifiedName");
        Intrinsics.checkNotNullParameter(value, "value");
        registerComponent(keyQualifiedName, value);
    }

    public String toString() {
        FirModuleData nullableModuleData = FirModuleDataKt.getNullableModuleData(this);
        return nullableModuleData == null ? "Libraries session" : "Source session for module " + nullableModuleData.getName();
    }
}
