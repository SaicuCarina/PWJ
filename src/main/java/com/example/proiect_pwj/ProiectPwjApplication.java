package com.example.proiect_pwj;

import com.example.proiect_pwj.model.*;
import com.example.proiect_pwj.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

@SpringBootApplication
public class ProiectPwjApplication implements CommandLineRunner {

	@Autowired
	private LocationRepository locationRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private EventRepository eventRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ReservationRepository reservationRepository;

	public static void main(String[] args) {
		SpringApplication.run(ProiectPwjApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (userRepository.count() == 0) {

			Location l1 = createLocation("Concert Hall", "123 Main St", 500);
			Location l2 = createLocation("Conference Center", "456 Elm St", 300);
			Location l3 = createLocation("Theater", "789 Oak St", 200);
			locationRepository.saveAll(Arrays.asList(l1, l2, l3));

			Category concertCat = new Category(); concertCat.setName("concert");
			Category confCat = new Category(); confCat.setName("conference");
			categoryRepository.saveAll(Arrays.asList(concertCat, confCat));


			User u1 = createUser("carina", "car@gmail.com", "123");
			User u27 = createUser("calin", "calin@gmail.com", "111");
			User u28 = createUser("ggg", "ggg@gmail.com", "111");
			User u31 = createUser("car", "carina@gmail.com", "111");
			User u32 = createUser("caarina", "hhh@gmail.com", "111");
			userRepository.saveAll(Arrays.asList(u1, u27, u28, u31, u32));


			Event e7 = createEvent("Music Concert", LocalDate.of(2026, 2, 15), LocalTime.of(19, 0), l1, concertCat, 150.0);
			Event e8 = createEvent("Tech Conference", LocalDate.of(2026, 1, 20), LocalTime.of(9, 0), l2, confCat, 200.0);
			Event e9 = createEvent("Community Meeting", LocalDate.of(2026, 2, 1), LocalTime.of(18, 30), l3, confCat, 50.0);
			eventRepository.saveAll(Arrays.asList(e7, e8, e9));


			saveReservation(u1, e8, 3, LocalDateTime.of(2026, 1, 31, 10, 0));
			saveReservation(u1, e8, 8, LocalDateTime.of(2026, 2, 1, 11, 0));
			saveReservation(u1, e7, 5, LocalDateTime.of(2026, 2, 5, 15, 0));

			System.out.println("Datele au fost incarcate in baza de date!");
		}
	}

	private Location createLocation(String name, String addr, int cap) {
		Location l = new Location();
		l.setName(name); l.setAddress(addr); l.setCapacity(cap);
		return l;
	}

	private User createUser(String name, String email, String pass) {
		User u = new User();
		u.setFullName(name); u.setEmail(email); u.setPassword(pass);
		return u;
	}

	private Event createEvent(String title, LocalDate date, LocalTime time, Location loc, Category cat, double price) {
		Event e = new Event();
		e.setTitle(title);
		e.setDateTime(LocalDateTime.of(date, time));
		e.setLocation(loc);
		e.setCategory(cat);
		e.setTicketPrice(price);
		e.setAvailableSeats(loc.getCapacity());
		return e;
	}

	private void saveReservation(User u, Event e, int tickets, LocalDateTime date) {
		Reservation r = new Reservation();
		r.setUser(u); r.setEvent(e); r.setNumberOfTickets(tickets);
		r.setReservationDate(date);
		r.setTotalPrice(tickets * e.getTicketPrice());
		reservationRepository.save(r);
	}
}