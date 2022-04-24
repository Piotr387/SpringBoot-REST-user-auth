### SpringBoot-REST-user-auth<br>
<br>
Current progress on spring boot web service app <br>
<br>
for now app allows us for only one request without authorization token<br>
Currently implemented user sign up<br>
Security for user sign-up and sing-in<br>
Request and responde - XML and JSON support<br>
Custom exceptions handling<br>
CRUD user api call<br>

<br>
example post request wchich will work without Bearer Token<br>
<br>
POST Request to sign-up<br>
URL -> http://localhost:8080/spring-boot-edu/users<br>
Body:<br>
{<br>
    "firstName" : "Jan",<br>
    "lastName" : "Kowalski",<br>
    "email" : "test4@test.com",<br>
    "password" : "123456789"<br>
}<br>
<br>
POST Request to sign-in<br>
{<br>
    "email" : "test4@test.com",<br>
    "password" : "123456789"<br>
}<br>
<br>
After receiving 200 code from above request we need to copy Bearer token to next requests<br>
also in header we need to get UserID for belows requests <br>
<br>
PUT Reqest to update first or last name <br>
URL -> http://localhost:8080/spring-boot-edu/users/id <br>
<br>
{<br>
    "firstName": "xxx", <br>
    "lastName": "xyz" <br>
} <br>

DELETE Request to delete user from database <br>
URL -> http://localhost:8080/spring-boot-edu/users/id <br>

GET Request to get user details <br>
URL -> http://localhost:8080/spring-boot-edu/users/id <br>
