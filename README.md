# ========== Problem Statement ========== 
Build a RESTful service that implments a check out counter for an online retail store that scans products and generates an itemized bill.
The bill should also total the cost of all the products and the applicable sales tax for each product.
The total cost and total sales tax should be printed
Sales tax varies based on the type of products
- category A products carry a levy of 10%
- category B products carry a levy of 20%
- category C products carry no levy

# ========== Solution ========== 

# Step-1: Deploy My Retail Store Service WAR to Tomcat
- Clone GIT Repository locally using this URL : https://github.com/124938/my-retail-store.git
- Import Maven Project in eclipse
- Deploy myretailstore-service war in Tomcat
- Open browser and type this URL to see available REST services exposed : http://localhost:8080/myretailstore-service/api

# Step-2: Create New Cart Id
curl -H "Content-Type: application/json" -X GET http://localhost:8080/myretailstore-service/api/cart/new

# Step-3: Fetch Cart Details - 1455435427311 should be replaced by Cart Id generated in Step-1
curl -H "Content-Type: application/json" -X GET http://localhost:8080/myretailstore-service/api/cart/1455435427311

# Step-4: Update Cart by adding item code "PRD-007" - 1455435427311 should be replaced by Cart Id generated in Step-1
curl -H "Content-Type: application/json" -X POST -d '{"code":"PRD-007","quantity":"25"}' http://localhost:8080/myretailstore-service/api/cart/1455435427311/update

# Step-5: Update Cart by adding item code "PRD-008" - 1455435427311 should be replaced by Cart Id generated in Step-1
curl -H "Content-Type: application/json" -X POST -d '{"code":"PRD-008","quantity":"50"}' http://localhost:8080/myretailstore-service/api/cart/1455435427311/update

# Step-6: Checkout Cart - 1455393455969 should be replaced by Cart Id generated in Step-1
curl -H "Content-Type: application/json" -X GET http://localhost:8080/myretailstore-service/api/cart/1455435427311/checkout
