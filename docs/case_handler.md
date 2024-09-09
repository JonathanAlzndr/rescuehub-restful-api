# Case Report API Spec

## Create Case Report (Done)

Endpoint : POST /api/cases/{caseId}/response

Request Header :

- X-API-TOKEN : Token (Mandatory)

Request Body : 
```json
{
  "image": "string",
  "description": "string"
}
```

Response Body (Success) : 

```json
{
  "data" : "Report submitted successfully and case status updated."
}
```

Response Body (Failed) : 
```json
{
  "data" : "Case report failed"
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

