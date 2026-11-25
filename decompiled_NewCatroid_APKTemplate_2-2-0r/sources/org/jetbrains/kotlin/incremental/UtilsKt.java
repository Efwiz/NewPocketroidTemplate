package org.jetbrains.kotlin.incremental;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.incremental.components.LocationInfo;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.incremental.components.Position;
import org.jetbrains.kotlin.incremental.components.ScopeKind;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.DescriptorUtils;

/* JADX INFO: compiled from: utils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000*\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\"\u0010\u0002\u001a\u00020\u0003*\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n\u001a\"\u0010\u0002\u001a\u00020\u0003*\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u000b2\u0006\u0010\t\u001a\u00020\n\u001a\"\u0010\f\u001a\u00020\u0003*\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\u0001\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"ANDROID_LAYOUT_CONTENT_LOOKUP_NAME", "", PsiKeyword.RECORD, "", "Lorg/jetbrains/kotlin/incremental/components/LookupTracker;", TypedValues.TransitionType.S_FROM, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "scopeOwner", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "name", "Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "recordPackageLookup", "packageFqName", "descriptors"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class UtilsKt {
    public static final String ANDROID_LAYOUT_CONTENT_LOOKUP_NAME = "<LAYOUT-CONTENT>";

    public static final void record(LookupTracker lookupTracker, LookupLocation from, ClassDescriptor scopeOwner, Name name2) {
        LocationInfo location;
        Intrinsics.checkNotNullParameter(lookupTracker, "<this>");
        Intrinsics.checkNotNullParameter(from, "from");
        Intrinsics.checkNotNullParameter(scopeOwner, "scopeOwner");
        Intrinsics.checkNotNullParameter(name2, "name");
        if (lookupTracker == LookupTracker.DO_NOTHING.INSTANCE || (location = from.getLocation()) == null) {
            return;
        }
        Position position = lookupTracker.getRequiresPosition() ? location.getPosition() : Position.INSTANCE.getNO_POSITION();
        String filePath = location.getFilePath();
        String strAsString = DescriptorUtils.getFqName(scopeOwner).asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        ScopeKind scopeKind = ScopeKind.CLASSIFIER;
        String strAsString2 = name2.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
        lookupTracker.record(filePath, position, strAsString, scopeKind, strAsString2);
    }

    public static final void record(LookupTracker lookupTracker, LookupLocation from, PackageFragmentDescriptor scopeOwner, Name name2) {
        Intrinsics.checkNotNullParameter(lookupTracker, "<this>");
        Intrinsics.checkNotNullParameter(from, "from");
        Intrinsics.checkNotNullParameter(scopeOwner, "scopeOwner");
        Intrinsics.checkNotNullParameter(name2, "name");
        String strAsString = scopeOwner.getFqName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        String strAsString2 = name2.asString();
        Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
        recordPackageLookup(lookupTracker, from, strAsString, strAsString2);
    }

    public static final void recordPackageLookup(LookupTracker lookupTracker, LookupLocation from, String packageFqName, String name2) {
        LocationInfo location;
        Intrinsics.checkNotNullParameter(lookupTracker, "<this>");
        Intrinsics.checkNotNullParameter(from, "from");
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        Intrinsics.checkNotNullParameter(name2, "name");
        if (lookupTracker == LookupTracker.DO_NOTHING.INSTANCE || (location = from.getLocation()) == null) {
            return;
        }
        lookupTracker.record(location.getFilePath(), lookupTracker.getRequiresPosition() ? location.getPosition() : Position.INSTANCE.getNO_POSITION(), packageFqName, ScopeKind.PACKAGE, name2);
    }
}
