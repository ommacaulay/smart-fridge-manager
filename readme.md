_smart-fridge-manager_ is a java springboot micro-service with an in memory H2 database running on an embedded tomcat.
It is designed to accept event and query calls from a smart fridge.

**Assumptions in design**
1) All events sent by smart fridge are as a result of validated actions.
2) All event parameters are valid hence require no validations required. 
3) SmartFridgeManager Micro service runs in a cloud distributed system where authentication is taken care of by another authentication micro service.
4) Smart Fridge stocks 5 item types
    - Dairy
    - Fish
    - Meat
    - Soda
    - Vegetables

**Instructions to Run.**
1) Navigate to project folder. 
2) `./gradlew clean build` will run Integration tests and build.
3) `./gradlew bootRun` will run the application.
You can visualize data by going to the in memory H2 DB Console at http://localhost:8080/h2-console/ and connect with default credentials
4) Alternatively if you have Docker installed, `./gradlew jibDockerBuild` to build a Docker image and `docker container run -d --name smart-fridge-manager -p 8080:8080 <image name: version>`

REST APIs trigger service calls.
* handleItemAdd - POST http://localhost:8080/event/v1/item-added
```
Request Body:
{
	"uuid": "7c6e0e56-1952-11e9-ab14-d663bd873d93",
	"name": "pak choi",
	"fillFactor": 0.01,
	"type": 
		{
			"id": 5
		}
}
```

* DELETE http://localhost:8080/event/v1/item-removed/{id}

* getItems - GET http://localhost:8080/query/v1/items?fillFactor={fillFactor}

* getFillFactor - GET http://localhost:8080/query/v1/types/{id}/fill-factor

* forgetItem - PUT http://localhost:8080/query/v1/types/{id}/forget

**Possible future improvements.** 
1) Remember Item service.
2) Add Type service.
3) ItemAdded service improved to receive a collection of items. 
4) Get single Item service. 
