package org.catrobat.catroid.io;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.reflection.ReflectionConverter;
import com.thoughtworks.xstream.converters.reflection.ReflectionProvider;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.formulaeditor.FormulaElement;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/io/XStreamFormulaElementConverter.class
  classes14.dex
 */
/* JADX INFO: compiled from: XStreamFormulaElementConverter.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u0019\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u0014\u0010\u0007\u001a\u00020\b2\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\nH\u0016J \u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0014J\"\u0010\u0013\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u0014\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0011\u001a\u00020\u0017H\u0016J\u0018\u0010\u0018\u001a\n \u001a*\u0004\u0018\u00010\u00190\u00192\u0006\u0010\u001b\u001a\u00020\u001cH\u0002¨\u0006\u001d"}, d2 = {"Lorg/catrobat/catroid/io/XStreamFormulaElementConverter;", "Lcom/thoughtworks/xstream/converters/reflection/ReflectionConverter;", "mapper", "Lcom/thoughtworks/xstream/mapper/Mapper;", "reflectionProvider", "Lcom/thoughtworks/xstream/converters/reflection/ReflectionProvider;", "(Lcom/thoughtworks/xstream/mapper/Mapper;Lcom/thoughtworks/xstream/converters/reflection/ReflectionProvider;)V", "canConvert", "", ModuleXmlParser.TYPE, "Ljava/lang/Class;", "doMarshal", "", FirebaseAnalytics.Param.SOURCE, "", "writer", "Lcom/thoughtworks/xstream/io/HierarchicalStreamWriter;", "context", "Lcom/thoughtworks/xstream/converters/MarshallingContext;", "doUnmarshal", "result", "reader", "Lcom/thoughtworks/xstream/io/HierarchicalStreamReader;", "Lcom/thoughtworks/xstream/converters/UnmarshallingContext;", "replaceOldSensorNames", "", JvmProtoBufUtil.PLATFORM_TYPE_ID, "formulaElement", "Lorg/catrobat/catroid/formulaeditor/FormulaElement;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class XStreamFormulaElementConverter extends ReflectionConverter {
    public XStreamFormulaElementConverter(Mapper mapper, ReflectionProvider reflectionProvider) {
        super(mapper, reflectionProvider);
    }

    @Override // com.thoughtworks.xstream.converters.reflection.ReflectionConverter, com.thoughtworks.xstream.converters.ConverterMatcher
    public boolean canConvert(Class<?> type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return Intrinsics.areEqual(FormulaElement.class, type);
    }

    @Override // com.thoughtworks.xstream.converters.reflection.AbstractReflectionConverter
    protected void doMarshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(writer, "writer");
        Intrinsics.checkNotNullParameter(context, "context");
        super.doMarshal(source, writer, context);
    }

    @Override // com.thoughtworks.xstream.converters.reflection.AbstractReflectionConverter
    public Object doUnmarshal(Object result, HierarchicalStreamReader reader, UnmarshallingContext context) {
        Intrinsics.checkNotNullParameter(result, "result");
        Intrinsics.checkNotNullParameter(reader, "reader");
        Intrinsics.checkNotNullParameter(context, "context");
        Object objDoUnmarshal = super.doUnmarshal(result, reader, context);
        if (!(objDoUnmarshal instanceof FormulaElement)) {
            return objDoUnmarshal;
        }
        FormulaElement formulaElement = (FormulaElement) objDoUnmarshal;
        if (formulaElement.getElementType() != FormulaElement.ElementType.SENSOR) {
            return objDoUnmarshal;
        }
        formulaElement.setValue(replaceOldSensorNames(formulaElement));
        return objDoUnmarshal;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private final String replaceOldSensorNames(FormulaElement formulaElement) {
        String value = formulaElement.getValue();
        if (value != null) {
            switch (value.hashCode()) {
                case -2004290195:
                    if (value.equals("RIGHT_PINKY_KNUCKLE_X")) {
                        return "RIGHT_PINKY_X";
                    }
                    break;
                case -2004290194:
                    if (value.equals("RIGHT_PINKY_KNUCKLE_Y")) {
                        return "RIGHT_PINKY_Y";
                    }
                    break;
                case -873228853:
                    if (value.equals("LEFT_THUMB_KNUCKLE_X")) {
                        return "LEFT_THUMB_X";
                    }
                    break;
                case -873228852:
                    if (value.equals("LEFT_THUMB_KNUCKLE_Y")) {
                        return "LEFT_THUMB_Y";
                    }
                    break;
                case -232816185:
                    if (value.equals("LEFT_INDEX_KNUCKLE_X")) {
                        return "LEFT_INDEX_X";
                    }
                    break;
                case -232816184:
                    if (value.equals("LEFT_INDEX_KNUCKLE_Y")) {
                        return "LEFT_INDEX_Y";
                    }
                    break;
                case -109057679:
                    if (value.equals("FACE_Y_POSITION")) {
                        return "FACE_Y";
                    }
                    break;
                case 87455826:
                    if (value.equals("FACE_X_POSITION")) {
                        return "FACE_X";
                    }
                    break;
                case 301419896:
                    if (value.equals("LEFT_PINKY_KNUCKLE_X")) {
                        return "LEFT_PINKY_X";
                    }
                    break;
                case 301419897:
                    if (value.equals("LEFT_PINKY_KNUCKLE_Y")) {
                        return "LEFT_PINKY_Y";
                    }
                    break;
                case 431493030:
                    if (value.equals("SECOND_FACE_Y_POSITION")) {
                        return "FACE_Y";
                    }
                    break;
                case 628006535:
                    if (value.equals("SECOND_FACE_X_POSITION")) {
                        return "SECOND_FACE_X";
                    }
                    break;
                case 1116028352:
                    if (value.equals("RIGHT_THUMB_KNUCKLE_X")) {
                        return "RIGHT_THUMB_X";
                    }
                    break;
                case 1116028353:
                    if (value.equals("RIGHT_THUMB_KNUCKLE_Y")) {
                        return "RIGHT_THUMB_Y";
                    }
                    break;
                case 1756441020:
                    if (value.equals("RIGHT_INDEX_KNUCKLE_X")) {
                        return "RIGHT_INDEX_X";
                    }
                    break;
                case 1756441021:
                    if (value.equals("RIGHT_INDEX_KNUCKLE_Y")) {
                        return "RIGHT_INDEX_Y";
                    }
                    break;
            }
        }
        return formulaElement.getValue();
    }
}
