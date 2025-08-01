Rewards System - Spring Boot Project

This is a simple Spring Boot application that awards points to customersbased on their transaction amounts.
The system supports CRUD operations for customers, transaction tracking, and reward point calculation based on specific rules.

Technoligies used are as below:
- Java 
- Spring Boot
- Postgre sql
- Maven
- RESTful API
- Swagger (optional for API docs)

  Features:
  
- Add & manage Customers(add,delete,update,get)
- Create Transactions
- Calculate Reward Points
- Track Total Spent per Customer
- Get Transactions for:
  - Specific Month
  - Last 3 Months
  - For Specific Customer
 
    Reward Point Logic
    $50–$100 → 1 point per $1 over 50
    Over $100 → 2 points per $1 over 100 + 50 points (for first $50–$100)
    Example: Spend $120 → (20 × 2) + (50 × 1) = 90 points
  
