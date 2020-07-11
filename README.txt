ISTRUZIONI IN ORDINE*******************************************************************************************************************************************
~~~~~~~~~~~~~
1. attivare l'addons ingress
minikube addons list
minikube addons enable ingress

kubectl get pods -n kube-system

~~~~~~~~~~~~~~~

2. creare un self signed certificate
openssl req -x509 -newkey rsa:4096 -sha256 -nodes -keyout tls.key -out tls.crt -subj "/CN=example.com" -days 365
kubectl create secret tls example-com-tls --cert=tls.crt --key=tls.key
kubectl get secret -o yaml

~~~~~~~~~~~~~~~~
3. aggiungere l'host DNS virtuale per cui abbiamo creato il certificato
echo "$(minikube ip) example.com" | sudo tee -a /etc/hosts
cat /etc/hosts | tail -1

~~~~~~~~~~~~~~~~

4. Creare l'App Github ... : 
(nuova: https://github.com/settings/applications/new)
(esistente: https://github.com/settings/applications/1243899)

Homepage url:              https://example.com
Authorization callbackUrl: https://example.com/login.html

(url di FE su cui il browser fa la redirect dopo la risposta di Github alla richiesta del FE per il code che riceve come query param)

Copiare ClientID e Secret

~~~~~~~~~~~~~~~

5. sostituire i giusti valori negli yaml dei servizi

~~~~~~~~~~~~~~~

6. kubectl apply -f microservice-deployment.yaml
   kubectl apply -f gateway-deployment.yaml
   kubectl apply -f frontend-deployment.yaml

~~~~~~~~~~~~~~~

7. applicare le ingress resource all'ingress controller gia' abilitato

cat > ingress.yaml << EOF 
apiVersion: extensions/v1beta1
kind: Ingress
metadata:
  name: example-ingress
spec:
  tls:
    - secretName: example-com-tls
      hosts:
        - example.com
  rules:
  - host: example.com
    http:
      paths:
      - path: /
        backend:
          serviceName: frontend-lb
          servicePort: 80 
      - path: /javalin-api-gateway
        backend:
          serviceName: gateway-lb
          servicePort: 80
EOF

kubectl apply -f ingress.yaml

~~~~~~~~~~~~~~

dal browser:

https://example.com

con curl 

curl -k https://example.com

~~~~~~~~~~~~~~


~~~~~~~~~~~~~~~~~~~~~~~~

$ cat gateway-deployment.yaml 

apiVersion: v1
kind: Service              
metadata:
  name: gateway-lb
spec:
  type: LoadBalancer       
  ports:
  - port: 80              
    targetPort: 4000        
  selector:            
    app: gateway    
---
apiVersion:  apps/v1 #extensions/v1beta1
kind: Deployment
metadata:
  name: gateway
  labels:
    app: gateway
spec:
  replicas: 2                                             
  minReadySeconds: 15
  strategy:
    type: RollingUpdate                                   
    rollingUpdate: 
      maxUnavailable: 1                                   
      maxSurge: 1                                         
  selector:
    matchLabels:
      app: gateway
      tier: experiment
  strategy:
    type: Recreate
  template:
    metadata:
      labels:
        app: gateway
        tier: experiment
    spec:
      containers:
      - image: alessandroargentieri/exp-gateway
        name: gateway
        env:
        - name: OAUTH2_CLIENT_SECRET
          value: "880bbe71b5015700cbd46f4411f7c4e437dc6abb"
        - name: OAUTH2_ACCESS_TOKEN_URL
          value: "https://github.com/login/oauth/access_token"
        - name: OAUTH2_PROVIDER_URL
          value: "https://api.github.com"
        - name: ENCRYPTION_PASSPHRASE
          value: "gTrvB78hfjkhg"
        - name: JWT_SECRET_KEY
          value: "hGyhjjg65fh6g"
        - name: SERVICE_NAME_1
          value: "javalin-api"
        - name: SERVICE_HOST_1
          value: "http://microservice-lb"  
        ports:
        - containerPort: 4000
          name: gateway

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   

$ cat Dockerfile 

# Dockerfile gateway

# install the base image
FROM openjdk:8-jdk-alpine

# create directory in the container
RUN mkdir /app 

# copy called.jar into the newly created directory
ADD gateway.jar /app/

# establish this directory as the working directory
WORKDIR /app 

# launch the microservice
CMD ["java", "-jar", "/app/gateway.jar"]

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


$ cat microservice-deployment.yaml 
apiVersion: v1
kind: Service              
metadata:
  name: microservice-lb
spec:
  type: NodePort       
  ports:
  - port: 80              
    targetPort: 7000        
  selector:            
    app: microservice    
---
apiVersion:  apps/v1 #extensions/v1beta1
kind: Deployment
metadata:
  name: microservice
  labels:
    app: microservice
spec:
  replicas: 2                                             
  minReadySeconds: 15
  strategy:
    type: RollingUpdate                                   
    rollingUpdate: 
      maxUnavailable: 1                                   
      maxSurge: 1                                         
  selector:
    matchLabels:
      app: microservice
      tier: experiment
  strategy:
    type: Recreate
  template:
    metadata:
      labels:
        app: microservice
        tier: experiment
    spec:
      containers:
      - image: alessandroargentieri/exp-microservice
        name: microservice
        ports:
        - containerPort: 7000
          name: caller

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

$ cat Dockerfile 

# Dockerfile microservice

# install the base image
FROM openjdk:8-jdk-alpine

# create directory in the container
RUN mkdir /app 

# copy called.jar into the newly created directory
ADD microservice.jar /app/

# establish this directory as the working directory
WORKDIR /app 

# launch the microservice
CMD ["java", "-jar", "/app/microservice.jar"]

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


$ cat frontend-nginx-deployment.yaml 
apiVersion: v1
kind: Service              
metadata:
  name: frontend-lb
spec:
  type: LoadBalancer       
  ports:
  - port: 80              
    targetPort: 80
  selector:            
    app: frontend    
---
apiVersion:  apps/v1 #extensions/v1beta1
kind: Deployment
metadata:
  name: frontend
  labels:
    app: frontend
spec:
  replicas: 2                                             
  minReadySeconds: 15
  strategy:
    type: RollingUpdate                                   
    rollingUpdate: 
      maxUnavailable: 1                                   
      maxSurge: 1                                         
  selector:
    matchLabels:
      app: frontend
      tier: experiment
  strategy:
    type: Recreate
  template:
    metadata:
      labels:
        app: frontend
        tier: experiment
    spec:
      containers:
      - image: alessandroargentieri/exp-frontend-nginx
        name: frontend
        env:
        - name: OAUTH2_CLIENT_ID
          value: "efe81665e4bbab322bb8"
        - name: FRONTEND_HOST
          value:  "https://example.com" # "http://192.168.64.2:32065"     # http://frontend-lb  
        - name: LOGIN_REDIRECT_PATH
          value: "login.html" # "login/oauth2/code/github"
        - name: OAUTH2_STATE
          value: "fknrrdyjikfn"
        - name: OAUTH2_SCOPE
          value: "user"   
        - name: GATEWAY_HOST
          value: "https://example.com" # "https://example.com/api" # "http://192.168.64.2:32392"    # http://gateway-lb
        - name: GATEWAY_ROOT_PATH
          value: "javalin-api-gateway"
        - name: OAUTH2_RESOURCE_PROVIDER_URL
          value: "https://github.com/login/oauth/authorize"   
        ports:
        - containerPort: 80
          name: frontend

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

$ tree -C
.
├── Dockerfile
├── frontend-nginx-deployment.yaml
├── index.html
├── js
│   ├── env.template.js
│   ├── index-script.js
│   └── login-script.js
└── login.html

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

$ cat Dockerfile 
FROM nginx:alpine
COPY .  /usr/share/nginx/html

# When the container starts, replace the env.js with values from environment variables
CMD ["/bin/sh",  "-c",  "envsubst < /usr/share/nginx/html/js/env.template.js > /usr/share/nginx/html/js/env.js && exec nginx -g 'daemon off;'"]

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


