## How would you change this app to support multiple users?

In order to support multiple users we need an entity to hold user's information.

So by creating user's model class and/or table with unique id and password 
we can hold user's information.

And then in order to identify todo's associated with each user 
we need to add user's id into the Todo table and/or model class so that we can uniquely identify todos
created by each user.

## How would you handle errors?

To handle errors for the REST controller we can create a model class of RestExceptionHandler and make our Controller to throw exception of type that class. This handler contains information of
resource throwing error, status code, cause of error etc.

Also adding a logger can help understand source of errors. 

In order to take more control over error management we can send status code in response header 
so user/frond-end  can know what is the reason for the error such as 404 not found, 200 ok, 401 Unauthorized etc.
