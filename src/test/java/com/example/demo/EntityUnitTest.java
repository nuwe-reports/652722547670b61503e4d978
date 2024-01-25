package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.example.demo.controllers.DoctorController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import com.example.demo.entities.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@TestInstance(Lifecycle.PER_CLASS)
class EntityUnitTest {

	@Autowired
	private TestEntityManager entityManager;

	private Doctor d1;

	private Patient p1;

    private Room r1;

    private Appointment a1;
    private Appointment a2;
    private Appointment a3;



    @Test
    void shouldSetAttributesFromSuperClass(){
        d1 = new Doctor();
        d1.setFirstName("Juan");
        d1.setLastName("Persico");
        d1.setEmail("juanp@gmail.com");
        d1.setAge(22);

        assertThat(d1).isNotNull()
                .hasFieldOrPropertyWithValue("firstName","Juan")
                .hasFieldOrPropertyWithValue("lastName","Persico")
                .hasFieldOrPropertyWithValue("age",22)
                .hasFieldOrPropertyWithValue("email","juanp@gmail.com");
    }

    @Test
    void shouldCreateDoctorWithEmptyConstructor(){
        d1 = new Doctor();

        assertThat(d1).isNotNull();
    }


    @Test
    void shouldCreateDoctorWithFullConstructor(){
        d1 = new Doctor("Juan","Persico",22,"juanp@gmail.com");


        assertThat(d1).isNotNull()
                .hasFieldOrPropertyWithValue("firstName","Juan")
                .hasFieldOrPropertyWithValue("lastName","Persico")
                .hasFieldOrPropertyWithValue("age",22)
                .hasFieldOrPropertyWithValue("email","juanp@gmail.com");
    }

    @Test
    void shouldSetDoctorId(){
        d1 = new Doctor();
        d1.setId(1);

        assertThat(d1).hasFieldOrPropertyWithValue("id",1L);
    }

    @Test
    void shouldGetDoctorId(){
        d1 = new Doctor();
        entityManager.persist(d1);

        assertThat(d1).hasFieldOrPropertyWithValue("id",d1.getId());
    }

    @Test
    void shouldCreatePatientWithEmptyConstructor(){
        p1 = new Patient();

        assertThat(p1).isNotNull();
    }


    @Test
    void shouldCreatePatientWithFullConstructor(){
        p1 = new Patient("Maria","Ramos",17,"mramos@gmail.com");


        assertThat(p1).isNotNull()
                .hasFieldOrPropertyWithValue("firstName","Maria")
                .hasFieldOrPropertyWithValue("lastName","Ramos")
                .hasFieldOrPropertyWithValue("age",17)
                .hasFieldOrPropertyWithValue("email","mramos@gmail.com");
    }

    @Test
    void shouldSetPatientId(){
        p1 = new Patient();
        p1.setId(1);

        assertThat(p1).hasFieldOrPropertyWithValue("id",1L);
    }

    @Test
    void shouldGetPatientId(){
        p1 = new Patient();
        p1.setId(1);

        assertThat(p1).hasFieldOrPropertyWithValue("id",p1.getId());
    }

    @Test
    void shouldCreateRoomWithEmptyConstructor(){
        r1 = new Room();

        assertThat(p1).isNotNull();
    }


    @Test
    void shouldCreateRoomWithFullConstructor() {
        r1 = new Room("TITA");


        assertThat(r1).isNotNull()
                .hasFieldOrPropertyWithValue("roomName", "TITA");
    }

    @Test
    void shouldGetRoomName(){
        r1 = new Room("TITA");
        assertThat(r1.getRoomName()).isEqualTo("TITA");
    }

    @Test
    void shouldCreateAppointmentWithEmptyConstructor(){
        a1 = new Appointment();
        assertThat(a1).isNotNull();
    }


    @Test
    void shouldCreateAppointmentWithFullConstructor(){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

        LocalDateTime startDate = LocalDateTime.parse("09:30 13/09/2024",formatter);
        LocalDateTime endDate = LocalDateTime.parse("18:30 25/09/2024",formatter);

        r1 = new Room("TITA");
        p1 = new Patient("Maria","Ramos",17,"mramos@gmail.com");
        d1 = new Doctor("Juan","Persico",22,"juanp@gmail.com");

        a1= new Appointment(p1,d1,r1,startDate,endDate);
        a1.setId(1);

        assertThat(a1).isNotNull()
                .hasFieldOrPropertyWithValue("id",1L)
                .hasFieldOrPropertyWithValue("patient",p1)
                .hasFieldOrPropertyWithValue("doctor",d1)
                .hasFieldOrPropertyWithValue("room",r1)
                .hasFieldOrPropertyWithValue("startsAt",startDate)
                .hasFieldOrPropertyWithValue("finishesAt",endDate);
    }

