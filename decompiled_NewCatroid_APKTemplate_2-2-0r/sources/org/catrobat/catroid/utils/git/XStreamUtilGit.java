package org.catrobat.catroid.utils.git;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.io.BackwardCompatibleCatrobatLanguageXStream;
import org.catrobat.catroid.io.XstreamSerializer;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/git/XStreamUtilGit.class
  classes14.dex
 */
/* JADX INFO: compiled from: XStreamUtilGit.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0019\u0010\b\u001a\u0002H\t\"\u0004\b\u0000\u0010\t2\u0006\u0010\n\u001a\u0002H\t¢\u0006\u0002\u0010\u000bJ\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0004J\u000e\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\rJ\u0012\u0010\u0011\u001a\u0004\u0018\u00010\u00042\b\u0010\n\u001a\u0004\u0018\u00010\u0001R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\n \u0007*\u0004\u0018\u00010\u00060\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/catrobat/catroid/utils/git/XStreamUtilGit;", "", "()V", "XML_HEADER", "", "xstream", "Lorg/catrobat/catroid/io/BackwardCompatibleCatrobatLanguageXStream;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "deepCopy", ExifInterface.GPS_DIRECTION_TRUE, "obj", "(Ljava/lang/Object;)Ljava/lang/Object;", "fromXML", "Lorg/catrobat/catroid/content/Project;", "xml", "toXML", "project", "toXMLAny", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class XStreamUtilGit {
    private static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?>\n";
    public static final XStreamUtilGit INSTANCE = new XStreamUtilGit();
    private static final BackwardCompatibleCatrobatLanguageXStream xstream = XstreamSerializer.getInstance().getXstream();

    private XStreamUtilGit() {
    }

    public final Project fromXML(String xml) {
        Intrinsics.checkNotNullParameter(xml, "xml");
        Object objFromXML = xstream.fromXML(xml);
        Intrinsics.checkNotNull(objFromXML, "null cannot be cast to non-null type org.catrobat.catroid.content.Project");
        return (Project) objFromXML;
    }

    public final String toXML(Project project) {
        Intrinsics.checkNotNullParameter(project, "project");
        return XML_HEADER + xstream.toXML(project);
    }

    public final String toXMLAny(Object obj) {
        if (obj == null) {
            return null;
        }
        return xstream.toXML(obj);
    }

    public final <T> T deepCopy(T obj) {
        BackwardCompatibleCatrobatLanguageXStream backwardCompatibleCatrobatLanguageXStream = xstream;
        return (T) backwardCompatibleCatrobatLanguageXStream.fromXML(backwardCompatibleCatrobatLanguageXStream.toXML(obj));
    }
}
