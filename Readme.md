
# Tiny Url

Converts long url to short url.

[API-Contract](doc/api.yaml) @ doc/api.yaml

[LLD](doc/LLD.drawio.html) @ doc/LLD.drawio.html

[HLD](doc/HLD.drawio.html) @ doc/HLD.drawio.html

### Create payload :
curl --location --request PUT 'localhost:9080/create' \
--header 'Content-Type: application/json' \
--data '{
"url" : "https://mvnrepository.com/artifact/org.springframework.data/spring-data-redis/3.3.0"
}'
