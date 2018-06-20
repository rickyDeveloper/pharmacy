This is a POC app for approving and viewing prescriptions.

App is deployed on AWS cloud. I have used Spring boot, spring MVC, H2 database, hibernate to build the app.


Login page -->  http://testapp-env.vpmqyis3tz.us-west-2.elasticbeanstalk.com/login


App has three users which can login into this app using link. 

1)   doctor
2)   patient
3)   pharmacist

Password to login for all the three users is password



Steps to do:-

1) Login with user name as doctor and password as password

http://testapp-env.vpmqyis3tz.us-west-2.elasticbeanstalk.com/login

2) If you get JS error on next page.  Pls try again to login. (Its some JS issue)
http://testapp-env.vpmqyis3tz.us-west-2.elasticbeanstalk.com/webjars/bootstrap/3.3.7/js/bootstrap.min.js

3) Try login again with user name as doctor and password as password
http://testapp-env.vpmqyis3tz.us-west-2.elasticbeanstalk.com/login

4) This will take you to welcome page.  Showing text like "Welcome to Pharmacy App!"

5) This page will show you two links.  One to view prescription list.  Second to view all users.

6) Click on view prescriptions link it will take you to listing page all users from where you can see prescriptions.

http://testapp-env.vpmqyis3tz.us-west-2.elasticbeanstalk.com/actions

7) On this page you will see all the users and link to view their prescriptions.

8) Click on View link for patient "Vikas Naiyar" 

9)  It will take you to listing page where you can see all prescriptions of patient "Vikas Naiyar"

http://testapp-env.vpmqyis3tz.us-west-2.elasticbeanstalk.com/prescriptions/users/2

10) On this page you can request access to view the prescription by clicking on link "Request Access"

11) After you click on "Request Access" link you will get a success page message for submission of your request.

http://testapp-env.vpmqyis3tz.us-west-2.elasticbeanstalk.com/prescriptions/requests/1

12) Click on Sign out link to log out of the application.

13) Now login to application as user patient and password as password.

14) On next page click on link for prescriptions link.

15) This will show the pending request awaiting you action.
http://testapp-env.vpmqyis3tz.us-west-2.elasticbeanstalk.com/actions

16) On this page you can see the prescription id and details of which user requested your access. This will also the prescription text for reference.

17) Click on approve request link on this page to approve view access.

18) Once you get successful message for approval.  Click on sign out.

19) Now login again as user doctor and password as password.

20) Click on link to view prescriptions list.

http://testapp-env.vpmqyis3tz.us-west-2.elasticbeanstalk.com/actions

21) On this page click on View link for patient "Vikas Naiyar"

http://testapp-env.vpmqyis3tz.us-west-2.elasticbeanstalk.com/prescriptions/users/2

22) Now you can see that you can see prescription which has been approved by end user.











