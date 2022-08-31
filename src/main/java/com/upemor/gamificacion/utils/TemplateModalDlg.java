package com.upemor.gamificacion.utils;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.upemor.gamificacion.MainUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.apache.poi.ss.formula.functions.T;

/** @author cerimice **/
/** @company tiamex **/

public abstract class TemplateModalDlg extends Panel{
    
    protected MainUI ui;
    protected long id;
    
    protected VerticalLayout contentLayout;
    protected VerticalLayout main;
    protected Button delete;
    protected Button accept;
    protected Button cancel;
    
    public TemplateModalDlg(){
        initDlg();
    }
    
    protected Grid<T> grid;
    
    private void initDlg(){
        ui = Element.getUI();
        ResponsiveLayout actions = new ResponsiveLayout();
        
        delete = new Button("templateModalDlg.buttonDelete");
            Element.cfgComponent(delete);
            delete.setStyleName(ValoTheme.BUTTON_FRIENDLY);
            delete.setVisible(false);
        accept = new Button("buttonAccept");
            Element.cfgComponent(accept);
            accept.addClickListener((Button.ClickEvent event) -> {buttonAcceptEvent();});
            accept.setStyleName(ValoTheme.BUTTON_PRIMARY);
        cancel = new Button(".buttonCancel");
            Element.cfgComponent(cancel);
            cancel.addClickListener((Button.ClickEvent event) -> {buttonCancelEvent();});
            cancel.setStyleName(ValoTheme.BUTTON_DANGER);
        ResponsiveRow row1 = actions.addRow().withAlignment(Alignment.BOTTOM_RIGHT);
            Element.cfgLayoutComponent(row1,true,true);
            row1.addColumn().withDisplayRules(12,2,2,2).withComponent(delete);
            row1.addColumn().withDisplayRules(12,2,2,2).withComponent(cancel);
            row1.addColumn().withDisplayRules(12,2,2,2).withComponent(accept);
        
        contentLayout = new VerticalLayout();
            Element.cfgLayoutComponent(contentLayout,false,false);
        
        main = new VerticalLayout();
            Element.cfgLayoutComponent(main,true,true);
            main.addComponent(contentLayout);
            main.addComponent(actions);
        
        this.setContent(main);
        this.setCaptionAsHtml(true);
        this.setHeight(Element.windowHeightPx());
    }
    
    public void updateDlg(){
        buttonSearchEvent();
        grid.recalculateColumnWidths();
    }
    
    protected abstract void buttonSearchEvent();
    protected abstract void buttonCancelEvent();
    protected abstract void loadData(long id);
    protected abstract void buttonAcceptEvent();
}
