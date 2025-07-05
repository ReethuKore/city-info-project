# CityInfo - A Java-Based City Guide Application

## 📌 Description
CityInfo is a Java desktop application designed to help city newcomers, tourists, and locals quickly access essential services and places. It includes information on hotels, educational institutions, hostels, ATMs, restaurants, and hospitals. The app supports role-based login, allowing admins to manage data and users to view city details.

## 💡 Features
- **Role-Based Access:** Admin and User roles with tailored dashboards.
- **Authentication System:** Secure registration and login using MySQL.
- **Modular Design:** Each city service (hotels, education, hostels, etc.) is managed via separate modules.
- **Admin Panel:**
  - Add new entries for each module.
  - View and delete existing data.
- **User Dashboard:**
  - Browse listings of city services in a read-only format.
- **Neat GUI Layout:** Built using Java Swing with consistent and responsive components.
- **Welcome Screen:** Entry point before registration/login.

## 🛠️ Technologies Used
- **Programming Language:** Java (JDK 1.8)
- **Frontend:** Java Swing & AWT
- **Backend Database:** MySQL (via XAMPP)
- **Connectivity:** JDBC (with MySQL Connector/J)
- **IDE Used:** VS Code

## 🗂️ Database Tables
- `users(id, username, password, role)`
- `hotels(id, name, address, phone, city)`
- `education(id, name, type, address, city)`
- `hostels(id, name, address, contact, city)`
- `atms(id, bank_name, location, city)`
- `restaurants(id, name, address, contact, city)`
- `hospitals(id, name, address, contact, city)`

## ▶️ How to Run
1. Install **Java JDK 1.8** and **XAMPP** with MySQL.
2. Import the SQL scripts to create the required tables.
3. Place the `mysql-connector-j-8.0.xx.jar` in the `lib` directory.
4. Compile the project:
   ```bash
   javac -cp ".;lib\mysql-connector-j-8.0.xx.jar" path\to\*.java
5.Run the application:
   ```bash
   java -cp ".;lib\mysql-connector-j-8.0.xx.jar" ui.WelcomePage
   ```

📌 Note
> This is a desktop-only application and must be run in a Java-supported environment with access to MySQL. 
> Replace 8.0.33 with your exact jar version if different.
> Always make sure MySQL/XAMPP is running when testing database access.

Example Guide : 📁 Project Structure – CityInfo

CityInfo/
│
├── lib/
│   └── mysql-connector-j-8.0.33.jar      # JDBC Driver
│
├── db/
│   └── DBConnection.java                 # JDBC connection logic
│
├── auth/
│   ├── Register.java                     # User registration with role selection
│   └── Login.java                        # Login logic (Admin/User routing)
│
├── dashboard/
│   ├── AdminDashboard.java              # Dashboard for admin role
│   └── UserDashboard.java               # Dashboard for user role
│
├── modules/
│   ├── HotelsAdmin.java                 # Admin management for hotels
│   ├── HotelsViewer.java                # User view for hotels
│   ├── EducationAdmin.java              # Admin management for education
│   ├── EducationViewer.java             # User view for education
│   ├── HostelsAdmin.java                # Admin management for hostels
│   ├── HostelsViewer.java               # User view for hostels
│   ├── ATMsAdmin.java                   # Admin management for ATMs
│   ├── ATMsViewer.java                  # User view for ATMs
│   ├── RestaurantsAdmin.java            # Admin management for restaurants
│   ├── RestaurantsViewer.java           # User view for restaurants
│   ├── HospitalsAdmin.java              # Admin management for hospitals
│   └── HospitalsViewer.java             # User view for hospitals
│
├── ui/
│   └── WelcomePage.java                 # Initial entry screen with “Enter” button
│
├── resources/                           # (optional) for images, icons, future use
│
├── README.md                            # Project overview and usage instructions
└── cityinfo.sql                         # SQL script to create DB and tables


## 🖼️ Screenshots
![image](https://github.com/user-attachments/assets/e26b6364-7bd1-4e76-b9e8-7b699a275652)
![image](https://github.com/user-attachments/assets/f73e1092-3460-4087-bd1e-c7c0bc886cba)
![image](https://github.com/user-attachments/assets/e21cdab0-a465-4c9f-8e04-21df1e9534dd)
![image](https://github.com/user-attachments/assets/cae157b1-35fd-4c53-aa66-0656594bbeda)
![image](https://github.com/user-attachments/assets/4296ea28-364f-48fa-bf02-595cbd1eb9ee)
![image](https://github.com/user-attachments/assets/cb29d8e6-8efc-44d5-9153-b36a94fe0e16)
![image](https://github.com/user-attachments/assets/10d7f12d-268e-45aa-b6a5-85b9978a8ca5)
![image](https://github.com/user-attachments/assets/db79e145-7064-4c67-9635-eee2116c92f0)
![image](https://github.com/user-attachments/assets/9000de8b-4bf0-4af0-b063-a399afa231ca)

## 📄 License
This project is for educational purposes and open for further development or migration. Attribution appreciated.
