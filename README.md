# Users_Manager_Backend_App

# Project Overview

This Spring Boot application provides APIs for user management, including creation, retrieval, deletion, and updates. The application ensures data validation and handles user information with timestamps and statuses.

## Endpoints

### **1. POST /create_user**

- **Purpose:** Creates a new user with details including `full_name`, `mob_num`, `pan_num`, and optionally `manager_id(optional)`.
- **Features:**
  - Validates user details.
  - Formats and stores data (e.g., PAN number in uppercase).
  - Generates a unique user ID.
  - Handles timestamps and active status.

### **2. POST /get_users**

- **Purpose:** Retrieves user records.
- **Features:**
  - Can filter by `mob_num`, `user_id`, or `manager_id`.
  - Returns user details including `full_name`, `mob_num`, and timestamps.

### **3. POST /delete_user**

- **Purpose:** Deletes a user based on `user_id` or `mob_num`.
- **Features:**
  - Checks existence of the user before deletion.
  - Returns success or error message.

### **4. POST /update_user**

- **Purpose:** Updates user information.
- **Features:**
  - Supports bulk updates for `manager_id` only.
  - Validates and updates details such as `full_name`, `mob_num`, `pan_num`, and `manager_id`.
  - Individual user updated possible only if specific user details provided.
  - Manages existing and new records for `manager_id` updates with timestamps.

## Database Schema

- **User Table:**
  - `user_id` (UUID)
  - `full_name` (String)
  - `mob_num` (String)
  - `pan_num` (String)
  - `manager_id` (UUID, nullable)
  - `created_at` (Timestamp)
  - `updated_at` (Timestamp)
  - `is_active` (Boolean)

- **Manager Table:**
  - `manager_id` (UUID)
  - `name` (String)
  - Additional fields as needed.

# Repository Access

## Cloning the Repository

To clone the repository to your local machine, follow these instructions:

1. **Copy the Repository URL:**

   The URL to clone the repository is: https://github.com/Harikaran-u/users-manager-backend-app-it-design.git


2. **Open Terminal or Command Prompt:**

Use your terminal (Linux/macOS) or command prompt (Windows).

3. **Run the Clone Command:**

Execute the following command to clone the repository:

```bash
git clone [https://github.com/yourusername/your-repository.git](https://github.com/Harikaran-u/users-manager-backend-app-it-design.git)
