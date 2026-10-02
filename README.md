# 🎉 EventSync — Full-Stack Event Booking & Management Portal

EventSync is a modern, real-time Full-Stack Event Scheduling and Booking Management system. Built with **Spring Boot** and **MongoDB (NoSQL)**, it features a complete client booking portal, event search/filtering capabilities, an organizer approval panel, and automated real-time seat allocation.

---

## 🚀 Key Features

### 👤 Client-Facing Portal
*   **Live Event Catalog:** Displays active service packages categorized by types (Weddings, Birthdays, Seminars, Parties) with live seat counters.
*   **Search & Filter:** Advanced search bar to instantly query event packages by name, venue, or category.
*   **Pending Approval Bookings:** Clients request booking slots which start in a `PENDING` state and do not block seats until approved.
*   **Live Booking Status Checker:** Clients can enter their email to track their booking status (`PENDING`, `ACCEPTED`, or `REJECTED`) in real-time.

### 🛡️ Administrative Dashboard
*   **Analytics Panel:** Summary metrics calculating **Total Packages**, **Pending Requests**, **Approved Clients**, and **Total Earned Revenue** in real-time.
*   **Full Event CRUD:** Create, edit, and delete event packages dynamically.
*   **Approval Flow Panel:** Organizers can review list of attendees, and click `Accept` or `Reject` on individual bookings.
*   **Automatic Seat Restoring:** Rejecting a booking automatically increments the available capacity back by `+1` in the database.

---

## 🛠️ Technology Stack
*   **Backend Framework:** Spring Boot 3.2.5
*   **NoSQL Database:** MongoDB (Spring Data MongoDB)
*   **Template Engine:** Thymeleaf (Server-Side Rendering)
*   **Frontend Styling:** HTML5, CSS3, Bootstrap 4.6, FontAwesome Icons
*   **Boilerplate Reducer:** Lombok

---

## 💾 NoSQL Document Structure
Using MongoDB allows EventSync to store registrations directly inside the Event document for optimal, join-free query speeds:

```json
{
  "_id": "60d5ec4b1234567890abcdef",
  "title": "Premium Marriage Event Package",
  "description": "Full catering, decoration, and venue arrangements at Kazipet.",
  "date": "2026-10-17",
  "time": "11:45:00",
  "venue": "Kazipet",
  "price": 500000.00,
  "capacity": 200,
  "availableSeats": 199,
  "category": "WEDDING",
  "registrations": [
    {
      "userId": "60d5ec4b1234567890abc111",
      "userName": "akshith_vodnala",
      "userEmail": "vodnalaakshith@gmail.com",
      "status": "ACCEPTED"
    }
  ]
}
```

---

## ⚙️ How to Run Locally

### Prerequisites
*   Java Development Kit (JDK) 17 or higher
*   Maven 3.x
*   MongoDB Server running locally on default port `27017`

### Execution Steps
1.  **Clone the Repository:**
    ```bash
    git clone https://github.com/Akshith-Vodnala/EventSync.git
    cd EventSync
    ```
2.  **Verify MongoDB Connection:** Make sure MongoDB is active on `mongodb://localhost:27017`.
3.  **Run the Project via Maven:**
    ```bash
    mvn spring-boot:run
    ```
4.  **Access the Application:**
    *   Client Portal: `http://localhost:8082/`
    *   Organizer Panel: `http://localhost:8082/admin/dashboard`
