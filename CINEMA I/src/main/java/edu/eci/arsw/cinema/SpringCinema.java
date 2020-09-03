package edu.eci.arsw.cinema;

import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.model.Movie;
import edu.eci.arsw.cinema.persistence.CinemaException;
import edu.eci.arsw.cinema.persistence.CinemaPersistenceException;
import edu.eci.arsw.cinema.services.CinemaServices;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringCinema {

    public static void main(String[] args) throws CinemaPersistenceException, CinemaException {
        ApplicationContext ac=new ClassPathXmlApplicationContext("applicationContext.xml");
        CinemaServices cs=ac.getBean(CinemaServices.class);

        String functionDate1 = "2018-12-18 15:30";
        String functionDate2 = "2018-12-25 15:30";
        String functionDate3 = "2018-12-11 15:30";

        List<CinemaFunction> functions= new ArrayList<>();

        //Creando Funciones
        CinemaFunction funct1 = new CinemaFunction(new Movie("SpiderMan Homecoming","Action"),functionDate1);
        CinemaFunction funct2 = new CinemaFunction(new Movie("Anabelle","Terror"),functionDate2);
        CinemaFunction funct3 = new CinemaFunction(new Movie("Joker","Drama"),functionDate3);

        functions.add(funct1);
        functions.add(funct2);
        functions.add(funct3);

        //Registrando Cine
        Cinema c=new Cinema("Cine Colombia",functions);
        cs.addNewCinema(c);

        //Consultando Cines
        for(Cinema cine : cs.getAllCinemas()){
            System.out.println(cine.getName());
        }

        //Consultando Funciones
        for(Cinema cine : cs.getAllCinemas()){
            System.out.println(cine.getFunctions());
        }

        //Consultando Funciones por Cine
        String reqCine="Cine Colombia";
        for(Cinema cine : cs.getAllCinemas()){
            if(cine.getName() == reqCine){
                System.out.println(cine.getFunctions().toString());
            }
        }

        //Comprando entradas
        cs.buyTicket(1, 5, reqCine, functionDate1, "SpiderMan Homecoming");
        cs.buyTicket(2, 5, reqCine, functionDate3, "Joker");
        cs.buyTicket(5, 5, reqCine, functionDate3, "Joker");

        //Consultando Asientos
        String reqFunction="Joker";
        for(Cinema cine : cs.getAllCinemas()){
            if(cine.getName() == reqCine ){
                for(CinemaFunction function : cine.getFunctions()){
                    if(function.getMovie().getName() == reqFunction ){
                        System.out.println(function.getAvailableSeats());
                    }
                }
            }
        }

    }
}