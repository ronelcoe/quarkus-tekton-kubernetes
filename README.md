# Quarkus Proejct Creation
1. mvn command with docker and kubernetes plugin

mvn io.quarkus:quarkus-maven-plugin:2.7.0.Final:create \
    -DprojectGroupId=org.acme \
    -DprojectArtifactId=quarkus-tekton-kubernetes \
    -DclassName="org.acme.quickstart.GreetingResource" \
    -Dextensions=quarkus-kubernetes,container-image-docker \
    -Dpath="/hello"

# Call SVC from different pod via curl
kubectl run curl --image=radial/busyboxplus:curl -i --tty

## Setup Tekton CICD Pipeline

1. Git Clone
kubectl apply -f https://raw.githubusercontent.com/tektoncd/catalog/main/task/git-clone/0.6/git-clone.yaml

2. Maven
kubectl apply -f https://raw.githubusercontent.com/tektoncd/catalog/main/task/maven/0.2/maven.yaml

3. Buildah
kubectl apply -f https://raw.githubusercontent.com/tektoncd/catalog/main/task/buildah/0.3/buildah.yaml

    a. Needs to setup K8s secret for Docker Registry credential

k create secret docker-registry secret-docker-credentials \
--docker-server='index.docker.io' \
--docker-username=$DOCKER_HUB_USERNAME \
--docker-password=$DOCKER_HUB_PASSWORD \
--docker-email=$DOCKER_HUB_EMAIL

    b. Create service account to use in PipelineRun

apiVersion: v1
kind: ServiceAccount
metadata:
  name: build-bot
secrets:
  - name: secret-docker-credentials

4. Kubectk Actions
kubectl apply -f https://raw.githubusercontent.com/tektoncd/catalog/main/task/kubernetes-actions/0.2/kubernetes-actions.yaml


Tekton Dashboard Setup
1. 
kubectl apply --filename https://github.com/tektoncd/dashboard/releases/latest/download/tekton-dashboard-release.yaml

2. Optional port forward or change svc to NodePort
kubectl --namespace tekton-pipelines port-forward svc/tekton-dashboard 9097:9097


# Qurkus run in local
./mvnw compile quarkus:dev

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application
./mvnw package

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/quarkus-tekton-kubernetes-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

## Provided Code

### RESTEasy JAX-RS

Easily start your RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started#the-jax-rs-resources)
