# Build a Library Management System using Java Swing, MVC, and SOLID Principles

## 1. Role and Objective

Act as a Senior Java Software Engineer and Desktop UI/UX Developer.

Your task is to develop a simple but well-structured **Library Management System** using Java Swing.

The application must support two roles:

1. Member (Student)
2. Librarian (Administrator)

The application should provide book management, book borrowing, book returns, and member management.

This is an educational project, but the code architecture must follow professional software engineering practices.

## 2. Technology Stack

Use the following technologies:

- Java 17 or newer
- Java Swing for desktop GUI
- Maven for dependency management
- SQLite for local database persistence
- JDBC for database connectivity
- MVC (Model-View-Controller) architecture
- SOLID Principles

Avoid using Spring Boot, JavaFX, or web-based technologies.

## 3. Application Architecture

Follow MVC strictly.

Each component must have a clearly defined responsibility:

- Model: Represents application entities and business data.
- View: Contains Java Swing UI components.
- Controller: Handles user interactions and connects View with Service.
- Service: Contains business logic and validation.
- Repository: Handles database operations using JDBC.
- Config: Handles database configuration and connection management.

Do not put SQL queries inside Controllers or Views.

Do not put business logic inside Swing components.

## 4. User Authentication and Roles

Create a login screen with:

- NIM input field
- Username input field
- Login button

After successful login, open the main application dashboard.

The application supports two roles:

### Member
- Can browse the book catalog.
- Can search books.
- Can borrow available books.
- Can see their borrowing history.
- Can see borrowing status.

### Librarian
- Can manage books (Create, Read, Update, Delete).
- Can manage members.
- Can view all borrowing transactions.
- Can approve or process book returns.
- Can monitor book availability.

Provide a role-switching menu in the interface for demonstration/testing purposes.

Important security rule:
- The active role must be validated by the application.
- A Member must never gain Librarian permissions simply by changing a dropdown.
- For demonstration, role switching can be available only through a controlled demo account or authentication mechanism.
- Enforce role permissions in the Controller and Service layers, not only by hiding buttons.

Provide sample accounts for testing.

## 5. Main Application Layout

Create a modern desktop interface using Java Swing.

Design direction:

- Clean and professional library dashboard.
- Modern admin dashboard appearance.
- Consistent spacing and typography.
- Neutral background colors.
- White or light-colored content panels.
- Dark sidebar navigation.
- Subtle borders and rounded-looking components where practical.
- Consistent button styling.
- Responsive resizing using appropriate Swing Layout Managers.

Avoid absolute positioning with `null` layouts.

Use:
- BorderLayout
- GridBagLayout
- BoxLayout
- CardLayout
- JTable
- JScrollPane
- JDialog

Use a sidebar navigation and a main content area.

### Sidebar Navigation

Librarian:
1. Dashboard
2. Book Management
3. Member Management
4. Borrowing Management
5. Return Management
6. Logout

Member:
1. Dashboard
2. Book Catalog
3. My Borrowings
4. My Profile
5. Logout

The dashboard content must change based on the selected sidebar menu.

## 6. Dashboard

Create a dashboard with summary cards.

For Librarian:
- Total Books
- Available Books
- Total Members
- Active Borrowings
- Overdue Borrowings

For Member:
- Total Borrowed Books
- Currently Borrowed
- Returned Books
- Overdue Books

Display recent borrowing activities in a JTable.

## 7. Book Management (CRUD)

Create a book management page using JTable.

### Book Table

Columns:

| Column | Description |
|---|---|
| ID | Unique book identifier |
| ISBN | Book ISBN |
| Title | Book title |
| Author | Author name |
| Publisher | Publisher |
| Category | Book category |
| Published Year | Publication year |
| Stock | Total book quantity |
| Available | Available quantity |
| Actions | Edit / Delete |

### Table Actions

Provide buttons above the table:

- Add Book
- Refresh
- Search Book

Each row must support:
- Edit
- Delete

### Add Book Dialog

When the librarian clicks Add Book, open a modal JDialog.

Form fields:

- ISBN
- Book Title
- Author
- Publisher
- Category
- Published Year
- Stock Quantity

Buttons:
- Save
- Cancel

Validation:
- Required fields cannot be empty.
- ISBN must be unique.
- Stock must not be negative.
- Published year must be valid.

After successful submission:
- Save the book to SQLite.
- Close the dialog.
- Refresh the JTable.
- Display a success message.

### Edit Book Dialog

