# User API Spec

## Register User (Done)

Endpoint : POST /api/users

Request Body :

```json
{
  "nik" : "123456",
  "nama pengguna" : "Jonathan Alezandro",
  "password" : "rahasia",
  "nomor telepon" : "1234567",
  "kecamatan" : "Dimembe",
  "kelurahan" : "Dimembe",
  "lingkungan" : "3"
}
```

Response Body (Success) :

```json
 {
    "data" : "User created successfully"
 }
```

Response Body (Failed) : 

```json
{
  "errors": "Username must not blank, ???"
}
```

## Login User (Done)

Endpoint : POST /api/auth/login

Request Body : 

```json
{
  "nik" : "123456",
  "password" : "rahasia"
}
```

Response Body (Success):
```json
{
  "data": {
    "nama pengguna" : "Jonathan",
    "token": "TOKEN",
    "expiredAt": 12312312343 //milliseconds
  }
}
```

Response Body (Failed, 401):
```json
{
  "errors": "Username or password wrong" 
}
```

## Get User (Done)

Endpoint : GET /api/users/current

Request Header :

- X-API-TOKEN: Token (Mandatory)

Response Body (Success):
```json
{
  "data": {
    "nik" : "123456",
    "nama pengguna" : "Jonathan Alezandro",
    "password" : "rahasia",
    "nomor telepon" : "1234567",
    "kecamatan" : "Dimembe",
    "kelurahan" : "Dimembe",
    "lingkungan" : "3"
  }
}
```

Response Body (Failed, 401):
```json
{
  "errors": "Unauthorized" 
}
```

## Logout User 

Endpoint : DELETE /api/auth/logout

Request Header :

- X-API-TOKEN : Token (Mandatory)

Response Body :
```json
{
  "data": "OK"
}
```
