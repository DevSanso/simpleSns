# /register


---

**Method** : `GET`
**역할** : `html 페이지`

## Success Response
**상태 코드** : `200 OK`
## Error Response
**에러 코드** 
- `400 Bad Request`
- `500 Internal Server Error`

---


**Method** : `POST`
**역할** : `회원가입`

**Form Parameter**
``` 
name : String
id : String
password : String
imageData : String (dataurl)
```


## Success Response
**Rediect Url** : `/result`
**상태 코드** : `301 Redirect`
## Error Response
**에러 코드** 
- `400 Bad Request`
- `500 Internal Server Error`
