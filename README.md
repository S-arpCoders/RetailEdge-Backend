# RetailEdge Backend

RetailEdge is a microservices-based point-of-sale (POS) system designed to help small businesses efficiently manage inventory and sales in one unified platform. This project aims to address common challenges faced by retail stores, grocery shops, restaurants, and pharmacies by integrating POS functionality with real-time inventory tracking and expiry management.

## Background
### Problem Statement
Small businesses in the retail and food industries struggle with:
- **Stock Mismanagement:** No real-time tracking leads to inaccurate stock levels and potential stockouts.
- **Expiry Tracking Issues:** Perishable goods often go unnoticed until they expire, causing financial losses.
- **Inefficient Sales Tracking:** Using separate systems for inventory and POS results in data mismatches.
- **Limited Accessibility:** Businesses need multi-user, multi-device access for smooth operations.

### Solution
RetailEdge provides a unified platform that integrates inventory management and sales tracking, reducing operational inefficiencies and ensuring better stock control. Key features include:
- **POS Interface:** For seamless sales processing.
- **Inventory Dashboard:** Monitors stock levels and alerts for expiry.
- **User Management:** Role-based access control for security.
- **Automated Notifications:** Alerts for low stock and expiring products.

## Getting Started
### Prerequisites
- Java 21+
- Maven
- Docker (optional, for containerized deployment)

## Microservices Overview
### Inventory Service
Handles product-related operations.
- **ProductController.java**: Manages CRUD operations for products.

### Sale Service
Manages sales transactions.
- **SaleController.java**: Handles sale-related API requests.

### API Gateway
Acts as a single entry point for all client requests.
- **AuthControllerGate.java**: Manages authentication.
- **ProductsControllerGate.java**: Routes inventory-related requests.
- **SalesControllerGate.java**: Routes sales-related requests.

### Authentication Service
Manages user authentication and security.
- **AuthController.java**: Handles login and token validation.

## API Documentation
For detailed API specifications and endpoints, visit:
[API Documentation](https://github.com/S-arpCoders/RetailEdge-Backend/wiki)

## Environment Variables
- `PORT`: Defines the application port (default: 8080)
- `JWT_SECRET`: Secret key for JWT authentication
- `DATABASE_URL`: External database service URL