When Edit is clicked:
- Open a modal JDialog.
- Automatically populate the form with existing book data.
- Allow the librarian to modify the fields.
- Save changes to the database.
- Refresh the table.

### Delete Book Confirmation

When Delete is clicked:
- Show a confirmation dialog using JOptionPane.
- Ask the librarian to confirm deletion.
- Prevent deletion if the book has active borrowing transactions.
- Refresh the table after successful deletion.

## 8. Book Catalog (Member)

Create a catalog page for members.

Display books in a JTable.

Columns:
- ISBN
- Title
- Author
- Category
- Available Stock
- Action

Features:
- Search by title or author.
- Filter by category.
- Borrow Book button.

When Borrow Book is clicked:
- Display a confirmation dialog.
- Check book availability.
- Create a borrowing transaction.
- Update available stock.
- Display the result.

A member cannot borrow a book if available stock is zero.

## 9. Borrowing Management

Create a librarian borrowing management page.

Display borrowing transactions using JTable.

Columns:

| Column | Description |
|---|---|
| Borrow ID | Transaction ID |
| NIM | Member NIM |
| Member Name | Member name |
| Book Title | Borrowed book |
| Borrow Date | Date borrowed |
| Due Date | Expected return date |
| Return Date | Actual return date |
| Status | Borrowing status |
| Actions | Details / Process Return |

Available statuses:
- BORROWED
- RETURNED
- OVERDUE

Features:
- Search by NIM or book title.
- Filter by transaction status.
- View transaction details.
- Process book returns.

When a librarian processes a return:
- Update transaction status.
- Set the actual return date.
- Increase available book stock.
- Refresh the table.

## 10. Member Management

Create a member management page.

Use JTable with these columns:

- Member ID
- NIM
- Username
- Full Name
- Email
- Role
- Actions

Features:
- Add Member
- Edit Member
- Delete Member
- Search Member

Use modal JDialogs for Add and Edit forms.

Do not allow deletion of members with active borrowing transactions.

## 11. My Borrowings

For Member users, create a page showing their personal borrowing history.

Columns:

- Borrow ID
- Book Title
- Borrow Date
- Due Date
- Return Date
- Status

Members must only see their own borrowing transactions.

## 12. Modal and Popup Design

Use modal JDialogs instead of opening separate windows for CRUD operations.

Required dialogs:

1. Add Book Dialog
2. Edit Book Dialog
3. Delete Confirmation Dialog
4. Add Member Dialog
5. Edit Member Dialog
6. Borrow Book Confirmation Dialog
7. Transaction Details Dialog
8. Return Confirmation Dialog

Use JOptionPane for:
- Success notifications
- Error notifications
- Warning notifications
- Delete confirmations

All dialogs must have consistent styling and appropriate validation.

## 13. Database Design

Use SQLite.

Create the following tables:

### users
- id (INTEGER PRIMARY KEY AUTOINCREMENT)
- nim (TEXT UNIQUE NOT NULL)
- username (TEXT NOT NULL)
- full_name (TEXT NOT NULL)
- email (TEXT)
- role (TEXT NOT NULL)

### books
- id (INTEGER PRIMARY KEY AUTOINCREMENT)
- isbn (TEXT UNIQUE NOT NULL)
- title (TEXT NOT NULL)
- author (TEXT NOT NULL)
- publisher (TEXT)
- category (TEXT)
- published_year (INTEGER)
- stock (INTEGER NOT NULL)
- available_stock (INTEGER NOT NULL)

### borrowings
- id (INTEGER PRIMARY KEY AUTOINCREMENT)
- user_id (INTEGER NOT NULL)
- book_id (INTEGER NOT NULL)
- borrow_date (TEXT NOT NULL)
- due_date (TEXT NOT NULL)
- return_date (TEXT)
- status (TEXT NOT NULL)

Use foreign keys to maintain referential integrity.

Use PreparedStatement for all database queries.

Use database transactions when processing borrowing and returning operations to ensure stock and transaction data remain consistent.

## 14. SOLID Principles

Implement the five SOLID principles:

**S - Single Responsibility Principle**
- Each class must have one clear responsibility.
- Repository handles database operations.
- Service handles business logic.
- Controller handles user interactions.

**O - Open/Closed Principle**
- Make services and components extensible without modifying existing functionality unnecessarily.
- Use interfaces where appropriate.

**L - Liskov Substitution Principle**
- Implementations must be replaceable by their abstractions without breaking behavior.

**I - Interface Segregation Principle**
- Avoid large interfaces with unrelated methods.
- Separate interfaces based on responsibilities.

