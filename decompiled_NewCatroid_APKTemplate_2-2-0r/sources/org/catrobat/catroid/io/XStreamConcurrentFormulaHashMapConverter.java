package org.catrobat.catroid.io;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.ConcurrentFormulaHashMap;
import org.catrobat.catroid.formulaeditor.Formula;
import org.catrobat.catroid.formulaeditor.FormulaElement;
import org.catrobat.catroid.userbrick.InputFormulaField;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/io/XStreamConcurrentFormulaHashMapConverter.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class XStreamConcurrentFormulaHashMapConverter implements Converter {
    private static final String CATEGORY = "category";
    private static final String FORMULA = "formula";
    private static final String USER_DEFINED_INPUT = "input";

    @Override // com.thoughtworks.xstream.converters.ConverterMatcher
    public boolean canConvert(Class type) {
        return type.equals(ConcurrentFormulaHashMap.class);
    }

    @Override // com.thoughtworks.xstream.converters.Converter
    public void marshal(Object object, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext) {
        String str;
        ConcurrentFormulaHashMap concurrentFormulaHashMap = (ConcurrentFormulaHashMap) object;
        for (Brick.FormulaField formulaField : concurrentFormulaHashMap.keySet()) {
            hierarchicalStreamWriter.startNode(FORMULA);
            if (formulaField instanceof InputFormulaField) {
                str = USER_DEFINED_INPUT;
            } else {
                str = CATEGORY;
            }
            hierarchicalStreamWriter.addAttribute(str, formulaField.toString());
            marshallingContext.convertAnother(concurrentFormulaHashMap.get(formulaField).getRoot());
            hierarchicalStreamWriter.endNode();
        }
    }

    @Override // com.thoughtworks.xstream.converters.Converter
    public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext) {
        Object inputFormulaField;
        Formula formula;
        ConcurrentFormulaHashMap concurrentFormulaHashMap = new ConcurrentFormulaHashMap();
        while (hierarchicalStreamReader.hasMoreChildren()) {
            hierarchicalStreamReader.moveDown();
            if (hierarchicalStreamReader.getAttribute(CATEGORY) != null) {
                inputFormulaField = Brick.BrickField.valueOf(hierarchicalStreamReader.getAttribute(CATEGORY));
            } else {
                inputFormulaField = new InputFormulaField(hierarchicalStreamReader.getAttribute(USER_DEFINED_INPUT));
            }
            if (FORMULA.equals(hierarchicalStreamReader.getNodeName())) {
                formula = new Formula((FormulaElement) unmarshallingContext.convertAnother(concurrentFormulaHashMap, FormulaElement.class));
            } else {
                formula = new Formula((Integer) 0);
            }
            hierarchicalStreamReader.moveUp();
            concurrentFormulaHashMap.putIfAbsent(inputFormulaField, formula);
        }
        return concurrentFormulaHashMap;
    }
}
