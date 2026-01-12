# Sistem de Gestiune Rezervări Evenimente
## I. REGULI DE BUSINESS
[1] Unicitate Cont: Înregistrarea necesită un email unic; sistemul verifică baza de date înainte de a crea un utilizator nou.

[2] Securitate Token-Based: Doar utilizatorii autentificați (cu token valid) pot accesa funcțiile de rezervare și review.

[3] Filtrare Cronologică: Sistemul prioritizează și afișează automat evenimentele viitoare (Upcoming Events), sortate de la cea mai apropiată dată.

[4] Validare Dată: Rezervările se pot face exclusiv pentru evenimente din viitor.

[5] Control Stoc: Nu se poate rezerva un număr de bilete mai mare decât cel disponibil în câmpul availableSeats.

[6] Actualizare Automată: Numărul de locuri disponibile scade instantaneu în baza de date la confirmarea rezervării.

[7] Politica de Anulare: Utilizatorii își pot anula rezervarea, moment în care locurile sunt returnate automat în stoc.

[8] Condiție Review: Utilizatorul poate lăsa un review/rating doar dacă a avut o rezervare la evenimentul respectiv.

[9] Filtrare Avansată: Căutarea evenimentelor funcționează combinat după categorie și locație (case-insensitive).

[10] Calcul Dinamic: Prețul total este calculat de sistem (nr_bilete * pret_eveniment) și afișat în istoricul rezervărilor.

## II. FUNCȚIONALITĂȚI PRINCIPALE (MVP)
👤 Sistem Auth: Register, Login și Management de sesiune.

🔍 Căutare Inteligentă: Navigare prin evenimente cu filtre de locație/categorie.

🛒 Sistem de Booking: Procesare rezervări cu validări stricte de business.

📋 Dashboard Utilizator: Vizualizarea istoricului personal și anularea biletelor.

⭐ Sistem Feedback: Rating și comentarii pentru experiențele trecute.

## III. ARHITECTURĂ
Backend: Java 17 cu Spring Boot.

Bază de date: MySQL (6 entități: User, Event, Reservation, Review, Location, Category).

<img alt="{314F2154-C696-4DCF-9F02-32BEB0809B3C}" src="https://github.com/user-attachments/assets/5609b48a-b27d-4808-a326-7a71b63ec573" />

Arhitectură: Layered Architecture (Controller -> Service -> Repository).

## IV. GESTIUNEA ERORILOR (EXCEPȚII ȘI CODURI HTTP)
Aplicația folosește un sistem de validare, interceptat de GlobalExceptionHandler, care mapează excepțiile de tip RuntimeException pe coduri de stare HTTP specifice:

🔑 Utilizatori & Securitate

"Sesiune expirata! Te rugam sa te reloghezi." / "Sesiune expirata!" (401 Unauthorized)

"Email inexistent" (404 Not Found)

"Parola incorecta" (401 Unauthorized)

"Acest email este deja inregistrat!" (400 Bad Request)

"Trebuie să fii logat pentru a lasa un review!" (401 Unauthorized)

📅 Evenimente

"Evenimentul nu a fost gasit" / "Eveniment negasit" / "Evenimentul nu exista" (404 Not Found)

"Nu a fost gasit niciun eveniment pentru criteriile selectate." (404 Not Found - gestionat prin logica de căutare)

🎟️ Rezervări

"Nu poti rezerva locuri la un eveniment care a trecut deja!" (400 Bad Request)

"Nu sunt destule locuri libere" (400 Bad Request)

"Rezervarea nu a fost gasita" (404 Not Found)

"Nu poti anula rezervarea altcuiva!" (400 Bad Request - Fallback)

"Nu poti anula o rezervare pentru un eveniment care a trecut deja!" (400 Bad Request)

⭐ Review-uri

"Nu poți lasa un review pentru un eveniment care nu a avut loc inca!" (400 Bad Request)

"Doar persoanele care au rezervat bilete pot lasa un review!" (400 Bad Request)


Toate aceste mesaje sunt procesate prin metoda handleRuntimeException, care scanează conținutul mesajului excepției folosind .contains() și returnează un obiect de tip ResponseEntity<String> cu statusul corespunzător. Această abordare asigură că frontend-ul sau clientul de API (Postman) primește un feedback clar și standardizat pentru orice eroare de business întâlnită.

## V. TESTARE ȘI DOCUMENTAȚIE
✅ Unit Tests: Realizate cu JUnit 5 și Mockito (acoperire pe Service-uri).

<img alt="{965F458E-9D4F-4E93-8BFD-E968350479CD}" src="https://github.com/user-attachments/assets/4b0604fb-b962-464d-aa52-88eeacd3ea62" />



📜 Swagger UI

<img width="749" height="443" alt="{09EEB8FF-8811-4708-8341-27D61D95B38E}" src="https://github.com/user-attachments/assets/400593fd-973d-465f-8573-1e634f698967" />


📡 Postman: Colecție configurată cu variabile de mediu ({{token}})

<img alt="{7EE21EB7-FB68-47C4-ABB0-179734D4BE9B}" src="https://github.com/user-attachments/assets/41b53c6e-553b-4f84-8de9-a5541e44368b" />

