package com.nttdata.esercitazione.controller;

import com.nttdata.esercitazione.course.Course;
import com.nttdata.esercitazione.repository.CourseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RestController
public class CourseController {
    final CourseRepository courseRepository;

    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @PostMapping("/postcorso")
    public ResponseEntity<Course> create (@RequestBody Course corso){
        Course corso1 = courseRepository.save(corso);
        return new ResponseEntity<> (corso1, HttpStatus.CREATED);
    }

    @GetMapping("/corso")
    public ResponseEntity<List<Course>> findAll(){
        List<Course> corsoList= courseRepository.findAll();
        return new ResponseEntity<>(corsoList, HttpStatus.OK);
    }

    @DeleteMapping("/deletecorso/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable long id) {
        System.out.println("Corso: " + id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/addcorso")
    public ResponseEntity<Course> getCourse() {
        Course corso = new Course(3L,"Matematica","Matematica primo anno");
        Course corso1 = courseRepository.save(corso);
        return new ResponseEntity<>(corso1, HttpStatus.OK);
    }
    @PutMapping("/putcorso/{id}")
    public ResponseEntity<?> updateCorso(@PathVariable("id") long id, @RequestBody Course corso) {
        Course corso1 = new Course(4L, "Filosofia", "Da Aristotele a Platone");
        if (corso1.getId() == id) return new ResponseEntity<>(corso1, HttpStatus.CREATED);
        return new ResponseEntity<>(corso, HttpStatus.NO_CONTENT);
    }

    @PostMapping("/corsodupicato") //Slide2
    public ResponseEntity<?> createCorso(@RequestBody Course corso) {
        List<Course> corsi = courseRepository.findAll();
        HashMap<String, String> hash = new HashMap<>();
        hash.put("Error", "duplicate");
        for (Course corso2 : corsi) {
            if (Objects.equals(corso2.getId(), corso.getId())) {
                return new ResponseEntity<>(hash, HttpStatus.OK);
            }
        }
        courseRepository.save(corso);
        return new ResponseEntity<>(corso, HttpStatus.CREATED);
    }

    @PostMapping("/corso8c") //Slide3
    public ResponseEntity<?> creaCorso1(@RequestBody Course corso) {
        if (corso.getNomeCorso().contains("corso_") && corso.getNomeCorso().length() > 8) {
            return new ResponseEntity<>(corso, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(corso, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/getCorso3/{id}/{campo}") //Side 4 (seleziona singolarmente il corso)
    public ResponseEntity<?> findOneCampo(@PathVariable long id, @PathVariable String campo) {
        final Course[] c = new Course[1];
        courseRepository.findAll().forEach(course -> {
            if (course.getId() == id) {
                c[0] = course;
            }
        });
        if (c[0] == null) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>((campo.equalsIgnoreCase("descrizione"))
                ? c[0].getDescrizione() : c[0].getNomeCorso(), HttpStatus.OK);
    }

    @GetMapping("/getCorso3/{id}") //Side 4 (seleziona singolarmente i campi del corso)
    public ResponseEntity<?> findOne(@PathVariable long id) {
        final Course[] c = new Course[1];
        courseRepository.findAll().forEach(course -> {
            if (course.getId() == id) {
                c[0] = course;
            }
        });
        if (c[0] == null) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(c[0], HttpStatus.OK);
    }
    @GetMapping("/contacorsi") //SLide 5
    public ResponseEntity<?> contaCorso (){
        return new ResponseEntity<>(courseRepository.findAll().size(), HttpStatus.OK);
    }

    @GetMapping("/NumeroCorsi_") //Slide6
    public ResponseEntity<?> getNumeroCorsi_() {
        int i = 0;
        for (Course course : courseRepository.findAll())
            if (course.getNomeCorso().toLowerCase().contains("corso_")) i++;
        return new ResponseEntity<>(i, HttpStatus.OK);
    }

    @GetMapping("/Corsi20C") //Slide 7
    public ResponseEntity<?> Corsi20C() {
        List<Course> list = new ArrayList<>();
        for (Course course : courseRepository.findAll())
            if (course.getDescrizione() != null && course.getDescrizione().length() < 20) list.add(course);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
