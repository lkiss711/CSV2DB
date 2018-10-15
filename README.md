# CSV2DB

## Short summary about my ideas
I'm going to develop a maven project with hibernate to a persistent part and apache common tools for parsing a csv files. MySql server 
will be run in AWS environment.
Mysql server: ec2-34-207-215-129.compute-1.amazonaws.com (root/password - for mysql, ubuntu/ubuntu - for operating system.)
I have an another user for the application (db_user/db_pass)

I put the adnetworks data to database, so I can check the validation and the list of the company can be longer in the future.
The application checks the parameters with a getCompanyList() and an IsValidDateString() method. After successfull checking, the url of
the csv file derived from a database. According to a different date format - at the and of the url-s - I convert the second parameter.
Over the csv field, I put a timestamp and a key in the database.

Docker: docker pull lkiss711/apps7
