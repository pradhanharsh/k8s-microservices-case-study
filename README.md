# Kubernetes Microservices Case Study 

This project demonstrates deploying two microservices (`MicroserviceA` and `MicroserviceB`) into a Kubernetes cluster.  
It covers containerization using **Docker**, deployment with **Kubernetes YAML manifests**, and inter-service communication inside the cluster.


## Architecture

- MicroserviceA  
  - Exposes `/callB` endpoint.  
  - Internally calls `MicroserviceB` via Kubernetes **service discovery**.  

- MicroserviceB  
  - Exposes `/hello` endpoint.  
  - Returns a greeting message with a request counter.  

- Communication Flow
  -Client (Browser / Postman) → MicroserviceA → MicroserviceB → Response


---

## 🛠️ Tech Stack

- Java 17
- Spring Boot 3.x
- Docker
- Kubernetes (kubectl, minikube / k3s / docker-desktop cluster)
- YAML manifests for deployments & services


## Project Structure
k8s-microservices-case-study/
│── MicroserviceA/
│ ├── src/main/java/com/raisethebar/MicroserviceA/
│ │ ├── MicroserviceAApplication.java
│ │ └── ServiceAController.java
│ ├── Dockerfile
│ └── pom.xml
│
│── MicroserviceB/
│ ├── src/main/java/com/raisethebar/MicroserviceB/
│ │ ├── MicroserviceBApplication.java
│ │ └── ServiceBController.java
│ ├── Dockerfile
│ └── pom.xml
│
│── k8s/
│ ├── microservicea-deployment.yaml
│ ├── microservicea-service.yaml
│ ├── microserviceb-deployment.yaml
│ └── microserviceb-service.yaml


---

##Setup Instructions

1. Clone Repository
bash
git clone https://github.com/pradhanharsh/k8s-microservices-case-study.git
cd k8s-microservices-case-study

2. Build JARs
cd MicroserviceA
mvn clean package -DskipTests
cd ../MicroserviceB
mvn clean package -DskipTests

3. Build Docker Images
docker build -t harshdockerrtb/microservicea:1.0 ./MicroserviceA
docker build -t harshdockerrtb/microserviceb:1.0 ./MicroserviceB

4. Push Images to DockerHub
docker login
docker push harshdockerrtb/microservicea:1.0
docker push harshdockerrtb/microserviceb:1.0

5. Apply Kubernetes Manifests
kubectl apply -f k8s/microservicea-deployment.yaml
kubectl apply -f k8s/microservicea-service.yaml
kubectl apply -f k8s/microserviceb-deployment.yaml
kubectl apply -f k8s/microserviceb-service.yaml

6. Verify Deployments
kubectl get pods
kubectl get svc

**Testing the Services**

1. Get the NodePort assigned to MicroserviceA:

kubectl get svc microservicea-service

Example output:

NAME                    TYPE       CLUSTER-IP     EXTERNAL-IP   PORT(S)          AGE
microservicea-service   NodePort   10.102.223.108 <none>        8080:32219/TCP   10m
→ Here, 32219 is the NodePort.

1. Access endpoints:

##MicroserviceA → http://localhost:<NodePort>/callB
Example: http://localhost:32219/callB

##MicroserviceB is not exposed externally (ClusterIP). It is only accessible from within the cluster at:
http://microserviceb-service:8080/hello

Sample Output
**Calling MicroserviceA endpoint:**
http://localhost:32219/callB
Response from Service B: Hello from Service B! Request count: 5

**Internal call (ServiceA → ServiceB):**
Hello from Service B! Request count: 19

Notes

MicroserviceA uses RestTemplate to call MicroserviceB via service name (microserviceb-service).

MicroserviceB runs on port 8081 internally but exposed as 8080 in Kubernetes Service.

Kubernetes ClusterIP allows internal communication, and NodePort exposes MicroserviceA externally.

DockerHub repo: harshdockerrtb

**Author

Harsh Pradhan**
