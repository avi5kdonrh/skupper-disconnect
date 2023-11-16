# Distributed AMQP clients, powered by skupper

 ##  Prerequisites
 - An AMQP 1.0 compliant broker running on local machine
 - Docker is installed on local machine
 - Skupper CLI is installed on local machine
 - Openshift/Kubernetes CLI client installed on local machine
 - A Kubernetes or Openshift cluster

 ## Procedure
 - Login to the Openshift cluster from a terminal</br>
 ```
oc login --token=sha256~xxxxxxx --server=https://api.myserver.com:6443
 ```
- Initialize Skupper in the selected namespace</br>
 ``` 
skupper init --enable-console --console-password=admin --enable-flow-collector
```
- Initialize Skupper gateway for local services</br>
 ```
skupper gateway init --type docker
  ```
- Create and bind local service</br>
```
skupper service create amq-service 5672
```
```
skupper gateway bind amq-service localhost 5672
  ```
- Deploy the quarkus-jms-consumer project to Openshift</br>
 ```
cd quarkus-jms-consumer &&
mvn clean install -Dquarkus.kubernetes.deploy=true -Dquarkus.kubernetes-client.trust-certs=true
```
- The following screenshot shows that the application is connected to the local AMQ broker through 
  amq-service created with Skupper earlier.
![img.png](img.png)

- Run the quarkus-jms-producer application locally </br>
 ```
cd quarkus-jms-producer && mvn quarkus:dev
```

![img_1.png](img_1.png)

- The application running locally is connecting to the localhost as you can see from the screenshot.

- Call the rest API to send messages to the local application.</br>

```
curl --location 'http://localhost:8080/register' --header 'Content-Type: application/json' --data '{"firstName" : "Foo","lastName" : "Bar",
"address" : "Cwa",
"zipCode" : 1000,
"remarks" : "test"
}'
```

- At this point, the application running on Openshift should receive these messages. Check the application logs to confirm.
![img_2.png](img_2.png)
- The topology can also be viewed using the skupper web console.
![img_4.png](img_4.png)
