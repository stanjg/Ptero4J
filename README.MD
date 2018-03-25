# Ptero4J

A java library to interact with the Admin API of [Pterodactyl Panel](https://github.com/pterodactyl/panel).

##### _This project is currently not finished! Feel free to use it, but there's no guarantee it will 100% work._

### Content:
- [General Concepts](#concepts)
    - [Controllers](#controllers)
    - [Actions](#actions)
    - [Admin and User API](#apis)
- [Maven Repo](#maven)
- [Usage Examples](#examples)
- [Documentation](#documentation)
    - [Admin API](#admin-api-docs)
        - [Servers](#servers)
            - [Server Object](#server-object)
            - [Servers Controller](#server-controller)
            - [Server Actions](#server-actions)
        - [Users](#users)
            - [Server Object](#user-object)
            - [Servers Controller](#user-controller)
            - [Server Actions](#user-actions)
    - [User API](#user-api-docs)
        - Documentation still has to be made

### <a name="concepts"></a>General Concepts
#### <a name="controllers"></a>Controllers
Controllers are the objects that make the requests to the panel. There is a controller class for every Model and they all derive from `Controller`.
With a controller class you can fetch resources from the panel, instantiate Action objects and perform generic actions.
All Controllers are instantiated and 'kept' in the PteroAdminAPI or PteroUserAPI class. 
Example:
```java
UsersController usersController = api.getUsersController();
List<User> users = usersController.getAllUsers();
```

#### <a name="actions"></a>Actions
There are two type of actions in this library; `Explicit` and `Generic`. Explicit Actions often need more details. 
- An example of an explicit action is ServerUpdateDetailsAction. When calling this action an object of this class will be returned and it behaves like a builder, when all wanted settings have been set you call the `execute()` method and it will make the  call to the panel API.
- An example of a generic action is suspending a server, this can simply be done by calling `suspend()` on a `Server` object.

Example:
```java
// Explicit Action
server.editBuild().setMemory(2048).execute();

// Generic Action
server.suspend();
```

#### <a name="apis"></a>Admin and User API
The library is split into two seperate APIs, the PteroUserAPI and the PteroAdminAPI. This is done because the Pterodactyl Panel API is actually split up into these two sections aswell.
- The Admin API is used to get/modify/create objects in the Panel.
- The User API is used to send commands and power options to servers.

### <a name="maven"></a>Maven use
In your pom.xml you need set the Jitpack repository and the panel Dependency
```xml
    <repositories>
        <!-- Jitpack repo -->
        <repository>
            <id>jitpack</id>
            <url>https://jitpack.io</url>
        </repository>
    </repositories>
    
    <dependencies>
        <!-- Panel API -->
        <dependency>
            <groupId>com.github.stanjg</groupId>
            <artifactId>Ptero4J</artifactId>
            <version>-SNAPSHOT</version>
        </dependency>
    </dependencies>
```

Or you can use clone the project, run a "mvn clean install" and use the dependency in local.

### <a name="examples"></a>Usage Examples
Let's imagine that a server is overdue, you have it's ID and you want to suspend the server
```java
public static void main(String[] args) {
    // First you instantiate the API class (Admin in this case) 
    // and give it your panel URL and API key
    PteroAdminAPI api = new PteroAdminAPI("https://panel.pterodactyl.io/", "api key");
    
    // Then fetch the server using the ServersController
    Server server = api.getServersController().getServer(12 /* Server ID */);
    
    // Now you can suspend the server using it's generic action
    server.suspend();
    
    // You're all set! The server has been suspended.
}
```

Now let's imagine that you want to change the amount of ram a server can use, here's how:
```java
public static void main(String[] args) {
    // First you instantiate the API class (Admin in this case) 
    // and give it your panel URL and API key
    PteroAdminAPI api = new PteroAdminAPI("https://panel.pterodactyl.io/", "api key");
    
    // Then fetch the server using the ServersController
    Server server = api.getServersController().getServer(12 /* Server ID */);
    
    // Now you can update memory of the server using it's explicit action
    server.editBuild().setMemory(4096 /* in MBs, so this is 4GB */).execute();
    
    // You're all set! The memory has been updated.
}
```

One last example, you want to update the email and the name of a user
```java
public static void main(String[] args) {
    // First you instantiate the API class (Admin in this case) 
    // and give it your panel URL and API key
    PteroAdminAPI api = new PteroAdminAPI("https://panel.pterodactyl.io/", "api key");
    
    // Then fetch the user using the UsersController
    User user = api.getUsersController().getUser(88 /* User ID */);
    
    // Now you can update the users emai and username using it's explicit action
    user.edit().setEmail("stanjg@github.com").setName("stanjg").execute();
    
    // You're all set! The email and username has been updated.
}
```

### <a name="documentation"></a>Documentation
### <a name="admin-api-docs"></a>Admin API
#### <a name="servers"></a>Servers
##### <a name="server-object"></a>Server Object
```java
// Fields
server.getLongId()
server.getName()
server.getDescription()
server.getUuid()
server.getShortId()
server.getAllocationId()
server.getEggId()
server.getNestId()
server.getExternalId()
server.getPackId()
server.getNodeId()
server.getOwnerId()
server.isSuspended()

// Field sub classes
server.getContainer()
    container.getStartupCommand()
    container.getImage()
    container.isInstalled()
    container.getEnvironmentVariables()
    
server.getLimits()
    limits.getDisk()
    limits.getMemory()
    limits.getSwap()
    limits.getIo()
    limits.getCpu()
    
server.getFeatureLimits()
    featureLimits.getMaxDatabases()
    featureLimits.getMaxAllocations()

// Relationships
server.getOwner()
```

##### <a name="server-controller"></a>Server Controller
```java
ServersController controller = api.getServersController();

// Fetch All Servers
List<Server> servers = controller.getAllServers();

// Fetch Servers With Search Query
List<Server> servers = controller.getServers("search");

// Fetch Single Server By ID
Server server = controller.getServer(8888);

// Fetch Page of Servers
List<Server> servers = controller.getServerPage(1);

// Fetch Servers For User by ID
List<Server> servers = controller.getServersForUser(88);
```



##### <a name="server-actions"></a>Server Actions
```java
// Explicit Actions
server.editDetails()            -> ServerUpdateDetailsAction
server.editBuild()              -> ServerUpdateBuildAction
server.editStartup()            -> ServerUpdateStartupAction
serversController.createNew()   -> ServerCreateAction

// Generic Actions
server.suspend()
server.unsuspend()
server.reinstall()
server.rebuild()
server.delete()
```

#### <a name="users"></a>Users
##### <a name="user-object"></a>User Object
```java
// Fields
user.getId()
user.getExternalId()
user.getUuid()
user.getUsername()
user.getEmail()
user.getFirstName()
user.getLastName()
user.getLangauge()
user.isAdmin()
user.hasTotpEnabled()

// Relationships
user.getServers()
```

##### <a name="user-controller"></a>User Controller
```java
UsersController controller = api.getUsersController();

// Fetch All Users
List<User> users = controller.getAllUsers();

// Fetch Users With Search Query
List<User> users = controller.getUsers("search");

// Fetch Single User By ID
User users = controller.getUser(8888);

// Fetch Page of Users
List<User> users = controller.getUserPage(1);

// Fetch Users For User by ID
List<User> users = controller.getServersForUser(88);
```

##### <a name="user-actions"></a>User Actions
```java
// Explicit Actions
user.edit()                 -> UserUpdateAction
usersController.createNew() -> UserCreateAction

// Generic Actions
user.delete()
```

### <a name="user-api-docs"></a>User API
Documention still has to be made. Feel free to create it and open a PR :)