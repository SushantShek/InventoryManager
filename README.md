### `REQUIREMENTS`
The primary values for the code we look for are: simplicity, readability, maintainability, testability. It should be easy to scan the code, and rather quickly understand what it’s doing. Pay attention to naming.

#### `SPECIFICATIONS`

* The assignment is to implement a warehouse software. This software should hold articles, and the articles should contain an identification number, a name and available stock. It should be possible to load articles into the software from a file, see the attached inventory.json.
* The warehouse software should also have products, products are made of different articles. Products should have a name, price and a list of articles of which they are made from with a quantity. The products should also be loaded from a file, see the attached products.json. 

#### `FUNCTIONALITY`
The warehouse should have at least the following functionality;
* Get all products and quantity of each that is an available with the current inventory
* Remove(Sell) a product and update the inventory accordingly

###### `SETUP`
Redis For In-memory cache/DB
Docker image redis
`_ docker run -p 16379:6379 -d redis:6.0 redis-server --requirepass "mypass" _`

JAVA-11
MAVEN
TOMCAT PORT 8081

**Service URL's**

###### `Inventory`
curl --location --request POST 'http://localhost:8081/api/inventory' \
--form 'file=@"<LocalFilePath>/inventory.json"'

curl --location --request GET 'http://localhost:8081/api/inventory'

###### `Product`
curl --location --request POST 'http://localhost:8081/api/products' \
--form 'file=@"/<LocalFilePath>/products.json"'

curl --location --request GET 'http://localhost:8081/api/products'

curl --location --request PUT 'http://localhost:8081/api/products' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Dinning Table",
    "contain_articles": [
        {
            "art_id": 1,
            "amount_of": 4,
            "quantity": 6
        },
        {
            "art_id": 2,
            "amount_of": 8,
            "quantity": 7
        },
        {
            "art_id": 4,
            "amount_of": 1,
            "quantity": 10
        }
    ]
}'
