
import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.model.Movie;
import edu.eci.arsw.cinema.persistence.CinemaException;
import edu.eci.arsw.cinema.persistence.CinemaPersistenceException;
import edu.eci.arsw.cinema.persistence.impl.InMemoryCinemaPersistence;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import org.junit.Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author cristian
 */
public class InMemoryPersistenceTest {

    @Test
    public void saveNewAndLoadTest() throws CinemaPersistenceException{
        InMemoryCinemaPersistence ipct=new InMemoryCinemaPersistence();

        String functionDate = "2018-12-18 15:30";
        List<CinemaFunction> functions= new ArrayList<>();
        CinemaFunction funct1 = new CinemaFunction(new Movie("SuperHeroes Movie 2","Action"),functionDate);
        CinemaFunction funct2 = new CinemaFunction(new Movie("The Night 2","Horror"),functionDate);
        functions.add(funct1);
        functions.add(funct2);
        Cinema c=new Cinema("Movies Bogotá",functions);
        ipct.saveCinema(c);
        
        assertNotNull("Loading a previously stored cinema returned null.",ipct.getCinema(c.getName()));
        assertEquals("Loading a previously stored cinema returned a different cinema.",ipct.getCinema(c.getName()), c);
    }


    @Test
    public void saveExistingCinemaTest() {
        InMemoryCinemaPersistence ipct=new InMemoryCinemaPersistence();
        
        String functionDate = "2018-12-18 15:30";
        List<CinemaFunction> functions= new ArrayList<>();
        CinemaFunction funct1 = new CinemaFunction(new Movie("SuperHeroes Movie 2","Action"),functionDate);
        CinemaFunction funct2 = new CinemaFunction(new Movie("The Night 2","Horror"),functionDate);
        functions.add(funct1);
        functions.add(funct2);
        Cinema c=new Cinema("Movies Bogotá",functions);
        
        try {
            ipct.saveCinema(c);
        } catch (CinemaPersistenceException ex) {
            fail("Cinema persistence failed inserting the first cinema.");
        }
        
        List<CinemaFunction> functions2= new ArrayList<>();
        CinemaFunction funct12 = new CinemaFunction(new Movie("SuperHeroes Movie 3","Action"),functionDate);
        CinemaFunction funct22 = new CinemaFunction(new Movie("The Night 3","Horror"),functionDate);
        functions.add(funct12);
        functions.add(funct22);
        Cinema c2=new Cinema("Movies Bogotá",functions2);
        try{
            ipct.saveCinema(c2);
            fail("An exception was expected after saving a second cinema with the same name");
        }
        catch (CinemaPersistenceException ex){
            
        }
    }

    @Test
    public void getCinemaByNameTest(){
        InMemoryCinemaPersistence ipct=new InMemoryCinemaPersistence();

        String functionDate = "2019-12-11 15:30";
        List<CinemaFunction> functions= new ArrayList<>();
        CinemaFunction funct1 = new CinemaFunction(new Movie("Fast","Action"),functionDate);
        CinemaFunction funct2 = new CinemaFunction(new Movie("IT","Horror"),functionDate);
        functions.add(funct1);
        functions.add(funct2);
        Cinema c=new Cinema("Cine Colombia 22",functions);

        try{
            ipct.saveCinema(c);
            assertEquals("Loading a previously stored cinema returned a different cinema.", ipct.getCinema(c.getName()), c);
        } catch (CinemaPersistenceException e) {
            fail("Cinema persistence failed consulting the first cinema.");
        }

        String functionDate2 = "2019-12-11 15:30";
        List<CinemaFunction> functions2= new ArrayList<>();
        CinemaFunction funct12 = new CinemaFunction(new Movie("Fast","Action"),functionDate);
        CinemaFunction funct22 = new CinemaFunction(new Movie("IT","Horror"),functionDate);
        functions2.add(funct12);
        functions2.add(funct22);
        Cinema c2=new Cinema("M&M",functions2);

        try{
            assertNull("Loading a not stored cinema returned different to null", ipct.getCinema(c2.getName()));
        } catch (CinemaPersistenceException e) {
            fail("Cinema persistence failed consulting the first cinema.");
        }
    }

    @Test
    public void buyTicketTest(){
        InMemoryCinemaPersistence ipct=new InMemoryCinemaPersistence();

        String functionDate = "2019-12-11 15:30";
        List<CinemaFunction> functions= new ArrayList<>();
        CinemaFunction funct1 = new CinemaFunction(new Movie("Fast","Action"),functionDate);
        CinemaFunction funct2 = new CinemaFunction(new Movie("IT","Horror"),functionDate);
        functions.add(funct1);
        functions.add(funct2);
        Cinema c=new Cinema("Cine Colombia 22",functions);

        try {
            ipct.saveCinema(c);
            ipct.buyTicket(6,7, c.getName(), functionDate, funct1.getMovie().getName());
        } catch (CinemaException | CinemaPersistenceException e) {
            fail("Cinema persistence failed with ticket reservation.");
        }

        try {
            ipct.buyTicket(100000,1000000, c.getName(), functionDate, funct1.getMovie().getName());
            fail("Cinema persistence failed because not row and col not existing.");
        } catch (CinemaException e) {
        }

        try {
            ipct.buyTicket(6,7, c.getName(), functionDate, funct1.getMovie().getName());
            fail("Cinema persistence succeded with ticket reservated that should not have been made.");
        } catch (CinemaException e) {
            System.out.println(":)");
        }

    }

    @Test
    public void getFunctionsbyCinemaAndDateTest(){
        InMemoryCinemaPersistence ipct=new InMemoryCinemaPersistence();
        String functionDate = "2020-12-11 15:00";
        String functionDate2 = "2020-12-11 14:00";
        List<CinemaFunction> functions1= new ArrayList<>();
        CinemaFunction funct1 = new CinemaFunction(new Movie("Fast","Action"),functionDate);
        CinemaFunction funct2 = new CinemaFunction(new Movie("IT","Horror"),functionDate);
        functions1.add(funct1);
        functions1.add(funct2);

        List<CinemaFunction> functions2= new ArrayList<>();
        CinemaFunction funct12 = new CinemaFunction(new Movie("Fast","Action"),functionDate2);
        CinemaFunction funct22 = new CinemaFunction(new Movie("IT","Horror"),functionDate2);
        functions2.add(funct12);
        functions2.add(funct22);
        Cinema c=new Cinema("Cine Colombia 22",functions1);
        Cinema c2=new Cinema("Cine tar",functions2);
        try {
            ipct.saveCinema(c);
            ipct.saveCinema(c2);
            List<CinemaFunction> lis = ipct.getFunctionsbyCinemaAndDate(c.getName(), functionDate);
            assertEquals(lis.size(), 2);
            assertEquals(lis.get(0), funct1);
            assertEquals(lis.get(1), funct2);
        } catch (CinemaPersistenceException e) {
            fail("Cinema persistence failed when consulting cinemas.");
        }

        assertEquals("Deberia dar una lista vacía, debido a la fecha.", ipct.getFunctionsbyCinemaAndDate(c2.getName(), functionDate).size(), 0);
        assertNull("El cinema no debería existir.", ipct.getFunctionsbyCinemaAndDate("NO EXISTE", functionDate));
        System.out.println(":)");
    }
}
