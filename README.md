# Service Novigrad

## Project description

Service Novigrad is an Android Application that mirrors the functionality of in-person Service Novigrad (an imaginary province service like Service Ontario), letting users access essential services online.

- The application was developed using Java and Android Studio Framework
- Firebase Realtime Database was used to manage and keep track of all the necessary data
- CircleCI was used to automate builds and tests

The application supports three types of users: admin, employee and client

- Admin can:
  - Create, modify or delete the services that can be offered by Service Novigrad branches
  - See and delete accounts of employees and clients
- Employee can:
  - Create a branch account and log in to the account
  - Modify the services that can be offered by the branch
  - Modify the branch opening days and hours
  - See the details of requests that were submitted to the branch as well as approve/reject those requests
- Client can:
  - Search for a branch by branch name/branch address/available services/opening days
  - View branch information
  - Send service requests to the selected branch
  - Rate the branch

## Screenshots of Application

### Log in and Sign up page

<center> <img style="center" src="screenshots/login.png" alt="Log in page" width="200"/> <img src="screenshots/signup.png" alr="Sign up page" width="200"> </center>

### Admin
<center> <img src="screenshots/Admin/admin_homepage.png" alt="Admin Homepage" width="200"> <br>
Admin Homepage <br>
<br>
<img src="screenshots/Admin/manage_service.png" alt="Manage Services Page" width="200"> <img src="screenshots/Admin/create_service.png" alt="Create Services" width="200"> <img src="screenshots/Admin/modify_service.png" alt="Modify Services" width="200"> <br>
Manage services page with the functions to create a new service, modify/delete existing services <br>
<br>

<img src="screenshots/Admin/manage_accounts.png" alt="Manage Accounts Page" width="200"> <img src="screenshots/Admin/delete_accounts.png" alt="Delete Accounts" width="200"> <br>
Manage accounts page with the function to delete accounts </center>

### Employee
<center> <img src="screenshots/Employee/employee_homepage.png" alt="Employee Homepage" width="200"> <br>
Employee Homepage <br>
<br>
<img src="screenshots/Employee/create_branch.png" alt="Create branch account" width="200"> <img src="screenshots/Employee/branch_login.png" alt="Log in branch account" width="200"> <br>
Create a branch account and log in to a branch account <br>
<br>
<img src="screenshots/Employee/branch_homepage.png" alt="Branch Homepage" width="200"> <br>
Branch Homepage <br>
<br>
<img src="screenshots/Employee/modify_branch_profile.png" alt="Modify branch profile" width="200"> <br>
Modify branch profile/information <br>
<br>
<img src="screenshots/Employee/modify_branch_services.png" alt="Modify Branch Services" width="200"> <img src="screenshots/Employee/add_branch_service.png" alt=" Add Branch Services" width="200"> <img src="screenshots/Employee/delete_branch_service.png" alt="Delete Branch Services" width="200"> <br>
Modify branch services page with the functions to add and delete services that can be offered by the branch <br>
<br>
<img src="screenshots/Employee/modify_branch_hours.png" alt="Modify Hours" width="200"> <img src="screenshots/Employee/modify_hours.png" alt="Modify Hours" width="200"> <br>
Modify branch opening days and opening hours <br>
Opening hours can only be updated if the branch is opened on that day <br>
<br>
<img src="screenshots/Employee/see_request_homepage.png" alt="See Requests" width="200"> <img src="screenshots/Employee/view_request_details.png" alt="See Request Details" width="200"> <br>
See requests page with the functions to view the details of each request and to approve/reject requests </center>

### Client
<center> <img src="screenshots/Client/client_homepage.png" alt="Client Homepage" width="200"> <br>
Client Homepage <br>
<br>
<img src="screenshots/Client/search_branch.png" alt="Search Branch" width="200"> <img src="screenshots/Client/additional_filter.png" alt="Additional Filter" width="200"> <br>
Search for branch by branch name/address/opening days/available services <br>
<br>
<img src="screenshots/Client/branch_info.png" alt="Branch Info" width="200"> <img src="screenshots/Client/send_request.png" alt="Send Request" width="200"> <img src="screenshots/Client/rate_branch.png" alt="Rate branch" width="200"> <br>
View information of the selected branch, send a request to that branch, and rate that branch <br>
<br>
<img src="screenshots/Client/view_submitted_requests.png" alt="View Submitted Services" width="200"> <img src="screenshots/Client/view_request_detail.png" alt="View Submitted Services Details" width="200"> <br>
View submitted requests with the option to delete them </center>
