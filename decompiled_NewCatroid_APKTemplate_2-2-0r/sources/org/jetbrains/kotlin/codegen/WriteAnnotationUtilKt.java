package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.state.GenerationState;
import org.jetbrains.kotlin.config.JvmAnalysisFlags;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.load.kotlin.JvmBytecodeBinaryVersion;
import org.jetbrains.kotlin.load.kotlin.header.KotlinClassHeader;
import org.jetbrains.org.objectweb.asm.AnnotationVisitor;

/* JADX INFO: compiled from: writeAnnotationUtil.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000:\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001aB\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00012\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00050\u0010\u001a\u001e\u0010\u0012\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\r¨\u0006\u0013"}, d2 = {"generateLanguageVersionSettingsBasedMetadataFlags", "", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "writeKotlinMetadata", "", "cb", "Lorg/jetbrains/kotlin/codegen/ClassBuilder;", "state", "Lorg/jetbrains/kotlin/codegen/state/GenerationState;", Namer.METADATA_CLASS_KIND, "Lorg/jetbrains/kotlin/load/kotlin/header/KotlinClassHeader$Kind;", "publicAbi", "", "extraFlags", "action", "Lkotlin/Function1;", "Lorg/jetbrains/org/objectweb/asm/AnnotationVisitor;", "writeSyntheticClassMetadata", Marker.BACKEND}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class WriteAnnotationUtilKt {
    public static final void writeKotlinMetadata(ClassBuilder cb, GenerationState state, KotlinClassHeader.Kind kind, boolean z, int i, Function1<? super AnnotationVisitor, Unit> action) {
        Intrinsics.checkNotNullParameter(cb, "cb");
        Intrinsics.checkNotNullParameter(state, "state");
        Intrinsics.checkNotNullParameter(kind, "kind");
        Intrinsics.checkNotNullParameter(action, "action");
        AnnotationVisitor annotationVisitorNewAnnotation = cb.newAnnotation(JvmAnnotationNames.METADATA_DESC, true);
        Intrinsics.checkNotNullExpressionValue(annotationVisitorNewAnnotation, "newAnnotation(...)");
        annotationVisitorNewAnnotation.visit(JvmAnnotationNames.METADATA_VERSION_FIELD_NAME, state.getConfig().getMetadataVersion().getNumbers());
        if (!state.getConfig().getMetadataVersion().isAtLeast(1, 5, 0)) {
            annotationVisitorNewAnnotation.visit("bv", JvmBytecodeBinaryVersion.INSTANCE.toArray());
        }
        annotationVisitorNewAnnotation.visit(JvmAnnotationNames.KIND_FIELD_NAME, Integer.valueOf(kind.getId()));
        int iGenerateLanguageVersionSettingsBasedMetadataFlags = generateLanguageVersionSettingsBasedMetadataFlags(state.getLanguageVersionSettings()) | i;
        if (z) {
            iGenerateLanguageVersionSettingsBasedMetadataFlags |= 128;
        }
        if (iGenerateLanguageVersionSettingsBasedMetadataFlags != 0) {
            annotationVisitorNewAnnotation.visit(JvmAnnotationNames.METADATA_EXTRA_INT_FIELD_NAME, Integer.valueOf(iGenerateLanguageVersionSettingsBasedMetadataFlags));
        }
        action.invoke(annotationVisitorNewAnnotation);
        annotationVisitorNewAnnotation.visitEnd();
    }

    public static final void writeSyntheticClassMetadata(ClassBuilder cb, GenerationState state, boolean z) {
        Intrinsics.checkNotNullParameter(cb, "cb");
        Intrinsics.checkNotNullParameter(state, "state");
        writeKotlinMetadata(cb, state, KotlinClassHeader.Kind.SYNTHETIC_CLASS, z, 0, new Function1<AnnotationVisitor, Unit>() { // from class: org.jetbrains.kotlin.codegen.WriteAnnotationUtilKt.writeSyntheticClassMetadata.1
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(AnnotationVisitor annotationVisitor) {
                Intrinsics.checkNotNullParameter(annotationVisitor, "<anonymous parameter 0>");
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(AnnotationVisitor annotationVisitor) {
                invoke2(annotationVisitor);
                return Unit.INSTANCE;
            }
        });
    }

    public static final int generateLanguageVersionSettingsBasedMetadataFlags(LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        int i = languageVersionSettings.isPreRelease() ? 2 : 0;
        return ((Boolean) languageVersionSettings.getFlag(JvmAnalysisFlags.getStrictMetadataVersionSemantics())).booleanValue() ? i | 8 : i;
    }
}