    @Test
    void shouldSetAppointmentData(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

        LocalDateTime startDate = LocalDateTime.parse("09:30 13/09/2024",formatter);
        LocalDateTime endDate = LocalDateTime.parse("18:30 25/09/2024",formatter);

        r1 = new Room("TITA");
        p1 = new Patient("Maria","Ramos",17,"mramos@gmail.com");
        d1 = new Doctor("Juan","Persico",22,"juanp@gmail.com");

        a1= new Appointment();

        a1.setId(1);
        a1.setPatient(p1);
        a1.setDoctor(d1);
        a1.setRoom(r1);
        a1.setStartsAt(startDate);
        a1.setFinishesAt(endDate);

        assertThat(a1).isNotNull()
                .hasFieldOrPropertyWithValue("patient",p1)
                .hasFieldOrPropertyWithValue("doctor",d1)
                .hasFieldOrPropertyWithValue("room",r1)
                .hasFieldOrPropertyWithValue("startsAt",startDate)
                .hasFieldOrPropertyWithValue("finishesAt",endDate);
    }


    @Test
    void shouldReturnTrueIfBothAppointmentsStartAtTheSameTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

        LocalDateTime startDate = LocalDateTime.parse("09:30 13/09/2024",formatter);
        LocalDateTime endDate = LocalDateTime.parse("18:30 25/09/2024",formatter);

        LocalDateTime endDate2 = LocalDateTime.parse("18:30 29/09/2024",formatter);

        r1 = new Room("TITA");
        p1 = new Patient("Maria","Ramos",17,"mramos@gmail.com");
        d1 = new Doctor("Juan","Persico",22,"juanp@gmail.com");

        a1= new Appointment(p1,d1,r1,startDate,endDate);
        a2= new Appointment(p1,d1,r1,startDate,endDate2);


        assertThat(a1.overlaps(a2)).isTrue();
    }

    @Test
    void shouldOverlapIfBothAppointmentsEndAtTheSameTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

        LocalDateTime startDate = LocalDateTime.parse("09:30 13/09/2024",formatter);
        LocalDateTime endDate = LocalDateTime.parse("18:30 25/09/2024",formatter);

        LocalDateTime startDate2 = LocalDateTime.parse("09:30 11/09/2024",formatter);

        r1 = new Room("TITA");
        p1 = new Patient("Maria","Ramos",17,"mramos@gmail.com");
        d1 = new Doctor("Juan","Persico",22,"juanp@gmail.com");

        a1= new Appointment(p1,d1,r1,startDate,endDate);
        a2= new Appointment(p1,d1,r1,startDate2,endDate);


        assertThat(a1.overlaps(a2)).isTrue();
    }

    @Test
    void shouldOverlapIfBStartsBeforeAEnds(){

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

            LocalDateTime startDate = LocalDateTime.parse("09:30 13/09/2024",formatter);
            LocalDateTime endDate = LocalDateTime.parse("18:30 25/09/2024",formatter);

            LocalDateTime startDate2 = LocalDateTime.parse("09:30 24/09/2024",formatter);
            LocalDateTime endDate2 = LocalDateTime.parse("18:30 29/09/2024",formatter);

            r1 = new Room("TITA");
            p1 = new Patient("Maria","Ramos",17,"mramos@gmail.com");
            d1 = new Doctor("Juan","Persico",22,"juanp@gmail.com");

            a1= new Appointment(p1,d1,r1,startDate,endDate);
            a2= new Appointment(p1,d1,r1,startDate2,endDate2);


            assertThat(a1.overlaps(a2)).isTrue();
    }

    @Test
    void shouldOverlapIfBEndsBeforeAEnds(){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

        LocalDateTime startDate = LocalDateTime.parse("09:30 13/09/2024",formatter);
        LocalDateTime endDate = LocalDateTime.parse("18:30 25/09/2024",formatter);

        LocalDateTime startDate2 = LocalDateTime.parse("09:30 22/09/2024",formatter);
        LocalDateTime endDate2 = LocalDateTime.parse("18:30 24/09/2024",formatter);

        r1 = new Room("TITA");
        p1 = new Patient("Maria","Ramos",17,"mramos@gmail.com");
        d1 = new Doctor("Juan","Persico",22,"juanp@gmail.com");

        a1= new Appointment(p1,d1,r1,startDate,endDate);
        a2= new Appointment(p1,d1,r1,startDate2,endDate2);


        assertThat(a1.overlaps(a2)).isTrue();
    }


    @Test
    void shouldReturnFalseIfNotOverlap(){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

        LocalDateTime startDate = LocalDateTime.parse("09:30 13/09/2024",formatter);
        LocalDateTime endDate = LocalDateTime.parse("18:30 25/09/2024",formatter);

        LocalDateTime startDate2 = LocalDateTime.parse("09:30 22/10/2024",formatter);
        LocalDateTime endDate2 = LocalDateTime.parse("18:30 24/10/2024",formatter);

        r1 = new Room("TITA");
        p1 = new Patient("Maria","Ramos",17,"mramos@gmail.com");
        d1 = new Doctor("Juan","Persico",22,"juanp@gmail.com");

        a1= new Appointment(p1,d1,r1,startDate,endDate);
        a3= new Appointment(p1,d1,r1,startDate2,endDate2);


        assertThat(a1.overlaps(a3)).isFalse();
    }

}



