
# Tiny Url

Converts long url to short url.

[API-Contract](doc/api.yaml) @ doc/api.yaml

[LLD](doc/LLD.drawio.html) @ doc/LLD.drawio.html

[HLD](doc/HLD.drawio.html) @ doc/HLD.drawio.html

[Code Coverage](build/jacoco/index.html) @ Code Coverage Report

### Create payload :
```
curl --location --request PUT 'localhost:9080/create' \
--header 'Content-Type: application/json' \
--data '{
"url" : "https://stackoverflow.com/questions/11583562/how-to-kill-a-process-running-on-particular-port-in-linux",
"alias" : "test"
}'
```

## HOW TO RUN Locally:
- Install postgres and have it run on port 5433
- Install Zookeeper & run the following commands to set up counter
  ```
  create /counter
  create /counter/0_10M
  create /counter/10_20M
  create /counter/20_30M
  create /counter/30_40M
  set /counter/0_10M 0
  set /counter/10_20M 10000000
  set /counter/20_30M 20000000
  set /counter/30_40M 30000000
  set /counter 9999999 19999999 29999999 39999999 
  ```
- Build jar file
    ```
    ./gradlew build
    ```
- Build docker image
    ```
    sudo docker image build -t app .
    ```
- Run the docker image
   ```
   sudo docker run -e DB_USER_NAME=<your-db-user-name> -e DB_PASSWORD=<your-db-password> --network=host app
   ```

