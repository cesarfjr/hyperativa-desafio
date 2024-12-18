mkdir tmp
mvn clean install
cp target/desafio-hyperativa-backend-*.jar tmp/app.jar
cp docs/docker/Dockerfile tmp/Dockerfile
cd tmp
docker build -t desafio:0.0.1-SNAPSHOT .
cd ..
rm -rfv tmp
