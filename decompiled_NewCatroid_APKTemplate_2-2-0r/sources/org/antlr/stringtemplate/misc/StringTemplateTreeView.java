package org.antlr.stringtemplate.misc;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;

/* JADX INFO: loaded from: classes12.dex */
public class StringTemplateTreeView extends JFrame {
    static final int HEIGHT = 300;
    static final int WIDTH = 200;

    public StringTemplateTreeView(String str, StringTemplate stringTemplate) {
        super(str);
        getContentPane().add(new JTreeStringTemplatePanel(new JTreeStringTemplateModel(stringTemplate), null), "Center");
        addWindowListener(new WindowAdapter() { // from class: org.antlr.stringtemplate.misc.StringTemplateTreeView.1
            public void windowClosing(WindowEvent windowEvent) {
                Frame frame = (Frame) windowEvent.getSource();
                frame.setVisible(false);
                frame.dispose();
            }
        });
        setSize(200, 300);
    }

    public static void main(String[] strArr) {
        StringTemplateGroup stringTemplateGroup = new StringTemplateGroup("dummy");
        StringTemplate stringTemplateDefineTemplate = stringTemplateGroup.defineTemplate("bold", "<b>$attr$</b>");
        stringTemplateGroup.defineTemplate("banner", "the banner");
        StringTemplate stringTemplate = new StringTemplate(stringTemplateGroup, "<html>\n$banner(a=b)$<p><b>$name$:$email$</b>$if(member)$<i>$fontTag$member</font></i>$endif$");
        stringTemplate.setAttribute("name", "Terence");
        stringTemplate.setAttribute("name", "Tom");
        stringTemplate.setAttribute("email", "parrt@cs.usfca.edu");
        stringTemplate.setAttribute("templateAttr", stringTemplateDefineTemplate);
        new StringTemplateTreeView("StringTemplate JTree Example", stringTemplate).setVisible(true);
    }
}
