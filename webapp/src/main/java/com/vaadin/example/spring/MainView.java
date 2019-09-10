package com.vaadin.example.spring;
import com.vaadin.example.spring.customer.Customer;
import com.vaadin.example.spring.customer.CustomerService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;

@Route("")
@PWA(name = "Project Base for Vaadin Flow with Spring", shortName = "Project Base")
public class MainView extends VerticalLayout {

    private CustomerService service = CustomerService.getInstance();
    private Grid<Customer> grid = new Grid<Customer>(Customer.class);
    private TextField filterText = new TextField();
    public MainView(@Autowired MessageBean bean) {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.EAGER);
        filterText.addValueChangeListener(e -> updateList());

        grid.setColumns("firstName", "lastName", "status");
        grid.setItems(service.findAll());

        Button button = new Button("Click me",
                e -> Notification.show(bean.getMessage()));

        add(filterText, grid,button);

        setSizeFull();
    }

    public void updateList() {
        grid.setItems(service.findAll(filterText.getValue()));
    }

}
