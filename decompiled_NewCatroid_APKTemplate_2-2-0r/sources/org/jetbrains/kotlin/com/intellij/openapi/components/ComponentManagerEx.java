package org.jetbrains.kotlin.com.intellij.openapi.components;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;

/* JADX INFO: compiled from: ComponentManagerEx.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bg\u0018\u00002\u00020\u0001J!\u0010\u0002\u001a\u0004\u0018\u0001H\u0003\"\b\b\u0000\u0010\u0003*\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u0005H&¢\u0006\u0002\u0010\u0006J:\u0010\u0007\u001a\u00020\b\"\b\b\u0000\u0010\u0003*\u00020\u00012\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00030\n2\u0018\u0010\u000b\u001a\u0014\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\b0\fH&¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/com/intellij/openapi/components/ComponentManagerEx;", "", "getServiceByClassName", ExifInterface.GPS_DIRECTION_TRUE, "serviceClassName", "", "(Ljava/lang/String;)Ljava/lang/Object;", "processInitializedComponents", "", "aClass", "Ljava/lang/Class;", "processor", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/com/intellij/openapi/extensions/PluginDescriptor;", "intellij.platform.core"}, k = 1, mv = {1, 5, 1})
public interface ComponentManagerEx {
    <T> T getServiceByClassName(String serviceClassName);
}
