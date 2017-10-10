# joveo-server
a tcp network library implement the HTTP protocol with the  following capabilities.  Support HTTP methods GET, PUT, POST, DELETE

How to run App:

Checkout this project and Import as maven project in IntelliJ. Run main application class JoveoServer 

Sample PUT curl :

curl -X PUT -H "Content-Type: application/json" -H "Cache-Control: no-cache" -H "Postman-Token: 921048c5-4ad5-798b-5050-83d09978e5d3" -d '{
  "name": "SDE II"
}' "http://localhost:9333/job"

Sample GET curl :

curl -X GET -H "Content-Type: application/json" -H "Cache-Control: no-cache" -H "Postman-Token: 19c516e6-c1eb-9b46-116d-758e5bcf2d49" "http://localhost:9333/job?id=28b10ec9-1482-4ddc-916d-7953896f2b9d"

Sample DELETE curl :

curl -X DELETE -H "Content-Type: application/json" -H "Cache-Control: no-cache" -H "Postman-Token: 892fd96d-6093-ecfb-906b-912c90a3dffa" -d '' "http://localhost:9333/job?id=19d2cd04-5ea8-47df-a74e-1ba7f78993b8"

Sample POST curl :

curl -X POST -H "Content-Type: application/json" -H "Cache-Control: no-cache" -H "Postman-Token: 8c546b78-242b-6a0b-9cad-bd8224f659d7" -d '{
  "name": "SDE III",
  "id": "f0585ad8-40f9-43a8-8e19-cfaecf48cbc2"
}' "http://localhost:9333/job"


