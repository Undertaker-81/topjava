rest api

PUT http://localhost:8080/topjava/rest/meals/100002

Accept: */*

Cache-Control: no-cache

Content-Type: application/json

{ "id": 100002, "dateTime": "2020-01-30T10:00:00", "description": "ЗавтракEdit", "calories": 500 }

POST http://localhost:8080/topjava/rest/meals/

Accept: */*
Cache-Control: no-cache
Content-Type: application/json


{ "dateTime": "2020-11-20T10:00:00", "description": "Ужин", "calories": 500 }

GET ALL

GET http://localhost:8080/topjava/rest/meals/

GET ID 100002

GET http://localhost:8080/topjava/rest/meals/100002

DELETE ID 100011 if exist

DELETE http://localhost:8080/topjava/rest/meals/100011

GET FILTERED

get http://localhost:8080/topjava/rest/meals/filtered?startDate=&endDate=&startTime=&endTime=
