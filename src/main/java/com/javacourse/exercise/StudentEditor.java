package com.javacourse.exercise;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
public class StudentEditor extends VerticalLayout implements KeyNotifier {

    private final StudentRepository repository;

    private Student student;

    TextField firstName = new TextField("First name");
    TextField lastName = new TextField("Last name");
    TextField address = new TextField("Address");
    TextField groupCode = new TextField("Groupcode");

    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    Binder<Student> binder = new Binder<>(Student.class);
    private ChangeHandler changeHandler;

    @Autowired
    public StudentEditor(StudentRepository repository)
    {
        this.repository = repository;
        
        add(firstName, lastName, address, groupCode, actions);

        binder.bindInstanceFields(this);

        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> editStudent(student));
        setVisible(false);
    }

    void delete()
    {
        repository.delete(student);
        changeHandler.onChange();
    }

    void save()
    {
        repository.save(student);
        changeHandler.onChange();
    }

    public interface ChangeHandler
    {
        void onChange();
    }

    public final void editStudent(Student s)
    {
        if (s == null)
        {
            setVisible(false);
            return;
        }
        final boolean persisted = s.getId() != null;
        if (persisted)
        {
            student = repository.findById(s.getId()).get();
        }
        else
        {
            student = s;
        }
        cancel.setVisible(persisted);

        binder.setBean(student);

        setVisible(true);

        firstName.focus();
    }

    public void setChangeHandler(ChangeHandler h)
    {
        changeHandler = h;
    }
}
