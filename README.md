# Sistem de Gestiune Rezervări Evenimente
## I. REGULI DE BUSINESS (10 CERINȚE)
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

Arhitectură: Layered Architecture (Controller -> Service -> Repository).

## IV. GESTIUNEA ERORILOR
Aplicația folosește un sistem de validare riguros, aruncând RuntimeException cu mesaje specifice pentru următoarele scenarii:

🔑 Utilizatori & Securitate
"Sesiune expirata! Te rugam sa te reloghezi." / "Sesiune expirata!" (Când token-ul este invalid).

"Email inexistent" (La login, dacă adresa nu se găsește).

"Parola incorecta" (Dacă parola nu corespunde cu cea din baza de date).

"Acest email este deja inregistrat!" (La register, dacă email-ul este duplicat).

"Trebuie să fii logat pentru a lasa un review!" (Acces neautorizat la recenzii).

📅 Evenimente
"Evenimentul nu a fost gasit" / "Eveniment negasit" / "Evenimentul nu exista" (Căutare după ID invalid).

"Nu a fost gasit niciun eveniment pentru criteriile selectate." (Dacă search-ul nu returnează rezultate).

🎟️ Rezervări
"Nu poti rezerva locuri la un eveniment care a trecut deja!" (Validare dată rezervare).

"Nu sunt destule locuri libere" (Validare stoc/capacitate).

"Rezervarea nu a fost gasita" (La anularea unui ID invalid).

"Nu poti anula rezervarea altcuiva!" (Protecția datelor între utilizatori).

"Nu poti anula o rezervare pentru un eveniment care a trecut deja!" (Limitare anulare post-eveniment).

⭐ Review-uri
"Nu poți lasa un review pentru un eveniment care nu a avut loc inca!" (Blocare feedback prematur).

"Doar persoanele care au rezervat bilete pot lasa un review!" (Validare participare prin rezervare).

## V. TESTARE ȘI DOCUMENTAȚIE
✅ Unit Tests: Realizate cu JUnit 5 și Mockito (acoperire pe Service-uri).

📜 Swagger UI: Documentație API generată automat la: http://localhost:8080/swagger-ui/index.html.

📡 Postman: Colecție configurată cu variabile de mediu ({{token}}) pentru demonstrație live.
