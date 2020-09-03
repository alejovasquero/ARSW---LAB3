
import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.services.CinemaServices;
import edu.eci.arsw.cinema.model.Movie;
import edu.eci.arsw.cinema.persistence.CinemaException;
import edu.eci.arsw.cinema.persistence.CinemaPersistenceException;
import edu.eci.arsw.cinema.persistence.impl.InMemoryCinemaPersistence;
import java.util.ArrayList;
import java.util.List;

import edu.eci.arsw.cinema.services.filters.impl.GenderFilter;
import edu.eci.arsw.cinema.services.filters.impl.SeatsFilter;
import org.junit.Before;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author cristian
 */
public class CinemaServicesTest {

	CinemaServices cs, cs2;
    Cinema c1, c2, c3;
    String functionDate1 = "2018-12-18 15:30";
    String functionDate2 = "2019-12-18 15:30";
    String functionDate3 = "2010-12-18 15:30";
    String functionDate4 = "2021-12-18 15:30";

	
	@Before
	public void init() throws CinemaException {
		ApplicationContext ac=new ClassPathXmlApplicationContext("applicationContext.xml");
        cs=ac.getBean(CinemaServices.class);
        cs2=ac.getBean(CinemaServices.class);
        setFilters();
        poblate();
	}


	private void setFilters(){
        cs.setFilter(new GenderFilter());
        cs2.setFilter(new SeatsFilter());
    }

	private void poblate() throws CinemaException{
        List<CinemaFunction> functions1= new ArrayList<>();
        List<CinemaFunction> functions2= new ArrayList<>();
        List<CinemaFunction> functions3= new ArrayList<>();
        CinemaFunction funct1 = new CinemaFunction(new Movie("SuperHeroes Movie 2","Action"),functionDate1);
        CinemaFunction funct2 = new CinemaFunction(new Movie("The Night 2","Horror"),functionDate1);
        CinemaFunction funct3 = new CinemaFunction(new Movie("The Night 2","Action"),functionDate2);
        functions1.add(funct1);
        functions1.add(funct2);
        functions1.add(funct3);
        c1=new Cinema("Movies Bogotá",functions1);
        CinemaFunction funct4 = new CinemaFunction(new Movie("The Night 2","Horror"),functionDate3);
        CinemaFunction funct5 = new CinemaFunction(new Movie("The Night 2","Comedy"),functionDate3);
        CinemaFunction funct6 = new CinemaFunction(new Movie("The Night 2","Action"),functionDate3);
        CinemaFunction funct7 = new CinemaFunction(new Movie("The Night 2","Horror"),functionDate2);
        functions2.add(funct4);
        functions2.add(funct5);
        functions2.add(funct6);
        functions2.add(funct7);
        c2=new Cinema("Center",functions2);
        CinemaFunction funct8 = new CinemaFunction(new Movie("The Night 2","Comedy"),functionDate1);
        CinemaFunction funct9 = new CinemaFunction(new Movie("The Night 2","Horror"),functionDate2);
        CinemaFunction funct10 = new CinemaFunction(new Movie("The Night 2","Comedy"),functionDate2);
        functions3.add(funct8);
        functions3.add(funct9);
        functions3.add(funct10);
        c3=new Cinema("Tupal",functions3);
        cs.addNewCinema(c1);
        cs.addNewCinema(c2);
        cs.addNewCinema(c3);
    }

    @Test
    public void shouldNotExistCinema() throws CinemaPersistenceException{
        try {
            cs.getFilteredMovies("dd", "fff", "dd");
            fail("Una excepción debería haber sido lanzada, debido a que el cinema no existe");
            cs.getFilteredMovies("Prueba fallida", "fff", "dd");
            fail("Una excepción debería haber sido lanzada, debido a que el cinema no existe");
            cs.getFilteredMovies("Prueba fallida", "fff", "dd");
            fail("Una excepción debería haber sido lanzada, debido a que el cinema no existe");

            cs2.getFilteredMovies("dd", "fff", "dd");
            fail("Una excepción debería haber sido lanzada, debido a que el cinema no existe");
            cs2.getFilteredMovies("Prueba fallida", "fff", "dd");
            fail("Una excepción debería haber sido lanzada, debido a que el cinema no existe");
            cs2.getFilteredMovies("Prueba fallida", "fff", "dd");
            fail("Una excepción debería haber sido lanzada, debido a que el cinema no existe");
        } catch (CinemaException e) {

        }
    }

    @Test
    public void genderFiltersEmptyTest(){
        try {
            assertEquals(cs.getFilteredMovies(c1.getName(), functionDate3, "Accion").size(), 0);
            assertEquals(cs.getFilteredMovies(c2.getName(), functionDate1, "Accion").size(), 0);
            assertEquals(cs.getFilteredMovies(c3.getName(), functionDate3, "Accion").size(), 0);
        } catch (CinemaException e) {
            fail("Ocurrió algún error inesperado");
        }
    }

    @Test
    public void seatsFiltersEmptyTest(){
        try {
            assertEquals(cs2.getFilteredMovies(c1.getName(), functionDate3, 7).size(), 0);
            assertEquals(cs2.getFilteredMovies(c2.getName(), functionDate1, 2).size(), 0);
            assertEquals(cs2.getFilteredMovies(c3.getName(), functionDate3, 33).size(), 0);
        } catch (CinemaException e) {
            fail("Ocurrió algún error inesperado");
        }
    }

    @Test
    public void genderFiltersTest(){
        try {
            assertEquals(cs.getFilteredMovies(c1.getName(), functionDate1, "Action").size(), 1);
            assertEquals(cs.getFilteredMovies(c2.getName(), functionDate3, "Comedy").size(), 1);
            assertEquals(cs.getFilteredMovies(c3.getName(), functionDate2, "Horror").size(), 1);
        } catch (CinemaException e) {
            fail("Ocurrió algún error inesperado");
        }
    }

    @Test
    public void seatsFiltersTest(){
        try {
            cs2.buyTicket(1,1,c1.getName(), functionDate1, "The Night 2");
            cs2.buyTicket(1,2,c1.getName(), functionDate1, "The Night 2");
            cs2.buyTicket(1,3,c1.getName(), functionDate1, "The Night 2");
            assertEquals(cs2.getFilteredMovies(c1.getName(), functionDate1, 84).size(), 1);
            assertEquals(cs2.getFilteredMovies(c2.getName(), functionDate3, 100).size(), 0);

            cs2.buyTicket(1,1,c3.getName(), functionDate1, "The Night 2");
            assertEquals(cs2.getFilteredMovies(c3.getName(), functionDate1, 83).size(), 1);
            assertEquals(cs2.getFilteredMovies(c3.getName(), functionDate2, 55).size(), 2);
        } catch (CinemaException e) {
            fail("Ocurrió algún error inesperado");
        }
    }
}
