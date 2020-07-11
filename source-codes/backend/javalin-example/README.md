# Javalin Example

Example of the usage of Javalin (http://javalin.io) a lightweight framework to build Java http server.
Inside the MainApplication.java class you will find:

- some simple configurations for embedded Jetty
- definition for a context path for the whole microservice
- input validation
- Json databinding (with Jackson Library)
- Non-Blocking response (with the usage of java CompletableFutures).

In the deploy folder you can find the fat-jar created with *$ mvn clean package* the Dockerfile and the kubernetes deployment.yaml file.

Here, syntetically, some steps to deploy on kubernetes cluster:

1. create the fat-jar file mvn clean package
2. create and set the Dockerfile in the same folder of the fat-jar file
3. build docker image using the Dockerfile
4. login on Dockerhub and push the newly created docker image
5. create a deployment.yaml file which uses that image
6. start or connect to kubernetes cluster and call kubectl apply method
7. test the service through kubernetes

### apply the deployment
```
$ sudo kubectl apply -f javalin-deployment.yaml
service/javalin-lb configured
deployment.apps/javalin created
```

### get the pods 
```
$ sudo kubectl get pods
NAME                       READY   STATUS    RESTARTS   AGE
javalin-745945dc8c-d279c   1/1     Running   0          11s
```

### get the load balancer service 
```
$ sudo kubectl get services | grep javalin
javalin-lb         LoadBalancer   10.152.183.253   <pending>     80:30536/TCP        16m
```
### describe the service
```
$ sudo kubectl describe service javalin-lb | grep Endpoints
Endpoints:                10.1.36.16:7000
```

### test
```
curl http://10.1.36.16:7000/javalin-api
```



### NOTES - Dockerfile:
```
# install the base image
FROM openjdk:8-jdk-alpine

# create directory in the container
RUN mkdir /app 

# copy called.jar into the newly created directory
ADD javalin-example-fat.jar /app/

# establish this directory as the working directory
WORKDIR /app 

# launch the microservice
CMD ["java", "-jar", "/app/javalin-example-fat.jar"]

```


### NOTES - javalin-deployment.yaml:
```
apiVersion: v1
kind: Service              
metadata:
  name: javalin-lb
spec:
  type: LoadBalancer       
  ports:
  - port: 80             
    targetPort: 7000        
  selector:            
    app: javalin    
---
apiVersion: apps/v1 #apps/v1beta2 #extensions/v1beta2 #extensions/v1beta1
kind: Deployment
metadata:
  name: javalin
  labels:
    app: javalin
spec:
  replicas: 1                                             
  minReadySeconds: 15
  strategy:
    type: RollingUpdate                                   
    rollingUpdate: 
      maxUnavailable: 1                                   
      maxSurge: 1                                         
  selector:
    matchLabels:
      app: javalin
      tier: javalin-example
  strategy:
    type: Recreate
  template:
    metadata:
      labels:
        app: javalin
        tier: javalin-example
    spec:
      containers:
      - image: alessandroargentieri/javalin-example
        name: javalin
        ports:
        - containerPort: 7000
          name: javalin
```
