# Ptero4J

A java library to interact with the Admin API of [Pterodactyl Panel](https://github.com/pterodactyl/panel).

##### _This project is currently not finished! Feel free to use it, but there's no guarantee it will 100% work._

### Few examples
```java
PteroAPI api = new PteroAPI("panel.pterodactyl.io", "api key here");

List<Server> servers = api.getServersController().getServers();
List<User> users = api.getUsersController().getUsers();

// For this one you should do empty checking of course
List<Server> serversFromUser = api.getUsersController().getUser("email@email.com").get(0).getServers(); 
```

_This library is not persistent! Every time you call a method on a controller a new API call will be made, so be aware of that!_
Example:
```java
// This way of retrieving a users servers takes 2 API calls, one to fetch the user, and another one to fetch the servers
api.getUsersController.getUser(88).getServers();

// If you already have the ID of the user this would be a better way:
api.getServersController().getServersForUser(88);
```