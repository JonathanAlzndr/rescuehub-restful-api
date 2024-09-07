# Case Report API Spec

## Create Case Report 

Endpoint : POST api/cases/{id}/response

Request Header :

- X-API-TOKEN : Token (Mandatory)

Request Body : 
```json
{
  "image": "string (base64 encoded image)",
  "description": "string"
}
```

Response Body : 

```json
{
  "data" : "Response submitted successfully and case status updated."
}
```


## Update Case Status

Endpoint : PUT api/cases/{id}

Request Header :

- X-API-TOKEN : Token (Mandatory)

Request Body : 
```json
{
  "status_kasus": "string"
}
```

Response Body (Success) :
```json
{
  "data" : "Case Status Updated Successfully"
}
```

