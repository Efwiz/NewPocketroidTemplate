package org.jetbrains.kotlin.renderer;

import androidx.exifinterface.media.ExifInterface;
import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import java.lang.reflect.Field;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.Delegates;
import kotlin.properties.ObservableProperty;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KClass;
import kotlin.reflect.KProperty;
import kotlin.text.StringsKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: DescriptorRendererOptionsImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0096\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b<\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b \n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0007\u0010ï\u0001\u001a\u00020\u0000J\b\u0010ð\u0001\u001a\u00030ñ\u0001J.\u0010ò\u0001\u001a\u0010\u0012\u0004\u0012\u00020\u0000\u0012\u0005\u0012\u0003Hô\u00010ó\u0001\"\u0005\b\u0000\u0010ô\u00012\b\u0010õ\u0001\u001a\u0003Hô\u0001H\u0002¢\u0006\u0003\u0010ö\u0001R+\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\b\n\u0010\u000b\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR+\u0010\f\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\b\u000f\u0010\u000b\u001a\u0004\b\r\u0010\u0007\"\u0004\b\u000e\u0010\tR+\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0003\u001a\u00020\u00108V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\b\u0016\u0010\u000b\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015RG\u0010\u0019\u001a\u0010\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00172\u0014\u0010\u0003\u001a\u0010\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00178V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\b\u001e\u0010\u000b\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR+\u0010\u001f\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\b\"\u0010\u000b\u001a\u0004\b \u0010\u0007\"\u0004\b!\u0010\tR+\u0010#\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\b&\u0010\u000b\u001a\u0004\b$\u0010\u0007\"\u0004\b%\u0010\tR+\u0010(\u001a\u00020'2\u0006\u0010\u0003\u001a\u00020'8V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\b-\u0010\u000b\u001a\u0004\b)\u0010*\"\u0004\b+\u0010,R+\u0010.\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\b1\u0010\u000b\u001a\u0004\b/\u0010\u0007\"\u0004\b0\u0010\tRG\u00104\u001a\u0010\u0012\u0004\u0012\u000202\u0012\u0004\u0012\u000203\u0018\u00010\u00172\u0014\u0010\u0003\u001a\u0010\u0012\u0004\u0012\u000202\u0012\u0004\u0012\u000203\u0018\u00010\u00178V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\b7\u0010\u000b\u001a\u0004\b5\u0010\u001b\"\u0004\b6\u0010\u001dR+\u00108\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\b;\u0010\u000b\u001a\u0004\b9\u0010\u0007\"\u0004\b:\u0010\tR+\u0010<\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\b?\u0010\u000b\u001a\u0004\b=\u0010\u0007\"\u0004\b>\u0010\tR7\u0010B\u001a\b\u0012\u0004\u0012\u00020A0@2\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020A0@8V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\bG\u0010\u000b\u001a\u0004\bC\u0010D\"\u0004\bE\u0010FR7\u0010H\u001a\b\u0012\u0004\u0012\u00020A0@2\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020A0@8V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\bK\u0010\u000b\u001a\u0004\bI\u0010D\"\u0004\bJ\u0010FR+\u0010L\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\bO\u0010\u000b\u001a\u0004\bM\u0010\u0007\"\u0004\bN\u0010\tR+\u0010P\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\bS\u0010\u000b\u001a\u0004\bQ\u0010\u0007\"\u0004\bR\u0010\tR+\u0010T\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\bW\u0010\u000b\u001a\u0004\bU\u0010\u0007\"\u0004\bV\u0010\tR\u001e\u0010X\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\bX\u0010\u0007R7\u0010Z\u001a\b\u0012\u0004\u0012\u00020Y0@2\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020Y0@8V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\b]\u0010\u000b\u001a\u0004\b[\u0010D\"\u0004\b\\\u0010FR+\u0010^\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\ba\u0010\u000b\u001a\u0004\b_\u0010\u0007\"\u0004\b`\u0010\tR+\u0010c\u001a\u00020b2\u0006\u0010\u0003\u001a\u00020b8V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\bh\u0010\u000b\u001a\u0004\bd\u0010e\"\u0004\bf\u0010gR+\u0010j\u001a\u00020i2\u0006\u0010\u0003\u001a\u00020i8V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\bo\u0010\u000b\u001a\u0004\bk\u0010l\"\u0004\bm\u0010nR+\u0010p\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\bs\u0010\u000b\u001a\u0004\bq\u0010\u0007\"\u0004\br\u0010\tR+\u0010t\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\bw\u0010\u000b\u001a\u0004\bu\u0010\u0007\"\u0004\bv\u0010\tR+\u0010y\u001a\u00020x2\u0006\u0010\u0003\u001a\u00020x8V@VX\u0096\u008e\u0002¢\u0006\u0012\n\u0004\b~\u0010\u000b\u001a\u0004\bz\u0010{\"\u0004\b|\u0010}RW\u0010\u0080\u0001\u001a\u0016\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u007f\u0012\u0006\u0012\u0004\u0018\u000103\u0018\u00010\u00172\u001a\u0010\u0003\u001a\u0016\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u007f\u0012\u0006\u0012\u0004\u0018\u000103\u0018\u00010\u00178V@VX\u0096\u008e\u0002¢\u0006\u0015\n\u0005\b\u0083\u0001\u0010\u000b\u001a\u0005\b\u0081\u0001\u0010\u001b\"\u0005\b\u0082\u0001\u0010\u001dR/\u0010\u0084\u0001\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048V@VX\u0096\u008e\u0002¢\u0006\u0015\n\u0005\b\u0087\u0001\u0010\u000b\u001a\u0005\b\u0085\u0001\u0010\u0007\"\u0005\b\u0086\u0001\u0010\tR/\u0010\u0088\u0001\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048V@VX\u0096\u008e\u0002¢\u0006\u0015\n\u0005\b\u008b\u0001\u0010\u000b\u001a\u0005\b\u0089\u0001\u0010\u0007\"\u0005\b\u008a\u0001\u0010\tR/\u0010\u008c\u0001\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048V@VX\u0096\u008e\u0002¢\u0006\u0015\n\u0005\b\u008f\u0001\u0010\u000b\u001a\u0005\b\u008d\u0001\u0010\u0007\"\u0005\b\u008e\u0001\u0010\tR/\u0010\u0090\u0001\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048V@VX\u0096\u008e\u0002¢\u0006\u0015\n\u0005\b\u0093\u0001\u0010\u000b\u001a\u0005\b\u0091\u0001\u0010\u0007\"\u0005\b\u0092\u0001\u0010\tR/\u0010\u0094\u0001\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048V@VX\u0096\u008e\u0002¢\u0006\u0015\n\u0005\b\u0097\u0001\u0010\u000b\u001a\u0005\b\u0095\u0001\u0010\u0007\"\u0005\b\u0096\u0001\u0010\tR/\u0010\u0098\u0001\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048V@VX\u0096\u008e\u0002¢\u0006\u0015\n\u0005\b\u009b\u0001\u0010\u000b\u001a\u0005\b\u0099\u0001\u0010\u0007\"\u0005\b\u009a\u0001\u0010\tR/\u0010\u009c\u0001\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048V@VX\u0096\u008e\u0002¢\u0006\u0015\n\u0005\b\u009f\u0001\u0010\u000b\u001a\u0005\b\u009d\u0001\u0010\u0007\"\u0005\b\u009e\u0001\u0010\tR/\u0010 \u0001\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048V@VX\u0096\u008e\u0002¢\u0006\u0015\n\u0005\b£\u0001\u0010\u000b\u001a\u0005\b¡\u0001\u0010\u0007\"\u0005\b¢\u0001\u0010\tR/\u0010¤\u0001\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048V@VX\u0096\u008e\u0002¢\u0006\u0015\n\u0005\b§\u0001\u0010\u000b\u001a\u0005\b¥\u0001\u0010\u0007\"\u0005\b¦\u0001\u0010\tR/\u0010¨\u0001\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048V@VX\u0096\u008e\u0002¢\u0006\u0015\n\u0005\b«\u0001\u0010\u000b\u001a\u0005\b©\u0001\u0010\u0007\"\u0005\bª\u0001\u0010\tR/\u0010¬\u0001\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048V@VX\u0096\u008e\u0002¢\u0006\u0015\n\u0005\b¯\u0001\u0010\u000b\u001a\u0005\b\u00ad\u0001\u0010\u0007\"\u0005\b®\u0001\u0010\tR/\u0010°\u0001\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048V@VX\u0096\u008e\u0002¢\u0006\u0015\n\u0005\b³\u0001\u0010\u000b\u001a\u0005\b±\u0001\u0010\u0007\"\u0005\b²\u0001\u0010\tR/\u0010´\u0001\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048V@VX\u0096\u008e\u0002¢\u0006\u0015\n\u0005\b·\u0001\u0010\u000b\u001a\u0005\bµ\u0001\u0010\u0007\"\u0005\b¶\u0001\u0010\tR/\u0010¸\u0001\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048V@VX\u0096\u008e\u0002¢\u0006\u0015\n\u0005\b»\u0001\u0010\u000b\u001a\u0005\b¹\u0001\u0010\u0007\"\u0005\bº\u0001\u0010\tR3\u0010½\u0001\u001a\u00030¼\u00012\u0007\u0010\u0003\u001a\u00030¼\u00018V@VX\u0096\u008e\u0002¢\u0006\u0017\n\u0005\bÂ\u0001\u0010\u000b\u001a\u0006\b¾\u0001\u0010¿\u0001\"\u0006\bÀ\u0001\u0010Á\u0001RK\u0010Ä\u0001\u001a\u0010\u0012\u0005\u0012\u00030Ã\u0001\u0012\u0005\u0012\u00030Ã\u00010\u00172\u0014\u0010\u0003\u001a\u0010\u0012\u0005\u0012\u00030Ã\u0001\u0012\u0005\u0012\u00030Ã\u00010\u00178V@VX\u0096\u008e\u0002¢\u0006\u0015\n\u0005\bÇ\u0001\u0010\u000b\u001a\u0005\bÅ\u0001\u0010\u001b\"\u0005\bÆ\u0001\u0010\u001dR/\u0010È\u0001\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048V@VX\u0096\u008e\u0002¢\u0006\u0015\n\u0005\bË\u0001\u0010\u000b\u001a\u0005\bÉ\u0001\u0010\u0007\"\u0005\bÊ\u0001\u0010\tR/\u0010Ì\u0001\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048V@VX\u0096\u008e\u0002¢\u0006\u0015\n\u0005\bÏ\u0001\u0010\u000b\u001a\u0005\bÍ\u0001\u0010\u0007\"\u0005\bÎ\u0001\u0010\tR3\u0010Ñ\u0001\u001a\u00030Ð\u00012\u0007\u0010\u0003\u001a\u00030Ð\u00018V@VX\u0096\u008e\u0002¢\u0006\u0017\n\u0005\bÖ\u0001\u0010\u000b\u001a\u0006\bÒ\u0001\u0010Ó\u0001\"\u0006\bÔ\u0001\u0010Õ\u0001R/\u0010×\u0001\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048V@VX\u0096\u008e\u0002¢\u0006\u0015\n\u0005\bÚ\u0001\u0010\u000b\u001a\u0005\bØ\u0001\u0010\u0007\"\u0005\bÙ\u0001\u0010\tR/\u0010Û\u0001\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048V@VX\u0096\u008e\u0002¢\u0006\u0015\n\u0005\bÞ\u0001\u0010\u000b\u001a\u0005\bÜ\u0001\u0010\u0007\"\u0005\bÝ\u0001\u0010\tR/\u0010ß\u0001\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048V@VX\u0096\u008e\u0002¢\u0006\u0015\n\u0005\bâ\u0001\u0010\u000b\u001a\u0005\bà\u0001\u0010\u0007\"\u0005\bá\u0001\u0010\tR/\u0010ã\u0001\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048V@VX\u0096\u008e\u0002¢\u0006\u0015\n\u0005\bæ\u0001\u0010\u000b\u001a\u0005\bä\u0001\u0010\u0007\"\u0005\bå\u0001\u0010\tR/\u0010ç\u0001\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048V@VX\u0096\u008e\u0002¢\u0006\u0015\n\u0005\bê\u0001\u0010\u000b\u001a\u0005\bè\u0001\u0010\u0007\"\u0005\bé\u0001\u0010\tR/\u0010ë\u0001\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00048V@VX\u0096\u008e\u0002¢\u0006\u0015\n\u0005\bî\u0001\u0010\u000b\u001a\u0005\bì\u0001\u0010\u0007\"\u0005\bí\u0001\u0010\t¨\u0006÷\u0001"}, d2 = {"Lorg/jetbrains/kotlin/renderer/DescriptorRendererOptionsImpl;", "Lorg/jetbrains/kotlin/renderer/DescriptorRendererOptions;", "()V", "<set-?>", "", "actualPropertiesInPrimaryConstructor", "getActualPropertiesInPrimaryConstructor", "()Z", "setActualPropertiesInPrimaryConstructor", "(Z)V", "actualPropertiesInPrimaryConstructor$delegate", "Lkotlin/properties/ReadWriteProperty;", "alwaysRenderModifiers", "getAlwaysRenderModifiers", "setAlwaysRenderModifiers", "alwaysRenderModifiers$delegate", "Lorg/jetbrains/kotlin/renderer/AnnotationArgumentsRenderingPolicy;", "annotationArgumentsRenderingPolicy", "getAnnotationArgumentsRenderingPolicy", "()Lorg/jetbrains/kotlin/renderer/AnnotationArgumentsRenderingPolicy;", "setAnnotationArgumentsRenderingPolicy", "(Lorg/jetbrains/kotlin/renderer/AnnotationArgumentsRenderingPolicy;)V", "annotationArgumentsRenderingPolicy$delegate", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "annotationFilter", "getAnnotationFilter", "()Lkotlin/jvm/functions/Function1;", "setAnnotationFilter", "(Lkotlin/jvm/functions/Function1;)V", "annotationFilter$delegate", "boldOnlyForNamesInHtml", "getBoldOnlyForNamesInHtml", "setBoldOnlyForNamesInHtml", "boldOnlyForNamesInHtml$delegate", "classWithPrimaryConstructor", "getClassWithPrimaryConstructor", "setClassWithPrimaryConstructor", "classWithPrimaryConstructor$delegate", "Lorg/jetbrains/kotlin/renderer/ClassifierNamePolicy;", "classifierNamePolicy", "getClassifierNamePolicy", "()Lorg/jetbrains/kotlin/renderer/ClassifierNamePolicy;", "setClassifierNamePolicy", "(Lorg/jetbrains/kotlin/renderer/ClassifierNamePolicy;)V", "classifierNamePolicy$delegate", "debugMode", "getDebugMode", "setDebugMode", "debugMode$delegate", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "", "defaultParameterValueRenderer", "getDefaultParameterValueRenderer", "setDefaultParameterValueRenderer", "defaultParameterValueRenderer$delegate", "eachAnnotationOnNewLine", "getEachAnnotationOnNewLine", "setEachAnnotationOnNewLine", "eachAnnotationOnNewLine$delegate", "enhancedTypes", "getEnhancedTypes", "setEnhancedTypes", "enhancedTypes$delegate", "", "Lorg/jetbrains/kotlin/name/FqName;", "excludedAnnotationClasses", "getExcludedAnnotationClasses", "()Ljava/util/Set;", "setExcludedAnnotationClasses", "(Ljava/util/Set;)V", "excludedAnnotationClasses$delegate", "excludedTypeAnnotationClasses", "getExcludedTypeAnnotationClasses", "setExcludedTypeAnnotationClasses", "excludedTypeAnnotationClasses$delegate", "includeAdditionalModifiers", "getIncludeAdditionalModifiers", "setIncludeAdditionalModifiers", "includeAdditionalModifiers$delegate", "includePropertyConstant", "getIncludePropertyConstant", "setIncludePropertyConstant", "includePropertyConstant$delegate", "informativeErrorType", "getInformativeErrorType", "setInformativeErrorType", "informativeErrorType$delegate", "isLocked", "Lorg/jetbrains/kotlin/renderer/DescriptorRendererModifier;", "modifiers", "getModifiers", "setModifiers", "modifiers$delegate", "normalizedVisibilities", "getNormalizedVisibilities", "setNormalizedVisibilities", "normalizedVisibilities$delegate", "Lorg/jetbrains/kotlin/renderer/OverrideRenderingPolicy;", "overrideRenderingPolicy", "getOverrideRenderingPolicy", "()Lorg/jetbrains/kotlin/renderer/OverrideRenderingPolicy;", "setOverrideRenderingPolicy", "(Lorg/jetbrains/kotlin/renderer/OverrideRenderingPolicy;)V", "overrideRenderingPolicy$delegate", "Lorg/jetbrains/kotlin/renderer/ParameterNameRenderingPolicy;", "parameterNameRenderingPolicy", "getParameterNameRenderingPolicy", "()Lorg/jetbrains/kotlin/renderer/ParameterNameRenderingPolicy;", "setParameterNameRenderingPolicy", "(Lorg/jetbrains/kotlin/renderer/ParameterNameRenderingPolicy;)V", "parameterNameRenderingPolicy$delegate", "parameterNamesInFunctionalTypes", "getParameterNamesInFunctionalTypes", "setParameterNamesInFunctionalTypes", "parameterNamesInFunctionalTypes$delegate", "presentableUnresolvedTypes", "getPresentableUnresolvedTypes", "setPresentableUnresolvedTypes", "presentableUnresolvedTypes$delegate", "Lorg/jetbrains/kotlin/renderer/PropertyAccessorRenderingPolicy;", "propertyAccessorRenderingPolicy", "getPropertyAccessorRenderingPolicy", "()Lorg/jetbrains/kotlin/renderer/PropertyAccessorRenderingPolicy;", "setPropertyAccessorRenderingPolicy", "(Lorg/jetbrains/kotlin/renderer/PropertyAccessorRenderingPolicy;)V", "propertyAccessorRenderingPolicy$delegate", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "propertyConstantRenderer", "getPropertyConstantRenderer", "setPropertyConstantRenderer", "propertyConstantRenderer$delegate", "receiverAfterName", "getReceiverAfterName", "setReceiverAfterName", "receiverAfterName$delegate", "renderCompanionObjectName", "getRenderCompanionObjectName", "setRenderCompanionObjectName", "renderCompanionObjectName$delegate", "renderConstructorDelegation", "getRenderConstructorDelegation", "setRenderConstructorDelegation", "renderConstructorDelegation$delegate", "renderConstructorKeyword", "getRenderConstructorKeyword", "setRenderConstructorKeyword", "renderConstructorKeyword$delegate", "renderDefaultAnnotationArguments", "getRenderDefaultAnnotationArguments", "setRenderDefaultAnnotationArguments", "renderDefaultAnnotationArguments$delegate", "renderDefaultModality", "getRenderDefaultModality", "setRenderDefaultModality", "renderDefaultModality$delegate", "renderDefaultVisibility", "getRenderDefaultVisibility", "setRenderDefaultVisibility", "renderDefaultVisibility$delegate", "renderFunctionContracts", "getRenderFunctionContracts", "setRenderFunctionContracts", "renderFunctionContracts$delegate", "renderPrimaryConstructorParametersAsProperties", "getRenderPrimaryConstructorParametersAsProperties", "setRenderPrimaryConstructorParametersAsProperties", "renderPrimaryConstructorParametersAsProperties$delegate", "renderTypeExpansions", "getRenderTypeExpansions", "setRenderTypeExpansions", "renderTypeExpansions$delegate", "renderUnabbreviatedType", "getRenderUnabbreviatedType", "setRenderUnabbreviatedType", "renderUnabbreviatedType$delegate", "secondaryConstructorsAsPrimary", "getSecondaryConstructorsAsPrimary", "setSecondaryConstructorsAsPrimary", "secondaryConstructorsAsPrimary$delegate", "startFromDeclarationKeyword", "getStartFromDeclarationKeyword", "setStartFromDeclarationKeyword", "startFromDeclarationKeyword$delegate", "startFromName", "getStartFromName", "setStartFromName", "startFromName$delegate", "Lorg/jetbrains/kotlin/renderer/RenderingFormat;", "textFormat", "getTextFormat", "()Lorg/jetbrains/kotlin/renderer/RenderingFormat;", "setTextFormat", "(Lorg/jetbrains/kotlin/renderer/RenderingFormat;)V", "textFormat$delegate", "Lorg/jetbrains/kotlin/types/KotlinType;", "typeNormalizer", "getTypeNormalizer", "setTypeNormalizer", "typeNormalizer$delegate", "uninferredTypeParameterAsName", "getUninferredTypeParameterAsName", "setUninferredTypeParameterAsName", "uninferredTypeParameterAsName$delegate", "unitReturnType", "getUnitReturnType", "setUnitReturnType", "unitReturnType$delegate", "Lorg/jetbrains/kotlin/renderer/DescriptorRenderer$ValueParametersHandler;", "valueParametersHandler", "getValueParametersHandler", "()Lorg/jetbrains/kotlin/renderer/DescriptorRenderer$ValueParametersHandler;", "setValueParametersHandler", "(Lorg/jetbrains/kotlin/renderer/DescriptorRenderer$ValueParametersHandler;)V", "valueParametersHandler$delegate", "verbose", "getVerbose", "setVerbose", "verbose$delegate", "withDefinedIn", "getWithDefinedIn", "setWithDefinedIn", "withDefinedIn$delegate", "withSourceFileForTopLevel", "getWithSourceFileForTopLevel", "setWithSourceFileForTopLevel", "withSourceFileForTopLevel$delegate", "withoutReturnType", "getWithoutReturnType", "setWithoutReturnType", "withoutReturnType$delegate", "withoutSuperTypes", "getWithoutSuperTypes", "setWithoutSuperTypes", "withoutSuperTypes$delegate", "withoutTypeParameters", "getWithoutTypeParameters", "setWithoutTypeParameters", "withoutTypeParameters$delegate", ConfigConstants.CONFIG_RENAMELIMIT_COPY, "lock", "", "property", "Lkotlin/properties/ReadWriteProperty;", ExifInterface.GPS_DIRECTION_TRUE, "initialValue", "(Ljava/lang/Object;)Lkotlin/properties/ReadWriteProperty;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DescriptorRendererOptionsImpl implements DescriptorRendererOptions {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "classifierNamePolicy", "getClassifierNamePolicy()Lorg/jetbrains/kotlin/renderer/ClassifierNamePolicy;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "withDefinedIn", "getWithDefinedIn()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "withSourceFileForTopLevel", "getWithSourceFileForTopLevel()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "modifiers", "getModifiers()Ljava/util/Set;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "startFromName", "getStartFromName()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "startFromDeclarationKeyword", "getStartFromDeclarationKeyword()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "debugMode", "getDebugMode()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "classWithPrimaryConstructor", "getClassWithPrimaryConstructor()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "verbose", "getVerbose()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "unitReturnType", "getUnitReturnType()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "withoutReturnType", "getWithoutReturnType()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "enhancedTypes", "getEnhancedTypes()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "normalizedVisibilities", "getNormalizedVisibilities()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "renderDefaultVisibility", "getRenderDefaultVisibility()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "renderDefaultModality", "getRenderDefaultModality()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "renderConstructorDelegation", "getRenderConstructorDelegation()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "renderPrimaryConstructorParametersAsProperties", "getRenderPrimaryConstructorParametersAsProperties()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "actualPropertiesInPrimaryConstructor", "getActualPropertiesInPrimaryConstructor()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "uninferredTypeParameterAsName", "getUninferredTypeParameterAsName()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "includePropertyConstant", "getIncludePropertyConstant()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "propertyConstantRenderer", "getPropertyConstantRenderer()Lkotlin/jvm/functions/Function1;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "withoutTypeParameters", "getWithoutTypeParameters()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "withoutSuperTypes", "getWithoutSuperTypes()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "typeNormalizer", "getTypeNormalizer()Lkotlin/jvm/functions/Function1;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "defaultParameterValueRenderer", "getDefaultParameterValueRenderer()Lkotlin/jvm/functions/Function1;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "secondaryConstructorsAsPrimary", "getSecondaryConstructorsAsPrimary()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "overrideRenderingPolicy", "getOverrideRenderingPolicy()Lorg/jetbrains/kotlin/renderer/OverrideRenderingPolicy;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "valueParametersHandler", "getValueParametersHandler()Lorg/jetbrains/kotlin/renderer/DescriptorRenderer$ValueParametersHandler;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "textFormat", "getTextFormat()Lorg/jetbrains/kotlin/renderer/RenderingFormat;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "parameterNameRenderingPolicy", "getParameterNameRenderingPolicy()Lorg/jetbrains/kotlin/renderer/ParameterNameRenderingPolicy;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "receiverAfterName", "getReceiverAfterName()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "renderCompanionObjectName", "getRenderCompanionObjectName()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "propertyAccessorRenderingPolicy", "getPropertyAccessorRenderingPolicy()Lorg/jetbrains/kotlin/renderer/PropertyAccessorRenderingPolicy;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "renderDefaultAnnotationArguments", "getRenderDefaultAnnotationArguments()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "eachAnnotationOnNewLine", "getEachAnnotationOnNewLine()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "excludedAnnotationClasses", "getExcludedAnnotationClasses()Ljava/util/Set;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "excludedTypeAnnotationClasses", "getExcludedTypeAnnotationClasses()Ljava/util/Set;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "annotationFilter", "getAnnotationFilter()Lkotlin/jvm/functions/Function1;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "annotationArgumentsRenderingPolicy", "getAnnotationArgumentsRenderingPolicy()Lorg/jetbrains/kotlin/renderer/AnnotationArgumentsRenderingPolicy;")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "alwaysRenderModifiers", "getAlwaysRenderModifiers()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "renderConstructorKeyword", "getRenderConstructorKeyword()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "renderUnabbreviatedType", "getRenderUnabbreviatedType()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "renderTypeExpansions", "getRenderTypeExpansions()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "includeAdditionalModifiers", "getIncludeAdditionalModifiers()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "parameterNamesInFunctionalTypes", "getParameterNamesInFunctionalTypes()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "renderFunctionContracts", "getRenderFunctionContracts()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "presentableUnresolvedTypes", "getPresentableUnresolvedTypes()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "boldOnlyForNamesInHtml", "getBoldOnlyForNamesInHtml()Z")), Reflection.mutableProperty1(new MutablePropertyReference1Impl(Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class), "informativeErrorType", "getInformativeErrorType()Z"))};
    private boolean isLocked;

    /* JADX INFO: renamed from: classifierNamePolicy$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty classifierNamePolicy = property(ClassifierNamePolicy.SOURCE_CODE_QUALIFIED.INSTANCE);

    /* JADX INFO: renamed from: withDefinedIn$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty withDefinedIn = property(true);

    /* JADX INFO: renamed from: withSourceFileForTopLevel$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty withSourceFileForTopLevel = property(true);

    /* JADX INFO: renamed from: modifiers$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty modifiers = property(DescriptorRendererModifier.ALL_EXCEPT_ANNOTATIONS);

    /* JADX INFO: renamed from: startFromName$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty startFromName = property(false);

    /* JADX INFO: renamed from: startFromDeclarationKeyword$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty startFromDeclarationKeyword = property(false);

    /* JADX INFO: renamed from: debugMode$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty debugMode = property(false);

    /* JADX INFO: renamed from: classWithPrimaryConstructor$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty classWithPrimaryConstructor = property(false);

    /* JADX INFO: renamed from: verbose$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty verbose = property(false);

    /* JADX INFO: renamed from: unitReturnType$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty unitReturnType = property(true);

    /* JADX INFO: renamed from: withoutReturnType$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty withoutReturnType = property(false);

    /* JADX INFO: renamed from: enhancedTypes$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty enhancedTypes = property(false);

    /* JADX INFO: renamed from: normalizedVisibilities$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty normalizedVisibilities = property(false);

    /* JADX INFO: renamed from: renderDefaultVisibility$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty renderDefaultVisibility = property(true);

    /* JADX INFO: renamed from: renderDefaultModality$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty renderDefaultModality = property(true);

    /* JADX INFO: renamed from: renderConstructorDelegation$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty renderConstructorDelegation = property(false);

    /* JADX INFO: renamed from: renderPrimaryConstructorParametersAsProperties$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty renderPrimaryConstructorParametersAsProperties = property(false);

    /* JADX INFO: renamed from: actualPropertiesInPrimaryConstructor$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty actualPropertiesInPrimaryConstructor = property(false);

    /* JADX INFO: renamed from: uninferredTypeParameterAsName$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty uninferredTypeParameterAsName = property(false);

    /* JADX INFO: renamed from: includePropertyConstant$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty includePropertyConstant = property(false);

    /* JADX INFO: renamed from: propertyConstantRenderer$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty propertyConstantRenderer = property(null);

    /* JADX INFO: renamed from: withoutTypeParameters$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty withoutTypeParameters = property(false);

    /* JADX INFO: renamed from: withoutSuperTypes$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty withoutSuperTypes = property(false);

    /* JADX INFO: renamed from: typeNormalizer$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty typeNormalizer = property(new Function1<KotlinType, KotlinType>() { // from class: org.jetbrains.kotlin.renderer.DescriptorRendererOptionsImpl$typeNormalizer$2
        @Override // kotlin.jvm.functions.Function1
        public final KotlinType invoke(KotlinType it) {
            Intrinsics.checkNotNullParameter(it, "it");
            return it;
        }
    });

    /* JADX INFO: renamed from: defaultParameterValueRenderer$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty defaultParameterValueRenderer = property(new Function1<ValueParameterDescriptor, String>() { // from class: org.jetbrains.kotlin.renderer.DescriptorRendererOptionsImpl$defaultParameterValueRenderer$2
        @Override // kotlin.jvm.functions.Function1
        public final String invoke(ValueParameterDescriptor it) {
            Intrinsics.checkNotNullParameter(it, "it");
            return "...";
        }
    });

    /* JADX INFO: renamed from: secondaryConstructorsAsPrimary$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty secondaryConstructorsAsPrimary = property(true);

    /* JADX INFO: renamed from: overrideRenderingPolicy$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty overrideRenderingPolicy = property(OverrideRenderingPolicy.RENDER_OPEN);

    /* JADX INFO: renamed from: valueParametersHandler$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty valueParametersHandler = property(DescriptorRenderer.ValueParametersHandler.DEFAULT.INSTANCE);

    /* JADX INFO: renamed from: textFormat$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty textFormat = property(RenderingFormat.PLAIN);

    /* JADX INFO: renamed from: parameterNameRenderingPolicy$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty parameterNameRenderingPolicy = property(ParameterNameRenderingPolicy.ALL);

    /* JADX INFO: renamed from: receiverAfterName$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty receiverAfterName = property(false);

    /* JADX INFO: renamed from: renderCompanionObjectName$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty renderCompanionObjectName = property(false);

    /* JADX INFO: renamed from: propertyAccessorRenderingPolicy$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty propertyAccessorRenderingPolicy = property(PropertyAccessorRenderingPolicy.DEBUG);

    /* JADX INFO: renamed from: renderDefaultAnnotationArguments$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty renderDefaultAnnotationArguments = property(false);

    /* JADX INFO: renamed from: eachAnnotationOnNewLine$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty eachAnnotationOnNewLine = property(false);

    /* JADX INFO: renamed from: excludedAnnotationClasses$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty excludedAnnotationClasses = property(SetsKt.emptySet());

    /* JADX INFO: renamed from: excludedTypeAnnotationClasses$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty excludedTypeAnnotationClasses = property(ExcludedTypeAnnotations.INSTANCE.getInternalAnnotationsForResolve());

    /* JADX INFO: renamed from: annotationFilter$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty annotationFilter = property(null);

    /* JADX INFO: renamed from: annotationArgumentsRenderingPolicy$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty annotationArgumentsRenderingPolicy = property(AnnotationArgumentsRenderingPolicy.NO_ARGUMENTS);

    /* JADX INFO: renamed from: alwaysRenderModifiers$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty alwaysRenderModifiers = property(false);

    /* JADX INFO: renamed from: renderConstructorKeyword$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty renderConstructorKeyword = property(true);

    /* JADX INFO: renamed from: renderUnabbreviatedType$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty renderUnabbreviatedType = property(true);

    /* JADX INFO: renamed from: renderTypeExpansions$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty renderTypeExpansions = property(false);

    /* JADX INFO: renamed from: includeAdditionalModifiers$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty includeAdditionalModifiers = property(true);

    /* JADX INFO: renamed from: parameterNamesInFunctionalTypes$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty parameterNamesInFunctionalTypes = property(true);

    /* JADX INFO: renamed from: renderFunctionContracts$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty renderFunctionContracts = property(false);

    /* JADX INFO: renamed from: presentableUnresolvedTypes$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty presentableUnresolvedTypes = property(false);

    /* JADX INFO: renamed from: boldOnlyForNamesInHtml$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty boldOnlyForNamesInHtml = property(false);

    /* JADX INFO: renamed from: informativeErrorType$delegate, reason: from kotlin metadata */
    private final ReadWriteProperty informativeErrorType = property(true);

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getIncludeAnnotationArguments() {
        return DescriptorRendererOptions.DefaultImpls.getIncludeAnnotationArguments(this);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getIncludeEmptyAnnotationArguments() {
        return DescriptorRendererOptions.DefaultImpls.getIncludeEmptyAnnotationArguments(this);
    }

    /* JADX INFO: renamed from: isLocked, reason: from getter */
    public final boolean getIsLocked() {
        return this.isLocked;
    }

    public final void lock() {
        this.isLocked = true;
    }

    public final DescriptorRendererOptionsImpl copy() {
        DescriptorRendererOptionsImpl descriptorRendererOptionsImpl = new DescriptorRendererOptionsImpl();
        Field[] declaredFields = getClass().getDeclaredFields();
        Intrinsics.checkNotNullExpressionValue(declaredFields, "getDeclaredFields(...)");
        for (Field field : declaredFields) {
            if ((field.getModifiers() & 8) == 0) {
                field.setAccessible(true);
                Object obj = field.get(this);
                ObservableProperty observableProperty = obj instanceof ObservableProperty ? (ObservableProperty) obj : null;
                if (observableProperty != null) {
                    String name2 = field.getName();
                    Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                    StringsKt.startsWith$default(name2, LanguageCodeUtil.IS, false, 2, (Object) null);
                    KClass orCreateKotlinClass = Reflection.getOrCreateKotlinClass(DescriptorRendererOptionsImpl.class);
                    String name3 = field.getName();
                    StringBuilder sb = new StringBuilder("get");
                    String name4 = field.getName();
                    Intrinsics.checkNotNullExpressionValue(name4, "getName(...)");
                    if (name4.length() > 0) {
                        char upperCase = Character.toUpperCase(name4.charAt(0));
                        String strSubstring = name4.substring(1);
                        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
                        name4 = upperCase + strSubstring;
                    }
                    field.set(descriptorRendererOptionsImpl, descriptorRendererOptionsImpl.property(observableProperty.getValue(this, new PropertyReference1Impl(orCreateKotlinClass, name3, sb.append(name4).toString()))));
                }
            }
        }
        return descriptorRendererOptionsImpl;
    }

    private final <T> ReadWriteProperty<DescriptorRendererOptionsImpl, T> property(final T initialValue) {
        Delegates delegates = Delegates.INSTANCE;
        return new ObservableProperty<T>(initialValue) { // from class: org.jetbrains.kotlin.renderer.DescriptorRendererOptionsImpl$property$$inlined$vetoable$1
            @Override // kotlin.properties.ObservableProperty
            protected boolean beforeChange(KProperty<?> property, T oldValue, T newValue) {
                Intrinsics.checkNotNullParameter(property, "property");
                if (this.getIsLocked()) {
                    throw new IllegalStateException("Cannot modify readonly DescriptorRendererOptions");
                }
                return true;
            }
        };
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public ClassifierNamePolicy getClassifierNamePolicy() {
        return (ClassifierNamePolicy) this.classifierNamePolicy.getValue(this, $$delegatedProperties[0]);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setClassifierNamePolicy(ClassifierNamePolicy classifierNamePolicy) {
        Intrinsics.checkNotNullParameter(classifierNamePolicy, "<set-?>");
        this.classifierNamePolicy.setValue(this, $$delegatedProperties[0], classifierNamePolicy);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getWithDefinedIn() {
        return ((Boolean) this.withDefinedIn.getValue(this, $$delegatedProperties[1])).booleanValue();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setWithDefinedIn(boolean z) {
        this.withDefinedIn.setValue(this, $$delegatedProperties[1], Boolean.valueOf(z));
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getWithSourceFileForTopLevel() {
        return ((Boolean) this.withSourceFileForTopLevel.getValue(this, $$delegatedProperties[2])).booleanValue();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setWithSourceFileForTopLevel(boolean z) {
        this.withSourceFileForTopLevel.setValue(this, $$delegatedProperties[2], Boolean.valueOf(z));
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public Set<DescriptorRendererModifier> getModifiers() {
        return (Set) this.modifiers.getValue(this, $$delegatedProperties[3]);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setModifiers(Set<? extends DescriptorRendererModifier> set) {
        Intrinsics.checkNotNullParameter(set, "<set-?>");
        this.modifiers.setValue(this, $$delegatedProperties[3], set);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getStartFromName() {
        return ((Boolean) this.startFromName.getValue(this, $$delegatedProperties[4])).booleanValue();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setStartFromName(boolean z) {
        this.startFromName.setValue(this, $$delegatedProperties[4], Boolean.valueOf(z));
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getStartFromDeclarationKeyword() {
        return ((Boolean) this.startFromDeclarationKeyword.getValue(this, $$delegatedProperties[5])).booleanValue();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setStartFromDeclarationKeyword(boolean z) {
        this.startFromDeclarationKeyword.setValue(this, $$delegatedProperties[5], Boolean.valueOf(z));
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getDebugMode() {
        return ((Boolean) this.debugMode.getValue(this, $$delegatedProperties[6])).booleanValue();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setDebugMode(boolean z) {
        this.debugMode.setValue(this, $$delegatedProperties[6], Boolean.valueOf(z));
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getClassWithPrimaryConstructor() {
        return ((Boolean) this.classWithPrimaryConstructor.getValue(this, $$delegatedProperties[7])).booleanValue();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setClassWithPrimaryConstructor(boolean z) {
        this.classWithPrimaryConstructor.setValue(this, $$delegatedProperties[7], Boolean.valueOf(z));
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getVerbose() {
        return ((Boolean) this.verbose.getValue(this, $$delegatedProperties[8])).booleanValue();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setVerbose(boolean z) {
        this.verbose.setValue(this, $$delegatedProperties[8], Boolean.valueOf(z));
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getUnitReturnType() {
        return ((Boolean) this.unitReturnType.getValue(this, $$delegatedProperties[9])).booleanValue();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setUnitReturnType(boolean z) {
        this.unitReturnType.setValue(this, $$delegatedProperties[9], Boolean.valueOf(z));
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getWithoutReturnType() {
        return ((Boolean) this.withoutReturnType.getValue(this, $$delegatedProperties[10])).booleanValue();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setWithoutReturnType(boolean z) {
        this.withoutReturnType.setValue(this, $$delegatedProperties[10], Boolean.valueOf(z));
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getEnhancedTypes() {
        return ((Boolean) this.enhancedTypes.getValue(this, $$delegatedProperties[11])).booleanValue();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setEnhancedTypes(boolean z) {
        this.enhancedTypes.setValue(this, $$delegatedProperties[11], Boolean.valueOf(z));
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getNormalizedVisibilities() {
        return ((Boolean) this.normalizedVisibilities.getValue(this, $$delegatedProperties[12])).booleanValue();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setNormalizedVisibilities(boolean z) {
        this.normalizedVisibilities.setValue(this, $$delegatedProperties[12], Boolean.valueOf(z));
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getRenderDefaultVisibility() {
        return ((Boolean) this.renderDefaultVisibility.getValue(this, $$delegatedProperties[13])).booleanValue();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setRenderDefaultVisibility(boolean z) {
        this.renderDefaultVisibility.setValue(this, $$delegatedProperties[13], Boolean.valueOf(z));
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getRenderDefaultModality() {
        return ((Boolean) this.renderDefaultModality.getValue(this, $$delegatedProperties[14])).booleanValue();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setRenderDefaultModality(boolean z) {
        this.renderDefaultModality.setValue(this, $$delegatedProperties[14], Boolean.valueOf(z));
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getRenderConstructorDelegation() {
        return ((Boolean) this.renderConstructorDelegation.getValue(this, $$delegatedProperties[15])).booleanValue();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setRenderConstructorDelegation(boolean z) {
        this.renderConstructorDelegation.setValue(this, $$delegatedProperties[15], Boolean.valueOf(z));
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getRenderPrimaryConstructorParametersAsProperties() {
        return ((Boolean) this.renderPrimaryConstructorParametersAsProperties.getValue(this, $$delegatedProperties[16])).booleanValue();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setRenderPrimaryConstructorParametersAsProperties(boolean z) {
        this.renderPrimaryConstructorParametersAsProperties.setValue(this, $$delegatedProperties[16], Boolean.valueOf(z));
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getActualPropertiesInPrimaryConstructor() {
        return ((Boolean) this.actualPropertiesInPrimaryConstructor.getValue(this, $$delegatedProperties[17])).booleanValue();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setActualPropertiesInPrimaryConstructor(boolean z) {
        this.actualPropertiesInPrimaryConstructor.setValue(this, $$delegatedProperties[17], Boolean.valueOf(z));
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getUninferredTypeParameterAsName() {
        return ((Boolean) this.uninferredTypeParameterAsName.getValue(this, $$delegatedProperties[18])).booleanValue();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setUninferredTypeParameterAsName(boolean z) {
        this.uninferredTypeParameterAsName.setValue(this, $$delegatedProperties[18], Boolean.valueOf(z));
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getIncludePropertyConstant() {
        return ((Boolean) this.includePropertyConstant.getValue(this, $$delegatedProperties[19])).booleanValue();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setIncludePropertyConstant(boolean z) {
        this.includePropertyConstant.setValue(this, $$delegatedProperties[19], Boolean.valueOf(z));
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public Function1<ConstantValue<?>, String> getPropertyConstantRenderer() {
        return (Function1) this.propertyConstantRenderer.getValue(this, $$delegatedProperties[20]);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setPropertyConstantRenderer(Function1<? super ConstantValue<?>, String> function1) {
        this.propertyConstantRenderer.setValue(this, $$delegatedProperties[20], function1);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getWithoutTypeParameters() {
        return ((Boolean) this.withoutTypeParameters.getValue(this, $$delegatedProperties[21])).booleanValue();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setWithoutTypeParameters(boolean z) {
        this.withoutTypeParameters.setValue(this, $$delegatedProperties[21], Boolean.valueOf(z));
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getWithoutSuperTypes() {
        return ((Boolean) this.withoutSuperTypes.getValue(this, $$delegatedProperties[22])).booleanValue();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setWithoutSuperTypes(boolean z) {
        this.withoutSuperTypes.setValue(this, $$delegatedProperties[22], Boolean.valueOf(z));
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public Function1<KotlinType, KotlinType> getTypeNormalizer() {
        return (Function1) this.typeNormalizer.getValue(this, $$delegatedProperties[23]);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setTypeNormalizer(Function1<? super KotlinType, ? extends KotlinType> function1) {
        Intrinsics.checkNotNullParameter(function1, "<set-?>");
        this.typeNormalizer.setValue(this, $$delegatedProperties[23], function1);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public Function1<ValueParameterDescriptor, String> getDefaultParameterValueRenderer() {
        return (Function1) this.defaultParameterValueRenderer.getValue(this, $$delegatedProperties[24]);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setDefaultParameterValueRenderer(Function1<? super ValueParameterDescriptor, String> function1) {
        this.defaultParameterValueRenderer.setValue(this, $$delegatedProperties[24], function1);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getSecondaryConstructorsAsPrimary() {
        return ((Boolean) this.secondaryConstructorsAsPrimary.getValue(this, $$delegatedProperties[25])).booleanValue();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setSecondaryConstructorsAsPrimary(boolean z) {
        this.secondaryConstructorsAsPrimary.setValue(this, $$delegatedProperties[25], Boolean.valueOf(z));
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public OverrideRenderingPolicy getOverrideRenderingPolicy() {
        return (OverrideRenderingPolicy) this.overrideRenderingPolicy.getValue(this, $$delegatedProperties[26]);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setOverrideRenderingPolicy(OverrideRenderingPolicy overrideRenderingPolicy) {
        Intrinsics.checkNotNullParameter(overrideRenderingPolicy, "<set-?>");
        this.overrideRenderingPolicy.setValue(this, $$delegatedProperties[26], overrideRenderingPolicy);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public DescriptorRenderer.ValueParametersHandler getValueParametersHandler() {
        return (DescriptorRenderer.ValueParametersHandler) this.valueParametersHandler.getValue(this, $$delegatedProperties[27]);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setValueParametersHandler(DescriptorRenderer.ValueParametersHandler valueParametersHandler) {
        Intrinsics.checkNotNullParameter(valueParametersHandler, "<set-?>");
        this.valueParametersHandler.setValue(this, $$delegatedProperties[27], valueParametersHandler);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public RenderingFormat getTextFormat() {
        return (RenderingFormat) this.textFormat.getValue(this, $$delegatedProperties[28]);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setTextFormat(RenderingFormat renderingFormat) {
        Intrinsics.checkNotNullParameter(renderingFormat, "<set-?>");
        this.textFormat.setValue(this, $$delegatedProperties[28], renderingFormat);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public ParameterNameRenderingPolicy getParameterNameRenderingPolicy() {
        return (ParameterNameRenderingPolicy) this.parameterNameRenderingPolicy.getValue(this, $$delegatedProperties[29]);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setParameterNameRenderingPolicy(ParameterNameRenderingPolicy parameterNameRenderingPolicy) {
        Intrinsics.checkNotNullParameter(parameterNameRenderingPolicy, "<set-?>");
        this.parameterNameRenderingPolicy.setValue(this, $$delegatedProperties[29], parameterNameRenderingPolicy);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getReceiverAfterName() {
        return ((Boolean) this.receiverAfterName.getValue(this, $$delegatedProperties[30])).booleanValue();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setReceiverAfterName(boolean z) {
        this.receiverAfterName.setValue(this, $$delegatedProperties[30], Boolean.valueOf(z));
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getRenderCompanionObjectName() {
        return ((Boolean) this.renderCompanionObjectName.getValue(this, $$delegatedProperties[31])).booleanValue();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setRenderCompanionObjectName(boolean z) {
        this.renderCompanionObjectName.setValue(this, $$delegatedProperties[31], Boolean.valueOf(z));
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public PropertyAccessorRenderingPolicy getPropertyAccessorRenderingPolicy() {
        return (PropertyAccessorRenderingPolicy) this.propertyAccessorRenderingPolicy.getValue(this, $$delegatedProperties[32]);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setPropertyAccessorRenderingPolicy(PropertyAccessorRenderingPolicy propertyAccessorRenderingPolicy) {
        Intrinsics.checkNotNullParameter(propertyAccessorRenderingPolicy, "<set-?>");
        this.propertyAccessorRenderingPolicy.setValue(this, $$delegatedProperties[32], propertyAccessorRenderingPolicy);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getRenderDefaultAnnotationArguments() {
        return ((Boolean) this.renderDefaultAnnotationArguments.getValue(this, $$delegatedProperties[33])).booleanValue();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setRenderDefaultAnnotationArguments(boolean z) {
        this.renderDefaultAnnotationArguments.setValue(this, $$delegatedProperties[33], Boolean.valueOf(z));
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getEachAnnotationOnNewLine() {
        return ((Boolean) this.eachAnnotationOnNewLine.getValue(this, $$delegatedProperties[34])).booleanValue();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setEachAnnotationOnNewLine(boolean z) {
        this.eachAnnotationOnNewLine.setValue(this, $$delegatedProperties[34], Boolean.valueOf(z));
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public Set<FqName> getExcludedAnnotationClasses() {
        return (Set) this.excludedAnnotationClasses.getValue(this, $$delegatedProperties[35]);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setExcludedAnnotationClasses(Set<FqName> set) {
        Intrinsics.checkNotNullParameter(set, "<set-?>");
        this.excludedAnnotationClasses.setValue(this, $$delegatedProperties[35], set);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public Set<FqName> getExcludedTypeAnnotationClasses() {
        return (Set) this.excludedTypeAnnotationClasses.getValue(this, $$delegatedProperties[36]);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setExcludedTypeAnnotationClasses(Set<FqName> set) {
        Intrinsics.checkNotNullParameter(set, "<set-?>");
        this.excludedTypeAnnotationClasses.setValue(this, $$delegatedProperties[36], set);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public Function1<AnnotationDescriptor, Boolean> getAnnotationFilter() {
        return (Function1) this.annotationFilter.getValue(this, $$delegatedProperties[37]);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setAnnotationFilter(Function1<? super AnnotationDescriptor, Boolean> function1) {
        this.annotationFilter.setValue(this, $$delegatedProperties[37], function1);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public AnnotationArgumentsRenderingPolicy getAnnotationArgumentsRenderingPolicy() {
        return (AnnotationArgumentsRenderingPolicy) this.annotationArgumentsRenderingPolicy.getValue(this, $$delegatedProperties[38]);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setAnnotationArgumentsRenderingPolicy(AnnotationArgumentsRenderingPolicy annotationArgumentsRenderingPolicy) {
        Intrinsics.checkNotNullParameter(annotationArgumentsRenderingPolicy, "<set-?>");
        this.annotationArgumentsRenderingPolicy.setValue(this, $$delegatedProperties[38], annotationArgumentsRenderingPolicy);
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getAlwaysRenderModifiers() {
        return ((Boolean) this.alwaysRenderModifiers.getValue(this, $$delegatedProperties[39])).booleanValue();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setAlwaysRenderModifiers(boolean z) {
        this.alwaysRenderModifiers.setValue(this, $$delegatedProperties[39], Boolean.valueOf(z));
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getRenderConstructorKeyword() {
        return ((Boolean) this.renderConstructorKeyword.getValue(this, $$delegatedProperties[40])).booleanValue();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setRenderConstructorKeyword(boolean z) {
        this.renderConstructorKeyword.setValue(this, $$delegatedProperties[40], Boolean.valueOf(z));
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getRenderUnabbreviatedType() {
        return ((Boolean) this.renderUnabbreviatedType.getValue(this, $$delegatedProperties[41])).booleanValue();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setRenderUnabbreviatedType(boolean z) {
        this.renderUnabbreviatedType.setValue(this, $$delegatedProperties[41], Boolean.valueOf(z));
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getRenderTypeExpansions() {
        return ((Boolean) this.renderTypeExpansions.getValue(this, $$delegatedProperties[42])).booleanValue();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setRenderTypeExpansions(boolean z) {
        this.renderTypeExpansions.setValue(this, $$delegatedProperties[42], Boolean.valueOf(z));
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getIncludeAdditionalModifiers() {
        return ((Boolean) this.includeAdditionalModifiers.getValue(this, $$delegatedProperties[43])).booleanValue();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setIncludeAdditionalModifiers(boolean z) {
        this.includeAdditionalModifiers.setValue(this, $$delegatedProperties[43], Boolean.valueOf(z));
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getParameterNamesInFunctionalTypes() {
        return ((Boolean) this.parameterNamesInFunctionalTypes.getValue(this, $$delegatedProperties[44])).booleanValue();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setParameterNamesInFunctionalTypes(boolean z) {
        this.parameterNamesInFunctionalTypes.setValue(this, $$delegatedProperties[44], Boolean.valueOf(z));
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getRenderFunctionContracts() {
        return ((Boolean) this.renderFunctionContracts.getValue(this, $$delegatedProperties[45])).booleanValue();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setRenderFunctionContracts(boolean z) {
        this.renderFunctionContracts.setValue(this, $$delegatedProperties[45], Boolean.valueOf(z));
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getPresentableUnresolvedTypes() {
        return ((Boolean) this.presentableUnresolvedTypes.getValue(this, $$delegatedProperties[46])).booleanValue();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setPresentableUnresolvedTypes(boolean z) {
        this.presentableUnresolvedTypes.setValue(this, $$delegatedProperties[46], Boolean.valueOf(z));
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getBoldOnlyForNamesInHtml() {
        return ((Boolean) this.boldOnlyForNamesInHtml.getValue(this, $$delegatedProperties[47])).booleanValue();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setBoldOnlyForNamesInHtml(boolean z) {
        this.boldOnlyForNamesInHtml.setValue(this, $$delegatedProperties[47], Boolean.valueOf(z));
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public boolean getInformativeErrorType() {
        return ((Boolean) this.informativeErrorType.getValue(this, $$delegatedProperties[48])).booleanValue();
    }

    @Override // org.jetbrains.kotlin.renderer.DescriptorRendererOptions
    public void setInformativeErrorType(boolean z) {
        this.informativeErrorType.setValue(this, $$delegatedProperties[48], Boolean.valueOf(z));
    }
}
