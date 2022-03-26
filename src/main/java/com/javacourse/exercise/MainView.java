package com.javacourse.exercise;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
//import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.util.StringUtils;

@Route
public class MainView extends VerticalLayout {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final StudentCourseRepository scRepository;

    private final StudentEditor studentEditor;
    private final CourseEditor courseEditor;

    final Grid<Student> studentGrid;
    final Grid<Course> courseGrid;

    final TextField studentFilter;
    final TextField courseFilter;

    private final Button addStudentBtn;
    private final Button addCourseBtn;
    private final Button saveToFileBtn;

    public MainView(StudentRepository studentRepository, StudentCourseRepository scRepository, StudentEditor studentEditor, CourseRepository courseRepository, CourseEditor courseEditor)
    {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.scRepository = scRepository;
        this.studentEditor = studentEditor;
        this.courseEditor = courseEditor;
        this.studentGrid = new Grid<>(Student.class);
        this.courseGrid = new Grid<>(Course.class);
        this.studentFilter = new TextField();
        this.courseFilter = new TextField();
        this.addStudentBtn = new Button("New student", VaadinIcon.PLUS.create());
        this.addCourseBtn = new Button("New course", VaadinIcon.PLUS.create());
        this.saveToFileBtn = new Button("Save to file", VaadinIcon.ARCHIVE.create());

        HorizontalLayout studentActions = new HorizontalLayout(studentFilter, addStudentBtn, saveToFileBtn);
        HorizontalLayout courseActions = new HorizontalLayout(courseFilter, addCourseBtn);
        add(studentActions, studentGrid, courseActions, courseGrid, studentEditor, courseEditor);

        studentGrid.setHeight("200px");
        studentGrid.setColumns("id", "firstName", "lastName", "address", "groupCode");
        studentGrid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

        courseGrid.setHeight("200px");
        courseGrid.setColumns("courseId", "name", "credit", "type");
        courseGrid.getColumnByKey("courseId").setWidth("50px").setFlexGrow(0);

        studentFilter.setPlaceholder("Filter students by last name");
        courseFilter.setPlaceholder("Filter courses by name");
        
        studentFilter.setValueChangeMode(ValueChangeMode.EAGER);
        studentFilter.addValueChangeListener(e -> listStudents(e.getValue()));

        courseFilter.setValueChangeMode(ValueChangeMode.EAGER);
        courseFilter.addValueChangeListener(e -> listCourses(e.getValue()));

        studentGrid.asSingleSelect().addValueChangeListener(e -> {editStudent(e.getValue());
        });

        courseGrid.asSingleSelect().addValueChangeListener(e -> {editCourse(e.getValue());
        });

        addStudentBtn.addClickListener(e -> editStudent(new Student("", "", "", "")));
        addCourseBtn.addClickListener(e -> editCourse(new Course()));
        saveToFileBtn.addClickListener(e -> saveToFile());


        studentEditor.setChangeHandler(() -> {studentEditor.setVisible(false);
        listStudents(studentFilter.getValue());
    });
        courseEditor.setChangeHandler(() -> {courseEditor.setVisible(false);
        listCourses(courseFilter.getValue());
        });
        listStudents(null);
        listCourses(null);
    }
    
    private void listStudents(String filterText)
    {
        if (StringUtils.isEmpty(filterText))
        {
            studentGrid.setItems(studentRepository.findAll());
        }
        else
        {
            studentGrid.setItems(studentRepository.findByLastNameStartsWithIgnoreCase(filterText));
        }
    }

    private void listCourses(String filterText)
    {
        if (StringUtils.isEmpty(filterText))
        {
            courseGrid.setItems(courseRepository.findAll());
        }
        else
        {
            courseGrid.setItems(courseRepository.findByNameStartsWithIgnoreCase(filterText));
        }
    }

    private void editCourse(Course course)
    {
        studentEditor.setVisible(false);
        courseEditor.editCourse(course);
    }

    private void editStudent(Student student)
    {
        courseEditor.setVisible(false);
        studentEditor.editStudent(student);
    }

    private void saveToFile()
    {
        FileController fileController = new FileController(courseRepository, studentRepository, scRepository);

        fileController.saveToFile();
    }
    
}
