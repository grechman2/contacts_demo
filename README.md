![Java CI with Maven](https://github.com/grechman2/contacts_demo/workflows/Java%20CI%20with%20Maven/badge.svg)

**Demo contacts spring boot 2 application**

You need to have a docker and docker-composed to be installed on your pc.

To run the app

1. Clone the repository
2. Run command **mvn clean install -Ddocker** to generate docker image
3. Run app by **docker-compose up**

**Test the app**

Get all contacts that doesn't match with following regex pattern **^В.\*$**
```
curl -G -v "http://localhost:8100/hello/contact/" --data-urlencode "nameFilter=^В.*$"
```
Get all contacts that doesn't contains word Tom **Tom**
```
curl -G -v "http://localhost:8100/hello/contact/" --data-urlencode "nameFilter=Tom"
```

**Docker commands:**

1. To check what containers are running **docker ps**
2. Remove container **docker rm -f <container-id>**
3. To stop all of the containers **docker rm -f $(docker ps -a -q)**