**D - Dependency Inversion Principle**
- Controllers depend on service abstractions.
- Services depend on repository abstractions.
- Use constructor injection.
- Avoid unnecessary direct dependencies between layers.

Do not overengineer the application with unnecessary design patterns.

## 15. Project Structure

Use this package:

com.rifiki.java_project_task1

Recommended structure:

src/main/java/com/rifiki/java_project_task1/
├── config/
│   └── DatabaseConfig.java
│
├── model/
│   ├── User.java
│   ├── Book.java
│   ├── Borrowing.java
│   └── enums/
│       ├── Role.java
│       └── BorrowingStatus.java
│
├── repository/
│   ├── UserRepository.java
│   ├── BookRepository.java
│   ├── BorrowingRepository.java
│   └── impl/
│       ├── UserRepositoryImpl.java
│       ├── BookRepositoryImpl.java
│       └── BorrowingRepositoryImpl.java
│
├── services/
│   ├── AuthService.java
│   ├── BookService.java
│   ├── MemberService.java
│   ├── BorrowingService.java
│   └── impl/
│       ├── AuthServiceImpl.java
│       ├── BookServiceImpl.java
│       ├── MemberServiceImpl.java
│       └── BorrowingServiceImpl.java
│
├── controller/
│   ├── AuthController.java
│   ├── BookController.java
│   ├── MemberController.java
│   └── BorrowingController.java
│
├── view/
│   ├── LoginFrame.java
│   ├── MainFrame.java
│   ├── components/
│   │   ├── Sidebar.java
│   │   ├── Header.java
│   │   └── StatCard.java
│   ├── panels/
│   │   ├── DashboardPanel.java
│   │   ├── BookManagementPanel.java
│   │   ├── BookCatalogPanel.java
│   │   ├── MemberManagementPanel.java
│   │   ├── BorrowingManagementPanel.java
│   │   └── MyBorrowingsPanel.java
│   └── dialogs/
│       ├── AddBookDialog.java
│       ├── EditBookDialog.java
│       ├── AddMemberDialog.java
│       ├── EditMemberDialog.java
│       ├── BorrowBookDialog.java
│       └── TransactionDetailDialog.java
│
└── server/
    └── Main.java

src/main/resources/
├── database/
│   └── schema.sql
└── icons/

The server package can be renamed to app or bootstrap if more appropriate for a desktop application. Do not create an actual network server.

## 16. Important Implementation Rules

1. Use Java Swing Event Dispatch Thread correctly.
2. Use SwingUtilities.invokeLater() to start the application.
3. Avoid blocking the UI with long-running operations.
4. Use JTable with custom TableModel when appropriate.
5. Use PreparedStatement to prevent SQL injection.
6. Handle database exceptions properly.
7. Use try-with-resources for JDBC resources.
8. Keep View classes focused on UI rendering.
9. Avoid duplicated code.
10. Use meaningful class and method names.
11. Use enums for roles and borrowing statuses.
12. Validate input before saving data.
13. Use consistent date formats.
14. Provide useful error messages.
15. Ensure the application works without an internet connection.

## 17. Expected Deliverables

Build the application incrementally.

Phase 1:
- Maven configuration.
- SQLite database configuration.
- Database schema.
- Model classes.
- Repository interfaces and implementations.

Phase 2:
- Service interfaces and implementations.
- Authentication.
- Role-based authorization.
- Book CRUD operations.

Phase 3:
- Login screen.
- Main dashboard.
- Sidebar navigation.
- Book management UI.
- Modal CRUD dialogs.

Phase 4:
- Member management.
- Borrowing transactions.
- Return management.
- Member borrowing history.

Phase 5:
- UI polish.
- Validation.
- Error handling.
- Testing.
- README documentation.

## 18. Final Requirements

Before implementing anything:

1. Inspect the existing Maven project.
2. Inspect the existing pom.xml and Java files.
3. Understand the existing package structure.
4. Reuse existing files when appropriate.
5. Do not overwrite working code without understanding it.
6. Explain the implementation plan before making major changes.

After implementation:

- Ensure the project compiles.
- Ensure SQLite persistence works.
- Test CRUD operations.
- Test borrowing and returning.
- Test role-based access.
- Provide sample login credentials.
- Update README.md with setup and execution instructions.

**Most Important:** Prioritize clean architecture, readable Java code, functional features, and a polished Java Swing interface. Do not sacrifice code quality for unnecessary complexity.
