# my-retail-store
My retail store - MO Assignment

# Step-1: Create New Cart Id
curl -H "Content-Type: application/json" -X GET http://localhost:8080/myretailstore-service/api/cart/new

# Step-2: Fetch Cart Details - 1455435427311 should be replaced by Cart Id generated in Step-1
curl -H "Content-Type: application/json" -X GET http://localhost:8080/myretailstore-service/api/cart/1455435427311

# Step-3: Update Cart by adding item code "PRD-007" - 1455435427311 should be replaced by Cart Id generated in Step-1
curl -H "Content-Type: application/json" -X POST -d '{"code":"PRD-007","quantity":"25"}' http://localhost:8080/myretailstore-service/api/cart/1455435427311/update

# Step-4: Update Cart by adding item code "PRD-008" - 1455435427311 should be replaced by Cart Id generated in Step-1
curl -H "Content-Type: application/json" -X POST -d '{"code":"PRD-008","quantity":"50"}' http://localhost:8080/myretailstore-service/api/cart/1455435427311/update

# Step-5: Checkout Cart - 1455393455969 should be replaced by Cart Id generated in Step-1
curl -H "Content-Type: application/json" -X GET http://localhost:8080/myretailstore-service/api/cart/1455435427311/checkout
