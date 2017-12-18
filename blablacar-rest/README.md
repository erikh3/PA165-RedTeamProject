# Examples of this REST api:

For easy manual testing [Insomnia](https://insomnia.rest/) REST client is recommended. 
You can import [this provided file](blablacar_insomnia_export.json) into your insomnia client and you will see every available API call.

## Curl commands:

##### root
```shell
curl --request GET \
  --url http://localhost:8080/pa165/rest/
```

### comments

##### list comments
```shell
curl --request GET \
  --url http://localhost:8080/pa165/rest/comments
```

##### get single comment
```shell
curl --request GET \
  --url http://localhost:8080/pa165/rest/comments/1
```

##### get comments with author
```shell
curl --request GET \
  --url http://localhost:8080/pa165/rest/comments/by-author/2
```

##### get comments with ride
```shell
curl --request GET \
  --url http://localhost:8080/pa165/rest/comments/by-ride/4
```

##### delete single comment
```shell
curl --request DELETE \
  --url http://localhost:8080/pa165/rest/comments/1
```

##### create new comment
```shell
curl --request POST \
  --url http://localhost:8080/pa165/rest/comments/create \
  --header 'content-type: application/json' \
  --data '{
	"text": "A new comment created via REST API",
	"rideId": 1,
	"authorId": 1
}'
```

##### change comment's text
```shell
curl --request POST \
  --url http://localhost:8080/pa165/rest/comments/change-text \
  --header 'content-type: application/json' \
  --data '{
	"commentId": 1,
	"text": "Changed text"
}'
```

### rides

##### list rides
```shell
curl --request GET \
  --url http://localhost:8080/pa165/rest/rides
```

##### get single ride
```shell
curl --request GET \
  --url http://localhost:8080/pa165/rest/rides/5
```

##### get rides with driver
```shell
curl --request GET \
  --url http://localhost:8080/pa165/rest/rides/by-driver/1
```

##### get rides with passenger
```shell
curl --request GET \
  --url http://localhost:8080/pa165/rest/rides/by-passenger/2
```

##### delete ride
```shell
curl --request DELETE \
  --url http://localhost:8080/pa165/rest/rides/1
```

##### create new ride
```shell
curl --request POST \
  --url http://localhost:8080/pa165/rest/rides/create \
  --header 'content-type: application/json' \
  --data '{
	"departure": "20-6-2016 20:00",
	"driverId": 1,
	"sourcePlaceId": 1,
	"destinationPlaceId": 2,
	"seatPrize": 124.5,
	"seatsAvailable": 2
}'
```

##### add passenger
```shell
curl --request POST \
  --url http://localhost:8080/pa165/rest/rides/add-passenger \
  --header 'content-type: application/json' \
  --data '{
	"rideId": 5,
	"passengerId": 1
}'
```

##### remove passenger
```shell
curl --request POST \
  --url http://localhost:8080/pa165/rest/rides/remove-passenger \
  --header 'content-type: application/json' \
  --data '{
	"rideId": 5,
	"passengerId": 1
}'
```
##### change seat price
```shell
curl --request POST \
  --url http://localhost:8080/pa165/rest/rides/change-price \
  --header 'content-type: application/json' \
  --data '{
	"rideId": 1,
	"price": 12.7
}'
```

##### change number of available seats
```shell
curl --request POST \
  --url http://localhost:8080/pa165/rest/rides/change-seats \
  --header 'content-type: application/json' \
  --data '{
	"rideId": 1,
	"seats": 1000
}'
```

##### change departure
```shell
curl --request POST \
  --url http://localhost:8080/pa165/rest/rides/change-departure \
  --header 'content-type: application/json' \
  --data '{
	"rideId": 1,
	"departure": "13-10-2016 22:00"
}'
```

### users

##### list users
```shell
curl --request GET \
  --url http://localhost:8080/pa165/rest/users
```

##### get single user
```shell
curl --request GET \
  --url http://localhost:8080/pa165/rest/users/1
```

##### delete user
```shell
curl --request DELETE \
  --url http://localhost:8080/blablacar-rest/users/5
```

##### create new user
```shell
curl --request POST \
  --url http://localhost:8080/pa165/rest/users/create \
  --header 'content-type: application/json' \
  --data '{
	"name": "Jan",
	"surname": "Novák",
	"nickname": "Jsan_Novak"
}'
```

### places

##### list places
```shell
curl --request GET \
  --url http://localhost:8080/pa165/rest/places
```

##### get single place
```shell
curl --request GET \
  --url http://localhost:8080/pa165/rest/places/1
```

##### delete place
```shell
curl --request DELETE \
  --url http://localhost:8080/pa165/rest/places/1
```

##### create new place
```shell
curl --request POST \
  --url http://localhost:8080/pa165/rest/places/create \
  --header 'content-type: application/json' \
  --data '{
	"name": "Sample place"
}'
```
