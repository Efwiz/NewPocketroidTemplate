package org.jetbrains.kotlin.backend.common.serialization.signature;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.android.gms.cast.MediaTrack;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.PropertySetterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.ir.util.IdSignature;
import org.jetbrains.kotlin.ir.util.KotlinMangler;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: IdSignatureBuilder.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0098\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000f\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u0002*\u000e\b\u0001\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00010\u00042\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0005J\u0015\u00106\u001a\u0002072\u0006\u00108\u001a\u00028\u0000H$¢\u0006\u0002\u00109J\b\u0010:\u001a\u00020\fH\u0004J\u0010\u0010;\u001a\u00020<2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0013\u0010=\u001a\u00020\f2\u0006\u0010>\u001a\u00028\u0000¢\u0006\u0002\u0010?J\b\u0010@\u001a\u000207H\u0004J\u0010\u0010A\u001a\u0002072\u0006\u0010B\u001a\u00020CH\u0014J\u0010\u0010D\u001a\u0002072\u0006\u0010B\u001a\u00020EH\u0014J\u0010\u0010F\u001a\u0002072\u0006\u0010B\u001a\u00020GH\u0014J\u0010\u0010H\u001a\u0002072\u0006\u0010B\u001a\u00020IH\u0014J\u0010\u0010J\u001a\u0002072\u0006\u0010B\u001a\u00020KH\u0014J\u0010\u0010L\u001a\u0002072\u0006\u0010B\u001a\u00020MH\u0014J\u0010\u0010N\u001a\u0002072\u0006\u0010B\u001a\u00020OH\u0014J\u0010\u0010P\u001a\u0002072\u0006\u0010B\u001a\u00020QH\u0014J\u0010\u0010R\u001a\u0002072\u0006\u0010B\u001a\u00020SH\u0014J\u0015\u0010T\u001a\u00020\b2\u0006\u0010>\u001a\u00028\u0000H$¢\u0006\u0002\u0010UJ\u0012\u0010V\u001a\u0002072\b\b\u0002\u0010W\u001a\u00020\u001fH\u0004J\u0015\u0010X\u001a\u0002072\u0006\u0010>\u001a\u00028\u0000H\u0004¢\u0006\u0002\u00109J\u0010\u0010Y\u001a\u0002072\u0006\u0010Z\u001a\u00020\u001fH\u0004J \u0010[\u001a\u0002072\u0006\u0010\\\u001a\u00020\u001b2\u0006\u0010\u0015\u001a\u00020\b2\u0006\u0010]\u001a\u00020\u001fH\u0004J\u001d\u0010^\u001a\u0002072\u0006\u0010>\u001a\u00028\u00002\u0006\u0010]\u001a\u00020\u001fH\u0004¢\u0006\u0002\u0010_J\u0010\u0010`\u001a\u0002072\u0006\u0010Z\u001a\u00020\u001fH\u0004J\u0010\u0010a\u001a\u0002072\u0006\u0010Z\u001a\u00020\u001fH\u0004R\u001a\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001c\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0011\u001a\u0004\u0018\u00010\u0012X¤\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u001c\u0010\u0015\u001a\u0004\u0018\u00010\bX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u0012\u0010\u001a\u001a\u0004\u0018\u00010\u001bX\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u001cR\u0012\u0010\u001d\u001a\u0004\u0018\u00010\u001bX\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u001cR\u001a\u0010\u001e\u001a\u00020\u001fX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010 \"\u0004\b!\u0010\"R\u0012\u0010#\u001a\u00028\u0001X¤\u0004¢\u0006\u0006\u001a\u0004\b$\u0010%R\u001a\u0010&\u001a\u00020\u001bX\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010(\"\u0004\b)\u0010*R\"\u0010+\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010,X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b-\u0010\n\"\u0004\b.\u0010/R\u001a\u00100\u001a\u000201X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b2\u00103\"\u0004\b4\u00105¨\u0006b"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/signature/IdSignatureBuilder;", "Declaration", "", "Mangler", "Lorg/jetbrains/kotlin/ir/util/KotlinMangler;", "()V", "classFqnSegments", "", "", "getClassFqnSegments", "()Ljava/util/List;", "container", "Lorg/jetbrains/kotlin/ir/util/IdSignature;", "getContainer", "()Lorg/jetbrains/kotlin/ir/util/IdSignature;", "setContainer", "(Lorg/jetbrains/kotlin/ir/util/IdSignature;)V", "currentFileSignature", "Lorg/jetbrains/kotlin/ir/util/IdSignature$FileSignature;", "getCurrentFileSignature", "()Lorg/jetbrains/kotlin/ir/util/IdSignature$FileSignature;", MediaTrack.ROLE_DESCRIPTION, "getDescription", "()Ljava/lang/String;", "setDescription", "(Ljava/lang/String;)V", "hashId", "", "Ljava/lang/Long;", "hashIdAcc", "isTopLevelPrivate", "", "()Z", "setTopLevelPrivate", "(Z)V", "mangler", "getMangler", "()Lorg/jetbrains/kotlin/ir/util/KotlinMangler;", "mask", "getMask", "()J", "setMask", "(J)V", "overridden", "", "getOverridden", "setOverridden", "(Ljava/util/List;)V", "packageFqn", "Lorg/jetbrains/kotlin/name/FqName;", "getPackageFqn", "()Lorg/jetbrains/kotlin/name/FqName;", "setPackageFqn", "(Lorg/jetbrains/kotlin/name/FqName;)V", "accept", "", "d", ReifiedTypeInliner.pluginIntrinsicsMarkerSignature, JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "buildContainerSignature", "Lorg/jetbrains/kotlin/ir/util/IdSignature$CompositeSignature;", "buildSignature", "declaration", "(Ljava/lang/Object;)Lorg/jetbrains/kotlin/ir/util/IdSignature;", "createContainer", "platformSpecificAlias", "descriptor", "Lorg/jetbrains/kotlin/descriptors/TypeAliasDescriptor;", "platformSpecificClass", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "platformSpecificConstructor", "Lorg/jetbrains/kotlin/descriptors/ConstructorDescriptor;", "platformSpecificFunction", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "platformSpecificGetter", "Lorg/jetbrains/kotlin/descriptors/PropertyGetterDescriptor;", "platformSpecificModule", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "platformSpecificPackage", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "platformSpecificProperty", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "platformSpecificSetter", "Lorg/jetbrains/kotlin/descriptors/PropertySetterDescriptor;", "renderDeclarationForDescription", "(Ljava/lang/Object;)Ljava/lang/String;", "reset", "resetContainer", "setDescriptionIfLocalDeclaration", "setExpected", "f", "setHashIdAndDescription", "id", "isPropertyAccessor", "setHashIdAndDescriptionFor", "(Ljava/lang/Object;Z)V", "setSpecialJavaProperty", "setSyntheticJavaProperty", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IdSignatureBuilder<Declaration, Mangler extends KotlinMangler<Declaration>> {
    private final List<String> classFqnSegments;
    private IdSignature container;
    private String description;
    private Long hashId;
    private Long hashIdAcc;
    private boolean isTopLevelPrivate;
    private long mask;
    private List<? extends Declaration> overridden;
    private FqName packageFqn;

    protected abstract void accept(Declaration d);

    protected abstract IdSignature.FileSignature getCurrentFileSignature();

    protected abstract Mangler getMangler();

    protected void platformSpecificAlias(TypeAliasDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
    }

    protected void platformSpecificClass(ClassDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
    }

    protected void platformSpecificConstructor(ConstructorDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
    }

    protected void platformSpecificFunction(FunctionDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
    }

    protected void platformSpecificGetter(PropertyGetterDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
    }

    protected void platformSpecificPackage(PackageFragmentDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
    }

    protected void platformSpecificProperty(PropertyDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
    }

    protected void platformSpecificSetter(PropertySetterDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
    }

    protected abstract String renderDeclarationForDescription(Declaration declaration);

    public IdSignatureBuilder() {
        FqName ROOT = FqName.ROOT;
        Intrinsics.checkNotNullExpressionValue(ROOT, "ROOT");
        this.packageFqn = ROOT;
        this.classFqnSegments = new ArrayList();
    }

    protected final FqName getPackageFqn() {
        return this.packageFqn;
    }

    protected final void setPackageFqn(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "<set-?>");
        this.packageFqn = fqName;
    }

    protected final List<String> getClassFqnSegments() {
        return this.classFqnSegments;
    }

    protected final List<Declaration> getOverridden() {
        return this.overridden;
    }

    protected final void setOverridden(List<? extends Declaration> list) {
        this.overridden = list;
    }

    protected final long getMask() {
        return this.mask;
    }

    protected final void setMask(long j) {
        this.mask = j;
    }

    protected final IdSignature getContainer() {
        return this.container;
    }

    protected final void setContainer(IdSignature idSignature) {
        this.container = idSignature;
    }

    protected final void createContainer() {
        IdSignature.CompositeSignature compositeSignatureBuild;
        IdSignature.CompositeSignature compositeSignatureBuildContainerSignature;
        IdSignature idSignature = this.container;
        if (idSignature != null && (compositeSignatureBuildContainerSignature = buildContainerSignature(idSignature)) != null) {
            compositeSignatureBuild = compositeSignatureBuildContainerSignature;
        } else {
            compositeSignatureBuild = build();
        }
        this.container = compositeSignatureBuild;
        reset(false);
    }

    protected final String getDescription() {
        return this.description;
    }

    protected final void setDescription(String str) {
        this.description = str;
    }

    protected final void setDescriptionIfLocalDeclaration(Declaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (this.container != null) {
            this.description = renderDeclarationForDescription(declaration);
        }
    }

    /* JADX INFO: renamed from: isTopLevelPrivate, reason: from getter */
    protected final boolean getIsTopLevelPrivate() {
        return this.isTopLevelPrivate;
    }

    protected final void setTopLevelPrivate(boolean z) {
        this.isTopLevelPrivate = z;
    }

    protected final void setHashIdAndDescriptionFor(Declaration declaration, boolean isPropertyAccessor) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        KotlinMangler mangler = getMangler();
        String strSignatureString = mangler.signatureString(declaration, false);
        setHashIdAndDescription(mangler.getHashMangle(strSignatureString), strSignatureString, isPropertyAccessor);
    }

    protected final void setHashIdAndDescription(long id2, String description, boolean isPropertyAccessor) {
        Intrinsics.checkNotNullParameter(description, "description");
        if (isPropertyAccessor) {
            this.hashIdAcc = Long.valueOf(id2);
        } else {
            this.hashId = Long.valueOf(id2);
        }
        this.description = description;
    }

    public static /* synthetic */ void reset$default(IdSignatureBuilder idSignatureBuilder, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: reset");
        }
        if ((i & 1) != 0) {
            z = true;
        }
        idSignatureBuilder.reset(z);
    }

    protected final void reset(boolean resetContainer) {
        FqName ROOT = FqName.ROOT;
        Intrinsics.checkNotNullExpressionValue(ROOT, "ROOT");
        this.packageFqn = ROOT;
        this.classFqnSegments.clear();
        this.hashId = null;
        this.hashIdAcc = null;
        this.mask = 0L;
        this.overridden = null;
        this.description = null;
        this.isTopLevelPrivate = false;
        if (resetContainer) {
            this.container = null;
        }
    }

    private final IdSignature.CompositeSignature buildContainerSignature(IdSignature container) {
        return new IdSignature.CompositeSignature(container, new IdSignature.LocalSignature(CollectionsKt.joinToString$default(this.classFqnSegments, ".", null, null, 0, null, null, 62, null), this.hashId, this.description));
    }

    /* JADX WARN: Multi-variable type inference failed */
    protected final IdSignature build() {
        String strAsString = this.packageFqn.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        String strJoinToString$default = CollectionsKt.joinToString$default(this.classFqnSegments, ".", null, null, 0, null, null, 62, null);
        List<? extends Declaration> list = this.overridden;
        if (list != null) {
            Intrinsics.checkNotNull(list);
            this.overridden = null;
            IdSignature idSignatureBuild = build();
            List<? extends Declaration> list2 = list;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
            Iterator<T> it = list2.iterator();
            while (it.hasNext()) {
                arrayList.add(buildSignature(it.next()));
            }
            return new IdSignature.SpecialFakeOverrideSignature(idSignatureBuild, arrayList);
        }
        if (this.isTopLevelPrivate) {
            IdSignature.FileSignature currentFileSignature = getCurrentFileSignature();
            if (currentFileSignature == null) {
                throw new IllegalStateException(("File expected to be not null (" + strAsString + ", " + strJoinToString$default + ')').toString());
            }
            this.isTopLevelPrivate = false;
            return new IdSignature.CompositeSignature(currentFileSignature, build());
        }
        IdSignature idSignature = this.container;
        if (idSignature != null) {
            Intrinsics.checkNotNull(idSignature);
            this.container = null;
            return buildContainerSignature(idSignature);
        }
        if (this.hashIdAcc == null) {
            return new IdSignature.CommonSignature(strAsString, strJoinToString$default, this.hashId, this.mask, this.description);
        }
        IdSignature.CommonSignature commonSignature = new IdSignature.CommonSignature(strAsString, strJoinToString$default, this.hashIdAcc, this.mask, this.description);
        this.hashIdAcc = null;
        List<String> list3 = this.classFqnSegments;
        list3.remove(CollectionsKt.getLastIndex(list3));
        return new IdSignature.AccessorSignature(build(), commonSignature);
    }

    protected final void setExpected(boolean f) {
        this.mask |= IdSignature.Flags.IS_EXPECT.encode(f);
    }

    protected final void setSpecialJavaProperty(boolean f) {
        this.mask |= IdSignature.Flags.IS_JAVA_FOR_KOTLIN_OVERRIDE_PROPERTY.encode(f);
    }

    protected final void setSyntheticJavaProperty(boolean f) {
        this.mask |= IdSignature.Flags.IS_SYNTHETIC_JAVA_PROPERTY.encode(f);
    }

    protected void platformSpecificModule(ModuleDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        throw new IllegalStateException(("Should not reach here with " + descriptor).toString());
    }

    public final IdSignature buildSignature(Declaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        reset$default(this, false, 1, null);
        accept(declaration);
        return build();
    }
}
