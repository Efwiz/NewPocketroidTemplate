package org.jetbrains.kotlin.utils.exceptions;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Attachment;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.ExceptionWithAttachments;

/* JADX INFO: compiled from: KotlinExceptionWithAttachments.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\bf\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fJ\u0013\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\bH\u0016¢\u0006\u0002\u0010\tJ\u001a\u0010\n\u001a\u00020\u00002\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0016R\u0018\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0010À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/utils/exceptions/KotlinExceptionWithAttachments;", "Lorg/jetbrains/kotlin/com/intellij/openapi/diagnostic/ExceptionWithAttachments;", "mutableAttachments", "", "Lorg/jetbrains/kotlin/com/intellij/openapi/diagnostic/Attachment;", "getMutableAttachments", "()Ljava/util/List;", "getAttachments", "", "()[Lcom/intellij/openapi/diagnostic/Attachment;", "withAttachment", "name", "", FirebaseAnalytics.Param.CONTENT, "", "Companion", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface KotlinExceptionWithAttachments extends ExceptionWithAttachments {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = Companion.$$INSTANCE;

    List<Attachment> getMutableAttachments();

    @Override // org.jetbrains.kotlin.com.intellij.openapi.diagnostic.ExceptionWithAttachments
    default Attachment[] getAttachments() {
        return (Attachment[]) getMutableAttachments().toArray(new Attachment[0]);
    }

    default KotlinExceptionWithAttachments withAttachment(String name2, Object content) {
        String string;
        Intrinsics.checkNotNullParameter(name2, "name");
        List<Attachment> mutableAttachments = getMutableAttachments();
        if (content == null || (string = content.toString()) == null) {
            string = "<null>";
        }
        mutableAttachments.add(new Attachment(name2, string));
        return this;
    }

    /* JADX INFO: compiled from: KotlinExceptionWithAttachments.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0014\u0010\u0003\u001a\u00020\u0004*\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u001b\u0010\u0007\u001a\u00020\b*\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0000¢\u0006\u0002\b\f¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/utils/exceptions/KotlinExceptionWithAttachments$Companion;", "", "()V", "copyWithNewName", "Lorg/jetbrains/kotlin/com/intellij/openapi/diagnostic/Attachment;", "newName", "", "withAttachmentsFrom", "", "Lorg/jetbrains/kotlin/utils/exceptions/KotlinExceptionWithAttachments;", TypedValues.TransitionType.S_FROM, "", "withAttachmentsFrom$util", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final void withAttachmentsFrom$util(KotlinExceptionWithAttachments kotlinExceptionWithAttachments, Throwable th) {
            Intrinsics.checkNotNullParameter(kotlinExceptionWithAttachments, "<this>");
            if (th instanceof KotlinExceptionWithAttachments) {
                List<Attachment> mutableAttachments = ((KotlinExceptionWithAttachments) th).getMutableAttachments();
                List<Attachment> mutableAttachments2 = kotlinExceptionWithAttachments.getMutableAttachments();
                for (Attachment attachment : mutableAttachments) {
                    mutableAttachments2.add($$INSTANCE.copyWithNewName(attachment, "case_" + attachment.getPath()));
                }
            }
            if (th != 0) {
                kotlinExceptionWithAttachments.withAttachment("causeThrowable", ExceptionsKt.stackTraceToString(th));
            }
        }

        private final Attachment copyWithNewName(Attachment attachment, String str) {
            byte[] bytes = attachment.getBytes();
            Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
            Charset UTF_8 = StandardCharsets.UTF_8;
            Intrinsics.checkNotNullExpressionValue(UTF_8, "UTF_8");
            return new Attachment(str, new String(bytes, UTF_8));
        }
    }
}
