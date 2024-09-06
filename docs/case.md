# Case API Spec

## Get All Cases

Endpoint : GET /api/cases

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
  ]
}
```

Response body (Failed) 

```json
{
  "errors" : "Unauthorized"
}
```

## Create Case

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

## Get Case Detail 

Endpoint  : GET /api/cases/{id}

Request Header :

- X-API-TOKEN : Token (Mandatory)

Response Body (Success) :

```json
{
  "data" : {
    "id": "integer",
    "created_at": "string (date-time)",
    "latitude": "number (float)",
    "longitude": "number (float)",
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




