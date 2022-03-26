package com.javacourse.exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.gatanaso.MultiselectComboBox;

@SpringComponent
@UIScope
public class CourseEditor extends VerticalLayout implements KeyNotifier {
    
    private final CourseRepository repository;
    private final StudentCourseRepository scRepository;
    private final StudentRepository studentRepository;

    private Course course;

    TextField name = new TextField("Name");
    TextField credit = new TextField("Credit");
    Select<String> type = new Select<>("Type");
    MultiselectComboBox<Student> studentComboBox = new MultiselectComboBox();

    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    Binder<Course> binder = new Binder<>(Course.class);
    private ChangeHandler changeHandler;

    @Autowired
    public CourseEditor(CourseRepository repository, StudentRepository studentRepository, StudentCourseRepository scRepository)
    {
        this.repository = repository;
        this.studentRepository = studentRepository;
        this.scRepository = scRepository;

        

        type.setLabel("Select type");
        type.setItems("OnlineCourse", "ClassRoomCourse");

        studentComboBox.setLabel("Select students");
       


        binder.forField(credit)
                .withConverter(new StringToIntegerConverter(""))
                //.withConverter(Integer::valueOf, String::valueOf)
                .bind(Course::getCredit, Course::setCredit);

        //add(name, credit, type, actions, typeSelect);
        add(name, credit, type, studentComboBox, actions);

        binder.bindInstanceFields(this);

        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> editCourse(course));
        setVisible(false);
    }

    void delete()
    {
        if (course != null && course.getCourseId() != null && course.getCourseId() > 0)
        {
            List<StudentCourse> scList = scRepository.findByCourseId(course.getCourseId());

            for (int i = 0; i < scList.size(); i++)
            {
                StudentCourse sc = scList.get(i);
                scRepository.delete(sc);
            }
        }

        repository.delete(course);
        changeHandler.onChange();
    }

    void save()
    {
        course = repository.save(course);
        
        if (course != null && course.getCourseId() != null && course.getCourseId() > 0)
        {
            List<StudentCourse> scList = scRepository.findByCourseId(course.getCourseId());

            for (int i = 0; i < scList.size(); i++)
            {
                StudentCourse sc = scList.get(i);
                scRepository.delete(sc);
            }

            Set<Student> selectedStudents = studentComboBox.getSelectedItems();
            List<Student> selectedStudentList = new ArrayList<>(selectedStudents);
            
            for (int i = 0; i < selectedStudentList.size(); i++)
            {
                Student selectedStudent = selectedStudentList.get(i);
                scRepository.save(new StudentCourse(selectedStudent.getId(), course.getCourseId()));
            }

        }
        changeHandler.onChange();
    }

    public interface ChangeHandler
    {
        void onChange();
    }

    public final void editCourse(Course c)
    {
        if (c == null)
        {
            setVisible(false);
            return;
        }
        final boolean persisted = c.getCourseId() != null;
        if (persisted)
        {
            course = repository.findById(c.getCourseId()).get();
        }
        else
        {
            course = c;
        }

        studentComboBox.deselectAll();
        List<Student> students = studentRepository.findAll();
        studentComboBox.setItems(students);

        if (course != null && course.getCourseId() != null && course.getCourseId() > 0)
        {
            //List<StudentCourse> asd = scRepository.findAll();
            List<StudentCourse> scList = scRepository.findByCourseId(course.getCourseId());

            for (int i = 0; i < students.size(); i++)
            {
                Student student = students.get(i);
                StudentCourse sc = scList.stream().filter(x -> x.getStudentId() == student.getId()).findAny().orElse(null);
                
                if (sc != null)
                {
                    studentComboBox.select(student);
                }
            }

        }

        cancel.setVisible(persisted);

        binder.setBean(course);

        setVisible(true);

        name.focus();
    }

    public void setChangeHandler(ChangeHandler h)
    {
        changeHandler = h;
    }
}
