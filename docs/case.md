# Case API Spec

## Get All Cases (Done)

Endpoint : GET /api/cases/

Query param : 
- page: Integer, start from 0, default 0
- size: Integer, default 10

Request Header :

- X-API-TOKEN : Token (Mandatory)

Response body (Success) :
```json 
{
  "data": [
    {
      "id": "integer",
      "created_at": "string (date-time)",
      "latitude": "string",
      "longitude": "string",
      "nama_pengguna": "string",
      "status_kasus": "string",
      "nomor_telepon_pengguna": "string"
    }
  ],
  "page": 0,
  "size": 10
}
```

Response body (Failed) 

```json
{
  "errors" : "Unauthorized"
}
```

## Create Case (done)

Endpoint : POST /api/cases

Request Header :

- X-API-TOKEN : Token (Mandatory)

Request Body : 
```json
{
  "createdAt": "TimeStamp",
  "status": "String",
  "latitude": "String",
  "longitude": "String"
}
```

Response Body (Success) :
```json
{
  "data" : "Case created successfully"
}
```

Response Body (Failed) : 
```json
{
  "data" : "Unauthorized"
}
```

## Get Case Detail (done)

Endpoint  : GET /api/cases/{caseId}

Request Header :

- X-API-TOKEN : Token (Mandatory)

Response Body (Success) :

```json
{
  "data" : {
    "id": "integer",
    "created_at": "string (date-time)",
    "latitude": "string",
    "longitude": "string",
    "nama_pengguna": "string",
    "status_kasus": "string",
    "nomor_telepon_pengguna": "string",
    "responses": [
      {
        "image": "string (base64 encoded image)",
        "description": "string"
      }
    ]
  }
}
```

Response Body (failed) :

```json
{
  "errors" : "Unauthorized"
}
```




