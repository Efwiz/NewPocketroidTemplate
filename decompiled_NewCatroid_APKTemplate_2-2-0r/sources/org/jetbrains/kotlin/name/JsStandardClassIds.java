package org.jetbrains.kotlin.name;

import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: JsStandardClassIds.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001:\u0002\u0007\bB\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/name/JsStandardClassIds;", "", "()V", "BASE_JS_PACKAGE", "Lorg/jetbrains/kotlin/name/FqName;", "getBASE_JS_PACKAGE", "()Lorg/jetbrains/kotlin/name/FqName;", "Annotations", "Callables", "compiler.common.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JsStandardClassIds {
    private static final FqName BASE_JS_PACKAGE;
    public static final JsStandardClassIds INSTANCE = new JsStandardClassIds();

    private JsStandardClassIds() {
    }

    static {
        FqName fqNameChild = StandardClassIds.INSTANCE.getBASE_KOTLIN_PACKAGE().child(Name.identifier("js"));
        Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
        BASE_JS_PACKAGE = fqNameChild;
    }

    public final FqName getBASE_JS_PACKAGE() {
        return BASE_JS_PACKAGE;
    }

    /* JADX INFO: compiled from: JsStandardClassIds.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\"\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00040\u00128\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00040\u00128\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/name/JsStandardClassIds$Annotations;", "", "()V", "JsExport", "Lorg/jetbrains/kotlin/name/ClassId;", "JsExportIgnore", "JsExternalArgument", "JsExternalInheritorsOnly", "JsLibrary", "JsModule", "JsName", "JsNative", "JsNativeGetter", "JsNativeInvoke", "JsNativeSetter", "JsNonModule", "JsQualifier", "annotationsRequiringExternal", "", "nativeAnnotations", "compiler.common.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Annotations {
        public static final Annotations INSTANCE = new Annotations();
        public static final ClassId JsExport;
        public static final ClassId JsExportIgnore;
        public static final ClassId JsExternalArgument;
        public static final ClassId JsExternalInheritorsOnly;
        public static final ClassId JsLibrary;
        public static final ClassId JsModule;
        public static final ClassId JsName;
        public static final ClassId JsNative;
        public static final ClassId JsNativeGetter;
        public static final ClassId JsNativeInvoke;
        public static final ClassId JsNativeSetter;
        public static final ClassId JsNonModule;
        public static final ClassId JsQualifier;
        public static final Set<ClassId> annotationsRequiringExternal;
        public static final Set<ClassId> nativeAnnotations;

        private Annotations() {
        }

        static {
            ClassId classIdJsId = JsStandardClassIdsKt.jsId("JsQualifier");
            JsQualifier = classIdJsId;
            ClassId classIdJsId2 = JsStandardClassIdsKt.jsId("JsModule");
            JsModule = classIdJsId2;
            JsNonModule = JsStandardClassIdsKt.jsId("JsNonModule");
            ClassId classIdJsId3 = JsStandardClassIdsKt.jsId("native");
            JsNative = classIdJsId3;
            JsLibrary = JsStandardClassIdsKt.jsId("library");
            ClassId classIdJsId4 = JsStandardClassIdsKt.jsId("nativeInvoke");
            JsNativeInvoke = classIdJsId4;
            ClassId classIdJsId5 = JsStandardClassIdsKt.jsId("nativeGetter");
            JsNativeGetter = classIdJsId5;
            ClassId classIdJsId6 = JsStandardClassIdsKt.jsId("nativeSetter");
            JsNativeSetter = classIdJsId6;
            JsName = JsStandardClassIdsKt.jsId("JsName");
            ClassId classIdJsId7 = JsStandardClassIdsKt.jsId("JsExport");
            JsExport = classIdJsId7;
            JsExternalInheritorsOnly = JsStandardClassIdsKt.jsId("JsExternalInheritorsOnly");
            JsExternalArgument = JsStandardClassIdsKt.jsId("JsExternalArgument");
            ClassId classIdCreateNestedClassId = classIdJsId7.createNestedClassId(Name.identifier("Ignore"));
            Intrinsics.checkNotNullExpressionValue(classIdCreateNestedClassId, "createNestedClassId(...)");
            JsExportIgnore = classIdCreateNestedClassId;
            annotationsRequiringExternal = SetsKt.setOf((Object[]) new ClassId[]{classIdJsId2, classIdJsId});
            nativeAnnotations = SetsKt.setOf((Object[]) new ClassId[]{classIdJsId3, classIdJsId4, classIdJsId5, classIdJsId6});
        }
    }

    /* JADX INFO: compiled from: JsStandardClassIds.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00040\u00078\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/name/JsStandardClassIds$Callables;", "", "()V", "JsDefinedExternally", "Lorg/jetbrains/kotlin/name/CallableId;", "JsNoImpl", "definedExternallyPropertyNames", "", "compiler.common.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Callables {
        public static final Callables INSTANCE = new Callables();
        public static final CallableId JsDefinedExternally;
        public static final CallableId JsNoImpl;
        public static final Set<CallableId> definedExternallyPropertyNames;

        private Callables() {
        }

        static {
            CallableId callableId = JsStandardClassIdsKt.callableId("definedExternally", JsStandardClassIds.INSTANCE.getBASE_JS_PACKAGE());
            JsDefinedExternally = callableId;
            CallableId callableId2 = JsStandardClassIdsKt.callableId("noImpl", JsStandardClassIds.INSTANCE.getBASE_JS_PACKAGE());
            JsNoImpl = callableId2;
            definedExternallyPropertyNames = SetsKt.setOf((Object[]) new CallableId[]{callableId2, callableId});
        }
    }
}
